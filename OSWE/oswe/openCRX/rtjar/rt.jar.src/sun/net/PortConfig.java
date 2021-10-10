/*    */ package sun.net;
/*    */ 
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
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
/*    */ public final class PortConfig
/*    */ {
/*    */   private static int defaultUpper;
/*    */   private static int defaultLower;
/*    */   private static final int upper;
/*    */   private static final int lower;
/*    */   
/*    */   static {
/* 44 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*    */         {
/*    */           public Void run() {
/* 47 */             System.loadLibrary("net");
/* 48 */             String str = System.getProperty("os.name");
/* 49 */             if (str.startsWith("Linux")) {
/* 50 */               PortConfig.defaultLower = 32768;
/* 51 */               PortConfig.defaultUpper = 61000;
/* 52 */             } else if (str.startsWith("SunOS")) {
/* 53 */               PortConfig.defaultLower = 32768;
/* 54 */               PortConfig.defaultUpper = 65535;
/* 55 */             } else if (str.contains("OS X")) {
/* 56 */               PortConfig.defaultLower = 49152;
/* 57 */               PortConfig.defaultUpper = 65535;
/* 58 */             } else if (str.startsWith("AIX")) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */               
/* 64 */               PortConfig.defaultLower = 32768;
/* 65 */               PortConfig.defaultUpper = 65535;
/*    */             } else {
/* 67 */               throw new InternalError("sun.net.PortConfig: unknown OS");
/*    */             } 
/*    */             
/* 70 */             return null;
/*    */           }
/*    */         });
/*    */     
/* 74 */     int i = getLower0();
/* 75 */     if (i == -1) {
/* 76 */       i = defaultLower;
/*    */     }
/* 78 */     lower = i;
/*    */     
/* 80 */     i = getUpper0();
/* 81 */     if (i == -1) {
/* 82 */       i = defaultUpper;
/*    */     }
/* 84 */     upper = i;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int getLower() {
/* 91 */     return lower;
/*    */   }
/*    */   
/*    */   public static int getUpper() {
/* 95 */     return upper;
/*    */   }
/*    */   
/*    */   static native int getLower0();
/*    */   
/*    */   static native int getUpper0();
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/PortConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */