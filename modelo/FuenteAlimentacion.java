import java.awt.Image;

public class FuenteAlimentacion extends Componente{

	protected String certificacionEnergetica;
	protected String potencia;
	
	
	public FuenteAlimentacion(String descripcion, String nombre, Image imagen, int stock,
			String certificacionEnergetica, String potencia) {
		super(descripcion, nombre, imagen, stock);
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
