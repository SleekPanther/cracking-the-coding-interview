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

	//4.7 Build Order: You are given a list of projects and a list of dependencies (which is a list of pairs of projects, where the second project is dependent on the first project). All of a project's dependencies must be built before the project is. Find a build order that will allow the projects to be built. If there is no valid build order, return an error.
	private static void buildOrder(String[] projectNames, String[][] dependencies){
		HashMap<String, Project> projects = new HashMap<String, Project>();
		PriorityQueue<Project> projectsByDependencyCount = new PriorityQueue<Project>();
		
		//Create objects with 0 dependencies
		for(String projectName : projectNames){
			projects.put(projectName, new Project(projectName));
		}

		//Populate objects with dependencies & dependents
		for(String[] dependencyPair : dependencies){
			String project = dependencyPair[0];
			String dependencyProject = dependencyPair[1];
			
			projects.get(dependencyProject).dependencies.add(projects.get(project));	//add dependency on 2nd project to the current one
			projects.get(project).dependents.add(projects.get(dependencyProject));
		}

		//create priority queue
		for(Project project : projects.values()){
			 projectsByDependencyCount.add(project);
		}

		//Attempt to build projects with 0 dependencies
		while(!projectsByDependencyCount.isEmpty()){
			Project project = projectsByDependencyCount.poll();
			//System.out.println(project);
			if(!project.dependencies.isEmpty()){
				System.out.println("No valid build order");
				break;
			}
			else{
				System.out.println(project);	//build the project
				//decrement references to built project for all dependents
				for(Project dependent : project.dependents){
					dependent.dependencies.remove(project);		//remove the dependency to the project just built
					//remove & re-add to priority queue to reorder & keep fewest dependencies @ the top
					projectsByDependencyCount.remove(dependent);
					projectsByDependencyCount.add(dependent);
				}
			}
		}
	}
	public static void buildOrderTest(){
		String[] projects = new String[] {"a", "b", "c", "d", "e", "f"};
		String[][] dependencies = new String[][]{ {"a", "d"},  {"f", "b"}, {"b", "d"},  {"f", "a"}, {"d", "c"}};
		runBuildOrderTest(projects, dependencies);

		String[] projects2 = new String[] {"a", "b", "c"};
		String[][] dependencies2 = new String[][]{ {"a", "b"},  {"b", "c"}, {"c", "a"}};
		System.out.println();
		runBuildOrderTest(projects2, dependencies2);
	}
	private static void runBuildOrderTest(String[] projects, String[][] dependencies){
		System.out.print("Valid Build order? ");
		System.out.println("Projects="+Arrays.toString(projects));
		System.out.println("Dependencies="+Arrays.deepToString(dependencies));
		buildOrder(projects, dependencies);
	}
	static class Project implements Comparable<Project>{
		public String name;
		public LinkedList<Project> dependencies = new LinkedList<Project>();
		public LinkedList<Project> dependents = new LinkedList<Project>();

		//Constructor for something with no dependents, OK to build
		public Project(String name){
			this(name, null, null);
		}
		public Project(String name, Project dependancy, Project dependent){
			this.name=name;
			if(dependancy!=null){
				dependencies.add(dependancy);
			}
			if(dependent!=null){
				dependents.add(dependent);
			}
		}

		@Override
		public int compareTo(Project other) {
			return this.dependencies.size() - other.dependencies.size();
		}

		@Override
		public String toString(){
			String str = name + " dependenCIES=" + dependencies.size() + ": ";
			if(!dependencies.isEmpty()){
				str+= "[";
				for(Project project : dependencies){
					str+= project.name + " ";
				}
				str+="]";
			}
			if(!dependents.isEmpty()){
				str+= " dependENTS=" + dependents.size() + ": ";
				str+= "[";
				for(Project project : dependents){
					str+= project.name + " ";
				}
				str+="]";
			}
			return str;
		}
	}

	//4.10 Check Subtree: Tl and T2 are two very large binary trees, with Tl much bigger than T2. Create an algorithm to determine if T2 is a subtree of Tl. A tree T2 is a subtree of Tl if there exists a node n in Tl such that the subtree of n is identical to T2. That is, if you cut off the tree at node n, the two trees would be identical.
	public static boolean checkSubtreeTest(){
		NodeBinaryInt T1 = new NodeBinaryInt(1);
		NodeBinaryInt T1l = new NodeBinaryInt(2);
		NodeBinaryInt T1r = new NodeBinaryInt(20);
		T1.left = T1l;
		T1.right = T1r;
		NodeBinaryInt T1ll = new NodeBinaryInt(3);
		NodeBinaryInt T1lr = new NodeBinaryInt(4);
		T1l.left = T1ll;
		T1l.right = T1lr;
		NodeBinaryInt T1lll = new NodeBinaryInt(5);
		T1ll.left = T1lll;

		NodeBinaryInt T1rl = new NodeBinaryInt(30);
		T1r.left = T1rl;
		NodeBinaryInt T1rr = new NodeBinaryInt(40);
		T1r.right = T1rr;
		NodeBinaryInt T1rll = new NodeBinaryInt(50);
		T1rl.left = T1rll;


		NodeBinaryInt T2 = new NodeBinaryInt(2);
		NodeBinaryInt T2l = new NodeBinaryInt(3);
		NodeBinaryInt T2r = new NodeBinaryInt(4);
		T2.left = T2l;
		T2.right = T2r;
		NodeBinaryInt T2ll = new NodeBinaryInt(5);
		T2l.left = T2ll;

		return checkSubtree(T1, T2);
	}
	private static boolean checkSubtree(NodeBinaryInt T1, NodeBinaryInt T2){
		if(T2==null	//empty tree is always a subtree
			|| T1==null && T2==null){	//2 empty subtrees always match, reached the bottom
			return true;
		}
		if(T1!=null){
			if(T1.name!=T2.name){	//If different, check if left or right subtree of T1 matches all of T2
				return checkSubtree(T1.left, T2) || checkSubtree(T1.right, T2);
			}
			if(T1.name==T2.name){	//If nodes match, check that left and right subtrees of both T1 & T2 also match
				return checkSubtree(T1.left, T2.left) && checkSubtree(T1.right, T2.right);
			}
		}
		return false;
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
		
		System.out.println("\n4.7 Build Order: You are given a list of projects and a list of dependencies (which is a list of pairs of projects, where the second project is dependent on the first project). All of a project's dependencies must be built before the project is. Find a build order that will allow the projects to be built. If there is no valid build order, return an error.");
		TreesAndGraphs.buildOrderTest();

		//4.8 First Common Ancestor: Design an algorithm and write code to find the first common ancestor of two nodes in a binary tree. Avoid storing additional nodes in a data structure. NOTE: This is not necessarily a binary search tree.

		//4.9 BST Sequences: A binary search tree was created by traversing through an array from left to right and inserting each element. Given a binary search tree with distinct elements, print all possible arrays that could have led to this tree.

		System.out.println("\n4.10 Check Subtree: Tl and T2 are two very large binary trees, with Tl much bigger than T2. Create an algorithm to determine if T2 is a subtree of Tl. A tree T2 is a subtree of Tl if there exists a node n in Tl such that the subtree of n is identical to T2. That is, if you cut off the tree at node n, the two trees would be identical.");
		System.out.println(TreesAndGraphs.checkSubtreeTest());
	}

}