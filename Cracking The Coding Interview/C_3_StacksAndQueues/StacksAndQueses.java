package C_3_StacksAndQueues;

import java.util.*;

public class StacksAndQueses {
	
	//3.3 Stack of Plates: Imagine a (literal) stack of plates. If the stack gets too high, it might topple. Therefore, in real life, we would likely start a new stack when the previous stack exceeds some threshold. Implement a data structure SetOfStacks that mimics this. SetO-fStacks should be composed of several stacks and should create a new stack once the previous one exceeds capacity. SetOfStacks. push() and SetOfStacks. pop() should behave identically to a single stack (that is, pop () should return the same values as it would if there were just a single stack).
	//didn't implement popAt()
	static class SetOfStacks{
		private int size = 0;
		private int threshhold;		//how full each stack can be
		private ArrayList<LinkedList<Integer>> stacks = new ArrayList<LinkedList<Integer>>();
		private int currentStack = 0;

		public SetOfStacks(int threshhold){
			this.threshhold=threshhold;
			stacks.add(new LinkedList<Integer>());
		}

		public void push(int item){
			//Add new sub-stack if threshhold is met
			if(stacks.get(currentStack).size() == threshhold){
				stacks.add(new LinkedList<Integer>());
				currentStack++;
			}
			//always add to current stack & increment size
			stacks.get(currentStack).add(item);
			size++;
		}

		public int pop(){
			int item = stacks.get(currentStack).removeLast();	//remove the top item of the current stack
			//remove current stack if it's empty
			if(stacks.get(currentStack).isEmpty()){
				stacks.remove(currentStack);
				currentStack--;
			}
			size--;
			return item;
		}

		public boolean isEmpty(){
			return size == 0;
		}

		public void printAndEmpty(){
			while(!isEmpty()){
				System.out.println(pop());
			}
		}
	}
	public static void setOfStacksTest(){
		SetOfStacks stack = new SetOfStacks(3);
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);
		stack.push(5);
		
		stack.printAndEmpty();
	}


	public static void main(String[] args) {
		System.out.println("3.3 Stack of Plates: Imagine a (literal) stack of plates. If the stack gets too high, it might topple. Therefore, in real life, we would likely start a new stack when the previous stack exceeds some threshold. Implement a data structure SetOfStacks that mimics this. SetO-fStacks should be composed of several stacks and should create a new stack once the previous one exceeds capacity. SetOfStacks. push() and SetOfStacks. pop() should behave identically to a single stack (that is, pop () should return the same values as it would if there were just a single stack).");
		StacksAndQueses.setOfStacksTest();

	}
}
