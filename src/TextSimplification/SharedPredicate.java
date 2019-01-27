package TextSimplification;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import TextSimplification.ZemberekIslemler;
import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.Kelime;

public class SharedPredicate {
	@SuppressWarnings("resource")
	public void SharePredicate() throws Exception {
		Zemberek zemberek = new Zemberek(new TurkiyeTurkcesi());
		File file = new File("C:\\Users\\Acer\\eclipse-workspace\\TextSimplification\\ChangeSynonym.txt");
		BufferedReader reader = null;

		reader = new BufferedReader(new FileReader(file));
		String satir = reader.readLine();
		while (satir != null) {
			satir = reader.readLine();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream("C:\\Users\\Acer\\eclipse-workspace\\TextSimplification\\ChangeSynonym.txt"),
					"ISO-8859-9"));

			{
				String text = " ";
				String line = null;
				{
					while ((line = br.readLine()) != null) {
						text += line;
					}
				}
			
				FileWriter fstream;
				BufferedWriter out;
				//fstream = new FileWriter("C:\\Users\\Acer\\eclipse-workspace\\TextSimplification\\75.txt");
				//out = new BufferedWriter(fstream);
				String ayrac = ".";
				StringTokenizer y = new StringTokenizer(text, ayrac);
				System.out.println("Cümle cümle ayýrma: ");
			while (y.hasMoreTokens()) {
					if (y.hasMoreElements() && y.hasMoreTokens()) {
						String token = y.nextToken();
						System.out.println(token + ".");
						//out.write(token + ".");

						this.cumleCozumle(zemberek, token);

						continue;

					}

				}
				//out.close();
			}
		}
	}

	private void cumleCozumle(Zemberek zemberek, String str) throws IOException {
		List<String> birinciCumle = new ArrayList<String>();
		List<String> ikinciCumle = new ArrayList<String>();	
		StringTokenizer a = new StringTokenizer(str, ";");
		int count = 0;
		while (a.hasMoreElements()) {
			if (a.hasMoreElements() && a.hasMoreTokens()) {
				String kelimeler = a.nextToken();
				count++;
				if (count <= 1) {
					if (birinciCumle.add(kelimeler)) {
						continue;
					}
				}
				ikinciCumle.add(kelimeler);
				StringTokenizer ab = new StringTokenizer(kelimeler, " ");
				while (ab.hasMoreTokens()) {
					String kelime = ab.nextToken();
					String fiil = ZemberekIslemler.getFiil(zemberek, kelime);
					if (fiil != null) {
						if (birinciCumle.add(fiil)) {
							System.out.println("SharePredicate Sentence is :");
						}
					}
				}
				
				ZemberekIslemler.listeYazdir(birinciCumle, "");
				ZemberekIslemler.listeYazdir1(ikinciCumle, "");
				
			}
		}
	}
}
