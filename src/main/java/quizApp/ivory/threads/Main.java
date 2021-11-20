package quizApp.ivory.threads;

public class Main 
{

	public static void main(String[] args) 
	{
		QuestionPrinter qp = new QuestionPrinter(10);
		
		ThreadClass t1 = new ThreadClass(qp, 1);
		ThreadClass t2 = new ThreadClass(qp,0);
		
		t1.start();
		t2.start();
		
		//putting a comment
		//putting a comment in Branch1
	}

}
