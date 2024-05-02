import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DictionaryTest {

	public static void main(String[] args) throws Exception {

		Dictionary dict = new Dictionary();

		Scanner sc = new Scanner(System.in);

		boolean loaded = false;
		while (!loaded) {
			System.out.print("Enter filename> ");
			String filename = sc.nextLine();

			try {

				dict = new Dictionary(new File(filename));
				System.out.println("dictionary loaded successfully.");
				loaded = true;
			} 

			catch (FileNotFoundException e) {
				System.out.println("File not found. Please try again.");
			}
		}
		
		boolean done = false;

		while (!done) {

			System.out.print("Enter a command (add, remove, check, similar, save, exit)> ");
			String command = sc.nextLine().toLowerCase();

			switch (command) {

				case "add":

					System.out.print("add new word> ");
					String addWord = sc.nextLine();

					try {
						dict.addWord(addWord);
						System.out.println("word added successfully.");
					} 

					catch (Exception e) {
						System.out.println("Exception: Word already exists.");
					}

					break;

				case "remove":

					System.out.print("remove word> ");
					String removeWord = sc.nextLine();

					try {
						dict.deleteWord(removeWord);
						System.out.println("word removed successfully.");
					} 

					catch (Exception e) {
						System.out.println("Exception: Word not found.");
					}

					break;

				case "check":

					System.out.print("check word> ");
					String checkWord = sc.nextLine();

					if (dict.findWord(checkWord)) {
						System.out.println("word found.");
					} 

					else {
						System.out.println("word not found.");
					}

					break;

				case "similar":

					System.out.print("search for similar words> ");
					String similarWord = sc.nextLine();
					String[] similarWords = dict.findSimilar(similarWord);

					if (similarWords.length == 0) {
						System.out.println("no similar words found.");
					} 

					else {
						System.out.print(similarWords[0]);
						for (int i = 1; i < similarWords.length; i++) {
							System.out.print(", " + similarWords[i]);
						}

						System.out.println(".");
					}

					break;

				case "save":

					System.out.print("Save Updated Dictionary (Y/N)> ");
					String saveChoice = sc.nextLine().toLowerCase();
					if (saveChoice.equals("y")) {
						System.out.print("Enter filename> ");
						String saveFile = sc.nextLine();
						dict.saveToFile(saveFile);
						System.out.println("Dictionary saved successfully.");
					}

					break;

				case "exit":

					done = true;
					break;

				default:
					System.out.println("Invalid command. Please try again.");
					break;
			}
		}

		sc.close();

	}

}
