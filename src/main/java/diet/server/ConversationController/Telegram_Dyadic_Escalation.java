/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.ConversationController;

import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.textmanipulationmodules.CyclicRandomTextGenerators.CyclicRandomTextGenerator;
import diet.tg.TelegramMessageFromClient;
import diet.tg.TelegramParticipant;
import diet.utils.HashtableOfLong;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Random;

/**
 *
 * @author LX1C
 * @author vladmaraev
 */
public class Telegram_Dyadic_Escalation extends TelegramController{

    public Telegram_Dyadic_Escalation(Conversation c) {
        super(c);
        // Thread t = new Thread(){public void run(){loop();}};
        // t.start();
    }

    public Telegram_Dyadic_Escalation(Conversation c, long istypingtimeout) {
        super(c, istypingtimeout);
        // Thread t = new Thread(){public void run(){loop();}};
        // t.start();
        
    }

    public void telegram_participantJoinedConversation(TelegramParticipant p) {
        if(c.getParticipants().getAllParticipants().size()==2) {
            
             pp.createNewSubdialogue(c.getParticipants().getAllParticipants());
             //this.itnt.addGroupWhoAreMutuallyInformedOfTyping(c.getParticipants().getAllParticipants());
              
             CustomDialog.showDialog("PRESS OK TO START!");
             this.experimentHasStarted=true;
             
        }
    }
    
   
    @Override
    public void telegram_participantReJoinedConversation(TelegramParticipant p) {
        
      // c.telegram_sendPoll(p, "This is a questionnaire","What is it?", new String[]{"option1", "option2", "option3", "option4"});
        
       // c.telegram_sendInstructionToParticipantWithForcedKeyboardButtons(p, "This is a question",  new String[]{"option1", "option2", "option3", "option4", "option5", "option6", "option7"},3);
        
       if(c.getParticipants().getAllParticipants().size()==2) {
            
             pp.createNewSubdialogue(c.getParticipants().getAllParticipants());
             //this.itnt.addGroupWhoAreMutuallyInformedOfTyping(c.getParticipants().getAllParticipants());
              
             CustomDialog.showDialog("PRESS OK TO START!");
             this.experimentHasStarted=true;
             
             
        }
    }
     

    @Override
    public synchronized void telegram_processTelegramMessageFromClient(TelegramParticipant sender, TelegramMessageFromClient tmfc) {
        if(tmfc.u.hasMessage()  && tmfc.u.getMessage().hasText()){
             String text=tmfc.u.getMessage().getText();
             
             long numberOfTurns = this.htTurns.get(sender);
             numberOfTurns++;
             this.htTurns.put(sender, numberOfTurns);
             long interventionType = -1;
            
             if(this.mode==normalconversation){
                 interventionType = generateCheckIfStatement(sender,text);
                 // Conversation.printWSln("Main", sender.getUsername());
                 if (interventionType < 0) {
                     c.telegram_relayMessageTextToOtherParticipants(sender, tmfc);
                 } else {
                     this.mode = this.emptyingqueue;
                 }
             }
             // else if(this.mode==this.waitingaftertargetbeforesending & sender==this.detectedParticipant){
             //     Conversation.printWSln("Main", "The target has sent another message...");
             //     this.mode=normalconversation;
             //     generateClarification(sender,text);
             //     c.telegram_relayMessageTextToOtherParticipants(sender, tmfc);    
             // }
             // else if(this.mode==this.waitingforresponse & sender ==this.detectedParticipant){
             //     Conversation.printWSln("Main", "Received response!");
             //     this.messageQueue.add(tmfc);
             //     this.htOriginOfMessage.put(tmfc,sender);
             //     this.mode=this.emptyingqueue;
             // }
             if (this.mode==this.emptyingqueue & sender ==this.detectedParticipant) {
                 Conversation.printWSln("Main", "Emptying queue");
                 // TelegramMessageFromClient tmfc = this.messageQueue.firstElement();
                             // TelegramParticipant sender = this.htOriginOfMessage.get(tmfc);
                             // String text = tmfc.u.getMessage().getText();
// 1. When the original message contains "I" + it does not finish with "?",
// then substitute "you" for "I".
// 2. Insert ", p2" at the end of statements (i.e. turns with no final
// punctuation). Alternatively, it would be nice to have the name of the
// user but it seems even more tricky.
// 3. Insert "Listen, " before statements (i.e. turns that do not finish
                 // with "?")
                             if (interventionType == 1) {
                                 text = " " + text + " ";
                                 text = text.replaceAll("I\\s+", "you ")
                                     .replaceAll("i\\s+", "you ")
                                     .strip();
                             } else if (interventionType == 2) {
                                 String targetP;
                                 if (sender.getUsername().equals("p1")) {
                                     targetP = "p2";
                                 } else {
                                     targetP = "p1";
                                 }
                                 text = text + ", " + targetP;
                             }
                             else if (interventionType == 3) {
                                 text = "Listen, " + text.substring(0,1).toLowerCase() + text.substring(1);
                             }
                 
                             c.telegram_sendArtificialTurnFromApparentOriginToPermittedParticipants(sender, text);
                             this.timestampOfMostRecentQueueSend = new Date().getTime();
                             this.messageQueue.remove(tmfc);

                             Conversation.printWSln("Main", "Queue empty. Changing mode to normal");
                            this.htTurnOfLastIntervention.put(detectedParticipant,this.htTurns.get(detectedParticipant));
                            this.mode=this.normalconversation;
             }
             // else {
             //     Conversation.printWSln("Main", "Message was sent while queue was not empty. This message has been enqueued.");
             //     this.messageQueue.add(tmfc);
             //     this.htOriginOfMessage.put(tmfc,sender);
                 
             // }
        }
    }

