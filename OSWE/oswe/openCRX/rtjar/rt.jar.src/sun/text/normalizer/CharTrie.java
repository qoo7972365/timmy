/*     */ package sun.text.normalizer;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CharTrie
/*     */   extends Trie
/*     */ {
/*     */   private char m_initialValue_;
/*     */   private char[] m_data_;
/*     */   private FriendAgent m_friendAgent_;
/*     */   
/*     */   public CharTrie(InputStream paramInputStream, Trie.DataManipulate paramDataManipulate) throws IOException {
/*  70 */     super(paramInputStream, paramDataManipulate);
/*     */     
/*  72 */     if (!isCharTrie()) {
/*  73 */       throw new IllegalArgumentException("Data given does not belong to a char trie.");
/*     */     }
/*     */     
/*  76 */     this.m_friendAgent_ = new FriendAgent();
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
/*     */   public CharTrie(int paramInt1, int paramInt2, Trie.DataManipulate paramDataManipulate) {
/*  94 */     super(new char[2080], 512, paramDataManipulate);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     char c2 = 'Ā', c1 = c2;
/* 103 */     if (paramInt2 != paramInt1) {
/* 104 */       c1 += ' ';
/*     */     }
/* 106 */     this.m_data_ = new char[c1];
/* 107 */     this.m_dataLength_ = c1;
/*     */     
/* 109 */     this.m_initialValue_ = (char)paramInt1;
/*     */ 
/*     */ 
/*     */     
/*     */     char c3;
/*     */ 
/*     */     
/* 116 */     for (c3 = Character.MIN_VALUE; c3 < c2; c3++) {
/* 117 */       this.m_data_[c3] = (char)paramInt1;
/*     */     }
/*     */     
/* 120 */     if (paramInt2 != paramInt1) {
/*     */       
/* 122 */       char c = (char)(c2 >> 2);
/* 123 */       c3 = 'ۀ';
/* 124 */       int i = 1760;
/* 125 */       for (; c3 < i; c3++) {
/* 126 */         this.m_index_[c3] = c;
/*     */       }
/*     */ 
/*     */       
/* 130 */       i = c2 + 32;
/* 131 */       for (c3 = c2; c3 < i; c3++) {
/* 132 */         this.m_data_[c3] = (char)paramInt2;
/*     */       }
/*     */     } 
/*     */     
/* 136 */     this.m_friendAgent_ = new FriendAgent();
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
/*     */   public class FriendAgent
/*     */   {
/*     */     public char[] getPrivateIndex() {
/* 150 */       return CharTrie.this.m_index_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public char[] getPrivateData() {
/* 158 */       return CharTrie.this.m_data_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getPrivateInitialValue() {
/* 166 */       return CharTrie.this.m_initialValue_;
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
/*     */   public void putIndexData(UCharacterProperty paramUCharacterProperty) {
/* 179 */     paramUCharacterProperty.setIndexData(this.m_friendAgent_);
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
/*     */   public final char getCodePointValue(int paramInt) {
/* 195 */     if (0 <= paramInt && paramInt < 55296) {
/*     */       
/* 197 */       int j = (this.m_index_[paramInt >> 5] << 2) + (paramInt & 0x1F);
/*     */       
/* 199 */       return this.m_data_[j];
/*     */     } 
/*     */ 
/*     */     
/* 203 */     int i = getCodePointOffset(paramInt);
/*     */ 
/*     */ 
/*     */     
/* 207 */     return (i >= 0) ? this.m_data_[i] : this.m_initialValue_;
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
/*     */   public final char getLeadValue(char paramChar) {
/* 222 */     return this.m_data_[getLeadOffset(paramChar)];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final char getSurrogateValue(char paramChar1, char paramChar2) {
/* 233 */     int i = getSurrogateOffset(paramChar1, paramChar2);
/* 234 */     if (i > 0) {
/* 235 */       return this.m_data_[i];
/*     */     }
/* 237 */     return this.m_initialValue_;
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
/*     */   public final char getTrailValue(int paramInt, char paramChar) {
/* 252 */     if (this.m_dataManipulate_ == null) {
/* 253 */       throw new NullPointerException("The field DataManipulate in this Trie is null");
/*     */     }
/*     */     
/* 256 */     int i = this.m_dataManipulate_.getFoldingOffset(paramInt);
/* 257 */     if (i > 0) {
/* 258 */       return this.m_data_[getRawOffset(i, (char)(paramChar & 0x3FF))];
/*     */     }
/*     */     
/* 261 */     return this.m_initialValue_;
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
/*     */   protected final void unserialize(InputStream paramInputStream) throws IOException {
/* 275 */     DataInputStream dataInputStream = new DataInputStream(paramInputStream);
/* 276 */     int i = this.m_dataOffset_ + this.m_dataLength_;
/* 277 */     this.m_index_ = new char[i];
/* 278 */     for (byte b = 0; b < i; b++) {
/* 279 */       this.m_index_[b] = dataInputStream.readChar();
/*     */     }
/* 281 */     this.m_data_ = this.m_index_;
/* 282 */     this.m_initialValue_ = this.m_data_[this.m_dataOffset_];
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
/*     */   protected final int getSurrogateOffset(char paramChar1, char paramChar2) {
/* 294 */     if (this.m_dataManipulate_ == null) {
/* 295 */       throw new NullPointerException("The field DataManipulate in this Trie is null");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 300 */     int i = this.m_dataManipulate_.getFoldingOffset(getLeadValue(paramChar1));
/*     */ 
/*     */     
/* 303 */     if (i > 0) {
/* 304 */       return getRawOffset(i, (char)(paramChar2 & 0x3FF));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 309 */     return -1;
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
/*     */   protected final int getValue(int paramInt) {
/* 322 */     return this.m_data_[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getInitialValue() {
/* 332 */     return this.m_initialValue_;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/normalizer/CharTrie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */