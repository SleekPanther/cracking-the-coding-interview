package H_8_RecursionAndDynamicProgramming;

import java.util.*;

public class RecursiveAndDynamicProgramming {
	//8.1 A child is running up a staircase with n steps and can hop either 1 step, 2 steps, or 3 steps at a time. Implement a method to count how many possible ways the child can run up the stairs.
	//I solved the more advanced case where left & right feet are different
	public static int tripleStepPermutations(int numSteps){
		int[] memo = new int[numSteps+1];
		memo[0]=1;
		memo[1]=2;
		memo[2]=4;

		for(int i=3; i<=numSteps; i++){
			memo[i]=2*memo[i-1] + 2*memo[i-2] + 2*memo[i-3];
		}
		return memo[numSteps];
	}
	public static int tripleStepPermutationsRecursive(int numSteps){
		if(numSteps==0){
			return 1;
		}
		if(numSteps==1){	//right or left
			return 2;
		}
		if(numSteps==2){	//rr ll r2 l2
			return 4;
		}
		// if(numSteps==3){	//r3 l3 	rrr rll rr2 rl2 	lrr lll lr2 ll2		r2r r2l 	l2r l2l 	
		// 	return 14;
		// }
		return 2*tripleStepPermutationsRecursive(numSteps-1)	//1 step left or right
			+ 2*tripleStepPermutationsRecursive(numSteps-2)
			+ 2*tripleStepPermutationsRecursive(numSteps-3);
	}

	//8.2 Robot in a Grid: Imagine a robot sitting on the upper left corner of grid with r rows and c columns. The robot can only move in two directions, RIGHT and DOWN, but certain cells are "off limits" such that the robot cannot step on them. Design an algorithm to find a path for the robot from the top left to the bottom right.
	public static void robotInAGridTest(){
		//0 is clear, 1 means wall
		int[][] grid = new int[][]{
			{0, 0, 0, 0},
			{0, 1, 0, 1},
			{1, 0, 0, 0},
		};
		robotInAGrid(grid);
	}
	private static void robotInAGrid(int[][] grid){
		ArrayList<int[]> path = new ArrayList<int[]>();
		int yCurrent=grid.length-1;
		int xCurrent=grid[0].length-1;

		//Start in bottom right & work backwards up to starting location
		boolean changedAtLastIteration = true;	//loop termination variable
		while(changedAtLastIteration){
			changedAtLastIteration=false;
			if(yCurrent-1>=0 && grid[yCurrent-1][xCurrent]==0){		//must check that preious row is still in bounds 1st
				path.add(new int[]{yCurrent, xCurrent});
				changedAtLastIteration=true;
				yCurrent--;
			}
			else if(xCurrent-1>=0 && grid[yCurrent][xCurrent-1]==0){
				path.add(new int[]{yCurrent, xCurrent});
				changedAtLastIteration=true;
				xCurrent--;
			}
		}
		if(yCurrent!=0 || xCurrent!=0){		//didn't reach upper left
			System.out.println("No Path");
			return;
		}
		else{
			path.add(new int[]{0, 0});
		}

		//Print path in forward direction since created backwards
		for(int i=path.size()-1; i>=0; i--){
			System.out.println(Arrays.toString(path.get(i)));
		}
	}

	//8.3 Magic Index: A magic index in an array A [ 0 ... n -1] is defined to be an index such that A[ i] = i. Given a sorted array of distinct integers, write a method to find a magic index, if one exists, in array A.
	//Didn't get working
	// private static int magicIndexNonDistinct(int[] array){
	// 	int left=0;
	// 	int right=array.length-1;
	// 	while(left<=right){
	// 		int mid = (right+left)/2;
	// 		int value = array[mid];
	// 		if(array[mid]==mid){	//found magic index
	// 			return mid;
	// 		}
	// 		if(array[mid]<mid){	//discard 
	// 			left = Math.max(mid+1, mid);
	// 			// left++;
	// 		}
	// 		else{
	// 			right = Math.min(mid-1, mid);
	// 			// right--;
	// 		}
	// 	}
	// 	return -1;
	// }
	private static int magicIndexDistinct(int[] array){
		int left=0;
		int right=array.length-1;
		while(left<=right){
			int mid = (right+left)/2;
			if(array[mid]==mid){	//found magic index
				return mid;
			}
			if(array[mid]>mid){	//discard 
				right = mid-1;
			}
			else{
				left = mid+1;
			}
		}
		return -1;
	}
	public static void magicIndexTest(){
		int[] array1 = new int[]{-2, 0, 1, 2, 4, 55, 60};
		System.out.println(Arrays.toString(array1)+" Magic index = "+magicIndexDistinct(array1));

		int[] array2 = new int[]{-1, 0, 2, 10, 11};
		System.out.println(Arrays.toString(array2)+" Magic index = "+magicIndexDistinct(array2));

		//non destinct didn't work
		// int[] array3 = new int[]{-100, -90, -80, -70, -60, 1, 6, 70, 90 };
		// int[] array3 = new int[]{-10, -5, 2, 2, 2, 3, 4, 70, 90, 102, 130 };
		// System.out.println(Arrays.toString(array3)+" Magic index = "+magicIndexNonDistinct(array3));
	}

	//8.4 Power Set: Write a method to return all subsets of a set.
	public static ArrayList<ArrayList<Integer>> powerSet(int[] set){
		ArrayList<ArrayList<Integer>> powerSet = new ArrayList<ArrayList<Integer>>();
		powerSet.add(new ArrayList<Integer>());		//add empty set

		//Start from beginning of the list & insert the end of the list into existing subsets
		for(int end = 0; end<set.length; end++){
			int size = powerSet.size();		//get current number of subsets since the next loop updates powerSet.size()
			for(int i=0; i<size; i++){	//insert the tail elemnt of the list into all subsets
				ArrayList<Integer> subset = powerSet.get(i);

				ArrayList<Integer> newSet = new ArrayList<Integer>();
				newSet.addAll(subset);
				newSet.add(set[end]);
				powerSet.add(newSet);
			}
		}
		return powerSet;
	}

	public static void main(String[] args) {
		System.out.println("8.1 A child is running up a staircase with n steps and can hop either 1 step, 2 steps, or 3 steps at a time. Implement a method to count how many possible ways the child can run up the stairs.");
		System.out.println("Ways to climb 5 stairs: "+RecursiveAndDynamicProgramming.tripleStepPermutations(5));

		System.out.println("\n8.2 Robot in a Grid: Imagine a robot sitting on the upper left corner of grid with r rows and c columns. The robot can only move in two directions, right and down, but certain cells are 'off limits' such that the robot cannot step on them. Design an algorithm to find a path for the robot from the top left to the bottom right.");
		RecursiveAndDynamicProgramming.robotInAGridTest();

		System.out.println("\n8.3 Magic Index: A magic index in an array A [ 0 ... n -1] is defined to be an index such that A[ i] = i. Given a sorted array of distinct integers, write a method to find a magic index, if one exists, in array A.");
		RecursiveAndDynamicProgramming.magicIndexTest();

		System.out.println("\n8.4 Power Set: Write a method to return all subsets of a set.");
		System.out.println("PowerSet of {0, 1, 2, 3} = "+RecursiveAndDynamicProgramming.powerSet(new int[]{0, 1, 2, 3}));
	}
}