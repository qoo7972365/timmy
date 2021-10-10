/*    */ package sun.misc;
/*    */ 
/*    */ import java.util.concurrent.locks.ReentrantLock;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class GThreadHelper
/*    */ {
/* 55 */   private static final ReentrantLock LOCK = new ReentrantLock();
/*    */ 
/*    */   
/*    */   private static boolean isGThreadInitialized = false;
/*    */ 
/*    */   
/*    */   public static void lock() {
/* 62 */     LOCK.lock();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void unlock() {
/* 69 */     LOCK.unlock();
/*    */   }
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
/*    */   public static boolean getAndSetInitializationNeededFlag() {
/* 83 */     boolean bool = isGThreadInitialized;
/* 84 */     isGThreadInitialized = true;
/* 85 */     return bool;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/GThreadHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */