package D_4_TreesAndGraphs;

import java.util.*;

class NodeBinaryInt{
	public int name;
	public NodeBinaryInt left;
	public NodeBinaryInt right;
	
	public NodeBinaryInt(int name){
		this.name=name;
	}
	
	@Override
	public String toString(){
		return name + "";
	}
}

public class TreesAndGraphs {

	//4.2 Given a sorted (increasing order) array with unique integer elements, write an algorithm to create a binary search tree with minimal height.
	public static void minimalTree(int[] list){
		NodeBinaryInt root = minimalTree(list, 0, list.length-1);
		//System.out.println("done");	//for breakpoint
	}
	
	//Pick middle element from sub-array
	private static NodeBinaryInt minimalTree(int[] list, int left, int right){
		if(right<left){
			return null;
		}

		int mid = (left + right) /2;	//integer division
		System.out.println(list[mid]);
		
		NodeBinaryInt node = new NodeBinaryInt(list[mid]);
		node.left=minimalTree(list, left, mid-1);
		node.right=minimalTree(list, mid+1, right);		
		return node;
	}

	//4.3 Given a binary tree, design an algorithm which creates a linked list of all the nodes at each depth (e.g., if you have a tree with depth D, you'll have D linked lists).
	public static void ListDepths(NodeBinaryInt root, ArrayList<LinkedList<NodeBinaryInt>> depths, int currentDepth){
		if(root==null){
			return;
		}

		//Surround with try/catch in case a LinkedList @ that level doesn't exist yet
		//Add current node at current level
		try{
			depths.get(currentDepth).add(root);
		}
		catch(IndexOutOfBoundsException e){
			depths.add(new LinkedList<NodeBinaryInt>());
			depths.get(currentDepth).add(root);
		}

		//increment depth & search left & right
		currentDepth++;
		ListDepths(root.left, depths, currentDepth);
		ListDepths(root.right, depths, currentDepth);
	}

	private static void ListDepthsTest(){
		NodeBinaryInt root = new NodeBinaryInt(4);
		NodeBinaryInt l1 = new NodeBinaryInt(2);
		NodeBinaryInt r1 = new NodeBinaryInt(6);
		root.left=l1;
		root.right=r1;
		NodeBinaryInt ll2 = new NodeBinaryInt(1);
		NodeBinaryInt lr2 = new NodeBinaryInt(50);
		l1.left=ll2;
		l1.right=lr2;
		NodeBinaryInt rr2 = new NodeBinaryInt(10);
		r1.right=rr2;
		NodeBinaryInt rrl3 = new NodeBinaryInt(-2);
		rr2.left=rrl3;
		ArrayList<LinkedList<NodeBinaryInt>> depths = new ArrayList<LinkedList<NodeBinaryInt>>();
		TreesAndGraphs.ListDepths(root, depths, 0);
		for(int i=0; i<depths.size(); i++){
			System.out.print("Level "+i+": ");
			System.out.println(depths.get(i));
		}
	}

	//4.4 Implement a function to check if a binary tree is balanced. For the purposes of this question, a balanced tree is defined to be a tree such that the heights of the two subtrees of any node never differ by more than one.
	//works for just a root (empty tree) or root with just 1 child
	//not sure if my version is as efficicent as the improved version from the book
	private static boolean isBalancedTree(NodeBinaryInt root){
		System.out.println("Left height="+getHeight(root.left, 0));
		System.out.println("Right height="+getHeight(root.right, 0));
		return Math.abs(getHeight(root.left, 0) - getHeight(root.right, 0)) <= 1;	//heights differ by @ most 1
	}
	private static int getHeight(NodeBinaryInt root, int currentDepth){
		if(root==null){
			return currentDepth;
		}

		currentDepth++;		//increment depth since root is not null meaning we went down 1 layer
		return Math.max(getHeight(root.left, currentDepth), getHeight(root.right, currentDepth));	//pick larger height of each subtree
	}
	public static void isBalancedTreeTest(){
		NodeBinaryInt root = new NodeBinaryInt(5);
		NodeBinaryInt l1 = new NodeBinaryInt(3);
		NodeBinaryInt r1 = new NodeBinaryInt(8);
		root.left=l1;
		root.right=r1;
		NodeBinaryInt ll2 = new NodeBinaryInt(2);
		l1.left=ll2;
		NodeBinaryInt lll3 = new NodeBinaryInt(1);		//comment out to create a Balanced tree
		ll2.left=lll3;
		System.out.println("valid? "+isBalancedTree(root));
	}


	//4.5 Validate BST: Implement a function to check if a binary tree is a binary search tree.
	//Not checking for balanced-ness
	public static boolean isValidBST(NodeBinaryInt root, Integer min, Integer max){
		if(root==null){
			return true;
		}

		if( (min!=null && root.name <= min) || (max!=null && root.name> max)){
			return false;
		}

		//Started with checking only valid subtrees, but didn't work for making sure a child was on the right side compared to a higher up parent
		//This is really to close to validating a heap
		// //Check if left should be on the right
		// if(root.left!=null){
		// 	if(root.left.name > root.name || (max!=null && root.left.name < max)){
		// 		return false;
		// 	}
		// }
		// //Check if right should be on the left
		// if(root.right!=null){
		// 	if(root.right.name < root.name || (min!=null && root.right.name > min)){
		// 		return false;
		// 	}
		// }
		// //Check if left is > right
		// if(root.left!=null && root.right!=null){
		// 	if(root.left.name > root.right.name){
		// 		return false;
		// 	}
		// }

		return isValidBST(root.left, min, root.name) && isValidBST(root.right, root.name, max);
	}
	public static void isValidBSTTest(){
		NodeBinaryInt root = new NodeBinaryInt(5);
		NodeBinaryInt l1 = new NodeBinaryInt(3);
		NodeBinaryInt r1 = new NodeBinaryInt(8);
		root.left=l1;
		root.right=r1;
		NodeBinaryInt ll2 = new NodeBinaryInt(3);
		NodeBinaryInt lr2 = new NodeBinaryInt(4);	//2==> false
		l1.left=ll2;
		l1.right=lr2;
		System.out.println("Valid? "+isValidBST(root, null, null));
	}

	//4.6 Write an algorithm to find the 'next' node (i.e., in-order successor) of a given node in a binary search tree. You may assume that each node has a link to its parent.
	//Didn't check a ton of edge cases but seems simpler than the book's solution
	public static NodeBinaryInt successor(NodeBinaryInt root, NodeBinaryInt parent){
		if(root==null){
			return parent;
		}

		//Successor is leftmost of right subtree. So go right, then as far left as possible passing on the current node to 1 level down knows its parent 
		return successor(successor(root.right, root).left, root);
	}
	public static void successorTest(){
		NodeBinaryInt root = new NodeBinaryInt(60);
		NodeBinaryInt l1 = new NodeBinaryInt(40);
		NodeBinaryInt r1 = new NodeBinaryInt(80);
		root.left=l1;
		root.right=r1;
		NodeBinaryInt rl2 = new NodeBinaryInt(90);
		NodeBinaryInt rr2 = new NodeBinaryInt(100);
		r1.left=rl2;
		r1.right=rr2;
		NodeBinaryInt rll3 = new NodeBinaryInt(70);
		rl2.left=rll3;

		NodeBinaryInt rllr4 = new NodeBinaryInt(75);
		rll3.right=rllr4;

		NodeBinaryInt source = root;
		NodeBinaryInt successor = successor(source, null);
		if(source.name==successor.name){
			System.out.println(source.name+" has no successor");
		}
		else{
			System.out.println("Successor of "+source.name+" is "+successor(source, null).name);
		}		
	}

	public static void main(String[] args) {
		System.out.println("4.2 Given a sorted (increasing order) array with unique integer elements, write an algorithm to create a binary search tree with minimal height.");
		System.out.println("Minimal tree creation order");
		TreesAndGraphs.minimalTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});

		System.out.println("\n4.3 Given a binary tree, design an algorithm which creates a linked list of all the nodes at each depth (e.g., if you have a tree with depth D, you'll have D linked lists).");
		TreesAndGraphs.ListDepthsTest();

		System.out.println("\n4.4 Implement a function to check if a binary tree is balanced. For the purposes of this question, a balanced tree is defined to be a tree such that the heights of the two subtrees of any node never differ by more than one.");
		TreesAndGraphs.isBalancedTreeTest();

		System.out.println("\n4.5 Validate BST: Implement a function to check if a binary tree is a binary search tree.");
		TreesAndGraphs.isValidBSTTest();

		System.out.println("\n4.6 Write an algorithm to find the 'next' node (i.e., in-order successor) of a given node in a binary search tree. You may assume that each node has a link to its parent.");
		TreesAndGraphs.successorTest();
	}

}