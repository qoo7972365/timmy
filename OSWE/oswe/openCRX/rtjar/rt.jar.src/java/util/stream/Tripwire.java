/*    */ package java.util.stream;
/*    */ 
/*    */ import java.security.AccessController;
/*    */ import sun.util.logging.PlatformLogger;
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
/*    */ final class Tripwire
/*    */ {
/*    */   private static final String TRIPWIRE_PROPERTY = "org.openjdk.java.util.stream.tripwire";
/* 52 */   static final boolean ENABLED = ((Boolean)AccessController.<Boolean>doPrivileged(() -> Boolean.valueOf(Boolean.getBoolean("org.openjdk.java.util.stream.tripwire")))).booleanValue();
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
/*    */   static void trip(Class<?> paramClass, String paramString) {
/* 67 */     PlatformLogger.getLogger(paramClass.getName()).warning(paramString, new Object[] { paramClass.getName() });
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/stream/Tripwire.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */