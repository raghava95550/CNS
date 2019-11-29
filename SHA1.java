import java.util.*;
import java.math.*;
public class SHA1 
{
	private String H0="67452301";
	private String H1="EFCDAB89";
	private String H2="98BADCFE";
	private String H3="10325476";
	private String H4="C3D2E1F0";
	
	private String round_constant[]= {"5A827999","6ED9EBA1","8F1BBCDC","CA62C1D6"};
	
	
	public String stringToBin(String s)
	{
		String bin="";
		for(int i=0;i<s.length();i++)
			bin=bin+String.format("%8s",Integer.toBinaryString((int)s.charAt(i))).replace(' ','0');
		return bin;
	}
	
	
	public String hexToBin(String s)
	{
		String bin="";
		for(int i=0;i<s.length();i++)
			bin=bin+String.format("%4s",Integer.toBinaryString(Integer.parseInt(s.charAt(i)+"",16))).replace(' ','0');
		return bin;
	}
	
	public String binToHex(String s)
	{
		String hex="";
		for(int i=0;i<s.length();i=i+4)
			hex=hex+Integer.toHexString(Integer.parseInt(s.substring(i,i+4),2));
		return hex;
	}
	
	public String xor(String s1,String s2)
	{
		String output="";
		long n1=Long.parseLong(s1,2);
		long n2=Long.parseLong(s2,2);
		long n=n1^n2;
		output=String.format("%32s",Long.toBinaryString(n)).replace(' ','0');
		return output;
	}
	
	public String and(String s1,String s2)
	{
		String output="";
		long n1=Long.parseLong(s1,2);
		long n2=Long.parseLong(s2,2);
		long n=n1&n2;
		output=String.format("%32s",Long.toBinaryString(n)).replace(' ','0');
		return output;
	}
	
	public String or(String s1,String s2)
	{
		String output="";
		long n1=Long.parseLong(s1,2);
		long n2=Long.parseLong(s2,2);
		long n=n1|n2;
		output=String.format("%32s",Long.toBinaryString(n)).replace(' ','0');
		return output;
	}
	
	public String orBig(String s1,String s2)
	{
		String output="";
		BigInteger n1=new BigInteger(s1,2);
		BigInteger n2=new BigInteger(s2,2);
		BigInteger n=n1.xor(n2);
		output=String.format("%160s",n.toString(2)).replace(' ','0');
		return output;
	}
	
	public String not(String s1)
	{
		String output="";
		int len=s1.length();
		for(int i=0;i<len;i++)
		{
			if(s1.charAt(i)=='1')
				output=output+'0';
			else
				output=output+'1';
		}
		return output;
	}
	
	
	public String modulo_addition(String s1,String s2)
	{
		String res="";
		long n1=Long.parseLong(s1,2);
		long n2=Long.parseLong(s2,2);
		long n=(n1+n2)%(long)(Math.pow(2,32));
		res=String.format("%32s",Long.toBinaryString(n)).replace(' ','0');
		return res;
	}
	
	public String leftCircularShift(String msg,int num)
	{
		int len=msg.length();
		String res=new String();
		for(int i=0;i<len;i++)
			res=res+msg.charAt((i+num)%len);
		return res;
	}
	
	public List<String> word_generation(String msg)
	{
		String str[]=new String[80];
		for(int i=0,j=0;i<16;i++,j=j+32)
			str[i]=msg.substring(j,j+32);
		for(int i=16;i<80;i++)
		{
			str[i]=xor(xor(xor(str[i-16],str[i-14]),str[i-8]),str[i-3]);
			str[i]=leftCircularShift(str[i],1);
			System.out.println(Long.parseLong(str[i],2));
		}
		List<String> ls=Arrays.asList(str);
		return ls;
	}
	
	public String round_operation(String a,String b,String c,String d,String e,String word,int round)
	{
		String fun_out=new String();
		String k=new String();
		if(0<=round&&round<=19)
		{
			fun_out = or(and(b,c),and(not(b),d));
			k=hexToBin(round_constant[0]);
		}
		else if(20<=round&&round<=39)
		{
			fun_out = xor(b,xor(c,d));
			k=hexToBin(round_constant[1]);
		}
		else if(40<=round&&round<=59)
		{
			fun_out = or(and(b,c),or(and(b,d),and(c,d)));
			k=hexToBin(round_constant[2]);
		}
		else if(60<=round&&round<=79)
		{
			fun_out = xor(b,xor(c,d));
			k=hexToBin(round_constant[3]);
		}
		
		String temp=leftCircularShift(a,5);
		fun_out = modulo_addition(modulo_addition(modulo_addition(modulo_addition(fun_out,temp),e),k),word);
		
		return fun_out;
	}
	
	//The function which hash the input message
	public void hash_fun(String msg)
	{
		//calling words generating function
		List<String> words=word_generation(msg);
		
		//Intializing strings with intial values by converting into binary
		String A=hexToBin(H0);
		String B=hexToBin(H1);
		String C=hexToBin(H2);
		String D=hexToBin(H3);
		String E=hexToBin(H4);
		
		String fun_out=new String();
		for(int i=0;i<80;i++)
		{
			fun_out = round_operation(A,B,C,D,E,words.get(i),i);
			E=D;
			D=C;
			C=leftCircularShift(B,30);
			B=A;
			A=fun_out;
		}
		
		A=modulo_addition(hexToBin(H0),A);
		B=modulo_addition(hexToBin(H1),B);
		C=modulo_addition(hexToBin(H2),C);
		D=modulo_addition(hexToBin(H3),D);
		E=modulo_addition(hexToBin(H4),E);
		
		A=String.format("%160s",A).replace(' ','0');
		B=String.format("%160s",B).replace(' ','0');
		C=String.format("%160s",C).replace(' ','0');
		D=String.format("%160s",D).replace(' ','0');
		E=String.format("%160s",E).replace(' ','0');
		String final_hex = orBig(leftCircularShift(D,32),E);
		final_hex=orBig(leftCircularShift(C,64),final_hex);
		final_hex=orBig(leftCircularShift(B,96),final_hex);
		final_hex=binToHex(orBig(leftCircularShift(A,128),final_hex));
				
		System.out.println("The hashed string is= "+final_hex);
 		
	}
	
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter some text");
		String s=sc.nextLine();
		SHA1 sha=new SHA1();
		//Converting length of the string to binary and adding padding bits upto 448 length
		String bin=sha.stringToBin(s);
		int len=bin.length();
		if(len<448)
		{
			bin=bin+'1';
			int rem=448-(len+1);
			for(int i=0;i<rem;i++)
				bin=bin+'0';
		}
		
		//Adding length of the string to binary string
		bin=bin+String.format("%64s",Integer.toBinaryString(sha.stringToBin(s).length())).replace(' ','0');
		//Hashing the input value
		sha.hash_fun(bin);
	}
}
