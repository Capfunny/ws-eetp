rem set CONF_DIR=C:\_Work_\_JAVA_\ws-eetp\complete\config_custom
rem  java -Dspring.config.location=%CONF_DIR%/ -jar target\ws-eetp-0.1.0.jar tratata
call catalina.bat stop
call mvn clean package
java -jar target\ws-eetp-0.1.0.jar  %1

pause