/*     */ package com.sun.xml.internal.bind.api.impl;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class NameUtil
/*     */ {
/*     */   protected static final int UPPER_LETTER = 0;
/*     */   protected static final int LOWER_LETTER = 1;
/*     */   protected static final int OTHER_LETTER = 2;
/*     */   protected static final int DIGIT = 3;
/*     */   protected static final int OTHER = 4;
/*     */   
/*     */   protected boolean isPunct(char c) {
/*  46 */     return (c == '-' || c == '.' || c == ':' || c == '_' || c == '·' || c == '·' || c == '۝' || c == '۞');
/*     */   }
/*     */   
/*     */   protected static boolean isDigit(char c) {
/*  50 */     return ((c >= '0' && c <= '9') || Character.isDigit(c));
/*     */   }
/*     */   
/*     */   protected static boolean isUpper(char c) {
/*  54 */     return ((c >= 'A' && c <= 'Z') || Character.isUpperCase(c));
/*     */   }
/*     */   
/*     */   protected static boolean isLower(char c) {
/*  58 */     return ((c >= 'a' && c <= 'z') || Character.isLowerCase(c));
/*     */   }
/*     */   
/*     */   protected boolean isLetter(char c) {
/*  62 */     return ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || Character.isLetter(c));
/*     */   }
/*     */ 
/*     */   
/*     */   private String toLowerCase(String s) {
/*  67 */     return s.toLowerCase(Locale.ENGLISH);
/*     */   }
/*     */ 
/*     */   
/*     */   private String toUpperCase(char c) {
/*  72 */     return String.valueOf(c).toUpperCase(Locale.ENGLISH);
/*     */   }
/*     */ 
/*     */   
/*     */   private String toUpperCase(String s) {
/*  77 */     return s.toUpperCase(Locale.ENGLISH);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String capitalize(String s) {
/*  85 */     if (!isLower(s.charAt(0)))
/*  86 */       return s; 
/*  87 */     StringBuilder sb = new StringBuilder(s.length());
/*  88 */     sb.append(toUpperCase(s.charAt(0)));
/*  89 */     sb.append(toLowerCase(s.substring(1)));
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private int nextBreak(String s, int start) {
/*  95 */     int n = s.length();
/*     */     
/*  97 */     char c1 = s.charAt(start);
/*  98 */     int t1 = classify(c1);
/*     */     
/* 100 */     for (int i = start + 1; i < n; i++) {
/*     */ 
/*     */       
/* 103 */       int t0 = t1;
/*     */       
/* 105 */       c1 = s.charAt(i);
/* 106 */       t1 = classify(c1);
/*     */       
/* 108 */       switch (actionTable[t0 * 5 + t1]) {
/*     */         case 0:
/* 110 */           if (isPunct(c1)) return i; 
/*     */           break;
/*     */         case 1:
/* 113 */           if (i < n - 1) {
/* 114 */             char c2 = s.charAt(i + 1);
/* 115 */             if (isLower(c2))
/* 116 */               return i; 
/*     */           } 
/*     */           break;
/*     */         case 2:
/* 120 */           return i;
/*     */       } 
/*     */     } 
/* 123 */     return -1;
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
/* 138 */   private static final byte[] actionTable = new byte[25];
/*     */ 
/*     */   
/*     */   private static final byte ACTION_CHECK_PUNCT = 0;
/*     */ 
/*     */   
/*     */   private static final byte ACTION_CHECK_C2 = 1;
/*     */   
/*     */   private static final byte ACTION_BREAK = 2;
/*     */   
/*     */   private static final byte ACTION_NOBREAK = 3;
/*     */ 
/*     */   
/*     */   private static byte decideAction(int t0, int t1) {
/* 152 */     if (t0 == 4 && t1 == 4) return 0; 
/* 153 */     if (!xor((t0 == 3), (t1 == 3))) return 2; 
/* 154 */     if (t0 == 1 && t1 != 1) return 2; 
/* 155 */     if (!xor((t0 <= 2), (t1 <= 2))) return 2; 
/* 156 */     if (!xor((t0 == 2), (t1 == 2))) return 2;
/*     */     
/* 158 */     if (t0 == 0 && t1 == 0) return 1;
/*     */     
/* 160 */     return 3;
/*     */   }
/*     */   
/*     */   private static boolean xor(boolean x, boolean y) {
/* 164 */     return ((x && y) || (!x && !y));
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 169 */     for (int t0 = 0; t0 < 5; t0++) {
/* 170 */       for (int t1 = 0; t1 < 5; t1++) {
/* 171 */         actionTable[t0 * 5 + t1] = decideAction(t0, t1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected int classify(char c0) {
/* 178 */     switch (Character.getType(c0)) { case 1:
/* 179 */         return 0;
/* 180 */       case 2: return 1;
/*     */       case 3: case 4:
/*     */       case 5:
/* 183 */         return 2;
/* 184 */       case 9: return 3; }
/* 185 */      return 4;
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
/*     */   public List<String> toWordList(String s) {
/* 200 */     ArrayList<String> ss = new ArrayList<>();
/* 201 */     int n = s.length(); int i;
/* 202 */     for (i = 0; i < n; ) {
/*     */ 
/*     */       
/* 205 */       while (i < n && 
/* 206 */         isPunct(s.charAt(i)))
/*     */       {
/* 208 */         i++;
/*     */       }
/* 210 */       if (i >= n) {
/*     */         break;
/*     */       }
/* 213 */       int b = nextBreak(s, i);
/* 214 */       String w = (b == -1) ? s.substring(i) : s.substring(i, b);
/* 215 */       ss.add(escape(capitalize(w)));
/* 216 */       if (b == -1)
/* 217 */         break;  i = b;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 224 */     return ss;
/*     */   }
/*     */   
/*     */   protected String toMixedCaseName(List<String> ss, boolean startUpper) {
/* 228 */     StringBuilder sb = new StringBuilder();
/* 229 */     if (!ss.isEmpty()) {
/* 230 */       sb.append(startUpper ? ss.get(0) : toLowerCase(ss.get(0)));
/* 231 */       for (int i = 1; i < ss.size(); i++)
/* 232 */         sb.append(ss.get(i)); 
/*     */     } 
/* 234 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String toMixedCaseVariableName(String[] ss, boolean startUpper, boolean cdrUpper) {
/* 240 */     if (cdrUpper)
/* 241 */       for (int i = 1; i < ss.length; i++)
/* 242 */         ss[i] = capitalize(ss[i]);  
/* 243 */     StringBuilder sb = new StringBuilder();
/* 244 */     if (ss.length > 0) {
/* 245 */       sb.append(startUpper ? ss[0] : toLowerCase(ss[0]));
/* 246 */       for (int i = 1; i < ss.length; i++)
/* 247 */         sb.append(ss[i]); 
/*     */     } 
/* 249 */     return sb.toString();
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
/*     */   public String toConstantName(String s) {
/* 261 */     return toConstantName(toWordList(s));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toConstantName(List<String> ss) {
/* 272 */     StringBuilder sb = new StringBuilder();
/* 273 */     if (!ss.isEmpty()) {
/* 274 */       sb.append(toUpperCase(ss.get(0)));
/* 275 */       for (int i = 1; i < ss.size(); i++) {
/* 276 */         sb.append('_');
/* 277 */         sb.append(toUpperCase(ss.get(i)));
/*     */       } 
/*     */     } 
/* 280 */     return sb.toString();
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
/*     */   public static void escape(StringBuilder sb, String s, int start) {
/* 299 */     int n = s.length();
/* 300 */     for (int i = start; i < n; i++) {
/* 301 */       char c = s.charAt(i);
/* 302 */       if (Character.isJavaIdentifierPart(c)) {
/* 303 */         sb.append(c);
/*     */       } else {
/* 305 */         sb.append('_');
/* 306 */         if (c <= '\017') { sb.append("000"); }
/* 307 */         else if (c <= 'ÿ') { sb.append("00"); }
/* 308 */         else if (c <= '࿿') { sb.append('0'); }
/* 309 */          sb.append(Integer.toString(c, 16));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String escape(String s) {
/* 319 */     int n = s.length();
/* 320 */     for (int i = 0; i < n; i++) {
/* 321 */       if (!Character.isJavaIdentifierPart(s.charAt(i))) {
/* 322 */         StringBuilder sb = new StringBuilder(s.substring(0, i));
/* 323 */         escape(sb, s, i);
/* 324 */         return sb.toString();
/*     */       } 
/* 326 */     }  return s;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/api/impl/NameUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */