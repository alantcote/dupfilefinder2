@echo off

setlocal

set APP_JAR=dupfilefinder2-0.0.1-SNAPSHOT.jar

start /B javaw --module-path lib --add-modules clutilities,javafx.fxml,javafx.web,org.apache.commons.collections4 -jar %APP_JAR%
rem java --module-path lib --add-modules clutilities,javafx.fxml,javafx.web,org.apache.commons.collections4 -jar %APP_JAR%

endlocal