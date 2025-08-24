import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-modal',
  templateUrl: './modalhelper.html',

})
export class ModalComponent {
  @Output() close = new EventEmitter<void>();

  cerrar() {
    this.close.emit();
  }
}
