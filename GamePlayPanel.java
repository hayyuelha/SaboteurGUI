/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author user
 */
public class GamePlayPanel extends ImagePanel {    
    private handCardGroup cardPanel;
    private fieldCardGroup fieldPanel;
    private GridBagConstraints c = new GridBagConstraints();
    private JTextArea statusList;
    private JTextArea messageBox;

    /**
     * JButton untuk keluar dari permainan
     */
    public JButton giveUpButton,

    /**
     * JButton untuk mengambil kartu dari deck
     */
    drawButton,

    /**
     * JButton untuk memutar kartu
     */
    rotateButton,

    /**
     * JButton untuk menyelesaikan turn
     */
    finishTurnButton;
    private int koorX, koorY;
    String tempPath = new String("img/fieldCard/forbiddenCard.png");
    private int  handIndex;
    GamePlay gamePlay = new GamePlay();

    /**
     * ImagePanel untuk menampilkan kartu-kartu yang telah dibuang
     */
    public ImagePanel garbage;

    /**
     * Default constructor untuk kelas GamePlayPanel; menginisialisasi semua atribut
     */
    public GamePlayPanel() {
        super("img/backgroundBoard.jpg");
        setLayout(new GridBagLayout());
        koorX = -1;
        koorY = -1;
        
        Init();
        ResetConstraints();
        
        /* Title */
        ImagePanel tes = new ImagePanel("C:/Users/user/Desktop/tesHelp.png");
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(10,0,0,0);
        c.anchor = GridBagConstraints.NORTH;
        add(tes,c);
        
        /* List status pemain */
        statusList = new JTextArea(20,20);
        statusList.setEditable(false);
        c.fill = GridBagConstraints.NONE;
        //c.fill = GridBagConstraints.VERTICAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 20, 0, 0);
        add(statusList,c);
        
        /* Field */
        ResetConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.CENTER;
        add(fieldPanel,c);
        
        /* Message box */
        messageBox = new JTextArea(20,25);
        messageBox.setEditable(false);
        JScrollPane scroll = new JScrollPane(messageBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //c.fill = GridBagConstraints.VERTICAL;
        c.weighty = 0;
        c.gridy = 1;
        c.gridx = 2;
        c.insets = new Insets(0, 0, 0, 20);
        c.anchor = GridBagConstraints.EAST;
        add(scroll,c);
        
        /* Garbage Collector */
        garbage = new ImagePanel("img/card/actioncard2.png");
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0, 0, 10, 0);
        c.anchor = GridBagConstraints.CENTER;
        garbage.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed (MouseEvent e) {
                if(handIndex != -1 && !gamePlay.currentPlayer.getFinishedTurn()){
                    geserKartu(handIndex);
                    gamePlay.currentPlayer.setFinishedTurn(true);
                }
            }
        });
        
        JLabel label = new JLabel("Graveyard");
        garbage.add(label);
        Font font = new Font("Verdana",5,14);
        label.setFont(font);
        add(garbage,c);
        
        /* Button - button */
        JPanel panelButton = new JPanel(new GridLayout(2,2));
        giveUpButton = new JButton("GIVE UP");
        drawButton = new JButton("DRAW");
        rotateButton = new JButton("ROTATE");
        finishTurnButton = new JButton("FINISH TURN");        
        
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!gamePlay.currentPlayer.getFinishedDraw() && gamePlay.currentPlayer.getFinishedTurn()){
                    gamePlay.currentPlayer.setFinishedDraw(true);
                    Card crd = gamePlay.aDeck.popCard();
                    gamePlay.currentPlayer.drawCard(crd);
                    setCardOnHand(crd);
                }
            }
        });
        
        rotateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        
        
        
        giveUpButton.setPreferredSize(new Dimension(120,40));
        drawButton.setPreferredSize(new Dimension(120,40));
        rotateButton.setPreferredSize(new Dimension(120,40));
        finishTurnButton.setPreferredSize(new Dimension(120,40));
        
        panelButton.add(rotateButton);
        panelButton.add(finishTurnButton);
        panelButton.add(drawButton);
        panelButton.add(giveUpButton);
        
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 2;
        c.gridy = 2;
        add(panelButton,c);
        
        /* Hand Card */
        c.gridx = 1;
        c.gridy = 2;
        c.anchor = GridBagConstraints.SOUTH;
        c.insets = new Insets (0, 0, 10, 0);
        add(cardPanel,c);
        
        setVisible(true);
    }
    
    /**
     * Mengatur tampilan pada kartu di tangan 
     * @param crd kartu yang akan ditampilkan
     */
    public void setCardOnHand(Card crd){
        int idx = gamePlay.currentPlayer.CardsOnHand.size()-1;
        int Id = crd.getID();
        int Type = crd.getType();
        String strType = new String();
        if(Type==1)
            strType = "path"+Id;
        else if(Type == 2)
            strType = "actioncard"+Id;
        try {
            cardPanel.handCardPanel[idx].card = ImageIO.read(new File("img/card/"+strType+".png"));
            cardPanel.handCardPanel[idx].cardSelect = ImageIO.read(new File("img/cardSelect/"+strType+"Select.png"));
            cardPanel.handCardPanel[idx].cardClick = ImageIO.read(new File("img/cardClick/"+strType+"Click.png"));
            cardPanel.handCardPanel[idx].vPath.clear();
            cardPanel.handCardPanel[idx].vPath.add("img/card/"+strType+".png");
            cardPanel.handCardPanel[idx].vPath.add("img/cardSelect/"+strType+"Select.png");
            cardPanel.handCardPanel[idx].vPath.add("img/cardClick/"+strType+"Click.png");
            cardPanel.handCardPanel[idx].ID = 4;
            cardPanel.handCardPanel[idx].image = cardPanel.handCardPanel[4].card;
            cardPanel.handCardPanel[idx].repaint();
        } catch (IOException ex) {
            Logger.getLogger(GamePlayPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    /**
     * Method untuk mngurangi kartu yang sebelumnya 5 menjadi 4 
     * @param Idx indeks kartu yang dihilangkan dari tangan
     */
    public void geserKartu(int Idx){
        System.out.println("mmm " + Idx);
        gamePlay.currentPlayer.CardsOnHand.removeElementAt(handIndex);
        gamePlay.currentPlayer.setFinishedTurn(true);
        try{
            for(int i = Idx+1 ; i < 5 ; i++){
                cardPanel.handCardPanel[i -1].card = ImageIO.read(new File(cardPanel.handCardPanel[i].vPath.elementAt(0)));                        
                cardPanel.handCardPanel[i -1].cardSelect = ImageIO.read(new File(cardPanel.handCardPanel[i].vPath.elementAt(1)));
                cardPanel.handCardPanel[i -1].cardClick = ImageIO.read(new File(cardPanel.handCardPanel[i].vPath.elementAt(2)));
                cardPanel.handCardPanel[i -1].vPath = (Vector<String>) cardPanel.handCardPanel[i].vPath.clone();
                cardPanel.handCardPanel[i -1].ID = i-1;
                cardPanel.handCardPanel[i -1].image = cardPanel.handCardPanel[i-1].card;
                cardPanel.handCardPanel[i -1].repaint();
            }
            cardPanel.handCardPanel[4].card = ImageIO.read(new File("img/card/blank.png"));
            cardPanel.handCardPanel[4].cardSelect = ImageIO.read(new File("img/card/blank.png"));
            cardPanel.handCardPanel[4].cardClick = ImageIO.read(new File("img/card/blank.png"));
            cardPanel.handCardPanel[4].vPath.clear();
            cardPanel.handCardPanel[4].vPath.add("img/card/blank.png");
            cardPanel.handCardPanel[4].vPath.add("img/card/blank.png");
            cardPanel.handCardPanel[4].vPath.add("img/card/blank.png");
            cardPanel.handCardPanel[4].ID = 4;
            cardPanel.handCardPanel[4].image = cardPanel.handCardPanel[4].card;
            cardPanel.handCardPanel[4].repaint();

            cardPanel.repaint();
            cardPanel.handCardPanel[handIndex].clicked = false;
            handIndex = -1;
            tempPath = "img/fieldCard/forbiddenCard.png";
        }catch (IOException e){}
    }
            
    
    
    // semua berawal dari sini

    /**
     * Method untuk mengisi message/keterangan tentang pemain
     * @param game GamePlay
     */
        public void setGamePlay(GamePlay game){
        gamePlay = game;
        String Status = new String();
        Status+=("No  nama\n");
        for (Player p : gamePlay.ListOfPlayer){ 
            Status+=(p.getTurn() + ".  " + p.getPlayerName() + "    [" +
                    p.getStatus()+"]\n");
        }
        statusList.setText(Status);
        String Msg = new String();
        Msg += "Hai, "+gamePlay.currentPlayer.getPlayerName()+"\n";
        if(gamePlay.currentPlayer.getRoleId()==1){
            Msg += "Anda adalah seorang GoldMiner.\n";
        } else
            Msg += "Anda adalah seorang Saboteur.\n";
        if(gamePlay.currentPlayer.getFinishedTurn())
            Msg += "Anda belum melakukan draw kartu baru\n";
        else
            Msg += "Anda belum menggunakan kartu satu pun\n";
        messageBox.setText(Msg);
        cardPanel.updateCardOnHand();
    }
    
    
    private void Init() {
        /* cardPanel */
        //ResetConstraints();
        cardPanel = new handCardGroup();

        /* fieldPanel */
        fieldPanel = new fieldCardGroup();

    }
    
    private void ResetConstraints() {
       c.weightx = 0.0;
       c.weighty = 0.0;
       c.ipadx = 0;
       c.ipady = 0;
       c.gridheight = 1;
       c.gridwidth = 1;
       c.anchor = GridBagConstraints.CENTER;
       c.fill = GridBagConstraints.NONE;
       c.insets = new Insets(0, 0, 0, 0);
    }
    
    /**
     * Kelas turunan JPanel yang menampung kartu-kartu di tangan 
     */
    public class handCardGroup extends JPanel {

        /**
         * 
         */
        public handCard[] handCardPanel;
        //private int click;
//1=Path_Card, 2=Action_Card, 3=Character_Card
//1=ViewMap 2=break 3=repair
//1=GoldMiner 2=Saboteur

        /**
         * Default constructor handCardGroup 
         */
                public handCardGroup() {
            
            
        }
        
        /**
         * Method untuk memperbarui kartu di tangan
         */
        public void updateCardOnHand(){
            removeAll();
            ResetConstraints();
            setLayout(new GridBagLayout());
            c.insets = new Insets(0, 5, 0, 5);
            handCardPanel = new handCard[5];
            System.out.println("nih  "+gamePlay.currentPlayer.CardsOnHand.size());
            c.gridy = 0;
            for (int i=0; i<gamePlay.currentPlayer.CardsOnHand.size(); i++) {
                    int Id = gamePlay.currentPlayer.CardsOnHand.elementAt(i).getID();
                    int Type = gamePlay.currentPlayer.CardsOnHand.elementAt(i).getType();
                    String strType = new String();
                    if(Type==1)
                        strType = "path"+Id;
                    else if(Type == 2)
                        strType = "actioncard"+Id;
                    c.gridx = i;
                    System.out.println(strType);
                    handCardPanel[i] = new handCard("img/card/"+strType+".png", "img/cardSelect/"+strType+"Select.png",
                            "img/cardClick/"+strType+"Click.png", i);
                   // handCardPanel[i].repaint();
                    add(handCardPanel[i],c);                    
            }
            handIndex = -1;
            //repaint();
        }
        
        class handCard extends ImagePanel {
            int ID;
            public Vector<String> vPath = new Vector<>();
            public boolean clicked;
            public BufferedImage card, cardSelect, cardClick;
            
            public handCard(final String path, String select, String click1, final int ID) {
                super(path);
                vPath.add(path);
                vPath.add(select);
                vPath.add(click1);               
                
                try {
                    card = ImageIO.read(new File(path));
                    cardSelect = ImageIO.read(new File(select));
                    cardClick = ImageIO.read(new File(click1));
                } catch(IOException e) { 
                    System.out.println(e.getMessage());
                }
                this.ID = ID;
                clicked = false;
                addMouseListener(new MouseAdapter() {
                   @Override
                   public void mousePressed(MouseEvent e) {
                       if (e.isMetaDown()) {
                           System.out.println("Tes");
                       } else {
                            changeImage();
                            if (handIndex != ID) {
                                if (handIndex != -1) handCardPanel[handIndex].changeImage();
                                handIndex = ID;
                                tempPath = "img/fieldCard/" + vPath.elementAt(0).substring(9, vPath.elementAt(0).length());
                            } else {
                                 handIndex = -1;
                                 tempPath = "img/fieldCard/forbiddenCard.png";
                            }
                            System.out.println("Klik " + handIndex);

                            //System.out.println(clicked);
                       }
                   }
                   @Override
                   public void mouseEntered(MouseEvent e) {
                       System.out.println(ID);
                       if (!clicked) {
                           image = cardSelect;
                           repaint();
                       }
                   }
                   @Override
                   public void mouseExited(MouseEvent e) {
                       if (!clicked) {
                           image = card;
                           repaint();
                       }
                   }
                });
            }

            /*public void setAttribute(String path1, String path2) {
                try{
                    this.path1 = path1;
                    this.path2 = path2;
                    image = ImageIO.read(new File(path1));
                } catch(IOException e) {
                    System.out.println(e.getMessage());
                }
                repaint();
            }*/

            private void changeImage() {
                clicked = !clicked;
                image = clicked ? cardClick : card;
                setPreferredSize(new Dimension(image.getWidth(),image.getHeight()));
                repaint();
            }
        }

    }
    
    /**
     * Kelas turunan JPanel untuk menampung board
     */
    public class fieldCardGroup extends JPanel {

        /**
         * Array of fieldCard sebagai representasi board
         */
        public fieldCard[][] fCard = new fieldCard[5][9];
        
        /**
         * Default constructor kelas fieldCardGroup; Mengisi fCard dengan gambar
         */
        public fieldCardGroup() {
            setLayout(new GridBagLayout());
            ResetConstraints();
            for (int i=0; i<5; i++) {
                c.gridy = i;
                for (int j=0; j<9; j++) {
                    // empty card
                    fCard[i][j] = new fieldCard("img/fieldCard/cardEmpty.png", "img/fieldCard/path2.png", i, j);
                    //fCard[i][j].cardEmpty = Resize(fCard[i][j].cardEmpty, 0.5);
                    //fCard[i][j].cardSelect = Resize(fCard[i][j].cardSelect, 0.5);
                    c.gridx = j;
                    add(fCard[i][j],c);
                }
            }
            try{
                fCard[2][8].image = ImageIO.read(new File("img/card/startcard.png"));
                //fCard[2][8].image = Resize(fCard[2][8], 0.5);
            } catch (IOException e) {}
            fCard[2][8].filled = true;
            fCard[2][8].repaint();
        }
        
        /**
         * kelas turunan ImagePanel untuk menampung kartu
         */
        public class fieldCard extends ImagePanel {

            /**
             * BufferedImage untuk menampung kartu kosong
             */
            public BufferedImage cardEmpty,

            /**
             * BufferedImage untuk menampung kartu yang dipilih
             */
            cardSelect;
            private int[] ID = new int[2];
            private boolean filled;

            /**
             * Konstruktor berparameter kelas fieldCard
             * @param empty String untuk menampung path gambar kartu kosong
             * @param select String untuk menampung path gambar kartu yang dipilih
             * @param x absis
             * @param y ordinat
             */
            public fieldCard(String empty, String select, int x, int y) {
                super(empty);
                try {
                    cardEmpty = ImageIO.read(new File(empty));
                    cardSelect = ImageIO.read(new File(select));
                } catch(IOException e) {
                     System.out.println(e.getMessage());
                }
                filled = false;
                ID[0] = x;
                ID[1] = y;
                addMouseListener(new MouseAdapter() {
                   @Override
                    public void mouseEntered(MouseEvent e) {
                        if (!filled) {
                            //System.out.println("Koor " + ID[0] + "," + ID[1]);
                            try{
                                cardSelect = ImageIO.read(new File(tempPath));
                            } catch(IOException ex) {}
                            image = cardSelect;
                            repaint();
                        }
                    }

                   @Override
                   public void mousePressed(MouseEvent e) {
                       if (handIndex != -1)  {
                           koorX = ID[0]+1;
                           koorY = ID[1]+1;
                           System.out.println("super sekali "+koorX+"   "+koorY);
                           Card crd = gamePlay.currentPlayer.CardsOnHand.elementAt(handIndex);
                            Vector<Integer> pos = new Vector(); pos.add(0,koorX);
                                pos.add(1,koorY);
                           if(crd.getType()==1 && -1 != gamePlay.board.putCardOnBoard((Path_Card) crd, pos)
                                   && !gamePlay.currentPlayer.getFinishedTurn()){
                                filled = true;
                                geserKartu(handIndex);
                           }
                        }
                   }
                   
                   @Override
                   public void mouseExited(MouseEvent e) {
                        if (!filled) {
                            image = cardEmpty;
                            repaint();
                        }
                   }
                });
            }
            
        }
    }
    
    
}

