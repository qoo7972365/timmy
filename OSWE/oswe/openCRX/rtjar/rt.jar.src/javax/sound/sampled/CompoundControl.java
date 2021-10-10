/*     */ package javax.sound.sampled;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CompoundControl
/*     */   extends Control
/*     */ {
/*     */   private Control[] controls;
/*     */   
/*     */   protected CompoundControl(Type paramType, Control[] paramArrayOfControl) {
/*  63 */     super(paramType);
/*  64 */     this.controls = paramArrayOfControl;
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
/*     */   public Control[] getMemberControls() {
/*  78 */     Control[] arrayOfControl = new Control[this.controls.length];
/*     */     
/*  80 */     for (byte b = 0; b < this.controls.length; b++) {
/*  81 */       arrayOfControl[b] = this.controls[b];
/*     */     }
/*     */     
/*  84 */     return arrayOfControl;
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
/*     */   public String toString() {
/*  97 */     StringBuffer stringBuffer = new StringBuffer();
/*  98 */     for (byte b = 0; b < this.controls.length; b++) {
/*  99 */       if (b != 0) {
/* 100 */         stringBuffer.append(", ");
/* 101 */         if (b + 1 == this.controls.length) {
/* 102 */           stringBuffer.append("and ");
/*     */         }
/*     */       } 
/* 105 */       stringBuffer.append(this.controls[b].getType());
/*     */     } 
/*     */     
/* 108 */     return new String(getType() + " Control containing " + stringBuffer + " Controls.");
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
/*     */   public static class Type
/*     */     extends Control.Type
/*     */   {
/*     */     protected Type(String param1String) {
/* 136 */       super(param1String);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sound/sampled/CompoundControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */