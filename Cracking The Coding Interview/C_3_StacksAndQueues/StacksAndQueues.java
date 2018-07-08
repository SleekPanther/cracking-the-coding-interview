package C_3_StacksAndQueues;

import java.util.*;

public class StacksAndQueues {	
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

	//3.5 Sort Stack: Write a program to sort a stack such that the smallest items are on the top. You can use an additional temporary stack, but you may not copy the elements into any other data structure (such as an array). The stack supports the following operations: push, pop, peek, and is Empty.
	public static void sortStack(LinkedList<Integer> stackToSort){
		System.out.println("\nOriginal");
		for(int i=stackToSort.size()-1; i>=0; i--){
			System.out.println(stackToSort.get(i));
		}

		LinkedList<Integer> tempStack = new LinkedList<Integer>();
		while(!stackToSort.isEmpty()){
			//Make sure right stack has something on it
			if(tempStack.isEmpty()){
				tempStack.add(stackToSort.removeLast());
			}

			int leftStackItem = stackToSort.getLast();
			int rightStackItem = tempStack.getLast();
			//If left<right, copy over since it's correct order
			if(leftStackItem <= rightStackItem){
				tempStack.add(stackToSort.removeLast());
			}
			else{	//swap tops of left & right
				tempStack.removeLast();
				tempStack.add(leftStackItem);
				stackToSort.removeLast();
				stackToSort.add(rightStackItem);
			}
		}

		System.out.println("Sorted");
		for(int i=tempStack.size()-1; i>=0; i--){
			System.out.println(tempStack.get(i));
		}
	}

	//3.6 Animal Shelter: An animal shelter, which holds only dogs and cats, operates on a strictly"first in, first out" basis. People must adopt either the "oldest" (based on arrival time) of all animals at the shelter, or they can select whether they would prefer a dog or a cat (and will receive the oldest animal of that type). They cannot select which specific animal they would like. Create the data structures to maintain this system and implement operations such as enqueue, dequeueAny, dequeueDog, and dequeueCat. You may use the built-in Linked list data structure.
	public static void animalShelterTest(){
		AnimalShelter shelter = new AnimalShelter();
		shelter.enqueue(new Animal(AnimalType.Cat, "C1"));
		shelter.enqueue(new Animal(AnimalType.Cat, "C2"));
		shelter.enqueue(new Animal(AnimalType.Dog, "D1"));
		shelter.enqueue(new Animal(AnimalType.Cat, "C3"));
		shelter.enqueue(new Animal(AnimalType.Dog, "D2"));
		
		System.out.println(shelter.dequeueAny());
		System.out.println(shelter.dequeueAny());
		System.out.println(shelter.dequeueAny());
		// System.out.println(shelter.dequeueDog());
		// System.out.println(shelter.dequeueCat());
	}
	enum AnimalType {Cat, Dog};
	static class AnimalShelter{
		private LinkedList<Animal> cats = new LinkedList<Animal>();
		private LinkedList<Animal> dogs = new LinkedList<Animal>();
		private int animalInsertionCounter = 0;

		public void enqueue(Animal animal){
			animal.insertOrder = animalInsertionCounter++;
			if(animal.type == AnimalType.Cat){
				cats.add(animal);
			}
			else if(animal.type == AnimalType.Dog){
				dogs.add(animal);
			}
		}

		public Animal dequeueDog(){
			return dogs.removeFirst();
		}
		public Animal dequeueCat(){
			return cats.removeFirst();
		}

		public Animal dequeueAny(){
			if(cats.getFirst().insertOrder < dogs.getFirst().insertOrder){
				return cats.removeFirst();
			}
			return dogs.removeFirst();
		}
	}
	static class Animal{
		public AnimalType type;
		public String name;
		public int insertOrder;

		public Animal(AnimalType type, String name){
			this.type=type;
			this.name=name;
		}

		@Override
		public String toString(){
			return type + " " + name + ": " + insertOrder;
		}
	}

	public static void main(String[] args) {
		System.out.println("3.3 Stack of Plates: Imagine a (literal) stack of plates. If the stack gets too high, it might topple. Therefore, in real life, we would likely start a new stack when the previous stack exceeds some threshold. Implement a data structure SetOfStacks that mimics this. SetO-fStacks should be composed of several stacks and should create a new stack once the previous one exceeds capacity. SetOfStacks. push() and SetOfStacks. pop() should behave identically to a single stack (that is, pop () should return the same values as it would if there were just a single stack).");
		StacksAndQueues.setOfStacksTest();

		System.out.println("\n3.4 Queue via Stacks: Implement a MyQueue class which implements a queue using two stacks.");
		StacksAndQueues.queueViaStacksTest();

		System.out.print("\n3.5 Sort Stack: Write a program to sort a stack such that the smallest items are on the top. You can use an additional temporary stack, but you may not copy the elements into any other data structure (such as an array). The stack supports the following operations: push, pop, peek, and is Empty.");
		StacksAndQueues.sortStack(new LinkedList<Integer>(Arrays.asList(1, 7, 9, 56, 3)));
		StacksAndQueues.sortStack(new LinkedList<Integer>(Arrays.asList(1, 2, 3)));
		StacksAndQueues.sortStack(new LinkedList<Integer>(Arrays.asList(3, 2, 1)));
		StacksAndQueues.sortStack(new LinkedList<Integer>(Arrays.asList(3, 2, 1, 5)));

		System.out.println("\n3.6 Animal Shelter: An animal shelter, which holds only dogs and cats, operates on a strictly'first in, first out' basis. People must adopt either the 'oldest' (based on arrival time) of all animals at the shelter, or they can select whether they would prefer a dog or a cat (and will receive the oldest animal of that type). They cannot select which specific animal they would like. Create the data structures to maintain this system and implement operations such as enqueue, dequeueAny, dequeueDog, and dequeueCat. You may use the built-in Linked list data structure.");
		StacksAndQueues.animalShelterTest();
	}
}