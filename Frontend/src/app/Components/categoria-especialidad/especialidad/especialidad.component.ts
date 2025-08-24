import { Component, OnInit } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import Swal from 'sweetalert2';
import { Especialidad } from "../../../interfaces/especialidad";
import { tablaGenericaComponent } from "../../../shared/Components/tabla-generica/tabla-generica.component";
import { PageResponse } from "../../../interfaces/pageresponse";
import { EspecialidadService } from "../../../services/especialidad.service";
import { NotificacionService } from "../../../services/notificacion.service";

@Component({
  selector: "app-especialidad",
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, tablaGenericaComponent],
  templateUrl: "./especialidad.component.html",
})
export class EspecialidadComponent implements OnInit {

  especialidadForm: FormGroup;

  especialidades: Especialidad[] = [];
  PaginaActual = 0;
  pageSize = 5;
  ElementosTotales = 0;

  colums = [
    { field: 'nombre', header: 'Nombre' },
  ];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private especialidad: EspecialidadService,
    private notificacionService: NotificacionService
  ) {
    this.especialidadForm = this.fb.group({
      nombre: ["", Validators.required],
    });
  }

  ngOnInit() {
    this.loadEspecialidades();
  }

  loadEspecialidades() {
    this.especialidad.obtenerEspecialidadesPaginados(this.PaginaActual, this.pageSize)
      .subscribe((response: PageResponse<Especialidad>) => {
        this.especialidades = response.content;
        this.ElementosTotales = response.totalElements;
      });
  }

  onPageChangeEspe(newPage: number) {
    
    this.PaginaActual = newPage;
    this.loadEspecialidades();
  }

  onEnviar() {
    if (this.especialidadForm.valid) {
      this.especialidad.postEspecialidad(this.especialidadForm.value).subscribe({
        next: (response: Especialidad) => {
          this.notificacionService.success(
            '¡Especialidad registrada!',
            'La especialidad ha sido registrada correctamente.'
          );
          this.especialidadForm.reset();
          this.router.navigate(["/Categoria&Especialidad"]);

        },
        error: () => {
           this.notificacionService.error('¡ERROR!', 'Especialidad no registrada')
        }
      });
    } else {
      this.especialidadForm.markAllAsTouched();
    }
  }

  campoInvalido(campo: string): boolean {
    const control = this.especialidadForm.get(campo);
    return !!(control && control.invalid && (control.dirty || control.touched));
  }

  cancelar(): void {
    this.especialidadForm.reset();
  }
}