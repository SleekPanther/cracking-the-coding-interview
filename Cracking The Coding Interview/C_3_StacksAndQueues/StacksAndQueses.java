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

	//3.4 Queue via Stacks: Implement a MyQueue class which implements a queue using two stacks.
	static class MyQueue{
		private LinkedList<Integer> newestStack = new LinkedList<Integer>();	//added to in push, always has newest on top
		private LinkedList<Integer> oldestStack = new LinkedList<Integer>();	//only used for pop/peek, reverses order from newestStack

		public void push(int item){
			newestStack.add(item);
		}

		public int pop(){
			balanceStacks();
			return oldestStack.removeLast();
		}

		public int peek(){
			balanceStacks();
			return oldestStack.getLast();
		}

		//copies items from newest stack & adds to oldestStack so that the oldest is now on top
		private void balanceStacks(){
			if(oldestStack.isEmpty()){
				while(!newestStack.isEmpty()){
					oldestStack.add(newestStack.removeLast());
				}
			}
		}
	}
	public static void queueViaStacksTest(){
		MyQueue queue = new MyQueue();
		queue.push(1);
		queue.push(2);
		queue.push(3);
		// queue.push(4);

		System.out.println(queue.pop());
		System.out.println(queue.pop());
		System.out.println(queue.pop());
		// System.out.println(queue.pop());

		queue.push(4);
		queue.push(5);
		System.out.println("peek = "+queue.peek());
		System.out.println(queue.pop());
		System.out.println(queue.pop());
	}

	public static void main(String[] args) {
		System.out.println("3.3 Stack of Plates: Imagine a (literal) stack of plates. If the stack gets too high, it might topple. Therefore, in real life, we would likely start a new stack when the previous stack exceeds some threshold. Implement a data structure SetOfStacks that mimics this. SetO-fStacks should be composed of several stacks and should create a new stack once the previous one exceeds capacity. SetOfStacks. push() and SetOfStacks. pop() should behave identically to a single stack (that is, pop () should return the same values as it would if there were just a single stack).");
		StacksAndQueses.setOfStacksTest();

		System.out.println("\n3.4 Queue via Stacks: Implement a MyQueue class which implements a queue using two stacks.");
		StacksAndQueses.queueViaStacksTest();
	}
}