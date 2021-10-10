/*    */ package sun.security.smartcardio;
/*    */ 
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ import java.security.Provider;
/*    */ import javax.smartcardio.CardTerminals;
/*    */ import javax.smartcardio.TerminalFactorySpi;
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
/*    */ public final class SunPCSC
/*    */   extends Provider
/*    */ {
/*    */   private static final long serialVersionUID = 6168388284028876579L;
/*    */   
/*    */   public SunPCSC() {
/* 43 */     super("SunPCSC", 1.8D, "Sun PC/SC provider");
/* 44 */     AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*    */           public Void run() {
/* 46 */             SunPCSC.this.put("TerminalFactory.PC/SC", "sun.security.smartcardio.SunPCSC$Factory");
/* 47 */             return null;
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public static final class Factory extends TerminalFactorySpi {
/*    */     public Factory(Object param1Object) throws PCSCException {
/* 54 */       if (param1Object != null) {
/* 55 */         throw new IllegalArgumentException("SunPCSC factory does not use parameters");
/*    */       }
/*    */ 
/*    */       
/* 59 */       PCSC.checkAvailable();
/* 60 */       PCSCTerminals.initContext();
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     protected CardTerminals engineTerminals() {
/* 67 */       return new PCSCTerminals();
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/smartcardio/SunPCSC.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */