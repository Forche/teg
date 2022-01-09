package proceso;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import objetos.Pais;

public class Atacador {

	public static void ejecutarAtaque(Pais atacante, Pais defensor) {
		Integer dadosAtaque = getDadosAtaque(atacante);
		Integer dadosDefensa = getDadosDefensa(defensor);
		List<Integer> valoresAtaque = roll(dadosAtaque);
		List<Integer> valoresDefensa = roll(dadosDefensa);
		Integer asd = dadosAtaque > dadosDefensa ? dadosDefensa : dadosAtaque;
		for (int i = 0; i < asd; i++) {
			Integer valorAtaque = valoresAtaque.get(i);
			Integer valorDefensa = valoresDefensa.get(i);
			System.out.println("Dado ataque: " + valorAtaque + ". Dado defensa: " + valorDefensa);
			if (valorAtaque > valorDefensa) {
				defensor.addFichas(-1);
				System.out.println(defensor.getNombre() + " perdio una ficha");
			} else {
				atacante.addFichas(-1);
				System.out.println(atacante.getNombre() + " perdio una ficha");
			}
			if (defensor.getFichas() == 0) {
				atacante.getJugador().conquistar(defensor);
				System.out.println(defensor.getNombre() + " conquistado por " + defensor.getJugador().getColor());
				agrupar(atacante, defensor);
				MazoCartas.getInstance().setPuedeRobar(true);
			}
		}
	}

	private static void agrupar(Pais atacante, Pais defensor) {
		System.out.println("Cuantas fichas pasa a " + defensor.getNombre() + "?" );
		Scanner sc = new Scanner(System.in);
		Integer fichas = sc.nextInt() ;
		atacante.addFichas((fichas + 1) * -1);
		defensor.addFichas(fichas);
	}

	private static List<Integer> roll(Integer dadosAtaque) {
		List<Integer> list = new ArrayList();
		for (int i = 0; i < dadosAtaque; i++) {
			Random random = new Random();
			Integer val = random.nextInt(6) + 1;
			list.add(dadosAtaque);
		}
		list.sort(Comparator.reverseOrder());
		return list;
	}

	private static Integer getDadosDefensa(Pais atacante) {
		Integer fichas = atacante.getFichas();
		if (fichas > 3) {
			return 3;
		} else {
			return fichas;
		}
	}

	private static Integer getDadosAtaque(Pais atacante) {
		Integer fichas = atacante.getFichas();
		if (fichas > 3) {
			return 3;
		} else {
			return fichas - 1;
		}
	}
}
