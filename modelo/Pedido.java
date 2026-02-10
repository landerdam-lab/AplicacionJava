
import java.sql.Date;

public class Pedido {
    
    private int idPedido;
    private double precioTotal;
    private boolean montaje;
    private Date fecha;
    private boolean pagado; // 1. NUEVO CAMPO

    // 2. CONSTRUCTOR ACTUALIZADO (Añadimos 'boolean pagado' al final)
    public Pedido(int idPedido, double precioTotal, boolean montaje, Date fecha, boolean pagado) {
        this.idPedido = idPedido;
        this.precioTotal = precioTotal;
        this.montaje = montaje;
        this.fecha = fecha;
        this.pagado = pagado;
    }

    // --- MÉTODOS DE VISUALIZACIÓN (Para la tabla) ---

    public String getTipoPedido() {
        return montaje ? "PC Configurado (+Montaje)" : "Componentes Sueltos";
    }

    // Nuevo método para que la tabla muestre texto legible
    public String getEstadoPago() {
        return pagado ? "PAGADO" : "PENDIENTE";
    }

    // --- GETTERS Y SETTERS ---

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public boolean isMontaje() {
        return montaje;
    }

    public void setMontaje(boolean montaje) {
        this.montaje = montaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    // 3. NUEVOS GETTER Y SETTER PARA PAGADO
    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }
}