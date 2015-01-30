package smlTests;

import org.junit.*;
import static org.junit.Assert.*;
import sml.*;
import java.io.*;

/*
 * An integrated test which uses all types of Instructions 
 */
public class IntegratedInstructionTest {
	private String fileName = "src" + File.separator + "IntegratedTest.txt";
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
			out.println("f1 lin 1 4");
			out.println("f2 add 2 0 1"); //Add Instruction 5 + 4 = 9
			out.println("f4 lin 3 1");
			out.println("f5 sub 4 2 3"); //Subtract Instruction 9 - 1 = 8
			out.println("f7 lin 5 10");
			out.println("f8 mul 6 5 4"); //Multiply instruction 10 * 8 = 80
			out.println("f10 lin 7 5");
			out.println("f11 div 8 6 7"); //Divide instruction 80 / 5 = 16
			out.println("f12 lin 9 1");
			out.println("f13 lin 10 5");
			out.println("f14 mul 9 9 10");
			out.println("f15 sub 10 10 3");
			out.println("f16 bnz 10 f14"); //Finding the factorial of 5 with a loop
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
	public void IntegratedTest(){
		testerRegisters.setRegister(0, 5);
		testerRegisters.setRegister(1, 4);
		testerRegisters.setRegister(2, 9);
		testerRegisters.setRegister(3, 1);
		testerRegisters.setRegister(4, 8);
		testerRegisters.setRegister(5, 10);
		testerRegisters.setRegister(6, 80);
		testerRegisters.setRegister(7, 5);
		testerRegisters.setRegister(8, 16);
		testerRegisters.setRegister(9, 120);
		testerRegisters.setRegister(10, 0);
		testerMachine = new Machine();
		Translator TranslatorTester = new Translator("IntegratedTest.txt");
		TranslatorTester.readAndTranslate(testerMachine.getLabels(), testerMachine.getProg());
		testerMachine.execute();
		assertEquals(testerRegisters, testerMachine.getRegisters());
	}
}
