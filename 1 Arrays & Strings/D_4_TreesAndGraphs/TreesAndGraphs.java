package D_4_TreesAndGraphs;

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

	public static void main(String[] args) {
		System.out.println("4.2 Given a sorted (increasing order) array with unique integer elements, write an algorithm to create a binary search tree with minimal height.");
		System.out.println("Minimal tree creation order");
		TreesAndGraphs.minimalTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
	}

}