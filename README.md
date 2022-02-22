# QuantcastCodingTask
A coding task given in the hiring process

This project can be opened in IntelliJ, Eclipse, Netbeans, Visual Studio Code, etc.
It can be compiled without and IDE with `mvn clean package` command from console.
This assumes that `Java 8+` as well as `maven` are installed and added to the path.

This project does provide the executable program as well. It can be run by going to the target folder and invoking the jar file like this:
 - `cd target/` on linux - `cd ./target`
 - `java -jar QuantcastCodingTask-1.0-SNAPSHOT.jar`

If not given any parameters, the program will report errors and will instruct the user on how to use the program.
I have provided a sample `file.log` file which was based on the data from the initial task document.
It can be used as a sample, using following command:
 - `java -jar QuantcastCodingTask-1.0-SNAPSHOT.jar -f file.log -d 2018-12-09`
 - or
 - `java -jar QuantcastCodingTask-1.0-SNAPSHOT.jar -f file.log -d 2018-12-08`

These will give different results due to different dates being used. This is just for demonstration and surely other inputs could be used as well.

## Testing
The tests included could be run in an IDE or in console via maven. This is done by the following command:
- `mvn test`
- or
- `mvn verify`
