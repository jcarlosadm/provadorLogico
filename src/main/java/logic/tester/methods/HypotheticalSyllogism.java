package logic.tester.methods;

import logic.tester.declaration.Declaration;
import logic.tester.declaration.components.Component;
import logic.tester.declaration.components.ComponentType;
import logic.tester.entities.Conclusion;
import logic.tester.entities.Premise;

public class HypotheticalSyllogism implements Method {

	private boolean isApplicable(Premise premise1, Premise premise2) {
		if (premise1.getDeclaration().isVarType() || premise2.getDeclaration().isVarType())
			return false;

		if (premise1.getDeclaration().getComponents().get(2).getSymbol()
				.equals(premise2.getDeclaration().getComponents().get(0).getSymbol())
				&& premise1.getDeclaration().getComponents().get(0).getSymbol()
						.equals(premise2.getDeclaration().getComponents().get(2).getSymbol()))
			return false;

		Premise first = (premise1.getDeclaration().getComponents().get(2).getSymbol()
				.equals(premise2.getDeclaration().getComponents().get(0).getSymbol()) ? premise1 : premise2);
		Premise second = (premise1.getDeclaration().getComponents().get(2).getSymbol()
				.equals(premise2.getDeclaration().getComponents().get(0).getSymbol()) ? premise2 : premise1);
		
		if (first.getDeclaration().getComponents().get(0)
				.equals(first.getDeclaration().getComponents().get(2)))
			return false;
		
		if (second.getDeclaration().getComponents().get(0)
				.equals(second.getDeclaration().getComponents().get(2)))
			return false;

		String secondSymbol = "";
		String firstSymbol = "";
		boolean containsImplies = false;
		boolean firstSymbolNotNegate = false;
		boolean secondSymbolNotNegate = false;

		for (Component component : first.getDeclaration().getComponents()) {
			if (component.getType() == ComponentType.IMPLIES) {
				containsImplies = true;
			} else if (component.getType() == ComponentType.VARIABLE) {
				if (containsImplies == false) {
					if (!component.getSymbol().contains("~"))
						firstSymbolNotNegate = true;
				} else {
					if (!component.getSymbol().contains("~")) {
						secondSymbol = component.getSymbol();
						secondSymbolNotNegate = true;
					}
				}
			} else
				break;
		}

		if (!containsImplies || !firstSymbolNotNegate || !secondSymbolNotNegate)
			return false;

		containsImplies = false;
		firstSymbolNotNegate = false;
		secondSymbolNotNegate = false;

		for (Component component : second.getDeclaration().getComponents()) {
			if (component.getType() == ComponentType.IMPLIES) {
				containsImplies = true;
			} else if (component.getType() == ComponentType.VARIABLE) {
				if (containsImplies == false) {
					if (!component.getSymbol().contains("~")) {
						firstSymbolNotNegate = true;
						firstSymbol = component.getSymbol();
					}
				} else {
					if (!component.getSymbol().contains("~"))
						secondSymbolNotNegate = true;
				}
			} else
				break;
		}

		if (!containsImplies || !firstSymbolNotNegate || !secondSymbolNotNegate)
			return false;

		if (firstSymbol.toLowerCase().equals(secondSymbol.toLowerCase()))
			return true;

		return false;
	}

	@Override
	public Conclusion conclusion(Premise premise1, Premise premise2) throws Exception {
		if (!this.isApplicable(premise1, premise2))
			return null;

		Premise first = (premise1.getDeclaration().getComponents().get(2).getSymbol()
				.equals(premise2.getDeclaration().getComponents().get(0).getSymbol()) ? premise1 : premise2);
		Premise second = (premise1.getDeclaration().getComponents().get(2).getSymbol()
				.equals(premise2.getDeclaration().getComponents().get(0).getSymbol()) ? premise2 : premise1);

		Conclusion conclusion = new Conclusion(new Declaration(first.getDeclaration().getComponents().get(0).getSymbol()
				+ ">" + second.getDeclaration().getComponents().get(2).getSymbol()));
		conclusion.setMessage("silogismo hipotético por " + premise1.getID() + " e " + premise2.getID());

		return conclusion;
	}

}
