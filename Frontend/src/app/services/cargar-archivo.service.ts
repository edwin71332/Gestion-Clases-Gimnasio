import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class CargarArchivo {
  private files: File[] = [];

  setFiles(fileList: FileList | null): void {
    this.files = fileList ? Array.from(fileList) : [];
  }

  getFiles(): File[] {
    return this.files;
  }

  clearFiles(): void {
    this.files = [];
  }
}
