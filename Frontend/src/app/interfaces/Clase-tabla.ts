
export interface ClasesTabla {
  id: number;
  nombre: string;
  capacidad: number;
  instructor: string;
  categoria: string;
  sala: string;
  fechaInicio?: string;
  fechaFinal?: string;
  horario?: string[];
  duracion?: String;
  estado?: string;
  categoriaColor?: string; // ← Asegúrate de que esté

}
