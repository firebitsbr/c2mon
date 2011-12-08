#!/bin/bash

# resolve links - $0 may be a softlink
PRG="$0"

while [ -h "$PRG" ] ; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

PRGDIR=`dirname "$PRG"`

# Set DIP_PUBLISHER_HOME
DIP_PUBLISHER_HOME=`cd "$PRGDIR/.." >/dev/null; pwd`

FILE_ROOT=$1
FILE_STUB="DataTags"
CONF_FILE=$DIP_PUBLISHER_HOME/conf/$FILE_ROOT$FILE_STUB.xml
TEMP_FILE=$DIP_PUBLISHER_HOME/conf/$FILE_ROOT$FILE_STUB.new.xml 

echo $CONF_FILE
echo $TEMP_FILE

if [ "$FILE_ROOT" = "" ] ; then
  echo "* * * * * * * * * * * * * * * * * * * * * * * * * *"
  echo "please supply the DIP publisher name as a parameter"
  echo "* * * * * * * * * * * * * * * * * * * * * * * * * *"
else
  sqlplus -S timop/optim@timdb @$DIP_PUBLISHER_HOME/bin/dipconf.sql $FILE_ROOT >$TEMP_FILE

# If the new configuration is different from the old one:
# (1) create a backup of the current configuration
# (2) replace the current configuration by the new one

  if [ -s $CONF_FILE ]; then
    diff $CONF_FILE $TEMP_FILE >/dev/null
    if [ $? -eq 1 ] ; then
      echo "copying file..."
      cp $CONF_FILE $CONF_FILE.`date +%y%m%d_%k%M%S`
      mv $TEMP_FILE $CONF_FILE
    else
      echo "removing file..."
      rm $TEMP_FILE 	
    fi
  else
    mv $TEMP_FILE $CONF_FILE
  fi
fi

# The dippublisher is restarted
echo "restart publisher..."
dippublisher restart $FILE_ROOT 

