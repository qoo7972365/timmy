/*    */ package sun.security.x509;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class X509AttributeName
/*    */ {
/*    */   private static final char SEPARATOR = '.';
/* 39 */   private String prefix = null;
/* 40 */   private String suffix = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public X509AttributeName(String paramString) {
/* 49 */     int i = paramString.indexOf('.');
/* 50 */     if (i < 0) {
/* 51 */       this.prefix = paramString;
/*    */     } else {
/* 53 */       this.prefix = paramString.substring(0, i);
/* 54 */       this.suffix = paramString.substring(i + 1);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPrefix() {
/* 62 */     return this.prefix;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSuffix() {
/* 69 */     return this.suffix;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/x509/X509AttributeName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */