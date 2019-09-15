import java.io.FileReader;
import java.util.*;
public class CeaserAttack1 
{
	List<String> itemset;
	/*
	public CeaserAttack1()
	{
		itemset=new ArrayList<String>();
		String path="C:\\Users\\user\\Desktop\\words.txt";
		try
		{
			Scanner fileReader=new Scanner(new FileReader(path));
			while(fileReader.hasNext())
			{
				itemset.add(fileReader.next().toLowerCase());
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	*/
	public String decrypt(String s)
	{
		String out="";
		String finalOut="";
		s=s+" ";
		boolean completed=false;
		for(int i=1;i<26;i++)
		{
			out="";
			int n=s.length();
			for(int j=0;j<n;j++)
			{
				if(s.charAt(j)!=' ')
				{
					int dec=(((int)s.charAt(j)-97)-i)%26;
					if(dec<0)
						dec=dec+26;
					out=out+(char)(dec+97);
				}
				else
				{
					out=out+" ";		
				}
			}
			System.out.println(out);
		}
		//System.out.println("the decrypt string="+finalOut);
		return finalOut;
	}
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter a cipher text");
		String str=sc.nextLine();
		CeaserAttack1 ca=new CeaserAttack1();
		String out=ca.decrypt(str);
		System.out.println("final ="+out);
	}
}
