%==============================================
% WorldTheory.pl for actor qabusinesslogicproban
%==============================================
/*
For a QActor as a singleton statically degined in the model
*/
myname(qatuqabusinesslogicproban).	%%old version (deprecated)
actorobj(qatuqabusinesslogicproban).	%% see registerActorInProlog18 in QActor

/*
For a QActor instance of name=Name dynamically created
*/
setActorName( Name ):-
	retract( myname(X) ),
	retract( actorobj(X) ),
	text_term( TS,Name ),
	text_concat("qatu",TS,NN),
	assert( myname(NN) ),
	assert( actorobj(NN) ).
createActor(Name, Class ):-
 	actorobj(A),
 	%% actorPrintln( createActor(A) ),
	A <- getName returns CurActorName,
	%% actorPrintln( createActor(CurActorName,Ctx,View) ),
 	A <- getQActorContext  returns Ctx,
 	%% actorPrintln( createActor(CurActorName,Ctx,View) ),
    A <- getOutputEnvView returns View,
	%% actorPrintln( createActor(CurActorName,Ctx,View) ),
 	Ctx <- addInstance(Ctx, Name,Class,View). 
/*
Name generator
*/	
value(nameCounter,0). 
newName( Prot, Name,N1 ) :-
	inc(nameCounter,1,N1),
	text_term(N1S,N1), 
	text_term(ProtS,Prot),
 	text_concat(ProtS,N1S,Name),
	replaceRule( instance( _, _, _ ), instance( Prot, N1, Name ) ).
newInstance( Prot, Name, P ) :-
 	text_term(N1S,P), 
	text_term(ProtS,Prot),
 	text_concat(ProtS,N1S,Name),
	replaceRule( instance( _, _, _ ), instance( Prot, P, Name ) ).

%%setPrologResult( timeOut(T) ):- setTimeOut( tout("prolog", tout(T))  ).

setPrologResult( Res ):-
	( retract( goalResult( _ ) ),!; true ),	%%remove previous goalResult (if any) 
	assert( goalResult(Res) ).
setTimeOut( tout( EV, A ) ):-
	( retract( tout( _._ ) ),!; true ),		    %%remove previous tout (if any) 
	assert( tout( EV, A ) ).

addRule( Rule ):-
	%%output( addRule( Rule ) ),
	assert( Rule ).
removeRule( Rule ):-
	retract( Rule ),
	%%output( removedFact(Rule) ),
	!.
removeRule( A  ):- 
	%%output( remove(A) ),
	retract( A :- B ),!.
removeRule( _  ).

replaceRule( Rule, NewRule ):-
	removeRule( Rule ),addRule( NewRule ).
	
setResult( A ):-
 	( retract( result( _ ) ),!; true ), %%remove previous result (if any)	
	assert( result( A ) ).

evalGuard( not(G) ) :-
	G, !, fail .
evalGuard( not(G) ):- !.
evalGuard( true ) :- !.
evalGuard( G ) :-  
	%stdout <- println( evalGuard( G ) ),
	G . 

output( M ):-stdout <- println( M ).
%-------------------------------------------------
%  TuProlo FEATURES of the QActor qabusinesslogicproban
%-------------------------------------------------
dialog( FileName ) :-  
	java_object('javax.swing.JFileChooser', [], Dialog),
	Dialog <- showOpenDialog(_),
	Dialog <- getSelectedFile returns File,
	File <- getName returns FileName. 		 

%% :- stdout <- println(  "hello from world theory of qabusinesslogicproban" ). 

%-------------------------------------------------
%  UTILITIES for TuProlog computations
%-------------------------------------------------
loop( N,Op ) :- 
	assign(loopcount,1),
	loop(loopcount,N,Op).

loop(I,N,Op) :-  
		value(I,V),
		%actorPrintln( values( I,V,N,Op ) ),
 		V =< N ,!,  
   		%actorPrintln( loop( I,V ) ),
      	actorOp(  Op  ) ,
		V1 is V + 1 ,
		assign(I,V1) ,   
		loop(I,N,Op).	%%tail recursion
loop(I,N,Op).

numOfFacts( F, N ) :-
	bagof( F,F,L ),
	length(L, N). 
	
eval( plus, V1, V2, R ):- R is V1+V2.
eval( minus, V1, V2, R ):- R is V1-V2.
eval( times, V1, V2, R ):- R is V1*V2.
eval( div, V1, V2, R ):- R is V1/V2.
eval( lt, X,Y ) :- X < Y.
eval( gt, X,Y ) :- X > Y.

divisible( V1, V2 ) :- 0 is mod(V1,V2). 

getVal( I, V ):-
	value(I,V), !.
getVal( I, failure ).

assign( I,V ):-
	retract( value(I,_) ),!,
	assert( value( I,V )).
assign( I,V ):-
	assert( value( I,V )).

inc(I,K,N):-
	value( I,V ),
	N is V + K,
	assign( I,N ).

actorPrintln( X ):- actorobj(A), text_term(XS,X), A  <- println( XS ).

%-------------------------------------------------
%  User static rules about qabusinesslogicproban
%------------------------------------------------- 
systemmodel( name( application),value( off)).
switchSystemState( on):-systemmodel( name( application),value( off)),replaceRule( systemmodel( NAME,value( off)),systemmodel( NAME,value( on))), ! .
switchSystemState( off):-systemmodel( name( application),value( on)),replaceRule( systemmodel( NAME,value( on)),systemmodel( NAME,value( off))), ! .
eval( ge,X,X).
eval( ge,X,V):-eval( gt,X,V).
curTemperatureValue( 10).
curTimeValue( 7).
evalTemperature( cold):-curTemperatureValue( V),eval( lt,V,25).
evalTemperature( hot):-curTemperatureValue( V),eval( ge,V,25), ! .
evalTime( good):-curTimeValue( V),output( curTimeValue( V)),eval( ge,V,7),eval( lt,V,18), ! .
/*
------------------------------------------------------------------------
testex :- actorPrintln( testex ),
java_catch(
	java_object('Counter', ['MyCounter'], c),
	%% java_object('java.util.ArrayList', [], l),
	[ (EEE,              actorPrintln( a )) ],
	  %% ('java.lang.Exception'(Cause, Message, _),              actorPrintln( b )),
	  %% ('java.lang.ClassNotFoundException'(Cause, Message, _), actorPrintln( c ))  ],
	actorPrintln( d )
).
------------------------------------------------------------------------
*/
loadTheory(T):-
 	%% actorPrintln( loadTheory(T) ),
	consult(T), !.
loadTheory(T):-
	actorPrintln( loadTheory(T, FAILURE) ).

opInfo( Op,F,A,L ):-
	functor( Op, F, A ),
	Op =.. L.

actorExec( Op ) :- actorOp( Op ).
actorOp( Op )   :- actorobj(Actor),
				   % actorPrintln( actorOp( Op  ) ),
				   java_catch(
						Actor <- Op returns R, 	%% R unbound if void
 						[ ( E,  output( actorOpFailure( E  ) ), setActorOpResult( Op,failure ), fail )],  				
  						setActorOpResult( Op,R )				%%executed in any way
					). 
actionResultOp( Op ):-
					% actorPrintln( actionResultOp( Op  ) ),
				   java_catch(
				   		%% actoropresult is bound to actorOp result (registered by Actor<-setActionResult)
						actoropresult <- Op returns R, 	%% R unbound if void
 						[ ( E, setActorOpResult( Op,failure ), fail )],  
  						setActorOpResult( Op,R )				%%executed in any way
					). 

%% setActorOpResulttt(Op, V ):-  actorPrintln( setActorOpResulttt(Op, V ) ),setActorOpResult( Op, V ).
setActorOpResult( Op, null ):-   !, storeActorOpResult( Op,void  ).
setActorOpResult( Op, V ):-   unbound(V),!, storeActorOpResult( Op,void  ).	  
setActorOpResult( Op, Res ):- text_term(Res,Term),!,storeActorOpResult( Op,Term  ).
setActorOpResult( Op, Res ):- %% Res is $obj_xxx	
	cvtToString(Res,ResStr),
	%% actorPrintln( actorOpDone( Op,ResStr  ) ),  
   	storeActorOpResult( Op, ResStr ).
storeActorOpResult( Op, R ):-
	%% actorPrintln( storeActorOpResult( Op,R  ) ),  
	( retract( actorOpDone( _,_ ) ),!; true ), %%remove previous actionResult (if any) 
	assert( actorOpDone( Op, R) ).


%% setActionResult : Actor register (in Java) the result under name 'actoropresult'
cvtToString( true , "true" ):-  %% true is the result of robot move operation
	setResult(true).
cvtToString( false , "false" ):-   
	setResult(false).
cvtToString( V , "void" ):-  
	unbound(V),!,
	actorobj(Actor),
	Actor <-  setActionResult( void ).
cvtToString( V , S ):-
	number(V),!,
	setResult(V),
	text_term( S, V ).
cvtToString( V , S ):-
	( retract( result(_) ),!; true ),		%eliminate any previous numeric result
	V <- toString returns S,
	actorobj(Actor),
	Actor <- setActionResult( V ),
	!.
actorOpResult( V )   :- result(V),!.	
actorOpResult( Res ) :- actoropresult <- toString returns Res, !.
%% actorOpResult( "" ).

androidConsult(T) :- actorobj(Actor), Actor <- androidConsult(T), !.
androidConsult(T) :- actorPrintln( failure(androidConsult) ).

/*
======================================================================
PLANS
======================================================================
*/
%%% ---------  runPlan	---------------	
runPlan(P):-
	actorobj(Actor),
 	initPlan(Actor,P).
initPlan(Actor,P) :-
	assign( iternum, iternum(P,1) ),
	execPlan(Actor,P,1).
execPlan(Actor,P,PC) :-
	plan(PC, P, S) ,
	%% actorPrintln( execPlan(S) ),
	(   runTheSentence(Actor,S),!
 	    ; true ), %%failure should be related to guards
	PC1 is PC + 1,
	execPlan(Actor,P,PC1).
execPlan(_,_,_).

%%% --------- loadPlan	--------------- 	
loadPlan( FName ):-
	actorobj(Actor),
	Actor <-  consultFromFile(  FName  ).

%%% ---------  Showplan	---------------		
executeCmd( Actor, move(showplan,P), Events, Plans ):-
	%% actorPrintln( showPlan(P) ),
	showPlan(P ).
showPlan( P ):-
	showPlan( P,1 ).
showPlan( P, PC ):-
	plan(PC, P, S) ,!,
	actorPrintln(  plan(PC, P, S )  ),
	PC1 is PC + 1,
	showPlan( P, PC1 ).
showPlan( _,_ ).

showPlan :-
	curPlan(P),
	showPlan(P).

%%% --------- storePlan	--------------- 	
storePlan( FName,P ):-
	actorobj( Actor ), 
	%% actorPrintln( storePlan( Actor, FName, P) ),
 	bagof( plan(PC, P, S) , plan(PC, pdefault, S) , L ),
	%% actorPrintln( storePlan( Actor , FName, L) ),
	Actor <-  writeListInFile( FName ,L ).

%%% --------- clearPlan	--------------- 	
clearPlan :-
	curPlan(P),
	%% class("it.unibo.robot.interpreter.RobotInterpreter") <- clearPC , 
	retractall( plan(PC, P, S) ),
	actorPrintln( clearPlan( P,done ) ).

/*
======================================================================
SENTENCES
======================================================================
*/
runTheSentence(Actor, sentence( not GUARD, MOVE, EVENTS, PLANS ) ):-
	GUARD , !, setAnswer( failure  ) .	
runTheSentence(Actor, sentence( not GUARD, MOVE, EVENTS, PLANS ) ):-
	!, runTheSentence(Actor, sentence( true, MOVE, EVENTS, PLANS ) ).
runTheSentence(Actor, sentence( GUARD, MOVE, EVENTS, PLANS ) ):-
 	 %% output(  runTheSentence111( GUARD, MOVE, EVENTS, PLANS ) ),
  	( GUARD = - G , !, retract(G),  ! ; GUARD, ! ),
 	 %% output(  preexecuteCmd( GUARD, MOVE, EVENTS, PLANS ) ),
  	( executeCmd(Actor, MOVE, EVENTS, PLANS, RESULT), !,
  	  %% output(  preexecuteCmdResult(RESULT) ),	
  	  setAnswer( RESULT  );	  
  	  %% output(  sentence4( failure ) ),
  	  setAnswer( done(MOVE,failure)  )
  	). 
runTheSentence(Actor, sentence( GUARD, MOVE, EVENTS, PLANS ) ):-   
	setAnswer( guard(GUARD,failed)  ).
runTheSentence(Actor, sentence( GUARD, GOAL, ANSWEREV  ) ) :- 
	%%actorPrintln(  goingToexecuteCmd( Actor, GOAL , ANSWEREV , RES) ),
	executeCmd( Actor, GOAL , ANSWEREV , RES ),
	setAnswer(RES). 

/*
-----------------------------------
Not implemented actions
-----------------------------------
*/	 
 
runTheSentence(Actor, sentence( GUARD, GOAL, DURATION, ANSWEREV, EVENTS, PLANS ) ) :-
 	actorPrintln(  "WARNING: actions asynchronous and reactive not implemented" ).
%% perhaps to remove  
solvePrologRule(GOAL):-
	actorPrintln(  solvePrologRule(GOAL) ),
	GOAL,!,
	setAnswer( GOAL  ).
solvePrologRule(GOAL):-
	%% actorPrintln(  sentence6sprry( GOAL  ) ),
	setAnswer( done(GOAL,failure)  ).

result("noresultyet").

%% Store answer (singleton) to current command
setAnswer( A ):-
	( retract( answer( _ ) ),!; true ),		    %%remove previous answer (if any)
	( retract( result( _ ) ),!; true ),		    %%remove previous result (if any)	
	addRule( result( A ) ),
 	addRule( answer( result(A) ) ).	

/*
======================================================================
MOVES
======================================================================
*/
%%% ---------  Actor move	---------------
executeCmd( Actor, move(robotmove,CMD,SPEED,DURATION,ANGLE), ENDEV, RES ):-
 	!,
 	mapCmdToMove(CMD,MOVE), 
	%% actorPrintln(  executeCmd(Actor,MOVE, SPEED, ANGLE, DURATION, ENDEV ) ),
	Actor <- execute(MOVE, SPEED, ANGLE, DURATION, ENDEV, '', '') returns AAR,
	AAR <- getResult returns RES.
executeCmd( Actor, move(robotmove,CMD,SPEED,DURATION,ANGLE), Events, Plans, RES ):-
 	!,
 	mapCmdToMove(CMD,MOVE), 
	%% actorPrintln(  executeCmd(Actor,MOVE, SPEED, ANGLE, DURATION, Events, Plans) ),
	Actor <- execute(MOVE, SPEED, ANGLE, DURATION, Events, Plans) returns AAR,
	AAR <- getResult returns RES.

%%% ---------  Play sound	---------------
executeCmd( Actor,  move(playsound(FNAME,DURATION)), Events, Plans, RES ):-
	%% output(  executeCmd1(Actor, playsound1( FNAME,DURATION ), Events, Plans) ),
	!,
	Actor <- playSound( FNAME, DURATION, Events, Plans ) returns AAR,
	AAR <- getResult returns RES.
executeCmd( Actor,  move(playsound(FNAME,DURATION,ENDEVENT)), Events, Plans, RES ):-
	%% output(  executeCmd2(Actor, move(playsound,FNAME,DURATION,ENDEVENT), Events, Plans) ),
	!,
	Actor <- playSound( FNAME, ENDEVENT, DURATION  ) returns AAR,
	AAR <- getResult returns RES.

%%% --------- Photo	---------------
executeCmd( Actor,  move( photo(DURATION,FNAME,ANSWEREVENT) ), Events, Plans, done(photo,FNAME) ):-
	%% actorPrintln(  executeCmd(Actor, photo( DURATION,FNAME,ANSWEREVENT )) ),
	!,
	Actor <- photo( FNAME, DURATION, ANSWEREVENT, Events, Plans ).
executeCmd( Actor, move( photo(DURATION,FNAME) ) , Events, Plans, done(photo,FNAME) ):-
	%% actorPrintln(  executeCmd(Actor, photo(DURATION,FNAME) )) ),
	Actor <- photo( FNAME, DURATION, '', Events, Plans ).
	
%%% ---------  Solve	---------------
/*
We cannot solve a goal in asynch way while pengine is engaged
Thus we ignore DURATION and Events, Plans
*/
executeCmd( Actor,   move(solve,GOAL,DURATION, ANSWEREVENT), Events, Plans, RES ):-
	%% actorPrintln(  executeCmd(Actor, move(solve,GOAL,DURATION, ANSWEREVENT) ) ),
	( GOAL, !, RES=GOAL ; RES = failure(GOAL) ),	
 	%%MARCH2017 Actor <- solveSentence(sentence(true, GOAL, 0, ANSWEREVENT, Events, Plans)) returns AAR,
 	%%MARCH2017 AAR   <- getResult returns RES,
 	setPrologResult(RES).

/*	%%MARCH2017 
executeCmd( Actor, move(solve,GOAL,DURATION), Events, Plans, RES ):-
	%% actorPrintln(  executeCmd(Actor, move(solve,GOAL,DURATION) ) ),
	Actor <- solveSentence(sentence(true, GOAL, 0, '', '', '')) returns AAR,
	AAR <- getResult returns RES,
	setPrologResult(RES).
*/	
	%% actorPrintln( result(GOAL,RES) ).
	%% The following  DOES NOT WORK since pengine is engaged
	%% Actor <- solveSentence(sentence(true, GOAL, DURATION, '', Events, Plans)) returns AAR.	
 	
%%% ---------  ActorOp	---------------	
executeCmd( Actor,  move(actorOp,OPERATION,T,_), Events, Plans, done(actorOp) ):-
	actorPrintln( actorOp(OPERATION )),
	Actor <- OPERATION. 		
%%% ---------  Println	---------------	
executeCmd( Actor,  move(print(ARG)), Events, Plans, done(print) ):-
	text_term(ARGS,ARG),
	actorPrintln( runplanmsg( ARGS ) ).
executeCmd( Actor,  printCurrentEvent(ARG), Events, Plans, done(printCurrentEvent) ):-
	Actor <- printCurrentEvent( ARG ). 
%%% ---------  addRule	---------------	
executeCmd( Actor,  addRule(ARG), Events, Plans, done(adRule) ):-
	%% actorPrintln(  move(addRule,ARG) ),
	addRule( ARG ).
%%% ---------  removeRule	---------------	
executeCmd( Actor,  removeRule(ARG), Events, Plans, done(adRule) ):-
	%% actorPrintln(  move(removeRule,ARG) ),
	removeRule( ARG ).
%%% ---------  Emit	---------------	
executeCmd( Actor,  move(emit,EVID,CONTENT), Events, Plans, done(emit,EVID) ):-
	actorPrintln(  move(emit,EVID,CONTENT) ),
	Actor <- emit( EVID,CONTENT ).
%%% ---------  Forward	---------------	
executeCmd( Actor,  move(forward, DEST, MSGID, MSGCNT), Events, Plans, done(forward,MSGID,dest(DEST)) ):-
	actorPrintln(  forward( 1,Actor,MSGID,DEST,MSGCNT ) ),	
	text_term(A1,DEST),  	 
%%%	text_concat("''",A1,A2),
%%%	text_concat(A2,"''",DESTSTR),
 	Actor <- forwardFromProlog( MSGID , DEST , MSGCNT ). %%% DESTSTR
%%% ---------  sense	---------------	
executeCmd( Actor, senseEvent(Tout, Events, Plans), E,P, done(senseEvent) ):-
	output( senseEvent(Events, Plans, E,P) ),
	Actor <- senseEvents(Tout, Events, Plans, E,P ).	%%blocks


%%% ---------  plan	---------------	
executeCmd( Actor, move(runplan,P), Events, Plans,done(runplan(P)) ):-
 	%% actorPrintln( runplan(P) ),
	execPlan(Actor,P,1).
executeCmd( Actor, move(resumePlan), Events, Plans,done(resume(P)) ).
executeCmd( Actor, move(switchplan(PLAN)), Events, Plans,done(switchplan(PLAN)) ):-
	output( switchplan(Actor, PLAN) ),
	initPlan(Actor,PLAN).
executeCmd( Actor, repeatplan(0), Events, Plans,done(repeatplan(0)) ):-
	value(iternum, iternum(P,_)),
	execPlan(Actor, P, 1).
executeCmd( Actor, repeatplan(N), Events, Plans,done(repeatplan(0)) ):-
	value(iternum, iternum(P,PN)),
	PN1 is PN + 1,
	( PN1 < N, !, execPlan(Actor, PLAN, 1); true ).
%%% ---------  robot moves	---------------	
executeCmd( Actor, move(M), Events, Plans,done(M) ):-
	output( M ),
	robotMove( M, CMD,S,A,T ),!,
 	Actor <- execute(CMD,S,A,T,Events, Plans).
robotMove( robotForward(SPEED,ANGLE,TIME),robotforward,SPEED,ANGLE,TIME  ).
robotMove( robotBackward(SPEED,ANGLE,TIME),robotbackward,SPEED,ANGLE,TIME  ).
robotMove( robotRight(SPEED,ANGLE,TIME),robotright,SPEED,ANGLE,TIME ).
robotMove( robotLeft(SPEED,ANGLE,TIME),robotleft,SPEED,ANGLE,TIME ).
robotMove( robotStop(SPEED,ANGLE,TIME),robotstop,SPEED,ANGLE,TIME ).
	
%%% ---------  collector (default)  --------------	
%% executeCmd( Actor, MOVE, Events, Plans,done(MOVE) ):-
%% 	output( collector(MOVE) ),
%% 	Actor <- MOVE.


/*
%-------------------------------------------------
%  Some predefined code
%------------------------------------------------- 
*/
fibo(0,1).
fibo(1,1).
fibo(2,1).
fibo(3,2).
fibo(4,3).
fibo(I,N) :- I < 5, !, fail.	%%%JULY2017, if N bound
fibo(I,N) :- V1 is I-1, V2 is I-2,
  fibo(V1,N1), fibo(V2,N2),
  N is N1 + N2.

%% Fibonacci with cache (to be used in guards)

fib(V):-
	fibmemo( V,N ),!,
	actorPrintln( fib_a(V,N) ).
fib(V):-
	fibWithCache(V,N), 					
	actorPrintln( fib_b(V,N) ).

fib( V,R ) :-
	fibWithCache(V,R).

fibmemo( 0,1 ).
fibmemo( 1,1 ).
fibmemo( 2,1 ).
fibmemo( 3,2 ).
fibWithCache( V,N ) :-
	fibmemo( V,N ),!.
fibWithCache( V,N ) :- V < 4, !, fail. %%%JULY2017, if N bound
fibWithCache( V,N ) :-
	V1 is V-1, V2 is V-2,
  	fibWithCache(V1,N1), fibWithCache(V2,N2),
  	N is N1 + N2,
	%% actorPrintln( fib( V,N ) ),
	assert( fibmemo( V,N ) ).
/*
------------------------------------------------------------
initialize
------------------------------------------------------------
*/
initialize  :-  
	actorobj(Actor),
 	output( worlTheoryLoaded(Actor) ).
 
%% :- initialization(initialize).
