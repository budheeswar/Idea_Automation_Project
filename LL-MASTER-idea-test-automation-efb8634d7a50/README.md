# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

* Quick summary
* This is IDEA WFS Console test automation repository. The primary objective is to automate WFS Console functionalities
* and automate WFS Workflow such as 
* StandardWithEIR, OnePassAutomatedDE, CompletelyManagedAutomatedOnePassDE, CompletelyManagedAutomatedIdexingOnly.
* WFS Console test automation is UI based where as Workflow automation could be achieved by integrating WFS Server and
* Smartkey. 
* Smartkey is the thick client application developed using C#. 
* After thorough brainstorming, QA Team opted to use TestNG, Selenium Web Driver for UI Test automation. 
* The Smartkey would be automated using Windows Application Driver using Appium library
* Version 1.0
* [Learn Markdown](https://bitbucket.org/tutorials/markdowndemo)

### How do I get set up? ###

* Summary of set up
*    1) Get bitbucket (https://bitbucket.org/LL-MASTER/idea-test-automation) access. Reach out to David Simmons.
*           email at <david.simmons@loanlogics.com>
*    2) Install SourceTree - configuration management client tool which is open source. it can be downloaded and setup
*           through https://sourcetreeapp.com/
*    3) Edit and modify the bitbucket configuration, user login in Source Tree settings page
*    4) Checkout the right branch
*			4.1) Login to bitbucket and access to IDEA Test Automation repository
*			4.2) Go to branches 
*           4.3) Select "idea-wfs-dev" branch 
*                 4.3.1) IDEA Test Automation is branched out to master, idea-wfs-qa and idea-wfs-dev. dev branch is used *                        for concurrent development
*			4.4) Click on checkout & choose "Check out in Source Tree"
*            4.5) Select local folder and checkout the files successfully without any issues
*    5) Install Eclipse from https://www.eclipse.org/ 
*			5.1) (Sample verison - 2019-09 R (4.13.0)). Latest version would be more appropriate
*           5.2) Launch Eclipse IDE, Import IDEA WFS automation project as below
*                 5.3) File --> Import --> Select Existing Maven Project --> Select the pom.xml and finish
* Configuration
*	  1) config.properties file is used to set one time and static parameter and value. config.properties can be found from
*     2) idea-wfs-sandbox.properties file is used to manage environment specific parameters and value. 
*     3) The properties files can be found from /src/main/java/resources folder
* Dependencies
*     All dependencies are managed through pom.xml. Hence all dependenices are downloaded from market place at first time
*     while we build the project at first time
* Database configuration
*     1) MySQL Database is being used to verify loan status, to load master data into cache. 
*     2) There are 2 DB schema such as idea_db_211 and aklero_prod_db
*     3) idea_db_211 is being used to track the loan process & WFS related transaction
*     4) Aklero_prod_db is used to manage master data configuration such as proudct, document, document types, client etc.
*	  5) DB Configuration
*			mysql_host=idt-copy-release-6-0-mysql.loanhdsandbox.com:3306
*           mysql_db=idea_db_211
*           mysql_aklero_db=aklero_prod_db
*           mysql_user=idea
*           mysql_pwd=U4Xe7Ta6
* How to run tests
*     6) Go to project/runnerFiles folder
*          6.1) Select and open a testsuite.xml For example, open TestUserSuite.xml
*          6.2) Right on the file and select Run Configuration as
*          6.3) Select the base directory to the project folder and specify the maven goal as below
*                  goals: "clean test -Dbrowser=chrome -Denvironment=sandbox -Dgroups=StdEIR 
*                         -DtestngXmlFile=TestWFSEndToEndSuite.xml -Dtestng.dtd.http=true  -DisScreenShot=True
*                         -DTestRailUser=ramesh.ramanujam@loanlogics.com -DTestRailPwd=RamAkshi20 -DTestRunID=2956
*                         -Dheadless=False"
*                  -Dgroups = <the group of test method to be executed>
*                  -DTestRunID=<The test run id from Test Rail where the test status and test evidence would be updated
*                  -Dheadless = False | True. True means, the UI will get executed in background otherwise in the front end
* Deployment instructions

### Contribution guidelines ###

* Writing tests
*     1) Create Test<XX>Suite.java
*           1.1) instantiate required object, initialize browser, load master data etc. from before suite, before
*           method, after method and after suite. 
*           1.2) Test status, evidence will be updated at after method, the browser will be quit in afet suite method
*           1.3) Write test method & make sure the @Test annotation is provided with group, dataprovider class
*           1.4) Create corresponding input CSV file by following the naming convention as 
*                 1.4.1)  <TestSuite.java class name>.<Test method name>.csv
*                 1.4.2) The input CSV file must have Test Case Number. Keep unique column name and column name should 
*                        be in Camel case. Do not duplicate or redadunt fields. it could have multipe test case number 
*                        but must be comma separated and enclosed by single quote. For example, 'C891697,C891698,C891699'
*           1.5) Introduce corresponding page class. File name convetion would WFS<Page Name>Page.java. Page class is
*                designed to contain required xpath and business logic
*           1.6) Assertion should not be done any other class other than TestSuite method or corresponding page class
*           1.7) Leverage and resue Selenium Utils, CommonUtils etc. for validating the test scenario
*           1.8) All test method, business logic in page class should be provided with java documentation
*           1.9) Return either booelan or throw exception which should be handled at Test Method and aseert based on the
*                return value
* Code review
*           1) SonarLint code coverage plugin must be installed to address code smells
*           2) All Class name should be in camel case and short as much as possible 
*           3) Method name should start with lower case, throws generalized Exception or checked exception
*           4) Create small method not more than 20 lines of code. make as much as reusable method. Do not build tightly
*              coupled with business logic. Each method should take bunch of input parameters or collection and return
*              value to determine the result by calling method
* Other guidelines
* 			1) Test Output & Report
*                 1.1) Report is created under /<user.project>/target/test-output/testReports/<test case number>
*                 1.2) The test evidence is compressed into zip file and upload into test rail - test case link
*                 1.3) TestNG report can be found from /<user.project>/target/surefire-reports/emailable-report
*                 
*           

### Who do I talk to? ###

* Repo owner or admin
* Other community or team contact