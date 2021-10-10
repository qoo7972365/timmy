/*     */ package sun.java2d.jules;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TrapezoidList
/*     */ {
/*     */   public static final int TRAP_START_INDEX = 5;
/*     */   public static final int TRAP_SIZE = 10;
/*     */   int[] trapArray;
/*     */   
/*     */   public TrapezoidList(int[] paramArrayOfint) {
/*  35 */     this.trapArray = paramArrayOfint;
/*     */   }
/*     */   
/*     */   public final int[] getTrapArray() {
/*  39 */     return this.trapArray;
/*     */   }
/*     */   
/*     */   public final int getSize() {
/*  43 */     return this.trapArray[0];
/*     */   }
/*     */   
/*     */   public final void setSize(int paramInt) {
/*  47 */     this.trapArray[0] = 0;
/*     */   }
/*     */   
/*     */   public final int getLeft() {
/*  51 */     return this.trapArray[1];
/*     */   }
/*     */   
/*     */   public final int getTop() {
/*  55 */     return this.trapArray[2];
/*     */   }
/*     */   
/*     */   public final int getRight() {
/*  59 */     return this.trapArray[3];
/*     */   }
/*     */   
/*     */   public final int getBottom() {
/*  63 */     return this.trapArray[4];
/*     */   }
/*     */ 
/*     */   
/*     */   private final int getTrapStartAddresse(int paramInt) {
/*  68 */     return 5 + 10 * paramInt;
/*     */   }
/*     */   
/*     */   public final int getTop(int paramInt) {
/*  72 */     return this.trapArray[getTrapStartAddresse(paramInt) + 0];
/*     */   }
/*     */   
/*     */   public final int getBottom(int paramInt) {
/*  76 */     return this.trapArray[getTrapStartAddresse(paramInt) + 1];
/*     */   }
/*     */   
/*     */   public final int getP1XLeft(int paramInt) {
/*  80 */     return this.trapArray[getTrapStartAddresse(paramInt) + 2];
/*     */   }
/*     */   
/*     */   public final int getP1YLeft(int paramInt) {
/*  84 */     return this.trapArray[getTrapStartAddresse(paramInt) + 3];
/*     */   }
/*     */   
/*     */   public final int getP2XLeft(int paramInt) {
/*  88 */     return this.trapArray[getTrapStartAddresse(paramInt) + 4];
/*     */   }
/*     */   
/*     */   public final int getP2YLeft(int paramInt) {
/*  92 */     return this.trapArray[getTrapStartAddresse(paramInt) + 5];
/*     */   }
/*     */   
/*     */   public final int getP1XRight(int paramInt) {
/*  96 */     return this.trapArray[getTrapStartAddresse(paramInt) + 6];
/*     */   }
/*     */   
/*     */   public final int getP1YRight(int paramInt) {
/* 100 */     return this.trapArray[getTrapStartAddresse(paramInt) + 7];
/*     */   }
/*     */   
/*     */   public final int getP2XRight(int paramInt) {
/* 104 */     return this.trapArray[getTrapStartAddresse(paramInt) + 8];
/*     */   }
/*     */   
/*     */   public final int getP2YRight(int paramInt) {
/* 108 */     return this.trapArray[getTrapStartAddresse(paramInt) + 9];
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/jules/TrapezoidList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */