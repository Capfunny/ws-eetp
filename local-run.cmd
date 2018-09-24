rem set CONF_DIR=C:\_Work_\_JAVA_\ws-eetp\complete\config_custom
rem  java -Dspring.config.location=%CONF_DIR%/ -jar target\ws-eetp-0.1.0.jar tratata
call catalina.bat stop
call mvn -Dmaven.test.skip clean package
set JAVA_HOME=C:\Program Files\Java\jre1.8.0_171
rem "%JAVA_HOME%\bin\java" -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar target\ws-eetp-0.1.0.jar  %1
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000 -jar target\ws-eetp-0.1.0.jar

pause