import java.util.*;
import java.math.*;
public class RSA 
{
	private BigInteger p;
	private BigInteger q;
	private BigInteger n;
	private BigInteger phi;
	private BigInteger e; //public key
	private BigInteger d; //private key
	private int bitlength=1024;//maximum bit length
	private Random r;
	
	RSA()
	{
		r=new Random();
		p=BigInteger.probablePrime(bitlength, r);
		q=BigInteger.probablePrime(bitlength, r);
		n=p.multiply(q);
		phi=(p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		e=BigInteger.probablePrime(bitlength/2, r);
		while(phi.gcd(e).compareTo(BigInteger.ONE)>0 && e.compareTo(phi)<0)
		{
			e.add(BigInteger.ONE);
		}
		d=e.modInverse(phi);
		System.out.println("Public key:"+e.toString()+","+n.toString());
		System.out.println("Private key:"+d.toString()+","+n.toString());
	}
	
	public byte[] encrypt(byte[] plaintext) 
	{
		byte[] encrypted=(new BigInteger(plaintext)).modPow(e,n).toByteArray();
		return encrypted;
	}
	
	public byte[] decrypt(byte[] ciphertext)
	{
		byte[] decrypted=(new BigInteger(ciphertext)).modPow(d,n).toByteArray();
		return decrypted;
	}
	
	public static void main(String args[])
	{
		RSA rsa=new RSA();
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter a string");
		String plaintext=sc.nextLine();
		byte[] cipherbytes=rsa.encrypt(plaintext.getBytes());
		System.out.println("Encrypted string ="+new String(cipherbytes));
		System.out.println("-------------------------------------------------------");
		byte[] plainbytes=rsa.decrypt(cipherbytes);
		System.out.println("decrypted string ="+new String(plainbytes));
		sc.close();
	}
}
