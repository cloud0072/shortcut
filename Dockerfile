# Docker image for springboot file run
# VERSION 1.0.0

# 基础镜像使用java
FROM openjdk:8

# 作者
MAINTAINER caolei <352419394@qq.com>

# 端口
EXPOSE 9528

# 将jar包添加到容器中并更名为app.jar
COPY target/shortcut-1.0.0.jar /app/bin/shortcut.jar
COPY bin/start.sh /app/start.sh

# 运行jar包
#RUN bash -c 'touch /usr/adms-server/bin/shortcut-1.0.0.jar'

# 运行参数
ENTRYPOINT ["/bin/bash","/app/start.sh"]

# 打包命令
# docker build -t shortcut:1.0.1 .