package temp;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Solomon
 * @date 2018/11/10
 * if you founded any bugs in my code
 * look at my face
 * that's a feature
 * ─ wow ──▌▒█───────────▄▀▒▌───
 * ────────▌▒▒▀▄───────▄▀▒▒▒▐───
 * ───────▐▄▀▒▒▀▀▀▀▄▄▄▀▒▒▒▒▒▐───
 * ─────▄▄▀▒▒▒▒▒▒▒▒▒▒▒█▒▒▄█▒▐───
 * ───▄▀▒▒▒▒▒▒ such difference ─
 * ──▐▒▒▒▄▄▄▒▒▒▒▒▒▒▒▒▒▒▒▒▀▄▒▒▌──
 * ──▌▒▒▐▄█▀▒▒▒▒▄▀█▄▒▒▒▒▒▒▒█▒▐──
 * ─▐▒▒▒▒▒▒▒▒▒▒▒▌██▀▒▒▒▒▒▒▒▒▀▄▌─
 * ─▌▒▀▄██▄▒▒▒▒▒▒▒▒▒▒▒░░░░▒▒▒▒▌─
 * ─▌▀▐▄█▄█▌▄▒▀▒▒▒▒▒▒░░░░░░▒▒▒▐─
 * ▐▒▀▐▀▐▀▒▒▄▄▒▄▒▒▒ electrons ▒▌
 * ▐▒▒▒▀▀▄▄▒▒▒▄▒▒▒▒▒▒░░░░░░▒▒▒▐─
 * ─▌▒▒▒▒▒▒▀▀▀▒▒▒▒▒▒▒▒░░░░▒▒▒▒▌─
 * ─▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▐──
 * ──▀ amaze ▒▒▒▒▒▒▒▒▒▒▒▄▒▒▒▒▌──
 * ────▀▄▒▒▒▒▒▒▒▒▒▒▄▄▄▀▒▒▒▒▄▀───
 * ───▐▀▒▀▄▄▄▄▄▄▀▀▀▒▒▒▒▒▄▄▀─────
 * ──▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▀▀────────
 * " "
 */
public class SocketImpl implements PlayerLogic {


    private Player player;

    public SocketImpl(Player player) {
        this.player = player;
    }

    @Override
    public int getMyPlayerId() {
        return player.getPlayerId();
    }


