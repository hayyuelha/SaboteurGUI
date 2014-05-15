
import java.util.Random;
import java.util.Vector;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author icha
 */
public class Board {
    private Path_Card[][] MatrixOfCard;     // Papan permainan
    private boolean[] GoalCondition;        // True jika GoalCard dalam keadaan tertutup
    
    public Board(){
        MatrixOfCard = new Path_Card[6][10];
        for (int i=1; i<=5; i++){
            for (int j=1; j<=9; j++){
                MatrixOfCard[i][j] = new Path_Card('0', '0', '0', '0', '0', 0); 
            }
        }
        MatrixOfCard[3][9] = Path_Card.StartCard;    //startcard
        
        Random randomGenerator = new Random();
        int X = randomGenerator.nextInt(3);
        if (X == 1) 
		{MatrixOfCard[1][1] = Path_Card.GoldCard;   //Path_Card.GoldCard
		 MatrixOfCard[3][1] = Path_Card.RockCard;   //Path_Card.RockCard
		 MatrixOfCard[5][1] = Path_Card.RockCard;
		}
	else if (X == 2) 
		{MatrixOfCard[3][1] = Path_Card.GoldCard;
		 MatrixOfCard[5][1] = Path_Card.RockCard;
		 MatrixOfCard[1][1] = Path_Card.RockCard;
		}
	else if (X == 3)
		{MatrixOfCard[5][1] = Path_Card.GoldCard;
		 MatrixOfCard[3][1] = Path_Card.RockCard;
		 MatrixOfCard[1][1] = Path_Card.RockCard;
		}
        GoalCondition = new boolean[3];
        for (int i=0; i<3; i++){
            GoalCondition[i] = true;
        }
    }
    
    public void viewMap(int i){
        switch (i){
            case 1:
                printCard(MatrixOfCard[1][1]); break;
            case 2:
                printCard(MatrixOfCard[3][1]); break;
            case 3:
                printCard(MatrixOfCard[5][1]); break;
        }
    }
    
    private void printCard(Path_Card P){
        if (P.CompareCard(Path_Card.GoldCard)){System.out.println("GoldCard");}
        else if (P.CompareCard(Path_Card.RockCard)){System.out.println("RockCard");}
        else{System.out.println("KampretCard");}
    }
    
    private Path_Card getCardLeftOf(Vector<Integer> Position){
        if (Position.get(1) == 1){
            return new Path_Card('0', '0', '0', '0', '0', 0);     //nilcard
        } else {
            return MatrixOfCard[Position.get(0)][Position.get(1)-1];
        }
    }
    
    private Path_Card getCardRightOf(Vector<Integer> Position){
        if (Position.get(1) == 9){
            return new Path_Card('0', '0', '0', '0', '0', 0);     //nilcard
        } else {
            return MatrixOfCard[Position.get(0)][Position.get(1)+1];
        }
    }
    
    private Path_Card getCardAboveOf(Vector<Integer> Position){
        if (Position.get(0) == 1){
            return new Path_Card('0', '0', '0', '0', '0', 0);     //nilcard
        } else {
            return MatrixOfCard[Position.get(0)-1][Position.get(1)];
        }
    }
    
    private Path_Card getCardBelowOf(Vector<Integer> Position){
        if (Position.get(0) == 5){
            return new Path_Card('0', '0', '0', '0', '0', 0);     //nilcard
        } else {
            return MatrixOfCard[Position.get(0)+1][Position.get(1)];
        }
    }
    
    public boolean validatorPosition(Path_Card P, Vector<Integer> Position){
        boolean valid = false, aboveIsValid, belowIsValid, rightIsValid, leftIsValid;
        
        if (Path_Card.NilCard.CompareCard(MatrixOfCard[Position.get(0)][Position.get(1)])){
            Path_Card CardAbove = getCardAboveOf(Position);
            Path_Card CardBelow = getCardBelowOf(Position);
            Path_Card CardRight = getCardRightOf(Position);
            Path_Card CardLeft = getCardLeftOf(Position);
            
            if (Path_Card.NilCard.CompareCard(CardAbove) && Path_Card.NilCard.CompareCard(CardBelow) && Path_Card.NilCard.CompareCard(CardRight) && Path_Card.NilCard.CompareCard(CardLeft)){
                valid = false;
            } else {
                if ((Position.get(1) == 2) || (Position.get(1) == 1)){
                    if (Path_Card.RockCard.CompareCard(CardLeft) || Path_Card.GoldCard.CompareCard(CardLeft)){
                        if (! Path_Card.NilCard.CompareCard(CardAbove)){
                            aboveIsValid = P.canBePlacedBelowOf(CardAbove);
                        } else {
                            aboveIsValid = true;
                        }
                        
                        if (! Path_Card.NilCard.CompareCard(CardBelow)){
                            belowIsValid = P.canBePlacedAboveOf(CardBelow);
                        } else {
                            belowIsValid = true;
                        }
                        
                        if (! Path_Card.NilCard.CompareCard(CardRight)){
                            rightIsValid = P.canBePlacedLeftOf(CardRight);
                        } else {
                            rightIsValid = true;
                        }
                        
                        valid = aboveIsValid && belowIsValid && rightIsValid;
                    } else if ((Path_Card.RockCard.CompareCard(CardAbove) || Path_Card.GoldCard.CompareCard(CardAbove)) && (Path_Card.RockCard.CompareCard(CardBelow) || Path_Card.GoldCard.CompareCard(CardBelow))){
                        valid = P.canBePlacedLeftOf(CardRight);
                    } else if ((Position.get(1) == 2) && (CardLeft.CompareCard(Path_Card.NilCard))){
                        valid = P.canBePlacedLeftOf(CardRight);
                    }
                } else {
                    if (! Path_Card.NilCard.CompareCard(CardAbove)) {
                        aboveIsValid = P.canBePlacedBelowOf(CardAbove);
                    } else {
                        aboveIsValid = true;
                    }
                    
                    if (! Path_Card.NilCard.CompareCard(CardBelow)){
                        belowIsValid = P.canBePlacedAboveOf(CardBelow);
                    } else {
                        belowIsValid = true;
                    }
                        
                    if (! Path_Card.NilCard.CompareCard(CardRight)){
                        rightIsValid = P.canBePlacedLeftOf(CardRight);
                    } else {
                        rightIsValid = true;
                    }
                    
                    if (! Path_Card.NilCard.CompareCard(CardLeft)) {
                        leftIsValid = P.canBePlacedRightOf(CardLeft);
                    } else {
                        leftIsValid = true;
                    }
                    
                    valid = aboveIsValid && belowIsValid && rightIsValid && leftIsValid;
                }
            }
        } else {
            valid = false;
        }
        return valid;
    }
    
    public int putCardOnBoard(Path_Card P, Vector<Integer> Position){
        
        if (this.validatorPosition(P,Position)){
            MatrixOfCard[Position.get(0)][Position.get(1)] = P;
            return 0;
        }else{
            System.out.println("Koordinat tidak valid ");
            return -1;
        }
    }
    
    public void PrintBoard(){
	//Kamus Lokal
	int i,j;
	String lineu = "\u2594";
	String linel = "\u258F";
	String liner = "\u2595";
	String lined = "\u2581";
	String block = "\u2588";
	String goal = "\u0047";
	String rock = "\u25CE";
	String gold = "\u2606";
	
	//Algoritma
	System.out.println();
        System.out.println();
		
	for(i=1;i<=5;i++){	  
		if (i==1) {
			System.out.println("      1      2      3      4      5      6      7      8      9");
                        System.out.println();
                        System.out.print("  ");
                        if(GoalCondition[0]){
                            System.out.print(liner + lineu + lineu + lineu + lineu + lineu + lineu);
                        } else {
                            System.out.print(liner + lineu + lineu + block + block + lineu + lineu);
                        }
                        for (j=2;j<=9;j++){
                            System.out.print(liner + lineu + lineu); 
                            if (MatrixOfCard[i][j].getTop() == '1') {
                                System.out.print(block + block);
                            } else {
                                System.out.print(lineu + lineu);
                            } 
                            System.out.print(lineu + lineu);
                        } 
                        System.out.println(linel); 
			if(GoalCondition[0]){
                            System.out.print("  " + liner + "      ");
                        } else {
                            System.out.print("  "+ liner + "  " + block + block + "   ");
                        } 
                        for (j=2;j<=9;j++) {
                            System.out.print(liner + "  ");
                            if (MatrixOfCard[i][j].getTop() == '1') {
                                System.out.print(block + block);
                            } else {
                                System.out.print("  ");
                            } 
                            System.out.print("  ");
                        } 
                        System.out.println(linel); 
			if(GoalCondition[0]){
                            System.out.print(" 1" + liner + "   " + goal + "  ");
                        } else {
                            if(MatrixOfCard[1][1].getCenter() == 'R'){
                                System.out.print(" 1" + liner + block + block + rock + " " + block + block + "");
                            } else {
                                System.out.print(" 1" + liner + block + block + gold + " " + block + block + "");
                            }
                        }
                        for (j=2;j<=9;j++) {
                            if(MatrixOfCard[i][j].getLeft() == '1'){
                                if(MatrixOfCard[i][j].getCenter() == '1'){
                                    if (MatrixOfCard[i][j].getRight() == '1'){
                                        System.out.print(liner + block + block + block + block + block + block);
                                    } else {
                                        System.out.print(liner + block + block + block + block + "  ");
                                    }
                                } else {
                                    if (MatrixOfCard[i][j].getRight() == '1'){
                                        System.out.print(liner + block + block + "  " + block + block);
                                    } else {
                                        System.out.print(liner + block + block + "    ");
                                    }
                                }
                            } else {
                                if(MatrixOfCard[i][j].getCenter() == '1'){
                                    if (MatrixOfCard[i][j].getRight() == '1'){
                                        System.out.print(liner + "  " + block + block + block + block);
                                    } else {
                                        System.out.print(liner + "  " + block + block +"  ");
                                    }
                                } else {
                                    if (MatrixOfCard[i][j].getRight() == '1') {
                                        System.out.print(liner + "    " + block + block);
                                    } else {
                                        System.out.print(liner + "      ");
                                    }
                                }
                            }
                        }
                        System.out.println(linel);
			if(GoalCondition[0]){
                            System.out.print("  " + liner + "      ");
                        } else {
                            System.out.print("  " + liner + "  "+ block + block + "  ");
                        }
                        for (j=2;j<=9;j++) {
                            System.out.print(liner + "  "); 
                            if (MatrixOfCard[i][j].getBottom() == '1') {
                                System.out.print(block + block);
                            } else {
                                System.out.print("  ");
                            } 
                            System.out.print("  ");
                        }
                        System.out.println(linel); 
			if(GoalCondition[0]){
                            System.out.print("  " + liner + lined + lined + lined + lined + lined + lined);
                        } else {
                            System.out.print("  " + liner + lined + lined + block + block + lined + lined);
                        } 
                        
                        for (j=2;j<=9;j++) {
                            System.out.print(liner + lined + lined);
                            if (MatrixOfCard[i][j].getBottom() == '1') {
                                System.out.print(block + block);
                            } else {
                                System.out.print(lined + lined);
                            }
                            System.out.print(lined + lined);
                        } 
                        System.out.println(linel);
			} else if (i==3) {
				if(GoalCondition[1]){
                                    System.out.print("  " + liner + "      ");
                                } else {
                                    System.out.print("  " + liner + "  "+ block + block + "  ");
                                } 
                                for (j=2;j<=8;j++) {
                                    System.out.print(liner + "  ");
                                    if (MatrixOfCard[i][j].getTop() == '1') {
                                        System.out.print(block + block);
                                    } else {
                                        System.out.print("  ");
                                    } 
                                    System.out.print("  ");
                                } 
                                System.out.print(liner + "  " + block + block + "  ");
                                System.out.println(linel); 
				if(GoalCondition[1]){
                                    System.out.print("  " + liner + "      ");
                                } else {
                                    System.out.print("  " + liner + "  " + block + block + "  ");
                                }
                                for (j=2;j<=8;j++) {
                                    System.out.print(liner + "  ");
                                    if (MatrixOfCard[i][j].getTop() == '1') {
                                        System.out.print(block + block);
                                    } else {
                                        System.out.print("  ");
                                    } 
                                    System.out.print("  ");
                                } 
                                System.out.print(liner + "  " + block + block + "  ");
                                System.out.println(linel); 
				if(GoalCondition[1]){
                                    System.out.print(" 3" + liner + "   " + goal + "  ");
                                } else {
                                    if(MatrixOfCard[3][1].getCenter() == 'R'){
                                        System.out.print(" 3"+ liner + block + block + rock + " " + block + block);
                                    } else {
                                        System.out.print("  "+ liner + block + block + gold + " " + block + block);
                                    }
                                }
                                for (j=2;j<=8;j++) {
                                    if(MatrixOfCard[i][j].getLeft() == '1'){
                                        if(MatrixOfCard[i][j].getCenter() == '1'){
                                            if (MatrixOfCard[i][j].getRight() == '1'){
                                                System.out.print(liner + block + block + block + block + block + block);
                                            } else {
                                                System.out.print(liner + block + block + block + block + "  ");
                                            }
                                        } else {
                                            if (MatrixOfCard[i][j].getRight() == '1'){
                                                System.out.print(liner + block + block + "  " + block + block);
                                            } else {
                                                System.out.print(liner + block + block + "    ");
                                            }
                                        }
                                    } else {
                                        if(MatrixOfCard[i][j].getCenter( )== '1') {
                                            if (MatrixOfCard[i][j].getRight() == '1'){
                                                System.out.print(liner + "  " + liner + block + block + block + block);
                                            } else {
                                                System.out.print(liner + "  " + block + block + "  ");
                                            }
                                        } else {
                                            if (MatrixOfCard[i][j].getRight() == '1') {
                                                System.out.print(liner + "    " + block + block); 
                                            } else {
                                                System.out.print(liner + "      ");
                                            }
                                        }
                                    }
                                }
                                System.out.print(liner + block + block + block + block + block + block);
                                System.out.println(linel);
				if(GoalCondition[1]){
                                    System.out.print("  " + liner + "      ");
                                } else {
                                    System.out.print("  " + liner + "  " + block + block + "  ");
                                } 
                                for (j=2;j<=8;j++) {
                                    System.out.print(liner + "  ");
                                    if (MatrixOfCard[i][j].getBottom() == '1') {
                                        System.out.print(block + block);
                                    } else {
                                        System.out.print("  ");
                                    }
                                    System.out.print("  ");
                                }
                                System.out.print(liner + "  " + block + block + "  ");
                                System.out.println(linel); 
				if(GoalCondition[1]){
                                    System.out.print("  " + liner + lined + lined + lined + lined + lined + lined);
                                } else {
                                    System.out.print("  " + liner + lined + lined + block + block + lined + lined);
                                }
                                for (j=2;j<=8;j++) {
                                    System.out.print(liner + lined + lined); 
                                    if (MatrixOfCard[i][j].getBottom() == '1') {
                                        System.out.print(block + block);
                                    } else {
                                        System.out.print(lined + lined);
                                    } 
                                    System.out.print(lined + lined);
                                }
                                System.out.print(liner + lined + lined + block + block + lined + lined);
                                System.out.println(linel);
				} else if (i==5) {
                                    if(GoalCondition[2]){
                                        System.out.print("  " + liner + "      ");
                                    } else {
                                        System.out.print("  " + liner + "  " + block + block + "  ");
                                    }
                                    for (j=2;j<=9;j++) {
                                        System.out.print(liner + "  ");
                                        if (MatrixOfCard[i][j].getTop() == '1') {
                                            System.out.print(block + block);
                                        } else {
                                            System.out.print("  ");
                                        }
                                        System.out.print("  ");
                                    }
                                    System.out.println(linel); 
                                    if(GoalCondition[2]){
                                        System.out.print("  " + liner + "      ");
                                    } else {
                                        System.out.print("  " + liner + "  " + block + block + "  ");
                                    }
                                    for (j=2;j<=9;j++) {
                                        System.out.print(liner + "  ");
                                        if (MatrixOfCard[i][j].getTop() == '1') {
                                            System.out.print(block + block);
                                        } else {
                                            System.out.print("  ");
                                        }
                                        System.out.print("  ");
                                    }
                                    System.out.println(linel); 
                                    if(GoalCondition[2]){
                                        System.out.print(" 5" + liner + "   " + goal + "  ");
                                    } else {
                                        if(MatrixOfCard[5][1].getCenter() == 'R'){
                                            System.out.print(" 5" + liner + block + block + rock + " " + block + block);
                                        } else {
                                            System.out.print(" 5" + liner + block + block + gold + " " + block + block);
                                        }
                                    }
                                    for (j=2;j<=9;j++) {
                                        if(MatrixOfCard[i][j].getLeft() == '1'){
                                            if(MatrixOfCard[i][j].getCenter() == '1'){
                                                if (MatrixOfCard[i][j].getRight() == '1'){
                                                    System.out.print(liner + block + block + block + block + block + block);
                                                } else {
                                                    System.out.print(liner + block + block + block + block + "  ");
                                                }
                                            } else {
                                                if (MatrixOfCard[i][j].getRight() == '1'){
                                                    System.out.print(liner + block + block + "  " + block + block);
                                                } else {
                                                    System.out.print(liner + block + block + "    ");
                                                }
                                            }
                                        } else {
                                            if(MatrixOfCard[i][j].getCenter() == '1') {
                                                if (MatrixOfCard[i][j].getRight() == '1'){
                                                    System.out.print(liner + "  " + liner + block + block + block + block);
                                                } else {
                                                    System.out.print(liner + "  " + block + block + "  ");
                                                }
                                            } else {
                                                if (MatrixOfCard[i][j].getRight() == '1') {
                                                    System.out.print(liner + "    " + block + block);
                                                } else {
                                                    System.out.print(liner + "      ");
                                                }
                                            }
                                        }
                                    }
                                    System.out.println(linel);
                                    if(GoalCondition[2]){
                                        System.out.print("  " + liner + "      ");
                                    } else {
                                        System.out.print("  " + liner + "  " + block + block + "  ");
                                    } 
                                    for (j=2;j<=9;j++) {
                                        System.out.print(""+ liner + "  ");
                                        if (MatrixOfCard[i][j].getBottom() == '1') {
                                            System.out.print(block + block);
                                        } else {
                                            System.out.print("  ");
                                        }
                                        System.out.print("  ");
                                    }
                                    System.out.println(linel); 
                                    if(GoalCondition[2]){
                                        System.out.print("  " + liner + lined + lined + lined + lined + lined + lined);
                                    } else {
                                        System.out.print("  " + liner + lined + lined + block + block + lined + lined);
                                    }
                                    for (j=2;j<=9;j++) {
                                        System.out.print(liner + lined + lined); 
                                        if (MatrixOfCard[i][j].getBottom() == '1') {
                                            System.out.print(block + block);
                                        } else {
                                            System.out.print(lined + lined);
                                        }
                                        System.out.print(lined + lined);
                                    }
                                    System.out.println(linel);
				} else if (i==2){
                                    System.out.print("  ");
                                    for (j=1;j<=9;j++) {
                                        System.out.print(liner + "  "); 
                                        if (MatrixOfCard[i][j].getTop() == '1') {
                                            System.out.print(block + block);
                                        } else {
                                            System.out.print("  ");
                                        }
                                        System.out.print("  ");
                                    } 
                                    System.out.println(linel); 
                                    System.out.print("  ");
                                    for (j=1;j<=9;j++) {
                                        System.out.print(liner + "  "); 
                                        if (MatrixOfCard[i][j].getTop() == '1') {
                                            System.out.print(block + block);
                                        } else {
                                            System.out.print("  ");
                                        }
                                        System.out.print("  ");
                                    }
                                    System.out.println(linel); 
                                    System.out.print(" 2");
                                    for (j=1;j<=9;j++) {
                                        if(MatrixOfCard[i][j].getLeft() == '1'){
                                            if(MatrixOfCard[i][j].getCenter() == '1'){
                                                if (MatrixOfCard[i][j].getRight() == '1'){
                                                    System.out.print(liner + block + block + block + block + block + block);
                                                } else {
                                                    System.out.print(liner + block + block + block + block + "  ");
                                                }
                                            } else {
                                                if (MatrixOfCard[i][j].getRight() == '1'){
                                                    System.out.print(liner + block + block + "  " + block + block);
                                                } else {
                                                    System.out.print(liner + block + block + "    ");
                                                }
                                            }
                                        } else {
                                            if(MatrixOfCard[i][j].getCenter() == '1') {
                                                if (MatrixOfCard[i][j].getRight() == '1'){
                                                    System.out.print(liner + "  " + block + block + block + block);
                                                } else {
                                                    System.out.print(liner + "  " + block + block);
                                                }
                                            } else {
                                                if (MatrixOfCard[i][j].getRight() == '1') {
                                                    System.out.print(liner + "    " + block + block);
                                                } else {
                                                    System.out.print(liner + "      ");
                                                }
                                            }
                                        }
                                    }
                                    System.out.println(linel);
                                    System.out.print("  ");
                                    for (j=1;j<=9;j++) {
                                        System.out.print(liner + "  ");
                                        if (MatrixOfCard[i][j].getBottom() == '1') {
                                            System.out.print(block + block);
                                        } else {
                                            System.out.print("  ");
                                        }
                                        System.out.print("  ");
                                    }
                                    System.out.println(linel); 
                                    System.out.print("  ");
                                    for (j=1;j<=9;j++) {
                                        System.out.print(liner + lined + lined); 
                                        if (MatrixOfCard[i][j].getBottom() == '1') {
                                            System.out.print(block + block);
                                        } else {
                                            System.out.print(lined + lined);
                                        }
                                        System.out.print(lined + lined);
                                    }
                                    System.out.println(linel);

				}
				else if (i==4){
					System.out.print("  ");
                                        for (j=1;j<=9;j++) {
                                            System.out.print(liner + "  ");
                                            if (MatrixOfCard[i][j].getTop() == '1') {
                                                System.out.print(block + block);
                                            } else {
                                                System.out.print("  ");
                                            } 
                                            System.out.print("  ");
                                        } System.out.println(linel); 
					System.out.print("  ");
                                        for (j=1;j<=9;j++) {
                                            System.out.print(liner + "  ");
                                            if (MatrixOfCard[i][j].getTop() == '1') {
                                                System.out.print(block + block);
                                            } else {
                                                System.out.print("  ");
                                            }
                                            System.out.print("  ");
                                        }
                                        System.out.println(linel); 
					System.out.print(" 4");
                                        for (j=1;j<=9;j++) {
                                            if(MatrixOfCard[i][j].getLeft() == '1'){
                                                if(MatrixOfCard[i][j].getCenter() == '1'){
                                                    if (MatrixOfCard[i][j].getRight() == '1'){
                                                        System.out.print(liner + block + block + block + block + block + block);
                                                    } else {
                                                        System.out.print(liner + block + block + block + block + "  ");
                                                    }
                                                } else {
                                                    if (MatrixOfCard[i][j].getRight() == '1'){
                                                        System.out.print(liner + block + block + "  " + block + block);
                                                    } else {
                                                        System.out.print(liner + block + block + "    ");
                                                    }
                                                }
                                            } else {
                                                if(MatrixOfCard[i][j].getCenter() == '1') {
                                                    if (MatrixOfCard[i][j].getRight() == '1'){
                                                        System.out.print(liner + "  " + block + block + block + block);
                                                    } else {
                                                        System.out.print(liner + "  " + block + block);
                                                    }
                                                } else {
                                                    if (MatrixOfCard[i][j].getRight() == '1') {
                                                        System.out.print(liner + "    " + block + block);
                                                    } else {
                                                        System.out.print(liner + "      ");
                                                    }
                                                }
                                            }
                                        }
                                        System.out.println(linel);
					System.out.print("  ");
                                        for (j=1;j<=9;j++) {
                                            System.out.print(liner + "  ");
                                            if (MatrixOfCard[i][j].getBottom() == '1') {
                                                System.out.print(block + block);
                                            } else {
                                                System.out.print("  ");
                                            }
                                            System.out.print("  ");
                                        }
                                        System.out.println(linel); 
					System.out.print("  ");
                                        for (j=1;j<=9;j++) {
                                            System.out.print(liner + lined + lined); 
                                            if (MatrixOfCard[i][j].getBottom() == '1') {
                                                System.out.print(block + block);
                                            } else {
                                                System.out.print(lined + lined);
                                            }
                                            System.out.print(lined + lined);
                                        }
                                        System.out.println(linel);
				}
		}
}

    public void openGoal(){
    /* I.S. G dalam keadaan tertutup.
    *  F.S. Mengecek apakah pemain berhasil meletakkan PathCard di sebelah GoalCard yang berisi RockCard pada Board B.
    *      Jika berhasil, maka G dalam keadaan terbuka.
    *      Jika tidak, maka G tetap dalam keadaan tertutup. */
	
	boolean[][] check = new boolean[6][10];
	int i, j;
	Path_Card P = new Path_Card('0', '0', '0', '0', '0', 0);
	
	for (i=1;i<=5;i++) {
		for (j=1;j<=9;j++) {
			check[i][j]=false;
		}
	}
        Vector<Integer> position1 = new Vector<>(); position1.add(0, 1); position1.add(1, 1);
        Vector<Integer> position2 = new Vector<>(); position2.add(0, 3); position2.add(1, 1);
        Vector<Integer> position3 = new Vector<>(); position3.add(0, 5); position3.add(1, 1);
	if (! Path_Card.GoldCard.CompareCard(MatrixOfCard[1][1]))	
	{this.openGoalRecc(position1, check, P); if (Path_Card.StartCard.CompareCard(P)) {GoalCondition[0] = false;}}		//jika terdapat lintasan dari GoalCard sampai StartCard, maka GoalCard dalam keadaan terbuka
	if (! Path_Card.GoldCard.CompareCard(MatrixOfCard[3][1]))	
	{this.openGoalRecc(position3, check, P); if (Path_Card.StartCard.CompareCard(P)) {GoalCondition[1] = false;}}
	if (! Path_Card.GoldCard.CompareCard(MatrixOfCard[5][1]))	
        {this.openGoalRecc(position3, check, P); if (Path_Card.StartCard.CompareCard(P)) {GoalCondition[2] = false;}}

    }
    
    public boolean isFinished(){
    /* I.S. B terdefinisi
    * F.S. Mengembalikan nilai true jika kondisi akhir permainan tercapai, yaitu saat pemain 
            berhasil meletakkan PathCard di sebelah GoalCard yang merupakan GoldCard. */
	
	boolean[][] check = new boolean[6][10];
	boolean berakhir;
        int i, j;
	Path_Card P = new Path_Card('0', '0', '0', '0', '0', 0);

	for (i=1;i<=5;i++) {
		for (j=1;j<=9;j++) {
			check[i][j]=false;
		}
	}
	Vector<Integer> position1 = new Vector<>(); position1.add(0, 1); position1.add(1, 1);
        Vector<Integer> position2 = new Vector<>(); position2.add(0, 3); position2.add(1, 1);
        Vector<Integer> position3 = new Vector<>(); position3.add(0, 5); position3.add(1, 1);
	berakhir = false;
	if (Path_Card.GoldCard.CompareCard(MatrixOfCard[1][1]))
	{this.openGoalRecc(position1, check, P); if (Path_Card.StartCard.CompareCard(P)) {berakhir = true; GoalCondition[1] = false;}}
	else if (Path_Card.GoldCard.CompareCard(MatrixOfCard[3][1]))
	{this.openGoalRecc(position2, check, P); if (Path_Card.StartCard.CompareCard(P)) {berakhir = true; GoalCondition[2] = false;}}
	else if (Path_Card.GoldCard.CompareCard(MatrixOfCard[5][1]))
	{this.openGoalRecc(position3, check, P); if (Path_Card.StartCard.CompareCard(P)) {berakhir = true; GoalCondition[3] = false;}}
        return berakhir;
    }
    
