/*    */ package javax.net.ssl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SNIMatcher
/*    */ {
/*    */   private final int type;
/*    */   
/*    */   protected SNIMatcher(int paramInt) {
/* 66 */     if (paramInt < 0) {
/* 67 */       throw new IllegalArgumentException("Server name type cannot be less than zero");
/*    */     }
/* 69 */     if (paramInt > 255) {
/* 70 */       throw new IllegalArgumentException("Server name type cannot be greater than 255");
/*    */     }
/*    */ 
/*    */     
/* 74 */     this.type = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final int getType() {
/* 85 */     return this.type;
/*    */   }
/*    */   
/*    */   public abstract boolean matches(SNIServerName paramSNIServerName);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/net/ssl/SNIMatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */