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

	public static void main(String[] args) {
		System.out.println("4.2 Given a sorted (increasing order) array with unique integer elements, write an algorithm to create a binary search tree with minimal height.");
		System.out.println("Minimal tree creation order");
		TreesAndGraphs.minimalTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});

		System.out.println("\n4.3 Given a binary tree, design an algorithm which creates a linked list of all the nodes at each depth (e.g., if you have a tree with depth D, you'll have D linked lists).");
		NodeBinaryInt root = new NodeBinaryInt(4);
		NodeBinaryInt l1 = new NodeBinaryInt(2);
		NodeBinaryInt r1 = new NodeBinaryInt(6);
		root.left=l1;
		root.right=r1;
		NodeBinaryInt ll2 = new NodeBinaryInt(1);
		NodeBinaryInt rl2 = new NodeBinaryInt(50);
		l1.left=ll2;
		l1.right=rl2;
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

}