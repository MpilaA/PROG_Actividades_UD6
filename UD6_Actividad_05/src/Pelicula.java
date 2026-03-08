import java.time.LocalDateTime;

public class Pelicula extends Articulo{

    private GenerosP genero;
    public LocalDateTime FechaAlquiler;
    public boolean IsAlquilada;

    // Constructor de pelicula
    public Pelicula(String titulo, GenerosP genero){
        super(titulo);

        this.genero = genero;
        this.IsAlquilada = false;
    }

    // Funcion para mostrar la info de la pelicula
    public String mostrarInfoPelicula(){
        String texto = "";
        texto = String.format("Info de la pelicula %s: \n" +
                        "Codigo: %s \n" +
                        "Titulo: %s \n" +
                        "Genero: %s \n" +
                        "Fecha de registro: %s \n" +
                        "Fecha de baja: %s \n" +
                        "Fecha de alquiler: %s \n"
                , this.titulo, this.cod, this.titulo, this.genero, this.FechaRegistro, this.FechaBaja,
                this.FechaAlquiler);
        if (this.IsAlquilada) {
            texto += "Esta alquilada";
        } else {
            texto += "No esta alquilada";
        }
        return texto;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "genero=" + genero +
                ", FechaAlquiler=" + FechaAlquiler +
                ", IsAlquilada=" + IsAlquilada +
                ", cod='" + cod + '\'' +
                ", titulo='" + titulo + '\'' +
                ", FechaRegistro=" + FechaRegistro +
                ", FechaBaja=" + FechaBaja +
                '}';
    }
}
