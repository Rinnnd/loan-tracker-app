@echo off
rem Start Gradle Wrapper
set DIR=%~dp0
"%DIR%gradle\wrapper\gradle-wrapper.jar" %*
if not exist "%DIR%gradle\wrapper\gradle-wrapper.jar" (
    echo Gradle wrapper jar not found, downloading...
    powershell -Command "& { Invoke-WebRequest -Uri 'https://github.com/gradle/gradle/raw/master/gradle/wrapper/gradle-wrapper.jar' -OutFile '%DIR%gradle\wrapper\gradle-wrapper.jar' }"
)
java -jar "%DIR%gradle\wrapper\gradle-wrapper.jar" %*
