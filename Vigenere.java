import java.util.*;

public class Vigenere 
{
	public String constructKey(String key,String plain)
	{
		String mkey=new String();
		int len=plain.length();
		int keylen=key.length();
		if(keylen>len)
			mkey=key.substring(0,len);
		else
		{
			mkey=key;
			int n=(len-keylen)/keylen;
			while(n>0)
			{
				mkey=mkey+key;
				n--;
			}
			int newn=len-((len/keylen)*keylen);
			for(int i=0;i<newn;i++)
				mkey=mkey+key.charAt(i);
		}
		return mkey;
	}
	
	public String Encrypt(String plain,String key)
	{
		plain=plain.toUpperCase();
		key=key.toUpperCase();
		String enc=new String();
		int len=plain.length();
		if(key.length()!=len)
		{
			System.out.println("encryption not possible");
			return null;
		}
		
		for(int i=0;i<len;i++)
		{
			if((plain.charAt(i)!=' ')&&(key.charAt(i)!=' '))
			enc=enc+(char)((((int)plain.charAt(i)-65)+((int)key.charAt(i)-65))%26+65);
			else
				enc=enc+plain.charAt(i);
		}
		return enc;
	}
	
	public String Decrypt(String cipher,String key)
	{
		cipher=cipher.toUpperCase();
		key=key.toUpperCase();
		String enc=new String();
		int len=cipher.length();
		if(key.length()!=len)
		{
			System.out.println("encryption not possible");
			return null;
		}
		
		for(int i=0;i<len;i++)
		{
			if((cipher.charAt(i)!=' ')&&(key.charAt(i)!=' '))
			{
				int value=(((int)cipher.charAt(i)-65)-((int)key.charAt(i)-65))%26;
				if(value<0)
					value=value+26;
				enc=enc+(char)(value+65);
			}
			else
				enc=enc+cipher.charAt(i);
		}
		return enc;
	}
	
	public static void main(String args[])
	{
		Vigenere vr=new Vigenere();
		Scanner sc=new Scanner(System.in);
		System.out.println("enter plsin text");
		String plain=sc.nextLine();
		System.out.println("Enter key");
		String key=sc.nextLine();
		key=vr.constructKey(key, plain);
		System.out.println("The new key is "+key);
		String enc=vr.Encrypt(plain, key);
		System.out.println("The encrypted text is "+enc);
		System.out.println("The decrypted text is "+vr.Decrypt(enc,key));
	}
}
