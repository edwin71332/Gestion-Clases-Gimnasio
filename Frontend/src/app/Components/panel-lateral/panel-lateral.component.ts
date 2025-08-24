import { Component } from "@angular/core";
import { RouterLink, RouterLinkActive } from "@angular/router";
import { CommonModule } from "@angular/common";

@Component({
  selector: "app-sidebar",
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: "./panel-lateral.component.html",
})
export class panellateralComponent {
  menuItems = [
    { label: "Inicio", route: "/inicio", icon: "home" },
    { label: "Crear Clases", route: 'clases/crear', icon: "calendar" },
    { label: "Registrar Instructor", route: 'instructores/registrar', icon: "users" },
    { label: "Registrar Cliente", route: 'clientes', icon: "cliente" },
  ];


}
