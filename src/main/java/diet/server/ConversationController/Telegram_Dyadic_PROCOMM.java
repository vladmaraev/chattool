/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diet.server.ConversationController;

import diet.attribval.AttribVal;
import diet.server.Conversation;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.ConversationController.ui.JInterfaceMenuButtonsReceiverInterface;
import diet.server.ConversationController.ui.JInterfaceTenButtons;
import diet.server.Participant;
import diet.task.ProceduralComms.PCTaskTG;
import diet.task.ProceduralComms.Quad;
import diet.tg.TelegramMessageFromClient;
import diet.tg.TelegramParticipant;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 *
 * @author LX1C
 */
public class Telegram_Dyadic_PROCOMM extends TelegramController implements JInterfaceMenuButtonsReceiverInterface{

    //// 31685435608
     // 31686348684
    
    
   //Quad of 4
   //Start with practice                                                                             (DONE)
   //Practice is unbounded                                                                           (DONE)
   //button to swap within  (check is in experiment mode before swap)                                (DONE)
   //button to swap between (Check is in experiment mode before swap)                            
   //code that makes other appear as participant X                                                   (DONE)
   //Need to be able to see what level they are at and what practice level they have achieved        
   //The experiment size needs to be bounded....in terms of the maximum length of the sequence       (DONE)
   // Make sure it scores points correctly
   // Make it do the timeout for simultaneous moves correctly 
   // 
   //Quad of 8
   //Wire up so that it can do the same as quad of 4.
   //Automate the timing of swaps...
   //Needs to be able to swap between the 8
   //The timing needs to be done so that the swap doesn`t deadlock with the other timer!
   //   It will need to simultaneously acquire locks on ALL the PCTaskTG ...and only then do the swap...
   //   Should kill be synchronized in PCTaskTG
   //   Should kill remove reference to cC (instantly)? 
   // 
   //Sorts through list of participants in Telegram ID order (So that it can be restarted in case of crash)
   //Loads highest level to datafile (so can be recovered from crash)
   //TEST CRASH RESISTANCE
   //
   //The practice stage is used to make sure that participant technology works...IF they don`t get to a certain point, then they can be excluded...and experiment restarted....
    //Add functionality where when you do group assignment, it activates that in the ConversationController
    
    //Make it save in the CSV what the quad is and the swapstate

    //
    // Make it so that it records the number of points of each person and pair..
    //
    //Make it so that in the first game it ranks them by level....so that there is one quad with the w
    
    
    // Check the output file! Who was interacting with whom..  
    
    //
    //
    // Needs to have "joker" participant so that if there are 9,11,13,15,17,19 participants...they participate....OR alternatively simply get told to wait...
    
    
   //Needs to sort through IDs numerically and then assign themst be unbou
    //Practice game must be unbounded...
    //
    //Needs to be able to start with...
    //Must NOT be able to
    //Then automate the swapping...within AND between
    //Then be able to deal with
    
    JFrame jfUI;
    
    JInterfaceTenButtons jitb = new JInterfaceTenButtons (this, "practice stage", "start experiment", "start timer", "pause timer"   ,"swap within","swap between","","","","");
    
    public Telegram_Dyadic_PROCOMM(Conversation c) {
        super(c);
        
    }

    public Telegram_Dyadic_PROCOMM(Conversation c, long istypingtimeout) {
        super(c, istypingtimeout);
        
        
    }

