// Definición de una interfaz para las columnas de la tabla
export interface ColumnaDef {
  field: string;
  header: string;
  formatter?: (row: any) => { label: string; color?: string }; // <--- AÑADIDO

}