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
		String[] stopWrds = {"a" , "aa" , "acaba", "ait" , "altý", "altmýþ", "ama","Ama", "amma", "anca", "ancað", "ancak",
				"artýk", "asla", "aslýnda", "az", "b","bana","bari","baþkasý","bazen","bazý","bazýlarý","bazýsý","be",
				"belki","ben","bende","benden","beni", "benim", "beþ", "bide", "bile","bin","bir","birazý","bircoð",
				"birçoðu","birçok","birçoklarý","biri","birisi","birkaç","birkaçý","birkez","birþey","birþeyi",
				"biz","bizden","bize","bizi","bizim", "böyle" , "böylece" , "bu", "buna" , "bunda", "bundan", "bunu", 
				"bunun", "burada", "bütün", "c","ç", "çoðu", "çoðuna", "çoðunu", "çok", "çünkü","d","da","daha","dahi",
				"dandini","de","defa" ,"deð","deðil","deðin","dek","demek","diðer","diðeri","diðerleri","diye","dk",
				"dha", "doðrusu", "doksan", "dokuz","dolayý", "dört", "çoðuna", "çoðunu", "çok", "çünkü","d","da","daha",
				"dahi","e","eðer","eh" ,"elbette","elli","en","etkili","f","fakad","fakat","falan","falanca","felan",
				"filan", "filanca", "g", "ð", "gene","gereð", "gerek", "gibi", "göre", "görece","h", "hakeza", "hakkýnda", 
				"hâlâ", "halbuki", "hangi", "hangisi", "hani", "hasebiyle","hatime", "hatta", "hele", "hem", "henüz",
				"hep","hepsi","hepsine","hepsini","her", "her biri", "herkes", "herkese", "herkesi","hiç","hiç kimse",
				"hiçbiri","hiçbirine","hiçbirini","hoþ","i","ý","ýn","için","içinde","içre","iki","ila","ile","imdi",
				"indinde","intað","intak","ise","iþte","ister","j","k","kaç","kaçý","kadar","kah","karþýn","katrilyon",
				"kelli","kendi","kendine","kendini","keþke", "keþki" , "kez" , "keza", "kezalið" , "kezalik", "ki", "kim", 
				"kimden", "kime", "kimi", "kimin","kimisi", "kimse", "kýrk", "l", "lakin", "m","madem","mademki","mamafih",
				"meðer","meðerki","meðerse" ,"mi","mý","milyar","milyon","mu","mü","n","nasýl","nde","ne","ne kadar",
				"ne zaman","neden","nedense","nedir","nerde","nere","nerede","nereden","nereli","neresi","nereye","nesi",
				"neye","neyi","neyse","niçin","ni","ný","nin","nýn","nitekim","niye","o","ö","öbürkü","öbürü","on","ön",
				"ona","önce","onda","ondan","onlar", "onlara" , "onlardan" , "onlari", "onlarýn" , "onu", "onun", "orada", 
				"ötekisi", "ötürü", "otuz", "öyle","oysa", "oysaki", "p", "pad", "pat", "peki","r","raðmen","s","þ","sakýn",
				"sana","sanki","þayet" ,"sekiz","seksen","sen","senden","seni","senin","þey","þeyden","þeye","þeyi","þeyler",
				"þimdi", "siz", "sizden", "size","sizi", "sizin", "son", "sonra", "þöyle", "þu","þuna","þunda","þundan",
				"þunlar","þunu", "þunun","t","ta" ,"tabi","tamam","tl","trilyon","tüm" ,"tümü","u","ü","üç","üsd","üst",
				"uyarýnca","üzere","v" ,"var","ve","velev","velhasýl","velhasýlýkelam","vesselam","veya","veyahud",
				"veyahut","y", "ya","ya da","yani" ,"yazýð","yazýk","yedi","yekdiðeri","yerine" ,"yetmiþ","yine","yirmi",
				"yoksa","yukarda","yukardan" ,"yukarýda","yukarýdan","yüz","z","zaten","zi"};
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
