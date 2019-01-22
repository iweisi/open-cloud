cd /root/deploy
./startup.sh stop $JOB_NAME.jar
cp -rf $WORKSPACE/opencloud-gateway/$JOB_NAME/target/$JOB_NAME.jar /root/deploy
cd /root/deploy
./startup.sh restart $JOB_NAME.jar
