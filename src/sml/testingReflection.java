package sml;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class testingReflection {
	public static void main(String[] args){
		testingReflection launcher = new testingReflection();
		launcher.launch();
	}
	public void launch(){
		File file = new File("src/sml/");
		FilenameFilter fileFilterer = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.lastIndexOf(".") > 0){
					String fileType = name.substring(name.lastIndexOf("."));
					if (!fileType.equals(".java")){
						return false;
					}
					else if (name.indexOf("Instruction") != -1){
						return true;
					}
				}
				return false;
			}
		};

		

		String[] filesInPackage = file.list(fileFilterer);
		for(String x : filesInPackage){
			System.out.println(x);
		}
		file.delete();	
	
		System.out.println(this.getClass());
		
		try {
			Class<?> clazz = Class.forName("sml.MultiplyInstruction");
			Constructor[] allConstructors = clazz.getDeclaredConstructors(); 
			ArrayList<Object> parameterHolder = new ArrayList<Object>();
			parameterHolder.add("String");
			parameterHolder.add(new Integer(4));
			parameterHolder.add(new Integer(4));
			parameterHolder.add(new Integer(4));
			
			for (Constructor x : allConstructors){
				System.out.println(x);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

