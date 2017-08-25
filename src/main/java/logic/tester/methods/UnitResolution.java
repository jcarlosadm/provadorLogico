package logic.tester.methods;

import java.util.ArrayList;
import java.util.List;

import logic.tester.declaration.Declaration;
import logic.tester.declaration.components.Component;
import logic.tester.declaration.components.ComponentType;
import logic.tester.entities.Conclusion;
import logic.tester.entities.Premise;

public class UnitResolution implements Method {

	private boolean isApplicable(Premise premise1, Premise premise2) {

		if (premise1.getDeclaration().isVarType() && premise2.getDeclaration().isVarType())
			return false;

		if (!premise1.getDeclaration().isVarType() && !premise2.getDeclaration().isVarType())
			return false;

		Premise premiseNonVar = (premise1.getDeclaration().isVarType() ? premise2 : premise1);
		Premise premiseVar = (premise1.getDeclaration().isVarType() ? premise1 : premise2);

		boolean match = false;
		boolean containsOr = false;

		for (Component component : premiseNonVar.getDeclaration().getComponents()) {
			if (component.getType() == ComponentType.OR) {
				containsOr = true;
			} else if (component.getType() == ComponentType.VARIABLE) {
				if ((component.getSymbol().contains("~") && component.getSymbol()
						.equals("~" + premiseVar.getDeclaration().getComponents().get(0).getSymbol()))
						|| (!component.getSymbol().contains("~") && ("~" + component.getSymbol())
								.equals(premiseVar.getDeclaration().getComponents().get(0).getSymbol()))) {
					match = true;
				}
			}
		}

		return (match && containsOr);
	}

	@Override
	public List<Conclusion> conclusions(Premise... premises) throws Exception {

		Premise premise1 = premises[0];
		Premise premise2 = premises[1];

		if (!this.isApplicable(premise1, premise2))
			return null;

		List<Conclusion> conclusions = new ArrayList<>();

		Premise premiseNonVar = (premise1.getDeclaration().isVarType() ? premise2 : premise1);
		Premise premiseVar = (premise1.getDeclaration().isVarType() ? premise1 : premise2);

		String sequence = null;

		Component component2 = premiseVar.getDeclaration().getComponents().get(0);
		
		for (int index1 = 0; index1 < premiseNonVar.getDeclaration().getComponents().size(); ++index1) {
			Component component1 = premiseNonVar.getDeclaration().getComponents().get(index1);
			
			if (component1.getType() != ComponentType.VARIABLE)
				continue;

			if ((component1.getSymbol().contains("~") && component1.getSymbol().equals("~" + component2.getSymbol()))
					|| (!component1.getSymbol().contains("~")
							&& ("~" + component1.getSymbol()).equals(component2.getSymbol()))) {
				sequence = premiseNonVar.getDeclaration().getComponents().get(index1 == 0 ? 2 : 0).getSymbol();
				break;
			}
		}

		Conclusion conclusion = new Conclusion(new Declaration(sequence));
		conclusion.setMessage("unit resolution por " + premise1.getID() + " e " + premise2.getID());
		conclusions.add(conclusion);

		return conclusions;
	}

	@Override
	public int getNumberOfPremises() {
		return 2;
	}

}
