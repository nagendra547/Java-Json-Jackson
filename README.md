# Java Json Jackson

- Build using maven  (Tested on Apache Maven 3.5.4  and Java 8)

`mvn clean install`

- Compile and build it into single runnable jar

`mvn clean compile assembly:single`

- Execute

`java -jar target/*.jar <json-file> <budget> <days>`

Example:- 

`java -jar target/*.jar activities.json 3400 2`

It will output as  response.json file