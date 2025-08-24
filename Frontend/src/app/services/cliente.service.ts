import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { delay, Observable } from 'rxjs';
import { Cliente } from '../interfaces/cliente';
import { PageResponse } from '../interfaces/pageresponse';

//Interfaz Usuario para Modal
export interface UsuarioModal {
    id_usuario: number;
    nombre: string;
    apellido: string;
    cedula: String;
}

@Injectable({
    providedIn: 'root',
})
export class ClienteService {

    private clientes = 'http://localhost:8080/gimnasio-app/usuarios';

    private apiUrl = 'http://localhost:8080/gimnasio-app/usuarios/Registrar';
    private Url_UsuarioModal = 'http://localhost:8080/gimnasio-app/usuarios/Modal';

    constructor(private http: HttpClient) { }

    // Registrar un cliente admin
    registrar(cliente: Cliente): Observable<any> {
        return this.http.post<any>(this.apiUrl, cliente);
    }

    // Obtener clientes paginados
    obtenerClientesPaginados(page: number, size: number): Observable<PageResponse<Cliente>> {
        const params = `?page=${page}&size=${size}`;
        return this.http.get<PageResponse<Cliente>>(`${this.clientes}${params}`);
    }

    // Obtenemos Usuarios Basicos para modal
    obtenerClienteModal(): Observable<UsuarioModal[]> {
        return this.http.get<UsuarioModal[]>(this.Url_UsuarioModal).pipe(delay(0));
    }

}
