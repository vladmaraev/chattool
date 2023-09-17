/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.ConversationController;

import diet.server.Conversation;
import diet.server.ConversationController.ui.JInterfaceMenuButtonsReceiverInterface;
import diet.server.ConversationController.ui.JInterfaceTenButtons;
import diet.task.CustomizableReferentialTask.CustomizableReferentialTask;
import diet.task.CustomizableReferentialTask.CustomizableReferentialTaskSettingsFactory;
import diet.tg.TelegramMessageFromClient;
import diet.tg.TelegramParticipant;
import java.util.Hashtable;
import java.util.Vector;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 *
 * @author LX1C
 */
public class Telegram_Dyadic_Customizable_ReferentialTask_Automatic_Start_Extended extends TelegramController implements JInterfaceMenuButtonsReceiverInterface{

    
    
    CustomizableReferentialTaskSettingsFactory crtsf = new CustomizableReferentialTaskSettingsFactory(this, true);
    //CustomizableReferentialTaskSettings crts = crtsf.getNextCustomizableReferentialTaskSettings();
    //CustomizableReferentialTask crt = new CustomizableReferentialTask(this, crts);
   // CustomizableReferentialTask crt = new CustomizableReferentialTask(this, 5000,true);
   // Participant pDirector;
   // Participant pMatcher;
    
    
    public Telegram_Dyadic_Customizable_ReferentialTask_Automatic_Start_Extended(Conversation c) {
        super(c);
       
    }

    public Telegram_Dyadic_Customizable_ReferentialTask_Automatic_Start_Extended(Conversation c, long istypingtimeout) {
        super(c, istypingtimeout);
       
        
    }

    
    public Vector<TelegramParticipant> vQueued = new Vector();
    Hashtable htTasks = new Hashtable();
    public Vector<CustomizableReferentialTask> experimentalTasks = new Vector();
    
    
 
     public void startParticipants(TelegramParticipant tp1, TelegramParticipant tp2){
         pp.createNewSubdialogue(tp1,tp2);
         CustomizableReferentialTask crt = new CustomizableReferentialTask(this, crtsf.getNextCustomizableReferentialTaskSettings());
         c.telegram_sendInstructionToParticipant_MonospaceFont(tp1, "Please start!");
         c.telegram_sendInstructionToParticipant_MonospaceFont(tp2, "Please start!");
         crt.startTask(tp1, tp2);
         this.htTasks.put(tp1, crt);
         this.htTasks.put(tp2, crt);
         this.experimentalTasks.add(crt);
     }
    
    
    

   
     public void telegram_participantJoinedConversation(TelegramParticipant p) {
        vQueued.add(p);
        Conversation.printWSln("Main", "Participant "+p.getUsername()+ " logged in.  There are now "+this.vQueued.size()+ " participants waiting for a partner");
        //this.checkIfCanStart();
    }
    
   
    @Override
    public void telegram_participantReJoinedConversation(TelegramParticipant p) {
       vQueued.add(p);
       Conversation.printWSln("Main", "Participant "+p.getUsername()+ " relogged in. There are now "+this.vQueued.size()+ " participants waiting for a partner");
       //this.checkIfCanStart();
    }
     
    
    
    public void checkIfCanStart(){
         if (this.vQueued.size()>1){
             TelegramParticipant tpA = this.vQueued.elementAt(0);
             TelegramParticipant tpB = this.vQueued.elementAt(1);
             this.startParticipants(tpA, tpB);
             this.vQueued.remove(tpA);
             this.vQueued.remove(tpB);
         }
    }
    
    JInterfaceTenButtons jtb = new JInterfaceTenButtons(this, "4_1","4_2","4_3","6_1","6_2","6_3","8_1","8_2","8_3WITHIN","8_3BETWEEN","","","","","");
    
    
    public void startExperiment_4_1(){
             this.startParticipants(vQueued.elementAt(0), vQueued.elementAt(1));
             this.startParticipants(vQueued.elementAt(2), vQueued.elementAt(3));
    }
    public void startExperiment_4_2(){
             this.startParticipants(vQueued.elementAt(0), vQueued.elementAt(2));
             this.startParticipants(vQueued.elementAt(1), vQueued.elementAt(3));
    }

    public void startExperiment_4_3(){
             this.startParticipants(vQueued.elementAt(0), vQueued.elementAt(3));
             this.startParticipants(vQueued.elementAt(1), vQueued.elementAt(2));
    }
    
    
    
    
    public void startExperiment_6_1(){
             this.startParticipants(vQueued.elementAt(0), vQueued.elementAt(1));
             this.startParticipants(vQueued.elementAt(2), vQueued.elementAt(3));
             this.startParticipants(vQueued.elementAt(4), vQueued.elementAt(5));
    }
    public void startExperiment_6_2(){
             this.startParticipants(vQueued.elementAt(0), vQueued.elementAt(2));
             this.startParticipants(vQueued.elementAt(1), vQueued.elementAt(3));
             this.startParticipants(vQueued.elementAt(4), vQueued.elementAt(5));
    }
    
