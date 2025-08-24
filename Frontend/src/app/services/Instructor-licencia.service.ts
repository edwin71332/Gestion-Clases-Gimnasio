import { Injectable } from '@angular/core';
import { Observable, switchMap, map } from 'rxjs';
import { InstructorServiceService } from './instructor-service.service';
import { LicenciaService } from './licencia.service';
import { Instructor } from '../interfaces/instructor';

@Injectable({ providedIn: 'root' })
export class FacadeInstructor {
  constructor(
    private instructorService: InstructorServiceService,
    private licenciaService: LicenciaService
  ) { }

  // Registra un instructor y sube sus licencias asociadas
  registrarInstructorConLicencias(
    instructor: Instructor,
    archivosLicencias: File[]
  ): Observable<void> {
    return this.instructorService.postInstructor(instructor).pipe(
      switchMap(nuevoInstructor => {
         const instructorId = nuevoInstructor.id_instructor;
        return this.licenciaService.uploadMany(instructorId, archivosLicencias);
      }),
      map(() => void 0)
    );
  }
}