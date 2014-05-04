package atm.server.controller;

import java.util.Map;

import atm.server.common.NoteType;
import atm.server.exception.DispensingException;

/**
 * The Interface Dispensable.
 */
public interface Dispensable {
	
	/**
	 * Request money.
	 *
	 * @param amount the amount
	 * @return the map
	 * @throws DispensingException the dispensing exception
	 */
	public Map<NoteType, Integer> requestMoney(double amount) throws DispensingException;
	
	/**
	 * Gets the total money in ATM.
	 *
	 * @return the total money in ATM
	 */
	public double getTotalMoneyInAtm();
}
