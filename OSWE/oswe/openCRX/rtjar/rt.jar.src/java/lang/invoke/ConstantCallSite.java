/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.lang.invoke.CallSite;
/*     */ import java.lang.invoke.ConstantCallSite;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodType;
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
/*     */ public class ConstantCallSite
/*     */   extends CallSite
/*     */ {
/*     */   private final boolean isFrozen;
/*     */   
/*     */   public ConstantCallSite(MethodHandle paramMethodHandle) {
/*  43 */     super(paramMethodHandle);
/*  44 */     this.isFrozen = true;
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
/*     */   protected ConstantCallSite(MethodType paramMethodType, MethodHandle paramMethodHandle) throws Throwable {
/*  81 */     super(paramMethodType, paramMethodHandle);
/*  82 */     this.isFrozen = true;
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
/*     */   public final MethodHandle getTarget() {
/*  95 */     if (!this.isFrozen) throw new IllegalStateException(); 
/*  96 */     return this.target;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setTarget(MethodHandle paramMethodHandle) {
/* 106 */     throw new UnsupportedOperationException();
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
/*     */   public final MethodHandle dynamicInvoker() {
/* 118 */     return getTarget();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/ConstantCallSite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */