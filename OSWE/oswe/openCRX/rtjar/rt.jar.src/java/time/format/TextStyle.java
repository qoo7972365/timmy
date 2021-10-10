/*     */ package java.time.format;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum TextStyle
/*     */ {
/*  94 */   FULL(2, 0),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   FULL_STANDALONE(32770, 0),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   SHORT(1, 1),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   SHORT_STANDALONE(32769, 1),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   NARROW(4, 1),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   NARROW_STANDALONE(32772, 1);
/*     */   
/*     */   private final int calendarStyle;
/*     */   private final int zoneNameStyleIndex;
/*     */   
/*     */   TextStyle(int paramInt1, int paramInt2) {
/* 125 */     this.calendarStyle = paramInt1;
/* 126 */     this.zoneNameStyleIndex = paramInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStandalone() {
/* 134 */     return ((ordinal() & 0x1) == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextStyle asStandalone() {
/* 142 */     return values()[ordinal() | 0x1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextStyle asNormal() {
/* 151 */     return values()[ordinal() & 0xFFFFFFFE];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int toCalendarStyle() {
/* 160 */     return this.calendarStyle;
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
/*     */   int zoneNameStyleIndex() {
/* 173 */     return this.zoneNameStyleIndex;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/format/TextStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */