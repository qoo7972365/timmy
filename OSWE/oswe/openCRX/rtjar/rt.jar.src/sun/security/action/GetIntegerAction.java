/*     */ package sun.security.action;
/*     */ 
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GetIntegerAction
/*     */   implements PrivilegedAction<Integer>
/*     */ {
/*     */   private String theProp;
/*     */   private int defaultVal;
/*     */   private boolean defaultSet = false;
/*     */   
/*     */   public GetIntegerAction(String paramString) {
/*  79 */     this.theProp = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GetIntegerAction(String paramString, int paramInt) {
/*  90 */     this.theProp = paramString;
/*  91 */     this.defaultVal = paramInt;
/*  92 */     this.defaultSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer run() {
/* 108 */     Integer integer = Integer.getInteger(this.theProp);
/* 109 */     if (integer == null && this.defaultSet)
/* 110 */       return new Integer(this.defaultVal); 
/* 111 */     return integer;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/action/GetIntegerAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */