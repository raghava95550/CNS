import java.io.*;
import java.util.*;
public class AES 
{
	public String Sbox[][]={{"63","7C","77","7B","F2","6B","6F","C5","30","01","67","2B","FE","D7","AB","76"},
			{"CA","82","C9","7D","FA","59","47","F0","AD","D4","A2","AF","9C","A4","72","C0"},
			{"B7","FD","93","26","36","3F","F7","CC","34","A5","E5","F1","71","D8","31","15"},
			{"04","C7","23","C3","18","96","05","9A","07","12","80","E2","EB","27","B2","75"},
			{"09","83","2C","1A","1B","6E","5A","A0","52","3B","D6","B3","29","E3","2F","84"},
			{"53","D1","00","ED","20","FC","B1","5B","6A","CB","BE","39","4A","4C","58","CF"},
			{"D0","EF","AA","FB","43","4D","33","85","45","F9","02","7F","50","3C","9F","A8"},
			{"51","A3","40","8F","92","9D","38","F5","BC","B6","DA","21","10","FF","F3","D2"},
			{"CD","0C","13","EC","5F","97","44","17","C4","A7","7E","3D","64","5D","19","73"},
			{"60","81","4F","DC","22","2A","90","88","46","EE","B8","14","DE","5E","0B","DB"},
			{"E0","32","3A","0A","49","06","24","5C","C2","D3","AC","62","91","95","E4","79"},
			{"E7","C8","37","6D","8D","D5","4E","A9","6C","56","F4","EA","65","7A","AE","08"},
			{"BA","78","25","2E","1C","A6","B4","C6","E8","DD","74","1F","4B","BD","8B","8A"},
			{"70","3E","B5","66","48","03","F6","0E","61","35","57","B9","86","C1","1D","9E"},
			{"E1","F8","98","11","69","D9","8E","94","9B","1E","87","E9","CE","55","28","DF"},
			{"8C","A1","89","0D","BF","E6","42","68","41","99","2D","0F","B0","54","BB","16"}};	
	
	public String round_constant[][]={{"01","00","00","00"},
			{"02","00","00","00"},
			{"04","00","00","00"},
			{"08","00","00","00"},
			{"10","00","00","00"},
			{"20","00","00","00"},
			{"40","00","00","00"},
			{"80","00","00","00"},
			{"1B","00","00","00"},
			{"36","00","00","00"}};
	
	public String Inv_SBox[][]={{"52","09","6A","D5","30","36","A5","38","BF","40","A3","9E","81","F3","D7","FB"},
			{"7C","E3","39","82","9B","2F","FF","87","34","8E","43","44","C4","DE","E9","CB"},
			{"54","7B","94","32","A6","C2","23","3D","EE","4C","95","0B","42","FA","C3","4E"},
			{"08","2E","A1","66","28","D9","24","B2","76","5B","A2","49","6D","8B","D1","25"},
			{"72","F8","F6","64","86","68","98","16","D4","A4","5C","CC","5D","65","B6","92"},
			{"6C","70","48","50","FD","ED","B9","DA","5E","15","46","57","A7","8D","9D","84"},
			{"90","D8","AB","00","8C","BC","D3","0A","F7","E4","58","05","B8","B3","45","06"},
			{"D0","2C","1E","8F","CA","3F","0F","02","C1","AF","BD","03","01","13","8A","6B"},
			{"3A","91","11","41","4F","67","DC","EA","97","F2","CF","CE","F0","B4","E6","73"},
			{"96","AC","74","22","E7","AD","35","85","E2","F9","37","E8","1C","75","DF","6E"},
			{"47","F1","1A","71","1D","29","C5","89","6F","B7","62","0E","AA","18","BE","1B"},
			{"FC","56","3E","4B","C6","D2","79","20","9A","DB","C0","FE","78","CD","5A","F4"},
			{"1F","DD","A8","33","88","07","C7","31","B1","12","10","59","27","80","EC","5F"},
			{"60","51","7F","A9","19","B5","4A","0D","2D","E5","7A","9F","93","C9","9C","EF"},
			{"A0","E0","3B","4D","AE","2A","F5","B0","C8","EB","BB","3C","83","53","99","61"},
			{"17","2B","04","7E","BA","77","D6","26","E1","69","14","63","55","21","0C","7D"}};
	
	
	// Function to convert string to hexadecimal string,Input normal string and output is hexadecimal string
	public String stringToHex(String s)
	{
		String ret="";
		for(int i=0;i<s.length();i++)
			ret=ret+String.format("%2s",Integer.toHexString((int)s.charAt(i))).replace(' ','0');
		return ret;
	}
	
	// Function to convert hexadecimal string to normal string,Input hexadecimal string and output is normal string
	public String hexToString(String s)
	{
		String ret="";
		for(int i=0;i<s.length()-1;i=i+2)
			ret=ret+(char)Integer.parseInt(s.substring(i,i+2),16);
		return ret;
	}
	
	// Function to convert binary string to hexadecimal string,Input binary string and output is hexadecimal string
	public String binaryToHex(String s)
	{
		String hexval=Integer.toHexString(Integer.parseInt(s.substring(0,4),2));
		hexval=hexval+Integer.toHexString(Integer.parseInt(s.substring(4,8),2));
		return hexval;
	}
	
	// Function to convert hexadecimal string to binary string,Input hexadecimal string and output is binary string
	public String hexToBinary(String s)
	{
		String binval=String.format("%4s",Integer.toBinaryString(Integer.parseInt(s.charAt(0)+"",16))).replace(' ','0');
		binval=binval+String.format("%4s",Integer.toBinaryString(Integer.parseInt(s.charAt(1)+"",16))).replace(' ','0');
		return binval;
	}
	
	// Function two generate state table from string, input is string and output is state array
	public String[][] stateGeneration(String text)
	{
		String state[][]=new String[4][4];
		for(int i=0,k=0,j=0;k<text.length()&&i<16;i++,k=k+2)
		{
			state[i%4][j]=text.substring(k,k+2);
			if((i+1)%4==0)
				j++;
		}
		return state;
	}
	
	// Function to print the state table
	public void printGeneration(String state[][])
	{
		for(int i=0;i<4;i++)
		{
			for(int j=0;j<4;j++)
				System.out.print(state[i][j]+" ");
			System.out.println();
		}
	}
	
	//XOR function perform xor operation directly for two state arrays, input two state arrays and output is also a state array 
	public String[][] Matrix_XOR(String state1[][],String state2[][])
	{
		int n1=state1.length;
		String result[][]=new String[n1][n1];
		
		for(int i=0;i<n1;i++)
			for(int j=0;j<n1;j++)
				result[i][j]=Xor(state1[i][j],state2[i][j]);
		return result;
	}
	
	//Xor function for two strings,Input is two Hex Strings and output is also hex string only
	public String Xor(String s1,String s2)
	{
		String binval1=hexToBinary(s1);
		String binval2=hexToBinary(s2);
		String res="";
		if(binval1.length()!=binval2.length())
			return null;
		for(int i=0;i<binval1.length();i++)
			res=res+(binval1.charAt(i)^binval2.charAt(i));
		String hexval=binaryToHex(res);
		return hexval;
	}
	
	//Function to perform shfit rows operation, input and outputs are both state arrays. 
	public String[][] shiftRows(String state[][])
	{
		int n=state.length;
		String result[][]=new String[n][n];
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				result[i][j]=state[i][(j+i)%n];
		return result;
	}
	
	//Function to perform inverse shift operation, input and outputs are both state arrys.
	public String[][] InvshiftRows(String state[][])
	{
		int n=state.length;
		String result[][]=new String[n][n];
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				result[i][(j+i)%n]=state[i][j];
		return result;
	}
	
	//Both inputs are hex strings and output is also hex string only, Multiplication of hex numbers with constant values 01,02,03
	public String hexMul(String str1,String str2)
	{
		String res="";
		//Multiplication str*9 =(((str*2)*2)*2)+str ; 
		//Multiplication str*11 =((((str*2)*2)+str)*2)+str ;
		//Multiplication str*13 =((((str*2)+str)*2)*2)+str ;
		//Multiplication str*14 =((((str*2)+str)*2)+str)*2 ;
		switch(str2)
		{
			case "02":	{
						String bin1=hexToBinary(str1);
						bin1=bin1+'0';
						if(bin1.charAt(0)=='1')
							res=Xor(binaryToHex(bin1.substring(1,bin1.length())),"1B");
						else
							res=binaryToHex(bin1.substring(1,bin1.length()));
						}break;
			case "03":res=Xor(hexMul(str1,"01"),hexMul(str1,"02"));break;
			case "09":res=Xor(hexMul(hexMul(hexMul(str1,"02"),"02"),"02"),str1);break;
			case "11":res=Xor(hexMul(Xor(hexMul(hexMul(str1,"02"),"02"),str1),"02"),str1);break;
			case "13":res=Xor(hexMul(hexMul(Xor(hexMul(str1,"02"),str1),"02"),"02"),str1);break;
			case "14":res=hexMul(Xor(hexMul(Xor(hexMul(str1,"02"),str1),"02"),str1),"02");break;
			default:res=str1;
		}			
		return res;
	}
	
	// Mix columns operation is performed, multiplied with a constant matrix,input and output are state arrays
	public String[][] MixColumns(String state[][])
	{
		int n=state.length;
		String result[][]=new String[n][n];
		String constant[][]={{"02","03","01","01"},
				{"01","02","03","01"},
				{"01","01","02","03"},
				{"03","01","01","02"}};
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
			{
				String str="00";
				for(int k=0;k<n;k++)
					str=Xor(str,hexMul(state[k][j],constant[i][k]));
				result[i][j]=str;
			}		
		return result;
	}
	
	
	public String[][] InvMixColumns(String state[][])
	{
		int n=state.length;
		String constant[][]={{"14","11","13","09"},
				{"09","14","11","13"},
				{"13","09","14","11"},
				{"11","13","09","14"}};	
		String result[][]=new String[n][n];
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
			{
				String str="00";
				for(int k=0;k<n;k++)
					str=Xor(str,hexMul(state[k][j],constant[i][k]));
				result[i][j]=str;
			}
		
		return result;
	}
	
	// Substitute bytes operation is performed, input and output are state arrays
	public String[][] subBytes(String state[][])
	{
		int n=state.length;
		String result[][]=new String[n][n];
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
			{
				int row=Integer.parseInt(state[i][j].charAt(0)+"",16);
				int col=Integer.parseInt(state[i][j].charAt(1)+"",16);
				result[i][j]=Sbox[row][col];
			}
		return result;
	}
	
	//Inverse substitution bytes operation is performed,input and output are state arrays.
	public String[][] InvsubBytes(String state[][])
	{
		int n=state.length;
		String result[][]=new String[n][n];
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
			{
				int row=Integer.parseInt(state[i][j].charAt(0)+"",16);
				int col=Integer.parseInt(state[i][j].charAt(1)+"",16);
				result[i][j]=Inv_SBox[row][col];
			}
		return result;
	}
	
	// Function to perform xor operations for two words, Input is two word arrays and output is resultant word array
	public String[] word_XOR(String w1[],String w2[])
	{
		int n=w1.length;
		String res[]=new String[n];
		for(int i=0;i<n;i++)
			res[i]=Xor(w1[i],w2[i]);
		return res;
	}
	
	// Round key generation function; Input is key state array and round number and output is key generated string  
	public String RoundKeyGeneration(String keyState[][],int round)
	{
		int n=keyState.length;
		String final_res="";
		String result[][]=new String[n][n];
		String w3[]=new String[n];
		String tres[]=new String[n];
		String constant[]=new String[n];
		for(int i=0;i<n;i++)
			constant[i]=round_constant[round-1][i];
		// W3 word
		for(int i=0;i<n;i++)
			w3[i]=keyState[i][3];
		
		for(int i=0;i<n;i++)
			tres[i]=w3[(i+1)%n];
		for(int i=0;i<n;i++)
		{
			int row=Integer.parseInt(tres[i].charAt(0)+"",16);
			int col=Integer.parseInt(tres[i].charAt(1)+"",16);
			tres[i]=Sbox[row][col];
		}
		tres=word_XOR(tres,constant); // Final generation of t to be used in key generation
		
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
			{
				if(i==0)
						result[j][i]=Xor(tres[j],keyState[j][i]);
				else
					result[j][i]=Xor(keyState[j][i],result[j][i-1]);
			}
		
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				final_res+=result[j][i];
		return final_res;
	}
	
	// Each round operation will be performed in this function, input is plain and key state matrices and output result matrix 
	public String[][] Round(String plain[][],String key[][],int round)
	{
		int n=plain.length;
		String result[][]=new String[n][n];
		result=subBytes(plain);
		result=shiftRows(result);
		if(round!=10) // For round 10 there is no mix columns operation , so removing it 
		result=MixColumns(result);
		result=Matrix_XOR(result,key);
		return result;
	}
	
	
	public String[][] InvRound(String cipher[][],String key[][],int round)
	{
		int n=cipher.length;
		String result[][]=new String[n][n];
		result=Matrix_XOR(cipher,key);
		if(round!=10)
			result=InvMixColumns(result);
		result=InvshiftRows(result);
		result=InvsubBytes(result);
		return result;
	}
	
	// Input to this function is two hex Strings(plain text,key) and output is cipher text in plain format.
	public String Encryption(String plain,String key)
	{
		String cipherText="";
		plain=stringToHex(plain);
		key=stringToHex(key);
		List<String> roundKeys=new ArrayList<String>();
		roundKeys.add(key);  // Round keys generation
		for(int i=1;i<=10;i++)
		{
			key=RoundKeyGeneration(stateGeneration(key),i);
			roundKeys.add(key);
		}
		for(int i=0;i<plain.length()-1;i=i+32)
		{
			String keyState[][]=stateGeneration(roundKeys.get(0));  // state matrix generation for key
			String state[][]=stateGeneration(plain.substring(i,i+32)); // state matrix generation for plain hexstring
			System.out.println("Plain text state matrix");
			printGeneration(state);
			System.out.println("Key state matrix");
			printGeneration(keyState);
			
			//Primary round just xor plain text and key
			String cipher[][]=Matrix_XOR(state,keyState);
			
			// After initial round the output is
			int n=cipher.length;
			System.out.println("after initial round aes output is");
			for(int l=0;l<n;l++)
				for(int k=0;k<n;k++)
					System.out.print(cipher[k][l]+" ");
			System.out.println();
			System.out.println();
			
			//Rounds from 1 to 10
			for(int j=1;j<=10;j++)
			{
				String rkey=roundKeys.get(j); // Retrieving key from arraylist.
				System.out.println("Round "+j+" key ="+rkey);
				cipher=Round(cipher,stateGeneration(rkey),j); // Performing each round operation
				
				//Printing output after round
				System.out.println("after round "+j+" the output is");
				for(int l=0;l<n;l++)
					for(int k=0;k<n;k++)
						System.out.print(cipher[k][l]+" ");
				System.out.println();
				System.out.println();
			}
			
			
			for(int l=0;l<n;l++)
				for(int k=0;k<n;k++)
					cipherText=cipherText+hexToString(cipher[k][l]);
			System.out.println();
		}
		return cipherText;
	}
	
	
	public void Decryption(String cipher,String key)
	{
		String plainText="";
		cipher=stringToHex(cipher);
		key=stringToHex(key);
		List<String> roundKeys=new ArrayList<String>();
		roundKeys.add(key);  // Round keys generation
		for(int i=1;i<=10;i++)
		{
			key=RoundKeyGeneration(stateGeneration(key),i);
			roundKeys.add(key);
		}
		for(int i=0;i<cipher.length()-1;i=i+32)
		{
			String state[][]=stateGeneration(cipher.substring(i,i+32)); // state matrix generation for cipher hexstring
			int n=state.length;
			//Performing rounds in reverse order
			for(int j=10;j>0;j--)
			{
				String rkey=roundKeys.get(j); // Retrieving key from arraylist.
				state=InvRound(state,stateGeneration(rkey),j); // Performing each round operation
				
				/*
				//Printing output after round
				System.out.println("after round "+j+" the output is");
				for(int l=0;l<n;l++)
					for(int k=0;k<n;k++)
						System.out.print(state[k][l]+" ");
				System.out.println();
				System.out.println();
				*/
			}
			// Performing intial round;
			state=Matrix_XOR(state,stateGeneration(roundKeys.get(0)));
			
			for(int l=0;l<n;l++)
				for(int k=0;k<n;k++)
					plainText=plainText+hexToString(state[k][l]);
		}
		
		System.out.println("After decryption the string is  ="+plainText);
		
	}
	
	public static void main(String args[])
	{
		AES aes=new AES();
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the plain text");
		String plain=sc.nextLine();
		if(plain.length()%16!=0)
		{
			int k=16-(plain.length()%16);
			for(int i=0;i<k;i++)
				plain=plain+" ";
		}
		System.out.println("Enter a 16 letterd key");
		String key=sc.nextLine();
		if(key.length()!=16)
		{
			System.out.println("Invalid key, Key length must and should be 16 letters");
			return;
		}
		String cipher=aes.Encryption(plain, key);
		System.out.println("The encrypted text="+cipher);
		aes.Decryption(cipher, key);
	}
}