import java.util.*;
import java.io.*;
public class DES 
{
	// Initial Permutation Table
	private final byte[] IP1={ 58, 50, 42, 34, 26, 18, 10, 2,
			60, 52, 44, 36, 28, 20, 12, 4,
			62, 54, 46, 38, 30, 22, 14, 6,
			64, 56, 48, 40, 32, 24, 16, 8,
			57, 49, 41, 33, 25, 17, 9,  1,
			59, 51, 43, 35, 27, 19, 11, 3,
			61, 53, 45, 37, 29, 21, 13, 5,
			63, 55, 47, 39, 31, 23, 15, 7
		};
	
	// Final Permutation Table or Inverse Initial Permutation table 
	private final byte[] FP={
			40, 8, 48, 16, 56, 24, 64, 32,
			39, 7, 47, 15, 55, 23, 63, 31,
			38, 6, 46, 14, 54, 22, 62, 30,
			37, 5, 45, 13, 53, 21, 61, 29,
			36, 4, 44, 12, 52, 20, 60, 28,
			35, 3, 43, 11, 51, 19, 59, 27,
			34, 2, 42, 10, 50, 18, 58, 26,
			33, 1, 41, 9, 49, 17, 57, 25
		};
	
	//Permuted Choice-1
	private final byte[] PC1={
			57, 49, 41, 33, 25, 17, 9,
			1,  58, 50, 42, 34, 26, 18,
			10, 2,  59, 51, 43, 35, 27,
			19, 11, 3,  60, 52, 44, 36,
			63, 55, 47, 39, 31, 23, 15,
			7,  62, 54, 46, 38, 30, 22,
			14, 6,  61, 53, 45, 37, 29,
			21, 13, 5,  28, 20, 12, 4
		};
	
	//Permuted Choice-2
	private final byte[] PC2={
			14, 17, 11, 24, 1,  5,
			3,  28, 15, 6,  21, 10,
			23, 19, 12, 4,  26, 8,
			16, 7,  27, 20, 13, 2,
			41, 52, 31, 37, 47, 55,
			30, 40, 51, 45, 33, 48,
			44, 49, 39, 56, 34, 53,
			46, 42, 50, 36, 29, 32
		};
	
	//Rotations for each round
	private final byte[] rotations={
			1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
		};
	
	//Expansion permutation table
	private final byte[] E={
			32, 1,  2,  3,  4,  5,
			4,  5,  6,  7,  8,  9,
			8,  9,  10, 11, 12, 13,
			12, 13, 14, 15, 16, 17,
			16, 17, 18, 19, 20, 21,
			20, 21, 22, 23, 24, 25,
			24, 25, 26, 27, 28, 29,
			28, 29, 30, 31, 32, 1
		};
	
	//S-boxes table
	private final byte[][] S={ {
		14, 4,  13, 1,  2,  15, 11, 8,  3,  10, 6,  12, 5,  9,  0,  7,
		0,  15, 7,  4,  14, 2,  13, 1,  10, 6,  12, 11, 9,  5,  3,  8,
		4,  1,  14, 8,  13, 6,  2,  11, 15, 12, 9,  7,  3,  10, 5,  0,
		15, 12, 8,  2,  4,  9,  1,  7,  5,  11, 3,  14, 10, 0,  6,  13
	}, {
		15, 1,  8,  14, 6,  11, 3,  4,  9,  7,  2,  13, 12, 0,  5,  10,
		3,  13, 4,  7,  15, 2,  8,  14, 12, 0,  1,  10, 6,  9,  11, 5,
		0,  14, 7,  11, 10, 4,  13, 1,  5,  8,  12, 6,  9,  3,  2,  15,
		13, 8,  10, 1,  3,  15, 4,  2,  11, 6,  7,  12, 0,  5,  14, 9
	}, {
		10, 0,  9,  14, 6,  3,  15, 5,  1,  13, 12, 7,  11, 4,  2,  8,
		13, 7,  0,  9,  3,  4,  6,  10, 2,  8,  5,  14, 12, 11, 15, 1,
		13, 6,  4,  9,  8,  15, 3,  0,  11, 1,  2,  12, 5,  10, 14, 7,
		1,  10, 13, 0,  6,  9,  8,  7,  4,  15, 14, 3,  11, 5,  2,  12
	}, {
		7,  13, 14, 3,  0,  6,  9,  10, 1,  2,  8,  5,  11, 12, 4,  15,
		13, 8,  11, 5,  6,  15, 0,  3,  4,  7,  2,  12, 1,  10, 14, 9,
		10, 6,  9,  0,  12, 11, 7,  13, 15, 1,  3,  14, 5,  2,  8,  4,
		3,  15, 0,  6,  10, 1,  13, 8,  9,  4,  5,  11, 12, 7,  2,  14
	}, {
		2,  12, 4,  1,  7,  10, 11, 6,  8,  5,  3,  15, 13, 0,  14, 9,
		14, 11, 2,  12, 4,  7,  13, 1,  5,  0,  15, 10, 3,  9,  8,  6,
		4,  2,  1,  11, 10, 13, 7,  8,  15, 9,  12, 5,  6,  3,  0,  14,
		11, 8,  12, 7,  1,  14, 2,  13, 6,  15, 0,  9,  10, 4,  5,  3
	}, {
		12, 1,  10, 15, 9,  2,  6,  8,  0,  13, 3,  4,  14, 7,  5,  11,
		10, 15, 4,  2,  7,  12, 9,  5,  6,  1,  13, 14, 0,  11, 3,  8,
		9,  14, 15, 5,  2,  8,  12, 3,  7,  0,  4,  10, 1,  13, 11, 6,
		4,  3,  2,  12, 9,  5,  15, 10, 11, 14, 1,  7,  6,  0,  8,  13
	}, {
		4,  11, 2,  14, 15, 0,  8,  13, 3,  12, 9,  7,  5,  10, 6,  1,
		13, 0,  11, 7,  4,  9,  1,  10, 14, 3,  5,  12, 2,  15, 8,  6,
		1,  4,  11, 13, 12, 3,  7,  14, 10, 15, 6,  8,  0,  5,  9,  2,
		6,  11, 13, 8,  1,  4,  10, 7,  9,  5,  0,  15, 14, 2,  3,  12
	}, {
		13, 2,  8,  4,  6,  15, 11, 1,  10, 9,  3,  14, 5,  0,  12, 7,
		1,  15, 13, 8,  10, 3,  7,  4,  12, 5,  6,  11, 0,  14, 9,  2,
		7,  11, 4,  1,  9,  12, 14, 2,  0,  6,  10, 13, 15, 3,  5,  8,
		2,  1,  14, 7,  4,  10, 8,  13, 15, 12, 9,  0,  3,  5,  6,  11
	} };
	
	//Permutation box table
	private final byte[] P= {
			16, 7,  20, 21,
			29, 12, 28, 17,
			1,  15, 23, 26,
			5,  18, 31, 10,
			2,  8,  24, 14,
			32, 27, 3,  9,
			19, 13, 30, 6,
			22, 11, 4,  25
		};
	
	//Function to convert normal string to binary string,Input to this function must be normal String
	public String stringToBin(String s)
	{
		String bin="";
		for(int i=0;i<s.length();i++)
			bin=bin+String.format("%8s",Integer.toBinaryString((int)s.charAt(i))).replace(' ','0');
		return bin;
	}
	
	// Function to convert Binary string to normal string,
	public String binToString(String bin)
	{
		String normal="";
		for(int i=0;i<bin.length();i=i+8)
			normal=normal+(char)Integer.parseInt(bin.substring(i,i+8),2);
		return normal;
	}
	
	//Function to convert hex string to binary
	public String hexToBin(String s)
	{
		String bin="";
		for(int i=0;i<s.length();i++)
			bin=bin+String.format("%4s",Integer.toBinaryString(Integer.parseInt(s.charAt(i)+"",16))).replace(' ','0');
		return bin;
	}
	
	//Function to convert binary to hex
	public String binToHex(String s)
	{
		String hex="";
		for(int i=0;i<s.length();i=i+4)
			hex=hex+Integer.toHexString(Integer.parseInt(s.substring(i,i+4),2));
		return hex;
	}
	
	//Permuatation function from which we can return permuted string after completion,Inputs are string and permutation table
	public String permutation(String str,byte table[])
	{
		String permuted="";
		for(int i=0;i<table.length;i++)
			permuted=permuted+str.charAt(table[i]-1);
		return permuted;
	}
	
	//LeftCircularShift function,takes input as String and no of rotations it has to make. 
	public String LCS(String str,int no_of_rotations)
	{
		String rotated="";
		int n=str.length();
		for(int i=0;i<n;i++)
			rotated=rotated+str.charAt((i+no_of_rotations)%n);
		return rotated;
	}
	
	//Function to generate All Subkeys , input should be 28 bit key
	public List<String> generateSubKeys(String s)
	{
		List<String> subkey=new ArrayList<String>();
		String key=s;
		for(int i=0;i<16;i++)
		{
			key=LCS(key,rotations[i]);
			subkey.add(key);
		}
		return subkey;
	}
	
	//Function to perform xor operation between two strings in java
	public String Xor(String s1,String s2)
	{
		String res="";
		if(s1.length()!=s2.length())
			return null;
		for(int i=0;i<s1.length();i++)
			res=res+(s1.charAt(i)^s2.charAt(i));
		return res;
	}
	
	//Function to perform s-box operations ,i.e. Contraction step
	public String SBox(String s)
	{
		//String s will contain 48 bit , and each s-box will get 6-bits
		String res="";
		for(int i=0,j=0;i<s.length();i=i+6,j++)
		{
			String sub=s.substring(i,i+6);
			String str=new String();
			str=str+sub.charAt(0)+sub.charAt(5);
			int row=Integer.parseInt(str,2); //Taking 1st and 6th character as binary and converting to integer for row number
			int col=Integer.parseInt(sub.substring(1,5),2);// Taking remaining characters as binary and converting to integer for column number 
			res=res+String.format("%4s",Integer.toBinaryString(S[j][(row*16)+col])).replace(' ','0'); // Getting the value from s-box and converting it into binary with padding
		}
		//System.out.println("After s-box ="+res);
		return res;
	}
	
	
	//The round function will take Right Plaintext,Left Plain Text,key as input and returns the 
	public String Round(String Rpt,String Lpt,String key)
	{
		String res="";
		Rpt=permutation(Rpt,E); //Performing expansion permutation operation
		res=Xor(Rpt,key); // Performing xor operation for key and string from expansion permutation step.
		res=SBox(res); // Giving 48 bit string as input to s-box to get 32-bit output.
		res=permutation(res,P); // P -Permutation is performed in this step.
		res=Xor(res,Lpt); // Performing Xor operation for result string and Left Plain Text.
		return res;
	}
	
	//Total encryption is done in this function
	public String encrypt(String Plaintext,String key)
	{
		String cipherText="";
		key=hexToBin(key); // converting key into binary
		key=permutation(key,PC1); //Performing Permuted Choice-1
		List<String> Lsk=generateSubKeys(key.substring(0,28));
		List<String> Rsk=generateSubKeys(key.substring(28,56));
		int size=Lsk.size();
		
		for(int j=0;j<Plaintext.length()-1;j=j+8)
		{
			String plain=Plaintext.substring(j,j+8);
			plain=stringToBin(plain);// Converting plaintext into binary
			String cipher="";
			plain=permutation(plain,IP1);
			String Lpt=plain.substring(0,32);
			//System.out.println("L0="+binToHex(Lpt));
			String Rpt=plain.substring(32,64);
			//System.out.println("R0="+binToHex(Rpt));
			String temp=new String();
			for(int i=0;i<size;i++)
			{
				key=permutation(Lsk.get(i)+Rsk.get(i),PC2); //Performing Permuted Choice-2
				System.out.println(i+"th round Key:");
				System.out.println(binToHex(key));
				System.out.println();
				temp=Rpt;
				Rpt=Round(Rpt,Lpt,key);
				Lpt=temp;
				//System.out.println("After "+(i+1)+"th permutation "+binToString(Lpt+Rpt));
			}
			
			// Swapping the left and right sub texts
			temp=Rpt;
			Rpt=Lpt;
			Lpt=temp;
			cipher=Lpt+Rpt;
			cipher=permutation(cipher,FP);
			cipherText=cipherText+binToString(cipher);
		}
		
		//System.out.println("Binary of cipher is "+cipher);
		return cipherText;
	}
	
	
	//Function to decrypt
	public String decrypt(String cipherText,String key)
	{
		String Plaint="";
		key=hexToBin(key); // converting key into binary
		key=permutation(key,PC1); //Performing Permuted Choice-1
		List<String> Lsk=generateSubKeys(key.substring(0,28));
		List<String> Rsk=generateSubKeys(key.substring(28,56));
		int size=Lsk.size();
		Collections.reverse(Lsk);
		Collections.reverse(Rsk);
		
		for(int j=0;j<cipherText.length();j=j+8)
		{
			String cipher=stringToBin(cipherText.substring(j,j+8));
			String plain="";
			cipher=permutation(cipher,IP1);
			String Lpt=cipher.substring(0,32);
			//System.out.println("L0="+binToHex(Lpt));
			String Rpt=cipher.substring(32,64);
			//System.out.println("R0="+binToHex(Rpt));
			String temp=new String();
			for(int i=0;i<size;i++)
			{
				key=permutation(Lsk.get(i)+Rsk.get(i),PC2); //Performing Permuted Choice-2
				temp=Rpt;
				//System.out.println(" for "+i+"Key ="+binToHex(key));
				Rpt=Round(Rpt,Lpt,key);
				Lpt=temp;
			}
			
			// Swapping the left and right sub texts
			temp=Rpt;
			Rpt=Lpt;
			Lpt=temp;
			plain=Lpt+Rpt;
			plain=permutation(plain,FP);
			Plaint=Plaint+binToString(plain);
		}
		return Plaint;
	}
	
	public static void main(String args[])
	{
		DES des=new DES();
		Scanner sc=new Scanner(System.in);
		
		/*// in string to binary
		 * {
		 * */
		System.out.println("Enter Plain Text");
		String Plaintext=sc.nextLine();
		if(Plaintext.length()%16!=0)
		{
			int k=16-(Plaintext.length()%16);
			for(int i=0;i<k;i++)
				Plaintext=Plaintext+" ";
		}
		
		System.out.println("Enter Key in Hexa length of 16");
		String key=sc.nextLine(); 
		String cipherText=des.encrypt(Plaintext, key);
		System.out.println("The Encrypted value is="+cipherText);
		
		String Plaint=des.decrypt(cipherText, key);	
		System.out.println("The Decrypted value is ="+Plaint);
		/*
		}
		*/
		
		/*
		//In hexa deciaml string to binary
		 * {
		System.out.println("Enter Plain Text in hexa format length of 16");
		String Plaintext=sc.nextLine();
		Plaintext=des.hexToBin(Plaintext);
		System.out.println("Enter Key in hexa format of length 16");
		String key=sc.nextLine();
		key=des.hexToBin(key);
		String cipher=des.encrypt(Plaintext, key);
		String cipherText=des.binToHex(cipher);
		System.out.println("The Encrypted value is="+cipherText);
		String Plaint=des.decrypt(cipher, key);
		Plaint=des.binToHex(Plaint);
		System.out.println("The Decrypted value is ="+Plaint);
		}
		*/
	}
}
