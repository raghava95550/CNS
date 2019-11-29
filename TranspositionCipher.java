// Transposition cipher here implemented is columnar cipher
import java.util.*;
public class TranspositionCipher 
{
	int row,col;
	public char[][] constructMatrix(String key,String text)
	{
		int n=key.length();
		row=(int)Math.ceil((double)text.length()/n);
		col=n;
		char matrix[][]=new char[row][n];
		int k=0;
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				if(k<text.length())
				{
					if(text.charAt(k)!=' ')
					matrix[i][j]=text.charAt(k++);
					else
					{
						matrix[i][j]='_';k++;
					}
				}
				else
				matrix[i][j]='_';
			}
		}
		System.out.println("Printing the matrix");
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<n;j++)
				System.out.print(matrix[i][j]+" ");
			System.out.println();
		}
		return matrix;
	}
	
	public int position(String text,char c)
	{
		for(int i=0;i<text.length();i++)
		{
			if(c==text.charAt(i))
				return i;
		}
		return -1;
	}
	public String encrypt(String text,String key)
	{
		char matrix[][]=constructMatrix(key,text);
		char ch[]=key.toCharArray();
		Arrays.sort(ch);
		String encs=new String();
		for(int i=0;i<col;i++)
		{
			int pos=position(key,ch[i]);
			for(int j=0;j<row;j++)
			encs=encs+matrix[j][pos];
		}
		System.out.println("Encrypted String:"+encs);
		return encs;
	}
	
	public String decrypt(String text,String key)
	{
		String decs="";
		char matrix[][]=constructMatrix(key,text);
		char ch[]=key.toCharArray();
		Arrays.sort(ch);
		for(int i=0;i<col;i++)
		{
			int pos=position(key,ch[i]);
			for(int j=0;j<row;j++)
			decs=decs+matrix[j][pos];
		}
		return decs;
	}
	
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the Plain Text");
		String text=sc.nextLine();
		System.out.println("Enter the key");
		String key=sc.nextLine();
		TranspositionCipher tc=new TranspositionCipher();
		//char res[][]=tc.constructMatrix(key, text);
		String encs=tc.encrypt(text, key);
		String decs=tc.decrypt(encs, key);
		System.out.println("Decrypted String:"+decs);
		sc.close();
	}
}
