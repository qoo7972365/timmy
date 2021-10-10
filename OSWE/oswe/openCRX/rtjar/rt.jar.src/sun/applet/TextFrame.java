/*    */ package sun.applet;
/*    */ 
/*    */ import java.awt.Button;
/*    */ import java.awt.Frame;
/*    */ import java.awt.Panel;
/*    */ import java.awt.TextArea;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import java.awt.event.WindowAdapter;
/*    */ import java.awt.event.WindowEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class TextFrame
/*    */   extends Frame
/*    */ {
/*    */   TextFrame(int paramInt1, int paramInt2, String paramString1, String paramString2) {
/* 52 */     setTitle(paramString1);
/* 53 */     TextArea textArea = new TextArea(20, 60);
/* 54 */     textArea.setText(paramString2);
/* 55 */     textArea.setEditable(false);
/*    */     
/* 57 */     add("Center", textArea);
/*    */     
/* 59 */     Panel panel = new Panel();
/* 60 */     add("South", panel);
/* 61 */     Button button = new Button(amh.getMessage("button.dismiss", "Dismiss"));
/* 62 */     panel.add(button);
/*    */     class ActionEventListener
/*    */       implements ActionListener
/*    */     {
/*    */       public void actionPerformed(ActionEvent param1ActionEvent) {
/* 67 */         TextFrame.this.dispose();
/*    */       }
/*    */     };
/* 70 */     button.addActionListener(new ActionEventListener());
/*    */     
/* 72 */     pack();
/* 73 */     move(paramInt1, paramInt2);
/* 74 */     setVisible(true);
/*    */     
/* 76 */     WindowAdapter windowAdapter = new WindowAdapter()
/*    */       {
/*    */         public void windowClosing(WindowEvent param1WindowEvent)
/*    */         {
/* 80 */           TextFrame.this.dispose();
/*    */         }
/*    */       };
/*    */     
/* 84 */     addWindowListener(windowAdapter);
/*    */   }
/* 86 */   private static AppletMessageHandler amh = new AppletMessageHandler("textframe");
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/applet/TextFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */