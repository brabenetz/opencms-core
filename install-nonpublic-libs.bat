echo off
echo Installing Non-Puplic Libraries to the local Maven-Repository....

echo Installing ibm.as400-Driver to the local Maven-Repository....
call mvn install:install-file -DgroupId=com.ibm.as400 -DartifactId=jt400 -Dversion=6.0 -Dpackaging=jar -Dfile=webapp/setup/database/as400/jt400.jar

echo Installing ibm.db2-Driver to the local Maven-Repository....
call mvn install:install-file -DgroupId=com.ibm.db2.jcc -DartifactId=db2jcc -Dversion=V9 -Dpackaging=jar -Dfile=webapp/setup/database/db2/db2jcc_v9.jar
call mvn install:install-file -DgroupId=com.ibm.db2.jcc -DartifactId=db2jcc_license_cu -Dversion=V9 -Dpackaging=jar -Dfile=webapp/setup/database/db2/db2jcc_license_cu.jar

echo Installing oracle-Driver to the local Maven-Repository....
call mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc14 -Dversion=10.2.0.3 -Dpackaging=jar -Dfile=webapp/setup/database/oracle/ojdbc14-10.2.0.3.jar



echo Installing guava-r08-gwt to the local Maven-Repository....
echo Guava 10 is available in central repo: http://repo2.maven.org/maven2/com/google/guava/guava-gwt/10.0/
call mvn install:install-file -DgroupId=com.google.guava -DartifactId=guava-gwt -Dversion=r08 -Dpackaging=jar -Dfile=lib/runtime/guava-r08-gwt.jar

pause
