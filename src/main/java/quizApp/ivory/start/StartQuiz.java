package quizApp.ivory.start;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.parser.ParseException;

import quizApp.ivory.attempt.*;
import quizApp.ivory.quiz.*;
import quizApp.ivory.student.*;

public class StartQuiz 
{
	/**
	 * This method display the options available for the user once he login.
	 * @param student Object
	 * @return The choice user made among 3 available
	 * @throws IOException
	 * @throws ParseException 
	 */
	public static int displayMainOptions(Student student) throws IOException, ParseException
	{
		System.out.println("---------------------PICK ONE--------------------------");
		System.out.println("1. Attempt a quiz");
		System.out.println("2. View previous attempt");
		System.out.println("3. Logout");
		
		Scanner in = new Scanner(System.in);
		int choice = 0; //default choice
		
		try
		{
			choice = in.nextInt();
		}
		catch(Exception e)
		{
			System.out.println("Invalid input!");
		}
		
		switch(choice)
		{
		case 1:
			ArrayList<String> avlQuizzes = QuizHelper.displayAvailableQuizzes();
			System.out.println("Enter appropriate quiz index [1-" + avlQuizzes.size() + "] to attempt:" );
			
			int quizChoice = 0;
			try 
			{
				quizChoice = in.nextInt();
			}
			catch(Exception e)
			{
				System.out.println("Invalid Choice");
			}
			
			if(1 <= quizChoice && quizChoice <= avlQuizzes.size())
			{
				Quiz quiz = QuizHelper.getQuiz(avlQuizzes.get(quizChoice-1));
				
				if(quiz == null)
				{
					System.out.println("The quiz you are trying to attempt is no more available");
					break;
				}
				
				Attempt attempt = new Attempt(student, quiz);
				
				double perc = attempt.attempt() * 100;
				String YesNo = null; 
				
				while(perc < 50)
				{
					
					System.out.println("You got less than " + perc + "% of marks, do you want to re-take the quiz?");
					do {
					System.out.println("Enter 1 to re-take or 2 to continue :");
					Scanner in2 = new Scanner(System.in);
					YesNo = in2.nextLine();
					}
					while(!YesNo.equals("1") && !YesNo.equals("2"));
					
					if(YesNo.equals("1"))
					{
						perc = attempt.attempt() * 100;
					}
					else
					{
						break;
					}
				}
			}
			else
			{
				System.out.println("Please enter the correct index");
			}
			break;
			
		case 2:
			ArrayList<String> attemptQuizzes = student.displayPrevAttempts();
			int attemptChoice = 0;
			
			if(attemptQuizzes.isEmpty())
			{
				System.out.println("No attempts made so far");
			}
			else 
			{
				try 
				{
					attemptChoice = in.nextInt();
				}
				catch(Exception e)
				{
					System.out.println("Invalid Choice");
				}
				
				if(1 <= attemptChoice && attemptChoice <= attemptQuizzes.size())
				{
					Attempt.viewAttempt(attemptQuizzes.get(attemptChoice-1));
				}
				else
				{
					System.out.println("Please enter the correct index");
				}
			}
			
			break;
		case 3:
			System.out.println("Logging out..");
			break;		
		default:
			System.out.println("Enter a valid choice");
				
		}
		
		return choice;		
	}
	
	/**
	 * This method display the sign in options for the user once he opens the application.
	 * @return Integer that indicates which choice the user made
	 * @throws IOException
	 * @throws ParseException 
	 */
	public static int displayOptions() throws IOException, ParseException
	{
		System.out.println("1. Login");
		
		System.out.println("2. Register");
		
		System.out.println("3. Exit");
		
		Student student = null;
		Scanner in = new Scanner(System.in);
		int choice = 0; //can be improved
		
		try
		{
			choice = in.nextInt();
		}
		catch(Exception e)
		{
			System.out.println("Invalid input!");
		}
		
		switch(choice)
		{
		case 1: //moduralize
			student = StudentHelper.login();
			
			
			if(student != null)
			{
				int mainChoice = displayMainOptions(student);
				
				while(mainChoice != 3)
				{
					mainChoice = displayMainOptions(student);
				}
				System.out.println("Logged out successfully");
			}
			break;
			
		case 2:
			student = StudentHelper.register();
			
			if(student != null)
			{
				int mainChoice = displayMainOptions(student);
				
				while(mainChoice != 3)
				{
					mainChoice = displayMainOptions(student);
				}
				System.out.println("Logged out successfully");
			}
			break;
			
		case 3:
			System.out.println("Exiting the program");
			break;
			
		default:
			System.out.println("Please enter a valid choice");
			break;
		}
		
		return choice;
	}

	public static void main(String[] args) throws IOException, ParseException 
	{
		System.out.println("Welcome to the Quiz Program, enter your choice");
		
		int choice = displayOptions();
		
		while(choice != 3)
		{
			choice = displayOptions();
		}
		
		System.out.println("Program closed successfully");
		
	}

}
