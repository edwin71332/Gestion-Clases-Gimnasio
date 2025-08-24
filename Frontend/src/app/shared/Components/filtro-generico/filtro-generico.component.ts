import {
  Component,
  Input,
  Output,
  EventEmitter,
  OnInit,
  OnChanges,
  SimpleChanges,
  ElementRef,
  HostListener,
} from "@angular/core"
import { CommonModule } from "@angular/common"
import type  {filtrodef} from "../../../interfaces/filtro-def"

@Component({
  selector: "app-filtro-generico",
  standalone: true,
  imports: [CommonModule],
  templateUrl: "./filtro-generico.component.html",
})
export class filtrogenericoComponent implements OnInit, OnChanges {
  /** Modo legacy: un solo campo */
  @Input() filterField = ""
  @Input() isMulti = false

  /** Nuevo modo: múltiples campos */
  @Input() filters: filtrodef[] = []

  /** Datos a filtrar y placeholder */
  @Input() data: any[] = []
  @Input() placeholder = "Filtrar"

  @Output() filteredData = new EventEmitter<any[]>()

  @Input() filterFieldLabel?: string // etiqueta opcional

  public isOpen = false // controla el dropdown
  public uniqueOptions: Record<string, string[]> = {} // opciones únicas por campo
  public selectedOptions: Record<string, string[]> = {} // opciones seleccionadas por campo

  constructor(private _elementRef: ElementRef) {}

  // Ciclo de vida: al iniciar
  ngOnInit() {
    this.setup() // prepara opciones
    this.emitFilteredData() // emite datos filtrados (initial load)
  }

  // Cada vez que cambian inputs clave
  ngOnChanges(ch: SimpleChanges) {
    if (ch["data"] || ch["filterField"] || ch["filters"]) {
      this.setup()
      this.emitFilteredData()
    }
  }

  /** Inicializa uniqueOptions y selectedOptions según el modo */
  private setup() {
    // 1) Selecciona defs: legacy o múltiple
    const defs = this.filters.length
      ? this.filters
      : [{ field: this.filterField, label: this.placeholder, isMulti: this.isMulti }]

    // 2) Resetea selections
    this.selectedOptions = {}
    defs.forEach((f) => (this.selectedOptions[f.field] = []))

    // 3) Genera uniqueOptions
    this.uniqueOptions = {}
    defs.forEach((f) => {
      const vals: string[] = []
      this.data.forEach((item) => {
        const v = item[f.field]
        if (f.isMulti && Array.isArray(v)) {
          // varios valores: extrae string o x.nombre
          v.forEach((x) => vals.push(typeof x === "string" ? x : x?.nombre))
        } else if (!f.isMulti && v != null) {
          vals.push(String(v))
        }
      })
      // elimina duplicados y ordena
      this.uniqueOptions[f.field] = Array.from(new Set(vals)).sort((a, b) => a.localeCompare(b))
    })
  }

  // abre/cierra el dropdown
  toggleDropdown() {
    this.isOpen = !this.isOpen
  }

  // selecciona/deselecciona una opción
  onOptionToggle(field: string, opt: string) {
    const sel = this.selectedOptions[field]
    const idx = sel.indexOf(opt)
    idx >= 0 ? sel.splice(idx, 1) : sel.push(opt)
    this.emitFilteredData()
  }

  //  limpia selección de un campo específico (para píldora "Todos")
  clearField(field: string): void {
    this.selectedOptions[field] = []
    this.emitFilteredData()
  }

  // verifica si un campo específico está "limpio" (sin selecciones)
  isFieldClear(field: string): boolean {
    return this.selectedOptions[field].length === 0
  }

  // Aplica los filtros combinados
  private applyFilters(): any[] {
    const defs = this.filters.length ? this.filters : [{ field: this.filterField, isMulti: this.isMulti }]

    return this.data.filter((item) =>
      defs.every((f) => {
        const sel = this.selectedOptions[f.field]
        if (!sel.length) return true // sin filtro = acepta todo
        const v = item[f.field]
        if (f.isMulti && Array.isArray(v)) {
          // alguno de los valores aparece en el array seleccionado
          return v.some((x) => sel.includes(typeof x === "string" ? x : x?.nombre))
        }
        // valor simple igual a uno seleccionado
        return sel.includes(String(v))
      }),
    )
  }

  // emite resultados al oyente
  emitFilteredData() {
    this.filteredData.emit(this.applyFilters())
  }

  // limpia selección de un campo específico
  public clearAll(): void {
    // limpia todas las selecciones
    Object.keys(this.selectedOptions).forEach((field) => {
      this.selectedOptions[field] = []
    })
    this.emitFilteredData() // emite data original
    this.isOpen = false // cierra dropdown
  }

  // etiqueta del botón: placeholder, item o conteo
  // si hay un solo item seleccionado, muestra su nombre
  // si hay varios, muestra el conteo
  // si no hay ninguno, muestra el placeholder
  get buttonLabel(): string {
    const total = Object.values(this.selectedOptions).reduce((sum, a) => sum + a.length, 0)
    if (total === 0) return this.placeholder
    if (total === 1) {
      for (const arr of Object.values(this.selectedOptions)) {
        if (arr.length === 1) return arr[0]
      }
    }
    return `${total} seleccionadas`
  }

  //   obtiene el conteo de filtros activos para mostrar en UI
  get activeFiltersCount(): number {
    return Object.values(this.selectedOptions).reduce((sum, a) => sum + a.length, 0)
  }

  //  verifica si hay algún filtro activo
  get hasActiveFilters(): boolean {
    return this.activeFiltersCount > 0
  }

  // cierra dropdown si se hace click fuera
  @HostListener("document:click", ["$event"])
  onDocClick(e: MouseEvent) {
    if (!this.isOpen) return
    if (!this._elementRef.nativeElement.contains(e.target)) {
      this.isOpen = false
    }
  }
}
