import java.util.Vector;


public class Player {
	private Character_Card Role;
    private String Status = new String("Undefined");
    private Vector<Card> CardsOnHand = new Vector();;
    private int Turn;
    private int Score;
    private String Name = new String("Undefined");
    
    public Player(String playerName){
    	Name = playerName;
    }
    
    public void setCardsOnHand(){
    	
    }
    
    public void setStatus(String S)
    {
        Status = S;
    }
    
    public void setTurn(int t){
        Turn = t;
    }
    
    public void setScore(int s){
        Score = s;
    }
    public String getPlayerName(){
    	return Name;
    }
    
    public void setRole(int id){
    	Role = new Character_Card(id);
    }
    
    public int getRoleId(){
    	return Role.getID();
    }
    
    public void drawCard(Card c)
    {
        CardsOnHand.add(c);
    }
    
    public Card disCard(int index){
        return CardsOnHand.remove(index);
    }
}
