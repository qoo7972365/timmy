/*     */ package sun.text.normalizer;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class VersionInfo
/*     */ {
/*     */   private int m_version_;
/*     */   
/*     */   public static VersionInfo getInstance(String paramString) {
/*  66 */     int i = paramString.length();
/*  67 */     int[] arrayOfInt = { 0, 0, 0, 0 };
/*  68 */     byte b1 = 0;
/*  69 */     byte b2 = 0;
/*     */     
/*  71 */     while (b1 < 4 && b2 < i) {
/*  72 */       char c = paramString.charAt(b2);
/*  73 */       if (c == '.') {
/*  74 */         b1++;
/*     */       } else {
/*     */         
/*  77 */         c = (char)(c - 48);
/*  78 */         if (c < '\000' || c > '\t') {
/*  79 */           throw new IllegalArgumentException("Invalid version number: Version number may be negative or greater than 255");
/*     */         }
/*  81 */         arrayOfInt[b1] = arrayOfInt[b1] * 10;
/*  82 */         arrayOfInt[b1] = arrayOfInt[b1] + c;
/*     */       } 
/*  84 */       b2++;
/*     */     } 
/*  86 */     if (b2 != i) {
/*  87 */       throw new IllegalArgumentException("Invalid version number: String '" + paramString + "' exceeds version format");
/*     */     }
/*     */     
/*  90 */     for (byte b3 = 0; b3 < 4; b3++) {
/*  91 */       if (arrayOfInt[b3] < 0 || arrayOfInt[b3] > 255) {
/*  92 */         throw new IllegalArgumentException("Invalid version number: Version number may be negative or greater than 255");
/*     */       }
/*     */     } 
/*     */     
/*  96 */     return getInstance(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2], arrayOfInt[3]);
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
/*     */   public static VersionInfo getInstance(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 114 */     if (paramInt1 < 0 || paramInt1 > 255 || paramInt2 < 0 || paramInt2 > 255 || paramInt3 < 0 || paramInt3 > 255 || paramInt4 < 0 || paramInt4 > 255)
/*     */     {
/* 116 */       throw new IllegalArgumentException("Invalid version number: Version number may be negative or greater than 255");
/*     */     }
/* 118 */     int i = getInt(paramInt1, paramInt2, paramInt3, paramInt4);
/* 119 */     Integer integer = Integer.valueOf(i);
/* 120 */     Object object = MAP_.get(integer);
/* 121 */     if (object == null) {
/* 122 */       object = new VersionInfo(i);
/* 123 */       MAP_.put(integer, object);
/*     */     } 
/* 125 */     return (VersionInfo)object;
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
/*     */   public int compareTo(VersionInfo paramVersionInfo) {
/* 141 */     return this.m_version_ - paramVersionInfo.m_version_;
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
/* 156 */   private static final HashMap<Integer, Object> MAP_ = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String INVALID_VERSION_NUMBER_ = "Invalid version number: Version number may be negative or greater than 255";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private VersionInfo(int paramInt) {
/* 171 */     this.m_version_ = paramInt;
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
/*     */   private static int getInt(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 183 */     return paramInt1 << 24 | paramInt2 << 16 | paramInt3 << 8 | paramInt4;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/normalizer/VersionInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */