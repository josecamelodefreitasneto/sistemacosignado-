curl -v -u developer:123456 --upload-file pom.xml http://nexus.cooperforte.coop/repository/maven-releases/net/bytebuddy/1.9.5/byte-buddy-1.9.5.pom
curl -v -u developer:123456 --upload-file pom.xml http://nexus.cooperforte.coop/repository/maven-snapshots/net/bytebuddy/1.9.5/byte-buddy-1.9.5.pom

curl -v -u developer:123456 --upload-file pom.xml http://nexus.cooperforte.coop/repository/maven-releases/com/microsoft/sqlserver/sqljdbc4/4.0/sqljdbc4-4.0.pom


http://apjon:8081/nexus/content/repositories/releases

curl -v -u developer:123456 --upload-file pom.xml http://apjon:8081/nexus/content/repositories/releases/com/microsoft/sqlserver/sqljdbc4/4.0/sqljdbc4-4.0.pom
curl -v -u developer:123456 --upload-file pom.xml http://apjon:8081/nexus/content/repositories/snapshots/com/microsoft/sqlserver/sqljdbc4/4.0/sqljdbc4-4.0.pom


/opt/desen/java/m2/com/microsoft/sqlserver/sqljdbc4/4.0


mvn deploy:deploy-file -DgroupId=com.microsoft -DartifactId=sqljdbc4 -Dversion=4.0 -DgeneratePom=true -Dpackaging=jar -DrepositoryId=nexus -Durl=http://apjon:8081/nexus/content/repositories/releases -Dfile=sqljdbc4-4.0.jar
