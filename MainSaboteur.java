
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;




public class MainSaboteur {
    public static void main(String[] args) throws FileNotFoundException, IOException {
       GamePlay Game = new GamePlay();
       Game.start();
    }
}
