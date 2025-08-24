import { Component, ElementRef, ViewChild, OnInit } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { NotificacionService } from "../../services/notificacion.service";
import { FacadeInstructor } from "../../services/Instructor-licencia.service";
import { CargarArchivo } from "../../services/cargar-archivo.service";

import { EspecialidadesHelper } from "../../helper/especialidadhelper.helper";
import { Especialidad } from '../../interfaces/especialidad';
import { EspecialidadService } from "../../services/especialidad.service";

@Component({
  selector: "Registrar-instructor",
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: "./registrar-instructor.component.html",
})
export class RegistrarInstructorComponent implements OnInit {

  @ViewChild('fileInput', { static: false })
  fileInputRef!: ElementRef<HTMLInputElement>;
  formInstructor!: FormGroup;
  archivosSeleccionados: File[] = [];

  especialidadesHelper!: EspecialidadesHelper;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private notificacionService: NotificacionService,
    private FacadeInstructor: FacadeInstructor,
    private cargarArchivoService: CargarArchivo,
    private especialidadService: EspecialidadService
  ) {
    this.formInstructor = this.formBuilder.group({
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      cedula: ['', Validators.required],
      telefono: ['', [Validators.required, Validators.pattern('^[0-9]{6,12}$')]],
      correo: ['', [Validators.required, Validators.email]],
      especialidad: [''],
    });

    this.especialidadesHelper = new EspecialidadesHelper();
  }

  ngOnInit(): void {
    this.cargarEspecialidades();
  }



  private cargarEspecialidades() {
    this.especialidadService.getEspecialidades().subscribe({
      next: (especialidad: Especialidad[]) => {
        // Mantener los objetos completos con id y nombre
        this.especialidadesHelper.setEspecialidadesDisponibles(especialidad);
      },
      error: () => {
        this.especialidadesHelper.setEspecialidadesDisponibles([]);
      }
    });
  }


  agregarEspecialidad() {
    const espSeleccionada: any = this.formInstructor.get('especialidad')!.value;
    if (this.especialidadesHelper.agregarEspecialidad(espSeleccionada)) {
      this.formInstructor.get('especialidad')!.setValue(null);  // ← null en lugar de ''
    }
  }

  quitarEspecialidad(id: number) {
    this.especialidadesHelper.quitarEspecialidad(id);
  }
  // Para UI: mostrar nombres
  get nombresEspecialidadesAgregadas() {
    return this.especialidadesHelper.getNombresEspecialidadesAgregadas();
  }


  get idsEspecialidadesAgregadas() {
    return this.especialidadesHelper.getEspecialidadesAgregadas();
  }

  opcionesEspecialidad() {
    return this.especialidadesHelper.opcionesEspecialidad();
  }

  obtenerIdEspecialidad(nombre: string): number {
    const especialidad = this.especialidadesHelper.getEspecialidadesDisponibles()
      .find(e => e.nombre === nombre);
    return especialidad ? especialidad.id_especialidad : -1;
  }


  onSeleccionarArchivos(event: Event) {
    const input = event.target as HTMLInputElement;
    this.cargarArchivoService.setFiles(input.files);
    this.archivosSeleccionados = input.files ? Array.from(input.files) : [];
  }

  onRegistrarInstructor() {
    const archivos = this.cargarArchivoService.getFiles();

    if (this.formInstructor.invalid || archivos.length === 0) {
      this.notificacionService.error('ERROR!', 'Completa todos los campos y selecciona al menos una licencia.');
      return;
    }

    const datosInstructor = {
      ...this.formInstructor.value,
      especialidadesIds: this.idsEspecialidadesAgregadas // Enviar IDs
    };



    this.FacadeInstructor
      .registrarInstructorConLicencias(datosInstructor, archivos)
      .subscribe({
        next: () => {
          this.notificacionService.success('¡Éxito!', 'Instructor y licencias registrados');
          this.limpiarFormulario();
        },
        error: () => {
          this.notificacionService.error('Error', 'No se pudo completar el registro, inténtalo nuevamente');
        }
      });
  }

  // Limpia el formulario y los archivos seleccionados
  limpiarFormulario() {
    this.formInstructor.reset();
    this.cargarArchivoService.clearFiles();
    this.archivosSeleccionados = [];
    if (this.fileInputRef) this.fileInputRef.nativeElement.value = '';
    this.especialidadesHelper = new EspecialidadesHelper(this.especialidadesHelper.getEspecialidadesDisponibles());

  }

  // Verifica si un campo del formulario es inválido y fue tocado o modificado
  campoInvalido(nombreCampo: string): boolean {
    const campo = this.formInstructor.get(nombreCampo);
    return !!(campo && campo.invalid && (campo.dirty || campo.touched));
  }






  // Navega a la lista de instructores
  irAListaInstructores() {
    this.router.navigate(['/instructores']);
  }
}