@echo off

setlocal

set APP_JAR=dupfilefinder2-0.0.1-SNAPSHOT.jar

start /B javaw --module-path lib --add-modules clutilities,javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media -jar %APP_JAR%
rem java --module-path lib --add-modules java.prefs,javafx.base,javafx.graphics,javafx.controls,javafx.fxml -jar %APP_JAR%

endlocal