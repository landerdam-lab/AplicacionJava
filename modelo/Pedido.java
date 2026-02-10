

import java.sql.Date;

public class Pedido {

	private int idPedido;
	private double precioTotal;
	private boolean montaje;
	private Date fecha;
	private boolean pagado;
	private int idCliente; 

	public Pedido(int idPedido, double precioTotal, boolean montaje, Date fecha, boolean pagado, int idCliente) {
		this.idPedido = idPedido;
		this.precioTotal = precioTotal;
		this.montaje = montaje;
		this.fecha = fecha;
		this.pagado = pagado;
		this.idCliente = idCliente; 
	}


	public String getTipoPedido() {
		return montaje ? "PC Configurado (+Montaje)" : "Componentes Sueltos";
	}

	public String getEstadoPago() {
		return pagado ? "PAGADO" : "PENDIENTE";
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

	public boolean isPagado() {
		return pagado; 
	}
	public void setPagado(boolean pagado) {
		this.pagado = pagado; 
	}

	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
}