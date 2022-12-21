FROM java
COPY "./universal/target/ChatDirector-0.2.17-universal.jar" ChatDirector.jar
CMD java -jar ChatDirector.jar config/config.yml