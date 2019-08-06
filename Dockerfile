FROM centos

MAINTAINER Alan P Jiang jiangpeng@agilean.cn

#VOLUME /data

VOLUME /data

ADD minio minio

RUN bash -c 'touch /minio'

ENV MINIO_ACCESS_KEY=${MINIO_ACCESS_KEY}
ENV MINIO_SECRET_KEY=${MINO_SECRET_KEY}
  
EXPOSE 9000

ENTRYPOINT /minio server /data