    @Override
    public void performActionTriggeredByUI(String s) {
      
        
        
        
        
        if(s.equalsIgnoreCase("swap within")){
       
            try{
                //Thread.sleep(5000);
                q.swapWITHIN();
                buildUI();
            
            }catch(Exception e){e.printStackTrace();}         
        }
         else if (s.equalsIgnoreCase("swap between")){
            TelegramParticipant[] tps = q.swapBETWEEN();
             
             
             
            buildUI();
        }
         else if (s.equalsIgnoreCase("practice stage")){
            q.startPRACTICE();
            buildUI();
        }
        
        
        else if (s.equalsIgnoreCase("start experiment")){
            q.startEXPERIMENT();
            buildUI();
        }
        
        
        else if (s.equalsIgnoreCase("start timer")){
            q.startTIMER();
        }
        else if(s.equalsIgnoreCase("pause timer")){
            q.pauseTIMER();
        }
        
        else if (s.equalsIgnoreCase("send explanation once")){
            //pctg.sendInstructionsOnce();
        }
        
        
        
    }

    
    
    
     Quad q;
    
    
    

   
     public void telegram_participantJoinedConversation(TelegramParticipant p) {
              
        this.generatePinnedMessage(p);
         
        if(c.getParticipants().getAllParticipants().size()==4) {
            
             //pp.createNewSubdialogue(c.getParticipants().getAllParticipants());
               
             CustomDialog.showDialog("PRESS OK TO START!");
             this.experimentHasStarted=true;
             
             q = new Quad(this,(TelegramParticipant)c.getParticipants().getAllParticipants().elementAt(0), (TelegramParticipant)c.getParticipants().getAllParticipants().elementAt(1),
                     (TelegramParticipant)c.getParticipants().getAllParticipants().elementAt(2), (TelegramParticipant)c.getParticipants().getAllParticipants().elementAt(3));
             
             buildUI();
             
        }
       
    }
    
     public void buildUI(){
         
         if(SwingUtilities.isEventDispatchThread()){
             if(jfUI!=null){
                try{ jfUI.dispose();}catch(Exception e){e.printStackTrace(); }
             }
             jfUI = new JFrame();
             JPanel jpui = q.getUI();
             //JFrame jf = new JFrame();
             jfUI.getContentPane().add(jpui);
             jfUI.pack();
             jfUI.setVisible(true);
             positionInTopRight(jfUI);
         }
         else{
             try{
             SwingUtilities.invokeAndWait(new Runnable(){
                 public void run(){
                     if(jfUI!=null){
                          try{ jfUI.dispose();}catch(Exception e){e.printStackTrace(); }
                     }
                        jfUI = new JFrame();
                        JPanel jpui = q.getUI();
                        //JFrame jf = new JFrame();
                        jfUI.getContentPane().add(jpui);
                        jfUI.pack();
                        jfUI.setVisible(true);
                         positionInTopRight(jfUI);
                            }
             });
             }catch(Exception e){
                 e.printStackTrace();
                 Conversation.saveErr(e);
                 Conversation.saveErr("ERROR IN BUILDING UI");
                if(jfUI!=null){
                    try{ jfUI.dispose();}catch(Exception ee){ee.printStackTrace(); }
             }
             jfUI = new JFrame();
             JPanel jpui = q.getUI();
             //JFrame jf = new JFrame();
             jfUI.getContentPane().add(jpui);
             jfUI.pack();
             jfUI.setVisible(true);
              positionInTopRight(jfUI);
             }
         }
         
         
     }
     
     public void positionInTopRight(JFrame jf){
         GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - jf.getWidth();
        int y = 40;
        jf.setLocation(x, y);
        jf.setVisible(true);
     }
     
     
     
   
    @Override
    public void telegram_participantReJoinedConversation(TelegramParticipant p) {
        
        this.generatePinnedMessage(p);
               
       
       if(c.getParticipants().getAllParticipants().size()==4) {
            
           //  pp.createNewSubdialogue(c.getParticipants().getAllParticipants());
               
             CustomDialog.showDialog("PRESS OK TO START!");
             this.experimentHasStarted=true;
             
             q = new Quad(this,(TelegramParticipant)c.getParticipants().getAllParticipants().elementAt(0), (TelegramParticipant)c.getParticipants().getAllParticipants().elementAt(1),
                     (TelegramParticipant)c.getParticipants().getAllParticipants().elementAt(2), (TelegramParticipant)c.getParticipants().getAllParticipants().elementAt(3));
             
             buildUI();
             
        }
       
       
    }
     

