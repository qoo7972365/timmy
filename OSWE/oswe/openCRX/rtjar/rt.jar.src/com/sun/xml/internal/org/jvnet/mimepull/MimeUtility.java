/*     */ package com.sun.xml.internal.org.jvnet.mimepull;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class MimeUtility
/*     */ {
/* 130 */   private static final boolean ignoreUnknownEncoding = PropUtil.getBooleanSystemProperty("mail.mime.ignoreunknownencoding", false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InputStream decode(InputStream is, String encoding) throws DecodingException {
/* 152 */     if (encoding.equalsIgnoreCase("base64"))
/* 153 */       return new BASE64DecoderStream(is); 
/* 154 */     if (encoding.equalsIgnoreCase("quoted-printable"))
/* 155 */       return new QPDecoderStream(is); 
/* 156 */     if (encoding.equalsIgnoreCase("uuencode") || encoding
/* 157 */       .equalsIgnoreCase("x-uuencode") || encoding
/* 158 */       .equalsIgnoreCase("x-uue"))
/* 159 */       return new UUDecoderStream(is); 
/* 160 */     if (encoding.equalsIgnoreCase("binary") || encoding
/* 161 */       .equalsIgnoreCase("7bit") || encoding
/* 162 */       .equalsIgnoreCase("8bit")) {
/* 163 */       return is;
/*     */     }
/* 165 */     if (!ignoreUnknownEncoding) {
/* 166 */       throw new DecodingException("Unknown encoding: " + encoding);
/*     */     }
/* 168 */     return is;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/mimepull/MimeUtility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */