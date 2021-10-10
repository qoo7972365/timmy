/*     */ package sun.security.util;
/*     */ 
/*     */ import java.security.InvalidParameterException;
/*     */ import java.util.regex.PatternSyntaxException;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SecurityProviderConstants
/*     */ {
/*  38 */   private static final Debug debug = Debug.getInstance("jca", "ProviderConfig"); public static final int DEF_DSA_KEY_SIZE;
/*     */   public static final int DEF_RSA_KEY_SIZE;
/*     */   public static final int DEF_DH_KEY_SIZE;
/*     */   public static final int DEF_EC_KEY_SIZE;
/*     */   private static final String KEY_LENGTH_PROP = "jdk.security.defaultKeySize";
/*     */   
/*     */   public static final int getDefDSASubprimeSize(int paramInt) {
/*  45 */     if (paramInt <= 1024)
/*  46 */       return 160; 
/*  47 */     if (paramInt == 2048)
/*  48 */       return 224; 
/*  49 */     if (paramInt == 3072) {
/*  50 */       return 256;
/*     */     }
/*  52 */     throw new InvalidParameterException("Invalid DSA Prime Size: " + paramInt);
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
/*     */   static {
/*  66 */     String str = GetPropertyAction.privilegedGetProperty("jdk.security.defaultKeySize");
/*  67 */     int i = 2048;
/*  68 */     int j = 2048;
/*  69 */     int k = 2048;
/*  70 */     int m = 256;
/*     */     
/*  72 */     if (str != null) {
/*     */       try {
/*  74 */         String[] arrayOfString1 = str.split(",");
/*  75 */         String[] arrayOfString2 = arrayOfString1; int n = arrayOfString2.length; byte b = 0; while (true) { if (b < n) { String str1 = arrayOfString2[b];
/*  76 */             String[] arrayOfString = str1.split(":");
/*  77 */             if (arrayOfString.length != 2) {
/*     */               
/*  79 */               if (debug != null) {
/*  80 */                 debug.println("Ignoring invalid pair in jdk.security.defaultKeySize property: " + str1);
/*     */               }
/*     */             }
/*     */             else {
/*     */               
/*  85 */               String str2 = arrayOfString[0].trim().toUpperCase();
/*  86 */               int i1 = -1;
/*     */               try {
/*  88 */                 i1 = Integer.parseInt(arrayOfString[1].trim());
/*  89 */               } catch (NumberFormatException numberFormatException) {
/*     */                 
/*  91 */                 if (debug != null) {
/*  92 */                   debug.println("Ignoring invalid value in jdk.security.defaultKeySize property: " + str1);
/*     */                 }
/*     */               } 
/*     */ 
/*     */               
/*  97 */               if (str2.equals("DSA")) {
/*  98 */                 i = i1;
/*  99 */               } else if (str2.equals("RSA")) {
/* 100 */                 j = i1;
/* 101 */               } else if (str2.equals("DH")) {
/* 102 */                 k = i1;
/* 103 */               } else if (str2.equals("EC")) {
/* 104 */                 m = i1;
/*     */               } else {
/* 106 */                 if (debug != null) {
/* 107 */                   debug.println("Ignoring unsupported algo in jdk.security.defaultKeySize property: " + str1);
/*     */                 }
/*     */                 
/*     */                 b++;
/*     */               } 
/* 112 */               if (debug != null)
/* 113 */                 debug.println("Overriding default " + str2 + " keysize with value from " + "jdk.security.defaultKeySize" + " property: " + i1); 
/*     */             }  }
/*     */           else { break; }
/*     */            b++; }
/*     */       
/* 118 */       } catch (PatternSyntaxException patternSyntaxException) {
/*     */         
/* 120 */         if (debug != null) {
/* 121 */           debug.println("Unexpected exception while parsing jdk.security.defaultKeySize property: " + patternSyntaxException);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 126 */     DEF_DSA_KEY_SIZE = i;
/* 127 */     DEF_RSA_KEY_SIZE = j;
/* 128 */     DEF_DH_KEY_SIZE = k;
/* 129 */     DEF_EC_KEY_SIZE = m;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/SecurityProviderConstants.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */