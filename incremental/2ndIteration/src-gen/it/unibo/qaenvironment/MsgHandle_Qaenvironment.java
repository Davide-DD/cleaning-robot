/* Generated by AN DISI Unibo */ 
package it.unibo.qaenvironment;
import it.unibo.qactors.QActorContext;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.akka.QActorMsgQueue;

public class MsgHandle_Qaenvironment extends QActorMsgQueue{
	public MsgHandle_Qaenvironment(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  {
		super(actorId, myCtx, outEnvView);
	}
}
