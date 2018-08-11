/* Generated by AN DISI Unibo */ 
package it.unibo.architectureModelCtx;
import it.unibo.qactors.QActorContext;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.system.SituatedSysKb;
public class MainArchitectureModelCtx  {
  
//MAIN
public static QActorContext initTheContext() throws Exception{
	IOutputEnvView outEnvView = SituatedSysKb.standardOutEnvView;
	it.unibo.is.interfaces.IBasicEnvAwt env=new it.unibo.baseEnv.basicFrame.EnvFrame( 
		"Env_architectureModelCtx",java.awt.Color.cyan , java.awt.Color.black );
	env.init();
	outEnvView = env.getOutputEnvView();
	String webDir = null;
	return QActorContext.initQActorSystem(
		"architecturemodelctx", "./srcMore/it/unibo/architectureModelCtx/cleaningrobot.pl", 
		"./srcMore/it/unibo/architectureModelCtx/sysRules.pl", outEnvView,webDir,false);
}
public static void main(String[] args) throws Exception{
	QActorContext ctx = initTheContext();
} 	
}