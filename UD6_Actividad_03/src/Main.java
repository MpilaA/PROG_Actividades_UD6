import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Creacion de la biblioteca y extraccion de la informacion archivo.
        Biblioteca biblioteca = null;
        // Si el costructor lanza alguna de las excepciones decidi que el programa
        // es inutil asi que el prgrama termina en esos casos
        try {
            biblioteca = new Biblioteca();
        } catch (IOException e) {
            System.out.println("Archivo inaccesible");
            // e.printStackTrace();
            System.exit(0);
        } catch (ClassNotFoundException e) {
            System.out.println("Archivo corrupto");
            System.exit(0);
        }

        // Variable para finalizar el bucle
        boolean salida = false;

        do {
            Scanner sc;
            // Mensaje para ver las opciones
            System.out.println("1. Crear Libro y registrarlo en la Biblioteca\n" +
                    "2. Mostrar Libros ordenados\n" +
                    "3. Eliminar Libro\n" +
                    "4. Guardar Libros en el fichero\n" +
                    "5. Guardar y Salir");

            // Variable de la opcion.
            int opcion = 0;

            // Bucle que pregunta la opcion elegida
            while (opcion == 0) {
                System.out.print("Opcion: ");
                sc = new Scanner(System.in);
                // try para que no termine el bucle hasta que se le de un numero
                try {
                    opcion = sc.nextInt();
                } catch(InputMismatchException e){
                    System.out.println("Solo se admiten numeros");
                }
            }

            // Como esta variable esta en varios casos del switch la defino aqui
            String isbn;

            switch (opcion) {
                case 1: // Crear Libro y registrarlo en la Biblioteca (ISBN único)

                    isbn = null;
                    // Bucle para preguntar el ISBN
                    do {
                        System.out.print("Dame el ISBN: ");
                        sc = new Scanner(System.in);
                        isbn = sc.nextLine();
                    } while (isbn.isBlank());

                    // Vairable para guardar el titulo
                    String titulo = null;
                    // Bucle para preguntar el titulo
                    do {
                        System.out.print("Dame el titulo: ");
                        sc = new Scanner(System.in);
                        titulo = sc.nextLine();
                    } while (titulo.isBlank());

                    // Vairable para guardar el autor
                    String autor = null;
                    // Bucle para preguntar el autor
                    do {
                        System.out.print("Dame el autor: ");
                        sc = new Scanner(System.in);
                        autor = sc.nextLine();
                    } while (autor.isBlank());

                    // Vairable para guardar la fecha de Publicacion
                    LocalDate fecha = null;
                    // Bucle para preguntar la fecha con formato ISO
                    do {
                        // El try catch es para enviar un mensaje y que la fecha sea null
                        // en caso de no introducir una fecha en formato ISO
                        try{
                            System.out.print("Dame la fecha en formato ISO: ");
                            sc = new Scanner(System.in);
                            String fechaTexto = sc.nextLine();
                            fecha = LocalDate.parse(fechaTexto, DateTimeFormatter.ISO_DATE);
                        } catch(DateTimeParseException e) {
                            System.out.println("¡Por favor introduzca una fecha en formato ISO!");
                        }
                    } while (fecha == null);

                    // Creacion del Libro y añaddido a la biblioteca
                    biblioteca.add(new Libro(isbn, titulo, autor, fecha));
                    break;
                case 2: // Mostrar Libros existentes por (ISBN, titulo, Autor, Fecha)
                    // Creacion del array de libros que se va a usar en un
                    // switch dentro de este caso
                    LinkedList<Libro> libros = null;

                    // Reutilizo la variable de opcion ponidola otra vez en cero
                    opcion = 0;

                    // Mensaje con las opciones
                    System.out.println("Elige con que ordenar: \n" +
                    "1. ISBN \n2. Titulo \n3. Autor \n4. Fecha");

                    // Bucle que pregunta la opcion elegida
                    while (opcion == 0) {
                        System.out.print("Opcion: ");
                        sc = new Scanner(System.in);
                        // try para que no termine el bucle hasta que se le de un numero
                        try {
                            opcion = sc.nextInt();
                        } catch (InputMismatchException e){
                            System.out.println("Solo se aceptan numeros");
                        }
                    }

                    // Switch con que se extrae la lista con los libros ya
                    // ordenados segun lo que indique la opcion
                    switch (opcion) {
                        case 1:
                            libros = biblioteca.obtenerOrdenados(new ComparadorISBN());
                            break;
                        case 2:
                            libros = biblioteca.obtenerOrdenados(new ComparadorTitulo());
                            break;
                        case 3:
                            libros = biblioteca.obtenerOrdenados(new ComparadorAutor());
                            break;
                        case 4:
                            libros = biblioteca.obtenerOrdenados(new ComparadorFechaPublicacion());
                            break;
                        default:
                            // Mensaje po si se introduce un numero que no este en el menu
                            System.out.println("Eso no es una opcion valida");
                            break;
                    }

                    // Bucle para imprimir los libros de la lista ya ordenda en caso
                    // de ser null como al principio no se ejecuta el bucle.
                    if (libros != null) {
                        for (Libro l: libros) {
                            System.out.print(l + " \n");
                        }
                    }
                    break;
                case 3: // Eliminar Libro por ISBN
                    
                    isbn = null;
                    // Bucle para preguntar el ISBN
                    do {
                        System.out.print("Dame el ISBN: ");
                        sc = new Scanner(System.in);
                        isbn = sc.nextLine();
                    } while (isbn.isBlank());

                    // Variable para guardar el resultado de borrar el libro
                    boolean eliminado = biblioteca.remove(isbn);

                    // if con los mensajes cuando se a borrado y cando no.
                    if (eliminado){
                        System.out.println("Libro borrado correctamente");
                    } else {
                        System.out.println("El libro no existe");
                    }
                    break;
                case 4: // Guardar Libros en el fichero

                    System.out.println("Guardando");

                    try {
                        biblioteca.guardar();
                        System.out.println("Guardado");
                    } catch (IOException e) {
                        System.out.println("Error al escribir el archivo");
                    }

                    break;
                case 5: // Guardar y Salir

                    // Cambiar variable de salida para terminar el bucle
                    salida = true;

                    // Guardar la biblioteca en el archivo y saltar 
                    // unmensaje si algo sale mal
                    try {
                        biblioteca.guardar();
                    } catch (IOException e) {
                        System.out.println("Error al escribir el archivo");
                    }

                    break;
                default:
                    // Mensaje en caso de no ser un numero que este en el switch
                    System.out.println("Opcion no admitida.");
                    break;
            }

        } while (!salida);

    }
}