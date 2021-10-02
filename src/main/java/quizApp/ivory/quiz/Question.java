package quizApp.ivory.quiz;

public class Question 
{
	private String question;
	private String[] choices;
	private String answer;
	private int marks;
	
	public Question(String question, String[] choices, String answer)
	{
		this.question = question;
		this.choices = choices;
		this.answer = answer;
		this.marks = 1;
	}
	
	/**
	 * Validates if the parameter provided matches with the question answer
	 * @param answer
	 * @return true if answer provided is correct else false
	 */
	public boolean isCorrect(String answer)
	{
		return (this.answer.equals(answer));
	}

	public String getQuestion() {
		return question;
	}

	public String[] getChoices() {
		return choices;
	}

	public String getAnswer() {
		return answer;
	}

	public int getMarks() {
		return marks;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setChoices(String[] choices) {
		this.choices = choices;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public void setMarks(int marks) {
		this.marks = marks;
	}	

}
