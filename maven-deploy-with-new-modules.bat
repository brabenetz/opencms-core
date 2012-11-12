echo off
call set ANT_OPTS=-Xms128m -Xmx2048m -XX:PermSize=128m -XX:MaxPermSize=1024m
call echo ANT_OPTS: %ANT_OPTS%
call set MAVEN_OPTS=-Xms128m -Xmx2048m -XX:PermSize=128m -XX:MaxPermSize=1024m
call echo MAVEN_OPTS: %MAVEN_OPTS%
call ant clean maven-deploy-with-new-modules
pause
