import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { FormsModule } from '@angular/forms';
import { ClasesTabla } from '../../../interfaces/Clase-tabla';
import { NotificacionService } from '../../../services/notificacion.service';


// Componente para el calendario
@Component({
  selector: 'app-calendar',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './calendario.component.html',
})


export class CalendarComponent {

  constructor(
    private notificacionService: NotificacionService
  ) { }


  @Input() clase: ClasesTabla | null = null;
  @Output() closeCalendar = new EventEmitter<void>();
  // Emite el evento con los datos del horario confirmado
  @Output() horarioConfirmado = new EventEmitter<{
    idClase: number,
    fechaInicio: string,
    fechaFinal: string,
    horaInicio: string,
    horaFin: string,
    duracion: number
  }>();


  horas: string[] = [
    '06:00 am', '07:00 am', '08:00 am', '09:00 am', '10:00 am', '11:00 am', '12:00 pm',
    '01:00 pm', '02:00 pm', '03:00 pm', '04:00 pm', '05:00 pm', '06:00 pm', '07:00 pm', '08:00 pm', '09:00 pm'
  ];
  horaInicio: string = '';
  duracionMinutos: number = 45;
  horaFinCalculada: string = '';
  fechaInicio: string = '';
  fechaFinal: string = '';


  confirmarHorario() {
    if (!this.clase) return;

    if (!this.fechaInicio || !this.fechaFinal || !this.horaInicio || !this.horaFinCalculada) {
      this.notificacionService.error(
        'Datos incompletos',
        'Por favor completa fecha de inicio, fecha final, hora de inicio y duración.'
      );
      return;
    }

    if (!this.validarFechas()) {
      return; // No continuar si la validación falla
    }

    const datos = {
      idClase: this.clase.id,
      fechaInicio: this.fechaInicio,
      fechaFinal: this.fechaFinal,
      horaInicio: this.Formato24horas(this.horaInicio),
      duracion: this.duracionMinutos,
      horaFin: this.Formato24horas(this.horaFinCalculada),
    };


    this.notificacionService.success('Horario asignado', 'El horario fue enviado correctamente.').then(() => {
      location.reload();
    });
    this.horarioConfirmado.emit(datos);
    this.cerrar();
  }






  cerrar() {
    this.closeCalendar.emit();
  }
  actualizarHoraFin() {
    if (!this.horaInicio || !this.duracionMinutos) {
      this.horaFinCalculada = '';
      return;
    }

    const [time, period] = this.horaInicio.split(' ');
    let [h, m] = time.split(':').map(Number);
    if (period.toLowerCase() === 'pm' && h !== 12) h += 12;
    if (period.toLowerCase() === 'am' && h === 12) h = 0;

    const inicioMin = h * 60 + m;
    const finMin = inicioMin + this.duracionMinutos;
    const limiteFinMin = 22 * 60; // 10:00 PM = 1320 min

    if (finMin > limiteFinMin) {
      this.horaFinCalculada = '';
      this.notificacionService.error(
        'Duración no permitida',
        'La hora de finalización no puede ser posterior a las 10:00 PM'
      );
      return;
    }

    let newH = Math.floor(finMin / 60);
    let newM = finMin % 60;
    const ampm = newH >= 12 ? 'PM' : 'AM';
    newH = newH % 12 || 12;
    const paddedM = newM.toString().padStart(2, '0');

    this.horaFinCalculada = `${newH}:${paddedM} ${ampm}`;
  }

  validarDuracion(event: Event) {
    const input = event.target as HTMLInputElement;
    let value = Number(input.value);

    if (value > 240) {
      value = 240;
      input.value = value.toString();
    }

    if (value < 45) {
      value = 45;
      input.value = value.toString();
    }

    this.duracionMinutos = value;
    this.actualizarHoraFin();
  }
  // En tu componente Angular
  formatDuracion(minutos: number): string {
    if (minutos < 60) {
      return `${minutos} minutos`;
    } else {
      const horas = Math.floor(minutos / 60);
      const min = minutos % 60;
      let result = `${horas} hora${horas > 1 ? 's' : ''}`;
      if (min > 0) {
        result += ` ${min} minutos`;
      }
      return result;
    }
  }

  Formato24horas(hora12h: string): string {
    const [time, period] = hora12h.trim().split(' ');
    let [h, m] = time.split(':').map(Number);
    if (period.toLowerCase() === 'pm' && h !== 12) h += 12;
    if (period.toLowerCase() === 'am' && h === 12) h = 0;
    return `${h.toString().padStart(2, '0')}:${m.toString().padStart(2, '0')}`;
  }

  validarFechas() {
    if (!this.fechaInicio || !this.fechaFinal) return true; // No validar si alguna está vacía

    const inicio = new Date(this.fechaInicio);
    const final = new Date(this.fechaFinal);

    if (final < inicio) {
      this.notificacionService.error(
        'Fecha inválida',
        'La fecha final no puede ser anterior a la fecha de inicio.'
      );
      return false;
    }

    return true;
  }


}







