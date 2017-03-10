#!/bin/sh
javac -cp .:jdom-2.0.6.jar src/*.java src/Architecture/*.java -d out
cd out/
java -cp .:../jdom-2.0.6.jar JDOMXMLReader > ../GeneratedCode/FSM.java