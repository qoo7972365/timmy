/*     */ package sun.text.normalizer;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public final class UCharacterProperty
/*     */ {
/*     */   public CharTrie m_trie_;
/*     */   public char[] m_trieIndex_;
/*     */   public char[] m_trieData_;
/*     */   public int m_trieInitialValue_;
/*     */   public VersionInfo m_unicodeVersion_;
/*     */   public static final int SRC_PROPSVEC = 2;
/*     */   public static final int SRC_COUNT = 9;
/*     */   CharTrie m_additionalTrie_;
/*     */   int[] m_additionalVectors_;
/*     */   int m_additionalColumnsCount_;
/*     */   int m_maxBlockScriptValue_;
/*     */   int m_maxJTGValue_;
/*     */   
/*     */   public void setIndexData(CharTrie.FriendAgent paramFriendAgent) {
/* 101 */     this.m_trieIndex_ = paramFriendAgent.getPrivateIndex();
/* 102 */     this.m_trieData_ = paramFriendAgent.getPrivateData();
/* 103 */     this.m_trieInitialValue_ = paramFriendAgent.getPrivateInitialValue();
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
/*     */   public final int getProperty(int paramInt) {
/* 116 */     if (paramInt < 55296 || (paramInt > 56319 && paramInt < 65536)) {
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */         
/* 122 */         return this.m_trieData_[(this.m_trieIndex_[paramInt >> 5] << 2) + (paramInt & 0x1F)];
/*     */ 
/*     */       
/*     */       }
/* 126 */       catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 127 */         return this.m_trieInitialValue_;
/*     */       } 
/*     */     }
/* 130 */     if (paramInt <= 56319)
/*     */     {
/* 132 */       return this.m_trieData_[(this.m_trieIndex_[320 + (paramInt >> 5)] << 2) + (paramInt & 0x1F)];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     if (paramInt <= 1114111)
/*     */     {
/*     */ 
/*     */       
/* 142 */       return this.m_trie_.getSurrogateValue(
/* 143 */           UTF16.getLeadSurrogate(paramInt), (char)(paramInt & 0x3FF));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     return this.m_trieInitialValue_;
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
/*     */   public static int getUnsignedValue(int paramInt) {
/* 164 */     return paramInt >> 8 & 0xFF;
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
/*     */   public int getAdditional(int paramInt1, int paramInt2) {
/* 176 */     if (paramInt2 == -1) {
/* 177 */       return getProperty(paramInt1);
/*     */     }
/* 179 */     if (paramInt2 < 0 || paramInt2 >= this.m_additionalColumnsCount_) {
/* 180 */       return 0;
/*     */     }
/* 182 */     return this.m_additionalVectors_[this.m_additionalTrie_
/* 183 */         .getCodePointValue(paramInt1) + paramInt2];
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
/*     */   public VersionInfo getAge(int paramInt) {
/* 200 */     int i = getAdditional(paramInt, 0) >> 24;
/* 201 */     return VersionInfo.getInstance(i >> 4 & 0xF, i & 0xF, 0, 0);
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
/*     */   public static int getRawSupplementary(char paramChar1, char paramChar2) {
/* 216 */     return (paramChar1 << 10) + paramChar2 + -56613888;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UCharacterProperty getInstance() {
/* 225 */     if (INSTANCE_ == null) {
/*     */       try {
/* 227 */         INSTANCE_ = new UCharacterProperty();
/*     */       }
/* 229 */       catch (Exception exception) {
/* 230 */         throw new MissingResourceException(exception.getMessage(), "", "");
/*     */       } 
/*     */     }
/* 233 */     return INSTANCE_;
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
/*     */   public static boolean isRuleWhiteSpace(int paramInt) {
/* 253 */     return (paramInt >= 9 && paramInt <= 8233 && (paramInt <= 13 || paramInt == 32 || paramInt == 133 || paramInt == 8206 || paramInt == 8207 || paramInt >= 8232));
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
/* 289 */   private static UCharacterProperty INSTANCE_ = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String DATA_FILE_NAME_ = "/sun/text/resources/uprops.icu";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int DATA_BUFFER_SIZE_ = 25000;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int VALUE_SHIFT_ = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int UNSIGNED_VALUE_MASK_AFTER_SHIFT_ = 255;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int LEAD_SURROGATE_SHIFT_ = 10;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int SURROGATE_OFFSET_ = -56613888;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int FIRST_NIBBLE_SHIFT_ = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int LAST_NIBBLE_MASK_ = 15;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int AGE_SHIFT_ = 24;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private UCharacterProperty() throws IOException {
/* 348 */     InputStream inputStream = ICUData.getRequiredStream("/sun/text/resources/uprops.icu");
/* 349 */     BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 25000);
/* 350 */     UCharacterPropertyReader uCharacterPropertyReader = new UCharacterPropertyReader(bufferedInputStream);
/* 351 */     uCharacterPropertyReader.read(this);
/* 352 */     bufferedInputStream.close();
/*     */     
/* 354 */     this.m_trie_.putIndexData(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void upropsvec_addPropertyStarts(UnicodeSet paramUnicodeSet) {
/* 359 */     if (this.m_additionalColumnsCount_ > 0) {
/*     */       
/* 361 */       TrieIterator trieIterator = new TrieIterator(this.m_additionalTrie_);
/* 362 */       RangeValueIterator.Element element = new RangeValueIterator.Element();
/* 363 */       while (trieIterator.next(element))
/* 364 */         paramUnicodeSet.add(element.start); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/normalizer/UCharacterProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */