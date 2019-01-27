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
				System.out.println("Cümle cümle ayrýlmýþ hali:");
				while (y.hasMoreTokens()) {
					System.out.println(y.nextToken());
				}
				String ayracc = " ";
				StringTokenizer a = new StringTokenizer(text, ayracc);
				System.out.println("             ");
				System.out.println("             ");
				System.out.println("             ");
				System.out.println("Kelime kelime ayrýlýp eþ  anlamlýsýyla deðiþtirilmiþ hali ");
				while (a.hasMoreTokens()) {
					if (a.hasMoreElements() && a.hasMoreTokens()) {
						String orjinalKelime = a.nextToken();
						boolean isWordValid = zemberek.kelimeDenetle(orjinalKelime);
						if (!isWordValid) {
							System.out.println("Kelime Türkçe Deðil :" + orjinalKelime);
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
					System.out.println("2 den fazla tekrar eden kelime sayýsý :");
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
		dictionaryMap.put("Abide", "Anýt");
		dictionaryMap.put("Acayip", "Garip");
		dictionaryMap.put("Acýma", "Merhamet");
		dictionaryMap.put("Açýkgöz", "Kurnaz");
		dictionaryMap.put("Ad", "Ýsim");
		dictionaryMap.put("Adale", "Kas");
		dictionaryMap.put("Adet", "Tane");
		dictionaryMap.put("Adýl", "Zamir");
		dictionaryMap.put("Adi", "Bayaðý");
		dictionaryMap.put("Ahenk", "Uyum");
		dictionaryMap.put("Aksi", "Ters");
		dictionaryMap.put("Kýrmýzý", "Al");
		dictionaryMap.put("Alaz", "Alev");
		dictionaryMap.put("Sýradan", "Alelade");
		dictionaryMap.put("Amele", "Ýþçi");
		dictionaryMap.put("Amel", "Ýþ");
		dictionaryMap.put("Ana", "Anne");
		dictionaryMap.put("Aný", "Hatýra");
		dictionaryMap.put("Ant", "Yemin");
		dictionaryMap.put("Apse", "Ýltihap");
		dictionaryMap.put("Arýza", "Bozukluk");
		dictionaryMap.put("Armaðan", "Hediye");
		dictionaryMap.put("Arzu", "Ýstek");
		dictionaryMap.put("Asýr", "Yüzyýl");
		dictionaryMap.put("Atak", "Giriþken");
		dictionaryMap.put("Avare", "Serseri");
		dictionaryMap.put("Ýmtiyaz", "Ayrýcalýk");
		dictionaryMap.put("Aza", "Üye");
		dictionaryMap.put("Acemi", "Toy");
		dictionaryMap.put("Adalet", "Hak");
		dictionaryMap.put("Aka", "Büyük");
		dictionaryMap.put("Aleni", "Açýk");
		dictionaryMap.put("Baðnaz", "Yobaz");
		dictionaryMap.put("Sulh", "Barýþ");
		dictionaryMap.put("Matbaa", "Basýmevi");
		dictionaryMap.put("Baþkaldýrý", "Ýsyan");
		dictionaryMap.put("Baþþehir", "Baþkent");
		dictionaryMap.put("Müracaat", "Baþvuru");
		dictionaryMap.put("Baytar", "Veteriner");
		dictionaryMap.put("Belde", "Þehir");
		dictionaryMap.put("Bellek", "Hafýza");
		dictionaryMap.put("Benlik", "Kiþilik");
		dictionaryMap.put("Beraber", "Birlikte");
		dictionaryMap.put("Bereket", "Bolluk");
		dictionaryMap.put("Ýlan", "Duyuru");
		dictionaryMap.put("Berrak", "Duru");
		dictionaryMap.put("Ak", "Beyaz");
		dictionaryMap.put("Beygir", "At");
		dictionaryMap.put("Beyhude", "Boþuna");
		dictionaryMap.put("Biçare", "Zavallý");
		dictionaryMap.put("Bilakis", "Tersine");
		dictionaryMap.put("Bilhassa", "Özellikle");
		dictionaryMap.put("Bina", "Yapý");
		dictionaryMap.put("Birden", "Ani");
		dictionaryMap.put("Birdenbire", "Aniden");
		dictionaryMap.put("Step", "Bozkýr");
		dictionaryMap.put("Nahiye", "Bucak");
		dictionaryMap.put("Buðu", "Buhar");
		dictionaryMap.put("Buhran", "Bunalým");
		dictionaryMap.put("Buyruk", "Emir");
		dictionaryMap.put("Büro", "Ofis");
		dictionaryMap.put("Cahil", "Bilgisiz");
		dictionaryMap.put("Caka", "Gösteriþ");
		dictionaryMap.put("Camekan", "Vitrin");
		dictionaryMap.put("Cehalet", "Bilgisizlik");
		dictionaryMap.put("Celse", "Oturum");
		dictionaryMap.put("Cenk", "Savaþ");
		dictionaryMap.put("Cenup", "Güney");
		dictionaryMap.put("Cerahat", "Ýrin");
		dictionaryMap.put("Cesur", "Yürekli");
		dictionaryMap.put("Cet", "Ata");
		dictionaryMap.put("Cevap", "Yanýt");
		dictionaryMap.put("Cihaz", "Aygýt");
		dictionaryMap.put("Cihet", "Yön");
		dictionaryMap.put("Cilt", "Ten");
		dictionaryMap.put("Civar", "Yöre");
		dictionaryMap.put("Cýlýz", "Sýska");
		dictionaryMap.put("Aðýrbaþlý", "Ciddi");
		dictionaryMap.put("Cihet", "Yön");
		dictionaryMap.put("Cins", "Tür");
		dictionaryMap.put("Çabuk", "Acele");
		dictionaryMap.put("Çaðdaþ", "Modern");
		dictionaryMap.put("Çaðrý", "Davet");
		dictionaryMap.put("Çehre", "Yüz");
		dictionaryMap.put("Çeþit", "Tür");
		dictionaryMap.put("Feryat", "Çýðlýk");
		dictionaryMap.put("Çýlgýn", "Deli");
		dictionaryMap.put("Ekseriyet", "Çoðunluk");
		dictionaryMap.put("Balçýk", "Çamur");
		dictionaryMap.put("Deva", "Çare");
		dictionaryMap.put("Darbe", "Vuruþ");
		dictionaryMap.put("Defa", "Kez");
		dictionaryMap.put("Deðer", "Kýymet");
		dictionaryMap.put("Delil", "Kanýt");
		dictionaryMap.put("Denetim", "Kontrol");
		dictionaryMap.put("Zerzele", "Deprem");
		dictionaryMap.put("Sebep", "Neden");
		dictionaryMap.put("Derhal", "Hemen");
		dictionaryMap.put("Devinim", "Hareket");
		dictionaryMap.put("Devir", "Çað");
		dictionaryMap.put("Devre", "Dönem");
		dictionaryMap.put("Dýþalým", "Ýthalat");
		dictionaryMap.put("Ýhtifal", "Anma");
		dictionaryMap.put("Dýþsatým", "Ýhracat");
		dictionaryMap.put("Ýhbar", "Bildirim");
		dictionaryMap.put("Diri", "Canlý");
		dictionaryMap.put("Mýsra", "Dize");
		dictionaryMap.put("Donuk", "Mat");
		dictionaryMap.put("Doruk", "Zirve");
		dictionaryMap.put("Dönemeç", "Viraj");
		dictionaryMap.put("Maznun", "Sanýk");
		dictionaryMap.put("Döþek", "Yatak");
		dictionaryMap.put("Duygu", "His");
		dictionaryMap.put("Düzmece", "Sahte");
		dictionaryMap.put("Dilek", "Ýstek");
		dictionaryMap.put("Dizi", "Sýra");
		dictionaryMap.put("Ebat", "Boyut");
		dictionaryMap.put("Ebedi", "Sonsuz");
		dictionaryMap.put("Egoist", "Bencil");
		dictionaryMap.put("Ehemmiyet", "Önem");
		dictionaryMap.put("Tebaa", "Uyruk");
		dictionaryMap.put("Endiþe", "Kaygý");
		dictionaryMap.put("Enkaz", "Yýkýntý");
		dictionaryMap.put("Enlem", "Paralel");
		dictionaryMap.put("Entari", "Giysi");
		dictionaryMap.put("Enteresan", "Ýlginç");
		dictionaryMap.put("Erek", "Amaç");
		dictionaryMap.put("Esas", "Temel");
		dictionaryMap.put("Yapýt", "Eser");
		dictionaryMap.put("Evvel", "Önce");
		dictionaryMap.put("Eylem", "Fiil");
		dictionaryMap.put("Eylemsi", "Fiilimsi");
		dictionaryMap.put("Yazýn", "Edebiyat");
		dictionaryMap.put("Faaliyet", "Etkinlik");
		dictionaryMap.put("Fakat", "Ama");
		dictionaryMap.put("Fakir", "Yoksul");
		dictionaryMap.put("Fark", "Ayrým");
		dictionaryMap.put("Fasýla", "Ara");
		dictionaryMap.put("Fayda", "Yarar");
		dictionaryMap.put("Ziyade", "Fazla");
		dictionaryMap.put("Fert", "Birey");
		dictionaryMap.put("Feza", "Uzay");
		dictionaryMap.put("Fikir", "Düþünce");
		dictionaryMap.put("Lüks", "Gösteriþ");
		dictionaryMap.put("Gedikli", "Gedikli");
		dictionaryMap.put("Nüzul", "Felç");
		dictionaryMap.put("Fer", "Iþýk");
		dictionaryMap.put("Garp", "Batý");
		dictionaryMap.put("Gayret", "Çaba");
		dictionaryMap.put("Gebe", "Hamile");
		dictionaryMap.put("Gýda", "Besin");
		dictionaryMap.put("Giz", "Sýr");
		dictionaryMap.put("Saklý", "Gizli");
		dictionaryMap.put("Güz", "Sonbahar");
		dictionaryMap.put("Rasat", "Gözlem");
		dictionaryMap.put("Sema", "Gökyüzü");
		dictionaryMap.put("Halbuki", "Oysa");
		dictionaryMap.put("Ham", "Olmamýþ");
		dictionaryMap.put("Hariç", "Dýþ");
		dictionaryMap.put("Harp", "Savaþ");
		dictionaryMap.put("Hasret", "Özlem");
		dictionaryMap.put("Haysiyet", "Onur");
		dictionaryMap.put("Hekim", "Doktor");
		dictionaryMap.put("Hayta", "Serseri");
		dictionaryMap.put("Yurt", "Vatan");
		dictionaryMap.put("Hela", "Tuvalet");
		dictionaryMap.put("Hiddet", "Öfke");
		dictionaryMap.put("Hisse", "Pay");
		dictionaryMap.put("Hudut", "Sýnýr");
		dictionaryMap.put("Hususi", "Özel");
		dictionaryMap.put("Hür", "Özgür");
		dictionaryMap.put("Hýsým", "Akraba");
		dictionaryMap.put("Hasým", "Düþman");
		dictionaryMap.put("Ilýca", "Kaplýca");
		dictionaryMap.put("Irak", "Uzak");
		dictionaryMap.put("Irk", "Soy");
		dictionaryMap.put("Ira", "Karakter");
		dictionaryMap.put("Ýcat", "Buluþ");
		dictionaryMap.put("Ýçten", "Samimi");
		dictionaryMap.put("Ýdare", "Yönetim");
		dictionaryMap.put("Ýhtiyar", "Yaþlý");
		dictionaryMap.put("Ýhtiyaç", "Gereksinim");
		dictionaryMap.put("Ýktisat", "Ekonomi");
		dictionaryMap.put("Ýlave", "Ek");
		dictionaryMap.put("Ýlgeç", "Edat");
		dictionaryMap.put("Ýlgi", "Alaka");
		dictionaryMap.put("Ýlim", "Bilim");
		dictionaryMap.put("Ýmar", "Bayýndýr");
		dictionaryMap.put("Ýskemle", "Sandalye");
		dictionaryMap.put("Ýtibar", "Saygýnlýk");
		dictionaryMap.put("Ýtimat", "Güven");
		dictionaryMap.put("Ýtina", "Özen");
		dictionaryMap.put("Ýzah", "Açýklama");
		dictionaryMap.put("Ýdadi", "Lise");
		dictionaryMap.put("Münasebet", "Ýliþki");
		dictionaryMap.put("Ýmtihan", "Sýnav");
		dictionaryMap.put("Kabiliyet", "Yetenek");
		dictionaryMap.put("Kâfi", "Yeter");
		dictionaryMap.put("Kafiye", "Uyak");
		dictionaryMap.put("Kamu", "Halk");
		dictionaryMap.put("Siyah", "Kara");
		dictionaryMap.put("Karþýn", "Raðmen");
		dictionaryMap.put("Karþýt", "Zýt");
		dictionaryMap.put("Katý", "Sert");
		dictionaryMap.put("Keder", "Acý");
		dictionaryMap.put("Kent", "Þehir");
		dictionaryMap.put("Kocaman", "Ýri");
		dictionaryMap.put("Konuk", "misafir");
		dictionaryMap.put("Konut", "Ev");
		dictionaryMap.put("Kaide", "Kural");
		dictionaryMap.put("Mukaddes", "Kutsal");
		dictionaryMap.put("Kuvvet", "Güç");
		dictionaryMap.put("Þimal", "Kuzey");
		dictionaryMap.put("Kýlavuz", "Rehber");
		dictionaryMap.put("Kolay", "Basit");
		dictionaryMap.put("Lahza", "An");
		dictionaryMap.put("Lisan", "Dil");
		dictionaryMap.put("Lüzumlu", "Gerekli");
		dictionaryMap.put("Lüzumsuz", "Gereksiz");
		dictionaryMap.put("Lâl", "Dilsiz");
		dictionaryMap.put("Lama", "Deve");
		dictionaryMap.put("Lafazan", "Geveze");
		dictionaryMap.put("Mabet", "Tapýnak");
		dictionaryMap.put("Mafsal", "Eklem");
		dictionaryMap.put("Maðlup", "Yenik");
		dictionaryMap.put("Yenilgi", "Maðlubiyet");
		dictionaryMap.put("Mahalli", "Yerel");
		dictionaryMap.put("Mahcup", "Utangaç");
		dictionaryMap.put("Mahluk", "Yaratýk");
		dictionaryMap.put("Mahpushane", "Hapishane");
		dictionaryMap.put("Mahsul", "Ürün");
		dictionaryMap.put("Manzara", "Görünüm");
		dictionaryMap.put("Matem", "Yas");
		dictionaryMap.put("Mebus", "Milletvekili");
		dictionaryMap.put("Yengi", "Galibiyet");
		dictionaryMap.put("Mecburi", "Zorunlu");
		dictionaryMap.put("Mecmua", "Dergi");
		dictionaryMap.put("Medeni", "Uygar");
		dictionaryMap.put("Medeniyet", "Uygarlýk");
		dictionaryMap.put("Mektep", "Okul");
		dictionaryMap.put("Melodi", "Ezgi");
		dictionaryMap.put("Menfaat", "Çýkar");
		dictionaryMap.put("Menfi", "Olumsuz");
		dictionaryMap.put("Menkul", "Taþýnýr");
		dictionaryMap.put("Meridyen", "Boylam");
		dictionaryMap.put("Mesafe", "Ara");
		dictionaryMap.put("Mesele", "Sorun");
		dictionaryMap.put("Mesut", "Mutlu");
		dictionaryMap.put("Meþhur", "Ünlü");
		dictionaryMap.put("Meþrubat", "Ýçecek");
		dictionaryMap.put("Misal", "Örnek");
		dictionaryMap.put("Muharebe", "Savaþ");
		dictionaryMap.put("Muhtelif", "Çeþitli");
		dictionaryMap.put("Muþtu", "Müjde");
		dictionaryMap.put("Mükafat", "Ödül");
		dictionaryMap.put("Müsabaka", "Yarýþma");
		dictionaryMap.put("Müsait", "Uygun");
		dictionaryMap.put("Müstahsil", "Üretici");
		dictionaryMap.put("Merkep", "Eþek");
		dictionaryMap.put("Muallim", "Öðretmen");
		dictionaryMap.put("Merasim", "Tören");
		dictionaryMap.put("Nadir", "Engel");
		dictionaryMap.put("Nasihat", "Öðüt");
		dictionaryMap.put("Nebat", "Bitki");
		dictionaryMap.put("Soluk", "Nefes");
		dictionaryMap.put("Nesil", "Kuþak");
		dictionaryMap.put("Nesir", "Düzyazý");
		dictionaryMap.put("Netice", "Sonuç");
		dictionaryMap.put("Nispet", "Oran");
		dictionaryMap.put("Nitelik", "Kalite");
		dictionaryMap.put("Noksan", "Eksik");
		dictionaryMap.put("Numune", "Örnek");
		dictionaryMap.put("Nakliyeci", "Taþýmacý");
		dictionaryMap.put("Namzet", "Aday");
		dictionaryMap.put("Olanak", "Ýmkan");
		dictionaryMap.put("Olasý", "Mümkün");
		dictionaryMap.put("Mera", "Otlak");
		dictionaryMap.put("Ozan", "Þair");
		dictionaryMap.put("Öbek", "Grup");
		dictionaryMap.put("Aidat", "Ödenti");
		dictionaryMap.put("Ödlek", "Korkak");
		dictionaryMap.put("Taviz", "Ödün");
		dictionaryMap.put("Tahsil", "Öðrenim");
		dictionaryMap.put("Ömür", "Hayat");
		dictionaryMap.put("Önder", "Lider");
		dictionaryMap.put("Teþkilat", "Örgüt");
		dictionaryMap.put("Öteki", "Diðeri");
		dictionaryMap.put("Ötürü", "Dolayý");
		dictionaryMap.put("Öykü", "Hikaye");
		dictionaryMap.put("Orijinal", "Özgün");
		dictionaryMap.put("Pabuç", "Ayakkabý");
		dictionaryMap.put("Pasif", "Edilgen");
		dictionaryMap.put("Pinti", "Cimri");
		dictionaryMap.put("Pis", "Kirli");
		dictionaryMap.put("Politika", "Siyaset");
		dictionaryMap.put("Rastlantý", "Tesadüf");
		dictionaryMap.put("Rey", "Oy");
		dictionaryMap.put("Rutubet", "Nem");
		dictionaryMap.put("Problem", "Sorun");
		dictionaryMap.put("Rüþtiye", "Ortaokul");
		dictionaryMap.put("Sade", "Yalýn");
		dictionaryMap.put("Hücum", "Saldýrý");
		dictionaryMap.put("Sanayi", "Endüstri");
		dictionaryMap.put("Kapital", "Sermaye");
		dictionaryMap.put("Serüven", "Macera");
		dictionaryMap.put("Sýhhat", "Saðlýk");
		dictionaryMap.put("Nihayet", "Son");
		dictionaryMap.put("Soylu", "Asil");
		dictionaryMap.put("Sömestri", "Yarýyýl");
		dictionaryMap.put("Söylev", "Nutuk");
		dictionaryMap.put("Sözcük", "Kelime");
		dictionaryMap.put("Suni", "Yapay");
		dictionaryMap.put("Sürat", "Hýz");
		dictionaryMap.put("Mesuliyet", "Sorumluluk");
		dictionaryMap.put("Lügat", "Sözlük");
		dictionaryMap.put("Tan", "Þafak");
		dictionaryMap.put("Þahýs", "Kiþi");
		dictionaryMap.put("Þark", "Doðu");
		dictionaryMap.put("Þart", "Koþul");
		dictionaryMap.put("Þayet", "Eðer");
		dictionaryMap.put("Þöhret", "Ün");
		dictionaryMap.put("Þuur", "Bilinç");
		dictionaryMap.put("Þüphe", "Kuþku");
		dictionaryMap.put("Latife", "Þaka");
		dictionaryMap.put("Tabiat", "Doða");
		dictionaryMap.put("Tabii", "Doðal");
		dictionaryMap.put("Talebe", "Öðrenci");
		dictionaryMap.put("Talih", "Þans");
		dictionaryMap.put("Tamir", "Onarým");
		dictionaryMap.put("Tanýk", "Þahit");
		dictionaryMap.put("Tasdik", "Onay");
		dictionaryMap.put("Tedbir", "Önlem");
		dictionaryMap.put("Teklif", "Öneri");
		dictionaryMap.put("Tercüme", "Çeviri");
		dictionaryMap.put("Tertip", "Düzen");
		dictionaryMap.put("Tesir", "Etki");
		dictionaryMap.put("Toplum", "Cemiyet");
		dictionaryMap.put("Tutsak", "Esir");
		dictionaryMap.put("Ur", "Tümör");
		dictionaryMap.put("Münakaþa", "Tartýþma");
		dictionaryMap.put("Ulus", "Millet");
		dictionaryMap.put("Umumi", "Genel");
		dictionaryMap.put("Unsur", "Öðe");
		dictionaryMap.put("Us", "Akýl");
		dictionaryMap.put("Tayyare", "Uçak");
		dictionaryMap.put("Ehil", "Usta");
		dictionaryMap.put("Ülkü", "Ýdeal");
		dictionaryMap.put("Üleþ", "Pay");
		dictionaryMap.put("Ürem", "Faiz");
		dictionaryMap.put("Hadise", "Olay");
		dictionaryMap.put("Vakit", "Zaman");
		dictionaryMap.put("Varlýklý", "Zengin");
		dictionaryMap.put("Vasýta", "Araç");
		dictionaryMap.put("Vatan", "Yurt");
		dictionaryMap.put("Vazife", "Görev");
		dictionaryMap.put("Vaziyet", "Durum");
		dictionaryMap.put("Vesika", "Belge");
		dictionaryMap.put("Vilayet", "Ýl");
		dictionaryMap.put("Yargýç", "Hakim");
		dictionaryMap.put("Yasa", "Kanun");
		dictionaryMap.put("Yekün", "Toplam");
		dictionaryMap.put("Yel", "Rüzgar");
		dictionaryMap.put("Yýl", "Sene");
		dictionaryMap.put("Yine", "Tekrar");
		dictionaryMap.put("Yitik", "Kayýp");
		dictionaryMap.put("Metot", "Yöntem");
		dictionaryMap.put("Yüce", "Ulu");
		dictionaryMap.put("Zeybek", "Efe");
		dictionaryMap.put("Ziraat", "Tarým");
		dictionaryMap.put("Doðrultu", "Ýstikamet");
		dictionaryMap.put("Ziyan", "Zarar");
		dictionaryMap.put("Zabit", "Subay");
	}

}
