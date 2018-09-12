%====================================================================================
% Context consoleCtx4  SYSTEM-configuration: file it.unibo.consoleCtx4.cleaningRobotAnalisiRequisiti4.pl 
%====================================================================================
context(consolectx4, "localhost",  "TCP", "8019" ).  		 
context(robotctx4, "localhost",  "TCP", "8021" ).  		 
%%% -------------------------------------------
qactor( qaconsole4 , consolectx4, "it.unibo.qaconsole4.MsgHandle_Qaconsole4"   ). %%store msgs 
qactor( qaconsole4_ctrl , consolectx4, "it.unibo.qaconsole4.Qaconsole4"   ). %%control-driven 
qactor( qaenvironment , robotctx4, "it.unibo.qaenvironment.MsgHandle_Qaenvironment"   ). %%store msgs 
qactor( qaenvironment_ctrl , robotctx4, "it.unibo.qaenvironment.Qaenvironment"   ). %%control-driven 
qactor( qabusinesslogic , robotctx4, "it.unibo.qabusinesslogic.MsgHandle_Qabusinesslogic"   ). %%store msgs 
qactor( qabusinesslogic_ctrl , robotctx4, "it.unibo.qabusinesslogic.Qabusinesslogic"   ). %%control-driven 
qactor( qaproximitysensor , robotctx4, "it.unibo.qaproximitysensor.MsgHandle_Qaproximitysensor"   ). %%store msgs 
qactor( qaproximitysensor_ctrl , robotctx4, "it.unibo.qaproximitysensor.Qaproximitysensor"   ). %%control-driven 
qactor( qarobot4 , robotctx4, "it.unibo.qarobot4.MsgHandle_Qarobot4"   ). %%store msgs 
qactor( qarobot4_ctrl , robotctx4, "it.unibo.qarobot4.Qarobot4"   ). %%control-driven 
qactor( qaled , robotctx4, "it.unibo.qaled.MsgHandle_Qaled"   ). %%store msgs 
qactor( qaled_ctrl , robotctx4, "it.unibo.qaled.Qaled"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

