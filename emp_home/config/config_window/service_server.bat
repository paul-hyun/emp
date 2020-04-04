@ECHO OFF
set CURRENT_DIR=%CD%

set SERVICE_HOME="%CURRENT_DIR%"
set SERVICE_EXEC="%CURRENT_DIR%\eOrange_server.jar"
set SERVICE_MNGR="%CURRENT_DIR%\eOrange_serverw.jar"

REM IF [%1]==[]		GOTO USAGE
IF [%1]==[start]	GOTO START
IF [%1]==[stop]		GOTO STOP
IF [%1]==[start_w]	GOTO START_W
IF [%1]==[stop_w]	GOTO STOP_W

:USAGE
	ECHO Usage:    %0  [start^|stop^|start_w^|stop_w]
GOTO:EOF

:START
	start javaw -Dfile.encoding=UTF-8 -jar %SERVICE_EXEC%
GOTO:EOF

:STOP
	start javaw -Dfile.encoding=UTF-8 -jar %SERVICE_EXEC% stop
GOTO:EOF

:START_W
	start javaw -Dfile.encoding=UTF-8 -jar %SERVICE_MNGR% hidden
GOTO:EOF

:STOP_W
	start javaw -Dfile.encoding=UTF-8 -jar %SERVICE_MNGR% stop
GOTO:EOF

