FROM java
COPY "./universal/target/ChatDirector-0.2.11-universal.jar" ChatDirector.jar
CMD java -jar ChatDirector.jar config/config.yml