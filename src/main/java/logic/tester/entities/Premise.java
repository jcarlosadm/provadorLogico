package logic.tester.entities;

import logic.tester.declaration.Declaration;

public class Premise extends Entity {

	private long id = 0;
	private static long ids = 0;
	
	public Premise(Declaration declaration) {
		super(declaration);
		
		this.id = ids;
		++ids;
	}
	
	public long getID() {
		return this.id;
	}
}
