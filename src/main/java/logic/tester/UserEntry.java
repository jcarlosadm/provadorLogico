package logic.tester;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import logic.tester.declaration.Declaration;
import logic.tester.entities.Conclusion;
import logic.tester.entities.Premise;

public class UserEntry {
	
	public void run() throws Exception {
		boolean exit = false;
		while(exit == false) {
			try {
				List<Premise> premises = this.getPremises();
				Conclusion conclusion = this.getConclusion();
				
				BlackBox blackBox = new BlackBox();
				
				for (Premise premise : premises)
					blackBox.addPremisse(premise);
				
				blackBox.setConclusion(conclusion);
				
				if (blackBox.acceptConclusion() == true)
					System.out.println("ACEITA");
				else
					System.out.println("REJEITA");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			System.out.println("--------------------------\n");
			
			exit = (this.continueQuestion() == false);
		}
	}

	private List<Premise> getPremises() throws Exception {
		System.out.println("Para as premissas, use variáveis de \"a\" até \"f\" (maiúsculas ou minúsculas)");
		System.out.println("Não pode usar composições (use expressões simples, como \"a > b\")");
		System.out.println("símbolos: > (implica), <> (bi-implica), ~ (negação), ^ (E), v (OU)");
		System.out.println("digite as premissas, ou um ou mais \"=\" quando terminar:");
		
		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		List<Premise> premises = new ArrayList<>();
		
		boolean exit = false;
		while(exit == false) {
			String entry = bReader.readLine();
			entry = entry.trim();
			
			if (entry.contains("="))
				exit = true;
			else {
				try {
					premises.add(new Premise(new Declaration(entry)));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
		
		return premises;
	}

	private Conclusion getConclusion() throws Exception {
		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		Conclusion conclusion = null;
		
		boolean exit = false;
		while(exit == false) {
			try {
				conclusion = new Conclusion(new Declaration(bReader.readLine()));
				exit = true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		return conclusion;
	}

	private boolean continueQuestion() throws Exception {
		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("continuar? (\"no\" para terminar, ou qualquer outra palavra ou ENTER para continuar): ");
		
		String entry = bReader.readLine();
		if (entry.toLowerCase().trim().equals("no"))
			return false;
		
		return true;
	}

}
