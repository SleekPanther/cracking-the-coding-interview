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

	public static void main(String[] args) {
		System.out.println("8.1 A child is running up a staircase with n steps and can hop either 1 step, 2 steps, or 3 steps at a time. Implement a method to count how many possible ways the child can run up the stairs.");
		System.out.println("Ways to climb 5 stairs: "+RecursiveAndDynamicProgramming.tripleStepPermutations(5));

		System.out.println("\n8.2 Robot in a Grid: Imagine a robot sitting on the upper left corner of grid with r rows and c columns. The robot can only move in two directions, right and down, but certain cells are 'off limits' such that the robot cannot step on them. Design an algorithm to find a path for the robot from the top left to the bottom right.");
		RecursiveAndDynamicProgramming.robotInAGridTest();
	}
}