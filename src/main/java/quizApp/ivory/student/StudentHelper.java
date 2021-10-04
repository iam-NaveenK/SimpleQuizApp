package quizApp.ivory.student;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class StudentHelper 
{
	public String getLoginPath()
	{
		ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("Users/StudentLogin.json");
        return resource.getPath();
	}
	
	public String getDetailsPath()
	{
		ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("Users/StudentDetails.json");
        return resource.getPath();
	}
	
	public JSONObject isPresent(String userID,String path) throws IOException, ParseException
	{
		Object obj = new JSONParser().parse(new FileReader(path));
		JSONArray ja = (JSONArray) (obj);
		JSONObject jo = null;
		
		for(int i=0;i<ja.size();i++)
		{
			
			if(((JSONObject)(ja.get(i))).get("userID").equals(userID))
				return (JSONObject)(ja.get(i));
		}
		return jo;
	}
	
	public JSONObject getLogin(String userID) throws IOException, ParseException
	{
		return isPresent(userID, getLoginPath());
	}
	
	public JSONObject getDetails(String userID) throws IOException, ParseException
	{
		return isPresent(userID,getDetailsPath());
	}
	
	/**
	 * Takes userID and password from user and validates
	 * @return Student class object upon providing correct credentials
	 * @throws IOException
	 * @throws ParseException
	 */
	public Student login() throws IOException, ParseException
	{
		Student student = null;
		
		System.out.println("Enter the user ID :");
		String userID = getUserID();
		
		JSONObject jStudent = getLogin(userID);
		
		if(jStudent != null)
		{
			System.out.println("Enter password");
			String password = getPassword();
			
			if(jStudent.get("password").equals(password))
			{
				JSONObject jStudentDetails = getDetails(userID);
				
				//create student object 
				Gson gson = new GsonBuilder().create();
				student = gson.fromJson(jStudentDetails.toString(), Student.class);
			}
			else
			{
				System.out.println("Incorrect password");
			}
				
		}
		else
		{
			System.out.println("User not Found");
		}
		
		if(student != null)
			System.out.println(student.getName() + " , You are logged in successfully");
		
		return student;
	}
	
	/**
	 * Asks user details, validates and registers the user as well as stores the details provided during registration 
	 * @return Student class object upon successful registration
	 * @throws IOException
	 * @throws ParseException
	 */
	public Student register() throws IOException, ParseException
	{
		Student student = null;
		System.out.println("Enter the user ID :");
		String userID = getUserID();
		
		JSONObject jStudent = getLogin(userID);
		
		if(jStudent != null) //better name
		{
			System.out.println("User already exixsts");
		}
		else
		{
			System.out.println("Enter your name :");
			String name = getName();
			
			System.out.println("Enter your age :");
			int age = getAge();
			
			System.out.println("Enter password :");
			String password = getPassword(); //can be anything
			
			//to store user details
			JSONObject juser = new JSONObject();
			
			juser.put("name", name);
			juser.put("age", age);
			juser.put("userID", userID);
			
			//to store user login details
			JSONObject juserLogin = new JSONObject();
			
			juserLogin.put("userID", userID);
			juserLogin.put("password", password);
			
			Object obj = new JSONParser().parse(new FileReader(getDetailsPath()));
			JSONArray jaDetails = (JSONArray) (obj);
			
			obj = new JSONParser().parse(new FileReader(getLoginPath()));
			JSONArray jaLogin = (JSONArray) (obj);
			
			jaDetails.add(juser);
			
			jaLogin.add(juserLogin);
			
			PrintWriter pw = new PrintWriter(getDetailsPath());
			pw.write(jaDetails.toJSONString());
			pw.flush();
			pw.close();
			
			PrintWriter pw2 = new PrintWriter(getLoginPath());
			pw2.write(jaLogin.toJSONString());
			pw2.flush();
			pw2.close();
			
			//create student object 
			Gson gson = new GsonBuilder().create();
			student = gson.fromJson(juser.toString(), Student.class);
			
			System.out.println("User \"" + student.getName() + "\" successfully added and logged in");
			
		}
		
		return student;
	}
	
	public String getUserID()
	{
		Scanner in = new Scanner(System.in);
		String userID = null;
		boolean status = true;
		
		do
		{
			userID = in.nextLine();
			
			if(userID.length() > 10)
			{
				System.out.println("User ID is having more than 10 characters. Enter Again!");
			}
			else if(userID.contains(" "))
			{
				System.out.println("White spaces are not allowed in user ID. Enter Again!");
			}
			else
			{
				status = false;
			}
		}
		while(status);
		
		return userID;
	}
	
	public String getPassword()
	{
		Scanner in = new Scanner(System.in);
		String password = null;
		boolean status = true;
		
		do
		{
			password = in.nextLine();
			
			if(password.length() > 15)
			{
				System.out.println("Password is having more than 15 characters. Enter Again!");
			}
			else if(password.contains(" "))
			{
				System.out.println("White spaces are not allowed in password. Enter Correctly!");
			}
			else
			{
				status = false;
			}
		}
		while(status);
		
		return password;
	}
	
	public String getName()
	{
		Scanner in = new Scanner(System.in);
		String name = null;
		boolean status = true;
		
		do
		{
			name = in.nextLine();
			
			if(name.length() > 25)
			{
				System.out.println("name is having more than 25 characters. Enter Again!");
			}
			else
			{
				status = false;
			}
		}
		while(status);
		
		return name;
	}
	
	public int getAge()
	{
		int age = 0;
		boolean status = true;
		
		do
		{
			try
			{
				Scanner in = new Scanner(System.in);
				age = in.nextInt();
				status = false;
			}
			catch(Exception e)
			{
				System.out.println("Invalid input, Enter Again : [10-99] !");
			}
			
			if(!status && (age < 10 || age > 99))
			{
				System.out.println("Age is too high or too low, Enter Again : [10-99] !");
				status = true;
			}
		}
		while(status);
		
		return age;
	}

	
	

}
