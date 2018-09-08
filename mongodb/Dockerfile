FROM mongo:latest
MAINTAINER Duy Anh <duyanhnguyenngoc97@gmail.com>

ADD init.sh /init.sh
ADD ./dump /

#ENV http_proxy=http://10.10.10.10:8080
#ENV https_proxy=http://10.10.10.10:8080

RUN \
 chmod +x /init.sh && \
 apt-get update && apt-get dist-upgrade -y && \
 apt-get install psmisc -y -q && \
 apt-get autoremove -y && apt-get clean && \
 rm -rf /var/cache/* && rm -rf /var/lib/apt/lists/*

ENTRYPOINT ["/init.sh"]
