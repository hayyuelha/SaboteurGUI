
public class MainSaboteur {
    public static void main(String[] args) {
        
        System.setProperty(
            "Quaqua.tabLayoutPolicy","wrap"
         );

         // set the Quaqua Look and Feel in the UIManager
         try {
              javax.swing.UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
         // set UI manager properties here that affect Quaqua
         
         } catch (Exception e) {
             // take an appropriate action here
         }
       
        new eric.Console();
        while(true){
            System.out.println("Welcome to Java");
            System.out.println("Enter A number");
            
            try { System.in.read();}
            catch(Exception e){;}
        
            
       //GamePlay Game = new GamePlay();
       // Game.start();
        }
    }
}