    @Override
    public Move makeMove(GameState game) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(player.getSocket().getOutputStream()));
        writer.write("It's your turn.");
        writer.newLine();
        int[][] board = game.getBoard();
        for (int[] i : board){
            for (int j : i){
                writer.write(j + " ");
            }
            writer.newLine();
        }
        writer.write("please input your movement(if you want to use card input 0).");
        writer.write("   example : 0,1 is mean put your stone in 0,1:");
        writer.flush();
        while (true){
            BufferedReader reader = new BufferedReader(new InputStreamReader(player.getSocket().getInputStream()));
            String firstLine = reader.readLine();
            System.out.println(firstLine);
            if ("0".equals(firstLine)){
                //go into card logic
                writer.write("current cards:" );
                writer.newLine();
                writer.write(player.getCards().toString());
                writer.newLine();
                writer.write("Double(1),Replacement(2),Freedom(3),exit(0),please choose.");
                writer.flush();
                reader = new BufferedReader(new InputStreamReader(player.getSocket().getInputStream()));
                String content = reader.readLine();
                if ("1".equals(content)){
                    writer.write("input two position separate by white space.example : 0,1 0,2 : ");
                    writer.flush();
                    while (true){
                        try {
                            reader = new BufferedReader(new InputStreamReader(player.getSocket().getInputStream()));
                            String[] container = reader.readLine().split(" ");
                            String[] coordinateA = container[0].split(",");
                            String[] coordinateB = container[1].split(",");
                            Move first;
                            first = new Move(InfluenceCard.DOUBLE,
                                    new Coordinates(Integer.parseInt(coordinateA[0]),Integer.parseInt(coordinateA[1])),
                                    new Coordinates(Integer.parseInt(coordinateB[0]),Integer.parseInt(coordinateB[1])));
                            if (game.isMoveAllowed(first,player.getPlayerId())){
                                GameState.BOARD[first.getFirstMove().getX()][first.getFirstMove().getY()] = player.getPlayerId();
                                GameState.BOARD[first.getSecondMove().getX()][first.getSecondMove().getY()] = player.getPlayerId();
                                return first;
                            } else {
                                writer.write("your input is illegal,please input again:");
                                writer.flush();
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                            writer.write("your input is illegal.please input again:");
                            writer.flush();
                        }
                    }
                } else if ("2".equals(content)){
                    writer.write("input the position you want to replace : ");
                    writer.flush();
                    while (true){
                        try {
                            reader = new BufferedReader(new InputStreamReader(player.getSocket().getInputStream()));
                            String[] coordinate = reader.readLine().split(",");
                            Move step = new Move(InfluenceCard.REPLACEMENT,new Coordinates(Integer.parseInt(coordinate[0]),Integer.parseInt(coordinate[1])),null);
                            if (game.isMoveAllowed(step,player.getPlayerId())){
                                GameState.BOARD[step.getFirstMove().getX()][step.getFirstMove().getY()] = player.getPlayerId();
                                return step;
                            } else {
                                writer.write("your input is illegal,please input again:");
                                writer.flush();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            writer.write("your input is illegal.please input again:");
                            writer.flush();
                        }
                    }
                } else if ("3".equals(content)){
                    writer.write("input the position you want to free :  ");
                    writer.flush();
                    while (true){
                        try {
                            reader = new BufferedReader(new InputStreamReader(player.getSocket().getInputStream()));
                            String[] coordinate = reader.readLine().split(",");
                            Move step = new Move(null,new Coordinates(-1,-1),null);
                            step = new Move(InfluenceCard.FREEDOM,new Coordinates(Integer.parseInt(coordinate[0]),Integer.parseInt(coordinate[1])),null);
                            if (game.isMoveAllowed(step,player.getPlayerId())){
                                GameState.BOARD[step.getFirstMove().getY()][step.getFirstMove().getY()] = player.getPlayerId();
                                return step;
                            } else {
                                writer.write("your input is illegal..please input again:");
                                writer.flush();
                            }
                        } catch (NumberFormatException e){
                            writer.write("it's seem you input an illegal character.execute exit operation.");
                            writer.newLine();
                            writer.flush();
                        }
                    }
                } else if ("0".equals(content)){
                    writer.write("please input your movement(if you want to use card input 0).");
                    writer.write("   example : 0,1 is mean put your stone in 0,1");
                    writer.newLine();
                    writer.flush();
                    continue;
                } else {
                    writer.write("it's seem you input an illegal character.execute exit operation.");
                    writer.newLine();
                    writer.write("please input your movement(if you want to use card input 0).");
                    writer.write("   example : 0,1 is mean put your stone in 0,1");
                    writer.newLine();
                    writer.flush();
                    continue;
                }
            }
            String[] content = firstLine.split(",");
            if (content.length != 2){
                writer.write("it's seems you input illegal character.please input again.");
                writer.newLine();
                writer.flush();
                continue;
            }
            try{
                int row = Integer.parseInt(content[0]);
                int col = Integer.parseInt(content[1]);
                Move move = new Move(null,new Coordinates(row,col),null);
                if (game.isMoveAllowed(move,player.getPlayerId())){
                    //this move is legal,store it.
                    GameState.BOARD[row][col] = player.getPlayerId();
                    return move;
                } else {
                    writer.write("it's an illegal operation,try it again.");
                    writer.flush();
                }
            } catch (NumberFormatException e){
                writer.write("it's seems you input an illegal character.please input again.");
                writer.newLine();
                writer.flush();
            }
        }
    }
}
