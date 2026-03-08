import java.time.LocalDate;

public class Articulo {
    public static int generadorID = 0; // Esto es para generar el codigo

    protected final String cod;
    protected String titulo;
    protected LocalDate FechaRegistro;
    protected LocalDate FechaBaja;

    public Articulo(String titulo) {
        this.cod = String.format("P-%04d", generadorID);
        generadorID++;
        this.titulo = titulo;
        this.FechaRegistro = LocalDate.now();
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        this.FechaBaja = fechaBaja;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "cod='" + cod + '\'' +
                ", titulo='" + titulo + '\'' +
                ", FechaRegistro=" + FechaRegistro +
                ", FechaBaja=" + FechaBaja +
                '}';
    }
}
