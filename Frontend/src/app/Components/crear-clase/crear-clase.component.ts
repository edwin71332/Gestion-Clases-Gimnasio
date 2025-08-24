import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatOptionModule } from '@angular/material/core';
import { Categoria } from '../../interfaces/categoria';
import { CategoriaServices } from '../../services/categoria.service';
import { InstructorBasic } from '../../interfaces/instructor';
import { Sala } from '../../interfaces/sala';
import { ClaseService } from '../../services/clase.service';
import { SalaService } from '../../services/sala.service';
import { InstructorServiceService } from '../../services/instructor-service.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-crear-clase',
  standalone: true,
  imports: [
    ReactiveFormsModule,

    CommonModule,
  ],
  templateUrl: './crear-clase.component.html',
})
export class CrearClaseComponent implements OnInit {
  form: FormGroup;

  categorias: Categoria[] = [];
  salas: Sala[] = [];
  instructores: InstructorBasic[] = [];
  salasFiltradas: Sala[] = [];
  capacidadSalaSeleccionada: number = 0;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private claseService: ClaseService,
    private sala: SalaService,
    private instructorservice: InstructorServiceService,
    private categoria: CategoriaServices
  ) {
    // Inicialización del formulario con validaciones
    this.form = this.fb.group({
      nombre: ['', Validators.required],
      descripcion: ['', Validators.required],
      id_categoria: [null, Validators.required],
      id_sala: [null, Validators.required],
      capacidad: ['', Validators.required],
      id_instructor: [null, Validators.required],
    });
  }

  ngOnInit(): void {
    // Cargar datos iniciales
    this.cargarCategorias();
    this.cargarSalas();
    this.cargarInstructores();

    // Actualizar salas filtradas al cambiar la categoría
    this.form.get('id_categoria')?.valueChanges.subscribe((categoriaId) => {
      this.actualizarSalasFiltradas(categoriaId);
      this.resetearSalaSiNoCorresponde();
    });

    // Actualizar capacidad máxima al cambiar la sala
    this.form.get('id_sala')?.valueChanges.subscribe((salaId) => {
      this.actualizarCapacidadSala(salaId);
    });
  }

  private cargarCategorias(): void {
    this.categoria.getCategorias().subscribe({
      next: (data) => (this.categorias = data),
      error: () => console.error('Error al cargar las categorías'),
    });
  }

  private cargarSalas(): void {
    this.sala.getSalas().subscribe({
      next: (data) => (this.salas = data),
      error: () => console.error('Error al cargar las salas'),
    });
  }

  private cargarInstructores(): void {
    this.instructorservice.getInstructoresBasic().subscribe({
      next: (data: InstructorBasic[]) => (this.instructores = data),
    });
  }

  private actualizarSalasFiltradas(categoriaId: number | null): void {
    if (!categoriaId) {
      this.salasFiltradas = [];
      return;
    }
    this.salasFiltradas = this.salas.filter((sala) => sala.id_categoria === categoriaId);
  }

  private resetearSalaSiNoCorresponde(): void {
    const salaSeleccionada = this.form.get('id_sala')?.value;
    if (salaSeleccionada && !this.salasFiltradas.some((s) => s.id_sala === salaSeleccionada)) {
      this.form.get('id_sala')?.reset();
    }
  }

  private actualizarCapacidadSala(salaId: number | null): void {
    if (salaId) {
      const sala = this.salas.find((s) => s.id_sala === salaId);
      this.capacidadSalaSeleccionada = sala?.capacidad || 0;
      this.form.get('capacidad')?.setValidators([
        Validators.required,
        Validators.min(1),
        Validators.max(this.capacidadSalaSeleccionada),
      ]);
      // Si la capacidad ingresada supera la máxima, se resetea
      if (this.form.get('capacidad')?.value > this.capacidadSalaSeleccionada) {
        this.form.get('capacidad')?.reset();
      }
    } else {
      this.capacidadSalaSeleccionada = 0;
      this.form.get('capacidad')?.setValidators([Validators.required]);
    }
    this.form.get('capacidad')?.updateValueAndValidity();
  }

  // Envía el formulario para crear una nueva clase
  enviar(): void {
    console.log('Datos enviados:', this.form.value);

    if (!this.form.valid) {
      this.form.markAllAsTouched();
      return;
    }

    this.claseService.postClase(this.form.value).subscribe({
      next: () => {
        Swal.fire({
          title: '¡Clase registrada!',
          text: 'La nueva clase ha sido creada correctamente.',
          icon: 'success',
          width: '400px',
          confirmButtonText: 'Continuar',
          customClass: { popup: 'swal-wide' },
        }).then(() => {
          this.form.reset();
          this.router.navigate(['/clases']);
        });
      },
      error: () => {
        Swal.fire({
          title: 'Error',
          text: 'No se pudo registrar la clase',
          icon: 'error',
          confirmButtonText: 'Intentar nuevamente',
        });
      },
    });
  }

  cancelar(): void {
    this.form.reset();
  }

  retroceder(): void {
    this.router.navigate(['/clases']);
  }
}