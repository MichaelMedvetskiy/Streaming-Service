@echo off
:start
cmd /C mvn clean package exec:java -Dexec.mainClass="streamClient.StartNewClient" 
goto start
