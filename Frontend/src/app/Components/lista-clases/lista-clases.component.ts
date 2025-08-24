import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ClaseService } from '../../services/clase.service';
import { ClasesTabla } from '../../interfaces/Clase-tabla';
import { tablaGenericaComponent } from '../../shared/Components/tabla-generica/tabla-generica.component';
import { filtrogenericoComponent } from '../../shared/Components/filtro-generico/filtro-generico.component';
import { filtrodef } from "../../interfaces/filtro-def"
import { ColumnaDef } from '../../interfaces/columnas-def';
import { AccionDef } from '../../interfaces/acciones-tabla';
import { ModalComponent } from '../../helper/modalhelper/modalhelper';
import { CalendarComponent } from './calendario/calendario.component';
import { CommonModule } from '@angular/common';
import { CategoriaServices } from '../../services/categoria.service';
import { finalize } from 'rxjs/operators';




@Component({
  selector: 'app-lista-clases',
  standalone: true,
  imports: [tablaGenericaComponent, filtrogenericoComponent, ModalComponent, CalendarComponent, CommonModule],
  templateUrl: './lista-clases.component.html',
})
export class ListaClasesComponent implements OnInit {

  constructor(
    private claseService: ClaseService,
    private router: Router,
    private categoriaService: CategoriaServices



  ) { }
  categoriasConColor: { nombre: string; color: string; }[] = [];
  loadingCategorias = true;
  paginaActual = 0;
  elementosPorPagina = 8;
  totalClases = 0;
  mostrarModal = false;
  claseSeleccionada: ClasesTabla | null = null;

  clasesPagina: ClasesTabla[] = [];
  clasesFiltradasPagina: ClasesTabla[] = []; // Datos filtrados localmente  

  columnasTabla: ColumnaDef[] = [
    { field: 'nombre', header: 'Nombre' },
    { field: 'instructor', header: 'Instructor' },
    {
      field: 'categoria', header: 'Categoría', formatter: (row: any) => ({
        label: row.categoria,
        color: row.categoriaColor
      })
    },

    { field: 'sala', header: 'Sala' },
    { field: 'plazas', header: 'Capacidad' },
    { field: 'duracion', header: 'Duración' },

    {
      field: 'estado',
      header: 'Estado',
      formatter: (row: any) => {
        let color = '';
        if (row.estado.toLowerCase() === 'llena') {
          color = '#ef4444'; // rojo (tailwind red-500)
        } else if (row.estado.toLowerCase() === 'disponible') {
          color = '#22c55e'; // verde (tailwind green-500)
        }
        return {
          label: row.estado,
          color
        };
      }
    }, { field: 'fechaInicio', header: 'Fecha inicio' },
    { field: 'fechaFinal', header: 'Fecha final' },

  ];

  //acciones de la tabla
  accionesVisibles = [
    { name: 'Horario', visible: true },
  ];

  // Filtros disponibles
  filtrosDisponibles: filtrodef[] = [
    { field: 'categoria', label: 'Categoría', isMulti: false },
    { field: 'instructor', label: 'Instructor', isMulti: false },
    { field: 'estado', label: 'Estado', isMulti: false },
  ];



  ngOnInit() {
    this.obtenerClases();
    this.obtenerCategorias();

  }
  obtenerCategorias() {
    this.categoriaService.getCategoriasConColor().pipe(
      finalize(() => this.loadingCategorias = false)
    ).subscribe({
      next: (categorias) => {
        this.categoriasConColor = categorias as { nombre: string; color: string }[];
        this.obtenerClases();
      },
      error: (error) => {
        console.error('Error obteniendo categorías', error);
        this.obtenerClases();
      }
    });
  }

  obtenerClases() {
    this.claseService.obtenerInscripcionesIds().subscribe(inscripciones => {
      this.claseService.obtenerClasesPaginadas(this.paginaActual, this.elementosPorPagina)
        .subscribe({
          next: ({ content, totalElements }) => {
            this.clasesPagina = content.map(clase => {
              const estado = this.claseService.calcularEstadoClase(
                { id: clase.id, capacidad: clase.capacidad },
                inscripciones
              );

              return {
                ...this.mapearClase(clase),
                plazas: `${estado.inscritos} / ${clase.capacidad}`
              };
            });

            this.clasesFiltradasPagina = this.clasesPagina;
            this.totalClases = totalElements;
          },
          error: _ => {
            this.clasesPagina = this.clasesFiltradasPagina = [];
            this.totalClases = 0;
          }
        });
    });
  }



  private mapearClase(clase: any): ClasesTabla {
    // Encuentra la categoría correspondiente para obtener el color
    const categoriaInfo = this.categoriasConColor.find(cat => cat.nombre === clase.categoria);
    const categoriaColor = categoriaInfo ? categoriaInfo.color : '#CCCCCC'; // Color por defecto

    return {
      id: clase.id,
      nombre: clase.nombre,
      instructor: clase.instructor,
      categoria: clase.categoria,
      categoriaColor: categoriaColor, // Nueva propiedad añadida
      sala: clase.sala,
      fechaInicio: clase.fechaInicio ? clase.fechaInicio : 'Sin fecha',
      fechaFinal: clase.fechaFinal ? clase.fechaFinal : 'Sin fecha',
      capacidad: clase.capacidad,
      duracion: clase.duracion != null
        ? `${clase.duracion} min`
        : 'Duración no especificada',
      estado: clase.estado
        ? clase.estado.replace('_', ' ')
        : 'Activo',
    };
  }


  guardarHorario(data: {
    idClase: number,
    fechaInicio: string;
    fechaFinal: string;
    horaInicio: string,
    horaFin: string,
    duracion: number
  }) {

    const fechas = [data.fechaInicio, data.fechaFinal];

    this.claseService.asignarHorario(data.idClase, fechas, data.horaInicio, data.horaFin, data.duracion)
      .subscribe({
        next: () => {
          this.cerrarModal();
          this.obtenerClases();
        },
        error: (err: any) => {
        }
      });
  }



  // Cambio de página
  cambiarPagina(nuevaPagina: number) {
    this.paginaActual = nuevaPagina;
    this.obtenerClases();
  }

  actualizarFiltrado(filtradas: ClasesTabla[]) {
    this.clasesFiltradasPagina = filtradas;
  }

  // Acciones de la tabla
  manejarAccion(evento: { action: string, row: any }) {
    if (evento.action === 'Horario') {
      this.abrirModal(evento.row);

    }
  }

  abrirModal(clase: ClasesTabla) {

    this.claseSeleccionada = clase;
    this.mostrarModal = true;
  }

  cerrarModal() {

    this.mostrarModal = false;
  }




  crearClase() {
    this.router.navigate(["clases/crear"]);
  }

  eliminarClase(id: number) {
  }


  modificarClase(clase: any) {
    this.router.navigate(['/calendario']);
  }
}