package logic.tester;

import java.util.ArrayList;
import java.util.List;

import logic.tester.declaration.Declaration;
import logic.tester.entities.Conclusion;
import logic.tester.entities.Premise;
import logic.tester.methods.AndElimination;
import logic.tester.methods.AndIntroduction;
import logic.tester.methods.DisjunctiveSyllogism;
import logic.tester.methods.DoubleNegation;
import logic.tester.methods.HypotheticalSyllogism;
import logic.tester.methods.Method;
import logic.tester.methods.ModusPonens;
import logic.tester.methods.ModusTollens;
import logic.tester.methods.Resolution;
import logic.tester.methods.UnitResolution;

public class BlackBox {

	private boolean conclusionFound = false;
	private List<Premise> premises = new ArrayList<>();
	private Conclusion conclusion = null;

	public void addPremisse(Premise premise) {
		this.premises.add(premise);
	}

	public void setConclusion(Conclusion conclusion) {
		Premise premise = new Premise(conclusion.getDeclaration());

		DoubleNegation doubleNegation = new DoubleNegation();
		Conclusion other = null;
		try {
			other = doubleNegation.conclusions(premise).get(0);
			other.setMessage("");
		} catch (Exception e) {
			other = null;
		}

		if (other != null)
			this.conclusion = other;
		else
			this.conclusion = conclusion;
	}

	public boolean acceptConclusion() throws Exception {
		if (premises.isEmpty())
			throw new Exception("no premises!");

		if (conclusion == null)
			throw new Exception("no conclusion!");

		System.out.println();
		for (Premise premise : premises)
			System.out.println(premise.toString());

		for (Premise premise : premises) {
			if (this.conclusion.equals(premise)) {
				this.printConclusion();
				System.out.println("conclusão igual à premissa " + premise.getID());
				return true;
			}
		}

		Method[] methods = { new ModusPonens(), new ModusTollens(), new DisjunctiveSyllogism(),
				new HypotheticalSyllogism(), new AndIntroduction(), new AndElimination(), new UnitResolution(),
				new Resolution() };

		List<Premise> premisesExtra = new ArrayList<>();

		do {
			for (Premise premise : premisesExtra)
				System.out.println(premise.toString());

			premises.addAll(premisesExtra);
			premisesExtra.clear();

			List<Conclusion> conclusions = null;

			Method doubleNegation = new DoubleNegation();
			for (Premise premise : premises) {
				conclusions = doubleNegation.conclusions(premise);

				this.genNewConclusions(conclusions, premisesExtra);

				if (this.conclusionFound == true)
					return true;
			}

			for (int index1 = 0; index1 < premises.size(); ++index1) {

				for (Method method : methods) {

					if (method.getNumberOfPremises() == 1) {
						conclusions = method.conclusions(premises.get(index1));

						if (this.genNewConclusions(conclusions, premisesExtra))
							break;
					}
				}

				if (this.conclusionFound == true)
					return true;

				for (int index2 = index1 + 1; index2 < premises.size(); ++index2) {
					for (Method method : methods) {

						if (method.getNumberOfPremises() == 2) {
							conclusions = method.conclusions(premises.get(index1), premises.get(index2));

							if (this.genNewConclusions(conclusions, premisesExtra) == true)
								break;
						}

					}

					if (this.conclusionFound == true)
						return true;
				}
			}
		} while (premisesExtra.isEmpty() == false);

		this.printConclusion();
		return conclusionFound;
	}

	private boolean genNewConclusions(List<Conclusion> conclusions, List<Premise> premisesExtra) {
		if (conclusions == null)
			return false;

		boolean notNull = false;

		for (Conclusion conclusion : conclusions) {
			if (conclusion != null) {
				if (this.conclusion.equals(conclusion)) {
					this.printConclusion();
					System.out.println(conclusion.toString());
					this.conclusionFound = true;
					return true;
				}

				if (this.checkIfPremisesContainsDeclaration(conclusion.getDeclaration()) == false)
					premisesExtra.add(new Premise(conclusion.getDeclaration()));

				notNull = true;
			}
		}

		return notNull;
	}

	private void printConclusion() {
		System.out.println("==================");
		System.out.println(this.conclusion.toString());
	}

	private boolean checkIfPremisesContainsDeclaration(Declaration declaration) {
		for (Premise premise : premises) {
			if (premise.getDeclaration().toString().toLowerCase().trim()
					.equals(declaration.toString().toLowerCase().trim()))
				return true;
		}

		return false;
	}

}
