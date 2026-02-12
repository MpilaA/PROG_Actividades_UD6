import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;

public class Almacen {
    // Esto es una aatributo de la clase con la ruta del archivo 
    // a usar en los metodos de la clase.
    public static final String path = "./Almacen.dat";

    // Metodo para extraer los productos del archivo
    public static LinkedList<Producto> leer() throws IOException, ArrayIndexOutOfBoundsException {
        // Estas son las variables para ver si se puede escribir o leer
        boolean leer = false;
        boolean escribir = false;
        // Esta es la coleccion que se va a devolver una vez tenga el contenido del archivo
        LinkedList<Producto> productos = new LinkedList<>();

        // Esta parte es para crear el archivo en caso de que no exista
        // y saber si se puede escribir en el
        try {
            FileWriter archivoW = new FileWriter(path, true);
            archivoW.close();
            escribir = true;
        } catch (IOException e) {
            escribir = false;
        }
        // Aqui se crea la variable para leer el archivo
        FileReader archivoR = null;
        BufferedReader bufferR = null;
        try {
            archivoR = new FileReader(path);
            bufferR = new BufferedReader(archivoR);
            leer = true;
        } catch (IOException e){
        leer = false;
        }

        // Si alguna de las dos variables es falsa se devuelve una IOException con esto hago que si las dos son falsas
        // puedo devolver una excepcion que indique que no se puede ni leer ni escribir
        if ((!leer) && (!escribir)) {
            throw new IOException("No se puede escribir ni leer");
        } else if (!leer) {
            throw new IOException("No se puede leer");
        } else if (!escribir) {
            throw new IOException("No se puede escribir");
        }

        // Aqui extraigo los productos del archivo en formato csv y
        // los añado a la coleccion que voy a devolver
        String l = bufferR.readLine();
        while (l != null) {
            String[] datos = l.split(",");

            Producto p = new Producto(datos[0].trim(),
                    datos[1],
                    Integer.parseInt(datos[2].trim()),
                    Double.parseDouble(datos[3].trim()));
            l = bufferR.readLine();
            productos.add(p);
        }

        // Cierro las variables de lectura archivo
        bufferR.close();
        archivoR.close();

        // Devuelve la coleccion.
        return productos;
    }

    // Metodo para escribir los productos en el archivo
    public static void guardar(LinkedList<Producto> productos) throws IOException {
        // Para esto prefiero no usar directamente la coleccion que se introduce
        Iterator<Producto> productosIterator = productos.iterator();

        // Reviso si el contenido del archivo no sea el mismo
        // para evitar tener que escribir lo mismo
        if (!productosIterator.equals(leer().iterator())) {
            // Variables para escribir en un archivo
            FileWriter archivoW = new FileWriter(path, false);
            BufferedWriter bufferW = new BufferedWriter(archivoW);

            // Bucle para escribir los productos de
            // la coleccion en el archivo en formato csv
            while (productosIterator.hasNext()) {
                Producto p = productosIterator.next();
                bufferW.write(p.getCodigo() + "," +
                        p.getNombre() + "," +
                        p.getCantidad() + "," +
                        p.getPrecio());
                bufferW.newLine();
            }

            // Cierre de la varibles para escribir en el acrhivo
            bufferW.close();
            archivoW.close();
        }
    }


}
