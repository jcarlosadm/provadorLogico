package logic.tester.entities;

import logic.tester.declaration.Declaration;

public abstract class Entity {

	private Declaration declaration = null;
	
	public Entity(Declaration declaration) {
		this.declaration = declaration;
	}
	
	public Declaration getDeclaration() {
		return this.declaration;
	}
	
}
