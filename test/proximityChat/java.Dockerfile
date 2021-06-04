FROM java as config_parse
COPY "./test/proximityChat/config.yml" raw_config.yml
ARG DISCORD_TOKEN
ARG MYSQL_USER
ARG MYSQL_PASSWORD
ARG PROXIMITY_CHAT_DISCORD_CATEGORY
RUN cat raw_config.yml \
    | sed "s/\${DISCORD_TOKEN}/${DISCORD_TOKEN}/"\
    | sed "s/\${MYSQL_USER}/${MYSQL_USER}/"\
    | sed "s/\${MYSQL_PASSWORD}/${MYSQL_PASSWORD}/"\
    | sed "s/\${PROXIMITY_CHAT_DISCORD_CATEGORY}/${PROXIMITY_CHAT_DISCORD_CATEGORY}/"\
    | tee config.yml
FROM java
COPY "./specific/proximityChat/target/ChatDirector-*proximityChat.jar" ChatDirector.jar
COPY --from=config_parse config.yml config.yml
CMD [ "java","-jar","ChatDirector.jar" ,"./config.yml"]