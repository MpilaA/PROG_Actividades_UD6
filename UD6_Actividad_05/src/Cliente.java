import java.time.LocalDate;
import java.util.ArrayList;

public class Cliente extends Persona {
    // Variable fijas que se usan para cosas relacionadas 
    // a los objetos de esta clase
    public static final String NumPattern = "S-[0,9]{4}";

    public static int generadorID = 0; //Esto es para generar el numero de socio

    private String NumSocio;
    LocalDate FechaBaja;
    ArrayList<Articulo> ArticulosAlquilados = new ArrayList<>();

    // Constructor del cliente
    public Cliente(String DNI, String nombre, String direccion, LocalDate FechaN){
        super(DNI, nombre, FechaN, direccion);

        this.NumSocio = String.format("S-%04d", generadorID);
        generadorID++;
    }

    public String mostrarInfoCilente(){
        String texto = "";
        texto += String.format("Cliente %s:\n" +
                        "DNI: %s\n" +
                        "Numero de socio: %s\n" +
                        "Direccion %s\n" +
                        "Fecha de nacimiento: %s\n" +
                        "Fecha de baja: %s\n",
                this.nombre, this.DNI, this.NumSocio, this.direccion,
                this.FechaNacimiento, this.FechaBaja);
        // this.PeliculasAlquiladas de momento no
        return texto;
    }

    public String mostrarArticulosAlquilados() {
        String texto = "";
        // Bucle para listar las pelicualas alquiladas añadiendolas a texto.
        for (Articulo i : this.ArticulosAlquilados) {
            if (i != null) {
                texto += i.getTitulo();
            }
        }
        return texto;
    }

    // Getter
    public String getNumSocio() {
        return this.NumSocio;
    }

    // Setter
    public void setFechaBaja() {
        this.FechaBaja = LocalDate.now();
    }
    
    // Metodo para añadir una pelicula a PeliculasAlquiladas
    public boolean addArticulo(Articulo a){

        return ArticulosAlquilados.add(a);
    }

    // Metodo para quitar una pelicula a PeliculasAlquiladas y
    // hacer mas pequeño el array de PelicualasAlquiladas
    public boolean delArticulo(Articulo a){

        if (a != null){
            return ArticulosAlquilados.remove(a);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "NumSocio='" + NumSocio + '\'' +
                ", FechaBaja=" + FechaBaja +
                ", PeliculasAlquiladas=" + ArticulosAlquilados +
                ", DNI='" + DNI + '\'' +
                ", nombre='" + nombre + '\'' +
                ", FechaNacimiento=" + FechaNacimiento +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
