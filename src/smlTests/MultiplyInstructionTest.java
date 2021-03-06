package smlTests;

import org.junit.*;
import static org.junit.Assert.*;
import sml.*;
import java.io.*;

public class MultiplyInstructionTest {
	private String fileName = "src" + File.separator + "multiplyTest.txt";
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
			out.println("f0 lin 0 5");
			out.println("f1 lin 1 7");
			out.println("f2 mul 2 0 1"); 
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
	 */
	@Test
	public void multiplyTest(){
		testerRegisters.setRegister(0, 5);
		testerRegisters.setRegister(1, 7);
		testerRegisters.setRegister(2, 35);
		testerMachine = new Machine();
		Translator TranslatorTester = new Translator("multiplyTest.txt");
		TranslatorTester.readAndTranslate(testerMachine.getLabels(), testerMachine.getProg());
		testerMachine.execute();
		assertEquals(testerRegisters, testerMachine.getRegisters());
	}
}
