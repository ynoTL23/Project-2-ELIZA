package ElizaProject;

public class QuestionBank {

	private String [] questions;
	private int currQuestionIndex;
	public static final int NUM_QUESTIONS = 12;
	int[] indexUsed = new int[NUM_QUESTIONS];
	
	public QuestionBank(){
		questions = new String [NUM_QUESTIONS]; //increase array size if you will add more questions
		populateQuestionArray();
	}
	
	private void populateQuestionArray(){
		questions[0] = "What three words describe you best?";
		questions[1] = "What is your best feature?";
		questions[2] = "What common saying or phrase describes you best?";
		questions[3] = "What’s the best thing that has happened to you this week?";
		questions[4] = "Who was your role model when you were a child?";
		questions[5] = "If you could have one wish come true, what would it be?";
		questions[6] = "If you could travel anywhere in the world, where would you go? Why?";
		questions[7] = "What’s your favorite season? Why?";
		questions[8] = "What animal do you wish humans could keep as a pet? Why?";
		questions[9] = "What do you do to improve your mood when you are in a bad mood?";
		questions[10] = "If you could time travel, would you go to the past or future? Why?";
		questions[11] = "Have any personal goals? What are they? What's the progress?";
		
		/*add more questions if you like so the experience seems more realistic when randomly selecting
		remember the number of questions cannot exceed the length of the array*/
}
	public String getNextQuestion() {
		// do stuff in here to get the next question.. randomly from the array...

		int x = (int) (Math.random() * NUM_QUESTIONS);
		String s = questions[x];
		questions[x] = "";
		return s;
		
	}

	public int getNumQuestions() {
		return NUM_QUESTIONS;
	}
	
	public void reset() {
		populateQuestionArray();
	}
}
