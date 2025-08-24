// src/app/services/license.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpEventType } from '@angular/common/http';
import { Observable, forkJoin } from 'rxjs';
import { LicenciaDTO } from '../interfaces/LicenciaDTO';


@Injectable({ providedIn: 'root' })
export class LicenciaService {
  private BASE = 'http://localhost:8080/gimnasio-app';

  constructor(private http: HttpClient) { }

  uploadOne(instructorId: number, file: File): Observable<LicenciaDTO> {
    const url = `${this.BASE}/instructores/${instructorId}/licencias`;
    const form = new FormData();
    form.append('licencia', file, file.name);
    return this.http.post<LicenciaDTO>(url, form);
  }

  uploadMany(instructorId: number, files: File[]): Observable<LicenciaDTO[]> {
    // forkJoin espera a que todos los uploads completen
    const uploads = files.map(f => this.uploadOne(instructorId, f));
    return forkJoin(uploads);
  }
}
