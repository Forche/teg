package proceso;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.stream.Collectors;

import objetos.Jugador;
import objetos.Pais;

public class Inicializar {

	List<Pais> paises = new ArrayList();
	List<Jugador> jugadores = new ArrayList<>();
	private static Inicializar init = new Inicializar();
	
	private Inicializar() {
		
	}

	public static Inicializar getInstance() {
		return init;
	}
	public static void main(String[] args) throws IOException {
		init.loadPaises();
		init.loadJugadores("negro", "rojo");
		init.setPaisesToJugadores();
		MazoCartas.getInstance().loadCartas();

		init.jugadores.forEach(jugador -> ManejadorTurno.agregarTropas(jugador, 5));
		init.jugadores.forEach(jugador -> ManejadorTurno.agregarTropas(jugador, 3));

		boolean objetivoNoCumplido = true;
		boolean noEsPrimerTurno = false;
		for (int i = 0; i < init.jugadores.size(); i++) {
			Jugador jugador = init.jugadores.get(i);
			if(noEsPrimerTurno) {
				ManejadorTurno.agregarTropas(jugador, jugador.getPaises().size() / 2);
			} else {
				noEsPrimerTurno = true;
			}
			ManejadorTurno.proximoMovimiento(jugador);
			if(init.paises.size() == jugador.getPaises().size()) {
				System.out.println("Jugador " + jugador.getColor() + " ganador.");
				return;
			}
			if ((i == init.jugadores.size() - 1) && objetivoNoCumplido) {
				i = -1;
			}
		}
	}

	private void loadJugadores(String... colores) {
		for (String color : colores) {
			Jugador jugador = new Jugador(color);
			this.jugadores.add(jugador);
		}
	}

	private void setPaisesToJugadores() {
		for (int i = 0; i < paises.size(); i++) {
			Integer indexJugador = (i + jugadores.size()) % jugadores.size();
			Jugador jugador = this.jugadores.get(indexJugador);
			Pais pais = paises.get(i);
			jugador.conquistar(pais);
		}
	}

	public void loadPaises() throws IOException {
		Properties prop = new Properties();
		InputStream input = new FileInputStream(
				getClass().getClassLoader().getResource("limitrofes.properties").getFile());
		prop.load(input);

		prop.entrySet().forEach(this::createPaises);
	}

	private void createPaises(Entry<Object, Object> propiedad) {
		String paisNombre = propiedad.getKey().toString();
		String[] limitrofesNombres = propiedad.getValue().toString().split("-");

		Pais pais = getPaisOrCreate(paisNombre);
		pais.setLimitrofes(getLimitrofes(limitrofesNombres));
	}

	private List<Pais> getLimitrofes(String[] limitrofesNombres) {
		List<Pais> limitrofes = new ArrayList<>();
		for (String limitrofeNombre : limitrofesNombres) {
			Pais limitrofe = getPaisOrCreate(limitrofeNombre);
			limitrofes.add(limitrofe);
		}
		return limitrofes;
	}

	private Pais getPaisOrCreate(String nombre) {
		if (paises.isEmpty()) {
			return createPaisAndAdd(nombre);
		} else {
			List<Pais> pais = paises.stream().filter(paisCreado -> paisCreado.getNombre().equals(nombre))
					.collect(Collectors.toList());
			if (pais.isEmpty()) {
				return createPaisAndAdd(nombre);
			} else {
				return pais.get(0);
			}
		}
	}

	private Pais createPaisAndAdd(String nombre) {
		Pais pais = new Pais(nombre);
		paises.add(pais);
		return pais;
	}

}