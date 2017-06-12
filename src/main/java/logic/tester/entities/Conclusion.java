package logic.tester.entities;

import logic.tester.declaration.Declaration;

public class Conclusion extends Entity {

	private String message = "";

	public Conclusion(Declaration declaration) {
		super(declaration);
	}

	public void setMessage(String message) {
		if (message != null)
			this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	@Override
	public String toString() {
		return "   " + this.getDeclaration().toString() + (this.message.isEmpty() ? "" : " (" + this.message + ")");
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Conclusion) {
			Conclusion conclusion = (Conclusion) obj;
			return this.getDeclaration().toString().toLowerCase()
					.equals(conclusion.getDeclaration().toString().toLowerCase());
		}

		else if (obj instanceof Premise) {
			Premise premise = (Premise) obj;
			return this.getDeclaration().toString().toLowerCase()
					.equals(premise.getDeclaration().toString().toLowerCase());
		}

		return false;
	}

}
