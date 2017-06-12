package logic.tester;

public class Main {
	
	public static void main(String[] args) {
		try {
			(new UserEntry()).run();
		} catch (Exception e) {
			System.out.println("Erro Inesperado");
			e.printStackTrace();
		}
	}

}
