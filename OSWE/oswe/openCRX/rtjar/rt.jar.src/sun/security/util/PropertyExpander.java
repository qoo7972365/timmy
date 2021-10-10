/*     */ package sun.security.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.security.GeneralSecurityException;
/*     */ import sun.net.www.ParseUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PropertyExpander
/*     */ {
/*     */   public static class ExpandException
/*     */     extends GeneralSecurityException
/*     */   {
/*     */     private static final long serialVersionUID = -7941948581406161702L;
/*     */     
/*     */     public ExpandException(String param1String) {
/*  49 */       super(param1String);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String expand(String paramString) throws ExpandException {
/*  56 */     return expand(paramString, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String expand(String paramString, boolean paramBoolean) throws ExpandException {
/*  62 */     if (paramString == null) {
/*  63 */       return null;
/*     */     }
/*  65 */     int i = paramString.indexOf("${", 0);
/*     */ 
/*     */     
/*  68 */     if (i == -1) return paramString;
/*     */     
/*  70 */     StringBuffer stringBuffer = new StringBuffer(paramString.length());
/*  71 */     int j = paramString.length();
/*  72 */     int k = 0;
/*     */ 
/*     */     
/*  75 */     while (i < j) {
/*  76 */       if (i > k) {
/*     */         
/*  78 */         stringBuffer.append(paramString.substring(k, i));
/*  79 */         k = i;
/*     */       } 
/*  81 */       int m = i + 2;
/*     */ 
/*     */       
/*  84 */       if (m < j && paramString.charAt(m) == '{') {
/*  85 */         m = paramString.indexOf("}}", m);
/*  86 */         if (m == -1 || m + 2 == j) {
/*     */           
/*  88 */           stringBuffer.append(paramString.substring(i));
/*     */           
/*     */           break;
/*     */         } 
/*  92 */         m++;
/*  93 */         stringBuffer.append(paramString.substring(i, m + 1));
/*     */       } else {
/*     */         
/*  96 */         while (m < j && paramString.charAt(m) != '}') {
/*  97 */           m++;
/*     */         }
/*  99 */         if (m == j) {
/*     */           
/* 101 */           stringBuffer.append(paramString.substring(i, m));
/*     */           break;
/*     */         } 
/* 104 */         String str = paramString.substring(i + 2, m);
/* 105 */         if (str.equals("/")) {
/* 106 */           stringBuffer.append(File.separatorChar);
/*     */         } else {
/* 108 */           String str1 = System.getProperty(str);
/* 109 */           if (str1 != null) {
/* 110 */             if (paramBoolean) {
/*     */               
/*     */               try {
/*     */                 
/* 114 */                 if (stringBuffer.length() > 0 || 
/* 115 */                   !(new URI(str1)).isAbsolute()) {
/* 116 */                   str1 = ParseUtil.encodePath(str1);
/*     */                 }
/* 118 */               } catch (URISyntaxException uRISyntaxException) {
/* 119 */                 str1 = ParseUtil.encodePath(str1);
/*     */               } 
/*     */             }
/* 122 */             stringBuffer.append(str1);
/*     */           } else {
/* 124 */             throw new ExpandException("unable to expand property " + str);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 130 */       k = m + 1;
/* 131 */       i = paramString.indexOf("${", k);
/* 132 */       if (i == -1) {
/*     */         
/* 134 */         if (k < j) {
/* 135 */           stringBuffer.append(paramString.substring(k, j));
/*     */         }
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 141 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/PropertyExpander.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */