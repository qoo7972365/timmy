/*     */ package sun.security.util;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AlgorithmDecomposer
/*     */ {
/*  39 */   private static final Pattern transPattern = Pattern.compile("/");
/*     */   
/*  41 */   private static final Pattern pattern = Pattern.compile("with|and", 2);
/*     */ 
/*     */ 
/*     */   
/*     */   private static Set<String> decomposeImpl(String paramString) {
/*  46 */     String[] arrayOfString = transPattern.split(paramString);
/*     */     
/*  48 */     HashSet<String> hashSet = new HashSet();
/*  49 */     for (String str : arrayOfString) {
/*  50 */       if (str != null && str.length() != 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  59 */         String[] arrayOfString1 = pattern.split(str);
/*     */         
/*  61 */         for (String str1 : arrayOfString1) {
/*  62 */           if (str1 != null && str1.length() != 0)
/*     */           {
/*     */ 
/*     */             
/*  66 */             hashSet.add(str1); } 
/*     */         } 
/*     */       } 
/*  69 */     }  return hashSet;
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
/*     */   public Set<String> decompose(String paramString) {
/*  82 */     if (paramString == null || paramString.length() == 0) {
/*  83 */       return new HashSet<>();
/*     */     }
/*     */     
/*  86 */     Set<String> set = decomposeImpl(paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     if (set.contains("SHA1") && !set.contains("SHA-1")) {
/*  97 */       set.add("SHA-1");
/*     */     }
/*  99 */     if (set.contains("SHA-1") && !set.contains("SHA1")) {
/* 100 */       set.add("SHA1");
/*     */     }
/*     */ 
/*     */     
/* 104 */     if (set.contains("SHA224") && !set.contains("SHA-224")) {
/* 105 */       set.add("SHA-224");
/*     */     }
/* 107 */     if (set.contains("SHA-224") && !set.contains("SHA224")) {
/* 108 */       set.add("SHA224");
/*     */     }
/*     */ 
/*     */     
/* 112 */     if (set.contains("SHA256") && !set.contains("SHA-256")) {
/* 113 */       set.add("SHA-256");
/*     */     }
/* 115 */     if (set.contains("SHA-256") && !set.contains("SHA256")) {
/* 116 */       set.add("SHA256");
/*     */     }
/*     */ 
/*     */     
/* 120 */     if (set.contains("SHA384") && !set.contains("SHA-384")) {
/* 121 */       set.add("SHA-384");
/*     */     }
/* 123 */     if (set.contains("SHA-384") && !set.contains("SHA384")) {
/* 124 */       set.add("SHA384");
/*     */     }
/*     */ 
/*     */     
/* 128 */     if (set.contains("SHA512") && !set.contains("SHA-512")) {
/* 129 */       set.add("SHA-512");
/*     */     }
/* 131 */     if (set.contains("SHA-512") && !set.contains("SHA512")) {
/* 132 */       set.add("SHA512");
/*     */     }
/*     */     
/* 135 */     return set;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Collection<String> getAliases(String paramString) {
/*     */     String[] arrayOfString;
/* 145 */     if (paramString.equalsIgnoreCase("DH") || paramString
/* 146 */       .equalsIgnoreCase("DiffieHellman")) {
/* 147 */       arrayOfString = new String[] { "DH", "DiffieHellman" };
/*     */     } else {
/* 149 */       arrayOfString = new String[] { paramString };
/*     */     } 
/*     */     
/* 152 */     return Arrays.asList(arrayOfString);
/*     */   }
/*     */   
/*     */   private static void hasLoop(Set<String> paramSet, String paramString1, String paramString2) {
/* 156 */     if (paramSet.contains(paramString1)) {
/* 157 */       if (!paramSet.contains(paramString2)) {
/* 158 */         paramSet.add(paramString2);
/*     */       }
/* 160 */       paramSet.remove(paramString1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<String> decomposeOneHash(String paramString) {
/* 169 */     if (paramString == null || paramString.length() == 0) {
/* 170 */       return new HashSet<>();
/*     */     }
/*     */     
/* 173 */     Set<String> set = decomposeImpl(paramString);
/*     */     
/* 175 */     hasLoop(set, "SHA-1", "SHA1");
/* 176 */     hasLoop(set, "SHA-224", "SHA224");
/* 177 */     hasLoop(set, "SHA-256", "SHA256");
/* 178 */     hasLoop(set, "SHA-384", "SHA384");
/* 179 */     hasLoop(set, "SHA-512", "SHA512");
/*     */     
/* 181 */     return set;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String hashName(String paramString) {
/* 189 */     return paramString.replace("-", "");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/AlgorithmDecomposer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */