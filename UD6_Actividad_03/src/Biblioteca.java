import java.io.*;
import java.util.*;


public class Biblioteca {

    // Atributo de la clase para los metodos
    private static final File path = new File("./Bibliteca.dat");

    // Atributo del objeto
    private HashMap<String, Libro> libros;

    // Constructor del objeto
    public Biblioteca() throws IOException, ClassNotFoundException {

        // Inicializacion de la lista
        this.libros = new HashMap<>();

        // Por ciertos porblemas si el archivo no existe o esta vacio solo se consultara
        // con FileWiter para crear el achivo y si existe no hacer nada.

        if (path.length() == 0) { // En caso de no existir o de estar en blanco
            FileWriter archivoW = new FileWriter(path, true);
            archivoW.close();
        } else { // En caso de existir y no estar en blanco
            // Variables para la lectura del archivo
            FileInputStream archivoR = new FileInputStream(path);
            ObjectInputStream lector = new ObjectInputStream(archivoR);

            // Variable para indicar el final del archivo
            boolean eof = false;

            // Bucle para volcar los libros almacendos en el archivo al HashMap
            while (!eof) {
                try {
                    Object l = lector.readObject();
                    if (l instanceof Libro) {
                        this.libros.put(((Libro) l).getISBN(), ((Libro) l));
                    }
                } catch (EOFException e) {
                    eof = true;
                }
            }

            // Cirre variables para la lectura del archivo en orden coerente
            lector.close();
            archivoR.close();
        }
    }

    // Metodo para añadir libros
    public void add(Libro l) {
        this.libros.put((l.getISBN()), l);
    }

    // Metodo para borrar libros del HashMap
    public boolean remove(String isbn) {
        Libro l = this.libros.remove(isbn);
        if (l != null){
            return true;
        } else {
            return false;
        }
    }

    // Metodo para obtener los libros ordenados
    public LinkedList<Libro> obtenerOrdenados(Comparator<Libro> c) {
        // Obtencio de la lista sin ordenar
        LinkedList<Libro> lista = new LinkedList<>(this.libros.values());

        lista.sort(c); // Ordenar la lista
        return lista; // Devolver lista ya ordenada
    }

    // Metodo para volcar los libros del HashMap al archivo
    public void guardar() throws IOException {
        // Variables para interactuar con el archivo.
        FileOutputStream archivoW = new FileOutputStream(path);
        ObjectOutputStream escritor = new ObjectOutputStream(archivoW);

        // Bucle para escribir los del HashMap libros solamente en el archivo
        for (Libro l:this.libros.values()) {
            escritor.writeObject(l);
        }

        // Cirre variables para la escritura del archivo en orden coerente
        escritor.close();
        archivoW.close();
    }

}
