import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';

@Injectable({ providedIn: 'root' })

export class NotificacionService {
  success(title: string, text: string) {
    return Swal.fire({ icon: 'success', title, text });
  }
  error(title: string, text: string) {
    Swal.fire({ icon: 'error', title, text });
  }
} 