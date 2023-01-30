package examples;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class AminoAcidQuizGUI extends JFrame
{
	
		private static final long serialVersionUID = 3794059922116115530L;
		
		private static JTextField mytextField = new JTextField(); // shows messages
		private static JTextField tallybox = new JTextField(); //shows questions
		private static JTextField inputbox = new JTextField(); //text to show answer input
		private static JTextField timerbox = new JTextField(); //shows the timer
		private static JTextField showtext = new JTextField(); 
		private JButton CancelButton= new JButton("Cancel");
		private JButton StartButton = new JButton("Start");
		private static Random random = new Random();
		private static int trackcorrectanswers=0;
		private static long startTime=System.currentTimeMillis();
		private volatile static boolean bool =true;
		String rightanswer;
		String question;
		static long now = System.currentTimeMillis();
		
		public static String[] SHORT_NAMES = 

			{ 
				"A","R", "N", "D", "C", "Q", "E",
				"G",  "H", "I", "L", "K", "M", "F",
				"P", "S", "T", "W", "Y", "V" 	
			};
		public static String[] FULL_NAMES = 
			{

				"alanine","arginine", "asparagine",
				"aspartic acid", "cysteine",
				"glutamine",  "glutamic acid",
				"glycine" ,"histidine","isoleucine",
				"leucine",  "lysine", "methionine",
				"phenylalanine", "proline",
				"serine","threonine","tryptophan",
				"tyrosine", "valine"

			};


	
		private class ButtonActionListener implements ActionListener //an inner class for Action listener
		{
			
			public void actionPerformed (ActionEvent event)
				{
				
					String inputForBox = inputbox.getText().toUpperCase();
					//startquiz();
					
					if(inputForBox.equals(rightanswer))
					{
						trackcorrectanswers++;
						updateTextField("Correctanswer: " + rightanswer);
						tallybox("Number right: " + trackcorrectanswers);
						
					}
					else
					{
						updateTextField("Wrong! The answer was: " + rightanswer);
						tallybox("Number right: " + trackcorrectanswers);
					}
					if ((now-startTime)/1000f >= 30)
					{
						System.exit(0);
						showTimer("The quiz has ended."); 
					}
					inputbox.setText("");
					updateTextField("");
				}
			
			
		}
		
		private class BeginQuiz implements ActionListener 
		{
			public void actionPerformed(ActionEvent e)
			{
				//(new Thread(new Timekeep())).start();
				StartButton.setEnabled(false);
				inputbox.setEditable(true);
				CancelButton.setEnabled(true);
				bool=true;
				trackcorrectanswers=0;
				startquiz();
				
			}
		}
/*		private static class Timekeep implements Runnable
		{
			@Override
			public void begin()
			{
				
			}
		}
*/
		private static void updateTextField(String content) //helper method to update display
	{
		mytextField.setText(content);
	}

	public void showTimer (String content) //method to show timer 
	{
		timerbox.setText(content);
	}
	private static void inputbox(String content) //method to show display user input
	{
		inputbox.setText(content);
	}
	public void tallybox(String content) //method to show the correct answer and number of right answers
	{
		tallybox.setText(content);
	}

	public void showquestion () //method to make the question
	{
		
		while(true)
		{			
			int randomnumber = random.nextInt(20);
			String question = FULL_NAMES[randomnumber];
			String rightanswer= SHORT_NAMES[randomnumber];
			System.out.println(question);
			//String inputStringUpper = input.nextLine().toUpperCase();
			mytextField.setText("What is the letter code for the following amino acid?" + question);
		}
		
	}
	public void startquiz()
	{
		showquestion(); 
		inputbox.setEditable(true);
		
	}
	
	public AminoAcidQuizGUI(String title)
	{
		super("Amino Acid Quiz");
		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch(Exception ex)
		{
			System.out.println("Could not set Windows Look And Feel");
		}
		setLocationRelativeTo(null); 
		setSize(200, 200);
		//JFrame frame = new JFrame("Amino Acid Quiz");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getBottomPanel(), BorderLayout.SOUTH);
		getContentPane().add(inputbox, BorderLayout.NORTH);
		getContentPane().add(tallybox, BorderLayout.WEST);
		getContentPane().add(timerbox, BorderLayout.EAST);
		getContentPane().add(mytextField, BorderLayout.CENTER);
		StartButton.addActionListener(new BeginQuiz());
		mytextField.setText("Ready to Begin?");
		setVisible(true);
		
	
	}
	private JPanel getBottomPanel() 
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.add(StartButton);
		panel.add(CancelButton);
		StartButton.addActionListener(new BeginQuiz());
		CancelButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CancelButton.setEnabled(false);
				StartButton.setEnabled(true);
				inputbox.setEditable(false);
				System.exit(0);
			}
		});

		return panel;
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater
		(new Runnable()
				{
					@Override
					public void run()
					{
						new AminoAcidQuizGUI("First quiz");
					}
				}
		);
	
	}
}
