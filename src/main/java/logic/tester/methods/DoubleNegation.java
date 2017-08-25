package logic.tester.methods;

import java.util.ArrayList;
import java.util.List;

import logic.tester.declaration.Declaration;
import logic.tester.entities.Conclusion;
import logic.tester.entities.Premise;

public class DoubleNegation implements Method {

	@Override
	public List<Conclusion> conclusions(Premise... premises) throws Exception {
		Premise premise = premises[0];
		
		List<String> symbols = new ArrayList<>();
		symbols.add(premise.getDeclaration().getComponents().get(0).getSymbol());
		String connector = null;
		
		if(!premise.getDeclaration().isVarType()) {
			symbols.add(premise.getDeclaration().getComponents().get(2).getSymbol());
			connector = premise.getDeclaration().getComponents().get(1).getSymbol();
		}
		
		for(int i = 0; i < symbols.size() ; ++i) {
			String symbol = symbols.get(i);
			while (symbol.contains("~~")) {
				symbol = symbol.replace("~~", "");
				symbols.set(i, symbol);
			}
		}
		
		Conclusion conclusion = null;
		
		if (premise.getDeclaration().isVarType())
			conclusion = new Conclusion(new Declaration(symbols.get(0)));
		else
			conclusion = new Conclusion(new Declaration(symbols.get(0) + connector + symbols.get(1)));
		
		conclusion.setMessage("Double negation por " + premise.getID());
		
		List<Conclusion> conclusions = new ArrayList<>();
		conclusions.add(conclusion);
		
		return conclusions;
	}

	@Override
	public int getNumberOfPremises() {
		return 1;
	}

}
