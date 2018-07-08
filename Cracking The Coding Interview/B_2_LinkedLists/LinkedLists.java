package B_2_LinkedLists;

import java.util.*;

public class LinkedLists {
	static class Node{
		public Node next = null;
		public int data;

		public Node(int data){
			this.data = data;
		}

		@Override
		public String toString(){
			return data + "";
		}
	}
	static class LinkedListNew{
		public Node head = null;
		public Node tail = null;

		public void add(int data){
			Node temp = new Node(data);
			if(tail==null){
				tail=temp;
			}
			else{
				tail.next = temp;
				tail=tail.next;
			}

			if(head==null){
				head = temp;
			}
		}

		//doesn't update tail for 1 element?
		public boolean remove(int dataToRemove){
			if(head.data == dataToRemove){	//remove & advance head
				head = head.next;
				return true;
			}

			Node temp = head;
			while(temp.next!=null){
				if(temp.next.data==dataToRemove){
					if(temp.next.data==tail.data){
						tail=temp;
					}
					temp.next=temp.next.next;
					return true;	//found
				}
				temp = temp.next;
			}
			return false;	//not found
		}

		@Override
		public String toString(){
			StringBuilder str = new StringBuilder();
			for(Node node = head; node!=null; node=node.next){
				str.append(node+", ");
			}
			return str.toString();
		}
	}

	//2.1 Remove Dups: Write code to remove duplicates from an unsorted linked list. 	FOLLOW UP. How would you solve this problem if a temporary buffer is not allowed?
	public static void removeDuplicatesTest(){
		LinkedListNew list = new LinkedListNew();
		list.add(1);
		list.add(3);
		list.add(3);
		list.add(2);
		list.add(4);
		list.add(3);
		list.add(4);

		System.out.println("Original = "+list);
		// list.remove(4);
		// removeduplicatesHashMap(list);
		removeduplicates2Pointers(list);
		System.out.println("Duplicates Removed = "+list);
	}
	private static void removeduplicatesHashMap(LinkedListNew list){
		HashMap<Integer, Boolean> frequency  = new HashMap<Integer, Boolean>();		//called frequency, but no need, just a quick way to store things
		for(Node temp = list.head; temp!=null; temp=temp.next){
			//remove if it's already been seen
			if(frequency.containsKey(temp.data)){
				list.remove(temp.data);
				//potentially remove empty count in hashmap, but maybe not
			}
			//otherwise add to the map for the 1st time
			else{
				frequency.put(temp.data, true);
			}
		}
	}
	//2 pointers, 1 goes through all the list & the 2nd goes from 1 ahead of the list to the end for each value of the 1st pointer
	//Doesn't handle null? or <2 items?
	private static void removeduplicates2Pointers(LinkedListNew list){
		Node fastPointer;
		for(Node slowPointer = list.head; slowPointer!=null; slowPointer=slowPointer.next){
			for(fastPointer=slowPointer; fastPointer!=null && fastPointer.next!=null; fastPointer=fastPointer.next){	//also check fastPointer itself isn't null in case it's the last thing removed & next doesn't exist
				if(slowPointer.data==fastPointer.next.data){
					fastPointer.next = fastPointer.next.next;
				}
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("2.1 Remove Dups: Write code to remove duplicates from an unsorted linked list. 	FOLLOW UP. How would you solve this problem if a temporary buffer is not allowed?");
		LinkedLists.removeDuplicatesTest();
	}
}