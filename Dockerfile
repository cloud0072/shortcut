# Docker image for springboot file run
# VERSION 1.0.0

# 基础镜像使用java
FROM java:8

# 作者
MAINTAINER caolei <352419394@qq.com>

# 端口
EXPOSE 9527

# VOLUME 指定了临时文件目录为/tmp。
# 其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp
# VOLUME /tmp

# 将jar包添加到容器中并更名为app.jar
ADD bin/shortcut-1.0.0.jar /usr/shortcut/bin/shortcut-1.0.0.jar
ADD bin/shortcut-1.0.0.jar /usr/shortcut/bin/shortcut-1.0.0.jar
ADD bin/shortcut-1.0.0.jar /usr/shortcut/bin/shortcut-1.0.0.jar

# 运行jar包
#RUN bash -c 'touch /usr/adms-server/bin/shortcut-1.0.0.jar'

# 运行参数
ENTRYPOINT ["/bin/bash","/usr/shortcut/bin/start.sh"]

# 打包命令
# docker build -t shortcut:1.0.0 ~/docker/shortcut