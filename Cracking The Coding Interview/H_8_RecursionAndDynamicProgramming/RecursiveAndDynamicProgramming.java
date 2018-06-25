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
			for(int i=0; i<size; i++){	//insert the tail element of the list into all subsets
				ArrayList<Integer> subset = powerSet.get(i);

				ArrayList<Integer> newSet = new ArrayList<Integer>();
				newSet.addAll(subset);
				newSet.add(set[end]);
				powerSet.add(newSet);
			}
		}
		return powerSet;
	}

	//8.5 Write a recursive function to multiply two positive integers without using the *operator.You can use addition, subtraction, and bit shifting, but you should minimize the number of those operations.
	//I did an iterative version instead
	public static int productIterative(int a, int b){
		System.out.print(a+"*"+b+" = ");

		int ans=0;
		boolean negate = false;
		if(a<0 || b<0){
			negate = true;
		}

		//find larger number x & add it to the total y times
		if(a>b){
			if(b<0){
				b=-b;
			}
			for(; b>0; b--){
				ans+=a;
			}
		}
		else{
			if(a<0){
				a=-a;
			}
			for(; a>0; a--){
				ans+=b;
			}
		}

		if(negate){
			return -ans;
		}

		return ans;
	}



	//8.7 Permutations without Dups: Write a method to compute all permutations of a string of unique characters.
	public static ArrayList<String> permutation(String str){
		ArrayList<String> permutations = new ArrayList<String>();
		permutations.add("");		//add empty string permutation

		//Start from beginning of the list & insert the end of the list into existing subsets
		for(int index = 0; index<str.length(); index++){
			int size = permutations.size();		//get current number of permutations since the next loop updates permutations.size()
			for(int i=0; i<size; i++){
				String oldPermutation = permutations.get(i);

				//Premend the current character to the beginning of the permutation
				String prependedNewPermutation = str.charAt(index)+oldPermutation;
				permutations.set(i, prependedNewPermutation);

				StringBuilder newPermutation = new StringBuilder();		//stringBuilder to allow insertion
				for(int j=1; j<=oldPermutation.length(); j++){	//start @ 1 & go 1 beyond the "end" of the old permutations inserting the current character to make a new permutation
					newPermutation.setLength(0);	//"clear" any previous value
					newPermutation.append(oldPermutation);	//start with original permutations
					newPermutation.insert(j, str.charAt(index)+"");		//insert the current character at the current index giving a new permutation
					permutations.add(newPermutation.toString());
				}
			}
		}
		return permutations;
	}

	//8.9 Parens: Implement an algorithm to print all valid (e.g., properly opened and closed) combinations of n pairs of parentheses.
	//breaks after n=4
	// public static ArrayList<String> parenthesesCombinations(int numParenthesesPairs){
	// 	if(numParenthesesPairs==0){
	// 		return new ArrayList<String>(Arrays.asList(""));
	// 	}

	// 	ArrayList<String> combinations = new ArrayList<String>(Arrays.asList("()"));
	// 	for(int i=1; i<numParenthesesPairs; i++){
	// 		int size = combinations.size();		//get size before adding anything new

	// 		String oldCombination = combinations.get(0);
	// 		combinations.set(0, "()"+oldCombination);
	// 		combinations.add("("+oldCombination+")");

	// 		for(int j=1; j<size; j++){	//skip 1st permutation composed of ()()()()()...
	// 			oldCombination = combinations.get(j);
	// 			combinations.set(j, "()"+oldCombination);
	// 			combinations.add("("+oldCombination+")");
	// 			combinations.add(oldCombination+"()");
	// 		}
	// 	}

	// 	return combinations;
	// }

	//8.10 Paint Fill: Implement the 'paint fill' function that one might see on many image editing programs. That is, given a screen (represented by a two-dimensional array of colors), a point, and a new color, fill in the surrounding area until the color changes from the original color");
	public static void paintFillTest(){
		int[][] canvas = new int[][]{
			{1, 0, 1, 1},
			{0, 0, 2, 1},
			{0, 0, 1, 1},
			{1, 0, 1, 1}
		};
		paintFill(canvas, 1, 1, 0, 9);
		for(int[] row : canvas){
			System.out.println(Arrays.toString(row));
		}
	}
	//O(n*m)	worst case colors entire grid	O(8) constant work at each pixel to check surrounding pixels
	private static void paintFill(int[][] canvas, int yPosition, int xPosition, int oldColor, int newColor){
		if(canvas[yPosition][xPosition]!=oldColor){		//stop searching when reached a color that's not the original color
			return;
		}
		canvas[yPosition][xPosition] = newColor;
		//Loop over all neighbors excluding current pixel and color if new pixel matches old color
		for(int y=yPosition-1; y<=yPosition+1; y++){
			for(int x=xPosition-1; x<=xPosition+1; x++){
				if( !(y==yPosition && x==xPosition)			//skip the spot we just colored
						&& (y>=0 && x>=0)					//check bounds
						&& (y<canvas.length && x<canvas[0].length) ){	//check bounds
					if(canvas[y][x]==oldColor){		//only color pixels that match the old color
						paintFill(canvas, y, x, oldColor, newColor);
					}
				}
			}
		}
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

		System.out.println("\n8.5 Write a recursive function to multiply two positive integers without using the *operator.You can use addition, subtraction, and bit shifting, but you should minimize the number of those operations.");
		System.out.println("Iterative approach");
		System.out.println(RecursiveAndDynamicProgramming.productIterative(4, 3));
		System.out.println(RecursiveAndDynamicProgramming.productIterative(3, -4));
		System.out.println(RecursiveAndDynamicProgramming.productIterative(-3, 4));
		System.out.println(RecursiveAndDynamicProgramming.productIterative(-3, -4));
		System.out.println(RecursiveAndDynamicProgramming.productIterative(0, -4));
		System.out.println(RecursiveAndDynamicProgramming.productIterative(0, 4));
		System.out.println(RecursiveAndDynamicProgramming.productIterative(2, 0));
		System.out.println(RecursiveAndDynamicProgramming.productIterative(-2, 0));
		System.out.println(RecursiveAndDynamicProgramming.productIterative(0, 0));
	
		//8.6 Towers of Hanoi

		System.out.println("\n8.7 Permutations without Dups: Write a method to compute all permutations of a string of unique characters.");
		System.out.println(RecursiveAndDynamicProgramming.permutation("abcd"));

		//8.8 Permutations with Dups

		// System.out.println("\n8.9 Parens: Implement an algorithm to print all valid (e.g., properly opened and closed) combinations of n pairs of parentheses.");
		// System.out.println(RecursiveAndDynamicProgramming.parenthesesCombinations(3));
		// System.out.println(RecursiveAndDynamicProgramming.parenthesesCombinations(3).size());

		System.out.println("\n8.10 Paint Fill: Implement the 'paint fill' function that one might see on many image editing programs. That is, given a screen (represented by a two-dimensional array of colors), a point, and a new color, fill in the surrounding area until the color changes from the original color");
		RecursiveAndDynamicProgramming.paintFillTest();
	}
}