
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
    private Path_Card[][] MatrixOfCard;
    private boolean[] GoalCondition;
    
    public Board(){
        MatrixOfCard = new Path_Card[6][10];
        for (int i=1; i<=5; i++){
            for (int j=1; j<=9; j++){
                MatrixOfCard[i][j] = new Path_Card('0', '0', '0', '0', '0', 0);     //nilcard
            }
        }
        MatrixOfCard[1][3] = new Path_Card('1', '1', '1', '1', '1', 17);    //startcard
        
        Random randomGenerator = new Random();
        int X = randomGenerator.nextInt(3);
        if (X == 1) 
		{MatrixOfCard[1][1] = new Path_Card('1', '1', '1', '1', 'G', 18);   //GoldCard
		 MatrixOfCard[3][1] = new Path_Card('1', '1', '1', '1', 'R', 19);   //RockCard
		 MatrixOfCard[5][1] = new Path_Card('1', '1', '1', '1', 'R', 19);
		}
	else if (X == 2) 
		{MatrixOfCard[3][1] = new Path_Card('1', '1', '1', '1', 'G', 18);
		 MatrixOfCard[5][1] = new Path_Card('1', '1', '1', '1', 'R', 19);
		 MatrixOfCard[1][1] = new Path_Card('1', '1', '1', '1', 'R', 19);
		}
	else if (X == 3)
		{MatrixOfCard[5][1] = new Path_Card('1', '1', '1', '1', 'G', 18);
		 MatrixOfCard[3][1] = new Path_Card('1', '1', '1', '1', 'R', 19);
		 MatrixOfCard[1][1] = new Path_Card('1', '1', '1', '1', 'R', 19);
		}
        GoalCondition = new boolean[3];
        for (int i=0; i<3; i++){
            GoalCondition[i] = true;
        }
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
        
        Path_Card NilCard = new Path_Card('0', '0', '0', '0', '0', 0);
        Path_Card RockCard = new Path_Card('1', '1', '1', '1', 'R', 19);
        Path_Card GoldCard = new Path_Card('1', '1', '1', '1', 'G', 18);
        
        if (NilCard.CompareCard(MatrixOfCard[Position.get(0)][Position.get(1)])){
            Path_Card CardAbove = getCardAboveOf(Position);
            Path_Card CardBelow = getCardBelowOf(Position);
            Path_Card CardRight = getCardRightOf(Position);
            Path_Card CardLeft = getCardLeftOf(Position);
            
            if (NilCard.CompareCard(CardAbove) && NilCard.CompareCard(CardBelow) && NilCard.CompareCard(CardRight) && NilCard.CompareCard(CardLeft)){
                valid = false;
            } else {
                if ((Position.get(1) == 2) || (Position.get(1) == 1)){
                    if (RockCard.CompareCard(CardLeft) || GoldCard.CompareCard(CardLeft)){
                        if (! NilCard.CompareCard(CardAbove)){
                            aboveIsValid = P.canBePlacedBelowOf(CardAbove);
                        } else {
                            aboveIsValid = true;
                        }
                        
                        if (! NilCard.CompareCard(CardBelow)){
                            belowIsValid = P.canBePlacedAboveOf(CardBelow);
                        } else {
                            belowIsValid = true;
                        }
                        
                        if (! NilCard.CompareCard(CardRight)){
                            rightIsValid = P.canBePlacedLeftOf(CardRight);
                        } else {
                            rightIsValid = true;
                        }
                        
                        valid = aboveIsValid && belowIsValid && rightIsValid;
                    } else if ((RockCard.CompareCard(CardAbove) || GoldCard.CompareCard(CardAbove)) && (RockCard.CompareCard(CardBelow) || GoldCard.CompareCard(CardBelow))){
                        valid = P.canBePlacedLeftOf(CardRight);
                    }
                } else {
                    if (! NilCard.CompareCard(CardAbove)) {
                        aboveIsValid = P.canBePlacedBelowOf(CardAbove);
                    } else {
                        aboveIsValid = true;
                    }
                    
                    if (! NilCard.CompareCard(CardBelow)){
                        belowIsValid = P.canBePlacedAboveOf(CardBelow);
                    } else {
                        belowIsValid = true;
                    }
                        
                    if (! NilCard.CompareCard(CardRight)){
                        rightIsValid = P.canBePlacedLeftOf(CardRight);
                    } else {
                        rightIsValid = true;
                    }
                    
                    if (! NilCard.CompareCard(CardLeft)) {
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
    
    public void putCardOnBoard(Path_Card P, Vector<Integer> Position){
        MatrixOfCard[Position.get(0)][Position.get(1)] = P;
    }
    
    public void PrintBoard (){
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

}
