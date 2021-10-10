/*    */ package com.sun.corba.se.impl.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class PackagePrefixChecker
/*    */ {
/*    */   private static final String PACKAGE_PREFIX = "org.omg.stub.";
/*    */   
/*    */   public static String packagePrefix() {
/* 39 */     return "org.omg.stub.";
/*    */   }
/*    */   public static String correctPackageName(String paramString) {
/* 42 */     if (paramString == null) return paramString; 
/* 43 */     if (hasOffendingPrefix(paramString))
/*    */     {
/* 45 */       return "org.omg.stub." + paramString;
/*    */     }
/* 47 */     return paramString;
/*    */   }
/*    */   
/*    */   public static boolean isOffendingPackage(String paramString) {
/* 51 */     return (paramString != null && 
/*    */ 
/*    */       
/* 54 */       hasOffendingPrefix(paramString));
/*    */   }
/*    */   
/*    */   public static boolean hasOffendingPrefix(String paramString) {
/* 58 */     return (paramString
/* 59 */       .startsWith("java.") || paramString.equals("java") || paramString
/*    */       
/* 61 */       .startsWith("net.jini.") || paramString.equals("net.jini") || paramString
/* 62 */       .startsWith("jini.") || paramString.equals("jini") || paramString
/* 63 */       .startsWith("javax.") || paramString.equals("javax"));
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean hasBeenPrefixed(String paramString) {
/* 68 */     return paramString.startsWith(packagePrefix());
/*    */   }
/*    */   
/*    */   public static String withoutPackagePrefix(String paramString) {
/* 72 */     if (hasBeenPrefixed(paramString)) return paramString.substring(packagePrefix().length()); 
/* 73 */     return paramString;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/util/PackagePrefixChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */