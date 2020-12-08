# Binary Search Tree Implementation


Implementing a binary search tree: deleting a node 

We discussed deleting a node of a binary search tree in class, but we did not write code for it. 
The following will provide scaffolding for you to write the algorithm for deleting.
It is split into three parts, and you’ll write each as a separate method. 
I have provided an overarching delete method that will call each of these. 
Which one is called depends on whether the node we’re deleting has no children, one child, or two children.
In each of these, toDel is the node we want to delete. The node parent is toDel’s parent, which we will need access to.

(a) Write a method delWithNoChildren(BTNode toDel, BTNode parent) that deletes a node with no children.

(b) Write a method delWithOneChild(BTNode toDel, BTNode parent) that deletes a node with one child. 
Re- member that this child should become one of the children of the deleted node’s parent. 
(How do we know which child?)

(c) Write a method findSuccessorParent(BTNode toDel, BTNode parent) that finds the parent of toDel’s successor,
which is the node with the greatest value less than toDel’s. 
(For example, if the nodes in my tree held the letters A, B, F, H, T, and X, and I was deleting H, it’s successor would be F – it comes before H, 
but it comes after all the other nodes that come before H. We would want to return H’s parent.)

(d) Write a method delWithTwoChildren(BTNode toDel) that deletes a node with two children. 
This node should be replaced with its successor, so you should use findSuccessor to access the successor and its parent.
