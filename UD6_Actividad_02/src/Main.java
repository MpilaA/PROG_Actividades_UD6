import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LinkedList<Producto> productos = new LinkedList<>();

        // En este try con recursos he puesto un file writer que es solo para 
        // crear el archivo en caso de que no este.
        try (FileWriter a = new FileWriter(Almacen.path, true)) {
            productos = new LinkedList<>(Almacen.leer());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // En este catch he decidido que por lo inutil que es sin 
            // poder usar el archivo, el programa termina.
            System.exit(0);
        }

        boolean salida = true;

        do {
            Scanner sc;

            // Mensje para ver las mostrar las opciones
            System.out.println("\n" +
                    "1. Crear producto\n" +
                    "2. Mostrar productos existentes\n" +
                    "3. Eliminar producto por código\n" +
                    "4. Guardar productos en el fichero.\n" +
                    "5. Salir");

            int opcion = 0; 

            try { // try para pedir un numero sin que de problemas
                System.out.print("Dame la opcion: ");
                sc = new Scanner(System.in);
                opcion = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Solo se admiten numeros.");
            }

            // boolean estado = true;
            String codigo = null;

            switch (opcion) { // Switch con las acciones de las opciones
                case 1: // Crear producto
                    do {
                        System.out.print("Dame el codigo: ");
                        sc = new Scanner(System.in);
                        codigo = sc.nextLine();
                    } while (codigo.isBlank());

                    String nombre;
                    do {
                        System.out.print("Dame el nombre: ");
                        sc = new Scanner(System.in);
                        nombre = sc.nextLine();
                    } while (nombre.isBlank());
                    // Los dos siguientes bucles son para pedir la 
                    // cantidad y el precio tienen un try catch por 
                    // si se introduce algo no valido y que lo vuelva
                    // pedir.
                    int cantidad = 0;
                    do {
                        System.out.print("Dame la cantidad: ");
                        sc = new Scanner(System.in);
                        try {
                            cantidad = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Solo numeros");
                        }
                    } while (cantidad == 0);

                    double precio = 0;
                    do {
                        System.out.print("Dame el precio: ");
                        sc = new Scanner(System.in);
                        try {
                            precio = sc.nextDouble();
                        } catch (InputMismatchException e) {
                            System.out.println("Solo numeros");
                        }
                    } while (precio == 0);

                    // Creacion del producto y añadido de este a la coleccion.
                    Producto producto = new Producto(codigo, nombre, cantidad, precio);
                    productos.add(producto);

                    break;
                case 2: // Mostrar productos existentes
                    for (Producto p : productos) {
                        System.out.println(p);
                    }
                    break;
                case 3: // Eliminar producto por código

                    do { // Bucle pidiendo el codigo del objeto a eliminar
                        System.out.println("Dame el codigo: ");
                        sc = new Scanner(System.in);
                        codigo = sc.nextLine();
                    } while (codigo.isBlank());

                    // Creacion del del iterator de productod y 
                    // bucle para eliminar el producto con el codigo 
                    // antes pedido
                    Iterator<Producto> productosIterator = productos.iterator();

                    boolean estado = false;

                    while (productosIterator.hasNext()) {
                        Producto p = productosIterator.next();
                        if (codigo.equals(p.getCodigo())) {
                            estado = true;
                            try { // Esto es por si no se puede borrar
                                productosIterator.remove();
                            } catch (Exception e) {
                                estado = false;
                            }
                            break;
                        }
                    }

                    // If con mensajes para indicar si sea borrado o no
                    if (estado) {
                        System.out.println("Sea borrado correctamente");
                    } else {
                        System.out.println("No sea podido borrar");
                    }

                    break;
                case 4: // Guardar productos en el fichero.
                    // Imprimo las excepciones para indicar si fallo
                    try { 
                        Almacen.guardar(productos);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5: // Salir
                    salida = false;
                    System.out.println("Saliendo");
                    break;
                default: // Caso de que no coincidad con ninguna de las opciones
                    System.out.println("Esa opcion es incorrecta.");
            }
        } while (salida);
    }
}