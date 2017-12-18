#!/bin/sh

set JAVA_CMD=`which java`
set JAVA_OPTS=
set PROG_OPTS=

APP_NAME=kjvService
echo Running: ${JAVA_CMD} ${JAVA_OPTS} ${PROG_OPTS} /etc/${APP_NAME}/${APP_NAME}.jar
${JAVA_CMD} ${JAVA_OPTS} ${PROG_OPTS} /etc/${APP_NAME}/${APP_NAME}.jar > /etc/${APP_NAME}/run.log
PID=$!
echo ${PID} > /etc/${APP_NAME}/pid.txt

echo ${APP_NAME} Service Started ${PID}

