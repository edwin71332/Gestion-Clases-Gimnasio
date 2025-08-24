import { Component, OnInit } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import Swal from 'sweetalert2';
import { Categoria } from "../../../interfaces/categoria";
import { CategoriaServices } from "../../../services/categoria.service";
import { tablaGenericaComponent } from "../../../shared/Components/tabla-generica/tabla-generica.component";
import { PageResponse } from "../../../interfaces/pageresponse";
import { NotificacionService } from "../../../services/notificacion.service";


@Component({
    selector: "app-categoria",
    standalone: true,
    imports: [CommonModule, ReactiveFormsModule, tablaGenericaComponent],
    templateUrl: "./categoria.component.html",
})
export class CategoriaComponent implements OnInit {

    currentPage = 0;
    pageSize = 5;
    totalElements = 0;
    categorias: Categoria[] = [];

    cols = [
        { field: 'nombre', header: 'Nombre' },
    ];

    categoriaForm: FormGroup;

    constructor(
        private fb: FormBuilder,
        private router: Router,
        private notificacionService: NotificacionService,
        private categoria: CategoriaServices
    ) {
        this.categoriaForm = this.fb.group({
            nombre: ["", Validators.required],
        });
    }

    ngOnInit() {
        this.loadCategorias();
    }

    loadCategorias() {
        this.categoria.obtenerCategoriasPaginados(this.currentPage, this.pageSize)
            .subscribe((response: PageResponse<Categoria>) => {
                this.categorias = response.content;
                this.totalElements = response.totalElements;
            });
    }

    onPageChange(newPage: number) {
        this.currentPage = newPage;
        this.loadCategorias();
    }




    onSubmit() {
        if (this.categoriaForm.valid) {
            this.categoria.postCategoria(this.categoriaForm.value).subscribe({
                next: () => {
                    this.notificacionService.success(
                        '¡Especialidad registrada!',
                        'La especialidad ha sido registrada correctamente.'
                    );
                    this.categoriaForm.reset();
                    this.router.navigate(["/Categoria&Especialidad"]);

                },
                error: () => {
                    this.notificacionService.error('¡ERROR!', 'Especialidad no registrada')
                }
            });
        } else {
            this.categoriaForm.markAllAsTouched();
        }
    }

    cancelar(): void {
        this.categoriaForm.reset();
    }

    campoInvalido(campo: string): boolean {
        const control = this.categoriaForm.get(campo);
        return !!(control && control.invalid && (control.dirty || control.touched));
    }
}
