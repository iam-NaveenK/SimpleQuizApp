package quizApp.ivory.student;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Student
{
	private String userID;
	private int age;
	private String name;
	
	public Student(String userID, int age, String name)
	{
		this.userID = userID;		
		this.age = age;
		this.name = name;
	}
	
	public String getAttemptsPath()
	{
		ClassLoader classLoader = getClass().getClassLoader();
		
		URL resource = classLoader.getResource("Attempts/");
		
		return resource.getPath();
	}
	
	/**
	 * Displays all the quiz attempts user made so far
	 * @return saved attempt file names as a array list
	 */
	public ArrayList<String> displayPrevAttempts()
	{
		File directory = null;
		ArrayList<String> attempts = new ArrayList<>();
		
		int count = 0;
		
		try
		{
			directory = new File(getAttemptsPath());
			
			for(String attempt : directory.list())
			{
				
				if(attempt.contains(this.getUserID()))
				{
					count++;
					attempts.add(attempt);
					System.out.println(count + ":" + attempt.split(".json")[0]);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong with getting attempts");
			e.printStackTrace();
		}
		finally
		{
			directory.exists();
		}
		return attempts;
	}
	
	
	/**
	 * This method reads the json file of the saved attempt and displays it to the user in text format
	 * @param attemptName : the attempt user want to have a look at
	 */
	public void viewAttempt(String attemptName)
	{
		try {
		String attemptPath = getAttemptsPath() + attemptName; 
		Object obj = new JSONParser().parse(new FileReader(attemptPath));
		JSONObject jo = (JSONObject) (obj);
		
		System.out.println("-----------------------INFO-------------------------");
		System.out.println("Quiz name : " + jo.get("quizName"));
		System.out.println("Quiz subject : " + jo.get("subject"));
		System.out.println("Submission time : " + jo.get("submission time"));
		System.out.println("Marks you got :" + jo.get("marks"));
		System.out.println("----------------------------------------------------");
		
		JSONArray jaQues = (JSONArray)(jo.get("questions"));
		
		
		for(int i=0;i<jaQues.size();i++)
		{
			JSONObject joQue  = (JSONObject)jaQues.get(i);
			
			System.out.println("Q" + (i+1)  + ")" + joQue.get("question"));
			
			JSONArray jaChoices = (JSONArray) joQue.get("choices");
			
			for(int j=0;j<jaChoices.size();j++)
			{
				System.out.println("Choice " + (j+1) + ":" + jaChoices.get(j));
			}
			
			System.out.println("Actual answer : " + joQue.get("Actual answer"));
			System.out.println("Your answer : " + joQue.get("Your answer"));
			System.out.println("\n");
		}
		
		System.out.println("------------------END-------------------------");
		}
		catch(FileNotFoundException nf)
		{
			System.out.println("Your saved attempt is misplaced or removed");
		}
		catch(ParseException pe)
		{
			System.out.println("Something went wrong with reading the saved attempt file");
		}
		catch(IOException io)
		{
			System.out.println("Something went wrong with getting the saved attempt file");
		}
		
	}
	
	public int getAge() {
		return age;
	}

	public String getName() {
		return name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}	

}
