import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Categoria } from '../interfaces/categoria';
import { delay, Observable } from 'rxjs';
import { PageResponse } from '../interfaces/pageresponse';

@Injectable({
    providedIn: 'root'
})

export class CategoriaServices {

    private CategoriaPaginado_URl = 'http://localhost:8080/gimnasio-app/categorias/Paginados';
    private categoria_URL = 'http://localhost:8080/gimnasio-app/categorias';

    constructor(private http: HttpClient) {

    }


    getCategorias(): Observable<Categoria[]> {
        return this.http.get<Categoria[]>(this.categoria_URL)
            .pipe(delay(0)); // Agrega un pequeño retraso para ayudar a romper posibles dependencias circulares.
    }

    obtenerCategoriasPaginados(page: number, size: number): Observable<PageResponse<Categoria>> {
        return this.http.get<PageResponse<Categoria>>(
            `${this.CategoriaPaginado_URl}?page=${page}&size=${size}`);
    }

    postCategoria(categoria: Categoria): Observable<Categoria> {
        return this.http.post<Categoria>(this.categoria_URL, categoria)
            .pipe(delay(0));
    }

    getCategoriasConColor(): Observable<{ nombre: string; color: string }[]> {
        return this.http.get<{ nombre: string, color: string }[]>('http://localhost:8080/gimnasio-app/categorias');
    }

}