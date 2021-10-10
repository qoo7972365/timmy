/*    */ package java.io;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.LinkedHashSet;
/*    */ import sun.misc.SharedSecrets;
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
/*    */ class DeleteOnExitHook
/*    */ {
/* 37 */   private static LinkedHashSet<String> files = new LinkedHashSet<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 44 */     SharedSecrets.getJavaLangAccess()
/* 45 */       .registerShutdownHook(2, true, new Runnable()
/*    */         {
/*    */           public void run()
/*    */           {
/* 49 */             DeleteOnExitHook.runHooks();
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static synchronized void add(String paramString) {
/* 58 */     if (files == null)
/*    */     {
/* 60 */       throw new IllegalStateException("Shutdown in progress");
/*    */     }
/*    */     
/* 63 */     files.add(paramString);
/*    */   }
/*    */ 
/*    */   
/*    */   static void runHooks() {
/*    */     LinkedHashSet<String> linkedHashSet;
/* 69 */     synchronized (DeleteOnExitHook.class) {
/* 70 */       linkedHashSet = files;
/* 71 */       files = null;
/*    */     } 
/*    */     
/* 74 */     ArrayList<String> arrayList = new ArrayList<>(linkedHashSet);
/*    */ 
/*    */ 
/*    */     
/* 78 */     Collections.reverse(arrayList);
/* 79 */     for (String str : arrayList)
/* 80 */       (new File(str)).delete(); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/DeleteOnExitHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */