package workingFrontend;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import alice.tuprolog.SolveInfo;
import it.unibo.progCtx.MainProgCtx;
import it.unibo.qactors.QActorUtils;
import it.unibo.qactors.akka.QActor;

public class TestWorkingFrontend {

	/**
	 *  Ogni test seguente controlla se l'attore interessato contiene un determinato predicato Prolog.
	 *  Alcuni sono gia' stati implementati nel modello attuale, altri mancano: 
	 *  per ogni test, ho messo un TODO per indicare il predicato Prolog che serve.
	 *  Aggiunti i predicati Prolog, basta sostituirli nel ciclo while all'interno del metodo getActors.
	 *  Se vengono modificati i nomi degli attori, sostituire anche quelli mantenendo il suffisso _ctrl.
	 *    
	 *  
	 *  N.B.: AvoidFix e R-AvoidMobile sono intrinsecamente rispettati dalla strategia di IA. Quindi, non serve un test
	 *  
	 **/

	private TestActor frontend = null, mindOfRobot = null, realRobot = null, 
			virtualRobot = null, businessLogic = null, led = null, hue = null;

	@Before
	public void systemSetUp() throws Exception	{
		System.out.println("systemSetUp starts " );
		MainProgCtx.initTheContext();
		getActors();
	}

	@After
	public void terminate()	{
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("====== terminate " ); 
	}

	protected void getActors() { 
		try {
			System.out.println("waitForActors ... " ); 
			
			while(frontend == null || mindOfRobot == null || virtualRobot == null 
					|| realRobot == null || businessLogic == null || led == null || hue == null) {
				
				Thread.sleep(250);
				
				frontend = new TestActor(QActorUtils.getQActor("qafrontendactivator_ctrl"));
				mindOfRobot = new TestActor(QActorUtils.getQActor("qamindofrobotproban_ctrl"));
				
				realRobot = new TestActor(QActorUtils.getQActor("qarobotproban_ctrl"));
				realRobot.getDict().put("rFloorClean", "");
				
				// In questo momento, virtual e real coincidono. Una volta separati, eliminare la riga sotto e scommentare quelle che seguono
				virtualRobot = realRobot;
				// virtualRobot = new TestActor(QActorUtils.getQActor("qarobotproban_ctrl"));
				// virtualRobot.getDict().put("rFloorClean", "");
				
				businessLogic = new TestActor(QActorUtils.getQActor("qabusinesslogicproban_ctrl"));
				businessLogic.getDict().put("rStart", ""); 
				businessLogic.getDict().put("rTempAndTimeOk", "systemmodel( name(application), value(on) )"); 
				businessLogic.getDict().put("rStop", ""); 
				businessLogic.getDict().put("rTempKo", "systemmodel( name(application), value(off) )"); 
				businessLogic.getDict().put("rTimeKo", "systemmodel( name(application), value(off) )"); 
				businessLogic.getDict().put("rObstacle", "");
				businessLogic.getDict().put("rEnd", "");
				
				led = new TestActor(QActorUtils.getQActor("qaledproban_ctrl"));
				led.getDict().put("rBlinkLed", "ledmodel( name(genericled), value(on) )");
				led.getDict().put("rStop", "ledmodel( name(genericled), value(off) )");
				led.getDict().put("rTempKo", "ledmodel( name(genericled), value(off) )");
				led.getDict().put("rTimeKo", "ledmodel( name(genericled), value(off) )");
				led.getDict().put("rObstacle", "ledmodel( name(genericled), value(off) )");
				led.getDict().put("rEnd", "ledmodel( name(genericled), value(off) )");
				
				// Valgono le stesse considerazioni che valgono per il robot
				hue = led;
				// hue = new TestActor(QActorUtils.getQActor("qaledproban_ctrl"));
				// hue.getDict().put("rBlinkHue", "ledmodel( name(genericled), value(on) )");
				// hue.getDict().put("rStop", "ledmodel( name(genericled), value(on) )");
				// hue.getDict().put("rTempKo", "ledmodel( name(genericled), value(on) )");
				// hue.getDict().put("rTimeKo", "ledmodel( name(genericled), value(on) )");
				// hue.getDict().put("rObstacle", "ledmodel( name(genericled), value(on) )");
				// hue.getDict().put("rEnd", "ledmodel( name(genericled), value(on) )");
			}
			startSystem();
		} catch( Exception e) {
			System.out.println("waitForActors fails " + e.getMessage() ); 
		}
	}
	
