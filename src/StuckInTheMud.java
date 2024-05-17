import java.util.Random;

public class StuckInTheMud {

    private static final int NUM_PLAYERS = 4;
    private static final int WINNING_SCORE = 100;
    private static final int NUM_DICE = 4;

    public static void main(String[] args) {
        StuckInTheMud game = new StuckInTheMud(); // Create an instance of StuckInTheMud
        game.play();
    }

    public void play() {
        Player[] players = new Player[NUM_PLAYERS];
        players[0] = new Player("Wilma");
        players[1] = new Player("Barney");
        players[2] = new Player("Betty");
        players[3] = new Player("Fred");

        int currentPlayerIndex = 0;
        Player currentPlayer;

        System.out.println("New Round Starting"); // Moved outside the loop

        while (!isWinner(players)) {
            currentPlayer = players[currentPlayerIndex];
            int roundTotal = rollDice(currentPlayer);

//            currentPlayer.addToScore(roundTotal);
//            System.out.println(currentPlayer.getName() + " earned " + roundTotal + " this round, totaling " + currentPlayer.getScore());

            if (currentPlayer.getScore() >= WINNING_SCORE) {
                System.out.println("...new high player!");
                break;
            }

            currentPlayerIndex = (currentPlayerIndex + 1) % NUM_PLAYERS;
        }
    }

    private int rollDice(Player player) {
        Dice[] dice = new Dice[NUM_DICE];
        int roundTotal;
        boolean allStuck = false;

        for (int i = 0; i < NUM_DICE; i++) {
            dice[i] = new Dice();
        }

        do {
            roundTotal = 0;

            System.out.print(player.getName() + " rolled: [");

            for (int i = 0; i < NUM_DICE; i++) {
                dice[i].roll();
                System.out.print(dice[i].getResult());
                if (!dice[i].isStuck()) {
                    roundTotal += dice[i].getResult();
                }
                if (i != NUM_DICE - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            allStuck = true;
            for (Dice d : dice) {
                if (!d.isStuck()) {
                    allStuck = false;
                    break;
                }
            }

            if (!allStuck) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while (!allStuck);

        player.addToScore(roundTotal); // Add round total to player's score
        System.out.println(player.getName() + " earned " + roundTotal + " this round, totaling " + player.getScore());
        return roundTotal;
    }



    private static boolean isWinner(Player[] players) {
        for (Player player : players) {
            if (player.getScore() >= WINNING_SCORE) {
                System.out.println(player.getName() + " wins!");
                return true;
            }
        }
        return false;
    }

    public static class Dice {
        private int result;
        private boolean stuck;

        public Dice() {
            this.result = 0;
            this.stuck = false;
        }

        public void roll() {
            if (!stuck) {
                Random random = new Random();
                result = random.nextInt(6) + 1;
                if (result == 5 || result == 2) {
                    stuck = true;
                }
            }
        }

        public int getResult() {
            return result;
        }

        public boolean isStuck() {
            return stuck;
        }
    }

}

