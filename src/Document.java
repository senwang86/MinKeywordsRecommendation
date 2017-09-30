import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Document implements Comparable<Document>{

	private HashMap<String,Integer> terms;
	private String filePath;
	public int score;
	
	public HashMap<String,Double> termScore;
	public double weightScore;
	
	public Document(String filePath){
		this.terms = new HashMap<String,Integer>();
		this.filePath = filePath;
		preProcessing(this.filePath,this.terms);
		termScore = new HashMap<String,Double>();
		
	}
		
	public HashMap<String,Integer> getTerms() {
		return terms;
	}

	public String getFilePath() {
		return filePath;
	}
	
	public HashMap<String,Double> getTermScores(){
		return this.termScore;
	}
	
	public String getFileName(){
		File f = new File(this.filePath);
		String fName = f.getName();
		f = null;
		return fName;
	}
	
	private void preProcessing(String folderPath,HashMap<String,Integer> termMap){
		FileInputStream fstream;
		BufferedReader br = null;
		try {
			fstream = new FileInputStream(folderPath);
			br = new BufferedReader(new InputStreamReader(fstream));

			String strLine = null;
			
			Pattern pattern = Pattern.compile("([A-Za-z]{3,})");
			Matcher matcher;
			String tempString;
			int tempCount;

			while ((strLine = br.readLine()) != null) {
				matcher = pattern.matcher(strLine);
				while(matcher.find()){
					tempString = matcher.group().toLowerCase();
					if(!tempString.matches("(?i)the")){
						
						if(termMap.containsKey(tempString)){
							tempCount = termMap.get(tempString);
							tempCount++;
							termMap.put(tempString, tempCount);
						}else{
							termMap.put(tempString, 1);
						}
							
					}	
//					System.out.println(tempString);
				}
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	@Override
	public int compareTo(Document d){
		return d.score - this.score;
	}
	/**
	 * Descending order!!! 
	 */
	public static Comparator<Document> weightComparator 
    = new Comparator<Document>() {

		public int compare(Document d1, Document d2) {

			if (d1.weightScore == d2.weightScore){
				return 0;
			}
			else{
				//Notice here, how the descending order is defined.
				return d1.weightScore < d2.weightScore ? 1 : -1;
			}
		}
	};

}