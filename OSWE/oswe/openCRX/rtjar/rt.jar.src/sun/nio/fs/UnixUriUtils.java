/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class UnixUriUtils
/*     */ {
/*     */   static Path fromUri(UnixFileSystem paramUnixFileSystem, URI paramURI) {
/*  45 */     if (!paramURI.isAbsolute())
/*  46 */       throw new IllegalArgumentException("URI is not absolute"); 
/*  47 */     if (paramURI.isOpaque())
/*  48 */       throw new IllegalArgumentException("URI is not hierarchical"); 
/*  49 */     String str1 = paramURI.getScheme();
/*  50 */     if (str1 == null || !str1.equalsIgnoreCase("file"))
/*  51 */       throw new IllegalArgumentException("URI scheme is not \"file\""); 
/*  52 */     if (paramURI.getAuthority() != null)
/*  53 */       throw new IllegalArgumentException("URI has an authority component"); 
/*  54 */     if (paramURI.getFragment() != null)
/*  55 */       throw new IllegalArgumentException("URI has a fragment component"); 
/*  56 */     if (paramURI.getQuery() != null) {
/*  57 */       throw new IllegalArgumentException("URI has a query component");
/*     */     }
/*     */     
/*  60 */     if (!paramURI.toString().startsWith("file:///")) {
/*  61 */       return (new File(paramURI)).toPath();
/*     */     }
/*     */     
/*  64 */     String str2 = paramURI.getRawPath();
/*  65 */     int i = str2.length();
/*  66 */     if (i == 0) {
/*  67 */       throw new IllegalArgumentException("URI path component is empty");
/*     */     }
/*     */     
/*  70 */     if (str2.endsWith("/") && i > 1)
/*  71 */       i--; 
/*  72 */     byte[] arrayOfByte = new byte[i];
/*  73 */     byte b1 = 0;
/*  74 */     byte b2 = 0;
/*  75 */     while (b2 < i) {
/*  76 */       byte b; char c = str2.charAt(b2++);
/*     */       
/*  78 */       if (c == '%') {
/*  79 */         assert b2 + 2 <= i;
/*  80 */         char c1 = str2.charAt(b2++);
/*  81 */         char c2 = str2.charAt(b2++);
/*  82 */         b = (byte)(decode(c1) << 4 | decode(c2));
/*  83 */         if (b == 0)
/*  84 */           throw new IllegalArgumentException("Nul character not allowed"); 
/*     */       } else {
/*  86 */         assert c < '';
/*  87 */         b = (byte)c;
/*     */       } 
/*  89 */       arrayOfByte[b1++] = b;
/*     */     } 
/*  91 */     if (b1 != arrayOfByte.length) {
/*  92 */       arrayOfByte = Arrays.copyOf(arrayOfByte, b1);
/*     */     }
/*  94 */     return new UnixPath(paramUnixFileSystem, arrayOfByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static URI toUri(UnixPath paramUnixPath) {
/* 101 */     byte[] arrayOfByte = paramUnixPath.toAbsolutePath().asByteArray();
/* 102 */     StringBuilder stringBuilder = new StringBuilder("file:///");
/* 103 */     assert arrayOfByte[0] == 47;
/* 104 */     for (byte b = 1; b < arrayOfByte.length; b++) {
/* 105 */       char c = (char)(arrayOfByte[b] & 0xFF);
/* 106 */       if (match(c, L_PATH, H_PATH)) {
/* 107 */         stringBuilder.append(c);
/*     */       } else {
/* 109 */         stringBuilder.append('%');
/* 110 */         stringBuilder.append(hexDigits[c >> 4 & 0xF]);
/* 111 */         stringBuilder.append(hexDigits[c & 0xF]);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 116 */     if (stringBuilder.charAt(stringBuilder.length() - 1) != '/') {
/*     */       try {
/* 118 */         if (UnixFileAttributes.get(paramUnixPath, true).isDirectory())
/* 119 */           stringBuilder.append('/'); 
/* 120 */       } catch (UnixException unixException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 126 */       return new URI(stringBuilder.toString());
/* 127 */     } catch (URISyntaxException uRISyntaxException) {
/* 128 */       throw new AssertionError(uRISyntaxException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long lowMask(String paramString) {
/* 136 */     int i = paramString.length();
/* 137 */     long l = 0L;
/* 138 */     for (byte b = 0; b < i; b++) {
/* 139 */       char c = paramString.charAt(b);
/* 140 */       if (c < '@')
/* 141 */         l |= 1L << c; 
/*     */     } 
/* 143 */     return l;
/*     */   }
/*     */ 
/*     */   
/*     */   private static long highMask(String paramString) {
/* 148 */     int i = paramString.length();
/* 149 */     long l = 0L;
/* 150 */     for (byte b = 0; b < i; b++) {
/* 151 */       char c = paramString.charAt(b);
/* 152 */       if (c >= '@' && c < '')
/* 153 */         l |= 1L << c - 64; 
/*     */     } 
/* 155 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static long lowMask(char paramChar1, char paramChar2) {
/* 161 */     long l = 0L;
/* 162 */     int i = Math.max(Math.min(paramChar1, 63), 0);
/* 163 */     int j = Math.max(Math.min(paramChar2, 63), 0);
/* 164 */     for (int k = i; k <= j; k++)
/* 165 */       l |= 1L << k; 
/* 166 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static long highMask(char paramChar1, char paramChar2) {
/* 172 */     long l = 0L;
/* 173 */     int i = Math.max(Math.min(paramChar1, 127), 64) - 64;
/* 174 */     int j = Math.max(Math.min(paramChar2, 127), 64) - 64;
/* 175 */     for (int k = i; k <= j; k++)
/* 176 */       l |= 1L << k; 
/* 177 */     return l;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean match(char paramChar, long paramLong1, long paramLong2) {
/* 182 */     if (paramChar < '@')
/* 183 */       return ((1L << paramChar & paramLong1) != 0L); 
/* 184 */     if (paramChar < '')
/* 185 */       return ((1L << paramChar - 64 & paramLong2) != 0L); 
/* 186 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int decode(char paramChar) {
/* 191 */     if (paramChar >= '0' && paramChar <= '9')
/* 192 */       return paramChar - 48; 
/* 193 */     if (paramChar >= 'a' && paramChar <= 'f')
/* 194 */       return paramChar - 97 + 10; 
/* 195 */     if (paramChar >= 'A' && paramChar <= 'F')
/* 196 */       return paramChar - 65 + 10; 
/* 197 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 202 */   private static final long L_DIGIT = lowMask('0', '9');
/*     */ 
/*     */   
/*     */   private static final long H_DIGIT = 0L;
/*     */   
/*     */   private static final long L_UPALPHA = 0L;
/*     */   
/* 209 */   private static final long H_UPALPHA = highMask('A', 'Z');
/*     */ 
/*     */   
/*     */   private static final long L_LOWALPHA = 0L;
/*     */ 
/*     */   
/* 215 */   private static final long H_LOWALPHA = highMask('a', 'z');
/*     */   
/*     */   private static final long L_ALPHA = 0L;
/*     */   
/* 219 */   private static final long H_ALPHA = H_LOWALPHA | H_UPALPHA;
/*     */ 
/*     */   
/* 222 */   private static final long L_ALPHANUM = L_DIGIT | 0x0L;
/* 223 */   private static final long H_ALPHANUM = 0x0L | H_ALPHA;
/*     */ 
/*     */ 
/*     */   
/* 227 */   private static final long L_MARK = lowMask("-_.!~*'()");
/* 228 */   private static final long H_MARK = highMask("-_.!~*'()");
/*     */ 
/*     */   
/* 231 */   private static final long L_UNRESERVED = L_ALPHANUM | L_MARK;
/* 232 */   private static final long H_UNRESERVED = H_ALPHANUM | H_MARK;
/*     */ 
/*     */ 
/*     */   
/* 236 */   private static final long L_PCHAR = L_UNRESERVED | 
/* 237 */     lowMask(":@&=+$,");
/* 238 */   private static final long H_PCHAR = H_UNRESERVED | 
/* 239 */     highMask(":@&=+$,");
/*     */ 
/*     */   
/* 242 */   private static final long L_PATH = L_PCHAR | lowMask(";/");
/* 243 */   private static final long H_PATH = H_PCHAR | highMask(";/");
/*     */   
/* 245 */   private static final char[] hexDigits = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/UnixUriUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */