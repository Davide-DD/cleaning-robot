/* Generated by AN DISI Unibo */ 
package it.unibo.robotCtx3;
import it.unibo.qactors.QActorContext;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.system.SituatedSysKb;
public class MainRobotCtx3  {
  
//MAIN
public static QActorContext initTheContext() throws Exception{
	IOutputEnvView outEnvView = SituatedSysKb.standardOutEnvView;
	it.unibo.is.interfaces.IBasicEnvAwt env=new it.unibo.baseEnv.basicFrame.EnvFrame( 
		"Env_robotCtx3",java.awt.Color.yellow , java.awt.Color.black );
	env.init();
	outEnvView = env.getOutputEnvView();
	String webDir = "./srcMore/it/unibo/robotCtx3";
	return QActorContext.initQActorSystem(
		"robotctx3", "./srcMore/it/unibo/robotCtx3/cleaningrobotanalisirequisiti3.pl", 
		"./srcMore/it/unibo/robotCtx3/sysRules.pl", outEnvView,webDir,false);
}
public static void main(String[] args) throws Exception{
	QActorContext ctx = initTheContext();
} 	
}