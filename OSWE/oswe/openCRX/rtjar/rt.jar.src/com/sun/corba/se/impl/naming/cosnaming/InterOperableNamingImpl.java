/*     */ package com.sun.corba.se.impl.naming.cosnaming;
/*     */ 
/*     */ import java.io.StringWriter;
/*     */ import org.omg.CosNaming.NameComponent;
/*     */ import org.omg.CosNaming.NamingContextExtPackage.InvalidAddress;
/*     */ import org.omg.CosNaming.NamingContextPackage.InvalidName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InterOperableNamingImpl
/*     */ {
/*     */   public String convertToString(NameComponent[] paramArrayOfNameComponent) {
/*  60 */     String str = convertNameComponentToString(paramArrayOfNameComponent[0]);
/*     */     
/*  62 */     for (byte b = 1; b < paramArrayOfNameComponent.length; b++) {
/*  63 */       String str1 = convertNameComponentToString(paramArrayOfNameComponent[b]);
/*  64 */       if (str1 != null)
/*     */       {
/*  66 */         str = str + "/" + convertNameComponentToString(paramArrayOfNameComponent[b]);
/*     */       }
/*     */     } 
/*     */     
/*  70 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String convertNameComponentToString(NameComponent paramNameComponent) {
/*  79 */     if ((paramNameComponent.id == null || paramNameComponent.id
/*  80 */       .length() == 0) && (paramNameComponent.kind == null || paramNameComponent.kind
/*     */       
/*  82 */       .length() == 0))
/*     */     {
/*  84 */       return ".";
/*     */     }
/*  86 */     if (paramNameComponent.id == null || paramNameComponent.id
/*  87 */       .length() == 0) {
/*     */       
/*  89 */       String str = addEscape(paramNameComponent.kind);
/*  90 */       return "." + str;
/*     */     } 
/*  92 */     if (paramNameComponent.kind == null || paramNameComponent.kind
/*  93 */       .length() == 0)
/*     */     {
/*  95 */       return addEscape(paramNameComponent.id);
/*     */     }
/*     */ 
/*     */     
/*  99 */     String str1 = addEscape(paramNameComponent.id);
/* 100 */     String str2 = addEscape(paramNameComponent.kind);
/* 101 */     return str1 + "." + str2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String addEscape(String paramString) {
/*     */     StringBuffer stringBuffer;
/* 111 */     if (paramString != null && (paramString.indexOf('.') != -1 || paramString
/* 112 */       .indexOf('/') != -1)) {
/*     */ 
/*     */       
/* 115 */       stringBuffer = new StringBuffer();
/* 116 */       for (byte b = 0; b < paramString.length(); b++) {
/* 117 */         char c = paramString.charAt(b);
/* 118 */         if (c != '.' && c != '/') {
/*     */           
/* 120 */           stringBuffer.append(c);
/*     */         }
/*     */         else {
/*     */           
/* 124 */           stringBuffer.append('\\');
/* 125 */           stringBuffer.append(c);
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 130 */       return paramString;
/*     */     } 
/* 132 */     return new String(stringBuffer);
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
/*     */   public NameComponent[] convertToNameComponent(String paramString) throws InvalidName {
/* 146 */     String[] arrayOfString = breakStringToNameComponents(paramString);
/* 147 */     if (arrayOfString == null || arrayOfString.length == 0)
/*     */     {
/*     */       
/* 150 */       return null;
/*     */     }
/* 152 */     NameComponent[] arrayOfNameComponent = new NameComponent[arrayOfString.length];
/*     */     
/* 154 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 155 */       arrayOfNameComponent[b] = createNameComponentFromString(arrayOfString[b]);
/*     */     }
/*     */     
/* 158 */     return arrayOfNameComponent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] breakStringToNameComponents(String paramString) {
/* 165 */     int[] arrayOfInt = new int[100];
/* 166 */     byte b = 0;
/*     */     
/* 168 */     for (int i = 0; i <= paramString.length(); ) {
/* 169 */       arrayOfInt[b] = paramString.indexOf('/', i);
/*     */       
/* 171 */       if (arrayOfInt[b] == -1) {
/*     */ 
/*     */         
/* 174 */         i = paramString.length() + 1;
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */       
/* 181 */       if (arrayOfInt[b] > 0 && paramString
/* 182 */         .charAt(arrayOfInt[b] - 1) == '\\') {
/*     */ 
/*     */         
/* 185 */         i = arrayOfInt[b] + 1;
/* 186 */         arrayOfInt[b] = -1;
/*     */         continue;
/*     */       } 
/* 189 */       i = arrayOfInt[b] + 1;
/* 190 */       b++;
/*     */     } 
/*     */ 
/*     */     
/* 194 */     if (b == 0) {
/* 195 */       String[] arrayOfString = new String[1];
/* 196 */       arrayOfString[0] = paramString;
/* 197 */       return arrayOfString;
/*     */     } 
/* 199 */     if (b != 0) {
/* 200 */       b++;
/*     */     }
/* 202 */     return StringComponentsFromIndices(arrayOfInt, b, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] StringComponentsFromIndices(int[] paramArrayOfint, int paramInt, String paramString) {
/* 212 */     String[] arrayOfString = new String[paramInt];
/* 213 */     int i = 0;
/* 214 */     int j = paramArrayOfint[0];
/* 215 */     for (int k = 0; k < paramInt; k++) {
/* 216 */       arrayOfString[k] = paramString.substring(i, j);
/*     */       
/* 218 */       if (paramArrayOfint[k] < paramString.length() - 1 && paramArrayOfint[k] != -1) {
/*     */ 
/*     */         
/* 221 */         i = paramArrayOfint[k] + 1;
/*     */       } else {
/*     */         
/* 224 */         i = 0;
/* 225 */         k = paramInt;
/*     */       } 
/* 227 */       if (k + 1 < paramArrayOfint.length && paramArrayOfint[k + 1] < paramString
/* 228 */         .length() - 1 && paramArrayOfint[k + 1] != -1) {
/*     */ 
/*     */         
/* 231 */         j = paramArrayOfint[k + 1];
/*     */       } else {
/*     */         
/* 234 */         k = paramInt;
/*     */       } 
/*     */       
/* 237 */       if (i != 0 && k == paramInt) {
/* 238 */         arrayOfString[paramInt - 1] = paramString
/* 239 */           .substring(i);
/*     */       }
/*     */     } 
/* 242 */     return arrayOfString;
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
/*     */   private NameComponent createNameComponentFromString(String paramString) throws InvalidName {
/* 254 */     String str1 = null;
/* 255 */     String str2 = null;
/* 256 */     if (paramString == null || paramString
/* 257 */       .length() == 0 || paramString
/* 258 */       .endsWith("."))
/*     */     {
/*     */ 
/*     */       
/* 262 */       throw new InvalidName();
/*     */     }
/*     */     
/* 265 */     int i = paramString.indexOf('.', 0);
/*     */     
/* 267 */     if (i == -1) {
/* 268 */       str1 = paramString;
/*     */     
/*     */     }
/* 271 */     else if (i == 0) {
/*     */ 
/*     */       
/* 274 */       if (paramString.length() != 1) {
/* 275 */         str2 = paramString.substring(1);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 280 */     else if (paramString.charAt(i - 1) != '\\') {
/* 281 */       str1 = paramString.substring(0, i);
/* 282 */       str2 = paramString.substring(i + 1);
/*     */     } else {
/*     */       
/* 285 */       boolean bool = false;
/* 286 */       while (i < paramString.length() && bool != true) {
/*     */ 
/*     */         
/* 289 */         i = paramString.indexOf('.', i + 1);
/* 290 */         if (i > 0) {
/* 291 */           if (paramString.charAt(i - 1) != '\\')
/*     */           {
/*     */             
/* 294 */             bool = true;
/*     */           }
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 300 */         i = paramString.length();
/*     */       } 
/*     */       
/* 303 */       if (bool == true) {
/* 304 */         str1 = paramString.substring(0, i);
/* 305 */         str2 = paramString.substring(i + 1);
/*     */       } else {
/*     */         
/* 308 */         str1 = paramString;
/*     */       } 
/*     */     } 
/*     */     
/* 312 */     str1 = cleanEscapeCharacter(str1);
/* 313 */     str2 = cleanEscapeCharacter(str2);
/* 314 */     if (str1 == null) {
/* 315 */       str1 = "";
/*     */     }
/* 317 */     if (str2 == null) {
/* 318 */       str2 = "";
/*     */     }
/* 320 */     return new NameComponent(str1, str2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String cleanEscapeCharacter(String paramString) {
/* 329 */     if (paramString == null || paramString.length() == 0) {
/* 330 */       return paramString;
/*     */     }
/* 332 */     int i = paramString.indexOf('\\');
/* 333 */     if (i == 0) {
/* 334 */       return paramString;
/*     */     }
/*     */     
/* 337 */     StringBuffer stringBuffer1 = new StringBuffer(paramString);
/* 338 */     StringBuffer stringBuffer2 = new StringBuffer();
/*     */     
/* 340 */     for (byte b = 0; b < paramString.length(); b++) {
/* 341 */       char c = stringBuffer1.charAt(b);
/* 342 */       if (c != '\\') {
/* 343 */         stringBuffer2.append(c);
/*     */       }
/* 345 */       else if (b + 1 < paramString.length()) {
/* 346 */         char c1 = stringBuffer1.charAt(b + 1);
/*     */ 
/*     */ 
/*     */         
/* 350 */         if (Character.isLetterOrDigit(c1)) {
/* 351 */           stringBuffer2.append(c);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 356 */     return new String(stringBuffer2);
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
/*     */   public String createURLBasedAddress(String paramString1, String paramString2) throws InvalidAddress {
/* 371 */     String str = null;
/* 372 */     if (paramString1 == null || paramString1
/* 373 */       .length() == 0) {
/* 374 */       throw new InvalidAddress();
/*     */     }
/*     */     
/* 377 */     str = "corbaname:" + paramString1 + "#" + encode(paramString2);
/*     */     
/* 379 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String encode(String paramString) {
/* 385 */     StringWriter stringWriter = new StringWriter();
/* 386 */     boolean bool = false;
/* 387 */     for (byte b = 0; b < paramString.length(); b++) {
/*     */       
/* 389 */       char c = paramString.charAt(b);
/* 390 */       if (Character.isLetterOrDigit(c)) {
/* 391 */         stringWriter.write(c);
/*     */ 
/*     */       
/*     */       }
/* 395 */       else if (c == ';' || c == '/' || c == '?' || c == ':' || c == '@' || c == '&' || c == '=' || c == '+' || c == '$' || c == ';' || c == '-' || c == '_' || c == '.' || c == '!' || c == '~' || c == '*' || c == ' ' || c == '(' || c == ')') {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 401 */         stringWriter.write(c);
/*     */       }
/*     */       else {
/*     */         
/* 405 */         stringWriter.write(37);
/* 406 */         String str = Integer.toHexString(c);
/* 407 */         stringWriter.write(str);
/*     */       } 
/*     */     } 
/* 410 */     return stringWriter.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/naming/cosnaming/InterOperableNamingImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */