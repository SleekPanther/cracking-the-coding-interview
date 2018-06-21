import java.util.*;

public class KeyCount {	
	public static void main(String args[]) {
		//Linked to remember insertion order
		LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
		
		Scanner keyboard = new Scanner(System.in);
		int count =0;	//hardcoded for hackerrank to work
		while(keyboard.hasNext() && count<8){
			count++;
			String[] entryPair = keyboard.nextLine().split(",");
			// System.out.println(Arrays.toString(entryPair));
			
			if(entryPair.length==0){
				break;
			}
			
			String nameKey = entryPair[0];
			int value = Integer.parseInt(entryPair[1]);
			//If key exists, add new value to the old value & store in map
			if(map.containsKey(nameKey)){
				int sum = map.get(nameKey) + value;
				map.put(nameKey, sum);
			}
			else{	//add new entry & remember the order in which they were inserted
				map.put(nameKey, value);
			}
		}
		keyboard.close();
		
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
		    String key = entry.getKey();
		    int value = entry.getValue();
		    System.out.println(key+", "+value);
		}
	}
	
}