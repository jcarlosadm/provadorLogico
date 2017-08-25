package logic.tester.methods;

import java.util.ArrayList;
import java.util.List;

import logic.tester.declaration.Declaration;
import logic.tester.declaration.components.Component;
import logic.tester.declaration.components.ComponentType;
import logic.tester.entities.Conclusion;
import logic.tester.entities.Premise;

public class Resolution implements Method {

	private boolean isApplicable(Premise premise1, Premise premise2) {

		if (premise1.getDeclaration().isVarType() || premise2.getDeclaration().isVarType())
			return false;

		boolean match = false;
		boolean prem1ContainsOr = false;
		boolean prem2ContainsOr = false;

		for (Component component1 : premise1.getDeclaration().getComponents()) {
			if (component1.getType() != ComponentType.VARIABLE) {
				if (component1.getType() == ComponentType.OR)
					prem1ContainsOr = true;
				continue;
			}

			for (Component component2 : premise2.getDeclaration().getComponents()) {
				if (component2.getType() != ComponentType.VARIABLE) {
					if (component2.getType() == ComponentType.OR)
						prem2ContainsOr = true;
					continue;
				}

				if ((component1.getSymbol().contains("~")
						&& component1.getSymbol().equals("~" + component2.getSymbol()))
						|| (!component1.getSymbol().contains("~")
								&& ("~" + component1.getSymbol()).equals(component2.getSymbol()))) {
					match = true;
				}
			}
		}

		return (match && prem1ContainsOr && prem2ContainsOr);
	}

	@Override
	public List<Conclusion> conclusions(Premise... premises) throws Exception {
		Premise premise1 = premises[0];
		Premise premise2 = premises[1];

		if (!this.isApplicable(premise1, premise2))
			return null;

		String sequence = null;
		String sequence2 = null;

		for (int index1 = 0; index1 < premise1.getDeclaration().getComponents().size(); ++index1) {
			Component component1 = premise1.getDeclaration().getComponents().get(index1);
			
			if (component1.getType() != ComponentType.VARIABLE)
				continue;

			for (int index2 = 0; index2 < premise2.getDeclaration().getComponents().size(); ++index2) {
				Component component2 = premise2.getDeclaration().getComponents().get(index2);
				
				if (component2.getType() != ComponentType.VARIABLE)
					continue;

				if ((component1.getSymbol().contains("~")
						&& component1.getSymbol().equals("~" + component2.getSymbol()))
						|| (!component1.getSymbol().contains("~")
								&& ("~" + component1.getSymbol()).equals(component2.getSymbol()))) {
					sequence = premise1.getDeclaration().getComponents().get(index1 == 0 ? 2 : 0).getSymbol() + "v"
							+ premise2.getDeclaration().getComponents().get(index2 == 0 ? 2 : 0).getSymbol();
					sequence2 = premise2.getDeclaration().getComponents().get(index2 == 0 ? 2 : 0).getSymbol() + "v"
							+ premise1.getDeclaration().getComponents().get(index1 == 0 ? 2 : 0).getSymbol();
					break;
				}
			}

			if (sequence != null)
				break;
		}

		Conclusion conclusion = new Conclusion(new Declaration(sequence));
		conclusion.setMessage("resolution por " + premise1.getID() + " e " + premise2.getID());
		
		Conclusion conclusion2 = new Conclusion(new Declaration(sequence2));
		conclusion2.setMessage("resolution por " + premise1.getID() + " e " + premise2.getID());

		List<Conclusion> conclusions = new ArrayList<>();
		conclusions.add(conclusion);
		conclusions.add(conclusion2);

		return conclusions;
	}

	@Override
	public int getNumberOfPremises() {
		return 2;
	}

}
