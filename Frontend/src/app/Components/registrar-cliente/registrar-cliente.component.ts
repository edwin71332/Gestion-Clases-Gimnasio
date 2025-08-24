import { Component } from '@angular/core';
import { CommonModule } from "@angular/common";
import { FormBuilder, FormGroup, Validators, } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { NotificacionService } from "../../services/notificacion.service";
import { ClienteService } from "../../services/cliente.service";
import { Cliente } from '../../interfaces/cliente';

@Component({
    selector: 'app-cliente-form',
    imports: [CommonModule, ReactiveFormsModule],

    templateUrl: './registrar-cliente.component.html',
})
export class ClienteFormComponent {
    formCliente: FormGroup;

    constructor(
        private fb: FormBuilder,
        private notificacionService: NotificacionService,
        private clienteService: ClienteService




    ) {
        this.formCliente = this.fb.group({
            nombre: ['', Validators.required],
            apellido: ['', Validators.required],
            cedula: ['', [Validators.required, Validators.pattern(/^\d+$/)]],
            correo: ['', [Validators.required, Validators.email]],
            telefono: ['', [Validators.required, Validators.pattern('^[0-9]{6,12}$')]],

        });
    }

    onRegistrarCliente(): void {
        if (this.formCliente.invalid) {
            this.formCliente.markAllAsTouched();
            return;
        }

        const cliente: Cliente = this.formCliente.value;
        console.log(cliente);


        this.clienteService.registrar(cliente).subscribe({
            next: () => {
                this.notificacionService.success(
                    'Cliente registrado',
                    `El cliente ${cliente.nombre} fue registrado correctamente.`
                );
                this.formCliente.reset();
            },
            error: (err) => {
                this.notificacionService.error(
                    'Error',
                    'No se pudo registrar el cliente. Intenta nuevamente.'
                );
                console.error('Error al registrar cliente:', err);
            }
        });
    }

    limpiarFormulario(): void {
        this.formCliente.reset();
    }

    volverAListaClientes(): void {
        console.log('Volver a la lista de clientes');
    }
}
