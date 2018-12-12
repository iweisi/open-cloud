#!/usr/bin/env bash
#description:开机自启脚本
#启动tomcat
/root/deploy/service/springboot.sh start opencloud-discovery.jar
#启动tomcat
/root/deploy/service/springboot.sh start opencloud-config.jar