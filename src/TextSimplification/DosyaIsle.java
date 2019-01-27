/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TextSimplification;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.Kok;
import net.zemberek.yapi.KelimeTipi;

/**
 *
 * @author Acer
 */
public class DosyaIsle {
	private Map<String, String> dictionaryMap = new HashMap<String, String>();

	@SuppressWarnings("resource")
	public void processFileOperation() throws Exception {
		Zemberek zemberek = new Zemberek(new TurkiyeTurkcesi());
		int charCount = 0;
		int heceCount = 0;
		int wordCount = 0;
		int sentenceCount = 0;
		int lineCount = 0;
		this.prepareDictionaryMap();
		// TODO code application logic here
		System.out.println("       ");
		File file = new File("C:\\Users\\Acer\\eclipse-workspace\\TextSimplification\\RemoveStopWordsForm.txt");
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(file));
		String satir = reader.readLine();

		while (satir != null) {
			satir = reader.readLine();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream("C:\\Users\\Acer\\eclipse-workspace\\TextSimplification\\RemoveStopWordsForm.txt"),
					"ISO-8859-9"));
			{
				String text = " ";
				String line = null;
				{
					while ((line = br.readLine()) != null) {
						text += line;
						lineCount++;
						charCount += line.length();
						for (int i = 0; i < line.length(); i++) {
							char c = line.charAt(i);
							line = line.toLowerCase();
							if ((c == 'a') || (c == 'e') || (c == 'i') || (c == '?') || (c == 'o') || (c == '?')
									|| (c == 'u') || (c == '?')) {
								heceCount++;
							} else if (c == ' ') {
								wordCount++;
							} else if ((c == '!') || (c == '?')) {
								sentenceCount++;
							} else if (c == '.') {
								if (!(line.charAt(i - 1) == '.')) {
									sentenceCount++;
								}
							}
						}
					}
					wordCount += lineCount;
					System.out.println("Karakter sayisi : " + charCount);
					System.out.println("Hece sayisi : " + heceCount);
					System.out.println("Kelime sayisi : " + wordCount);
					System.out.println("Cumle sayisi : " + sentenceCount);
					System.out.println("Satir sayisi : " + lineCount);
					System.out.println("             ");
					System.out.println("             ");
				}
				FileWriter fstream;
				BufferedWriter out;
				fstream = new FileWriter("C:\\Users\\Acer\\eclipse-workspace\\TextSimplification\\ChangeSynonym.txt");
				out = new BufferedWriter(fstream);
				String ayrac = ".";
				StringTokenizer y = new StringTokenizer(text, ayrac);
				System.out.println("C�mle c�mle ayr�lm�� hali:");
				while (y.hasMoreTokens()) {
					System.out.println(y.nextToken());
				}
				String ayracc = " ";
				StringTokenizer a = new StringTokenizer(text, ayracc);
				System.out.println("             ");
				System.out.println("             ");
				System.out.println("             ");
				System.out.println("Kelime kelime ayr�l�p e�  anlaml�s�yla de�i�tirilmi� hali ");
				while (a.hasMoreTokens()) {
					if (a.hasMoreElements() && a.hasMoreTokens()) {
						String orjinalKelime = a.nextToken();
						boolean isWordValid = zemberek.kelimeDenetle(orjinalKelime);
						if (!isWordValid) {
							System.out.println("Kelime T�rk�e De�il :" + orjinalKelime);
							out.write(orjinalKelime + " ");
							continue;
						}
						String kokKelime = FileOperationsUtils.kelimeCozumle(zemberek, orjinalKelime);
						boolean boo = FileOperationsUtils.isFirstLetterLower(orjinalKelime);
						String esAnlamliKelime = null;
						String ilkHarfiKucukKelime = FileOperationsUtils.doUpperCaseFirstLetter(kokKelime);
						esAnlamliKelime = dictionaryMap.get(ilkHarfiKucukKelime);
						if (esAnlamliKelime != null) {
							if (boo) {
								String esAnlamliKelimeSonhali = FileOperationsUtils
										.doLowerCaseFirstLetter(esAnlamliKelime);
								String kelimeninSonHali = FileOperationsUtils.getZemberekKelime(zemberek, orjinalKelime,
										esAnlamliKelimeSonhali);
								out.write(kelimeninSonHali + " ");
							} else {
								String orjinalKelime2 = FileOperationsUtils.doLowerCaseFirstLetter(orjinalKelime);
								String esAnlamliKelime2 = FileOperationsUtils.doLowerCaseFirstLetter(esAnlamliKelime);
								String kelimeninSonHali = FileOperationsUtils.getZemberekKelime(zemberek,
										orjinalKelime2, esAnlamliKelime2);
								if (kelimeninSonHali != null) {
									kelimeninSonHali = FileOperationsUtils.doUpperCaseFirstLetter(kelimeninSonHali);
									out.write(kelimeninSonHali + " ");
								}
							}

						} else {
							System.out.println(orjinalKelime);
							out.write(orjinalKelime + " ");
						}
					}
				}
				out.close();
				BufferedReader br1 = new BufferedReader(new InputStreamReader(
						new FileInputStream("C:\\Users\\Acer\\eclipse-workspace\\TextSimplification\\ChangeSynonym.txt"),
						"ISO-8859-9"));
				{
					String text1 = " ";
					String line1 = br1.readLine();
					while (line1 != null) {
						text1 += line1;
						line1 = br1.readLine();
					}
					System.out.println("             ");
					System.out.println("             ");
					System.out.println("             ");
					System.out.println("2 den fazla tekrar eden kelime say�s� :");
					System.out.println("             ");
					String string2[] = text.split("[(' '),.-]");
					HashMap<String, Integer> uniques = new HashMap<String, Integer>();
					for (String word : string2) {
						if (word.length() < 2) {
							continue;
						}
						Integer existingCount = uniques.get(word);
						uniques.put(word, existingCount == null ? 1 : (existingCount + 1));
					}
					Set<Map.Entry<String, Integer>> uniqueSet = uniques.entrySet();
					for (Map.Entry<String, Integer> entry : uniqueSet) {
						if (entry.getValue() > 1) {
							System.out.println(entry.getKey() + " " + entry.getValue());
						} else {
						}
					}
				}
				System.out.println("--------------");
			}
		}
	}

	private void prepareDictionaryMap() {
		dictionaryMap.put("Abide", "An�t");
		dictionaryMap.put("Acayip", "Garip");
		dictionaryMap.put("Ac�ma", "Merhamet");
		dictionaryMap.put("A��kg�z", "Kurnaz");
		dictionaryMap.put("Ad", "�sim");
		dictionaryMap.put("Adale", "Kas");
		dictionaryMap.put("Adet", "Tane");
		dictionaryMap.put("Ad�l", "Zamir");
		dictionaryMap.put("Adi", "Baya��");
		dictionaryMap.put("Ahenk", "Uyum");
		dictionaryMap.put("Aksi", "Ters");
		dictionaryMap.put("K�rm�z�", "Al");
		dictionaryMap.put("Alaz", "Alev");
		dictionaryMap.put("S�radan", "Alelade");
		dictionaryMap.put("Amele", "���i");
		dictionaryMap.put("Amel", "��");
		dictionaryMap.put("Ana", "Anne");
		dictionaryMap.put("An�", "Hat�ra");
		dictionaryMap.put("Ant", "Yemin");
		dictionaryMap.put("Apse", "�ltihap");
		dictionaryMap.put("Ar�za", "Bozukluk");
		dictionaryMap.put("Arma�an", "Hediye");
		dictionaryMap.put("Arzu", "�stek");
		dictionaryMap.put("As�r", "Y�zy�l");
		dictionaryMap.put("Atak", "Giri�ken");
		dictionaryMap.put("Avare", "Serseri");
		dictionaryMap.put("�mtiyaz", "Ayr�cal�k");
		dictionaryMap.put("Aza", "�ye");
		dictionaryMap.put("Acemi", "Toy");
		dictionaryMap.put("Adalet", "Hak");
		dictionaryMap.put("Aka", "B�y�k");
		dictionaryMap.put("Aleni", "A��k");
		dictionaryMap.put("Ba�naz", "Yobaz");
		dictionaryMap.put("Sulh", "Bar��");
		dictionaryMap.put("Matbaa", "Bas�mevi");
		dictionaryMap.put("Ba�kald�r�", "�syan");
		dictionaryMap.put("Ba��ehir", "Ba�kent");
		dictionaryMap.put("M�racaat", "Ba�vuru");
		dictionaryMap.put("Baytar", "Veteriner");
		dictionaryMap.put("Belde", "�ehir");
		dictionaryMap.put("Bellek", "Haf�za");
		dictionaryMap.put("Benlik", "Ki�ilik");
		dictionaryMap.put("Beraber", "Birlikte");
		dictionaryMap.put("Bereket", "Bolluk");
		dictionaryMap.put("�lan", "Duyuru");
		dictionaryMap.put("Berrak", "Duru");
		dictionaryMap.put("Ak", "Beyaz");
		dictionaryMap.put("Beygir", "At");
		dictionaryMap.put("Beyhude", "Bo�una");
		dictionaryMap.put("Bi�are", "Zavall�");
		dictionaryMap.put("Bilakis", "Tersine");
		dictionaryMap.put("Bilhassa", "�zellikle");
		dictionaryMap.put("Bina", "Yap�");
		dictionaryMap.put("Birden", "Ani");
		dictionaryMap.put("Birdenbire", "Aniden");
		dictionaryMap.put("Step", "Bozk�r");
		dictionaryMap.put("Nahiye", "Bucak");
		dictionaryMap.put("Bu�u", "Buhar");
		dictionaryMap.put("Buhran", "Bunal�m");
		dictionaryMap.put("Buyruk", "Emir");
		dictionaryMap.put("B�ro", "Ofis");
		dictionaryMap.put("Cahil", "Bilgisiz");
		dictionaryMap.put("Caka", "G�steri�");
		dictionaryMap.put("Camekan", "Vitrin");
		dictionaryMap.put("Cehalet", "Bilgisizlik");
		dictionaryMap.put("Celse", "Oturum");
		dictionaryMap.put("Cenk", "Sava�");
		dictionaryMap.put("Cenup", "G�ney");
		dictionaryMap.put("Cerahat", "�rin");
		dictionaryMap.put("Cesur", "Y�rekli");
		dictionaryMap.put("Cet", "Ata");
		dictionaryMap.put("Cevap", "Yan�t");
		dictionaryMap.put("Cihaz", "Ayg�t");
		dictionaryMap.put("Cihet", "Y�n");
		dictionaryMap.put("Cilt", "Ten");
		dictionaryMap.put("Civar", "Y�re");
		dictionaryMap.put("C�l�z", "S�ska");
		dictionaryMap.put("A��rba�l�", "Ciddi");
		dictionaryMap.put("Cihet", "Y�n");
		dictionaryMap.put("Cins", "T�r");
		dictionaryMap.put("�abuk", "Acele");
		dictionaryMap.put("�a�da�", "Modern");
		dictionaryMap.put("�a�r�", "Davet");
		dictionaryMap.put("�ehre", "Y�z");
		dictionaryMap.put("�e�it", "T�r");
		dictionaryMap.put("Feryat", "���l�k");
		dictionaryMap.put("��lg�n", "Deli");
		dictionaryMap.put("Ekseriyet", "�o�unluk");
		dictionaryMap.put("Bal��k", "�amur");
		dictionaryMap.put("Deva", "�are");
		dictionaryMap.put("Darbe", "Vuru�");
		dictionaryMap.put("Defa", "Kez");
		dictionaryMap.put("De�er", "K�ymet");
		dictionaryMap.put("Delil", "Kan�t");
		dictionaryMap.put("Denetim", "Kontrol");
		dictionaryMap.put("Zerzele", "Deprem");
		dictionaryMap.put("Sebep", "Neden");
		dictionaryMap.put("Derhal", "Hemen");
		dictionaryMap.put("Devinim", "Hareket");
		dictionaryMap.put("Devir", "�a�");
		dictionaryMap.put("Devre", "D�nem");
		dictionaryMap.put("D��al�m", "�thalat");
		dictionaryMap.put("�htifal", "Anma");
		dictionaryMap.put("D��sat�m", "�hracat");
		dictionaryMap.put("�hbar", "Bildirim");
		dictionaryMap.put("Diri", "Canl�");
		dictionaryMap.put("M�sra", "Dize");
		dictionaryMap.put("Donuk", "Mat");
		dictionaryMap.put("Doruk", "Zirve");
		dictionaryMap.put("D�neme�", "Viraj");
		dictionaryMap.put("Maznun", "San�k");
		dictionaryMap.put("D��ek", "Yatak");
		dictionaryMap.put("Duygu", "His");
		dictionaryMap.put("D�zmece", "Sahte");
		dictionaryMap.put("Dilek", "�stek");
		dictionaryMap.put("Dizi", "S�ra");
		dictionaryMap.put("Ebat", "Boyut");
		dictionaryMap.put("Ebedi", "Sonsuz");
		dictionaryMap.put("Egoist", "Bencil");
		dictionaryMap.put("Ehemmiyet", "�nem");
		dictionaryMap.put("Tebaa", "Uyruk");
		dictionaryMap.put("Endi�e", "Kayg�");
		dictionaryMap.put("Enkaz", "Y�k�nt�");
		dictionaryMap.put("Enlem", "Paralel");
		dictionaryMap.put("Entari", "Giysi");
		dictionaryMap.put("Enteresan", "�lgin�");
		dictionaryMap.put("Erek", "Ama�");
		dictionaryMap.put("Esas", "Temel");
		dictionaryMap.put("Yap�t", "Eser");
		dictionaryMap.put("Evvel", "�nce");
		dictionaryMap.put("Eylem", "Fiil");
		dictionaryMap.put("Eylemsi", "Fiilimsi");
		dictionaryMap.put("Yaz�n", "Edebiyat");
		dictionaryMap.put("Faaliyet", "Etkinlik");
		dictionaryMap.put("Fakat", "Ama");
		dictionaryMap.put("Fakir", "Yoksul");
		dictionaryMap.put("Fark", "Ayr�m");
		dictionaryMap.put("Fas�la", "Ara");
		dictionaryMap.put("Fayda", "Yarar");
		dictionaryMap.put("Ziyade", "Fazla");
		dictionaryMap.put("Fert", "Birey");
		dictionaryMap.put("Feza", "Uzay");
		dictionaryMap.put("Fikir", "D���nce");
		dictionaryMap.put("L�ks", "G�steri�");
		dictionaryMap.put("Gedikli", "Gedikli");
		dictionaryMap.put("N�zul", "Fel�");
		dictionaryMap.put("Fer", "I��k");
		dictionaryMap.put("Garp", "Bat�");
		dictionaryMap.put("Gayret", "�aba");
		dictionaryMap.put("Gebe", "Hamile");
		dictionaryMap.put("G�da", "Besin");
		dictionaryMap.put("Giz", "S�r");
		dictionaryMap.put("Sakl�", "Gizli");
		dictionaryMap.put("G�z", "Sonbahar");
		dictionaryMap.put("Rasat", "G�zlem");
		dictionaryMap.put("Sema", "G�ky�z�");
		dictionaryMap.put("Halbuki", "Oysa");
		dictionaryMap.put("Ham", "Olmam��");
		dictionaryMap.put("Hari�", "D��");
		dictionaryMap.put("Harp", "Sava�");
		dictionaryMap.put("Hasret", "�zlem");
		dictionaryMap.put("Haysiyet", "Onur");
		dictionaryMap.put("Hekim", "Doktor");
		dictionaryMap.put("Hayta", "Serseri");
		dictionaryMap.put("Yurt", "Vatan");
		dictionaryMap.put("Hela", "Tuvalet");
		dictionaryMap.put("Hiddet", "�fke");
		dictionaryMap.put("Hisse", "Pay");
		dictionaryMap.put("Hudut", "S�n�r");
		dictionaryMap.put("Hususi", "�zel");
		dictionaryMap.put("H�r", "�zg�r");
		dictionaryMap.put("H�s�m", "Akraba");
		dictionaryMap.put("Has�m", "D��man");
		dictionaryMap.put("Il�ca", "Kapl�ca");
		dictionaryMap.put("Irak", "Uzak");
		dictionaryMap.put("Irk", "Soy");
		dictionaryMap.put("Ira", "Karakter");
		dictionaryMap.put("�cat", "Bulu�");
		dictionaryMap.put("��ten", "Samimi");
		dictionaryMap.put("�dare", "Y�netim");
		dictionaryMap.put("�htiyar", "Ya�l�");
		dictionaryMap.put("�htiya�", "Gereksinim");
		dictionaryMap.put("�ktisat", "Ekonomi");
		dictionaryMap.put("�lave", "Ek");
		dictionaryMap.put("�lge�", "Edat");
		dictionaryMap.put("�lgi", "Alaka");
		dictionaryMap.put("�lim", "Bilim");
		dictionaryMap.put("�mar", "Bay�nd�r");
		dictionaryMap.put("�skemle", "Sandalye");
		dictionaryMap.put("�tibar", "Sayg�nl�k");
		dictionaryMap.put("�timat", "G�ven");
		dictionaryMap.put("�tina", "�zen");
		dictionaryMap.put("�zah", "A��klama");
		dictionaryMap.put("�dadi", "Lise");
		dictionaryMap.put("M�nasebet", "�li�ki");
		dictionaryMap.put("�mtihan", "S�nav");
		dictionaryMap.put("Kabiliyet", "Yetenek");
		dictionaryMap.put("K�fi", "Yeter");
		dictionaryMap.put("Kafiye", "Uyak");
		dictionaryMap.put("Kamu", "Halk");
		dictionaryMap.put("Siyah", "Kara");
		dictionaryMap.put("Kar��n", "Ra�men");
		dictionaryMap.put("Kar��t", "Z�t");
		dictionaryMap.put("Kat�", "Sert");
		dictionaryMap.put("Keder", "Ac�");
		dictionaryMap.put("Kent", "�ehir");
		dictionaryMap.put("Kocaman", "�ri");
		dictionaryMap.put("Konuk", "misafir");
		dictionaryMap.put("Konut", "Ev");
		dictionaryMap.put("Kaide", "Kural");
		dictionaryMap.put("Mukaddes", "Kutsal");
		dictionaryMap.put("Kuvvet", "G��");
		dictionaryMap.put("�imal", "Kuzey");
		dictionaryMap.put("K�lavuz", "Rehber");
		dictionaryMap.put("Kolay", "Basit");
		dictionaryMap.put("Lahza", "An");
		dictionaryMap.put("Lisan", "Dil");
		dictionaryMap.put("L�zumlu", "Gerekli");
		dictionaryMap.put("L�zumsuz", "Gereksiz");
		dictionaryMap.put("L�l", "Dilsiz");
		dictionaryMap.put("Lama", "Deve");
		dictionaryMap.put("Lafazan", "Geveze");
		dictionaryMap.put("Mabet", "Tap�nak");
		dictionaryMap.put("Mafsal", "Eklem");
		dictionaryMap.put("Ma�lup", "Yenik");
		dictionaryMap.put("Yenilgi", "Ma�lubiyet");
		dictionaryMap.put("Mahalli", "Yerel");
		dictionaryMap.put("Mahcup", "Utanga�");
		dictionaryMap.put("Mahluk", "Yarat�k");
		dictionaryMap.put("Mahpushane", "Hapishane");
		dictionaryMap.put("Mahsul", "�r�n");
		dictionaryMap.put("Manzara", "G�r�n�m");
		dictionaryMap.put("Matem", "Yas");
		dictionaryMap.put("Mebus", "Milletvekili");
		dictionaryMap.put("Yengi", "Galibiyet");
		dictionaryMap.put("Mecburi", "Zorunlu");
		dictionaryMap.put("Mecmua", "Dergi");
		dictionaryMap.put("Medeni", "Uygar");
		dictionaryMap.put("Medeniyet", "Uygarl�k");
		dictionaryMap.put("Mektep", "Okul");
		dictionaryMap.put("Melodi", "Ezgi");
		dictionaryMap.put("Menfaat", "��kar");
		dictionaryMap.put("Menfi", "Olumsuz");
		dictionaryMap.put("Menkul", "Ta��n�r");
		dictionaryMap.put("Meridyen", "Boylam");
		dictionaryMap.put("Mesafe", "Ara");
		dictionaryMap.put("Mesele", "Sorun");
		dictionaryMap.put("Mesut", "Mutlu");
		dictionaryMap.put("Me�hur", "�nl�");
		dictionaryMap.put("Me�rubat", "��ecek");
		dictionaryMap.put("Misal", "�rnek");
		dictionaryMap.put("Muharebe", "Sava�");
		dictionaryMap.put("Muhtelif", "�e�itli");
		dictionaryMap.put("Mu�tu", "M�jde");
		dictionaryMap.put("M�kafat", "�d�l");
		dictionaryMap.put("M�sabaka", "Yar��ma");
		dictionaryMap.put("M�sait", "Uygun");
		dictionaryMap.put("M�stahsil", "�retici");
		dictionaryMap.put("Merkep", "E�ek");
		dictionaryMap.put("Muallim", "��retmen");
		dictionaryMap.put("Merasim", "T�ren");
		dictionaryMap.put("Nadir", "Engel");
		dictionaryMap.put("Nasihat", "���t");
		dictionaryMap.put("Nebat", "Bitki");
		dictionaryMap.put("Soluk", "Nefes");
		dictionaryMap.put("Nesil", "Ku�ak");
		dictionaryMap.put("Nesir", "D�zyaz�");
		dictionaryMap.put("Netice", "Sonu�");
		dictionaryMap.put("Nispet", "Oran");
		dictionaryMap.put("Nitelik", "Kalite");
		dictionaryMap.put("Noksan", "Eksik");
		dictionaryMap.put("Numune", "�rnek");
		dictionaryMap.put("Nakliyeci", "Ta��mac�");
		dictionaryMap.put("Namzet", "Aday");
		dictionaryMap.put("Olanak", "�mkan");
		dictionaryMap.put("Olas�", "M�mk�n");
		dictionaryMap.put("Mera", "Otlak");
		dictionaryMap.put("Ozan", "�air");
		dictionaryMap.put("�bek", "Grup");
		dictionaryMap.put("Aidat", "�denti");
		dictionaryMap.put("�dlek", "Korkak");
		dictionaryMap.put("Taviz", "�d�n");
		dictionaryMap.put("Tahsil", "��renim");
		dictionaryMap.put("�m�r", "Hayat");
		dictionaryMap.put("�nder", "Lider");
		dictionaryMap.put("Te�kilat", "�rg�t");
		dictionaryMap.put("�teki", "Di�eri");
		dictionaryMap.put("�t�r�", "Dolay�");
		dictionaryMap.put("�yk�", "Hikaye");
		dictionaryMap.put("Orijinal", "�zg�n");
		dictionaryMap.put("Pabu�", "Ayakkab�");
		dictionaryMap.put("Pasif", "Edilgen");
		dictionaryMap.put("Pinti", "Cimri");
		dictionaryMap.put("Pis", "Kirli");
		dictionaryMap.put("Politika", "Siyaset");
		dictionaryMap.put("Rastlant�", "Tesad�f");
		dictionaryMap.put("Rey", "Oy");
		dictionaryMap.put("Rutubet", "Nem");
		dictionaryMap.put("Problem", "Sorun");
		dictionaryMap.put("R��tiye", "Ortaokul");
		dictionaryMap.put("Sade", "Yal�n");
		dictionaryMap.put("H�cum", "Sald�r�");
		dictionaryMap.put("Sanayi", "End�stri");
		dictionaryMap.put("Kapital", "Sermaye");
		dictionaryMap.put("Ser�ven", "Macera");
		dictionaryMap.put("S�hhat", "Sa�l�k");
		dictionaryMap.put("Nihayet", "Son");
		dictionaryMap.put("Soylu", "Asil");
		dictionaryMap.put("S�mestri", "Yar�y�l");
		dictionaryMap.put("S�ylev", "Nutuk");
		dictionaryMap.put("S�zc�k", "Kelime");
		dictionaryMap.put("Suni", "Yapay");
		dictionaryMap.put("S�rat", "H�z");
		dictionaryMap.put("Mesuliyet", "Sorumluluk");
		dictionaryMap.put("L�gat", "S�zl�k");
		dictionaryMap.put("Tan", "�afak");
		dictionaryMap.put("�ah�s", "Ki�i");
		dictionaryMap.put("�ark", "Do�u");
		dictionaryMap.put("�art", "Ko�ul");
		dictionaryMap.put("�ayet", "E�er");
		dictionaryMap.put("��hret", "�n");
		dictionaryMap.put("�uur", "Bilin�");
		dictionaryMap.put("��phe", "Ku�ku");
		dictionaryMap.put("Latife", "�aka");
		dictionaryMap.put("Tabiat", "Do�a");
		dictionaryMap.put("Tabii", "Do�al");
		dictionaryMap.put("Talebe", "��renci");
		dictionaryMap.put("Talih", "�ans");
		dictionaryMap.put("Tamir", "Onar�m");
		dictionaryMap.put("Tan�k", "�ahit");
		dictionaryMap.put("Tasdik", "Onay");
		dictionaryMap.put("Tedbir", "�nlem");
		dictionaryMap.put("Teklif", "�neri");
		dictionaryMap.put("Terc�me", "�eviri");
		dictionaryMap.put("Tertip", "D�zen");
		dictionaryMap.put("Tesir", "Etki");
		dictionaryMap.put("Toplum", "Cemiyet");
		dictionaryMap.put("Tutsak", "Esir");
		dictionaryMap.put("Ur", "T�m�r");
		dictionaryMap.put("M�naka�a", "Tart��ma");
		dictionaryMap.put("Ulus", "Millet");
		dictionaryMap.put("Umumi", "Genel");
		dictionaryMap.put("Unsur", "��e");
		dictionaryMap.put("Us", "Ak�l");
		dictionaryMap.put("Tayyare", "U�ak");
		dictionaryMap.put("Ehil", "Usta");
		dictionaryMap.put("�lk�", "�deal");
		dictionaryMap.put("�le�", "Pay");
		dictionaryMap.put("�rem", "Faiz");
		dictionaryMap.put("Hadise", "Olay");
		dictionaryMap.put("Vakit", "Zaman");
		dictionaryMap.put("Varl�kl�", "Zengin");
		dictionaryMap.put("Vas�ta", "Ara�");
		dictionaryMap.put("Vatan", "Yurt");
		dictionaryMap.put("Vazife", "G�rev");
		dictionaryMap.put("Vaziyet", "Durum");
		dictionaryMap.put("Vesika", "Belge");
		dictionaryMap.put("Vilayet", "�l");
		dictionaryMap.put("Yarg��", "Hakim");
		dictionaryMap.put("Yasa", "Kanun");
		dictionaryMap.put("Yek�n", "Toplam");
		dictionaryMap.put("Yel", "R�zgar");
		dictionaryMap.put("Y�l", "Sene");
		dictionaryMap.put("Yine", "Tekrar");
		dictionaryMap.put("Yitik", "Kay�p");
		dictionaryMap.put("Metot", "Y�ntem");
		dictionaryMap.put("Y�ce", "Ulu");
		dictionaryMap.put("Zeybek", "Efe");
		dictionaryMap.put("Ziraat", "Tar�m");
		dictionaryMap.put("Do�rultu", "�stikamet");
		dictionaryMap.put("Ziyan", "Zarar");
		dictionaryMap.put("Zabit", "Subay");
	}

}
