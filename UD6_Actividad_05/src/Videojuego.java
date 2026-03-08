import java.time.LocalDateTime;

public class Videojuego extends Articulo{

    private GenerosV genero;
    LocalDateTime FechaAlquiler;
    boolean IsAlquilada;

    // Constructor de pelicula
    public Videojuego(String titulo, GenerosV genero){
        super(titulo);

        this.genero = genero;
        this.IsAlquilada = false;
    }

    // Funcion para mostrar la info de la pelicula
    public String mostrarInfoVideojuego(){
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
        return "Videojuego{" +
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
