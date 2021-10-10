/*     */ package com.sun.org.apache.xml.internal.security.utils;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.StringReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RFC2253Parser
/*     */ {
/*     */   public static String rfc2253toXMLdsig(String paramString) {
/*  38 */     String str = normalize(paramString, true);
/*     */     
/*  40 */     return rfctoXML(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String xmldsigtoRFC2253(String paramString) {
/*  51 */     String str = normalize(paramString, false);
/*     */     
/*  53 */     return xmltoRFC(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String normalize(String paramString) {
/*  63 */     return normalize(paramString, true);
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
/*     */   public static String normalize(String paramString, boolean paramBoolean) {
/*  75 */     if (paramString == null || paramString.equals("")) {
/*  76 */       return "";
/*     */     }
/*     */     
/*     */     try {
/*  80 */       String str = semicolonToComma(paramString);
/*  81 */       StringBuilder stringBuilder = new StringBuilder();
/*  82 */       int i = 0;
/*  83 */       int j = 0;
/*     */       
/*     */       int k, m;
/*     */       
/*  87 */       for (m = 0; (k = str.indexOf(',', m)) >= 0; m = k + 1) {
/*  88 */         j += countQuotes(str, m, k);
/*     */         
/*  90 */         if (k > 0 && str.charAt(k - 1) != '\\' && j % 2 == 0) {
/*  91 */           stringBuilder.append(parseRDN(str.substring(i, k).trim(), paramBoolean) + ",");
/*     */           
/*  93 */           i = k + 1;
/*  94 */           j = 0;
/*     */         } 
/*     */       } 
/*     */       
/*  98 */       stringBuilder.append(parseRDN(trim(str.substring(i)), paramBoolean));
/*     */       
/* 100 */       return stringBuilder.toString();
/* 101 */     } catch (IOException iOException) {
/* 102 */       return paramString;
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
/*     */   static String parseRDN(String paramString, boolean paramBoolean) throws IOException {
/* 115 */     StringBuilder stringBuilder = new StringBuilder();
/* 116 */     int i = 0;
/* 117 */     int j = 0;
/*     */     
/*     */     int k, m;
/* 120 */     for (m = 0; (k = paramString.indexOf('+', m)) >= 0; m = k + 1) {
/* 121 */       j += countQuotes(paramString, m, k);
/*     */       
/* 123 */       if (k > 0 && paramString.charAt(k - 1) != '\\' && j % 2 == 0) {
/* 124 */         stringBuilder.append(parseATAV(trim(paramString.substring(i, k)), paramBoolean) + "+");
/*     */         
/* 126 */         i = k + 1;
/* 127 */         j = 0;
/*     */       } 
/*     */     } 
/*     */     
/* 131 */     stringBuilder.append(parseATAV(trim(paramString.substring(i)), paramBoolean));
/*     */     
/* 133 */     return stringBuilder.toString();
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
/*     */   static String parseATAV(String paramString, boolean paramBoolean) throws IOException {
/* 145 */     int i = paramString.indexOf('=');
/*     */     
/* 147 */     if (i == -1 || (i > 0 && paramString.charAt(i - 1) == '\\')) {
/* 148 */       return paramString;
/*     */     }
/* 150 */     String str1 = normalizeAT(paramString.substring(0, i));
/*     */     
/* 152 */     String str2 = null;
/* 153 */     if (str1.charAt(0) >= '0' && str1.charAt(0) <= '9') {
/* 154 */       str2 = paramString.substring(i + 1);
/*     */     } else {
/* 156 */       str2 = normalizeV(paramString.substring(i + 1), paramBoolean);
/*     */     } 
/*     */     
/* 159 */     return str1 + "=" + str2;
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
/*     */   static String normalizeAT(String paramString) {
/* 171 */     String str = paramString.toUpperCase().trim();
/*     */     
/* 173 */     if (str.startsWith("OID")) {
/* 174 */       str = str.substring(3);
/*     */     }
/*     */     
/* 177 */     return str;
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
/*     */   static String normalizeV(String paramString, boolean paramBoolean) throws IOException {
/* 189 */     String str = trim(paramString);
/*     */     
/* 191 */     if (str.startsWith("\"")) {
/* 192 */       StringBuilder stringBuilder = new StringBuilder();
/* 193 */       StringReader stringReader = new StringReader(str.substring(1, str.length() - 1));
/* 194 */       int i = 0;
/*     */ 
/*     */       
/* 197 */       while ((i = stringReader.read()) > -1) {
/* 198 */         char c = (char)i;
/*     */ 
/*     */         
/* 201 */         if (c == ',' || c == '=' || c == '+' || c == '<' || c == '>' || c == '#' || c == ';')
/*     */         {
/* 203 */           stringBuilder.append('\\');
/*     */         }
/*     */         
/* 206 */         stringBuilder.append(c);
/*     */       } 
/*     */       
/* 209 */       str = trim(stringBuilder.toString());
/*     */     } 
/*     */     
/* 212 */     if (paramBoolean) {
/* 213 */       if (str.startsWith("#")) {
/* 214 */         str = '\\' + str;
/*     */       }
/*     */     }
/* 217 */     else if (str.startsWith("\\#")) {
/* 218 */       str = str.substring(1);
/*     */     } 
/*     */ 
/*     */     
/* 222 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String rfctoXML(String paramString) {
/*     */     try {
/* 233 */       String str = changeLess32toXML(paramString);
/*     */       
/* 235 */       return changeWStoXML(str);
/* 236 */     } catch (Exception exception) {
/* 237 */       return paramString;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String xmltoRFC(String paramString) {
/*     */     try {
/* 249 */       String str = changeLess32toRFC(paramString);
/*     */       
/* 251 */       return changeWStoRFC(str);
/* 252 */     } catch (Exception exception) {
/* 253 */       return paramString;
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
/*     */   static String changeLess32toRFC(String paramString) throws IOException {
/* 265 */     StringBuilder stringBuilder = new StringBuilder();
/* 266 */     StringReader stringReader = new StringReader(paramString);
/* 267 */     int i = 0;
/*     */ 
/*     */     
/* 270 */     while ((i = stringReader.read()) > -1) {
/* 271 */       char c = (char)i;
/*     */       
/* 273 */       if (c == '\\') {
/* 274 */         stringBuilder.append(c);
/*     */         
/* 276 */         char c1 = (char)stringReader.read();
/* 277 */         char c2 = (char)stringReader.read();
/*     */ 
/*     */         
/* 280 */         if (((c1 >= '0' && c1 <= '9') || (c1 >= 'A' && c1 <= 'F') || (c1 >= 'a' && c1 <= 'f')) && ((c2 >= '0' && c2 <= '9') || (c2 >= 'A' && c2 <= 'F') || (c2 >= 'a' && c2 <= 'f'))) {
/*     */ 
/*     */ 
/*     */           
/* 284 */           char c3 = (char)Byte.parseByte("" + c1 + c2, 16);
/*     */           
/* 286 */           stringBuilder.append(c3); continue;
/*     */         } 
/* 288 */         stringBuilder.append(c1);
/* 289 */         stringBuilder.append(c2);
/*     */         continue;
/*     */       } 
/* 292 */       stringBuilder.append(c);
/*     */     } 
/*     */ 
/*     */     
/* 296 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String changeLess32toXML(String paramString) throws IOException {
/* 307 */     StringBuilder stringBuilder = new StringBuilder();
/* 308 */     StringReader stringReader = new StringReader(paramString);
/* 309 */     int i = 0;
/*     */     
/* 311 */     while ((i = stringReader.read()) > -1) {
/* 312 */       if (i < 32) {
/* 313 */         stringBuilder.append('\\');
/* 314 */         stringBuilder.append(Integer.toHexString(i)); continue;
/*     */       } 
/* 316 */       stringBuilder.append((char)i);
/*     */     } 
/*     */ 
/*     */     
/* 320 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String changeWStoXML(String paramString) throws IOException {
/* 331 */     StringBuilder stringBuilder = new StringBuilder();
/* 332 */     StringReader stringReader = new StringReader(paramString);
/* 333 */     int i = 0;
/*     */ 
/*     */     
/* 336 */     while ((i = stringReader.read()) > -1) {
/* 337 */       char c = (char)i;
/*     */       
/* 339 */       if (c == '\\') {
/* 340 */         char c1 = (char)stringReader.read();
/*     */         
/* 342 */         if (c1 == ' ') {
/* 343 */           stringBuilder.append('\\');
/*     */           
/* 345 */           String str = "20";
/*     */           
/* 347 */           stringBuilder.append(str); continue;
/*     */         } 
/* 349 */         stringBuilder.append('\\');
/* 350 */         stringBuilder.append(c1);
/*     */         continue;
/*     */       } 
/* 353 */       stringBuilder.append(c);
/*     */     } 
/*     */ 
/*     */     
/* 357 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String changeWStoRFC(String paramString) {
/* 367 */     StringBuilder stringBuilder = new StringBuilder();
/* 368 */     int i = 0;
/*     */     
/*     */     int j, k;
/* 371 */     for (k = 0; (j = paramString.indexOf("\\20", k)) >= 0; k = j + 3) {
/* 372 */       stringBuilder.append(trim(paramString.substring(i, j)) + "\\ ");
/*     */       
/* 374 */       i = j + 3;
/*     */     } 
/*     */     
/* 377 */     stringBuilder.append(paramString.substring(i));
/*     */     
/* 379 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String semicolonToComma(String paramString) {
/* 389 */     return removeWSandReplace(paramString, ";", ",");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String removeWhiteSpace(String paramString1, String paramString2) {
/* 400 */     return removeWSandReplace(paramString1, paramString2, paramString2);
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
/*     */   static String removeWSandReplace(String paramString1, String paramString2, String paramString3) {
/* 412 */     StringBuilder stringBuilder = new StringBuilder();
/* 413 */     int i = 0;
/* 414 */     int j = 0;
/*     */     
/*     */     int k, m;
/* 417 */     for (m = 0; (k = paramString1.indexOf(paramString2, m)) >= 0; m = k + 1) {
/* 418 */       j += countQuotes(paramString1, m, k);
/*     */       
/* 420 */       if (k > 0 && paramString1.charAt(k - 1) != '\\' && j % 2 == 0) {
/* 421 */         stringBuilder.append(trim(paramString1.substring(i, k)) + paramString3);
/*     */         
/* 423 */         i = k + 1;
/* 424 */         j = 0;
/*     */       } 
/*     */     } 
/*     */     
/* 428 */     stringBuilder.append(trim(paramString1.substring(i)));
/*     */     
/* 430 */     return stringBuilder.toString();
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
/*     */   private static int countQuotes(String paramString, int paramInt1, int paramInt2) {
/* 442 */     byte b = 0;
/*     */     
/* 444 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 445 */       if (paramString.charAt(i) == '"') {
/* 446 */         b++;
/*     */       }
/*     */     } 
/*     */     
/* 450 */     return b;
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
/*     */   static String trim(String paramString) {
/* 463 */     String str = paramString.trim();
/* 464 */     int i = paramString.indexOf(str) + str.length();
/*     */     
/* 466 */     if (paramString.length() > i && str.endsWith("\\") && 
/* 467 */       !str.endsWith("\\\\") && paramString.charAt(i) == ' ') {
/* 468 */       str = str + " ";
/*     */     }
/*     */     
/* 471 */     return str;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/RFC2253Parser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */