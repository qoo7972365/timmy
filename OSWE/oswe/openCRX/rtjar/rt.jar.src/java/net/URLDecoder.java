/*     */ package java.net;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class URLDecoder
/*     */ {
/*  81 */   static String dfltEncName = URLEncoder.dfltEncName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public static String decode(String paramString) {
/*  97 */     String str = null;
/*     */     
/*     */     try {
/* 100 */       str = decode(paramString, dfltEncName);
/* 101 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*     */ 
/*     */ 
/*     */     
/* 105 */     return str;
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
/*     */   public static String decode(String paramString1, String paramString2) throws UnsupportedEncodingException {
/* 135 */     boolean bool = false;
/* 136 */     int i = paramString1.length();
/* 137 */     StringBuffer stringBuffer = new StringBuffer((i > 500) ? (i / 2) : i);
/* 138 */     byte b = 0;
/*     */     
/* 140 */     if (paramString2.length() == 0) {
/* 141 */       throw new UnsupportedEncodingException("URLDecoder: empty string enc parameter");
/*     */     }
/*     */ 
/*     */     
/* 145 */     byte[] arrayOfByte = null;
/* 146 */     while (b < i) {
/* 147 */       char c = paramString1.charAt(b);
/* 148 */       switch (c) {
/*     */         case '+':
/* 150 */           stringBuffer.append(' ');
/* 151 */           b++;
/* 152 */           bool = true;
/*     */           continue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case '%':
/*     */           try {
/* 168 */             if (arrayOfByte == null)
/* 169 */               arrayOfByte = new byte[(i - b) / 3]; 
/* 170 */             byte b1 = 0;
/*     */             
/* 172 */             while (b + 2 < i && c == '%') {
/*     */               
/* 174 */               int j = Integer.parseInt(paramString1.substring(b + 1, b + 3), 16);
/* 175 */               if (j < 0)
/* 176 */                 throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - negative value"); 
/* 177 */               arrayOfByte[b1++] = (byte)j;
/* 178 */               b += 3;
/* 179 */               if (b < i) {
/* 180 */                 c = paramString1.charAt(b);
/*     */               }
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 186 */             if (b < i && c == '%') {
/* 187 */               throw new IllegalArgumentException("URLDecoder: Incomplete trailing escape (%) pattern");
/*     */             }
/*     */             
/* 190 */             stringBuffer.append(new String(arrayOfByte, 0, b1, paramString2));
/* 191 */           } catch (NumberFormatException numberFormatException) {
/* 192 */             throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - " + numberFormatException
/*     */                 
/* 194 */                 .getMessage());
/*     */           } 
/* 196 */           bool = true;
/*     */           continue;
/*     */       } 
/* 199 */       stringBuffer.append(c);
/* 200 */       b++;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 205 */     return bool ? stringBuffer.toString() : paramString1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/URLDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */