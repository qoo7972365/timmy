/*    */ package sun.security.action;
/*    */ 
/*    */ import java.security.PrivilegedAction;
/*    */ import java.security.Provider;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PutAllAction
/*    */   implements PrivilegedAction<Void>
/*    */ {
/*    */   private final Provider provider;
/*    */   private final Map<?, ?> map;
/*    */   
/*    */   public PutAllAction(Provider paramProvider, Map<?, ?> paramMap) {
/* 47 */     this.provider = paramProvider;
/* 48 */     this.map = paramMap;
/*    */   }
/*    */   
/*    */   public Void run() {
/* 52 */     this.provider.putAll(this.map);
/* 53 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/action/PutAllAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */