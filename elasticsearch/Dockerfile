FROM docker.elastic.co/elasticsearch/elasticsearch:6.2.4

USER root

ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD ./config/elasticsearch.yml /usr/share/elasticsearch/config/

RUN chown elasticsearch:elasticsearch config/elasticsearch.yml

USER elasticsearch
