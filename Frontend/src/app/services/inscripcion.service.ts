import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { delay, Observable } from 'rxjs';
import { Inscripcion } from '../interfaces/inscripcionClases';


@Injectable({
    providedIn: 'root'
})
export class ClaseService {

    private inscripcion_URL = 'http://localhost:8080/gimnasio-app/inscripcion/ids';


    constructor(private http: HttpClient) { }

    


}