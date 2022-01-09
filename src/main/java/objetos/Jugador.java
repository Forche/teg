package objetos;

import java.util.ArrayList;
import java.util.List;

public class Jugador {

	private String color;
	private List<Pais> paises;
	private List<Carta> cartas;
	private String objetivo;

	public Jugador(String color) {
		this.color = color;
		this.paises = new ArrayList();
		this.cartas = new ArrayList();
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<Pais> getPaises() {
		return paises;
	}

	public void setPaises(List<Pais> paises) {
		this.paises = paises;
	}

	public List<Carta> getCartas() {
		return cartas;
	}

	public void setCartas(List<Carta> cartas) {
		this.cartas = cartas;
	}

	public void addCarta(Carta carta) {
		if (this.cartas == null) {
			this.cartas = new ArrayList<>();
		}
		this.cartas.add(carta);
	}

	public void conquistar(Pais pais) {
		if (this.paises == null) {
			paises = new ArrayList<>();
		}
		paises.add(pais);
		pais.conquistado(this);
	}

	public void removePais(Pais pais) {
		this.paises.remove(pais);
	}

	public void printPaises() {
		this.getPaises().stream().forEach(Pais::print);
	}

	public void printCartas() {
		this.getCartas().stream().forEach(Carta::print);
	}

	public Pais getPaisByPrefix(String prefix) {
		return this.paises.stream().filter(pais -> pais.getPrefix().equals(prefix)).findFirst().get();
	}
}
