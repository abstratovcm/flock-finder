#!/bin/bash

TOMCAT_PATH="/opt/tomcat"

while getopts "t:" opt; do
  case $opt in
    t)
      TOMCAT_PATH="$OPTARG"
      ;;
    \?)
      echo "Usage: ./deploy.sh [-t /path/to/your/tomcat]"
      exit 1
      ;;
  esac
done

# Step 1: Compile the project
mvn clean package

# Step 2: Move the war file
cp target/flock-finder-1.0.0-SNAPSHOT.war "$TOMCAT_PATH/webapps/"

# Step 3: Start Tomcat
"$TOMCAT_PATH/bin/startup.sh"

# Step 4: Open the application in a web browser
xdg-open "http://localhost:8080/flock-finder-1.0.0-SNAPSHOT/"

# Step 5: Wait for user input to stop the program
echo "Press any key to stop the program..."
read -n1 -r

# Step 6: Stop Tomcat
"$TOMCAT_PATH/bin/shutdown.sh"
