
import { Component } from "@angular/core";

import { Router } from "@angular/router";
import { CategoriaComponent } from "./categoria/categoria.component";
import { EspecialidadComponent } from "./especialidad/especialidad.component";


@Component({
    selector: "app-categoria&Especialidad",
    standalone: true,
    imports: [CategoriaComponent, EspecialidadComponent],
    templateUrl: "./categoria-especialidad.component.html",
})


export class AgregarCategoriaEspecialidad {

    constructor(
        private router: Router,

    ) {


    }

}