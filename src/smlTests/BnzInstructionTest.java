package smlTests;

import org.junit.*;
import static org.junit.Assert.*;
import sml.*;
import java.io.*;

public class BnzInstructionTest {
	private String fileName = "src" + File.separator + "bnzTest.txt";
	private File file;
	private Registers testerRegisters = new Registers();
	private Machine testerMachine;
		
	/*
	 * Create a file with instructions to be read by the SML Machine
	 */
	@Before
	public void buildUp(){
		file = new File(fileName);
		try (PrintWriter out = new PrintWriter(file)){
			out.println("f0 lin 20 6");
			out.println("f1 lin 21 1");
			out.println("f2 lin 22 1");
			out.println("f3 mul 21 21 20");
			out.println("f4 sub 20 20 22");
			out.println("f5 bnz 20 f3");
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
	 * Add expected final register values to testerRegisters
	 * Create a Machine and Translator to process the lines written to a file in @Before
	 * The Machine and Translator should be able to correctly process the contained instructions
	 * Using Factorial of 6 algorithm to check bnz Instruction:
	 */
	@Test
	public void bnzTest(){
		testerMachine = new Machine();
		Translator TranslatorTester = new Translator("bnzTest.txt");
		TranslatorTester.readAndTranslate(testerMachine.getLabels(), testerMachine.getProg());
		testerMachine.execute();
		assertEquals(720, testerMachine.getRegisters().getRegister(21)); // 6!  = 720 
	}
}
