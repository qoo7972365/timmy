/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.MutableCallSite;
/*     */ import java.lang.invoke.SwitchPoint;
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
/*     */ public class SwitchPoint
/*     */ {
/* 114 */   private static final MethodHandle K_true = MethodHandles.constant(boolean.class, Boolean.valueOf(true));
/* 115 */   private static final MethodHandle K_false = MethodHandles.constant(boolean.class, Boolean.valueOf(false));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   private final MutableCallSite mcs = new MutableCallSite(K_true);
/* 125 */   private final MethodHandle mcsInvoker = this.mcs.dynamicInvoker();
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
/*     */   public boolean hasBeenInvalidated() {
/* 152 */     return (this.mcs.getTarget() != K_true);
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
/*     */   public MethodHandle guardWithTest(MethodHandle paramMethodHandle1, MethodHandle paramMethodHandle2) {
/* 171 */     if (this.mcs.getTarget() == K_false)
/* 172 */       return paramMethodHandle2; 
/* 173 */     return MethodHandles.guardWithTest(this.mcsInvoker, paramMethodHandle1, paramMethodHandle2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void invalidateAll(SwitchPoint[] paramArrayOfSwitchPoint) {
/* 218 */     if (paramArrayOfSwitchPoint.length == 0)
/* 219 */       return;  MutableCallSite[] arrayOfMutableCallSite = new MutableCallSite[paramArrayOfSwitchPoint.length];
/* 220 */     for (byte b = 0; b < paramArrayOfSwitchPoint.length; b++) {
/* 221 */       SwitchPoint switchPoint = paramArrayOfSwitchPoint[b];
/* 222 */       if (switchPoint == null)
/* 223 */         break;  arrayOfMutableCallSite[b] = switchPoint.mcs;
/* 224 */       switchPoint.mcs.setTarget(K_false);
/*     */     } 
/* 226 */     MutableCallSite.syncAll(arrayOfMutableCallSite);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/SwitchPoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */