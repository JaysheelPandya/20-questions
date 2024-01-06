// Jaysheel Pandya
// CSE 143 DD with Xunmei Liu
// Homework 7
// The QuestionTree class implements a yes/no guessing
// game. It asks questions until the computer guesses the
// player's object. If the computer guesses correctly, it wins.
// If not, the player wins, and the computer adds a question
// and answer to its game to become more accurate.

import java.io.*;
import java.util.*;

public class QuestionTree {

    private Scanner console;
    private QuestionNode overallRoot;

    // Sets up the guessing game's tree with a first
    // guess, an object "computer"
    public QuestionTree() {
        console = new Scanner(System.in);
        overallRoot = new QuestionNode("computer");
    }

    // Reads in a text file and converts it into a
    // tree of questions and corresponding answers,
    // replacing the current tree
    public void read(Scanner input) {
        overallRoot = readHelper(input);
    }

    // Reads each line of the text file and determines
    // whether it is a question or answer, updating the
    // game's tree accordingly
    // Returns each node of the tree in until the full tree
    // is created in preorder
    private QuestionNode readHelper(Scanner input) {
        String type = input.nextLine();
        String data = input.nextLine();
        QuestionNode root = new QuestionNode(data);
        if (type.equals("Q:")) {
            root.left = readHelper(input);
            root.right = readHelper(input);
        }
        return root;
    }

    // Writes the current tree of questions and
    // their corresponding answers to an external
    // text file
    public void write(PrintStream output) {
        writeHelper(output, overallRoot);
    }

    // Converts each node of the tree to a two-line
    // output to be put into the external text file,
    // classifying each as either a question or an answer
    private void writeHelper(PrintStream output, QuestionNode root) {
        if (root.left == null) {
            output.println("A:");
            output.println(root.data);
        }
        else {
            output.println("Q:");
            output.println(root.data);
            writeHelper(output, root.left);
            writeHelper(output, root.right);
        }
    }

    // Runs the game of yes/no questions, going down
    // the current tree according to the player's answers
    // Ends round with either a correct guess, or an incorrect
    // guess (in which case the game adds a question and answer
    // to the tree to distinguish answers)
    public void askQuestions() {
        overallRoot = askQuestionsHelper(overallRoot);
    }

    // Asks questions from the game's tree according to the player's
    // answers until the computer is ready to guess
    // Updates the tree if the computer guesses wrong by prompting the
    // player for a distinguishing question and their answer
    private QuestionNode askQuestionsHelper(QuestionNode root) {
        if (root.left != null) {
            if (yesTo(root.data)) {
                root.left = askQuestionsHelper(root.left);
            }
            else {
                root.right = askQuestionsHelper(root.right);
            }
        }
        else {
            if (yesTo("Would your object happen to be " + root.data + "?")) {
                System.out.println("Great, I got it right!");
            }
            else {
                System.out.print("What is the name of your object? ");
                String answer = console.nextLine();
                System.out.println("Please give me a yes/no question that");
                System.out.println("distinguishes between your object");
                System.out.print("and mine--> ");
                String newQ = console.nextLine();
                QuestionNode temp = root;
                root = new QuestionNode(newQ);
                if (yesTo("And what is the answer for your object? ")) {
                    root.left = new QuestionNode(answer);
                    root.right = temp;
                }
                else {
                    root.left = temp;
                    root.right = new QuestionNode(answer);
                }
                System.out.println(root.left.data);
                System.out.println(root.right.data);
            }
        }
        return root;
    }

    // Asks the user a question, forcing an answer of "y " or "n"
    // Returns true if the answer was yes, returns false otherwise
    public boolean yesTo(String prompt) {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");
    }
}