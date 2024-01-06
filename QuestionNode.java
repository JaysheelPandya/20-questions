// Jaysheel Pandya
// CSE 143 DD with Xunmei Liu
// Homework 7
// The QuestionNode class constructs question
// and answer nodes for the yes/no guessing game.

public class QuestionNode {

    public String data;
    public QuestionNode left;
    public QuestionNode right;

    // Constructs a node with the given data,
    // which points to null left and right branches
    public QuestionNode(String data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}