package atm.server.controller.priv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import atm.server.common.NoteType;
import atm.server.controller.Dispensable;
import atm.server.entity.ATM;
import atm.server.exception.DispensingException;

/**
 * The Class NoteDispenser.
 */
public class Dispenser implements Dispensable {
	
	/** The atm. */
	private ATM atm;
		
	/**
	 * Instantiates a new note dispenser.
	 *
	 * @param atm the ATM
	 */
	public Dispenser(ATM atm){
		this.atm = atm;
	}
	
	/* (non-Javadoc)
	 * @see atm.server.controller.Dispensable#requestMoney(double)
	 */
	public synchronized Map<NoteType, Integer> requestMoney(double amount) throws DispensingException {
		//throw error if the request amount is negative
		if(amount < 0){
			throw new DispensingException("Request Amount cannot be negative");
		}
		
		if(this.getTotalMoneyInAtm()<=0){
			throw new DispensingException("Not money available in the ATM. Please initialise ATM with bank notes");
		}
		
		//throw error if the request amount is not available
		if(amount > this.atm.getTotalMoneyInATM()){
			throw new DispensingException("Request Amount is not available");
		}
		
		Map<NoteType, Integer> result = this.getDistribution(amount);
		//if successful, deduce the amount from ATM, Note: exception will be throws if distribute note is not successful 
		for(Map.Entry<NoteType, Integer> request : result.entrySet()){
			this.atm.getNotes().put(request.getKey(), this.atm.getNotes().get(request.getKey())-request.getValue());
		}
			
		return result;
	}

	/* (non-Javadoc)
	 * @see atm.server.controller.Dispensable#getTotalMoneyInAtm()
	 */
	public double getTotalMoneyInAtm() {
		return this.atm.getTotalMoneyInATM();
	}

	/**
	 * Gets the next note type.
	 *
	 * @param noteType the note type
	 * @return the next note type
	 */
	public NoteType getNextNoteType(NoteType noteType){ 
		//get all enum and add them into list
		List<NoteType> types = new ArrayList<NoteType>();
		for(NoteType type : NoteType.values()){
			types.add(type);
		}
		
		//sorting the List according the value of the note
		Comparator<NoteType> comparatorByNoteValue = new Comparator<NoteType>(){
			public int compare(NoteType note1, NoteType note2) {
				if(note2==null){
					return -1;
				} 
				
				if(note1==null){
					return +1;
				}
				return note2.getNoteValue().compareTo(note1.getNoteValue());
			}};
		Collections.sort(types, comparatorByNoteValue);
				
		//return next least value of note for a given note. 
		for(int i=0; i< types.size(); i++){
			if(noteType.equals(types.get(i))){
				//if the given note is the last note, then return null
				if(i==(types.size()-1)){
					return null;
				} else {
					return types.get(i+1);
				}
			}
		}

		return null;
	}
	
	/**
	 * Gets the distribution.
	 *
	 * @param amount the amount
	 * @return the distribution
	 * @throws DispensingException 
	 */
	public Map<NoteType, Integer> getDistribution(Double amount) throws DispensingException{
		Map<NoteType, Integer> result = new HashMap<NoteType, Integer>();
		if(amount <= 0 ){
			return result;
		}
		
		boolean isSuccessful = false;
		for(NoteType note : NoteType.values()){
			//note type is not available
			int noteNoInATM = this.atm.getAvableNotes(note);
			if(noteNoInATM <= 0 ){
				continue;
			}
			
			//allocation number of start point
			int allocationNo = (int) Math.floor(amount/note.getNoteValue());
			if(allocationNo <= 0 ){
				continue;
			}
			
			//call a recursive method to find whether the given amount can be successfully dispensed
			isSuccessful = findDispensableNotes(amount, note, Math.min(noteNoInATM, allocationNo),result);
			
			//if the above step isn't success, calling the recursive method with start point notes minus one
			if(isSuccessful != true){
				isSuccessful = findDispensableNotes(amount, note, Math.min(noteNoInATM, allocationNo)-1,result);
			} 
			
			if(isSuccessful==true){
				break;
			} 
		}
		
		if(isSuccessful==false){
			throw new DispensingException("Unable to dispensing bank note for request amount " + amount);
		}
		
		return result;
	}

	/**
	 * Find available note - this is recursive method to find if the given amound can be dispensed or not.
	 *
	 * @param amount the amount
	 * @param note the note
	 * @param count the count
	 * @param result the result
	 * @return true, if successful
	 */
	private boolean findDispensableNotes(Double amount, NoteType note, int count, Map<NoteType, Integer> result) {
		amount = amount - count*note.getNoteValue();
		result.put(note, count);
		//stop condition
		if(amount == 0 ){
			return true;
		} 
		
		//calling another recursive method to find next available not type in ATM
		NoteType nextNote = getAvailableNextNoteType(note);
		int noteNoInATM = this.atm.getAvableNotes(nextNote);
		if(nextNote == null){
			return false;
		}		
			
		//allocation number of start point
		int allocationNo = (int) Math.floor(amount/nextNote.getNoteValue());
		//another stop condition
		if(allocationNo <= 0 ){
			return false;
		}
		
		//recursively calling itself
		return findDispensableNotes(amount, nextNote, Math.min(noteNoInATM, allocationNo),result);
	} 
	
	private NoteType getAvailableNextNoteType(NoteType note){
		//get the next note in the enum and its value in ATM (number of note)
		NoteType nextNote = getNextNoteType(note);	
		int noteNoInATM = this.atm.getAvableNotes(nextNote);
		
		//stop is nextNote reach the end of enum
		if(nextNote == null){
			return null;
		}
		
		//return the current nextNote if it is avaiable in ATM (number of note is greater than 0)
		if(noteNoInATM > 0){
			return nextNote;
		} else {
			//calling itself to find the nextAvailable one
			return getAvailableNextNoteType(nextNote);
		}
	}
	
	/**
	 * Gets the atm.
	 *
	 * @return the atm
	 */
	public ATM getATM(){
		return this.atm;
	}
}
