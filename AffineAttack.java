// Cipher text only attack

import java.util.*;
import java.io.*;
public class AffineAttack 
{ 
	Map<String,String> hmap;
	
	List<String> itemset;
	
	public AffineAttack()
	{
		hmap=new HashMap<>();
		hmap.put("1","1"); hmap.put("3","9"); hmap.put("5","21");
		hmap.put("7","15"); hmap.put("9","3"); hmap.put("11","19");
		hmap.put("15","7"); hmap.put("17","23"); hmap.put("19","11");
		hmap.put("21","5"); hmap.put("23","17"); hmap.put("25","25");
		itemset=new ArrayList<String>();
		String path="C:\\Users\\user\\Desktop\\words.txt";
		try
		{
			Scanner fileReader=new Scanner(new FileReader(path));
			while(fileReader.hasNext())
			{
				itemset.add(fileReader.next().toUpperCase());
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	
	public void decrypt(String cipher)
	{
		int a,b,count=0,thres=0;
		Set<String> keyset=hmap.keySet();
		System.out.println("Decrypted strings are:");
		for(String i:keyset)
		{
			for(int j=0;j<26;j++)
			{
				String out="";
				String dec="";thres=0;
				for(int k=0;k<cipher.length();k++)
				{
					if(cipher.charAt(k)!='2')
					{
						int n=(int)cipher.charAt(k)-65;
						int n2=n-j;
						int n3;
						if(n2>=0)
						n3=(n2*Integer.parseInt(hmap.get(i)))%26;
						else
							n3=((n2+26)*Integer.parseInt(hmap.get(i)))%26;
						dec=dec+(char)(n3+65);
					}
					else
					{
						thres++;
						if((!itemset.contains(dec))&&(1<=thres&&thres<=2))
							break;
						else
							out=out+" "+dec;
						dec="";
					}
				}
				if(thres>2)
				{
					count++;
					out=out+" "+dec;
					out=out.substring(1,out.length());
					System.out.println(out);
				}
			}
		}
		System.out.println("Total count="+count);
	}
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter a cipher text with more than two words");
		String cipher=sc.nextLine();
		AffineAttack att=new AffineAttack();
		att.decrypt(cipher);
	}
}
