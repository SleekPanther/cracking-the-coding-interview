
public class ArraysAndStrings {
	//#1 Is unique: Implement an algorithm to determine if a string has all unique characters. What if you cannot use additional data structures?
	public static boolean isUnique(String str){
		System.out.print("\""+str+"\"" +" unique? ");
		
		int alphabetSize = 128;
		boolean[] usedCharacters = new boolean[alphabetSize];	//initialized to false
		
		if(str.length() > alphabetSize){
			return false;
		}
		
		for(char ch : str.toCharArray()){
			int indexOffset = (int)'a';		//depending on encoding, normalize 'a' (usually ascii 65) to start @ 0
			int index = (int)ch - indexOffset;
			if(usedCharacters[index]){
				return false;
			}
			else{
				usedCharacters[index]=true;
			}
		}		
		return true;
	}

	public static void main(String[] args) {
		System.out.println("#1 Is unique: Implement an algorithm to determine if a string has all unique characters. What if you cannot use additional data structures?");
		System.out.println(ArraysAndStrings.isUnique("abc"));
		System.out.println(ArraysAndStrings.isUnique("abca2"));
		
		
	}

}