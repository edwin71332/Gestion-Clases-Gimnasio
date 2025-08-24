import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, delay } from 'rxjs';
import { Instructor, InstructorBasic } from '../interfaces/instructor';  
import { EditarInstructorDTO } from '../interfaces/EditarInstructorDTO';
import { PageResponse } from '../interfaces/pageresponse';
 

@Injectable({
  providedIn: 'root'
})
export class InstructorServiceService {
  private BASE_URL = 'http://localhost:8080/gimnasio-app';
  private INSTRUCTORES_URL = `${this.BASE_URL}/instructores`;
  private instructoresbasic_URL = 'http://localhost:8080/gimnasio-app/instructores/simple';
  private instructor_URL = 'http://localhost:8080/gimnasio-app/instructores';
  private InstructorPaginado_URl = 'http://localhost:8080/gimnasio-app/instructores/Nuevo';
  constructor(private http: HttpClient) {
  }

  postInstructor(instructor: Instructor): Observable<Instructor> {
    return this.http.post<Instructor>(`${this.INSTRUCTORES_URL}/registrar`, instructor);
  }


  putInstructor(id: number, instructorEditable: EditarInstructorDTO): Observable<Instructor> {
    return this.http.put<Instructor>(
      `${this.INSTRUCTORES_URL}/${id}`,
      instructorEditable
    );
  }



  // OBTENER LA LISTA DE INSTRUCTORES
  getInstructores(): Observable<Instructor[]> {
    return this.http.get<Instructor[]>(this.instructor_URL)
      .pipe(delay(0));
  }

  getInstructoresBasic(): Observable<InstructorBasic[]> {
    return this.http.get<InstructorBasic[]>(this.instructoresbasic_URL);
  }

  //OBTENER LISTA DE INSTRUCTORES--PAGINADOS PARA LA TABLA INSTRUCTOR
  obtenerInstructoresPaginados(page: number, size: number): Observable<PageResponse<Instructor>> {
    return this.http.get<PageResponse<Instructor>>(
      `${this.InstructorPaginado_URl}?page=${page}&size=${size}`);
  }


}
