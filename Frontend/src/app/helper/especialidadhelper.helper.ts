import { Especialidad } from "../interfaces/especialidad";
export class EspecialidadesHelper {
  private especialidadesAgregadas: number[] = [];  
  private especialidadesDisponibles: Especialidad[] = [];  

  constructor(especialidadesDisponibles: Especialidad[] = []) {
    this.especialidadesDisponibles = especialidadesDisponibles;
  }

  setEspecialidadesDisponibles(especialidades: Especialidad[]) {
    this.especialidadesDisponibles = especialidades;
  }

  getEspecialidadesDisponibles() {
    return this.especialidadesDisponibles;
  }

  getEspecialidadesAgregadas(): number[] {
    return this.especialidadesAgregadas;
  }

  // Devuelve los nombres para mostrar en UI
  getNombresEspecialidadesAgregadas(): string[] {
    return this.especialidadesAgregadas.map(id => {
      const esp = this.especialidadesDisponibles.find(e => e.id_especialidad === id);
      return esp ? esp.nombre : '';
    });
  }

  agregarEspecialidad(especialidad: any): boolean {
    if (
      especialidad &&
      !this.especialidadesAgregadas.includes(especialidad.id_especialidad) &&
      this.especialidadesDisponibles.some(e => e.id_especialidad === especialidad.id_especialidad)
    ) {
      this.especialidadesAgregadas.push(especialidad.id_especialidad);
      return true;
    }
    return false;
  }
  quitarEspecialidad(especialidadId: number) {
    this.especialidadesAgregadas = this.especialidadesAgregadas.filter(id => id !== especialidadId);
  }

  opcionesEspecialidad(): any[] {
    return this.especialidadesDisponibles.filter(
      esp => !this.especialidadesAgregadas.includes(esp.id_especialidad)
    );
  }
}