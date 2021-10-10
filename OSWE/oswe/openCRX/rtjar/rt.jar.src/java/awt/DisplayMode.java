/*     */ package java.awt;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DisplayMode
/*     */ {
/*     */   private Dimension size;
/*     */   private int bitDepth;
/*     */   private int refreshRate;
/*     */   public static final int BIT_DEPTH_MULTI = -1;
/*     */   public static final int REFRESH_RATE_UNKNOWN = 0;
/*     */   
/*     */   public DisplayMode(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  69 */     this.size = new Dimension(paramInt1, paramInt2);
/*  70 */     this.bitDepth = paramInt3;
/*  71 */     this.refreshRate = paramInt4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  79 */     return this.size.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  87 */     return this.size.width;
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
/*     */   public int getBitDepth() {
/* 106 */     return this.bitDepth;
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
/*     */   public int getRefreshRate() {
/* 123 */     return this.refreshRate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(DisplayMode paramDisplayMode) {
/* 131 */     if (paramDisplayMode == null) {
/* 132 */       return false;
/*     */     }
/* 134 */     return (getHeight() == paramDisplayMode.getHeight() && 
/* 135 */       getWidth() == paramDisplayMode.getWidth() && 
/* 136 */       getBitDepth() == paramDisplayMode.getBitDepth() && 
/* 137 */       getRefreshRate() == paramDisplayMode.getRefreshRate());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 144 */     if (paramObject instanceof DisplayMode) {
/* 145 */       return equals((DisplayMode)paramObject);
/*     */     }
/* 147 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 155 */     return getWidth() + getHeight() + getBitDepth() * 7 + 
/* 156 */       getRefreshRate() * 13;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/DisplayMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */