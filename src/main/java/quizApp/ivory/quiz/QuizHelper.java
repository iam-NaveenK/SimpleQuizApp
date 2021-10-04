package quizApp.ivory.quiz;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class QuizHelper 
{	
	/**
	 * This method is useful while taking input from the user for a quiz question
	 * Asks the user right input until provided
	 * @return the choice user made {1,2,3,4} for the given question as an integer
	 */
	
	public String getQuizFilePath(String quizName)
	{
		ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("Quizzes/"+quizName);
        return resource.getPath();
	}
	
	public static  int getAnswer()
	{
		Scanner in = new Scanner(System.in);
		String answer = null;
		boolean status = true;
		
		do
		{
			answer = in.nextLine();
			
			if(answer.equals("1") || answer.equals("2") || answer.equals("3") 
					|| answer.equals("4"))
			{
				status = false;
			}
			else
			{
				System.out.println("Please enter a valid choice in {1,2,3,4} :");
			}
		}
		while(status);
		
		return Integer.parseInt(answer);		
	}
	
	
	/**
	 * This method will display all the available quizzes for the user to attempt
	 * @return quiz file names in a array list
	 */
	public ArrayList<String> displayAvailableQuizzes()
	{
		ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("Quizzes/");
        
		File directory = new File(resource.getPath());
		
		String folderContents[] = directory.list();
		ArrayList<String> quizFiles = new ArrayList<>();
		
		
		int count = 1;
		for(int i=0;i<folderContents.length;i++)
		{
			if(folderContents[i].contains(".json"))
			{
				quizFiles.add(folderContents[i]);
				
				System.out.println(count + ": " + folderContents[i].split(".json")[0]);
				
				count++;
			
			}
		}
		
		return quizFiles;
	}
	
	
	/**
	 * This method reads the appropriate quiz file stored in json format 
	 * and converts quiz json object into Quiz class object and returns it.
	 * @param quizName : The name of the quiz(json file name) we want to access
	 * @return Quiz class object.
	 */
	
	public Quiz getQuiz(String quizName)
	{
		String quizPath = getQuizFilePath(quizName);
		Quiz quiz = null;
		
		try
		{
			//access the json quiz object from stored files
			Object obj = new JSONParser().parse(new FileReader(quizPath));
			JSONObject joQuiz = (JSONObject) (obj);
			
			//create quiz object through conversion 
			Gson gson = new GsonBuilder().create();
			quiz = gson.fromJson(joQuiz.toString(), Quiz.class);
			
		}
		catch(FileNotFoundException nf)
		{
			System.out.println("Quiz you are trying to access is removed");
		}
		catch(ParseException pe)
		{
			System.out.println("Something went wrong with reading the quiz file");
		}
		catch(IOException io)
		{
			System.out.println("Something went wrong with accessing the quiz file");
		}
		return quiz;
	}

}