    int turnsElapsedBeforeClarification = CustomDialog.getInteger("How many turns need to elapse between interventions? (per participant)", 5);
    // long durationToWaitAfterTargetBeforeIntervention = CustomDialog.getLong("Duration to wait after target detected, before sending clarification?", 6000);
    // long durationToWaitAfterIntervention =  CustomDialog.getLong("Duration to wait after sending intervention before resuming and sending messages from the queue?", 10000);
    // long durationBetweenMessagesWhenEmptyingQueueMin = CustomDialog.getLong("When emptying the queue, what is the gap between messages?", 3000);
    
            
    
    // String[] possibleTargets = new String[]{"Bob", "Riviera"};
    // Vector possibleTargets = CustomDialog.loadTextFileWithExtensionToVector(System.getProperty("user.dir"), "What is the text file containing the target strings", "txt", "");
    
    //String[] whyvariants = new String[]{"why?", "sorry why?", "umm why?"};
    
    
    // Vector whyvariants = CustomDialog.loadTextFileWithExtensionToVector(System.getProperty("user.dir"), "What is the text file containing all the WHY? variants", "txt", "");
    // CyclicRandomTextGenerator crt = new CyclicRandomTextGenerator( whyvariants);
    

    HashtableOfLong htTurns = new HashtableOfLong(0);
    HashtableOfLong htTurnOfLastIntervention = new HashtableOfLong(0);
    Hashtable<TelegramMessageFromClient, TelegramParticipant> htOriginOfMessage = new Hashtable();
    
    
    long timestampOfDetection = new Date().getTime();
    long timestampOfSendingWHY = new Date().getTime();
    TelegramParticipant detectedParticipant = null;
    long timestampOfMostRecentQueueSend = 0;
    
    
    
    
    
    public synchronized long generateCheckIfStatement(TelegramParticipant sender, String t){
         if(this.mode!=normalconversation) {
             
             Conversation.printWSln("Main", "Turn by "+sender.getConnection().telegramID+ " can`t be clarified - not in mode 0" );
             return -1;
         }
         long turnOfLastInterventionToParticipant = this.htTurnOfLastIntervention.get(sender);
         long turnsProducedByParticipant = this.htTurns.get(sender);
         long turnsSinceLastIntervention = turnsProducedByParticipant - turnOfLastInterventionToParticipant;
         if(turnsSinceLastIntervention<  turnsElapsedBeforeClarification){
             Conversation.printWSln("Main", "Turn by "+sender.getConnection().telegramID+ " can`t be clarified - Only "+turnsSinceLastIntervention+ "  turns since last intervention" );
             return -1;
         }
         
        long produceIntervention = -1;
        Random rand = new Random();
        int flipCoin = rand.nextInt(3);
        String haystack = " " + t.toLowerCase();

        if (flipCoin == 0) { // search for I in statements
            boolean containsI = (haystack + " ").contains(" i ");
            if (containsI) {
                produceIntervention = 1;
            }
        } else if (flipCoin == 1) {
            if  (!(haystack.endsWith("?"))) {
                produceIntervention = 2;
         }
        }
         else {
            if  (!(haystack.endsWith("?"))) {
                produceIntervention = 3;
         }
        }


        


// 1. When the original message contains "I" + it does not finish with "?",
// then substitute "you" for "I".
// 2. Insert ", p2" at the end of statements (i.e. turns with no final
// punctuation). Alternatively, it would be nice to have the name of the
// user but it seems even more tricky.
// 3. Insert "Listen, " before statements (i.e. turns that do not finish
// with "?")

         // Conversation.printWSln("Main", "Haystack: "+haystack+ "    !Endswith: "+!(haystack.endsWith("?")));
         
         
         // for(int i =0; i < possibleTargets.size(); i++){
         //     String needle = " "+((String)possibleTargets.elementAt(i)).toUpperCase()+" ";
             
         //     String haystack = " "+t.toUpperCase()+ " ";
             
         //     //Conversation.printWSln("Main", "Haystack: "+haystack+ "    Needle: "+needle);
             
             
         //     if(haystack.contains(needle))containsTarget = true;
         // }

         
         if(produceIntervention < 0){
             Conversation.printWSln("Main", "Turn by "+sender.getConnection().telegramID+ " can`t be clarified - doesn`t contain target");
             return produceIntervention;
         } else {
         
         this.detectedParticipant = sender;
         this.timestampOfDetection = new Date().getTime();
         
         
       
         Conversation.printWSln("Main", "Target identified. Immediately fixing.");

         return produceIntervention;
         // this.mode=this.emptyingqueue;
         }
         
         
    }
    
    
    Vector<TelegramMessageFromClient> messageQueue = new Vector();
    
   
    
   public static boolean showcCONGUI() {
        return true;
    }
    
   
   
    
    final int normalconversation = 0;
    final int waitingaftertargetbeforesending = 1;
    final int waitingforresponse = 2;
    final int emptyingqueue = 3;
    int mode = normalconversation;
    
   
    public void updateModeCRSENT(){
        if(mode==normalconversation){
            mode=waitingforresponse;
        }
        else{
            c.saveErrorLog("Trying to go from mode "+ mode+ " to "+waitingforresponse);
        }
    }
   
   
   
   
   
}







