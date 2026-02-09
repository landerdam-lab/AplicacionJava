import java.awt.Image;

public class FuenteAlimentacion extends Componente{

	protected String certificacionEnergetica;
	protected String potencia;
	
	
	public FuenteAlimentacion(int idComponente, String descripcion, String nombre, Image imagen, int stock, int idMarca,
			double precioVenta, String certificacionEnergetica, String potencia) {
		super(idComponente, descripcion, nombre, imagen, stock, idMarca, precioVenta);
		this.certificacionEnergetica = certificacionEnergetica;
		this.potencia = potencia;
	}


	public String getCertificacionEnergetica() {
		return certificacionEnergetica;
	}


	public void setCertificacionEnergetica(String certificacionEnergetica) {
		this.certificacionEnergetica = certificacionEnergetica;
	}


	public String getPotencia() {
		return potencia;
	}


	public void setPotencia(String potencia) {
		this.potencia = potencia;
	}
	
	
	
	
	
}
