# this script is not to be executed standalone
# it is to be sourced into wrapper scritps ONLY

TIME=`date +"%F %T.%3N"`

# sets the home directory
#
DAQ_HOME=`dirname $0`
[[ ${DAQ_HOME} == "." ]] && DAQ_HOME=$PWD
DAQ_HOME=${DAQ_HOME}/..

DAQ_HOST=`hostname -s`

DAQ_SCRIPT=${DAQ_HOME}/bin/DMN2-DAQ.jvm

export DAQ_LOG_HOME=${DAQ_HOME}/log
export DAQ_CONF_HOME=${DAQ_HOME}/conf
DAQ_LOG_FILE=${DAQ_LOG_HOME}/daqprocess.log

export C2MON_PROPERTIES_FILE=${DAQ_CONF_HOME}/daq.properties

# the name of the parameter determining that the DAQ start-up script
# should output only XML feedback messages

export ADDITIONAL_PARAMS="$1 $2 $3 $4 $5 $6 $7"

if [ -f ${DAQ_CONF_HOME}/local/${PROCESS_NAME}.xml ] ; then
  export ADDITIONAL_PARAMS="${ADDITIONAL_PARAMS} -c ${DAQ_CONF_HOME}/local/${PROCESS_NAME}.xml"
fi


# Check which log4j configuration script should be used
if [ -f ${DAQ_CONF_HOME}/${PROCESS_NAME}_log4j.xml ] ; then
  export  LOG4J_CONF_FILE=${DAQ_CONF_HOME}/${PROCESS_NAME}_log4j.xml
else
  # set the default one
  export LOG4J_CONF_FILE=${DAQ_CONF_HOME}/log4j.xml
fi

# Make sure the JAVA_BIN variable points to the java bin directory on your machine
export JAVA_HOME=/usr/java/jdk
export PATH=$JAVA_HOME/bin:$JAVA_HOME/jre/bin:$PATH/

# Source-in the script generated by the deployment tool
. ${DAQ_SCRIPT}
