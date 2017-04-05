package logic.tester.declaration;

import java.util.ArrayList;
import java.util.List;

import logic.tester.declaration.components.Component;

public class Declaration {

	private List<Component> symbols = new ArrayList<>();
	
	public void addSymbol(Component component) {
		this.symbols.add(component);
	}
	
	public List<Component> getDeclaration() {
		return this.symbols;
	}
	
}
