package quizApp.ivory.threads;

public class ThreadClass extends Thread
{
	private QuestionPrinter qp;
	private int bit;
	
	public ThreadClass(QuestionPrinter qp, int bit)
	{
		this.qp = qp;
		this.bit = bit;
	}
	
	public void run()
	{
		qp.printQuestion(bit);
	}

}
