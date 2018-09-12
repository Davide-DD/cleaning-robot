/* Generated by AN DISI Unibo */ 
package it.unibo.robotCtx4;
import it.unibo.qactors.QActorContext;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.system.SituatedSysKb;
public class MainRobotCtx4  {
  
//MAIN
public static QActorContext initTheContext() throws Exception{
	IOutputEnvView outEnvView = SituatedSysKb.standardOutEnvView;
	it.unibo.is.interfaces.IBasicEnvAwt env=new it.unibo.baseEnv.basicFrame.EnvFrame( 
		"Env_robotCtx4",java.awt.Color.yellow , java.awt.Color.black );
	env.init();
	outEnvView = env.getOutputEnvView();
	String webDir = "./srcMore/it/unibo/robotCtx4";
	return QActorContext.initQActorSystem(
		"robotctx4", "./srcMore/it/unibo/robotCtx4/cleaningrobotanalisirequisiti4.pl", 
		"./srcMore/it/unibo/robotCtx4/sysRules.pl", outEnvView,webDir,false);
}
public static void main(String[] args) throws Exception{
	QActorContext ctx = initTheContext();
} 	
}
