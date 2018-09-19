%====================================================================================
% Context architectureModelCtx  SYSTEM-configuration: file it.unibo.architectureModelCtx.cleaningRobot.pl 
%====================================================================================
context(architecturemodelctx, "localhost",  "TCP", "8019" ).  		 
%%% -------------------------------------------
qactor( qaappl , architecturemodelctx, "it.unibo.qaappl.MsgHandle_Qaappl"   ). %%store msgs 
qactor( qaappl_ctrl , architecturemodelctx, "it.unibo.qaappl.Qaappl"   ). %%control-driven 
qactor( qaled , architecturemodelctx, "it.unibo.qaled.MsgHandle_Qaled"   ). %%store msgs 
qactor( qaled_ctrl , architecturemodelctx, "it.unibo.qaled.Qaled"   ). %%control-driven 
qactor( qarobot , architecturemodelctx, "it.unibo.qarobot.MsgHandle_Qarobot"   ). %%store msgs 
qactor( qarobot_ctrl , architecturemodelctx, "it.unibo.qarobot.Qarobot"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

