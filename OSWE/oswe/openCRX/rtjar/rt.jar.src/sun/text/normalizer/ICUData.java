/*    */ package sun.text.normalizer;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ import java.util.MissingResourceException;
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
/*    */ public final class ICUData
/*    */ {
/*    */   private static InputStream getStream(final Class<ICUData> root, final String resourceName, boolean paramBoolean) {
/* 52 */     InputStream inputStream = null;
/*    */     
/* 54 */     if (System.getSecurityManager() != null) {
/* 55 */       inputStream = AccessController.<InputStream>doPrivileged(new PrivilegedAction<InputStream>() {
/*    */             public InputStream run() {
/* 57 */               return root.getResourceAsStream(resourceName);
/*    */             }
/*    */           });
/*    */     } else {
/* 61 */       inputStream = root.getResourceAsStream(resourceName);
/*    */     } 
/*    */     
/* 64 */     if (inputStream == null && paramBoolean) {
/* 65 */       throw new MissingResourceException("could not locate data", root.getPackage().getName(), resourceName);
/*    */     }
/* 67 */     return inputStream;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static InputStream getStream(String paramString) {
/* 74 */     return getStream(ICUData.class, paramString, false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static InputStream getRequiredStream(String paramString) {
/* 81 */     return getStream(ICUData.class, paramString, true);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/normalizer/ICUData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */