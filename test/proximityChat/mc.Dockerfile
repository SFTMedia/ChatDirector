FROM java:alpine as build
RUN apk update
RUN apk add jq wget
ARG MC_VERSION=latest
ARG PAPER_BUILD=latest
RUN URL=https://papermc.io/api/v2/projects/paper;\
if [ ${MC_VERSION} = latest ];then\
  MC_VERSION=$(wget -qO - $URL | jq -r '.versions[-1]');\
fi; \
URL=${URL}/versions/${MC_VERSION}; \
if [ ${PAPER_BUILD} = latest ]; \
then \
  PAPER_BUILD=$(wget -qO - $URL | jq '.builds[-1]'); \
fi ;\
JAR_NAME=paper-${MC_VERSION}-${PAPER_BUILD}.jar; \
URL=${URL}/builds/${PAPER_BUILD}/downloads/${JAR_NAME}; \
if [ ! -e ${JAR_NAME} ]; \
then \
  rm -f *.jar;\
  wget ${URL} -qO paper.jar;\
  if [ ! -e eula.txt ];\
  then \
    java -jar paper.jar;\
    sed -i 's/false/true/g' eula.txt;\
  fi;\
fi

FROM java 
WORKDIR /server
COPY --from=build paper.jar .
COPY --from=build eula.txt .
CMD [ "java","-jar","paper.jar","nogui" ]
VOLUME [ "/server/world" , "/server/plugins"]