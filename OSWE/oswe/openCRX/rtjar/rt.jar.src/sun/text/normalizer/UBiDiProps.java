/*     */ package sun.text.normalizer;
/*     */ 
/*     */ import java.io.BufferedInputStream;
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
/*     */ public final class UBiDiProps
/*     */ {
/*     */   public UBiDiProps() throws IOException {
/*  59 */     InputStream inputStream = ICUData.getStream("/sun/text/resources/ubidi.icu");
/*  60 */     BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 4096);
/*  61 */     readData(bufferedInputStream);
/*  62 */     bufferedInputStream.close();
/*  63 */     inputStream.close();
/*     */   }
/*     */ 
/*     */   
/*     */   private void readData(InputStream paramInputStream) throws IOException {
/*  68 */     DataInputStream dataInputStream = new DataInputStream(paramInputStream);
/*     */ 
/*     */     
/*  71 */     ICUBinary.readHeader(dataInputStream, FMT, new IsAcceptable());
/*     */ 
/*     */ 
/*     */     
/*  75 */     int i = dataInputStream.readInt();
/*  76 */     if (i < 0) {
/*  77 */       throw new IOException("indexes[0] too small in /sun/text/resources/ubidi.icu");
/*     */     }
/*  79 */     this.indexes = new int[i];
/*     */     
/*  81 */     this.indexes[0] = i; byte b;
/*  82 */     for (b = 1; b < i; b++) {
/*  83 */       this.indexes[b] = dataInputStream.readInt();
/*     */     }
/*     */ 
/*     */     
/*  87 */     this.trie = new CharTrie(dataInputStream, null);
/*     */ 
/*     */     
/*  90 */     i = this.indexes[3];
/*  91 */     if (i > 0) {
/*  92 */       this.mirrors = new int[i];
/*  93 */       for (b = 0; b < i; b++) {
/*  94 */         this.mirrors[b] = dataInputStream.readInt();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  99 */     i = this.indexes[5] - this.indexes[4];
/* 100 */     this.jgArray = new byte[i];
/* 101 */     for (b = 0; b < i; b++)
/* 102 */       this.jgArray[b] = dataInputStream.readByte(); 
/*     */   }
/*     */   
/*     */   private final class IsAcceptable implements ICUBinary.Authenticate {
/*     */     private IsAcceptable() {}
/*     */     
/*     */     public boolean isDataVersionAcceptable(byte[] param1ArrayOfbyte) {
/* 109 */       return (param1ArrayOfbyte[0] == 1 && param1ArrayOfbyte[2] == 5 && param1ArrayOfbyte[3] == 2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 115 */   private static UBiDiProps gBdp = null;
/*     */ 
/*     */   
/*     */   public static final synchronized UBiDiProps getSingleton() throws IOException {
/* 119 */     if (gBdp == null) {
/* 120 */       gBdp = new UBiDiProps();
/*     */     }
/* 122 */     return gBdp;
/*     */   }
/*     */ 
/*     */   
/* 126 */   private static UBiDiProps gBdpDummy = null; private int[] indexes;
/*     */   
/*     */   private UBiDiProps(boolean paramBoolean) {
/* 129 */     this.indexes = new int[16];
/* 130 */     this.indexes[0] = 16;
/* 131 */     this.trie = new CharTrie(0, 0, null);
/*     */   }
/*     */ 
/*     */   
/*     */   private int[] mirrors;
/*     */   private byte[] jgArray;
/*     */   private CharTrie trie;
/*     */   private static final String DATA_FILE_NAME = "/sun/text/resources/ubidi.icu";
/*     */   
/*     */   public static final synchronized UBiDiProps getDummy() {
/* 141 */     if (gBdpDummy == null) {
/* 142 */       gBdpDummy = new UBiDiProps(true);
/*     */     }
/* 144 */     return gBdpDummy;
/*     */   }
/*     */   
/*     */   public final int getClass(int paramInt) {
/* 148 */     return getClassFromProps(this.trie.getCodePointValue(paramInt));
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
/* 162 */   private static final byte[] FMT = new byte[] { 66, 105, 68, 105 };
/*     */   
/*     */   private static final int IX_INDEX_TOP = 0;
/*     */   
/*     */   private static final int IX_MIRROR_LENGTH = 3;
/*     */   
/*     */   private static final int IX_JG_START = 4;
/*     */   
/*     */   private static final int IX_JG_LIMIT = 5;
/*     */   
/*     */   private static final int IX_TOP = 16;
/*     */   private static final int CLASS_MASK = 31;
/*     */   
/*     */   private static final int getClassFromProps(int paramInt) {
/* 176 */     return paramInt & 0x1F;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/normalizer/UBiDiProps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */