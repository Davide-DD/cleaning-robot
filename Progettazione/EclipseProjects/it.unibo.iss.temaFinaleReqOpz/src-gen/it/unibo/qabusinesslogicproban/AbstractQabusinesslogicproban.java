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
	    	stateTab.put("handleDeleteMapCmd",handleDeleteMapCmd);
	    	stateTab.put("handleConsoleStartCmd",handleConsoleStartCmd);
	    	stateTab.put("waitForAlgCmd",waitForAlgCmd);
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
	          new StateFun[]{stateTab.get("handleDeleteMapCmd"), stateTab.get("handleConsoleStartCmd"), stateTab.get("handleCityMsg"), stateTab.get("handleCityMsg") }, 
	          new String[]{"true","M","deleteMapMsg", "true","M","consoleMsg", "true","M","timeMsg", "true","M","tempMsg" },
	          30000000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_waitForUserCmd){  
	    	 println( getName() + " plan=waitForUserCmd WARNING:" + e_waitForUserCmd.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForUserCmd
	    
	    StateFun handleDeleteMapCmd = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp("handleDeleteMapCmd",-1);
	    	String myselfName = "handleDeleteMapCmd";  
	    	temporaryStr = QActorUtils.unifyMsgContent(pengine, "thinkingRequest(DATA)","thinkingRequest(\"deleteMap\")", guardVars ).toString();
	    	emit( "thinkingRequest", temporaryStr );
	    	repeatPlanNoTransition(pr,myselfName,"qabusinesslogicproban_"+myselfName,false,true);
	    }catch(Exception e_handleDeleteMapCmd){  
	    	 println( getName() + " plan=handleDeleteMapCmd WARNING:" + e_handleDeleteMapCmd.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//handleDeleteMapCmd
	    
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
	    		temporaryStr = "rStart";
	    		addRule( temporaryStr );  
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
	          new StateFun[]{stateTab.get("handleStopOrFinished"), () -> {	//AD HOC state to execute an action and resumeLastPlan
	          try{
	            PlanRepeat pr1 = PlanRepeat.setUp("adhocstate",-1);
	            //ActionSwitch for a message or event
	             if( currentMessage.msgContent().startsWith("thought") ){
	            	//println("WARNING: variable substitution not yet fully implemented " ); 
	            	{//actionseq
	            	//onMsg 
	            	setCurrentMsgFromStore(); 
	            	curT = Term.createTerm("thought(mind,\"finished\")");
	            	if( currentMessage != null && currentMessage.msgId().equals("thoughtMsg") && 
	            		pengine.unify(curT, Term.createTerm("thought(SENDER,DATA)")) && 
	            		pengine.unify(curT, Term.createTerm( currentMessage.msgContent() ) )){ 
	            		String parg="finished(\"finishedCleaning\")";
	            		/* RaiseEvent */
	            		parg = updateVars(Term.createTerm("thought(SENDER,DATA)"),  Term.createTerm("thought(mind,\"finished\")"), 
	            			    		  					Term.createTerm(currentMessage.msgContent()), parg);
	            		if( parg != null ) emit( "finished", parg );
	            	}
	            	//onMsg 
	            	setCurrentMsgFromStore(); 
	            	curT = Term.createTerm("thought(mind,\"cantfinish\")");
	            	if( currentMessage != null && currentMessage.msgId().equals("thoughtMsg") && 
	            		pengine.unify(curT, Term.createTerm("thought(SENDER,DATA)")) && 
	            		pengine.unify(curT, Term.createTerm( currentMessage.msgContent() ) )){ 
	            		String parg="finished(\"cantFinish\")";
	            		/* RaiseEvent */
	            		parg = updateVars(Term.createTerm("thought(SENDER,DATA)"),  Term.createTerm("thought(mind,\"cantfinish\")"), 
	            			    		  					Term.createTerm(currentMessage.msgContent()), parg);
	            		if( parg != null ) emit( "finished", parg );
	            	}
	            	//onMsg 
	            	setCurrentMsgFromStore(); 
	            	curT = Term.createTerm("thought(mind,\"error\")");
	            	if( currentMessage != null && currentMessage.msgId().equals("thoughtMsg") && 
	            		pengine.unify(curT, Term.createTerm("thought(SENDER,DATA)")) && 
	            		pengine.unify(curT, Term.createTerm( currentMessage.msgContent() ) )){ 
	            		String parg="finished(\"cantFinish\")";
	            		/* RaiseEvent */
	            		parg = updateVars(Term.createTerm("thought(SENDER,DATA)"),  Term.createTerm("thought(mind,\"error\")"), 
	            			    		  					Term.createTerm(currentMessage.msgContent()), parg);
	            		if( parg != null ) emit( "finished", parg );
	            	}
	            	//onMsg 
	            	setCurrentMsgFromStore(); 
	            	curT = Term.createTerm("thought(mind,DATA)");
	            	if( currentMessage != null && currentMessage.msgId().equals("thoughtMsg") && 
	            		pengine.unify(curT, Term.createTerm("thought(SENDER,DATA)")) && 
	            		pengine.unify(curT, Term.createTerm( currentMessage.msgContent() ) )){ 
	            		String parg="robotCmd(\"buslog\",DATA)";
	            		/* SendDispatch */
	            		parg = updateVars(Term.createTerm("thought(SENDER,DATA)"),  Term.createTerm("thought(mind,DATA)"), 
	            			    		  					Term.createTerm(currentMessage.msgContent()), parg);
	            		if( parg != null ) sendMsg("robotCmd","qasoffrittirobot", QActorContext.dispatch, parg ); 
	            	}
	            	};//actionseq
	             }
	            repeatPlanNoTransition(pr1,"adhocstate","adhocstate",false,true);
	          }catch(Exception e ){  
	             println( getName() + " plan=waitForAlgCmd WARNING:" + e.getMessage() );
	             //QActorContext.terminateQActorSystem(this); 
	          }
	          },
	           stateTab.get("waitForRobotResponse"), stateTab.get("handleRobotAnswer"), stateTab.get("handleStopOrFinished"), stateTab.get("handleCityMsg"), stateTab.get("handleCityMsg") }, 
	          new String[]{"true","M","consoleMsg", "true","M","thoughtMsg", "true","M","directionReceived", "true","M","robotAnswer", "true","E","finished", "true","M","timeMsg", "true","M","tempMsg" },
	          30000000, "handleToutBuiltIn" );//msgTransition
	    }catch(Exception e_waitForAlgCmd){  
	    	 println( getName() + " plan=waitForAlgCmd WARNING:" + e_waitForAlgCmd.getMessage() );
	    	 QActorContext.terminateQActorSystem(this); 
	    }
	    };//waitForAlgCmd
	    
	    StateFun waitForRobotResponse = () -> {	
	    try{	
	     PlanRepeat pr = PlanRepeat.setUp(getName()+"_waitForRobotResponse",0);
	     pr.incNumIter(); 	
	    	String myselfName = "waitForRobotResponse";  
	    	//bbb
	     msgTransition( pr,myselfName,"qabusinesslogicproban_"+myselfName,false,
	          new StateFun[]{stateTab.get("handleStopOrFinished"), stateTab.get("handleRobotAnswer"), stateTab.get("waitForAlgCmd"), stateTab.get("handleCityMsg"), stateTab.get("handleCityMsg") }, 
	          new String[]{"true","M","consoleMsg", "true","M","robotAnswer", "true","M","thinkingRequestReceived", "true","M","timeMsg", "true","M","tempMsg" },
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
	    	//onMsg 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("robotAnswer(\"obstacle\")");
	    	if( currentMessage != null && currentMessage.msgId().equals("robotAnswer") && 
	    		pengine.unify(curT, Term.createTerm("robotAnswer(VALUE)")) && 
	    		pengine.unify(curT, Term.createTerm( currentMessage.msgContent() ) )){ 
	    		//println("WARNING: variable substitution not yet fully implemented " ); 
	    		{//actionseq
	    		temporaryStr = "\"ROBOT TO BUSINESS LOGIC: C'� un ostacolo.\"";
	    		println( temporaryStr );  
	    		temporaryStr = QActorUtils.unifyMsgContent(pengine, "thinkingRequest(DATA)","thinkingRequest(\"obstacle\")", guardVars ).toString();
	    		emit( "thinkingRequest", temporaryStr );
	    		};//actionseq
	    	}
	    	//onMsg 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("robotAnswer(\"clear\")");
	    	if( currentMessage != null && currentMessage.msgId().equals("robotAnswer") && 
	    		pengine.unify(curT, Term.createTerm("robotAnswer(VALUE)")) && 
	    		pengine.unify(curT, Term.createTerm( currentMessage.msgContent() ) )){ 
	    		//println("WARNING: variable substitution not yet fully implemented " ); 
	    		{//actionseq
	    		temporaryStr = "\"ROBOT TO BUSINESS LOGIC: Il campo � libero.\"";
	    		println( temporaryStr );  
	    		temporaryStr = QActorUtils.unifyMsgContent(pengine, "thinkingRequest(DATA)","thinkingRequest(\"clear\")", guardVars ).toString();
	    		emit( "thinkingRequest", temporaryStr );
	    		};//actionseq
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
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("finished(\"finishedCleaning\")");
	    	if( currentEvent != null && currentEvent.getEventId().equals("finished") && 
	    		pengine.unify(curT, Term.createTerm("finished(DATA)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			//println("WARNING: variable substitution not yet fully implemented " ); 
	    			{//actionseq
	    			temporaryStr = "rEnd";
	    			addRule( temporaryStr );  
	    			temporaryStr = "\"BUSINESS LOGIC: The floor is clean, stopping\"";
	    			println( temporaryStr );  
	    			};//actionseq
	    	}
	    	//onEvent 
	    	setCurrentMsgFromStore(); 
	    	curT = Term.createTerm("finished(\"cantFinish\")");
	    	if( currentEvent != null && currentEvent.getEventId().equals("finished") && 
	    		pengine.unify(curT, Term.createTerm("finished(DATA)")) && 
	    		pengine.unify(curT, Term.createTerm( currentEvent.getMsg() ) )){ 
	    			//println("WARNING: variable substitution not yet fully implemented " ); 
	    			{//actionseq
	    			temporaryStr = "rObstacle";
	    			addRule( temporaryStr );  
	    			temporaryStr = "\"BUSINESS LOGIC: couldn't finish because of obstacle or error\"";
	    			println( temporaryStr );  
	    			};//actionseq
	    	}
	    	temporaryStr = "\"STOP command received from console or triggered from application\"";
	    	println( temporaryStr );  
	    	temporaryStr = "rStop";
	    	addRule( temporaryStr );  
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
	    	temporaryStr = "rTempAndTimeOk";
	    	addRule( temporaryStr );  
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
	    	temporaryStr = "rTempKo";
	    	addRule( temporaryStr );  
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
	    	temporaryStr = "rTimeKo";
	    	addRule( temporaryStr );  
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
