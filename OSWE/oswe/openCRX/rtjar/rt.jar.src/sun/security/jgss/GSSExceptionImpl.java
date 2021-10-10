/*    */ package sun.security.jgss;
/*    */ 
/*    */ import org.ietf.jgss.GSSException;
/*    */ import org.ietf.jgss.Oid;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GSSExceptionImpl
/*    */   extends GSSException
/*    */ {
/*    */   private static final long serialVersionUID = 4251197939069005575L;
/*    */   private String majorMessage;
/*    */   
/*    */   GSSExceptionImpl(int paramInt, Oid paramOid) {
/* 46 */     super(paramInt);
/* 47 */     this.majorMessage = getMajorString() + ": " + paramOid;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GSSExceptionImpl(int paramInt, String paramString) {
/* 55 */     super(paramInt);
/* 56 */     this.majorMessage = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GSSExceptionImpl(int paramInt, Exception paramException) {
/* 63 */     super(paramInt);
/* 64 */     initCause(paramException);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GSSExceptionImpl(int paramInt, String paramString, Exception paramException) {
/* 73 */     this(paramInt, paramString);
/* 74 */     initCause(paramException);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 83 */     if (this.majorMessage != null) {
/* 84 */       return this.majorMessage;
/*    */     }
/* 86 */     return super.getMessage();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/GSSExceptionImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */