/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.ConversationController;

import diet.server.Conversation;
import diet.task.CustomizableReferentialTask.CustomizableReferentialTask;
import diet.task.CustomizableReferentialTask.CustomizableReferentialTaskSettings;
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
public class Telegram_Dyadic_Automatic_Start_Extended extends TelegramController{

    
    
       
    public Telegram_Dyadic_Automatic_Start_Extended(Conversation c) {
        super(c);
       
    }

    public Telegram_Dyadic_Automatic_Start_Extended(Conversation c, long istypingtimeout) {
        super(c, istypingtimeout);
       
        
    }

    
    public Vector<TelegramParticipant> vQueued = new Vector();
     
    
 
     public void startParticipants(TelegramParticipant tp1, TelegramParticipant tp2){
         pp.createNewSubdialogue(tp1,tp2);
         c.telegram_sendInstructionToParticipant_MonospaceFont(tp1, "Please start!");
         c.telegram_sendInstructionToParticipant_MonospaceFont(tp2, "Please start!");
     }
    
    
    

   
     public void telegram_participantJoinedConversation(TelegramParticipant p) {
        vQueued.add(p);
        Conversation.printWSln("Main", "Participant "+p.getUsername()+ " logged in.  There are now "+this.vQueued.size()+ " participants waiting for a partner");
        this.checkIfCanStart();
    }
    
   
    @Override
    public void telegram_participantReJoinedConversation(TelegramParticipant p) {
       vQueued.add(p);
       Conversation.printWSln("Main", "Participant "+p.getUsername()+ " relogged in. There are now "+this.vQueued.size()+ " participants waiting for a partner");
       this.checkIfCanStart();
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
    
    
    
    
    

    
    
    
    

    @Override
    public void telegram_processTelegramMessageFromClient(TelegramParticipant sender, TelegramMessageFromClient tmfc) {
         
        
        
  
        if(tmfc.u.hasMessage()  && tmfc.u.getMessage().hasText()){            
             String text=tmfc.u.getMessage().getText();  
             c.telegram_relayMessageTextToOtherParticipants(sender, tmfc);            
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
            
            //crt.telegram_processButtonPress(sender, tmfc.u);
            
            //c.telegram_respondToCallback(sender, tmfc.u, "THIS IS THE RESPONSE");
            
            
           // String[][] nb = new String[][]{  {"a","b"}, {"c","d","e"}     };
            
            //c.telegram_sendEditMessageReplyMarkup(sender, tmfc.u,nb);
            
        }
        
        
        
    }

   

    
    
    
    
    
    
    
   public static boolean showcCONGUI() {
        return true;
    }
    
}
