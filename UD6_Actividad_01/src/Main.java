import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        // Variable para el Scanner
        Scanner sc;

        // Creacion de la lista vacia.
        LinkedList<Producto> productos = new LinkedList<>();
        // Try para imprimir mensajes si hay fallos al leer el
        try {
            productos = Almacen.leer();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // Vi que el programa es inutil si no se puede
            // ni leer ni escribir en el asi que indico que se cierre.
            System.exit(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            // Esto es en caso de que el archivo pueda este vacio y no con mal formato
            File path = new File(Almacen.path);
            if (path.length() != 0L) {
                System.out.println("Archivo con mal formato. \nPor favor reviselo.");
                // Vi que si el archivo esta mal no es buena idea sobreescribirlo asi
                // que indico que se cierre.
                System.exit(0);
            }
        }

        // Variable para poder salir del bucle y bucle para el menu
        boolean salida = true;

        do {
            // Mensaje para  ver las opciones del menu.
            System.out.println("\n" +
                    "1. Crear producto\n" +
                    "2. Mostrar productos existentes\n" +
                    "3. Eliminar producto por código\n" +
                    "4. Guardar productos en el fichero.\n" +
                    "5. Salir");

            int opcion = 0;
            try { // try para poder evitar la excepcion al no poner un nomero
                System.out.print("Dame la opcion: ");
                sc = new Scanner(System.in);
                opcion = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Solo se admiten numeros.");
            }

            // Como esta variable la necesito vacia varias veces la creo aqui
            String codigo = null;

            switch (opcion) {
                case 1: // Crear producto
                    // Pido el codigo de el producto
                    do { 
                        System.out.print("Dame el codigo: ");
                        sc = new Scanner(System.in);
                        codigo = sc.nextLine();
                    } while (codigo.isBlank());

                    // Pido el nombre del producto
                    String nombre;
                    do {
                        System.out.print("Dame el nombre: ");
                        sc = new Scanner(System.in);
                        nombre = sc.nextLine();
                    } while (nombre.isBlank());

                    // Pido la cantidad
                    int cantidad = 0;
                    do{
                        System.out.print("Dame la cantidad: ");
                        sc = new Scanner(System.in);
                        // Como es un numero capturo la excepcion de si no lo es
                        try {
                            cantidad = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Solo numeros");
                        }
                    }while (cantidad == 0);

                    // Pido el precio
                    double precio = 0;
                    do{
                        System.out.print("Dame el precio: ");
                        sc = new Scanner(System.in);
                        // Como es un numero capturo la excepcion de si no lo es
                        try {
                            precio = sc.nextDouble();
                        } catch (InputMismatchException e) {
                            System.out.println("Solo numeros");
                        }
                    }while (precio == 0);

                    // Creo el producto con lo anterior y lo meto en la lista
                    Producto producto = new Producto(codigo, nombre, cantidad, precio);
                    productos.add(producto);

                    break;
                case 2: // Mostrar productos existentes
                    for (Producto p: productos) {
                        System.out.println(p);
                    }
                    break;
                case 3: // Eliminar producto por código

                    do { // Bucle para pedir el codigo
                        System.out.println("Dame el codigo: ");
                        sc = new Scanner(System.in);
                        codigo = sc.nextLine();
                    } while (codigo.isBlank());

                    // Para eliminar el producto creo un iterator de la lista
                    Iterator<Producto> productosIterator = productos.iterator();

                    // Esto es para poder saber si sea borrado 
                    boolean estado = false;

                    // bucle que recorre el iterator
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

                    // If que imprime si se a borrado o no
                    if (estado) {
                        System.out.println("Sea borrado correctamente");
                    } else {
                        System.out.println("No sea podido borrar");
                    }

                    break;
                case 4: // Guardar productos en el fichero.
                    try {// Recoge las IOException para indicar si se puede escribir
                        Almacen.guardar(productos);
                    } catch (IOException e) {
                        System.out.println("No se puede escribir");
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