package logic.tester.entities;

import logic.tester.declaration.Declaration;

public class Conclusion extends Entity {

	private Conclusion(Declaration declaration) {
		super(declaration);
	}

	public static Conclusion getInstance(Declaration declaration) {
		return (declarationIsValid(declaration) == false ? null : new Conclusion(declaration));
	}

	private static boolean declarationIsValid(Declaration declaration) {
		// TODO implement it!
		return false;
	}

}
