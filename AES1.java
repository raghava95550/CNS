import java.io.*;
import java.security.*;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.*;
public class AES1
{
	public byte[] generateKey(String mkey)
	{
		byte key[]=mkey.getBytes();
		MessageDigest sha=null;
		try
		{
			sha=MessageDigest.getInstance("SHA-1");
			key=sha.digest(key);
			key=Arrays.copyOf(key,16);
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		return key;
	}
	
	
	public String Encrypt(String ptext,String key)
	{
		String ciphertext=new String();
		byte mkey[]=generateKey(key);
		SecretKey skey=new SecretKeySpec(mkey,"AES");
		try
		{
			Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE,skey);
			ciphertext=Base64.getEncoder().encodeToString(cipher.doFinal(ptext.getBytes()));
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		return ciphertext;
	}
	
	public String Decrypt(String ctext,String key)
	{
		String plaintext=new String();
		byte mkey[]=generateKey(key);
		SecretKey skey=new SecretKeySpec(mkey,"AES");
		try
		{
			Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE,skey);
			plaintext=new String(cipher.doFinal(Base64.getDecoder().decode(ctext.getBytes())));
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
		System.out.println("Enter a key string of length 16 min");
		Key=sc.nextLine();
		System.out.println("Enter a String");
		String plaintext=sc.nextLine();
		AES1 aes=new AES1();
		String encs=aes.Encrypt(plaintext, Key);
		System.out.println("Encrypted string is "+encs);
		System.out.println("Decypted string is "+aes.Decrypt(encs, Key));
	}
}
