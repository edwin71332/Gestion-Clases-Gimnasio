import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { delay, Observable } from 'rxjs';
import { Sala } from '../interfaces/sala';

@Injectable({
  providedIn: 'root'
})
export class SalaService {
  private sala_URL = 'http://localhost:8080/gimnasio-app/salas'; // URl PARA INTERACTUAR CON EL BACK

  constructor(private http: HttpClient) { }

  getSalas(): Observable<Sala[]> {
    return this.http.get<Sala[]>(this.sala_URL)
      .pipe(delay(0));
  }


  postSala(sala: Sala): Observable<Sala> {
    return this.http.post<Sala>(this.sala_URL, sala)
      .pipe(delay(0)); 
  }
}
