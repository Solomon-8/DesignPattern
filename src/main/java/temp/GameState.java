package temp;

import behavioral.chain.template.Boss;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

// This class (not yet fully implemented) will give access to the current state of the game.
public final class GameState {
    public static final int ROWS = 6;
    public static final int COLUMNS = 10;
    public static final AtomicReference<Boolean> ROOM_STATUS = new AtomicReference<>(false);
    public static String ROOM_OWNER_NAME = "null";
    public static final AtomicInteger PLAYER_NUMBER = new AtomicInteger(0);
    public static final AtomicInteger CUR_PLAYER_NUMBER = new AtomicInteger(0);
    public static final AtomicInteger CUR_PLAYER_ID = new AtomicInteger(1);
    public static final int[][] BOARD  = new int[ROWS][COLUMNS];
    public static final List<Player> PLAYERS = new ArrayList<>();

    // Returns a rectangular matrix of board cells, with six rows and ten columns.
    // Zeros indicate empty cells.
    // Non-zero values indicate stones of the corresponding player.  E.g., 3 means a stone of the third player.
    public int[][] getBoard() {
        return BOARD;
    }

    // Returns the set of influence cards available to the given player.
    public Set<InfluenceCard> getAvailableInfluenceCards(int player) {
        return new HashSet<>(PLAYERS.get(player-1).getCards());
    }

    // Checks if the specified move is allowed for the given player.
    public boolean isMoveAllowed(Move move, int player) {
        if (move.getFirstMove().getX() >=6 || move.getFirstMove().getX() < 0 || move.getFirstMove().getY() >= 10 || move.getFirstMove().getY() < 0){
            return false;
        }
        if (move.getSecondMove() != null && (move.getSecondMove().getX() >= 6 || move.getSecondMove().getX() < 0 || move.getSecondMove().getY() >= 10 || move.getSecondMove().getY() < 0)){
            return false;
        }
        if (move.getCard() != null){
            if (!PLAYERS.get(player).getCards().contains(move.getCard())){
                return false;
            } else {
                if (move.getCard() == InfluenceCard.DOUBLE){
                    Coordinates first = move.getFirstMove();
                    Coordinates second = move.getSecondMove();
                    boolean result = isAvailable(player,first.getX(),first.getY()) && isAvailable(player,second.getX(),second.getY());
                    if (result){
                        System.out.println(PLAYERS.get(player).getCards());
                        PLAYERS.get(player).getCards().remove(InfluenceCard.DOUBLE);
                        System.out.println(PLAYERS.get(player).getCards());
                    }
                    return result;
                }
                if (move.getCard() == InfluenceCard.REPLACEMENT){
                    Coordinates replace = move.getFirstMove();
                    boolean result = isAvailable(player,replace.getX(),replace.getY());
                    if (result){
                        PLAYERS.get(player).getCards().remove(InfluenceCard.REPLACEMENT);
                    }
                    return result;
                }
                if (move.getCard() == InfluenceCard.FREEDOM){
                    Coordinates freedom = move.getFirstMove();
                    boolean result = BOARD[freedom.getX()][freedom.getY()] == 0;
                    if (result){
                        PLAYERS.get(player).getCards().remove(InfluenceCard.FREEDOM);
                    }
                    return result;
                }
            }

        }
        boolean flag = false;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (BOARD[i][j] == player){
                    flag = true;
                }
            }
        }
        if (flag){
            Coordinates coordinates = move.getFirstMove();
            System.out.println(coordinates);
            if (BOARD[coordinates.getX()][coordinates.getY()] != 0){
                return false;
            }
            return isAvailable(player, coordinates.getX(), coordinates.getY());
        } else if (BOARD[move.getFirstMove().getX()][move.getFirstMove().getY()] == 0){
            return true;
        } else {
            return false;
        }
    }


    public static int getPlayerNumber(){
        return CUR_PLAYER_NUMBER.get();
    }

    public boolean isGameEnd(){
        boolean target = true;
        int cnt = 1;
        for (int i = 1; i <= PLAYER_NUMBER.get(); i++) {
            if (isAlive(i)){
                if (target){
                    target = false;
                }
                cnt++;
            }
        }
        if (cnt == PLAYER_NUMBER.get()){
            return true;
        }
        return target;
    }

    public boolean isAlive(int player){
        boolean flag = false;
        boolean result = false;
        for (int j = 0; j < ROWS; j++) {
            for (int k = 0; k < COLUMNS; k++) {
                if (BOARD[j][k] == player){
                    flag = true;
                }
            }
        }
        if (!flag){
            return true;
        } else {
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    if (BOARD[i][j] == player){
                        result = isAvailable(0,i,j);
                        if (result){
                            return result;
                        }
                    }
                }
            }
        }
        return result;
    }

//    public static void main(String[] args) {
//        BOARD = new int[][]{{1,2,0,0,0,0,0,0,0,0},{1,2,0,0,0,0,0,0,0,0},{1,2,0,0,0,0,0,0,0,0},{1,2,0,0,0,0,0,0,0,0},{1,2,0,0,0,0,0,0,0,0},{1,2,0,0,0,0,0,0,0,0}};
//        System.out.println(isAlive(1));
//    }

    public boolean isAvailable(int player,int x,int y){
        boolean flag;
        if (x - 1 < 0){
            flag = BOARD[0][y] == player;
            if (flag){
                return flag;
            }
        } else {
            flag = BOARD[x-1][y] == player;
            if (flag){
                return flag;
            }
        }
        if (y - 1 < 0){
            flag = BOARD[x][0] == player;
            if (flag){
                return flag;
            }
        } else {
            flag = BOARD[x][y-1] == player;
            if (flag){
                return flag;
            }
        }
        if (x + 1 >= ROWS){
            flag = BOARD[ROWS-1][y] == player;
            if (flag){
                return flag;
            }
        } else {
            flag = BOARD[x+1][y] == player;
            if (flag){
                return flag;
            }
        }
        if (y + 1 >= COLUMNS){
            flag = BOARD[x][COLUMNS-1] == player;
            if (flag){
                return flag;
            }
        } else {
            flag = BOARD[x][y+1] == player;
            if (flag){
                return flag;
            }
        }

        if (x - 1 < 0 && y - 1 < 0){
            flag = BOARD[0][0] == player;
            if (flag){
                return flag;
            }
        } else {
            if (x -1 < 0 && y - 1 >= 0){
                flag = BOARD[0][y-1] == player;
                if (flag){
                    return flag;
                }
            } else if (x - 1 >= 0 && y - 1 < 0){
                flag = BOARD[x-1][0] == player;
                if (flag){
                    return flag;
                }
            } else {
                flag = BOARD[x-1][y-1] == player;
                if (flag){
                    return flag;
                }
            }
        }
        if (x - 1 < 0 && y + 1 >= COLUMNS){
            flag = BOARD[0][COLUMNS-1] == player;
            if (flag){
                return flag;
            }
        } else {
            if (x - 1 < 0 && y + 1 < COLUMNS){
                flag = BOARD[0][y+1] == player;
                if (flag){
                    return flag;
                }
            } else if (x - 1 >= 0 && y + 1 >= COLUMNS){
                flag = BOARD[x-1][COLUMNS-1] == player;
                if (flag){
                    return flag;
                }
            } else {
                flag = BOARD[x-1][y+1] == player;
                if (flag){
                    return flag;
                }
            }
        }

        if (x + 1 >= ROWS && y + 1 >= COLUMNS){
            flag = BOARD[ROWS-1][COLUMNS-1] == player;
            if (flag){
                return flag;
            }
        } else {
            if (x + 1 >= ROWS && y + 1 < COLUMNS){
                flag = BOARD[ROWS-1][y+1] == player;
                if (flag){
                    return flag;
                }
            } else if (x + 1 < ROWS && y + 1 >= COLUMNS){
                flag = BOARD[x+1][COLUMNS - 1] == player;
                if (flag){
                    return flag;
                }
            } else {
                flag = BOARD[x+1][ y+1] == player;
                if (flag){
                    return flag;
                }
            }
        }

        if (x + 1 >= ROWS && y - 1 < 0){
            flag = BOARD[ROWS-1][0] == player;
            if (flag){
                return flag;
            }
        } else {
            if (x+1 >= ROWS && y - 1 >= 0){
                flag = BOARD[ROWS-1][y-1] == player;
                if (flag){
                    return flag;
                }
            } else if (x+1 < ROWS && y - 1 < 0){
                flag = BOARD[x+1][0] == player;
                if (flag){
                    return flag;
                }
            } else {
                flag = BOARD[x+1][y-1] == player;
                if (flag){
                    return flag;
                }
            }
        }
        return flag;
    }

    public int getWinner(){
        int winner = 1;
        int max = 0;
        for (int i = 1; i < PLAYER_NUMBER.get(); i++) {
            int curMax = 0;
            for (int j = 0; j < ROWS; j++) {
                for (int k = 0; k < COLUMNS; k++) {
                    if (BOARD[j][k] == i){
                        curMax++;
                        if (curMax > max){
                            max = curMax;
                            winner = i;
                        }
                    }
                }
            }
        }
        return winner;
    }

    public int getCurPlayerId(){
        return CUR_PLAYER_ID.get();
    }
}

