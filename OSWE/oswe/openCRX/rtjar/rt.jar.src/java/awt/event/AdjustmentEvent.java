/*     */ package java.awt.event;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.Adjustable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AdjustmentEvent
/*     */   extends AWTEvent
/*     */ {
/*     */   public static final int ADJUSTMENT_FIRST = 601;
/*     */   public static final int ADJUSTMENT_LAST = 601;
/*     */   public static final int ADJUSTMENT_VALUE_CHANGED = 601;
/*     */   public static final int UNIT_INCREMENT = 1;
/*     */   public static final int UNIT_DECREMENT = 2;
/*     */   public static final int BLOCK_DECREMENT = 3;
/*     */   public static final int BLOCK_INCREMENT = 4;
/*     */   public static final int TRACK = 5;
/*     */   Adjustable adjustable;
/*     */   int value;
/*     */   int adjustmentType;
/*     */   boolean isAdjusting;
/*     */   private static final long serialVersionUID = 5700290645205279921L;
/*     */   
/*     */   public AdjustmentEvent(Adjustable paramAdjustable, int paramInt1, int paramInt2, int paramInt3) {
/* 173 */     this(paramAdjustable, paramInt1, paramInt2, paramInt3, false);
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
/*     */   public AdjustmentEvent(Adjustable paramAdjustable, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
/* 204 */     super(paramAdjustable, paramInt1);
/* 205 */     this.adjustable = paramAdjustable;
/* 206 */     this.adjustmentType = paramInt2;
/* 207 */     this.value = paramInt3;
/* 208 */     this.isAdjusting = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Adjustable getAdjustable() {
/* 217 */     return this.adjustable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValue() {
/* 226 */     return this.value;
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
/*     */   public int getAdjustmentType() {
/* 242 */     return this.adjustmentType;
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
/*     */   public boolean getValueIsAdjusting() {
/* 254 */     return this.isAdjusting;
/*     */   }
/*     */   
/*     */   public String paramString() {
/*     */     String str1;
/* 259 */     switch (this.id) {
/*     */       case 601:
/* 261 */         str1 = "ADJUSTMENT_VALUE_CHANGED";
/*     */         break;
/*     */       default:
/* 264 */         str1 = "unknown type";
/*     */         break;
/*     */     } 
/* 267 */     switch (this.adjustmentType)
/*     */     { case 1:
/* 269 */         str2 = "UNIT_INCREMENT";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 286 */         return str1 + ",adjType=" + str2 + ",value=" + this.value + ",isAdjusting=" + this.isAdjusting;case 2: str2 = "UNIT_DECREMENT"; return str1 + ",adjType=" + str2 + ",value=" + this.value + ",isAdjusting=" + this.isAdjusting;case 4: str2 = "BLOCK_INCREMENT"; return str1 + ",adjType=" + str2 + ",value=" + this.value + ",isAdjusting=" + this.isAdjusting;case 3: str2 = "BLOCK_DECREMENT"; return str1 + ",adjType=" + str2 + ",value=" + this.value + ",isAdjusting=" + this.isAdjusting;case 5: str2 = "TRACK"; return str1 + ",adjType=" + str2 + ",value=" + this.value + ",isAdjusting=" + this.isAdjusting; }  String str2 = "unknown type"; return str1 + ",adjType=" + str2 + ",value=" + this.value + ",isAdjusting=" + this.isAdjusting;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/event/AdjustmentEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */