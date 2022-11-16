import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileValidation {
	public static boolean processFilesForValidation(Scanner[] sc, PrintWriter[] pwIEEE, PrintWriter[] pwACM, PrintWriter[] pwNJ, int latexID, int num){
		while(sc[latexID].hasNextLine()){	
			try{
				String s = sc[latexID].nextLine();  
				
				if(s.contains("ARTICLE")){
					String authors = null;  
					String title = null;
					String journal = null;
					String volume = null;
					String number = null;
					String pages = null;
					String month = null;
					String year = null;
					String doi = null;
					String line = null;
					while(true){
						line = sc[latexID].nextLine();  
						if(line.contains("={}")){  
							num++; 
							int indexOfEqual = line.indexOf('=');
							String keyWord = line.substring(0,indexOfEqual);  
							throw new FileInvalidException("Error: Detected Empty Field!\n" 
								+ "============================\n\nProblem detected with input file: Latex"+(latexID+1)+".bib\n"
										+ "File is invalid: Field \""+keyWord+"\" is empty. Processing stopped at this point."
												+ " Other empty fields may be present as well.\n");	
						}
						
						if(line.equals("}")){ 
							break;
						}
						else if(line.contains("author={")){
							authors = line.substring(8,line.indexOf('}'));
						}else if(line.contains("title={")){
							title = line.substring(7,line.indexOf('}'));
						}else if(line.contains("journal={")){
							journal = line.substring(9,line.indexOf('}'));
						}else if(line.contains("volume={")){
							volume = line.substring(8,line.indexOf('}'));
						}else if(line.contains("number={")){
							number = line.substring(8,line.indexOf('}'));
						}else if(line.contains("pages={")){
							pages = line.substring(7,line.indexOf('}'));
						}else if(line.contains("month={")){
							month = line.substring(7,line.indexOf('}'));
						}else if(line.contains("year={")){
							year = line.substring(6,line.indexOf('}'));
						}else if(line.contains("doi={")){
							doi = line.substring(5,line.indexOf('}'));
						}
					}
					 
					String ieeeAuthor = authors.replace(" and",",");
					
					String acmAuthor = null;
					if(authors.indexOf("and") == -1){  
						acmAuthor = authors;
					}else
						acmAuthor = authors.substring(0,authors.indexOf("and"));
					
					String njAuthor = authors.replace("and", "&");
					 
					pwIEEE[latexID].println(ieeeAuthor+". \""+title+"\", "+journal+", vol. "+volume+", no. "+number+", p. "+pages+", "+month+" "+year+".\r\n");
					pwACM[latexID].println(acmAuthor+" et al. "+year+". "+title+". "+journal+". "+volume+", "+number+" ("+year+"), "+pages+". DOI: "+doi+".\r\n");
					pwNJ[latexID].println(njAuthor+". "+title+". "+journal+". "+volume+", "+pages+" ("+year+").\r\n");
						
				}
					
				
			}catch(FileInvalidException e){
			 
				String message = e.getMessage();
				System.out.println(message);
				String ieee = "IEEE"+(latexID+1)+".json";
				String acmi = "ACM"+(latexID+1)+".json";
				String nj = "NJ"+(latexID+1)+".json";
				pwIEEE[latexID].close(); 
				pwACM[latexID].close();
				pwNJ[latexID].close();
				File a = new File(ieee);  
				a.delete();
				File b = new File(acmi);
				b.delete();
				File c = new File(nj);
				c.delete();
				return false;
			}
		}
		pwIEEE[latexID].close(); 
		pwACM[latexID].close();
		pwNJ[latexID].close();
		return true;
	}
}
