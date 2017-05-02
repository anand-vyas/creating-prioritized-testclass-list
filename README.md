# creating-prioritized-testclass-list
This repository contains the files used to create the java agent which is used to create the prioritized test class lists as per total and additional strategies. 

Steps to execute this project:
1. Download the folder. 
2. Change your directory to the folder joda-time which is present within this folder. 
3. run the mvn test command. 
4. It creates the following files within the joda-time folder

- prioritized-class-list.txt – contains the test class names in order of priority as per total strategy. 
- additive-class-list.txt – contains the test class names in order of priority as per additional strategy.
- additional-strategy.txt – contains the individual test names in order of priority as per the additional strategy along with the number of unique statements covered by the test that are yet uncovered by the previously executed tests.
- total-strategy.txt – contains the individual test names in order of priority as per the total strategy along with the number of unique statements covered by the test. 
- stmt-cov-2.txt – this is the file that contains each test name along with the detailed statement coverage information for each test. 

5. We use the content in prioritized-class-list.txt and additive-class-list.txt in https://github.com/anand-vyas/automated-test-prioritization
to generate the prioritized test suites as per total and additional strategies.
6. The other files are good for reference. 
7. Please note these files can also be found in the generated-files-for-reference folder from my previous execution - added for your reference. 
