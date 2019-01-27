package TextSimplification;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.Spring;

import net.zemberek.erisim.Zemberek;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.ek.Ek;

public class ZemberekIslemler {

	
	public static void listeYazdir(List<String> strList, String str) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream("C:\\Users\\Acer\\eclipse-workspace\\TextSimplification\\ChangeSynonym.txt"),
				"ISO-8859-9"));
	
	
		String ayrac1 = ".";
		String text1= " ";
		StringTokenizer z = new StringTokenizer(text1, ayrac1);
		System.out.println("Cümle cümle ayýrma: ");
		while (z.hasMoreTokens()) {
			if (z.hasMoreElements() && z.hasMoreTokens()) {
				String token1 = z.nextToken();
				System.out.println(token1 + ".");
		FileWriter fstream;
		BufferedWriter outt;
		fstream = new FileWriter("C:\\Users\\Acer\\eclipse-workspace\\TextSimplification\\123.txt");
		outt = new BufferedWriter(fstream);
		
		outt.write("SharedPredicate Sentence is :");
		for (String s : strList) {
			outt.write(str);
			System.out.print(" "+ s);
			outt.write(" " +s);
			//outt.write(" ");	
		}
		System.out.print(".");
		outt.write(".");
		outt.write(str);
		outt.close();
	}}}
	public static void listeYazdir1(List<String> strList, String str1) throws IOException {
		FileWriter fstream1;
		BufferedWriter out1t;
		fstream1 = new FileWriter("C:\\Users\\Acer\\eclipse-workspace\\TextSimplification\\124.txt");
		out1t = new BufferedWriter(fstream1);
		//out1t.write("SharePredicate Sentence is :");
		for (String s1 : strList) {
			out1t.write(str1);
			System.out.print(" " + s1);
			out1t.write(s1);
			//out1t.write(" ");	
		}
		System.out.print(".");
		out1t.write(".");
		out1t.write(str1);
		out1t.close();
		
	}
	
	public static void copyFile() throws IOException {
		 PrintWriter pw = new PrintWriter("C:\\Users\\Acer\\eclipse-workspace\\TextSimplification\\file3.txt");
	        
	        // BufferedReader object for file1.txt
	        BufferedReader br1 = new BufferedReader(new FileReader("C:\\Users\\Acer\\eclipse-workspace\\TextSimplification\\123.txt"));
	        BufferedReader br2 = new BufferedReader(new FileReader("C:\\Users\\Acer\\eclipse-workspace\\TextSimplification\\124.txt"));
	        String line1 = br1.readLine();
	        String line2 = br2.readLine();
	        while (line1 != null || line2 !=null)
	        {
	            if(line1 != null)
	            {
	                pw.print(line1);
	                line1 = br1.readLine();
	            }
	             
	            if(line2 != null)
	            {
	                pw.print(line2);
	                line2 = br2.readLine();
	            }
	        }
	     
	        
	         
	        // closing resources
	        br1.close();
	        br2.close();
	        pw.close();
	         
	        //System.out.println("Merged file1.txt and file2.txt alternatively into file3.txt");
		} 
	
	
	
	
	public static void copyFile1() throws IOException {
		 PrintWriter pw = new PrintWriter("C:\\Users\\Acer\\eclipse-workspace\\TextSimplification\\SharedPredicate.txt");
	        
	        // BufferedReader object for file1.txt
	        BufferedReader br1 = new BufferedReader(new FileReader("C:\\Users\\Acer\\eclipse-workspace\\TextSimplification\\ChangeSynonym.txt"));
	        BufferedReader br2 = new BufferedReader(new FileReader("C:\\Users\\Acer\\eclipse-workspace\\TextSimplification\\file3.txt"));
	        String line1 = br1.readLine();
	        String line2 = br2.readLine();
	        while (line1 != null || line2 !=null)
	        {
	            if(line1 != null)
	            {
	                pw.println(line1);
	                line1 = br1.readLine();
	            }
	             
	            if(line2 != null)
	            {
	                pw.println(line2);
	                line2 = br2.readLine();
	            }
	        }
	     
	        
	         
	        // closing resources
	        br1.close();
	        br2.close();
	        pw.close();
	         
	        //System.out.println("Merged file1.txt and file2.txt alternatively into file3.txt");
		} 
	
	
	
	public static String getFiil(Zemberek zemberek, String str) {
		Kelime[] kelimeCozum = zemberek.kelimeCozumle(str);
		if (kelimeCozum != null && kelimeCozum.length > 0) {
			KelimeTipi kelimeTipi = kelimeCozum[0].kok().tip();
			if (KelimeTipi.FIIL.equals(kelimeTipi)) {
				return str;
			}
		}
		return null;
	}
	
	
	
	
	}
