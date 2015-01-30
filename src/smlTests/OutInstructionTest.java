package smlTests;

import sml.*;
import java.io.*;

public class OutInstructionTest {
	private String fileName = "src" + File.separator + "outTest.txt";
	private File file;
	private Machine testerMachine;
	private String[] mainArgs = {"OutTest.txt"};
	
	public static void main(String[]args){
		OutInstructionTest launcher = new OutInstructionTest();
		launcher.launch();
	}
	
	public void launch(){
		file = new File(fileName);
		try (PrintWriter out = new PrintWriter(file)){
			out.println("f0 lin 12 20");
			out.println("f1 out 12"); 
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();;
			} catch (IOException ex) {
			ex.printStackTrace();
			} 	
			System.out.println("This is a non-junit test to test the Out Instruction");
			System.out.println("I expect the number 12 to be printed on the console:");
			testerMachine = new Machine();
			Translator TranslatorTester = new Translator("outTest.txt");
			TranslatorTester.readAndTranslate(testerMachine.getLabels(), testerMachine.getProg());
			testerMachine.execute();
			file.delete();
			
		}
}
	


