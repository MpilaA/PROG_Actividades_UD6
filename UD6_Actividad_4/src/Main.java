import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Variable del Scanner sin incializar
        Scanner sc;

        // Variables con las rutas de los archivos
        final String pathBinario = "./almacen.dat";
        final String pathUnicode = "./productos.csv";
        final File pathInventario = new File("./inventario.dat");

        // Variable que contiene el inventario en una lista
        LinkedList<Producto> inventario = new LinkedList<>();

        // try catch para imprimir mensajes de error en el que se agregan 
        // los productos del archivo del inventario.
        try {
            inventario = new LinkedList<>(leerInventario(pathInventario));
        } catch (IOException e) {
            System.out.println("El archivo no es accesible.");
        } catch (ClassNotFoundException e) {
            System.out.println("El archivo esta corrupto.");
        }

        // Variable para decidir si importar los archivos o no
        String eleccion = "";

        // Bucle para preguntar al usuario si quiere importar los archivos
        do {
            System.out.print("¿Quiere importar los archivos? (s/n) ");
            sc = new Scanner(System.in);
            eleccion = sc.nextLine().toLowerCase();
        } while (!(eleccion.equals("s") || eleccion.equals("n")));

        // If con la importacion de los archivos.
        if (eleccion.equals("s")) {
            List<Producto> productos = leerArchivos(pathUnicode, pathBinario);
            if (productos != null) {
                for (Producto p: productos) {
                    // If ara evitar productos repetidos antes de añadirlos
                    if (!inventario.contains(p)) {
                        inventario.addFirst(p);
                    }
                }
                // Mensaje para indicar que ya termino
                System.out.println("Archivos importados");
            } else {
                // Mensaje en caso de que falle la importancion
                System.out.println("Error al importar los archivos");
            }
        }

        // Variable para indicar la salidad del bucle
        boolean salida = false;

        // Bucle del menu
        do {
            // Pongo la variable del escaner como nula para 
            // tener que volverlo a inicializar.
            sc = null;

            // Mensaje con las opciones del menu
            System.out.println("" +
                    "1. Mostrar Productos del Inventario.\n" +
                    "2. Eliminar Producto.\n" +
                    "3. Guardar y Salir.");

            // Variable para almacenar la opcion elegida
            int opcion = 0;

            // Bucle para preguntar la opcion por numeros
            while (opcion == 0) {
                System.out.print("Opcion: ");
                try {
                    sc = new Scanner(System.in);
                    opcion = sc.nextInt();
                } catch (InputMismatchException e) {
                    // Mensje en caso de que no se usen numeros enteros
                    System.out.println("Solo se admiten numeros");
                }

            }

            // Switch con las acciones de las opciones.
            switch (opcion) {
                case 1: // Mostrar Productos en el Inventario.
                    System.out.println();
                    // Bucle que imprime todos los productos del inventario
                    for (Producto p: inventario){
                        System.out.println(p);
                    }
                    System.out.println();
                    break;
                case 2: // Eliminar Producto por referencia.
                    // Variable para guardar la referencia
                    String referencia;
                    // Bucle para pedir la referencia
                    do {
                        System.out.println();
                        System.out.print("Dame la referencia: ");

                        sc = new Scanner(System.in);
                        referencia = sc.nextLine();
                    } while (referencia.isBlank());

                    // Iterator del invntario
                    Iterator<Producto> inventarioIterator = inventario.iterator();

                    // Bucle para buscar y borrar el producto
                    while (inventarioIterator.hasNext()){

                        Producto p = inventarioIterator.next();
                        
                        // Si el producto coincide con la referencia se borra
                        if (p.getReferencia().equals(referencia)){
                            inventarioIterator.remove();
                        }
                    }
                    break;
                case 3: // Guardar y Salir (inventario.dat)
                    // Try con la funcion para escribir el inventario y en caso de fallar 
                    // para que no ponga la la salida en verdadero
                    try {
                        escribirInventario(pathInventario, inventario);
                        salida = true;
                    } catch (IOException e) {
                        // Mensaje en caso de error al escribir
                        System.out.println("Error al escribir el archivo de inventario");
                    }
                    break;
//                case 4: // (Opcional) Registrar producto en el Inventario (no permitir referencias repetidas).
//                    break;
                default:
                    break;
            }

        } while (!salida);
        sc.close();
    }

    // Funcion para importar el archivo
    public static List<Producto> leerArchivos(String fileU, String fileB){
        // Lista con los productos
        ArrayList<Producto> productos = new ArrayList<>();
        // Try con las acciones para leer los productos 
        // del archivo productos y meterlos en la lista.
        try (FileReader fileR = new FileReader(fileU);
            BufferedReader lector = new BufferedReader(fileR)){
            String linea = lector.readLine();
            while (linea != null){
                String [] atributos = linea.split("/");
                linea = lector.readLine();
                productos.add(new Producto(atributos[0], atributos[2], atributos[3]));
            }
        } catch (IOException e){
            // En caso de una IOException decidi que devolviera null 
            // ya que con lo del otro archivo la infomacion estaria incompleta
            return null;
        }

        // Try con las acciones para leer los productos 
        // del archivo productos y meterlos en la lista.
        try (FileInputStream fileR = new FileInputStream(fileB);
            DataInputStream lector = new DataInputStream(fileR)){
            
            // Index es para ir producto por producto de la lista
            int index = 0;
            boolean eof = false;

            while (!eof) {
                try {

                    // Primero obtengo el producto
                    Producto p = productos.get(index);

                    // Modifico los atributos con los valores del archivo
                    p.setCantidad(lector.readInt());
                    p.setPrecio(lector.readDouble());
                    p.setDescuento(lector.readInt());
                    p.setIVA(lector.readInt());
                    p.setAplicarDto(lector.readBoolean());

                    // Sobreescribo el producto con el nuevo
                    productos.set(index, p);
                } catch (EOFException e) {
                    eof = true;
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
                index++;
            }
        } catch (IOException e){
            // En caso de una IOException decidi que devolviera null 
            // ya que con lo del otro archivo la infomacion estaria incompleta
            return null;
        }
        return productos;
    }

    // Funcion para leer el archivo del inventario
    public static List<Producto> leerInventario(File file) throws IOException, ClassNotFoundException {

        // Esto es por si el archivo no existe
        if (file.length() == 0) {
            FileWriter fileW = new FileWriter(file, true);
            fileW.close();
            return new LinkedList<>();
        } else {
            // Lista para guardar los productos.
            LinkedList<Producto> productos = new LinkedList<>();

            // Variables para usar el archivo en modo lectura
            FileInputStream fileR = new FileInputStream(file);
            ObjectInputStream lector = new ObjectInputStream(fileR);

            // Proceso para obtener los productos 
            // serializados del archivo
            boolean eof = false;

            while (!eof) {
                try {
                    Object o = lector.readObject();
                    if (o instanceof Producto){
                        productos.add((Producto) o);
                    }
                } catch (EOFException e) {
                    eof = true;
                }
            }
            // Una vez termine se devolvera la lista con los productos
            return productos;
        }
    }

    // Funcion para volcar para volcar el inventario en el archivo 
    public static void escribirInventario(File file, List<Producto> inventario) throws IOException {
        // Variables para usar el archivo en modo lectura
        FileOutputStream fileW = new FileOutputStream(file, false);
        ObjectOutputStream escritor = new ObjectOutputStream(fileW);

        // Bucle que escribe todos los productos del inventario en el archivo
        for (Producto p: inventario) {
            escritor.writeObject(p);
        }
    }
}