    public void openGoalRecc(Vector<Integer> Position, boolean[][] cek, Path_Card P){
    /* Proses: Prosedur rekursif 4 arah untuk mengecek apakah ada jalan yang terbentuk dari GoalCard ke Path_Card.StartCard.
    * F.S: P akan berisi Path_Card.StartCard jika prosedur ini berhasil mencapai Path_Card.StartCard.
    *      P akan berisi Path_Card.NilCard jika prosedur ini berhenti pada kotak kosong.
    *      P akan berisi PathCard yang lain jika prosedur ini berhenti di PathCard yang merupakan kartu buntu */ 
        cek[Position.get(0)][Position.get(1)] = true;		//sudah dicek
	if (((Position.get(0) == 3) && (Position.get(1) == 9)) || (Path_Card.NilCard.CompareCard(MatrixOfCard[Position.get(0)][Position.get(1)])) || ((MatrixOfCard[Position.get(0)][Position.get(1)]).getCenter() == '0'))
	{
		P = MatrixOfCard[Position.get(0)][Position.get(1)];
	}
	else
	{
		if (((MatrixOfCard[Position.get(0)][Position.get(1)]).getTop() == '1') && (Position.get(0) > 1) && (! Path_Card.NilCard.CompareCard(this.getCardAboveOf(Position))) && (! cek[Position.get(0)-1][Position.get(1)])) 
		{this.openGoalRecc(Position, cek, P);} 
		if ((! (Path_Card.StartCard.CompareCard(P))) && ((MatrixOfCard[Position.get(0)][Position.get(1)]).getRight() == '1') && (Position.get(1) < 9) && (! Path_Card.NilCard.CompareCard(this.getCardRightOf(Position))) && (! cek[Position.get(0)][Position.get(1)+1])) 
		{this.openGoalRecc(Position, cek, P);}
		if ((! (Path_Card.StartCard.CompareCard(P))) && ((MatrixOfCard[Position.get(0)][Position.get(1)]).getBottom() == '1') && (Position.get(0) < 5) && (! Path_Card.NilCard.CompareCard(this.getCardBelowOf(Position))) && (! cek[Position.get(0) +1][Position.get(1)])) 
		{this.openGoalRecc(Position, cek, P);}
		if ((! (Path_Card.StartCard.CompareCard(P))) && ((MatrixOfCard[Position.get(0)][Position.get(1)]).getLeft() == '1') && (Position.get(1) > 1) && (! Path_Card.NilCard.CompareCard(this.getCardLeftOf(Position))) && (! cek[Position.get(0)][Position.get(1)-1])) 
		{this.openGoalRecc(Position, cek, P);}
	}
    }
}
