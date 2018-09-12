%====================================================================================
% Context robotCtx2  SYSTEM-configuration: file it.unibo.robotCtx2.cleaningRobotAnalisiRequisiti2.pl 
%====================================================================================
context(consolectx2, "localhost",  "TCP", "8019" ).  		 
context(robotctx2, "localhost",  "TCP", "8021" ).  		 
%%% -------------------------------------------
qactor( qaconsole2 , consolectx2, "it.unibo.qaconsole2.MsgHandle_Qaconsole2"   ). %%store msgs 
qactor( qaconsole2_ctrl , consolectx2, "it.unibo.qaconsole2.Qaconsole2"   ). %%control-driven 
qactor( qaenvironment , robotctx2, "it.unibo.qaenvironment.MsgHandle_Qaenvironment"   ). %%store msgs 
qactor( qaenvironment_ctrl , robotctx2, "it.unibo.qaenvironment.Qaenvironment"   ). %%control-driven 
qactor( qarobot2 , robotctx2, "it.unibo.qarobot2.MsgHandle_Qarobot2"   ). %%store msgs 
qactor( qarobot2_ctrl , robotctx2, "it.unibo.qarobot2.Qarobot2"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

