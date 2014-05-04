import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import atm.server.common.NoteType;
import atm.server.controller.priv.Dispenser;
import atm.server.entity.ATM;
import atm.server.exception.DispensingException;


public class Main {
	private Dispenser dispensor = null;
	public Main(){
		ATM atm = new ATM();
		this.dispensor = new Dispenser(atm);
	}
	public static void main(String[] args){
		new Main().process();	
	}
	
	private void process(){
		System.out.println("ATM Simulation Application");
		System.out.println("**************************");
		while(true){
			int userInput = selectMainMenu();
			if(userInput==0){
				System.out.println("Application Exit.");
				System.exit(0);
			} else if(userInput==1){
				while(true){
					int submenu = selectATMInitializingMenu();
					if(submenu==0){
						break;
					} else if(submenu==6){ 
						displayCashInATM();
					} else {
						initializeATM(submenu);
					}
				}
			} else if(userInput==2){
				displayCashInATM();
			} else if(userInput == 3){
				requestMoney();
			}
		}			
	}
	
	
	private void requestMoney() {
		System.out.print("Enter the request amount: ");
		@SuppressWarnings("resource")
		Scanner aScanner = new Scanner(System.in);
		try{
			int amount = aScanner.nextInt();
			if(amount > 0){
				System.out.println("**************************");
				Map<NoteType,Integer> result = this.dispensor.requestMoney(Double.valueOf(amount));
				if(result.isEmpty()){
					System.out.println("Cannot dispense the request money with amount " + amount);
				} else {
					System.out.println("ATM dispensing bank notes as follow:");
					for(Map.Entry<NoteType,Integer> note : result.entrySet()){
						System.out.println(" " + note.getValue() + " of "+ note.getKey()+ " NOTE");
					}
					System.out.println("Operation is successful");
				}
			} else {
				System.out.println("Number of bank note in ATM must be greater of equals than zero");
			}
		}catch (DispensingException e) {
			System.out.println(e.getMessage());
		}catch(Exception e){
			System.out.println("**************************");
			System.out.println("Invalid input. please enter a number for the given bank note.");
		} 
		System.out.println("**************************");
	}

	private int selectMainMenu(){
		int selectedMenu;
		do{
			try{
				System.out.println("Main Menu");
				System.out.println(
						"  0. Exit\n" +
		                "  1. Initialize ATM\n" + 
		                "  2. Show Cash in ATM\n" + 
		                "  3. Request Money\n" 
		                
		        );
				System.out.print("Select option: ");
				@SuppressWarnings("resource")
				Scanner command = new Scanner(System.in);
				selectedMenu = command.nextInt();
				if(selectedMenu < 0 || selectedMenu > 3){
					System.out.println("Input out of range \"" + selectedMenu + "\". Input should be a number between 0-3.");
				}
			}catch(Exception e){
				System.out.println("Invalid inputm please input a number between 0-3.");
				selectedMenu = -1;
			}
		} while(selectedMenu < 0 || selectedMenu > 3);
		
		return selectedMenu;		
	}
	
	private int selectATMInitializingMenu(){
		int selectedMenu;
		do{
			try{
				System.out.println("Sub Menu of \"1. Initialize ATM \"");
				System.out.println(
		                "     0. Back to Main Menu\n" + 
		                "     1. Set $100 note\n" +  
		                "     2. Set $50 note\n" +  
		                "     3. Set $20 note\n" +  
		                "     4. Set $10 note\n" +  
		                "     5. Set $5 note\n"  +
		                "     6. Show Cash in ATM\n"
		        );
				System.out.print("Select option: ");
				@SuppressWarnings("resource")
				Scanner command = new Scanner(System.in);
				selectedMenu = command.nextInt();
				if(selectedMenu < 0 || selectedMenu > 6){
					System.out.println("Input out of range \"" + selectedMenu + "\". Input should be a number between 0-6.");
				}
			}catch(InputMismatchException e){
				System.out.println("Invalid inputm please input a number between 0-6.");
				selectedMenu = -1;
			}
		} while(selectedMenu < 0 || selectedMenu > 6);
		
		return selectedMenu;		
	}
	
	private void displayCashInATM(){
		System.out.println("**************************");
		if(this.dispensor.getATM().getNotes().isEmpty()){
			System.out.println("There is NOT money in the ATM");
		} else {
			System.out.println("Note Type \tCOUNT");
			System.out.println("--------------------");
			double sum = 0;
			for(Map.Entry<NoteType, Integer> entry : this.dispensor.getATM().getNotes().entrySet()){
				System.out.println(entry.getKey()+ "\t\t" + entry.getValue());
				sum += entry.getValue()*entry.getKey().getNoteValue();
			}
			System.out.println("-------------------");
			System.out.println("Total \t\t$" + sum);
		}
		System.out.println("**************************");
	}
	
	private void initializeATM(int userChoice){
		System.out.println("**************************");
		if(userChoice==1){
			System.out.print("Enter number of $100 note: ");
		} else if(userChoice==2){
			System.out.print("Enter number of $50 note: ");
		} else if(userChoice==3){
			System.out.print("Enter number of $20 note: ");
		} else if(userChoice==4){
			System.out.print("Enter number of $10 note: ");
		} else if(userChoice==5){
			System.out.print("Enter number of $5 note: ");
		} 
		updateNotesInATM(userChoice);
		System.out.println("**************************");
	}
	
	private void updateNotesInATM(int userChoice){
		@SuppressWarnings("resource")
		Scanner aScanner = new Scanner(System.in);
		try{
			int number = aScanner.nextInt();
			if(number >= 0){
				if(userChoice==1){
					this.dispensor.getATM().setNumberOfBankNote(NoteType.HUNDRED, number);
				} else if (userChoice==2){
					this.dispensor.getATM().setNumberOfBankNote(NoteType.FIFTY, number);
				} else if (userChoice==3){
					this.dispensor.getATM().setNumberOfBankNote(NoteType.TWENTY, number);
				} else if (userChoice==4){
					this.dispensor.getATM().setNumberOfBankNote(NoteType.TEN, number);
				} else if (userChoice==5){
					this.dispensor.getATM().setNumberOfBankNote(NoteType.FIVE, number);
				} 
				System.out.println("Operation is successful");
			} else {
				System.out.println("Number of bank note in ATM must be greater of equals than zero");
			}
		}catch(InputMismatchException e){
			System.out.println("Invalid input. please enter a number for the given bank note.");
		}
	}
}
