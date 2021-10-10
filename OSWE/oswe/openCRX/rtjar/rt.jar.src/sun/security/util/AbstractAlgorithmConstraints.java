/*     */ package sun.security.util;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.AlgorithmConstraints;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.Security;
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
/*     */ public abstract class AbstractAlgorithmConstraints
/*     */   implements AlgorithmConstraints
/*     */ {
/*     */   protected final AlgorithmDecomposer decomposer;
/*     */   
/*     */   protected AbstractAlgorithmConstraints(AlgorithmDecomposer paramAlgorithmDecomposer) {
/*  43 */     this.decomposer = paramAlgorithmDecomposer;
/*     */   }
/*     */ 
/*     */   
/*     */   static String[] getAlgorithms(final String propertyName) {
/*  48 */     String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public String run()
/*     */           {
/*  52 */             return Security.getProperty(propertyName);
/*     */           }
/*     */         });
/*     */     
/*  56 */     String[] arrayOfString = null;
/*  57 */     if (str != null && !str.isEmpty()) {
/*     */       
/*  59 */       if (str.length() >= 2 && str.charAt(0) == '"' && str
/*  60 */         .charAt(str.length() - 1) == '"') {
/*  61 */         str = str.substring(1, str.length() - 1);
/*     */       }
/*  63 */       arrayOfString = str.split(",");
/*  64 */       for (byte b = 0; b < arrayOfString.length; b++) {
/*  65 */         arrayOfString[b] = arrayOfString[b].trim();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  70 */     if (arrayOfString == null) {
/*  71 */       arrayOfString = new String[0];
/*     */     }
/*  73 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean checkAlgorithm(String[] paramArrayOfString, String paramString, AlgorithmDecomposer paramAlgorithmDecomposer) {
/*  78 */     if (paramString == null || paramString.length() == 0) {
/*  79 */       throw new IllegalArgumentException("No algorithm name specified");
/*     */     }
/*     */     
/*  82 */     Set<String> set = null;
/*  83 */     for (String str : paramArrayOfString) {
/*  84 */       if (str != null && !str.isEmpty()) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  89 */         if (str.equalsIgnoreCase(paramString)) {
/*  90 */           return false;
/*     */         }
/*     */ 
/*     */         
/*  94 */         if (set == null) {
/*  95 */           set = paramAlgorithmDecomposer.decompose(paramString);
/*     */         }
/*     */ 
/*     */         
/*  99 */         for (String str1 : set) {
/* 100 */           if (str.equalsIgnoreCase(str1)) {
/* 101 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 106 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/AbstractAlgorithmConstraints.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */