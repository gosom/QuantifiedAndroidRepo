package quantifiedAndroid.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GeneralUtilities {
	
	/*
	 * @param filepath path of the file 
	 * 
	 */
	public static byte[] getByteArrayFromFile(String filepath) 
			throws FileNotFoundException, IOException{
		
		File file = new File(filepath);
		FileInputStream fin = new FileInputStream(file);
		
		byte fileContent[] = new byte[(int) file.length()];
		
		fin.read(fileContent);
		fin.close();

		return fileContent;
		
	}
	

}
