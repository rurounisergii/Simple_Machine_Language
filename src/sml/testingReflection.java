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
			Class[] constructParaneters = allConstructors[0].getParameterTypes();
			try {
				Constructor c = clazz.getConstructor(new Class[]{String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE});
				System.out.println("Consturcot: "+ c);
				Class[] types1 = new Class[4];
				types1[0] = String.class;
				types1[1] = Integer.TYPE;
				types1[2] = Integer.TYPE;
				types1[3] = Integer.TYPE;
				Constructor d = clazz.getConstructor(types1);
				System.out.println("Consturcot: "+ d);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			/*Object[] parameterHolder = new Object[4];
			parameterHolder[0] = ("String");
			parameterHolder[1] = (new Integer(4));
			parameterHolder[2] = (new Integer(4));
			parameterHolder[3] = (new Integer(4));
			
			Class[] classTypes = new Class[parameterHolder.size()];
			for (int x = 0; x < parameterHolder.length; x++){
				Class<?> c = Class.forName(parameterHolder[x]);
				classTypes[x] = c.getClass();
			}
			*/
			for (Constructor x : allConstructors){
				System.out.println(x);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

