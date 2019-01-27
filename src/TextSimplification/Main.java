/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TextSimplification;

import java.io.*;
import java.util.*;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;
import TextSimplification.Sentence;

import TextSimplification.SimplificationUtils;
import TextSimplification.SharedPredicate;
//import javaaplication76.StopWords.RemoveStopWords;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;

/**
 *
 * @author Acer
 */
public class Main {
	/**
	 * @param args
	 *            the command line arguments
	 */

	public static ArrayList<Sentence> listSentences = new ArrayList<>();
	private static Scanner sc;

	public static void main(String[] args) throws Exception {
		StopWords abc = new StopWords();
		abc.stopword();
		DosyaIsle dosya = new DosyaIsle();
		dosya.processFileOperation();
		SharedPredicate ac = new SharedPredicate();
		ac.SharePredicate();
		ZemberekIslemler.copyFile();
		ZemberekIslemler.copyFile1();
		/////////////////

	   	SimplificationUtils.getInstance().initArrays();
		SimplificationUtils.getInstance().setSpecialWordsToArray();
		BufferedReader br1 = new BufferedReader(new InputStreamReader(
				new FileInputStream(SimplificationUtils.getInstance().getFilePath("files/50.txt")),
				"ISO-8859-9"));
		{
			String text1 = " ";
			String line1 = br1.readLine();
	          
	           
	splitToSentences(line1);
		}
	       
	      // sc = new Scanner(System.in);

			//SimplificationUtils.getInstance().initArrays();
			//SimplificationUtils.getInstance().setSpecialWordsToArray();
			//System.out.println("Bir cümle giriniz: \n");
			//splitToSentences(sc.nextLine());

		}

		private static void splitToSentences(String inputText) throws IOException {
			// String s = "This is test... Sentense number 2. Sentence number 3?";
			BreakIterator bi = BreakIterator.getSentenceInstance(Locale.ENGLISH);
			bi.setText(inputText);
			int start = 0;
			int end = 0;
			while ((end = bi.next()) != BreakIterator.DONE) {
				Sentence sentence = new Sentence();
				sentence.sentenceString = inputText.substring(start, end);
				listSentences.add(sentence);
				System.out.println(inputText.substring(start, end));
				start = end;
			}
			System.out.println("Girdi");
			SimplificationUtils.getInstance().seperatorDetector(inputText);
		}


	    }   
