package sml;

public class OutInstruction extends Instruction {
	private int registerToPrint;
	
	public OutInstruction(String label, int register) {
		super(label, "out");
		this.registerToPrint = register;
	}

	@Override
	public void execute(Machine m) {
		System.out.println(m.getRegisters().getRegister(registerToPrint));

	}
	@Override
	public String toString() {
		return super.toString() + " print register " + registerToPrint ;
	}

}
