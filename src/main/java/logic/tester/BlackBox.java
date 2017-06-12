package logic.tester;

import java.util.ArrayList;
import java.util.List;

import logic.tester.declaration.Declaration;
import logic.tester.entities.Conclusion;
import logic.tester.entities.Premise;
import logic.tester.methods.DisjunctiveSyllogism;
import logic.tester.methods.HypotheticalSyllogism;
import logic.tester.methods.Method;
import logic.tester.methods.ModusPonens;
import logic.tester.methods.ModusTollens;

public class BlackBox {

	private List<Premise> premises = new ArrayList<>();
	private Conclusion conclusion = null;

	public void addPremisse(Premise premise) {
		this.premises.add(premise);
	}

	public void setConclusion(Conclusion conclusion) {
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
				new HypotheticalSyllogism() };

		List<Premise> premisesExtra = new ArrayList<>();

		do {
			for (Premise premise : premisesExtra)
				System.out.println(premise.toString());
			
			premises.addAll(premisesExtra);
			premisesExtra.clear();

			for (int index1 = 0; index1 < premises.size(); ++index1) {
				for (int index2 = index1 + 1; index2 < premises.size(); ++index2) {
					Conclusion conclusion = null;
					for (Method method : methods) {
						conclusion = method.conclusion(premises.get(index1), premises.get(index2));
						if (conclusion != null) {
							if (this.conclusion.equals(conclusion)) {
								this.printConclusion();
								System.out.println(conclusion.toString());
								return true;
							}

							if (this.checkIfPremisesContainsDeclaration(conclusion.getDeclaration()) == false)
								premisesExtra.add(new Premise(conclusion.getDeclaration()));

							break;
						}
					}
				}
			}
		} while (premisesExtra.isEmpty() == false);

		this.printConclusion();
		return false;
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
