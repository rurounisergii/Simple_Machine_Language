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
	Constructor[] allConstructors;
	Class[]constructorParameters;
	Object[] constructorArguments;
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
		
		if (line.equals("")){
			return null;
		}

		String ins = scan();
		String newins = (ins.substring(0,1)).toUpperCase() + ins.substring(1);
		String className = "sml" + File.separator + newins + "Instruction";
		try{
			Class instructionClass = Class.forName(className);
			getParamArgumentsAndTypes();
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
	 * A method that scans the remaining line to determine what the Argument Types
	 * will be for the constructor of the Instruction Class that needs to be made
	 * The Argument data types as well as the actual arguments are saved to 2 global arrays
	 * 
	 */
	public void getParamArgumentsAndTypes(){
		int counter = 0;
		ArrayList<Class> paraMeterTypesList = new ArrayList<Class>();
		ArrayList<Object> constructorArgumentsList = new ArrayList<Object>();
		do{
				String stringOrInt = scan();
				if (isLabel(stringOrInt)){
					paraMeterTypesList.add(counter, String.class);
					constructorArgumentsList.add(counter, stringOrInt);
					counter++;
				}
				else{
					int intRegister = scanInt(stringOrInt);
					paraMeterTypesList.add(counter, Integer.TYPE);
					constructorArgumentsList.add(counter, intRegister);
					counter++;					
				}
		} while (line != "");
		constructorArguments = constructorArgumentsList.toArray();
		constructorParameters = (Class[]) paraMeterTypesList.toArray();	
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

	// Return the first word of line as an integer. If there is
	// any error, return the maximum int
	private int scanInt(String intString) {
		if (intString.length() == 0) {
			return Integer.MAX_VALUE;
		}
		try {
			return Integer.parseInt(intString);
		} catch (NumberFormatException e) {
			return Integer.MAX_VALUE;
		}
	}
	

}