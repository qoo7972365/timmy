/*     */ package sun.net.idn;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.text.Normalizer;
/*     */ import java.text.ParseException;
/*     */ import sun.text.Normalizer;
/*     */ import sun.text.normalizer.CharTrie;
/*     */ import sun.text.normalizer.NormalizerImpl;
/*     */ import sun.text.normalizer.Trie;
/*     */ import sun.text.normalizer.UCharacter;
/*     */ import sun.text.normalizer.UCharacterIterator;
/*     */ import sun.text.normalizer.UTF16;
/*     */ import sun.text.normalizer.VersionInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StringPrep
/*     */ {
/*     */   public static final int DEFAULT = 0;
/*     */   public static final int ALLOW_UNASSIGNED = 1;
/*     */   private static final int UNASSIGNED = 0;
/*     */   private static final int MAP = 1;
/*     */   private static final int PROHIBITED = 2;
/*     */   private static final int DELETE = 3;
/*     */   private static final int TYPE_LIMIT = 4;
/*     */   private static final int NORMALIZATION_ON = 1;
/*     */   private static final int CHECK_BIDI_ON = 2;
/*     */   private static final int TYPE_THRESHOLD = 65520;
/*     */   private static final int MAX_INDEX_VALUE = 16319;
/*     */   private static final int MAX_INDEX_TOP_LENGTH = 3;
/*     */   private static final int INDEX_TRIE_SIZE = 0;
/*     */   private static final int INDEX_MAPPING_DATA_SIZE = 1;
/*     */   private static final int NORM_CORRECTNS_LAST_UNI_VERSION = 2;
/*     */   private static final int ONE_UCHAR_MAPPING_INDEX_START = 3;
/*     */   private static final int TWO_UCHARS_MAPPING_INDEX_START = 4;
/*     */   private static final int THREE_UCHARS_MAPPING_INDEX_START = 5;
/*     */   private static final int FOUR_UCHARS_MAPPING_INDEX_START = 6;
/*     */   private static final int OPTIONS = 7;
/*     */   private static final int INDEX_TOP = 16;
/*     */   private static final int DATA_BUFFER_SIZE = 25000;
/*     */   private StringPrepTrieImpl sprepTrieImpl;
/*     */   private int[] indexes;
/*     */   private char[] mappingData;
/*     */   private byte[] formatVersion;
/*     */   private VersionInfo sprepUniVer;
/*     */   private VersionInfo normCorrVer;
/*     */   private boolean doNFKC;
/*     */   private boolean checkBiDi;
/*     */   
/*     */   private static final class StringPrepTrieImpl
/*     */     implements Trie.DataManipulate
/*     */   {
/* 143 */     private CharTrie sprepTrie = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getFoldingOffset(int param1Int) {
/* 152 */       return param1Int;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private StringPrepTrieImpl() {}
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
/*     */   private char getCodePointValue(int paramInt) {
/* 177 */     return this.sprepTrieImpl.sprepTrie.getCodePointValue(paramInt);
/*     */   }
/*     */   
/*     */   private static VersionInfo getVersionInfo(int paramInt) {
/* 181 */     int i = paramInt & 0xFF;
/* 182 */     int j = paramInt >> 8 & 0xFF;
/* 183 */     int k = paramInt >> 16 & 0xFF;
/* 184 */     int m = paramInt >> 24 & 0xFF;
/* 185 */     return VersionInfo.getInstance(m, k, j, i);
/*     */   }
/*     */   private static VersionInfo getVersionInfo(byte[] paramArrayOfbyte) {
/* 188 */     if (paramArrayOfbyte.length != 4) {
/* 189 */       return null;
/*     */     }
/* 191 */     return VersionInfo.getInstance(paramArrayOfbyte[0], paramArrayOfbyte[1], paramArrayOfbyte[2], paramArrayOfbyte[3]);
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
/*     */   public StringPrep(InputStream paramInputStream) throws IOException {
/* 204 */     BufferedInputStream bufferedInputStream = new BufferedInputStream(paramInputStream, 25000);
/*     */     
/* 206 */     StringPrepDataReader stringPrepDataReader = new StringPrepDataReader(bufferedInputStream);
/*     */ 
/*     */     
/* 209 */     this.indexes = stringPrepDataReader.readIndexes(16);
/*     */     
/* 211 */     byte[] arrayOfByte = new byte[this.indexes[0]];
/*     */ 
/*     */ 
/*     */     
/* 215 */     this.mappingData = new char[this.indexes[1] / 2];
/*     */     
/* 217 */     stringPrepDataReader.read(arrayOfByte, this.mappingData);
/*     */     
/* 219 */     this.sprepTrieImpl = new StringPrepTrieImpl();
/* 220 */     this.sprepTrieImpl.sprepTrie = new CharTrie(new ByteArrayInputStream(arrayOfByte), this.sprepTrieImpl);
/*     */ 
/*     */     
/* 223 */     this.formatVersion = stringPrepDataReader.getDataFormatVersion();
/*     */ 
/*     */     
/* 226 */     this.doNFKC = ((this.indexes[7] & 0x1) > 0);
/* 227 */     this.checkBiDi = ((this.indexes[7] & 0x2) > 0);
/* 228 */     this.sprepUniVer = getVersionInfo(stringPrepDataReader.getUnicodeVersion());
/* 229 */     this.normCorrVer = getVersionInfo(this.indexes[2]);
/* 230 */     VersionInfo versionInfo = NormalizerImpl.getUnicodeVersion();
/* 231 */     if (versionInfo.compareTo(this.sprepUniVer) < 0 && versionInfo
/* 232 */       .compareTo(this.normCorrVer) < 0 && (this.indexes[7] & 0x1) > 0)
/*     */     {
/*     */       
/* 235 */       throw new IOException("Normalization Correction version not supported");
/*     */     }
/* 237 */     bufferedInputStream.close();
/*     */   }
/*     */   
/*     */   private static final class Values {
/*     */     boolean isIndex;
/*     */     int value;
/*     */     
/*     */     public void reset() {
/* 245 */       this.isIndex = false;
/* 246 */       this.value = 0;
/* 247 */       this.type = -1;
/*     */     }
/*     */     int type;
/*     */     private Values() {} }
/*     */   private static final void getValues(char paramChar, Values paramValues) {
/* 252 */     paramValues.reset();
/* 253 */     if (paramChar == '\000') {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 259 */       paramValues.type = 4;
/* 260 */     } else if (paramChar >= '￰') {
/* 261 */       paramValues.type = paramChar - 65520;
/*     */     } else {
/*     */       
/* 264 */       paramValues.type = 1;
/*     */       
/* 266 */       if ((paramChar & 0x2) > 0) {
/* 267 */         paramValues.isIndex = true;
/* 268 */         paramValues.value = paramChar >> 2;
/*     */       } else {
/*     */         
/* 271 */         paramValues.isIndex = false;
/* 272 */         paramValues.value = paramChar << 16 >> 16;
/* 273 */         paramValues.value >>= 2;
/*     */       } 
/*     */ 
/*     */       
/* 277 */       if (paramChar >> 2 == 16319) {
/* 278 */         paramValues.type = 3;
/* 279 */         paramValues.isIndex = false;
/* 280 */         paramValues.value = 0;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private StringBuffer map(UCharacterIterator paramUCharacterIterator, int paramInt) throws ParseException {
/* 290 */     Values values = new Values();
/* 291 */     char c = Character.MIN_VALUE;
/* 292 */     int i = -1;
/* 293 */     StringBuffer stringBuffer = new StringBuffer();
/* 294 */     boolean bool = ((paramInt & 0x1) > 0) ? true : false;
/*     */     
/* 296 */     while ((i = paramUCharacterIterator.nextCodePoint()) != -1) {
/*     */       
/* 298 */       c = getCodePointValue(i);
/* 299 */       getValues(c, values);
/*     */ 
/*     */       
/* 302 */       if (values.type == 0 && !bool)
/* 303 */         throw new ParseException("An unassigned code point was found in the input " + paramUCharacterIterator
/* 304 */             .getText(), paramUCharacterIterator.getIndex()); 
/* 305 */       if (values.type == 1) {
/*     */ 
/*     */         
/* 308 */         if (values.isIndex) {
/* 309 */           char c1; int j = values.value;
/* 310 */           if (j >= this.indexes[3] && j < this.indexes[4]) {
/*     */             
/* 312 */             c1 = '\001';
/* 313 */           } else if (j >= this.indexes[4] && j < this.indexes[5]) {
/*     */             
/* 315 */             c1 = '\002';
/* 316 */           } else if (j >= this.indexes[5] && j < this.indexes[6]) {
/*     */             
/* 318 */             c1 = '\003';
/*     */           } else {
/* 320 */             c1 = this.mappingData[j++];
/*     */           } 
/*     */           
/* 323 */           stringBuffer.append(this.mappingData, j, c1);
/*     */           
/*     */           continue;
/*     */         } 
/* 327 */         i -= values.value;
/*     */       }
/* 329 */       else if (values.type == 3) {
/*     */         continue;
/*     */       } 
/*     */ 
/*     */       
/* 334 */       UTF16.append(stringBuffer, i);
/*     */     } 
/*     */     
/* 337 */     return stringBuffer;
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
/*     */   private StringBuffer normalize(StringBuffer paramStringBuffer) {
/* 353 */     return new StringBuffer(
/* 354 */         Normalizer.normalize(paramStringBuffer
/* 355 */           .toString(), Normalizer.Form.NFKC, 262432));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuffer prepare(UCharacterIterator paramUCharacterIterator, int paramInt) throws ParseException {
/* 427 */     StringBuffer stringBuffer1 = map(paramUCharacterIterator, paramInt);
/* 428 */     StringBuffer stringBuffer2 = stringBuffer1;
/*     */     
/* 430 */     if (this.doNFKC)
/*     */     {
/* 432 */       stringBuffer2 = normalize(stringBuffer1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 437 */     UCharacterIterator uCharacterIterator = UCharacterIterator.getInstance(stringBuffer2);
/* 438 */     Values values = new Values();
/* 439 */     int j = 19;
/* 440 */     int k = 19;
/* 441 */     int m = -1, n = -1;
/* 442 */     boolean bool1 = false, bool2 = false;
/*     */     int i;
/* 444 */     while ((i = uCharacterIterator.nextCodePoint()) != -1) {
/* 445 */       char c = getCodePointValue(i);
/* 446 */       getValues(c, values);
/*     */       
/* 448 */       if (values.type == 2) {
/* 449 */         throw new ParseException("A prohibited code point was found in the input" + uCharacterIterator
/* 450 */             .getText(), values.value);
/*     */       }
/*     */       
/* 453 */       j = UCharacter.getDirection(i);
/* 454 */       if (k == 19) {
/* 455 */         k = j;
/*     */       }
/* 457 */       if (j == 0) {
/* 458 */         bool2 = true;
/* 459 */         n = uCharacterIterator.getIndex() - 1;
/*     */       } 
/* 461 */       if (j == 1 || j == 13) {
/* 462 */         bool1 = true;
/* 463 */         m = uCharacterIterator.getIndex() - 1;
/*     */       } 
/*     */     } 
/* 466 */     if (this.checkBiDi == true) {
/*     */       
/* 468 */       if (bool2 == true && bool1 == true) {
/* 469 */         throw new ParseException("The input does not conform to the rules for BiDi code points." + uCharacterIterator
/* 470 */             .getText(), (m > n) ? m : n);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 475 */       if (bool1 == true && ((k != 1 && k != 13) || (j != 1 && j != 13)))
/*     */       {
/*     */ 
/*     */         
/* 479 */         throw new ParseException("The input does not conform to the rules for BiDi code points." + uCharacterIterator
/* 480 */             .getText(), (m > n) ? m : n);
/*     */       }
/*     */     } 
/*     */     
/* 484 */     return stringBuffer2;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/idn/StringPrep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */