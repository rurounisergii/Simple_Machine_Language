package smlTests;

import org.junit.*;
import static org.junit.Assert.*;
import sml.*;
import java.io.*;

public class AddInstructionTest {
	private String result;
	private String fileName = "src" + File.separator + "addTest.txt";
	private File file;
	private Registers testerRegisters = new Registers();
	
	/*
	 * Create a file with instructions to be read by the SML Machine
	 */
	@Before
	public void buildUp(){
		file = new File(fileName);
		try (PrintWriter out = new PrintWriter(file)){
			out.println("L1 lin 0 5");
			out.println("L2 lin 1 7");
			out.println("L3 add 2 1 2"); 
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();;
			} catch (IOException ex) {
			ex.printStackTrace();
			} 	
		
	}
	
	/*
	 * Delete the file once tests are done
	 */
	@After
	public void cleanUp(){
		file.delete();
	}
	
	/*
	 * Add expected values into testerRegisters based on the file created in @Before
	 * Create a new Machine with name of the appropriate file as command line arguments
	 * Check expected registers are the same as the Machine registers
	 */
	@Test
	public void addTest(){
		testerRegisters.setRegister(0, 5);
		testerRegisters.setRegister(1, 7);
		testerRegisters.setRegister(2, 12);
		Machine testerMachine = new Machine();
		assertEquals(testerRegisters, testerMachine.getRegisters());
	}
}
