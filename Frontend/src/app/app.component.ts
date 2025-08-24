import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Router } from '@angular/router';
import { HeaderComponent } from "./Components/header/header.component";
import { panellateralComponent } from './Components/panel-lateral/panel-lateral.component';
import { CommonModule } from '@angular/common';

/*
 * Componente raíz de la aplicación.
 * Define la estructura general incluyendo el encabezado, panel lateral y vistas principales.
*/
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, HeaderComponent, panellateralComponent ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  template: `
    <app-instructors-list></app-instructors-list>
    <app-classes-list></app-classes-list>
  ` 
  // Renderiza directamente los componentes de lista en el HTML
})
/** Constructor con inyección de dependencias del router.
  * @param router Manejador de navegación de Angular
**/
export class AppComponent {
  constructor(public router: Router) { }

}
