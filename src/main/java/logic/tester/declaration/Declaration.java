package logic.tester.declaration;

import java.util.ArrayList;
import java.util.List;

import logic.tester.declaration.components.Component;
import logic.tester.declaration.components.ComponentType;

public class Declaration {

	private List<Component> symbols = new ArrayList<>();

	private static final String LEFT_OR_RIGHT_REGEX = "[\\s]*[~]*[a-fA-F][\\s]*";
	
	private static final String OR_REGEX = LEFT_OR_RIGHT_REGEX + "[vV]" + LEFT_OR_RIGHT_REGEX;
	private static final String AND_REGEX = LEFT_OR_RIGHT_REGEX + "\\^" + LEFT_OR_RIGHT_REGEX;
	private static final String IMPLIES_REGEX = LEFT_OR_RIGHT_REGEX + "\\>" + LEFT_OR_RIGHT_REGEX;
	private static final String EQUIVALENT_REGEX = LEFT_OR_RIGHT_REGEX + "\\<\\>" + LEFT_OR_RIGHT_REGEX;
	private static final String VARIABLE_REGEX = LEFT_OR_RIGHT_REGEX;
	
	private boolean varType = false;

	public Declaration(String sequence) throws Exception {
		if (!this.isValid(sequence))
			throw new Exception("the sequence \""+sequence+"\" is not valid");

		for (int index = 0; index < sequence.length(); index++) {

			switch (sequence.toLowerCase().charAt(index)) {

			case ' ':
				continue;

			case '>':
				index = this.addImplies(sequence, index);
				break;

			case 'v':
				index = this.addOr(sequence, index);
				break;

			case '^':
				index = this.addAnd(sequence, index);
				break;

			case '<':
				index = this.addEquivalent(sequence, index);
				break;

			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case '~':
				index = this.addVariable(sequence, index);
				break;
			}

		}
	}

	private int addImplies(String sequence, int index) {
		this.addSymbol(new Component(String.valueOf(sequence.charAt(index)), ComponentType.IMPLIES));
		return index;
	}

	private int addOr(String sequence, int index) {
		this.addSymbol(new Component(String.valueOf(sequence.charAt(index)), ComponentType.OR));
		return index;
	}

	private int addAnd(String sequence, int index) {
		this.addSymbol(new Component(String.valueOf(sequence.charAt(index)), ComponentType.AND));
		return index;
	}

	private int addEquivalent(String sequence, int index) {
		String string = sequence.substring(index, index + 2);
		this.addSymbol(new Component(string, ComponentType.EQUIVALENT));
		return (index + 1);
	}

	private int addVariable(String sequence, int index) {
		if (sequence.charAt(index) == '~') {
			int i = index + 1;
			while(i < sequence.length() && sequence.charAt(i) == '~')
				++i;
			
			String string = sequence.substring(index, i + 1);
			this.addSymbol(new Component(string, ComponentType.VARIABLE));
			return i;
		} else
			this.addSymbol(new Component(String.valueOf(sequence.charAt(index)), ComponentType.VARIABLE));
		
		return index;
	}

	private void addSymbol(Component component) {
		this.symbols.add(component);
	}

	public List<Component> getComponents() {
		return this.symbols;
	}
	
	public boolean isVarType() {
		return this.varType;
	}

	private boolean isValid(String sequence) {
		
		if(sequence.matches(AND_REGEX))
			return true;
		if(sequence.matches(OR_REGEX))
			return true;
		if(sequence.matches(IMPLIES_REGEX))
			return true;
		if(sequence.matches(EQUIVALENT_REGEX))
			return true;
		if(sequence.matches(VARIABLE_REGEX)) {
			this.varType = true;
			return true;
		}
			

		return false;
	}
	
	@Override
	public String toString() {
		String string = "";
		for (Component component : symbols)
			string += component.getSymbol() + " ";
		
		return string.trim();
	}

}
