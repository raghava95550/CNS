import java.io.*;
import java.util.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class DES 
{
	public SecretKey generateKey(String mkey)
	{
		byte key[]=mkey.getBytes();
		SecretKey skey;
		MessageDigest sha=null;
		try
		{
			sha=MessageDigest.getInstance("SHA-1");
			key=sha.digest(key);
			key=Arrays.copyOf(key,8);
			skey = new SecretKeySpec(key,"DES");
			return skey;
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		return null;
	}
	
	public String Encrypt(String ptext,String mkey)
	{
		String ciphertext=new String();
		SecretKey skey=generateKey(mkey);
		try
		{
			Cipher cipher=Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE,skey);
			ciphertext = Base64.getEncoder().encodeToString(cipher.doFinal(ptext.getBytes()));
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		return ciphertext;
	}
	
	public String Decrypt(String ctext,String mkey)
	{
		String plaintext=new String();
		SecretKey skey=generateKey(mkey);
		try
		{
			Cipher cipher=Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE,skey);
			plaintext = new String(cipher.doFinal(Base64.getDecoder().decode(ctext)));
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		
		return plaintext;
	}
	
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		String Key=new String();
		System.out.println("Enter a key string of length 8 min");
		Key=sc.nextLine();
		System.out.println("Enter a String");
		String plaintext=sc.nextLine();
		DES aes=new DES();
		String encs=aes.Encrypt(plaintext, Key);
		System.out.println("Encrypted string is "+encs);
		System.out.println("Decypted string is "+aes.Decrypt(encs, Key));
	}

}
