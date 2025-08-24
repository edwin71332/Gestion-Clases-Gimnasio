 import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GetDataService } from '../services/get-data.service';
 import { Categoria } from '../interfaces/categoria';
import { InstructorBasic } from '../interfaces/instructor';
import { Sala } from '../interfaces/sala';  

@Injectable({ providedIn: 'root' })
export class ClaseHelperService {
  constructor(private dataService: GetDataService) {}

  cargarCategorias(): Observable<Categoria[]> {
    return this.dataService.getCategorias();
  }

  cargarSalas(): Observable<Sala[]> {
    return this.dataService.getSalas();
  }

  cargarInstructores(): Observable<InstructorBasic[]> {
    return this.dataService.getInstructoresBasic();
  }

  filtrarSalasPorCategoria(salas: Sala[], categoriaId: number): Sala[] {
    return salas.filter(sala => sala.id_categoria === categoriaId);
  }

  obtenerCapacidadSala(salas: Sala[], salaId: number): number {
    const sala = salas.find(s => s.id_sala === salaId);
    return sala?.capacidad || 0;
  }
}