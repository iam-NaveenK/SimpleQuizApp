package quizApp.ivory.quiz;

import java.util.ArrayList;

public class Quiz 
{
	private String quizName;
	private String subject;
	private int totalQuestions;
	private ArrayList<Question> questions;
	
	
	public Quiz(String name, String subject, int totalQuestions, ArrayList<Question> questions)
	{
		this.quizName = name;
		this.subject = subject;
		this.totalQuestions = totalQuestions;
		this.questions = questions;
	}
	
	/**
	 * Just displays the quiz information including total questions, name, subject.
	 */
	public String toString()
	{
		String instructions;
		
		instructions = "This \"" + this.quizName + "\" quiz contains a total of " 
		+ this.totalQuestions + " questions and is based on the subject \"" + this.subject + "\". Good luck!"; 
		
		return instructions;
	}
	
	public String getQuizName() {
		return quizName;
	}

	public String getSubject() {
		return subject;
	}

	public int getTotalQuestions() {
		return totalQuestions;
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setTotalQuestions(int totalQuestions) {
		this.totalQuestions = totalQuestions;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}	

}
