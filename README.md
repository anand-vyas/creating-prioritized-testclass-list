# creating-prioritized-testclass-list
Please note: I am unable to upload the contents of joda-time folder to git from my local. Please download the joda time folder from https://github.com/JodaOrg/joda-time and follow the steps listed below. 

This repository contains the files used to create the java agent which is used to create the prioritized test class lists as per total and additional strategies. 

Steps to execute this project:
1. Download the joda time folder from https://github.com/JodaOrg/joda-time. 
2. Replace the pom.xml file in joda-time with the pom.xml given in this repository. 
2. Copy the following files given above agent.jar, asm-all-5.0.3.jar, junit-4.12.jar to the joda-time folder. 
3. Change your directory to the folder joda-time which is present within this folder. 
4. run the mvn test command. 
5. It creates the following files within the joda-time folder

- prioritized-class-list.txt – contains the test class names in order of priority as per total strategy. 
- additive-class-list.txt – contains the test class names in order of priority as per additional strategy.
- additional-strategy.txt – contains the individual test names in order of priority as per the additional strategy along with the number of unique statements covered by the test that are yet uncovered by the previously executed tests.
- total-strategy.txt – contains the individual test names in order of priority as per the total strategy along with the number of unique statements covered by the test. 
- stmt-cov-2.txt – this is the file that contains each test name along with the detailed statement coverage information for each test. 

# Please Note
- We use the content in prioritized-class-list.txt and additive-class-list.txt in https://github.com/anand-vyas/automated-test-prioritization to generate the prioritized test suites as per total and additional strategies.
- The other files are good for reference. 
- Please note these all files can also be found in the generated-files-for-reference folder from my previous execution - added for your reference. 
- files-used-for-java-agent folder contains the files used to create the java agent. 
- The following command can be used to compile these files: javac -cp "./asm-all-5.0.3.jar:./junit-4.12.jar" *.java 
- To create the agent.jar: jar cmf manifest.mf agent.jar *.class 
- This is the jar file provided above for you along with the jar files its dependent on asm-all-5.0.3.jar, junit-4.12.jar which should also be copied to the joda-time folder. 
