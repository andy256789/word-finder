import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Word Reader");
        System.out.println("(enter 'exit' to quit)");

        while (true) {
            System.out.print("Enter file path: ");
            String input = scanner.nextLine();

            if (input.equals("exit")) {
                break;
            }

            Path path = Paths.get(input);

            if (Files.notExists(path)) {
                System.out.println(path + " does not exist");
                continue;
            }

            List<String> bookLines;

            try {
                bookLines = Files.readAllLines(path);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                continue;
            }

            if (bookLines.isEmpty()) {
                System.out.println("Book file is empty");
                continue;
            }

            handleBookMenu(bookLines, scanner);
        }
        scanner.close();
    }

    private static void handleBookMenu(List<String> bookLines, Scanner scanner) {
        while (true) {
            System.out.println("--- Book Menu ---");
            System.out.println("1: Find a word by line number");
            System.out.println("2: Search for a specific word");
            System.out.println("0: Open a different book");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    processLines(bookLines, scanner);
                    break;
                case "2":
                    searchForWord(bookLines, scanner);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }

    private static void processLines(List<String> bookLines, Scanner scanner) {
        while (true) {
            System.out.println("Book has " + bookLines.size() + " lines");
            System.out.print("Enter line number ('0' to quit): ");
            int lineIndex = Integer.parseInt(scanner.nextLine());
            if (lineIndex == 0) break;

            if (lineIndex < 0 || lineIndex > bookLines.size()) {
                System.out.println(lineIndex + " is not a valid line number");
                continue;
            }

            String lineText = bookLines.get(lineIndex - 1).trim();

            if (lineText.isEmpty()) {
                System.out.println("Line " + lineIndex + " is empty");
                continue;
            }

            String[] words = lineText.split("[^a-zA-Z0-9']+"); // leave only the word

            processWords(words, lineIndex, scanner);
        }
    }

    private static void processWords(String[] words, int lineIndex, Scanner scanner) {
        while (true) {
            System.out.println("Line " + lineIndex + " has " + words.length + " words");
            System.out.print("Enter word index ('0' to quit): ");
            int wordIndex = Integer.parseInt(scanner.nextLine());
            if (wordIndex == 0) break;

            if (wordIndex < 0 || wordIndex > words.length) {
                System.out.println(wordIndex + " is not a valid word number");
                continue;
            }

            System.out.println("The word at line " + lineIndex + ", index  " + wordIndex + " is \"" + words[wordIndex - 1] + "\"");
        }
    }

    private static void searchForWord(List<String> bookLines, Scanner scanner) {
        while (true) {
            System.out.print("Enter a word to search for ('exit' to go back): ");
            String targetWord = scanner.nextLine().trim();

            if (targetWord.equalsIgnoreCase("exit")) {
                break;
            }

            if (targetWord.isEmpty()) {
                System.out.println("Enter a word");
                continue;
            }

            int matchCount = 0;

            for (int i = 0; i < bookLines.size(); i++) {
                String lineText = bookLines.get(i).trim();

                if (lineText.isEmpty()) continue;

                String[] words = lineText.split("[^a-zA-Z0-9']+");

                for (String word : words) {
                    if (word.equalsIgnoreCase(targetWord)) {
                        System.out.println("Line " + (i + 1) + ": " + lineText);
                        matchCount++;
                        break;
                    }
                }
            }
            System.out.println("Matches found: " + matchCount);
        }
    }
}