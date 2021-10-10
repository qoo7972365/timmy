/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.lang.invoke.CallSite;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.lang.invoke.VolatileCallSite;
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
/*     */ public class VolatileCallSite
/*     */   extends CallSite
/*     */ {
/*     */   public VolatileCallSite(MethodType paramMethodType) {
/*  53 */     super(paramMethodType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VolatileCallSite(MethodHandle paramMethodHandle) {
/*  63 */     super(paramMethodHandle);
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
/*     */   public final MethodHandle getTarget() {
/*  81 */     return getTargetVolatile();
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
/*     */   public void setTarget(MethodHandle paramMethodHandle) {
/*  98 */     checkTargetChange(getTargetVolatile(), paramMethodHandle);
/*  99 */     setTargetVolatile(paramMethodHandle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final MethodHandle dynamicInvoker() {
/* 107 */     return makeDynamicInvoker();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/VolatileCallSite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */