/*     */ package java.net;
/*     */ 
/*     */ import java.io.CharArrayWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.IllegalCharsetNameException;
/*     */ import java.nio.charset.UnsupportedCharsetException;
/*     */ import java.security.AccessController;
/*     */ import java.util.BitSet;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class URLEncoder
/*     */ {
/*     */   static BitSet dontNeedEncoding;
/*     */   static final int caseDiff = 32;
/*  85 */   static String dfltEncName = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 125 */     dontNeedEncoding = new BitSet(256);
/*     */     byte b;
/* 127 */     for (b = 97; b <= 122; b++) {
/* 128 */       dontNeedEncoding.set(b);
/*     */     }
/* 130 */     for (b = 65; b <= 90; b++) {
/* 131 */       dontNeedEncoding.set(b);
/*     */     }
/* 133 */     for (b = 48; b <= 57; b++) {
/* 134 */       dontNeedEncoding.set(b);
/*     */     }
/* 136 */     dontNeedEncoding.set(32);
/*     */     
/* 138 */     dontNeedEncoding.set(45);
/* 139 */     dontNeedEncoding.set(95);
/* 140 */     dontNeedEncoding.set(46);
/* 141 */     dontNeedEncoding.set(42);
/*     */     
/* 143 */     dfltEncName = AccessController.<String>doPrivileged(new GetPropertyAction("file.encoding"));
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
/*     */   @Deprecated
/*     */   public static String encode(String paramString) {
/* 167 */     String str = null;
/*     */     
/*     */     try {
/* 170 */       str = encode(paramString, dfltEncName);
/* 171 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*     */ 
/*     */ 
/*     */     
/* 175 */     return str;
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
/*     */   public static String encode(String paramString1, String paramString2) throws UnsupportedEncodingException {
/*     */     Charset charset;
/* 203 */     boolean bool = false;
/* 204 */     StringBuffer stringBuffer = new StringBuffer(paramString1.length());
/*     */     
/* 206 */     CharArrayWriter charArrayWriter = new CharArrayWriter();
/*     */     
/* 208 */     if (paramString2 == null) {
/* 209 */       throw new NullPointerException("charsetName");
/*     */     }
/*     */     try {
/* 212 */       charset = Charset.forName(paramString2);
/* 213 */     } catch (IllegalCharsetNameException illegalCharsetNameException) {
/* 214 */       throw new UnsupportedEncodingException(paramString2);
/* 215 */     } catch (UnsupportedCharsetException unsupportedCharsetException) {
/* 216 */       throw new UnsupportedEncodingException(paramString2);
/*     */     } 
/*     */     
/* 219 */     for (byte b = 0; b < paramString1.length(); ) {
/* 220 */       char c = paramString1.charAt(b);
/*     */       
/* 222 */       if (dontNeedEncoding.get(c)) {
/* 223 */         if (c == ' ') {
/* 224 */           c = '+';
/* 225 */           bool = true;
/*     */         } 
/*     */         
/* 228 */         stringBuffer.append((char)c);
/* 229 */         b++;
/*     */         continue;
/*     */       } 
/*     */       do {
/* 233 */         charArrayWriter.write(c);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 242 */         if (c < '?' || c > '?') {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/* 247 */         if (b + 1 >= paramString1.length())
/* 248 */           continue;  char c1 = paramString1.charAt(b + 1);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 253 */         if (c1 < '?' || c1 > '?') {
/*     */           continue;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 259 */         charArrayWriter.write(c1);
/* 260 */         b++;
/*     */ 
/*     */ 
/*     */         
/* 264 */         ++b;
/* 265 */       } while (b < paramString1.length() && !dontNeedEncoding.get(c = paramString1.charAt(b)));
/*     */       
/* 267 */       charArrayWriter.flush();
/* 268 */       String str = new String(charArrayWriter.toCharArray());
/* 269 */       byte[] arrayOfByte = str.getBytes(charset);
/* 270 */       for (byte b1 = 0; b1 < arrayOfByte.length; b1++) {
/* 271 */         stringBuffer.append('%');
/* 272 */         char c1 = Character.forDigit(arrayOfByte[b1] >> 4 & 0xF, 16);
/*     */ 
/*     */         
/* 275 */         if (Character.isLetter(c1)) {
/* 276 */           c1 = (char)(c1 - 32);
/*     */         }
/* 278 */         stringBuffer.append(c1);
/* 279 */         c1 = Character.forDigit(arrayOfByte[b1] & 0xF, 16);
/* 280 */         if (Character.isLetter(c1)) {
/* 281 */           c1 = (char)(c1 - 32);
/*     */         }
/* 283 */         stringBuffer.append(c1);
/*     */       } 
/* 285 */       charArrayWriter.reset();
/* 286 */       bool = true;
/*     */     } 
/*     */ 
/*     */     
/* 290 */     return bool ? stringBuffer.toString() : paramString1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/URLEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */