import { Component, OnInit } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import Swal from 'sweetalert2'; // para mostrar mensajes 
import { SalaService } from "../../services/sala.service";
import { Categoria } from "../../interfaces/categoria";
 import { CategoriaServices } from "../../services/categoria.service";


/**
 * @Component Decorator
 * Define los metadatos para el RegistrarSalaComponent.
 */
@Component({
  selector: "app-añadir-sala",
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: "./añadir-sala.component.html",
})

/**
 * RegistrarSalaComponent Class
 * Este componente es responsable de manejar el registro de nuevas Salas.
 * Incluye un formulario para ingresar los datos de la sala y utiliza un servicio para enviar los datos al backend.
*/

export class añadirsalaComponent implements OnInit {
  /**
   * salaForm: FormGroup
   * Define el grupo de formularios para el formulario de la sala.
   */
  salaForm: FormGroup;

  // Arrays para almacenar datos provenientes de la base de datos.
  categorias: Categoria[] = [];


  constructor(
    private fb: FormBuilder,
    private router: Router,
    private salaService: SalaService,
     private categoria: CategoriaServices

  ) {
    // Inicializa el salaForm con controles de formulario y validadores
    this.salaForm = this.fb.group({
      nombre: ["", Validators.required],
      descripcion: [""],
      capacidad: ["", [Validators.required]],
      estado_sala: ["", Validators.required],
      id_categoria: [null as number | null, Validators.required],
    });
  }


  ngOnInit(): void {
    this.categoria.getCategorias().subscribe({
      next: data => {
        this.categorias = data;
      },
      error: error => {
        console.error('Error al cargar las categorias')
      }

    });
  }

  /**
   * onSubmit Method
   * Maneja el envío del formulario de la sala.
   * Comprueba si el formulario es válido, envía los datos al backend.
   */
  onSubmit() {
    if (this.salaForm.valid) {
      // Imprime los datos del formulario por consola
      console.log(this.salaForm.value);

      // Enviar datos al backend
      this.salaService.postSala(this.salaForm.value).subscribe({
        next: (response) => {
          Swal.fire({
            title: '¡Sala registrada!',
            text: 'la sala ha sido agregada correctamente.',
            icon: 'success',
            width: '400px', // Cambia el ancho del moda
            confirmButtonText: 'Confirmar',
            customClass: {
              popup: 'swal-wide' // Clase CSS personalizada
            }
          }).then(() => {
            this.salaForm.reset();
            this.router.navigate(["/sala/crear"]);
          });
        },
        error: () => {
          Swal.fire({
            title: 'Error',
            text: 'No se pudo registrar la sala',
            icon: 'error',
            confirmButtonText: 'Intentar nuevamente'
          });
        }
      });
    } else {
      this.salaForm.markAllAsTouched();
    }
  }

  cancelar() {
    this.router.navigate(["/inicio"]);
  }

  /**
   * campoInvalido Method
   * Método de ayuda para determinar si un campo del formulario no es válido y debe mostrar un error.
   * @param {string} campo - El nombre del campo del formulario para verificar.
   * @returns {boolean} - True si el campo no es válido y debe mostrar un error, false de lo contrario.
   */
  campoInvalido(campo: string): boolean {
    const control = this.salaForm.get(campo);
    return !!(control && control.invalid && (control.dirty || control.touched));
  }
}
