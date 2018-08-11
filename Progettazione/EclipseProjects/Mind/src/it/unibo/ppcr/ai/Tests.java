package it.unibo.ppcr.ai;

public class Tests {
	public static void main(String[] args) {
		PPCR.initForTests(4,4);

		// Test di un ostacolo inevitabile: griglia 4x4 con ostacoli su 0,1 1,1 2,1 3,1
		//impassableExample();
		
		//Test di un reset: mando alcuni comandi in una griglia 4x4 e poi resetto e ricomincio
		//resetExample();
		
		//Test di una keyword errata mandata alla mind
		//wrongKeywordExample();

		// BFS TEST:
		// Primo esempio: griglia 4x4 con test dell'ostacolo in 3,1
		//firstExample();

		// Secondo esempio: griglia 4x4 con test dell'ostacolo in 1,1
		//secondExample();

		// Terzo esempio: griglia 4x4 con test su ostacolo 3,1+1,1
		// Il primo è mobile, il secondo è fisso
		// thirdExample();

		// ASTAR TEST:
		// Quarto esempio: terzo esempio + path sgombro per arrivare alla posizione
		// finale (nessun ostacolo mobile rilevato)
		//fourthExample();

		// Quinto esempio: terzo esempio + ostacolo mobile trovato in 3,2
		fifthExample();

	}

	private static void wrongKeywordExample() {
		System.out.println(PPCR.getNextMove("eh le cose"));		
	}

	private static void resetExample() {
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		
		System.out.println(PPCR.getNextMove("reset"));
		
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		
	}

