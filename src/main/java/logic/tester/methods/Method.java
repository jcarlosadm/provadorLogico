package logic.tester.methods;

import logic.tester.entities.Conclusion;
import logic.tester.entities.Premise;

public interface Method {
	
	/**
	 * Get a conclusion from premises
	 * @param premise1
	 * @param premise2
	 * @return a new conclusion, or null
	 */
	public Conclusion conclusion(Premise premise1, Premise premise2) throws Exception;
	
}
