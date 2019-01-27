package TextSimplification;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.Scanner;

public class StopWords {
	@SuppressWarnings("resource")
	public void stopword() throws Exception {
		String[] stopWrds = {"a" , "aa" , "acaba", "ait" , "alt�", "altm��", "ama","Ama", "amma", "anca", "anca�", "ancak",
				"art�k", "asla", "asl�nda", "az", "b","bana","bari","ba�kas�","bazen","baz�","baz�lar�","baz�s�","be",
				"belki","ben","bende","benden","beni", "benim", "be�", "bide", "bile","bin","bir","biraz�","birco�",
				"bir�o�u","bir�ok","bir�oklar�","biri","birisi","birka�","birka��","birkez","bir�ey","bir�eyi",
				"biz","bizden","bize","bizi","bizim", "b�yle" , "b�ylece" , "bu", "buna" , "bunda", "bundan", "bunu", 
				"bunun", "burada", "b�t�n", "c","�", "�o�u", "�o�una", "�o�unu", "�ok", "��nk�","d","da","daha","dahi",
				"dandini","de","defa" ,"de�","de�il","de�in","dek","demek","di�er","di�eri","di�erleri","diye","dk",
				"dha", "do�rusu", "doksan", "dokuz","dolay�", "d�rt", "�o�una", "�o�unu", "�ok", "��nk�","d","da","daha",
				"dahi","e","e�er","eh" ,"elbette","elli","en","etkili","f","fakad","fakat","falan","falanca","felan",
				"filan", "filanca", "g", "�", "gene","gere�", "gerek", "gibi", "g�re", "g�rece","h", "hakeza", "hakk�nda", 
				"h�l�", "halbuki", "hangi", "hangisi", "hani", "hasebiyle","hatime", "hatta", "hele", "hem", "hen�z",
				"hep","hepsi","hepsine","hepsini","her", "her biri", "herkes", "herkese", "herkesi","hi�","hi� kimse",
				"hi�biri","hi�birine","hi�birini","ho�","i","�","�n","i�in","i�inde","i�re","iki","ila","ile","imdi",
				"indinde","inta�","intak","ise","i�te","ister","j","k","ka�","ka��","kadar","kah","kar��n","katrilyon",
				"kelli","kendi","kendine","kendini","ke�ke", "ke�ki" , "kez" , "keza", "kezali�" , "kezalik", "ki", "kim", 
				"kimden", "kime", "kimi", "kimin","kimisi", "kimse", "k�rk", "l", "lakin", "m","madem","mademki","mamafih",
				"me�er","me�erki","me�erse" ,"mi","m�","milyar","milyon","mu","m�","n","nas�l","nde","ne","ne kadar",
				"ne zaman","neden","nedense","nedir","nerde","nere","nerede","nereden","nereli","neresi","nereye","nesi",
				"neye","neyi","neyse","ni�in","ni","n�","nin","n�n","nitekim","niye","o","�","�b�rk�","�b�r�","on","�n",
				"ona","�nce","onda","ondan","onlar", "onlara" , "onlardan" , "onlari", "onlar�n" , "onu", "onun", "orada", 
				"�tekisi", "�t�r�", "otuz", "�yle","oysa", "oysaki", "p", "pad", "pat", "peki","r","ra�men","s","�","sak�n",
				"sana","sanki","�ayet" ,"sekiz","seksen","sen","senden","seni","senin","�ey","�eyden","�eye","�eyi","�eyler",
				"�imdi", "siz", "sizden", "size","sizi", "sizin", "son", "sonra", "��yle", "�u","�una","�unda","�undan",
				"�unlar","�unu", "�unun","t","ta" ,"tabi","tamam","tl","trilyon","t�m" ,"t�m�","u","�","��","�sd","�st",
				"uyar�nca","�zere","v" ,"var","ve","velev","velhas�l","velhas�l�kelam","vesselam","veya","veyahud",
				"veyahut","y", "ya","ya da","yani" ,"yaz��","yaz�k","yedi","yekdi�eri","yerine" ,"yetmi�","yine","yirmi",
				"yoksa","yukarda","yukardan" ,"yukar�da","yukar�dan","y�z","z","zaten","zi"};
		try {
			Scanner fip1 = new Scanner(new File("C:\\Users\\Acer\\eclipse-workspace\\TextSimplification\\50.txt")); // file
																													// here
																													// from
																													// which
																													// you
																													// want
																													// to
																													// remove
																													// the
																													// stop
																													// words
			FileWriter fstream;
			BufferedWriter out;
			fstream = new FileWriter("C:\\Users\\Acer\\eclipse-workspace\\TextSimplification\\RemoveStopWordsForm.txt");
			out = new BufferedWriter(fstream);
			System.out.println("Remove stopwords form :");
			while (fip1.hasNext()) {
				int flag = 1;
				String s1 = fip1.next();
				// s1=s1.toLowerCase();
				for (int i = 0; i < stopWrds.length; i++) {
					if (s1.equals(stopWrds[i])) {
						flag = 0;
					}
				}
				if (flag != 0) {
					System.out.print(s1 + " ");
					out.write(s1 + " ");
				}
			}
			out.close();
		} catch (Exception e) {
			System.err.println("cannot read file");
		}
	}
}
