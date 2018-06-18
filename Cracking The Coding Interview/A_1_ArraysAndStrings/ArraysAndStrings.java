package A_1_ArraysAndStrings;

import java.util.*;

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

	//1.2 Given two strings, write a method to decide if one is a permutation of the other.
	//O(n) ===(O(3n))		ignores lower/upper case	counts whitespace
	public static boolean isPermutation(String str1, String str2){
		System.out.println("isPermutation? "+str1+" vs "+str2 +" ");
		if(str1.length() != str2.length()){
			return false;
		}

		//Count number of characters in each string & same in a map
		HashMap<Character,Integer> characterFrequency1 = new HashMap<Character,Integer>();
		HashMap<Character,Integer> characterFrequency2 = new HashMap<Character,Integer>();
		for(int i=0; i<str1.length(); i++){		//must be same length to be permutations of each other
			char ch1 = str1.charAt(i);
			if(characterFrequency1.containsKey(ch1)){
				characterFrequency1.put(ch1, characterFrequency1.get(ch1)+1);
			}
			else{
				characterFrequency1.put(ch1, 1);
			}
			char ch2 = str2.charAt(i);
			if(characterFrequency2.containsKey(ch2)){
				characterFrequency2.put(ch2, characterFrequency2.get(ch2)+1);
			}
			else{
				characterFrequency2.put(ch2, 1);
			}
		}
		
		//Loop over keys in 1 map to look for any counts that are different
		for(char key2 : characterFrequency2.keySet()){
			if(characterFrequency2.get(key2)!=characterFrequency1.get(key2)){
				return false;
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
	
	//1.4 Palindrome Permutation: Given a string, write a function to check if it is a permutation of a palindrome. A palindrome is a word or phrase that is the same forwards and backwards. A permutation is a rearrangement of letters. The palindrome does not need to be limited to just dictionary words.
	//n log n since sorting, use hash table for faster
	//treats all characters including spaces as unique (potential to remove spaces if required)
	public static boolean isPalindromePermutation(String str){
		System.out.println("isPalindromePermutation? "+str);

		str = str.toLowerCase();
		char chars[] = str.toCharArray();
		Arrays.sort(chars);
		// System.out.println(Arrays.toString(chars));

		for(int i=0; i<chars.length; i+=2){		//Loop over array by steps of 2 & compare to next thing in array
			if(i+1==chars.length){	//break if array is an odd length since 1 lone character without a match is fine @ the end
				return true;
			}
			if(chars[i]!=chars[i+1] && (chars.length%2)!=0){		//If extra character is in the middle, skip since it's allowed in an odd lengh string
				i++;
			}
			if(chars[i]!=chars[i+1]){	//compare adjacent chars
				return false;
			}
		}
		return true;
	}


	//1.5 One Away: There are three types of edits that can be performed on strings: insert a character, remove a character, or replace a character. Given two strings, write a function to check if they are one edit (or zero edits) away.
	public static boolean isOneEditAway(char[] str1, char[] str2){
		System.out.print("One edit away? ");
		for(char ch : str1){
			System.out.print(ch);
		}
		System.out.print(" & ");
		for(char ch : str2){
			System.out.print(ch);
		}
		System.out.println();

		//Make sure strings are within 1 length
		if(Math.abs(str1.length - str2.length) > 1){
			return false;
		}

		//Replacement
		int differencesIfReplacement = 0;
		for(int i=0; i<str1.length; i++){
			if(str1[i]!=str2[i]){
				differencesIfReplacement++;
				if(differencesIfReplacement>1){
					break;
				}
			}
		}

		if(str1.length==str2.length){
			return differencesIfReplacement <= 1;
		}

		//Insertion/deletion
		int differencesIfInsertionDeletion = 0;
		int str1Index = 0, str2Index = 0;
		for(; str1Index<str1.length && str2Index<str2.length; str1Index++, str2Index++){
			if(str1[str1Index]!=str2[str2Index]){
				differencesIfInsertionDeletion++;
				if(str1.length>str2.length){
					str1Index++;
				}
				else{
					str2Index++;
				}
			}
		}
		return differencesIfInsertionDeletion <= 1;
	}
	

	public static void main(String[] args) {
		System.out.println("#1 Is unique: Implement an algorithm to determine if a string has all unique characters. What if you cannot use additional data structures?");
		System.out.println(ArraysAndStrings.isUnique("abc"));
		System.out.println(ArraysAndStrings.isUnique("abca2"));

		System.out.println("\n1.2 Given two strings, write a method to decide if one is a permutation of the other.");
		System.out.println(ArraysAndStrings.isPermutation("asd", "add"));
		System.out.println(ArraysAndStrings.isPermutation("asd", "dsa"));

		System.out.println("\n#1.3 Write a method to replace all spaces in a string with '%20'. You may assume that the string has sufficient space at the end to hold the additional characters, and that you are given the 'true' length of the string. (Note: If implementing in Java, please use a character array so that you can perform this operation in place.)");
		ArraysAndStrings.URLify(new char[]{'M','r',' ','J','o','h','n',' ',' ','m','i','t','h',' ', ' ',' ', ' ',' ',' '},13);
		System.out.println(ArraysAndStrings.URLifyOnePass("Mr John  mith",13));

		System.out.println("\n1.4 Palindrome Permutation: Given a string, write a function to check if it is a permutation of a palindrome.");
		System.out.println(ArraysAndStrings.isPalindromePermutation("tactcoapapa"));

		System.out.println("\n1.5 There are three types of edits that can be performed on strings: insert a character, remove a character, or replace a character. One edit away?");
		System.out.println(ArraysAndStrings.isOneEditAway(new char[]{'p', 'a', 'l', 'e'}, new char[]{'b', 'a', 'l', 'e'}));
		System.out.println(ArraysAndStrings.isOneEditAway(new char[]{'p', 'a', 'l', 'e'}, new char[]{'p', 'a', 'l', 'e', 's'}));
		System.out.println(ArraysAndStrings.isOneEditAway(new char[]{'p', 'a', 'l', 'e'}, new char[]{'a', 'p', 'a', 'l', 'e'}));
		System.out.println(ArraysAndStrings.isOneEditAway(new char[]{'a', 'p', 'a', 'l', 'e', 'a'}, new char[]{'p', 'a', 'l', 'e'}));
		System.out.println(ArraysAndStrings.isOneEditAway(new char[]{'p', 'a', 'l', 'e'}, new char[]{'b', 'a', 'e'}));
	}

}