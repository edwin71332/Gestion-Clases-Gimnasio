import { Especialidad } from "./especialidad";
import { EspecialidadInstructor } from "./EspecialidadesInstructor";

 export class Instructor {
  id_instructor!: number;
  nombre!: string;
  apellido!: string;
  telefono!: string;
  correo!: string;
  cedula!: number;
  instructorEspecialidades!: EspecialidadInstructor[];
   especialidades?: string[];       
  especialidad?: string;
}


 export interface InstructorBasic {
  id: number;
  nombre: string;
   apellido: string;
  especialidades: string[];
}
 