	protected void startSystem() {
		frontend.getActor().emit("consoleCmd", "consoleCmd(start)");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		 
	/**  
	 *  R-FloorClean: posta come ipotesi che la strategia trovata e' la migliore possibile in base alla stanza,
	 *  abbiamo che se, per ogni evento dell'algoritmo che individua la prossima mossa da eseguire, 
	 *  il robot esegue effettivamente la mossa, allora se l'evento finale sarà finished, 
	 *  sicuramente la stanza sarà pulita, altrimenti (obstacle) conterrà un ostacolo invalicabile.
	 *  Basta quindi controllare che, per ogni mossa dettata dall'algoritmo, ci sia un'effettiva mossa del robot.
	 *  QActor: robot reale, robot virtuale
	 *  
	 *  rFloorClean per robot virtuale e reale coincide, quindi verranno entrambi trattati sotto
	 *  
	 *  TODO: serve un predicato Prolog che i robot virtuali e reali devono settare quando eseguono/ricevono la mossa da fare dalla business logic
	 */
	@Test
	public void rFloorClean() {
		System.out.println("====== rFloorClean ===============" ); 
		try {
			frontend.getActor().emit("consoleCmd", "consoleCmd(start)"); // Avvio il sistema
			Thread.sleep(2000);
			mindOfRobot.getActor().emit("thought", "thought(w)");
			
			Thread.sleep(250);
			
			SolveInfo sol = realRobot.getActor().solveGoal(businessLogic.getDict().get("rFloorClean"));
			assertTrue("", sol.isSuccess());
			
			sol = virtualRobot.getActor().solveGoal(virtualRobot.getDict().get("rFloorClean"));
			assertTrue("", sol.isSuccess());
			
		} catch (Exception e) {
			System.out.println( "ERROR=" + e.getMessage() ); fail("actorTest " + e.getMessage() );
		}
	}
	
	/**
	 *  R-Start: il robot deve iniziare la pulizia della stanza quando viene premuto start.
	 *  Dato che al punto precedente si e' verificato che a ogni mossa dell'algoritmo corrisponde un'effettiva mossa del robot,
	 *  basterà verificare che la business logic, che sceglie le mosse, abbia ricevuto il comando di start,
	 *  che nel modello coincide con la messa in moto dell'intelligenza artificiale che decide le mosse
	 *  QActor: business logic
	 *  
	 *  R-BlinkLed: appena ricevuto il comando di start, per come e' strutturato il sistema, basta vedere se lo stato del led diventa lampeggiante
	 *  QActor: led
	 *  
	 *  R-BlinkHue: come sopra ma per la hue
	 *  QActor: hue
	 *  
	 *  Le precondizioni di questi eventi coincidono, quindi verranno trattati tutti qui sotto
	 *  
	 *  TODO: serve un predicato Prolog che la business logic deve settare per indicare che il comando di start e' stato ricevuto 
	 *  		(per indicare se la pulizia e' in corso o e' fermo)
	 */  
	@Test
	public void rStart() {
		System.out.println("====== rStart ===============" ); 
		try {
			frontend.getActor().emit("consoleCmd", "consoleCmd(start)");
			
			Thread.sleep(250);
			
			SolveInfo sol = businessLogic.getActor().solveGoal(led.getDict().get("rStart"));
			assertTrue("", sol.isSuccess());
			
			sol = led.getActor().solveGoal(led.getDict().get("rBlinkLed"));
			assertTrue("", sol.isSuccess());
			
			sol = hue.getActor().solveGoal(hue.getDict().get("rBlinkHue"));
			assertTrue("", sol.isSuccess());
			
		} catch (Exception e) {
			System.out.println( "ERROR=" + e.getMessage() ); fail("actorTest " + e.getMessage() );
		}
	}
	
	/**
	 * 	R-TempOk: il robot puo' ricevere il comando di start se e solo se la temperatura (e il tempo, indicato sotto) e' ok.
	 *  Dato che questo e' vincolato dal frontend, e' necessario semplicemente verificare se 
	 *  quando la temperatura e' ok (e anche il tempo lo e'), lo stato del sistema torna attivo.
	 *  Per questo, R-TempOk e R-TimeOk saranno trattati insieme
	 *  QActor: business logic
	 *  
	 *  R-TimeOk: come sopra ma per il tempo
	 *  QActor: business logic
	 *  
	 *  Dato che devono essere entrambi veri per l'attivazione del sistema, verranno trattati qui sotto insieme
	 */
	@Test
	public void rTempAndTimeOk() {
		System.out.println("====== rTempAndTimeOk ===============" ); 
		try {
			frontend.getActor().emit("timeEvent", "timeEvent(10)");
			frontend.getActor().emit("tempEvent", "tempEvent(20)");
			
			Thread.sleep(8000);
			
			SolveInfo sol = businessLogic.getActor().solveGoal(businessLogic.getDict().get("rTempAndTimeOk"));
			assertTrue("", sol.isSuccess());
			
		} catch (Exception e) {
			System.out.println( "ERROR=" + e.getMessage() ); fail("actorTest " + e.getMessage() );
		}
	}
	
	/**
	 * 	R-Stop: come per lo start, lo stop e' permesso solo quando il robot sta effettivamente pulendo.
	 *  Di conseguenza, basta semplicemente verificare che lo stato del sistema (che e' stato gia' verificato attivarsi con successo con R-Start),
	 *  cambi in stoppato.
	 *  I led pero' continueranno a lampeggiare: bisogna quindi controllare che anch'essi si spengano
	 *  QActor: business logic, led, hue
	 *  
	 *  TODO: serve lo stesso predicato Prolog di cui parlavo a rStart
	 */
	@Test
	public void rStop() {
		System.out.println("====== rStop ===============" ); 
		try {
			frontend.getActor().emit("consoleCmd", "consoleCmd(start)"); // Avvio il sistema
			Thread.sleep(2000);
			frontend.getActor().emit("consoleCmd", "consoleCmd(stop)"); // Dopo 2 secondi, lo stoppo
			
			Thread.sleep(250);

			SolveInfo sol = businessLogic.getActor().solveGoal(businessLogic.getDict().get("rStop"));
			assertTrue("", sol.isSuccess());
			
			sol = led.getActor().solveGoal(led.getDict().get("rStop"));
			assertTrue("", sol.isSuccess());
		
			sol = hue.getActor().solveGoal(hue.getDict().get("rStop"));
			assertTrue("", sol.isSuccess());
		
		} catch (Exception e) {
			System.out.println( "ERROR=" + e.getMessage() ); fail("actorTest " + e.getMessage() );
		}
	}
	
	/**
	 * 	R-TempKo: vale lo stesso discorso fatto per R-TempOk.
	 *  Di conseguenza, basta verificare che lo stato del sistema si disattiva quando la condizione e' violata
	 *  I led potrebbero essere lampeggianti: bisogna quindi controllare che si spengano in questo caso
	 *  QActor: business logic, led, hue
	 *  
	 *  TODO: il predicato attuale mi dice se il sistema e' spento o acceso (pronto a ricevere comandi)
	 *  		Servirebbe estenderlo dicendo perche' il sistema e' spento (come diceva il prof l'ultima volta):
	 *  		se per la temperatura o per il tempo
	 */
	@Test
	public void rTempKo() {
		System.out.println("====== rTempKo ===============" ); 
		try {
			frontend.getActor().emit("tempEvent", "tempEvent(30)");
			frontend.getActor().emit("timeEvent", "timeEvent(10)");

			Thread.sleep(250);

			SolveInfo sol = businessLogic.getActor().solveGoal(businessLogic.getDict().get("rTempKo"));
			assertTrue("", sol.isSuccess());
			
			sol = led.getActor().solveGoal(led.getDict().get("rTempKo"));
			assertTrue("", sol.isSuccess());
		
			sol = hue.getActor().solveGoal(hue.getDict().get("rTempKo"));
			assertTrue("", sol.isSuccess());
					
		} catch (Exception e) {
			System.out.println( "ERROR=" + e.getMessage() ); fail("actorTest " + e.getMessage() );
		}
	}
	
	/**
	 * R-TimeKo: come sopra
	 * QActor: business logic, led, hue
	 * 
	 * TODO: come sopra
	 */
	@Test
	public void rTimeKo() {
		System.out.println("====== rTimeKo ===============" ); 
		try {
			frontend.getActor().emit("timeEvent", "timeEvent(4)");
			frontend.getActor().emit("tempEvent", "tempEvent(20)");

			Thread.sleep(250);

			SolveInfo sol = businessLogic.getActor().solveGoal(businessLogic.getDict().get("rTimeKo"));
			assertTrue("", sol.isSuccess());
			
			sol = led.getActor().solveGoal(led.getDict().get("rTimeKo"));
			assertTrue("", sol.isSuccess());
		
			sol = hue.getActor().solveGoal(hue.getDict().get("rTimeKo"));
			assertTrue("", sol.isSuccess());
		
		} catch (Exception e) {
			System.out.println( "ERROR=" + e.getMessage() ); fail("actorTest " + e.getMessage() );
		}
	}
	
	/**
	 * R-Obstacle: considerando il discorso fatto prima sulla strategia, 
	 * abbiamo che se l'algoritmo di IA termina con obstacle, allora sicuramente e' presente un ostacolo invalicabile nella stanza.
	 * Basta quindi verificare che il sistema si stoppa all'arrivo del messaggio di obstacle
	 * Vale la stessa considerazione sui led che in R-Stop
	 * QActor: business logic, led, hue
	 * 
	 * TODO: serve un predicato Prolog che memorizzi il perche' il robot si e' fermato: 
	 * 			se si e' fermato perche' e' stato trovato un ostacolo invalicabile oppure se semplicemente e' finito l'algoritmo
	 */
	@Test
	public void rObstacle() {
		System.out.println("====== rObstacle ===============" ); 
		try {
			frontend.getActor().emit("consoleCmd", "consoleCmd(start)"); // Avvio il sistema
			Thread.sleep(2000);
			mindOfRobot.getActor().emit("finished", "obstacle");
			
			Thread.sleep(250);

			SolveInfo sol = businessLogic.getActor().solveGoal(businessLogic.getDict().get("rObstacle"));
			assertTrue("", sol.isSuccess());
			
			sol = led.getActor().solveGoal(led.getDict().get("rObstacle"));
			assertTrue("", sol.isSuccess());
		
			sol = hue.getActor().solveGoal(hue.getDict().get("rObstacle"));
			assertTrue("", sol.isSuccess());
		
		} catch (Exception e) {
			System.out.println( "ERROR=" + e.getMessage() ); fail("actorTest " + e.getMessage() );
		}
	}
	
	/**
	 *  R-End: considerando la struttura del modello, in cui il robot esegue passo passo l'algoritmo di IA,
	 *  una volta arrivato o il messaggio di finished o di obstacle, il robot termina il suo lavoro.
	 *  Basta quindi verificare che il sistema si stoppa all'arrivo del messaggio di finished
	 *  Vale la stessa considerazione sui led che in R-Stop
	 *  QActor: business logic, led, hue
	 *  
	 *  TODO: come sopra
	 */
	@Test
	public void rEnd() {
		System.out.println("====== rEnd ===============" ); 
		try {
			frontend.getActor().emit("consoleCmd", "consoleCmd(start)"); // Avvio il sistema
			Thread.sleep(2000);
			mindOfRobot.getActor().emit("finished", "clean");
			
			Thread.sleep(250);

			SolveInfo sol = businessLogic.getActor().solveGoal(businessLogic.getDict().get("rEnd"));
			assertTrue("", sol.isSuccess());
			
			sol = led.getActor().solveGoal(led.getDict().get("rEnd"));
			assertTrue("", sol.isSuccess());
		
			sol = hue.getActor().solveGoal(hue.getDict().get("rEnd"));
			assertTrue("", sol.isSuccess());
		
		} catch (Exception e) {
			System.out.println( "ERROR=" + e.getMessage() ); fail("actorTest " + e.getMessage() );
		}
	}

}
