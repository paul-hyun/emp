@ECHO OFF
set CURRENT_DIR=%CD%\database

set SERVICE_EXEC="%CURRENT_DIR%\bin\pg_ctl.exe"
set SERVICE_NAME=eOrange_db
set SERVICE_DESCRIPTION=eOrange_db

REM IF [%1]==[]		GOTO USAGE
IF [%1]==[install]	GOTO INSTALL
IF [%1]==[uninstall]	GOTO UNINSTALL
IF [%1]==[start]	GOTO START
IF [%1]==[stop]		GOTO STOP

:USAGE
	ECHO Usage:    %0  [install^|uninstall^|start^|stop]
GOTO:EOF

:INSTALL
	ECHO install %SERVICE_DESCRIPTION% ......

	%SERVICE_EXEC% register -N "%SERVICE_NAME%" -D "%CURRENT_DIR%/data"
GOTO:EOF

:UNINSTALL
	ECHO uninstall %SERVICE_DESCRIPTION% ......

	%SERVICE_EXEC% unregister -N "%SERVICE_NAME%"
GOTO:EOF

:START
	ECHO start %SERVICE_DESCRIPTION% ......

	net start %SERVICE_NAME%
GOTO:EOF


:STOP
	ECHO stop %SERVICE_DESCRIPTION% ......

	net stop %SERVICE_NAME%
GOTO:EOF
