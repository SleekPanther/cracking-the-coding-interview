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

	public static void main(String[] args) {
		System.out.println("A child is running up a staircase with n steps and can hop either 1 step, 2 steps, or 3 steps at a time. Implement a method to count how many possible ways the child can run up the stairs.");
		System.out.println("Ways to climb 5 stairs: "+RecursiveAndDynamicProgramming.tripleStepPermutations(5));
	}
}