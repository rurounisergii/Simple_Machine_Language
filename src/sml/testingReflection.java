package sml;

import java.io.File;
import java.io.FilenameFilter;

public class testingReflection {
	public static void main(String[] args){
		testingReflection launcher = new testingReflection();
		launcher.launch();
	}
	public void launch(){
		File file = new File("src/sml/");
		FilenameFilter fileFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.lastIndexOf(".") > 0){
					String fileType = name.substring(name.lastIndexOf("."));
					if (fileType != ".java"){
						return false;
					}
					if (name.substring(0,1).equals("A")){
						return true;
					}
				}
				return false;
			}
		};

		File[] filesInPackage = file.listFiles(fileFilter);
		for(File x : filesInPackage){
			System.out.println(x);
		}
		file.delete();	
	}
}

