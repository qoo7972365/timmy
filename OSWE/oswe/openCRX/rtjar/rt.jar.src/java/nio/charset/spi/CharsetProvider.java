/*    */ package java.nio.charset.spi;
/*    */ 
/*    */ import java.nio.charset.Charset;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class CharsetProvider
/*    */ {
/*    */   protected CharsetProvider() {
/* 82 */     SecurityManager securityManager = System.getSecurityManager();
/* 83 */     if (securityManager != null)
/* 84 */       securityManager.checkPermission(new RuntimePermission("charsetProvider")); 
/*    */   }
/*    */   
/*    */   public abstract Iterator<Charset> charsets();
/*    */   
/*    */   public abstract Charset charsetForName(String paramString);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/charset/spi/CharsetProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */