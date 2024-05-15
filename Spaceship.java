package for_project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Spaceship extends Game {
    private ArrayList<String> spaceshipParts = new ArrayList<>();
    private ArrayList<Character> guessedLetters = new ArrayList<>();
    private ArrayList<String> words = new ArrayList<>();
    private String secretWord;
    private int counter = 0;
    private char lastInput;
    private boolean isGuessCorrect;

    public Spaceship() {
        try {
            File file = new File("rockets.txt");
            Scanner scan = new Scanner(file);
            scan.useDelimiter(",");
            while (scan.hasNext()) {
                String item = scan.next();
                spaceshipParts.add(item);
            }
            
            File wordsFile = new File("words.txt");
            Scanner scan2 = new Scanner(wordsFile);
            scan2.useDelimiter(",");
            while(scan2.hasNext()) {
                words.add(scan2.next().toLowerCase()); // Add all words to the list
            }
            scan2.close();
            
            // Randomly select a word from the list
            Random random = new Random();
            int randomInd = random.nextInt(words.size());
            secretWord = words.get(randomInd);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < secretWord.length(); i++) {
            guessedLetters.add('_');
        }
    }

    @Override
    public String explainRules() {
        return "The goal of the game is to guess the secret word letter by letter.";
    }

    @Override
    public String setup() {
        // Print spaceship parts based on the number of wrong guesses
        for (int i = 0; i <= counter; i++) {
            System.out.println(spaceshipParts.get(i)); 
        }

        // Print guessed letters or underscores for secret word
        for (char letter : guessedLetters) {
            System.out.print(letter + " ");
        }
        return "\n\nEnter a letter: ";
    }

    @Override
    public boolean goodPlayerInput(String guess) {
        if (guess.length() == 1 && Character.isLetter(guess.charAt(0))) {
            lastInput = guess.toLowerCase().charAt(0);
            return true;
        } else {
            System.out.println("Invalid input. Please enter a single letter.");
            return false;
        }
    }

    @Override
    public String checkWinOrLose() {
        char guessedLetter = lastInput;
        isGuessCorrect = false;

        if (secretWord.indexOf(guessedLetter) != -1) {
            isGuessCorrect = true;
            for (int i = 0; i < secretWord.length(); i++) {
                if (secretWord.charAt(i) == guessedLetter) {
                    this.guessedLetters.set(i, guessedLetter);
                }
            }
        } else {
            counter++;
        }

        if (isGuessCorrect) {
            return "Good job, the letters have been updated!";
        } else {
            return "Letter not found in word. Try again!";
        }
    }

    @Override
    public boolean canPlayAgain() {
        if (counter >= spaceshipParts.size() - 1) {
            System.out.println(spaceshipParts.get(spaceshipParts.size() - 1));
            System.out.println("LIFTOFF! Sorry...You lose!");
            return false;
        } else if (guessedLetters.contains('_')) {
            return true;
        } else {
            System.out.println("Congratulations! You guessed the word: " + secretWord);
            return false;
        }
    }
}
