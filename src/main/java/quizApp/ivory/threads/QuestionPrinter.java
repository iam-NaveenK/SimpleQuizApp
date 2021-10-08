package quizApp.ivory.threads;

public class QuestionPrinter 
{	
	private static int currentQuestion = 1;
	private int totalQuestions;
	
	public QuestionPrinter(int totalQuestions)
	{
		this.totalQuestions = totalQuestions;
	}
	
	static
	{
		currentQuestion = 1;
	}
	
	
	public void printQuestion(int bit)
	{
		synchronized (this) 
		{
			while(currentQuestion < totalQuestions)
			{
				while(currentQuestion%2 != bit)
				{
					try 
					{
						//go and sleep
						wait();
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
				
				System.out.println(Thread.currentThread().getName() + ":" + currentQuestion++);
				//wake up sleeping thread
				notify();
			}
		}
	}

}
