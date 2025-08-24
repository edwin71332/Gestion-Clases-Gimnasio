import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideHttpClient, withFetch } from '@angular/common/http';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }), // reduce la cantidad de eventos procesados.
    provideRouter(routes), // Define las rutas de la aplicación usando app.config.ts
    provideHttpClient(withFetch()) //Configura HTTP Client para realizar peticiones HTTP.
  ]
};
