/* Generated by AN DISI Unibo */ 
package it.unibo.qabusinesslogicproban;
import it.unibo.qactors.PlanRepeat;
import it.unibo.qactors.QActorContext;
import it.unibo.qactors.StateExecMessage;
import it.unibo.qactors.QActorUtils;
import it.unibo.is.interfaces.IOutputEnvView;
import it.unibo.qactors.action.AsynchActionResult;
import it.unibo.qactors.action.IActorAction;
import it.unibo.qactors.action.IActorAction.ActionExecMode;
import it.unibo.qactors.action.IMsgQueue;
import it.unibo.qactors.akka.QActor;
import it.unibo.qactors.StateFun;
import java.util.Stack;
import java.util.Hashtable;
import java.util.concurrent.Callable;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import it.unibo.qactors.action.ActorTimedAction;
public abstract class AbstractQabusinesslogicproban extends QActor { 
	protected AsynchActionResult aar = null;
	protected boolean actionResult = true;
	protected alice.tuprolog.SolveInfo sol;
	protected String planFilePath    = null;
	protected String terminationEvId = "default";
	protected String parg="";
	protected boolean bres=false;
	protected IActorAction action;
	 
	
		protected static IOutputEnvView setTheEnv(IOutputEnvView outEnvView ){
			return outEnvView;
		}
		public AbstractQabusinesslogicproban(String actorId, QActorContext myCtx, IOutputEnvView outEnvView )  throws Exception{
			super(actorId, myCtx,  
			"./srcMore/it/unibo/qabusinesslogicproban/WorldTheory.pl",
			setTheEnv( outEnvView )  , "init");
			this.planFilePath = "./srcMore/it/unibo/qabusinesslogicproban/plans.txt";
	  	}
		@Override
		protected void doJob() throws Exception {
			String name  = getName().replace("_ctrl", "");
			mysupport = (IMsgQueue) QActorUtils.getQActor( name ); 
			initStateTable(); 
	 		initSensorSystem();
	 		history.push(stateTab.get( "init" ));
	  	 	autoSendStateExecMsg();
	  		//QActorContext.terminateQActorSystem(this);//todo
		} 	
		/* 
		* ------------------------------------------------------------
		* PLANS
		* ------------------------------------------------------------
		*/    
	    //genAkkaMshHandleStructure
	    protected void initStateTable(){  	
	    	stateTab.put("handleToutBuiltIn",handleToutBuiltIn);
	    	stateTab.put("init",init);
	    	stateTab.put("waitForFrontendActivation",waitForFrontendActivation);
	    	stateTab.put("handleConsoleReady",handleConsoleReady);
	    	stateTab.put("waitForUserCmd",waitForUserCmd);
	    	stateTab.put("handleConsoleStartCmd",handleConsoleStartCmd);
	    	stateTab.put("waitForAlgCmd",waitForAlgCmd);
	    	stateTab.put("handleThought",handleThought);
	    	stateTab.put("waitForRobotResponse",waitForRobotResponse);
	    	stateTab.put("handleRobotAnswer",handleRobotAnswer);
	    	stateTab.put("handleStopOrFinished",handleStopOrFinished);
	    	stateTab.put("handleCityMsg",handleCityMsg);
	    }
	    StateFun handleToutBuiltIn = () -> {	
	    	try{	
	    		PlanRepeat pr = PlanRepeat.setUp("handleTout",-1);
	    		String myselfName = "handleToutBuiltIn";  
	    		println( "qabusinesslogicproban tout : stops");  
	    		repeatPlanNoTransition(pr,myselfName,"application_"+myselfName,false,false);
	    	}catch(Exception e_handleToutBuiltIn){  
	    		println( getName() + " plan=handleToutBuiltIn WARNING:" + e_handleToutBuiltIn.getMessage() );
	    		QActorContext.terminateQActorSystem(this); 
	    	}
	    };//handleToutBuiltIn
	    
	    StateFun init = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("init",-1);
	    	String myselfName = "init";  
	    	temporaryStr = "qabusinesslogicreqan(starts)";
	    	println( temporaryStr );  
	    	//ConnectToSubscribe
	    	connectAndSubscribe( this.getName(), "tcp://localhost:1883", "temperature");
	    	//ConnectToSubscribe
	    	connectAndSubscribe( this.getName(), "tcp://localhost:1883", "hour");
	    	//switchTo waitForFrontendActivation
	        switchToPlanAsNextState(pr, myselfName, "qabusinesslogicproban_"+myselfName, 
	              "waitForFrontendActivation",false, false, null); 
	    }catch(Exception e_init){  
	    	 println( getName() + " plan=init WARNING:" + e_init.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//init
	    
	    StateFun waitForFrontendActivation = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_waitForFrontendActivation",0);
	     pr.incNumIter(); 	
	    	String myselfName = "waitForFrontendActivation";  
	    	temporaryStr = "\"Waiting for frontend to be ready\"";
	    	println( temporaryStr );  
	    	//bbb
	     msgTransition( pr,myselfName,"qabusinesslogicproban_"+myselfName,false,
	          new StateFun[]{stateTab.get("handleConsoleReady") }, 
	          new String[]{"true","M","consoleMsg" },
	          30000000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_waitForFrontendActivation){  
	    	 println( getName() + " plan=waitForFrontendActivation WARNING:" + e_waitForFrontendActivation.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForFrontendActivation
	    
	    StateFun handleConsoleReady = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("handleConsoleReady",-1);
	    	String myselfName = "handleConsoleReady";  
	    	temporaryStr = "\"console is ready\"";
	    	println( temporaryStr );  
	    	//switchTo waitForUserCmd
	        switchToPlanAsNextState(pr, myselfName, "qabusinesslogicproban_"+myselfName, 
	              "waitForUserCmd",false, false, null); 
	    }catch(Exception e_handleConsoleReady){  
	    	 println( getName() + " plan=handleConsoleReady WARNING:" + e_handleConsoleReady.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//handleConsoleReady
	    
	    StateFun waitForUserCmd = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_waitForUserCmd",0);
	     pr.incNumIter(); 	
	    	String myselfName = "waitForUserCmd";  
	    	temporaryStr = "\"Waiting for user commands\"";
	    	println( temporaryStr );  
	    	//bbb
	     msgTransition( pr,myselfName,"qabusinesslogicproban_"+myselfName,false,
	          new StateFun[]{stateTab.get("handleConsoleStartCmd"), stateTab.get("handleCityMsg"), stateTab.get("handleCityMsg") }, 
	          new String[]{"true","M","consoleMsg", "true","M","timeMsg", "true","M","tempMsg" },
	          30000000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_waitForUserCmd){  
	    	 println( getName() + " plan=waitForUserCmd WARNING:" + e_waitForUserCmd.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForUserCmd
	    
	    StateFun handleConsoleStartCmd = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("handleConsoleStartCmd",-1);
	    	String myselfName = "handleConsoleStartCmd";  
	    	printCurrentEvent(false);
	    	//onMsg 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("consoleCmd(\"start\")");
	    	if( currentMessage != null && currentMessage.msgId().equals("consoleMsg") && 
	    		pengine.unify(curT, Term.createTerm("consoleCmd(CMD)")) && 
	    		pengine.unify(curT, Term.createTerm( currentMessage.msgContent() ) )){ 
	    		//println("WARNING: variable substitution not yet fully implemented " ); 
	    		{//actionseq
	    		temporaryStr = "\"START command received from console\"";
	    		println( temporaryStr );  
	    		temporaryStr = QActorUtils.unifyMsgContent(pengine, "manageCommands(SENDER,DATA)","manageCommands(\"buslog\",\"disable_start, enable_stop\")", guardVars ).toString();
	    		emit( "manageCommandsEvent", temporaryStr );
	    		temporaryStr = QActorUtils.unifyMsgContent(pengine, "ledEvent(VALUE)","ledEvent(\"blink\")", guardVars ).toString();
	    		emit( "ledEvent", temporaryStr );
	    		temporaryStr = QActorUtils.unifyMsgContent(pengine, "thinkingRequest(DATA)","thinkingRequest(\"reset\")", guardVars ).toString();
	    		emit( "thinkingRequest", temporaryStr );
	    		};//actionseq
	    	}
	    	//switchTo waitForAlgCmd
	        switchToPlanAsNextState(pr, myselfName, "qabusinesslogicproban_"+myselfName, 
	              "waitForAlgCmd",false, false, null); 
	    }catch(Exception e_handleConsoleStartCmd){  
	    	 println( getName() + " plan=handleConsoleStartCmd WARNING:" + e_handleConsoleStartCmd.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//handleConsoleStartCmd
	    
	    StateFun waitForAlgCmd = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_waitForAlgCmd",0);
	     pr.incNumIter(); 	
	    	String myselfName = "waitForAlgCmd";  
	    	//bbb
	     msgTransition( pr,myselfName,"qabusinesslogicproban_"+myselfName,false,
	          new StateFun[]{stateTab.get("handleStopOrFinished"), stateTab.get("handleThought"), stateTab.get("handleStopOrFinished"), stateTab.get("handleCityMsg"), stateTab.get("handleCityMsg") }, 
	          new String[]{"true","M","consoleMsg", "true","E","thought", "true","E","finished", "true","M","timeMsg", "true","M","tempMsg" },
	          30000000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_waitForAlgCmd){  
	    	 println( getName() + " plan=waitForAlgCmd WARNING:" + e_waitForAlgCmd.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForAlgCmd
	    
	    StateFun handleThought = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("handleThought",-1);
	    	String myselfName = "handleThought";  
	    	printCurrentEvent(false);
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("thought(V)");
	    	if( currentEvent != null && currentEvent.getEventId().equals("thought") && 
	    		pengine.unify(curT, Term.createTerm("thought(DATA)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			String parg="robotCmd(V)";
	    			/* SendDispatch */
	    			parg = updateVars(Term.createTerm("thought(DATA)"),  Term.createTerm("thought(V)"), 
	    				    		  					Term.createTerm(currentEvent.getMsg()), parg);
	    			if( parg != null ) sendMsg("robotCmd","qarobotproban", QActorContext.dispatch, parg ); 
	    	}
	    	//switchTo waitForRobotResponse
	        switchToPlanAsNextState(pr, myselfName, "qabusinesslogicproban_"+myselfName, 
	              "waitForRobotResponse",false, false, null); 
	    }catch(Exception e_handleThought){  
	    	 println( getName() + " plan=handleThought WARNING:" + e_handleThought.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//handleThought
	    
	    StateFun waitForRobotResponse = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_waitForRobotResponse",0);
	     pr.incNumIter(); 	
	    	String myselfName = "waitForRobotResponse";  
	    	//bbb
	     msgTransition( pr,myselfName,"qabusinesslogicproban_"+myselfName,false,
	          new StateFun[]{stateTab.get("handleStopOrFinished"), stateTab.get("handleRobotAnswer"), stateTab.get("handleCityMsg"), stateTab.get("handleCityMsg") }, 
	          new String[]{"true","M","consoleMsg", "true","M","robotAnswer", "true","M","timeMsg", "true","M","tempMsg" },
	          30000000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_waitForRobotResponse){  
	    	 println( getName() + " plan=waitForRobotResponse WARNING:" + e_waitForRobotResponse.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForRobotResponse
	    
	    StateFun handleRobotAnswer = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("handleRobotAnswer",-1);
	    	String myselfName = "handleRobotAnswer";  
	    	printCurrentMessage(false);
	    	//onMsg 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("robotAnswer(V)");
	    	if( currentMessage != null && currentMessage.msgId().equals("robotAnswer") && 
	    		pengine.unify(curT, Term.createTerm("robotAnswer(VALUE)")) && 
	    		pengine.unify(curT, Term.createTerm( currentMessage.msgContent() ) )){ 
	    		String parg="thinkingRequest(V)";
	    		/* RaiseEvent */
	    		parg = updateVars(Term.createTerm("robotAnswer(VALUE)"),  Term.createTerm("robotAnswer(V)"), 
	    			    		  					Term.createTerm(currentMessage.msgContent()), parg);
	    		if( parg != null ) emit( "thinkingRequest", parg );
	    	}
	    	//switchTo waitForAlgCmd
	        switchToPlanAsNextState(pr, myselfName, "qabusinesslogicproban_"+myselfName, 
	              "waitForAlgCmd",false, false, null); 
	    }catch(Exception e_handleRobotAnswer){  
	    	 println( getName() + " plan=handleRobotAnswer WARNING:" + e_handleRobotAnswer.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//handleRobotAnswer
	    
	    StateFun handleStopOrFinished = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("handleStopOrFinished",-1);
	    	String myselfName = "handleStopOrFinished";  
	    	temporaryStr = "\"STOP command received from console or triggered from application\"";
	    	println( temporaryStr );  
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "ledEvent(VALUE)","ledEvent(\"off\")", guardVars ).toString();
	    	emit( "ledEvent", temporaryStr );
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " !?systemmodel(name(application),value(on))" )) != null ){
	    	{//actionseq
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "manageCommands(SENDER,DATA)","manageCommands(\"buslog\",\"enable_start, disable_stop\")", guardVars ).toString();
	    	emit( "manageCommandsEvent", temporaryStr );
	    	};//actionseq
	    	}
	    	//switchTo waitForUserCmd
	        switchToPlanAsNextState(pr, myselfName, "qabusinesslogicproban_"+myselfName, 
	              "waitForUserCmd",false, false, null); 
	    }catch(Exception e_handleStopOrFinished){  
	    	 println( getName() + " plan=handleStopOrFinished WARNING:" + e_handleStopOrFinished.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//handleStopOrFinished
	    
	    StateFun handleCityMsg = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("handleCityMsg",-1);
	    	String myselfName = "handleCityMsg";  
	    	printCurrentMessage(false);
	    	//onMsg 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("timeEvent(V)");
	    	if( currentMessage != null && currentMessage.msgId().equals("timeMsg") && 
	    		pengine.unify(curT, Term.createTerm("timeEvent(VALUE)")) && 
	    		pengine.unify(curT, Term.createTerm( currentMessage.msgContent() ) )){ 
	    		String parg  ="curTimeValue(X)";
	    		String parg1 ="curTimeValue(V)";
	    		/* ReplaceRule */
	    		parg = updateVars(Term.createTerm("timeEvent(VALUE)"),  Term.createTerm("timeEvent(V)"), 
	    			    		  					Term.createTerm(currentMessage.msgContent()), parg);
	    		parg1 = updateVars(Term.createTerm("timeEvent(VALUE)"),  Term.createTerm("timeEvent(V)"), 
	    			    		  					Term.createTerm(currentMessage.msgContent()), parg1);
	    		if( parg != null && parg1 != null  ) replaceRule(parg, parg1);	    		  					
	    	}
	    	//onMsg 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("tempEvent(V)");
	    	if( currentMessage != null && currentMessage.msgId().equals("tempMsg") && 
	    		pengine.unify(curT, Term.createTerm("tempEvent(VALUE)")) && 
	    		pengine.unify(curT, Term.createTerm( currentMessage.msgContent() ) )){ 
	    		String parg  ="curTemperatureValue(X)";
	    		String parg1 ="curTemperatureValue(V)";
	    		/* ReplaceRule */
	    		parg = updateVars(Term.createTerm("tempEvent(VALUE)"),  Term.createTerm("tempEvent(V)"), 
	    			    		  					Term.createTerm(currentMessage.msgContent()), parg);
	    		parg1 = updateVars(Term.createTerm("tempEvent(VALUE)"),  Term.createTerm("tempEvent(V)"), 
	    			    		  					Term.createTerm(currentMessage.msgContent()), parg1);
	    		if( parg != null && parg1 != null  ) replaceRule(parg, parg1);	    		  					
	    	}
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " !?evalTime(good)" )) != null ){
	    	{//actionseq
	    	temporaryStr = "\"GOOD time range, doing nothing.\"";
	    	println( temporaryStr );  
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " !?evalTemperature(cold)" )) != null ){
	    	{//actionseq
	    	temporaryStr = "\"Temperature is nice, doing nothing.\"";
	    	println( temporaryStr );  
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " !?switchSystemState(on)" )) != null ){
	    	{//actionseq
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "manageCommands(SENDER,DATA)","manageCommands(\"buslog\",\"enable_start, disable_stop\")", guardVars ).toString();
	    	emit( "manageCommandsEvent", temporaryStr );
	    	};//actionseq
	    	}
	    	};//actionseq
	    	}
	    	else{ {//actionseq
	    	temporaryStr = "\"Temperature too hot, stopping everything.\"";
	    	println( temporaryStr );  
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " !?switchSystemState(off)" )) != null ){
	    	{//actionseq
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "manageCommands(SENDER,DATA)","manageCommands(\"buslog\",\"disable_all\")", guardVars ).toString();
	    	emit( "manageCommandsEvent", temporaryStr );
	    	};//actionseq
	    	}
	    	};//actionseq
	    	}};//actionseq
	    	}
	    	else{ {//actionseq
	    	temporaryStr = "\"BAD time range, stopping everything.\"";
	    	println( temporaryStr );  
	    	if( (guardVars = QActorUtils.evalTheGuard(this, " !?switchSystemState(off)" )) != null ){
	    	{//actionseq
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "manageCommands(SENDER,DATA)","manageCommands(\"buslog\",\"disable_all\")", guardVars ).toString();
	    	emit( "manageCommandsEvent", temporaryStr );
	    	};//actionseq
	    	}
	    	};//actionseq
	    	}
	    	//switchTo handleStopOrFinished
	        switchToPlanAsNextState(pr, myselfName, "qabusinesslogicproban_"+myselfName, 
	              "handleStopOrFinished",false, true, " !?systemmodel(name(application),value(off))"); 
	    }catch(Exception e_handleCityMsg){  
	    	 println( getName() + " plan=handleCityMsg WARNING:" + e_handleCityMsg.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//handleCityMsg
	    
	    protected void initSensorSystem(){
	    	//doing nothing in a QActor
	    }
	
	}