package for_project3;

import java.util.Scanner;

public class ChoHan extends Game {
    int balance;
    boolean canPlay;

    public ChoHan() {
        balance = 2;
        canPlay = true;
    }

    @Override
    public String explainRules() {
        return "Cho-Han is a traditional Japanese guessing game.\n"
                + "A dealer rolls two 6-sided dice, and you must guess if their sum is CHO (even) or HAN (odd).\n"
                + "You start with $2. A wrong answer loses you $1, a right answer wins $1. You must stop playing at $0.";
    }

    @Override
    public String setup() {
        return "Let's play!\n"
                + "The dealer rolls the covered dice and asks for your bet.\n"
                + "CHO (even) or HAN (odd)?";
    }

    @Override
    public boolean goodPlayerInput(String guess) {
        String guessUpper = guess.toUpperCase();
        return guessUpper.equals("CHO") || guessUpper.equals("HAN");
    }

    @Override
    public String checkWinOrLose() {
        int dice1 = rollDice();
        int dice2 = rollDice();
        int sum = dice1 + dice2;

        String result;
        if (sum % 2 == 0) {
            result = "CHO";
        } else {
            result = "HAN";
        }

        String message = "The dice show " + dice1 + " and " + dice2 + ". ";

        if (balance > 0) {
        	String input = getPlayerGuess();
            if (result.equalsIgnoreCase(input)) {
                balance++;
                message += "Correct! It was " + result + " You win $1!";
            } else if (!result.equalsIgnoreCase(input)) {
                balance--;
                message += "Incorrect! It was " + result + " You lose $1!";
            }
        } else {
            canPlay = false;
            message += "Your balance is $0. The game is over.";
        }

        return message;
    }

    @Override
    public boolean canPlayAgain() {
        return canPlay;
    }

    private int rollDice() {
        return (int) (Math.random() * 6) + 1;
    }

    public String getPlayerGuess() {
        Scanner scanner = new Scanner(System.in);
        String guess = scanner.nextLine().trim().toUpperCase(); // Read the entire line, trim any leading or trailing whitespace, and convert to uppercase
        return guess;
    }

}
