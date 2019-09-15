import java.util.*;
import java.io.*;
public class Ceaser 
{
	public String encrypt(String s,int key)
	{
		String enc="";
		s=s.toLowerCase();
		for(int i=0;i<s.length();i++)
		{
			if(s.charAt(i)!=' ')
			{
				int l=(int)s.charAt(i)-97;
				int newval=(l+key)%26;
				enc=enc+(char)(newval+97);
			}
			else
			{
				enc=enc+' ';
			}
		}
		return enc;
	}
	public String decrypt(String s,int key)
	{
		String dec="";
		s=s.toLowerCase();
		for(int i=0;i<s.length();i++)
		{
			if(s.charAt(i)!=' ')
			{
				int l=(int)s.charAt(i)-97;
				int newval=(l-key)%26;
				if(newval<0)
					newval=newval+26;
				//System.out.println("value ="+newval);
				dec=dec+(char)(newval+97);
				//s.replace(s.charAt(i),(char)(newval+97));
			}
			else
			{
				dec=dec+' ';
			}
		}
		return dec;
	}
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter a Text");
		String str=sc.nextLine();
		System.out.println("Enter the Key");
		int key=sc.nextInt();
		Ceaser cr=new Ceaser();
		String encs=cr.encrypt(str,key);
		System.out.println("The encrypted String ="+encs);
		String decs=cr.decrypt(encs, key);
		System.out.println("The decrypted String ="+decs);
	}

}
