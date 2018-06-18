import java.util.*;

public class HashTable {
	private int tableSize;		//prime number
	private LinkedList[] table;
	
	public HashTable(){
		this(17);
	}
	
	public HashTable(int tableSize){
		this.tableSize = tableSize;
		
		table = new LinkedList[tableSize];
		for(int i=0; i<tableSize; i++){
			table[i] = new LinkedList<Integer>();
		}
	}
	
	public void add(int item){
		int hashVal = hash(item);
		table[hashVal].add(item);
	}
	
	private int hash(int item){
		return item % tableSize;
	}
	
	public int get(int item){
		int hashVal = hash(item);

		for(int i=0; i<table[hashVal].size(); i++){
			if(((LinkedList<Integer>)table[hashVal]).get(i) == item){
				return i;
			}
		}
		return -1;
	}
	
	//override string version with str.hashCode()
	
	public void print(){
		for(int i=0; i<tableSize; i++){
			System.out.println(table[i]);
		}
	}
	
	public static void main(String[] args) {
//		System.out.println();
		HashTable table = new HashTable();
		table.add(1);
		table.add(20);
		table.add(3);
		
		table.print();
	}

}