    @Override
    public synchronized void telegram_processTelegramMessageFromClient(TelegramParticipant sender, TelegramMessageFromClient tmfc) {
        if(tmfc.u.hasMessage()  && tmfc.u.getMessage().hasText()){
             String text = tmfc.u.getMessage().getText();
             
             
             
             if(text.startsWith("/menu")){
                 generatePinnedMessage(sender);
             }
             else{
                 System.err.println("Attempting to acquire lock on pctasktg");
                 
                 if(q.isParticipantInQuad(sender)) q.evaluate(sender, tmfc);
                 //if(this.pctg!=null) this.pctg.evaluate(sender, tmfc);
                
                 
             }
             
             
        }
        else{
        try{
            Message pm = tmfc.u.getMessage().getPinnedMessage();
            if(pm!=null&&pm.hasText()){
                
            }else{
                 c.telegram_sendInstructionToParticipant_MonospaceFont(sender, "Please only send text");
            }
            
        }catch(Exception e){
               c.telegram_sendInstructionToParticipant_MonospaceFont(sender, "Please only send text");   
               e.printStackTrace();
               Conversation.saveErr(e);
        }
        }        
          

        
    
    }
    
    
    
    
     //PCTaskTG pctg;
     public Hashtable htPinnedMessages = new Hashtable();
     public Hashtable<TelegramParticipant,String> htMostRecentPinnedText = new Hashtable();
    
    
    public void generatePinnedMessage(TelegramParticipant p){ 
        if(p==null){
            Conversation.saveErr("Trying to generate pinned message for null participant");
            return;
        }
        Message m = c.telegram_sendInstructionToParticipant_MonospaceFont(p, "Please do not close this message. You will need it in the task");
        c.telegram_sendPinChatMessageToParticipant(p, m);
        htPinnedMessages.put(p, m);      
    }
    
    
    
    
    public void changePinnedMessage(TelegramParticipant p,String text){
        System.err.println("CHANGEPINNEDMESSAGE1"+text);
         if(p==null){
            Conversation.saveErr("Trying to change pinned message for null participant. "+text);
            return;
        }
          String mostRecent = this.htMostRecentPinnedText.get(p);
          System.err.println("CHANGEPINNEDMESSAGE2"+text);

          if(mostRecent!=null ){
              System.err.println("CHANGEPINNEDMESSAGE3"+text);
              if(mostRecent.equals(text))return;
              System.err.println("CHANGEPINNEDMESSAGE4"+text);
              if(mostRecent.equalsIgnoreCase(text)){
                    System.err.println("CHANGEPINNEDMESSAGE5"+text);
                    org.telegram.telegrambots.meta.api.objects.Message m = (org.telegram.telegrambots.meta.api.objects.Message)this.htPinnedMessages.get(p);
                    if(m!=null){
                        System.err.println("CHANGEPINNEDMESSAGE6"+text);
                        c.telegram_sendEditMessageToParticipant(p, m, "processing move");
                        htMostRecentPinnedText.put(p,"--------------------");
                    }

              }
          }
         
           System.err.println("CHANGEPINNEDMESSAGE7"+text);
           org.telegram.telegrambots.meta.api.objects.Message m = (org.telegram.telegrambots.meta.api.objects.Message)this.htPinnedMessages.get(p);
           if(m!=null){
               System.err.println("CHANGEPINNEDMESSAGE8"+text);
               c.telegram_sendEditMessageToParticipant(p, m, text);
               htMostRecentPinnedText.put(p,text);
           }
           System.err.println("CHANGEPINNEDMESSAGE9"+text);
     }
    
    
    
     public void changePinnedMessageOLD(TelegramParticipant p,String text){
          String mostRecent = this.htMostRecentPinnedText.get(p);
          if(mostRecent!=null){
              if(mostRecent.equalsIgnoreCase(text))return;
          }
         
         
           org.telegram.telegrambots.meta.api.objects.Message m = (org.telegram.telegrambots.meta.api.objects.Message)this.htPinnedMessages.get(p);
           if(m!=null){
               c.telegram_sendEditMessageToParticipant(p, m, text);
               htMostRecentPinnedText.put(p,text);
           }
     }

    @Override
    public Vector<AttribVal> getAdditionalInformationForParticipant(Participant p) {
        PCTaskTG pctg=null;
        if(pctg!=null) return pctg.getAdditionalValues(p);
        return super.getAdditionalInformationForParticipant(p);
    }
    
    
    
    
    
    
   public static boolean showcCONGUI() {
        return true;
    }
    
   
   
   public PCTaskTG getPCTaskTGForParticipant(TelegramParticipant tp){
       return null;//q.getPCTaskTGForParticipant(tp);
   } 
   
   
   
   
   
   
}







