package logic.tester.methods;

import logic.tester.declaration.Declaration;
import logic.tester.declaration.components.Component;
import logic.tester.declaration.components.ComponentType;
import logic.tester.entities.Conclusion;
import logic.tester.entities.Premise;

public class ModusPonens implements Method {

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
		boolean containsImplies = false;
		boolean secondVarNotNegate = false;
		String firstVar = "";
		for (Component component : premNonVar.getDeclaration().getComponents()) {
			
			if (component.getType() == ComponentType.IMPLIES) {
				containsImplies = true;
			} else if (component.getType() == ComponentType.VARIABLE) {
				if (containsImplies == false) {
					if (!component.getSymbol().contains("~")) {
						firstVarNotNegate = true;
						firstVar = component.getSymbol().toLowerCase();
					}
				} else {
					if (!component.getSymbol().contains("~"))
						secondVarNotNegate = true;
				}
			}  else
				break;
		}
		
		if(!containsImplies || !firstVarNotNegate || !secondVarNotNegate)
			return false;
		
		for (Component component : premVar.getDeclaration().getComponents()) {
			if (!component.getSymbol().contains("~") && component.getSymbol().toLowerCase().equals(firstVar))
				return true;
		}
		
		return false;
	}

	@Override
	public Conclusion conclusion(Premise premise1, Premise premise2) throws Exception {
		if(!this.isApplicable(premise1, premise2))
			return null;
		
		Premise premise = (premise1.getDeclaration().isVarType() ? premise2 : premise1);
		
		String symbol = premise.getDeclaration().getComponents().get(2).getSymbol();
		
		Conclusion conclusion = new Conclusion(new Declaration(symbol));
		conclusion.setMessage("modus ponens por " + premise1.getID() + " e " + premise2.getID());
		
		return conclusion;
	}

}
