package firstTest;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import alice.tuprolog.SolveInfo;
import it.unibo.qactors.akka.QActor;

public class FirstTest {

	private QActor robot = null, console = null;

	protected void runContextsAndGetQActors() { 
		CtxRun consoleRun = new CtxRun("consolectx", "./srcMore/it/unibo/consoleCtx/cleaningrobotanalisirequisiti.pl", 
				"./srcMore/it/unibo/consoleCtx/sysRules.pl", new String[] {"qaconsole_ctrl"});
		CtxRun robotRun = new CtxRun("robotctx", "./srcMore/it/unibo/robotCtx/cleaningrobotanalisirequisiti.pl", 
				"./srcMore/it/unibo/robotCtx/sysRules.pl", new String[] {"qarobot_ctrl"});
		
		Thread consoleThread = new Thread(consoleRun);
		consoleThread.start();
		
		Thread robotThread = new Thread(robotRun);
		robotThread.start();
		
		try {
			consoleThread.join();
			robotThread.join();
			
			console = consoleRun.getQActors().iterator().next();
			robot = robotRun.getQActors().iterator().next();
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
	 *  Di conseguenza, basta semplicemente verificare che lo stato del sistema (che e' stato gia' verificato attivarsi con successo con R-Start),
	 *  cambi in stoppato.
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
	
}
