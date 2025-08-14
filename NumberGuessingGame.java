import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    private static final int DEFAULT_MIN = 1;
    private static final int DEFAULT_MAX = 100;
    private static final int DEFAULT_ATTEMPT_LIMIT = 0 ;

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int min = DEFAULT_MIN;
            int max = DEFAULT_MAX;
            int attemptLimit = DEFAULT_ATTEMPT_LIMIT;

            int roundsPlayed = 0;
            int roundsWon = 0;
            int totalPoints = 0;

            System.out.println("=== Number Guessing Game ===");
            System.out.printf("Default range: %d to %d | Attempts per round: %d%n",
                    min, max, attemptLimit);

            if (askYesNo(sc, "Ready to (y/n): ")) {
                min = readInt(sc, "Enter minimum from 1: ");
                max = readInt(sc, "Enter maximum to 100 : ");
                while (max < min) {
                    System.out.println("Maximum must be >= minimum.");
                    max = readInt(sc, "Enter maximum again: ");
                }
                attemptLimit = readInt(sc, "Enter attempt limit: ");
                while (attemptLimit <= 0) {
                    System.out.println("Attempt limit must be > 0.");
                    attemptLimit = readInt(sc, "Enter attempt limit again: ");
                }
            }

            boolean playAgain = true;
            Random rand = new Random();

            while (playAgain) {
                roundsPlayed++;
                int target = rand.nextInt(max - min + 1) + min;
                int attemptsLeft = attemptLimit;
                boolean won = false;

                System.out.printf("%nRound %d: Guess a number between %d and %d.%n",
                        roundsPlayed, min, max);
                System.out.printf("You have %d attempts.%n", attemptLimit);

                while (attemptsLeft > 0) {
                    int guess = readIntInRange(sc, "Your guess: ", min, max);
                    attemptsLeft--;

                    if (guess == target) {
                        int attemptsUsed = attemptLimit - attemptsLeft;
                        int pointsEarned = attemptsLeft + 1; 
                        System.out.printf("Correct! You got it in %d attempt%s.%n",
                                attemptsUsed, attemptsUsed == 1 ? "" : "s");
                        System.out.printf("Points this round: %d%n", pointsEarned);
                        totalPoints += pointsEarned;
                        roundsWon++;
                        won = true;
                        break;
                    } else if (guess < target) {
                        System.out.printf("Too low. Attempts left: %d%n", attemptsLeft);
                    } else {
                        System.out.printf("Too high. Attempts left: %d%n", attemptsLeft);
                    }
                }

                if (!won) {
                    System.out.printf(" Out of attempts. The number was %d.%n", target);
                }

                System.out.printf("Scoreboard → Rounds: %d | Wins: %d | Total Points: %d%n",
                        roundsPlayed, roundsWon, totalPoints);

                playAgain = askYesNo(sc, "Play again? (y/n): ");
            }

            System.out.printf("%nThanks for playing! Final Score → Rounds: %d | Wins: %d | Total Points: %d%n",
                    roundsPlayed, roundsWon, totalPoints);
        }
    }
    private static boolean askYesNo(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim().toLowerCase();
            if (s.equals("y") || s.equals("yes")) return true;
            if (s.equals("n") || s.equals("no")) return false;
            System.out.println("Please type 'y' or 'n'.");
        }
    }

    private static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static int readIntInRange(Scanner sc, String prompt, int min, int max) {
        while (true) {
            int val = readInt(sc, prompt);
            if (val < min || val > max) {
                System.out.printf("Enter a number in the range [%d, %d].%n", min, max);
            } else {
                return val;
            }
        }
    }
}