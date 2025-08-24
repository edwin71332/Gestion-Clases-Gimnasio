import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, delay } from 'rxjs';
import { Especialidad } from '../interfaces/especialidad';
import { PageResponse } from '../interfaces/pageresponse';

@Injectable({
    providedIn: 'root'
})

export class EspecialidadService {
    private especialidad_URL = 'http://localhost:8080/gimnasio-app/especialidades'
    private EspecialidadPaginado_URl = 'http://localhost:8080/gimnasio-app/especialidades/Paginados';


    constructor(private http: HttpClient) {

    }

    postEspecialidad(especialidad: Especialidad): Observable<Especialidad> {
        return this.http.post<Especialidad>(this.especialidad_URL, especialidad)
            .pipe(delay(0)); // Agrega un pequeño retraso para ayudar a romper posibles dependencias circulares.
    }
    getEspecialidades(): Observable<Especialidad[]> {
        return this.http.get<Especialidad[]>('http://localhost:8080/gimnasio-app/especialidades');
    }

    obtenerEspecialidadesPaginados(page: number, size: number): Observable<PageResponse<Especialidad>> {
        return this.http.get<PageResponse<Especialidad>>(
            `${this.EspecialidadPaginado_URl}?page=${page}&size=${size}`);
    }
}