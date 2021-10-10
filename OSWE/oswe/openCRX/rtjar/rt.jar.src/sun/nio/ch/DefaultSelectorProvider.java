/*    */ package sun.nio.ch;
/*    */ 
/*    */ import java.nio.channels.spi.SelectorProvider;
/*    */ import java.security.AccessController;
/*    */ import sun.security.action.GetPropertyAction;
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
/*    */ public class DefaultSelectorProvider
/*    */ {
/*    */   private static SelectorProvider createProvider(String paramString) {
/*    */     Class<?> clazz;
/*    */     try {
/* 47 */       clazz = Class.forName(paramString);
/* 48 */     } catch (ClassNotFoundException classNotFoundException) {
/* 49 */       throw new AssertionError(classNotFoundException);
/*    */     } 
/*    */     try {
/* 52 */       return (SelectorProvider)clazz.newInstance();
/* 53 */     } catch (IllegalAccessException|InstantiationException illegalAccessException) {
/* 54 */       throw new AssertionError(illegalAccessException);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SelectorProvider create() {
/* 64 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("os.name"));
/* 65 */     if (str.equals("SunOS"))
/* 66 */       return createProvider("sun.nio.ch.DevPollSelectorProvider"); 
/* 67 */     if (str.equals("Linux"))
/* 68 */       return createProvider("sun.nio.ch.EPollSelectorProvider"); 
/* 69 */     return new PollSelectorProvider();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/DefaultSelectorProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */