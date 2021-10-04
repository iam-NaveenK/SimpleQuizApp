package quizApp.ivory.student;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

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
