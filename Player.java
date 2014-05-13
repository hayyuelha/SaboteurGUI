import java.util.Date;
import java.util.Vector;


public class Player {
    private Character_Card Role;
    private String Status = new String("Undefined");
    private Vector<Card> CardsOnHand = new Vector();;
    private int Turn;
    private int Score;
    private String Name = new String("Undefined");
    private Date lastplay = new Date();
    private String LastPlay = lastplay.toString();
   
    public Player(){
        
    }
    
    public Player(String playerName){
    	Name = playerName;
    }
    
    public void setStatus(String S)
    {
        Status = S;
    }
    
    public void setTurn(int t){
        Turn = t;
    }
    
    public int getTurn(){
        return Turn;
    }
    
    public void setScore(int s){
        Score = s;
    }
    
    public void setDate(String d){
        LastPlay = d;        
    }
    
    public String getPlayerName(){
    	return Name;
    }
    
    public int getNCardonHand(){
        return CardsOnHand.size();
    }
    
    public void setRole(int id){
    	Role = new Character_Card(id);
    }
    
    public int getRoleId(){
    	return Role.getID();
    }
    
    public String getLastPlay(){
        return LastPlay;
    }
    
    public void drawCard(Card c)
    {
        CardsOnHand.add(c);
    }
    
    public Card disCard(int index){
        return CardsOnHand.remove(index);
    }
    
    public String getStatus(){
        return Status;
    }
    
    public void printCardsOnHand(){     // Mencetak semua kartu di tangan
        String lineu = "\u2594";
	String linel = "\u258F";
	String liner = "\u2595";
	String lined = "\u2581";
	String block = "\u2588";
        
        for (int i=0; i<CardsOnHand.size(); i++){
            System.out.print("      " + i);
        }
        System.out.println();
        System.out.println();
        System.out.print("  ");
        for (int i=0; i<CardsOnHand.size(); i++){
            System.out.print(liner + lineu + lineu);
            if (CardsOnHand.get(i).getType() == 1){
                if (((Path_Card) CardsOnHand.get(i)).getTop() == '1') {
                    System.out.print(block + block);
                } else {
                    System.out.print(lineu + lineu);
                } 
                System.out.print(lineu + lineu + linel);
            } else {
                System.out.print(lineu + lineu);
                System.out.print(lineu + lineu + linel);
            }
        }
        System.out.println();
        System.out.print("  ");
        for (int i=0; i<CardsOnHand.size(); i++){
            if (CardsOnHand.get(i).getType() == 1){
                System.out.print(liner + "  ");
                if (((Path_Card) CardsOnHand.get(i)).getTop() == '1') {
                    System.out.print(block + block);
                } else {
                    System.out.print("  ");
                } 
                System.out.print("  " + linel);
            } else {
                if (((Action_Card) CardsOnHand.get(i)).getID() == 1){
                    System.out.print(liner + " ");
                    System.out.print("VIEW");
                    System.out.print(" " + linel);
                } else if (((Action_Card) CardsOnHand.get(i)).getID() == 2){
                    System.out.print(liner + "");
                    System.out.print("BREAK");
                    System.out.print(" " + linel);
                } else {
                    System.out.print(liner + "");
                    System.out.print("REPAIR");
                    System.out.print("" + linel);
                }
            }
        }
        System.out.println();
        System.out.print("  ");
        for (int i=0; i<CardsOnHand.size(); i++){
            if (CardsOnHand.get(i).getType() == 1){
                if(((Path_Card) CardsOnHand.get(i)).getLeft() == '1'){
                    if(((Path_Card) CardsOnHand.get(i)).getCenter() == '1'){
                        if (((Path_Card) CardsOnHand.get(i)).getRight() == '1'){
                            System.out.print(liner + block + block + block + block + block + block + linel);
                        } else {
                            System.out.print(liner + block + block + block + block + "  " + linel);
                        }
                    } else {
                        if (((Path_Card) CardsOnHand.get(i)).getRight() == '1'){
                            System.out.print(liner + block + block + "  " + block + block + linel);
                        } else {
                            System.out.print(liner + block + block + "    " + linel);
                        }
                    }
                } else {
                    if(((Path_Card) CardsOnHand.get(i)).getCenter() == '1'){
                        if (((Path_Card) CardsOnHand.get(i)).getRight() == '1'){
                            System.out.print(liner + "  " + block + block + block + block + linel);
                        } else {
                            System.out.print(liner + "  " + block + block + "  " + linel);
                        }
                    } else {
                        if (((Path_Card) CardsOnHand.get(i)).getRight() == '1') {
                            System.out.print(liner + "    " + block + block + linel);
                        } else {
                            System.out.print(liner + "      " + linel);
                        }
                    }
                }    
            } else {
                if (((Action_Card) CardsOnHand.get(i)).getID() == 1){
                    System.out.print(liner + " ");
                    System.out.print("MAP");
                    System.out.print("  " + linel);
                } else if (((Action_Card) CardsOnHand.get(i)).getID() == 2){
                    System.out.print(liner + " ");
                    System.out.print("TOOL");
                    System.out.print(" " + linel);
                } else {
                    System.out.print(liner + " ");
                    System.out.print("TOOL");
                    System.out.print(" " + linel);
                }
            }
        }
        System.out.println();
        System.out.print("  ");
        for (int i=0; i<CardsOnHand.size(); i++){
            if (CardsOnHand.get(i).getType() == 1){
                System.out.print(liner + "  "); 
                if (((Path_Card) CardsOnHand.get(i)).getBottom() == '1') {
                    System.out.print(block + block);
                } else {
                    System.out.print("  ");
                } 
                System.out.print("  " + linel);
            } else {
                System.out.print(liner + "  "); 
                System.out.print("  ");
                System.out.print("  " + linel);
            }
        }
        System.out.println();
        System.out.print("  ");
        for (int i=0; i<CardsOnHand.size(); i++){
            if (CardsOnHand.get(i).getType() == 1){
                System.out.print(liner + lined + lined);
                if (((Path_Card) CardsOnHand.get(i)).getBottom() == '1') {
                    System.out.print(block + block);
                } else {
                    System.out.print(lined + lined);
                }
                System.out.print(lined + lined + linel);
            } else {
                System.out.print(liner + lined + lined);
                System.out.print(lined + lined);
                System.out.print(lined + lined + linel);
            }
        }
        System.out.println();
    }
}
