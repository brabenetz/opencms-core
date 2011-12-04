echo off
call set ANT_OPTS=-Xms128m -Xmx1024m -XX:PermSize=128m -XX:MaxPermSize=512m
call echo ANT_OPTS: %ANT_OPTS%
call set MAVEN_OPTS=-Xms128m -Xmx1024m -XX:PermSize=128m -XX:MaxPermSize=512m
call echo MAVEN_OPTS: %MAVEN_OPTS%
call ant clean maven-install-init-all
pause
