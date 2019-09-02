FROM docker.elastic.co/kibana/kibana:6.2.4

USER root

ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD ./config/kibana.yml /usr/share/kibana/config/

RUN chown kibana:kibana config/kibana.yml

USER kibana
