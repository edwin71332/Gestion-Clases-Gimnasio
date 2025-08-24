import { Component, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ColumnaDef } from "../../../interfaces/columnas-def"
import { AccionDef } from '../../../interfaces/acciones-tabla';



@Component({
  selector: 'app-tabla-generica',
  templateUrl: './tabla-generica.component.html',
  imports: [CommonModule]
})
export class tablaGenericaComponent implements OnChanges {
  @Input() data: any[] = [];
  @Input() columns: ColumnaDef[] = [];
  @Input() actions: AccionDef[] = [];
  @Input() totalElements = 0;
  @Input() pageSize = 0;
  @Input() currentPage = 0;

  // Eventos de salida
  @Output() pageChanged = new EventEmitter<number>();
  @Output() actionClicked = new EventEmitter<{ action: string, row: any }>();

  // Calcula el total de páginas
  get totalPages(): number {
    return Math.ceil(this.totalElements / this.pageSize);
  }

  // Actualiza cuando cambian los inputs
  ngOnChanges(changes: SimpleChanges) {
    if (changes['totalElements'] || changes['pageSize']) {
      this.currentPage = 0;
    }
  }

  // Manejo de cambio de página
  changePage(newPage: number) {
    if (newPage >= 0 && newPage < this.totalPages) {
      this.currentPage = newPage;
      this.pageChanged.emit(newPage);
    }
  }


  // Manejo de acciones
  onAction(action: string, row: any) {
    this.actionClicked.emit({ action, row });
  }

  trackById(index: number, item: any) {
    return item.id ?? index;
  }

}
