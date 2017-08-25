package logic.tester.methods;

import java.util.ArrayList;
import java.util.List;

import logic.tester.declaration.Declaration;
import logic.tester.declaration.components.Component;
import logic.tester.declaration.components.ComponentType;
import logic.tester.entities.Conclusion;
import logic.tester.entities.Premise;

public class DisjunctiveSyllogism implements Method {

	private boolean isApplicable(Premise premise1, Premise premise2) {
		if (premise1.getDeclaration().isVarType() && premise2.getDeclaration().isVarType())
			return false;

		if (!premise1.getDeclaration().isVarType() && !premise2.getDeclaration().isVarType())
			return false;

		Premise premNonVar = (premise1.getDeclaration().isVarType() ? premise2 : premise1);
		Premise premVar = (premise1.getDeclaration().isVarType() ? premise1 : premise2);

		if (premNonVar.getDeclaration().getComponents().get(0)
				.equals(premNonVar.getDeclaration().getComponents().get(2)))
			return false;

		boolean firstVarNotNegate = false;
		boolean containsOr = false;
		boolean secondVarNotNegate = false;
		String firstVar = "";
		String secondVar = "";
		for (Component component : premNonVar.getDeclaration().getComponents()) {

			if (component.getType() == ComponentType.OR) {
				containsOr = true;
			} else if (component.getType() == ComponentType.VARIABLE) {
				if (containsOr == false) {
					if (!component.getSymbol().contains("~")) {
						firstVarNotNegate = true;
						firstVar = component.getSymbol().toLowerCase();
					}
				} else {
					if (!component.getSymbol().contains("~")) {
						secondVarNotNegate = true;
						secondVar = component.getSymbol().toLowerCase();
					}
				}
			} else
				break;
		}

		if (!containsOr || !firstVarNotNegate || !secondVarNotNegate)
			return false;

		for (Component component : premVar.getDeclaration().getComponents()) {
			if (component.getSymbol().contains("~") && (component.getSymbol().toLowerCase().equals("~" + firstVar)
					|| component.getSymbol().toLowerCase().equals("~" + secondVar)))
				return true;
		}

		return false;
	}

	@Override
	public List<Conclusion> conclusions(Premise ... premises) throws Exception {
		Premise premise1 = premises[0];
		Premise premise2 = premises[1];
		
		List<Conclusion> conclusions = new ArrayList<>();
		
		if (!this.isApplicable(premise1, premise2))
			return null;

		Premise premise = (premise1.getDeclaration().isVarType() ? premise2 : premise1);
		String var = (premise1.getDeclaration().isVarType() ? premise1 : premise2).getDeclaration().getComponents()
				.get(0).getSymbol();

		int indexAnswer = 0;
		if (var.equals("~" + premise.getDeclaration().getComponents().get(0).getSymbol()))
			indexAnswer = 2;

		Conclusion conclusion = new Conclusion(
				new Declaration(premise.getDeclaration().getComponents().get(indexAnswer).getSymbol()));
		conclusion.setMessage("silogismo disjuntivo por " + premise1.getID() + " e " + premise2.getID());
		conclusions.add(conclusion);

		return conclusions;
	}

	@Override
	public int getNumberOfPremises() {
		return 2;
	}

}
