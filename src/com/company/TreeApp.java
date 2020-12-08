package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

// Node class: stores the object that is our data in the binary tree
class Node {

    int iData;          // data used as key value
    double dData;       // other data
    Node leftChild;     // this nodes left child
    Node rightChild;    // this nodes right child

    // display:
    public void displayNode(){
        // Node:
        System.out.print("{");
            System.out.print(iData);
            System.out.print(", ");
            System.out.print(dData);
            System.out.print("} ");
    }
}

////////////////////////////////////////////////////////////////////////////
// Tree class: instantiate the tree + holds all of the nodes
// - it only has one field: a Node var that holds the root.
//   it doesn't need fields for the other nodes, because they can all
//   be accessed from the root
// - time complexity: depends on the levels required to find the itme.
//   - O(logN) time.
class Tree{

    private Node root; // root

    // constructor:
    public Tree(){
        // no nodes in tree yet:
        root = null;
    }
    // find(int key): finds the node with a given key
    public Node find(int key){

        // assumes this is a non-empty tree:
        // start at the root
        Node current = root;

        // while theres no match at the root;
        // check the left and the right
        while(current.iData != key){

            // go left:
            if(key < current.iData){
                current = current.leftChild;
            } else {
                // go right:

                current = current.rightChild;
            }

            // if current becomes equal to null, we couldnt find the next child
            // node in the sequence, we've reached the end of the line without finding
            // the node we were looking for: so it can't exist.
            if(current == null)
                return null; // didnt find it doesnt exist
        }
        return current; // found it
    } // ends find

    // insert():
    public void insert(int iD, double dD){

        // make new node:
        // insert data:
        Node newNode = new Node();
        newNode.iData = iD;
        newNode.dData = dD;

        // now starting at root check if bst is empty:
        // no node in root insert @ this point if so
        if(root == null){
            root = newNode;
        } else {

            // roots occupied find the path of insertion
            // start at the root
            Node current = root;
            Node parent;

            while(true){

                // this will exit internally
                parent = current;

                // go left first:
                if(iD < current.iData){
                    current = current.leftChild;

                    // if it's the end of the line: insert on the left
                    if(current == null){
                        parent.leftChild = newNode;
                        return;
                    }
                } // end if go left
                else {
                    current = current.rightChild;

                    // if you've reached the end of the line insert at the right
                    if(current == null){
                        parent.rightChild = newNode;
                        return;
                    }
                } // ends else go right
            } // ends while
        } // end else not root
    } // ends insert

    // delete():
    // the first part of this is similar to find + insert methods.
    // you need to find the node to be deleted so that we can modify the child field.
    public boolean delete(int key){

       // assume the list is not empty
       Node current = root;
       Node parent = root;
       boolean isLeftChild = true;

       // start to search for the node:
        while(current.iData != key){

            parent = current;

            // go left:
            if(key < current.iData){

                // we've determined its on the left side of the tree
                isLeftChild = true;
                current = current.leftChild; // iterate to the next left node
            } else {

                // go right:
                isLeftChild = false;
                current = current.rightChild;
            }

            // end of the line, we didnt find it
            if(current == null){
                return false;
            }
        } // ends while

            // found node to delete
            // first we check to verify that is has no children.
            // if true we check the special case of the root.
            // if thats the node ot be deleted, we simply set it to null;
            // this empties the tree. otherwise we set the parents
            // leftchid or rightchild field to null to disconnect the parent from the node

            // * 1
            // NO CHILD: if no children, simply delete it:
            if(current.leftChild == null && current.rightChild == null){
                // if root:
                // tree is empty:
                if(current == root){

                    // tree is empty
                    root = null;
                } else if(isLeftChild){

                    // disconnect from parent
                    parent.leftChild = null;
                } else {
                    parent.rightChild = null;
                } // ends else
            }  // ends if

            // * 2
                // ONE CHILD: if no right child, replace with left subtree:
                else if(current.rightChild == null){
                    if(current == root){
                        root = current.leftChild;
                    } else if(isLeftChild){
                        parent.leftChild = current.leftChild;
                    } else {
                        parent.rightChild = current.leftChild;
                    }
                }


                // ONE CHILD: if no left child, replace with the right subtree
                else if(current.leftChild == null){
                    if(current == root){
                        root = current.leftChild;
                    } else if(isLeftChild){
                        parent.leftChild = current.leftChild;
                    } else {
                        parent.rightChild = current.leftChild;
                    }
                }


                // 4 *
                // two childen, so replace with inorder successor:
                else {

                    // 3 *
                    // get successor of node to delete(current)
                    Node successor = getSuccessor(current);

                    // connect parent of current to successor instead:
                    if (current == root) {
                        root = successor;
                    } else if(isLeftChild){
                        parent.leftChild = successor;
                    } else {
                        parent.rightChild = successor;

                        // connect succesor to currents left child
                        successor.leftChild = current.leftChild;
                    } // end else two children
                }
                // successor cannot have a left child
                return true;
            // if no right child, replace with left subtree:
    } // ends delete

    // getSuccessor(): returns node with next-highest value after delNode
    // goes to right child, then right childs left descendants
    private Node getSuccessor(Node deleteNode) {

        Node successorParent = deleteNode;
        Node successor = deleteNode;
        Node current = deleteNode.rightChild; // go to right child

        while(current != null){
            successorParent = successor;
            successor = current;

            // go to left child:
            current = current.leftChild;
        }

        if(successor != deleteNode.rightChild){
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = deleteNode.rightChild;
        }
        return successor;
    }

    // traverse: in order + to visit is to display the contents of the node
    // due to the fns recursive nature, it must have a base case
    // this happens when its null
    private void inOrderTraversal(Node localRoot){

        if(localRoot != null){

            // pass the left child to be traversed through
            inOrderTraversal(localRoot.leftChild);

            System.out.println(localRoot.iData + " ");

            // pass the right child
            inOrderTraversal(localRoot.rightChild);
        }
    } // ends in order traversal

    // display():
    public void displayTree() {

        Stack globalStack = new Stack();
        globalStack.push(root);
        int nBlanks = 32;
        boolean isRowEmpty = false;
        System.out.println("---------------------------------------------");

        while (isRowEmpty == false) {
            Stack localStack = new Stack();
            isRowEmpty = true;

            for (int j = 0; j < nBlanks; j++) {
                System.out.print(' ');
            }

            while (globalStack.isEmpty() == false) {

                Node temp = (Node) globalStack.pop();
                if (temp != null) {
                    System.out.print(temp.iData);
                    localStack.push(temp.leftChild);
                    localStack.push(temp.rightChild);

                    if (temp.leftChild != null || temp.rightChild != null)
                        isRowEmpty = false;
                } else {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < nBlanks * 2 - 2; j++) {
                    System.out.print(" ");
                }
            } // ends while global stack is empty

                System.out.println();
                nBlanks /= 2;

                while (localStack.isEmpty() == false)
                    globalStack.push(localStack.pop());


            System.out.println("---------------------------");
        } // ends while
    }// ends display tree
} // end class Tree

////////////////////////////////////////////////////////////////////////////
public class TreeApp {

    public static void main(String[] args) throws IOException {

        int value;

        // create tree:
        Tree theTree = new Tree(); // make a tree

        // insert into tree:
        theTree.insert(50, 1.5);
        theTree.insert(25, 1.7);
        theTree.insert(75, 1.9);
        theTree.insert(12, 1.5);
        theTree.insert(37, 1.2);
        theTree.insert(43, 1.7);
        theTree.insert(30, 1.5);
        theTree.insert(33, 1.2);
        theTree.insert(87, 1.7);
        theTree.insert(93, 1.5);
        theTree.insert(97, 1.5);

        System.out.println("display: ");
        theTree.displayTree();

        // FIND:
        Node found = theTree.find(93);

        if (found != null) {
            System.out.print("Found: ");
            found.displayNode();
            System.out.print("\n");

        } else
            System.out.print("Could not find ");

        System.out.print(93 + "\n");

        //DELETE LEAF WITH NO CHILDREN:
        boolean didDelete = theTree.delete(97);
        if(didDelete)
            System.out.print("Deleted 97 " + "\n");
        else
            System.out.print("Could not delete ");
        System.out.print(97 + "\n");
        theTree.displayTree();

        //DELETE NODE WITH TWO CHILDREN:
        didDelete = theTree.delete(37);
        if(didDelete)
            System.out.print("Deleted 37 " + "\n");
        else
            System.out.print("Could not delete ");
        System.out.print(37 + "\n");
        theTree.displayTree();



    } // end main()

    // -------------------------------------------------------------
    public static String getString() throws IOException
    {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        return s;
    }
    // -------------------------------------------------------------
    public static char getChar() throws IOException
    {
        String s = getString();
        return s.charAt(0);
    }
    //-------------------------------------------------------------
    public static int getInt() throws IOException
    {
        String s = getString();
        return Integer.parseInt(s);
    }
} // ends TreeApp
