import java.awt.Image;

public class Procesador extends Componente {

	protected String numNucleos;
	protected String frecuenciaBase;
	
	
	public Procesador(int idComponente, String descripcion, String nombre, Image imagen, int stock, int idMarca,
			double precioVenta, String numNucleos, String frecuenciaBase) {
		super(idComponente, descripcion, nombre, imagen, stock, idMarca, precioVenta);
		this.numNucleos = numNucleos;
		this.frecuenciaBase = frecuenciaBase;
	}


	public String getNumNucleos() {
		return numNucleos;
	}


	public void setNumNucleos(String numNucleos) {
		this.numNucleos = numNucleos;
	}


	public String getFrecuenciaBase() {
		return frecuenciaBase;
	}


	public void setFrecuenciaBase(String frecuenciaBase) {
		this.frecuenciaBase = frecuenciaBase;
	}
	
	
	
	
}
