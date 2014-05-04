package atm.server.entity;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import atm.server.common.NoteType;

/**
 * The Class ATM.
 */
public class ATM {
	
	/** The notes. */
	private SortedMap<NoteType, Integer> notes = new TreeMap<NoteType,Integer>(new Comparator<NoteType>(){
		public int compare(NoteType note1, NoteType note2) {
			if(note2==null){
				return -1;
			} 
			
			if(note1==null){
				return +1;
			}
			
			return note2.getNoteValue().compareTo(note1.getNoteValue());
		}
	}); 
		
	/**
	 * Gets the notes.
	 *
	 * @return the notes
	 */
	public Map<NoteType, Integer> getNotes() {
		return notes;
	}

	/**
	 * Sets the notes.
	 *
	 * @param notes the notes
	 */
	public void setNotes(Map<NoteType, Integer> notes) {
		this.notes.putAll(notes);
	}
	
	//return the current total money left in the ATM
	/**
	 * Gets the total money in atm.
	 *
	 * @return the total money in atm
	 */
	public double getTotalMoneyInATM() {
		double sum = 0;
		for (Map.Entry<NoteType, Integer> entry : this.notes.entrySet()) {
			sum = sum + entry.getValue()*entry.getKey().getNoteValue();
		}
		return sum;
	}
	
	/**
	 * Gets the avable notes.
	 *
	 * @param noteType the note type
	 * @return the avable notes
	 */
	public int getAvableNotes(NoteType noteType){
		return this.notes.get(noteType)==null?0:this.notes.get(noteType);
	}
	
	/**
	 * Sets the number of bank note.
	 *
	 * @param noteType the note type
	 * @param number the number
	 */
	public void setNumberOfBankNote(NoteType noteType, int number){
		if(number <= 0){
			this.notes.remove(noteType);
		} else {
			this.notes.put(noteType, number);
		}
	}
}
