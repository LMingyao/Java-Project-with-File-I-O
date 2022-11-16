import java.util.Scanner;
import java.io.*;

public class BibCreator {
	
	public static void main (String[] args){
		int invalidnum = 0;//number of invalid files 
		System.out.println("------------Welcome to BibCreator! Created By Mingyao & Borui------------\n");
		Scanner scfile = new Scanner(System.in);  //scanner for getting which file the user want to review
		Scanner[] sc = new Scanner[10]; //File Scanner
		
		PrintWriter[] IEEE = new PrintWriter[10]; //Three PrintWriters
		PrintWriter[] ACM = new PrintWriter[10];   
		PrintWriter[] NJ = new PrintWriter[10];
		
		String[] fileNames = new String[30]; 
		
		
		for(int i = 0; i <= 9; i++){             //The loop for reading latex files
			String s = "Latex"+(i+1)+".bib"; //The name of the latex file

			try{
				sc[i] = new Scanner(new FileInputStream(s));  
			}catch(FileNotFoundException e){
				System.out.println("Could not open input file "+s+" for "
				+ "reading.\n\nPlease check if file exists! Program "
				+ "will terminate after closing any opened files.\n");
				
				
				if(sc[i] != null) 
					sc[i].close();
				System.exit(0);
			}
			
			
		
			String ieee = "IEEE"+(i+1)+".json";
			int count = 0;
			fileNames[count] = ieee;  
			count++;  
			String acm = "ACM"+(i+1)+".json";
			fileNames[count] = ieee;
			count++;
			String nj = "NJ"+(i+1)+".json";
			fileNames[count] = ieee;
			count++;
				
			try{
				IEEE[i] = new PrintWriter(new FileOutputStream(ieee));  
			}catch(FileNotFoundException e){
				System.out.println("The file named \""+ieee+"\" could not be created.");
				for(int j = 0; j < fileNames.length; j++){
					//Check if we have those files before, if we have, delete them and re-create them
					if(fileNames[j] != null){  
						File f = new File(fileNames[j]);
						f.delete();
					}
				}
				System.exit(0);
				 
			}
			try{
				ACM[i] = new PrintWriter(new FileOutputStream(acm));
			}catch(FileNotFoundException ee){
				System.out.println("The file named \""+acm+"\" could not be created.");
				for(int j = 0; j < fileNames.length; j++){
					if(fileNames[j] != null){
						File f = new File(fileNames[j]);
						f.delete();
					}
				}
				System.exit(0);
			}
			try{
				NJ[i] = new PrintWriter(new FileOutputStream(nj));
			}catch(FileNotFoundException eee){
				System.out.println("The file named \""+nj+"\" could not be created.");
				for(int j = 0; j < fileNames.length; j++){
					if(fileNames[j] != null){
						File f = new File(fileNames[j]);
						f.delete();
					}
				}
				System.exit(0);
			}
		
		 
			
			if(!FileValidation.processFilesForValidation(sc, IEEE, ACM, NJ, i, invalidnum)){ 
				
				invalidnum++; 
			}
		sc[i].close();	
		}
		 
		
		
		 
		System.out.println("A total of "+invalidnum+" files were invalid, and could not be processed. "
				+ "All other "+(10-invalidnum)+" \"Valid\" files have been created.");

		 //---------------------The operation of reading files and writing three format files is finished--------------------------
		
		System.out.print("Please enter the name of one of the files you need to review: \n");
		//First Chance
		String namefile = scfile.next(); 
		
		BufferedReader br = null; 
		try{
			br = new BufferedReader(new FileReader(namefile)); 
		}catch(FileNotFoundException e){
			System.out.println("Could not open input file. File does not exist; possibly it could not be created!\n\n"
					+ "Re-enter it please.\n");
			
			System.out.print("Please enter the name of a file you would like to review\n");
			//Second Chance for first time failure
			namefile = scfile.next();
			try{
				br = new BufferedReader(new FileReader(namefile));
			}catch(FileNotFoundException ee){
				System.out.println("Could not open input file; File does not exist; possibly it could not be created!\n");
				System.exit(0);
			}
		}
		
		try{
			String s = br.readLine(); 
			while(br.readLine()!=null){
				System.out.println("\n"+s);
				s = br.readLine();
			}
			br.close(); 
		}catch(IOException e){
			String m = e.getMessage();
			System.out.println(m+" System Exit");
			System.exit(0);
		}
		scfile.close();
		System.out.println("\nByebye, thank you for using BibCreator");
	}
}

