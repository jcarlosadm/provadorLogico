package logic.tester.declaration.components;

public class Component {

	private String symbol = "";
	private ComponentType type = null;

	public Component(String symbol, ComponentType type) {
		this.symbol = symbol;
		this.type = type;
	}

	public String getSymbol() {
		return symbol;
	}

	public ComponentType getType() {
		return type;
	}
}
