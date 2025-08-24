import { Routes } from '@angular/router';
import { ListaInstructoresComponent } from './Components/lista-instructores/lista-instructores.component';
import { ListaClasesComponent } from './Components/lista-clases/lista-clases.component';
import { AgregarCategoriaEspecialidad } from '../app/Components/categoria-especialidad/categoria-especialidad.component';
import { ClasesDisponiblesComponent } from './Components/clases-disponibles/clases-disponibles.component';
import { añadirsalaComponent } from './Components/añadir-sala/añadir-sala.component';
import { ClienteFormComponent } from './Components/registrar-cliente/registrar-cliente.component';
import { CalendarComponent } from './Components/lista-clases/calendario/calendario.component';

export const routes: Routes = [
  { path: 'inicio', component: ClasesDisponiblesComponent },
  { path: 'clases', component: ListaClasesComponent },
  {
    path: 'clases/crear',
    loadComponent: () => import('./Components/crear-clase/crear-clase.component').then(m => m.CrearClaseComponent)
  },
  {
    path: 'sala', component: añadirsalaComponent
  },
  { path: 'instructores', component: ListaInstructoresComponent },
  { path: 'clientes', component: ClienteFormComponent },

  { path: 'CategoriaEspecialidad', component: AgregarCategoriaEspecialidad },
  {
    // Carga diferida --> se cargar dinamicamente al acceder a la ruta mejorando el rendimiento
    path: 'instructores/registrar',
    loadComponent: () => import('./Components/registrar-instructor/registrar-instructor.component').then(m => m.RegistrarInstructorComponent)
  },

  // Redireccion por defecto
  { path: '', redirectTo: 'inicio', pathMatch: 'full' }
];


