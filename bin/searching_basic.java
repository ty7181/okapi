import java.util.*;
import java.io.*;
import java.lang.*;
import java.util.regex.*;

//AUTHOUR: RAYMOND NUQUI
//DATE: 1 May 2007

//THIS IS A SIMPLE DEMONSTRATION OF THE METHODS IN JAVOKAPI.

public class searching_basic{
	
	public searching_basic() throws Exception{
        String out = "Welcome to the Okapi Information Retrieval Engine.\n";
        javokapi okapi = new javokapi();  //CREATES NEW INSTANCE OF JAVOKAPI
	System.out.println("Welcome to the Okapi Information Retrieval Engine!\n");
	System.out.println("Available Databases:" + okapi.infoDB()); //OUTPUTS THE AVAILABLE DATABASES


	//ASK THE USER WHAT DATABASE THEY WOULD LIKE TO USE
	System.out.println("Enter Database name: ");	
	BufferedReader db = new BufferedReader(new InputStreamReader(System.in));
	String databaseName = null;
	databaseName = db.readLine();
	System.out.println("The database you are you using is: \n" + databaseName);

	System.out.println(okapi.displayStemFunctions());
	okapi.chooseDB(databaseName); //CHOOSE THE DATABASE UESR INPUTTED
	okapi.deleteAllSets(); //CLEAR ALL EXISTING DATASETS IF ANY
	
	//ASK THE USER WHICH TERMS TO PARSE
	System.out.println("Enter term(s) to parse (multiple terms supported): ");
	BufferedReader term = new BufferedReader(new InputStreamReader(System.in));
	String parseWord = null;
	parseWord = term.readLine();
	System.out.println("Parsed term(s): " + okapi.superParse("",parseWord));  //OUTPUT THE PARSED TERMS

	
	//ASK THE USER WHICH TERM TO SEARCH
	System.out.println("Enter term to search (multiple terms not supported): ");
	term = new BufferedReader(new InputStreamReader(System.in));
	String searchWord = null;
	searchWord = term.readLine();
	String stemmedSearchWord=okapi.stem("psstem",searchWord); //FINDS THE ROOT OF THE WORD (i.e. 'running' becomes 'run')
	String trimString = stemmedSearchWord.replace( "t=", "");  //Trims the t= produced from the stem() function
	stemmedSearchWord = trimString;
	System.out.println("Stemmed Search Word: " + stemmedSearchWord);
	String found = null;
	String np = null;
	found = okapi.find("1","0","DEFAULT", stemmedSearchWord); //QUERIES OKAPI WITH THE SEARCH WORD ENTERED BY USER
	System.out.println("Results: "+ found);
	String splitFound = null;

	np = findNP(found,stemmedSearchWord);  //SEE METHOD findNP() BELOW
	int npInt = Integer.parseInt(np);
	
	String weight = okapi.weight("2",np,"0","0","4","5");
	System.out.println("weight()= "+ weight);  //OUTPUT THE WEIGHT FOUND BY THE weight() FUNCTION
	System.out.println("setFind()= " + okapi.setFind("0", weight,"")); //OUTPUT THE SET
	System.out.println(okapi.displayStemFunctions());
	
	//DISPLAYS ALL THE RECORDS FOUND
	System.out.println("\n\nYour query returned the following results");
	for (int i=0; i<5; i++){
		//System.out.println();
		System.out.println("Record #" + i + ": "+okapi.showSetRecord("197", "1", Integer.toString(i),"0"));
	}

	}
	
	//THESE ARE JUST DEMOS OF THE METHODS AND HAVE BEEN COMMENTED OUT TO KEEP OUTPUT ORGANIZED
	/*System.out.println(System.getenv("BSS_PARMPATH"));
	okapi.setMainParameter("2004gendoc");
	okapi.setSearchGroupsParamFile("2004gendoc");
	System.out.println(okapi.showRecord("1", "1"));*/

	private String findNP(String toSplit, String queryTerm) //THIS METHOD REMOVES THE EXCESS OUTPUT GENERATED BY OKAPI AND RETURNS ONLY THE VALUE OF NP
	{	
		//--------------THIS CODE REMOVES THE "SO np=" PART GENERATED BY OKAPI
		//Create a pattern to match cat
	        Pattern p = Pattern.compile("S\\d np=");
	        // Create a matcher with an input string
	        Matcher m = p.matcher(toSplit);
	        StringBuffer sb = new StringBuffer();
	        boolean result = m.find();
	        // Loop through and create a new String 
	        // with the replacements
	        while(result) {
	            m.appendReplacement(sb, "");
	            result = m.find();
	        }
	        // Add the last segment of input to 
	        // the new String
	        m.appendTail(sb);
		//System.out.println(sb.toString());
		String secondSplit = sb.toString();

		//--------------THIS CODE REMOVES THE "t=" PART GENERATED BY OKAPI
	        Pattern p2 = Pattern.compile("t=");
	        // Create a matcher with an input string
	        Matcher m2 = p2.matcher(secondSplit);
	        StringBuffer sb2 = new StringBuffer();
	        boolean result2 = m2.find();
	        // Loop through and create a new String 
	        // with the replacements
	        while(result2) {
	            m2.appendReplacement(sb2, "");
	            result2 = m2.find();
	        }
	        // Add the last segment of input to 
	        // the new String
	        m2.appendTail(sb2);
		String thirdSplit=sb2.toString();

		//--------------THIS CODE REMOVES THE "term searched" GENERATED BY OKAPI
		Pattern p3 = Pattern.compile(queryTerm);
	        // Create a matcher with an input string
	        Matcher m3 = p3.matcher(thirdSplit);
	        StringBuffer sb3 = new StringBuffer();
	        boolean result3 = m3.find();
	        // Loop through and create a new String 
	        // with the replacements
	        while(result3) {
	            m3.appendReplacement(sb3, "");
	            result3 = m3.find();
	        }
	        // Add the last segment of input to 
	        // the new String
	        m3.appendTail(sb3);
		String finalResult = sb3.toString();
		finalResult = finalResult.trim();
		return finalResult;
		
	}

	
	public static void main(String[] args) throws Exception{
	 	searching_basic s = new searching_basic();
 	}

} 