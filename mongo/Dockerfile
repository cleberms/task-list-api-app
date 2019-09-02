FROM mongo:latest

ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /data/db2 \
    && echo "dbpath = /data/db2" > /etc/mongod.conf.orig \
    && chown -R mongodb:mongodb /data/db2
    
COPY . /data/db2

VOLUME /data/db2

CMD ["mongod", "--config", "/etc/mongod.conf.orig", "--smallfiles"]