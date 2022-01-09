package objetos;

public class Carta {

	private SimboloEnum simbolo;
	private Pais pais;

	public Carta(Pais pais, SimboloEnum simbolo) {
		this.pais = pais;
		this.simbolo = simbolo;
	}

	public SimboloEnum getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(SimboloEnum simbolo) {
		this.simbolo = simbolo;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}
	
	public void print() {
		System.out.println("Carta del pais: " + pais.getNombre() + ". Simbolo: " + simbolo.name());
	}
}
