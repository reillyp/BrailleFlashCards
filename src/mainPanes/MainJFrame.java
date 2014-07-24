/**
 * This program presents braille flash cards. There are five groupings: ABCs,
 * Numbers and Symbols, Common Combinations, Words and a combination of All of
 * the previous four. Each group is divided into subsets of 5-6 flash card.
 *
 * There are two test modes Baseline and Training. When baseline is selected the
 * user is presented all the cards in the selected group and no feedback is
 * provided. When training is selected the user must identify each of the cards
 * in a subset correctly three times consecutively before the next subset is
 * added to drill. The user is notified if the answer is right or wrong. The
 * results of both test are recorded in a csv file.
 *
 * 2/14/2013 Revisions:
 *
 * 1.	Replace Name and Birth date with: 
 *  a.	Subject ID  
 *  b.	Gender 
 *  c.	Major 
 *  d.  Session ID 
 * 2.	Remove Consecutive Correct count from training feedback
 * message. 
 * 3.	Move answer selections to under Braille image. 
 * 4.	Remove “Next Card” button. 
 * 5.	Add a “Response Recorded” Message to Baseline test. Press of
 * the “OK” button* will trigger display of next card and start the stop watch
 * used to measure **response time. 
 * 6.	In Training Test, press of the “OK”
 * button will trigger display of next card and start the stop watch used to
 * measure response time. 
 * 7.	The “All” test should use 5 cards each from the
 * ABC, Numbers and Symbols, Common Combinations and Words sets.
 *
 * When running a Training Trial: 
 * 1. The cards in the last group added are
 * presented three times. 
 * 2. The cards from previous groups are presented once.
 * 3. Only cards from the last group added are graded with respect to the "last
 * 15 correct. 
 * 4. Cards not correctly identified are presented again until
 * correctly identified. If the card is part of the last group added only the
 * first presentation is LOGGED and scored. 
 * 5. The grade for the last 15 trials
 * must be greater than 90% before adding the nest group.
 * 
 * 5/13/2013 revision 
 * 
 * Added a GroupAdded == false logic test to the last if condition in the 
 * addNext* methods.
 * 
 * 5/23/2013 Revision. 
 * 
 * Modified initialization of Words to take place as Word groups are added
 * to resolve a java.lang.OutOfMemoryError: Java Heap Space. Also set heap to 
 * start at 768MB by setting Project Properties --> Run --> VM Options: -Xms768m.
 *
 * 5/30/2013
 * 
 * Reduced starting size of heap to 512MB
 * 
 * 9/17/2013
 * 
 * Added a call to reset the respective tab when training is completed. 
 * The call was added near the end of each training set's addNext*() method.
 * This will write reset into the the training record's csv file and
 * prevent the user from continuing on in training mode after successfully 
 * completing the module.
 * 
 * 9/18/2013. 
 * Bug reported. Program looped after fourth Word Group B was added. Found 
 * there was no method to add Word Group B 14. Added missing code block.
 *
 * Following e-mail message describes "Probe" added July 2014.
 * From: "Brittany Putnam" <bputnam@uwm.edu>
* To: "Patrick F Reilly"
* Sent: Thursday, May 8, 2014 6:36:50 PM
* Subject: Re: Braille program

* Hi Pat,

* Dr. Tiger and I met today to discuss my project and we decided to make 
* an adjustment to the "Probe" tab.  Instead of having two different 
* options, we would like the responses to be multiple choice (no 
* "construction" responses).  Also, Instead of having stimuli from the 
* first subset of modules 1-6, we would like to have a random array of
* 40 stimuli from modules 3-6 (10 from module 3, 10 from module 4, 10 
* from module 5, and 10 from module 6).  

*Sorry for the changes and I hope this won't be too much re-programming.

*Thank you!

*Brittany C. Putnam, M.S., BCBA
*Applied Behavior Analysis Lab
*Department of Psychology
*University of Wisconsin-Milwaukee
* 
* From previous Probe tab request
* 
* "d.	This tab would be similar to the “baseline” portion of the “All” 
* tab in that participants would receive no feedback on their responses.  
* They will simply respond to the 34 stimuli and then the program will 
* indicate that they are finished"
* 
* @author Patrick F. Reilly
 */

package mainPanes;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainJFrame extends javax.swing.JFrame {

    // Create Stopwatch to time responses.
    StopWatch timerResponse = new StopWatch();
    // Log file for test results.
    File file;
    // Instantiate FlashCards so their scope includes 
    // the entire program. Thus the attributes
    // are accessible through out the program.
    // CurrentCard is the FlashCard currently displayed.
    // It value is assigned from a flashcard list element.
    FlashCard CurrentCard = new FlashCard();
    // A blank card loaded when tab is selected.
    FlashCard BlankCard = new FlashCard();
    // abcGrp1
    FlashCard abcGrp1_a = new FlashCard();
    FlashCard abcGrp1_b = new FlashCard();
    FlashCard abcGrp1_c = new FlashCard();
    FlashCard abcGrp1_e = new FlashCard();
    FlashCard abcGrp1_i = new FlashCard();
    // Array and list object for abcGrp1
    FlashCard[] abcGrp1_Cards = new FlashCard[5];
    // List allows use of shuffle.
    List<FlashCard> abcGrp1_CardsList = Arrays.asList(abcGrp1_Cards);
    // abcGrp2
    FlashCard abcGrp2_k = new FlashCard();
    FlashCard abcGrp2_l = new FlashCard();
    FlashCard abcGrp2_u = new FlashCard();
    FlashCard abcGrp2_v = new FlashCard();
    FlashCard abcGrp2_x = new FlashCard();
    // Array object for abcGrp2
    FlashCard[] abcGrp2_Cards = new FlashCard[5];
    List<FlashCard> abcGrp2_CardsList = Arrays.asList(abcGrp2_Cards);
    // abcGrp3
    FlashCard abcGrp3_n = new FlashCard();
    FlashCard abcGrp3_s = new FlashCard();
    FlashCard abcGrp3_t = new FlashCard();
    FlashCard abcGrp3_y = new FlashCard();
    FlashCard abcGrp3_z = new FlashCard();
    // Array object for abcGrp3
    FlashCard[] abcGrp3_Cards = new FlashCard[5];

    List<FlashCard> abcGrp3_CardsList = Arrays.asList(abcGrp3_Cards);
    // abcGrp4
    FlashCard abcGrp4_g = new FlashCard();
    FlashCard abcGrp4_o = new FlashCard();
    FlashCard abcGrp4_p = new FlashCard();
    FlashCard abcGrp4_q = new FlashCard();
    FlashCard abcGrp4_r = new FlashCard();
    FlashCard abcGrp4_w = new FlashCard();
    // Array and object for abcGrp4
    FlashCard[] abcGrp4_Cards = new FlashCard[6];

    List<FlashCard> abcGrp4_CardsList = Arrays.asList(abcGrp4_Cards);
    // abcGrp5
    FlashCard abcGrp5_d = new FlashCard();
    FlashCard abcGrp5_f = new FlashCard();
    FlashCard abcGrp5_h = new FlashCard();
    FlashCard abcGrp5_j = new FlashCard();
    FlashCard abcGrp5_m = new FlashCard();
    // Array and object for abcGrp5
    FlashCard[] abcGrp5_Cards = new FlashCard[5];
    List<FlashCard> abcGrp5_CardsList = Arrays.asList(abcGrp5_Cards);
    /////////////////////////////////////////////////////////////////
    // END OF ABCs.
    // Number and Symbol Flashcards.
    // NumSymGrp1
    FlashCard NumSymGrp1_1 = new FlashCard();
    FlashCard NumSymGrp1_2 = new FlashCard();
    FlashCard NumSymGrp1_3 = new FlashCard();
    FlashCard NumSymGrp1_5 = new FlashCard();
    FlashCard NumSymGrp1_9 = new FlashCard();
    // Array and list object for NumSymGrp1
    FlashCard[] NumSymGrp1_Cards = new FlashCard[5];
    // List allows use of shuffle.
    List<FlashCard> NumSymGrp1_CardsList = Arrays.asList(NumSymGrp1_Cards);
    // NumSymGrp2
    FlashCard NumSymGrp2_0 = new FlashCard();
    FlashCard NumSymGrp2_4 = new FlashCard();
    FlashCard NumSymGrp2_6 = new FlashCard();
    FlashCard NumSymGrp2_7 = new FlashCard();
    FlashCard NumSymGrp2_8 = new FlashCard();
    // Array object for NumSymGrp2
    FlashCard[] NumSymGrp2_Cards = new FlashCard[5];
    List<FlashCard> NumSymGrp2_CardsList = Arrays.asList(NumSymGrp2_Cards);
    // NumSymGrp3
    FlashCard NumSymGrp3_Apostrophe = new FlashCard();
    FlashCard NumSymGrp3_Colon = new FlashCard();
    FlashCard NumSymGrp3_Comma = new FlashCard();
    FlashCard NumSymGrp3_Hyphen = new FlashCard();
    FlashCard NumSymGrp3_SemiColon = new FlashCard();
    FlashCard NumSymGrp3_Asterisk = new FlashCard();
    // Array object for NumSymGrp3
    FlashCard[] NumSymGrp3_Cards = new FlashCard[6];
    
    List<FlashCard> NumSymGrp3_CardsList = Arrays.asList(NumSymGrp3_Cards);
    // NumSymGrp4
    FlashCard NumSymGrp4_ClosingQuotation = new FlashCard();
    FlashCard NumSymGrp4_ExclamationPoint = new FlashCard();
    FlashCard NumSymGrp4_OpeningOrClosingParantheses = new FlashCard();
    FlashCard NumSymGrp4_OpeningQuotationOrQuestionMark = new FlashCard();
    FlashCard NumSymGrp4_Period = new FlashCard();
    // Array and object for NumSymGrp4
    FlashCard[] NumSymGrp4_Cards = new FlashCard[5];
    
    List<FlashCard> NumSymGrp4_CardsList = Arrays.asList(NumSymGrp4_Cards);
    // NumSymGrp5
    FlashCard NumSymGrp5_CapitalSign = new FlashCard();
    FlashCard NumSymGrp5_Dollars = new FlashCard();
    FlashCard NumSymGrp5_ItalicSignDecimalPoint = new FlashCard();
    FlashCard NumSymGrp5_LetterSign = new FlashCard();
    FlashCard NumSymGrp5_NumberSign = new FlashCard();
    // Array and object for NumSymGrp5
    FlashCard[] NumSymGrp5_Cards = new FlashCard[5];
    List<FlashCard> NumSymGrp5_CardsList = Arrays.asList(NumSymGrp5_Cards);
    // NumSymGrp6 
    FlashCard NumSymGrp6_DoubleCapitalSign = new FlashCard();
    FlashCard NumSymGrp6_DoubleItalicSign = new FlashCard();
    FlashCard NumSymGrp6_TerminationSign = new FlashCard();
    FlashCard NumSymGrp6_Degrees = new FlashCard();
    // Array and object for NumSymGrp6
    FlashCard[] NumSymGrp6_Cards = new FlashCard[4];
    
    List<FlashCard> NumSymGrp6_CardsList = Arrays.asList(NumSymGrp6_Cards);
    // NumSymGrp7
    FlashCard NumSymGrp7_Ampersand = new FlashCard();
    FlashCard NumSymGrp7_AtSymbol = new FlashCard();
    FlashCard NumSymGrp7_Cents = new FlashCard();
    FlashCard NumSymGrp7_Percent = new FlashCard();
    FlashCard NumSymGrp7_ClosedBracket = new FlashCard();
    FlashCard NumSymGrp7_OpenBracket = new FlashCard();
    // Array and object for NumSymGrp7
    FlashCard[] NumSymGrp7_Cards = new FlashCard[6];

    List<FlashCard> NumSymGrp7_CardsList = Arrays.asList(NumSymGrp7_Cards);
    // NumSymGrp8
    FlashCard NumSymGrp8_PoundSymbol = new FlashCard();
    FlashCard NumSymGrp8_Copyright = new FlashCard();
    FlashCard NumSymGrp8_RegisteredTrademark = new FlashCard();
    FlashCard NumSymGrp8_Slash = new FlashCard();
    FlashCard NumSymGrp8_Trademark = new FlashCard();
    // Array and object for NumSymGrp8
    FlashCard[] NumSymGrp8_Cards = new FlashCard[5];

    List<FlashCard> NumSymGrp8_CardsList = Arrays.asList(NumSymGrp8_Cards);
    ///////////////////////////////////////////////////////////////////////////
    // END OF Number and Symbols.
    // Common Combination Flashcards.
    // ComComboGrp1
    FlashCard ComComboGrp1__bb_ = new FlashCard();
    FlashCard ComComboGrp1__cc_ = new FlashCard();
    FlashCard ComComboGrp1_ch = new FlashCard();
    FlashCard ComComboGrp1_com__ = new FlashCard();
    FlashCard ComComboGrp1_con__ = new FlashCard();
    FlashCard ComComboGrp1_st = new FlashCard();
    // Array and list object for ComComboGrp1
    FlashCard[] ComComboGrp1_Cards = new FlashCard[6];
    // List allows use of shuffle.
    List<FlashCard> ComComboGrp1_CardsList = Arrays.asList(ComComboGrp1_Cards);
    // ComComboGrp2
    FlashCard ComComboGrp2__ble = new FlashCard();
    FlashCard ComComboGrp2_dd_ = new FlashCard();
    FlashCard ComComboGrp2_dis_ = new FlashCard();
    // ea Removed 4/23/2013 as per customer request.
    //FlashCard ComComboGrp2_ea = new FlashCard(); 
    FlashCard ComComboGrp2_sh = new FlashCard();
    FlashCard ComComboGrp2_th = new FlashCard();
    // Array and list object for ComComboGrp2
    FlashCard[] ComComboGrp2_Cards = new FlashCard[5];
    // List allows use of shuffle.
    List<FlashCard> ComComboGrp2_CardsList = Arrays.asList(ComComboGrp2_Cards);
    // ComComboGrp3
    FlashCard ComComboGrp3_ar = new FlashCard();
    FlashCard ComComboGrp3_ed = new FlashCard();
    FlashCard ComComboGrp3_er = new FlashCard();
    FlashCard ComComboGrp3_ing = new FlashCard();
    FlashCard ComComboGrp3_ou = new FlashCard();
    FlashCard ComComboGrp3_ow = new FlashCard();
    // Array and list object for ComComboGrp3
    FlashCard[] ComComboGrp3_Cards = new FlashCard[6];
    // List allows use of shuffle.
    List<FlashCard> ComComboGrp3_CardsList = Arrays.asList(ComComboGrp3_Cards);
    // ComComboGrp4
    FlashCard ComComboGrp4__ea_ = new FlashCard();
    FlashCard ComComboGrp4_en = new FlashCard();
    FlashCard ComComboGrp4__ff_ = new FlashCard();
    FlashCard ComComboGrp4__gg_ = new FlashCard();
    FlashCard ComComboGrp4_gh = new FlashCard();
    FlashCard ComComboGrp4_wh = new FlashCard();
    // Array and list object for ComComboGrp4
    FlashCard[] ComComboGrp4_Cards = new FlashCard[6];
    // List allows use of shuffle.
    List<FlashCard> ComComboGrp4_CardsList = Arrays.asList(ComComboGrp4_Cards);
    // ComComboGrp5
    FlashCard ComComboGrp5__ation = new FlashCard();
    FlashCard ComComboGrp5__ong = new FlashCard();
    FlashCard ComComboGrp5__ound = new FlashCard();
    FlashCard ComComboGrp5__sion = new FlashCard();
    FlashCard ComComboGrp5__tion = new FlashCard();
    // Array and list object for ComComboGrp5
    FlashCard[] ComComboGrp5_Cards = new FlashCard[5];
    // List allows use of shuffle.
    List<FlashCard> ComComboGrp5_CardsList = Arrays.asList(ComComboGrp5_Cards);
    // ComComboGrp6
    FlashCard ComComboGrp6__less = new FlashCard();
    FlashCard ComComboGrp6__ment = new FlashCard();
    FlashCard ComComboGrp6__ness = new FlashCard();
    FlashCard ComComboGrp6__ount = new FlashCard();
    // Array and list object for ComComboGrp6
    FlashCard[] ComComboGrp6_Cards = new FlashCard[4];
    // List allows use of shuffle.
    List<FlashCard> ComComboGrp6_CardsList = Arrays.asList(ComComboGrp6_Cards);
    // ComComboGrp7
    FlashCard ComComboGrp7_ally = new FlashCard();
    FlashCard ComComboGrp7__ance = new FlashCard();
    FlashCard ComComboGrp7__ence = new FlashCard();
    FlashCard ComComboGrp7__ful = new FlashCard();
    FlashCard ComComboGrp7_ity = new FlashCard();
    // Array and list object for ComComboGrp7
    FlashCard[] ComComboGrp7_Cards = new FlashCard[5];
    // List allows use of shuffle.
    List<FlashCard> ComComboGrp7_CardsList = Arrays.asList(ComComboGrp7_Cards);
    ///////////////////////////////////////////////////////////////////////////
    // END OF COMMON COMBINATIONS
 
    // This Linked list allows use of .addAll to add list together.
    List<FlashCard> CurrentGrps_CardsList = new ArrayList<FlashCard>();
    
    // Iterator to move through list.
    Iterator<FlashCard> iteratorCurrentGrps = CurrentGrps_CardsList.iterator();
    String Ans1 = "BLANK1";
    String Ans2 = "BLANK2";
    String Ans3 = "BLANK3";
    String Ans4 = "BLANK4";
    String Ans5 = "BLANK5";
    String Ans6 = "BLANK6";
    int initializeABC = 0;
    int nextCardIndex = 0;
    boolean consecutiveCorrectLessThanZero = true;
    // Variables to take the selected tabs 
    // labels and choices buttons.
    JLabel currentTabImgLabel = new JLabel();
    JRadioButton currentTabAns1Btn = new JRadioButton();
    JRadioButton currentTabAns2Btn = new JRadioButton();
    JRadioButton currentTabAns3Btn = new JRadioButton();
    JRadioButton currentTabAns4Btn = new JRadioButton();
    JRadioButton currentTabAns5Btn = new JRadioButton();
    JRadioButton currentTabAns6Btn = new JRadioButton();
    // Flags to track which tab is in use and last group added to 
    // CurrentGrps_CardsList.
    String CurrentTab;
    String LastGrpAdded;
    
    // Flags Set by Baseline and Training buttons.
    boolean baselineABC = false;
    boolean trainingABC = false;
    boolean rehearsalABC = false;
    
    boolean baselineNumSym = false;
    boolean trainingNumSym = false;
    boolean rehearsalNumSym = false;
    
    boolean baselineComCombo = false;
    boolean trainingComCombo = false;
    boolean rehearsalComCombo = false;
    
    boolean baselineWords = false;
    boolean trainingWords = false;
    boolean rehearsalWords = false;
    
    boolean baselineWords_B = false;
    boolean trainingWords_B = false;
    boolean rehearsalWords_B = false;
    
    boolean baselineWords_C = false;
    boolean trainingWords_C = false;
    boolean rehearsalWords_C = false;
    
    boolean baselineAll = false;
    boolean trainingAll = false;
    boolean rehearsalAll = false;
    
    // Generic Baseline and Training flags.
    boolean baseline = false;
    boolean training = false;
    boolean rehearsal = false;
    
    // Test type used to record Baseline or Training
    String testType;
    // CardListAll set flag
    boolean cardListAllFlag = false;
    // Flag for press of answerButton
    boolean answerButton = false;
    // Index counter for use when running baseline test.
    int indexAll = 0;
    // Group name as it is written to message box and record.
    String groupName;
    // String to record result.
    String result;
    // Flag to mark when FIRST answer has been recorded.
    boolean ansRecorded = false;
    // Count of question answered. ONLY THE FIRST ANSWER IS COUNTED.
    Double countQuestionsAnswered = 0.000;
    // Used as reference for array of last 15 answers
    //int currentCardCount = 0;
    // Count of correct answers. ONLY THE FIRST ANSWER IS COUNTED.
    Double countCorrectAnswers = 0.000;
    // Percent Correct.
    Double percentCorrect = 0.00;
    Double last15PercentCorrect = 0.00;
    // Flag set when a question is answered.
    boolean questionAnswered = false;
    // Score of 15 last cards from the last 
    // added group calculated flag.
    boolean scoreCalculated = false;
    // Variables added to support calculation of running
    // score for last 15 trials
    // AnsValue is 1 for correct Answers, 0 for wrong answers.
    int ansValue = 0;
    // Array and List to hold last 15 answers.
    Integer[] last15AnswersArray = new Integer[15];
    List<Integer> last15AnswersList = Arrays.asList(last15AnswersArray);
    // This is index of last15AnswersArray. It should be reset to zero
    // for the most recent group of flashcards added.
    int indexLast15 = 0;
    // Flag for when the score for the FIRST 15 cards of the last 
    // groupg added is calculated.
    boolean first15Calculated = false;
    // Logic flag to mark when all cards in CurrentGrps_CardsList
    // have been presented the targeted number of times.        
    boolean presentationCountEqualsTarget = false;
    //private int i;

    // Card ImageIcon width and height:
    // ABC width = 45, height = 63
    // Number and Symbols width = 90, height = 63
    // Common Combos width = 261, height = 63
    // Words width = 261, height = 63
    public void initializeCardsABC() {

        // Set attributes for a FlashCard
        // Changed ImageIcon(getClass().getResource("/BlankCard/blank.PNG")) 
        //to ImageIcon("blank.PNG")
        BlankCard.setFlashCardImg(new ImageIcon(getClass().getResource("blank.png")), 260, 63);

        // Set choice prototype for FlashCard.
        // NOTE: CORRECT ANSWER IS FIRST ENTRY.
        // public void setChoices(String CorrAns, String Chc1, String Chc2,
        //    String Chc3, String Chc4, String Chc5)

        BlankCard.setChoices(" ", " ", " ", " ", " ", " ");

        BlankCard.setCardName(BlankCard);

        // Set attributes for a FlashCard
        abcGrp1_a.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp1_a.PNG")), 45, 63);

        // Set choice prototype for FlashCard.
        // NOTE: CORRECT ANSWER IS FIRST ENTRY.
        // public void setChoices(String CorrAns, String Chc1, String Chc2,
        //    String Chc3, String Chc4, String Chc5)

        abcGrp1_a.setChoices("a", "b", "c", "e", "i", " ");

        abcGrp1_a.setCardName(abcGrp1_a);

        // First Array element for abcGrp1
        abcGrp1_Cards[0] = abcGrp1_a;

        // Set attributes for b FlashCard
        abcGrp1_b.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp1_b.PNG")), 45, 63);

        abcGrp1_b.setChoices("b", "a", "c", "e", "i", " ");

        abcGrp1_b.setCardName(abcGrp1_b);

        abcGrp1_Cards[1] = abcGrp1_b;

        // Set attributes for c FlashCard
        abcGrp1_c.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp1_c.PNG")), 45, 63);

        abcGrp1_c.setChoices("c", "a", "b", "e", "i", " ");

        abcGrp1_c.setCardName(abcGrp1_c);

        abcGrp1_Cards[2] = abcGrp1_c;

        // Set attributes for e FlashCard
        abcGrp1_e.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp1_e.PNG")), 45, 63);

        abcGrp1_e.setChoices("e", "a", "c", "b", "i", " ");

        abcGrp1_e.setCardName(abcGrp1_e);

        abcGrp1_Cards[3] = abcGrp1_e;

        // Set attributes for i FlashCard
        abcGrp1_i.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp1_i.PNG")), 45, 63);

        abcGrp1_i.setChoices("i", "a", "c", "e", "b", " ");

        abcGrp1_i.setCardName(abcGrp1_i);

        abcGrp1_Cards[4] = abcGrp1_i;

        // List allows use of shuffle.
        abcGrp1_CardsList = Arrays.asList(abcGrp1_Cards);
//         END OF abcGRP1 //////////////////////////////////////////////

        // Set attributes for k FlashCard
        abcGrp2_k.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp2_k.PNG")), 45, 63);

        abcGrp2_k.setChoices("k", "l", "u", "v", "x", " ");

        abcGrp2_k.setCardName(abcGrp2_k);

        abcGrp2_Cards[0] = abcGrp2_k;

        // Set attributes for l FlashCard
        abcGrp2_l.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp2_l.PNG")), 45, 63);

        abcGrp2_l.setChoices("l", "k", "u", "v", "x", " ");

        abcGrp2_l.setCardName(abcGrp2_l);

        abcGrp2_Cards[1] = abcGrp2_l;

        // Set attributes for u FlashCard
        abcGrp2_u.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp2_u.PNG")), 45, 63);

        abcGrp2_u.setChoices("u", "k", "l", "v", "x", " ");

        abcGrp2_u.setCardName(abcGrp2_u);

        abcGrp2_Cards[2] = abcGrp2_u;

        // Set attributes for v FlashCard
        abcGrp2_v.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp2_v.PNG")), 45, 63);

        abcGrp2_v.setChoices("v", "k", "l", "u", "x", " ");

        abcGrp2_v.setCardName(abcGrp2_v);

        abcGrp2_Cards[3] = abcGrp2_v;

        // Set attributes for x FlashCard
        abcGrp2_x.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp2_x.PNG")), 45, 63);

        abcGrp2_x.setChoices("x", "k", "l", "u", "v", " ");

        abcGrp2_x.setCardName(abcGrp2_x);

        abcGrp2_Cards[4] = abcGrp2_x;

        // List allows use of shuffle.
        abcGrp2_CardsList = Arrays.asList(abcGrp2_Cards);

        // END OF abcGRP2 ////////////////////////////////////////////////

        // Set attributes for n FlashCard
        abcGrp3_n.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp3_n.PNG")), 45, 63);

        abcGrp3_n.setChoices("n", "s", "t", "y", "z", " ");

        abcGrp3_n.setCardName(abcGrp3_n);

        abcGrp3_Cards[0] = abcGrp3_n;

        // Set attributes for s FlashCard
        abcGrp3_s.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp3_s.PNG")), 45, 63);

        abcGrp3_s.setChoices("s", "n", "t", "y", "z", " ");

        abcGrp3_s.setCardName(abcGrp3_s);

        abcGrp3_Cards[1] = abcGrp3_s;

        // Set attributes for t FlashCard
        abcGrp3_t.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp3_t.PNG")), 45, 63);

        abcGrp3_t.setChoices("t", "n", "s", "y", "z", " ");

        abcGrp3_t.setCardName(abcGrp3_t);

        abcGrp3_Cards[2] = abcGrp3_t;

        // Set attributes for y FlashCard
        abcGrp3_y.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp3_y.PNG")), 45, 63);

        abcGrp3_y.setChoices("y", "n", "s", "t", "z", " ");

        abcGrp3_y.setCardName(abcGrp3_y);

        abcGrp3_Cards[3] = abcGrp3_y;

        // Set attributes for z FlashCard
        abcGrp3_z.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp3_z.PNG")), 45, 63);

        abcGrp3_z.setChoices("z", "n", "s", "t", "y", " ");

        abcGrp3_z.setCardName(abcGrp3_z);

        abcGrp3_Cards[4] = abcGrp3_z;

        // List allows use of shuffle.
        abcGrp3_CardsList = Arrays.asList(abcGrp3_Cards);

        // END OF abcGrp3 ////////////////////////////////////////////////

        // Set attributes for g FlashCard
        abcGrp4_g.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp4_g.PNG")), 45, 63);

        abcGrp4_g.setChoices("g", "o", "p", "q", "r", "w");

        abcGrp4_g.setCardName(abcGrp4_g);

        abcGrp4_Cards[0] = abcGrp4_g;

        // Set attributes for o FlashCard
        abcGrp4_o.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp4_o.PNG")), 45, 63);

        abcGrp4_o.setChoices("o", "g", "p", "q", "r", "w");

        abcGrp4_o.setCardName(abcGrp4_o);

        abcGrp4_Cards[1] = abcGrp4_o;

        // Set attributes for p FlashCard
        abcGrp4_p.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp4_p.PNG")), 45, 63);

        abcGrp4_p.setChoices("p", "g", "o", "q", "r", "w");

        abcGrp4_p.setCardName(abcGrp4_p);

        abcGrp4_Cards[2] = abcGrp4_p;

        // Set attributes for q FlashCard
        abcGrp4_q.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp4_q.PNG")), 45, 63);

        abcGrp4_q.setChoices("q", "g", "o", "p", "r", "w");

        abcGrp4_q.setCardName(abcGrp4_q);

        abcGrp4_Cards[3] = abcGrp4_q;

        // Set attributes for r FlashCard
        abcGrp4_r.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp4_r.PNG")), 45, 63);

        abcGrp4_r.setChoices("r", "g", "o", "p", "q", "w");

        abcGrp4_r.setCardName(abcGrp4_r);

        abcGrp4_Cards[4] = abcGrp4_r;

        // Set attributes for w FlashCard
        abcGrp4_w.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp4_w.PNG")), 45, 63);

        abcGrp4_w.setChoices("w", "g", "o", "p", "q", "r");

        abcGrp4_w.setCardName(abcGrp4_w);

        abcGrp4_Cards[5] = abcGrp4_w;

        // List allows use of shuffle.
        abcGrp4_CardsList = Arrays.asList(abcGrp4_Cards);

        // END OF abcGrp4 ///////////////////////////////////////////////////

        // Set attributes for d FlashCard
        abcGrp5_d.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp5_d.PNG")), 45, 63);

        abcGrp5_d.setChoices("d", "f", "h", "j", "m", " ");

        abcGrp5_d.setCardName(abcGrp5_d);

        abcGrp5_Cards[0] = abcGrp5_d;

        // Set attributes for f FlashCard
        abcGrp5_f.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp5_f.PNG")), 45, 63);

        abcGrp5_f.setChoices("f", "d", "h", "j", "m", " ");

        abcGrp5_f.setCardName(abcGrp5_f);

        abcGrp5_Cards[1] = abcGrp5_f;

        // Set attributes for h FlashCard
        abcGrp5_h.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp5_h.PNG")), 45, 63);

        abcGrp5_h.setChoices("h", "d", "f", "j", "m", " ");

        abcGrp5_h.setCardName(abcGrp5_h);

        abcGrp5_Cards[2] = abcGrp5_h;

        // Set attributes for j FlashCard
        abcGrp5_j.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp5_j.PNG")), 45, 63);

        abcGrp5_j.setChoices("j", "d", "f", "h", "m", " ");

        abcGrp5_j.setCardName(abcGrp5_j);

        abcGrp5_Cards[3] = abcGrp5_j;

        // Set attributes for m FlashCard
        abcGrp5_m.setFlashCardImg(new ImageIcon(getClass().getResource("abcGrp5_m.PNG")), 45, 63);

        abcGrp5_m.setChoices("m", "d", "f", "h", "j", " ");

        abcGrp5_m.setCardName(abcGrp5_m);

        abcGrp5_Cards[4] = abcGrp5_m;

        // List allows use of shuffle.
        abcGrp5_CardsList = Arrays.asList(abcGrp5_Cards);

    }

    public void initializeCardsNumSym() {

        // Set attributes for a FlashCard
        // Changed ImageIcon(getClass().getResource("/BlankCard/blank.PNG")) 
        //to ImageIcon("blank.PNG")
        BlankCard.setFlashCardImg(new ImageIcon(getClass().getResource("blank.png")), 260, 63);

        // Set choice prototype for FlashCard.
        // NOTE: CORRECT ANSWER IS FIRST ENTRY.
        // public void setChoices(String CorrAns, String Chc1, String Chc2,
        //    String Chc3, String Chc4, String Chc5)

        BlankCard.setChoices(" ", " ", " ", " ", " ", " ");

        BlankCard.setCardName(BlankCard);


        // Set attributes for 1 FlashCard
        NumSymGrp1_1.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp1_1.PNG")), 90, 63);

        // Set choice prototype for FlashCard.
        // NOTE: CORRECT ANSWER IS FIRST ENTRY.
        // public void setChoices(String CorrAns, String Chc1, String Chc2,
        //    String Chc3, String Chc4, String Chc5)

        NumSymGrp1_1.setChoices("1", "2", "3", "5", "9", " ");

        NumSymGrp1_1.setCardName(NumSymGrp1_1);

        // First Array element for NumSymGrp1
        NumSymGrp1_Cards[0] = NumSymGrp1_1;

        // Set attributes for 2 FlashCard
        NumSymGrp1_2.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp1_2.PNG")), 90, 63);

        NumSymGrp1_2.setChoices("2", "1", "3", "5", "9", " ");

        NumSymGrp1_2.setCardName(NumSymGrp1_2);

        NumSymGrp1_Cards[1] = NumSymGrp1_2;

        // Set attributes for 3 FlashCard
        NumSymGrp1_3.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp1_3.PNG")), 90, 63);

        NumSymGrp1_3.setChoices("3", "2", "5", "1", "9", " ");

        NumSymGrp1_3.setCardName(NumSymGrp1_3);

        // First Array element for NumSymGrp1
        NumSymGrp1_Cards[2] = NumSymGrp1_3;

        // Set attributes for 5 FlashCard
        NumSymGrp1_5.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp1_5.PNG")), 90, 63);

        NumSymGrp1_5.setChoices("5", "2", "3", "1", "9", " ");

        NumSymGrp1_5.setCardName(NumSymGrp1_5);

        NumSymGrp1_Cards[3] = NumSymGrp1_5;

        // Set attributes for 9 FlashCard
        NumSymGrp1_9.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp1_9.PNG")), 90, 63);

        NumSymGrp1_9.setChoices("9", "2", "3", "1", "5", " ");

        NumSymGrp1_9.setCardName(NumSymGrp1_9);

        NumSymGrp1_Cards[4] = NumSymGrp1_9;

        // List allows use of shuffle.
        NumSymGrp1_CardsList = Arrays.asList(NumSymGrp1_Cards);

//        // END OF abcGRP1 ////////////////////////////////////////////////

        // Set attributes for 0 FlashCard
        NumSymGrp2_0.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp2_0.PNG")), 90, 63);

        NumSymGrp2_0.setChoices("0", "4", "6", "7", "8", " ");

        NumSymGrp2_0.setCardName(NumSymGrp2_0);

        NumSymGrp2_Cards[0] = NumSymGrp2_0;

        // Set attributes for 4 FlashCard
        NumSymGrp2_4.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp2_4.PNG")), 90, 63);

        NumSymGrp2_4.setChoices("4", "0", "6", "7", "8", " ");

        NumSymGrp2_4.setCardName(NumSymGrp2_4);

        NumSymGrp2_Cards[1] = NumSymGrp2_4;

        // Set attributes for 6 FlashCard
        NumSymGrp2_6.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp2_6.PNG")), 90, 63);

        NumSymGrp2_6.setChoices("6", "0", "4", "7", "8", " ");

        NumSymGrp2_6.setCardName(NumSymGrp2_6);

        NumSymGrp2_Cards[2] = NumSymGrp2_6;

        // Set attributes for 7 FlashCard
        NumSymGrp2_7.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp2_7.PNG")), 90, 63);

        NumSymGrp2_7.setChoices("7", "0", "6", "4", "8", " ");

        NumSymGrp2_7.setCardName(NumSymGrp2_7);

        NumSymGrp2_Cards[3] = NumSymGrp2_7;

        // Set attributes for 8 FlashCard
        NumSymGrp2_8.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp2_8.PNG")), 90, 63);

        NumSymGrp2_8.setChoices("8", "0", "6", "7", "4", " ");

        NumSymGrp2_8.setCardName(NumSymGrp2_8);

        NumSymGrp2_Cards[4] = NumSymGrp2_8;

        // List allows use of shuffle.
        NumSymGrp2_CardsList = Arrays.asList(NumSymGrp2_Cards);

        // END OF NumSymGrp2 ////////////////////////////////////////////////
        // Source files for symbol images are 2112 x 672. Therefore changing to 
        // 198, 63 to display images.

        // Set attributes for ' FlashCard
        NumSymGrp3_Apostrophe.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp3_Apostrophe.PNG")), 198, 63);

        NumSymGrp3_Apostrophe.setChoices("'", ":", ",", "-", ";", "*");

        NumSymGrp3_Apostrophe.setCardName(NumSymGrp3_Apostrophe);

        NumSymGrp3_Cards[0] = NumSymGrp3_Apostrophe;

        // Set attributes for : FlashCard
        NumSymGrp3_Colon.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp3_Colon.PNG")), 198, 63);

        NumSymGrp3_Colon.setChoices(":", "'", ",", "-", ";", "*");

        NumSymGrp3_Colon.setCardName(NumSymGrp3_Colon);

        NumSymGrp3_Cards[1] = NumSymGrp3_Colon;

        // Set attributes for , FlashCard
        NumSymGrp3_Comma.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp3_Comma.PNG")), 198, 63);

        NumSymGrp3_Comma.setChoices(",", "'", ":", "-", ";", "*");

        NumSymGrp3_Comma.setCardName(NumSymGrp3_Comma);

        NumSymGrp3_Cards[2] = NumSymGrp3_Comma;

        // Set attributes for - FlashCard
        NumSymGrp3_Hyphen.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp3_Hyphen.PNG")), 198, 63);

        NumSymGrp3_Hyphen.setChoices("-", "'", ":", ",", ";", "*");

        NumSymGrp3_Hyphen.setCardName(NumSymGrp3_Hyphen);

        NumSymGrp3_Cards[3] = NumSymGrp3_Hyphen;

        // Set attributes for ; FlashCard
        NumSymGrp3_SemiColon.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp3_SemiColon.PNG")), 198, 63);

        NumSymGrp3_SemiColon.setChoices(";", "'", ":", ",", "-", "*");

        NumSymGrp3_SemiColon.setCardName(NumSymGrp3_SemiColon);

        NumSymGrp3_Cards[4] = NumSymGrp3_SemiColon;

        // Set attributes for ; FlashCard
        NumSymGrp3_Asterisk.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp3_Asterisk.PNG")), 198, 63);

        NumSymGrp3_Asterisk.setChoices("*", ";", "'", ":", ",", "-");

        NumSymGrp3_Asterisk.setCardName(NumSymGrp3_Asterisk);

        NumSymGrp3_Cards[5] = NumSymGrp3_Asterisk;

        // List allows use of shuffle.
        NumSymGrp3_CardsList = Arrays.asList(NumSymGrp3_Cards);

        // END OF NumSymGrp3 ////////////////////////////////////////////////

        // Set attributes for ” FlashCard
        NumSymGrp4_ClosingQuotation.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp4_ClosingQuotation.PNG")), 198, 63);

        NumSymGrp4_ClosingQuotation.setChoices("”", "!", "( or )", "“ or ?", ". Period ", " ");

        NumSymGrp4_ClosingQuotation.setCardName(NumSymGrp4_ClosingQuotation);

        NumSymGrp4_Cards[0] = NumSymGrp4_ClosingQuotation;

        // Set attributes for ! FlashCard
        NumSymGrp4_ExclamationPoint.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp4_ExclamationPoint.PNG")), 198, 63);

        NumSymGrp4_ExclamationPoint.setChoices("!", "”", "( or )", "“ or ?", ". Period ", " ");

        NumSymGrp4_ExclamationPoint.setCardName(NumSymGrp4_ExclamationPoint);

        NumSymGrp4_Cards[1] = NumSymGrp4_ExclamationPoint;

        // Set attributes for ( or ) FlashCard
        NumSymGrp4_OpeningOrClosingParantheses.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp4_OpeningOrClosingParantheses.PNG")), 198, 63);

        NumSymGrp4_OpeningOrClosingParantheses.setChoices("( or )", "!", "”", "“ or ?", ". Period ", " ");

        NumSymGrp4_OpeningOrClosingParantheses.setCardName(NumSymGrp4_OpeningOrClosingParantheses);

        NumSymGrp4_Cards[2] = NumSymGrp4_OpeningOrClosingParantheses;

        // Set attributes for “ or ? FlashCard
        NumSymGrp4_OpeningQuotationOrQuestionMark.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp4_OpeningQuotationOrQuestionMark.PNG")), 198, 63);

        NumSymGrp4_OpeningQuotationOrQuestionMark.setChoices("“ or ?", "( or )", "!", "”", ". Period ", " ");

        NumSymGrp4_OpeningQuotationOrQuestionMark.setCardName(NumSymGrp4_OpeningQuotationOrQuestionMark);

        NumSymGrp4_Cards[3] = NumSymGrp4_OpeningQuotationOrQuestionMark;

        // Set attributes for . FlashCard
        NumSymGrp4_Period.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp4_Period.PNG")), 198, 63);

        NumSymGrp4_Period.setChoices(". Period ", "( or )", "!", "”", "“ or ?", " ");

        NumSymGrp4_Period.setCardName(NumSymGrp4_Period);

        NumSymGrp4_Cards[4] = NumSymGrp4_Period;

        // List allows use of shuffle.
        NumSymGrp4_CardsList = Arrays.asList(NumSymGrp4_Cards);

        // END OF NumSymGrp4 ///////////////////////////////////////////////////

        // Set attributes for Capital Sign FlashCard
        NumSymGrp5_CapitalSign.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp5_CapitalSign.PNG")), 198, 63);

        NumSymGrp5_CapitalSign.setChoices("Capital Sign", "$",
                "Italic Sign or Decimal Point", "Letter Sign", "Number Sign", " ");

        NumSymGrp5_CapitalSign.setCardName(NumSymGrp5_CapitalSign);

        NumSymGrp5_Cards[0] = NumSymGrp5_CapitalSign;

        // Set attributes for $ FlashCard
        NumSymGrp5_Dollars.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp5_Dollars.PNG")), 198, 63);

        NumSymGrp5_Dollars.setChoices("$", "Capital Sign",
                "Italic Sign or Decimal Point", "Letter Sign", "Number Sign", " ");

        NumSymGrp5_Dollars.setCardName(NumSymGrp5_Dollars);

        NumSymGrp5_Cards[1] = NumSymGrp5_Dollars;

        // Set attributes for Italic Sign Decimal Point FlashCard
        NumSymGrp5_ItalicSignDecimalPoint.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp5_ItalicSignDecimalPoint.PNG")), 198, 63);

        NumSymGrp5_ItalicSignDecimalPoint.setChoices("Italic Sign or Decimal Point", "$", "Capital Sign", "Letter Sign", "Number Sign", " ");

        NumSymGrp5_ItalicSignDecimalPoint.setCardName(NumSymGrp5_ItalicSignDecimalPoint);

        NumSymGrp5_Cards[2] = NumSymGrp5_ItalicSignDecimalPoint;

        // Set attributes for Letter Sign FlashCard
        NumSymGrp5_LetterSign.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp5_LetterSign.PNG")), 198, 63);

        NumSymGrp5_LetterSign.setChoices("Letter Sign", "Italic Sign or Decimal Point", "$",
                "Capital Sign", "Number Sign", " ");

        NumSymGrp5_LetterSign.setCardName(NumSymGrp5_LetterSign);

        NumSymGrp5_Cards[3] = NumSymGrp5_LetterSign;

        // Set attributes for Number Sign FlashCard
        NumSymGrp5_NumberSign.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp5_NumberSign.PNG")), 198, 63);

        NumSymGrp5_NumberSign.setChoices("Number Sign", "Letter Sign", "Italic Sign or Decimal Point", "$",
                "Capital Sign", " ");

        NumSymGrp5_NumberSign.setCardName(NumSymGrp5_NumberSign);

        NumSymGrp5_Cards[4] = NumSymGrp5_NumberSign;

        // List allows use of shuffle.
        NumSymGrp5_CardsList = Arrays.asList(NumSymGrp5_Cards);
        // END OF NumSymGrp5 ///////////////////////////////////////////////////        

        // Set attributes for Double Capital Sign FlashCard
        NumSymGrp6_DoubleCapitalSign.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp6_DoubleCapitalSign.PNG")), 198, 63);

        // Set choice prototype for FlashCard.
        // NOTE: CORRECT ANSWER IS FIRST ENTRY.
        // public void setChoices(String CorrAns, String Chc1, String Chc2,
        //    String Chc3, String Chc4, String Chc5)

        NumSymGrp6_DoubleCapitalSign.setChoices("Double Capital Sign", "Double Italic Sign", "Termination Sign", "°", " ", " ");

        NumSymGrp6_DoubleCapitalSign.setCardName(NumSymGrp6_DoubleCapitalSign);

        // First Array element for NumSymGrp6
        NumSymGrp6_Cards[0] = NumSymGrp6_DoubleCapitalSign;

        // Set attributes for Double Italic Sign FlashCard      
        NumSymGrp6_DoubleItalicSign.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp6_DoubleItalicSign.PNG")), 198, 63);

        NumSymGrp6_DoubleItalicSign.setChoices("Double Italic Sign", "Double Capital Sign", "Termination Sign", "°", " ", " ");

        NumSymGrp6_DoubleItalicSign.setCardName(NumSymGrp6_DoubleItalicSign);

        NumSymGrp6_Cards[1] = NumSymGrp6_DoubleItalicSign;

        // Set attributes for Termination Sign FlashCard
        NumSymGrp6_TerminationSign.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp6_TerminationSign.PNG")), 198, 63);

        NumSymGrp6_TerminationSign.setChoices("Termination Sign", "Double Capital Sign", "Double Italic Sign", "°", " ", " ");

        NumSymGrp6_TerminationSign.setCardName(NumSymGrp6_TerminationSign);

        // First Array element for NumSymGrp6
        NumSymGrp6_Cards[2] = NumSymGrp6_TerminationSign;

        // Set attributes for Degrees FlashCard
        NumSymGrp6_Degrees.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp6_Degrees.PNG")), 198, 63);

        NumSymGrp6_Degrees.setChoices("°", "Double Capital Sign", "Double Italic Sign", "Termination Sign", " ", " ");

        NumSymGrp6_Degrees.setCardName(NumSymGrp6_Degrees);

        NumSymGrp6_Cards[3] = NumSymGrp6_Degrees;

        NumSymGrp6_CardsList = Arrays.asList(NumSymGrp6_Cards);

        // END OF NumSymGrp6 /////////////////////////////////////////////   
        // Set attributes for @ FlashCard
        NumSymGrp7_AtSymbol.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp7_AtSymbol.PNG")), 198, 63);

        NumSymGrp7_AtSymbol.setChoices("@", "&", "¢", "%", "]", "[");

        NumSymGrp7_AtSymbol.setCardName(NumSymGrp7_AtSymbol);

        NumSymGrp7_Cards[0] = NumSymGrp7_AtSymbol;

        // Set attributes for & FlashCard
        NumSymGrp7_Ampersand.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp7_Ampersand.PNG")), 198, 63);

        NumSymGrp7_Ampersand.setChoices("&", "@", "¢", "%", "]", "[");

        NumSymGrp7_Ampersand.setCardName(NumSymGrp7_Ampersand);

        NumSymGrp7_Cards[1] = NumSymGrp7_Ampersand;

        // Set attributes for ¢ FlashCard
        NumSymGrp7_Cents.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp7_Cents.PNG")), 198, 63);

        NumSymGrp7_Cents.setChoices("¢", "@", "&", "%", "]", "[");

        NumSymGrp7_Cents.setCardName(NumSymGrp7_Cents);

        NumSymGrp7_Cards[2] = NumSymGrp7_Cents;

        // Set attributes for % FlashCard
        NumSymGrp7_Percent.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp7_Percent.PNG")), 198, 63);

        NumSymGrp7_Percent.setChoices("%", "@", "&", "¢", "]", "[");

        NumSymGrp7_Percent.setCardName(NumSymGrp7_Percent);

        NumSymGrp7_Cards[3] = NumSymGrp7_Percent;

        // Set attributes for ] FlashCard
        NumSymGrp7_ClosedBracket.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp7_ClosedBracket.PNG")), 198, 63);

        NumSymGrp7_ClosedBracket.setChoices("]", "@", "&", "¢", "%", "[");

        NumSymGrp7_ClosedBracket.setCardName(NumSymGrp7_ClosedBracket);

        NumSymGrp7_Cards[4] = NumSymGrp7_ClosedBracket;

        // Set attributes for [ FlashCard
        NumSymGrp7_OpenBracket.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp7_OpenBracket.PNG")), 198, 63);

        NumSymGrp7_OpenBracket.setChoices("[", "]", "@", "&", "¢", "%");

        NumSymGrp7_OpenBracket.setCardName(NumSymGrp7_OpenBracket);

        NumSymGrp7_Cards[5] = NumSymGrp7_OpenBracket;

        // List allows use of shuffle.
        NumSymGrp7_CardsList = Arrays.asList(NumSymGrp7_Cards);

//        // END OF NumSymGrp7 ////////////////////////////////////////////////

        // Set attributes for # FlashCard
        NumSymGrp8_PoundSymbol.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp8_PoundSymbol.PNG")), 198, 63);

        NumSymGrp8_PoundSymbol.setChoices("#", "©", "®", "/", "™", " ");

        NumSymGrp8_PoundSymbol.setCardName(NumSymGrp8_PoundSymbol);

        NumSymGrp8_Cards[0] = NumSymGrp8_PoundSymbol;

        // Set attributes for © FlashCard
        NumSymGrp8_Copyright.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp8_Copyright.PNG")), 198, 63);

        NumSymGrp8_Copyright.setChoices("©", "#", "®", "/", "™", " ");

        NumSymGrp8_Copyright.setCardName(NumSymGrp8_Copyright);

        NumSymGrp8_Cards[1] = NumSymGrp8_Copyright;

        // Set attributes for ® FlashCard
        NumSymGrp8_RegisteredTrademark.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp8_RegisteredTrademark.PNG")), 198, 63);

        NumSymGrp8_RegisteredTrademark.setChoices("®", "#", "©", "/", "™", " ");

        NumSymGrp8_RegisteredTrademark.setCardName(NumSymGrp8_RegisteredTrademark);

        NumSymGrp8_Cards[2] = NumSymGrp8_RegisteredTrademark;

        // Set attributes for / FlashCard
        NumSymGrp8_Slash.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp8_Slash.PNG")), 198, 63);

        NumSymGrp8_Slash.setChoices("/", "#", "©", "®", "™", " ");

        NumSymGrp8_Slash.setCardName(NumSymGrp8_Slash);

        NumSymGrp8_Cards[3] = NumSymGrp8_Slash;

        // Set attributes for ™ FlashCard
        NumSymGrp8_Trademark.setFlashCardImg(new ImageIcon(getClass().getResource("NumSymGrp8_Trademark.PNG")), 198, 63);

        NumSymGrp8_Trademark.setChoices("™", "#", "©", "®", "/", " ");

        NumSymGrp8_Trademark.setCardName(NumSymGrp8_Trademark);

        NumSymGrp8_Cards[4] = NumSymGrp8_Trademark;

        // List allows use of shuffle.
        NumSymGrp8_CardsList = Arrays.asList(NumSymGrp8_Cards);

        // END OF NumSymGrp8 //////////////////////////////    

    }

    public void initializeCardsComCombo() {

        // Set attributes for a FlashCard
        // Changed ImageIcon(getClass().getResource("/BlankCard/blank.PNG")) 
        //to ImageIcon("blank.PNG")
        BlankCard.setFlashCardImg(new ImageIcon(getClass().getResource("blank.png")), 260, 63);

        // Set choice prototype for FlashCard.
        // NOTE: CORRECT ANSWER IS FIRST ENTRY.
        // public void setChoices(String CorrAns, String Chc1, String Chc2,
        //    String Chc3, String Chc4, String Chc5)

        BlankCard.setChoices(" ", " ", " ", " ", " ", " ");

        BlankCard.setCardName(BlankCard);


        // Set attributes for _bb_ FlashCard
        ComComboGrp1__bb_.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp1_bb.PNG")), 261, 63);

        // Set choice prototype for FlashCard.
        // NOTE: CORRECT ANSWER IS FIRST ENTRY.
        // public void setChoices(String CorrAns, String Chc1, String Chc2,
        //    String Chc3, String Chc4, String Chc5)

        ComComboGrp1__bb_.setChoices("__bb__", "__cc__", "ch", "com__", "con__", "st");

        ComComboGrp1__bb_.setCardName(ComComboGrp1__bb_);

        // First Array element for ComComboGrp1
        ComComboGrp1_Cards[0] = ComComboGrp1__bb_;

        // Set attributes for ch FlashCard
        ComComboGrp1__cc_.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp1_cc.PNG")), 261, 63);

        ComComboGrp1__cc_.setChoices("__cc__", "__bb__", "ch", "com__", "con__", "st");

        ComComboGrp1__cc_.setCardName(ComComboGrp1__cc_);

        ComComboGrp1_Cards[1] = ComComboGrp1__cc_;

        // Set attributes for ch FlashCard
        ComComboGrp1_ch.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp1_ch.PNG")), 261, 63);

        ComComboGrp1_ch.setChoices("ch", "__cc__", "__bb__", "com__", "con__", "st");

        ComComboGrp1_ch.setCardName(ComComboGrp1_ch);

        // Third Array element for ComComboGrp1
        ComComboGrp1_Cards[2] = ComComboGrp1_ch;

        // Set attributes for com FlashCard
        ComComboGrp1_com__.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp1_com__.PNG")), 261, 63);

        ComComboGrp1_com__.setChoices("com__", "__cc__", "__bb__", "ch", "con__", "st");

        ComComboGrp1_com__.setCardName(ComComboGrp1_com__);

        ComComboGrp1_Cards[3] = ComComboGrp1_com__;

        // Set attributes for con__ FlashCard
        ComComboGrp1_con__.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp1_con__.PNG")), 261, 63);

        ComComboGrp1_con__.setChoices("con__", "__cc__", "__bb__", "ch", "com__", "st");

        ComComboGrp1_con__.setCardName(ComComboGrp1_con__);

        ComComboGrp1_Cards[4] = ComComboGrp1_con__;

        // Set attributes for st FlashCard
        ComComboGrp1_st.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp1_st.PNG")), 261, 63);

        ComComboGrp1_st.setChoices("st", "con__", "__cc__", "__bb__", "ch", "com__");

        ComComboGrp1_st.setCardName(ComComboGrp1_st);

        ComComboGrp1_Cards[5] = ComComboGrp1_st;

        // List allows use of shuffle.
        ComComboGrp1_CardsList = Arrays.asList(ComComboGrp1_Cards);

        // END OF abcGRP1 ////////////////////////////////////////////////

        // Set attributes for ble FlashCard
        ComComboGrp2__ble.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp2_ble.PNG")), 261, 63);

        ComComboGrp2__ble.setChoices("_ble", "__dd__", "dis__", " ", "sh", "th");

        ComComboGrp2__ble.setCardName(ComComboGrp2__ble);

        ComComboGrp2_Cards[0] = ComComboGrp2__ble;

        // Set attributes for dd FlashCard
        ComComboGrp2_dd_.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp2_dd.PNG")), 261, 63);

        ComComboGrp2_dd_.setChoices("__dd__", "_ble", "dis__", " ", "sh", "th");

        ComComboGrp2_dd_.setCardName(ComComboGrp2_dd_);

        ComComboGrp2_Cards[1] = ComComboGrp2_dd_;

        // Set attributes for dis FlashCard
        ComComboGrp2_dis_.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp2_dis.PNG")), 261, 63);

        ComComboGrp2_dis_.setChoices("dis__", "_ble", "__dd__", " ", "sh", "th");

        ComComboGrp2_dis_.setCardName(ComComboGrp2_dis_);

        ComComboGrp2_Cards[2] = ComComboGrp2_dis_;

        // Set attributes for sh FlashCard
        ComComboGrp2_sh.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp2_sh.PNG")), 261, 63);

        ComComboGrp2_sh.setChoices("sh", "_ble", "__dd__", "dis__", " ", "th");

        ComComboGrp2_sh.setCardName(ComComboGrp2_sh);

        ComComboGrp2_Cards[3] = ComComboGrp2_sh;

        // Set attributes for th FlashCard
        ComComboGrp2_th.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp2_th.PNG")), 261, 63);

        ComComboGrp2_th.setChoices("th", "_ble", "__dd__", "dis__", " ", "sh");

        ComComboGrp2_th.setCardName(ComComboGrp2_th);

        ComComboGrp2_Cards[4] = ComComboGrp2_th;

        // List allows use of shuffle.
        ComComboGrp2_CardsList = Arrays.asList(ComComboGrp2_Cards);

        //END OF ComComboGrp2 ////////////////////////////////////////////////

        // Set attributes for ar FlashCard
        ComComboGrp3_ar.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp3_ar.PNG")), 261, 63);

        ComComboGrp3_ar.setChoices("ar", "ed", "er", "ing", "ou", "ow");

        ComComboGrp3_ar.setCardName(ComComboGrp3_ar);

        ComComboGrp3_Cards[0] = ComComboGrp3_ar;

        // Set attributes for ed FlashCard
        ComComboGrp3_ed.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp3_ed.PNG")), 261, 63);

        ComComboGrp3_ed.setChoices("ed", "ar", "er", "ing", "ou", "ow");

        ComComboGrp3_ed.setCardName(ComComboGrp3_ed);

        ComComboGrp3_Cards[1] = ComComboGrp3_ed;

        // Set attributes for er FlashCard
        ComComboGrp3_er.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp3_er.PNG")), 261, 63);

        ComComboGrp3_er.setChoices("er", "ar", "ed", "ing", "ou", "ow");

        ComComboGrp3_er.setCardName(ComComboGrp3_er);

        ComComboGrp3_Cards[2] = ComComboGrp3_er;

        // Set attributes for ing FlashCard
        ComComboGrp3_ing.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp3_ing.PNG")), 261, 63);

        ComComboGrp3_ing.setChoices("ing", "ar", "ed", "er", "ou", "ow");

        ComComboGrp3_ing.setCardName(ComComboGrp3_ing);

        ComComboGrp3_Cards[3] = ComComboGrp3_ing;

        // Set attributes for ou FlashCard
        ComComboGrp3_ou.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp3_ou.PNG")), 261, 63);

        ComComboGrp3_ou.setChoices("ou", "ar", "ed", "er", "ing", "ow");

        ComComboGrp3_ou.setCardName(ComComboGrp3_ou);

        ComComboGrp3_Cards[4] = ComComboGrp3_ou;

        // Set attributes for ow FlashCard
        ComComboGrp3_ow.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp3_ow.PNG")), 261, 63);

        ComComboGrp3_ow.setChoices("ow", "ar", "ed", "er", "ing", "ou");

        ComComboGrp3_ow.setCardName(ComComboGrp3_ow);

        ComComboGrp3_Cards[5] = ComComboGrp3_ow;

        // List allows use of shuffle.
        ComComboGrp3_CardsList = Arrays.asList(ComComboGrp3_Cards);

        // END OF ComComboGrp3 ////////////////////////////////////////////////

        // Set attributes for ea FlashCard NOTE SECOND ea First is in group 2

        ComComboGrp4__ea_.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp4__ea__.PNG")), 261, 63);

        ComComboGrp4__ea_.setChoices("__ea__", "en", "__ff__", "__gg__", "gh ", "wh");

        ComComboGrp4__ea_.setCardName(ComComboGrp4__ea_);

        ComComboGrp4_Cards[0] = ComComboGrp4__ea_;

        // Set attributes for en FlashCard
        ComComboGrp4_en.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp4_en.PNG")), 261, 63);

        ComComboGrp4_en.setChoices("en", "__ea__", "__ff__", "__gg__", "gh ", "wh");

        ComComboGrp4_en.setCardName(ComComboGrp4_en);

        ComComboGrp4_Cards[1] = ComComboGrp4_en;

        // Set attributes for ff FlashCard
        ComComboGrp4__ff_.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp4__ff__.PNG")), 261, 63);

        ComComboGrp4__ff_.setChoices("__ff__", "__ea__", "en", "__gg__", "gh ", "wh");

        ComComboGrp4__ff_.setCardName(ComComboGrp4__ff_);

        ComComboGrp4_Cards[2] = ComComboGrp4__ff_;

        // Set attributes for gg FlashCard
        ComComboGrp4__gg_.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp4_gg.PNG")), 261, 63);

        ComComboGrp4__gg_.setChoices("__gg__", "__ea__", "en", "__ff__", "gh ", "wh");

        ComComboGrp4__gg_.setCardName(ComComboGrp4__gg_);

        ComComboGrp4_Cards[3] = ComComboGrp4__gg_;

        // Set attributes for gh FlashCard
        ComComboGrp4_gh.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp4_gh.PNG")), 261, 63);

        ComComboGrp4_gh.setChoices("gh", "__ea__", "en", "__ff__", "__gg__", "wh");

        ComComboGrp4_gh.setCardName(ComComboGrp4_gh);

        ComComboGrp4_Cards[4] = ComComboGrp4_gh;

        // Set attributes for wh FlashCard
        ComComboGrp4_wh.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp4_wh.PNG")), 261, 63);

        ComComboGrp4_wh.setChoices("wh", "gh", "__ea__", "en", "__ff__", "__gg__");

        ComComboGrp4_wh.setCardName(ComComboGrp4_wh);

        ComComboGrp4_Cards[5] = ComComboGrp4_wh;

        // List allows use of shuffle.
        ComComboGrp4_CardsList = Arrays.asList(ComComboGrp4_Cards);

        // END OF ComComboGrp4 ///////////////////////////////////////////////////

        // Set attributes for ation FlashCard
        ComComboGrp5__ation.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp5_ation.PNG")), 261, 63);

        ComComboGrp5__ation.setChoices("__ation", "__ong", "__ound", "__sion", "__tion", " ");

        ComComboGrp5__ation.setCardName(ComComboGrp5__ation);

        ComComboGrp5_Cards[0] = ComComboGrp5__ation;

        // Set attributes for ong FlashCard
        ComComboGrp5__ong.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp5_ong.PNG")), 261, 63);

        ComComboGrp5__ong.setChoices("__ong", "__ation", "__ound", "__sion", "__tion", " ");

        ComComboGrp5__ong.setCardName(ComComboGrp5__ong);

        ComComboGrp5_Cards[1] = ComComboGrp5__ong;

        // Set attributes for ound Point FlashCard
        ComComboGrp5__ound.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp5_ound.PNG")), 261, 63);

        ComComboGrp5__ound.setChoices("__ound", "__ation", "__ong", "__sion", "__tion", " ");

        ComComboGrp5__ound.setCardName(ComComboGrp5__ound);

        ComComboGrp5_Cards[2] = ComComboGrp5__ound;

        // Set attributes for sion FlashCard
        ComComboGrp5__sion.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp5__sion.PNG")), 261, 63);

        ComComboGrp5__sion.setChoices("__sion", "__ation", "__ong", "__ound", "__tion", " ");

        ComComboGrp5__sion.setCardName(ComComboGrp5__sion);

        ComComboGrp5_Cards[3] = ComComboGrp5__sion;

        // Set attributes for tion FlashCard
        ComComboGrp5__tion.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp5__tion.PNG")), 261, 63);

        ComComboGrp5__tion.setChoices("__tion", "__ation", "__ong", "__ound", "__sion", " ");

        ComComboGrp5__tion.setCardName(ComComboGrp5__tion);

        ComComboGrp5_Cards[4] = ComComboGrp5__tion;

        // List allows use of shuffle.
        ComComboGrp5_CardsList = Arrays.asList(ComComboGrp5_Cards);
        // END OF ComComboGrp5 /////////////////////////////////////////////////// 

        // Set attributes for less FlashCard
        ComComboGrp6__less.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp6_less.PNG")), 261, 63);

        ComComboGrp6__less.setChoices("__less", "__ment", "__ness", "__ount", " ", " ");

        ComComboGrp6__less.setCardName(ComComboGrp6__less);

        // First Array element for ComComboGrp6
        ComComboGrp6_Cards[0] = ComComboGrp6__less;

        // Set attributes for ment FlashCard      
        ComComboGrp6__less.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp6__ment.PNG")), 261, 63);

        ComComboGrp6__less.setChoices("__ment", "__less", "__ness", "__ount", " ", " ");

        ComComboGrp6__less.setCardName(ComComboGrp6__less);

        ComComboGrp6_Cards[1] = ComComboGrp6__less;

        // Set attributes for ness FlashCard
        ComComboGrp6__ness.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp6_ness.PNG")), 261, 63);

        ComComboGrp6__ness.setChoices("__ness", "__less", "__ment", "__ount", " ", " ");

        ComComboGrp6__ness.setCardName(ComComboGrp6__ness);

        // First Array element for ComComboGrp6
        ComComboGrp6_Cards[2] = ComComboGrp6__ness;

        // Set attributes for ount FlashCard
        ComComboGrp6__ount.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp6_ount.PNG")), 261, 63);

        ComComboGrp6__ount.setChoices("__ount", "__less", "__ment", "__ness", " ", " ");

        ComComboGrp6__ount.setCardName(ComComboGrp6__ount);

        ComComboGrp6_Cards[3] = ComComboGrp6__ount;

        ComComboGrp6_CardsList = Arrays.asList(ComComboGrp6_Cards);

        // END OF ComComboGrp6 /////////////////////////////////////////////

        // Set attributes for ally FlashCard
        ComComboGrp7_ally.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp7_ally.PNG")), 261, 63);

        ComComboGrp7_ally.setChoices("__ally", "__ance", "__ence", "__ful", "__ity", "");

        ComComboGrp7_ally.setCardName(ComComboGrp7_ally);

        ComComboGrp7_Cards[0] = ComComboGrp7_ally;

        // Set attributes for ance FlashCard
        ComComboGrp7__ance.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp7_ance.PNG")), 261, 63);

        ComComboGrp7__ance.setChoices("__ance", "__ally", "__ence", "__ful", "__ity", "");

        ComComboGrp7__ance.setCardName(ComComboGrp7__ance);

        ComComboGrp7_Cards[1] = ComComboGrp7__ance;

        // Set attributes for ence FlashCard
        ComComboGrp7__ence.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp7__ence.PNG")), 261, 63);

        ComComboGrp7__ence.setChoices("__ence", "__ally", "__ance", "__ful", "__ity", "");

        ComComboGrp7__ence.setCardName(ComComboGrp7__ence);

        ComComboGrp7_Cards[2] = ComComboGrp7__ence;

        // Set attributes for ful FlashCard
        ComComboGrp7__ful.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp7_ful.PNG")), 261, 63);

        ComComboGrp7__ful.setChoices("__ful", "__ally", "__ance", "__ence", "__ity", "");

        ComComboGrp7__ful.setCardName(ComComboGrp7__ful);

        ComComboGrp7_Cards[3] = ComComboGrp7__ful;

        // Set attributes for ity FlashCard
        ComComboGrp7_ity.setFlashCardImg(new ImageIcon(getClass().getResource("ComComboGrp7_ity.PNG")), 261, 63);

        ComComboGrp7_ity.setChoices("__ity", "__ally", "__ance", "__ence", "__ful", "");

        ComComboGrp7_ity.setCardName(ComComboGrp7_ity);

        ComComboGrp7_Cards[4] = ComComboGrp7_ity;

        // List allows use of shuffle.
        ComComboGrp7_CardsList = Arrays.asList(ComComboGrp7_Cards);

        // END OF ComComboGrp7 ////////////////////////////////////////////////    
    }

    public List<FlashCard> initWrdGrp1(){
        // WrdGrp1
        FlashCard WrdGrp1_like = new FlashCard();
        FlashCard WrdGrp1_more = new FlashCard();
        FlashCard WrdGrp1_shall = new FlashCard();
        FlashCard WrdGrp1_to = new FlashCard();
        FlashCard WrdGrp1_us = new FlashCard();
        FlashCard WrdGrp1_which = new FlashCard();
        // Array and list object for WrdGrp1
        FlashCard[] WrdGrp1_Cards = new FlashCard[6];
        // List allows use of shuffle.
        //List<FlashCard> WrdGrp1_CardsList;

        List<FlashCard> WrdGrp1_CardsList;
        
        // Start of WrdGrp1
        // Set attributes for like FlashCard
        WrdGrp1_like.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp1_like.PNG")), 261, 63);

        WrdGrp1_like.setChoices("like", "more", "shall", "to", "us", "which");

        WrdGrp1_like.setCardName(WrdGrp1_like);

        WrdGrp1_Cards[0] = WrdGrp1_like;

        // Set attributes for more FlashCard
        WrdGrp1_more.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp1_more.PNG")), 261, 63);

        WrdGrp1_more.setChoices("more", "like", "shall", "to", "us", "which");

        WrdGrp1_more.setCardName(WrdGrp1_more);

        WrdGrp1_Cards[1] = WrdGrp1_more;

        // Set attributes for shall FlashCard
        WrdGrp1_shall.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp1_shall.PNG")), 261, 63);

        WrdGrp1_shall.setChoices("shall", "more", "like", "to", "us", "which");

        WrdGrp1_shall.setCardName(WrdGrp1_shall);

        WrdGrp1_Cards[2] = WrdGrp1_shall;

        // Set attributes for to FlashCard
        WrdGrp1_to.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp1_to.PNG")), 261, 63);

        WrdGrp1_to.setChoices("to", "shall", "more", "like", "us", "which");

        WrdGrp1_to.setCardName(WrdGrp1_to);

        WrdGrp1_Cards[3] = WrdGrp1_to;

        // Set attributes for us FlashCard
        WrdGrp1_us.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp1_us.PNG")), 261, 63);

        WrdGrp1_us.setChoices("us", "to", "shall", "more", "like", "which");

        WrdGrp1_us.setCardName(WrdGrp1_us);

        WrdGrp1_Cards[4] = WrdGrp1_us;

        // Set attributes for which FlashCard
        WrdGrp1_which.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp1_which.PNG")), 261, 63);

        WrdGrp1_which.setChoices("which", "us", "to", "shall", "more", "like");

        WrdGrp1_which.setCardName(WrdGrp1_which);

        WrdGrp1_Cards[5] = WrdGrp1_which;

        // List allows use of shuffle.
        WrdGrp1_CardsList = Arrays.asList(WrdGrp1_Cards);
        // END OF WrdGrp1
        
        return  WrdGrp1_CardsList;
    }
    
    public List<FlashCard> initWrdGrp2(){
        
        // WrdGrp2
        FlashCard WrdGrp2_from = new FlashCard();
        FlashCard WrdGrp2_have = new FlashCard();
        FlashCard WrdGrp2_his = new FlashCard();
        FlashCard WrdGrp2_just = new FlashCard();
        FlashCard WrdGrp2_so = new FlashCard();
        FlashCard WrdGrp2_was = new FlashCard();
        // Array and list object for WrdGrp2
        FlashCard[] WrdGrp2_Cards = new FlashCard[6];
        // List allows use of shuffle.
        List<FlashCard> WrdGrp2_CardsList;       
        
        // Start of WrdGrp2
        // Set attributes for from FlashCard
        WrdGrp2_from.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp2_from.PNG")), 261, 63);

        WrdGrp2_from.setChoices("from", "have", "his", "just", "so", "was");

        WrdGrp2_from.setCardName(WrdGrp2_from);

        WrdGrp2_Cards[0] = WrdGrp2_from;

        // Set attributes for have FlashCard
        WrdGrp2_have.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp2_have.PNG")), 261, 63);

        WrdGrp2_have.setChoices("have", "from", "his", "just", "so", "was");

        WrdGrp2_have.setCardName(WrdGrp2_have);

        WrdGrp2_Cards[1] = WrdGrp2_have;

        // Set attributes for his FlashCard
        WrdGrp2_his.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp2_his.PNG")), 261, 63);

        WrdGrp2_his.setChoices("his", "from", "have", "just", "so", "was");

        WrdGrp2_his.setCardName(WrdGrp2_his);

        WrdGrp2_Cards[2] = WrdGrp2_his;

        // Set attributes for just FlashCard
        WrdGrp2_just.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp2_just.PNG")), 261, 63);

        WrdGrp2_just.setChoices("just", "from", "have", "his", "so", "was");

        WrdGrp2_just.setCardName(WrdGrp2_just);

        WrdGrp2_Cards[3] = WrdGrp2_just;

        // Set attributes for so FlashCard
        WrdGrp2_so.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp2_so.PNG")), 261, 63);

        WrdGrp2_so.setChoices("so", "from", "have", "his", "just", "was");

        WrdGrp2_so.setCardName(WrdGrp2_so);

        WrdGrp2_Cards[4] = WrdGrp2_so;

        // Set attributes for was FlashCard
        WrdGrp2_was.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp2_was.PNG")), 261, 63);

        WrdGrp2_was.setChoices("was", "from", "have", "his", "just", "so");

        WrdGrp2_was.setCardName(WrdGrp2_was);

        WrdGrp2_Cards[5] = WrdGrp2_was;

        // List allows use of shuffle.
        WrdGrp2_CardsList = Arrays.asList(WrdGrp2_Cards);
        // END OF WrdGrp2 
        
        return WrdGrp2_CardsList;
    }
    
    public List<FlashCard> initWrdGrp3(){
        
        // WrdGrp3
        FlashCard WrdGrp3_child = new FlashCard();
        FlashCard WrdGrp3_enough = new FlashCard();
        FlashCard WrdGrp3_every = new FlashCard();
        FlashCard WrdGrp3_in = new FlashCard();
        FlashCard WrdGrp3_still = new FlashCard();
        // Array and list object for WrdGrp3
        FlashCard[] WrdGrp3_Cards = new FlashCard[5];
        // List allows use of shuffle.
        List<FlashCard> WrdGrp3_CardsList;
        
                // Set attributes for child FlashCard
        WrdGrp3_child.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp3_child.PNG")), 261, 63);

        WrdGrp3_child.setChoices("child", "enough", "every", "in", "still", " ");

        WrdGrp3_child.setCardName(WrdGrp3_child);

        WrdGrp3_Cards[0] = WrdGrp3_child;

        // Set attributes for enough FlashCard
        WrdGrp3_enough.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp3_enough.PNG")), 261, 63);

        WrdGrp3_enough.setChoices("enough", "child", "every", "in", "still", " ");

        WrdGrp3_enough.setCardName(WrdGrp3_enough);

        WrdGrp3_Cards[1] = WrdGrp3_enough;

        // Set attributes for every FlashCard
        WrdGrp3_every.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp3_every.PNG")), 261, 63);

        WrdGrp3_every.setChoices("every", "child", "enough", "in", "still", " ");

        WrdGrp3_every.setCardName(WrdGrp3_every);

        WrdGrp3_Cards[2] = WrdGrp3_every;

        // Set attributes for in FlashCard
        WrdGrp3_in.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp3_in.PNG")), 261, 63);

        WrdGrp3_in.setChoices("in", "child", "enough", "every", "still", " ");

        WrdGrp3_in.setCardName(WrdGrp3_in);

        WrdGrp3_Cards[3] = WrdGrp3_in;

        // Set attributes for still FlashCard
        WrdGrp3_still.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp3_still.PNG")), 261, 63);

        WrdGrp3_still.setChoices("still", "child", "enough", "every", "in", " ");

        WrdGrp3_still.setCardName(WrdGrp3_still);

        WrdGrp3_Cards[4] = WrdGrp3_still;

        // List allows use of shuffle.
        WrdGrp3_CardsList = Arrays.asList(WrdGrp3_Cards);
        // END OF WrdGrp3 
        
        return WrdGrp3_CardsList;
    }
     
    public List<FlashCard> initWrdGrp4(){
        
        // WrdGrp4
        FlashCard WrdGrp4_be = new FlashCard();
        FlashCard WrdGrp4_but = new FlashCard();
        FlashCard WrdGrp4_by = new FlashCard();
        FlashCard WrdGrp4_can = new FlashCard();
        FlashCard WrdGrp4_do = new FlashCard();
        FlashCard WrdGrp4_knowledge = new FlashCard();
        // Array and list object for WrdGrp26
        FlashCard[] WrdGrp4_Cards = new FlashCard[6];
        // List allows use of shuffle.
        List<FlashCard> WrdGrp4_CardsList;
        
                // Start of WrdGrp4
        // Set attributes for be FlashCard
        WrdGrp4_be.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp4_be.PNG")), 261, 63);

        WrdGrp4_be.setChoices("be", "but", "by", "can", "do", "knowledge");

        WrdGrp4_be.setCardName(WrdGrp4_be);

        WrdGrp4_Cards[0] = WrdGrp4_be;

        // Set attributes for but FlashCard
        WrdGrp4_but.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp4_but.PNG")), 261, 63);

        WrdGrp4_but.setChoices("but", "be", "by", "can", "do", "knowledge");

        WrdGrp4_but.setCardName(WrdGrp4_but);

        WrdGrp4_Cards[1] = WrdGrp4_but;

        // Set attributes for by FlashCard
        WrdGrp4_by.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp4_by.PNG")), 261, 63);

        WrdGrp4_by.setChoices("by", "be", "but", "can", "do", "knowledge");

        WrdGrp4_by.setCardName(WrdGrp4_by);

        WrdGrp4_Cards[2] = WrdGrp4_by;

        // Set attributes for can FlashCard
        WrdGrp4_can.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp4_can.PNG")), 261, 63);

        WrdGrp4_can.setChoices("can", "be", "but", "by", "do", "knowledge");

        WrdGrp4_can.setCardName(WrdGrp4_can);

        WrdGrp4_Cards[3] = WrdGrp4_can;

        // Set attributes for do FlashCard
        WrdGrp4_do.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp4_do.PNG")), 261, 63);

        WrdGrp4_do.setChoices("do", "be", "but", "by", "can", "knowledge");

        WrdGrp4_do.setCardName(WrdGrp4_do);

        WrdGrp4_Cards[4] = WrdGrp4_do;

        // Set attributes for knowledge FlashCard
        WrdGrp4_knowledge.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp4_knowledge.PNG")), 261, 63);

        WrdGrp4_knowledge.setChoices("knowledge", "be", "but", "by", "can", "do");

        WrdGrp4_knowledge.setCardName(WrdGrp4_knowledge);

        WrdGrp4_Cards[5] = WrdGrp4_knowledge;

        // List allows use of shuffle.
        WrdGrp4_CardsList = Arrays.asList(WrdGrp4_Cards);
        // END OF WrdGrp4  
        
        return WrdGrp4_CardsList;
    }
     
    public List<FlashCard> initWrdGrp5(){

        // WrdGrp5
        FlashCard WrdGrp5_go = new FlashCard();
        FlashCard WrdGrp5_not = new FlashCard();
        FlashCard WrdGrp5_out = new FlashCard();
        FlashCard WrdGrp5_rather = new FlashCard();
        FlashCard WrdGrp5_this = new FlashCard();
        FlashCard WrdGrp5_were = new FlashCard();
        // Array and list object for WrdGrp5
        FlashCard[] WrdGrp5_Cards = new FlashCard[6];
        // List allows use of shuffle.
        List<FlashCard> WrdGrp5_CardsList;
        
                // Start of WrdGrp5
        // Set attributes for go FlashCard
        WrdGrp5_go.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp5_go.PNG")), 261, 63);

        WrdGrp5_go.setChoices("go", "not", "out", "rather", "this", "were");

        WrdGrp5_go.setCardName(WrdGrp5_go);

        WrdGrp5_Cards[0] = WrdGrp5_go;

        // Set attributes for not FlashCard
        WrdGrp5_not.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp5_not.PNG")), 261, 63);

        WrdGrp5_not.setChoices("not", "go", "out", "rather", "this", "were");

        WrdGrp5_not.setCardName(WrdGrp5_not);

        WrdGrp5_Cards[1] = WrdGrp5_not;

        // Set attributes for out FlashCard
        WrdGrp5_out.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp5_out.PNG")), 261, 63);

        WrdGrp5_out.setChoices("out", "go", "not", "rather", "this", "were");

        WrdGrp5_out.setCardName(WrdGrp5_out);

        WrdGrp5_Cards[2] = WrdGrp5_out;

        // Set attributes for rather FlashCard
        WrdGrp5_rather.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp5_rather.PNG")), 261, 63);

        WrdGrp5_rather.setChoices("rather", "go", "not", "out", "this", "were");

        WrdGrp5_rather.setCardName(WrdGrp5_rather);

        WrdGrp5_Cards[3] = WrdGrp5_rather;

        // Set attributes for this FlashCard
        WrdGrp5_this.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp5_this.PNG")), 261, 63);

        WrdGrp5_this.setChoices("this", "rather", "go", "not", "out", "were");

        WrdGrp5_this.setCardName(WrdGrp5_this);

        WrdGrp5_Cards[4] = WrdGrp5_this;

        // Set attributes for were FlashCard
        WrdGrp5_were.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp5_were.PNG")), 261, 63);

        WrdGrp5_were.setChoices("were", "go", "not", "out", "rather", "this");

        WrdGrp5_were.setCardName(WrdGrp5_were);

        WrdGrp5_Cards[5] = WrdGrp5_were;

        // List allows use of shuffle.
        WrdGrp5_CardsList = Arrays.asList(WrdGrp5_Cards);
        // END OF WrdGrp5
        
        return WrdGrp5_CardsList;
    }
     
    public List<FlashCard> initWrdGrp6(){    
    
        // WrdGrp6
        FlashCard WrdGrp6_as = new FlashCard();
        FlashCard WrdGrp6_it = new FlashCard();
        FlashCard WrdGrp6_people = new FlashCard();
        FlashCard WrdGrp6_that = new FlashCard();
        FlashCard WrdGrp6_very = new FlashCard();
        FlashCard WrdGrp6_will = new FlashCard();
        // Array and list object for WrdGrp6
        FlashCard[] WrdGrp6_Cards = new FlashCard[6];
        // List allows use of shuffle.
        List<FlashCard> WrdGrp6_CardsList;
        
                // Set attributes for as FlashCard
        WrdGrp6_as.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp6_as.PNG")), 261, 63);

        WrdGrp6_as.setChoices("as", "it", "people", "that", "very", "will");

        WrdGrp6_as.setCardName(WrdGrp6_as);

        WrdGrp6_Cards[0] = WrdGrp6_as;

        // Set attributes for it FlashCard
        WrdGrp6_it.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp6_it.PNG")), 261, 63);

        WrdGrp6_it.setChoices("it", "as", "people", "that", "very", "will");

        WrdGrp6_it.setCardName(WrdGrp6_it);

        WrdGrp6_Cards[1] = WrdGrp6_it;

        // Set attributes for people FlashCard
        WrdGrp6_people.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp6_people.PNG")), 261, 63);

        WrdGrp6_people.setChoices("people", "as", "it", "that", "very", "will");

        WrdGrp6_people.setCardName(WrdGrp6_people);

        WrdGrp6_Cards[2] = WrdGrp6_people;

        // Set attributes for that FlashCard
        WrdGrp6_that.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp6_that.PNG")), 261, 63);

        WrdGrp6_that.setChoices("that", "as", "it", "people", "very", "will");

        WrdGrp6_that.setCardName(WrdGrp6_that);

        WrdGrp6_Cards[3] = WrdGrp6_that;

        // Set attributes for gh FlashCard
        WrdGrp6_very.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp6_very.PNG")), 261, 63);

        WrdGrp6_very.setChoices("very", "as", "it", "people", "that", "will");

        WrdGrp6_very.setCardName(WrdGrp6_very);

        WrdGrp6_Cards[4] = WrdGrp6_very;

        // Set attributes for gh FlashCard
        WrdGrp6_will.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp6_will.PNG")), 261, 63);

        WrdGrp6_will.setChoices( "will", "as", "it", "people", "that", "very" );

        WrdGrp6_will.setCardName(WrdGrp6_will);

        WrdGrp6_Cards[5] = WrdGrp6_will;

        WrdGrp6_CardsList = Arrays.asList(WrdGrp6_Cards);
        // END OF WrdGrp6
        
        return WrdGrp6_CardsList;
    }
    
    public List<FlashCard> initWrdGrp7(){
        // WrdGrp7
        FlashCard WrdGrp7_and = new FlashCard();
        FlashCard WrdGrp7_for = new FlashCard();
        FlashCard WrdGrp7_of = new FlashCard();
        FlashCard WrdGrp7_quite = new FlashCard();
        FlashCard WrdGrp7_with = new FlashCard();
        FlashCard WrdGrp7_you = new FlashCard();
        // Array and list object for WrdGrp7
        FlashCard[] WrdGrp7_Cards = new FlashCard[6];
        // List allows use of shuffle.
        List<FlashCard> WrdGrp7_CardsList;
        
                // Set attributes for and FlashCard
        WrdGrp7_and.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp7_and.PNG")), 261, 63);

        WrdGrp7_and.setChoices("and", "for", "of", "quite", "with", "you");

        WrdGrp7_and.setCardName(WrdGrp7_and);

        WrdGrp7_Cards[0] = WrdGrp7_and;

        // Set attributes for en FlashCard
        WrdGrp7_for.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp7_for.PNG")), 261, 63);

        WrdGrp7_for.setChoices("for", "and", "of", "quite", "with", "you");

        WrdGrp7_for.setCardName(WrdGrp7_for);

        WrdGrp7_Cards[1] = WrdGrp7_for;

        // Set attributes for ff FlashCard
        WrdGrp7_of.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp7_of.PNG")), 261, 63);

        WrdGrp7_of.setChoices("of", "and", "for", "quite", "with", "you");

        WrdGrp7_of.setCardName(WrdGrp7_of);

        WrdGrp7_Cards[2] = WrdGrp7_of;

        // Set attributes for with FlashCard
        WrdGrp7_with.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp7_with.PNG")), 261, 63);

        WrdGrp7_with.setChoices("with", "and", "for", "of", "quite", "you");

        WrdGrp7_with.setCardName(WrdGrp7_with);

        WrdGrp7_Cards[3] = WrdGrp7_with;

        // Set attributes for quite FlashCard
        WrdGrp7_quite.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp7_quite.PNG")), 261, 63);

        WrdGrp7_quite.setChoices("quite", "and", "for", "of", "with", "you");

        WrdGrp7_quite.setCardName(WrdGrp7_quite);

        WrdGrp7_Cards[4] = WrdGrp7_quite;

        // Set attributes for you FlashCard
        WrdGrp7_you.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp7_you.PNG")), 261, 63);

        WrdGrp7_you.setChoices("you", "and", "for", "of", "quite", "with");

        WrdGrp7_you.setCardName(WrdGrp7_you);

        WrdGrp7_Cards[5] = WrdGrp7_you;

        // List allows use of shuffle.
        WrdGrp7_CardsList = Arrays.asList(WrdGrp7_Cards);
        // END OF WrdGrp7
        
        return WrdGrp7_CardsList;
    }
   
    public List<FlashCard> initWrdGrp8(){

        // WrdGrp8
        FlashCard WrdGrp8_about = new FlashCard();
        FlashCard WrdGrp8_according = new FlashCard();
        FlashCard WrdGrp8_character = new FlashCard();
        FlashCard WrdGrp8_ever = new FlashCard();
        FlashCard WrdGrp8_know = new FlashCard();
        // Array and list object for WrdGrp8
        FlashCard[] WrdGrp8_Cards = new FlashCard[5];
        // List allows use of shuffle.
        List<FlashCard> WrdGrp8_CardsList;
        
                // Set attributes for about FlashCard
        WrdGrp8_about.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp8_about.PNG")), 261, 63);
 
        WrdGrp8_about.setChoices("about", "according", "character", "ever", "know", " ");
        
        WrdGrp8_about.setCardName(WrdGrp8_about);

        WrdGrp8_Cards[0] = WrdGrp8_about;
        
        // Set attributes for en FlashCard
        WrdGrp8_according.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp8_according.PNG")), 261, 63);
 
        WrdGrp8_according.setChoices("according", "about", "character", "ever", "know", " ");
        
        WrdGrp8_according.setCardName(WrdGrp8_according);

        WrdGrp8_Cards[1] = WrdGrp8_according;
        
        // Set attributes for character FlashCard
        WrdGrp8_character.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp8_character.PNG")), 261, 63);
 
        WrdGrp8_character.setChoices( "character", "about", "according", "ever", "know", " " );
        
        WrdGrp8_character.setCardName(WrdGrp8_character);

        WrdGrp8_Cards[2] = WrdGrp8_character;  
        
        // Set attributes for ever FlashCard
        WrdGrp8_ever.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp8_ever.PNG")), 261, 63);
 
        WrdGrp8_ever.setChoices( "ever", "about", "according", "character", "know", " " );
        
        WrdGrp8_ever.setCardName(WrdGrp8_ever);

        WrdGrp8_Cards[3] = WrdGrp8_ever;     
        
        // Set attributes for know FlashCard
        WrdGrp8_know.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp8_know.PNG")), 261, 63);
 
        WrdGrp8_know.setChoices( "know", "about", "according", "character", "ever", " " );
        
        WrdGrp8_know.setCardName(WrdGrp8_know);

        WrdGrp8_Cards[4] = WrdGrp8_know;         
        
        // List allows use of shuffle.
        WrdGrp8_CardsList = Arrays.asList(WrdGrp8_Cards); 
        // END OF WrdGrp8 
        
        return WrdGrp8_CardsList;
    }
    
    public List<FlashCard> initWrdGrp9(){

        // WrdGrp9
        FlashCard WrdGrp9_because = new FlashCard();
        FlashCard WrdGrp9_beneath = new FlashCard();
        FlashCard WrdGrp9_beside = new FlashCard();
        FlashCard WrdGrp9_between = new FlashCard();
        FlashCard WrdGrp9_beyond = new FlashCard();
        FlashCard WrdGrp9_blind = new FlashCard();
        // Array and list object for WrdGrp9
        FlashCard[] WrdGrp9_Cards = new FlashCard[6];
        // List allows use of shuffle.
        List<FlashCard> WrdGrp9_CardsList;
        
                // Set attributes for because FlashCard
        WrdGrp9_because.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp9_because.PNG")), 261, 63);
 
        WrdGrp9_because.setChoices("because", "beneath", "beside", "between", "beyond", "blind");
        
        WrdGrp9_because.setCardName(WrdGrp9_because);

        WrdGrp9_Cards[0] = WrdGrp9_because;
        
        // Set attributes for beneath FlashCard
        WrdGrp9_beneath.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp9_beneath.PNG")), 261, 63);
 
        WrdGrp9_beneath.setChoices("beneath", "because", "beside", "between", "beyond", "blind");
        
        WrdGrp9_beneath.setCardName(WrdGrp9_beneath);

        WrdGrp9_Cards[1] = WrdGrp9_beneath;
        
        // Set attributes for beside FlashCard
        WrdGrp9_beside.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp9_beside.PNG")), 261, 63);
 
        WrdGrp9_beside.setChoices( "beside", "because", "beneath", "between", "beyond", "blind");
        
        WrdGrp9_beside.setCardName(WrdGrp9_beside);

        WrdGrp9_Cards[2] = WrdGrp9_beside;  
        
        // Set attributes for between FlashCard
        WrdGrp9_between.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp9_between.PNG")), 261, 63);
 
        WrdGrp9_between.setChoices("between", "because", "beneath", "beside", "beyond", "blind");
        
        WrdGrp9_between.setCardName(WrdGrp9_between);

        WrdGrp9_Cards[3] = WrdGrp9_between;         
        
        // Set attributes for beyond FlashCard
        WrdGrp9_beyond.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp9_beyond.PNG")), 261, 63);
 
        WrdGrp9_beyond.setChoices("beyond", "because", "beneath", "beside", "between", "blind");
        
        WrdGrp9_beyond.setCardName(WrdGrp9_beyond);

        WrdGrp9_Cards[4] = WrdGrp9_beyond;  
        
        // Set attributes for blind FlashCard
        WrdGrp9_blind.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp9_blind.PNG")), 261, 63);
 
        WrdGrp9_blind.setChoices("blind", "because", "beneath", "beside", "between", "beyond");
        
        WrdGrp9_blind.setCardName(WrdGrp9_blind);

        WrdGrp9_Cards[5] = WrdGrp9_blind;          
        
        // List allows use of shuffle.
        WrdGrp9_CardsList = Arrays.asList(WrdGrp9_Cards); 
        // END OF WrdGrp9 
        
        return WrdGrp9_CardsList;
    }
 
    public List<FlashCard> initWrdGrp10(){
        
        // WrdGrp10
        FlashCard WrdGrp10_after = new FlashCard();
        FlashCard WrdGrp10_again = new FlashCard();
        FlashCard WrdGrp10_here = new FlashCard();
        FlashCard WrdGrp10_mother = new FlashCard();
        FlashCard WrdGrp10_one = new FlashCard();
        FlashCard WrdGrp10_some = new FlashCard();
        // Array and list object for WrdGrp10
        FlashCard[] WrdGrp10_Cards = new FlashCard[6];
        // List allows use of shuffle.
        List<FlashCard> WrdGrp10_CardsList;
        
                // Set attributes for after FlashCard
        WrdGrp10_after.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp10_after.PNG")), 261, 63);
 
        WrdGrp10_after.setChoices("after", "again", "here", "mother", "one", "some");
        
        WrdGrp10_after.setCardName(WrdGrp10_after);

        WrdGrp10_Cards[0] = WrdGrp10_after;
        
        // Set attributes for again FlashCard
        WrdGrp10_again.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp10_again.PNG")), 261, 63);
 
        WrdGrp10_again.setChoices("again", "after", "here", "mother", "one", "some");
        
        WrdGrp10_again.setCardName(WrdGrp10_again);

        WrdGrp10_Cards[1] = WrdGrp10_again;
        
        // Set attributes for here FlashCard
        WrdGrp10_here.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp10_here.PNG")), 261, 63);
 
        WrdGrp10_here.setChoices("here", "after", "again", "mother", "one", "some" );
        
        WrdGrp10_here.setCardName(WrdGrp10_here);

        WrdGrp10_Cards[2] = WrdGrp10_here;  
        
        // Set attributes for mother FlashCard
        WrdGrp10_mother.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp10_mother.PNG")), 261, 63);
 
        WrdGrp10_mother.setChoices("mother", "after", "again", "here", "one", "some");
        
        WrdGrp10_mother.setCardName(WrdGrp10_mother);

        WrdGrp10_Cards[3] = WrdGrp10_mother;         
        
        // Set attributes for one FlashCard
        WrdGrp10_one.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp10_one.PNG")), 261, 63);
 
        WrdGrp10_one.setChoices("one", "after", "again", "here", "mother", "some");
        
        WrdGrp10_one.setCardName(WrdGrp10_one);

        WrdGrp10_Cards[4] = WrdGrp10_one;  
        
        // Set attributes for some FlashCard
        WrdGrp10_some.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp10_some.PNG")), 261, 63);
 
        WrdGrp10_some.setChoices( "some", "after", "again", "here", "mother", "one");
        
        WrdGrp10_some.setCardName(WrdGrp10_some);

        WrdGrp10_Cards[5] = WrdGrp10_some;          
        
        // List allows use of shuffle.
        WrdGrp10_CardsList = Arrays.asList(WrdGrp10_Cards); 
        // END OF WrdGrp10  
        
        return WrdGrp10_CardsList;
    }
   
    public List<FlashCard> initWrdGrp11(){

        // WrdGrp11
        FlashCard WrdGrp11_cannot = new FlashCard();
        FlashCard WrdGrp11_first = new FlashCard();
        FlashCard WrdGrp11_into = new FlashCard();
        FlashCard WrdGrp11_much = new FlashCard();
        FlashCard WrdGrp11_must = new FlashCard();
        FlashCard WrdGrp11_such = new FlashCard();
        // Array and list object for WrdGrp11
        FlashCard[] WrdGrp11_Cards = new FlashCard[6];
        // List allows use of shuffle.
        List<FlashCard> WrdGrp11_CardsList;

                // Set attributes for cannot FlashCard
        WrdGrp11_cannot.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp11_cannot.PNG")), 261, 63);
 
        WrdGrp11_cannot.setChoices("cannot", "first", "into", "much", "must", "such");
        
        WrdGrp11_cannot.setCardName(WrdGrp11_cannot);

        WrdGrp11_Cards[0] = WrdGrp11_cannot;
        
        // Set attributes for first FlashCard
        WrdGrp11_first.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp11_first.PNG")), 261, 63);
 
        WrdGrp11_first.setChoices("first", "cannot", "into", "much", "must", "such");
        
        WrdGrp11_first.setCardName(WrdGrp11_first);

        WrdGrp11_Cards[1] = WrdGrp11_first;
        
        // Set attributes into into FlashCard
        WrdGrp11_into.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp11_into.PNG")), 261, 63);
 
        WrdGrp11_into.setChoices( "into", "cannot", "first", "much", "must", "such" );
        
        WrdGrp11_into.setCardName(WrdGrp11_into);

        WrdGrp11_Cards[2] = WrdGrp11_into;  
        
        // Set attributes for much FlashCard
        WrdGrp11_much.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp11_much.PNG")), 261, 63);
 
        WrdGrp11_much.setChoices( "much", "cannot", "first", "into", "must", "such" );
        
        WrdGrp11_much.setCardName(WrdGrp11_much);

        WrdGrp11_Cards[3] = WrdGrp11_much;     
        
        // Set attributes for must FlashCard
        WrdGrp11_must.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp11_must.PNG")), 261, 63);
 
        WrdGrp11_must.setChoices( "must", "cannot", "first", "into", "much", "such" );
        
        WrdGrp11_must.setCardName(WrdGrp11_must);

        WrdGrp11_Cards[4] = WrdGrp11_must;
        
        // Set attributes for such FlashCard
        WrdGrp11_such.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp11_such.PNG")), 261, 63);
 
        WrdGrp11_such.setChoices( "such", "must", "cannot", "first", "into", "much" );
        
        WrdGrp11_such.setCardName(WrdGrp11_such);

        WrdGrp11_Cards[5] = WrdGrp11_such; 
        
        // List allows use of shuffle.
        WrdGrp11_CardsList = Arrays.asList(WrdGrp11_Cards); 
        // END OF WrdGrp11
        
        return WrdGrp11_CardsList;
    }
    
    public List<FlashCard> initWrdGrp12(){
        
        // WrdGrp12
        FlashCard WrdGrp12_children = new FlashCard();
        FlashCard WrdGrp12_could = new FlashCard();
        FlashCard WrdGrp12_either = new FlashCard();
        FlashCard WrdGrp12_these = new FlashCard();
        FlashCard WrdGrp12_those = new FlashCard();
        FlashCard WrdGrp12_word = new FlashCard();
        // Array and list object for WrdGrp12
        FlashCard[] WrdGrp12_Cards = new FlashCard[6];
        // List allows use of shuffle.
        List<FlashCard> WrdGrp12_CardsList;
        
                // Set attributes for children FlashCard
        WrdGrp12_children.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp12_children.PNG")), 261, 63);
 
        WrdGrp12_children.setChoices("children", "could", "either", "these", "those", "word");
        
        WrdGrp12_children.setCardName(WrdGrp12_children);

        WrdGrp12_Cards[0] = WrdGrp12_children;
        
        // Set attributes for could FlashCard
        WrdGrp12_could.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp12_could.PNG")), 261, 63);
 
        WrdGrp12_could.setChoices("could", "children", "either", "these", "those", "word");
        
        WrdGrp12_could.setCardName(WrdGrp12_could);

        WrdGrp12_Cards[1] = WrdGrp12_could;
        
        // Set attributes for either FlashCard
        WrdGrp12_either.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp12_either.PNG")), 261, 63);
 
        WrdGrp12_either.setChoices( "either", "children", "could", "these", "those", "word");
        
        WrdGrp12_either.setCardName(WrdGrp12_either);

        WrdGrp12_Cards[2] = WrdGrp12_either;  
        
        // Set attributes for these FlashCard
        WrdGrp12_these.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp12_these.PNG")), 261, 63);
 
        WrdGrp12_these.setChoices("these", "children", "could", "either", "those", "word");
        
        WrdGrp12_these.setCardName(WrdGrp12_these);

        WrdGrp12_Cards[3] = WrdGrp12_these;         
        
        // Set attributes for those FlashCard
        WrdGrp12_those.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp12_those.PNG")), 261, 63);
 
        WrdGrp12_those.setChoices("those", "children", "could", "either", "these", "word");
        
        WrdGrp12_those.setCardName(WrdGrp12_those);

        WrdGrp12_Cards[4] = WrdGrp12_those;  
        
        // Set attributes for word FlashCard
        WrdGrp12_word.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp12_word.PNG")), 261, 63);
 
        WrdGrp12_word.setChoices( "word", "children", "could", "either", "these", "those" );
        
        WrdGrp12_word.setCardName(WrdGrp12_word);

        WrdGrp12_Cards[5] = WrdGrp12_word;          
        
        // List allows use of shuffle.
        WrdGrp12_CardsList = Arrays.asList(WrdGrp12_Cards); 
        // END OF WrdGrp12         
        
        return WrdGrp12_CardsList;
    }
     
    public List<FlashCard> initWrdGrp13(){
        
        // WrdGrp13
        FlashCard WrdGrp13_also = new FlashCard();
        FlashCard WrdGrp13_before = new FlashCard();
        FlashCard WrdGrp13_behind = new FlashCard();
        FlashCard WrdGrp13_below = new FlashCard();
        FlashCard WrdGrp13_day = new FlashCard();
        FlashCard WrdGrp13_father = new FlashCard();
        // Array and list object for WrdGrp13
        FlashCard[] WrdGrp13_Cards = new FlashCard[6];
        // List allows use of shuffle.
        List<FlashCard> WrdGrp13_CardsList;
      
        // Set attributes for also FlashCard
        WrdGrp13_also.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp13_also.PNG")), 261, 63);
 
        WrdGrp13_also.setChoices("also", "before", "behind", "below", "day", "father");
        
        WrdGrp13_also.setCardName(WrdGrp13_also);

        WrdGrp13_Cards[0] = WrdGrp13_also;
        
        // Set attributes for before FlashCard
        WrdGrp13_before.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp13_before.PNG")), 261, 63);
 
        WrdGrp13_before.setChoices("before", "also", "behind", "below", "day", "father");
        
        WrdGrp13_before.setCardName(WrdGrp13_before);

        WrdGrp13_Cards[1] = WrdGrp13_before;
        
        // Set attributes for behind FlashCard
        WrdGrp13_behind.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp13_behind.PNG")), 261, 63);
 
        WrdGrp13_behind.setChoices("behind", "also", "before", "below", "day", "father" );
        
        WrdGrp13_behind.setCardName(WrdGrp13_behind);

        WrdGrp13_Cards[2] = WrdGrp13_behind;  
        
        // Set attributes for below FlashCard
        WrdGrp13_below.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp13_below.PNG")), 261, 63);
 
        WrdGrp13_below.setChoices("below", "also", "before", "behind", "day", "father");
        
        WrdGrp13_below.setCardName(WrdGrp13_below);

        WrdGrp13_Cards[3] = WrdGrp13_below;         
        
        // Set attributes for day FlashCard
        WrdGrp13_day.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp13_day.PNG")), 261, 63);
 
        WrdGrp13_day.setChoices("day", "also", "before", "behind", "below", "father");
        
        WrdGrp13_day.setCardName(WrdGrp13_day);

        WrdGrp13_Cards[4] = WrdGrp13_day;  
        
        // Set attributes for father FlashCard
        WrdGrp13_father.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp13_father.PNG")), 261, 63);
 
        WrdGrp13_father.setChoices( "father", "also", "before", "behind", "below", "day");
        
        WrdGrp13_father.setCardName(WrdGrp13_father);

        WrdGrp13_Cards[5] = WrdGrp13_father;          
        
        // List allows use of shuffle.
        WrdGrp13_CardsList = Arrays.asList(WrdGrp13_Cards); 
        // END OF WrdGrp13  
        
        return WrdGrp13_CardsList;
    }
     
    public List<FlashCard> initWrdGrp14(){

        // WrdGrp14
        FlashCard WrdGrp14_name = new FlashCard();
        FlashCard WrdGrp14_question = new FlashCard();
        FlashCard WrdGrp14_through = new FlashCard();
        FlashCard WrdGrp14_under = new FlashCard();
        FlashCard WrdGrp14_work = new FlashCard();
        FlashCard WrdGrp14_young = new FlashCard();
        // Array and list object for WrdGrp14
        FlashCard[] WrdGrp14_Cards = new FlashCard[6];
        // List allows use of shuffle.
        List<FlashCard> WrdGrp14_CardsList;
        
                // Set attributes for name FlashCard
        WrdGrp14_name.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp14_name.PNG")), 261, 63);
 
        WrdGrp14_name.setChoices("name", "question", "through", "under", "work ", "young");
        
        WrdGrp14_name.setCardName(WrdGrp14_name);

        WrdGrp14_Cards[0] = WrdGrp14_name;
        
        // Set attributes for perhaps FlashCard
        WrdGrp14_question.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp14_question.PNG")), 261, 63);
 
        WrdGrp14_question.setChoices( "question", "name", "through", "under", "work ", "young");
        
        WrdGrp14_question.setCardName(WrdGrp14_question);

        WrdGrp14_Cards[1] = WrdGrp14_question;
        
        // Set attributes for through FlashCard
        WrdGrp14_through.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp14_through.PNG")), 261, 63);
 
        WrdGrp14_through.setChoices( "through", "name", "question", "under", "work ", "young" );
        
        WrdGrp14_through.setCardName(WrdGrp14_through);

        WrdGrp14_Cards[2] = WrdGrp14_through;  
        
        // Set attributes for under FlashCard
        WrdGrp14_under.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp14_under.PNG")), 261, 63);
 
        WrdGrp14_under.setChoices(  "under", "name", "question", "through", "work ", "young");
        
        WrdGrp14_under.setCardName(WrdGrp14_under);

        WrdGrp14_Cards[3] = WrdGrp14_under;         
        
        // Set attributes for work FlashCard
        WrdGrp14_work.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp14_work.PNG")), 261, 63);
 
        WrdGrp14_work.setChoices("work", "name", "question", "through", "under", "young");
        
        WrdGrp14_work.setCardName(WrdGrp14_work);

        WrdGrp14_Cards[4] = WrdGrp14_work;  
        
        // Set attributes for young FlashCard
        WrdGrp14_young.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp14_young.PNG")), 261, 63);
 
        WrdGrp14_young.setChoices( "young", "work", "name", "question", "through", "under" );
        
        WrdGrp14_young.setCardName(WrdGrp14_young);

        WrdGrp14_Cards[5] = WrdGrp14_young;          
        
        // List allows use of shuffle.
        WrdGrp14_CardsList = Arrays.asList(WrdGrp14_Cards); 
        // END OF WrdGrp14 
        
        return WrdGrp14_CardsList;
    }
     
    public List<FlashCard> initWrdGrp15(){

        // WrdGrp15
        FlashCard WrdGrp15_ought = new FlashCard();
        FlashCard WrdGrp15_part = new FlashCard();
        FlashCard WrdGrp15_right = new FlashCard();
        FlashCard WrdGrp15_there = new FlashCard();
        FlashCard WrdGrp15_time = new FlashCard();
        FlashCard WrdGrp15_where = new FlashCard();
        // Array and list object for WrdGrp15
        FlashCard[] WrdGrp15_Cards = new FlashCard[6];
        // List allows use of shuffle.
        List<FlashCard> WrdGrp15_CardsList;
        
                // Set attributes for ought FlashCard
        WrdGrp15_ought.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp15_ought.PNG")), 261, 63);
 
        WrdGrp15_ought.setChoices("ought", "part", "right", "there", "time ", "where");
        
        WrdGrp15_ought.setCardName(WrdGrp15_ought);

        WrdGrp15_Cards[0] = WrdGrp15_ought;
        
        // Set attributes for part FlashCard
        WrdGrp15_part.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp15_part.PNG")), 261, 63);
 
        WrdGrp15_part.setChoices(  "part", "ought", "right", "there", "time ", "where");
        
        WrdGrp15_part.setCardName(WrdGrp15_part);

        WrdGrp15_Cards[1] = WrdGrp15_part;
        
        // Set attributes for right FlashCard
        WrdGrp15_right.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp15_right.PNG")), 261, 63);
 
        WrdGrp15_right.setChoices( "right", "ought", "part", "there", "time ", "where" );
        
        WrdGrp15_right.setCardName(WrdGrp15_right);

        WrdGrp15_Cards[2] = WrdGrp15_right;  
        
        // Set attributes for there FlashCard
        WrdGrp15_there.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp15_there.PNG")), 261, 63);
 
        WrdGrp15_there.setChoices(  "there", "ought", "part", "right", "time ", "where");
        
        WrdGrp15_there.setCardName(WrdGrp15_there);

        WrdGrp15_Cards[3] = WrdGrp15_there;         
        
        // Set attributes for time FlashCard
        WrdGrp15_time.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp15_time.PNG")), 261, 63);
 
        WrdGrp15_time.setChoices("time", "ought", "part", "right", "there", "where");
        
        WrdGrp15_time.setCardName(WrdGrp15_time);

        WrdGrp15_Cards[4] = WrdGrp15_time;  
        
        // Set attributes for where FlashCard
        WrdGrp15_where.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp15_where.PNG")), 261, 63);
 
        WrdGrp15_where.setChoices( "where", "time", "ought", "part", "right", "there" );
        
        WrdGrp15_where.setCardName(WrdGrp15_where);

        WrdGrp15_Cards[5] = WrdGrp15_where;          
        
        // List allows use of shuffle.
        WrdGrp15_CardsList = Arrays.asList(WrdGrp15_Cards); 
        // END OF WrdGrp15 
        
        return WrdGrp15_CardsList;
    }
     
    public List<FlashCard> initWrdGrp16(){
        
        // WrdGrp16
        FlashCard WrdGrp16_good = new FlashCard();
        FlashCard WrdGrp16_its = new FlashCard();
        FlashCard WrdGrp16_itself = new FlashCard();
        FlashCard WrdGrp16_tomorrow = new FlashCard();
        FlashCard WrdGrp16_world = new FlashCard();
        FlashCard WrdGrp16_would = new FlashCard();
        // Array and list object for WrdGrp16
        FlashCard[] WrdGrp16_Cards = new FlashCard[6];
        // List allows use of shuffle.
        List<FlashCard> WrdGrp16_CardsList;

        // Set attributes for good FlashCard
        WrdGrp16_good.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp16_good.PNG")), 261, 63);
 
        WrdGrp16_good.setChoices("good", "its", "itself", "tomorrow", "world ", "would");
        
        WrdGrp16_good.setCardName(WrdGrp16_good);

        WrdGrp16_Cards[0] = WrdGrp16_good;
        
        // Set attributes for its FlashCard
        WrdGrp16_its.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp16_its.PNG")), 261, 63);
 
        WrdGrp16_its.setChoices(  "its", "good", "itself", "tomorrow", "world ", "would");
        
        WrdGrp16_its.setCardName(WrdGrp16_its);

        WrdGrp16_Cards[1] = WrdGrp16_its;
        
        // Set attributes for itself FlashCard
        WrdGrp16_itself.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp16_itself.PNG")), 261, 63);
 
        WrdGrp16_itself.setChoices( "itself", "good", "its", "tomorrow", "world ", "would" );
        
        WrdGrp16_itself.setCardName(WrdGrp16_itself);

        WrdGrp16_Cards[2] = WrdGrp16_itself;  
        
        // Set attributes for tomorrow FlashCard
        WrdGrp16_tomorrow.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp16_tomorrow.PNG")), 261, 63);
 
        WrdGrp16_tomorrow.setChoices(  "tomorrow", "good", "its", "itself", "world", "would");
        
        WrdGrp16_tomorrow.setCardName(WrdGrp16_tomorrow);

        WrdGrp16_Cards[3] = WrdGrp16_tomorrow;         
        
        // Set attributes for world FlashCard
        WrdGrp16_world.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp16_world.PNG")), 261, 63);
 
        WrdGrp16_world.setChoices("world", "good", "its", "itself", "tomorrow", "would");
        
        WrdGrp16_world.setCardName(WrdGrp16_world);

        WrdGrp16_Cards[4] = WrdGrp16_world;  
        
        // Set attributes for would FlashCard
        WrdGrp16_would.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp16_would.PNG")), 261, 63);
 
        WrdGrp16_would.setChoices( "would", "world", "good", "its", "itself", "tomorrow" );
        
        WrdGrp16_would.setCardName(WrdGrp16_would);

        WrdGrp16_Cards[5] = WrdGrp16_would;          
        
        // List allows use of shuffle.
        WrdGrp16_CardsList = Arrays.asList(WrdGrp16_Cards); 
        // END OF WrdGrp16 
        
        return WrdGrp16_CardsList;
    }
 
    
    public List<FlashCard> initWrdGrp17(){

        // WrdGrp17
        FlashCard WrdGrp17_friend = new FlashCard();
        FlashCard WrdGrp17_letter = new FlashCard();
        FlashCard WrdGrp17_paid = new FlashCard();
        FlashCard WrdGrp17_their = new FlashCard();
        FlashCard WrdGrp17_today = new FlashCard();
        // Array and list object for WrdGrp17
        FlashCard[] WrdGrp17_Cards = new FlashCard[5];
        // List allows use of shuffle.
        List<FlashCard> WrdGrp17_CardsList;
        // Set attributes for friend FlashCard
        WrdGrp17_friend.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp17_friend.PNG")), 261, 63);
 
        WrdGrp17_friend.setChoices("friend", "letter", "paid", "their", "today ", "  ");
        
        WrdGrp17_friend.setCardName(WrdGrp17_friend);

        WrdGrp17_Cards[0] = WrdGrp17_friend;
        
        // Set attributes for letter FlashCard
        WrdGrp17_letter.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp17_letter.PNG")), 261, 63);
 
        WrdGrp17_letter.setChoices(  "letter", "friend", "paid", "their", "today ", "  ");
        
        WrdGrp17_letter.setCardName(WrdGrp17_letter);

        WrdGrp17_Cards[1] = WrdGrp17_letter;
        
        // Set attributes for paid FlashCard
        WrdGrp17_paid.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp17_paid.PNG")), 261, 63);
 
        WrdGrp17_paid.setChoices( "paid", "friend", "letter", "their", "today ", "  " );
        
        WrdGrp17_paid.setCardName(WrdGrp17_paid);

        WrdGrp17_Cards[2] = WrdGrp17_paid;  
        
        // Set attributes for their FlashCard
        WrdGrp17_their.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp17_their.PNG")), 261, 63);
 
        WrdGrp17_their.setChoices(  "their", "friend", "letter", "paid", "today ", "  ");
        
        WrdGrp17_their.setCardName(WrdGrp17_their);

        WrdGrp17_Cards[3] = WrdGrp17_their;         
        
        // Set attributes for today FlashCard
        WrdGrp17_today.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp17_today.PNG")), 261, 63);
 
        WrdGrp17_today.setChoices("today", "friend", "letter", "paid", "their", "  ");
        
        WrdGrp17_today.setCardName(WrdGrp17_today);

        WrdGrp17_Cards[4] = WrdGrp17_today;  
        
        // List allows use of shuffle.
        WrdGrp17_CardsList = Arrays.asList(WrdGrp17_Cards); 
        // END OF WrdGrp17
        
        return WrdGrp17_CardsList;
    }
 
    
    public List<FlashCard> initWrdGrp18(){

        // WrdGrp18
        FlashCard WrdGrp18_had = new FlashCard();
        FlashCard WrdGrp18_quick = new FlashCard();
        FlashCard WrdGrp18_said = new FlashCard();
        FlashCard WrdGrp18_should = new FlashCard();
        FlashCard WrdGrp18_tonight = new FlashCard();
        FlashCard WrdGrp18_your = new FlashCard();
        // Array and list object for WrdGrp18
        FlashCard[] WrdGrp18_Cards = new FlashCard[6];
        // List allows use of shuffle.
        List<FlashCard> WrdGrp18_CardsList;
        
                // Set attributes for had FlashCard
        WrdGrp18_had.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp18_had.PNG")), 261, 63);
 
        WrdGrp18_had.setChoices("had", "quick", "said", "should", "tonight ", "your");
        
        WrdGrp18_had.setCardName(WrdGrp18_had);

        WrdGrp18_Cards[0] = WrdGrp18_had;
        
        // Set attributes for quick FlashCard
        WrdGrp18_quick.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp18_quick.PNG")), 261, 63);
 
        WrdGrp18_quick.setChoices(  "quick", "had", "said", "should", "tonight ", "your");
        
        WrdGrp18_quick.setCardName(WrdGrp18_quick);

        WrdGrp18_Cards[1] = WrdGrp18_quick;
        
        // Set attributes for said FlashCard
        WrdGrp18_said.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp18_said.PNG")), 261, 63);
 
        WrdGrp18_said.setChoices( "said", "had", "quick", "should", "tonight ", "your" );
        
        WrdGrp18_said.setCardName(WrdGrp18_said);

        WrdGrp18_Cards[2] = WrdGrp18_said;  
        
        // Set attributes for should FlashCard
        WrdGrp18_should.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp18_should.PNG")), 261, 63);
 
        WrdGrp18_should.setChoices(  "should", "had", "quick", "said", "tonight", "your");
        
        WrdGrp18_should.setCardName(WrdGrp18_should);

        WrdGrp18_Cards[3] = WrdGrp18_should;         
        
        // Set attributes for tonight FlashCard
        WrdGrp18_tonight.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp18_tonight.PNG")), 261, 63);
 
        WrdGrp18_tonight.setChoices("tonight", "had", "quick", "said", "should", "your");
        
        WrdGrp18_tonight.setCardName(WrdGrp18_tonight);

        WrdGrp18_Cards[4] = WrdGrp18_tonight;  
        
        // Set attributes for your FlashCard
        WrdGrp18_your.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp18_your.PNG")), 261, 63);
 
        WrdGrp18_your.setChoices( "your", "tonight", "had", "quick", "said", "should" );
        
        WrdGrp18_your.setCardName(WrdGrp18_your);

        WrdGrp18_Cards[5] = WrdGrp18_your;          
        
        // List allows use of shuffle.
        WrdGrp18_CardsList = Arrays.asList(WrdGrp18_Cards); 
        // END OF WrdGrp18 

        return WrdGrp18_CardsList;
    }
    
    public List<FlashCard> initWrdGrp19(){
        
        // WrdGrp19
        FlashCard WrdGrp19_him = new FlashCard();
        FlashCard WrdGrp19_little = new FlashCard();
        FlashCard WrdGrp19_many = new FlashCard();
        FlashCard WrdGrp19_spirit = new FlashCard();
        FlashCard WrdGrp19_upon = new FlashCard();
        FlashCard WrdGrp19_whose = new FlashCard();
        // Array and list object for WrdGrp19
        FlashCard[] WrdGrp19_Cards = new FlashCard[6];
        // List allows use of shuffle.
        List<FlashCard> WrdGrp19_CardsList;
        
        // Set attributes for him FlashCard
        WrdGrp19_him.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp19_him.PNG")), 261, 63);
 
        WrdGrp19_him.setChoices("him", "little", "many", "spirit", "upon ", "whose");
        
        WrdGrp19_him.setCardName(WrdGrp19_him);

        WrdGrp19_Cards[0] = WrdGrp19_him;
        
        // Set attributes for little FlashCard
        WrdGrp19_little.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp19_little.PNG")), 261, 63);
 
        WrdGrp19_little.setChoices(  "little", "him", "many", "spirit", "upon ", "whose");
        
        WrdGrp19_little.setCardName(WrdGrp19_little);

        WrdGrp19_Cards[1] = WrdGrp19_little;
        
        // Set attributes for many FlashCard
        WrdGrp19_many.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp19_many.PNG")), 261, 63);
 
        WrdGrp19_many.setChoices( "many", "him", "little", "spirit", "upon ", "whose" );
        
        WrdGrp19_many.setCardName(WrdGrp19_many);

        WrdGrp19_Cards[2] = WrdGrp19_many;  
        
        // Set attributes for spirit FlashCard
        WrdGrp19_spirit.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp19_spirit.PNG")), 261, 63);
 
        WrdGrp19_spirit.setChoices(  "spirit", "him", "little", "many", "upon ", "whose");
        
        WrdGrp19_spirit.setCardName(WrdGrp19_spirit);

        WrdGrp19_Cards[3] = WrdGrp19_spirit;         
        
        // Set attributes for upon FlashCard
        WrdGrp19_upon.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp19_upon.PNG")), 261, 63);
 
        WrdGrp19_upon.setChoices("upon", "him", "little", "many", "spirit", "whose");
        
        WrdGrp19_upon.setCardName(WrdGrp19_upon);

        WrdGrp19_Cards[4] = WrdGrp19_upon;  
        
        // Set attributes for whose FlashCard
        WrdGrp19_whose.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp19_whose.PNG")), 261, 63);
 
        WrdGrp19_whose.setChoices( "whose", "upon", "him", "little", "many", "spirit" );
        
        WrdGrp19_whose.setCardName(WrdGrp19_whose);

        WrdGrp19_Cards[5] = WrdGrp19_whose;          
        
        // List allows use of shuffle.
        WrdGrp19_CardsList = Arrays.asList(WrdGrp19_Cards); 
        // END OF WrdGrp19 
        
        return WrdGrp19_CardsList;
    }
 
    
    public List<FlashCard> initWrdGrp20(){

        // WrdGrp20
        FlashCard WrdGrp20_almost = new FlashCard();
        FlashCard WrdGrp20_already = new FlashCard();
        FlashCard WrdGrp20_although = new FlashCard();
        FlashCard WrdGrp20_altogether = new FlashCard();
        FlashCard WrdGrp20_always = new FlashCard();
        // Array and list object for WrdGrp20
        FlashCard[] WrdGrp20_Cards = new FlashCard[5];
        // List allows use of shuffle.
        List<FlashCard> WrdGrp20_CardsList;
        
                // Set attributes for almost FlashCard
        WrdGrp20_almost.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp20_almost.PNG")), 261, 63);
 
        WrdGrp20_almost.setChoices("almost", "already", "although", "altogether", "always ", "  ");
        
        WrdGrp20_almost.setCardName(WrdGrp20_almost);

        WrdGrp20_Cards[0] = WrdGrp20_almost;
        
        // Set attributes for already FlashCard
        WrdGrp20_already.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp20_already.PNG")), 261, 63);
 
        WrdGrp20_already.setChoices(  "already", "almost", "although", "altogether", "always ", "  ");
        
        WrdGrp20_already.setCardName(WrdGrp20_already);

        WrdGrp20_Cards[1] = WrdGrp20_already;
        
        // Set attributes for although FlashCard
        WrdGrp20_although.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp20_although.PNG")), 261, 63);
 
        WrdGrp20_although.setChoices( "although", "almost", "already", "altogether", "always ", "  " );
        
        WrdGrp20_although.setCardName(WrdGrp20_although);

        WrdGrp20_Cards[2] = WrdGrp20_although;  
        
        // Set attributes for altogether FlashCard
        WrdGrp20_altogether.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp20_altogether.PNG")), 261, 63);
 
        WrdGrp20_altogether.setChoices(  "altogether", "almost", "already", "although", "always ", "  ");
        
        WrdGrp20_altogether.setCardName(WrdGrp20_altogether);

        WrdGrp20_Cards[3] = WrdGrp20_altogether;         
        
        // Set attributes for always FlashCard
        WrdGrp20_always.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp20_always.PNG")), 261, 63);
 
        WrdGrp20_always.setChoices("always", "almost", "already", "although", "altogether", "  ");
        
        WrdGrp20_always.setCardName(WrdGrp20_always);

        WrdGrp20_Cards[4] = WrdGrp20_always;  
               
        // List allows use of shuffle.
        WrdGrp20_CardsList = Arrays.asList(WrdGrp20_Cards);
        
        // END OF WrdGrp20
        
        return WrdGrp20_CardsList;
    }
 
    
    public List<FlashCard> initWrdGrp21(){

        // WrdGrp21
        FlashCard WrdGrp21_above = new FlashCard();
        FlashCard WrdGrp21_across = new FlashCard();
        FlashCard WrdGrp21_afternoon = new FlashCard();
        FlashCard WrdGrp21_afterward = new FlashCard();
        FlashCard WrdGrp21_against = new FlashCard();
        FlashCard WrdGrp21_together = new FlashCard();
        // Array and list object for WrdGrp21
        FlashCard[] WrdGrp21_Cards = new FlashCard[6];
        // List allows use of shuffle.
        List<FlashCard> WrdGrp21_CardsList;
        
                // Set attributes for above FlashCard
        WrdGrp21_above.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp21_above.PNG")), 261, 63);
 
        WrdGrp21_above.setChoices("above", "across", "afternoon", "afterward", "against ", "together");
        
        WrdGrp21_above.setCardName(WrdGrp21_above);

        WrdGrp21_Cards[0] = WrdGrp21_above;
        
        // Set attributes for across FlashCard
        WrdGrp21_across.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp21_across.PNG")), 261, 63);
 
        WrdGrp21_across.setChoices(  "across", "above", "afternoon", "afterward", "against ", "together");
        
        WrdGrp21_across.setCardName(WrdGrp21_across);

        WrdGrp21_Cards[1] = WrdGrp21_across;
        
        // Set attributes for afternoon FlashCard
        WrdGrp21_afternoon.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp21_afternoon.PNG")), 261, 63);
 
        WrdGrp21_afternoon.setChoices( "afternoon", "above", "across", "afterward", "against ", "together" );
        
        WrdGrp21_afternoon.setCardName(WrdGrp21_afternoon);

        WrdGrp21_Cards[2] = WrdGrp21_afternoon;  
        
        // Set attributes for afterward FlashCard
        WrdGrp21_afterward.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp21_afterward.PNG")), 261, 63);
 
        WrdGrp21_afterward.setChoices(  "afterward", "above", "across", "afternoon", "against ", "together");
        
        WrdGrp21_afterward.setCardName(WrdGrp21_afterward);

        WrdGrp21_Cards[3] = WrdGrp21_afterward;         
        
        // Set attributes for against FlashCard
        WrdGrp21_against.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp21_against.PNG")), 261, 63);
 
        WrdGrp21_against.setChoices("against", "above", "across", "afternoon", "afterward", "together");
        
        WrdGrp21_against.setCardName(WrdGrp21_against);

        WrdGrp21_Cards[4] = WrdGrp21_against;  
        
        // Set attributes for together FlashCard
        WrdGrp21_together.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp21_together.PNG")), 261, 63);
 
        WrdGrp21_together.setChoices( "together", "against", "above", "across", "afternoon", "afterward" );
        
        WrdGrp21_together.setCardName(WrdGrp21_together);

        WrdGrp21_Cards[5] = WrdGrp21_together;          
        
        // List allows use of shuffle.
        WrdGrp21_CardsList = Arrays.asList(WrdGrp21_Cards); 
        // END OF WrdGrp21
        
        return WrdGrp21_CardsList;
    }
    
    public List<FlashCard> initWrdGrp22(){
       
        // WrdGrp22
        FlashCard WrdGrp22_herself = new FlashCard();
        FlashCard WrdGrp22_himself = new FlashCard();
        FlashCard WrdGrp22_myself = new FlashCard();
        FlashCard WrdGrp22_oneself = new FlashCard();
        FlashCard WrdGrp22_thyself = new FlashCard();
        FlashCard WrdGrp22_yourself = new FlashCard();
        // Array and list object for WrdGrp22
        FlashCard[] WrdGrp22_Cards = new FlashCard[6];
        // List allows use of shuffle.
        List<FlashCard> WrdGrp22_CardsList;
        // Set attributes for herself FlashCard
        WrdGrp22_herself.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp22_herself.PNG")), 261, 63);
 
        WrdGrp22_herself.setChoices("herself", "himself", "myself", "oneself", "thyself ", "yourself");
        
        WrdGrp22_herself.setCardName(WrdGrp22_herself);

        WrdGrp22_Cards[0] = WrdGrp22_herself;
        
        // Set attributes for himself FlashCard
        WrdGrp22_himself.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp22_himself.PNG")), 261, 63);
 
        WrdGrp22_himself.setChoices(  "himself", "herself", "myself", "oneself", "thyself ", "yourself");
        
        WrdGrp22_himself.setCardName(WrdGrp22_himself);

        WrdGrp22_Cards[1] = WrdGrp22_himself;
        
        // Set attributes for myself FlashCard
        WrdGrp22_myself.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp22_myself.PNG")), 261, 63);
 
        WrdGrp22_myself.setChoices( "myself", "herself", "himself", "oneself", "thyself ", "yourself" );
        
        WrdGrp22_myself.setCardName(WrdGrp22_myself);

        WrdGrp22_Cards[2] = WrdGrp22_myself;  
        
        // Set attributes for oneself FlashCard
        WrdGrp22_oneself.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp22_oneself.PNG")), 261, 63);
 
        WrdGrp22_oneself.setChoices( "oneself", "herself", "himself", "myself", "thyself ", "yourself");
        
        WrdGrp22_oneself.setCardName(WrdGrp22_oneself);

        WrdGrp22_Cards[3] = WrdGrp22_oneself;         
        
        // Set attributes for thyself FlashCard
        WrdGrp22_thyself.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp22_thyself.PNG")), 261, 63);
 
        WrdGrp22_thyself.setChoices("thyself", "herself", "himself", "myself", "oneself", "yourself");
        
        WrdGrp22_thyself.setCardName(WrdGrp22_thyself);

        WrdGrp22_Cards[4] = WrdGrp22_thyself;  
        
        // Set attributes for yourself FlashCard
        WrdGrp22_yourself.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp22_yourself.PNG")), 261, 63);
 
        WrdGrp22_yourself.setChoices( "yourself", "thyself", "herself", "himself", "myself", "oneself" );
        
        WrdGrp22_yourself.setCardName(WrdGrp22_yourself);

        WrdGrp22_Cards[5] = WrdGrp22_yourself;          
        
        // List allows use of shuffle.
        WrdGrp22_CardsList = Arrays.asList(WrdGrp22_Cards); 
        // END OF WrdGrp22 
        
        return WrdGrp22_CardsList;
    }
 
    
    public List<FlashCard> initWrdGrp23(){

        // WrdGrp23
        FlashCard WrdGrp23_braille = new FlashCard();
        FlashCard WrdGrp23_declare = new FlashCard();
        FlashCard WrdGrp23_immediate = new FlashCard();
        FlashCard WrdGrp23_necessary = new FlashCard();
        FlashCard WrdGrp23_neither = new FlashCard();
        FlashCard WrdGrp23_perhaps = new FlashCard();
        // Array and list object for WrdGrp23
        FlashCard[] WrdGrp23_Cards = new FlashCard[6];
        // List allows use of shuffle.
        List<FlashCard> WrdGrp23_CardsList;

                // Set attributes for braille FlashCard
        WrdGrp23_braille.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp23_braille.PNG")), 261, 63);
 
        WrdGrp23_braille.setChoices("braille", "declare", "immediate", "necessary", "neither ", "perhaps");
        
        WrdGrp23_braille.setCardName(WrdGrp23_braille);

        WrdGrp23_Cards[0] = WrdGrp23_braille;
        
        // Set attributes for declare FlashCard
        WrdGrp23_declare.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp23_declare.PNG")), 261, 63);
 
        WrdGrp23_declare.setChoices(  "declare", "braille", "immediate", "necessary", "neither ", "perhaps");
        
        WrdGrp23_declare.setCardName(WrdGrp23_declare);

        WrdGrp23_Cards[1] = WrdGrp23_declare;
        
        // Set attributes for immediate FlashCard
        WrdGrp23_immediate.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp23_immediate.PNG")), 261, 63);
 
        WrdGrp23_immediate.setChoices( "immediate", "braille", "declare", "necessary", "neither ", "perhaps" );
        
        WrdGrp23_immediate.setCardName(WrdGrp23_immediate);

        WrdGrp23_Cards[2] = WrdGrp23_immediate;  
        
        // Set attributes for necessary FlashCard
        WrdGrp23_necessary.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp23_necessary.PNG")), 261, 63);
 
        WrdGrp23_necessary.setChoices(  "necessary", "braille", "declare", "immediate", "neither ", "perhaps");
        
        WrdGrp23_necessary.setCardName(WrdGrp23_necessary);

        WrdGrp23_Cards[3] = WrdGrp23_necessary;         
        
        // Set attributes for neither FlashCard
        WrdGrp23_neither.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp23_neither.PNG")), 261, 63);
 
        WrdGrp23_neither.setChoices("neither", "braille", "declare", "immediate", "necessary", "perhaps");
        
        WrdGrp23_neither.setCardName(WrdGrp23_neither);

        WrdGrp23_Cards[4] = WrdGrp23_neither;  
        
        // Set attributes for perhaps FlashCard
        WrdGrp23_perhaps.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp23_perhaps.PNG")), 261, 63);
 
        WrdGrp23_perhaps.setChoices( "perhaps", "neither", "braille", "declare", "immediate", "necessary" );
        
        WrdGrp23_perhaps.setCardName(WrdGrp23_perhaps);

        WrdGrp23_Cards[5] = WrdGrp23_perhaps;          
        
        // List allows use of shuffle.
        WrdGrp23_CardsList = Arrays.asList(WrdGrp23_Cards); 
        // END OF WrdGrp23
        
        return WrdGrp23_CardsList;
    }
 
    
    public List<FlashCard> initWrdGrp24(){

        // WrdGrp24
        FlashCard WrdGrp24_conceive = new FlashCard();
        FlashCard WrdGrp24_deceive = new FlashCard();
        FlashCard WrdGrp24_great = new FlashCard();
        FlashCard WrdGrp24_o_clock = new FlashCard();
        FlashCard WrdGrp24_receive = new FlashCard();
        FlashCard WrdGrp24_rejoice = new FlashCard();
        // Array and list object for WrdGrp24
        FlashCard[] WrdGrp24_Cards = new FlashCard[6];
        List<FlashCard> WrdGrp24_CardsList;
        
        // Set attributes for conceive FlashCard
        WrdGrp24_conceive.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp24_conceive.PNG")), 261, 63);
 
        WrdGrp24_conceive.setChoices("conceive", "deceive", "great", "o'clock", "receive ", "rejoice");
        
        WrdGrp24_conceive.setCardName(WrdGrp24_conceive);

        WrdGrp24_Cards[0] = WrdGrp24_conceive;
        
        // Set attributes for deceive FlashCard
        WrdGrp24_deceive.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp24_deceive.PNG")), 261, 63);
 
        WrdGrp24_deceive.setChoices(  "deceive", "conceive", "great", "o'clock", "receive ", "rejoice");
        
        WrdGrp24_deceive.setCardName(WrdGrp24_deceive);

        WrdGrp24_Cards[1] = WrdGrp24_deceive;
        
        // Set attributes for great FlashCard
        WrdGrp24_great.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp24_great.PNG")), 261, 63);
 
        WrdGrp24_great.setChoices( "great", "conceive", "deceive", "o'clock", "receive ", "rejoice" );
        
        WrdGrp24_great.setCardName(WrdGrp24_great);

        WrdGrp24_Cards[2] = WrdGrp24_great;  
        
        // Set attributes for o'clock FlashCard
        WrdGrp24_o_clock.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp24_o_clock.PNG")), 261, 63);
 
        WrdGrp24_o_clock.setChoices(  "o'clock", "conceive", "deceive", "great", "receive ", "rejoice");
        
        WrdGrp24_o_clock.setCardName(WrdGrp24_o_clock);

        WrdGrp24_Cards[3] = WrdGrp24_o_clock;         
        
        // Set attributes for receive FlashCard
        WrdGrp24_receive.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp24_receive.PNG")), 261, 63);
 
        WrdGrp24_receive.setChoices("receive", "conceive", "deceive", "great", "o'clock", "rejoice");
        
        WrdGrp24_receive.setCardName(WrdGrp24_receive);

        WrdGrp24_Cards[4] = WrdGrp24_receive;  
        
        // Set attributes for rejoice FlashCard
        WrdGrp24_rejoice.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp24_rejoice.PNG")), 261, 63);
 
        WrdGrp24_rejoice.setChoices( "rejoice", "receive", "conceive", "deceive", "great", "o'clock" );
        
        WrdGrp24_rejoice.setCardName(WrdGrp24_rejoice);

        WrdGrp24_Cards[5] = WrdGrp24_rejoice;          
        
        // List allows use of shuffle.
        WrdGrp24_CardsList = Arrays.asList(WrdGrp24_Cards); 
        // END OF WrdGrp24 
        
        return WrdGrp24_CardsList;
    }
     
    public List<FlashCard> initWrdGrp25(){
        
        // WrdGrp25
        FlashCard WrdGrp25_perceive = new FlashCard();
        FlashCard WrdGrp25_perceiving = new FlashCard();
        FlashCard WrdGrp25_receiving = new FlashCard();
        FlashCard WrdGrp25_rejoicing = new FlashCard();
        FlashCard WrdGrp25_themselves = new FlashCard();
        // Array and list object for WrdGrp25
        FlashCard[] WrdGrp25_Cards = new FlashCard[5];
        // List allows use of shuffle.
        List<FlashCard> WrdGrp25_CardsList;
        
                // Set attributes for perceive FlashCard
        WrdGrp25_perceive.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp25_perceive.PNG")), 261, 63);
 
        WrdGrp25_perceive.setChoices("perceive", "perceiving", "receiving", "rejoicing", "themselves ", " ");
        
        WrdGrp25_perceive.setCardName(WrdGrp25_perceive);

        WrdGrp25_Cards[0] = WrdGrp25_perceive;
        
        // Set attributes for perceiving FlashCard
        WrdGrp25_perceiving.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp25_perceiving.PNG")), 261, 63);
 
        WrdGrp25_perceiving.setChoices(  "perceiving", "perceive", "receiving", "rejoicing", "themselves ", " ");
        
        WrdGrp25_perceiving.setCardName(WrdGrp25_perceiving);

        WrdGrp25_Cards[1] = WrdGrp25_perceiving;
        
        // Set attributes for receiving FlashCard
        WrdGrp25_receiving.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp25_receiving.PNG")), 261, 63);
 
        WrdGrp25_receiving.setChoices( "receiving", "perceive", "perceiving", "rejoicing", "themselves ", " " );
        
        WrdGrp25_receiving.setCardName(WrdGrp25_receiving);

        WrdGrp25_Cards[2] = WrdGrp25_receiving;  
        
        // Set attributes for rejoicing FlashCard
        WrdGrp25_rejoicing.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp25_rejoicing.PNG")), 261, 63);
 
        WrdGrp25_rejoicing.setChoices(  "rejoicing", "perceive", "perceiving", "receiving", "themselves ", " ");
        
        WrdGrp25_rejoicing.setCardName(WrdGrp25_rejoicing);

        WrdGrp25_Cards[3] = WrdGrp25_rejoicing;         
        
        // Set attributes for themselves FlashCard
        WrdGrp25_themselves.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp25_themselves.PNG")), 261, 63);
 
        WrdGrp25_themselves.setChoices("themselves", "perceive", "perceiving", "receiving", "rejoicing", " ");
        
        WrdGrp25_themselves.setCardName(WrdGrp25_themselves);

        WrdGrp25_Cards[4] = WrdGrp25_themselves;           
        
        // List allows use of shuffle.
        WrdGrp25_CardsList = Arrays.asList(WrdGrp25_Cards); 
        // END OF WrdGrp25 
        
        return WrdGrp25_CardsList;
    }
 
    
    public List<FlashCard> initWrdGrp26(){

        // WrdGrp26
        FlashCard WrdGrp26_conceiving = new FlashCard();
        FlashCard WrdGrp26_deceiving = new FlashCard();
        FlashCard WrdGrp26_declaring = new FlashCard();
        FlashCard WrdGrp26_ourselves = new FlashCard();
        FlashCard WrdGrp26_yourselves = new FlashCard();
        // Array and list object for WrdGrp26
        FlashCard[] WrdGrp26_Cards = new FlashCard[5];
        // List allows use of shuffle.
        List<FlashCard> WrdGrp26_CardsList;
        
        // Set attributes for conceiving FlashCard
        WrdGrp26_conceiving.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp26_conceiving.PNG")), 261, 63);
 
        WrdGrp26_conceiving.setChoices("conceiving", " deceiving", "declaring", "ourselves", "yourselves ", " ");
        
        WrdGrp26_conceiving.setCardName(WrdGrp26_conceiving);

        WrdGrp26_Cards[0] = WrdGrp26_conceiving;
        
        // Set attributes for deceiving FlashCard
        WrdGrp26_deceiving.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp26_deceiving.PNG")), 261, 63);
 
        WrdGrp26_deceiving.setChoices( "deceiving", "conceiving", "declaring", "ourselves", "yourselves ", " ");
        
        WrdGrp26_deceiving.setCardName(WrdGrp26_deceiving);

        WrdGrp26_Cards[1] = WrdGrp26_deceiving;
        
        // Set attributes for declaring FlashCard
        WrdGrp26_declaring.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp26_declaring.PNG")), 261, 63);
 
        WrdGrp26_declaring.setChoices( "declaring", "conceiving", "deceiving", "ourselves", "yourselves ", " " );
        
        WrdGrp26_declaring.setCardName(WrdGrp26_declaring);

        WrdGrp26_Cards[2] = WrdGrp26_declaring;  
        
        // Set attributes for ourselves FlashCard
        WrdGrp26_ourselves.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp26_ourselves.PNG")), 261, 63);
 
        WrdGrp26_ourselves.setChoices(  "ourselves", "conceiving", "deceiving", "declaring", "yourselves ", " ");
        
        WrdGrp26_ourselves.setCardName(WrdGrp26_ourselves);

        WrdGrp26_Cards[3] = WrdGrp26_ourselves;         
        
        // Set attributes for yourselves FlashCard
        WrdGrp26_yourselves.setFlashCardImg(new ImageIcon(getClass().getResource("WrdGrp26_yourselves.PNG")), 261, 63);
 
        WrdGrp26_yourselves.setChoices("yourselves", "conceiving", " deceiving", "declaring", "ourselves", " ");
        
        WrdGrp26_yourselves.setCardName(WrdGrp26_yourselves);

        WrdGrp26_Cards[4] = WrdGrp26_yourselves;            
        
        // List allows use of shuffle.
        WrdGrp26_CardsList = Arrays.asList(WrdGrp26_Cards); 
        
        return WrdGrp26_CardsList;
    }
    
    /**
     * Creates new form MainJFrame
     */
    public MainJFrame() {
        initComponents();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupABC = new javax.swing.ButtonGroup();
        buttonGroupABCSessionType = new javax.swing.ButtonGroup();
        buttonGroupNumSym = new javax.swing.ButtonGroup();
        buttonGroupNumSymSessionType = new javax.swing.ButtonGroup();
        buttonGroupComCombo = new javax.swing.ButtonGroup();
        buttonGroupComComboSessionType = new javax.swing.ButtonGroup();
        buttonGroupWords = new javax.swing.ButtonGroup();
        buttonGroupWordsSessionType = new javax.swing.ButtonGroup();
        buttonGroupWords_B = new javax.swing.ButtonGroup();
        buttonGroupWords_B_SessionType = new javax.swing.ButtonGroup();
        buttonGroupWords_C = new javax.swing.ButtonGroup();
        buttonGroupWords_C_SessionType = new javax.swing.ButtonGroup();
        buttonGroupAll = new javax.swing.ButtonGroup();
        buttonGroupAllSessionType = new javax.swing.ButtonGroup();
        buttonGroupProbe = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        intro = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        createLog = new javax.swing.JPanel();
        jTextFieldSubjectID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldMajor = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextSessionID = new javax.swing.JTextField();
        jLabelAdditionalInfo = new javax.swing.JLabel();
        jTextFieldGender = new javax.swing.JTextField();
        jButtonCreateLog = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        abcs = new javax.swing.JPanel();
        abcResetButton = new javax.swing.JButton();
        jLabelAbcImg = new javax.swing.JLabel();
        // From http://stackoverflow.com/questions/5895829/resizing-image-in-java
        //    ImageIcon braillePic = new ImageIcon(getClass().getResource("/abcGrp1/i.PNG"));

        //    Image scaleImage = braillePic.getImage().getScaledInstance(45, 63, Image.SCALE_DEFAULT);

        //    ImageIcon scaleIcon = new ImageIcon(scaleImage);
        jRadioButtonABC1 = new javax.swing.JRadioButton();
        jRadioButtonABC2 = new javax.swing.JRadioButton();
        jRadioButtonABC3 = new javax.swing.JRadioButton();
        jRadioButtonABC4 = new javax.swing.JRadioButton();
        jRadioButtonABC5 = new javax.swing.JRadioButton();
        jRadioButtonABC6 = new javax.swing.JRadioButton();
        jRadioButtonABCBaseline = new javax.swing.JRadioButton();
        jRadioButtonABCTrain = new javax.swing.JRadioButton();
        jRadioButtonABCRehearsal = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        numSym = new javax.swing.JPanel();
        numSymResetButton = new javax.swing.JButton();
        jLabelNumSymImg = new javax.swing.JLabel();
        // From http://stackoverflow.com/questions/5895829/resizing-image-in-java
        //    ImageIcon braillePic = new ImageIcon(getClass().getResource("/abcGrp1/i.PNG"));

        //    Image scaleImage = braillePic.getImage().getScaledInstance(45, 63, Image.SCALE_DEFAULT);

        //    ImageIcon scaleIcon = new ImageIcon(scaleImage);
        jLabel23 = new javax.swing.JLabel();
        jRadioButtonNumSymBaseline = new javax.swing.JRadioButton();
        jRadioButtonNumSymTrain = new javax.swing.JRadioButton();
        jRadioButtonNumSym_Rehearsal = new javax.swing.JRadioButton();
        jRadioButtonNumSym1 = new javax.swing.JRadioButton();
        jRadioButtonNumSym2 = new javax.swing.JRadioButton();
        jRadioButtonNumSym3 = new javax.swing.JRadioButton();
        jRadioButtonNumSym4 = new javax.swing.JRadioButton();
        jRadioButtonNumSym5 = new javax.swing.JRadioButton();
        jRadioButtonNumSym6 = new javax.swing.JRadioButton();
        commonCombo = new javax.swing.JPanel();
        comComboResetButton = new javax.swing.JButton();
        jLabelComComboImg = new javax.swing.JLabel();
        // From http://stackoverflow.com/questions/5895829/resizing-image-in-java
        //    ImageIcon braillePic = new ImageIcon(getClass().getResource("/abcGrp1/i.PNG"));

        //    Image scaleImage = braillePic.getImage().getScaledInstance(45, 63, Image.SCALE_DEFAULT);

        //    ImageIcon scaleIcon = new ImageIcon(scaleImage);
        jLabel12 = new javax.swing.JLabel();
        jRadioButtonComComboBaseline = new javax.swing.JRadioButton();
        jRadioButtonComComboTrain = new javax.swing.JRadioButton();
        jRadioButtonComCombo1 = new javax.swing.JRadioButton();
        jRadioButtonComCombo2 = new javax.swing.JRadioButton();
        jRadioButtonComCombo3 = new javax.swing.JRadioButton();
        jRadioButtonComCombo4 = new javax.swing.JRadioButton();
        jRadioButtonComCombo5 = new javax.swing.JRadioButton();
        jRadioButtonComCombo6 = new javax.swing.JRadioButton();
        jRadioButtonCommCombo_Rehearsal = new javax.swing.JRadioButton();
        words = new javax.swing.JPanel();
        wordResetButton = new javax.swing.JButton();
        jLabelWordImg = new javax.swing.JLabel();
        // From http://stackoverflow.com/questions/5895829/resizing-image-in-java
        //    ImageIcon braillePic = new ImageIcon(getClass().getResource("/abcGrp1/i.PNG"));

        //    Image scaleImage = braillePic.getImage().getScaledInstance(45, 63, Image.SCALE_DEFAULT);

        //    ImageIcon scaleIcon = new ImageIcon(scaleImage);
        jLabel21 = new javax.swing.JLabel();
        jRadioButtonWordsBaseline = new javax.swing.JRadioButton();
        jRadioButtonWordTrain = new javax.swing.JRadioButton();
        jRadioButtonWord1 = new javax.swing.JRadioButton();
        jRadioButtonWord2 = new javax.swing.JRadioButton();
        jRadioButtonWord3 = new javax.swing.JRadioButton();
        jRadioButtonWord4 = new javax.swing.JRadioButton();
        jRadioButtonWord5 = new javax.swing.JRadioButton();
        jRadioButtonWord6 = new javax.swing.JRadioButton();
        jRadioButtonWord_A_Rehearsal = new javax.swing.JRadioButton();
        words_B = new javax.swing.JPanel();
        word_B_ResetButton = new javax.swing.JButton();
        jLabelWord_B_Img = new javax.swing.JLabel();
        // From http://stackoverflow.com/questions/5895829/resizing-image-in-java
        //    ImageIcon braillePic = new ImageIcon(getClass().getResource("/abcGrp1/i.PNG"));

        //    Image scaleImage = braillePic.getImage().getScaledInstance(45, 63, Image.SCALE_DEFAULT);

        //    ImageIcon scaleIcon = new ImageIcon(scaleImage);
        jLabel26 = new javax.swing.JLabel();
        jRadioButtonWords_B_Baseline1 = new javax.swing.JRadioButton();
        jRadioButtonWord_B_Train = new javax.swing.JRadioButton();
        jRadioButtonWord_B_1 = new javax.swing.JRadioButton();
        jRadioButtonWord_B_2 = new javax.swing.JRadioButton();
        jRadioButtonWord_B_3 = new javax.swing.JRadioButton();
        jRadioButtonWord_B_4 = new javax.swing.JRadioButton();
        jRadioButtonWord_B_5 = new javax.swing.JRadioButton();
        jRadioButtonWord_B_6 = new javax.swing.JRadioButton();
        jRadioButtonWord_B_Rehearsal = new javax.swing.JRadioButton();
        words_C = new javax.swing.JPanel();
        word_C_ResetButton = new javax.swing.JButton();
        jLabelWord_C_Img = new javax.swing.JLabel();
        // From http://stackoverflow.com/questions/5895829/resizing-image-in-java
        //    ImageIcon braillePic = new ImageIcon(getClass().getResource("/abcGrp1/i.PNG"));

        //    Image scaleImage = braillePic.getImage().getScaledInstance(45, 63, Image.SCALE_DEFAULT);

        //    ImageIcon scaleIcon = new ImageIcon(scaleImage);
        jLabel28 = new javax.swing.JLabel();
        jRadioButtonWords_C_Baseline = new javax.swing.JRadioButton();
        jRadioButtonWord_C_Train = new javax.swing.JRadioButton();
        jRadioButtonWord_C_1 = new javax.swing.JRadioButton();
        jRadioButtonWord_C_2 = new javax.swing.JRadioButton();
        jRadioButtonWord_C_3 = new javax.swing.JRadioButton();
        jRadioButtonWord_C_4 = new javax.swing.JRadioButton();
        jRadioButtonWord_C_5 = new javax.swing.JRadioButton();
        jRadioButtonWord_C_6 = new javax.swing.JRadioButton();
        jRadioButtonWord_C_Rehearsal = new javax.swing.JRadioButton();
        all = new javax.swing.JPanel();
        allResetButton = new javax.swing.JButton();
        jLabelAllImg = new javax.swing.JLabel();
        // From http://stackoverflow.com/questions/5895829/resizing-image-in-java
        //    ImageIcon braillePic = new ImageIcon(getClass().getResource("/abcGrp1/i.PNG"));

        //    Image scaleImage = braillePic.getImage().getScaledInstance(45, 63, Image.SCALE_DEFAULT);

        //    ImageIcon scaleIcon = new ImageIcon(scaleImage);
        jLabel22 = new javax.swing.JLabel();
        jRadioButtonAllBaseline = new javax.swing.JRadioButton();
        jRadioButtonAllTrain = new javax.swing.JRadioButton();
        jRadioButtonAll1 = new javax.swing.JRadioButton();
        jRadioButtonAll2 = new javax.swing.JRadioButton();
        jRadioButtonAll3 = new javax.swing.JRadioButton();
        jRadioButtonAll4 = new javax.swing.JRadioButton();
        jRadioButtonAll5 = new javax.swing.JRadioButton();
        jRadioButtonAll6 = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        probe = new javax.swing.JPanel();
        jLabelProbeImg = new javax.swing.JLabel();
        // From http://stackoverflow.com/questions/5895829/resizing-image-in-java
        //    ImageIcon braillePic = new ImageIcon(getClass().getResource("/abcGrp1/i.PNG"));

        //    Image scaleImage = braillePic.getImage().getScaledInstance(45, 63, Image.SCALE_DEFAULT);

        //    ImageIcon scaleIcon = new ImageIcon(scaleImage);
        jRadioButtonProbe1 = new javax.swing.JRadioButton();
        jRadioButtonProbe2 = new javax.swing.JRadioButton();
        jRadioButtonProbe3 = new javax.swing.JRadioButton();
        jRadioButtonProbe4 = new javax.swing.JRadioButton();
        jRadioButtonProbe5 = new javax.swing.JRadioButton();
        jRadioButtonProbe6 = new javax.swing.JRadioButton();
        jScrollPaneProbe = new javax.swing.JScrollPane();
        jTextAreaProbe = new javax.swing.JTextArea();
        buttonStartProbe = new java.awt.Button();
        buttonResetProbe = new java.awt.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Braille Flash Cards");

        jTabbedPane1.setAlignmentX(0.0F);
        jTabbedPane1.setAutoscrolls(true);

        intro.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Welcome");

        jScrollPane1.setBorder(null);

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(191, 205, 219));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("Braille dot matrices for the ABCs, Numbers and Symbols, Common Combinations of Letters, \nthree sets of Words and All in combination are presented on their respective tabs. \n\nTo use this program:\n1. Go to Create Log tab and create a log. NOTE: A Log is required by the program.\n2. Go to desired tab select Baseline, Training or Rehearsal.  \n     * Baseline presents all the Braille for the given tab, with a message the response has \n        been recorded. \n     * Training presents the Braille in groups of 4-6. The user is informed if the answer is \n        correct or incorrect. Incorrect cards are presented repeatedely until correctly identified. \n        If presented repeatedly only the first presentation is scored. A score of 90% or more for \n        the current group, based on the last 15 trials, is needed before the next group is added.\n        A score of 90% or more for the current group, based on the last 15 trials, is needed \n        before the next group is added.\n     * Rehearsal presents 10 randomly selected cards for the given tab, plus 10 from the \n        previous tabs. For example Rehearsal for the tab \"1-ABCs\" would only present 10\n        cards. Rehearsal for tab \"6-Words C\" would present 10 from Word C group and 10 each \n        from each of the preceding tabs for a total of 60 cards. If a response is incorrect the \n        card is presented again until the correct reply.\n ");
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout introLayout = new javax.swing.GroupLayout(intro);
        intro.setLayout(introLayout);
        introLayout.setHorizontalGroup(
            introLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(introLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(introLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 37, Short.MAX_VALUE))
        );
        introLayout.setVerticalGroup(
            introLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(introLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Intro", intro);
        //Initialize FlashCard when Welcome opens.
        initializeCardsABC();
        initializeCardsNumSym();
        initializeCardsComCombo();
        //initializeCardsWords();

        jLabel2.setText("Subject ID:");

        jLabel3.setText("Gender:");

        jLabel4.setText("Major:");

        jLabelAdditionalInfo.setText("Session ID:");

        jButtonCreateLog.setText("Create Log");
        jButtonCreateLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCreateLogActionPerformed(evt);
            }
        });

        jLabel5.setText("NOTE: File is automatically assigned a .csv file extension.");

        javax.swing.GroupLayout createLogLayout = new javax.swing.GroupLayout(createLog);
        createLog.setLayout(createLogLayout);
        createLogLayout.setHorizontalGroup(
            createLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createLogLayout.createSequentialGroup()
                .addGroup(createLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(createLogLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(createLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelAdditionalInfo)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(createLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldGender)
                            .addComponent(jTextFieldSubjectID)
                            .addComponent(jTextFieldMajor, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextSessionID)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(createLogLayout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addComponent(jButtonCreateLog)))
                .addContainerGap(287, Short.MAX_VALUE))
        );
        createLogLayout.setVerticalGroup(
            createLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createLogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(createLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSubjectID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(createLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(createLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldMajor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(createLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextSessionID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelAdditionalInfo))
                .addGap(6, 6, 6)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonCreateLog)
                .addContainerGap(164, Short.MAX_VALUE))
        );

        jLabel2.getAccessibleContext().setAccessibleName("jLabelFirstName");
        jLabel3.getAccessibleContext().setAccessibleName("jLablel");

        jTabbedPane1.addTab("Create Log", createLog);

        assignABCImgChoices();
        abcs.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                abcsFocusGained(evt);
            }
        });

        abcResetButton.setBackground(new java.awt.Color(255, 255, 102));
        abcResetButton.setText("RESET");
        abcResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abcResetButtonActionPerformed(evt);
            }
        });

        jLabelAbcImg.setBackground(java.awt.Color.white);
        //Load first group, first card.
        //if (initializeABC == 0) {
            //    ++initializeABC;
            //    abcCurrentGrps_CardsList = abcGrp1_CardsList;
            //    Collections.shuffle(abcCurrentGrps_CardsList);
            //    getNextCard();
            //}
        jLabelAbcImg.setIcon(CurrentCard.getScaledImage());

        buttonGroupABC.add(jRadioButtonABC1);
        //
        jRadioButtonABC1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonABC1.setText(Ans1);
        jRadioButtonABC1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonABC1ActionPerformed(evt);
            }
        });
        // Code taken from http://journals.ecs.soton.ac.uk/java/tutorial/post1.0/ui/radiobutton.html

        buttonGroupABC.add(jRadioButtonABC2);
        jRadioButtonABC2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonABC2.setText(Ans2);
        jRadioButtonABC2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonABC2ActionPerformed(evt);
            }
        });

        buttonGroupABC.add(jRadioButtonABC3);
        jRadioButtonABC3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonABC3.setText(Ans3);
        jRadioButtonABC3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonABC3ActionPerformed(evt);
            }
        });

        buttonGroupABC.add(jRadioButtonABC4);
        jRadioButtonABC4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonABC4.setText(Ans4);
        jRadioButtonABC4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonABC4ActionPerformed(evt);
            }
        });

        buttonGroupABC.add(jRadioButtonABC5);
        jRadioButtonABC5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonABC5.setText(Ans5);
        jRadioButtonABC5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonABC5ActionPerformed(evt);
            }
        });

        buttonGroupABC.add(jRadioButtonABC6);
        jRadioButtonABC6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonABC6.setText(Ans6);
        jRadioButtonABC6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonABC6ActionPerformed(evt);
            }
        });

        buttonGroupABCSessionType.add(jRadioButtonABCBaseline);
        jRadioButtonABCBaseline.setText("Baseline");
        jRadioButtonABCBaseline.setAutoscrolls(true);
        jRadioButtonABCBaseline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonABCBaselineActionPerformed(evt);
            }
        });

        buttonGroupABCSessionType.add(jRadioButtonABCTrain);
        jRadioButtonABCTrain.setText("Training");
        jRadioButtonABCTrain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonABCTrainActionPerformed(evt);
            }
        });

        buttonGroupABCSessionType.add(jRadioButtonABCRehearsal);
        jRadioButtonABCRehearsal.setText("Rehearsal");
        jRadioButtonABCRehearsal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonABCRehearsalActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Select Test Type");

        javax.swing.GroupLayout abcsLayout = new javax.swing.GroupLayout(abcs);
        abcs.setLayout(abcsLayout);
        abcsLayout.setHorizontalGroup(
            abcsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abcsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(abcsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButtonABC6, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(abcsLayout.createSequentialGroup()
                        .addGroup(abcsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelAbcImg, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButtonABC1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButtonABC3, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButtonABC5, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButtonABC2, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButtonABC4, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(abcsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(abcResetButton)
                            .addComponent(jRadioButtonABCRehearsal)
                            .addComponent(jLabel7)
                            .addComponent(jRadioButtonABCBaseline, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButtonABCTrain))))
                .addContainerGap(197, Short.MAX_VALUE))
        );
        abcsLayout.setVerticalGroup(
            abcsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abcsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(abcsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(abcsLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonABCBaseline)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonABCTrain)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonABCRehearsal))
                    .addGroup(abcsLayout.createSequentialGroup()
                        .addComponent(jLabelAbcImg, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonABC1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonABC3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addGroup(abcsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(abcsLayout.createSequentialGroup()
                        .addComponent(abcResetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3))
                    .addComponent(jRadioButtonABC5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButtonABC2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButtonABC4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButtonABC6)
                .addContainerGap(123, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("1-ABCs", abcs);

        assignNumSymImgChoices();
        numSym.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                numSymFocusGained(evt);
            }
        });

        numSymResetButton.setBackground(new java.awt.Color(255, 255, 102));
        numSymResetButton.setText("RESET");
        numSymResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numSymResetButtonActionPerformed(evt);
            }
        });

        jLabelNumSymImg.setBackground(java.awt.Color.white);
        jLabelNumSymImg.setIcon(CurrentCard.getScaledImage());

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setText("Select Test Type");

        buttonGroupNumSymSessionType.add(jRadioButtonNumSymBaseline);
        jRadioButtonNumSymBaseline.setText("Baseline");
        jRadioButtonNumSymBaseline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonNumSymBaselineActionPerformed(evt);
            }
        });

        buttonGroupNumSymSessionType.add(jRadioButtonNumSymTrain);
        jRadioButtonNumSymTrain.setText("Training");
        jRadioButtonNumSymTrain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonNumSymTrainActionPerformed(evt);
            }
        });

        buttonGroupNumSymSessionType.add(jRadioButtonNumSym_Rehearsal);
        jRadioButtonNumSym_Rehearsal.setText("Rehearsal");

        buttonGroupNumSym.add(jRadioButtonNumSym1);
        jRadioButtonNumSym1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonNumSym1.setText(Ans1);
        jRadioButtonNumSym1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonNumSym1ActionPerformed(evt);
            }
        });
        // Code taken from http://journals.ecs.soton.ac.uk/java/tutorial/post1.0/ui/radiobutton.html

        buttonGroupNumSym.add(jRadioButtonNumSym2);
        jRadioButtonNumSym2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonNumSym2.setText(Ans3);
        jRadioButtonNumSym2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonNumSym2ActionPerformed(evt);
            }
        });

        buttonGroupNumSym.add(jRadioButtonNumSym3);
        jRadioButtonNumSym3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonNumSym3.setText(Ans5);
        jRadioButtonNumSym3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonNumSym3ActionPerformed(evt);
            }
        });

        buttonGroupNumSym.add(jRadioButtonNumSym4);
        jRadioButtonNumSym4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonNumSym4.setText(Ans2);
        jRadioButtonNumSym4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonNumSym4ActionPerformed(evt);
            }
        });

        buttonGroupNumSym.add(jRadioButtonNumSym5);
        jRadioButtonNumSym5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonNumSym5.setText(Ans4);
        jRadioButtonNumSym5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonNumSym5ActionPerformed(evt);
            }
        });

        buttonGroupNumSym.add(jRadioButtonNumSym6);
        jRadioButtonNumSym6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonNumSym6.setText(Ans6);
        jRadioButtonNumSym6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonNumSym6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout numSymLayout = new javax.swing.GroupLayout(numSym);
        numSym.setLayout(numSymLayout);
        numSymLayout.setHorizontalGroup(
            numSymLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(numSymLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(numSymLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(numSymLayout.createSequentialGroup()
                        .addComponent(jRadioButtonNumSym6, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(numSymLayout.createSequentialGroup()
                        .addGroup(numSymLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButtonNumSym5, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(numSymLayout.createSequentialGroup()
                                .addGroup(numSymLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButtonNumSym1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelNumSymImg, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jRadioButtonNumSym2, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jRadioButtonNumSym3, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jRadioButtonNumSym4, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50)
                                .addGroup(numSymLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButtonNumSymBaseline)
                                    .addComponent(jLabel23)
                                    .addComponent(jRadioButtonNumSymTrain)
                                    .addComponent(numSymResetButton)
                                    .addComponent(jRadioButtonNumSym_Rehearsal))))
                        .addContainerGap(197, Short.MAX_VALUE))))
        );
        numSymLayout.setVerticalGroup(
            numSymLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(numSymLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(numSymLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(numSymLayout.createSequentialGroup()
                        .addComponent(jLabelNumSymImg, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonNumSym1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonNumSym2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonNumSym3))
                    .addGroup(numSymLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonNumSymBaseline)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonNumSymTrain)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonNumSym_Rehearsal)
                        .addGap(18, 18, 18)
                        .addComponent(numSymResetButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButtonNumSym4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButtonNumSym5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButtonNumSym6)
                .addContainerGap(125, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("2-Numbers & Symbols", numSym);

        assignComComboImgChoices();
        commonCombo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                commonComboFocusGained(evt);
            }
        });

        comComboResetButton.setBackground(new java.awt.Color(255, 255, 102));
        comComboResetButton.setText("RESET");
        comComboResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comComboResetButtonActionPerformed(evt);
            }
        });

        jLabelComComboImg.setBackground(java.awt.Color.white);
        jLabelComComboImg.setIcon(CurrentCard.getScaledImage());

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Select Test Type");

        buttonGroupComComboSessionType.add(jRadioButtonComComboBaseline);
        jRadioButtonComComboBaseline.setText("Baseline");
        jRadioButtonComComboBaseline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonComComboBaselineActionPerformed(evt);
            }
        });

        buttonGroupComComboSessionType.add(jRadioButtonComComboTrain);
        jRadioButtonComComboTrain.setText("Training");
        jRadioButtonComComboTrain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonComComboTrainActionPerformed(evt);
            }
        });

        buttonGroupComCombo.add(jRadioButtonComCombo1);
        jRadioButtonComCombo1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonComCombo1.setText(Ans1);
        jRadioButtonComCombo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonComCombo1ActionPerformed(evt);
            }
        });
        // Code taken from http://journals.ecs.soton.ac.uk/java/tutorial/post1.0/ui/radiobutton.html

        buttonGroupComCombo.add(jRadioButtonComCombo2);
        jRadioButtonComCombo2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonComCombo2.setText(Ans3);
        jRadioButtonComCombo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonComCombo2ActionPerformed(evt);
            }
        });

        buttonGroupComCombo.add(jRadioButtonComCombo3);
        jRadioButtonComCombo3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonComCombo3.setText(Ans5);
        jRadioButtonComCombo3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonComCombo3ActionPerformed(evt);
            }
        });

        buttonGroupComCombo.add(jRadioButtonComCombo4);
        jRadioButtonComCombo4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonComCombo4.setText(Ans2);
        jRadioButtonComCombo4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonComCombo4ActionPerformed(evt);
            }
        });

        buttonGroupComCombo.add(jRadioButtonComCombo5);
        jRadioButtonComCombo5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonComCombo5.setText(Ans4);
        jRadioButtonComCombo5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonComCombo5ActionPerformed(evt);
            }
        });

        buttonGroupComCombo.add(jRadioButtonComCombo6);
        jRadioButtonComCombo6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonComCombo6.setText(Ans6);
        jRadioButtonComCombo6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonComCombo6ActionPerformed(evt);
            }
        });

        buttonGroupComComboSessionType.add(jRadioButtonCommCombo_Rehearsal);
        jRadioButtonCommCombo_Rehearsal.setText("Rehearsal");

        javax.swing.GroupLayout commonComboLayout = new javax.swing.GroupLayout(commonCombo);
        commonCombo.setLayout(commonComboLayout);
        commonComboLayout.setHorizontalGroup(
            commonComboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(commonComboLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(commonComboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButtonComCombo5, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(commonComboLayout.createSequentialGroup()
                        .addGroup(commonComboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(commonComboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jRadioButtonComCombo6, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                                .addComponent(jRadioButtonComCombo3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jRadioButtonComCombo1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabelComComboImg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                                .addComponent(jRadioButtonComCombo2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jRadioButtonComCombo4, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(commonComboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addGroup(commonComboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jRadioButtonComComboBaseline)
                                .addComponent(jRadioButtonComComboTrain, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jRadioButtonCommCombo_Rehearsal)
                            .addComponent(comComboResetButton))))
                .addGap(50, 50, 50))
        );
        commonComboLayout.setVerticalGroup(
            commonComboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(commonComboLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(commonComboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(commonComboLayout.createSequentialGroup()
                        .addComponent(jLabelComComboImg, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonComCombo1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonComCombo2)
                        .addComponent(jRadioButtonComCombo3)
                        .addGap(0, 0, 0)
                        .addComponent(jRadioButtonComCombo4)
                        .addGap(0, 2, Short.MAX_VALUE)
                        .addComponent(jRadioButtonComCombo5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonComCombo6)
                        .addContainerGap(123, Short.MAX_VALUE))
                    .addGroup(commonComboLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonComComboBaseline)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonComComboTrain)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonCommCombo_Rehearsal)
                        .addGap(18, 18, 18)
                        .addComponent(comComboResetButton)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPane1.addTab("3-Common Combos", commonCombo);

        assignWordImgChoices();
        words.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                wordsFocusGained(evt);
            }
        });

        wordResetButton.setBackground(new java.awt.Color(255, 255, 102));
        wordResetButton.setText("RESET");
        wordResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wordResetButtonActionPerformed(evt);
            }
        });

        jLabelWordImg.setBackground(java.awt.Color.white);
        jLabelWordImg.setIcon(CurrentCard.getScaledImage());

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("Select Test Type");

        buttonGroupWordsSessionType.add(jRadioButtonWordsBaseline);
        jRadioButtonWordsBaseline.setText("Baseline");
        jRadioButtonWordsBaseline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonWordsBaselineActionPerformed(evt);
            }
        });

        buttonGroupWordsSessionType.add(jRadioButtonWordTrain);
        jRadioButtonWordTrain.setText("Training");
        jRadioButtonWordTrain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonWordTrainActionPerformed(evt);
            }
        });

        buttonGroupWords.add(jRadioButtonWord1);
        jRadioButtonWord1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonWord1.setText(Ans1);
        jRadioButtonWord1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonWord1ActionPerformed(evt);
            }
        });
        // Code taken from http://journals.ecs.soton.ac.uk/java/tutorial/post1.0/ui/radiobutton.html

        buttonGroupWords.add(jRadioButtonWord2);
        jRadioButtonWord2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonWord2.setText(Ans3);
        jRadioButtonWord2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonWord2ActionPerformed(evt);
            }
        });

        buttonGroupWords.add(jRadioButtonWord3);
        jRadioButtonWord3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonWord3.setText(Ans5);
        jRadioButtonWord3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonWord3ActionPerformed(evt);
            }
        });

        buttonGroupWords.add(jRadioButtonWord4);
        jRadioButtonWord4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonWord4.setText(Ans2);
        jRadioButtonWord4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonWord4ActionPerformed(evt);
            }
        });

        buttonGroupWords.add(jRadioButtonWord5);
        jRadioButtonWord5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonWord5.setText(Ans4);
        jRadioButtonWord5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonWord5ActionPerformed(evt);
            }
        });

        buttonGroupWords.add(jRadioButtonWord6);
        jRadioButtonWord6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonWord6.setText(Ans6);
        jRadioButtonWord6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonWord6ActionPerformed(evt);
            }
        });

        buttonGroupWordsSessionType.add(jRadioButtonWord_A_Rehearsal);
        jRadioButtonWord_A_Rehearsal.setText("Rehearsal");
        jRadioButtonWord_A_Rehearsal.setToolTipText("");

        javax.swing.GroupLayout wordsLayout = new javax.swing.GroupLayout(words);
        words.setLayout(wordsLayout);
        wordsLayout.setHorizontalGroup(
            wordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wordsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(wordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jRadioButtonWord6, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .addComponent(jRadioButtonWord5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButtonWord4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButtonWord3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButtonWord1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelWordImg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .addComponent(jRadioButtonWord2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50)
                .addGroup(wordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButtonWordsBaseline)
                    .addComponent(jRadioButtonWordTrain)
                    .addComponent(jLabel21)
                    .addComponent(jRadioButtonWord_A_Rehearsal)
                    .addComponent(wordResetButton))
                .addContainerGap(197, Short.MAX_VALUE))
        );
        wordsLayout.setVerticalGroup(
            wordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wordsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(wordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(wordsLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonWordsBaseline)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonWordTrain)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonWord_A_Rehearsal)
                        .addGap(18, 18, 18)
                        .addComponent(wordResetButton))
                    .addGroup(wordsLayout.createSequentialGroup()
                        .addComponent(jLabelWordImg, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonWord1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonWord2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonWord3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButtonWord4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButtonWord5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButtonWord6)
                .addContainerGap(125, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("4-Words A", words);

        assignWords_B_ImgChoices();
        words_B.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                words_BFocusGained(evt);
            }
        });

        word_B_ResetButton.setBackground(new java.awt.Color(255, 255, 102));
        word_B_ResetButton.setText("RESET");
        word_B_ResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                word_B_ResetButtonActionPerformed(evt);
            }
        });

        jLabelWord_B_Img.setBackground(java.awt.Color.white);
        jLabelWord_B_Img.setIcon(CurrentCard.getScaledImage());

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setText("Select Test Type");

        buttonGroupWords_B_SessionType.add(jRadioButtonWords_B_Baseline1);
        jRadioButtonWords_B_Baseline1.setText("Baseline");
        jRadioButtonWords_B_Baseline1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonWords_B_Baseline1ActionPerformed(evt);
            }
        });

        buttonGroupWords_B_SessionType.add(jRadioButtonWord_B_Train);
        jRadioButtonWord_B_Train.setText("Training");
        jRadioButtonWord_B_Train.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonWord_B_TrainActionPerformed(evt);
            }
        });

        buttonGroupWords_B.add(jRadioButtonWord_B_1);
        jRadioButtonWord_B_1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonWord_B_1.setText(Ans1);
        jRadioButtonWord_B_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonWord_B_1ActionPerformed(evt);
            }
        });
        // Code taken from http://journals.ecs.soton.ac.uk/java/tutorial/post1.0/ui/radiobutton.html

        buttonGroupWords_B.add(jRadioButtonWord_B_2);
        jRadioButtonWord_B_2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonWord_B_2.setText(Ans3);
        jRadioButtonWord_B_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonWord_B_2ActionPerformed(evt);
            }
        });

        buttonGroupWords_B.add(jRadioButtonWord_B_3);
        jRadioButtonWord_B_3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonWord_B_3.setText(Ans5);
        jRadioButtonWord_B_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonWord_B_3ActionPerformed(evt);
            }
        });

        buttonGroupWords_B.add(jRadioButtonWord_B_4);
        jRadioButtonWord_B_4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonWord_B_4.setText(Ans2);
        jRadioButtonWord_B_4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonWord_B_4ActionPerformed(evt);
            }
        });

        buttonGroupWords_B.add(jRadioButtonWord_B_5);
        jRadioButtonWord_B_5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonWord_B_5.setText(Ans4);
        jRadioButtonWord_B_5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonWord_B_5ActionPerformed(evt);
            }
        });

        buttonGroupWords_B.add(jRadioButtonWord_B_6);
        jRadioButtonWord_B_6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonWord_B_6.setText(Ans6);
        jRadioButtonWord_B_6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonWord_B_6ActionPerformed(evt);
            }
        });

        buttonGroupWords_B_SessionType.add(jRadioButtonWord_B_Rehearsal);
        jRadioButtonWord_B_Rehearsal.setText("Rehearsal");

        javax.swing.GroupLayout words_BLayout = new javax.swing.GroupLayout(words_B);
        words_B.setLayout(words_BLayout);
        words_BLayout.setHorizontalGroup(
            words_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(words_BLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(words_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jRadioButtonWord_B_6, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .addComponent(jRadioButtonWord_B_5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButtonWord_B_4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButtonWord_B_3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButtonWord_B_1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelWord_B_Img, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButtonWord_B_2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50)
                .addGroup(words_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jRadioButtonWords_B_Baseline1)
                    .addComponent(jRadioButtonWord_B_Train)
                    .addComponent(jRadioButtonWord_B_Rehearsal)
                    .addComponent(word_B_ResetButton))
                .addContainerGap(197, Short.MAX_VALUE))
        );
        words_BLayout.setVerticalGroup(
            words_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(words_BLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(words_BLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(words_BLayout.createSequentialGroup()
                        .addComponent(jLabelWord_B_Img, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonWord_B_1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonWord_B_2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonWord_B_3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonWord_B_4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonWord_B_5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonWord_B_6))
                    .addGroup(words_BLayout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonWords_B_Baseline1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonWord_B_Train)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonWord_B_Rehearsal)
                        .addGap(18, 18, 18)
                        .addComponent(word_B_ResetButton)))
                .addContainerGap(125, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("5-Words B", words_B);

        assignWords_C_ImgChoices();
        words_C.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                words_CFocusGained(evt);
            }
        });

        word_C_ResetButton.setBackground(new java.awt.Color(255, 255, 102));
        word_C_ResetButton.setText("RESET");
        word_C_ResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                word_C_ResetButtonActionPerformed(evt);
            }
        });

        jLabelWord_C_Img.setBackground(java.awt.Color.white);
        jLabelWord_C_Img.setIcon(CurrentCard.getScaledImage());

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setText("Select Test Type");

        buttonGroupWords_C_SessionType.add(jRadioButtonWords_C_Baseline);
        jRadioButtonWords_C_Baseline.setText("Baseline");
        jRadioButtonWords_C_Baseline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonWords_C_BaselineActionPerformed(evt);
            }
        });

        buttonGroupWords_C_SessionType.add(jRadioButtonWord_C_Train);
        jRadioButtonWord_C_Train.setText("Training");
        jRadioButtonWord_C_Train.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonWord_C_TrainActionPerformed(evt);
            }
        });

        buttonGroupWords_C.add(jRadioButtonWord_C_1);
        jRadioButtonWord_C_1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonWord_C_1.setText(Ans1);
        jRadioButtonWord_C_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonWord_C_1ActionPerformed(evt);
            }
        });
        // Code taken from http://journals.ecs.soton.ac.uk/java/tutorial/post1.0/ui/radiobutton.html

        buttonGroupWords_C.add(jRadioButtonWord_C_2);
        jRadioButtonWord_C_2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonWord_C_2.setText(Ans3);
        jRadioButtonWord_C_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonWord_C_2ActionPerformed(evt);
            }
        });

        buttonGroupWords_C.add(jRadioButtonWord_C_3);
        jRadioButtonWord_C_3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonWord_C_3.setText(Ans5);
        jRadioButtonWord_C_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonWord_C_3ActionPerformed(evt);
            }
        });

        buttonGroupWords_C.add(jRadioButtonWord_C_4);
        jRadioButtonWord_C_4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonWord_C_4.setText(Ans2);
        jRadioButtonWord_C_4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonWord_C_4ActionPerformed(evt);
            }
        });

        buttonGroupWords_C.add(jRadioButtonWord_C_5);
        jRadioButtonWord_C_5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonWord_C_5.setText(Ans4);
        jRadioButtonWord_C_5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonWord_C_5ActionPerformed(evt);
            }
        });

        buttonGroupWords_C.add(jRadioButtonWord_C_6);
        jRadioButtonWord_C_6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonWord_C_6.setText(Ans6);
        jRadioButtonWord_C_6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonWord_C_6ActionPerformed(evt);
            }
        });

        buttonGroupWords_C_SessionType.add(jRadioButtonWord_C_Rehearsal);
        jRadioButtonWord_C_Rehearsal.setText("Rehearsal");

        javax.swing.GroupLayout words_CLayout = new javax.swing.GroupLayout(words_C);
        words_C.setLayout(words_CLayout);
        words_CLayout.setHorizontalGroup(
            words_CLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(words_CLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(words_CLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jRadioButtonWord_C_6, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .addComponent(jRadioButtonWord_C_5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButtonWord_C_4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButtonWord_C_3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButtonWord_C_1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButtonWord_C_2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelWord_C_Img, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50)
                .addGroup(words_CLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addComponent(jRadioButtonWords_C_Baseline)
                    .addComponent(jRadioButtonWord_C_Train)
                    .addComponent(jRadioButtonWord_C_Rehearsal)
                    .addComponent(word_C_ResetButton))
                .addContainerGap(197, Short.MAX_VALUE))
        );
        words_CLayout.setVerticalGroup(
            words_CLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(words_CLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(words_CLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(words_CLayout.createSequentialGroup()
                        .addComponent(jLabelWord_C_Img, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonWord_C_1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonWord_C_2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonWord_C_3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonWord_C_4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonWord_C_5))
                    .addGroup(words_CLayout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonWords_C_Baseline)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonWord_C_Train)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonWord_C_Rehearsal)
                        .addGap(18, 18, 18)
                        .addComponent(word_C_ResetButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButtonWord_C_6)
                .addContainerGap(125, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("6-Words C", words_C);

        assignAllImgChoices();
        all.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                allFocusGained(evt);
            }
        });

        allResetButton.setBackground(new java.awt.Color(255, 255, 102));
        allResetButton.setText("RESET");
        allResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allResetButtonActionPerformed(evt);
            }
        });

        jLabelAllImg.setBackground(java.awt.Color.white);
        jLabelAllImg.setIcon(CurrentCard.getScaledImage());

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("Select Test Type");

        buttonGroupAllSessionType.add(jRadioButtonAllBaseline);
        jRadioButtonAllBaseline.setText("Baseline");
        jRadioButtonAllBaseline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonAllBaselineActionPerformed(evt);
            }
        });

        buttonGroupAllSessionType.add(jRadioButtonAllTrain);
        jRadioButtonAllTrain.setText("Training");
        jRadioButtonAllTrain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonAllTrainActionPerformed(evt);
            }
        });

        buttonGroupAll.add(jRadioButtonAll1);
        jRadioButtonAll1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonAll1.setText(Ans1);
        jRadioButtonAll1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonAll1ActionPerformed(evt);
            }
        });
        // Code taken from http://journals.ecs.soton.ac.uk/java/tutorial/post1.0/ui/radiobutton.html

        buttonGroupAll.add(jRadioButtonAll2);
        jRadioButtonAll2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonAll2.setText(Ans3);
        jRadioButtonAll2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonAll2ActionPerformed(evt);
            }
        });

        buttonGroupAll.add(jRadioButtonAll3);
        jRadioButtonAll3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonAll3.setText(Ans5);
        jRadioButtonAll3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonAll3ActionPerformed(evt);
            }
        });

        buttonGroupAll.add(jRadioButtonAll4);
        jRadioButtonAll4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonAll4.setText(Ans2);
        jRadioButtonAll4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonAll4ActionPerformed(evt);
            }
        });

        buttonGroupAll.add(jRadioButtonAll5);
        jRadioButtonAll5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonAll5.setText(Ans4);
        jRadioButtonAll5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonAll5ActionPerformed(evt);
            }
        });

        buttonGroupAll.add(jRadioButtonAll6);
        jRadioButtonAll6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonAll6.setText(Ans6);
        jRadioButtonAll6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonAll6ActionPerformed(evt);
            }
        });

        jTextArea2.setEditable(false);
        jTextArea2.setBackground(new java.awt.Color(191, 205, 219));
        jTextArea2.setColumns(20);
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jTextArea2.setText("This tab will present 30 Braille dot matices, a mix of 5 each of the ABCs, Numbers and Symbols, Common Combos and the three sets of Words.");
        jScrollPane2.setViewportView(jTextArea2);

        javax.swing.GroupLayout allLayout = new javax.swing.GroupLayout(all);
        all.setLayout(allLayout);
        allLayout.setHorizontalGroup(
            allLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(allLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(allLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jRadioButtonAll2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButtonAll1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelAllImg, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .addComponent(jRadioButtonAll5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButtonAll4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButtonAll3, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .addComponent(jRadioButtonAll6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50)
                .addGroup(allLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addGroup(allLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jRadioButtonAllBaseline)
                        .addComponent(jRadioButtonAllTrain, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(allResetButton)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 57, Short.MAX_VALUE))
        );
        allLayout.setVerticalGroup(
            allLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(allLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(allLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(allLayout.createSequentialGroup()
                        .addComponent(jLabelAllImg, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonAll1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonAll2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonAll3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonAll4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonAll5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonAll6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(allLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonAllBaseline)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonAllTrain)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(allResetButton)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 101, Short.MAX_VALUE))))
        );

        jTabbedPane1.addTab("All", all);

        assignAllImgChoices();
        probe.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        probe.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                probeFocusGained(evt);
            }
        });

        jLabelProbeImg.setBackground(java.awt.Color.white);
        jLabelProbeImg.setIcon(CurrentCard.getScaledImage());

        buttonGroupProbe.add(jRadioButtonProbe1);
        jRadioButtonProbe1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonProbe1.setText(Ans1);
        jRadioButtonProbe1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonProbe1ActionPerformed(evt);
            }
        });
        // Code taken from http://journals.ecs.soton.ac.uk/java/tutorial/post1.0/ui/radiobutton.html

        buttonGroupProbe.add(jRadioButtonProbe2);
        jRadioButtonProbe2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonProbe2.setText(Ans3);
        jRadioButtonProbe2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonProbe2ActionPerformed(evt);
            }
        });

        buttonGroupProbe.add(jRadioButtonProbe3);
        jRadioButtonProbe3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonProbe3.setText(Ans5);
        jRadioButtonProbe3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonProbe3ActionPerformed(evt);
            }
        });

        buttonGroupProbe.add(jRadioButtonProbe4);
        jRadioButtonProbe4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonProbe4.setText(Ans2);
        jRadioButtonProbe4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonProbe4ActionPerformed(evt);
            }
        });

        buttonGroupProbe.add(jRadioButtonProbe5);
        jRadioButtonProbe5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonProbe5.setText(Ans4);
        jRadioButtonProbe5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonProbe5ActionPerformed(evt);
            }
        });

        buttonGroupProbe.add(jRadioButtonProbe6);
        jRadioButtonProbe6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButtonProbe6.setText(Ans6);
        jRadioButtonProbe6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonProbe6ActionPerformed(evt);
            }
        });

        jTextAreaProbe.setEditable(false);
        jTextAreaProbe.setBackground(new java.awt.Color(191, 205, 219));
        jTextAreaProbe.setColumns(20);
        jTextAreaProbe.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextAreaProbe.setRows(7);
        jTextAreaProbe.setText("This tab presents 40 randomly selected \ncards, 10 each from the Common Combos,\nWords A, B and C tabs. There is no feedback.\nYou will be notified when the set has been\ncompleted. ");
        jScrollPaneProbe.setViewportView(jTextAreaProbe);

        buttonStartProbe.setBackground(new java.awt.Color(0, 255, 0));
        buttonStartProbe.setLabel("Start Probe");
        buttonStartProbe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonStartProbeActionPerformed(evt);
            }
        });

        buttonResetProbe.setBackground(new java.awt.Color(255, 255, 0));
        buttonResetProbe.setLabel("Reset Probe");
        buttonResetProbe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonResetProbeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout probeLayout = new javax.swing.GroupLayout(probe);
        probe.setLayout(probeLayout);
        probeLayout.setHorizontalGroup(
            probeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(probeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(probeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButtonProbe6, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(probeLayout.createSequentialGroup()
                        .addGroup(probeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButtonProbe1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelProbeImg, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButtonProbe2, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButtonProbe3, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButtonProbe4, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButtonProbe5, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(probeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(probeLayout.createSequentialGroup()
                                .addComponent(buttonStartProbe, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonResetProbe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPaneProbe, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(19, 19, 19))
        );
        probeLayout.setVerticalGroup(
            probeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(probeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(probeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(probeLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jScrollPaneProbe, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(probeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(probeLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonResetProbe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(probeLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(buttonStartProbe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(probeLayout.createSequentialGroup()
                        .addComponent(jLabelProbeImg, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonProbe1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonProbe2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonProbe3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonProbe4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonProbe5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButtonProbe6)
                .addContainerGap(123, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Probe", probe);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void probeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_probeFocusGained
        // TODO add your handling code here:
        currentTabImgLabel = jLabelProbeImg;
    }//GEN-LAST:event_probeFocusGained

    private void buttonResetProbeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonResetProbeActionPerformed
        // TODO add your handling code here:

        // Changed to from assignAllImgChoices() to
        // assignABCImgChoices() 9/17/2013
        assignProbeChoices();

        genericReset();
    }//GEN-LAST:event_buttonResetProbeActionPerformed

    private void buttonStartProbeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonStartProbeActionPerformed
        // TODO add your handling code here:

        if (file == null) {

            JOptionPane.showMessageDialog(this, "PLEASE GO TO CREATE LOG TAB\n"
                + " AND CREATE A LOG FILE!",
                "CREATE LOG FILE!", JOptionPane.ERROR_MESSAGE);

        }

        if (file != null){

            // Set test type string.
            testType = "Probe";

            // Set generic flags.
            baseline = false;
            training = false;
            rehearsal = false;

            // Assign image and buttons
            assignProbeChoices();

            // Set questionAnswered flag as True to get first card.
            questionAnswered = true;

            runProbe();
        }

    }//GEN-LAST:event_buttonStartProbeActionPerformed

    private void jRadioButtonProbe6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonProbe6ActionPerformed
        // TODO add your handling code here:
        processAns(Ans6);
    }//GEN-LAST:event_jRadioButtonProbe6ActionPerformed

    private void jRadioButtonProbe5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonProbe5ActionPerformed
        // TODO add your handling code here:
        processAns(Ans5);
    }//GEN-LAST:event_jRadioButtonProbe5ActionPerformed

    private void jRadioButtonProbe4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonProbe4ActionPerformed
        // TODO add your handling code here:
        processAns(Ans4);
    }//GEN-LAST:event_jRadioButtonProbe4ActionPerformed

    private void jRadioButtonProbe3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonProbe3ActionPerformed
        // TODO add your handling code here:
        processAns(Ans3);
    }//GEN-LAST:event_jRadioButtonProbe3ActionPerformed

    private void jRadioButtonProbe2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonProbe2ActionPerformed
        // TODO add your handling code here:
        processAns(Ans2);
    }//GEN-LAST:event_jRadioButtonProbe2ActionPerformed

    private void jRadioButtonProbe1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonProbe1ActionPerformed
        // TODO add your handling code here:
        processAns(Ans1);
    }//GEN-LAST:event_jRadioButtonProbe1ActionPerformed

    private void allFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_allFocusGained
        // TODO add your handling code here:
        currentTabImgLabel = jLabelAllImg;
    }//GEN-LAST:event_allFocusGained

    private void jRadioButtonAll6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonAll6ActionPerformed
        // TODO add your handling code here:
        processAns(Ans6);
    }//GEN-LAST:event_jRadioButtonAll6ActionPerformed

    private void jRadioButtonAll5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonAll5ActionPerformed
        // TODO add your handling code here:
        processAns(Ans5);
    }//GEN-LAST:event_jRadioButtonAll5ActionPerformed

    private void jRadioButtonAll4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonAll4ActionPerformed
        // TODO add your handling code here:
        processAns(Ans4);
    }//GEN-LAST:event_jRadioButtonAll4ActionPerformed

    private void jRadioButtonAll3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonAll3ActionPerformed
        // TODO add your handling code here:
        processAns(Ans3);
    }//GEN-LAST:event_jRadioButtonAll3ActionPerformed

    private void jRadioButtonAll2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonAll2ActionPerformed
        // TODO add your handling code here:
        processAns(Ans2);
    }//GEN-LAST:event_jRadioButtonAll2ActionPerformed

    private void jRadioButtonAll1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonAll1ActionPerformed
        // TODO add your handling code here:
        processAns(Ans1);
    }//GEN-LAST:event_jRadioButtonAll1ActionPerformed

    private void jRadioButtonAllTrainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonAllTrainActionPerformed
        // TODO add your handling code here:
        if (baseline == true) {

            resetTabAll();

            JOptionPane.showMessageDialog(this, "Select Training again if you wish to "
                + "\nchange from Baseline to Training.", "Select Training Again", JOptionPane.INFORMATION_MESSAGE);

        } else {

            baselineAll = false;
            trainingAll = true;

            // Set test type string.
            testType = "Training";

            // Set generic flags.
            baseline = false;
            training = true;

            // Assign image and buttons.
            assignAllImgChoices();

            // Set questionAnswered flag as True to get first card.
            questionAnswered = true;

            getNextCard();

        }
    }//GEN-LAST:event_jRadioButtonAllTrainActionPerformed

    private void jRadioButtonAllBaselineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonAllBaselineActionPerformed
        // TODO add your handling code here:
        if (training == true) {

            resetTabAll();

            JOptionPane.showMessageDialog(this, "Select Baseline again if you wish to "
                + "\nchange from Training to Baseline.", "Select Baseline Again", JOptionPane.INFORMATION_MESSAGE);

        } else {
            baselineAll = true;
            trainingAll = false;

            // Set test type string.
            testType = "Baseline";

            // Set generic flags.
            baseline = true;
            training = false;
            // Assign image and buttons
            assignAllImgChoices();

            // Set questionAnswered flag as True to get first card.
            questionAnswered = true;

            getNextCard();
        }
    }//GEN-LAST:event_jRadioButtonAllBaselineActionPerformed

    private void allResetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allResetButtonActionPerformed
        // TODO add your handling code here:
        resetTabAll();
    }//GEN-LAST:event_allResetButtonActionPerformed

    private void words_CFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_words_CFocusGained
        // TODO add your handling code here:
        currentTabImgLabel = jLabelWord_C_Img;
    }//GEN-LAST:event_words_CFocusGained

    private void jRadioButtonWord_C_6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonWord_C_6ActionPerformed
        // TODO add your handling code here:
        processAns(Ans6);
    }//GEN-LAST:event_jRadioButtonWord_C_6ActionPerformed

    private void jRadioButtonWord_C_5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonWord_C_5ActionPerformed
        // TODO add your handling code here:
        processAns(Ans5);
    }//GEN-LAST:event_jRadioButtonWord_C_5ActionPerformed

    private void jRadioButtonWord_C_4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonWord_C_4ActionPerformed
        // TODO add your handling code here:
        processAns(Ans4);
    }//GEN-LAST:event_jRadioButtonWord_C_4ActionPerformed

    private void jRadioButtonWord_C_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonWord_C_3ActionPerformed
        // TODO add your handling code here:
        processAns(Ans3);
    }//GEN-LAST:event_jRadioButtonWord_C_3ActionPerformed

    private void jRadioButtonWord_C_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonWord_C_2ActionPerformed
        // TODO add your handling code here:
        processAns(Ans2);
    }//GEN-LAST:event_jRadioButtonWord_C_2ActionPerformed

    private void jRadioButtonWord_C_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonWord_C_1ActionPerformed
        // TODO add your handling code here:
        processAns(Ans1);
    }//GEN-LAST:event_jRadioButtonWord_C_1ActionPerformed

    private void jRadioButtonWord_C_TrainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonWord_C_TrainActionPerformed
        // TODO add your handling code here:
        // Check if generic training has been set true by user
        // working with ANY tab.
        if (baseline == true) {

            resetTabWords_C();

            JOptionPane.showMessageDialog(this, "Select Training again if you wish to "
                + "\nchange from Baseline to Training.", "Select Training Again", JOptionPane.INFORMATION_MESSAGE);

        } else {

            baselineWords_C = false;
            trainingWords_C = true;

            // Set test type string.
            testType = "Training";

            // Set generic flags.
            baseline = false;
            training = true;

            // Assign Image and buttons.
            assignWords_C_ImgChoices();

            // Set questionAnswered flag as True to get first card.
            questionAnswered = true;

            loadFirstWord_C_Group();

            getNextCard();

        }

    }//GEN-LAST:event_jRadioButtonWord_C_TrainActionPerformed

    private void jRadioButtonWords_C_BaselineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonWords_C_BaselineActionPerformed
        // TODO add your handling code here:
        // Check if generic training has been set true by user
        // working with ANY tab.
        if (training == true) {

            resetTabWords_C();

            JOptionPane.showMessageDialog(this, "Select Baseline again if you wish to "
                + "\nchange from Training to Baseline.", "Select Baseline Again", JOptionPane.INFORMATION_MESSAGE);

        } else {
            baselineWords_C = true;
            trainingWords_C = false;

            // Set test type string.
            testType = "Baseline";

            // Set generic flags.
            baseline = true;
            training = false;

            // Assign Image and buttons.
            assignWords_C_ImgChoices();

            // Set questionAnswered flag as True to get first card.
            questionAnswered = true;

            getNextCard();

        }
    }//GEN-LAST:event_jRadioButtonWords_C_BaselineActionPerformed

    private void word_C_ResetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_word_C_ResetButtonActionPerformed
        // TODO add your handling code here:
        resetTabWords_C();
    }//GEN-LAST:event_word_C_ResetButtonActionPerformed

    private void words_BFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_words_BFocusGained
        // TODO add your handling code here:
        currentTabImgLabel = jLabelWord_B_Img;
    }//GEN-LAST:event_words_BFocusGained

    private void jRadioButtonWord_B_6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonWord_B_6ActionPerformed
        // TODO add your handling code here:
        processAns(Ans6);
    }//GEN-LAST:event_jRadioButtonWord_B_6ActionPerformed

    private void jRadioButtonWord_B_5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonWord_B_5ActionPerformed
        // TODO add your handling code here:
        processAns(Ans5);
    }//GEN-LAST:event_jRadioButtonWord_B_5ActionPerformed

    private void jRadioButtonWord_B_4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonWord_B_4ActionPerformed
        // TODO add your handling code here:
        processAns(Ans4);
    }//GEN-LAST:event_jRadioButtonWord_B_4ActionPerformed

    private void jRadioButtonWord_B_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonWord_B_3ActionPerformed
        // TODO add your handling code here:
        processAns(Ans3);
    }//GEN-LAST:event_jRadioButtonWord_B_3ActionPerformed

    private void jRadioButtonWord_B_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonWord_B_2ActionPerformed
        // TODO add your handling code here:
        processAns(Ans2);
    }//GEN-LAST:event_jRadioButtonWord_B_2ActionPerformed

    private void jRadioButtonWord_B_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonWord_B_1ActionPerformed
        // TODO add your handling code here:
        processAns(Ans1);
    }//GEN-LAST:event_jRadioButtonWord_B_1ActionPerformed

    private void jRadioButtonWord_B_TrainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonWord_B_TrainActionPerformed
        // TODO add your handling code here:
        // Check if generic training has been set true by user
        // working with ANY tab.
        if (baseline == true) {

            resetTabWords_B();

            JOptionPane.showMessageDialog(this, "Select Training again if you wish to "
                + "\nchange from Baseline to Training.", "Select Training Again", JOptionPane.INFORMATION_MESSAGE);

        } else {

            baselineWords_B = false;
            trainingWords_B = true;

            // Set test type string.
            testType = "Training";

            // Set generic flags.
            baseline = false;
            training = true;

            // Assign Image and buttons.
            assignWords_B_ImgChoices();

            // Set questionAnswered flag as True to get first card.
            questionAnswered = true;

            loadFirstWord_B_Group();

            getNextCard();

        }
    }//GEN-LAST:event_jRadioButtonWord_B_TrainActionPerformed

    private void jRadioButtonWords_B_Baseline1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonWords_B_Baseline1ActionPerformed
        // TODO add your handling code here:
        // Check if generic training has been set true by user
        // working with ANY tab.
        if (training == true) {

            resetTabWords_B();

            JOptionPane.showMessageDialog(this, "Select Baseline again if you wish to "
                + "\nchange from Training to Baseline.", "Select Baseline Again", JOptionPane.INFORMATION_MESSAGE);

        } else {
            baselineWords_B = true;
            trainingWords_B = false;

            // Set test type string.
            testType = "Baseline";

            // Set generic flags.
            baseline = true;
            training = false;

            // Assign Image and buttons.
            assignWords_B_ImgChoices();

            // Set questionAnswered flag as True to get first card.
            questionAnswered = true;

            getNextCard();

        }

    }//GEN-LAST:event_jRadioButtonWords_B_Baseline1ActionPerformed

    private void word_B_ResetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_word_B_ResetButtonActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        resetTabWords_B();

    }//GEN-LAST:event_word_B_ResetButtonActionPerformed

    private void wordsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_wordsFocusGained
        // TODO add your handling code here:
        currentTabImgLabel = jLabelWordImg;
    }//GEN-LAST:event_wordsFocusGained

    private void jRadioButtonWord6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonWord6ActionPerformed
        // TODO add your handling code here:
        processAns(Ans6);
    }//GEN-LAST:event_jRadioButtonWord6ActionPerformed

    private void jRadioButtonWord5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonWord5ActionPerformed
        // TODO add your handling code here:
        processAns(Ans5);
    }//GEN-LAST:event_jRadioButtonWord5ActionPerformed

    private void jRadioButtonWord4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonWord4ActionPerformed
        // TODO add your handling code here:
        processAns(Ans4);
    }//GEN-LAST:event_jRadioButtonWord4ActionPerformed

    private void jRadioButtonWord3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonWord3ActionPerformed
        // TODO add your handling code here:
        processAns(Ans3);
    }//GEN-LAST:event_jRadioButtonWord3ActionPerformed

    private void jRadioButtonWord2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonWord2ActionPerformed
        // TODO add your handling code here:
        processAns(Ans2);
    }//GEN-LAST:event_jRadioButtonWord2ActionPerformed

    private void jRadioButtonWord1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonWord1ActionPerformed
        // TODO add your handling code here:
        processAns(Ans1);
    }//GEN-LAST:event_jRadioButtonWord1ActionPerformed

    private void jRadioButtonWordTrainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonWordTrainActionPerformed
        // TODO add your handling code here:
        // Check if generic baseline has been set true by user
        // working with ANY tab.
        if (baseline == true) {

            resetTabWords();

            JOptionPane.showMessageDialog(this, "Select Training again if you wish to "
                + "\nchange from Baseline to Training.", "Select Training Again", JOptionPane.INFORMATION_MESSAGE);

        } else {

            baselineWords = false;
            trainingWords = true;

            // Set test type string.
            testType = "Training";

            // Set generic flags.
            baseline = false;
            training = true;

            // Assign image and buttons.
            assignWordImgChoices();

            // Set questionAnswered flag as True to get first card.
            questionAnswered = true;

            loadFirstWordGroup();

            getNextCard();

        }
    }//GEN-LAST:event_jRadioButtonWordTrainActionPerformed

    private void jRadioButtonWordsBaselineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonWordsBaselineActionPerformed
        // TODO add your handling code here:

        if (training == true) {

            resetTabWords();

            JOptionPane.showMessageDialog(this, "Select Baseline again if you wish to "
                + "\nchange from Training to Baseline.", "Select Baseline Again", JOptionPane.INFORMATION_MESSAGE);

        } else {
            baselineWords = true;
            trainingWords = false;

            // Set test type string.
            testType = "Baseline";

            // Set generic flags.
            baseline = true;
            training = false;

            // Assign Image and buttons.
            assignWordImgChoices();

            // Set questionAnswered flag as True to get first card.
            questionAnswered = true;

            getNextCard();

        }
    }//GEN-LAST:event_jRadioButtonWordsBaselineActionPerformed

    private void wordResetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wordResetButtonActionPerformed
        // TODO add your handling code here:
        resetTabWords();
    }//GEN-LAST:event_wordResetButtonActionPerformed

    private void numSymFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_numSymFocusGained
        // TODO add your handling code here:
        // Assign NumSym panels img and choices to currentPanel.
        currentTabImgLabel = jLabelNumSymImg;
    }//GEN-LAST:event_numSymFocusGained

    private void jRadioButtonNumSym6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonNumSym6ActionPerformed
        // TODO add your handling code here:
        processAns(Ans6);
    }//GEN-LAST:event_jRadioButtonNumSym6ActionPerformed

    private void jRadioButtonNumSym5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonNumSym5ActionPerformed
        // TODO add your handling code here:
        processAns(Ans5);
    }//GEN-LAST:event_jRadioButtonNumSym5ActionPerformed

    private void jRadioButtonNumSym4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonNumSym4ActionPerformed
        // TODO add your handling code here:
        processAns(Ans4);
    }//GEN-LAST:event_jRadioButtonNumSym4ActionPerformed

    private void jRadioButtonNumSym3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonNumSym3ActionPerformed
        // TODO add your handling code here:
        processAns(Ans3);
    }//GEN-LAST:event_jRadioButtonNumSym3ActionPerformed

    private void jRadioButtonNumSym2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonNumSym2ActionPerformed
        // TODO add your handling code here:
        processAns(Ans2);
    }//GEN-LAST:event_jRadioButtonNumSym2ActionPerformed

    private void jRadioButtonNumSym1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonNumSym1ActionPerformed
        // TODO add your handling code here:
        processAns(Ans1);
    }//GEN-LAST:event_jRadioButtonNumSym1ActionPerformed

    private void jRadioButtonNumSymTrainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonNumSymTrainActionPerformed
        // TODO add your handling code here:
        // Check if generic training has been set true by user
        // working with ANY tab.
        if (baseline == true) {

            resetTabNumSym();

            JOptionPane.showMessageDialog(this, "Select Training again if you wish to "
                + "\nchange from Baseline to Training.", "Select Training Again", JOptionPane.INFORMATION_MESSAGE);

        } else {

            baselineNumSym = false;
            trainingNumSym = true;

            // Set test type string.
            testType = "Training";

            // Set generic flags.
            baseline = false;
            training = true;

            // Assign Image and buttons.
            assignNumSymImgChoices();

            // Set questionAnswered flag as True to get first card.
            questionAnswered = true;

            loadFirstNumSymGroup();

            getNextCard();

        }
    }//GEN-LAST:event_jRadioButtonNumSymTrainActionPerformed

    private void jRadioButtonNumSymBaselineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonNumSymBaselineActionPerformed
        // TODO add your handling code here:
        // Check if generic training has been set true by user
        // working with ANY tab.
        if (training == true) {

            resetTabNumSym();

            JOptionPane.showMessageDialog(this, "Select Baseline again if you wish to "
                + "\nchange from Training to Baseline.", "Select Baseline Again", JOptionPane.INFORMATION_MESSAGE);

        } else {
            baselineNumSym = true;
            trainingNumSym = false;

            // Set test type string.
            testType = "Baseline";

            // Set generic flags.
            baseline = true;
            training = false;

            // Assign Image and buttons.
            assignNumSymImgChoices();

            // Set questionAnswered flag as True to get first card.
            questionAnswered = true;

            getNextCard();

        }
    }//GEN-LAST:event_jRadioButtonNumSymBaselineActionPerformed

    private void numSymResetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numSymResetButtonActionPerformed
        // TODO add your handling code here:
        resetTabNumSym();
    }//GEN-LAST:event_numSymResetButtonActionPerformed

    private void abcsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_abcsFocusGained
        // TODO add your handling code here:
        // Asign ABC panels img and choices to currentPanel.
        currentTabImgLabel = jLabelAbcImg;
    }//GEN-LAST:event_abcsFocusGained

    private void jRadioButtonABCTrainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonABCTrainActionPerformed
        // TODO add your handling code here:
        // Check if generic baseline has been set true by user
        // working with ANY tab.
        if (baseline == true || rehearsal == true) {

            resetTabABC();

            JOptionPane.showMessageDialog(this, "Select Training again if you wish to "
                + "\nchange from Baseline to Training.", "Select Training Again", JOptionPane.INFORMATION_MESSAGE);

        } else {

            baselineABC = false;
            trainingABC = true;
            rehearsalABC = false;

            // Set test type string.
            testType = "Training";

            // Set generic flags.
            baseline = false;
            training = true;
            rehearsal = false;

            // Assign image and buttons.
            assignABCImgChoices();

            // Set questionAnswered flag as True to get first card.
            questionAnswered = true;

            loadFirstABCGroup();

            getNextCard();

        }
    }//GEN-LAST:event_jRadioButtonABCTrainActionPerformed

    private void jRadioButtonABCBaselineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonABCBaselineActionPerformed
        // TODO add your handling code here:
        // Check if generic training has been set true by user
        // working with ANY tab
        if (training == true || rehearsal == true) {

            resetTabABC();

            JOptionPane.showMessageDialog(this, "Select Baseline again if you wish to "
                + "\nchange from Training to Baseline.", "Select Baseline Again", JOptionPane.INFORMATION_MESSAGE);

        } else {
            
            baselineABC = true;
            trainingABC = false;
            rehearsalABC = false;

            // Set test type string.
            testType = "Baseline";

            // Set generic flags.
            baseline = true;
            training = false;
            rehearsal = false;
            
            // Assign image and buttons
            assignABCImgChoices();

            // Set questionAnswered flag as True to get first card.
            questionAnswered = true;

            getNextCard();
        }
    }//GEN-LAST:event_jRadioButtonABCBaselineActionPerformed

    
    
    private void jRadioButtonABC6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonABC6ActionPerformed
        // TODO add your handling code here:
        processAns(Ans6);
    }//GEN-LAST:event_jRadioButtonABC6ActionPerformed

    private void jRadioButtonABC5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonABC5ActionPerformed
        // TODO add your handling code here:
        processAns(Ans5);
    }//GEN-LAST:event_jRadioButtonABC5ActionPerformed

    private void jRadioButtonABC4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonABC4ActionPerformed
        // TODO add your handling code here:

        processAns(Ans4);
    }//GEN-LAST:event_jRadioButtonABC4ActionPerformed

    private void jRadioButtonABC3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonABC3ActionPerformed
        // TODO add your handling code here:

        processAns(Ans3);
    }//GEN-LAST:event_jRadioButtonABC3ActionPerformed

    private void jRadioButtonABC2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonABC2ActionPerformed
        // TODO add your handling code here:

        processAns(Ans2);
    }//GEN-LAST:event_jRadioButtonABC2ActionPerformed

    private void jRadioButtonABC1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonABC1ActionPerformed
        // TODO add your handling code here:

        processAns(Ans1);
    }//GEN-LAST:event_jRadioButtonABC1ActionPerformed

    private void abcResetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abcResetButtonActionPerformed
        // TODO add your handling code here:

        resetTabABC();
    }//GEN-LAST:event_abcResetButtonActionPerformed

    private void jButtonCreateLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCreateLogActionPerformed
        // TODO add your handling code here:
        JFileChooser saver = new JFileChooser();

        //timerResponse.start();

        //While loop limit.
        int repeatAndRename = 0;

        //Loop until log file created or operation canceled.
        while (repeatAndRename == 0) {
            int returnVal = saver.showSaveDialog(this);

            if (returnVal == JFileChooser.CANCEL_OPTION) {
                JOptionPane.showMessageDialog(this, "The operation has been canceled by the user!", "CANCELED!", JOptionPane.INFORMATION_MESSAGE);
                repeatAndRename = 1;
            } else {

                file = saver.getSelectedFile();

                // Create test case file with csv extension.
                File fileTest = new File(file.getAbsolutePath() + ".csv");

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    /* Check if file already exists.
                    * Warn user if it does. */

                    /* Initialize reply to 0.
                    * 0 = YES_OPTION of JOptionPane.
                    */
                    int reply = 0;

                    if (fileTest.exists()) {
                        Component frame = null;

                        reply = JOptionPane.showConfirmDialog(
                            frame,
                            "The File already exists. Continue and overwrite existing file?", "OVERWRITE WARNING",
                            /* YES_OPTION = 0,
                            * NO_OPTION = 1. */
                            JOptionPane.YES_NO_OPTION);

                    }

                    /* Since reply is initialized to 0,
                    * this if block will execute even
                    * when the previous file exist check
                    * does not execute.
                    */

                    if (reply == 0) {
                        writeLogHeaderAndData(file);

                        // Set flag to exit while loop.
                        repeatAndRename = 1;

                        // This code was used to test stopwatch class.
                        //                JOptionPane.showMessageDialog(this, "Time to respond: " +
                            //                   timerResponse.getElapsedTimeSecs() + " in seconds.",
                            //                   "TIMER", JOptionPane.INFORMATION_MESSAGE);

                    }

                }

            }// End of else
        }
    }//GEN-LAST:event_jButtonCreateLogActionPerformed

    private void commonComboFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_commonComboFocusGained
        // TODO add your handling code here:
        // Assign ComCombo panels img and choices to currentPanel.
        currentTabImgLabel = jLabelComComboImg;
    }//GEN-LAST:event_commonComboFocusGained

    private void jRadioButtonComCombo6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonComCombo6ActionPerformed
        // TODO add your handling code here:
        processAns(Ans6);
    }//GEN-LAST:event_jRadioButtonComCombo6ActionPerformed

    private void jRadioButtonComCombo5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonComCombo5ActionPerformed
        // TODO add your handling code here:
        processAns(Ans5);
    }//GEN-LAST:event_jRadioButtonComCombo5ActionPerformed

    private void jRadioButtonComCombo4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonComCombo4ActionPerformed
        // TODO add your handling code here:
        processAns(Ans4);
    }//GEN-LAST:event_jRadioButtonComCombo4ActionPerformed

    private void jRadioButtonComCombo3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonComCombo3ActionPerformed
        // TODO add your handling code here:
        processAns(Ans3);
    }//GEN-LAST:event_jRadioButtonComCombo3ActionPerformed

    private void jRadioButtonComCombo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonComCombo2ActionPerformed
        // TODO add your handling code here:
        processAns(Ans2);
    }//GEN-LAST:event_jRadioButtonComCombo2ActionPerformed

    private void jRadioButtonComCombo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonComCombo1ActionPerformed
        // TODO add your handling code here:
        processAns(Ans1);
    }//GEN-LAST:event_jRadioButtonComCombo1ActionPerformed

    private void jRadioButtonComComboTrainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonComComboTrainActionPerformed
        // TODO add your handling code here:
        // Check if generic baseline has been set true by user
        // working with ANY tab.
        if (baseline == true) {

            resetTabComCombo();

            JOptionPane.showMessageDialog(this, "Select Training again if you wish to "
                + "\nchange from Baseline to Training.", "Select Training Again", JOptionPane.INFORMATION_MESSAGE);

        } else {

            baselineComCombo = false;
            trainingComCombo = true;

            // Set test type string.
            testType = "Training";

            // Set generic flags.
            baseline = false;
            training = true;

            // Assign image and buttons.
            assignComComboImgChoices();

            // Set questionAnswered flag as True to get first card.
            questionAnswered = true;

            loadFirstComComboGroup();

            getNextCard();

        }
    }//GEN-LAST:event_jRadioButtonComComboTrainActionPerformed

    private void jRadioButtonComComboBaselineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonComComboBaselineActionPerformed
        // TODO add your handling code here:
        if (training == true) {

            resetTabComCombo();

            JOptionPane.showMessageDialog(this, "Select Baseline again if you wish to "
                + "\nchange from Training to Baseline.", "Select Baseline Again", JOptionPane.INFORMATION_MESSAGE);

        } else {
            baselineComCombo = true;
            trainingComCombo = false;

            // Set test type string.
            testType = "Baseline";

            // Set generic flags.
            baseline = true;
            training = false;

            // Assign Image and buttons.
            assignComComboImgChoices();

            // Set questionAnswered flag as True to get first card.
            questionAnswered = true;

            getNextCard();

        }
    }//GEN-LAST:event_jRadioButtonComComboBaselineActionPerformed

    private void comComboResetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comComboResetButtonActionPerformed
        // TODO add your handling code here:
        resetTabComCombo();
    }//GEN-LAST:event_comComboResetButtonActionPerformed

    private void jRadioButtonABCRehearsalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonABCRehearsalActionPerformed
        // TODO add your handling code here:
        
        // Check if generic baseline has been set true by user
        // working with ANY tab.
        if (baseline == true || training == true) {

            resetTabABC();

            JOptionPane.showMessageDialog(this, "Select Rehearsal again if you wish to "
                + "\nrehearse.", "Select Rehearsal Again", JOptionPane.INFORMATION_MESSAGE);

        } else {

            baselineABC = false;
            trainingABC = false;
            rehearsalABC = true;

            // Set test type string.
            testType = "Rehearsal";
            
            //Set Current Tab.
            CurrentTab = "ABC";

            // Set generic flags.
            baseline = false;
            training = false;
            rehearsal = true;

            // Assign image and buttons.
            assignABCImgChoices();
            
            // Get rehearsal set of 10 cards.
            initRehearsalABC();

            Collections.shuffle(CurrentGrps_CardsList);

            // Set questionAnswered flag as True to get first card.
            questionAnswered = true;

            getNextCard();

        }
        
    }//GEN-LAST:event_jRadioButtonABCRehearsalActionPerformed
 
    private void resetTabABC() {

        baselineABC = false;
        trainingABC = false;
        rehearsalABC = false;

        // Clear test type button selection
        buttonGroupABCSessionType.clearSelection();

        // Changed to from assignAllImgChoices() to
        // assignABCImgChoices() 9/17/2013
        assignABCImgChoices();

        genericReset();

    }

    private void loadFirstABCGroup() {

        // These flags used to select appropriate 
        // reset and list add methods.
        CurrentTab = "ABC";
        LastGrpAdded = "abcGrp1";

        // Reset current list to abcGrp1_CardsList;
        CurrentGrps_CardsList = abcGrp1_CardsList;

        // Shuffle List
        Collections.shuffle(CurrentGrps_CardsList);

        // Set presentation target to three.
        setPresentationTargetToThree(CurrentGrps_CardsList);

    }

    private void resetTabNumSym() {

        baselineNumSym = false;
        trainingNumSym = false;
        rehearsalNumSym = false;

        // Clear test type button selection
        buttonGroupNumSymSessionType.clearSelection();

        loadFirstNumSymGroup();

        genericReset();

    }

    private void resetTabComCombo() {

        baselineNumSym = false;
        trainingNumSym = false;
        rehearsalComCombo = false;

        // Clear test type button selection
        buttonGroupComComboSessionType.clearSelection();

        loadFirstComComboGroup();

        genericReset();

    }

    private void resetTabWords() {

        baselineWords = false;
        trainingWords = false;
        rehearsalWords = false;

        // Clear test type button selection
        buttonGroupWordsSessionType.clearSelection();

        loadFirstWordGroup();

        genericReset();

    }
    
    private void resetTabWords_B() {
    
        baselineWords_B = false;
        trainingWords_B = false;
        rehearsalWords_B = false;

        // Clear test type button selection
        buttonGroupWords_B_SessionType.clearSelection();

        loadFirstWord_B_Group();

        genericReset();
    
    }    

    private void loadFirstNumSymGroup() {

        // These flags used to select appropriate 
        // reset and list add methods.
        CurrentTab = "NumSym";
        LastGrpAdded = "NumSymGrp1";

        // Reset current list to abcGrp1_CardsList;
        CurrentGrps_CardsList = NumSymGrp1_CardsList;

        // Shuffle List
        Collections.shuffle(CurrentGrps_CardsList);

        // Set presentation target to three.
        setPresentationTargetToThree(CurrentGrps_CardsList);

    }

    private void loadFirstComComboGroup() {

        // These flags used to select appropriate 
        // reset and list add methods.
        CurrentTab = "ComCombo";
        LastGrpAdded = "ComComboGrp1";

        // Reset current list to ComComboGrp1_CardsList;
        CurrentGrps_CardsList = ComComboGrp1_CardsList;

        // Shuffle List
        Collections.shuffle(CurrentGrps_CardsList);

        // Set presentation target to three.
        setPresentationTargetToThree(CurrentGrps_CardsList);

    }

    private void loadFirstWord_B_Group() {
         
        // These flags used to select appropriate 
        // reset and list add methods.
        CurrentTab = "Words_B";
        LastGrpAdded = "WrdGrp10";

        // Reset current list to ComComboGrp1_CardsList;
        CurrentGrps_CardsList = initWrdGrp10();

        // Shuffle List
        Collections.shuffle(CurrentGrps_CardsList);

        // Set presentation target to three.
        setPresentationTargetToThree(CurrentGrps_CardsList);    
    
    }    
    
    private void loadFirstWordGroup() {

        // These flags used to select appropriate 
        // reset and list add methods.
        CurrentTab = "Words";
        LastGrpAdded = "WrdGrp1";

        // Reset current list to ComComboGrp1_CardsList;
        CurrentGrps_CardsList = initWrdGrp1();

        // Shuffle List
        Collections.shuffle(CurrentGrps_CardsList);

        // Set presentation target to three.
        setPresentationTargetToThree(CurrentGrps_CardsList);

    }

    private void genericReset() {

        // Stop timerRsponse stopwatch.
        timerResponse.stop();

        baseline = false;
        training = false;
        rehearsal = false;

        // Reset flags.
        // CardListAll set flag
        cardListAllFlag = false;

        // Flag for press of answerButton
        answerButton = false;

        // Flag marking when answer is recorded.
        ansRecorded = false;

        // Set questionAnswered flag as True to get first card.
        questionAnswered = false;

        // Index counter for use when running baseline test.
        indexAll = 0;

        // Flag marking calculation of the score for the 
        // first 15 cards in the last group added.
        first15Calculated = false;

        // Reset to zero
        countQuestionsAnswered = 0.000;
        countCorrectAnswers = 0.000;
        //currentCardCount = 0;

        // Clear Answer button selections.
        resetAnswerButtons();

        // Reset Correct Count for current list.
        resetCorrectCount();

        // Write reset to log file.
        writeResetToLog();

        Collections.shuffle(CurrentGrps_CardsList);

        // Set group name.
        setGroupName();

        // Display BlankCard first. 

        CurrentCard = BlankCard;

        scoreCalculated = false;

        presentationCountEqualsTarget = false;

//        // Use checkConsecutiveCorrect() method to display
//        // BlankCard. Assign return value to itself.
//        consecutiveCorrectLessThanZero = checkConsecutiveCorrect(); 
        displayCurrentCard();

    }

    private void writeLogHeaderAndData(File file) {
        // Buffered writer is faster than file writer.
        BufferedWriter writer;

        try {
            writer = new BufferedWriter(new FileWriter(file.getAbsolutePath() + ".csv"));

            /* Write column headers to file */
            writer.write("Subject ID, Gender, Major, Session ID, ");

            /* Add new line for next row of data. */
            writer.newLine();

            /* Write text fields from Create Log form to file */
            writer.write(jTextFieldSubjectID.getText() + " ," + jTextFieldGender.getText() + " ," + jTextFieldMajor.getText() + " ,"
                    + jTextSessionID.getText());

            /* Add new line for next row of data. */
            writer.newLine();

            /* Write column header for test results. */
            writer.write("Current Tab,Test Type,Correct Answer,"
                    + " Respondent Answer,Result,Presentation"
                    + " Count,"
                    + "Response Time - Seconds,Total Questions Correct,"
                    + "Total Questons Answered,Percentage Correct (Over All),"
                    + "Percent Correct of Last 15 Presentations (Last Group Added Only");

            /* Add new line for next row of data. */
            writer.newLine();

            writer.close();
            JOptionPane.showMessageDialog(this, "The Message was Saved Successfully!",
                    "Success!", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "The Text could not be Saved!",
                    "Error!", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private void writeResults(File file, String selectedAnswer, String correctIncorrect) {
        // Buffered writer is faster than file writer.
        BufferedWriter writer;

        try {
// Example of append to file from: http://www.mkyong.com/java/how-to-append-content-to-file-in-java/
//            //true = append file
//            FileWriter fileWritter = new FileWriter(file.getName(),true);
//            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);

            FileWriter fileWritter = new FileWriter(file.getAbsolutePath() + ".csv", true);

            writer = new BufferedWriter(fileWritter);

            // Added to convert , to comma so it does not get lost in csv.
            selectedAnswer = commaConversion(selectedAnswer);

            /* Write column header for test results. */
            writer.write(groupName + "," + testType + "," + CurrentCard.getCorrectAns() + ","
                    + selectedAnswer + "," + correctIncorrect + ","
                    + CurrentCard.getPresentationCount() + ","
                    + timerResponse.getElapsedTimeSecs() + ","
                    + countCorrectAnswers + "," + countQuestionsAnswered + ","
                    + Double.toString(percentCorrect) + ","
                    + Double.toString(last15PercentCorrect) + ",");

            /* Add new line for next row of data. */
            writer.newLine();

            writer.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "The Text could not be Saved!",
                    "Error!", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private String commaConversion(String selectedAnswer) {

        if (",".equals(CurrentCard.getCorrectAns())) {

            CurrentCard.setCorrectAns("comma");

        }

        // Convert back to "," for message box display after results are 
        // written to csv.
        if ("comma".equals(CurrentCard.getCorrectAns()) && (ansRecorded == true)) {

            CurrentCard.setCorrectAns(",");

        }

        // Convert selected answer to "comma" if ","
        if (",".equals(selectedAnswer)) {

            selectedAnswer = "comma";

        }

        return selectedAnswer;
    }

    private void writeResetToLog() {

        // Buffered writer is faster than file writer.
        BufferedWriter writer;

        try {
// Example of append to file from: http://www.mkyong.com/java/how-to-append-content-to-file-in-java/
//            //true = append file
//            FileWriter fileWritter = new FileWriter(file.getName(),true);
//            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);

            FileWriter fileWritter = new FileWriter(file.getAbsolutePath() + ".csv", true);

            writer = new BufferedWriter(fileWritter);

            /* Write */
            writer.write("User Reset");

            /* Add new line for next row of data. */
            writer.newLine();

            writer.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "The Text could not be Saved!",
                    "Error!", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    // Set group name for message boxes and record.
    private void setGroupName() {
        
        if (CurrentTab.equals("ABC")) {

            groupName = "ABCs";

        } else if (CurrentTab.equals("NumSym")) {

            groupName = "Numbers and Symbols";

        } else if (CurrentTab.equals("ComCombo")) {

            groupName = "Common Combos";

        } else if (CurrentTab.equals("Words_A")) {

            groupName = "Words A";

        } else if (CurrentTab.equals("Words_B")) {

            groupName = "Words B";

        } else if (CurrentTab.equals("Words_C")) {

            groupName = "Words C";

        } else if (CurrentTab.equals("All")) {

            groupName = "All";

        } else if (CurrentTab.equals("Probe")) {

            groupName = "Probe";

        }

    }

    private void getNextCard() {

        if (file == null) {

            JOptionPane.showMessageDialog(this, "PLEASE GO TO CREATE LOG TAB\n"
                    + " AND CREATE A LOG FILE!",
                    "CREATE LOG FILE!", JOptionPane.ERROR_MESSAGE);

        }

        if ((training == false) && (baseline == false) && (rehearsal == false)) {

            JOptionPane.showMessageDialog(this, "PLEASE SELECT BASELINE,"
                    + "/nTRAINING OR REHEARSAL!",
                    "SELECT BASELINE, TRAINING OR REHEARSAL!", JOptionPane.ERROR_MESSAGE);

        }

        if (training == true) {

            trainingCards();

        }

        if (baseline == true) {

            displayBaselineCards();

        }
        
        if (rehearsal == true){
            
            rehearsalCards();
        }
        

    }

    // Display cards for baseline test.
    private void displayBaselineCards() {

        // Reset answer buttons.
        resetAnswerButtons();

        ansRecorded = false;

        // This should execute once to add all the groups 
        // for the selected tab and initialize the index to zero.
        if (cardListAllFlag == false) {
            baselineCards();
            indexAll = 0;
        }

        // This if statement should execute once per getNextCard()call.
        if (((indexAll < CurrentGrps_CardsList.size()) && (questionAnswered == true))
                || (indexAll == 0)) {

            // Reset flag to false until next question answered.
            questionAnswered = false;

            CurrentCard = CurrentGrps_CardsList.get(indexAll);

            displayCurrentCard();

            indexAll++;
        }

        // Added questionAnswered flag to display card until user provides an
        // answer. Initially the last card was not being captured because it 
        // displayed to quickly for response.

        if ((indexAll == CurrentGrps_CardsList.size()) && (questionAnswered == true)) {
            JOptionPane.showMessageDialog(this, "This is the end of the"
                    + " baseline test for " + groupName + "."
                    + " Total number of flashcards: " + indexAll + ".",
                    "End of Baseline Test", JOptionPane.INFORMATION_MESSAGE);

            JOptionPane.showMessageDialog(this, "TAB WILL NOW RESET!!",
                    "TAB RESET", JOptionPane.INFORMATION_MESSAGE);

            // This would be a good place to insert a method that would 
            // in turn call the reset method for the currently active 
            // tab.
            genericReset();

        }
    }

    // This executes if Training is selected.
    private void trainingCards() {

        // Reset answer buttons.
        resetAnswerButtons();

        // Local copy of flashcard.
        FlashCard currentTrainingCard;

        //Shuffle List
        Collections.shuffle(CurrentGrps_CardsList);

        // Run if the presentation target has not been met
        // and if the last card has been answered.
        if ((presentationCountEqualsTarget == false) && (questionAnswered == true)) {

            // Loop through current card list.
            for (int i = 0; i < CurrentGrps_CardsList.size(); i++) {

                currentTrainingCard = CurrentGrps_CardsList.get(i);

                // Display card if PresentationCount() is less than target.
                if (currentTrainingCard.getPresentationCount() < currentTrainingCard.getPresentationTarget()) {

                    // Reset questionAnswered to false to hold card on display.
                    questionAnswered = false;

                    displayCurrentTrainingCard(currentTrainingCard);

                    break;
                }

                // If the end of loop is reached, then all the presentation
                // targets have been reached for all the current cards. 
                // Therefore set presentationCountEqualsTarget true.                
                if (i == (CurrentGrps_CardsList.size() - 1)) {

                    presentationCountEqualsTarget = true;

                }
            }
        }

        if ((last15PercentCorrect < 90.00) && (presentationCountEqualsTarget == true) && (questionAnswered == true)) {

            Collections.shuffle(CurrentGrps_CardsList);

            // Loop through current card list.
            for (int i = 0; i < CurrentGrps_CardsList.size(); i++) {

                currentTrainingCard = CurrentGrps_CardsList.get(i);

                // Only display card if it is from the last group 
                // added.
                if ((currentTrainingCard.getPresentationTarget() == 3) && (currentTrainingCard.getCardName() != CurrentCard.getCardName())) {

                    // Reset questionAnswered to false to hold card on display.
                    questionAnswered = false;

                    displayCurrentTrainingCard(currentTrainingCard);

                    break;

                }
            }
        }

        // Only the most recently added group's cards are used for this 
        // computation.
        if ((last15PercentCorrect >= 90.00) && (presentationCountEqualsTarget == true)) {

            evaluateForNextGroup();

        }
    }

    private void evaluateForNextGroup() {
        // Call correct method to add next group and reset consecutive
        // correct count to zero.

        // Reset index for next group to be added and set
        // last15PercentCorrect to 0.
        indexLast15 = 0;
        last15PercentCorrect = 0.00;

        // Reset flag for the score calculation of the first
        // 15 cards of the group about to be added.
        first15Calculated = false;

        presentationCountEqualsTarget = false;
        
        if (CurrentTab.equals("ABC")) {

            addNextABCGrp();

        } else if (CurrentTab.equals("NumSym")) {

            addNextNumSymGrp();

        } else if (CurrentTab.equals("ComCombo")) {

            addNextComComboGrp();

        } else if (CurrentTab.equals("Words")) {

            addNextWordsGrp();

        } else if (CurrentTab.equals("Words_B")) {

            addNextWords_B_Grp();

        } else if (CurrentTab.equals("Words_C")) {

            addNextWords_C_Grp();

        } else if (CurrentTab.equals("All")) {

            addNextAllGrp();

        }

    }

    private void baselineCards() {
        // Select appropriate group for current tab.
        
        if (CurrentTab.equals("ABC")) {

            allABCGrp();

        } else if (CurrentTab.equals("NumSym")) {

            allNumSymGrp();

        } else if (CurrentTab.equals("ComCombo")) {

            allComComboGrp();

        } else if (CurrentTab.equals("Words")){

            allWordsGrp();

        } else if (CurrentTab.equals("Words_B")){

            allWords_B_Grp();

        } else if (CurrentTab.equals("Words_C")){

            allWords_C_Grp();

        } else if (CurrentTab.equals("All")){

            initAll();

        }

    }

    private void allABCGrp() {

        // List to take all ABC cards
        List<FlashCard> cardListAll = new ArrayList<FlashCard>();

        // Add all ABC groups to list.
        cardListAll.addAll(abcGrp1_CardsList);

        cardListAll.addAll(abcGrp2_CardsList);

        cardListAll.addAll(abcGrp3_CardsList);

        cardListAll.addAll(abcGrp4_CardsList);

        cardListAll.addAll(abcGrp5_CardsList);

        CurrentGrps_CardsList = cardListAll;

        setPresentationTargetToOne();

        resetPresentationCountToZero();

        // Shuffle List
        Collections.shuffle(CurrentGrps_CardsList);

        cardListAllFlag = true;

    }

    // Use of re-assignable label and choices
    // should allow this method to be used with 
    // any FlashCard.
    private boolean checkConsecutiveCorrect() {

        if (CurrentCard.getConsecutiveCorrectCount() < 3) {

            displayCurrentCard();

            consecutiveCorrectLessThanZero = true;

        } else {

            consecutiveCorrectLessThanZero = false;

        }

        return consecutiveCorrectLessThanZero;

    }

    // Assign ABC jLabel and jRadio choide buttons to 
    // Current selection. Also loads first group of 
    // FlashCards in Current
    private void assignABCImgChoices() {

        currentTabImgLabel = jLabelAbcImg;
        currentTabAns1Btn = jRadioButtonABC1;
        currentTabAns2Btn = jRadioButtonABC2;
        currentTabAns3Btn = jRadioButtonABC3;
        currentTabAns4Btn = jRadioButtonABC4;
        currentTabAns5Btn = jRadioButtonABC5;
        currentTabAns6Btn = jRadioButtonABC6;

        // Method also loads first group of
        // ABCs.
        CurrentGrps_CardsList = abcGrp1_CardsList;

        Collections.shuffle(CurrentGrps_CardsList);

        // As first group add set presentationTarget to 3.
        setPresentationTargetToThree(CurrentGrps_CardsList);

        // Set presentationCount to 0.
        resetPresentationCountToZero();

        // These flag used to select appropriate 
        // reset and list add methods.
        CurrentTab = "ABC";
        LastGrpAdded = "abcGrp1";

        // Set group name.
        setGroupName();

        // Display BlankCard first. 

        CurrentCard = BlankCard;

        displayCurrentCard();

    }

    private void assignNumSymImgChoices() {

        currentTabImgLabel = jLabelNumSymImg;
        currentTabAns1Btn = jRadioButtonNumSym1;
        currentTabAns2Btn = jRadioButtonNumSym2;
        currentTabAns3Btn = jRadioButtonNumSym3;
        currentTabAns4Btn = jRadioButtonNumSym4;
        currentTabAns5Btn = jRadioButtonNumSym5;
        currentTabAns6Btn = jRadioButtonNumSym6;

        // Method also loads first group of
        // ABCs.
        CurrentGrps_CardsList = NumSymGrp1_CardsList;

        Collections.shuffle(CurrentGrps_CardsList);

        // As first group add set presentationTarget to 3.
        setPresentationTargetToThree(CurrentGrps_CardsList);

        // Set presentationCount to 0.
        resetPresentationCountToZero();

        // These flag used to select appropriate 
        // reset and list add methods.
        // Changed from "Number and Symbols" to "NumSym"
        CurrentTab = "NumSym";
        LastGrpAdded = "NumSymGrp1";

        // Set group name.
        setGroupName();

        // Display BlankCard first. 

        CurrentCard = BlankCard;

        displayCurrentCard();

    }

    private void assignComComboImgChoices() {

        currentTabImgLabel = jLabelComComboImg;
        currentTabAns1Btn = jRadioButtonComCombo1;
        currentTabAns2Btn = jRadioButtonComCombo2;
        currentTabAns3Btn = jRadioButtonComCombo3;
        currentTabAns4Btn = jRadioButtonComCombo4;
        currentTabAns5Btn = jRadioButtonComCombo5;
        currentTabAns6Btn = jRadioButtonComCombo6;

        // Method also loads first group of
        // ABCs.
        CurrentGrps_CardsList = ComComboGrp1_CardsList;

        Collections.shuffle(CurrentGrps_CardsList);

        // As first group add set presentationTarget to 3.
        setPresentationTargetToThree(CurrentGrps_CardsList);

        // Set presentationCount to 0.
        resetPresentationCountToZero();

        // These flag used to select appropriate 
        // reset and list add methods.
        // Changed from "Number and Symbols" to "NumSym"
        CurrentTab = "ComCombo";
        LastGrpAdded = "ComComboGrp1";

        // Set group name.
        setGroupName();

        // Display BlankCard first. 

        CurrentCard = BlankCard;

        displayCurrentCard();

    }

    private void assignWordImgChoices() {

        currentTabImgLabel = jLabelWordImg;
        currentTabAns1Btn = jRadioButtonWord1;
        currentTabAns2Btn = jRadioButtonWord2;
        currentTabAns3Btn = jRadioButtonWord3;
        currentTabAns4Btn = jRadioButtonWord4;
        currentTabAns5Btn = jRadioButtonWord5;
        currentTabAns6Btn = jRadioButtonWord6;

        // Method also loads first group of
        // ABCs.
        
//        initializeCardsABC();
        
        CurrentGrps_CardsList = initWrdGrp1();

        Collections.shuffle(CurrentGrps_CardsList);

        // As first group add set presentationTarget to 3.
        setPresentationTargetToThree(CurrentGrps_CardsList);

        // Set presentationCount to 0.
        resetPresentationCountToZero();

        // These flag used to select appropriate 
        // reset and list add methods.

        CurrentTab = "Words_A";
        LastGrpAdded = "WrdGrp1";

        // Set group name.
        setGroupName();

        // Display BlankCard first. 

        CurrentCard = BlankCard;

        displayCurrentCard();

    }
    
    private void assignWords_B_ImgChoices() {
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
        currentTabImgLabel = jLabelWord_B_Img;
        currentTabAns1Btn = jRadioButtonWord_B_1;
        currentTabAns2Btn = jRadioButtonWord_B_2;
        currentTabAns3Btn = jRadioButtonWord_B_3;
        currentTabAns4Btn = jRadioButtonWord_B_4;
        currentTabAns5Btn = jRadioButtonWord_B_5;
        currentTabAns6Btn = jRadioButtonWord_B_6;

        // Method also loads first group of
        // ABCs.
        
        CurrentGrps_CardsList = initWrdGrp10();

        Collections.shuffle(CurrentGrps_CardsList);

        // As first group add set presentationTarget to 3.
        setPresentationTargetToThree(CurrentGrps_CardsList);

        // Set presentationCount to 0.
        resetPresentationCountToZero();

        // These flag used to select appropriate 
        // reset and list add methods.

        CurrentTab = "Words_B";
        LastGrpAdded = "WrdGrp10";

        // Set group name.
        setGroupName();

        // Display BlankCard first. 

        CurrentCard = BlankCard;

        displayCurrentCard();   
    
    }
    
    private void assignWords_C_ImgChoices() {
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
        currentTabImgLabel = jLabelWord_C_Img;
        currentTabAns1Btn = jRadioButtonWord_C_1;
        currentTabAns2Btn = jRadioButtonWord_C_2;
        currentTabAns3Btn = jRadioButtonWord_C_3;
        currentTabAns4Btn = jRadioButtonWord_C_4;
        currentTabAns5Btn = jRadioButtonWord_C_5;
        currentTabAns6Btn = jRadioButtonWord_C_6;

        // Method also loads first group of
        // ABCs.
        
        CurrentGrps_CardsList = initWrdGrp19();

        Collections.shuffle(CurrentGrps_CardsList);

        // As first group add set presentationTarget to 3.
        setPresentationTargetToThree(CurrentGrps_CardsList);

        // Set presentationCount to 0.
        resetPresentationCountToZero();

        // These flag used to select appropriate 
        // reset and list add methods.

        CurrentTab = "Words_C";
        LastGrpAdded = "WrdGrp19";

        // Set group name.
        setGroupName();

        // Display BlankCard first. 

        CurrentCard = BlankCard;

        displayCurrentCard();   
    
    }
    
    private void assignAllImgChoices() {

        currentTabImgLabel = jLabelAllImg;
        currentTabAns1Btn = jRadioButtonAll1;
        currentTabAns2Btn = jRadioButtonAll2;
        currentTabAns3Btn = jRadioButtonAll3;
        currentTabAns4Btn = jRadioButtonAll4;
        currentTabAns5Btn = jRadioButtonAll5;
        currentTabAns6Btn = jRadioButtonAll6;

        // Method also loads first group of
        // ABCs.
        initAll();

        Collections.shuffle(CurrentGrps_CardsList);

        // As first group add set presentationTarget to 3.
        setPresentationTargetToThree(CurrentGrps_CardsList);

        // Set presentationCount to 0.
        resetPresentationCountToZero();

        // These flag used to select appropriate 
        // reset and list add methods.

        CurrentTab = "All";
        LastGrpAdded = "All";

        // Set group name.
        setGroupName();

        // Display BlankCard first. 

        CurrentCard = BlankCard;

        displayCurrentCard();

    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws InstantiationException {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    try {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedLookAndFeelException ex) {
                        Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainJFrame().setVisible(true);

            }
        });


    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton abcResetButton;
    javax.swing.JPanel abcs;
    javax.swing.JPanel all;
    javax.swing.JButton allResetButton;
    javax.swing.ButtonGroup buttonGroupABC;
    javax.swing.ButtonGroup buttonGroupABCSessionType;
    javax.swing.ButtonGroup buttonGroupAll;
    javax.swing.ButtonGroup buttonGroupAllSessionType;
    javax.swing.ButtonGroup buttonGroupComCombo;
    javax.swing.ButtonGroup buttonGroupComComboSessionType;
    javax.swing.ButtonGroup buttonGroupNumSym;
    javax.swing.ButtonGroup buttonGroupNumSymSessionType;
    javax.swing.ButtonGroup buttonGroupProbe;
    javax.swing.ButtonGroup buttonGroupWords;
    javax.swing.ButtonGroup buttonGroupWordsSessionType;
    javax.swing.ButtonGroup buttonGroupWords_B;
    javax.swing.ButtonGroup buttonGroupWords_B_SessionType;
    javax.swing.ButtonGroup buttonGroupWords_C;
    javax.swing.ButtonGroup buttonGroupWords_C_SessionType;
    java.awt.Button buttonResetProbe;
    java.awt.Button buttonStartProbe;
    javax.swing.JButton comComboResetButton;
    javax.swing.JPanel commonCombo;
    javax.swing.JPanel createLog;
    javax.swing.JPanel intro;
    javax.swing.JButton jButtonCreateLog;
    javax.swing.JLabel jLabel1;
    javax.swing.JLabel jLabel12;
    javax.swing.JLabel jLabel2;
    javax.swing.JLabel jLabel21;
    javax.swing.JLabel jLabel22;
    javax.swing.JLabel jLabel23;
    javax.swing.JLabel jLabel26;
    javax.swing.JLabel jLabel28;
    javax.swing.JLabel jLabel3;
    javax.swing.JLabel jLabel4;
    javax.swing.JLabel jLabel5;
    javax.swing.JLabel jLabel7;
    javax.swing.JLabel jLabelAbcImg;
    javax.swing.JLabel jLabelAdditionalInfo;
    javax.swing.JLabel jLabelAllImg;
    javax.swing.JLabel jLabelComComboImg;
    javax.swing.JLabel jLabelNumSymImg;
    javax.swing.JLabel jLabelProbeImg;
    javax.swing.JLabel jLabelWordImg;
    javax.swing.JLabel jLabelWord_B_Img;
    javax.swing.JLabel jLabelWord_C_Img;
    javax.swing.JRadioButton jRadioButtonABC1;
    javax.swing.JRadioButton jRadioButtonABC2;
    javax.swing.JRadioButton jRadioButtonABC3;
    javax.swing.JRadioButton jRadioButtonABC4;
    javax.swing.JRadioButton jRadioButtonABC5;
    javax.swing.JRadioButton jRadioButtonABC6;
    javax.swing.JRadioButton jRadioButtonABCBaseline;
    javax.swing.JRadioButton jRadioButtonABCRehearsal;
    javax.swing.JRadioButton jRadioButtonABCTrain;
    javax.swing.JRadioButton jRadioButtonAll1;
    javax.swing.JRadioButton jRadioButtonAll2;
    javax.swing.JRadioButton jRadioButtonAll3;
    javax.swing.JRadioButton jRadioButtonAll4;
    javax.swing.JRadioButton jRadioButtonAll5;
    javax.swing.JRadioButton jRadioButtonAll6;
    javax.swing.JRadioButton jRadioButtonAllBaseline;
    javax.swing.JRadioButton jRadioButtonAllTrain;
    javax.swing.JRadioButton jRadioButtonComCombo1;
    javax.swing.JRadioButton jRadioButtonComCombo2;
    javax.swing.JRadioButton jRadioButtonComCombo3;
    javax.swing.JRadioButton jRadioButtonComCombo4;
    javax.swing.JRadioButton jRadioButtonComCombo5;
    javax.swing.JRadioButton jRadioButtonComCombo6;
    javax.swing.JRadioButton jRadioButtonComComboBaseline;
    javax.swing.JRadioButton jRadioButtonComComboTrain;
    javax.swing.JRadioButton jRadioButtonCommCombo_Rehearsal;
    javax.swing.JRadioButton jRadioButtonNumSym1;
    javax.swing.JRadioButton jRadioButtonNumSym2;
    javax.swing.JRadioButton jRadioButtonNumSym3;
    javax.swing.JRadioButton jRadioButtonNumSym4;
    javax.swing.JRadioButton jRadioButtonNumSym5;
    javax.swing.JRadioButton jRadioButtonNumSym6;
    javax.swing.JRadioButton jRadioButtonNumSymBaseline;
    javax.swing.JRadioButton jRadioButtonNumSymTrain;
    javax.swing.JRadioButton jRadioButtonNumSym_Rehearsal;
    javax.swing.JRadioButton jRadioButtonProbe1;
    javax.swing.JRadioButton jRadioButtonProbe2;
    javax.swing.JRadioButton jRadioButtonProbe3;
    javax.swing.JRadioButton jRadioButtonProbe4;
    javax.swing.JRadioButton jRadioButtonProbe5;
    javax.swing.JRadioButton jRadioButtonProbe6;
    javax.swing.JRadioButton jRadioButtonWord1;
    javax.swing.JRadioButton jRadioButtonWord2;
    javax.swing.JRadioButton jRadioButtonWord3;
    javax.swing.JRadioButton jRadioButtonWord4;
    javax.swing.JRadioButton jRadioButtonWord5;
    javax.swing.JRadioButton jRadioButtonWord6;
    javax.swing.JRadioButton jRadioButtonWordTrain;
    javax.swing.JRadioButton jRadioButtonWord_A_Rehearsal;
    javax.swing.JRadioButton jRadioButtonWord_B_1;
    javax.swing.JRadioButton jRadioButtonWord_B_2;
    javax.swing.JRadioButton jRadioButtonWord_B_3;
    javax.swing.JRadioButton jRadioButtonWord_B_4;
    javax.swing.JRadioButton jRadioButtonWord_B_5;
    javax.swing.JRadioButton jRadioButtonWord_B_6;
    javax.swing.JRadioButton jRadioButtonWord_B_Rehearsal;
    javax.swing.JRadioButton jRadioButtonWord_B_Train;
    javax.swing.JRadioButton jRadioButtonWord_C_1;
    javax.swing.JRadioButton jRadioButtonWord_C_2;
    javax.swing.JRadioButton jRadioButtonWord_C_3;
    javax.swing.JRadioButton jRadioButtonWord_C_4;
    javax.swing.JRadioButton jRadioButtonWord_C_5;
    javax.swing.JRadioButton jRadioButtonWord_C_6;
    javax.swing.JRadioButton jRadioButtonWord_C_Rehearsal;
    javax.swing.JRadioButton jRadioButtonWord_C_Train;
    javax.swing.JRadioButton jRadioButtonWordsBaseline;
    javax.swing.JRadioButton jRadioButtonWords_B_Baseline1;
    javax.swing.JRadioButton jRadioButtonWords_C_Baseline;
    javax.swing.JScrollPane jScrollPane1;
    javax.swing.JScrollPane jScrollPane2;
    javax.swing.JScrollPane jScrollPaneProbe;
    javax.swing.JTabbedPane jTabbedPane1;
    javax.swing.JTextArea jTextArea1;
    javax.swing.JTextArea jTextArea2;
    javax.swing.JTextArea jTextAreaProbe;
    javax.swing.JTextField jTextFieldGender;
    javax.swing.JTextField jTextFieldMajor;
    javax.swing.JTextField jTextFieldSubjectID;
    javax.swing.JTextField jTextSessionID;
    javax.swing.JPanel numSym;
    javax.swing.JButton numSymResetButton;
    javax.swing.JPanel probe;
    javax.swing.JButton wordResetButton;
    javax.swing.JButton word_B_ResetButton;
    javax.swing.JButton word_C_ResetButton;
    javax.swing.JPanel words;
    javax.swing.JPanel words_B;
    javax.swing.JPanel words_C;
    // End of variables declaration//GEN-END:variables

    private void addNextABCGrp() {

        // This ArrayList takes allow use of .addAll to 
        // add list together.
        List<FlashCard> cardList1 = new ArrayList<FlashCard>();

        // Reset presentation coutnt to zero and presentation target
        // to 1 for current flashcard list.
        resetPresentationCountToZero();

        setPresentationTargetToOne();

        // Flag to mark when group has been added.
        boolean GroupAdded = false;

        if ((LastGrpAdded.equals("abcGrp1")) && (GroupAdded == false)) {

            cardList1.addAll(abcGrp2_CardsList);

            // Set presentation target to three for 
            // newly added group.
            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "abcGrp2";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingABC == true) {
                JOptionPane.showMessageDialog(this, "Group 1 of ABCs "
                        + "have been completed! \nGroup 2 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }

        if ((LastGrpAdded.equals("abcGrp2")) && (GroupAdded == false)) {

            cardList1.addAll(abcGrp3_CardsList);

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "abcGrp3";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingABC == true) {
                JOptionPane.showMessageDialog(this, "Groups 1 and 2 of ABCs "
                        + "have been completed! \nGroup 3 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }

        if ((LastGrpAdded.equals("abcGrp3")) && (GroupAdded == false)) {

            cardList1.addAll(abcGrp4_CardsList);

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "abcGrp4";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingABC == true) {
                JOptionPane.showMessageDialog(this, "Groups 1, 2 and 3 of ABCs "
                        + "have been completed! \nGroup 4 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }

        if ((LastGrpAdded.equals("abcGrp4")) && (GroupAdded == false)) {

            cardList1.addAll(abcGrp5_CardsList);

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "abcGrp5";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingABC == true) {
                JOptionPane.showMessageDialog(this, "Groups 1, 2, 3 and 4 of ABCs "
                        + "have been completed! \nGroup 5 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }

        // Add reset 9/17/2013 to stop end users from continuing training
        // after completion.
        if ((trainingABC == true) && (LastGrpAdded.equals("abcGrp5") && (GroupAdded == false))) {
            JOptionPane.showMessageDialog(this, "All groups of ABCs "
                    + "have been completed! Congratulations! /nProgram will now reset.",
                    "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            
            resetTabABC();
            
        }
        
        // If program has executed a reset, training will be se to false
        // and the following if condition will be skipped. Add 9/17/2013.
        
        if(trainingABC == true){
            //Shuffle updated list.
            Collections.shuffle(CurrentGrps_CardsList);

            getNextCard();
        }

    }

    private void addNextNumSymGrp() {

        // This ArrayList takes allow use of .addAll to 
        // add list together.
        List<FlashCard> cardList1 = new ArrayList<FlashCard>();

        // Reset presentation coutnt to zero and presentation target
        // to 1 for current flashcard list.
        resetPresentationCountToZero();

        setPresentationTargetToOne();

        // Flag to mark when group has been added.
        boolean GroupAdded = false;

        if ((LastGrpAdded.equals("NumSymGrp1")) && (GroupAdded == false)) {

            cardList1.addAll(NumSymGrp2_CardsList);

            // Set presentation target to three for 
            // newly added group.
            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "NumSymGrp2";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingNumSym == true) {
                JOptionPane.showMessageDialog(this, "Group 1 of Numbers and Symbols "
                        + "have been completed! \nGroup 2 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }

        if ((LastGrpAdded.equals("NumSymGrp2")) && (GroupAdded == false)) {

            cardList1.addAll(NumSymGrp3_CardsList);

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "NumSymGrp3";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingNumSym == true) {
                JOptionPane.showMessageDialog(this, "Groups 1 and 2 of Numbers and Symbols "
                        + "have been completed! \nGroup 3 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }

        if ((LastGrpAdded.equals("NumSymGrp3")) && (GroupAdded == false)) {

            cardList1.addAll(NumSymGrp4_CardsList);

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "NumSymGrp4";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingNumSym == true) {
                JOptionPane.showMessageDialog(this, "Groups 1, 2 and 3 of Numbers and Symbols "
                        + "have been completed! \nGroup 4 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }

        if ((LastGrpAdded.equals("NumSymGrp4")) && (GroupAdded == false)) {

            cardList1.addAll(NumSymGrp5_CardsList);

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "NumSymGrp5";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingNumSym == true) {
                JOptionPane.showMessageDialog(this, "Groups 1, 2, 3 and 4 of Numbers and Symbols "
                        + "have been completed! \nGroup 5 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }

        if ((LastGrpAdded.equals("NumSymGrp5")) && (GroupAdded == false)) {

            cardList1.addAll(NumSymGrp6_CardsList);

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "NumSymGrp6";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingNumSym == true) {
                JOptionPane.showMessageDialog(this, "Groups 1, 2, 3, 4 and 5 of Numbers and Symbols "
                        + "have been completed! \nGroup 6 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }

        if ((LastGrpAdded.equals("NumSymGrp6")) && (GroupAdded == false)) {

            cardList1.addAll(NumSymGrp7_CardsList);

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "NumSymGrp7";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingNumSym == true) {
                JOptionPane.showMessageDialog(this, "Groups 1, 2, 3, 4, 5 and 6 of Numbers and Symbols "
                        + "have been completed! \nGroup 7 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }

        if ((LastGrpAdded.equals("NumSymGrp7")) && (GroupAdded == false)) {

            cardList1.addAll(NumSymGrp8_CardsList);

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "NumSymGrp8";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingNumSym == true) {
                JOptionPane.showMessageDialog(this, "Groups 1, 2, 3, 4, 5, 6 and 7 of Numbers and Symbols "
                        + "have been completed! \nGroup 8 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }

        // Added 9/17/2013.
        if ((trainingNumSym == true) && (LastGrpAdded.equals("NumSymGrp8"))  && (GroupAdded == false)) {
            JOptionPane.showMessageDialog(this, "All groups of Numbers and Symbols "
                    + "\nhave been completed! Congratulations! /nProgram will now reset. ",
                    "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            resetTabNumSym();
        }
        
        if (trainingNumSym == true){
        //Shuffle updated list.
        Collections.shuffle(CurrentGrps_CardsList);

        getNextCard();
    }
        
    }

    private void addNextComComboGrp() {

        // This ArrayList takes allow use of .addAll to 
        // add list together.
        List<FlashCard> cardList1 = new ArrayList<FlashCard>();

        // Reset presentation coutnt to zero and presentation target
        // to 1 for current flashcard list.
        resetPresentationCountToZero();

        setPresentationTargetToOne();

        // Flag to mark when group has been added.
        boolean GroupAdded = false;

        if ((LastGrpAdded.equals("ComComboGrp1")) && (GroupAdded == false)) {

            cardList1.addAll(ComComboGrp2_CardsList);

            // Set presentation target to three for 
            // newly added group.
            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "ComComboGrp2";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingComCombo == true) {
                JOptionPane.showMessageDialog(this, "Group 1 of Common Combinations "
                        + "have been completed! \nGroup 2 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }

        if ((LastGrpAdded.equals("ComComboGrp2")) && (GroupAdded == false)) {

            cardList1.addAll(ComComboGrp3_CardsList);

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "ComComboGrp3";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingComCombo == true) {
                JOptionPane.showMessageDialog(this, "Groups 1 and 2 of Common Combinations "
                        + "have been completed! \nGroup 3 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }

        if ((LastGrpAdded.equals("ComComboGrp3")) && (GroupAdded == false)) {

            cardList1.addAll(ComComboGrp4_CardsList);

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "ComComboGrp4";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingComCombo == true) {
                JOptionPane.showMessageDialog(this, "Groups 1, 2 and 3 of Common Combinations "
                        + "have been completed! \nGroup 4 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }

        if ((LastGrpAdded.equals("ComComboGrp4")) && (GroupAdded == false)) {

            cardList1.addAll(ComComboGrp5_CardsList);

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "ComComboGrp5";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingComCombo == true) {
                JOptionPane.showMessageDialog(this, "Groups 1, 2, 3 and 4 of Common Combinations "
                        + "have been completed! \nGroup 5 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }

        if ((LastGrpAdded.equals("ComComboGrp5")) && (GroupAdded == false)) {

            cardList1.addAll(ComComboGrp6_CardsList);

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.evaluateForNextGroup
            LastGrpAdded = "ComComboGrp6";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingComCombo == true) {
                JOptionPane.showMessageDialog(this, "Groups 1, 2, 3, 4 and 5 of Common Combinations "
                        + "have been completed! \nGroup 6 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }

        if ((LastGrpAdded.equals("ComComboGrp6")) && (GroupAdded == false)) {

            cardList1.addAll(ComComboGrp7_CardsList);

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "ComComboGrp7";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingComCombo == true) {
                JOptionPane.showMessageDialog(this, "Groups 1, 2, 3, 4, 5 and 6 of Common Combinations "
                        + "have been completed! \nGroup 7 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }

        if ((trainingComCombo == true) && (LastGrpAdded.equals("ComComboGrp7"))  && (GroupAdded == false)) {
            JOptionPane.showMessageDialog(this, "All groups of Common Combinations "
                    + "\nhave been completed! Congratulations! /nProgram will now reset.",
                    "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            resetTabComCombo();
        }

        if (trainingComCombo == true){
            
            //Shuffle updated list.
            Collections.shuffle(CurrentGrps_CardsList);

            getNextCard();
        }

    }

    private void addNextWordsGrp() {

        // This ArrayList takes allow use of .addAll to 
        // add list together.
        List<FlashCard> cardList1 = new ArrayList<FlashCard>();

        // Reset presentation coutnt to zero and presentation target
        // to 1 for current flashcard list.
        resetPresentationCountToZero();

        setPresentationTargetToOne();

        // Flag to mark when group has been added.
        boolean GroupAdded = false;

        if ((LastGrpAdded.equals("WrdGrp1")) && (GroupAdded == false)) {

            cardList1.addAll(initWrdGrp2());

            // Set presentation target to three for 
            // newly added group.
            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "WrdGrp2";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingWords == true) {
                JOptionPane.showMessageDialog(this, "Group 1 of Words "
                        + "have been completed! \nGroup 2 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }

        if ((LastGrpAdded.equals("WrdGrp2")) && (GroupAdded == false)) {

            cardList1.addAll(initWrdGrp3());

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "WrdGrp3";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingWords == true) {
                JOptionPane.showMessageDialog(this, "Groups 1 through 2 of Words "
                        + "have been completed! \nGroup 3 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }

        if ((LastGrpAdded.equals("WrdGrp3")) && (GroupAdded == false)) {

            cardList1.addAll(initWrdGrp4());

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "WrdGrp4";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingWords == true) {
                JOptionPane.showMessageDialog(this, "Groups 1 through 3 of Words "
                        + "have been completed! \nGroup 4 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }

        if ((LastGrpAdded.equals("WrdGrp4")) && (GroupAdded == false)) {

            cardList1.addAll(initWrdGrp5());

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "WrdGrp5";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingWords == true) {
                JOptionPane.showMessageDialog(this, "Groups 1 through 4 of Words "
                        + "have been completed! \nGroup 5 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }

        if ((LastGrpAdded.equals("WrdGrp5")) && (GroupAdded == false)) {

            cardList1.addAll(initWrdGrp6());

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.evaluateForNextGroup
            LastGrpAdded = "WrdGrp6";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingWords == true) {
                JOptionPane.showMessageDialog(this, "Groups 1 through 5 of Words "
                        + "have been completed! \nGroup 6 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }

        if ((LastGrpAdded.equals("WrdGrp6")) && (GroupAdded == false)) {

            cardList1.addAll(initWrdGrp7());

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "WrdGrp7";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingWords == true) {
                JOptionPane.showMessageDialog(this, "Groups 1 through 6 of Words "
                        + "have been completed! \nGroup 7 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }

        if ((LastGrpAdded.equals("WrdGrp7")) && (GroupAdded == false)) {

            cardList1.addAll(initWrdGrp8());

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "WrdGrp8";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingWords == true) {
                JOptionPane.showMessageDialog(this, "Groups 1 through 7 of Words "
                        + "have been completed! \nGroup 8 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }
        
        if ((LastGrpAdded.equals("WrdGrp8")) && (GroupAdded == false)) {

            cardList1.addAll(initWrdGrp9());

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "WrdGrp9";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingWords == true) {
                JOptionPane.showMessageDialog(this, "Groups 1 through 8 of Words "
                        + "have been completed! \nGroup 9 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }        
        
        if ((trainingWords == true) && (LastGrpAdded.equals("WrdGrp9"))  && (GroupAdded == false)){
                JOptionPane.showMessageDialog(this, "All Groups of Words - Set A "
                        + "have been completed! \nGo to Worde B to conitnue. /nProgram will reset.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
                resetTabWords();
        }        
         
        if (trainingWords == true){
            //Shuffle updated list.
            Collections.shuffle(CurrentGrps_CardsList);

            getNextCard();
        }
    }   
    
    private void addNextWords_B_Grp() {

        // Changed trainingWords to trainingWords_B in if conditions
        // 9/17/2013.
        
        // This ArrayList takes allow use of .addAll to 
        // add list together.
        List<FlashCard> cardList1 = new ArrayList<FlashCard>();

        // Reset presentation coutnt to zero and presentation target
        // to 1 for current flashcard list.
        resetPresentationCountToZero();

        setPresentationTargetToOne();

        // Flag to mark when group has been added.
        boolean GroupAdded = false;
        
        if ((LastGrpAdded.equals("WrdGrp10")) && (GroupAdded == false)) {

            cardList1.addAll(initWrdGrp11());

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "WrdGrp11";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingWords_B == true) {
                JOptionPane.showMessageDialog(this, "Group 10 of Words Set - B "
                        + "has been completed! \nGroup 11 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }        
        
        if ((LastGrpAdded.equals("WrdGrp11")) && (GroupAdded == false)) {

            cardList1.addAll(initWrdGrp12());

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "WrdGrp12";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingWords_B == true) {
                JOptionPane.showMessageDialog(this, "Groups 10 through 11 Words Set - B "
                        + "have been completed! \nGroup 12 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }
        
        if ((LastGrpAdded.equals("WrdGrp12")) && (GroupAdded == false)) {

            cardList1.addAll(initWrdGrp13());

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "WrdGrp13";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingWords_B == true) {
                JOptionPane.showMessageDialog(this, "Groups 10 through 12 of Words Set - B"
                        + "have been completed! \nGroup 13 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }
        
        // Bug reported 9/18/2013. Program looped after fourth group was added.  
        // Found there was no method to add Group 14.
        if ((LastGrpAdded.equals("WrdGrp13")) && (GroupAdded == false)) {

            cardList1.addAll(initWrdGrp14());

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "WrdGrp14";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingWords_B == true) {
                JOptionPane.showMessageDialog(this, "Groups 10 through 13 Words Set - B"
                        + "have been completed! \nGroup 14 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        } 
        
        if ((LastGrpAdded.equals("WrdGrp14")) && (GroupAdded == false)) {

            cardList1.addAll(initWrdGrp15());

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "WrdGrp15";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingWords_B == true) {
                JOptionPane.showMessageDialog(this, "Groups 10 through 14 Words Set - B"
                        + "have been completed! \nGroup 15 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }        
        
        if ((LastGrpAdded.equals("WrdGrp15")) && (GroupAdded == false)) {

            cardList1.addAll(initWrdGrp16());

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "WrdGrp16";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingWords_B == true) {
                JOptionPane.showMessageDialog(this, "Groups 10 through 15 of Words Set -B"
                        + "have been completed! \nGroup 16 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }
        
        if ((LastGrpAdded.equals("WrdGrp16")) && (GroupAdded == false)) {

            cardList1.addAll(initWrdGrp17());

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "WrdGrp17";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingWords_B == true) {
                JOptionPane.showMessageDialog(this, "Groups 10 through 16 of Words Set - B"
                        + "have been completed! \nGroup 17 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }       
         
        if ((LastGrpAdded.equals("WrdGrp17")) && (GroupAdded == false)) {

            cardList1.addAll(initWrdGrp18());

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "WrdGrp18";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingWords_B == true) {
                JOptionPane.showMessageDialog(this, "Groups 10 through 17 of Words Set - B"
                        + "have been completed! \nGroup 18 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }        
                 
        if ((trainingWords_B == true) && (LastGrpAdded.equals("WrdGrp18"))  && (GroupAdded == false)) {
            JOptionPane.showMessageDialog(this, "All groups of Words - Set B "
                    + "\nhave been completed! Congratulations!  \nGo to Words C to conitnue. \nProgram will now reset.",
                    "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            resetTabWords_B();
        }

        if (trainingWords_B == true){
        //Shuffle updated list.
        Collections.shuffle(CurrentGrps_CardsList);

        getNextCard();
        }
        
    }     
    
    private void addNextAllGrp() {
        
        JOptionPane.showMessageDialog(this, "The cards have been completed! Congratulations! /nProgram will now reset.",
        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
        
        resetTabAll();
        
        //Shuffle updated list.
        // Commented out 9/17/2013. Code is unneeded.
        //Collections.shuffle(CurrentGrps_CardsList);

        //getNextCard();
        
    }

    private void resetAnswerButtons() {

        if (CurrentTab.equals("ABC")) {
            buttonGroupABC.clearSelection();
        }

        if (CurrentTab.equals("NumSym")) {
            buttonGroupNumSym.clearSelection();
        }

        if (CurrentTab.equals("ComCombo")) {
            buttonGroupComCombo.clearSelection();
        }

        if (CurrentTab.equals("Words")) {
            buttonGroupWords.clearSelection();
        }

        if (CurrentTab.equals("Words_B")) {
            buttonGroupWords_B.clearSelection();
        }
        if (CurrentTab.equals("Words_C")) {
            buttonGroupWords_C.clearSelection();
        }
        if( CurrentTab.equals("All")){
           buttonGroupAll.clearSelection(); 
        } 
        if( CurrentTab.equals("Probe")){
           buttonGroupProbe.clearSelection();
        }

    }

    private void rightAnsMsg() {

        JOptionPane.showMessageDialog(this, "Correct answer!",
                "Correct!", JOptionPane.INFORMATION_MESSAGE);

        // Set flag indicating question has been answered.
        questionAnswered = true;

        // Reset ansRecorded flag.
        ansRecorded = false;

        // Go directly to trainingCards() or rehearsalCards()
        // method to get next card.
        
        if(training == true){
        
            trainingCards();
        }
        
        if(rehearsal == true){
        
            rehearsalCards();
        }

    }

    private void wrongAnsMsg() {

        JOptionPane.showMessageDialog(this, "Wrong answer."
                + "The Correct answer is: " + CurrentCard.getCorrectAns(),
                "Wrong!", JOptionPane.INFORMATION_MESSAGE);

        // Set flag indicating question has been answered.
        questionAnswered = true;

        // Clear answer buttons.
        resetAnswerButtons();

        displayCurrentCard();

    }

    private void ansRecordedMsg() {

        JOptionPane.showMessageDialog(this, "Answer Recorded.",
                "Answer Recorded", JOptionPane.INFORMATION_MESSAGE);

        // Set flag indicating question has been answered.
        questionAnswered = true;

        // Go directly to baselineCards() method.
        displayBaselineCards();

    }

    private void displayCurrentCard() {

        // Start timerResponse Stopwatch.
        timerResponse.start();

        currentTabImgLabel.setIcon(CurrentCard.getScaledImage());

        // Get answer labels
        Collections.shuffle(CurrentCard.choicesList);

        // Set answer and reset button text
        Ans1 = CurrentCard.choicesList.get(0);
        currentTabAns1Btn.setText(Ans1);

        Ans2 = CurrentCard.choicesList.get(1);
        currentTabAns2Btn.setText(Ans2);

        Ans3 = CurrentCard.choicesList.get(2);
        currentTabAns3Btn.setText(Ans3);

        Ans4 = CurrentCard.choicesList.get(3);
        currentTabAns4Btn.setText(Ans4);

        Ans5 = CurrentCard.choicesList.get(4);
        currentTabAns5Btn.setText(Ans5);

        Ans6 = CurrentCard.choicesList.get(5);
        currentTabAns6Btn.setText(Ans6);

    }

    // Created to limit initial display of 
    // a training card to one explicitly passed to
    // this method
    private void displayCurrentTrainingCard(FlashCard currentTrainingCard) {

        //
        CurrentCard = currentTrainingCard;

        // Start timerResponse Stopwatch.
        timerResponse.start();

        currentTabImgLabel.setIcon(CurrentCard.getScaledImage());

        // Get answer labels
        Collections.shuffle(CurrentCard.choicesList);

        // Set answer and reset button text
        Ans1 = CurrentCard.choicesList.get(0);
        currentTabAns1Btn.setText(Ans1);

        Ans2 = CurrentCard.choicesList.get(1);
        currentTabAns2Btn.setText(Ans2);

        Ans3 = CurrentCard.choicesList.get(2);
        currentTabAns3Btn.setText(Ans3);

        Ans4 = CurrentCard.choicesList.get(3);
        currentTabAns4Btn.setText(Ans4);

        Ans5 = CurrentCard.choicesList.get(4);
        currentTabAns5Btn.setText(Ans5);

        Ans6 = CurrentCard.choicesList.get(5);
        currentTabAns6Btn.setText(Ans6);

    }

    private void resetCorrectCount() {

        for (int i = 0; i < CurrentGrps_CardsList.size(); i++) {

            CurrentCard = CurrentGrps_CardsList.get(i);
            CurrentCard.resetConsecutiveCorrectCounttoZero();

        }

    }

    // Reset presentation count.
    private void resetPresentationCountToZero() {

        presentationCountEqualsTarget = false;

        for (int i = 0; i < CurrentGrps_CardsList.size(); i++) {

            CurrentCard = CurrentGrps_CardsList.get(i);
            CurrentCard.setPresentationCount(0);

        }

    }

    // Method to set presentation target for previously added
    // groups.
    private void setPresentationTargetToOne() {

        for (int i = 0; i < CurrentGrps_CardsList.size(); i++) {

            CurrentCard = CurrentGrps_CardsList.get(i);
            CurrentCard.setPresentationTarget(1);

        }

    }

    // Method to set presentation targer for most recently added
    // group.
    private void setPresentationTargetToThree(List<FlashCard> listJustAdded) {

        for (int i = 0; i < listJustAdded.size(); i++) {

            CurrentCard = listJustAdded.get(i);
            CurrentCard.setPresentationTarget(3);

        }

        // Set index for last15AnswersArray. 
        indexLast15 = 0;

        // Set last15PercentCorrect to 0.
        last15PercentCorrect = 0.00;

    }

    // Method to process answers
    private void processAns(String Ans) {

        timerResponse.stop();

        if (Ans.equals(CurrentCard.getCorrectAns()) && ansRecorded == false) {

            result = "Correct";

            ansValue = 1;

            // Increment count of correct answers.
            ++countCorrectAnswers;

        } else {

            result = "Incorrect";

            ansValue = 0;

        }

        // Write results to log file. Only record first answer for each card.
        if (ansRecorded == false) {

            ++countQuestionsAnswered;

            // Set last answer.
            CurrentCard.setLastAnswer(ansValue);

            CurrentCard.setPresentationCount(CurrentCard.getPresentationCount() + 1);

            percentCorrect = (countCorrectAnswers / countQuestionsAnswered) * 100;

            percentCorrectLast15();

            writeResults(file, Ans, result);

            ansRecorded = true;

            // Add "," back as correct answer if needed            
            commaConversion(Ans);
        }

        // Messages are turned on by selecting Training. Turned off by
        // Baseline and are off by default.
        if ((Ans.equals(CurrentCard.getCorrectAns())) && (training == true || rehearsal)) {

            rightAnsMsg();

        } else if (training == true || rehearsal) {

            wrongAnsMsg();

        }

        if (baseline == true) {

            ansRecordedMsg();

        }
        
        if ("Probe".equals(groupName)){
            
            questionAnswered = true;
        
            runProbe();
        }
    }

    // Revised to compute the score for the last 15 presentations of cards in 
    // the last group added. 
    private void percentCorrectLast15() {

        // NOTE WRT TO JAVA: Must use Integer rather than int array.
        // Java does not permit the use of primitive in arrays.        

        // If presentationTarget = 3 then card is part of the last group
        // added. It will be added to the calculation of the percent
        // correct for the last 15 presentations of cards from the last 
        // group added.

        // Set false to mark the one occasion when passing through method
        // that the score is calculated.
        scoreCalculated = false;

        if (CurrentCard.getPresentationTarget() == 3) {

            // Initial Load of last15AnswersArray
            if (indexLast15 < 15) {

                last15AnswersArray[ indexLast15] = ansValue;

                indexLast15++;

            }

            // Compute score for for first 15 flash cards.
            // This should only execute once per the addition 
            // of each new group.
            if ((indexLast15 == 15) && (first15Calculated == false)) {

                scoreLast15();

                first15Calculated = true;

                scoreCalculated = true;
            }

            // For every flash card after 15.
            if ((first15Calculated == true) && (scoreCalculated == false)) {

                // Shift oldest answer out and the rest of the 
                // answers down one in last15AnswersArray.   
                for (int i = 0; i < (last15AnswersArray.length - 1); i++) {

                    last15AnswersArray[i] = last15AnswersArray[i + 1];

                }

                // Add current answer to end of last15AnswersArray.
                // Length would give int value for number of entries in 
                // array. In this case 15, the first value would be at index
                // position 0, the last at index position 14.
                last15AnswersArray[last15AnswersArray.length - 1] = ansValue;

                scoreLast15();

            }

        }
    }

    private void scoreLast15() {

        double sumLast15CorrectAnswers = 0.00;

        //set index to 0
        int index = 0;

        // Sum the last 15 questions answered.
        while (index < (last15AnswersArray.length)) {

            sumLast15CorrectAnswers = sumLast15CorrectAnswers
                    + last15AnswersArray[index];

            index++;
        }

        last15PercentCorrect = (sumLast15CorrectAnswers / 15) * 100;

    }

    private void cardsAnsweredIncorrectly() {

        // Display card if it was incorrectly identified last time 
        // it was displayes.
        if ((CurrentCard.getLastAnswer() == 0) && (questionAnswered == true)) {

            // Reset questionAnswered to false to hold card on display.
            questionAnswered = false;

            displayCurrentCard();

        }

    }

    private void allNumSymGrp() {

        // List to take all Number and Symbol cards
        List<FlashCard> cardListAll = new ArrayList<FlashCard>();

        // Add all ABC groups to list.
        cardListAll.addAll(NumSymGrp1_CardsList);

        cardListAll.addAll(NumSymGrp2_CardsList);

        cardListAll.addAll(NumSymGrp3_CardsList);

        cardListAll.addAll(NumSymGrp4_CardsList);

        cardListAll.addAll(NumSymGrp5_CardsList);

        cardListAll.addAll(NumSymGrp6_CardsList);

        cardListAll.addAll(NumSymGrp7_CardsList);

        cardListAll.addAll(NumSymGrp8_CardsList);

        CurrentGrps_CardsList = cardListAll;

        setPresentationTargetToOne();

        resetPresentationCountToZero();

        // Shuffle List
        Collections.shuffle(CurrentGrps_CardsList);

        cardListAllFlag = true;
    }        // Changed from "Number and Symbols" to "NumSym"

    private void allComComboGrp() {

        // List to take all cards
        List<FlashCard> cardListAll = new ArrayList<FlashCard>();

        // Add all ABC groups to list.
        cardListAll.addAll(ComComboGrp1_CardsList);

        cardListAll.addAll(ComComboGrp2_CardsList);

        cardListAll.addAll(ComComboGrp3_CardsList);

        cardListAll.addAll(ComComboGrp4_CardsList);

        cardListAll.addAll(ComComboGrp5_CardsList);

        cardListAll.addAll(ComComboGrp6_CardsList);

        cardListAll.addAll(ComComboGrp7_CardsList);

        CurrentGrps_CardsList = cardListAll;

        setPresentationTargetToOne();

        resetPresentationCountToZero();

        // Shuffle List
        Collections.shuffle(CurrentGrps_CardsList);

        cardListAllFlag = true;
    }
    
    private void allWordsGrp() {

        // List to take all Number and Symbol cards
        List<FlashCard> cardListAll = new ArrayList<FlashCard>();

        // Add all Word groups to list.
        cardListAll.addAll(wrdGrps1thru9());
        
        CurrentGrps_CardsList = cardListAll;
        
        setPresentationTargetToOne();

        resetPresentationCountToZero();

        // Shuffle List
        Collections.shuffle(CurrentGrps_CardsList);

        cardListAllFlag = true;
        
    }
    
    private void allWords_B_Grp() {
           
        // List to take all Number and Symbol cards
        List<FlashCard> cardListAll = new ArrayList<FlashCard>();

        // Add all Word_B groups to list.
        
        cardListAll.addAll(wrdGrps10thru18());
        
        CurrentGrps_CardsList = cardListAll;
        
        setPresentationTargetToOne();

        resetPresentationCountToZero();

        // Shuffle List
        Collections.shuffle(CurrentGrps_CardsList);

        cardListAllFlag = true;   
    
    }
    
    private void allWords_C_Grp() {
           
        // List to take all Number and Symbol cards
        List<FlashCard> cardListAll = new ArrayList<FlashCard>();

        // Add all Word_B groups to list.
        
        cardListAll.addAll(wrdGrps19thru26());
        
        CurrentGrps_CardsList = cardListAll;
        
        setPresentationTargetToOne();

        resetPresentationCountToZero();

        // Shuffle List
        Collections.shuffle(CurrentGrps_CardsList);

        cardListAllFlag = true;   
    
    }
        
    public List<FlashCard> wrdGrps1thru9() {

        // List to take all Number and Symbol cards
        List<FlashCard> cardListAll = new ArrayList<FlashCard>();

        // Add all ABC groups to list.
        cardListAll.addAll(initWrdGrp1());

        cardListAll.addAll(initWrdGrp2());

        cardListAll.addAll(initWrdGrp3());

        cardListAll.addAll(initWrdGrp4());

        cardListAll.addAll(initWrdGrp5());

        cardListAll.addAll(initWrdGrp6());
        
        cardListAll.addAll(initWrdGrp7());

        cardListAll.addAll(initWrdGrp8());

        cardListAll.addAll(initWrdGrp9());

        return cardListAll;
    }
        

    public List<FlashCard> wrdGrps10thru18() {

        // List to take all Number and Symbol cards
        List<FlashCard> cardListAll = new ArrayList<FlashCard>();

        //cardListAll.addAll(initWrdGrp13());

        cardListAll.addAll(initWrdGrp10());
        
        cardListAll.addAll(initWrdGrp11());

        cardListAll.addAll(initWrdGrp12());

        cardListAll.addAll(initWrdGrp13());

        cardListAll.addAll(initWrdGrp14());
        
        cardListAll.addAll(initWrdGrp15());

        cardListAll.addAll(initWrdGrp16());

        cardListAll.addAll(initWrdGrp17());

        cardListAll.addAll(initWrdGrp18());

        return cardListAll;
    }   
        
     public List<FlashCard> wrdGrps19thru26() {

        // List to take all Number and Symbol cards
        List<FlashCard> cardListAll = new ArrayList<FlashCard>();

        cardListAll.addAll(initWrdGrp19());

        cardListAll.addAll(initWrdGrp20());

        cardListAll.addAll(initWrdGrp21());
        
        cardListAll.addAll(initWrdGrp22());

        cardListAll.addAll(initWrdGrp23());

        cardListAll.addAll(initWrdGrp24());

        cardListAll.addAll(initWrdGrp25());

        cardListAll.addAll(initWrdGrp26());

        return cardListAll;
    }   

    private void initAll() {
                
        // List to take all Number and Symbol cards
        List<FlashCard> cardListAllTab;
        cardListAllTab = new ArrayList<FlashCard>();
        
        // Get all ABCs.
        allABCGrp();
        
        draw5Cards();
        
        // Add to cardListAllTab as scratch pad.
        cardListAllTab.addAll(CurrentGrps_CardsList);
        
        // Get all Number and Symbols.
        allNumSymGrp();
        
        draw5Cards();
        
        // Add to cardListAllTab as scratch pad.
        cardListAllTab.addAll(CurrentGrps_CardsList);
        
        // Get all Common Combos.
        allComComboGrp();
        
        draw5Cards();
        
        // Add to cardListAllTab as scratch pad.
        cardListAllTab.addAll(CurrentGrps_CardsList);
        
        // Get set A of Words.
        allWordsGrp();
        
        draw5Cards();
        
        // Add to cardListAllTab as scratch pad.
        cardListAllTab.addAll(CurrentGrps_CardsList);
        
        // Get set B of Words.
        allWords_B_Grp();
        
        draw5Cards();
        
        // Add to cardListAllTab as scratch pad.
        cardListAllTab.addAll(CurrentGrps_CardsList);
        
        // Get set C of Words.
        allWords_C_Grp();
        
        draw5Cards();
        
        // Add to cardListAllTab as scratch pad.
        cardListAllTab.addAll(CurrentGrps_CardsList);
        
        // Set CurrentGrps_CardsList equal to cardListAllTab.
        CurrentGrps_CardsList = cardListAllTab;
    
    }

    // Initialize cards for Probe tab as per change request -
    // "Also, Instead of having stimuli from the 
    // first subset of modules 1-6, we would like to have a random array of
    // 40 stimuli from modules 3-6 (10 from module 3, 10 from module 4, 10 
    // from module 5, and 10 from module 6)."
    
    private void initProbe() {
                
        // List to take all cards
        List<FlashCard> cardListProbeTab;
        cardListProbeTab = new ArrayList<FlashCard>();
      
        // Get all Common Combos.
        allComComboGrp();
        
        draw10Cards();
        
        // Add to cardListAllTab as scratch pad.
        cardListProbeTab.addAll(CurrentGrps_CardsList);
        
        // Get set A of Words.
        allWordsGrp();
        
        draw10Cards();
        
        // Add to cardListAllTab as scratch pad.
        cardListProbeTab.addAll(CurrentGrps_CardsList);
        
        // Get set B of Words.
        allWords_B_Grp();
        
        draw10Cards();
        
        // Add to cardListAllTab as scratch pad.
        cardListProbeTab.addAll(CurrentGrps_CardsList);
        
        // Get set C of Words.
        allWords_C_Grp();
        
        draw10Cards();
        
        // Add to cardListAllTab as scratch pad.
        cardListProbeTab.addAll(CurrentGrps_CardsList);
        
        // Set CurrentGrps_CardsList equal to cardListAllTab.
        CurrentGrps_CardsList = cardListProbeTab;
    
    }
    
        private void initRehearsalABC() {
                
        // List to take all cards
        List<FlashCard> cardListABCTabRehearsal;
        cardListABCTabRehearsal = new ArrayList<FlashCard>();
      
        // Get all Common Combos.
        allABCGrp();
        
        draw10Cards();
        
        // Add to cardListABCTabRehearsal as scratch pad.
        cardListABCTabRehearsal.addAll(CurrentGrps_CardsList);
        
//        // Get set A of Words.
//        allWordsGrp();
//        
//        draw10Cards();
//        
//        // Add to cardListAllTab as scratch pad.
//        cardListProbeTab.addAll(CurrentGrps_CardsList);
//        
//        // Get set B of Words.
//        allWords_B_Grp();
//        
//        draw10Cards();
//        
//        // Add to cardListAllTab as scratch pad.
//        cardListProbeTab.addAll(CurrentGrps_CardsList);
//        
//        // Get set C of Words.
//        allWords_C_Grp();
//        
//        draw10Cards();
//        
//        // Add to cardListAllTab as scratch pad.
//        cardListProbeTab.addAll(CurrentGrps_CardsList);
        
        // Set CurrentGrps_CardsList equal to cardListAllTab.
        CurrentGrps_CardsList = cardListABCTabRehearsal;
    
    }
    
    // Determine which tab rehearsal has been selected
    // and add 10 cards each from the preceding tabs.    
    private void addMoreRehearsalCards() {
                
        // List to take cards
        List<FlashCard> cardListAddMoreRehearsal;
        cardListAddMoreRehearsal = new ArrayList<FlashCard>();
      
        // Get all Common Combos.
        allABCGrp();
        
        draw10Cards();
        
        // Add to cardListABCTabRehearsal as scratch pad.
        cardListAddMoreRehearsal.addAll(CurrentGrps_CardsList);
        
        // Get set A of Words.
        allWordsGrp();
        
        draw10Cards();
        
        // Add to cardListAllTab as scratch pad.
        cardListAddMoreRehearsal.addAll(CurrentGrps_CardsList);
        
        // Get set B of Words.
        allWords_B_Grp();
        
        draw10Cards();
        
        // Add to cardListAllTab as scratch pad.
        cardListAddMoreRehearsal.addAll(CurrentGrps_CardsList);
        
        if((rehearsalABC == true) || (rehearsalABC == true)||
           (rehearsalABC == true) || (rehearsalABC == true)||
           (rehearsalABC == true) || (rehearsalABC == true))
        {
            // Get set C of Words.
            allWords_C_Grp();

            draw10Cards();

            // Add to cardListAllTab as scratch pad.
            cardListAddMoreRehearsal.addAll(CurrentGrps_CardsList);

            // Set CurrentGrps_CardsList equal to cardListAllTab.
            CurrentGrps_CardsList = cardListAddMoreRehearsal;
        }
        
        // Set CurrentGrps_CardsList equal to cardListAllTab.
        CurrentGrps_CardsList = cardListAddMoreRehearsal;
    
    }    
        
    private void resetTabAll() {
     
        baselineAll = false;
        trainingAll = false;

        // Clear test type button selection
        buttonGroupAllSessionType.clearSelection();

        loadFirstABCGroup();

        genericReset();
    }

    private void draw5Cards() {
        
        // Assign first five cards back to CurrentGrps_CardList.
        CurrentGrps_CardsList = CurrentGrps_CardsList.subList( 0, 5);     
        
    }
    
   private void draw10Cards() {
        
        // Assign first five cards back to CurrentGrps_CardList.
        CurrentGrps_CardsList = CurrentGrps_CardsList.subList( 0, 10);     
        
    }

    private void addNextWords_C_Grp() {
        // Changed trainingWords to trainingWords_C in 
        // if condition 9/17/2013.
        
        // This ArrayList takes allow use of .addAll to 
        // add list together.
        List<FlashCard> cardList1 = new ArrayList<FlashCard>();

        // Reset presentation coutnt to zero and presentation target
        // to 1 for current flashcard list.
        resetPresentationCountToZero();

        setPresentationTargetToOne();

        // Flag to mark when group has been added.
        boolean GroupAdded = false;
       
        if ((LastGrpAdded.equals("WrdGrp19")) && (GroupAdded == false)) {

            cardList1.addAll(initWrdGrp20());

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "WrdGrp20";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingWords_C == true) {
                JOptionPane.showMessageDialog(this, "Group 19 of Words "
                        + "has been completed! \nGroup 20 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }        
        
        if ((LastGrpAdded.equals("WrdGrp20")) && (GroupAdded == false)) {

            cardList1.addAll(initWrdGrp21());

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "WrdGrp21";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingWords_C == true) {
                JOptionPane.showMessageDialog(this, "Groups 19 through 20 of Words "
                        + "have been completed! \nGroup 21 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }
        
        if ((LastGrpAdded.equals("WrdGrp21")) && (GroupAdded == false)) {

            cardList1.addAll(initWrdGrp22());

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "WrdGrp22";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingWords_C == true) {
                JOptionPane.showMessageDialog(this, "Groups 19 through 21 of Words "
                        + "have been completed! \nGroup 22 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }       
         
        if ((LastGrpAdded.equals("WrdGrp22")) && (GroupAdded == false)) {

            cardList1.addAll(initWrdGrp23());

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "WrdGrp23";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingWords_C == true) {
                JOptionPane.showMessageDialog(this, "Groups 19 through 22 of Words "
                        + "have been completed! \nGroup 23 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }        
        
        if ((LastGrpAdded.equals("WrdGrp23")) && (GroupAdded == false)) {

            cardList1.addAll(initWrdGrp24());

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "WrdGrp24";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingWords_C == true) {
                JOptionPane.showMessageDialog(this, "Groups 19 through 23 of Words "
                        + "have been completed! \nGroup 24 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }
        
        if ((LastGrpAdded.equals("WrdGrp24")) && (GroupAdded == false)) {

            cardList1.addAll(initWrdGrp25());

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "WrdGrp25";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingWords_C == true) {
                JOptionPane.showMessageDialog(this, "Groups 19 through 24 of Words "
                        + "have been completed! \nGroup 25 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        }        
        
        if ((LastGrpAdded.equals("WrdGrp25")) && (GroupAdded == false)) {

            cardList1.addAll(initWrdGrp26());

            setPresentationTargetToThree(cardList1);

            cardList1.addAll(CurrentGrps_CardsList);

            CurrentGrps_CardsList = cardList1;

            // Set flag for last group added.
            LastGrpAdded = "WrdGrp26";

            // Set GroupAdded flag true to prevent 
            // following code from executing.
            GroupAdded = true;

            if (trainingWords_C == true) {
                JOptionPane.showMessageDialog(this, "Groups 19 through 25 of Words "
                        + "have been completed! \nGroup 26 has been added.",
                        "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            }

        } 
     
        if ((trainingWords_C == true) && (LastGrpAdded.equals("WrdGrp26"))  && (GroupAdded == false)) {
            JOptionPane.showMessageDialog(this, "All groups of Words - Set C "
                    + "\nhave been completed! Congratulations! /nProgram will now reset.",
                    "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            resetTabWords_C();
        }

        if (trainingWords_C == true){
            //Shuffle updated list.
            Collections.shuffle(CurrentGrps_CardsList);

            getNextCard();
        }

    }

    private void resetTabWords_C() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        baselineWords_C = false;
        trainingWords_C = false;
        rehearsalWords_C = false;

        // Clear test type button selection
        buttonGroupWords_C_SessionType.clearSelection();

        loadFirstWord_C_Group();

        genericReset();
    
    }

    private void loadFirstWord_C_Group() {
        
        // These flags used to select appropriate 
        // reset and list add methods.
        CurrentTab = "Words_C";
        LastGrpAdded = "WrdGrp19";

        // Reset current list to ComComboGrp1_CardsList;
        CurrentGrps_CardsList = initWrdGrp19();

        // Shuffle List
        Collections.shuffle(CurrentGrps_CardsList);

        // Set presentation target to three.
        setPresentationTargetToThree(CurrentGrps_CardsList); 
    }

    private void assignProbeChoices() {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
           
        currentTabImgLabel = jLabelProbeImg;
        currentTabAns1Btn = jRadioButtonProbe1;
        currentTabAns2Btn = jRadioButtonProbe2;
        currentTabAns3Btn = jRadioButtonProbe3;
        currentTabAns4Btn = jRadioButtonProbe4;
        currentTabAns5Btn = jRadioButtonProbe5;
        currentTabAns6Btn = jRadioButtonProbe6;

        // Method also loads first group of
        // Probe.
        initProbe();

        Collections.shuffle(CurrentGrps_CardsList);

        
        // Each card is only presented once so the following
        // presentation setting are not needed.
        // As first group add set presentationTarget to 3.
        // setPresentationTargetToThree(CurrentGrps_CardsList);

        // Set presentationCount to 0.
        // resetPresentationCountToZero();

        // These flag used to select appropriate 
        // reset and list add methods.
        //

        CurrentTab = "Probe";
        LastGrpAdded = "Probe";

        // Set group name.
        setGroupName();

        // Display BlankCard first. 

        CurrentCard = BlankCard;

        displayCurrentCard();
    
    }

    private void runProbe() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
        // Reset answer buttons.
        resetAnswerButtons();

        ansRecorded = false;
//
//        // This should execute once to add all the groups 
//        // for the selected tab and initialize the index to zero.
//        if (cardListAllFlag == false) {
//            baselineCards();
//            indexAll = 0;
//        }

        // This if statement should execute once per getNextCard()call.
        if (((indexAll < CurrentGrps_CardsList.size()) && (questionAnswered == true))
                || (indexAll == 0)) {

            // Reset flag to false until next question answered.
            questionAnswered = false;

            CurrentCard = CurrentGrps_CardsList.get(indexAll);

            displayCurrentCard();

            indexAll++;
        }

        // Added questionAnswered flag to display card until user provides an
        // answer. Initially the last card was not being captured because it 
        // displayed to quickly for response.

        if ((indexAll == CurrentGrps_CardsList.size()) && (questionAnswered == true)) {
            JOptionPane.showMessageDialog(this, "This is the end of the"
                    + " Probe test."
                    + " Total number of flashcards: " + indexAll + ".",
                    "End of Probe", JOptionPane.INFORMATION_MESSAGE);

            JOptionPane.showMessageDialog(this, "TAB WILL NOW RESET!!",
                    "TAB RESET", JOptionPane.INFORMATION_MESSAGE);

            // This would be a good place to insert a method that would 
            // in turn call the reset method for the currently active 
            // tab.
            genericReset();

        }
    
    }

    private void rehearsalCards() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        // Reset answer buttons.
        resetAnswerButtons();

        ansRecorded = false;

        // This if statement should execute once per getNextCard()call.
        if (((indexAll < CurrentGrps_CardsList.size()) && (questionAnswered == true))
                || (indexAll == 0)) {

            // Reset flag to false until next question answered.
            questionAnswered = false;

            CurrentCard = CurrentGrps_CardsList.get(indexAll);

            displayCurrentCard();

            indexAll++;
        }

        // Added questionAnswered flag to display card until user provides an
        // answer. Initially the last card was not being captured because it 
        // displayed to quickly for response.

        if ((indexAll == CurrentGrps_CardsList.size()) && (questionAnswered == true)) {
            JOptionPane.showMessageDialog(this, "This is the end of the"
                    + " Rehearsal."
                    + " Total number of flashcards: " + indexAll + ".",
                    "End of Rehearsal", JOptionPane.INFORMATION_MESSAGE);

            JOptionPane.showMessageDialog(this, "TAB WILL NOW RESET!!",
                    "TAB RESET", JOptionPane.INFORMATION_MESSAGE);

            // This would be a good place to insert a method that would 
            // in turn call the reset method for the currently active 
            // tab.
            genericReset();
            
            if("ABC".equals(CurrentTab) ){
            
                resetTabABC();
            }
        }
    
    }
 
}
