#!/usr/bin/env bash
TARGET_NAME=$1
VERSION=$2
APP_HOME=/etc/${TARGET_NAME}
if [ -d  ${APP_HOME} ]
then
    rm -r ${APP_HOME}
fi

if [ ! -d  ${APP_HOME} ]
then
    mkdir ${APP_HOME}
fi

rm /usr/bin/${TARGET_NAME}.*

# Jar file
cp ./../build/libs/${TARGET_NAME}-${VERSION}.jar ${APP_HOME}/${TARGET_NAME}.jar
chmod +x ${APP_HOME}/${TARGET_NAME}.jar
# Start Script
cp ./start.sh /usr/bin/${TARGET_NAME}.start.sh
chmod +x /usr/bin/${TARGET_NAME}.start.sh
# Stop Script
cp ./stop.sh /usr/bin/${TARGET_NAME}.stop.sh
chmod +x /usr/bin/${TARGET_NAME}.stop.sh
cp ./${TARGET_NAME}.service /etc/systemd/system/${TARGET_NAME}.service
