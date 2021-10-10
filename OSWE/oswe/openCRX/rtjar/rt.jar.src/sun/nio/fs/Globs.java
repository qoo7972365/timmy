/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.util.regex.PatternSyntaxException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Globs
/*     */ {
/*     */   private static final String regexMetaChars = ".^$+{[]|()";
/*     */   private static final String globMetaChars = "\\*?[{";
/*     */   
/*     */   private static boolean isRegexMeta(char paramChar) {
/*  37 */     return (".^$+{[]|()".indexOf(paramChar) != -1);
/*     */   }
/*     */   
/*     */   private static boolean isGlobMeta(char paramChar) {
/*  41 */     return ("\\*?[{".indexOf(paramChar) != -1);
/*     */   }
/*  43 */   private static char EOL = Character.MIN_VALUE;
/*     */   
/*     */   private static char next(String paramString, int paramInt) {
/*  46 */     if (paramInt < paramString.length()) {
/*  47 */       return paramString.charAt(paramInt);
/*     */     }
/*  49 */     return EOL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String toRegexPattern(String paramString, boolean paramBoolean) {
/*  58 */     boolean bool = false;
/*  59 */     StringBuilder stringBuilder = new StringBuilder("^");
/*     */     
/*  61 */     byte b = 0;
/*  62 */     while (b < paramString.length()) {
/*  63 */       char c2; boolean bool1; char c; char c1 = paramString.charAt(b++);
/*  64 */       switch (c1) {
/*     */         
/*     */         case '\\':
/*  67 */           if (b == paramString.length()) {
/*  68 */             throw new PatternSyntaxException("No character to escape", paramString, b - 1);
/*     */           }
/*     */           
/*  71 */           c2 = paramString.charAt(b++);
/*  72 */           if (isGlobMeta(c2) || isRegexMeta(c2)) {
/*  73 */             stringBuilder.append('\\');
/*     */           }
/*  75 */           stringBuilder.append(c2);
/*     */           continue;
/*     */         case '/':
/*  78 */           if (paramBoolean) {
/*  79 */             stringBuilder.append("\\\\"); continue;
/*     */           } 
/*  81 */           stringBuilder.append(c1);
/*     */           continue;
/*     */ 
/*     */         
/*     */         case '[':
/*  86 */           if (paramBoolean) {
/*  87 */             stringBuilder.append("[[^\\\\]&&[");
/*     */           } else {
/*  89 */             stringBuilder.append("[[^/]&&[");
/*     */           } 
/*  91 */           if (next(paramString, b) == '^') {
/*     */             
/*  93 */             stringBuilder.append("\\^");
/*  94 */             b++;
/*     */           } else {
/*     */             
/*  97 */             if (next(paramString, b) == '!') {
/*  98 */               stringBuilder.append('^');
/*  99 */               b++;
/*     */             } 
/*     */             
/* 102 */             if (next(paramString, b) == '-') {
/* 103 */               stringBuilder.append('-');
/* 104 */               b++;
/*     */             } 
/*     */           } 
/* 107 */           bool1 = false;
/* 108 */           c = Character.MIN_VALUE;
/* 109 */           while (b < paramString.length()) {
/* 110 */             c1 = paramString.charAt(b++);
/* 111 */             if (c1 == ']') {
/*     */               break;
/*     */             }
/* 114 */             if (c1 == '/' || (paramBoolean && c1 == '\\')) {
/* 115 */               throw new PatternSyntaxException("Explicit 'name separator' in class", paramString, b - 1);
/*     */             }
/*     */ 
/*     */             
/* 119 */             if (c1 == '\\' || c1 == '[' || (c1 == '&' && 
/* 120 */               next(paramString, b) == '&'))
/*     */             {
/* 122 */               stringBuilder.append('\\');
/*     */             }
/* 124 */             stringBuilder.append(c1);
/*     */             
/* 126 */             if (c1 == '-') {
/* 127 */               if (!bool1) {
/* 128 */                 throw new PatternSyntaxException("Invalid range", paramString, b - 1);
/*     */               }
/*     */               
/* 131 */               if ((c1 = next(paramString, b++)) == EOL || c1 == ']') {
/*     */                 break;
/*     */               }
/* 134 */               if (c1 < c) {
/* 135 */                 throw new PatternSyntaxException("Invalid range", paramString, b - 3);
/*     */               }
/*     */               
/* 138 */               stringBuilder.append(c1);
/* 139 */               bool1 = false; continue;
/*     */             } 
/* 141 */             bool1 = true;
/* 142 */             c = c1;
/*     */           } 
/*     */           
/* 145 */           if (c1 != ']') {
/* 146 */             throw new PatternSyntaxException("Missing ']", paramString, b - 1);
/*     */           }
/* 148 */           stringBuilder.append("]]");
/*     */           continue;
/*     */         case '{':
/* 151 */           if (bool) {
/* 152 */             throw new PatternSyntaxException("Cannot nest groups", paramString, b - 1);
/*     */           }
/*     */           
/* 155 */           stringBuilder.append("(?:(?:");
/* 156 */           bool = true;
/*     */           continue;
/*     */         case '}':
/* 159 */           if (bool) {
/* 160 */             stringBuilder.append("))");
/* 161 */             bool = false; continue;
/*     */           } 
/* 163 */           stringBuilder.append('}');
/*     */           continue;
/*     */         
/*     */         case ',':
/* 167 */           if (bool) {
/* 168 */             stringBuilder.append(")|(?:"); continue;
/*     */           } 
/* 170 */           stringBuilder.append(',');
/*     */           continue;
/*     */         
/*     */         case '*':
/* 174 */           if (next(paramString, b) == '*') {
/*     */             
/* 176 */             stringBuilder.append(".*");
/* 177 */             b++;
/*     */             continue;
/*     */           } 
/* 180 */           if (paramBoolean) {
/* 181 */             stringBuilder.append("[^\\\\]*"); continue;
/*     */           } 
/* 183 */           stringBuilder.append("[^/]*");
/*     */           continue;
/*     */ 
/*     */         
/*     */         case '?':
/* 188 */           if (paramBoolean) {
/* 189 */             stringBuilder.append("[^\\\\]"); continue;
/*     */           } 
/* 191 */           stringBuilder.append("[^/]");
/*     */           continue;
/*     */       } 
/*     */ 
/*     */       
/* 196 */       if (isRegexMeta(c1)) {
/* 197 */         stringBuilder.append('\\');
/*     */       }
/* 199 */       stringBuilder.append(c1);
/*     */     } 
/*     */ 
/*     */     
/* 203 */     if (bool) {
/* 204 */       throw new PatternSyntaxException("Missing '}", paramString, b - 1);
/*     */     }
/*     */     
/* 207 */     return stringBuilder.append('$').toString();
/*     */   }
/*     */   
/*     */   static String toUnixRegexPattern(String paramString) {
/* 211 */     return toRegexPattern(paramString, false);
/*     */   }
/*     */   
/*     */   static String toWindowsRegexPattern(String paramString) {
/* 215 */     return toRegexPattern(paramString, true);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/Globs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */