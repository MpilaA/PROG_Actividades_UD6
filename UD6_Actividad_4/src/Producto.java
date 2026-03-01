import java.io.Serial;
import java.io.Serializable;

public class Producto implements Serializable {

    @Serial
    private static final long serialVersionUID = 4397837284138629127L;

    // Atributos del objeto
    private String referencia;
    private String descripcion;
    private String tipo;
    private int cantidad;
    private double precio;
    private int descuento;
    private int iva;
    private boolean aplicarDto;

    // Constructor del objeto completo
    public Producto(String referencia, String descripcion, String tipo, int cantidad, double precio, int descuento, int iva, boolean aplicarDto) {
        this.referencia = referencia;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descuento = descuento;
        this.iva = iva;
        this.aplicarDto = aplicarDto;
    }

    // Constructor del objeto para la generacion de objetos
    public Producto(String referencia, String descripcion, String tipo) {
        this.referencia = referencia;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    // Getters del objeto
    public String getReferencia() {
        return referencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public int getDescuento() {
        return descuento;
    }

    public int getIVA() {
        return iva;
    }

    public boolean isAplicarDto() {
        return aplicarDto;
    }

    // Setters del objeto
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public void setIVA(int iva) {
        this.iva = iva;
    }

    public void setAplicarDto(boolean aplicarDto) {
        this.aplicarDto = aplicarDto;
    }

    // Metodos modificados necesarios para el ejercicio
    @Override
    public String toString() {
        return "Producto{" +
                "referencia='" + referencia + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", tipo='" + tipo + '\'' +
                ", cantidad=" + cantidad +
                ", precio=" + precio +
                ", descuento=" + descuento +
                ", iva=" + iva +
                ", aplicarDto=" + aplicarDto +
                '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((referencia == null) ? 0 : referencia.hashCode());
        result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
        result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
        result = prime * result + cantidad;
        long temp;
        temp = Double.doubleToLongBits(precio);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + descuento;
        result = prime * result + iva;
        result = prime * result + (aplicarDto ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Producto other = (Producto) obj;
        if (referencia == null) {
            if (other.referencia != null)
                return false;
        } else if (!referencia.equals(other.referencia))
            return false;
        if (descripcion == null) {
            if (other.descripcion != null)
                return false;
        } else if (!descripcion.equals(other.descripcion))
            return false;
        if (tipo == null) {
            if (other.tipo != null)
                return false;
        } else if (!tipo.equals(other.tipo))
            return false;
        if (cantidad != other.cantidad)
            return false;
        if (Double.doubleToLongBits(precio) != Double.doubleToLongBits(other.precio))
            return false;
        if (descuento != other.descuento)
            return false;
        if (iva != other.iva)
            return false;
        if (aplicarDto != other.aplicarDto)
            return false;
        return true;
    }

    
}
