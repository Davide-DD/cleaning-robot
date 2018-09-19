package thirdTest;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import alice.tuprolog.SolveInfo;
import it.unibo.qactors.akka.QActor;

public class ThirdTest {

	private QActor robot = null, console = null, environment = null, sensor = null;

	protected void runContextsAndGetQActors() { 
		CtxRun consoleRun = new CtxRun("consolectx3", 
				"./srcMore/it/unibo/consoleCtx3/cleaningrobotanalisirequisiti3.pl", 
				"./srcMore/it/unibo/consoleCtx3/sysRules.pl", new String[]{"qaconsole3_ctrl"});
		CtxRun robotRun = new CtxRun("robotctx3", 
				"./srcMore/it/unibo/robotCtx3/cleaningrobotanalisirequisiti3.pl", 
				"./srcMore/it/unibo/robotCtx3/sysRules.pl", 
				new String[]{"qarobot3_ctrl", "qaenvironment_ctrl", "qaproximitysensor_ctrl"});
		
		Thread consoleThread = new Thread(consoleRun);
		consoleThread.start();
		
		Thread robotThread = new Thread(robotRun);
		robotThread.start();
		
		try {
			consoleThread.join();
			robotThread.join();
			
			console = consoleRun.getQActors().iterator().next();
			for (QActor q : robotRun.getQActors()) {
				if (q.getName().equals("qarobot3_ctrl")) {
					robot = q;
				}
				else if (q.getName().equals("qaenvironment_ctrl")) {
					environment = q;
				}
				else if (q.getName().equals("qaproximitysensor_ctrl")) {
					sensor = q;
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	@Before
	public void systemSetUp() throws Exception	{
		System.out.println("systemSetUp starts " );
		runContextsAndGetQActors();
	}

	@After
	public void terminate()	{
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("====== terminate " ); 
	}
	
	/**
	 *  R-Start: il robot deve iniziare la pulizia della stanza quando viene premuto start.
	 */  
	@Test
	public void rStart() {
		System.out.println("====== rStart ===============" ); 
		try {
			console.sendMsg("consoleMsg", robot.getNameNoCtrl(), "dispatch", "consoleMsg(start)");
			
			Thread.sleep(1000);
			
			SolveInfo sol = robot.solveGoal("rStart");
			assertTrue("", sol.isSuccess());
			
		} catch (Exception e) {
			System.out.println( "ERROR=" + e.getMessage() ); fail("actorTest " + e.getMessage() );
		}
	}
	
	/**
	 * 	R-Stop: come per lo start, lo stop e' permesso solo quando il robot sta effettivamente pulendo.
	 */
	@Test
	public void rStop() {
		System.out.println("====== rStop ===============" ); 
		try {
			console.sendMsg("consoleMsg", robot.getNameNoCtrl(), "dispatch", "consoleMsg(stop)");
			
			Thread.sleep(1000);

			SolveInfo sol = robot.solveGoal("rStop");
			assertTrue("", sol.isSuccess());
		
		} catch (Exception e) {
			System.out.println( "ERROR=" + e.getMessage() ); fail("actorTest " + e.getMessage() );
		}
	}
	
	/**
	 * 	R-TempKo: vale lo stesso discorso fatto per R-TempOk.
	 *  Di conseguenza, basta verificare che lo stato del sistema si disattiva quando la condizione e' violata
	 */
	@Test
	public void rTempKo() {
		System.out.println("====== rTempKo ===============" ); 
		try {
			environment.emit("environmentalEvent", "environmentalEvent(bad_temp)");

			Thread.sleep(250);

			SolveInfo sol = robot.solveGoal("rTempKo");
			assertTrue("", sol.isSuccess());
					
		} catch (Exception e) {
			System.out.println( "ERROR=" + e.getMessage() ); fail("actorTest " + e.getMessage() );
		}
	}
	
	/**
	 * R-TimeKo: come sopra
	 */
	@Test
	public void rTimeKo() {
		System.out.println("====== rTimeKo ===============" ); 
		try {
			environment.emit("environmentalEvent", "environmentalEvent(bad_time)");

			Thread.sleep(250);

			SolveInfo sol = robot.solveGoal("rTimeKo");
			assertTrue("", sol.isSuccess());
			
		} catch (Exception e) {
			System.out.println( "ERROR=" + e.getMessage() ); fail("actorTest " + e.getMessage() );
		}
	}
	
	/**
	 * REQUISITO AGGIUNTIVO
	 * 
	 * R-RobotObstacle: controlla che il robot riceva l'evento di collisione emesso dal sensore
	 */
	@Test
	public void rRobotObstacle() {
		System.out.println("====== rRobotObstacle ===============" ); 
		try {
			sensor.emit("collisionEvent", "collisionEvent(test)");

			Thread.sleep(250);

			SolveInfo sol = robot.solveGoal("rRobotObstacle");
			assertTrue("", sol.isSuccess());
			
		} catch (Exception e) {
			System.out.println( "ERROR=" + e.getMessage() ); fail("actorTest " + e.getMessage() );
		}
	}
	
}
