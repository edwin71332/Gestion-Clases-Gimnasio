import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Clase } from '../interfaces/clase';
import { delay, Observable } from 'rxjs';
import { ClasesTabla } from '../interfaces/Clase-tabla';
import { ClaseDisponible } from '../interfaces/clase-disponible';
import { inscripcionClases } from '../interfaces/inscripcionClases';


// Define la estructura de la respuesta paginada
export interface PagedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}


@Injectable({
  providedIn: 'root'
})
export class ClaseService {

  private clase_URL = 'http://localhost:8080/gimnasio-app/clases/crear'; // URl PARA INTERACTUAR CON EL BACK
  private obtener_URl = 'http://localhost:8080/gimnasio-app/clases';
  private URL_InscribirCliente = 'http://localhost:8080/gimnasio-app/inscripcion';
  private URL_ClasesDisponible = 'http://localhost:8080/gimnasio-app/clases/disponibles';
  private URL_AsignarHorario = 'http://localhost:8080/gimnasio-app/clases/asignar-horario';
  private inscripcion_URL = 'http://localhost:8080/gimnasio-app/inscripcion/ids';
  constructor(private http: HttpClient) { }


  postClase(clase: Clase): Observable<Clase> {
    return this.http.post<Clase>(this.clase_URL, clase)
      .pipe(delay(0));
  }


  obtenerClasesPaginadas(page: number, size: number): Observable<PagedResponse<ClasesTabla>> {
    const params = `?page=${page}&size=${size}`;
    return this.http.get<PagedResponse<ClasesTabla>>(this.obtener_URl + params);
  }

  reservacionClase(inscripcion: { id_usuario: number; id_clase: number; }) {
    return this.http.post(this.URL_InscribirCliente, inscripcion);
  }

  obtenerClasesDisponibles(): Observable<ClaseDisponible[]> {
    return this.http.get<ClaseDisponible[]>(this.URL_ClasesDisponible).pipe(delay(0));
  }

  asignarHorario(idClase: number, fechas: string[], horaInicio: string, horaFin: string, duracion: number) {
    const [fechaInicio, fechaFinal] = fechas; // separa las fechas

    const payload = {
      idClase,
      fechaInicio,
      fechaFinal,
      horaInicio,
      horaFin,
      duracion
    };

    console.log('📤 Payload corregido:', payload); // Verifica en consola

    return this.http.post(this.URL_AsignarHorario, payload);
  }


  obtenerInscripcionesIds(): Observable<inscripcionClases[]> {
    return this.http.get<inscripcionClases[]>(this.inscripcion_URL);
  }


  calcularEstadoClase(clase: { id: number, capacidad: number }, inscripciones: { idClase: number }[]) {
    const inscritos = inscripciones.filter(i => i.idClase === clase.id).length;
    const disponibles = clase.capacidad - inscritos;
    return {
      inscritos,
      disponibles: disponibles >= 0 ? disponibles : 0,
    };
  }
}