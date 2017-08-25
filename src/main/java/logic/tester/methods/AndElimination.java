package logic.tester.methods;

import java.util.ArrayList;
import java.util.List;

import logic.tester.declaration.Declaration;
import logic.tester.declaration.components.Component;
import logic.tester.declaration.components.ComponentType;
import logic.tester.entities.Conclusion;
import logic.tester.entities.Premise;

public class AndElimination implements Method {

	private boolean isApplicable(Premise premise) {
		if (premise.getDeclaration().isVarType())
			return false;

		for (Component component : premise.getDeclaration().getComponents())
			if (component.getType() == ComponentType.AND)
				return true;

		return false;
	}

	@Override
	public List<Conclusion> conclusions(Premise... premises) throws Exception {
		Premise premise = premises[0];

		if (!this.isApplicable(premise))
			return null;

		List<Conclusion> conclusions = new ArrayList<>();

		Conclusion conclusion1 = new Conclusion(
				new Declaration(premise.getDeclaration().getComponents().get(0).getSymbol()));
		conclusion1.setMessage("And Elimination por " + premise.getID());
		Conclusion conclusion2 = new Conclusion(
				new Declaration(premise.getDeclaration().getComponents().get(2).getSymbol()));
		conclusion2.setMessage("And Elimination por " + premise.getID());

		conclusions.add(conclusion1);
		conclusions.add(conclusion2);

		return conclusions;
	}

	@Override
	public int getNumberOfPremises() {
		return 1;
	}

}
