package objetos;

import java.util.List;
import java.util.stream.Collectors;

import proceso.Atacador;

public class Pais {

	private String nombre;
	private Jugador jugador;
	private List<Pais> limitrofes;
	private Integer fichas;
	private String prefix;

	public Pais(String nombre) {
		this.nombre = nombre;
		this.prefix = nombre.substring(0, 2);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Jugador getJugador() {
		return this.jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public List<Pais> getLimitrofes() {
		return limitrofes;
	}

	public void setLimitrofes(List<Pais> limitrofes) {
		this.limitrofes = limitrofes;
	}

	public Integer getFichas() {
		return this.fichas;
	}

	public void setFichas(Integer fichas) {
		this.fichas = fichas;
	}

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void conquistado(Jugador jugador) {
		if (this.getJugador() != null) {
			this.getJugador().removePais(this);
		}
		setJugador(jugador);
		setFichas(1);
	}

	public void addFichas(Integer fichas) {
		this.fichas += fichas;
	}

	public void print() {
		System.out.println("Pais: " + this.nombre + ". Fichas: " + this.fichas);
	}

	public void printLimitrofesEnemigos() {
		List<Pais> limitrofesParaAtacar = this.getLimitrofesEnemigos();
		if (!limitrofesParaAtacar.isEmpty()) {
			System.out.println(this.getNombre() + " (" + this.getFichas() + ") puede atacar a:");
			limitrofesParaAtacar.forEach(limitrofe -> System.out.println("Limitrofe: " + limitrofe.getNombre()
					+ ". Fichas: " + limitrofe.getFichas() + ". Pertenece a:" + limitrofe.getJugador().getColor()));
		}
	}

	public List<Pais> getLimitrofesEnemigos() {
		return this.getLimitrofes().stream().filter(limitrofe -> !limitrofe.getJugador().equals(this.getJugador()))
				.collect(Collectors.toList());
	}

	public void printLimitrofesPropios() {
		List<Pais> limitrofes = this.getLimitrofesPropios();
		if (!limitrofes.isEmpty()) {
			System.out.println(this.getNombre() + " (" + this.getFichas() + ") limita con:");
			limitrofes.forEach(limitrofe -> System.out.println("Limitrofe: " + limitrofe.getNombre() + ". Fichas: "
					+ limitrofe.getFichas() + ". Pertenece a:" + limitrofe.getJugador().getColor()));
		}
	}

	public List<Pais> getLimitrofesPropios() {
		return this.getLimitrofes().stream().filter(limitrofe -> limitrofe.getJugador().equals(this.getJugador()))
				.collect(Collectors.toList());
	}

	public void atacarA(Pais defensor) {
		this.print();
		System.out.println(" ataca a ");
		defensor.print();
		Atacador.ejecutarAtaque(this, defensor);
	}

}
