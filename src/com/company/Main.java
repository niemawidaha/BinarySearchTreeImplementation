package com.company;

import java.util.Stack;
import java.util.function.BinaryOperator;

public class Main {

    public static void main(String[] args) {

        // create tree:
        BinarySearchTree testTree = new BinarySearchTree();

        // insert values into tree:
        testTree.insert("A");
        testTree.insert("B");
        testTree.insert("D");
        testTree.insert("C");
        testTree.insert("E");
        testTree.insert("I");
        testTree.insert("F");

        // TEST 1: Delete with no children:
        testDeleteWithNoChild(testTree);

        // TEST 1: Delete with one child
        testDeleteWithOneChild(testTree);

        // TEST 3: Delete with two child
        testDeleteWithTwoChildren(testTree);

    } // ends main

    // TEST 1
    // tests deletion with no children on the tree with nodes from letters A-F
    // - Node to be deleted is "F"
    private static void testDeleteWithNoChild(BinarySearchTree testTree) {

        // tree before:
        // delete with no children:
        System.out.println("---------------------------------------------");
        System.out.println("TEST 1: DELETE WITH NO CHILDREN");
        testTree.displayTree();
        testTree.delete("F");

        // tree after:
        // display after del with no children:
        System.out.println("---------------------------------------------");
        System.out.println("Delete with no children: F node");
        testTree.displayTree();

    } // ends Test1

    // Test 2:
    // tests deletion with ONE child on the tree with nodes from letters A-I
    // - Node to be deleted is an "E"
    private static void testDeleteWithOneChild(BinarySearchTree testTree) {

        // tree before:
        // delete with one child:
        System.out.println("---------------------------------------------");
        System.out.println("TEST 2: DELETE WITH ONE CHILD");
        testTree.displayTree();
        testTree.delete("E");

        // tree after:
        // display after del with one child:
        System.out.println("---------------------------------------------");
        System.out.println("Delete with one child: E node");
        testTree.displayTree();

    } // ends Test2

    // Test 3:
    // tests deletion with TWO children on the tree with nodes from letters A-I
    // - Node to be deleted is an "D"
    private static void testDeleteWithTwoChildren(BinarySearchTree testTree) {

        // tree before:
        // delete with two children:
        System.out.println("---------------------------------------------");
        System.out.println("TEST 3: DELETE WITH TWO CHILDREN");
        testTree.displayTree();
        testTree.delete("D");

        // tree after:
        // display after del with one child:
        System.out.println("---------------------------------------------");
        System.out.println("Delete with two children: D node");
        testTree.displayTree();

    } // ends Test3
} // ends main
//////////////////////////////////////////////////////////////////////////

    // Node for a binary tree:
    class BTNode {
        //data
        private String username;
        
        //children
        private BTNode leftChild;
        private BTNode rightChild;
        
        //constructor
        public BTNode(String newName) {
            username = newName;
            leftChild = null;
            rightChild = null;
        }
        
        //get data
        public String getName() {
            return username;
        }
        public BTNode getLeft() {
            return leftChild;
        }
        public BTNode getRight() {
            return rightChild;
        }
        
        //set children
        public void setLeft(BTNode newChild) {
            leftChild = newChild;
        }
        public void setRight(BTNode newChild) {
            rightChild = newChild;
        }
    } // ends BTNode

//////////////////////////////////////////////////////////////////////////

class BinarySearchTree {

    // members:
    private BTNode root;
    boolean isLeft = false;     // used for the delete fns

    public BinarySearchTree() {
        root = null;
    }

    public BTNode getRoot() {
        return root;
    }

    public void insert(String newName) {
        BTNode newNode = new BTNode(newName);
        if (root == null) {
            root = newNode;
        }
        else {
            BTNode parent = root;
            BTNode current = root;
            boolean isLeft = false;
            while (current != null) {
                parent = current;
                if (newName.compareTo(current.getName())<0) {
                    current = current.getLeft();
                    isLeft = true;
                } else {
                    current = current.getRight();
                    isLeft = false;
                }
            }
            if (isLeft) {
                parent.setLeft(newNode);
            } else {
                parent.setRight(newNode);
            }
        }
    }

    public BTNode find(String toFind) {
        //we want to find a node with username toFind in the binary search tree.
        //if we find it, we'll return the node
        //otherwise, we'll return null

        //A.compareTo(B) < 0 if A comes before B, 0 if they're equal, > 0 if A comes after B
        BTNode current = root;
        while (current != null){
            int order = toFind.compareTo(current.getName());
            if (order == 0) {
                return current;
            } else if (order < 0){
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }
        System.out.println("No such node");
        return null; //didn't find it
    }

    public void delete(String toFind) {
        //setting up to search the tree
        BTNode current = root;
        BTNode parent = root;

        while (current != null){
            //how we compare the data
            int order = toFind.compareTo(current.getName());

            //we're looking for something smaller
            if (order < 0){
                isLeft = true; // determined its on the left side
                parent = current;
                current = current.getLeft();
            } else if (order > 0){
                //looking for something bigger

                isLeft = false;
                parent = current;
                current = current.getRight();
            } else {
                //found it
                BTNode lChild = current.getLeft();
                BTNode rChild = current.getRight();
                if (lChild == null && rChild == null) {
                    delWithNoChildren(current,parent);
                } else if (lChild == null || rChild == null) {
                    delWithOneChild(current,parent);
                } else {
                    delWithTwoChildren(current,parent);
                }
                break;
            }
        }
    }  // ends delete

    //A) delWithNoChildren
    private void delWithNoChildren(BTNode current, BTNode parent) {

        // if its at the root -> then tree is empty
        if(current == root){

            // tree is now empty:
            root = null;
        } else if(isLeft){
            // we've determined its the left child to the parent
            // disconnect left child from the parent:
            parent.setLeft(null);
        } else {
            // we've determined its the right child to the parent
            // disconnect right child from the parent:
            parent.setRight(null);
        } // ends else
    } // ends delWithNoChildren(current, parent)

    //B) delWithOneChild
    private void delWithOneChild(BTNode current, BTNode parent) {
        System.out.println("deleted with one child");

        // if no left child, replace with the right subtree
        if(current.getLeft() == null){
            if(current == root){
                root = current.getLeft();
            } else if(isLeft){
                parent.setLeft(current.getLeft());
            } else {
                parent.setRight(current.getRight());
            }
        } // ends if
    } // ends delWithOneChild(current, parent)
    
    //C) delWithTwoChildren
    private void delWithTwoChildren(BTNode current, BTNode parent) {

        // obtain successor of node to delete (current)
        BTNode currentSuccessor = getSuccessor(current);

        // connect parent of current to the successor instead:
        if(current == root){
            root = currentSuccessor;
        } else if(isLeft){
            parent.setLeft(currentSuccessor);
        } else {
            parent.setRight(currentSuccessor);

            // connect the successor to currents left child
            currentSuccessor.setLeft(current.getLeft());
        }
    }

    // getSucessor(): returns node with the next-highest value after delNode
    // goes to right child, then right childs left descendants
    private BTNode getSuccessor(BTNode deleteNode){

        BTNode successorParent = deleteNode;
        BTNode successor = deleteNode;
        BTNode current = deleteNode.getRight(); // visit the right child first

        while(current != null){
            successorParent = successor;
            successor = current;

            // visit  the left child:
            current = current.getLeft();
        }

        if(successor != deleteNode.getRight()){
            successorParent.setLeft(successor.getRight());
            successor.setRight(deleteNode.getRight());
        }
        return successor;
    }

    public void inOrder() {
        inOrderRecurse(root);
        System.out.println("");
    }

    public void inOrderRecurse(BTNode startNode) {
        if (startNode == null) {
            return;
        } else{
            inOrderRecurse(startNode.getLeft());
            System.out.print(startNode.getName()+" ");
            inOrderRecurse(startNode.getRight());
        }
    }

    public void preOrder() {
        preOrderRecurse(root);
        System.out.println("");
    }

    public void preOrderRecurse(BTNode startNode) {
        if (startNode == null) {
            return;
        } else{
            System.out.print(startNode.getName()+" ");
            preOrderRecurse(startNode.getLeft());
            preOrderRecurse(startNode.getRight());
        }
    }

    public void displayTree() {

        Stack globalStack = new Stack();
        globalStack.push(root);
        boolean isRowEmpty = false;
        System.out.println("---------------------------------------------");
        int i = 0;

        while(isRowEmpty == false){

            Stack localStack = new Stack();
            isRowEmpty = true;
            System.out.println("level: " + i);

            while(globalStack.isEmpty() == false){

                BTNode temp = (BTNode) globalStack.pop();

                if(temp != null){

                    // print current BTNode:
                    System.out.println(temp.getName());
                    localStack.push(temp.getLeft());
                    localStack.push(temp.getRight());

                    if(temp.getLeft() != null || temp.getRight() != null){
                        isRowEmpty = false;
                    }
                }
            } // ends while global stack is empty

            while(localStack.isEmpty() == false)
                globalStack.push(localStack.pop());

            i++; // increment the level
            System.out.println("---------------------------");
        } // ends while
    }  // ends displayTree()
} // ends Binary Search Tree class