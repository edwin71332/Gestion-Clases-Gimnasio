import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Instructor } from '../../interfaces/instructor';
import { PageResponse } from '../../interfaces/pageresponse';
import { tablaGenericaComponent } from '../../shared/Components/tabla-generica/tabla-generica.component';
import { filtrogenericoComponent } from '../../shared/Components/filtro-generico/filtro-generico.component';
import { InstructorServiceService } from '../../services/instructor-service.service';

@Component({
  selector: 'app-instructores',
  templateUrl: './lista-instructores.component.html',
  standalone: true,
  imports: [tablaGenericaComponent, filtrogenericoComponent,],
})
export class ListaInstructoresComponent implements OnInit {

  constructor(
    private instructorservice: InstructorServiceService,
    private router: Router
  ) { }


  paginaActual = 0;
  tamanoPagina = 5;
  totalRegistros = 0;

  instructores: Instructor[] = [];
  instructoresFiltrados: Instructor[] = [];

  columnasTabla = [
    { field: 'nombre', header: 'Nombre' },
    { field: 'apellido', header: 'Apellido' },
    { field: 'cedula', header: 'Cédula' },
    { field: 'especialidad', header: 'Especialidades' },
    { field: 'telefono', header: 'Teléfono' },
  ];



  ngOnInit() {
    this.cargarInstructores();
  }

  cargarInstructores() {
    this.instructorservice.obtenerInstructoresPaginados(this.paginaActual, this.tamanoPagina)
      .subscribe((respuesta: PageResponse<Instructor>) => {

        // Se transforma la respuesta para mostrar las especialidades como string
        this.instructores = respuesta.content.map(instructor => {
          const especialidades = (instructor.instructorEspecialidades || [])
            .map(e => e.especialidad?.nombre)
            .filter((nombre): nombre is string => !!nombre);

          return {
            ...instructor,
            especialidades,
            especialidad: especialidades.join(', ')
          } as Instructor;
        });

        this.instructoresFiltrados = [...this.instructores];
        this.totalRegistros = respuesta.totalElements;
      });
  }

  aplicarFiltro(filtrados: Instructor[]) {
    this.instructoresFiltrados = filtrados;
  }

  cambiarPagina(nuevaPagina: number) {
    this.paginaActual = nuevaPagina;
    this.cargarInstructores();
  }

  manejarAccion(evento: { action: string, row: Instructor }) {
    if (evento.action === 'eliminar') {
    } else if (evento.action === 'modificar') {
    }
  }

  irARegistroInstructor() {
    this.router.navigate(['/instructores/registrar']);
  }


}