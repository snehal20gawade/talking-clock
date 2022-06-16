# Talking-clock
This project is developed in java version 1.8, Spring Boot, Junit 5 and Mockito.

Its simple REST API service developed with spring rest controller to get numeric time in Human friendly text.

E.g

Numeric Time Human Friendly Text

•	1:00 One o'clock<br>
•	2:00 Two o'clock<br>
•	13:00 One o'clock<br>
•	13:05 Five past one<br>
•	13:10 Ten past one<br>
•	13:25 Twenty five past one<br>
•	13:30 Half past one<br>
•	13:35 Twenty five to two<br>
•	13:55 Five to two<br>



## Rest API URL's
Get current time in "Human Friendly Text" in JSON format.<br>
  https://localhost:8080/api/human-readable-time

Numeric Time parameter as input and return the "Human Friendly Text" equivalent.<br>
  https://localhost:8080/api/human-readable-time?time=13:05<br>
   above api call returns : Five past one

## Program execution
### Sring Boot Application
  1. Start spring boot application : TackingClockApplication.java and execute above API's

### Command Line Program
  1. Execute main class TalkingClockCommand.java to get a current or provided numeric time in Human friendly text.<br>
  You can pass numeric time as program argumnets if there is no program arugments passed then it would return current time in 
  "Human Friendly Text" if no argumnet provided
