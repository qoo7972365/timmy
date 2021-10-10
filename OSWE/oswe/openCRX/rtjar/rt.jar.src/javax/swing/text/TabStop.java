/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TabStop
/*     */   implements Serializable
/*     */ {
/*     */   public static final int ALIGN_LEFT = 0;
/*     */   public static final int ALIGN_RIGHT = 1;
/*     */   public static final int ALIGN_CENTER = 2;
/*     */   public static final int ALIGN_DECIMAL = 4;
/*     */   public static final int ALIGN_BAR = 5;
/*     */   public static final int LEAD_NONE = 0;
/*     */   public static final int LEAD_DOTS = 1;
/*     */   public static final int LEAD_HYPHENS = 2;
/*     */   public static final int LEAD_UNDERLINE = 3;
/*     */   public static final int LEAD_THICKLINE = 4;
/*     */   public static final int LEAD_EQUALS = 5;
/*     */   private int alignment;
/*     */   private float position;
/*     */   private int leader;
/*     */   
/*     */   public TabStop(float paramFloat) {
/*  86 */     this(paramFloat, 0, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TabStop(float paramFloat, int paramInt1, int paramInt2) {
/*  94 */     this.alignment = paramInt1;
/*  95 */     this.leader = paramInt2;
/*  96 */     this.position = paramFloat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPosition() {
/* 104 */     return this.position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAlignment() {
/* 112 */     return this.alignment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLeader() {
/* 120 */     return this.leader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 129 */     if (paramObject == this) {
/* 130 */       return true;
/*     */     }
/* 132 */     if (paramObject instanceof TabStop) {
/* 133 */       TabStop tabStop = (TabStop)paramObject;
/* 134 */       return (this.alignment == tabStop.alignment && this.leader == tabStop.leader && this.position == tabStop.position);
/*     */     } 
/*     */ 
/*     */     
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 148 */     return this.alignment ^ this.leader ^ Math.round(this.position);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 154 */     switch (this.alignment) {
/*     */       
/*     */       default:
/* 157 */         str = "";
/*     */         break;
/*     */       case 1:
/* 160 */         str = "right ";
/*     */         break;
/*     */       case 2:
/* 163 */         str = "center ";
/*     */         break;
/*     */       case 4:
/* 166 */         str = "decimal ";
/*     */         break;
/*     */       case 5:
/* 169 */         str = "bar ";
/*     */         break;
/*     */     } 
/* 172 */     String str = str + "tab @" + String.valueOf(this.position);
/* 173 */     if (this.leader != 0)
/* 174 */       str = str + " (w/leaders)"; 
/* 175 */     return str;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/TabStop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */