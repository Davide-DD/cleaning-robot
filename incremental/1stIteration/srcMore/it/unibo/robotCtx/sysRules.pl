%==============================================
% DEFINED BY THE SYSTEM DESIGNER
% CONTEXT HANDLING UTILTY RULES
%==============================================
getCtxNames(CTXNAMES) :-
	findall( NAME, context( NAME, _, _, _ ), CTXNAMES).
getCtxPortNames(PORTNAMES) :-
	findall( PORT, context( _, _, _, PORT ), PORTNAMES).
getTheContexts(CTXS) :-
	findall( context( CTX, HOST, PROTOCOL, PORT ), context( CTX, HOST, PROTOCOL, PORT ), CTXS).
getTheActors(ACTORS) :-
	findall( qactor( A, CTX, CLASS ), qactor( A, CTX, CLASS ), ACTORS).
	
getTheActors(ACTORS,CTX) :-
	findall( qactor( A, CTX, CLASS ), qactor( A, CTX, CLASS ),   ACTORS).
getTheHandlers(ACTORS,CTX) :-
	findall( eventhandler( A, CTX, CLASS, EVENTS ), eventhandler( A, CTX, CLASS, EVENTS ),   ACTORS).
getTheRobots(ACTORS,CTX) :-
	findall( qactor( A, CTX, CLASS ), qactor( A, CTX, CLASS, robot ),   ACTORS).

insertActorOnce( NAME, CTX ) :-
	qactor( NAME , CTX ), !.
insertActorOnce( NAME, CTX ) :-	
 	assert( qactor( NAME , CTX ) ).

removeActor( NAME, CTX ):-
	retract( qactor( NAME , CTX ) ).
removeActor( NAME, CTX ).

getCtxHost( NAME, HOST )  :- context( NAME, HOST, PROTOCOL, PORT ).
getCtxPort( NAME,  PORT ) :- context( NAME, HOST, PROTOCOL, PORT ).
getCtxProtocol( NAME,  PROTOCOL ) :- context( NAME, HOST, PROTOCOL, PORT ).

getMsgId( msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM ) , MSGID  ).
getMsgType( msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM ) , MSGTYPE ).
getMsgSenderId( msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM ) , SENDER ).
getMsgReceiverId( msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM ) , RECEIVER ).
getMsgContent( msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM ) , CONTENT ).
getMsgNum( msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM ) , SEQNUM ).

checkMsg( MSG, MSGLIST, PLANLIST, RES ) :- 
	%%stdout <- println( checkMsg( MSG, MSGLIST, PLANLIST, RES ) ),
	checkTheMsg(MSG, MSGLIST, PLANLIST, RES).	
checkTheMsg( MSG, [], _, dummyPlan ).
checkTheMsg( msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM ), [ MSGID | _ ], [ PLAN | _ ], PLAN ):-!.
checkTheMsg( MSG, [_|R], [_|P], RES ):- 
	%%stdout <- println( checkMsg( MSG, R, P, RES ) ),
	checkTheMsg(MSG, R, P, RES).

msgContentToPlan( MSG, CONTENTLIST,PLANLIST,RES ):-
	%stdout <- println( msgContentToPlan( MSG, CONTENTLIST,PLANLIST,RES) ),
	msgContentToAPlan( MSG, CONTENTLIST,PLANLIST,RES ).
msgContentToAPlan( MSG, [], _, dummyPlan ).
msgContentToAPlan( msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM ), [ CONTENT | _ ], [ PLAN | _ ], PLAN ):-!.
msgContentToAPlan( event(  CONTENT  ), [ CONTENT | _ ], [ PLAN | _ ], PLAN ):-!.
msgContentToAPlan( MSG, [_|R], [_|P], RES ):- 
	%stdout <- println( msgContentToAPlan( MSG, R, P, RES ) ),
	msgContentToPlan(MSG, R, P, RES).	


%==============================================
% MEMENTO
%==============================================
%%% :- stdout <- println( hello ).
%%% --------------------------------------------------
% context( NAME, HOST, PROTOCOL, PORT )
% PROTOCOL : "TCP" | "UDP" | "SERIAL" | "PROCESS" | ...
%%% --------------------------------------------------

%%% --------------------------------------------------
% msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM )
% MSGTYPE : dispatch request answer
%%% --------------------------------------------------

