/*     */ package javax.print.attribute.standard;
/*     */ 
/*     */ import javax.print.attribute.Attribute;
/*     */ import javax.print.attribute.SetOfIntegerSyntax;
/*     */ import javax.print.attribute.SupportedValuesAttribute;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NumberUpSupported
/*     */   extends SetOfIntegerSyntax
/*     */   implements SupportedValuesAttribute
/*     */ {
/*     */   private static final long serialVersionUID = -1041573395759141805L;
/*     */   
/*     */   public NumberUpSupported(int[][] paramArrayOfint) {
/*  70 */     super(paramArrayOfint);
/*  71 */     if (paramArrayOfint == null) {
/*  72 */       throw new NullPointerException("members is null");
/*     */     }
/*  74 */     int[][] arrayOfInt = getMembers();
/*  75 */     int i = arrayOfInt.length;
/*  76 */     if (i == 0) {
/*  77 */       throw new IllegalArgumentException("members is zero-length");
/*     */     }
/*     */     
/*  80 */     for (byte b = 0; b < i; b++) {
/*  81 */       if (arrayOfInt[b][0] < 1) {
/*  82 */         throw new IllegalArgumentException("Number up value must be > 0");
/*     */       }
/*     */     } 
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
/*     */   public NumberUpSupported(int paramInt) {
/*  99 */     super(paramInt);
/* 100 */     if (paramInt < 1) {
/* 101 */       throw new IllegalArgumentException("Number up value must be > 0");
/*     */     }
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
/*     */   public NumberUpSupported(int paramInt1, int paramInt2) {
/* 119 */     super(paramInt1, paramInt2);
/* 120 */     if (paramInt1 > paramInt2)
/* 121 */       throw new IllegalArgumentException("Null range specified"); 
/* 122 */     if (paramInt1 < 1) {
/* 123 */       throw new IllegalArgumentException("Number up value must be > 0");
/*     */     }
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
/*     */   public boolean equals(Object paramObject) {
/* 148 */     return (super.equals(paramObject) && paramObject instanceof NumberUpSupported);
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
/*     */   public final Class<? extends Attribute> getCategory() {
/* 163 */     return (Class)NumberUpSupported.class;
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
/*     */   public final String getName() {
/* 176 */     return "number-up-supported";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/attribute/standard/NumberUpSupported.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */