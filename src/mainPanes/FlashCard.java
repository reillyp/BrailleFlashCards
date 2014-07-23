/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mainPanes;

import java.awt.Image;
import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author Patrick Reilly
 */
public class FlashCard {
     
    private ImageIcon brailleImg;
    
    private Image scaleBrailleImg;
   
    private String correctAns;
    
    private String choiceOne;
    
    private String choiceTwo;
    
    private String choiceThree;
    
    private String choiceFour;
    
    // Choice 5 will be blank for most groups.
    
    private String choiceFive;
    
    private int consecutiveCorrectCount = 0;
    
    // Create string array of choices.
    
    private String[] choicesAbc = new String[6];
    
    // Create list to shuffle choices.
    
    public List <String> choicesList; // = Arrays.asList(choicesAbc);
    
    // cardName is used to get and set values of the source card when it is  
    // set as the CurrentCard. For example if FlashCard abcGrp1_e is set as the 
    // CurrentCard this would be used to get the consecutiveCorrectCount 
    // via cardName.getConsecutiveCorrectCount().
 
    private FlashCard cardName;
    
    // Presentation Target is number of times card should be repeated for current 
    // trial. It will be 3 if it is the first trial set in which the card 
    // has appeared, 1 for subseguent trials in a given session.
    // It may also be use as flag for scoring the last 15 presentation of the 
    // most recently added set/group.
    
    private int presentationTarget = 0;
    
    // Presentation Count tracks how many times the card has appeared to user
    // in current trial.
    
    private int presentationCount = 0;
    
    // Added to track last recorded answer
    // 1 = Correct, 0 = Incorrect.
    int lastAnswer = 0;
    
    // Set Correct Answer and Choices.
    // NOTE: Most entries for choice 5 will be blank.
    
    public void setChoices(String CorrAns, String Chc1, String Chc2,
            String Chc3, String Chc4, String Chc5){
    
        correctAns = CorrAns;
        choicesAbc[0] =  correctAns;      
        
        choiceOne = Chc1; 
        choicesAbc[1] =  choiceOne;
        
        choiceTwo = Chc2;
        choicesAbc[2] =  choiceTwo;
        
        choiceThree = Chc3; 
        choicesAbc[3] =  choiceThree;
        
        choiceFour = Chc4;
        choicesAbc[4] =  choiceFour;
        
        choiceFive = Chc5;
        choicesAbc[5] =  choiceFive;
        
        choicesList = Arrays.asList(choicesAbc);
            
    }
    
    public String[] getchoicesAbc (){
    
        return choicesAbc;
    }
    
    // Set Image.
    
    public void setFlashCardImg (ImageIcon Img, int width, int height){
       
        brailleImg = Img;
        
        // Scale Image for display. Assumes original image was 480 x 672.
        
        scaleBrailleImg = brailleImg.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);

    }

    // Return scaled image for display.
    
    public ImageIcon getScaledImage (){
        
        ImageIcon scaleImgIcon = new ImageIcon(scaleBrailleImg);
        return scaleImgIcon;
        
    }
    
    public int getConsecutiveCorrectCount(){
    
        return consecutiveCorrectCount;
        
    }
    
    public void setConsecutiveCorrectCount(int newValue){
    
        consecutiveCorrectCount = newValue;
        
    }
    
    public void resetConsecutiveCorrectCounttoZero(){
    
        consecutiveCorrectCount = 0;
        
    }
    
    public void setCardName(FlashCard SourceCardName){
        
        cardName = SourceCardName;
    }
    
    public FlashCard getCardName(){
        
        return cardName;
    }
    
    // Added to convert , to comma
    public void setCorrectAns( String Answer ){
    
        correctAns = Answer;
        
    }
    
    public String getCorrectAns(){
    
        return correctAns;
    }  
    
    public void setPresentationCount(int repeatCountCurrentTrial)
    {
        
        presentationCount = repeatCountCurrentTrial;
        
    }
    
    public int getPresentationCount()
    {
        
        return presentationCount;
        
    }
    
    // Repeat target set to 3 for first appearance,
    // 1 for subsequent appearances.
    public void setPresentationTarget(int repeatTargetSet ){
    
        presentationTarget = repeatTargetSet;
    
    }            
    
    public int getPresentationTarget(){
    
        return presentationTarget;
    
    }    
    
    public void setLastAnswer(int ansValue)
    {
        
        lastAnswer = ansValue;
        
    }
    
    public int getLastAnswer()
    {
        
        return lastAnswer;
        
    }
    
}
