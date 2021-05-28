FROM java
COPY "./universal/target/ChatDirector-0.2.5-universal.jar" ChatDirector.jar
CMD java -jar ChatDirector.jar config/config.yml