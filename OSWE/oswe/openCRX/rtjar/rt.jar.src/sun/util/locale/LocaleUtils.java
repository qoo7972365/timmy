/*     */ package sun.util.locale;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LocaleUtils
/*     */ {
/*     */   public static boolean caseIgnoreMatch(String paramString1, String paramString2) {
/*  51 */     if (paramString1 == paramString2) {
/*  52 */       return true;
/*     */     }
/*     */     
/*  55 */     int i = paramString1.length();
/*  56 */     if (i != paramString2.length()) {
/*  57 */       return false;
/*     */     }
/*     */     
/*  60 */     for (byte b = 0; b < i; b++) {
/*  61 */       char c1 = paramString1.charAt(b);
/*  62 */       char c2 = paramString2.charAt(b);
/*  63 */       if (c1 != c2 && toLower(c1) != toLower(c2)) {
/*  64 */         return false;
/*     */       }
/*     */     } 
/*  67 */     return true;
/*     */   }
/*     */   
/*     */   static int caseIgnoreCompare(String paramString1, String paramString2) {
/*  71 */     if (paramString1 == paramString2) {
/*  72 */       return 0;
/*     */     }
/*  74 */     return toLowerString(paramString1).compareTo(toLowerString(paramString2));
/*     */   }
/*     */   
/*     */   static char toUpper(char paramChar) {
/*  78 */     return isLower(paramChar) ? (char)(paramChar - 32) : paramChar;
/*     */   }
/*     */   
/*     */   static char toLower(char paramChar) {
/*  82 */     return isUpper(paramChar) ? (char)(paramChar + 32) : paramChar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toLowerString(String paramString) {
/*  89 */     int i = paramString.length();
/*  90 */     byte b1 = 0;
/*  91 */     for (; b1 < i && 
/*  92 */       !isUpper(paramString.charAt(b1)); b1++);
/*     */ 
/*     */ 
/*     */     
/*  96 */     if (b1 == i) {
/*  97 */       return paramString;
/*     */     }
/*     */     
/* 100 */     char[] arrayOfChar = new char[i];
/* 101 */     for (byte b2 = 0; b2 < i; b2++) {
/* 102 */       char c = paramString.charAt(b2);
/* 103 */       arrayOfChar[b2] = (b2 < b1) ? c : toLower(c);
/*     */     } 
/* 105 */     return new String(arrayOfChar);
/*     */   }
/*     */   
/*     */   static String toUpperString(String paramString) {
/* 109 */     int i = paramString.length();
/* 110 */     byte b1 = 0;
/* 111 */     for (; b1 < i && 
/* 112 */       !isLower(paramString.charAt(b1)); b1++);
/*     */ 
/*     */ 
/*     */     
/* 116 */     if (b1 == i) {
/* 117 */       return paramString;
/*     */     }
/*     */     
/* 120 */     char[] arrayOfChar = new char[i];
/* 121 */     for (byte b2 = 0; b2 < i; b2++) {
/* 122 */       char c = paramString.charAt(b2);
/* 123 */       arrayOfChar[b2] = (b2 < b1) ? c : toUpper(c);
/*     */     } 
/* 125 */     return new String(arrayOfChar);
/*     */   }
/*     */   
/*     */   static String toTitleString(String paramString) {
/*     */     int i;
/* 130 */     if ((i = paramString.length()) == 0) {
/* 131 */       return paramString;
/*     */     }
/* 133 */     byte b1 = 0;
/* 134 */     if (!isLower(paramString.charAt(b1))) {
/* 135 */       for (b1 = 1; b1 < i && 
/* 136 */         !isUpper(paramString.charAt(b1)); b1++);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 141 */     if (b1 == i) {
/* 142 */       return paramString;
/*     */     }
/*     */     
/* 145 */     char[] arrayOfChar = new char[i];
/* 146 */     for (byte b2 = 0; b2 < i; b2++) {
/* 147 */       char c = paramString.charAt(b2);
/* 148 */       if (b2 == 0 && b1 == 0) {
/* 149 */         arrayOfChar[b2] = toUpper(c);
/* 150 */       } else if (b2 < b1) {
/* 151 */         arrayOfChar[b2] = c;
/*     */       } else {
/* 153 */         arrayOfChar[b2] = toLower(c);
/*     */       } 
/*     */     } 
/* 156 */     return new String(arrayOfChar);
/*     */   }
/*     */   
/*     */   private static boolean isUpper(char paramChar) {
/* 160 */     return (paramChar >= 'A' && paramChar <= 'Z');
/*     */   }
/*     */   
/*     */   private static boolean isLower(char paramChar) {
/* 164 */     return (paramChar >= 'a' && paramChar <= 'z');
/*     */   }
/*     */   
/*     */   static boolean isAlpha(char paramChar) {
/* 168 */     return ((paramChar >= 'A' && paramChar <= 'Z') || (paramChar >= 'a' && paramChar <= 'z'));
/*     */   }
/*     */   
/*     */   static boolean isAlphaString(String paramString) {
/* 172 */     int i = paramString.length();
/* 173 */     for (byte b = 0; b < i; b++) {
/* 174 */       if (!isAlpha(paramString.charAt(b))) {
/* 175 */         return false;
/*     */       }
/*     */     } 
/* 178 */     return true;
/*     */   }
/*     */   
/*     */   static boolean isNumeric(char paramChar) {
/* 182 */     return (paramChar >= '0' && paramChar <= '9');
/*     */   }
/*     */   
/*     */   static boolean isNumericString(String paramString) {
/* 186 */     int i = paramString.length();
/* 187 */     for (byte b = 0; b < i; b++) {
/* 188 */       if (!isNumeric(paramString.charAt(b))) {
/* 189 */         return false;
/*     */       }
/*     */     } 
/* 192 */     return true;
/*     */   }
/*     */   
/*     */   static boolean isAlphaNumeric(char paramChar) {
/* 196 */     return (isAlpha(paramChar) || isNumeric(paramChar));
/*     */   }
/*     */   
/*     */   public static boolean isAlphaNumericString(String paramString) {
/* 200 */     int i = paramString.length();
/* 201 */     for (byte b = 0; b < i; b++) {
/* 202 */       if (!isAlphaNumeric(paramString.charAt(b))) {
/* 203 */         return false;
/*     */       }
/*     */     } 
/* 206 */     return true;
/*     */   }
/*     */   
/*     */   static boolean isEmpty(String paramString) {
/* 210 */     return (paramString == null || paramString.length() == 0);
/*     */   }
/*     */   
/*     */   static boolean isEmpty(Set<?> paramSet) {
/* 214 */     return (paramSet == null || paramSet.isEmpty());
/*     */   }
/*     */   
/*     */   static boolean isEmpty(Map<?, ?> paramMap) {
/* 218 */     return (paramMap == null || paramMap.isEmpty());
/*     */   }
/*     */   
/*     */   static boolean isEmpty(List<?> paramList) {
/* 222 */     return (paramList == null || paramList.isEmpty());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/LocaleUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */