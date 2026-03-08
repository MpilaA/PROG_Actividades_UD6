import java.time.format.DateTimeFormatter;

public class Common {
    /* Estos es una clase donde guardo las cosas comunes entre las diferentes clases.
    Esto es para no guardarlas junto a la calse que tiene el metodo main. */
    public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final String dtf_pattern = "[0-9]{2}-[0-9]{2}-[0-9]{4}";
    public static final String CIF_pattern = "[A-Z]{1}[0-9]{8}";
}