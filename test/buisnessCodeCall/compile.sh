#!/bin/sh
SCRIPT=$(readlink -f $0)
SCRIPTPATH=`dirname $SCRIPT`
cd $SCRIPTPATH
javac -cp .:../../jdom-2.0.6.jar ../../src/*.java ../../src/Architecture/*.java -d out
mkdir out
cd out/
java -cp .:../../../jdom-2.0.6.jar JDOMXMLReader "../StateChart.scxml" > ../GeneratedCode/FSM.java
cd ../GeneratedCode/
javac -Xlint *.java