    public void startExperiment_6_3(){
             this.startParticipants(vQueued.elementAt(0), vQueued.elementAt(3));
             this.startParticipants(vQueued.elementAt(1), vQueued.elementAt(2));
             this.startParticipants(vQueued.elementAt(4), vQueued.elementAt(5));
             
             
             
             
    } 
    public void startExperiment_8_1(){
             this.startParticipants(vQueued.elementAt(0), vQueued.elementAt(1));
             this.startParticipants(vQueued.elementAt(2), vQueued.elementAt(3));
    
             this.startParticipants(vQueued.elementAt(4), vQueued.elementAt(5));
             this.startParticipants(vQueued.elementAt(6), vQueued.elementAt(7));
            
    }
    public void startExperiment_8_2(){
             this.startParticipants(vQueued.elementAt(0), vQueued.elementAt(2));
             this.startParticipants(vQueued.elementAt(1), vQueued.elementAt(3));
             
             this.startParticipants(vQueued.elementAt(4), vQueued.elementAt(6));
             this.startParticipants(vQueued.elementAt(5), vQueued.elementAt(7));
    }

    public void startExperiment_8_3WITHIN(){
             this.startParticipants(vQueued.elementAt(0), vQueued.elementAt(3));
             this.startParticipants(vQueued.elementAt(1), vQueued.elementAt(2));     
             
             this.startParticipants(vQueued.elementAt(4), vQueued.elementAt(7));
             this.startParticipants(vQueued.elementAt(5), vQueued.elementAt(6)); 
    }
    
    public void startExperiment_8_3BETWEEN(){
             this.startParticipants(vQueued.elementAt(0), vQueued.elementAt(4));
             this.startParticipants(vQueued.elementAt(1), vQueued.elementAt(5));     
             
             this.startParticipants(vQueued.elementAt(2), vQueued.elementAt(6));
             this.startParticipants(vQueued.elementAt(3), vQueued.elementAt(7)); 
    }    
    
    
    
    
    
    
    @Override
    public void performActionTriggeredByUI(String s) {
        if   (s.equalsIgnoreCase("4_1"))startExperiment_4_1();
        else if(s.equalsIgnoreCase("4_2"))startExperiment_4_2();
        else if(s.equalsIgnoreCase("4_3"))startExperiment_4_3();
        else if(s.equalsIgnoreCase("6_1"))startExperiment_6_1();
        else if(s.equalsIgnoreCase("6_2"))startExperiment_6_2();
        else if(s.equalsIgnoreCase("6_3"))startExperiment_6_3();
        else if(s.equalsIgnoreCase("8_1"))startExperiment_8_1();
        else if(s.equalsIgnoreCase("8_2"))startExperiment_8_2();
        else if(s.equalsIgnoreCase("8_3"))startExperiment_8_3WITHIN();
        else if(s.equalsIgnoreCase("8_3"))startExperiment_8_3BETWEEN();

    }
    
    
    

    
    
    
    

    @Override
    public void telegram_processTelegramMessageFromClient(TelegramParticipant sender, TelegramMessageFromClient tmfc) {
         CustomizableReferentialTask crt =  (CustomizableReferentialTask)  this.htTasks.get(sender);
         if(crt ==null){
             //c.saveErrorLog("Noncritical error! Cannot find the Referential task for the participant "+sender.getParticipantID()+ " this is probably because the experiment hasn`t started yet!");
             Conversation.printWSln("Main", sender.getUsername() + " sent text before the experiment has started");
             return;
         }
        
        
  
        if(tmfc.u.hasMessage()  && tmfc.u.getMessage().hasText()){
             
             String text=tmfc.u.getMessage().getText();
             crt.processChatText(sender, text);
             if(!text.startsWith("/")){
                c.telegram_relayMessageTextToOtherParticipants(sender, tmfc);      
             } 
             
          
      
        }
        if(this.relayPhotos && tmfc.u.hasMessage()&&  tmfc.u.getMessage().hasPhoto()){
            //Need to block sending of images
            Conversation.printWSln("Main", "One of the participants tried to send an image! This was blocked by the server.");
            // c.telegram_relayMessagePhotoToOtherParticipants_By_File_ID(sender, tmfc);    
        }
        
        if(tmfc.u.hasCallbackQuery()){
            CallbackQuery cbq = tmfc.u.getCallbackQuery();
            Message  m =cbq.getMessage();
            String callbackData =   cbq.getData();
            System.err.println("callbackdata: "+callbackData);
            
            crt.telegram_processButtonPress(sender, tmfc.u);
            
            //c.telegram_respondToCallback(sender, tmfc.u, "THIS IS THE RESPONSE");
            
            
           // String[][] nb = new String[][]{  {"a","b"}, {"c","d","e"}     };
            
            //c.telegram_sendEditMessageReplyMarkup(sender, tmfc.u,nb);
            
        }
        
        
        
    }

   

    
    
    
    
    
    
    
   public static boolean showcCONGUI() {
        return true;
    }
    
}