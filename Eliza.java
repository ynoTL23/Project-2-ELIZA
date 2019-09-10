package ElizaProject;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Eliza extends JFrame implements ActionListener {

	JPanel mainJP, headerJP, userInputJP, upperJP, btnsJP;
	JLabel header, sessionInfo, question, answer;
	JTextField userInputTF;
	JButton sessionBtn, logBtn, longestWordPerLineBtn, SortedLongestBtn;
	JTextArea log;
	JScrollPane jsp;
	int sessionNum = 0;
	int questionNum;
	String q, name, fileName;
	Font font = new Font(Font.SANS_SERIF, Font.BOLD, 22);

	QuestionBank qb = new QuestionBank();
	fileHandler fh = new fileHandler();

	public Eliza() {

		name = JOptionPane.showInputDialog("Welcome to the ELIZA Project.\nType your name to begin a session.");

		if (name == null) {
			System.out.println("Terminating...");
			System.exit(0);
		}
		
		mainJP = new JPanel();
		mainJP.setLayout(new BoxLayout(mainJP, BoxLayout.Y_AXIS));
		headerJP = new JPanel();
		headerJP.setLayout(new GridLayout(2, 1));

		upperJP = new JPanel();
		upperJP.setLayout(new BoxLayout(upperJP, BoxLayout.Y_AXIS));

		sessionInfo = new JLabel("Welcome to the ELIZA Project, " + name, SwingConstants.CENTER);
		sessionInfo.setFont(font);
		question = new JLabel("", SwingConstants.CENTER);
		question.setFont(font);
		question.setText("<html>Start a session by clicking the button</html>");
		headerJP.add(sessionInfo);
		headerJP.add(question);

		upperJP.add(headerJP);

		userInputJP = new JPanel();
		userInputJP.setVisible(false);
		answer = new JLabel("Answer: ");
		userInputTF = new JTextField();
		
		userInputJP.add(answer);
		userInputJP.add(userInputTF);

		upperJP.add(userInputJP);

		log = new JTextArea();
		jsp = new JScrollPane(log);
		log.setEditable(false);
		log.setLineWrap(true);
		log.setWrapStyleWord(true);

		jsp.setVisible(false);
		upperJP.add(jsp);

		mainJP.add(upperJP);

		btnsJP = new JPanel();
		sessionBtn = new JButton("Start Session");
		sessionBtn.addActionListener(this);
		sessionBtn.setToolTipText("Start a new session");

		logBtn = new JButton("View All Logs");
		logBtn.setVisible(false);
		logBtn.setToolTipText("Shows a log of questions and answers from all sessions");
		logBtn.addActionListener(this);

		longestWordPerLineBtn = new JButton("View Longest Words");
		longestWordPerLineBtn.setVisible(false);
		longestWordPerLineBtn.setToolTipText("Show the longest word from each answer");
		longestWordPerLineBtn.addActionListener(this);

		SortedLongestBtn = new JButton("View Sorted Longest Words");
		SortedLongestBtn.setVisible(false);
		SortedLongestBtn.setToolTipText("Show the longest word from each answer, sorted alphabetically");
		SortedLongestBtn.addActionListener(this);

		btnsJP.add(logBtn);
		btnsJP.add(longestWordPerLineBtn);
		btnsJP.add(SortedLongestBtn);

		btnsJP.add(sessionBtn);

		mainJP.add(btnsJP);

		add(mainJP);
		setTitle("The ELIZA Project | Subject: " + name);
		setSize(616, 538);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String btn = e.getActionCommand();

		if ((btn.equals("Start Session")) || (btn.equals("New Session"))) {
			sessionNum++;
			questionNum = 0;
			fileName = name + " Session " + sessionNum + " Log.txt";
			fh.createFile(fileName);

			sessionInfo.setText("Session " + sessionNum);
			sessionBtn.setText("Next Question");
			sessionBtn.setToolTipText("Go to next question");

			qb.reset();

			userInputJP.setVisible(true);
			jsp.setVisible(false);
			logBtn.setVisible(false);
			longestWordPerLineBtn.setVisible(false);
			SortedLongestBtn.setVisible(false);
			getQuestion();

			userInputJP.setLayout(new BoxLayout(userInputJP, BoxLayout.X_AXIS));
			userInputJP.setMaximumSize(new Dimension(500, 20));

			answer.setFont(font);
			answer.setHorizontalAlignment(SwingConstants.CENTER);
			answer.setVisible(true);

			userInputTF.setHorizontalAlignment(JTextField.CENTER);
			userInputTF.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
			userInputTF.setText("");
			userInputTF.setVisible(true);

		} else if (btn.equals("Next Question")) {
			System.out.println("[Question " + questionNum + " > " + q + "] answered: " + userInputTF.getText());

			fh.appendToFile(fileName, q);
			fh.appendToFile(fileName, userInputTF.getText());
			getQuestion();
			userInputTF.setText("");
		} else if (btn.equals("Finish Session")) {
			fh.appendToFile(fileName, q);
			fh.appendToFile(fileName, userInputTF.getText());

			sessionBtn.setText("New Session");
			userInputJP.setVisible(false);
			sessionInfo.setText("End of Session " + sessionNum);
			question.setText("Displaying this session's log");
			jsp.setVisible(true);

			logBtn.setVisible(true);
			longestWordPerLineBtn.setVisible(true);
			SortedLongestBtn.setVisible(true);
			btnsJP.setVisible(true);

			String temp = fh.readFile(fileName);
			log.setText("### Session " + sessionNum + " Log of " + name + " ###\n");

			Scanner scan = new Scanner(temp);
			int qCount = 1;
			String longestWord = "", shortestWord = "";
			while (scan.hasNextLine()) {
				log.setText(log.getText() + "[Question " + qCount++ + "]: " + scan.nextLine() + "\n");

				String answerLine = scan.nextLine();
				String[] words = answerLine.split(" ");
				shortestWord = words[0];
				for (int i = 0; i < words.length; i++) {
					if (words[i].length() >= longestWord.length()) {
						longestWord = words[i];
					}
					if (words[i].length() < shortestWord.length()) {
						shortestWord = words[i];
					}
				}

				log.setText(log.getText() + "Answer: " + answerLine + "\n\n");
			}

			fh.appendToFile(fileName, "Session " + sessionNum + " Analysis: Interesting, \'" + longestWord + "\' and \'" + shortestWord + "\' seem to be important to you.");
			log.setText(log.getText() + "Session " + sessionNum + " Analysis: Interesting, \'" + longestWord + "\' and \'" + shortestWord + "\' seem to be important to you.");
			
		} else if (btn.equals("View All Logs")) {
			log.setText("");
			sessionInfo.setText("Viewing All Logs");
			question.setVisible(false);
			for (int x = 0; x < sessionNum; x++) {
				String file = name + " Session " + (x + 1) + " Log.txt";
				String temp = fh.readFile(file);
				log.setText(log.getText() + "### Session " + (x + 1) + " Log of " + name + " ###\n");

				Scanner scan = new Scanner(temp);
				int qCount = 1;
				while (scan.hasNextLine()) {
					log.setText(log.getText() + "[Question " + qCount++ + "]: " + scan.nextLine() + "\n");
					log.setText(log.getText() + scan.nextLine() + "\n\n");
				}
			}
			jsp.setVisible(true);
		} else if (btn.equals("View Longest Words")) {
			log.setText("The longest words from each answer was:\n");
			question.setVisible(false);
			String temp = fh.readFile(fileName);

			Scanner scan = new Scanner(temp);

			while (scan.hasNextLine()) {
				String longestWord = "";
				scan.nextLine();

				String answerLine = scan.nextLine();
				String[] words = answerLine.split(" ");
				for (int i = 0; i < words.length; i++) {
					if (words[i].length() >= longestWord.length()) {
						longestWord = words[i];
					}
				}
				log.setText(log.getText() + longestWord + "\n");
			}

		} else if (btn.equals("View Sorted Longest Words")) {
			log.setText("The longest words from each answer, sorted alphabetically, is:\n");
			question.setVisible(false);
			String temp = fh.readFile(fileName);

			Scanner scan = new Scanner(temp);
			String[] longestWords = new String[qb.getNumQuestions()];
			System.out.println("longestwords arr size " + longestWords.length);
			int lineCount = 0;

			while (scan.hasNextLine()) {
				String longestWord = "";
				scan.nextLine(); // question line, skip

				String answerLine = scan.nextLine(); // answer line
				String[] words = answerLine.split(" "); // separate words into  array
				for (int i = 0; i < words.length; i++) {
					if (words[i].length() >= longestWord.length()) {
						// check if each word in arr is longer than current longest word for that line
						longestWord = words[i]; // set curr longest to new one
					}
				}
				// set longest word for that line
				longestWords[lineCount] = longestWord;
				lineCount++;
			}

			Arrays.sort(longestWords); // sort the longest words
			for (int l = 0; l < longestWords.length; l++) { // log them
				log.setText(log.getText() + longestWords[l] + "\n");
			}
		}
	}

	public void getQuestion() {
		q = qb.getNextQuestion();

		if (q.equals("")) {
			if (questionNum >= qb.getNumQuestions() - 1) {
				getQuestion();
				sessionBtn.setText("Finish Session");
				sessionBtn.setToolTipText("End of the session");

			} else {
				getQuestion();
			}
		} else {
			questionNum++;
			question.setText(
					"<html> <div style='text-align: center;'>Question " + questionNum + ": " + q + "</div></html>");
			question.setHorizontalAlignment(JLabel.CENTER);
		}

	}

	public int getLines(String s) {
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.substring(i, i + 1).equals("\n"))
				count++;
		}
		
		return count + 1;
	}

}