	private static void impassableExample() {
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));

		// ROBOT: clear (e sono in 3,0)
		// MIND: vai verso 3,1
		System.out.println(PPCR.getNextMove("clear"));

		// ROBOT: obstacle sulla strada da 3,0 a 3,1
		// MIND: torna in 3,0
		System.out.println(PPCR.getNextMove("obstacle"));

		// ROBOT: clear (e sono in 3,0)
		// MIND: aspetta un tot di tempo e poi torna di nuovo in 3,1 per vedere se
		// l'ostacolo c'è ancora
		System.out.println(PPCR.getNextMove("clear"));

		// ROBOT: obstacle di nuovo in 3,1
		// MIND: marca 3,1 come ostacolo e torna indietro
		System.out.println(PPCR.getNextMove("obstacle"));

		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));

		// ROBOT: clear (e sono in 2,0)
		// MIND: vai verso 2,1
		System.out.println(PPCR.getNextMove("clear"));

		// ROBOT: obstacle sulla strada da 2,0 a 2,1
		// MIND: torna in 2,0
		System.out.println(PPCR.getNextMove("obstacle"));

		// ROBOT: clear (e sono in 2,0)
		// MIND: aspetta un tot di tempo e poi torna di nuovo in 2,1 per vedere se
		// l'ostacolo c'è ancora
		System.out.println(PPCR.getNextMove("clear"));

		// ROBOT: obstacle di nuovo in 2,1
		// MIND: marca 2,1 come ostacolo e torna indietro
		System.out.println(PPCR.getNextMove("obstacle"));

		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));

		// ROBOT: clear (e sono in 1,0)
		// MIND: vai verso 1,1
		System.out.println(PPCR.getNextMove("clear"));

		// ROBOT: obstacle sulla strada da 1,0 a 1,1
		// MIND: torna in 1,0
		System.out.println(PPCR.getNextMove("obstacle"));

		// ROBOT: clear (e sono in 1,0)
		// MIND: aspetta un tot di tempo e poi torna di nuovo in 1,1 per vedere se
		// l'ostacolo c'è ancora
		System.out.println(PPCR.getNextMove("clear"));

		// ROBOT: obstacle di nuovo in 1,1
		// MIND: marca 1,1 come ostacolo e torna indietro
		System.out.println(PPCR.getNextMove("obstacle"));

		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));

		// ROBOT: clear (e sono in 0,0)
		// MIND: vai verso 0,1
		System.out.println(PPCR.getNextMove("clear"));

		// ROBOT: obstacle sulla strada da 0,0 a 0,1
		// MIND: torna in 0,0
		System.out.println(PPCR.getNextMove("obstacle"));

		// ROBOT: clear (e sono in 0,0)
		// MIND: aspetta un tot di tempo e poi torna di nuovo in 0,1 per vedere se
		// l'ostacolo c'è ancora
		System.out.println(PPCR.getNextMove("clear"));

		// ROBOT: obstacle di nuovo in 0,1
		// MIND: marca 0,1 come ostacolo e torna indietro
		System.out.println(PPCR.getNextMove("obstacle"));
		
		// ROBOT: sono in 0,0
		// Mind: ok ma non ci sono più strade da percorrere: c'è per forza un ostacolo inevitabile!
		System.out.println(PPCR.getNextMove("clear"));

	}

	private static void fifthExample() {
		thirdExample();

		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));

		// ROBOT: obstacle sulla strada da 2,2 a 3,2
		// MIND: torna in 2,2
		System.out.println(PPCR.getNextMove("obstacle"));

		// ROBOT: clear (e sono in 2,2)
		// MIND: aspetta un tot di tempo e poi torna di nuovo in 3,2 per vedere se
		// l'ostacolo c'è ancora
		System.out.println(PPCR.getNextMove("clear"));

		// ROBOT: obstacle trovato ancora sulla strada da 2,2 a 3,2
		// MIND: torna in 2,2
		System.out.println(PPCR.getNextMove("obstacle"));

		// ROBOT: clear (e sono in 2,2)
		// MIND: aspetta un tot di tempo e poi torna di nuovo in 3,2 per vedere se
		// l'ostacolo c'è ancora
		System.out.println(PPCR.getNextMove("clear"));

		// ROBOT: finalmente in 3,2 l'ostacolo non c'è più!
		// MIND: perfetto, continua normalmente
		System.out.println(PPCR.getNextMove("clear"));

		// END
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));

	}

	private static void fourthExample() {
		thirdExample();

		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
	}

	private static void thirdExample() {
		// INIT
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));

		// ROBOT: clear (e sono in 3,0)
		// MIND: vai verso 3,1
		System.out.println(PPCR.getNextMove("clear"));

		// ROBOT: obstacle sulla strada da 3,0 a 3,1
		// MIND: torna in 3,0
		System.out.println(PPCR.getNextMove("obstacle"));

		// ROBOT: clear (e sono in 3,0)
		// MIND: aspetta un tot di tempo e poi torna di nuovo in 3,1 per vedere se
		// l'ostacolo c'è ancora
		System.out.println(PPCR.getNextMove("clear"));

		// CONSIDERO CASO IN CUI OSTACOLO IN 3,1 E' MOBILE!
		// ROBOT: obstacle non c'è più in 3,1
		// MIND: bene, continuo normalemente con l'algoritmo!
		System.out.println(PPCR.getNextMove("clear"));

		// PASSI IN MEZZO PRIMA DI ARRIVARE AL SECONDO OSTACOLO
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));

		// ROBOT: clear (e sono in 0,1)
		// MIND: vai verso 1,1
		System.out.println(PPCR.getNextMove("clear"));

		// ROBOT: obstacle sulla strada da 0,1 a 1,1
		// MIND: torna in 0,1
		System.out.println(PPCR.getNextMove("obstacle"));

		// ROBOT: clear (e sono in 0,1)
		// MIND: aspetta un tot di tempo e poi torna di nuovo in 1,1 per vedere se
		// l'ostacolo c'è ancora
		System.out.println(PPCR.getNextMove("clear"));

		// CONSIDERO CASO IN CUI OSTACOLO IN 1,1 E' FISSO!
		// ROBOT: obstacle di nuovo in 1,1
		// MIND: marca 1,1 come ostacolo e torna indietro
		System.out.println(PPCR.getNextMove("obstacle"));

		// ROBOT: clear su 0,1
		// MIND: ti dirò di andare altrove a seconda dell'algoritmo, ma sicuro non in
		// 1,1
		System.out.println(PPCR.getNextMove("clear"));

		// END
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));

	}

	private static void secondExample() {
		// INIT
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));

		// ROBOT: clear (e sono in 0,1)
		// MIND: vai verso 1,1
		System.out.println(PPCR.getNextMove("clear"));

		// ROBOT: obstacle sulla strada da 0,1 a 1,1
		// MIND: torna in 0,1
		System.out.println(PPCR.getNextMove("obstacle"));

		// ROBOT: clear (e sono in 0,1)
		// MIND: aspetta un tot di tempo e poi torna di nuovo in 1,1 per vedere se
		// l'ostacolo c'è ancora
		System.out.println(PPCR.getNextMove("clear"));

		// CONSIDERO IL CASO IN CUI:
		// L'ostacolo c'è ancora: è fisso quindi!
		// ROBOT: obstacle di nuovo in 1,1
		// MIND: marca 1,1 come ostacolo e torna indietro
		System.out.println(PPCR.getNextMove("obstacle"));

		// ROBOT: clear su 0,1
		// MIND: ti dirò di andare altrove a seconda dell'algoritmo, ma sicuro non in
		// 1,1
		System.out.println(PPCR.getNextMove("clear"));

		// END
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));

	}

	private static void firstExample() {
		// INIT
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));

		// ROBOT: clear (e sono in 3,0)
		// MIND: vai verso 3,1
		System.out.println(PPCR.getNextMove("clear"));

		// ROBOT: obstacle sulla strada da 3,0 a 3,1
		// MIND: torna in 3,0
		System.out.println(PPCR.getNextMove("obstacle"));

		// ROBOT: clear (e sono in 3,0)
		// MIND: aspetta un tot di tempo e poi torna di nuovo in 3,1 per vedere se
		// l'ostacolo c'è ancora
		System.out.println(PPCR.getNextMove("clear"));

		// DUE CASI (eseguire uno dei due):
		// 1) L'ostacolo c'è ancora: è fisso quindi!
		firstExampleFirstCase();
		// 2) L'ostacolo non c'è più: era mobile!
		// firstExampleSecondCase();

		// END
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));
		System.out.println(PPCR.getNextMove("clear"));

	}

	public static void firstExampleFirstCase() {
		// ROBOT: obstacle di nuovo in 3,1
		// MIND: marca 3,1 come ostacolo e torna indietro
		System.out.println(PPCR.getNextMove("obstacle"));

		// ROBOT: clear su 3,0
		// MIND: ti dirò di andare altrove a seconda dell'algoritmo, ma sicuro non in
		// 3,1
		System.out.println(PPCR.getNextMove("clear"));
	}

	public static void firstExampleSecondCase() {
		// ROBOT: obstacle non c'è più in 3,1
		// MIND: bene, continuo normalmente con l'algoritmo!
		System.out.println(PPCR.getNextMove("clear"));
	}
}
