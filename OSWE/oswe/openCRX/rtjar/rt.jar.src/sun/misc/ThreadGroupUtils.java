/*    */ package sun.misc;
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
/*    */ 
/*    */ public final class ThreadGroupUtils
/*    */ {
/*    */   public static ThreadGroup getRootThreadGroup() {
/* 47 */     ThreadGroup threadGroup1 = Thread.currentThread().getThreadGroup();
/* 48 */     ThreadGroup threadGroup2 = threadGroup1.getParent();
/* 49 */     while (threadGroup2 != null) {
/* 50 */       threadGroup1 = threadGroup2;
/* 51 */       threadGroup2 = threadGroup1.getParent();
/*    */     } 
/* 53 */     return threadGroup1;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/ThreadGroupUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */