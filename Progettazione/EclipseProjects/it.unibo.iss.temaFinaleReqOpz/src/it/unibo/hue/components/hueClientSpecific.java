package it.unibo.hue.components;

import it.unibo.qactors.akka.QActor;

//La classe Hue Client offre metodi generali per accendere/spegnere/blinkare una specifica lampada trovata (selezionata dal numero). 
//Nella nostra applicazione, utilizziamo invece questa classe come wrapper, sapendo che la hue lamp da 
//utilizzare sarà sempre una e di uno specifico numero (qua la 1).

public class hueClientSpecific {

	public static void on(QActor myself) {
		hueClient.on(1);
	}

	public static void off(QActor myself) {
		hueClient.off(1);
	}

	public static void blink(QActor myself) {
		hueClient.blink(1);
	}
	
	public static boolean testConnection()
	{
		return hueClient.testConnection(1);
	}
	
	// TEST
	public static void main(String args[]) {
		try {
			on(null);
			Thread.sleep(2000);
			off(null);
			Thread.sleep(2000);
			blink(null);
			Thread.sleep(10000);
			off(null);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}