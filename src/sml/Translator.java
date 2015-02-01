package sml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/*
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 */
public class Translator {
	private Class[]constructorParameters; //Holds the Data Type for each parameter in the constructor of an instruction
	private Object[] constructorArguments; //Holds the registers and Label read from a line that will be passed as actual parameters to an instruction
	private int arraySize; //used to determine the size of constructorParameters and constructorArguments 
	private String opcode; //the opcode for an instruction
	// word + line is the part of the current line that's not yet processed
	// word has no whitespace
	// If word and line are not empty, line begins with whitespace
	private String line = "";
	private Labels labels; // The labels of the program being translated
	private ArrayList<Instruction> program; // The program to be created
	private String fileName; // source file of SML code

	private static final String SRC = "src";

	public Translator(String fileName) {
		this.fileName = SRC + "/" + fileName;
	}

	// translate the small program in the file into lab (the labels) and
	// prog (the program)
	// return "no errors were detected"
	public boolean readAndTranslate(Labels lab, ArrayList<Instruction> prog) {

		try (Scanner sc = new Scanner(new File(fileName))) {
			// Scanner attached to the file chosen by the user
			labels = lab;
			labels.reset();
			program = prog;
			program.clear();

			try {
				line = sc.nextLine();
			} catch (NoSuchElementException ioE) {
				return false;
			}
			// Each iteration processes line and reads the next line into line
			while (line != null) {
				// Store the label in label
				String label = scan();
				if (label.length() > 0) {
					Instruction ins = getInstruction(label);
					if (ins != null) {
						labels.addLabel(label);
						program.add(ins);
					}
				}

				try {
					line = sc.nextLine();
				} catch (NoSuchElementException ioE) {
					return false;
				}
			}
		} catch (IOException ioE) {
			System.out.println("File: IO error " + ioE.getMessage());
			return false;
		}
		return true;
	}

	// line should consist of an SML instruction, with its label already
	// removed. Translate line into an instruction with label label
	// and return the instruction
	public Instruction getInstruction(String label) {
		//Split the line to determine how many registers there are. The number of registers + Label 
		//make up the number of parameters for an instruction constructor. One of the words in line is the opcode 
		//which is not a parameter so subtract 1 to determine the size of the arrays for holding Constructor parameters
		this.arraySize = ((line.split("\\s+")).length) -1; //\\s+ : regular expression for whitespace
		constructorParameters = new Class[arraySize];
		constructorArguments = new Object[arraySize];
		//The first parameter of all instructions is the Label. Now that the two arrays above have been initialised
		//this information about Label can be added
		constructorParameters[0] = String.class;
		constructorArguments[0] = label;
		
		if (line.equals("")){
			return null;
		}
		
		//Scan the opcode and change the first letter to UpperCase to get correct Instruction Class name
		String opcode = scan();
		String newopcode = (opcode.substring(0,1)).toUpperCase() + opcode.substring(1);
		String className = "sml." + newopcode + "Instruction";

		try{
			Class instructionClass = Class.forName(className);
			getParamArgumentsAndTypes(); //determine what arguments will be passed to the instruction constructor 
			Constructor correctConstructor = instructionClass.getConstructor(constructorParameters);
			Object instanceFromConstructor = correctConstructor.newInstance(constructorArguments);
			Instruction instructionToReturn = (Instruction) instanceFromConstructor;
			return instructionToReturn;
		} catch(ClassNotFoundException e){
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	/*
	 * A method that scans the remaining line to determine what the argument data types
	 * will be for the constructor of the Instruction Class that needs to be made
	 * The  data-type information as well as the actual arguments are saved to 2 global arrays
	 * 
	 */
	public void getParamArgumentsAndTypes(){
		String stringOrInt;
		int counter = 1; //counter starts at 1 as the Label information has been added to position 0
		 while ((stringOrInt = scan()) != ""){
				if (isLabel(stringOrInt)){  //if the scanned word is a label, then its data type is String
					constructorParameters[counter] = String.class;
					constructorArguments[counter] = stringOrInt;
					counter++;
				}
				else{ //if the scanned word is not a Label, it must be a register and is therefore an int
					int intRegister = Integer.parseInt(stringOrInt); 
					constructorParameters[counter] = Integer.TYPE;
					constructorArguments[counter] = intRegister;
					counter++;					
				}
		}
	}

	/*
	 * A method for checking whether a String is a label
	 * @param String - the possible label
	 * @return - true or false depending on whether the String represents a label
	 */
	public boolean isLabel(String possibleLabel){
		if (labels.indexOf(possibleLabel) != -1){
			return true;
		}
		return false;
	}
	
	/*
	 * Return the first word of line and remove it from line. If there is no
	 * word, return ""
	 */
	private String scan() {
		line = line.trim();
		if (line.length() == 0)
			return "";
		int i = 0;
		while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
			i = i + 1;
		}
		String word = line.substring(0, i);
		line = line.substring(i);
		return word;
	}

}