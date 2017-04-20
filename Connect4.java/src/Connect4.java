import javax.swing.JFrame; // For Jframe
import java.awt.Color; // For choosing background color for the Jframe
import java.awt.Container; // For the body of Jframe 
import java.awt.Image;  // For adding images to Jframe
import javax.swing.ImageIcon; // For adding images to Jframe
import java.awt.Graphics;  // For drawing images..etc.. the Jframe
import java.awt.event.ActionEvent;  //  For doing actions on JStuff, like pressing a button
import java.awt.event.ActionListener;  //  For doing actions on JStuff, like pressing a button
import java.awt.event.MouseEvent;  // For mouse events
import java.awt.event.MouseListener;  // For monitoring mouse actions
import javax.swing.JButton;  // For adding buttons
import javax.swing.JLabel;  // For adding labels
import javax.swing.JOptionPane;  // For adding question..information.. panels..

/* Before GUI
import java.util.Scanner; // For Input Object (SC)
import java.util.InputMismatchException; // For Checking User's Input
import java.util.Arrays;  // For Clear Screen Method
*/

@SuppressWarnings("serial") // Not Important, it is for preventing false warning..
public class Connect4 extends JFrame implements MouseListener {

	//Attributes
	private int x,y; // x variable for User, y variable for PC
	private boolean WhoPlayFirst,PCMod,UserPlayed,PCPlayed;  // We will need this boolean variables during the game
	private char[] a,b,c,d,e,f;  // Variables for columns and rows  // for drawing Method
    private int i,l,m,n;  // Variables for CheckUserWinner() and CheckPCWinner() and PCHardPlay() ( Artificial Intelligence )
    private int j;  // This variable is for counting played stones // to finish the game with DRAW if no one wins (42 stone)
	
	// GUI Variables
	private Container main=getContentPane();  // JFrame Body
	private JButton closeB;  // Close Button
	private Image boardImg,yellowImg,redImg,rgreenImg,ygreenImg;  // Importing images
	private JLabel userL,pcL,gamestatL; // Basic Labels
	private JLabel l1,l2,l3,l4,l5,l6,l7; // JLabel Columns (Mouse Click Sensors)
	private int qJ; // A Variable for answering some questions in GUI
	
    /* Before GUI
	private char q;  // A Variable for answering some questions with (Y or N) / (E or H) // Before GUI
	private Scanner sc=new Scanner(System.in); // Input Object // Before GUI
	*/
	
	
	//Constructors
	Connect4() throws InterruptedException{
		super("Connect4"); // The title of the frame
		
		// Basic variables
		a=new char[8];
		b=new char[8];
		c=new char[8];
		d=new char[8];
		e=new char[8];
		f=new char[8];
		//
		
		//User JFrame Settings
		getContentPane().setBackground(Color.pink);
		//
		
		//Default Settings For JFrame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit on Clicking X on The upper right corner
		setSize(800,600); //Default Resolution
		setVisible(true); // Visible Frame
		main.setLayout(null); // Default Layout
		//
		
		//Mouse Settings
    	addMouseListener(this);
	    //
    	
    	//Establishing Close Button
    	closeB=new JButton ("Close");
    	closeB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);	
			}
    	});
    	closeB.setBounds(50, 500, 80, 25);
    	main.add(closeB);
    	//
    	
    	boardImg = new ImageIcon("board.jpg").getImage(); // Establishing Game Board
    	yellowImg = new ImageIcon("yellow.jpg").getImage(); // Establishing Yellow Coin
    	redImg = new ImageIcon("red.jpg").getImage(); // Establishing Red Coin
    	ygreenImg = new ImageIcon("ygreen.jpg").getImage(); //Establishing YellowGreen Image (For User Winning)
    	rgreenImg = new ImageIcon("rgreen.jpg").getImage(); //Establishing RedGreen Image (For PC Winning)
        
        // Establishing User Label (Text)
    	userL = new JLabel("User");
    	userL.setBounds(61, 210, 50, 20);
        main.add(userL);
    	//
        
        // Establishing PC Label (Text)
    	pcL = new JLabel("PC");
    	pcL.setBounds(65, 345, 50, 20);
        main.add(pcL);
    	//
        
        // Establishing Game Status Label (Text)
        gamestatL = new JLabel();
        gamestatL.setBounds(330, 5, 300, 20);
        main.add(gamestatL);
    	//
        
        // Establishing JLabel Columns (Mouse Click Sensors)
    	l1=new JLabel();
    	l1.setBounds(160,70,70,425);
        l1.addMouseListener(this);
        main.add(l1);
        
        l2=new JLabel();
    	l2.setBounds(228,70,70,425);
        l2.addMouseListener(this);
        main.add(l2);
        
        l3=new JLabel();
    	l3.setBounds(296,70,70,425);
        l3.addMouseListener(this);
        main.add(l3);
        
        l4=new JLabel();
    	l4.setBounds(364,70,70,425);
        l4.addMouseListener(this);
        main.add(l4);
        
        l5=new JLabel();
    	l5.setBounds(432,70,70,425);
        l5.addMouseListener(this);
        main.add(l5);
        
        l6=new JLabel();
    	l6.setBounds(500,70,70,425);
        l6.addMouseListener(this);
        main.add(l6);
        
        l7=new JLabel();
    	l7.setBounds(568,70,70,425);
        l7.addMouseListener(this);
        main.add(l7);
    	//
        
		LoadingGame(); // Start the Game
	}
	
	//Methods
	private void LoadingGame() throws InterruptedException{  // Initialization of all Variables
        // Initialization of all Variables
		for(i=0;i!=8;i++){a[i]=' ';b[i]=' ';c[i]=' ';d[i]=' ';e[i]=' ';f[i]=' ';}
		x=0;y=0;
		i=0;l=0;m=0;n=0;
		j=0;
		WhoPlayFirst=true;
		PCMod=true;
		UserPlayed=false;
		PCPlayed=false;
		
		gamestatL.setText("Game Status: Started"); // GUI
		qJ=10; // GUI
		
		//q=' '; // Before GUI
		
		ChooseWhoPlayFirst();
	}
	
	private void ChooseWhoPlayFirst() throws InterruptedException{  // This Method Will Choose Who Will Start Playing First (User or PC)
		ClearScr();
		Object[] options = {
			"Yes",
			"No",
			"Exit!"
		};
		
		qJ=JOptionPane.showOptionDialog(
			this,
			"Hello User, Hope you enjoy this game!\nDo you want to play first?",
			"Question?",
			JOptionPane.YES_NO_CANCEL_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,
			options,
			options[2]
		);
		
		if(qJ==0){WhoPlayFirst=true;ChoosePCMod();}
		else if(qJ==1){WhoPlayFirst=false;ChoosePCMod();}
		else if(qJ==2){System.exit(0);}
		else if(qJ==-1){System.exit(0);}
		
		/* Before GUI
		ClearScr();
		q=' ';
		System.out.print("Hello User, Hope You Enjoy This Game.....!!\n\n");
		System.out.print("\n\n\n\n\n\n                       Do You Want To Play First....??\n\n");
		System.out.print("                          YES : Y            NO : N\n\n\n");
		System.out.print("                          Enter Your Choice : ");
		q=sc.next().charAt(0);
		if ((q=='y') || (q=='Y')){WhoPlayFirst=true;ChoosePCMod();}
		else if ((q=='n') || (q=='N')){WhoPlayFirst=false;ChoosePCMod();}
		else ChooseWhoPlayFirst();
		*/
	}
	
	private void ChoosePCMod() throws InterruptedException{  // This Method Will Choose The PC Mod (Easy or Hard)
		Object[] options = {
			"Easy",
			"Hard"
		};
		
		qJ=JOptionPane.showOptionDialog(
			this,
			"Please choose your opponent mod?",
			"Question?",
			JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null, // do not use a custom Icon
			options, // the titles of buttons
			options[0] // default button title
		 ); 
		
		if(qJ==0){PCMod=true;if(WhoPlayFirst==true)UserPlay();else PCEasyPlay();}
		else if(qJ==1){PCMod=false;if(WhoPlayFirst==true)UserPlay();else PCHardPlay();}
		else if(qJ==-1){System.exit(0);}
		
		/* Before GUI
		ClearScr();
		q=' ';
		System.out.print("Hello User, Hope You Enjoy This Game.....!!\n\n");
		System.out.print("\n\n\n\n\n\n                       Choose Your Opponent Mod.......\n\n");
		System.out.print("                          Easy : E            Hard : H\n\n\n");
		System.out.print("                          Enter Your Choice : ");
		q=sc.next().charAt(0);
		if ((q=='e') || (q=='E')){q=' ';PCMod=true;if(WhoPlayFirst==true)UserPlay();else PCEasyPlay();}
		else if ((q=='h') || (q=='H')){q=' ';PCMod=false;if(WhoPlayFirst==true)UserPlay();else PCHardPlay();}
		else ChoosePCMod();
		*/
	}
	
	private void UserPlay() throws InterruptedException{  // If User's Turn
		Drawing();
		x=0;
		// wait(); Function is writing errors..but game still working
		wait();  // Wait until user clicks on the game // This code generated ( throws InterruptedException ) all over the methods
		//
		CheckUserPlay();
		
		/* Before GUI
		System.out.print("Enter Your Play ( 1 -> 7 or 99 for quit ) : ");
		
		while(true){ // The game will stop working if user entered characters!
		    try {
		        x = sc.nextInt(); // User Input
		        break;
		    }
		    catch(InputMismatchException e){
		        System.out.print("Error: you did not enter a number!\nGame Stopped Working!\n");
		        System.exit(0); 
		    }
		}
		
		if (x==99){ClearScr();System.exit(0);} // 99 to exit
	    else if((x==1) || (x==2) || (x==3) || (x==4) || (x==5) || (x==6) || (x==7))CheckUserPlay();
		else UserPlay();  // if user didn't enter a value 1->7 it will ask to enter the Value again
		*/
	}	
	
	private void CheckUserPlay() throws InterruptedException{  // Method to Check what user Played
		UserPlayed=false;
		for(i=1;i!=8;i++){if(x==i && f[i]=='X'){UserPlay();}}
		for(i=1;i!=8;i++){if(x==i && f[i]=='O'){UserPlay();}}
		for(i=1;i!=8;i++){if(x==i && a[i]!='X' && a[i]!='O' && UserPlayed==false){a[i]='X';UserPlayed=true;CheckUserWinner();}}
		for(i=1;i!=8;i++){if(x==i && b[i]!='X' && b[i]!='O' && UserPlayed==false){b[i]='X';UserPlayed=true;CheckUserWinner();}}
		for(i=1;i!=8;i++){if(x==i && c[i]!='X' && c[i]!='O' && UserPlayed==false){c[i]='X';UserPlayed=true;CheckUserWinner();}}
		for(i=1;i!=8;i++){if(x==i && d[i]!='X' && d[i]!='O' && UserPlayed==false){d[i]='X';UserPlayed=true;CheckUserWinner();}}
		for(i=1;i!=8;i++){if(x==i && e[i]!='X' && e[i]!='O' && UserPlayed==false){e[i]='X';UserPlayed=true;CheckUserWinner();}}
		for(i=1;i!=8;i++){if(x==i && f[i]!='X' && f[i]!='O' && UserPlayed==false){f[i]='X';UserPlayed=true;CheckUserWinner();}}
		UserPlay();
	}
	
	private void CheckUserWinner() throws InterruptedException{  // Method to check if user won
		for(i=1;i!=8;i++){if('X'==a[i] && 'X'==b[i] && 'X'==c[i] && 'X'==d[i]){a[i]='W';b[i]='W';c[i]='W';d[i]='W';UserWins();}}
		for(i=1;i!=8;i++){if('X'==b[i] && 'X'==c[i] && 'X'==d[i] && 'X'==e[i]){b[i]='W';c[i]='W';d[i]='W';e[i]='W';UserWins();}}
		for(i=1;i!=8;i++){if('X'==c[i] && 'X'==d[i] && 'X'==e[i] && 'X'==f[i]){c[i]='W';d[i]='W';e[i]='W';f[i]='W';UserWins();}}
		
		i=1;l=2;m=3;n=4;
		while(i!=5 && l!=6 && m!=7 && n!=8){
			if('X'==a[i] && 'X'==b[l] && 'X'==c[m] && 'X'==d[n]){a[i]='W';b[l]='W';c[m]='W';d[n]='W';UserWins();}
			i++;l++;m++;n++;
		}
		
		i=1;l=2;m=3;n=4;
		while(i!=5 && l!=6 && m!=7 && n!=8){
			if('X'==b[i] && 'X'==c[l] && 'X'==d[m] && 'X'==e[n]){b[i]='W';c[l]='W';d[m]='W';e[n]='W';UserWins();}
			i++;l++;m++;n++;
		}
		
		i=1;l=2;m=3;n=4;
		while(i!=5 && l!=6 && m!=7 && n!=8){
			if('X'==c[i] && 'X'==d[l] && 'X'==e[m] && 'X'==f[n]){c[i]='W';d[l]='W';e[m]='W';f[n]='W';UserWins();}
			i++;l++;m++;n++;
		}
		
		i=4;l=3;m=2;n=1;
		while(i!=8 && l!=7 && m!=6 && n!=5){
			if('X'==a[i] && 'X'==b[l] && 'X'==c[m] && 'X'==d[n]){a[i]='W';b[l]='W';c[m]='W';d[n]='W';UserWins();}
			i++;l++;m++;n++;
		}
		
		i=4;l=3;m=2;n=1;
		while(i!=8 && l!=7 && m!=6 && n!=5){
			if('X'==b[i] && 'X'==c[l] && 'X'==d[m] && 'X'==e[n]){b[i]='W';c[l]='W';d[m]='W';e[n]='W';UserWins();}
			i++;l++;m++;n++;
		}
		
		i=4;l=3;m=2;n=1;
		while(i!=8 && l!=7 && m!=6 && n!=5){
			if('X'==c[i] && 'X'==d[l] && 'X'==e[m] && 'X'==f[n]){c[i]='W';d[l]='W';e[m]='W';f[n]='W';UserWins();}
			i++;l++;m++;n++;
		}
		
		i=1;l=2;m=3;n=4;
		while(i!=5 && l!=6 && m!=7 && n!=8){
			if('X'==a[i] && 'X'==a[l] && 'X'==a[m] && 'X'==a[n]){a[i]='W';a[l]='W';a[m]='W';a[n]='W';UserWins();}
			i++;l++;m++;n++;
		}
		
		i=1;l=2;m=3;n=4;
		while(i!=5 && l!=6 && m!=7 && n!=8){
			if('X'==b[i] && 'X'==b[l] && 'X'==b[m] && 'X'==b[n]){b[i]='W';b[l]='W';b[m]='W';b[n]='W';UserWins();}
			i++;l++;m++;n++;
		}
		
		i=1;l=2;m=3;n=4;
		while(i!=5 && l!=6 && m!=7 && n!=8){
			if('X'==c[i] && 'X'==c[l] && 'X'==c[m] && 'X'==c[n]){c[i]='W';c[l]='W';c[m]='W';c[n]='W';UserWins();}
			i++;l++;m++;n++;
		}
		
		i=1;l=2;m=3;n=4;
		while(i!=5 && l!=6 && m!=7 && n!=8){
			if('X'==d[i] && 'X'==d[l] && 'X'==d[m] && 'X'==d[n]){d[i]='W';d[l]='W';d[m]='W';d[n]='W';UserWins();}
			i++;l++;m++;n++;
		}
		
		i=1;l=2;m=3;n=4;
		while(i!=5 && l!=6 && m!=7 && n!=8){
			if('X'==e[i] && 'X'==e[l] && 'X'==e[m] && 'X'==e[n]){e[i]='W';e[l]='W';e[m]='W';e[n]='W';UserWins();}
			i++;l++;m++;n++;
		}
		
		i=1;l=2;m=3;n=4;
		while(i!=5 && l!=6 && m!=7 && n!=8){
			if('X'==f[i] && 'X'==f[l] && 'X'==f[m] && 'X'==f[n]){f[i]='W';f[l]='W';f[m]='W';f[n]='W';UserWins();}
			i++;l++;m++;n++;
		}
		
		StonesCounter();  // To count the played stones // if user didn't win yet so it will count the stones
		PCPlay();  // it is PC's turn
	}
	
	private void UserWins() throws InterruptedException{  // If user won? this Method will print the end
		gamestatL.setText("Game Status: Ended");
		ClearScr();
		Object[] options = {
			"Yes",
			"No"
		};
		
		qJ=JOptionPane.showOptionDialog(
			this,
			"Congradulations!, You won the game..\nPlay Again?",
			"You won!",
			JOptionPane.YES_NO_OPTION,
			JOptionPane.INFORMATION_MESSAGE,
			null, // do not use a custom Icon
			options, // the titles of buttons
			options[0] // default button title
		 ); 
		
		if(qJ==0){LoadingGame();}
		else if(qJ==1){System.exit(0);}
		else if(qJ==-1){System.exit(0);}
		
		/* Before GUI
		ClearScr();
		q=' ';
		Drawing();
		System.out.print("                  ---- Congradulations!, You Won The Game ----  \n");
		System.out.print("                                 Play Again....? \n");
		System.out.print("                            YES : Y            NO : N\n");
		System.out.print("                            Enter Your Choice : ");
		q=sc.next().charAt(0);
		if ((q=='y') || (q=='Y'))LoadingGame();
		else if ((q=='n') || (q=='N')){
			ClearScr();
			System.exit(0);
		}
		else UserWins();
		*/
	}

	private void PCPlay() throws InterruptedException{ // If it is PC's Turn
		// This Method will check if we choosed a hard pc or easy pc in the beginning 
		// then it will call the right Method we choosed to be, easy or hard pc
		if(PCMod==true)PCEasyPlay();
		else PCHardPlay();
	}
	
	private void PCEasyPlay() throws InterruptedException{  // Easy Playing PC (Dumb/Random)
		y=0;
		y=1+(int)(Math.random()*7); // to generate a number from 1->7
		CheckPCPlay();
	}

	private void PCHardPlay() throws InterruptedException{  // Hard Playing PC ( Artificial Intelligence )
		y=0;
		
		for(i=1;i!=8;i++){if(b[i]=='X' && c[i]!='X' && c[i]!='O'){y=i;CheckPCPlay();}}
		for(i=1;i!=8;i++){if(b[i]=='O' && c[i]!='X' && c[i]!='O'){y=i;CheckPCPlay();}}
		
		for(i=1;i!=8;i++){if(a[i]=='X' && b[i]=='X' && c[i]!='X' && c[i]!='O'){y=i;CheckPCPlay();}}
		for(i=1;i!=8;i++){if(b[i]=='X' && c[i]=='X' && d[i]!='X' && d[i]!='O'){y=i;CheckPCPlay();}}
		for(i=1;i!=8;i++){if(c[i]=='X' && d[i]=='X' && e[i]!='X' && e[i]!='O'){y=i;CheckPCPlay();}}
		
		i=1;l=2;m=3;n=4;
		while(i!=5 && l!=6 && m!=7 && n!=8){
			if(a[i]=='X' && a[l]=='X' && a[m]=='X' && a[n]!='X' && a[n]!='O'){y=n;CheckPCPlay();}
			i++;l++;m++;n++;
		}
		
		i=2;l=3;m=4;n=1;
		while(i!=6 && l!=7 && m!=8 && n!=5){
			if(a[i]=='X' && a[l]=='X' && a[m]=='X' && a[n]!='X' && a[n]!='O'){y=n;CheckPCPlay();}
			i++;l++;m++;n++;
		}

		i=1;l=2;m=3;n=4;
		while(i!=5 && l!=6 && m!=7 && n!=8){
			if(b[i]=='X' && b[l]=='X' && b[m]=='X' && b[n]!='X' && b[n]!='O'){y=n;CheckPCPlay();}
			i++;l++;m++;n++;
		}
		
		i=2;l=3;m=4;n=1;
		while(i!=6 && l!=7 && m!=8 && n!=5){
			if(b[i]=='X' && b[l]=='X' && b[m]=='X' && b[n]!='X' && b[n]!='O'){y=n;CheckPCPlay();}
			i++;l++;m++;n++;
		}

		i=1;l=2;m=3;n=4;
		while(i!=5 && l!=6 && m!=7 && n!=8){
			if(c[i]=='X' && c[l]=='X' && c[m]=='X' && c[n]!='X' && c[n]!='O'){y=n;CheckPCPlay();}
			i++;l++;m++;n++;
		}
		
		i=2;l=3;m=4;n=1;
		while(i!=6 && l!=7 && m!=8 && n!=5){
			if(c[i]=='X' && c[l]=='X' && c[m]=='X' && c[n]!='X' && c[n]!='O'){y=n;CheckPCPlay();}
			i++;l++;m++;n++;
		}

		i=1;l=2;m=3;n=4;
		while(i!=5 && l!=6 && m!=7 && n!=8){
			if(d[i]=='X' && d[l]=='X' && d[m]=='X' && d[n]!='X' && d[n]!='O'){y=n;CheckPCPlay();}
			i++;l++;m++;n++;
		}
		
		i=2;l=3;m=4;n=1;
		while(i!=6 && l!=7 && m!=8 && n!=5){
			if(d[i]=='X' && d[l]=='X' && d[m]=='X' && d[n]!='X' && d[n]!='O'){y=n;CheckPCPlay();}
			i++;l++;m++;n++;
		}
		
		i=1;l=2;m=3;n=4;
		while(i!=5 && l!=6 && m!=7 && n!=8){
			if(e[i]=='X' && e[l]=='X' && e[m]=='X' && e[n]!='X' && e[n]!='O'){y=n;CheckPCPlay();}
			i++;l++;m++;n++;
		}
		
		i=2;l=3;m=4;n=1;
		while(i!=6 && l!=7 && m!=8 && n!=5){
			if(e[i]=='X' && e[l]=='X' && e[m]=='X' && e[n]!='X' && e[n]!='O'){y=n;CheckPCPlay();}
			i++;l++;m++;n++;
		}
		
		i=1;l=2;m=3;n=4;
		while(i!=5 && l!=6 && m!=7 && n!=8){
			if(f[i]=='X' && f[l]=='X' && f[m]=='X' && f[n]!='X' && f[n]!='O'){y=n;CheckPCPlay();}
			i++;l++;m++;n++;
		}
		
		i=2;l=3;m=4;n=1;
		while(i!=6 && l!=7 && m!=8 && n!=5){
			if(f[i]=='X' && f[l]=='X' && f[m]=='X' && f[n]!='X' && f[n]!='O'){y=n;CheckPCPlay();}
			i++;l++;m++;n++;
		}
		
		i=2;l=3;m=1;
		while(i!=7 && l!=8 && m!=6){
			if(a[i]=='X' && a[l]=='X' && a[m]!='X' && a[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(a[i]=='X' && a[l]=='X' && a[m]!='X' && a[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=2;l=3;m=1;
		while(i!=7 && l!=8 && m!=6){
			if(b[i]=='X' && b[l]=='X' && b[m]!='X' && b[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(b[i]=='X' && b[l]=='X' && b[m]!='X' && b[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=2;l=3;m=1;
		while(i!=7 && l!=8 && m!=6){
			if(c[i]=='X' && c[l]=='X' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(c[i]=='X' && c[l]=='X' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=2;l=3;m=1;
		while(i!=7 && l!=8 && m!=6){
			if(d[i]=='X' && d[l]=='X' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(d[i]=='X' && d[l]=='X' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=2;l=3;m=1;
		while(i!=7 && l!=8 && m!=6){
			if(e[i]=='X' && e[l]=='X' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(e[i]=='X' && e[l]=='X' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=2;l=3;m=1;
		while(i!=7 && l!=8 && m!=6){
			if(f[i]=='X' && f[l]=='X' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(f[i]=='X' && f[l]=='X' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(a[i]=='X' && b[l]=='X' && b[m]=='X' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(a[i]=='X' && b[l]=='X' && b[m]=='X' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(a[i]=='X' && b[l]=='X' && b[m]=='X' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(a[i]=='X' && b[l]=='X' && b[m]=='X' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(b[i]=='X' && c[l]=='X' && c[m]=='X' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(b[i]=='X' && c[l]=='X' && c[m]=='X' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(b[i]=='X' && c[l]=='X' && c[m]=='X' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(b[i]=='X' && c[l]=='X' && c[m]=='X' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(c[i]=='X' && d[l]=='X' && d[m]=='X' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(c[i]=='X' && d[l]=='X' && d[m]=='X' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(c[i]=='X' && d[l]=='X' && d[m]=='X' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(c[i]=='X' && d[l]=='X' && d[m]=='X' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(d[i]=='X' && e[l]=='X' && e[m]=='X' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(d[i]=='X' && e[l]=='X' && e[m]=='X' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(d[i]=='X' && e[l]=='X' && e[m]=='X' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(d[i]=='X' && e[l]=='X' && e[m]=='X' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(a[i]=='X' && b[l]=='X' && b[m]=='X' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(a[i]=='X' && b[l]=='X' && b[m]=='X' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(a[i]=='X' && b[l]=='X' && b[m]=='X' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(a[i]=='X' && b[l]=='X' && b[m]=='X' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(b[i]=='X' && c[l]=='X' && c[m]=='X' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(b[i]=='X' && c[l]=='X' && c[m]=='X' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(b[i]=='X' && c[l]=='X' && c[m]=='X' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(b[i]=='X' && c[l]=='X' && c[m]=='X' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(c[i]=='X' && d[l]=='X' && d[m]=='X' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(c[i]=='X' && d[l]=='X' && d[m]=='X' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(c[i]=='X' && d[l]=='X' && d[m]=='X' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(c[i]=='X' && d[l]=='X' && d[m]=='X' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(d[i]=='X' && e[l]=='X' && e[m]=='X' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(d[i]=='X' && e[l]=='X' && e[m]=='X' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(d[i]=='X' && e[l]=='X' && e[m]=='X' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(d[i]=='X' && e[l]=='X' && e[m]=='X' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(a[i]=='X' && b[l]=='X' && b[m]=='X' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(a[i]=='X' && b[l]=='X' && b[m]=='X' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(a[i]=='X' && b[l]=='X' && b[m]=='X' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(a[i]=='X' && b[l]=='X' && b[m]=='X' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(b[i]=='X' && c[l]=='X' && c[m]=='X' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(b[i]=='X' && c[l]=='X' && c[m]=='X' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(b[i]=='X' && c[l]=='X' && c[m]=='X' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(b[i]=='X' && c[l]=='X' && c[m]=='X' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(c[i]=='X' && d[l]=='X' && d[m]=='X' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(c[i]=='X' && d[l]=='X' && d[m]=='X' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(c[i]=='X' && d[l]=='X' && d[m]=='X' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(c[i]=='X' && d[l]=='X' && d[m]=='X' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(d[i]=='X' && e[l]=='X' && e[m]=='X' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(d[i]=='X' && e[l]=='X' && e[m]=='X' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(d[i]=='X' && e[l]=='X' && e[m]=='X' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(d[i]=='X' && e[l]=='X' && e[m]=='X' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(d[i]=='X' && e[l]=='X' && e[m]=='X' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(a[i]=='X' && b[l]=='X' && b[m]=='X' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(a[i]=='X' && b[l]=='X' && b[m]=='X' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(a[i]=='X' && b[l]=='X' && b[m]=='X' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(a[i]=='X' && b[l]=='X' && b[m]=='X' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(b[i]=='X' && c[l]=='X' && c[m]=='X' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(b[i]=='X' && c[l]=='X' && c[m]=='X' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(b[i]=='X' && c[l]=='X' && c[m]=='X' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(b[i]=='X' && c[l]=='X' && c[m]=='X' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(c[i]=='X' && d[l]=='X' && d[m]=='X' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(c[i]=='X' && d[l]=='X' && d[m]=='X' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(c[i]=='X' && d[l]=='X' && d[m]=='X' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(c[i]=='X' && d[l]=='X' && d[m]=='X' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(d[i]=='X' && e[l]=='X' && e[m]=='X' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(d[i]=='X' && e[l]=='X' && e[m]=='X' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(d[i]=='X' && e[l]=='X' && e[m]=='X' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(d[i]=='X' && e[l]=='X' && e[m]=='X' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(a[i]=='X' && b[l]=='X' && b[m]=='O' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(a[i]=='X' && b[l]=='X' && b[m]=='O' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(a[i]=='X' && b[l]=='X' && b[m]=='O' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(a[i]=='X' && b[l]=='X' && b[m]=='O' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(b[i]=='X' && c[l]=='X' && c[m]=='O' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(b[i]=='X' && c[l]=='X' && c[m]=='O' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(b[i]=='X' && c[l]=='X' && c[m]=='O' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(b[i]=='X' && c[l]=='X' && c[m]=='O' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(c[i]=='X' && d[l]=='X' && d[m]=='O' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(c[i]=='X' && d[l]=='X' && d[m]=='O' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(c[i]=='X' && d[l]=='X' && d[m]=='O' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(c[i]=='X' && d[l]=='X' && d[m]=='O' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(d[i]=='X' && e[l]=='X' && e[m]=='O' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(d[i]=='X' && e[l]=='X' && e[m]=='O' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(d[i]=='X' && e[l]=='X' && e[m]=='O' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=1;l=2;m=3;
		while(i!=6 && l!=7 && m!=8){
			if(d[i]=='X' && e[l]=='X' && e[m]=='O' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(a[i]=='X' && b[l]=='X' && b[m]=='O' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(a[i]=='X' && b[l]=='X' && b[m]=='O' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(a[i]=='X' && b[l]=='X' && b[m]=='O' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(a[i]=='X' && b[l]=='X' && b[m]=='O' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(b[i]=='X' && c[l]=='X' && c[m]=='O' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(b[i]=='X' && c[l]=='X' && c[m]=='O' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(b[i]=='X' && c[l]=='X' && c[m]=='O' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(b[i]=='X' && c[l]=='X' && c[m]=='O' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(c[i]=='X' && d[l]=='X' && d[m]=='O' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(c[i]=='X' && d[l]=='X' && d[m]=='O' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(c[i]=='X' && d[l]=='X' && d[m]=='O' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(c[i]=='X' && d[l]=='X' && d[m]=='O' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(d[i]=='X' && e[l]=='X' && e[m]=='O' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(d[i]=='X' && e[l]=='X' && e[m]=='O' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(d[i]=='X' && e[l]=='X' && e[m]=='O' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=3;l=1;m=2;
		while(i!=8 && l!=6 && m!=7){
			if(d[i]=='X' && e[l]=='X' && e[m]=='O' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i++;l++;m++;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(a[i]=='X' && b[l]=='X' && b[m]=='O' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(a[i]=='X' && b[l]=='X' && b[m]=='O' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(a[i]=='X' && b[l]=='X' && b[m]=='O' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(a[i]=='X' && b[l]=='X' && b[m]=='O' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(b[i]=='X' && c[l]=='X' && c[m]=='O' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(b[i]=='X' && c[l]=='X' && c[m]=='O' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(b[i]=='X' && c[l]=='X' && c[m]=='O' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(b[i]=='X' && c[l]=='X' && c[m]=='O' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(c[i]=='X' && d[l]=='X' && d[m]=='O' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(c[i]=='X' && d[l]=='X' && d[m]=='O' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(c[i]=='X' && d[l]=='X' && d[m]=='O' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(c[i]=='X' && d[l]=='X' && d[m]=='O' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(d[i]=='X' && e[l]=='X' && e[m]=='O' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(d[i]=='X' && e[l]=='X' && e[m]=='O' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(d[i]=='X' && e[l]=='X' && e[m]=='O' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(d[i]=='X' && e[l]=='X' && e[m]=='O' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(a[i]=='X' && b[l]=='X' && b[m]=='O' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(a[i]=='X' && b[l]=='X' && b[m]=='O' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(a[i]=='X' && b[l]=='X' && b[m]=='O' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(a[i]=='X' && b[l]=='X' && b[m]=='O' && c[m]!='X' && c[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(b[i]=='X' && c[l]=='X' && c[m]=='O' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(b[i]=='X' && c[l]=='X' && c[m]=='O' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(b[i]=='X' && c[l]=='X' && c[m]=='O' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(b[i]=='X' && c[l]=='X' && c[m]=='O' && d[m]!='X' && d[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(c[i]=='X' && d[l]=='X' && d[m]=='O' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(c[i]=='X' && d[l]=='X' && d[m]=='O' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(c[i]=='X' && d[l]=='X' && d[m]=='O' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(c[i]=='X' && d[l]=='X' && d[m]=='O' && e[m]!='X' && e[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(d[i]=='X' && e[l]=='X' && e[m]=='O' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(d[i]=='X' && e[l]=='X' && e[m]=='O' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(d[i]=='X' && e[l]=='X' && e[m]=='O' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		i=7;l=6;m=5;
		while(i!=2 && l!=1 && m!=0){
			if(d[i]=='X' && e[l]=='X' && e[m]=='O' && f[m]!='X' && f[m]!='O'){y=m;CheckPCPlay();}
			i--;l--;m--;
		}
		
		PCEasyPlay(); // If non of above conditions worked!, then PC will Play dumb/random
	}
	
	private void CheckPCPlay() throws InterruptedException{  // Method to Check what PC Played
		PCPlayed=false;
		for(i=1;i!=8;i++){if(y==i && f[i]=='X'){PCPlay();}}
		for(i=1;i!=8;i++){if(y==i && f[i]=='O'){PCPlay();}}
		for(i=1;i!=8;i++){if(y==i && a[i]!='X' && a[i]!='O' && PCPlayed==false){a[i]='O';PCPlayed=true;CheckPCWinner();}}
		for(i=1;i!=8;i++){if(y==i && b[i]!='X' && b[i]!='O' && PCPlayed==false){b[i]='O';PCPlayed=true;CheckPCWinner();}}
		for(i=1;i!=8;i++){if(y==i && c[i]!='X' && c[i]!='O' && PCPlayed==false){c[i]='O';PCPlayed=true;CheckPCWinner();}}
		for(i=1;i!=8;i++){if(y==i && d[i]!='X' && d[i]!='O' && PCPlayed==false){d[i]='O';PCPlayed=true;CheckPCWinner();}}
		for(i=1;i!=8;i++){if(y==i && e[i]!='X' && e[i]!='O' && PCPlayed==false){e[i]='O';PCPlayed=true;CheckPCWinner();}}
		for(i=1;i!=8;i++){if(y==i && f[i]!='X' && f[i]!='O' && PCPlayed==false){f[i]='O';PCPlayed=true;CheckPCWinner();}}
		PCPlay();
	}	
	

	private void CheckPCWinner() throws InterruptedException{  // Method to check if pc won
		for(i=1;i!=8;i++){if('O'==a[i] && 'O'==b[i] && 'O'==c[i] && 'O'==d[i]){a[i]='Z';b[i]='Z';c[i]='Z';d[i]='Z';PCWins();}}
		for(i=1;i!=8;i++){if('O'==b[i] && 'O'==c[i] && 'O'==d[i] && 'O'==e[i]){b[i]='Z';c[i]='Z';d[i]='Z';e[i]='Z';PCWins();}}
		for(i=1;i!=8;i++){if('O'==c[i] && 'O'==d[i] && 'O'==e[i] && 'O'==f[i]){c[i]='Z';d[i]='Z';e[i]='Z';f[i]='Z';PCWins();}}
		
		i=1;l=2;m=3;n=4;
		while(i!=5 && l!=6 && m!=7 && n!=8){
			if('O'==a[i] && 'O'==b[l] && 'O'==c[m] && 'O'==d[n]){a[i]='Z';b[l]='Z';c[m]='Z';d[n]='Z';PCWins();}
			i++;l++;m++;n++;
		}
		
		i=1;l=2;m=3;n=4;
		while(i!=5 && l!=6 && m!=7 && n!=8){
			if('O'==b[i] && 'O'==c[l] && 'O'==d[m] && 'O'==e[n]){b[i]='Z';c[l]='Z';d[m]='Z';e[n]='Z';PCWins();}
			i++;l++;m++;n++;
		}
		
		i=1;l=2;m=3;n=4;
		while(i!=5 && l!=6 && m!=7 && n!=8){
			if('O'==c[i] && 'O'==d[l] && 'O'==e[m] && 'O'==f[n]){c[i]='Z';d[l]='Z';e[m]='Z';f[n]='Z';PCWins();}
			i++;l++;m++;n++;
		}
		
		i=4;l=3;m=2;n=1;
		while(i!=8 && l!=7 && m!=6 && n!=5){
			if('O'==a[i] && 'O'==b[l] && 'O'==c[m] && 'O'==d[n]){a[i]='Z';b[l]='Z';c[m]='Z';d[n]='Z';PCWins();}
			i++;l++;m++;n++;
		}
		
		i=4;l=3;m=2;n=1;
		while(i!=8 && l!=7 && m!=6 && n!=5){
			if('O'==b[i] && 'O'==c[l] && 'O'==d[m] && 'O'==e[n]){b[i]='Z';c[l]='Z';d[m]='Z';e[n]='Z';PCWins();}
			i++;l++;m++;n++;
		}
		
		i=4;l=3;m=2;n=1;
		while(i!=8 && l!=7 && m!=6 && n!=5){
			if('O'==c[i] && 'O'==d[l] && 'O'==e[m] && 'O'==f[n]){c[i]='Z';d[l]='Z';e[m]='Z';f[n]='Z';PCWins();}
			i++;l++;m++;n++;
		}
		
		i=1;l=2;m=3;n=4;
		while(i!=5 && l!=6 && m!=7 && n!=8){
			if('O'==a[i] && 'O'==a[l] && 'O'==a[m] && 'O'==a[n]){a[i]='Z';a[l]='Z';a[m]='Z';a[n]='Z';PCWins();}
			i++;l++;m++;n++;
		}
		
		i=1;l=2;m=3;n=4;
		while(i!=5 && l!=6 && m!=7 && n!=8){
			if('O'==b[i] && 'O'==b[l] && 'O'==b[m] && 'O'==b[n]){b[i]='Z';b[l]='Z';b[m]='Z';b[n]='Z';PCWins();}
			i++;l++;m++;n++;
		}
		
		i=1;l=2;m=3;n=4;
		while(i!=5 && l!=6 && m!=7 && n!=8){
			if('O'==c[i] && 'O'==c[l] && 'O'==c[m] && 'O'==c[n]){c[i]='Z';c[l]='Z';c[m]='Z';c[n]='Z';PCWins();}
			i++;l++;m++;n++;
		}
		
		i=1;l=2;m=3;n=4;
		while(i!=5 && l!=6 && m!=7 && n!=8){
			if('O'==d[i] && 'O'==d[l] && 'O'==d[m] && 'O'==d[n]){d[i]='Z';d[l]='Z';d[m]='Z';d[n]='Z';PCWins();}
			i++;l++;m++;n++;
		}
		
		i=1;l=2;m=3;n=4;
		while(i!=5 && l!=6 && m!=7 && n!=8){
			if('O'==e[i] && 'O'==e[l] && 'O'==e[m] && 'O'==e[n]){e[i]='Z';e[l]='Z';e[m]='Z';e[n]='Z';PCWins();}
			i++;l++;m++;n++;
		}
		
		i=1;l=2;m=3;n=4;
		while(i!=5 && l!=6 && m!=7 && n!=8){
			if('O'==f[i] && 'O'==f[l] && 'O'==f[m] && 'O'==f[n]){f[i]='Z';f[l]='Z';f[m]='Z';f[n]='Z';PCWins();}
			i++;l++;m++;n++;
		}
		
		StonesCounter();  // To count the played stones // if user didn't win yet so it will count the stones
		UserPlay();  // It is Uers's Turn
	}
	

	private void PCWins() throws InterruptedException{  // If PC won? this Method will print the end
		gamestatL.setText("Game Status: Ended");
		ClearScr();
		Object[] options = {
			"Yes",
			"No"
		};
		
		qJ=JOptionPane.showOptionDialog(
			this,
			"You lost the Game..!\nPlay Again?",
			"You lost!",
			JOptionPane.YES_NO_OPTION,
			JOptionPane.CANCEL_OPTION,
			null, // do not use a custom Icon
			options, // the titles of buttons
			options[0] // default button title
		 ); 
		
		if(qJ==0){LoadingGame();}
		else if(qJ==1){System.exit(0);}
		else if(qJ==-1){System.exit(0);}
		
		/* Before GUI
		ClearScr();
		q=' ';
		Drawing();
		System.out.print("                       ---- You Lost The Game ----  \n");
		System.out.print("                                 Play Again....? \n");
		System.out.print("                            YES : Y            NO : N\n");
		System.out.print("                            Enter Your Choice : ");
		q=sc.next().charAt(0);
		if ((q=='y') || (q=='Y'))LoadingGame();
		else if ((q=='n') || (q=='N')){
			ClearScr();
			System.exit(0);
		}
		else PCWins();
		*/
	}	
	
	private void Drawing(){  // Drawing Method // it will draw the columns
		ClearScr();
		/* Before GUI
		System.out.print("                   _____ _____ _____ _____ _____ _____ _____  \n");
		System.out.print("                  |     |     |     |     |     |     |     | \n");
		System.out.print("                  |  "+f[1]+"  |  "+f[2]+"  |  "+f[3]+"  |  "+f[4]+"  |  "+f[5]+"  |  "+f[6]+"  |  "+f[7]+"  | \n");
		System.out.print("                  |_____|_____|_____|_____|_____|_____|_____| \n");
		System.out.print("                  |     |     |     |     |     |     |     | \n");
		System.out.print("                  |  "+e[1]+"  |  "+e[2]+"  |  "+e[3]+"  |  "+e[4]+"  |  "+e[5]+"  |  "+e[6]+"  |  "+e[7]+"  | \n");
		System.out.print("                  |_____|_____|_____|_____|_____|_____|_____| \n");
		System.out.print("                  |     |     |     |     |     |     |     | \n");
		System.out.print("                  |  "+d[1]+"  |  "+d[2]+"  |  "+d[3]+"  |  "+d[4]+"  |  "+d[5]+"  |  "+d[6]+"  |  "+d[7]+"  | \n");
		System.out.print("                  |_____|_____|_____|_____|_____|_____|_____| \n");
		System.out.print("                  |     |     |     |     |     |     |     | \n");
		System.out.print("                  |  "+c[1]+"  |  "+c[2]+"  |  "+c[3]+"  |  "+c[4]+"  |  "+c[5]+"  |  "+c[6]+"  |  "+c[7]+"  | \n");
		System.out.print("                  |_____|_____|_____|_____|_____|_____|_____| \n");
		System.out.print("                  |     |     |     |     |     |     |     | \n");
		System.out.print("                  |  "+b[1]+"  |  "+b[2]+"  |  "+b[3]+"  |  "+b[4]+"  |  "+b[5]+"  |  "+b[6]+"  |  "+b[7]+"  | \n");
		System.out.print("                  |_____|_____|_____|_____|_____|_____|_____| \n");
		System.out.print("                  |     |     |     |     |     |     |     | \n");
		System.out.print("                  |  "+a[1]+"  |  "+a[2]+"  |  "+a[3]+"  |  "+a[4]+"  |  "+a[5]+"  |  "+a[6]+"  |  "+a[7]+"  | \n");
		System.out.print("                  |_____|_____|_____|_____|_____|_____|_____| \n");
		System.out.print("                     1     2     3     4     5     6     7    \n\n");
		*/
	} 
	
	private void StonesCounter() throws InterruptedException{  // This Method will end the game with DRAW (No Body Wins) if no body wins (Full Columns)
		j++;
		if(j>=42)NoOneWins(); // if the two players didn't win, and the columns are full then it will call (NoOneWins Method) and end the game
	}	
	
	private void NoOneWins() throws InterruptedException{  //  If no one Wins
		gamestatL.setText("Game Status: Ended");
		ClearScr();
		Object[] options = {
				"Yes",
        		"No"};
		qJ=JOptionPane.showOptionDialog(
			this,
			"D R A W\nPlay Again?",
			"DRAW!",
			JOptionPane.YES_NO_OPTION,
			JOptionPane.INFORMATION_MESSAGE,
			null, // do not use a custom Icon
			options, // the titles of buttons
			options[0] // default button title
		 ); 
		
		if(qJ==0){LoadingGame();}
		else if(qJ==1){System.exit(0);}
		else if(qJ==-1){System.exit(0);}
		
		/* Before GUI
		ClearScr();
		q=' ';
		Drawing();
		System.out.print("                               ---- D R A W ----  \n");
		System.out.print("                                 Play Again....? \n");
		System.out.print("                            YES : Y            NO : N\n");
		System.out.print("                            Enter Your Choice : ");
		q=sc.next().charAt(0);
		if ((q=='y') || (q=='Y'))LoadingGame();
		else if ((q=='n') || (q=='N')){
			ClearScr();
			System.exit(0);
		}
		else NoOneWins();
		*/
	}
	
	private void ClearScr(){ // Clear Screen Method
		repaint();
		
		/* Before GUI
		char cs = '\n';
		int length = 100;
		char[] chars = new char[length];
		Arrays.fill(chars, cs);
		System.out.print(String.valueOf(chars));
		*/
	}
	
    public void paint(Graphics g){ // JFrame Drawing Stuff
    	super.paint(g);
    	g.drawImage(boardImg,150,60,500,436,null); 
    	g.drawImage(redImg,50,165,70,70,null); 
    	g.drawImage(yellowImg,50,300,70,70,null); 
    	
    	
    	// The distance between each image x or y on the board is : 68px
    	if(a[1]=='X')g.drawImage(redImg,160,415,70,70,null);
    	else if(a[1]=='O')g.drawImage(yellowImg,160,415,70,70,null);
    	else if(a[1]=='Z')g.drawImage(ygreenImg,160,415,70,70,null);
    	else if(a[1]=='W')g.drawImage(rgreenImg,160,415,70,70,null);
    	
    	if(a[2]=='X')g.drawImage(redImg,228,415,70,70,null);
    	else if(a[2]=='O')g.drawImage(yellowImg,228,415,70,70,null);
    	else if(a[2]=='Z')g.drawImage(ygreenImg,228,415,70,70,null);
    	else if(a[2]=='W')g.drawImage(rgreenImg,228,415,70,70,null);
    	
    	if(a[3]=='X')g.drawImage(redImg,296,415,70,70,null);
    	else if(a[3]=='O')g.drawImage(yellowImg,296,415,70,70,null);
    	else if(a[3]=='Z')g.drawImage(ygreenImg,296,415,70,70,null);
    	else if(a[3]=='W')g.drawImage(rgreenImg,296,415,70,70,null);
    	
    	if(a[4]=='X')g.drawImage(redImg,364,415,70,70,null);
    	else if(a[4]=='O')g.drawImage(yellowImg,364,415,70,70,null);
    	else if(a[4]=='Z')g.drawImage(ygreenImg,364,415,70,70,null);
    	else if(a[4]=='W')g.drawImage(rgreenImg,364,415,70,70,null);
    	
    	if(a[5]=='X')g.drawImage(redImg,432,415,70,70,null);
    	else if(a[5]=='O')g.drawImage(yellowImg,432,415,70,70,null);
    	else if(a[5]=='Z')g.drawImage(ygreenImg,432,415,70,70,null);
    	else if(a[5]=='W')g.drawImage(rgreenImg,432,415,70,70,null);
    	
    	if(a[6]=='X')g.drawImage(redImg,500,415,70,70,null);
    	else if(a[6]=='O')g.drawImage(yellowImg,500,415,70,70,null);
    	else if(a[6]=='Z')g.drawImage(ygreenImg,500,415,70,70,null);
    	else if(a[6]=='W')g.drawImage(rgreenImg,500,415,70,70,null);
    	
    	if(a[7]=='X')g.drawImage(redImg,568,415,70,70,null);
    	else if(a[7]=='O')g.drawImage(yellowImg,568,415,70,70,null);
    	else if(a[7]=='Z')g.drawImage(ygreenImg,568,415,70,70,null);
    	else if(a[7]=='W')g.drawImage(rgreenImg,568,415,70,70,null);
    
    	if(b[1]=='X')g.drawImage(redImg,160,347,70,70,null);
    	else if(b[1]=='O')g.drawImage(yellowImg,160,347,70,70,null);
    	else if(b[1]=='Z')g.drawImage(ygreenImg,160,347,70,70,null);
    	else if(b[1]=='W')g.drawImage(rgreenImg,160,347,70,70,null);
    	
    	if(b[2]=='X')g.drawImage(redImg,228,347,70,70,null);
    	else if(b[2]=='O')g.drawImage(yellowImg,228,347,70,70,null);
    	else if(b[2]=='Z')g.drawImage(ygreenImg,228,347,70,70,null);
    	else if(b[2]=='W')g.drawImage(rgreenImg,228,347,70,70,null);
    	
    	if(b[3]=='X')g.drawImage(redImg,296,347,70,70,null);
    	else if(b[3]=='O')g.drawImage(yellowImg,296,347,70,70,null);
    	else if(b[3]=='Z')g.drawImage(ygreenImg,296,347,70,70,null);
    	else if(b[3]=='W')g.drawImage(rgreenImg,296,347,70,70,null);
    	
    	if(b[4]=='X')g.drawImage(redImg,364,347,70,70,null);
    	else if(b[4]=='O')g.drawImage(yellowImg,364,347,70,70,null);
    	else if(b[4]=='Z')g.drawImage(ygreenImg,364,347,70,70,null);
    	else if(b[4]=='W')g.drawImage(rgreenImg,364,347,70,70,null);
    	
    	if(b[5]=='X')g.drawImage(redImg,432,347,70,70,null);
    	else if(b[5]=='O')g.drawImage(yellowImg,432,347,70,70,null);
    	else if(b[5]=='Z')g.drawImage(ygreenImg,432,347,70,70,null);
    	else if(b[5]=='W')g.drawImage(rgreenImg,432,347,70,70,null);
    	
    	if(b[6]=='X')g.drawImage(redImg,500,347,70,70,null);
    	else if(b[6]=='O')g.drawImage(yellowImg,500,347,70,70,null);
    	else if(b[6]=='Z')g.drawImage(ygreenImg,500,347,70,70,null);
    	else if(b[6]=='W')g.drawImage(rgreenImg,500,347,70,70,null);
    	
    	if(b[7]=='X')g.drawImage(redImg,568,347,70,70,null);
    	else if(b[7]=='O')g.drawImage(yellowImg,568,347,70,70,null);
    	else if(b[7]=='Z')g.drawImage(ygreenImg,568,347,70,70,null);
    	else if(b[7]=='W')g.drawImage(rgreenImg,568,347,70,70,null);
    	
    	if(c[1]=='X')g.drawImage(redImg,160,279,70,70,null);
    	else if(c[1]=='O')g.drawImage(yellowImg,160,279,70,70,null);
    	else if(c[1]=='Z')g.drawImage(ygreenImg,160,279,70,70,null);
    	else if(c[1]=='W')g.drawImage(rgreenImg,160,279,70,70,null);
    	
    	if(c[2]=='X')g.drawImage(redImg,228,279,70,70,null);
    	else if(c[2]=='O')g.drawImage(yellowImg,228,279,70,70,null);
    	else if(c[2]=='Z')g.drawImage(ygreenImg,228,279,70,70,null);
    	else if(c[2]=='W')g.drawImage(rgreenImg,228,279,70,70,null);
    	
    	if(c[3]=='X')g.drawImage(redImg,296,279,70,70,null);
    	else if(c[3]=='O')g.drawImage(yellowImg,296,279,70,70,null);
    	else if(c[3]=='Z')g.drawImage(ygreenImg,296,279,70,70,null);
    	else if(c[3]=='W')g.drawImage(rgreenImg,296,279,70,70,null);
    	
    	if(c[4]=='X')g.drawImage(redImg,364,279,70,70,null);
    	else if(c[4]=='O')g.drawImage(yellowImg,364,279,70,70,null);
    	else if(c[4]=='Z')g.drawImage(ygreenImg,364,279,70,70,null);
    	else if(c[4]=='W')g.drawImage(rgreenImg,364,279,70,70,null);
    	
    	if(c[5]=='X')g.drawImage(redImg,432,279,70,70,null);
    	else if(c[5]=='O')g.drawImage(yellowImg,432,279,70,70,null);
    	else if(c[5]=='Z')g.drawImage(ygreenImg,432,279,70,70,null);
    	else if(c[5]=='W')g.drawImage(rgreenImg,432,279,70,70,null);
    	
    	if(c[6]=='X')g.drawImage(redImg,500,279,70,70,null);
    	else if(c[6]=='O')g.drawImage(yellowImg,500,279,70,70,null);
    	else if(c[6]=='Z')g.drawImage(ygreenImg,500,279,70,70,null);
    	else if(c[6]=='W')g.drawImage(rgreenImg,500,279,70,70,null);
    	
    	if(c[7]=='X')g.drawImage(redImg,568,279,70,70,null);
    	else if(c[7]=='O')g.drawImage(yellowImg,568,279,70,70,null);
    	else if(c[7]=='Z')g.drawImage(ygreenImg,568,279,70,70,null);
    	else if(c[7]=='W')g.drawImage(rgreenImg,568,279,70,70,null);
    	
    	if(d[1]=='X')g.drawImage(redImg,160,211,70,70,null);
    	else if(d[1]=='O')g.drawImage(yellowImg,160,211,70,70,null);
    	else if(d[1]=='Z')g.drawImage(ygreenImg,160,211,70,70,null);
    	else if(d[1]=='W')g.drawImage(rgreenImg,160,211,70,70,null);
    	
    	if(d[2]=='X')g.drawImage(redImg,228,211,70,70,null);
    	else if(d[2]=='O')g.drawImage(yellowImg,228,211,70,70,null);
    	else if(d[2]=='Z')g.drawImage(ygreenImg,228,211,70,70,null);
    	else if(d[2]=='W')g.drawImage(rgreenImg,228,211,70,70,null);
    	
    	if(d[3]=='X')g.drawImage(redImg,296,211,70,70,null);
    	else if(d[3]=='O')g.drawImage(yellowImg,296,211,70,70,null);
    	else if(d[3]=='Z')g.drawImage(ygreenImg,296,211,70,70,null);
    	else if(d[3]=='W')g.drawImage(rgreenImg,296,211,70,70,null);
    	
    	if(d[4]=='X')g.drawImage(redImg,364,211,70,70,null);
    	else if(d[4]=='O')g.drawImage(yellowImg,364,211,70,70,null);
    	else if(d[4]=='Z')g.drawImage(ygreenImg,364,211,70,70,null);
    	else if(d[4]=='W')g.drawImage(rgreenImg,364,211,70,70,null);
    	
    	if(d[5]=='X')g.drawImage(redImg,432,211,70,70,null);
    	else if(d[5]=='O')g.drawImage(yellowImg,432,211,70,70,null);
    	else if(d[5]=='Z')g.drawImage(ygreenImg,432,211,70,70,null);
    	else if(d[5]=='W')g.drawImage(rgreenImg,432,211,70,70,null);
    	
    	if(d[6]=='X')g.drawImage(redImg,500,211,70,70,null);
    	else if(d[6]=='O')g.drawImage(yellowImg,500,211,70,70,null);
    	else if(d[6]=='Z')g.drawImage(ygreenImg,500,211,70,70,null);
    	else if(d[6]=='W')g.drawImage(rgreenImg,500,211,70,70,null);
    	
    	if(d[7]=='X')g.drawImage(redImg,568,211,70,70,null);
    	else if(d[7]=='O')g.drawImage(yellowImg,568,211,70,70,null);
    	else if(d[7]=='Z')g.drawImage(ygreenImg,568,211,70,70,null);
    	else if(d[7]=='W')g.drawImage(rgreenImg,568,211,70,70,null);
    	
    	if(e[1]=='X')g.drawImage(redImg,160,143,70,70,null);
    	else if(e[1]=='O')g.drawImage(yellowImg,160,143,70,70,null);
    	else if(e[1]=='Z')g.drawImage(ygreenImg,160,143,70,70,null);
    	else if(e[1]=='W')g.drawImage(rgreenImg,160,143,70,70,null);
    	
    	if(e[2]=='X')g.drawImage(redImg,228,143,70,70,null);
    	else if(e[2]=='O')g.drawImage(yellowImg,228,143,70,70,null);
    	else if(e[2]=='Z')g.drawImage(ygreenImg,228,143,70,70,null);
    	else if(e[2]=='W')g.drawImage(rgreenImg,228,143,70,70,null);
    	
    	if(e[3]=='X')g.drawImage(redImg,296,143,70,70,null);
    	else if(e[3]=='O')g.drawImage(yellowImg,296,143,70,70,null);
    	else if(e[3]=='Z')g.drawImage(ygreenImg,296,143,70,70,null);
    	else if(e[3]=='W')g.drawImage(rgreenImg,296,143,70,70,null);
    	
    	if(e[4]=='X')g.drawImage(redImg,364,143,70,70,null);
    	else if(e[4]=='O')g.drawImage(yellowImg,364,143,70,70,null);
    	else if(e[4]=='Z')g.drawImage(ygreenImg,364,143,70,70,null);
    	else if(e[4]=='W')g.drawImage(rgreenImg,364,143,70,70,null);
    	
    	if(e[5]=='X')g.drawImage(redImg,432,143,70,70,null);
    	else if(e[5]=='O')g.drawImage(yellowImg,432,143,70,70,null);
    	else if(e[5]=='Z')g.drawImage(ygreenImg,432,143,70,70,null);
    	else if(e[5]=='W')g.drawImage(rgreenImg,432,143,70,70,null);
    	
    	if(e[6]=='X')g.drawImage(redImg,500,143,70,70,null);
    	else if(e[6]=='O')g.drawImage(yellowImg,500,143,70,70,null);
    	else if(e[6]=='Z')g.drawImage(ygreenImg,500,143,70,70,null);
    	else if(e[6]=='W')g.drawImage(rgreenImg,500,143,70,70,null);
    	
    	if(e[7]=='X')g.drawImage(redImg,568,143,70,70,null);
    	else if(e[7]=='O')g.drawImage(yellowImg,568,143,70,70,null);
    	else if(e[7]=='Z')g.drawImage(ygreenImg,568,143,70,70,null);
    	else if(e[7]=='W')g.drawImage(rgreenImg,568,143,70,70,null);
    	
    	if(f[1]=='X')g.drawImage(redImg,160,75,70,70,null);
    	else if(f[1]=='O')g.drawImage(yellowImg,160,75,70,70,null);
    	else if(f[1]=='Z')g.drawImage(ygreenImg,160,75,70,70,null);
    	else if(f[1]=='W')g.drawImage(rgreenImg,160,75,70,70,null);
    	
    	if(f[2]=='X')g.drawImage(redImg,228,75,70,70,null);
    	else if(f[2]=='O')g.drawImage(yellowImg,228,75,70,70,null);
    	else if(f[2]=='Z')g.drawImage(ygreenImg,228,75,70,70,null);
    	else if(f[2]=='W')g.drawImage(rgreenImg,228,75,70,70,null);
    	
    	if(f[3]=='X')g.drawImage(redImg,296,75,70,70,null);
    	else if(f[3]=='O')g.drawImage(yellowImg,296,75,70,70,null);
    	else if(f[3]=='Z')g.drawImage(ygreenImg,296,75,70,70,null);
    	else if(f[3]=='W')g.drawImage(rgreenImg,296,75,70,70,null);
    	
    	if(f[4]=='X')g.drawImage(redImg,364,75,70,70,null);
    	else if(f[4]=='O')g.drawImage(yellowImg,364,75,70,70,null);
    	else if(f[4]=='Z')g.drawImage(ygreenImg,364,75,70,70,null);
    	else if(f[4]=='W')g.drawImage(rgreenImg,364,75,70,70,null);
    	
    	if(f[5]=='X')g.drawImage(redImg,432,75,70,70,null);
    	else if(f[5]=='O')g.drawImage(yellowImg,432,75,70,70,null);
    	else if(f[5]=='Z')g.drawImage(ygreenImg,432,75,70,70,null);
    	else if(f[5]=='W')g.drawImage(rgreenImg,432,75,70,70,null);
    	
    	if(f[6]=='X')g.drawImage(redImg,500,75,70,70,null);
    	else if(f[6]=='O')g.drawImage(yellowImg,500,75,70,70,null);
    	else if(f[6]=='Z')g.drawImage(ygreenImg,500,75,70,70,null);
    	else if(f[6]=='W')g.drawImage(rgreenImg,500,75,70,70,null);
    	
    	if(f[7]=='X')g.drawImage(redImg,568,75,70,70,null);
    	else if(f[7]=='O')g.drawImage(yellowImg,568,75,70,70,null);
    	else if(f[7]=='Z')g.drawImage(ygreenImg,568,75,70,70,null);
    	else if(f[7]=='W')g.drawImage(rgreenImg,568,75,70,70,null);


    	
    	/* Full place of red coins in the game
    	// The distance between each image x or y on the board is : 68px
    	g.drawImage(redImg,160,415,70,70,null);//a1
    	g.drawImage(redImg,228,415,70,70,null);//a2
    	g.drawImage(redImg,296,415,70,70,null);//a3
    	g.drawImage(redImg,364,415,70,70,null);//a4
    	g.drawImage(redImg,432,415,70,70,null);//a5
    	g.drawImage(redImg,500,415,70,70,null);//a6
    	g.drawImage(redImg,568,415,70,70,null);//a7
    	
    	g.drawImage(redImg,160,347,70,70,null);//b1
    	g.drawImage(redImg,228,347,70,70,null);//b2
    	g.drawImage(redImg,296,347,70,70,null);//b3
    	g.drawImage(redImg,364,347,70,70,null);//b4
    	g.drawImage(redImg,432,347,70,70,null);//b5
    	g.drawImage(redImg,500,347,70,70,null);//b6
    	g.drawImage(redImg,568,347,70,70,null);//b7
    	
    	g.drawImage(redImg,160,279,70,70,null);//c1
    	g.drawImage(redImg,228,279,70,70,null);//c2
    	g.drawImage(redImg,296,279,70,70,null);//c3
    	g.drawImage(redImg,364,279,70,70,null);//c4
    	g.drawImage(redImg,432,279,70,70,null);//c5
    	g.drawImage(redImg,500,279,70,70,null);//c6
    	g.drawImage(redImg,568,279,70,70,null);//c7
    	
    	g.drawImage(redImg,160,211,70,70,null);//d1
    	g.drawImage(redImg,228,211,70,70,null);//d2
    	g.drawImage(redImg,296,211,70,70,null);//d3
    	g.drawImage(redImg,364,211,70,70,null);//d4
    	g.drawImage(redImg,432,211,70,70,null);//d5
    	g.drawImage(redImg,500,211,70,70,null);//d6
    	g.drawImage(redImg,568,211,70,70,null);//d7
    	
    	g.drawImage(redImg,160,143,70,70,null);//e1
    	g.drawImage(redImg,228,143,70,70,null);//e2
    	g.drawImage(redImg,296,143,70,70,null);//e3
    	g.drawImage(redImg,364,143,70,70,null);//e4
    	g.drawImage(redImg,432,143,70,70,null);//e5
    	g.drawImage(redImg,500,143,70,70,null);//e6
    	g.drawImage(redImg,568,143,70,70,null);//e7
    	
    	g.drawImage(redImg,160,75,70,70,null);//f1
    	g.drawImage(redImg,228,75,70,70,null);//f2
    	g.drawImage(redImg,296,75,70,70,null);//f3
    	g.drawImage(redImg,364,75,70,70,null);//f4
    	g.drawImage(redImg,432,75,70,70,null);//f5
    	g.drawImage(redImg,500,75,70,70,null);//f6
    	g.drawImage(redImg,568,75,70,70,null);//f7
    	*/
    	
    	/* To Understand the Label Codes
    	g.drawImage(redImg,160,70,70,425,null);//l1
    	g.drawImage(redImg,228,70,70,425,null);//l2
    	g.drawImage(redImg,296,70,70,425,null);//l3
    	g.drawImage(redImg,364,70,70,425,null);//l4
    	g.drawImage(redImg,432,70,70,425,null);//l5
    	g.drawImage(redImg,500,70,70,425,null);//l6
    	g.drawImage(redImg,568,70,70,425,null);//l7
    	*/
    }
	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(l1)){
			x=1;
			//System.out.println("l1 was Clicked..");
			try {
				CheckUserPlay(); // This code generated ( throws InterruptedException ) all over the methods
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		else if(e.getSource().equals(l2)){
			x=2;
			//System.out.println("l2 was Clicked..");
			try {
				CheckUserPlay();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		else if(e.getSource().equals(l3)){
			x=3;
			//System.out.println("l3 was Clicked..");
			try {
				CheckUserPlay();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		else if(e.getSource().equals(l4)){
			x=4;
			//System.out.println("l4 was Clicked..");
			try {
				CheckUserPlay();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		else if(e.getSource().equals(l5)){
			x=5;
			//System.out.println("l5 was Clicked..");
			try {
				CheckUserPlay();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		else if(e.getSource().equals(l6)){
			x=6;
			//System.out.println("l6 was Clicked..");
			try {
				CheckUserPlay();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		else if(e.getSource().equals(l7)){
			x=7;
			//System.out.println("l7 was Clicked..");
			try {
				CheckUserPlay();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main (String args[]) throws InterruptedException{
		@SuppressWarnings("unused") // Not Important, it is for preventing false warning..
		Connect4 Game=new Connect4();
	}
	
}
