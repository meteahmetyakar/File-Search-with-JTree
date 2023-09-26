import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/***
 * A static class that enables tree creation, JTree display, search and move operations in a DefaultMutableTreeNode structure.
 */
public class TreeOperations
{
    /**
     * it checks given file is valid, file must be include only numbers, capital letters and ;
     * @param fileName filename
     * @return if file is valid it returns true
     */
    private static boolean isFileValid(String fileName)
    {
        File file = new File( System.getProperty("user.dir") + "/src/" + fileName);
        Scanner sc = null;
        try {
            sc = new Scanner(file); //read from file
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        while (sc.hasNextLine())
        {
            String tmp = sc.nextLine();
            for(Character ch : tmp.toCharArray())
                if(!(Character.isUpperCase(ch) || Character.isDigit(ch) || ch == ';'))
                    return false;
        }

        return true;
    }


    /**
     *
     * @param fileName filename which includes tree
     * @return given tree as arraylist
     */
    public static ArrayList<String[]> readFromFile(String fileName) throws IllegalArgumentException, FileNotFoundException {
        if(!isFileValid(fileName))
            throw new IllegalArgumentException();

        ArrayList<String[]> data = new ArrayList<String[]>();

        File file = new File( System.getProperty("user.dir") + "/src/" + fileName);

        if(!file.isFile())
            throw new FileNotFoundException();

        Scanner sc = null;
        try {
            sc = new Scanner(file); //read from file
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        while (sc.hasNextLine())
            data.add(sc.nextLine().split(";")); //split with ';' and add to arraylist

        return data;
    }

    /**
     * Starting from root, find the elements in the arraylist one by one and add them there
     * @param data tree as arraylist
     * @return it returns tree as DefaultMutableTreeNode
     */
    public static DefaultMutableTreeNode createTree(ArrayList<String[]> data)
    {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Root"); //create a root
        DefaultMutableTreeNode iter;
        for(var path : data)
        {
            iter = top;
            for(var nodeOfPath : path)
            {
                DefaultMutableTreeNode node = null;
                for(int i = 0; i<iter.getChildCount(); i++)
                {
                    DefaultMutableTreeNode tmp = (DefaultMutableTreeNode) iter.getChildAt(i);
                    if(tmp.getUserObject().equals(nodeOfPath))
                    {
                        node = tmp;
                        break;
                    }
                }

                if(node == null)
                {
                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(nodeOfPath);
                    iter.add(newNode);
                    iter = newNode;
                }
                else
                    iter = node;
            }
        }

        return top;
    }

    /**
     * it shows Jtree
     * @param tree tree to be printed on the screen
     */
    public static void showTree(DefaultMutableTreeNode tree, String title)
    {
        JFrame frame = new JFrame();
        frame.setTitle(title);
        frame.add(new JScrollPane(new JTree(tree)));
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * step for searching calls
     */
    private static int step;

    /**
     * Searches the tree with BFS
     * @param tree tree to search
     * @param element element to search
     */
    public static void searchByBFS(DefaultMutableTreeNode tree, String element)
    {
        System.out.println();
        step = 0;
        if(tree.getUserObject() == null)
        {
            System.out.println("Tree is empty");
            return;
        }

        System.out.println("Using BFS to find '"+element+"' in the tree...");
        if(!BFS(tree,element)) {
            System.out.println("Not Found.");
        }

    }

    /**
     *  BFS Algorithm with queue
     * @param root Root of tree/subtree to BFS
     * @param element element to search
     * @return if element found it returns true, otherwise false
     */
    private static boolean BFS(DefaultMutableTreeNode root, String element)
    {
        DefaultMutableTreeNode curr;
        Queue<DefaultMutableTreeNode> q = new LinkedList<>();

        q.add(root);

        while(!q.isEmpty())
        {
            step += 1;
            curr = q.peek(); q.poll();
            System.out.print("Step " + step + " -> " + curr.getUserObject());

            if(curr.getUserObject().equals(element))
            {
                System.out.println("(Found!)");
                return true;
            }

            System.out.println();

            for(int i = 0; i<curr.getChildCount(); i++)
                q.add((DefaultMutableTreeNode) curr.getChildAt(i));
        }

        return false;
    }

    /**
     * Searches the tree with DFS
     * @param tree tree to search
     * @param element element to search
     */
    public static void searchByDFS(DefaultMutableTreeNode tree, Object element)
    {
        System.out.println();
        step = 0;
        if(tree.getUserObject() == null)
        {
            System.out.println("Tree is empty");
            return;
        }

        System.out.println("Using DFS to find '"+element+"' in the tree...");
        if(!DFS(tree,element))
            System.out.println("Not Found.");

    }

    /**
     * Recursive DFS algorithm
     * @param root Root of tree/subtree to DFS
     * @param element element to search
     * @return if element found it returns true, otherwise false
     */
    private static boolean DFS(DefaultMutableTreeNode root, Object element)
    {
        step += 1;
        System.out.print("Step " + step + " -> " + root.getUserObject() + " ");
        if(root.getUserObject().equals(element))
        {
            System.out.println("(Found!)");
            return true;
        }

        System.out.println();

        for(int i=root.getChildCount()-1; i>=0; i--)
            if(DFS((DefaultMutableTreeNode) root.getChildAt(i), element))
                return true;

        return false;
    }


    /**
     * search given tree with postorder
     * @param tree tree to search
     * @param element element to search
     */
    public static void postorder(DefaultMutableTreeNode tree, Object element)
    {
        System.out.println();
        step = 0;
        if(tree.getUserObject() == null)
        {
            System.out.println("Tree is empty");
            return;
        }

        System.out.println("Using Post-Order traversal to find '"+element.toString()+"' in the tree...");
        if(!postorderHelper(tree, element))
            System.out.println("Not Found.");

    }

    /**
     * recursive postorder algorithm
     * @param root Root of tree/subtree to DFS
     * @param element element to search
     * @return if element found it returns true, otherwise false
     */
    private static boolean postorderHelper(DefaultMutableTreeNode root, Object element)
    {
        for(int i=0; i<root.getChildCount(); i++)
            if(postorderHelper((DefaultMutableTreeNode) root.getChildAt(i), element))
                return true;

        step += 1;
        System.out.print("Step " + step + " -> " + root.getUserObject());
        if(root.getUserObject().equals(element))
        {
            System.out.println("(Found!)");
            return true;
        }
        System.out.println();

        return false;
    }

    /**
     * Move a node from one to another
     * @param tree tree to process
     * @param path root path of the element to be moved
     * @param destination target location
     * @throws IllegalArgumentException
     */
    public static void moveTo(DefaultMutableTreeNode tree, String[] path, String destination) throws IllegalArgumentException
    {
        if(tree == null || path.length == 0 || destination == "")
            throw new IllegalArgumentException();

        System.out.print("Moved ");
        for(int i=0; i<path.length-1; i++)
            System.out.print(path[i] + "->");
        System.out.print(path[path.length-1]);
        System.out.println(" to " + destination);

        Queue<DefaultMutableTreeNode> q = new LinkedList<>();
        DefaultMutableTreeNode iter = tree;
        DefaultMutableTreeNode moveIter = new DefaultMutableTreeNode();


        for(int j = 0; j<path.length; j++)
        {
            boolean isChild = false;
            for(int i=0; i<iter.getChildCount(); i++)
            {
                DefaultMutableTreeNode child = (DefaultMutableTreeNode) iter.getChildAt(i);
                if(child.getUserObject().equals(path[j]))
                {
                    isChild = true;
                    q.add( j == path.length-1 ? child : (DefaultMutableTreeNode) child.clone());
                    iter = child;
                    break;
                }
            }

            if(!isChild)
            {
                System.out.print("Cannot move ");
                for(int i=0; i<path.length-1; i++)
                    System.out.print(path[i] + "->");
                System.out.print(path[path.length-1]);
                System.out.print(" because it doesn't exist in the tree.");

                return;
            }

        }

        while(iter != tree)
        {
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) iter.getParent();
            if(parent.getChildCount() > 1 || parent == tree)
            {
                parent.remove(iter);
                break;
            }
            else
                iter = parent;
        }

        boolean isThereDestination = false;
        for(int i=0; i<tree.getChildCount(); i++)
        {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) tree.getChildAt(i);
            if (child.getUserObject().equals(destination))
            {
                isThereDestination = true;
                moveIter = child;
                break;
            }
        }

        if(!isThereDestination)
        {
            moveIter = new DefaultMutableTreeNode(destination);
            tree.add(moveIter);
        }

        q.poll();
        while(!q.isEmpty())
        {
            DefaultMutableTreeNode element = q.peek();
            DefaultMutableTreeNode child = new DefaultMutableTreeNode();
            boolean isChild = false;
            int idx = 0;
            for(; idx<moveIter.getChildCount(); idx++)
            {
                child = (DefaultMutableTreeNode) moveIter.getChildAt(idx);
                if(child.getUserObject().equals(element.getUserObject()))
                {
                    isChild = true;
                    break;
                }
            }

            if(!isChild)
            {
                moveIter.add(q.peek());
                moveIter = q.peek();
            }
            else if(q.size() == 1)
            {
                moveIter.remove(idx);
                moveIter.add(q.peek());
                for(int idx2=0; idx2<path.length-1; idx2++)
                    System.out.print(path[idx2] + "->");
                System.out.print(path[path.length-1]);
                System.out.print(" has been overwritten");
            }
            else
                moveIter = child;

            q.poll();
        }

    }

}



