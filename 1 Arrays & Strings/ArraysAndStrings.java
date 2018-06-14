
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
	
	//1.3 Write a method to replace all spaces in a string with '%20'. You may assume that the string has sufficient space at the end to hold the additional characters, and that you are given the "true" length of the string. (Note: If implementing in Java, please use a character array so that you can perform this operation in place.)
	public static void URLify(char[] string, int length){		
		int numSpaces = 0;
		for(int i=0; i<length; i++){
			if(string[i]==' '){
				numSpaces++;
			}
		}
		
		int copyPosition = length + 2*numSpaces;	//go to end of input string, add 2*numSpaces since each space turns into 3 characters & 1 is already taken up as the original space
		for(int i=length-1; i>=0; i--){
			if (string[i] == ' ') {
				string[copyPosition - 1] = '0';
				string[copyPosition - 2] = '2';
				string[copyPosition - 3] = '%';
				copyPosition -= 3;
			}
			else {
				string[copyPosition - 1] = string[i];
				copyPosition--;
			}
		}
		for(char ch : string){
			System.out.print(ch);
		}
		System.out.println();
	}
	
	//1.3 One-pass Using actual strings, not in place
	public static String URLifyOnePass(String string, int length){
		StringBuilder builder = new StringBuilder();
		for(int i=0; i<length; i++){
			if(string.charAt(i)==' '){
				builder.append("%20");
			}
			else{
				builder.append(string.charAt(i)+"");
			}
		}
		return builder.toString();
	}

	public static void main(String[] args) {
		System.out.println("#1 Is unique: Implement an algorithm to determine if a string has all unique characters. What if you cannot use additional data structures?");
		System.out.println(ArraysAndStrings.isUnique("abc"));
		System.out.println(ArraysAndStrings.isUnique("abca2"));
		
		ArraysAndStrings.URLify(new char[]{'M','r',' ','J','o','h','n',' ',' ','m','i','t','h',' ', ' ',' ', ' ',' ',' '},13);
		System.out.println(ArraysAndStrings.URLifyOnePass("Mr John  mith",13));
		
	}

}