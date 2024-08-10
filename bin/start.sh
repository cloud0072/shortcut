#!/bin/bash

# spring 启动参数
SPRING_OPTS="--spring.profiles.active=prod-bjhub --spring.config.additional-location=/app/conf/"
# java 启动参数
JAVA_OPTS="-server -Xmx1024M -Xms512M -XX:MaxMetaspaceSize=1024M -XX:MetaspaceSize=512M -Duser.timezone=GMT+08 -Djava.security.egd=file:/dev/./urandom"
# java文件
APP_NAME=/app/shortcut.jar
# 日志文件
LOG_NAME=/app/logs/nohup.out

# nohup 后台启动
nohup java $JAVA_OPTS -jar $APP_NAME $SPRING_OPTS >$LOG_NAME 2>&1 &
echo "$date ======= Service Start ======="
tail -f /dev/null
