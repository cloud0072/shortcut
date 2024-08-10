# Docker image for springboot file run
# VERSION 1.0.0

# 基础镜像使用java
FROM java:8

# 作者
MAINTAINER caolei <352419394@qq.com>

# 端口
EXPOSE 9528

# 将jar包添加到容器中并更名为app.jar
COPY bin/start.sh /app/shortcut/start.sh
COPY target/shortcut-1.0.0.jar /app/shortcut/shortcut.jar

# 运行jar包
#RUN bash -c 'touch /usr/adms-server/bin/shortcut-1.0.0.jar'

# 运行参数
ENTRYPOINT ["/bin/bash","/app/shortcut/start.sh"]

# 打包命令
# docker build -t shortcut:1.0.0 ~/docker/shortcut