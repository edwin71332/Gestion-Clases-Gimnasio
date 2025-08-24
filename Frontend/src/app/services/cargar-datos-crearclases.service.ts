// src/app/services/data-loader.service.ts
import { Injectable } from '@angular/core';
import { forkJoin, Observable } from 'rxjs';
import { Categoria } from '../interfaces/categoria';
import { Sala } from '../interfaces/sala';
import { InstructorBasic } from '../interfaces/instructor';
import { GetDataService } from './get-data.service';

export interface InitData {
  categorias: Categoria[];
  salas: Sala[];
  instructores: InstructorBasic[];
}

@Injectable({ providedIn: 'root' })
export class CargarDatosCrearClasesService {
  constructor(private dataService: GetDataService) {}

  loadAll(): Observable<InitData> {
    return forkJoin({
      categorias: this.dataService.getCategorias(),
      salas:       this.dataService.getSalas(),
      instructores: this.dataService.getInstructoresBasic()
    });
  }
}
