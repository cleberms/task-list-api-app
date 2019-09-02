FROM docker.elastic.co/logstash/logstash:6.2.4

USER root

ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD ./config/logstash.yml /usr/share/logstash/config/
ADD ./pipeline/logstash.conf /usr/share/logstash/pipeline/

RUN chown logstash:logstash config/logstash.yml && \
    chown logstash:logstash pipeline/logstash.conf

USER logstash
