import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class VideoDaw implements Serializable {

    @Serial
    private static final long serialVersionUID = 999518289L;

    public static final String CIF_pattern = "[A-Z]{1}[0-9]{8}";

    private String CIF;
    private String Direccion;
    private LocalDate FechaAlta;
    private LinkedList<Articulo> ArticulosRegistrados = new LinkedList<>();
    private HashMap<String, Cliente> ClientesRegistrados = new HashMap<>();

    // Construtor de VideoDaw
    public VideoDaw(String CIF, String Direccion) {
        this.CIF = CIF;
        this.Direccion = Direccion;
        this.FechaAlta = LocalDate.now();
    }

    public String mostrarInfoVideoClub(){
        String texto = "";
        texto += String.format("CIF: %s \n" +
                        "Direccion: %s \n" +
                        "FechaAlta: %s \n",
                this.CIF, this.Direccion, this.FechaAlta);
        return texto;
    }

    public String mostrarArticulosRegistrados(){
        String texto = "";
        // Bucle para listar la informacion de pelicualas y meterlas al texto.
        for (Articulo i : this.ArticulosRegistrados){
            if (i != null) {
                texto += i.toString();
            }
        }
        return texto;
    }

    public String mostrarClientesRegistrados(){
        String texto = "";
        // Bucle para listar la informacion de pelicualas y meterlas al texto.
        for (Cliente i : this.ClientesRegistrados.values()) {
            if (i != null) {
                texto += i.mostrarInfoCilente();
            }
        }
        return texto;
    }

    public boolean alquilarArticulo(Articulo a, Cliente c) {
        boolean estado1 = false, estado2 = false;
        if (a != null && c != null) {


            if (a instanceof Pelicula && !((Pelicula) a).IsAlquilada) {
                for (Articulo i : this.ArticulosRegistrados){
                    if (i == a && !((Pelicula) a).IsAlquilada){
                        ((Pelicula) i).IsAlquilada = true;
                        ((Pelicula) i).FechaAlquiler = LocalDateTime.now();
                        estado1 = true;
                    } else if (((Pelicula) a).IsAlquilada) {
                        // throw
                    }
                } 
            } else if (a instanceof Videojuego) {
                for (Articulo i : this.ArticulosRegistrados){
                    if (i == a && !((Videojuego) a).IsAlquilada) {
                        ((Videojuego) i).IsAlquilada = true;
                        ((Videojuego) i).FechaAlquiler = LocalDateTime.now();
                        estado1 = true;
                    } else if (((Videojuego) a).IsAlquilada) {
                        // throw
                    }
                }
            }

            // Comprobacion de que el anterior 
            // paso salio correctamente
            if (estado1) {
                c.addArticulo(a);
                ClientesRegistrados.put(c.getDNI(), c);
                estado2  = true;
            }

        }
        // El and esta para devolver false encaso de que alguno de los dos sea incorrecto.
        return (estado1 && estado2);
    }

    // Esto es para que paren las advertencias de codigo muerto
    public boolean devolverArticulo(Articulo a, Cliente c) {
        boolean estado1 = false, estado2 = false;

        if (a == null && c == null) {
            return false;
        } else if (a instanceof Pelicula) {

            Pelicula devolverPelicula = (Pelicula) a;
            
            for (Articulo i : this.ArticulosRegistrados) {
                if (i.equals(a)) {
                    // Comprobacion de que esta alquilada para
                    // modificarla para quitarla de estar alquilada en caso de ser
                    // corrrecto.
                    if (((Pelicula) i).IsAlquilada) {
                        devolverPelicula.IsAlquilada = false;
                        i = devolverPelicula;
                        estado1 = true;
                    }
                }
            }
            if ((LocalDateTime.now().getDayOfMonth() - devolverPelicula.FechaAlquiler.getDayOfMonth()) < 2) {}
        } else if (a instanceof Videojuego) {
            Videojuego devolverVideojuego = (Videojuego) a;
            
            for (Articulo i : this.ArticulosRegistrados) {
                if (i.equals(a)){
                    if (((Videojuego) i).IsAlquilada){
                    devolverVideojuego.IsAlquilada = false;
                    i = devolverVideojuego;
                    estado1 = true;
                    }
                }
            }
            if ((LocalDateTime.now().getDayOfMonth() - devolverVideojuego.FechaAlquiler.getDayOfMonth()) < 2) {}
        }

        if (estado1) {
            c.delArticulo(a);
            ClientesRegistrados.put(c.getDNI(), c);
            estado2  = true;
        }

        // El and esta para devolver false encaso de que alguno de los dos sea incorrecto.
        return (estado1 && estado2);
    }

    public boolean darBajaCliente(Cliente c){ // Revisar
        if (c != null) {
            return ClientesRegistrados.remove(c.getDNI(), c);
        }

        return false;
    }

    public boolean registrarCliente(Cliente c){
        if (c != null){
            if (!ClientesRegistrados.containsKey(c.getDNI())) {
                return (ClientesRegistrados.put(c.getDNI(), c) != null);
            }
        }
        
        return false;
    }

    public boolean addArticulo(Articulo a){

        if (a != null) {
            return ArticulosRegistrados.add(a);
        }

        return false;
    }

    public boolean darBajaArticulo(Articulo a){

        if (a != null) {
            for (Articulo i :this.ArticulosRegistrados) {
                if (i.equals(a)) {
                    i.setFechaBaja(LocalDate.now());
                    return true;
                }
            }
        }

        return false;
    }


    // Getters
    public String getCIF(){
        return this.CIF;
    }

    public List<Articulo> getArticulosRegistrados() {
        return this.ArticulosRegistrados;
    }

    public Map<String, Cliente> getClientesRegistrados() {
        return this.ClientesRegistrados;
    }
    
}