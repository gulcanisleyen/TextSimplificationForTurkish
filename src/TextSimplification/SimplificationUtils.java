package TextSimplification;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import TextSimplification.Main;
import TextSimplification.DateRules;
import TextSimplification.Property;
import TextSimplification.Sentence;


public class SimplificationUtils {

	public static ArrayList<String> specialWordsPersonBefore;
	public static ArrayList<String> specialWordsPersonAfter;
	public static ArrayList<String> specialWordsTimeBefore;
	public static ArrayList<String> specialWordsTimeAfter;
	public static ArrayList<String> specialWordsLocationBefore;
	public static ArrayList<String> specialWordsLocationAfter;
	public static ArrayList<String> specialWordsLocationIn;
	public static ArrayList<String> specialWordsOrganizationBefore;
	public static ArrayList<String> specialWordsOrganizationAfter;
	public static ArrayList<String> specialWordsMoneyAfter;
	public static ArrayList<String> specialWordsOrganizationIn;
	public static ArrayList<String> savedWords;

	private static int pointCount;
	
	public static SimplificationUtils getInstance() {
		return new SimplificationUtils();
	}

	public void initArrays() {
		specialWordsPersonBefore = new ArrayList<>();
		specialWordsPersonAfter = new ArrayList<>();
		specialWordsTimeBefore = new ArrayList<>();
		specialWordsTimeAfter = new ArrayList<>();
		specialWordsLocationBefore = new ArrayList<>();
		specialWordsLocationAfter = new ArrayList<>();
		specialWordsLocationIn = new ArrayList<>();
		specialWordsOrganizationBefore = new ArrayList<>();
		specialWordsOrganizationAfter = new ArrayList<>();
		specialWordsOrganizationIn = new ArrayList<>();
		specialWordsMoneyAfter= new ArrayList<>();
		savedWords = new ArrayList<>();
	}

	public void seperatorDetector(String sentence) throws IOException {
		String s = "This is a sample sentence.";
		String[] words = sentence.split("\\s+");
		
		pointCount = getPointCharacterCount(sentence);
		createPropertyArrays();

		List<String> arrayWordsOfSentence = Arrays.asList(words);
		
		compareWords(arrayWordsOfSentence);
//		selectNextWords(words);
	}

	public void setSpecialWordsToArray() {
		Scanner scanner;
		
		try {
			scanner = new Scanner(new File("files/PersonBefore.txt"));

			while (scanner.hasNextLine()) {
				specialWordsPersonBefore.add(scanner.nextLine().toLowerCase());
			}

			scanner = new Scanner(new File("files/PersonAfter.txt"));

			while (scanner.hasNextLine()) {
				specialWordsPersonAfter.add(scanner.nextLine().toLowerCase());
			}

			scanner = new Scanner(new File("files/TimeBefore.txt"));

			while (scanner.hasNextLine()) {
				specialWordsTimeBefore.add(scanner.nextLine().toLowerCase());
			}

			scanner = new Scanner(new File("files/TimeAfter.txt"));

			while (scanner.hasNextLine()) {
				specialWordsTimeAfter.add(scanner.nextLine().toLowerCase());
			}

			scanner = new Scanner(new File("files/LocationBefore.txt"));

			while (scanner.hasNextLine()) {
				specialWordsLocationBefore.add(scanner.nextLine().toLowerCase());
			}

			scanner = new Scanner(new File("files/LocationAfter.txt"));

			while (scanner.hasNextLine()) {
				specialWordsLocationAfter.add(scanner.nextLine().toLowerCase());
			}
			
			scanner = new Scanner(new File("files/LocationIn.txt"));

			while (scanner.hasNextLine()) {
				specialWordsLocationIn.add(scanner.nextLine().toLowerCase());
			}
			
			scanner = new Scanner(new File("files/OrganizationBefore.txt"));

			while (scanner.hasNextLine()) {
				specialWordsOrganizationBefore.add(scanner.nextLine().toLowerCase());
			}
			
			scanner = new Scanner(new File("files/OrganizationAfter.txt"));

			while (scanner.hasNextLine()) {
				specialWordsOrganizationAfter.add(scanner.nextLine().toLowerCase());
			}
			
			scanner = new Scanner(new File("files/OrganizationIn.txt"));

			while (scanner.hasNextLine()) {
				specialWordsOrganizationIn.add(scanner.nextLine().toLowerCase());
			}
			scanner = new Scanner(new File("files/MoneyAfter.txt"));

			while (scanner.hasNextLine()) {
				specialWordsMoneyAfter.add(scanner.nextLine().toLowerCase());
			}
			scanner = new Scanner(new File("files/LocationIn.txt"));

			while (scanner.hasNextLine()) {
				specialWordsLocationIn.add(scanner.nextLine().toLowerCase());
		} 
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void compareWords(List<String> arrayWordsOfSentence) throws IOException {

		for (String word : arrayWordsOfSentence) {
			String wordLowerCase = word.toLowerCase();

			for (int i = 0; i < specialWordsPersonBefore.size(); i++) {
				if (wordLowerCase.contains(specialWordsPersonBefore.get(i))) {
					if(arrayWordsOfSentence.size() >= arrayWordsOfSentence.indexOf(word) + 1) {
						String chosenWord = arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word) + 1);
						if(chosenWord.charAt(0) == chosenWord.toUpperCase().charAt(0)) {
							//dosyadaki kelimelerden birinin ard�ndan gelen c�mledeki ilk kelimenin ilk harfi b�y�kse (ozel isimse)
							if(!chosenWord.contains(".") && !chosenWord.contains(",") && arrayWordsOfSentence.size() >= arrayWordsOfSentence.indexOf(word) + 2) {
								//ozel isim cumle sonundaysa sonraki cumlenin ilk harfinin buyuk olmas� dogaldir.
								//ozel isimden sonra virgul geliyorsa da sonraki kelime bu kumeden olmadigi anlasilir.
								//bu yuzden ozel isim bir obek ya da kume mi
								//diye bakarken nokta ve virgul icermemesi gerekiyor. bu kontrol bu yuzden
								String secondChosenWord =  arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word) + 2);
								if(secondChosenWord.charAt(0) == secondChosenWord.toUpperCase().charAt(0)) {
									//takip eden kelime de b�y�k harfle basliyorsa (yani ozel isme dahilse)
									chosenWord = chosenWord + " "+ secondChosenWord;
									
									if(arrayWordsOfSentence.size() >= arrayWordsOfSentence.indexOf(word) + 3) {
										String thirdChosenWord =  arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word) + 3);
										
										if(thirdChosenWord.charAt(0) == thirdChosenWord.toUpperCase().charAt(0)) {
											chosenWord = chosenWord + " " + thirdChosenWord;
										}
									}
								}
							}
						
						
						if(!isAddedBefore(savedWords, getTrimmedSavedWord(chosenWord))) {
							mapSentenceAndProperties(arrayWordsOfSentence, chosenWord, Constants.PERSON);
							savedWords.add(getTrimmedSavedWord(chosenWord));	
						}
						}
					}
				}
			}
		}
		
		for (String word : arrayWordsOfSentence) {
			String wordLowerCase = word.toLowerCase();
			
			for (int i = 0; i < specialWordsTimeBefore.size(); i++) {
				if (wordLowerCase.contains(specialWordsTimeBefore.get(i))) {
					if(wordLowerCase.contains(DateRules.YIL) || wordLowerCase.contains(DateRules.SENE)) {
						//bu bir yildir. bu yuzden sonraki kelimenin dort haneli ve saedce say�lardan olustuguna emin olmal�y�z.
						//y�l disindaki tarih caselerini de buna benzer bir rule seti ile dusunebilirsin.
						if(arrayWordsOfSentence.size() >= arrayWordsOfSentence.indexOf(word) + 1) {
							String chosenWord = arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word) + 1);
							if(DateRules.isYear(getTrimmedSavedWord(chosenWord))) {
								if(!isAddedBefore(savedWords, getTrimmedSavedWord(chosenWord))) {
									mapSentenceAndProperties(arrayWordsOfSentence, chosenWord, Constants.DATE);
									savedWords.add(getTrimmedSavedWord(chosenWord));								
								}
							}
						}
					}
				}
			}
		}
		
		for (String word : arrayWordsOfSentence) {
			String wordLowerCase = word.toLowerCase();
			
			for (int i = 0; i < specialWordsLocationBefore.size(); i++) {
				if (wordLowerCase.contains(specialWordsLocationBefore.get(i))) {
					if(arrayWordsOfSentence.size() >= arrayWordsOfSentence.indexOf(word) + 1) {
						String chosenWord = arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word) + 1);
						if(chosenWord.charAt(0) == chosenWord.toUpperCase().charAt(0)) {
							//dosyadaki kelimelerden birinin ard�ndan gelen c�mledeki ilk kelimenin ilk harfi b�y�kse (ozel isimse)
							if(!chosenWord.contains(".") && !chosenWord.contains(",") && arrayWordsOfSentence.size() >= arrayWordsOfSentence.indexOf(word) + 2) {
								//ozel isim cumle sonundaysa sonraki cumlenin ilk harfinin buyuk olmas� dogaldir.
								//ozel isimden sonra virgul geliyorsa da sonraki kelime bu kumeden olmadigi anlasilir.
								//bu yuzden ozel isim bir obek ya da kume mi
								//diye bakarken nokta ve virgul icermemesi gerekiyor. bu kontrol bu yuzden
								String secondChosenWord =  arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word) + 2);
								if(secondChosenWord.charAt(0) == secondChosenWord.toUpperCase().charAt(0)) {
									//takip eden kelime de b�y�k harfle basliyorsa (yani ozel isme dahilse)
									chosenWord = chosenWord + " "+ secondChosenWord;
								}
							}
								
						if(!isAddedBefore(savedWords, getTrimmedSavedWord(chosenWord))) {	
							mapSentenceAndProperties(arrayWordsOfSentence, chosenWord, Constants.LOCATION);
							savedWords.add(getTrimmedSavedWord(chosenWord));
						}
						}
					}
				}
			}
		}
		
		for (String word : arrayWordsOfSentence) {
			String wordLowerCase = word.toLowerCase();
			
			for (int i = 0; i < specialWordsOrganizationBefore.size(); i++) {
				if (wordLowerCase.contains(specialWordsOrganizationBefore.get(i))) {
					if(arrayWordsOfSentence.size() > arrayWordsOfSentence.indexOf(word) + 1) {
						String chosenWord = arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word) + 1);
						if(chosenWord.charAt(0) == chosenWord.toUpperCase().charAt(0)) {
							//dosyadaki kelimelerden birinin ard�ndan gelen c�mledeki ilk kelimenin ilk harfi b�y�kse (ozel isimse)
							if(!chosenWord.contains(".") && !chosenWord.contains(",") && arrayWordsOfSentence.size() >= arrayWordsOfSentence.indexOf(word) + 2) {
								//ozel isim cumle sonundaysa sonraki cumlenin ilk harfinin buyuk olmas� dogaldir.
								//ozel isimden sonra virgul geliyorsa da sonraki kelime bu kumeden olmadigi anlasilir.
								//bu yuzden ozel isim bir obek ya da kume mi
								//diye bakarken nokta ve virgul icermemesi gerekiyor. bu kontrol bu yuzden
								String secondChosenWord =  arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word) + 2);
								if(secondChosenWord.charAt(0) == secondChosenWord.toUpperCase().charAt(0)) {
									//takip eden kelime de b�y�k harfle basliyorsa (yani ozel isme dahilse)
									chosenWord = chosenWord + " "+ secondChosenWord;
								}
							}
							
							if(!isAddedBefore(savedWords, getTrimmedSavedWord(chosenWord))) {
								mapSentenceAndProperties(arrayWordsOfSentence, chosenWord, Constants.ORGANIZATION);
								savedWords.add(getTrimmedSavedWord(chosenWord));								
							}
						}				
					}
				}
			}
				}
			
		for (String word : arrayWordsOfSentence) {
			String wordLowerCase = word.toLowerCase();

			for (int i = 0; i < specialWordsPersonAfter.size(); i++) {
				if (wordLowerCase.contains(specialWordsPersonAfter.get(i))) {
					if(arrayWordsOfSentence.size() >= arrayWordsOfSentence.indexOf(word) -1) {
						String chosenWord = arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word) - 1);
						if(chosenWord.charAt(0) == chosenWord.toUpperCase().charAt(0)) {
							//dosyadaki kelimelerden birinin ard�ndan gelen c�mledeki ilk kelimenin ilk harfi b�y�kse (ozel isimse)
							if(!chosenWord.contains(".") && !chosenWord.contains(",") && arrayWordsOfSentence.size() >= arrayWordsOfSentence.indexOf(word) + 2) {
								//ozel isim cumle sonundaysa sonraki cumlenin ilk harfinin buyuk olmas� dogaldir.
								//ozel isimden sonra virgul geliyorsa da sonraki kelime bu kumeden olmadigi anlasilir.
								//bu yuzden ozel isim bir obek ya da kume mi
								//diye bakarken nokta ve virgul icermemesi gerekiyor. bu kontrol bu yuzden
								String secondChosenWord =  arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word) - 2);
								if(secondChosenWord.charAt(0) == secondChosenWord.toUpperCase().charAt(0)) {
									//takip eden kelime de b�y�k harfle basliyorsa (yani ozel isme dahilse)
									chosenWord = chosenWord + " "+ secondChosenWord;
								}
							}
							
						if(!isAddedBefore(savedWords, getTrimmedSavedWord(chosenWord))) {	
							mapSentenceAndProperties(arrayWordsOfSentence, chosenWord, Constants.PERSON);
							savedWords.add(getTrimmedSavedWord(chosenWord));								
						}
						}
					}
				}
			}
				}
		
		for (String word : arrayWordsOfSentence) {
			String wordLowerCase = word.toLowerCase();
			
			for (int i = 0; i < specialWordsTimeBefore.size(); i++) {
				if (wordLowerCase.contains(specialWordsTimeBefore.get(i))) {
					if(wordLowerCase.contains(DateRules.SENE) || wordLowerCase.contains(DateRules.SENE)) {
						//bu bir yildir. bu yuzden sonraki kelimenin dort haneli ve saedce say�lardan olustuguna emin olmal�y�z.
						//y�l disindaki tarih caselerini de buna benzer bir rule seti ile dusunebilirsin.
						if(arrayWordsOfSentence.size() >= arrayWordsOfSentence.indexOf(word) -1) {
							String chosenWord = arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word) -1);
							if(DateRules.isYear(getTrimmedSavedWord(chosenWord))) {
								if(!isAddedBefore(savedWords, getTrimmedSavedWord(chosenWord))) {	
									mapSentenceAndProperties(arrayWordsOfSentence, chosenWord, Constants.DATE);
									savedWords.add(getTrimmedSavedWord(chosenWord));								
								}
							}
						}
					}
				}
			}
		}
		
		for (String word : arrayWordsOfSentence) {
			String wordLowerCase = word.toLowerCase();

			for (int i = 0; i < specialWordsTimeAfter.size(); i++) {
				if (wordLowerCase.contains(specialWordsTimeAfter.get(i))) {
					if (wordLowerCase.contains(DateRules.YIL) || wordLowerCase.contains(DateRules.SENE)) {
						// bu bir yildir. bu yuzden sonraki kelimenin dort haneli ve saedce say�lardan
						// olustuguna emin olmal�y�z.
						// y�l disindaki tarih caselerini de buna benzer bir rule seti ile
						// dusunebilirsin.
						if (arrayWordsOfSentence.size() >= arrayWordsOfSentence.indexOf(word) - 1) {
							String chosenWord = arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word) - 1);
							if (DateRules.isYear(getTrimmedSavedWord(chosenWord))) {
								if (!isAddedBefore(savedWords, getTrimmedSavedWord(chosenWord))) {
									mapSentenceAndProperties(arrayWordsOfSentence, chosenWord, Constants.DATE);
									savedWords.add(getTrimmedSavedWord(chosenWord));
								}
							}
						}
					} else if (arrayWordsOfSentence.indexOf(word) > 0 && DateRules.isDayOfMonth(arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word) - 1),
							getTrimmedSavedWord(word))) {
						if (arrayWordsOfSentence.size() >= arrayWordsOfSentence.indexOf(word) - 1) {
							String chosenWord = arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word) - 1) + " " + arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word));
							if (!isAddedBefore(savedWords, getTrimmedSavedWord(chosenWord))) {
								mapSentenceAndProperties(arrayWordsOfSentence, chosenWord, Constants.DATE);
								savedWords.add(getTrimmedSavedWord(chosenWord));
							}
						}
					}
				}
			}
		}
		
		for (String word : arrayWordsOfSentence) {
			String wordLowerCase = word.toLowerCase();

			for (int i = 0; i < specialWordsMoneyAfter.size(); i++) {
				if (wordLowerCase.contains(specialWordsMoneyAfter.get(i))) {
					if (wordLowerCase.contains(DateRules.YIL) || wordLowerCase.contains(DateRules.SENE)) {
						// bu bir yildir. bu yuzden sonraki kelimenin dort haneli ve saedce say�lardan
						// olustuguna emin olmal�y�z.
						// y�l disindaki tarih caselerini de buna benzer bir rule seti ile
						// dusunebilirsin.
						if (arrayWordsOfSentence.size() >= arrayWordsOfSentence.indexOf(word) - 1) {
							String chosenWord = arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word) - 1);
							if (DateRules.isYear(getTrimmedSavedWord(chosenWord))) {
								if (!isAddedBefore(savedWords, getTrimmedSavedWord(chosenWord))) {
									mapSentenceAndProperties(arrayWordsOfSentence, chosenWord, Constants.DATE);
									savedWords.add(getTrimmedSavedWord(chosenWord));
								}
							}
						}
					else if (arrayWordsOfSentence.indexOf(word) > 0 && DateRules.isMoney(arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word) - 1),
							getTrimmedSavedWord(word))) {
						if (arrayWordsOfSentence.size() >= arrayWordsOfSentence.indexOf(word) - 1) {
							String chosenWord = arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word) - 1) + " " + arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word));
							if (!isAddedBefore(savedWords, getTrimmedSavedWord(chosenWord))) {
								mapSentenceAndProperties(arrayWordsOfSentence, chosenWord, Constants.DATE);
								savedWords.add(getTrimmedSavedWord(chosenWord));
							}
						}
					}
					}
				}
			}
		}
				
		for (String word : arrayWordsOfSentence) {
			String wordLowerCase = word.toLowerCase();

			for (int i = 0; i < specialWordsLocationAfter.size(); i++) {
				if (wordLowerCase.contains(specialWordsLocationAfter.get(i))) {
					if(arrayWordsOfSentence.size() >= arrayWordsOfSentence.indexOf(word) -1) {
						String chosenWord = arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word) - 1);
						if(chosenWord.charAt(0) == chosenWord.toUpperCase().charAt(0)) {
							//dosyadaki kelimelerden birinin ard�ndan gelen c�mledeki ilk kelimenin ilk harfi b�y�kse (ozel isimse)
							if(!chosenWord.contains(".") && !chosenWord.contains(",") && arrayWordsOfSentence.size() >= arrayWordsOfSentence.indexOf(word) + 2) {
								//ozel isim cumle sonundaysa sonraki cumlenin ilk harfinin buyuk olmas� dogaldir.
								//ozel isimden sonra virgul geliyorsa da sonraki kelime bu kumeden olmadigi anlasilir.
								//bu yuzden ozel isim bir obek ya da kume mi
								//diye bakarken nokta ve virgul icermemesi gerekiyor. bu kontrol bu yuzden
								String secondChosenWord =  arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word) - 2);
								if(secondChosenWord.charAt(0) == secondChosenWord.toUpperCase().charAt(0)) {
									//takip eden kelime de b�y�k harfle basliyorsa (yani ozel isme dahilse)
									chosenWord = chosenWord + " "+ secondChosenWord;
								}
							}
						
						if(!isAddedBefore(savedWords, getTrimmedSavedWord(chosenWord))) {		
							mapSentenceAndProperties(arrayWordsOfSentence, chosenWord, Constants.LOCATION);
							savedWords.add(getTrimmedSavedWord(chosenWord));								
						}
					}
				}
				}
			}
				}
		for (String word : arrayWordsOfSentence) {
			String wordLowerCase = word.toLowerCase();

			for (int i = 0; i < specialWordsOrganizationAfter.size(); i++) {
				if (wordLowerCase.contains(specialWordsOrganizationAfter.get(i))) {
					if(arrayWordsOfSentence.size() >= arrayWordsOfSentence.indexOf(word) -1) {
						String chosenWord = arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word) - 1);
						if(chosenWord.charAt(0) == chosenWord.toUpperCase().charAt(0)) {
							//dosyadaki kelimelerden birinin ard�ndan gelen c�mledeki ilk kelimenin ilk harfi b�y�kse (ozel isimse)
							if(!chosenWord.contains(".") && !chosenWord.contains(",") && arrayWordsOfSentence.size() >= arrayWordsOfSentence.indexOf(word) + 2) {
								//ozel isim cumle sonundaysa sonraki cumlenin ilk harfinin buyuk olmas� dogaldir.
								//ozel isimden sonra virgul geliyorsa da sonraki kelime bu kumeden olmadigi anlasilir.
								//bu yuzden ozel isim bir obek ya da kume mi
								//diye bakarken nokta ve virgul icermemesi gerekiyor. bu kontrol bu yuzden
								String secondChosenWord =  arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word) - 2);
								if(secondChosenWord.charAt(0) == secondChosenWord.toUpperCase().charAt(0)) {
									//takip eden kelime de b�y�k harfle basliyorsa (yani ozel isme dahilse)
									chosenWord = chosenWord + " "+ secondChosenWord;
								}
							}
							
						if(!isAddedBefore(savedWords, getTrimmedSavedWord(chosenWord))) {		
							mapSentenceAndProperties(arrayWordsOfSentence, chosenWord, Constants.ORGANIZATION);
							savedWords.add(getTrimmedSavedWord(chosenWord));	
						}
						}
					}
				}
			}
		}
		for (String word : arrayWordsOfSentence) {
			String wordLowerCase = word.toLowerCase();

			for (int i = 0; i < specialWordsLocationIn.size(); i++) {
				if (wordLowerCase.contains(specialWordsLocationIn.get(i))) {
						String chosenWord = arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word));
						if(chosenWord.charAt(0) == chosenWord.toUpperCase().charAt(0)) {
							//dosyadaki kelimelerden birinin ard�ndan gelen c�mledeki ilk kelimenin ilk harfi b�y�kse (ozel isimse)
							if(!chosenWord.contains(".") && !chosenWord.contains(",") && arrayWordsOfSentence.size() >= arrayWordsOfSentence.indexOf(word) + 2) {
								//ozel isim cumle sonundaysa sonraki cumlenin ilk harfinin buyuk olmas� dogaldir.
								//ozel isimden sonra virgul geliyorsa da sonraki kelime bu kumeden olmadigi anlasilir.
								//bu yuzden ozel isim bir obek ya da kume mi
								//diye bakarken nokta ve virgul icermemesi gerekiyor. bu kontrol bu yuzden
								String secondChosenWord =  arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word));
								if(secondChosenWord.charAt(0) == secondChosenWord.toUpperCase().charAt(0)) {
									//takip eden kelime de b�y�k harfle basliyorsa (yani ozel isme dahilse)
									
								}
							}
						if(!isAddedBefore(savedWords, getTrimmedSavedWord(chosenWord))) {		
							mapSentenceAndProperties(arrayWordsOfSentence, chosenWord, Constants.LOCATION);
							savedWords.add(getTrimmedSavedWord(chosenWord));		
						}
						}
					}
				}
			}
		for (String word : arrayWordsOfSentence) {
			String wordLowerCase = word.toLowerCase();

			for (int i = 0; i < specialWordsOrganizationIn.size(); i++) {
				if (wordLowerCase.contains(specialWordsOrganizationIn.get(i))) {
						String chosenWord = arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word));
						if(chosenWord.charAt(0) == chosenWord.toUpperCase().charAt(0)) {
							//dosyadaki kelimelerden birinin ard�ndan gelen c�mledeki ilk kelimenin ilk harfi b�y�kse (ozel isimse)
							if(!chosenWord.contains(".") && !chosenWord.contains(",") && arrayWordsOfSentence.size() >= arrayWordsOfSentence.indexOf(word) + 2) {
								//ozel isim cumle sonundaysa sonraki cumlenin ilk harfinin buyuk olmas� dogaldir.
								//ozel isimden sonra virgul geliyorsa da sonraki kelime bu kumeden olmadigi anlasilir.
								//bu yuzden ozel isim bir obek ya da kume mi
								//diye bakarken nokta ve virgul icermemesi gerekiyor. bu kontrol bu yuzden
								String secondChosenWord =  arrayWordsOfSentence.get(arrayWordsOfSentence.indexOf(word));
								if(secondChosenWord.charAt(0) == secondChosenWord.toUpperCase().charAt(0)) {
									//takip eden kelime de b�y�k harfle basliyorsa (yani ozel isme dahilse)
									
								}
							}
						
						if(!isAddedBefore(savedWords, getTrimmedSavedWord(chosenWord))) {	
							mapSentenceAndProperties(arrayWordsOfSentence, chosenWord, Constants.ORGANIZATION);
							savedWords.add(getTrimmedSavedWord(chosenWord));								
						}
					}
				}
			}
		}
		
			System.out.println("DONE");
			writeSentenceObjects();
		
			
		System.out.println(savedWords.toString());
	

					
				
			
		}
		
	private static String getTrimmedSavedWord(String word) {
		//kelime tirnak ile ayrilmissa tirnaktan sonrasini kirpiyoruz
		if(word.contains("'")) {			
			word = word.substring(0, word.indexOf("'"));
		}
		
		//elimizde kalan kelimeyi tum alfanumerik olmayan karakterlerden kurtariyoruz.
		word = word.replaceAll("[-+.^:,]","");
		
		return word;
		
	}
	
	private static boolean isAddedBefore(List<String> savedWords, String chosenWord) {
		// bu metot daha once atilmis kelimeleri tekrar savedWords e atmamak i�in olu�turuldu.
		
		// *ornek case:*
		// ��lg�nlar �lkesi G�ney Amerika, ba�kan� Rafael Benitez y�z�nden sene 2002'den beri kupa alam�yor

		// burada hem �lke hem de g�ney location before case'ine d��mekte. bu y�zden
		// Amerika'n�n iki kez yaz�lmas�n� �nlemeliyiz.

		for(String word : savedWords) {
			if(word.contains(chosenWord)) {
				return true;
			}
		}
		
		return false;
	}

	private static void createPropertyArrays(){
		ArrayList<Property> arrProperty = new ArrayList<>();
		for(int i = 0; i < pointCount + 1; i++) {
			if(Main.listSentences.size() > i) {				
				Main.listSentences.get(i).property = new ArrayList<>();
			}
		}
	}
	
	private static int getPointCharacterCount(String sentence) {
	    String[] splitArray = sentence.split("\\.");
	    System.out.println("No of . chars is : " + String.valueOf(splitArray.length-1));
	    
	    return splitArray.length - 1;
	}

	private static void mapSentenceAndProperties(List<String> arrayWordsOfSentence, String chosenWord, String type) {
		int tempPointCount = 0;
		for (int j = 0; j < arrayWordsOfSentence.indexOf(chosenWord.split(" ")[0]); j++) {
			if(arrayWordsOfSentence.get(j).contains(".")) {
				tempPointCount++;
			}
		}
		
		Property property = new Property();
		property.key = type;
		property.value = getTrimmedSavedWord(chosenWord);
		if(tempPointCount == 1) {
			Main.listSentences.get(tempPointCount -1).property.add(property);			
		} else {			
			Main.listSentences.get(tempPointCount).property.add(property);
		}
	}

	private static void writeSentenceObjects() throws IOException {
		FileWriter fstream;
		BufferedWriter out;
		//fstream = new FileWriter("C:\\Users\\support\\eclipse-workspace\\TextSimplification\\x.txt");
		//File f = new File("files/output.txt");
		File f = new File("C:\\Users\\Acer\\Desktop\\TextSimplification\\output.txt");
		fstream = new FileWriter(f);
		out = new BufferedWriter(fstream);
		String wholeText = "";
		for (int i = 0; i < Main.listSentences.size(); i++) {
			Sentence sentence = Main.listSentences.get(i);
			wholeText += sentence.sentenceString + "\n";
			ArrayList<Property> listProperty = Main.listSentences.get(i).property;
			if(listProperty.size() <= 0) {
				wholeText += "----------------\n";
			}
			
			for (int j = 0; j < listProperty.size(); j++) {
				wholeText += listProperty.get(j).key + ": " + listProperty.get(j).value + "\n" + "----------------\n";
			}
		}
		
		
		 
		out.write(wholeText);
		
	        out.close();
			}
	

	
	public File getFilePath(String filePath) {
		return new File(filePath);
	}
}

	
