package proceso;

import java.util.List;
import java.util.Scanner;

import objetos.Carta;
import objetos.Jugador;
import objetos.Pais;

public class ManejadorTurno {

	public static void proximoMovimiento(Jugador jugador) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Jugador: " + jugador.getColor()
				+ " \n 1 - Agregar tropas \n 2 - Atacar \n 3 - Robar carta \n 4 - Reagrupar \n 5 - Listar paises propios \n 6 - Listar cartas \n 7 - Terminar turno");
		Integer nextAccion = sc.nextInt();
		if (nextAccion == 1) {
			agregarTropas(jugador);
			proximoMovimiento(jugador);
		} else if (nextAccion == 2) {
			atacar(jugador);
			proximoMovimiento(jugador);
		} else if (nextAccion == 3) {
			robarCarta(jugador);
			proximoMovimiento(jugador);
		} else if (nextAccion == 4) {
			reagrupar(jugador);
			proximoMovimiento(jugador);
		} else if (nextAccion == 5) {
			jugador.printPaises();
			proximoMovimiento(jugador);
		} else if (nextAccion == 6) {
			jugador.printCartas();
			proximoMovimiento(jugador);
		} else if (nextAccion == 7) {
			terminarTurno(jugador);
		} else {
			return;
		}
	}

	private static void terminarTurno(Jugador jugador) {
		MazoCartas.getInstance().setPuedeRobar(false);
	}

	private static void reagrupar(Jugador jugador) {
		List<Pais> paises = jugador.getPaises();
		paises.forEach(Pais::printLimitrofesPropios);
		Scanner sc = new Scanner(System.in);
		System.out.println("Elegi pais desde mandas tropas");
		String atacante = sc.next();
		System.out.println("Elegi pais que recibe tropas");
		String defensor = sc.next();
		System.out.println("Elegi cuantas tropas");
		Integer cantidadTropas = sc.nextInt();
		Pais paisAtacante = getPaisByPrefix(paises, atacante);
		Pais paisDefensor = getPaisByPrefix(paisAtacante.getLimitrofesEnemigos(), defensor);
		paisAtacante.addFichas(cantidadTropas * -1);
		paisDefensor.addFichas(cantidadTropas);
	}

	private static void robarCarta(Jugador jugador) {
		if (MazoCartas.getInstance().getPuedeRobar()) {
			Carta carta = MazoCartas.getInstance().robarCarta();
			jugador.addCarta(carta);
		} else {
			System.out.println("No conquisto ningun pais en el turno, no puede robar carta.");
		}
	}

	private static void atacar(Jugador jugador) {
		List<Pais> paises = jugador.getPaises();
		paises.forEach(Pais::printLimitrofesEnemigos);
		Scanner sc = new Scanner(System.in);
		System.out.println("Elegi pais desde donde atacas");
		String atacante = sc.next();
		System.out.println("Elegi pais que atacas");
		String defensor = sc.next();
		Pais paisAtacante = getPaisByPrefix(paises, atacante);
		Pais paisDefensor = getPaisByPrefix(paisAtacante.getLimitrofesEnemigos(), defensor);
		paisAtacante.atacarA(paisDefensor);
	}

	private static void agregarTropas(Jugador jugador) {
		Integer cantidadPaises = jugador.getPaises().size();
		Integer fichasAAgregar = cantidadPaises % 2;
		agregarTropas(jugador, fichasAAgregar);
	}

	public static void agregarTropas(Jugador jugador, Integer cantidad) {
		jugador.printPaises();
		Scanner sc = new Scanner(System.in);
		String paisElejido = sc.next();
		Pais pais = getPaisByPrefix(jugador.getPaises(), paisElejido);
		System.out.println("Cantidad de fichas para " + pais.getNombre() + ":");
		Integer cantidadParaPais = sc.nextInt();
		pais.addFichas(cantidadParaPais);
		Integer cantidadFichasRestantes = cantidad - cantidadParaPais;
		if (cantidadFichasRestantes > 0) {
			agregarTropas(jugador, cantidadFichasRestantes);
		}
	}
	
	private static Pais getPaisByPrefix(List<Pais> paises, String prefix) {
		return paises.stream().filter(pais -> pais.getPrefix().equalsIgnoreCase(prefix)).findFirst().get();
	}

}
