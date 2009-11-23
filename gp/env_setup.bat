

set PROPERTIES=properties
set LOG4J=lib\log4j-1.2.15.jar
set GP=lib\gp.jar
set MATH=lib\math.jar
set JFREE=lib\jfreechart-1.0.13.jar
set COMMON=lib\jcommon-1.0.16.jar
set PATH=%~dp0;%PATH%

set classpath=%PROPERTIES%;%LOG4J%;%MATH%;%JFREE%;%COMMON%;%GP%
echo %classpath%
