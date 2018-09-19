%====================================================================================
% Context ctxTest  SYSTEM-configuration: file it.unibo.ctxTest.testSelfMsg.pl 
%====================================================================================
context(ctxtest, "localhost",  "TCP", "8019" ).  		 
%%% -------------------------------------------
qactor( qamockmsgsender , ctxtest, "it.unibo.qamockmsgsender.MsgHandle_Qamockmsgsender"   ). %%store msgs 
qactor( qamockmsgsender_ctrl , ctxtest, "it.unibo.qamockmsgsender.Qamockmsgsender"   ). %%control-driven 
qactor( qaselfmsg , ctxtest, "it.unibo.qaselfmsg.MsgHandle_Qaselfmsg"   ). %%store msgs 
qactor( qaselfmsg_ctrl , ctxtest, "it.unibo.qaselfmsg.Qaselfmsg"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

