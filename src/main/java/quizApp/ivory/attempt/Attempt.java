package quizApp.ivory.attempt;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import quizApp.ivory.quiz.*;
import quizApp.ivory.student.*;


public class Attempt 
{
	private Student student;
	private Quiz quiz;
	
	public Attempt()
	{
		
	}
	
	public Attempt(Student student, Quiz quiz)
	{
		this.student = student;
		this.quiz = quiz;
	}
	
	public String getAttemptsPath()
	{
		ClassLoader classLoader = getClass().getClassLoader();
		
		URL resource = classLoader.getResource("Attempts/");
		
		return resource.getPath();
	}
	
	/**
	 * This method is the one which display the questions and takes the answers from user for the particular quiz user
	 * chose to attempt
	 * @return Aggregate % of the score
	 */
	public double attempt()
	{
		int marks = 0;
		int quesCount = 0;
		ArrayList<Question> currQues = this.quiz.getQuestions();
		ArrayList<Integer> answers = new ArrayList<>();
		Collections.shuffle(currQues);
		
		
		System.out.println("-----------------------INFO--------------------------");
		System.out.println(this.quiz.toString());
		System.out.println("-----------------------START-------------------------");
		
		for(int i=0;i<this.quiz.getTotalQuestions();i++)
		{
			quesCount++;
			
			System.out.println("Q" + quesCount + ") " + currQues.get(i).getQuestion());
			
			int count = 1;
			for(String choice : currQues.get(i).getChoices())
			{
				System.out.println(count + " : " + choice);
				count++;
			}
			
			int userChoice = QuizHelper.getAnswer() - 1;
			answers.add(userChoice);
			
			if(currQues.get(i).isCorrect(currQues.get(i).getChoices()[userChoice]))
				marks++;	
		}
		
		System.out.println("-----------------------------------------------------");
		
		System.out.println("Dear " + this.student.getName() + ", you got " + marks + " marks in \"" +
		this.quiz.getQuizName() + "\" quiz.");
		
		System.out.println("------------------------END--------------------------");
		
		saveQuiz(currQues, answers, marks, new Date());
		
		return ((double)marks/quesCount);
		
	}
	
	/**
	 * This method is invoked by the attempt() method above to save the user quiz attempt in json format
	 * @param questions : Type Question class
	 * @param answers : Answers submitted by the user while attempting quiz
	 * @param marks : marks user got after the quiz attempt	
	 * @param date : to store the submission time of the attempt
	 * @return true if quiz saved successfully or else false
	 */
	public boolean saveQuiz(ArrayList<Question> questions, ArrayList<Integer> answers, int marks, Date date)
	{
		boolean saveStatus = false;
		
		JSONObject jquiz = new JSONObject();
		
		jquiz.put("quizName", this.quiz.getQuizName());
		jquiz.put("subject", this.quiz.getSubject());
		jquiz.put("submission time", String.valueOf(date));
		jquiz.put("marks", marks);
		
		JSONArray jaQues = new JSONArray();		
		
		int count = 0;
		for(Question q : questions)
		{
			JSONObject joQue = new JSONObject();
			joQue.put("question", q.getQuestion());
			joQue.put("Actual answer", q.getAnswer());
			
			JSONArray jaChoices = new JSONArray();
			for(String choice : q.getChoices())
			{
				jaChoices.add(choice);
			}
			
			joQue.put("choices", jaChoices);
			joQue.put("Your answer", q.getChoices()[answers.get(count)]);
			
			jaQues.add(joQue);
			
			
			count++;
			
			if(count == this.quiz.getTotalQuestions())
				break;
		}
		
		jquiz.put("questions", jaQues);
		
		try 
		{
		String attemptPath = getAttemptsPath() + this.student.getUserID() + "_" + 
		this.quiz.getQuizName() + "_Attempt" + attemptNo() + ".json";
		PrintWriter pw = new PrintWriter(attemptPath);
		pw.write(jquiz.toJSONString());
		pw.flush();
		pw.close();
		saveStatus = true;
		}
		catch(FileNotFoundException nf)
		{
			System.out.println("Attempt could not be saved");
		}
		
		return saveStatus;
	}
	
	/**
	 * This method will return the attempt number the user is making for the particular quiz
	 * useful while storing the attempt to include it in the save file name
	 * @return corresponding attempt number
	 */
	
	public int attemptNo()
	{
		File directory = null;
		
		int attempts = 0;
		
		try
		{
			directory = new File(getAttemptsPath());
			
			for(String attempt : directory.list())
			{
				if(attempt.contains(this.student.getUserID()) && attempt.contains(this.quiz.getQuizName()))
				{
					attempts++;
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong with getting no.of attempts");
			e.printStackTrace();
		}
		finally
		{
			directory.exists();
		}
		return attempts+1;
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
}
