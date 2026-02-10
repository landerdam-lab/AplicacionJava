
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

    
    
    public String getTipoPedido() {
        return montaje ? "PC Configurado (+Montaje)" : "Componentes Sueltos";
    }



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
}