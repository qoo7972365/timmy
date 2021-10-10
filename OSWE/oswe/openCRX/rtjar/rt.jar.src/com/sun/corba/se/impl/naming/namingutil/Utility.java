/*     */ package com.sun.corba.se.impl.naming.namingutil;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.NamingSystemException;
/*     */ import java.io.StringWriter;
/*     */ import org.omg.CORBA.DATA_CONVERSION;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Utility
/*     */ {
/*  43 */   private static NamingSystemException wrapper = NamingSystemException.get("naming");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String cleanEscapes(String paramString) {
/*  49 */     StringWriter stringWriter = new StringWriter();
/*  50 */     for (byte b = 0; b < paramString.length(); b++) {
/*  51 */       char c = paramString.charAt(b);
/*  52 */       if (c != '%') {
/*  53 */         stringWriter.write(c);
/*     */       } else {
/*     */         
/*  56 */         b++;
/*  57 */         int i = hexOf(paramString.charAt(b));
/*  58 */         b++;
/*  59 */         int j = hexOf(paramString.charAt(b));
/*  60 */         int k = i * 16 + j;
/*     */         
/*  62 */         stringWriter.write((char)k);
/*     */       } 
/*     */     } 
/*  65 */     return stringWriter.toString();
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
/*     */   static int hexOf(char paramChar) {
/*  77 */     int i = paramChar - 48;
/*  78 */     if (i >= 0 && i <= 9) {
/*  79 */       return i;
/*     */     }
/*  81 */     i = paramChar - 97 + 10;
/*  82 */     if (i >= 10 && i <= 15) {
/*  83 */       return i;
/*     */     }
/*  85 */     i = paramChar - 65 + 10;
/*  86 */     if (i >= 10 && i <= 15) {
/*  87 */       return i;
/*     */     }
/*  89 */     throw new DATA_CONVERSION();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void validateGIOPVersion(IIOPEndpointInfo paramIIOPEndpointInfo) {
/*  97 */     if (paramIIOPEndpointInfo.getMajor() > 1 || paramIIOPEndpointInfo
/*  98 */       .getMinor() > 2)
/*     */     {
/* 100 */       throw wrapper.insBadAddress();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/naming/namingutil/Utility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */