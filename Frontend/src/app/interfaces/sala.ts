export class Sala {
    id_sala!: number;
    nombre!: string;
    descripcion!: string;
    capacidad!: number;
    estado!: 'Mantenimiento' | 'Disponible' | 'Cerrada';
    id_categoria!: number;
}
 