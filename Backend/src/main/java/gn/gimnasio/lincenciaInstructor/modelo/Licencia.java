package gn.gimnasio.lincenciaInstructor.modelo; // corrige typo "lincencia" a "licencia"

import gn.gimnasio.instructor.modelo.Instructor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "Licencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Licencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Relación ManyToOne con Instructor  
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_instructor", nullable = false)
    private Instructor instructor;

    @Column(nullable = false, length = 255)
    private String filename;

    @Column(nullable = false, length = 500)
    private String filepath;

    @Column(name = "uploaded_at", nullable = false)
    private Instant uploadedAt;

    /** 
     * Método de fábrica para crear la entidad tras guardar el archivo 
     */
    public static Licencia fromFile(Instructor instructor, String filename, String filepath) {
        Licencia lic = new Licencia();
        lic.setInstructor(instructor);
        lic.setFilename(filename);
        lic.setFilepath(filepath);
        lic.setUploadedAt(Instant.now());
        return lic;
    }
}
