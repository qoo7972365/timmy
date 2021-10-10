/*     */ package sun.util.locale.provider;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.MissingResourceException;
/*     */ import sun.text.CompactByteArray;
/*     */ import sun.text.SupplementaryCharacterData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class BreakDictionary
/*     */ {
/*  71 */   private static int supportedVersion = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private CompactByteArray columnMap = null;
/*  78 */   private SupplementaryCharacterData supplementaryCharColumnMap = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int numCols;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int numColGroups;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   private short[] table = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   private short[] rowIndex = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   private int[] rowIndexFlags = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   private short[] rowIndexFlagsIndex = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   private byte[] rowIndexShifts = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   BreakDictionary(String paramString) throws IOException, MissingResourceException {
/* 141 */     readDictionaryFile(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readDictionaryFile(final String dictionaryName) throws IOException, MissingResourceException {
/*     */     BufferedInputStream bufferedInputStream;
/*     */     try {
/* 149 */       bufferedInputStream = AccessController.<BufferedInputStream>doPrivileged(new PrivilegedExceptionAction<BufferedInputStream>()
/*     */           {
/*     */             public BufferedInputStream run() throws Exception
/*     */             {
/* 153 */               return new BufferedInputStream(getClass().getResourceAsStream("/sun/text/resources/" + dictionaryName));
/*     */             }
/*     */           });
/*     */     
/*     */     }
/* 158 */     catch (PrivilegedActionException privilegedActionException) {
/* 159 */       throw new InternalError(privilegedActionException.toString(), privilegedActionException);
/*     */     } 
/*     */     
/* 162 */     byte[] arrayOfByte1 = new byte[8];
/* 163 */     if (bufferedInputStream.read(arrayOfByte1) != 8) {
/* 164 */       throw new MissingResourceException("Wrong data length", dictionaryName, "");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 169 */     int i = RuleBasedBreakIterator.getInt(arrayOfByte1, 0);
/* 170 */     if (i != supportedVersion) {
/* 171 */       throw new MissingResourceException("Dictionary version(" + i + ") is unsupported", dictionaryName, "");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 176 */     int j = RuleBasedBreakIterator.getInt(arrayOfByte1, 4);
/* 177 */     arrayOfByte1 = new byte[j];
/* 178 */     if (bufferedInputStream.read(arrayOfByte1) != j) {
/* 179 */       throw new MissingResourceException("Wrong data length", dictionaryName, "");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 184 */     bufferedInputStream.close();
/*     */ 
/*     */     
/* 187 */     byte b1 = 0;
/*     */ 
/*     */ 
/*     */     
/* 191 */     int k = RuleBasedBreakIterator.getInt(arrayOfByte1, b1);
/* 192 */     b1 += true;
/* 193 */     short[] arrayOfShort = new short[k];
/* 194 */     for (byte b2 = 0; b2 < k; b2++, b1 += true) {
/* 195 */       arrayOfShort[b2] = RuleBasedBreakIterator.getShort(arrayOfByte1, b1);
/*     */     }
/* 197 */     k = RuleBasedBreakIterator.getInt(arrayOfByte1, b1);
/* 198 */     b1 += true;
/* 199 */     byte[] arrayOfByte2 = new byte[k]; byte b3;
/* 200 */     for (b3 = 0; b3 < k; b3++, b1++) {
/* 201 */       arrayOfByte2[b3] = arrayOfByte1[b1];
/*     */     }
/* 203 */     this.columnMap = new CompactByteArray(arrayOfShort, arrayOfByte2);
/*     */ 
/*     */     
/* 206 */     this.numCols = RuleBasedBreakIterator.getInt(arrayOfByte1, b1);
/* 207 */     b1 += 4;
/* 208 */     this.numColGroups = RuleBasedBreakIterator.getInt(arrayOfByte1, b1);
/* 209 */     b1 += 4;
/*     */ 
/*     */     
/* 212 */     k = RuleBasedBreakIterator.getInt(arrayOfByte1, b1);
/* 213 */     b1 += 4;
/* 214 */     this.rowIndex = new short[k];
/* 215 */     for (b3 = 0; b3 < k; b3++, b1 += 2) {
/* 216 */       this.rowIndex[b3] = RuleBasedBreakIterator.getShort(arrayOfByte1, b1);
/*     */     }
/*     */ 
/*     */     
/* 220 */     k = RuleBasedBreakIterator.getInt(arrayOfByte1, b1);
/* 221 */     b1 += 4;
/* 222 */     this.rowIndexFlagsIndex = new short[k];
/* 223 */     for (b3 = 0; b3 < k; b3++, b1 += 2) {
/* 224 */       this.rowIndexFlagsIndex[b3] = RuleBasedBreakIterator.getShort(arrayOfByte1, b1);
/*     */     }
/* 226 */     k = RuleBasedBreakIterator.getInt(arrayOfByte1, b1);
/* 227 */     b1 += 4;
/* 228 */     this.rowIndexFlags = new int[k];
/* 229 */     for (b3 = 0; b3 < k; b3++, b1 += 4) {
/* 230 */       this.rowIndexFlags[b3] = RuleBasedBreakIterator.getInt(arrayOfByte1, b1);
/*     */     }
/*     */ 
/*     */     
/* 234 */     k = RuleBasedBreakIterator.getInt(arrayOfByte1, b1);
/* 235 */     b1 += 4;
/* 236 */     this.rowIndexShifts = new byte[k];
/* 237 */     for (b3 = 0; b3 < k; b3++, b1++) {
/* 238 */       this.rowIndexShifts[b3] = arrayOfByte1[b1];
/*     */     }
/*     */ 
/*     */     
/* 242 */     k = RuleBasedBreakIterator.getInt(arrayOfByte1, b1);
/* 243 */     b1 += 4;
/* 244 */     this.table = new short[k];
/* 245 */     for (b3 = 0; b3 < k; b3++, b1 += 2) {
/* 246 */       this.table[b3] = RuleBasedBreakIterator.getShort(arrayOfByte1, b1);
/*     */     }
/*     */ 
/*     */     
/* 250 */     k = RuleBasedBreakIterator.getInt(arrayOfByte1, b1);
/* 251 */     b1 += 4;
/* 252 */     int[] arrayOfInt = new int[k];
/* 253 */     for (byte b4 = 0; b4 < k; b4++, b1 += 4) {
/* 254 */       arrayOfInt[b4] = RuleBasedBreakIterator.getInt(arrayOfByte1, b1);
/*     */     }
/* 256 */     this.supplementaryCharColumnMap = new SupplementaryCharacterData(arrayOfInt);
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
/*     */   public final short getNextStateFromCharacter(int paramInt1, int paramInt2) {
/*     */     int i;
/* 272 */     if (paramInt2 < 65536) {
/* 273 */       i = this.columnMap.elementAt((char)paramInt2);
/*     */     } else {
/* 275 */       i = this.supplementaryCharColumnMap.getValue(paramInt2);
/*     */     } 
/* 277 */     return getNextState(paramInt1, i);
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
/*     */   public final short getNextState(int paramInt1, int paramInt2) {
/* 292 */     if (cellIsPopulated(paramInt1, paramInt2))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 299 */       return internalAt(this.rowIndex[paramInt1], paramInt2 + this.rowIndexShifts[paramInt1]);
/*     */     }
/*     */     
/* 302 */     return 0;
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
/*     */   private boolean cellIsPopulated(int paramInt1, int paramInt2) {
/* 314 */     if (this.rowIndexFlagsIndex[paramInt1] < 0) {
/* 315 */       return (paramInt2 == -this.rowIndexFlagsIndex[paramInt1]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 325 */     int i = this.rowIndexFlags[this.rowIndexFlagsIndex[paramInt1] + (paramInt2 >> 5)];
/* 326 */     return ((i & 1 << (paramInt2 & 0x1F)) != 0);
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
/*     */   private short internalAt(int paramInt1, int paramInt2) {
/* 341 */     return this.table[paramInt1 * this.numCols + paramInt2];
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/provider/BreakDictionary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */