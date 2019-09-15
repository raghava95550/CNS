import java.util.*;
import java.io.*;
import java.lang.*;
public class AffineCipher 
{	
	String encrypt(String text,int a,int b)
	{
		text=text.toUpperCase();
		text=text.replaceAll(" ","");
		String cipher=new String();
		for(int i=0;i<text.length();i++)
		{
			int n=(int)(text.charAt(i))-65;
			int enc=(a*n+b)%26;
			char ch=(char)(enc+65);
			cipher=cipher+ch;
		}
		System.out.println("Encrypted ="+cipher);
		return cipher;
	}
	
	void decrypt(String cipherText,int a,int b)
	{
		cipherText=cipherText.toUpperCase();
		String plainText=new String();
		int inv=multiplicativeInverse(a);
		for(int i=0;i<cipherText.length();i++)
		{
			if(65<=(int)cipherText.charAt(i)&&(int)cipherText.charAt(i)<=90)
			{
				int n=(int)cipherText.charAt(i)-65;
				int n2=n-b;
				int n3;
				if(n2>=0)
				n3=(n2*inv)%26;
				else
					n3=((n2+26)*inv)%26;
				plainText=plainText+(char)(n3+65);
			}
		}
		System.out.println("Decrypted = "+plainText);
	}
	
	int multiplicativeInverse(int a)
	{
		int ret=0;
		for(int i=1;i<25;i++)
		{
			if((a*i)%26==1)
			{
				ret=i;break;
			}
		}
		return ret;
	}
	
	public int GCD(int a,int b)
	{
		int r=a%b;
		while(r>0)
		{
			a=b;
			b=r;
			r=a%b;
		}
		return b;
	}
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter plain text");
		String text=sc.nextLine();
		int a,b;
		System.out.println("Enter a and b values");
		a=sc.nextInt();
		b=sc.nextInt();
		AffineCipher aff=new AffineCipher();
		if((aff.GCD(a,26)==1)&&((1<=a)&&(a<=26))&&((0<=b)&&(b<=25)))
		{
			String encs=aff.encrypt(text,a,b);
			aff.decrypt(encs,a,b);
			return;
		}
		System.out.println("invalid key values");
	}
}
