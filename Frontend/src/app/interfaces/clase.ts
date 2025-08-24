export interface Clase {
    id_clase: number;
    nombre: string;
    descripcion: string;
    fecha_inicio?: string;
    hora_inicio?: string;
     hora_final?: string;
    duracion?: number;
    capacidad: number;
    estado?: 'Disponible' | 'Llena' | 'Cancelada';
    id_instructor: string;
    id_categoria: string;
    id_sala: string;
}