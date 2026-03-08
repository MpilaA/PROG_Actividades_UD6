import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        final File archivo = new File("./Video_clubs.dat");

        ArrayList<VideoDaw> VideoClubs = new ArrayList<>();
        try {
            VideoClubs = (ArrayList<VideoDaw>) leer(archivo);
        } catch (IOException e) {
            System.out.println("Archivo inaccesible");
        } catch (ClassNotFoundException e) {
            System.out.println("Archivo corrupto");
        }

        // Estas variables son necesarias para el bucle y el menu
        Scanner sc;
        String opcion;

        String VideoClubCIF = null;

        do{
            System.out.println("1. Crear y registrar VideoClub en la franquicia.\n" +
                    "2. Registrar articulo en videoclub.\n" +
                    "3. Crear y registrar cliente en videoclub.\n" +
                    "4. Alquilar articulo.\n" +
                    "5. Devolver articulo.\n" +
                    "6. Dar de baja cliente.\n" +
                    "7. Dar de baja articulo.\n" +
                    "8. Salir.");
            sc = new Scanner(System.in);
            System.out.print("Opcion: ");
            opcion = sc.next();
            
            /*Estas variable las creo aqui y las inicializo porque si las creo 
            por cada opcion el compilador no me deja ejecutar nada*/
            Boolean estado = true;
            String CIFVideoClub = null;

            switch(opcion){
                case "1": //Crear y registrar VideoClub en la franquicia.
                    String CIF;
                    do{
                        System.out.print("Introduzca el CIF: ");
                        sc = new Scanner(System.in);
                        CIF = sc.nextLine().toUpperCase();
                    }while(!Pattern.matches(VideoDaw.CIF_pattern, CIF));

                    String Direccion;
                    do{
                        System.out.print("Dame la direccion: ");
                        sc = new Scanner(System.in);
                        Direccion = sc.nextLine();
                    }while(Direccion.isBlank());

                    VideoClubs.add(new VideoDaw(CIF,Direccion));

                    
                    System.out.println("Video Club creado.");
                    break;
                case "2": // Registrar articulo en videoclub.
                    String eleccion = null;
                    do {
                        System.out.println("\n" +
                                "¿Que quires ingresar? \n" +
                                "pelicula o videojuego");
                        System.out.print("> ");
                        sc = new Scanner(System.in);
                        eleccion = sc.next();
                    } while (!(eleccion.equals("pelicula") || eleccion.equals("videojuego")));
                    // Pidiendo el titulo de la pelicula.
                    String titulo;
                    do {
                        System.out.print("Dame el titulo: ");
                        sc = new Scanner(System.in);
                        titulo = sc.next();
                    } while (titulo.isBlank());

                    // Pidiendo el genero de la pelicula.
                    GenerosP generoPelicula = null;
                    GenerosV generoVideojuego = null;
                    if (eleccion.equals("pelicula")) {
                        do {
                            for (int i = 0; i < GenerosP.values().length; i++) {
                                System.out.println((i + 1) + ". " + GenerosP.values()[i]);
                            }
                            try {
                                System.out.print("Numero del genero: ");
                                sc = new Scanner(System.in);
                                int NumGenero = (sc.nextInt() - 1);
                                if (NumGenero < GenerosP.values().length && NumGenero > -1) {
                                    generoPelicula = GenerosP.values()[NumGenero];
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Eso no es un numero");
                            }
                        } while (generoPelicula == null);
                    } else if (eleccion.equals("videojuego")) {
                        do {
                            for (int i = 0; i < GenerosV.values().length; i++) {
                                System.out.println((i + 1) + ". " + GenerosV.values()[i]);
                            }
                            System.out.print("Numero del genero: ");
                            sc = new Scanner(System.in);
                            try {
                                int NumGenero = (sc.nextInt() - 1);
                                if (NumGenero < GenerosV.values().length && NumGenero > -1) {
                                    generoVideojuego = GenerosV.values()[NumGenero];
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Eso no es un numero");
                            }
                        } while (generoVideojuego == null);
                    }


                    VideoClubCIF = preguntarVideoClub();
                    for (VideoDaw i: VideoClubs) {
                        if (VideoClubCIF.equals(i.getCIF()) && eleccion.equals("pelicula")) {
                            estado = i.addArticulo(new Pelicula(titulo, generoPelicula));
                        } else if (VideoClubCIF.equals(i.getCIF()) && eleccion.equals("videojuego")) {
                            estado = i.addArticulo(new Videojuego(titulo, generoVideojuego));
                        }
                    }
                    

                    if (estado){
                        System.out.println("La pelicula se ha registrada " +
                                "correctamente");
                    } else {
                        System.out.println("La pelicula no se ha podido" +
                                "registrar intentelo de nuevo");
                    }
                    break;
                case "3": //Crear y registrar cliente en videoclub.
                    // Pidiendo el DNI del cliente.
                    String DNI;
                    do {
                        System.out.print("Dame el DNI: ");
                        sc = new Scanner(System.in);
                        DNI = sc.next();
                        if (Pattern.matches(Cliente.dniPattern, DNI)){
                            estado = false;
                        }
                    } while (estado);
                    // Pidiendo el nombre de la persona.
                    estado = true;
                    String nombre;
                    do {
                        System.out.print("Dame el nombre: ");
                        sc = new Scanner(System.in);
                        nombre = sc.nextLine();
                        if (!nombre.isBlank()) {
                            estado = false;
                        }
                    } while (estado);
                    // Pidiendo la direccion del cliente.
                    estado = true;
                    String direccion;
                    do {
                        System.out.print("Dame la direccion: ");
                        sc = new Scanner(System.in);
                        direccion = sc.nextLine();
                        if (!direccion.isBlank()) {
                            estado = false;
                        }
                    } while (estado);
                    // Pidiendo la fecha de nacimiento.
                    if (!estado) {
                        estado = true;
                    }
                    // Pidiendo fecha de nacimiento
                    String FechaNacimeinto;
                    LocalDate FechaN = null;
                    do {
                        System.out.println("Por favor introduzca fechas al"+ 
                        " estilo 01-01-1991");
                        System.out.print("Dame la fecha de nacimiento: ");
                        sc = new Scanner(System.in);
                        FechaNacimeinto = sc.nextLine();
                        if (Pattern.matches(Common.dtf_pattern, FechaNacimeinto)) {
                            FechaN = LocalDate.parse(FechaNacimeinto, Common.dtf);
                            if ((LocalDate.now().getYear() - FechaN.getYear()) >= 18 &&
                            LocalDate.now().getDayOfYear() >= FechaN.getDayOfYear()) {
                                estado = false;
                            } else {
                                System.out.println("Eres menor de edad. ");
                            }
                        } 
                    } while (estado);

                    // Creo esto aqui porque dentro del if me daba error
                    String numSocio = "";

                    /* Creo el cliente, saco su numero de socio y lo 
                    guardo en el videoclub */
                    if (!estado) {
                        Cliente nuevoC = new Cliente(DNI, nombre, 
                            direccion, FechaN);

                        numSocio = nuevoC.getNumSocio();
                        VideoClubCIF = preguntarVideoClub();

                        for (VideoDaw i : VideoClubs) {
                            if (i.getCIF().equals(VideoClubCIF)){
                                estado = i.registrarCliente(nuevoC);
                                break;
                            }
                        }
                        
                    }
                    
                    /*  if (estado){
                        System.out.println("El cliente se ha registrado " +
                        "correctamente" + "\n Numero de socio: " +
                        numSocio);
                    } else {
                        System.out.println("El cliente no se ha podido" +
                        "registrar intentelo de nuevo");
                    } */
                    break;
                case "4": //Alquilar Articulo
                    VideoClubCIF = preguntarVideoClub();
                    String codAlquilarCliente = preguntarCliente();

                    Cliente AlquilarCliente = null;
                    VideoDaw VideoClub = null;

                    for (VideoDaw i : VideoClubs) {
                        if (i.getCIF().equals(VideoClubCIF)){
                            VideoClub = i;
                            for (Cliente c : i.getClientesRegistrados().values()) {
                                if (c.getNumSocio().equals(codAlquilarCliente)) {
                                    AlquilarCliente = c;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    Articulo AlquilarArticulo = preguntarArticulo(VideoClub);
                    // Compruebo si existen antes de alquilar y si algo sale mal
                    // pongo un mensaje indicando que esta mal.
                    if (AlquilarArticulo!=null && AlquilarCliente!=null) {
                        for (Articulo a : VideoClub.getArticulosRegistrados()) {
                            if (a == AlquilarArticulo) {
                                VideoClub.alquilarArticulo(a, AlquilarCliente);
                            }
                        }

                    } else {
                        System.out.println("Algo esta mal.");
                    }
                    
                    break;
                case "5": //Devolver articulo

                    VideoClubCIF = preguntarVideoClub();

                    String codDevolverCliente = preguntarCliente();

                    Cliente devolverCliente = null;

                    Articulo devoloverArticulo = null;

                    for (VideoDaw i : VideoClubs) {
                        if (i.getCIF().equals(VideoClubCIF)){
                            devoloverArticulo = preguntarArticulo(i);
                            for (Cliente c : i.getClientesRegistrados().values()) {
                                if (c.getNumSocio().equals(codDevolverCliente)) {
                                    devolverCliente = c;
                                }
                            }
                            i.devolverArticulo(devoloverArticulo, devolverCliente);
                            break;
                        }
                    }
                    break;
                case "6": //Dar de baja cliente.
                    VideoClubCIF = preguntarVideoClub();
                    if (!VideoClubCIF.isBlank()) {
                        String CodCliente = preguntarCliente();

                        estado = false;
                        for (VideoDaw i : VideoClubs){
                            if (i.getCIF().equals(VideoClubCIF)){
                                for (Cliente j : i.getClientesRegistrados().values()) {
                                    if (j.getNumSocio().equals(CodCliente)) {
                                        i.darBajaCliente(j);
                                        estado = true;
                                        break;
                                    }
                                }
                                break;
                            }
                        }

                    }
                    break;
                case "7": //Dar de baja película.
                    VideoClubCIF = preguntarVideoClub();

                    for (VideoDaw i : VideoClubs) {
                        if (VideoClubCIF.equals(i.getCIF())) {
                            Articulo bajaArticulo  = preguntarArticulo(i);
                            i.darBajaArticulo(bajaArticulo);
                            break;
                        }
                    }
                    break;
                case "8": //Salir.
                    System.out.println("Saliendo.");
                    try{
                        guardar(VideoClubs, archivo);
                    } catch (IOException e) {
                        System.out.println("Error al escribir en el archivo");
                    }
                    break;
                default: // Si se introduce un valor no esperado.
                    System.out.println("¿Que es eso?");
                    break;
            }
        }while(!opcion.equals("8"));
    }

    // Metodos para preguntar cosas al usuario sobre los objetos y la 
    // lista VideoClubs.
    public static String preguntarVideoClub(){

        String CIF;

        do {
            Scanner sc = new Scanner(System.in);
            System.out.print("Indique el CIF del video club: ");
            CIF = sc.nextLine();


        } while (!Pattern.matches(VideoDaw.CIF_pattern, CIF));
        return CIF;
    }

    public static Articulo preguntarArticulo(VideoDaw VideoClub){
        Scanner sc = new Scanner(System.in);
        System.out.println("Dame el titulo: ");
        String TArticulo = sc.next();

        if (VideoClub != null) {
            for (Articulo i : VideoClub.getArticulosRegistrados()) {
                if (TArticulo.equals(i.getTitulo())){
                    return i;
                }
            }
        }
        return null;
    }
    public static String preguntarCliente(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Dame el codigo del cliente: ");
        String codCliente = sc.next();

        return codCliente;
    }

    public static void guardar(List<VideoDaw> lista, File file) throws IOException{
        FileOutputStream archivoW = new FileOutputStream(file, false);
        ObjectOutputStream escritor = new ObjectOutputStream(archivoW);

        for (VideoDaw i :lista) {
            escritor.writeObject(i);
        }
        escritor.close();
        archivoW.close();
    }

    public static List<VideoDaw> leer(File file) throws IOException, ClassNotFoundException{
        ArrayList<VideoDaw> lista = new ArrayList<>();
        FileInputStream archivoR = new FileInputStream(file);
        ObjectInputStream lector = new ObjectInputStream(archivoR);

        boolean eof = false;

        while (!eof) {
            try {
                Object o = lector.readObject();
                if (o instanceof VideoDaw) {
                    lista.add((VideoDaw) o);
                }
            } catch (EOFException e) {
                eof = true;
            }
        }

        lector.close();
        archivoR.close();
        
        return lista;
    }
}