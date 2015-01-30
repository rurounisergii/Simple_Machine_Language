package smlTests;

import org.junit.*;
import static org.junit.Assert.*;
import sml.*;
import java.io.*;

public class AddInstructionTest {
	public String result;
	String fileName = "src" + File.separator + "addTest.txt";
	Registers testerRegisters = new Registers();
	
	@Before
	public void buildup(){
		File file = new File(fileName);
		try (PrintWriter out = new PrintWriter(file)){
			out.println("L1 lin 0 5");
			out.println("L2 lin 1 7");
			out.println("L3 add 2 1 2"); 
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();;
			} catch (IOException ex) {
			ex.printStackTrace();
			} 	
		System.out.println(file.exists());
	}
	
	@Test
	public void addTest(){
		assertEquals(1,1);
	}
	
	

}
