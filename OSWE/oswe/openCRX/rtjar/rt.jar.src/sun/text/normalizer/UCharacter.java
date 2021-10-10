/*     */ package sun.text.normalizer;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.MissingResourceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class UCharacter
/*     */ {
/*     */   public static final int MIN_VALUE = 0;
/*     */   public static final int MAX_VALUE = 1114111;
/*     */   public static final int SUPPLEMENTARY_MIN_VALUE = 65536;
/*     */   private static final UCharacterProperty PROPERTY_;
/*     */   private static final char[] PROPERTY_TRIE_INDEX_;
/*     */   private static final char[] PROPERTY_TRIE_DATA_;
/*     */   private static final int PROPERTY_INITIAL_VALUE_;
/*     */   private static final UBiDiProps gBdp;
/*     */   private static final int NUMERIC_TYPE_SHIFT_ = 5;
/*     */   private static final int NUMERIC_TYPE_MASK_ = 224;
/*     */   
/*     */   public static int digit(int paramInt1, int paramInt2) {
/* 235 */     int j, i = getProperty(paramInt1);
/*     */     
/* 237 */     if (getNumericType(i) == 1) {
/* 238 */       j = UCharacterProperty.getUnsignedValue(i);
/*     */     } else {
/* 240 */       j = getEuropeanDigit(paramInt1);
/*     */     } 
/* 242 */     return (0 <= j && j < paramInt2) ? j : -1;
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
/*     */   public static int getDirection(int paramInt) {
/* 257 */     return gBdp.getClass(paramInt);
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
/*     */   public static int getCodePoint(char paramChar1, char paramChar2) {
/* 271 */     if (UTF16.isLeadSurrogate(paramChar1) && UTF16.isTrailSurrogate(paramChar2)) {
/* 272 */       return UCharacterProperty.getRawSupplementary(paramChar1, paramChar2);
/*     */     }
/* 274 */     throw new IllegalArgumentException("Illegal surrogate characters");
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
/*     */   public static VersionInfo getAge(int paramInt) {
/* 291 */     if (paramInt < 0 || paramInt > 1114111) {
/* 292 */       throw new IllegalArgumentException("Codepoint out of bounds");
/*     */     }
/* 294 */     return PROPERTY_.getAge(paramInt);
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
/*     */   static {
/*     */     UBiDiProps uBiDiProps;
/*     */     try {
/* 317 */       PROPERTY_ = UCharacterProperty.getInstance();
/* 318 */       PROPERTY_TRIE_INDEX_ = PROPERTY_.m_trieIndex_;
/* 319 */       PROPERTY_TRIE_DATA_ = PROPERTY_.m_trieData_;
/* 320 */       PROPERTY_INITIAL_VALUE_ = PROPERTY_.m_trieInitialValue_;
/*     */     }
/* 322 */     catch (Exception exception) {
/*     */       
/* 324 */       throw new MissingResourceException(exception.getMessage(), "", "");
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 329 */       uBiDiProps = UBiDiProps.getSingleton();
/* 330 */     } catch (IOException iOException) {
/* 331 */       uBiDiProps = UBiDiProps.getDummy();
/*     */     } 
/* 333 */     gBdp = uBiDiProps;
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
/*     */   private static int getEuropeanDigit(int paramInt) {
/* 356 */     if ((paramInt > 122 && paramInt < 65313) || paramInt < 65 || (paramInt > 90 && paramInt < 97) || paramInt > 65370 || (paramInt > 65338 && paramInt < 65345))
/*     */     {
/*     */       
/* 359 */       return -1;
/*     */     }
/* 361 */     if (paramInt <= 122)
/*     */     {
/* 363 */       return paramInt + 10 - ((paramInt <= 90) ? 65 : 97);
/*     */     }
/*     */     
/* 366 */     if (paramInt <= 65338) {
/* 367 */       return paramInt + 10 - 65313;
/*     */     }
/*     */     
/* 370 */     return paramInt + 10 - 65345;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getNumericType(int paramInt) {
/* 380 */     return (paramInt & 0xE0) >> 5;
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
/*     */   private static final int getProperty(int paramInt) {
/* 397 */     if (paramInt < 55296 || (paramInt > 56319 && paramInt < 65536)) {
/*     */       
/*     */       try {
/*     */ 
/*     */         
/* 402 */         return PROPERTY_TRIE_DATA_[(PROPERTY_TRIE_INDEX_[paramInt >> 5] << 2) + (paramInt & 0x1F)];
/*     */       
/*     */       }
/* 405 */       catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 406 */         return PROPERTY_INITIAL_VALUE_;
/*     */       } 
/*     */     }
/* 409 */     if (paramInt <= 56319)
/*     */     {
/* 411 */       return PROPERTY_TRIE_DATA_[(PROPERTY_TRIE_INDEX_[320 + (paramInt >> 5)] << 2) + (paramInt & 0x1F)];
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 416 */     if (paramInt <= 1114111)
/*     */     {
/*     */ 
/*     */       
/* 420 */       return PROPERTY_.m_trie_.getSurrogateValue(
/* 421 */           UTF16.getLeadSurrogate(paramInt), (char)(paramInt & 0x3FF));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 428 */     return PROPERTY_INITIAL_VALUE_;
/*     */   }
/*     */   
/*     */   public static interface NumericType {
/*     */     public static final int DECIMAL = 1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/normalizer/UCharacter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */