package sml;

public class BnzInstruction extends Instruction {

	private String newLabel;
	private int oldRegister;

	public BnzInstruction(String label, int result, String op1) {
		super(label, "bnz");
		this.oldRegister = result;
		this.newLabel = op1;
	}

	@Override
	public void execute(Machine m) {
		int value1 = m.getRegisters().getRegister(oldRegister);
		if (value1 != 0){
			m.setPc(m.getLabels().indexOf(newLabel));
		}
	}

	@Override
	public String toString() {
		return super.toString() + " " + "If register: " + oldRegister + " contents is not 0, "
				+ "Change program counter to statement: " + newLabel;
	}
}
