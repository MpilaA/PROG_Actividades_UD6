import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Almacen {
    // Esto es para guradar la ruta del archivo y poderlo usar.
    public static final File path = new File ("./Almacen.dat");

    // Esta es la funcion con la que se lee el archivocon el formato que sea guardado
    // y se devuelve la lista con los productos.
    public static List<Producto> leer() throws IOException {
        LinkedList<Producto> lista = new LinkedList<>();

        // Estas son las varibles para leer en el archivo
        FileInputStream archivoR = new FileInputStream(path);
        DataInputStream lector = new DataInputStream(archivoR);

        // Bucle para leer en el archivo y
        // ademas si el archivo esta en blanco no hacer el bucle
        boolean eof = (path.length() == 0);
        while (!eof) {
            String codigo = null, nombre = null;
            int cantidad = 0;
            double precio = 0;
            try {
                codigo = lector.readUTF();
                nombre = lector.readUTF();
                cantidad = lector.readInt();
                precio = lector.readDouble();
                lista.add(new Producto(codigo, nombre, cantidad, precio));
            } catch (EOFException e) {
                eof = true;
            }

        }

        // Cerrando varibles de lectura del archivo
        lector.close();
        archivoR.close();

        // Devuelve la coleccion.
        return lista;
    }

    // Esta es la funcion con la que guardo la lista de productos 
    // en el archivo
    public  static void guardar(LinkedList<Producto> productos) throws IOException {
        // Para este caso perfiero el iterator en lugar de usar directamente 
        // la lista
        Iterator<Producto>  productosIterator = productos.iterator();

        // Estas son las varibles para escribir en el archivo
        FileOutputStream archivoW = new FileOutputStream(path);
        DataOutputStream writer = new DataOutputStream(archivoW);

        // Este es el bucle que he usado para que escribir la lista 
        // en el archivo con el mismo formato que la funcion de leer
        while (productosIterator.hasNext()) {
            Producto p = productosIterator.next();
            writer.writeUTF(p.getCodigo());
            writer.writeUTF(p.getNombre());
            writer.writeInt(p.getCantidad());
            writer.writeDouble(p.getPrecio());
        }

        // Cerrando las variables pos is no se finalizan correctamente
        writer.close();
        archivoW.close();
    }
}
