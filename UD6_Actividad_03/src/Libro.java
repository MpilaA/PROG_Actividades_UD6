import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class Libro implements Serializable{

    @Serial
    private static final long serialVersionUID = 1926772528577468160L;

    private String ISBN;
    private String titulo;
    private String autor;
    private LocalDate fechaPublicacion;

    // Constructor del objeto
    public Libro(String ISBN, String titulo, String autor, LocalDate fechaPublicacion) {
        this.ISBN = ISBN;
        this.titulo = titulo;
        this.autor = autor;
        this.fechaPublicacion = fechaPublicacion;
    }

    // Metdos get del objeto
    public String getISBN() {
        return this.ISBN;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getAutor() {
        return this.autor;
    }

    public LocalDate getFechaPublicacion() {
        return this.fechaPublicacion;
    }

    // Metodos toString sobrescrito
    @Override
    public String toString() {
        return "Libro [ISBN=" + ISBN + ", titulo=" + titulo + ", autor=" + autor + ", fechaPublicacion="
                + fechaPublicacion + "]";
    }

}
