package atm.server.common;

/**
 * The Enum NoteType.
 */
public enum NoteType {
	HUNDRED(100.00),
	FIFTY(50.00),
	TWENTY(20.00),
	TEN(10.00),
	FIVE(5.00);
	
	/** The note value. */
	private Double noteValue;

	/**
	 * Instantiates a new note type.
	 * 
	 * @param noteValue
	 *            the note value
	 */
	NoteType(double noteValue) {
		this.noteValue = noteValue;
	}

	/**
	 * Gets the note value.
	 * 
	 * @return the note value
	 */
	public Double getNoteValue() {
		return this.noteValue;
	}
}
