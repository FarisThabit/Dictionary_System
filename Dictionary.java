//Omar Alromaih         ID: 201728790
//Ahmed Radwan           ID: 202045860

import java.io.BufferedWriter;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


class Dictionary {

    AVLTree<String> tree;

    public Dictionary(String s) throws Exception {

        tree = new AVLTree<String>();
        tree.insertAVL(s);
    }

    public Dictionary() {

        tree = new AVLTree<String>();
    }

    public Dictionary(File f) throws FileNotFoundException, Exception {

        tree = new AVLTree<String>();
        Scanner scanner = new Scanner(f);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            try {
                tree.insertAVL(line);
            }

            catch (IllegalArgumentException e) {
                continue;
            }
            
        }
        scanner.close();
    }

    public void addWord(String s) throws Exception {

        if (tree.search(s)) {
            throw new Exception("The word already exists in the dictionary.");
        }

        tree.insertAVL(s);
    }

    public boolean findWord(String s) {
        return tree.search(s);
    }

    public void deleteWord(String s) throws Exception {

        if (!tree.search(s)) {

            throw new Exception("The word does not exist in the dictionary.");
        }

        tree.deleteAVL(s);
    }

    public String[] findSimilar(String s) {
        
        List<String> result = new LinkedList<>();
        findSimilar(tree.root, s, result);
        return result.toArray(new String[0]);
    }

    private void findSimilar(BTNode<String> node, String s, List<String> result) {
        if (node == null) {
            return;
        }

        String word = node.data;

        // check if word is similar to s
        if (isSimilar(word, s)) {
            result.add(word);
        }

        // recursively search left and right subtrees
        findSimilar(node.left, s, result);
        findSimilar(node.right, s, result);
    }

    private boolean isSimilar(String word1, String word2) {
        int diffCount = 0;
        int lenDiff = Math.abs(word1.length() - word2.length());

        if (lenDiff > 1) {
            return false;
        }

        int i = 0, j = 0;
        while (i < word1.length() && j < word2.length()) {
            if (word1.charAt(i) != word2.charAt(j)) {
                diffCount++;
                if (diffCount > 1) {
                    return false;
                }
                if (word1.length() > word2.length()) {
                    i++;
                } else if (word1.length() < word2.length()) {
                    j++;
                } else {
                    i++;
                    j++;
                }
            } else {
                i++;
                j++;
            }
        }

        if (i < word1.length() || j < word2.length()) {
            diffCount++;
        }

        return diffCount == 1;
    }

    public void saveToFile(String f) throws IOException {
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        saveToFileHelper(tree.root, writer);
        writer.close();
    }

    private void saveToFileHelper(BTNode<String> node, BufferedWriter writer) throws IOException {

        if (node == null) {
            return;
        }

        saveToFileHelper(node.left, writer);
        writer.write(node.data + "\n");
        saveToFileHelper(node.right, writer);
    }
}
