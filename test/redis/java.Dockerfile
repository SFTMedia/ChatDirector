FROM java
COPY "./universal/target/ChatDirector-0.2.14-universal.jar" ChatDirector.jar
CMD java -jar ChatDirector.jar config/config.yml