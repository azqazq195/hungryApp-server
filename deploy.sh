#!/bin/bash

HOME=/home/ubuntu
REPOSITORY=/home/ubuntu/apps


echo "> Git Restore"

cd $REPOSITORY/HungryApp/
git restore *
sudo rm ./server/nohup.out

echo "> Git Pull"

cd $REPOSITORY/HungryApp/
git pull

echo "> application.yml Remove"

sudo rm $REPOSITORY/HungryApp/server/src/main/resources/application.yml

echo "> application.yml Copy"

sudo cp $HOME/application.yml $REPOSITORY/HungryApp/server/src/main/resources/

echo "> Gradle Clean"

cd $REPOSITORY/HungryApp/server/
sudo chmod 777 ./gradlew
./gradlew clean

echo "> Gradle Build"

./gradlew bootjar

echo "> Build Copy"

rm $REPOSITORY/*.jar
cp ./build/libs/*.jar $REPOSITORY/

CURRENT_PID=$(pgrep -f hungry)

echo "$CURRENT_PID"

if [ -z $CURRENT_PID ]; then
    echo "> Cannot Find Running Application"
else
    echo "> kill -9 $CURRENT_PID"
    kill -9 $CURRENT_PID
    sleep 5
fi

echo "> Run Server"

cd $REPOSITORY/
nohup java -jar $REPOSITORY/hungry-0.0.1-SNAPSHOT.jar &
