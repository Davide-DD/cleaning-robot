%====================================================================================
% Context robotCtx3  SYSTEM-configuration: file it.unibo.robotCtx3.cleaningRobotAnalisiRequisiti3.pl 
%====================================================================================
context(consolectx3, "localhost",  "TCP", "8019" ).  		 
context(robotctx3, "localhost",  "TCP", "8021" ).  		 
%%% -------------------------------------------
qactor( qaconsole3 , consolectx3, "it.unibo.qaconsole3.MsgHandle_Qaconsole3"   ). %%store msgs 
qactor( qaconsole3_ctrl , consolectx3, "it.unibo.qaconsole3.Qaconsole3"   ). %%control-driven 
qactor( qaenvironment , robotctx3, "it.unibo.qaenvironment.MsgHandle_Qaenvironment"   ). %%store msgs 
qactor( qaenvironment_ctrl , robotctx3, "it.unibo.qaenvironment.Qaenvironment"   ). %%control-driven 
qactor( qabusinesslogic , robotctx3, "it.unibo.qabusinesslogic.MsgHandle_Qabusinesslogic"   ). %%store msgs 
qactor( qabusinesslogic_ctrl , robotctx3, "it.unibo.qabusinesslogic.Qabusinesslogic"   ). %%control-driven 
qactor( qaproximitysensor , robotctx3, "it.unibo.qaproximitysensor.MsgHandle_Qaproximitysensor"   ). %%store msgs 
qactor( qaproximitysensor_ctrl , robotctx3, "it.unibo.qaproximitysensor.Qaproximitysensor"   ). %%control-driven 
qactor( qarobot3 , robotctx3, "it.unibo.qarobot3.MsgHandle_Qarobot3"   ). %%store msgs 
qactor( qarobot3_ctrl , robotctx3, "it.unibo.qarobot3.Qarobot3"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

