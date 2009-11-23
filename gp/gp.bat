@echo off

@REM Bootstrap values ...
call "%~dp0env_setup.bat" %*

java -classpath %CLASSPATH% gp.GeneticProgramming
pause


