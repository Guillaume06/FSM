#!/bin/sh
SCRIPT=$(readlink -f $0)
SCRIPTPATH=`dirname $SCRIPT`
cd $SCRIPTPATH
javac -cp .:jdom-2.0.6.jar src/*.java src/Architecture/*.java -d out
cd out/
java -cp .:../jdom-2.0.6.jar JDOMXMLReader "data/StateChart.scxml" > ../GeneratedCode/FSM.java
cd ../GeneratedCode/
