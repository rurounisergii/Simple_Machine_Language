package smlTests;

import org.junit.*;
import static org.junit.Assert.*;
import sml.*;
import java.io.*;

public class DivideInstructionTest {
	private String fileName = "src" + File.separator + "divideTest.txt";
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
			out.println("f0 lin 0 80");
			out.println("f1 lin 1 10");
			out.println("f2 div 2 0 1"); 
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
	 * The Machine and Translator should be able to correctly the contained instructions
	 */
	@Test
	public void addTest(){
		testerRegisters.setRegister(0, 80);
		testerRegisters.setRegister(1, 10);
		testerRegisters.setRegister(2, 8);
		testerMachine = new Machine();
		Translator TranslatorTester = new Translator("divideTest.txt");
		TranslatorTester.readAndTranslate(testerMachine.getLabels(), testerMachine.getProg());
		testerMachine.execute();
		assertEquals(testerRegisters, testerMachine.getRegisters());
	}
}
