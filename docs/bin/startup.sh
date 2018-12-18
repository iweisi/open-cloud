#!/usr/bin/env bash

INPUT=$2
echo "输入的路径 $INPUT"
FILE_PATH=`readlink -f ${INPUT}`
echo "输入的服务路径 $FILE_PATH"
SERVICE=${INPUT##*/}
echo "输入的服务文件名 $SERVICE"
SERVICE_NAME=${SERVICE%.*}
DEPLOY_DIR=`pwd`
JVM_OPTS="-Xms128m -Xmx128m"

if [[ "$1" = "" ]];
then
    echo -e "\033[0;31m 未输入操作名 \033[0m  \033[0;34m {start|stop|restart|status} \033[0m"
    exit 1
fi

if [[ "$SERVICE" = "" ]];
then
    echo -e "\033[0;31m 未输入应用名 \033[0m"
    exit 1
fi

LOGS_DIR="$DEPLOY_DIR/logs/$SERVICE_NAME"
echo "$LOGS_DIR"
if [[ ! -d "$LOGS_DIR" ]]; then
        mkdir -p ${LOGS_DIR}
fi

LOG_PATH="$LOGS_DIR/stdout.out"
pid=0

start()
{
	checkPid
	if [[ ! -n "$pid" ]]; then
    nohup java -jar  $FILE_PATH >> $LOG_PATH 2>&1 &
    echo "---------------------------------"
    echo "启动完成，按CTRL+C退出日志界面即可>>>>>"
    echo "---------------------------------"
    sleep 2s
    tail -f ${LOG_PATH}
  else
      echo "$SERVICE_NAME is runing PID: $pid"
  fi
}

checkPid()
{
    pid=`ps -ef |grep ${FILE_PATH} |grep -v grep |awk '{print $2}'`
}

stop()
{
	checkPid
    if [[ ! -n "$pid" ]]; then
     echo "$SERVICE_NAME not runing"
    else
      echo "$SERVICE_NAME stop..."
      kill -9 ${pid}
    fi
}

restart()
{
	stop
	sleep 2
	start
}

status()
{
   checkPid
   if [[ ! -n "$pid" ]]; then
     echo "$SERVICE_NAME not runing"
   else
     echo "$SERVICE_NAME runing PID: $pid"
   fi
}

case $1 in
          start) start;;
          stop)  stop;;
          restart)  restart;;
          status)  status;;
              *)  echo "require start|stop|restart|status"  ;;
esac
