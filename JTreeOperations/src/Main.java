import javax.swing.tree.DefaultMutableTreeNode;
import java.io.FileNotFoundException;

/**
 * test operations
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException {
        DefaultMutableTreeNode tree = TreeOperations.createTree(TreeOperations.readFromFile("tree.txt"));
        TreeOperations.showTree(tree, "tree");

        TreeOperations.searchByBFS(tree, "COURSE4");
        TreeOperations.searchByBFS(tree, "COURSE44");

        TreeOperations.searchByDFS(tree, "COURSE4");
        TreeOperations.searchByDFS(tree, "COURSE44");

        TreeOperations.postorder(tree, "COURSE4");
        TreeOperations.postorder(tree, "COURSE44");



        DefaultMutableTreeNode movedTree = TreeOperations.createTree(TreeOperations.readFromFile("tree.txt"));

        TreeOperations.moveTo(movedTree, new String[]{"2022","COURSE2","LECTURE2"}, "2023");
        TreeOperations.showTree(movedTree," movedTree");
    }



}