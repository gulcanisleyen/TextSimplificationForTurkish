/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TextSimplification;

import java.util.List;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.KelimeTipi;
import net.zemberek.yapi.Kok;

/**
 *
 * @author Acer
 */
public class FileOperationsUtils {

	public static String getZemberekKelime(Zemberek zemberek, String orginalKelime, String esAnlamliKelime) {
		Kelime[] cozumler = zemberek.kelimeCozumle(orginalKelime);
		Kelime[] cozumler2 = zemberek.kelimeCozumle(esAnlamliKelime);
		KelimeTipi kelimeTipi = cozumler2[0].kok().tip();
		Kelime kelime = cozumler[0];
		Kok kok = zemberek.dilBilgisi().kokler().kokBul(esAnlamliKelime, kelimeTipi);
		String yeniKelime = zemberek.kelimeUret(kok, kelime.ekler());
		System.out.println("\nkok degisimi sonrasi yeni kelime: " + yeniKelime);
		return yeniKelime;
	}

	public static String kelimeCozumle(Zemberek zemberek, String kelime) { // Kökünü bull
		if (zemberek.kelimeDenetle(kelime)) {
			Kelime[] cozumler = zemberek.kelimeCozumle(kelime);
			// System.out.println("Cozumler"+ cozumler[0].kok());
			return cozumler[0].kok().icerik();
		}
		return null;
	}

	public static boolean isFirstLetterLower(String str) {

		return Character.isLowerCase(str.charAt(0));
	}

	public static String doUpperCaseFirstLetter(String str) {
		char ch = Character.toUpperCase(str.charAt(0));
		str = ch + str.substring(1);
		return str;
	}

	public static String doLowerCaseFirstLetter(String str) {
		char ch = Character.toLowerCase(str.charAt(0));
		str = ch + str.substring(1);
		return str;
	}

}
