import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;


public class GamePlay {
	public Vector<Player> ListOfPlayer = new Vector();
	private static Scanner scan = new Scanner(System.in);
	Deck aDeck = new Deck();
	
	public void addPlayer() {
		System.out.print("Enter your name : ");
		String whiteSpace = scan.nextLine();
		String playerName = scan.nextLine();
		
		Player myPlayer = new Player(playerName);
		if(IsNotRegistered(playerName))
			ListOfPlayer.add(myPlayer);
		else{
			System.out.println("Sorry your name already registered!");
			backToPrevMenu();
		}
	}
	
	public boolean IsNotRegistered(String Name){
		for (int i = 0 ; i <ListOfPlayer.size() ; i++)
			if(Name.compareTo(ListOfPlayer.elementAt(i).getPlayerName()) == 0)
				return false;
		return true;
	}
	
	public void clearScreen(){
		for (int a = 0 ; a <2 ; a++)
			System.out.println();
	}
	public void showMenuCMD(){
		clearScreen();
		System.out.println("==== Menu Utama ====");
		System.out.println("1. Play"+
				"\n2. AddPlayer"+
				"\n3. Show Registered Player"+
				"\n4. Exit");
		System.out.print("Input your index menu : ");
	}
	public void backToPrevMenu(){
		System.out.println("Enter to continue !");
		String whiteSpace = scan.nextLine();
		whiteSpace = scan.nextLine();
	}
	private void showRegisteredPlayer(){
		System.out.println("\nNumber of registered player is "+ ListOfPlayer.size() );
		for(int i = 0 ; i < ListOfPlayer.size() ; i++)
			System.out.println((i+1)+". "+ListOfPlayer.elementAt(i).getPlayerName());
		backToPrevMenu();
	}
	private void setRoleForPlayer(){
		Vector<Integer> myIdCharacter = new Vector();
		int i;
		for(i = 0 ; i <ListOfPlayer.size(); i++)
			if(i < ListOfPlayer.size()/2)
				myIdCharacter.add(2);// add id for saboteur
			else 
				myIdCharacter.add(1);// add id for goldminer
		System.out.println(i);
		Collections.shuffle(myIdCharacter);
		for (i = 0 ; i < ListOfPlayer.size(); i++)
			ListOfPlayer.elementAt(i).setRole(myIdCharacter.elementAt(i));
	}
	
	private void play(){
		if(ListOfPlayer.size()>=3){
			setRoleForPlayer();
			printRolePlayers();
			
		}else
			System.out.println("Sorry, please complete the requirement first!"+
					"\nminimal player is three player");
	}
	
	private void printRolePlayers(){
		// just for debuging
		for(int i = 0 ; i < ListOfPlayer.size() ; i++)
			System.out.println((i+1)+". "+ListOfPlayer.elementAt(i).getPlayerName()+
					"   role : "+ListOfPlayer.elementAt(i).getRoleId());
	}
	
	private void runMenu(int indexMenu){
		switch(indexMenu){
			case 1 : play(); break;
			case 2 : addPlayer(); break;
			case 3 : showRegisteredPlayer(); break;
			case 4 : //exit; 
				break;
			default: System.out.println("Sorry, your input is invalid!");
		}
	}
	
	public void start(){
		//addPlayer
		int indexMenu = 0;
		while(indexMenu!= 4){
			showMenuCMD();
			try{
				indexMenu = scan.nextInt();
				runMenu(indexMenu);
			}catch(Exception e){
				System.out.println("Sorry your input is not valid !");
				backToPrevMenu();
			}
			
		}
		System.out.print("==== Game closed ====");
	}
}
