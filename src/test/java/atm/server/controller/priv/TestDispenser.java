package atm.server.controller.priv;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import atm.server.common.NoteType;
import atm.server.entity.ATM;
import atm.server.exception.DispensingException;

/**
 * The Class TestDispenser.
 */
public class TestDispenser extends TestCase {

	/**
	 * Test dispenser case 1
	 * Condition: all types of notes are available, each type has 100 notes.
	 * Input:  First Request money: $220 and followed by second request 270
	 * Expected: both request are successful and cash in ATM matching with withdrawn
	 */
	public void testAllNoteTypeAvailable(){
		//create instance of dispenser
		ATM atm = new ATM();		
		//set initial notes in the Atm
		Map<NoteType, Integer> money = new HashMap<NoteType,Integer>();
		money.put(NoteType.HUNDRED, 100);
		double fund = 100*100;
		money.put(NoteType.FIFTY, 100);
		fund += 100*50;
		money.put(NoteType.TWENTY,100);
		fund += 100*20;
		money.put(NoteType.TEN,100);
		fund += 100*10;	
		money.put(NoteType.FIVE,100);
		fund += 100*5;		
		atm.setNotes(money);
		
		Dispenser dispenser = new Dispenser(atm);
		
		//first request -220
		double withdraw = 220;
		try {
			dispenser.requestMoney(withdraw);
		} catch (DispensingException e) {
			assertFalse(true);
		}
		fund = fund - withdraw;
		assertEquals(fund, dispenser.getTotalMoneyInAtm());		
		
		//second request 
		withdraw = 270;
		try {
			dispenser.requestMoney(withdraw);
		} catch (DispensingException e) {
			assertFalse(true);
		}
		fund = fund - withdraw;
		assertEquals(fund, dispenser.getTotalMoneyInAtm());		
	}
	
	/**
	 * Test Dispenser case 2.
	 * Condition: only two note types are available and they are not in consecutive
	 *            ATM have 2 type of notes 10 X $100 and 10 x $20 
	 * Input: request money $120
	 * Expected: dispensing 1 Hundred Dollar note and 2 Twenty Dollar Notes based on minimum notes distribution algorithm
	 */
	public void testDisconsecutiveNoteDispensing(){
		ATM atm = new ATM();		
		//set initial notes in the Atm
		Map<NoteType, Integer> money = new HashMap<NoteType,Integer>();
		money.put(NoteType.HUNDRED, 10);
		money.put(NoteType.TWENTY,10);
		atm.setNotes(money);
		
		Dispenser dispenser = new Dispenser(atm);
		//check if number of $100 notes is 10 
		assertEquals(Integer.valueOf(10), atm.getNotes().get(NoteType.HUNDRED));
		//check if number of $20 notes is 10 
		assertEquals(Integer.valueOf(10), atm.getNotes().get(NoteType.TWENTY));
		
		try{
			dispenser.requestMoney(120);
		}catch(Exception e){
			assertFalse(true);
		}
		
		//check if number of $100 notes is 9 after request $120 successful 
		assertEquals(Integer.valueOf(9), atm.getNotes().get(NoteType.HUNDRED));
		
		//check if number of $20 notes is 9 after request $120 successful 
		assertEquals(Integer.valueOf(9), atm.getNotes().get(NoteType.TWENTY));
	}
	
	/**
	 * Test dispenser case 3.
	 * Condition: ATM have 2 type of notes 10 X $50 and 10 x $20 
	 * Input: request money $260
	 * Expected: Dispensing as 4 Fifty Dollar note and 3 Twenty Dollar Notes
	 */
	public void testDispenserCase3(){
		ATM atm = new ATM();		
		//set initial notes in the Atm
		Map<NoteType, Integer> money = new HashMap<NoteType,Integer>();
		money.put(NoteType.FIFTY, 10);
		money.put(NoteType.TWENTY,10);
		atm.setNotes(money);
		
		Dispenser dispenser = new Dispenser(atm);
		//check if number of $100 notes is 10 
		assertEquals(Integer.valueOf(10), atm.getNotes().get(NoteType.FIFTY));
		//check if number of $20 notes is 10 
		assertEquals(Integer.valueOf(10), atm.getNotes().get(NoteType.TWENTY));
		
		try{
			dispenser.requestMoney(260);
		}catch(Exception e){
			assertFalse(true);
		}
		
		//check if number of $100 notes is 6 after request $260 successful 
		assertEquals(Integer.valueOf(6), atm.getNotes().get(NoteType.FIFTY));
		
		//check if number of $20 notes is 7 after request $260 successful 
		assertEquals(Integer.valueOf(7), atm.getNotes().get(NoteType.TWENTY));
	}

