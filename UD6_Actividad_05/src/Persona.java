import java.time.LocalDate;

public class Persona {

    public static final String dniPattern = "[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]{1}";

    protected final String DNI;
    protected String nombre;
    protected LocalDate FechaNacimiento;
    protected String direccion;

    public Persona(String DNI, String nombre, LocalDate fechaNacimiento, String direccion) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.FechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
    }

    public String getDNI() {
        return DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaNacimiento() {
        return FechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "DNI='" + DNI + '\'' +
                ", nombre='" + nombre + '\'' +
                ", FechaNacimiento=" + FechaNacimiento +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
