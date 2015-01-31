package sml;

import java.io.File;

public class testingReflection {
	public static void main(String[] args){
		testingReflection launcher = new testingReflection();
		launcher.launch();
	}
	public void launch(){
		File file = new File("src/sml/");
		String[] filesInPackage = file.list();
		for(String x : filesInPackage){
			System.out.println(x);
		}
		file.delete();
		
		
	}
}
