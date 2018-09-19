package it.unibo.hue.components;

//La classe Hue Client offre metodi generali per accendere/spegnere/blinkare una specifica lampada trovata (selezionata dal numero). 
//Nella nostra applicazione, utilizziamo invece questa classe come wrapper, sapendo che la hue lamp da 
//utilizzare sarà sempre una e di uno specifico numero (qua la 1).

public class hueClientSpecific {

	public static void on() {
		hueClient.on(1);
	}

	public static void off() {
		hueClient.off(1);
	}

	public static void blink() {
		hueClient.blink(1);
	}
	
	public static boolean testConnection()
	{
		return hueClient.testConnection(1);
	}
	
	// TEST
	public static void main(String args[]) {
		try {
			on();
			Thread.sleep(2000);
			off();
			Thread.sleep(2000);
			blink();
			Thread.sleep(10000);
			off();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}