package temp;

import java.io.*;
import java.net.Socket;

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
public class ServerHandler implements Runnable {

    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        if (GameState.ROOM_STATUS.get()){
            //player number has been set
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                writer.write("The room owner is "+ GameState.ROOM_OWNER_NAME + ",and this is a " + GameState.PLAYER_NUMBER.get() + " people game.");
                writer.newLine();
                writer.write("Input 'ready' to join the game: ");
                writer.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String firstInput = reader.readLine();
                System.out.println(firstInput);
                if ("ready".equals(firstInput)){
                    //spin until into the room or the room is full
                    int curNumber = GameState.CUR_PLAYER_NUMBER.get();
                    if (curNumber >= GameState.PLAYER_NUMBER.get()){
                        writer.write("so sorry the room is full.");
                        writer.flush();
                        return;
                    }
                    while (!GameState.CUR_PLAYER_NUMBER.compareAndSet(curNumber,curNumber+1)){
                        if (GameState.CUR_PLAYER_NUMBER.get() == GameState.PLAYER_NUMBER.get()){
                            //room full,can't into.
                            writer.write("so sorry the room is full.");
                            writer.flush();
                            return;
                        }
                        curNumber = GameState.CUR_PLAYER_NUMBER.get();
                    }
                    Player player = new Player(socket,socket.getInetAddress().getHostName(),curNumber+1);
                    GameState.PLAYERS.add(player);
                    writer.write("welcome to my game,you are player "+(curNumber+1)+" ,the number present your chess pieces.");
                    writer.newLine();
                    //observe the room state
                    observeTheRoom(writer);
                    writer.flush();
                    //enter the game logic
                    gameStart(player);
                } else {
                    writer.write("you input isn't equal ready,so see you next time.");
                    writer.flush();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //there is non player into this room , so become the room owner
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                writer.write("welcome,you are the first one into this room.");
                writer.newLine();
                writer.write("please input the player number(1-5): ");
                writer.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                int input;
                try {
                    input = Integer.parseInt(reader.readLine());
                } catch (NumberFormatException e){
                    writer.write("it's seem you input a non number.");
                    writer.flush();
                    return;
                }
                if (input >=1 && input <=5){
                    if (GameState.PLAYER_NUMBER.compareAndSet(0,input)){
                        GameState.ROOM_STATUS.compareAndSet(false,true);
                        GameState.ROOM_OWNER_NAME = socket.getInetAddress().getHostName();
                        GameState.CUR_PLAYER_NUMBER.compareAndSet(0,1);
                        Player player = new Player(socket,socket.getInetAddress().getHostName(),1);
                        GameState.PLAYERS.add(player);
                        writer.write("you make a room for " + input + " people,and your playerNumber is " + GameState.getPlayerNumber()+" go and ask your friend to join it.");
                        writer.newLine();
                        writer.flush();
                        //observe the state of the room
                        observeTheRoom(writer);
                        //game start into game logic
                        gameStart(player);
                    } else {
                        writer.write("your room is stolen by " + GameState.ROOM_OWNER_NAME);
                        writer.flush();
                    }
                } else {
                    writer.write("it's seem you input a illegal number , so see you next time.");
                    writer.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void observeTheRoom(BufferedWriter writer) throws IOException {
        int playerNumber = GameState.PLAYER_NUMBER.get();
        int curNumber = GameState.CUR_PLAYER_NUMBER.get();
        boolean flag = playerNumber == curNumber;
        while (!flag){
            //the room isn't full
            writer.write("current number is " + curNumber + " , ask your friend to join it.");
            writer.newLine();
            writer.write("===================I'M A SEPARATER=================");
            writer.newLine();
            writer.flush();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag = GameState.PLAYER_NUMBER.get() == GameState.CUR_PLAYER_NUMBER.get();
        }
        writer.write("Game start!");
        writer.flush();
    }

    private void gameStart(Player player) throws IOException {
        GameState gameState = new GameState();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(player.getSocket().getOutputStream()));
        if (GameState.PLAYER_NUMBER.get() == 1){
            //go into bot logic
            BotImpl bot = new BotImpl(player);
            BufferedReader reader = new BufferedReader(new InputStreamReader(player.getSocket().getInputStream()));
            writer.write("game start,please input(if you want to use a card input 0) :");
            writer.newLine();
            writer.flush();
            while (true){
                if (gameState.isGameEnd()){
                    writer.write("game over.");
                    writer.flush();
                    break;
                }
                int[][] board = GameState.BOARD;
                for (int[] i : board){
                    for (int j : i){
                        writer.write(j + " ");
                    }
                    writer.newLine();
                }
                writer.flush();
                String content = reader.readLine();
                System.out.println(content);
                if ("0".equals(content)){
                    //go into card logic
                } else {
                    //normal logic
                    try {
                        String[] result = content.split(",");
                        int x = Integer.parseInt(result[0]);
                        int y = Integer.parseInt(result[1]);
                        Move move = new Move(null,new Coordinates(x,y),null);
                        if (gameState.isMoveAllowed(move,player.getPlayerId())){
                            GameState.BOARD[x][y] = 1;
                            bot.makeMove(gameState);
                            writer.write("it's your turn.");
                            writer.newLine();
                            writer.flush();
                        } else {
                            writer.write("your operation is illegal,try it again.");
                            writer.newLine();
                            writer.flush();
                        }
                    } catch (NumberFormatException e) {
                        writer.write("it's seems your input contains illegal character,try it again.");
                        writer.newLine();
                        writer.flush();
                    }
                }
            }
        } else {
            SocketImpl game = new SocketImpl(player);
            while (true){
                boolean flag = gameState.isAlive(player.getPlayerId());
                System.out.println("flag:" + flag + ",id:" + player.getPlayerId());
                if (!flag){
                    writer = new BufferedWriter(new OutputStreamWriter(player.getSocket().getOutputStream()));
                    writer.write("you were dead.");
                    writer.flush();
                    break;
                }
                if (gameState.isGameEnd()){
                    int winner = gameState.getWinner();
                    writer.write("game end,the winner is " + winner);
                    writer.flush();
                    break;
                }
                int curPlay = gameState.getCurPlayerId();
                int myId = game.getMyPlayerId();
                if (curPlay == myId){
                    game.makeMove(gameState);
                    if (curPlay == GameState.getPlayerNumber()){
                        GameState.CUR_PLAYER_ID.compareAndSet(curPlay,1);
                    } else {
                        GameState.CUR_PLAYER_ID.compareAndSet(curPlay,curPlay+1);
                    }
                } else {
                    writer.write("it's not your turn,please wait.");
                    writer.newLine();
                    writer.flush();
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (gameState.isGameEnd()){
                int winner = gameState.getWinner();
                writer.write("game end,the winner is " + winner);
                writer.flush();
            }
        }
    }
}
