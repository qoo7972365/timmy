/*    */ package sun.security.jgss;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GSSCaller
/*    */ {
/* 34 */   public static final GSSCaller CALLER_UNKNOWN = new GSSCaller("UNKNOWN");
/* 35 */   public static final GSSCaller CALLER_INITIATE = new GSSCaller("INITIATE");
/* 36 */   public static final GSSCaller CALLER_ACCEPT = new GSSCaller("ACCEPT");
/* 37 */   public static final GSSCaller CALLER_SSL_CLIENT = new GSSCaller("SSL_CLIENT");
/* 38 */   public static final GSSCaller CALLER_SSL_SERVER = new GSSCaller("SSL_SERVER");
/*    */   private String name;
/*    */   
/*    */   GSSCaller(String paramString) {
/* 42 */     this.name = paramString;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 46 */     return "GSSCaller{" + this.name + '}';
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/GSSCaller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */