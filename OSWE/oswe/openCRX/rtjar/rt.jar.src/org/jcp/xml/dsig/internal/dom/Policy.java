/*     */ package org.jcp.xml.dsig.internal.dom;
/*     */ 
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.security.AccessController;
/*     */ import java.security.Security;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Locale;
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
/*     */ public final class Policy
/*     */ {
/*  47 */   private static Set<URI> disallowedAlgs = new HashSet<>();
/*  48 */   private static int maxTrans = Integer.MAX_VALUE;
/*  49 */   private static int maxRefs = Integer.MAX_VALUE;
/*  50 */   private static Set<String> disallowedRefUriSchemes = new HashSet<>();
/*  51 */   private static Map<String, Integer> minKeyMap = new HashMap<>();
/*     */   private static boolean noDuplicateIds = false;
/*     */   private static boolean noRMLoops = false;
/*     */   
/*     */   static {
/*     */     try {
/*  57 */       initialize();
/*  58 */     } catch (Exception exception) {
/*  59 */       throw new SecurityException("Cannot initialize the secure validation policy", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initialize() {
/*  68 */     String str = AccessController.<String>doPrivileged(() -> Security.getProperty("jdk.xml.dsig.secureValidationPolicy"));
/*     */     
/*  70 */     if (str == null || str.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/*  74 */     String[] arrayOfString = str.split(",");
/*  75 */     for (String str1 : arrayOfString) {
/*  76 */       byte b; String[] arrayOfString1 = str1.split("\\s");
/*  77 */       String str2 = arrayOfString1[0];
/*  78 */       switch (str2) {
/*     */         case "disallowAlg":
/*  80 */           if (arrayOfString1.length != 2) {
/*  81 */             error(str1);
/*     */           }
/*  83 */           disallowedAlgs.add(URI.create(arrayOfString1[1]));
/*     */           break;
/*     */         case "maxTransforms":
/*  86 */           if (arrayOfString1.length != 2) {
/*  87 */             error(str1);
/*     */           }
/*  89 */           maxTrans = Integer.parseUnsignedInt(arrayOfString1[1]);
/*     */           break;
/*     */         case "maxReferences":
/*  92 */           if (arrayOfString1.length != 2) {
/*  93 */             error(str1);
/*     */           }
/*  95 */           maxRefs = Integer.parseUnsignedInt(arrayOfString1[1]);
/*     */           break;
/*     */         case "disallowReferenceUriSchemes":
/*  98 */           if (arrayOfString1.length == 1) {
/*  99 */             error(str1);
/*     */           }
/* 101 */           for (b = 1; b < arrayOfString1.length; b++) {
/* 102 */             String str3 = arrayOfString1[b];
/* 103 */             disallowedRefUriSchemes.add(str3
/* 104 */                 .toLowerCase(Locale.ROOT));
/*     */           } 
/*     */           break;
/*     */         case "minKeySize":
/* 108 */           if (arrayOfString1.length != 3) {
/* 109 */             error(str1);
/*     */           }
/* 111 */           minKeyMap.put(arrayOfString1[1], 
/* 112 */               Integer.valueOf(Integer.parseUnsignedInt(arrayOfString1[2])));
/*     */           break;
/*     */         case "noDuplicateIds":
/* 115 */           if (arrayOfString1.length != 1) {
/* 116 */             error(str1);
/*     */           }
/* 118 */           noDuplicateIds = true;
/*     */           break;
/*     */         case "noRetrievalMethodLoops":
/* 121 */           if (arrayOfString1.length != 1) {
/* 122 */             error(str1);
/*     */           }
/* 124 */           noRMLoops = true;
/*     */           break;
/*     */         default:
/* 127 */           error(str1);
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public static boolean restrictAlg(String paramString) {
/*     */     try {
/* 134 */       URI uRI = new URI(paramString);
/* 135 */       return disallowedAlgs.contains(uRI);
/* 136 */     } catch (URISyntaxException uRISyntaxException) {
/* 137 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean restrictNumTransforms(int paramInt) {
/* 142 */     return (paramInt > maxTrans);
/*     */   }
/*     */   
/*     */   public static boolean restrictNumReferences(int paramInt) {
/* 146 */     return (paramInt > maxRefs);
/*     */   }
/*     */   
/*     */   public static boolean restrictReferenceUriScheme(String paramString) {
/* 150 */     if (paramString != null) {
/* 151 */       String str = URI.create(paramString).getScheme();
/* 152 */       if (str != null) {
/* 153 */         return disallowedRefUriSchemes.contains(str
/* 154 */             .toLowerCase(Locale.ROOT));
/*     */       }
/*     */     } 
/* 157 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean restrictKey(String paramString, int paramInt) {
/* 161 */     return (paramInt < ((Integer)minKeyMap.getOrDefault(paramString, (V)Integer.valueOf(0))).intValue());
/*     */   }
/*     */   
/*     */   public static boolean restrictDuplicateIds() {
/* 165 */     return noDuplicateIds;
/*     */   }
/*     */   
/*     */   public static boolean restrictRetrievalMethodLoops() {
/* 169 */     return noRMLoops;
/*     */   }
/*     */   
/*     */   public static Set<URI> disabledAlgs() {
/* 173 */     return Collections.unmodifiableSet(disallowedAlgs);
/*     */   }
/*     */   
/*     */   public static int maxTransforms() {
/* 177 */     return maxTrans;
/*     */   }
/*     */   
/*     */   public static int maxReferences() {
/* 181 */     return maxRefs;
/*     */   }
/*     */   
/*     */   public static Set<String> disabledReferenceUriSchemes() {
/* 185 */     return Collections.unmodifiableSet(disallowedRefUriSchemes);
/*     */   }
/*     */   
/*     */   public static int minKeySize(String paramString) {
/* 189 */     return ((Integer)minKeyMap.getOrDefault(paramString, Integer.valueOf(0))).intValue();
/*     */   }
/*     */   
/*     */   private static void error(String paramString) {
/* 193 */     throw new IllegalArgumentException("Invalid jdk.xml.dsig.secureValidationPolicy entry: " + paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/jcp/xml/dsig/internal/dom/Policy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */