ATM Simulation - backend Developer Test
by Leon Gu on 04/05/2014

I. How to build and run the application

This application is developed under a maven project called ATMMoneyDispenser. The command line interface is also embedded in the project 
for simply to run. 

To build project, run the following command:
mvn clean install

The output of the above command will create two jar files.
ATMDispensingSimulator-0.0.1.jar - standard jar. This should be used to be deployed on server 
ATMDispensingSimulator-0.0.1-jar-with-dependencies.jar - executable jar

To run the application (with command line interface), using either 
java -jar ATMDispensingSimulator-0.0.1-jar-with-dependencies.jar
or
java -cp ATMDispensingSimulator-0.0.1.jar Main

The application can also be run by standard java command after compiling as
java Main.class 

II. Command-line Interface Guide

The text menu driven command line interface is build for this application. The user needs to select correct 
number to operate the correspondence operation.  The follows are the details guidance.

2.1 Main Menu

The top menu is as follow: 

	Main Menu
  		0. Exit
  		1. Initialize ATM
  		2. Show Cash in ATM
  		3. Request Money 

The operation guidance for main menu is as follow. 		
Select 0 to exit the application. 
Select 1 to initialize ATM by given the number of each correspondence bank note (sub menu is used). 
Select 2 to show the current cash in the ATM (number of each note type and total amount of money). 
Select 3 to request money from ATM.

2.2 Initialize ATM

The following sub menu will be displayed if the user select 1 in the main menu.

	Sub Menu of "1. Initialize ATM "
    	0. Back to Main Menu
     	1. Set $100 note
     	2. Set $50 note
     	3. Set $20 note
     	4. Set $10 note
     	5. Set $5 note
     	6. Show Cash in ATM

The operation of this sub menu as follows. 
Select 0 back to main menu. 
Select 1 to 5 to set up the number of legal bank notes in the ATM. 
Select 6 to display the current cash in the ATM (this is for user convenience).


2.3 Display current cash in ATM

By selecting 2 in the main menu or select 6 in the sub menu of "Initialize ATM" , the application will show the 
current cash in ATM (the number of each type bank note and total amount of money). The following is an example.
		
		**************************
		Note Type       COUNT
		--------------------
		HUNDRED         100
		FIFTY           90
		TWENTY          780
		TEN             5
		FIVE            100
		-------------------
		Total           $30650.0
		**************************

2.4 Request Money
 
 By Select 3 in the main menu, the application will ask the user to enter the amount to be withdrawn. On successful, 
 the application will display the number of bank note given out. The following is an example of request of $150, 
 it shows one hundred dollar note and one fifty dollar note are given out. On failure, it will display correspondence 
 error message according to the customized exception.
		
		Enter the request amount: 150
		**************************
		ATM dispensing bank notes as follow:
		 1 of HUNDRED NOTE
		 1 of FIFTY NOTE
		Operation is successful
		**************************
   
III. Design feature and explanation

3.1 Greedy Algorithm

For given amount of money and a set of the available bank notes in the ATM, there could be a number ways to dispensing the notes. 
For example, for $500 request, it can be dispensed as 100 Five dollar notes or 5 Hundred dollar note or 25 Twenty dollar note etc. 
The most efficient way is to using the minimum number of available bank notes. I used greedy algorithm by starting with large bank note  and working 
down to the small bank note.  To achieve this, I used the recursive methods.  This not only make the code to be efficient, but also more generic.

3.2 Generic, Maintainable and Scalable in Mind

Even though the task is required to support $20 and $50 notes, I designed the application to support all legal bank notes. It can even further 
support the coin by just adding an entry to Emum. The implementation is done as generic as possible for future extension. For example, 
I using bank note value in the enum to determine the note order of the dispensing. I also applied synchronized code to further support multiple thread
running environment. 

3.3 Test Driven Development

The project was developed by TDD -from interface to test case, then to the implementation. By employing maven, it not only automatically run the test 
on by build, but also make the code meeting most popular enterprise standard.  

