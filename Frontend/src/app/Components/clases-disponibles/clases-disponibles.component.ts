import { Component, OnInit } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ClaseService } from "../../services/clase.service";
import { ClaseDisponible } from "../../interfaces/clase-disponible";
import { ClienteService, UsuarioModal } from '../../services/cliente.service';
import { FormsModule } from "@angular/forms";
import { CategoriaServices } from "../../services/categoria.service";
import { filtrogenericoComponent } from "../../shared/Components/filtro-generico/filtro-generico.component";
import { forkJoin } from 'rxjs';
import { NotificacionService } from "../../services/notificacion.service";
// Interfaz Detallada para el Modal
interface ClaseDetallada {
  id: number;
  nombre: string;
  categoria: string;
  hora: string;
  duracion: string;
  plazasDisponibles: number;
  plazasTotales: number;
  dia: string;
  fechaInicio: string;
  fechaFinal: string;
  instructor: string;
  descripcion: string;
  ubicacion: string;
}

@Component({
  selector: "app-clases-disponibles",
  standalone: true,
  imports: [CommonModule, FormsModule, filtrogenericoComponent],
  templateUrl: "./clases-disponibles.component.html",
})
export class ClasesDisponiblesComponent implements OnInit {
  categoriaSeleccionada = "todas";
  clases: ClaseDetallada[] = [];
  modalAbierto = false;
  claseSeleccionada: ClaseDetallada | null = null;
  usuarios: UsuarioModal[] = [];
  usuarioSeleccionadoId: number | null = null;
  categoriasConColor: { nombre: string; color: string }[] = [];
  clasesFiltradas: ClaseDetallada[] = [];



  constructor(
    private claseServicio: ClaseService,
    private clienteServicio: ClienteService,
    private categoria: CategoriaServices,
    private notificacionService: NotificacionService
  ) { }

  ngOnInit(): void {
    forkJoin({
      clases: this.claseServicio.obtenerClasesDisponibles(),
      inscripciones: this.claseServicio.obtenerInscripcionesIds()
    }).subscribe(({ clases, inscripciones }) => {
      this.clases = clases.map(clase => {
        const inscritos = inscripciones.filter(i => i.idClase === clase.id).length;

        return {
          id: clase.id,
          nombre: clase.nombre,
          categoria: clase.categoria.toLowerCase(),
          hora: clase.hora_inicio ? clase.hora_inicio.slice(0, 5) : '00:00',
          duracion: `${clase.duracion}min`,
          plazasDisponibles: inscritos,
          plazasTotales: clase.capacidad,
          dia: this.obtenerDiasSemanaEntre(clase.fechaInicio, clase.fechaFinal).join(', '),
          instructor: clase.instructor,
          descripcion: clase.descripcion,
          ubicacion: clase.sala,
          fechaInicio: clase.fechaInicio,
          fechaFinal: clase.fechaFinal
        };
      });
      this.clasesFiltradas = [...this.clases];
    });

    this.clienteServicio.obtenerClienteModal().subscribe(clientes => {
      this.usuarios = clientes;
    });

    this.categoria.getCategoriasConColor().subscribe(categorias => {
      this.categoriasConColor = categorias.map(c => ({
        nombre: c.nombre.toLowerCase(),
        color: c.color
      }));
    });
  }


  reservarClases(idClase: number): void {
    if (!this.usuarioSeleccionadoId) {
      alert('Por favor selecciona un cliente para reservar');
      return;
    }

    const nuevaInscripcion = {
      id_usuario: this.usuarioSeleccionadoId,
      id_clase: idClase,
    };

    this.claseServicio.reservacionClase(nuevaInscripcion).subscribe({
      next: () => {
        this.notificacionService.success('¡Éxito!', 'Reservación completada').then(() => {
          location.reload();
        });
        this.cerrarModal();
      },
      error: (err: any) => {
        this.notificacionService.error('Error', 'Este cliente ya está inscrito en esta clase.');

      },

    });
  }


  filtrarPorCategoria(categoria: string): void {
    this.categoriaSeleccionada = categoria;
  }

  onFilterChange(resultados: ClaseDetallada[]) {
    this.clasesFiltradas = resultados;
  }

  abrirModal(clase: ClaseDetallada): void {
    this.claseSeleccionada = clase;
    this.modalAbierto = true;
    document.body.style.overflow = "hidden";
  }

  cerrarModal(): void {
    this.modalAbierto = false;
    this.claseSeleccionada = null;
    document.body.style.overflow = "auto";
  }

  onReservarClick(event: Event, id: number): void {
    event.stopPropagation();
    this.reservarClases(id);
  }

  obtenerDiasSemanaEntre(fechaInicio: string, fechaFinal: string): string[] {
    const inicio = new Date(fechaInicio);
    const final = new Date(fechaFinal);
    const dias: Set<string> = new Set();

    const opciones = { weekday: 'long' } as const;

    while (inicio <= final) {
      const dia = new Intl.DateTimeFormat('es-ES', opciones).format(inicio);
      dias.add(dia.charAt(0).toUpperCase() + dia.slice(1)); // Capitaliza
      inicio.setDate(inicio.getDate() + 1);
    }

    return Array.from(dias);
  }


  getColor(nombreCategoria: string): string {
    const categoria = this.categoriasConColor.find(c => c.nombre === nombreCategoria);
    return categoria?.color || '#ccc';
  }
}
