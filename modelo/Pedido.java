
import java.sql.Date;

public class Pedido {
    private int idPedido;
    private double precioTotal;
    private boolean montaje;
    private Date fecha;
    
    public Pedido(int idPedido, double precioTotal, boolean montaje, Date fecha) {
        this.idPedido = idPedido;
        this.precioTotal = precioTotal;
        this.montaje = montaje;
        this.fecha = fecha;
    }

    public int getIdPedido() { return idPedido; }
    public double getPrecioTotal() { return precioTotal; }
    public boolean isMontaje() { return montaje; }
    public Date getFecha() { return fecha; }
    
    // Para mostrar texto amigable si tiene montaje o no
    public String getTipoPedido() {
        return montaje ? "PC Configurado (+Montaje)" : "Componentes Sueltos";
    }
}