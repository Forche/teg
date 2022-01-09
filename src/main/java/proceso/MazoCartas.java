package proceso;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;

import objetos.Carta;
import objetos.Pais;
import objetos.SimboloEnum;

public class MazoCartas {

	List<Carta> disponibles = new ArrayList();
	List<Carta> asignadas = new ArrayList();
	private Boolean puedeRobar = false;
	private static MazoCartas mazo = new MazoCartas();

	private MazoCartas() {
		// Constructor privado
	}

	public static MazoCartas getInstance() {
		return mazo;
	}

	public Boolean getPuedeRobar() {
		return puedeRobar;
	}

	public void setPuedeRobar(Boolean puedeRobar) {
		this.puedeRobar = puedeRobar;
	}

	public Carta robarCarta() {
		Carta carta = disponibles.remove(0);
		asignadas.add(carta);
		setPuedeRobar(false);
		return carta;
	}

	public void loadCartas() throws IOException {
		Properties prop = new Properties();
		InputStream input = new FileInputStream(
				getClass().getClassLoader().getResource("cartas.properties").getFile());
		prop.load(input);

		prop.entrySet().forEach(this::createCartas);
	}

	private void createCartas(Entry<Object, Object> propiedad) {
		String paisNombre = propiedad.getKey().toString();
		String simbolo = propiedad.getValue().toString();
		Pais pais = Inicializar.getInstance().paises.stream()
				.filter(paisLoaded -> paisLoaded.getNombre().equals(paisNombre)).findFirst().get();
		Carta carta = new Carta(pais, SimboloEnum.valueOf(simbolo));
		disponibles.add(carta);
	}

}
