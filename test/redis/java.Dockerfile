FROM java
COPY "./universal/target/ChatDirector-0.3.0-universal.jar" ChatDirector.jar
CMD java -jar ChatDirector.jar config/config.yml