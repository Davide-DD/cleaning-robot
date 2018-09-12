%====================================================================================
% Context robotCtx  SYSTEM-configuration: file it.unibo.robotCtx.cleaningRobotAnalisiRequisiti.pl 
%====================================================================================
context(consolectx, "localhost",  "TCP", "8019" ).  		 
context(robotctx, "localhost",  "TCP", "8021" ).  		 
%%% -------------------------------------------
qactor( qaconsole , consolectx, "it.unibo.qaconsole.MsgHandle_Qaconsole"   ). %%store msgs 
qactor( qaconsole_ctrl , consolectx, "it.unibo.qaconsole.Qaconsole"   ). %%control-driven 
qactor( qarobot , robotctx, "it.unibo.qarobot.MsgHandle_Qarobot"   ). %%store msgs 
qactor( qarobot_ctrl , robotctx, "it.unibo.qarobot.Qarobot"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

