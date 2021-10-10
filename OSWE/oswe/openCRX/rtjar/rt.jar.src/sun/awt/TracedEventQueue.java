/*    */ package sun.awt;
/*    */ 
/*    */ import java.awt.AWTEvent;
/*    */ import java.awt.EventQueue;
/*    */ import java.awt.Toolkit;
/*    */ import java.util.StringTokenizer;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TracedEventQueue
/*    */   extends EventQueue
/*    */ {
/*    */   static boolean trace = false;
/* 52 */   static int[] suppressedIDs = null;
/*    */   
/*    */   static {
/* 55 */     String str = Toolkit.getProperty("AWT.IgnoreEventIDs", "");
/* 56 */     if (str.length() > 0) {
/* 57 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 58 */       int i = stringTokenizer.countTokens();
/* 59 */       suppressedIDs = new int[i];
/* 60 */       for (byte b = 0; b < i; b++) {
/* 61 */         String str1 = stringTokenizer.nextToken();
/*    */         try {
/* 63 */           suppressedIDs[b] = Integer.parseInt(str1);
/* 64 */         } catch (NumberFormatException numberFormatException) {
/* 65 */           System.err.println("Bad ID listed in AWT.IgnoreEventIDs in awt.properties: \"" + str1 + "\" -- skipped");
/*    */ 
/*    */           
/* 68 */           suppressedIDs[b] = 0;
/*    */         } 
/*    */       } 
/*    */     } else {
/* 72 */       suppressedIDs = new int[0];
/*    */     } 
/*    */   }
/*    */   
/*    */   public void postEvent(AWTEvent paramAWTEvent) {
/* 77 */     boolean bool = true;
/* 78 */     int i = paramAWTEvent.getID();
/* 79 */     for (byte b = 0; b < suppressedIDs.length; b++) {
/* 80 */       if (i == suppressedIDs[b]) {
/* 81 */         bool = false;
/*    */         break;
/*    */       } 
/*    */     } 
/* 85 */     if (bool) {
/* 86 */       System.out.println(Thread.currentThread().getName() + ": " + paramAWTEvent);
/*    */     }
/*    */     
/* 89 */     super.postEvent(paramAWTEvent);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/TracedEventQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */