cp -rf $WORKSPACE/opencloud-upms/$JOB_NAME/target/$JOB_NAME.jar /root/deploy
cd /root/deploy
./startup.sh restart $JOB_NAME.jar
