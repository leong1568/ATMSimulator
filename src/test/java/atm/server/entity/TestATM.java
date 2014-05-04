package atm.server.entity;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import atm.server.common.NoteType;
import atm.server.controller.priv.Dispenser;

/**
 * The Class TestATM.
 */
public class TestATM extends TestCase {
	
	
	/**
	 * Test dispenser case 1 - test ATM initialization.
	 * Input:   ATM have 100x$100, 90x$50, 80x$20, 70x$10 and 60x$5 
	 * Expected: Number of notes for each type is correct as well as the total amount money in ATM
	 */
	public void testATMInitialisationAmount(){
		//create instance of dispenser
		ATM atm = new ATM();		
		//set initial notes in the Atm
		Map<NoteType, Integer> money = new HashMap<NoteType,Integer>();
		money.put(NoteType.HUNDRED, 100);
		double fund = 100*100;
		money.put(NoteType.FIFTY, 90);
		fund += 90*50;
		money.put(NoteType.TWENTY,80);
		fund += 80*20;
		money.put(NoteType.TEN,70);
		fund += 70*10;
		money.put(NoteType.FIVE,60);
		fund += 60*5;
		
		atm.setNotes(money);
		
		Dispenser dispenser = new Dispenser(atm);
		
		//check if the correct total fund in ATM
		assertEquals(fund, dispenser.getTotalMoneyInAtm());
		
		//test number of each type of note are correct
		assertEquals(Integer.valueOf(100), atm.getNotes().get(NoteType.HUNDRED));
		assertEquals(Integer.valueOf(90), atm.getNotes().get(NoteType.FIFTY));
		assertEquals(Integer.valueOf(80), atm.getNotes().get(NoteType.TWENTY));
		assertEquals(Integer.valueOf(70), atm.getNotes().get(NoteType.TEN));
		assertEquals(Integer.valueOf(60), atm.getNotes().get(NoteType.FIVE));
	}
	
	
	/**
	 * Test dispenser case 1 - test ATM initialization.
	 * Input:   ATM have 15x$50, 10x$20 
	 * Expected: Total cash in ATM is correct and total number of notes is 25
	 */
	public void testATMMachine(){
		ATM atm = new ATM();		
		//set ATM with notes - 15 fifty notes and 10 twenty notes
		Map<NoteType, Integer> money = new HashMap<NoteType,Integer>();
		money.put(NoteType.FIFTY, 15);
		money.put(NoteType.TWENTY,10);
		atm.setNotes(money);
		
		//test if total amount of meony is $950
		assertEquals(Double.valueOf(950), atm.getTotalMoneyInATM());
		
		//test if total number of notes is 25
		Map<NoteType,Integer> holdingNotes = atm.getNotes();
		int count = 0;
		for(Integer i : holdingNotes.values()){
			count += i;
		}
		assertEquals(25, count);
	}

	/**
	 * Test dispenser case 1 - test ATM initialization.
	 * Input:   ATM have 15x$50, 10x$20 
	 * Expected: 15 of $50 notes and 10 of $20 notes
	 */
	public void testGetNumberOfAvailableNotes(){
		ATM atm = new ATM();		
		//set ATM with notes - 15 fifty notes and 10 twenty notes
		Map<NoteType, Integer> money = new HashMap<NoteType,Integer>();
		money.put(NoteType.FIFTY, 15);
		money.put(NoteType.TWENTY,10);
		atm.setNotes(money);
		
		assertEquals(Integer.valueOf(15),Integer.valueOf(atm.getAvableNotes(NoteType.FIFTY)));
		assertEquals(Integer.valueOf(10),Integer.valueOf(atm.getAvableNotes(NoteType.TWENTY)));
		
	}
}
