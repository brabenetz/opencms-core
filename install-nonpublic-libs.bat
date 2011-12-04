echo off
echo Installing Non-Puplic Libraries to the local Maven-Repository....
call mvn install:install-file -DgroupId=com.alkacon -DartifactId=alkacon-diff -Dversion=0.9.2 -Dpackaging=jar -Dfile=lib/runtime/alkacon-diff-0.9.2.jar
call mvn install:install-file -DgroupId=com.alkacon -DartifactId=alkacon-simapi -Dversion=0.9.8 -Dpackaging=jar -Dfile=lib/runtime/alkacon-simapi-0.9.8.jar


echo Installing ibm.as400-Driver to the local Maven-Repository....
call mvn install:install-file -DgroupId=com.ibm.as400 -DartifactId=jt400 -Dversion=6.0 -Dpackaging=jar -Dfile=webapp/setup/database/as400/jt400.jar

echo Installing ibm.db2-Driver to the local Maven-Repository....
call mvn install:install-file -DgroupId=com.ibm.db2.jcc -DartifactId=db2jcc -Dversion=V9 -Dpackaging=jar -Dfile=webapp/setup/database/db2/db2jcc_v9.jar
call mvn install:install-file -DgroupId=com.ibm.db2.jcc -DartifactId=db2jcc_license_cu -Dversion=V9 -Dpackaging=jar -Dfile=webapp/setup/database/db2/db2jcc_license_cu.jar

echo Installing oracle-Driver to the local Maven-Repository....
call mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc14 -Dversion=10.2.0.3 -Dpackaging=jar -Dfile=webapp/setup/database/oracle/ojdbc14-10.2.0.3.jar



echo Installing guava-r08-gwt to the local Maven-Repository....
call mvn install:install-file -DgroupId=com.google.guava -DartifactId=guava-gwt -Dversion=r08 -Dpackaging=jar -Dfile=lib/runtime/guava-r08-gwt.jar
call mvn install:install-file -DgroupId=com.google.guava -DartifactId=guava -Dversion=r08 -Dpackaging=jar -Dclassifier=gwt -Dfile=lib/runtime/guava-r08-gwt.jar

pause