	/**
	 * Test dispenser case 4.
	 * Condition: ATM have 2 type of notes 10 X $50 and 10 x $20 
	 * Input:  Request money: $30
	 * Outcome: Exception of Unable to dispensing bank note exception is thrown
	 */
	public void testUnableDispensing(){
		ATM atm = new ATM();		
		//set initial notes in the Atm
		Map<NoteType, Integer> money = new HashMap<NoteType,Integer>();
		money.put(NoteType.FIFTY, 10);
		money.put(NoteType.TWENTY,10);
		atm.setNotes(money);
		
		Dispenser dispenser = new Dispenser(atm);
		//check if number of $100 notes is 10 
		assertEquals(Integer.valueOf(10), atm.getNotes().get(NoteType.FIFTY));
		//check if number of $20 notes is 10 
		assertEquals(Integer.valueOf(10), atm.getNotes().get(NoteType.TWENTY));
		
		try{
			dispenser.requestMoney(30);
			assertFalse(true);
		}catch(Exception e){
			assertTrue(e.getMessage().contains("Unable to dispensing bank note for request amount"));
		}
	}

	/**
	 * Test dispenser case 5.
	 * Condition: ATM have 2 type of notes 3 X $50 and 3 x $20 
	 * Input:  Request money: $500
	 * Expected: Exception of "Request Amount is not available" is thrown
	 */
	public void testDispenserCase5(){
		ATM atm = new ATM();		
		//set initial notes in the Atm
		Map<NoteType, Integer> money = new HashMap<NoteType,Integer>();
		money.put(NoteType.FIFTY, 3);
		money.put(NoteType.TWENTY,3);
		atm.setNotes(money);
		
		Dispenser dispenser = new Dispenser(atm);
		//check if number of $100 notes is 10 
		assertEquals(Integer.valueOf(3), atm.getNotes().get(NoteType.FIFTY));
		//check if number of $20 notes is 10 
		assertEquals(Integer.valueOf(3), atm.getNotes().get(NoteType.TWENTY));
		
		try{
			dispenser.requestMoney(500);
			assertFalse(true);
		}catch(Exception e){
			assertTrue(e.getMessage().contains("Request Amount is not available"));
		}
	}

	
	/**
	 * Test dispenser case 6.
	 * Condition:   ATM have 2 type of notes 3 X $50 and 3 x $20 
	 * Input: Request money: -$20
	 * Expected: Exception of "Request Amount cannot be negative" is thrown
	 */
	public void testDispenserCase6(){
		ATM atm = new ATM();		
		//set initial notes in the Atm
		Map<NoteType, Integer> money = new HashMap<NoteType,Integer>();
		money.put(NoteType.FIFTY, 3);
		money.put(NoteType.TWENTY,3);
		atm.setNotes(money);
		
		Dispenser dispenser = new Dispenser(atm);
		//check if number of $100 notes is 10 
		assertEquals(Integer.valueOf(3), atm.getNotes().get(NoteType.FIFTY));
		//check if number of $20 notes is 10 
		assertEquals(Integer.valueOf(3), atm.getNotes().get(NoteType.TWENTY));
		
		try{
			dispenser.requestMoney(-20);
			assertFalse(true);
		}catch(Exception e){
			assertTrue(e.getMessage().contains("Request Amount cannot be negative"));
		}
	}
	
	/**
	 * Test dispenser case 7.
	 * Condition: ATM have not money setup 
	 * Input:  Request money: $130
	 * Outcome: Exception of Not money available in the ATM is thrown
	 */
	public void testDispenserCase7(){
		ATM atm = new ATM();			
		Dispenser dispenser = new Dispenser(atm);
		
		try{
			dispenser.requestMoney(30);
			assertFalse(true);
		}catch(Exception e){
			assertTrue(e.getMessage().contains("Not money available in the ATM"));
		}
	}
}
