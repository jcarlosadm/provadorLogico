package logic.tester.methods;

import java.util.ArrayList;
import java.util.List;

import logic.tester.declaration.Declaration;
import logic.tester.entities.Conclusion;
import logic.tester.entities.Premise;

public class AndIntroduction implements Method {

	private boolean isApplicable(Premise premise1, Premise premise2) {

		if (!premise1.getDeclaration().isVarType() || !premise2.getDeclaration().isVarType())
			return false;

		return true;

	}

	@Override
	public List<Conclusion> conclusions(Premise ... premises) throws Exception {
		
		List<Conclusion> conclusions = new ArrayList<>();
		
		Premise premise1 = premises[0];
		Premise premise2 = premises[1];
		
		if (!this.isApplicable(premise1, premise2))
			return null;

		Conclusion conclusion = new Conclusion(
				new Declaration(premise1.getDeclaration().getComponents().get(0).getSymbol() + "^"
						+ premise2.getDeclaration().getComponents().get(0).getSymbol()));
		conclusion.setMessage("and introduction por " + premise1.getID() + " e " + premise2.getID());
		
		Conclusion conclusion2 = new Conclusion(
				new Declaration(premise2.getDeclaration().getComponents().get(0).getSymbol() + "^"
						+ premise1.getDeclaration().getComponents().get(0).getSymbol()));
		conclusion2.setMessage("and introduction por " + premise1.getID() + " e " + premise2.getID());
		conclusions.add(conclusion2);

		return conclusions;
	}

	@Override
	public int getNumberOfPremises() {
		return 2;
	}

}
