package logic.tester.methods;

import java.util.List;

import logic.tester.entities.Conclusion;
import logic.tester.entities.Premise;

public interface Method {
	
	/**
	 * Get a conclusion from premises
	 * @param premises
	 * @return a array of conclusions, or null
	 */
	public List<Conclusion> conclusions(Premise ... premises) throws Exception;
	
	/**
	 * @return number of premises
	 */
	public int getNumberOfPremises();
	
}
