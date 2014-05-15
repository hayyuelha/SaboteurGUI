import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;


public class GamePlay {
	public Vector<Player> ListOfPlayer = new Vector();
	private static Scanner scan = new Scanner(System.in);
	Deck aDeck = new Deck();
        Board board = new Board();
        Deck rubbish = new Deck();
        Player currentPlayer = new Player();
//        public void loadFromExFile (FileReader fin) throws IOException{
//            BufferedReader br = new BufferedReader(fin);
//            String name;
//            int s;
//            String LastPlay;
//            Player p;
//            while (br.ready()){
//                name = br.readLine();
//                p = new Player(name);
//                s = br.read();
//                p.setScore(s);
//                LastPlay = br.readLine();
//                p.setDate(LastPlay);
//                ListOfPlayer.addElement(p);
//            }
//            p = ListOfPlayer.get(0);
//            name = p.getPlayerName();
//            System.out.println(name);
//            LastPlay = p.getLastPlay();
//            System.out.println(LastPlay);
//        }
//	
	public void addPlayer() {
		System.out.print("Enter your name : ");
		String whiteSpace = scan.nextLine();
		String playerName = scan.nextLine();
		Player myPlayer = new Player(playerName);
		if(IsNotRegistered(playerName))
			ListOfPlayer.add(myPlayer);
		else
			printMessage("Sorry your name already registered!");
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
	private void setRoleAndTurnForPlayer(){
		Vector<Integer> myIdCharacter = new Vector();
		int i;
		for(i = 0 ; i <ListOfPlayer.size(); i++){
			ListOfPlayer.get(i).setTurn(i);
                        if(i < ListOfPlayer.size()/2)
				myIdCharacter.add(2);// add id for saboteur
			else 
				myIdCharacter.add(1);// add id for goldminer
                }
		System.out.println(i);
		Collections.shuffle(myIdCharacter);
		for (i = 0 ; i < ListOfPlayer.size(); i++)
			ListOfPlayer.elementAt(i).setRole(myIdCharacter.elementAt(i));
	}
	
	private void play(){
		if(ListOfPlayer.size()>=3){
			setRoleAndTurnForPlayer();
			//printRolePlayers();
                        aDeck.fillDeck();
                        for(Player p : ListOfPlayer){
                            for (int i=0;i<5;i++){
                                p.drawCard(aDeck.popCard());
                            }
                        }
                        gameLoop();
		}else
			printMessage("Sorry, please complete the requirement first!"+
					"\nminimal player is three player");
	}
        
        private void showMenuGame(){
            System.out.println("1. Draw card");
            System.out.println("2. Choose card");
            System.out.println("3. Tampilkan urutan dan status player");
            System.out.println("4. Tampilkan status player");
            System.out.println("5. keluar");
            System.out.print("Pilihan menu: ");
            
        }
        
        private void setNextPlayer(){
            if(currentPlayer.getTurn()==ListOfPlayer.size()-1){
                currentPlayer = ListOfPlayer.get(0);
            }else{
               do{
                currentPlayer = ListOfPlayer.get(currentPlayer.getTurn()+1);
               }while(currentPlayer.getStatus().compareTo("Disable")==0);
            }
            currentPlayer.setFinishedTurn(false);
            currentPlayer.setFinishedDraw(false);
        }
        
        private void gameLoop(){
            boolean finish=false;
            currentPlayer = ListOfPlayer.elementAt(0);
            
            while(!finish){
                board.PrintBoard();
                System.out.println("\nYour turn : "+currentPlayer.getPlayerName());
                currentPlayer.printCardsOnHand();
                showMenuGame();
                int menu = scan.nextInt();
                switch (menu){
                    case 1 ://draw Card 
                            processMenuDrawCard();
                            break;
                    case 2 : // Choose Card
                             processMenuChooseCard();
                             break;
                    case 3 : ShowPlayers();
                             break;
                    case 4 : currentPlayer.showStatus();
                             backToPrevMenu();
                             break;
                    case 5 : int IdxPrevPlayer = currentPlayer.getTurn();
                             setNextPlayer();
                             changeTurn(IdxPrevPlayer);
                             ListOfPlayer.removeElementAt(IdxPrevPlayer);
                             break;
                    default :printMessage("default : input is  not valid!");
                }
                finish = (board.isFinished() || ListOfPlayer.size()<3);
               // printMessage(currentPlayer.getFinishedDraw()+" bbb is  not valid!");
                if(currentPlayer.getFinishedDraw() && menu!=5)
                    setNextPlayer();
            }
            ListOfPlayer.clear();
        }
        
        private void changeTurn(int idx)
        {
            ListOfPlayer.clear();
            for (int i = idx+1 ; i < ListOfPlayer.size() ; i++){
                ListOfPlayer.get(i).setTurn(i-1);
            }
        }        
        private void processMenuChooseCard(){
            if(!currentPlayer.getFinishedTurn()){
                System.out.print("Masukkan indeks kartu : ");
                int idx = scan.nextInt();
                Card crd = currentPlayer.disCard(idx);
                System.out.print("1. Buang kartu \n2. Gunakan Kartu"+
                        "\nMasukkan pilihan Anda : ");
                try{
                    idx = scan.nextInt();
                    if(idx==1){
                        currentPlayer.setFinishedTurn(true);
                        printMessage("Anda telah membuang kartu tersebut");
                        currentPlayer.setIdxRemovedCard(-1);
                    }else if (idx==2){
                        usingCard(crd);
                    }else{
                         printMessage("input Anda tidak valid! serius woii");
                    }
                }catch(Exception e){
                    printMessage("input Anda tidak valid!");
                }
                
           }else
               printMessage("Anda telah memakai kartu pada giliran ini");
        }
        
        private void processMenuDrawCard(){
             if(currentPlayer.getFinishedTurn()){
                Card c = aDeck.popCard();
                try{
                   currentPlayer.drawCard(c);
                   currentPlayer.printCardsOnHand();
                   System.out.println("wes sukses ngedraw, hahahah\nEnter to change nextplayer !");
                   currentPlayer.setFinishedTurn(true);
                }catch(Exception e){
                    printMessage("kartu habis, hahahah");
                }
                currentPlayer.setFinishedDraw(true);
                backToPrevMenu();
            }else
                printMessage("Anda belum menggunakan kartu, hahahah");
        }
        
        private void printMessage(String msg){
            System.out.println(msg);
            backToPrevMenu();
        }
        
        private void ShowPlayers(){
            System.out.println("Urutan\tnama");
            for (Player p : ListOfPlayer){ 
                System.out.println(p.getTurn() + "\t" + p.getPlayerName() + "   " +
                        p.getStatus());
            }
            backToPrevMenu();
        }
	
        private void usingCard(Card crd){
            if (crd.getType()==1){
                //pathcard
                System.out.print("Apakah Anda mau memutar kartu? (y/n) ");
                if ("y".equals(scan.next())){((Path_Card) crd).rotateCard();}
                System.out.print("Masukkan koordinat x<spasi>y : ");
                Vector<Integer> pos = new Vector(); pos.add(0,scan.nextInt());
                pos.add(1,scan.nextInt());
                if(-1==board.putCardOnBoard((Path_Card) crd, pos)){
                    currentPlayer.drawCard(crd);
                    backToPrevMenu();
                }else
                    currentPlayer.setFinishedTurn(true);
                currentPlayer.setIdxRemovedCard(-1);
            } else {
                //actioncard
                int id = crd.getID();
                switch (id) {
                    case 1 : //viewmap
                             usingViewMap(crd);
                             break;
                    case 2 : //break
                             usingBreakTool(crd);
                             break;
                    case 3 : //repair
                             usingRepairTool(crd);
                             break;
                    default : 
                }
                currentPlayer.setIdxRemovedCard(-1);
            }
        }
        
        private void usingViewMap(Card crd){
            System.out.print("Masukkan pilihan goalcard: ");
            int urutan = scan.nextInt();
            if(urutan>=1 && urutan<=3){
               board.viewMap(urutan);
               currentPlayer.setFinishedTurn(true);
               backToPrevMenu();
            }else{
                currentPlayer.drawCard(crd);
                printMessage("index tidak valid!");
            }
        }
        
        private void usingBreakTool(Card crd){
            System.out.print("Masukkan urutan pemain: ");
            int urutan = scan.nextInt();
            Player victim = ListOfPlayer.get(urutan);
            if (victim.getStatus().compareTo("Enable")==0 && 
                    victim.getTurn()!=currentPlayer.getTurn()){
               victim.setStatus("Disable");
               currentPlayer.setFinishedTurn(true);
               printMessage("Player telah berstatus disable");
            }else{
                currentPlayer.drawCard(crd);
                printMessage("Player yang anda  jadikan target tidak valid ");
            }
        }
        
        private void usingRepairTool(Card crd){
            System.out.print("Masukkan urutan pemain: ");
            int urutan = scan.nextInt();
            Player victim = ListOfPlayer.get(urutan);
            if (victim.getStatus().compareTo("Disable")==0 && 
                    victim.getTurn()!=currentPlayer.getTurn()){
               victim.setStatus("Enable");
               currentPlayer.setFinishedTurn(true);
               printMessage("Player telah berstatus enable");
            }else{
                currentPlayer.drawCard(crd);
                printMessage("Player yang anda  jadikan target tidak valid ");
            }
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
			default: printMessage("Sorry, your input is invalid!");
		}
	}
	
	public void start() throws FileNotFoundException, IOException{
		FileReader fr = new FileReader("player.in");
//                loadFromExFile (fr);
                //addPlayer
		int indexMenu = 0;
		while(indexMenu!= 4){
			showMenuCMD();
                        try {
                            indexMenu = scan.nextInt();
                            runMenu(indexMenu);
                        }catch(Exception e){
                            printMessage("Sorry, your input is invalid!");
                        }
                }
		System.out.print("==== Game closed ====");
	}
}
