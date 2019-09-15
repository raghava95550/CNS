import java.util.*;
public class AffineKnownPlainTextAttack 
{
	Map<String,String> hmap;

	AffineKnownPlainTextAttack()
	{
		hmap=new HashMap<>();
		hmap.put("1","1"); hmap.put("3","9"); hmap.put("5","21");
		hmap.put("7","15"); hmap.put("9","3"); hmap.put("11","19");
		hmap.put("15","7"); hmap.put("17","23"); hmap.put("19","11");
		hmap.put("21","5"); hmap.put("23","17"); hmap.put("25","25");
	}
	
	public void Decrypt(String Plaintext,String Ciphertext)
	{
		Plaintext=Plaintext.toUpperCase();
		Plaintext=Plaintext.replaceAll(" ","");
		Ciphertext=Ciphertext.toUpperCase();
		int a=-1,b=-1;
		for(int i=0;i<Plaintext.length()-1;i++)
		{
			int c1=(int)Ciphertext.charAt(i)-65;
			int c2=(int)Ciphertext.charAt(i+1)-65;
			int p1=(int)Plaintext.charAt(i)-65;
			int p2=(int)Plaintext.charAt(i+1)-65;
			//System.out.println("c1="+c1+",c2="+c2+",p1="+p1+",p2="+p2);
			if(p1>p2)
			{
				if(hmap.get(Integer.toString(p1-p2))==null)
					continue;
				a=(c1%26)-(c2%26);
				if(a<0)
					a=a+26;
				a=(a*(Integer.parseInt(hmap.get(Integer.toString(p1-p2)))))%26;
				b=(c2%26)-((a*p2)%26);
				if(b<0)
					b=b+26;
				break;
			}
			else if(p2>p1)
			{
				if(hmap.get(Integer.toString(p2-p1))==null)
					continue;
				a=(c2%26)-(c1%26);
				if(a<0)
					a=a+26;
				//System.out.println("val="+(p2-p1));
				String str=hmap.get(Integer.toString(p2-p1));
				a=(a*Integer.parseInt(str))%26;
				b=(c2%26)-((a*p2)%26);
				if(b<0)
					b=b+26;
				break;
			}
		}
		System.out.println("a="+a+", b="+b);
	}
	
	public static void main(String args[])
	{
		AffineKnownPlainTextAttack att=new AffineKnownPlainTextAttack();
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter a Cipher text");
		String s1=sc.nextLine();
		System.out.println("Enter a Plain text");
		String s2=sc.nextLine();
		s2=s2.replaceAll("\\s+","");
		att.Decrypt(s2, s1);
	}
}
