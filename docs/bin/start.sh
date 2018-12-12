#!/usr/bin/env bash

INPUT=$2
echo "输入的路径 $INPUT"
SERVICE=${INPUT##*/}
echo "输入的服务文件名 $SERVICE"
SERVICE_NAME=${SERVICE%.*}
DEPLOY_DIR=`pwd`

if [ "$1" = "" ];
then
    echo -e "\033[0;31m 未输入操作名 \033[0m  \033[0;34m {start|stop|restart|status} \033[0m"
    exit 1
fi

if [ "$SERVICE" = "" ];
then
    echo -e "\033[0;31m 未输入应用名 \033[0m"
    exit 1
fi

LOGS_DIR=$DEPLOY_DIR/logs/$SERVICE_NAME
if [ ! -d $LOGS_DIR ]; then
        mkdir -p $LOGS_DIR
fi
STDOUT_FILE=$LOGS_DIR/stdout.out

function start()
{
	count=`ps -ef |grep java|grep $SERVICE|grep -v grep|wc -l`
	if [ $count != 0 ];then
		echo -e "\033[0;32m $SERVICE is running... \033[0m"
	else
		nohup java -jar $SERVICE >$STDOUT_FILE  2>&1 &
		boot_id=`ps -ef |grep java|grep $SERVICE|grep -v grep|awk '{print $2}'`
	    echo -e "\033[0;32m pid:$boot_id Start $SERVICE success... \033[0m "
	    echo -e "\033[0;31m $STDOUT_FILE \033[0m"
	fi
}

function stop()
{
	echo -e "\033[0;34m Stop $SERVICE \033[0m"
	boot_id=`ps -ef |grep java|grep $SERVICE|grep -v grep|awk '{print $2}'`
	count=`ps -ef |grep java|grep $SERVICE|grep -v grep|wc -l`

	if [ $count != 0 ];then
	    kill $boot_id
    	count=`ps -ef |grep java|grep $SERVICE|grep -v grep|wc -l`

		boot_id=`ps -ef |grep java|grep $SERVICE|grep -v grep|awk '{print $2}'`
		kill -9 $boot_id
		echo -e "\033[0;31m kill pid $boot_id \033[0m"
	fi
}

function restart()
{
	stop
	sleep 2
	start
}

function status()
{
    boot_id=`ps -ef |grep java|grep $SERVICE|grep -v grep|awk '{print $2}'`
    count=`ps -ef |grep java|grep $SERVICE|grep -v grep|wc -l`
    if [ $count != 0 ];then
        echo -e "\033[0;32m pid: $boot_id $SERVICE is running... \033[0m"
    else
        echo -e "\033[0;31m $SERVICE is not running... \033[0m"
    fi
}

case $1 in
	start)
	start;;
	stop)
	stop;;
	restart)
	restart;;
	status)
	status;;
	*)

	echo -e "\033[0;31m Usage: \033[0m  \033[0;34m sh  $0  {start|stop|restart|status}  {SERVICEJarName} \033[0m
\033[0;31m Example: \033[0m
	  \033[0;33m sh  $0  start esmart-java.jar \033[0m"
esac
