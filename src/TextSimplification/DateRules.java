package TextSimplification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DateRules {

	public static final String YIL = "yýl";
	public static final String SENE = "sene";
	public static List<String> listMonth = Arrays.asList("Ocak", "Þubat", "Mart", "Nisan", "Mayýs", "Haziran", "Temmuz", "Aðustos", "Eylül", "Ekim", "Kasým", "Aralýk");
	public static List<String> listHourNames=Arrays.asList("Saat","Dakika");
	public static List<String> listMoneyNames=Arrays.asList("Milyon","Trilyon", "Milyar", "Lira");
	public static boolean isYear(String word) {
		if (word.matches("[0-9]+") && word.length() > 3) {
			return true;
		}
		return false;
	}
    
	public static boolean isDayOfMonth(String day, String month) {
		if (day.matches("[0-9]+") && (day.length() > 0 || day.length() <= 2)) {
			if(listMonth.contains(month)) {
				return true;
			}
		}
		return false;
	}
	public static boolean isHour(String hour, String word) {
		if (hour.matches("[0-9]+") && (hour.length() > 0 || hour.length() <= 2)) {
			if(listHourNames.contains(word)) {
				return true;
			}
		}
		return false;
	}
	public static boolean isMoney(String money, String words) {
		if (money.matches("[0-9]+") && (money.length() > 0 || money.length() <= 3)) {
			if(listMoneyNames.contains(words)) {
				return true;
			}
		}
		return false;
	}
}
