/*     */ package javax.lang.model;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum SourceVersion
/*     */ {
/*  66 */   RELEASE_0,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   RELEASE_1,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   RELEASE_2,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   RELEASE_3,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   RELEASE_4,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   RELEASE_5,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   RELEASE_6,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   RELEASE_7,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 141 */   RELEASE_8;
/*     */ 
/*     */   
/*     */   private static final SourceVersion latestSupported;
/*     */ 
/*     */   
/*     */   private static final Set<String> keywords;
/*     */ 
/*     */ 
/*     */   
/*     */   public static SourceVersion latest() {
/* 152 */     return RELEASE_8;
/*     */   }
/*     */   
/* 155 */   static { latestSupported = getLatestSupported();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 244 */     HashSet<String> hashSet = new HashSet();
/* 245 */     String[] arrayOfString = { "abstract", "continue", "for", "new", "switch", "assert", "default", "if", "package", "synchronized", "boolean", "do", "goto", "private", "this", "break", "double", "implements", "protected", "throw", "byte", "else", "import", "public", "throws", "case", "enum", "instanceof", "return", "transient", "catch", "extends", "int", "short", "try", "char", "final", "interface", "static", "void", "class", "finally", "long", "strictfp", "volatile", "const", "float", "native", "super", "while", "null", "true", "false" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 259 */     for (String str : arrayOfString)
/* 260 */       hashSet.add(str); 
/* 261 */     keywords = Collections.unmodifiableSet(hashSet); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isKeyword(CharSequence paramCharSequence) {
/* 272 */     String str = paramCharSequence.toString();
/* 273 */     return keywords.contains(str);
/*     */   }
/*     */   
/*     */   private static SourceVersion getLatestSupported() {
/*     */     try {
/*     */       String str = System.getProperty("java.specification.version");
/*     */       if ("1.8".equals(str))
/*     */         return RELEASE_8; 
/*     */       if ("1.7".equals(str))
/*     */         return RELEASE_7; 
/*     */       if ("1.6".equals(str))
/*     */         return RELEASE_6; 
/*     */     } catch (SecurityException securityException) {}
/*     */     return RELEASE_5;
/*     */   }
/*     */   
/*     */   public static SourceVersion latestSupported() {
/*     */     return latestSupported;
/*     */   }
/*     */   
/*     */   public static boolean isIdentifier(CharSequence paramCharSequence) {
/*     */     String str = paramCharSequence.toString();
/*     */     if (str.length() == 0)
/*     */       return false; 
/*     */     int i = str.codePointAt(0);
/*     */     if (!Character.isJavaIdentifierStart(i))
/*     */       return false; 
/*     */     int j = Character.charCount(i);
/*     */     for (; j < str.length(); j += Character.charCount(i)) {
/*     */       i = str.codePointAt(j);
/*     */       if (!Character.isJavaIdentifierPart(i))
/*     */         return false; 
/*     */     } 
/*     */     return true;
/*     */   }
/*     */   
/*     */   public static boolean isName(CharSequence paramCharSequence) {
/*     */     String str = paramCharSequence.toString();
/*     */     for (String str1 : str.split("\\.", -1)) {
/*     */       if (!isIdentifier(str1) || isKeyword(str1))
/*     */         return false; 
/*     */     } 
/*     */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/lang/model/SourceVersion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */