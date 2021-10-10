/*    */ package com.sun.jndi.url.iiop;
/*    */ 
/*    */ import com.sun.jndi.cosnaming.CNCtx;
/*    */ import java.util.Hashtable;
/*    */ import javax.naming.Context;
/*    */ import javax.naming.Name;
/*    */ import javax.naming.NamingException;
/*    */ import javax.naming.spi.ObjectFactory;
/*    */ import javax.naming.spi.ResolveResult;
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
/*    */ public class iiopURLContextFactory
/*    */   implements ObjectFactory
/*    */ {
/*    */   public Object getObjectInstance(Object paramObject, Name paramName, Context paramContext, Hashtable<?, ?> paramHashtable) throws Exception {
/* 47 */     if (paramObject == null) {
/* 48 */       return new iiopURLContext(paramHashtable);
/*    */     }
/* 50 */     if (paramObject instanceof String)
/* 51 */       return getUsingURL((String)paramObject, paramHashtable); 
/* 52 */     if (paramObject instanceof String[]) {
/* 53 */       return getUsingURLs((String[])paramObject, paramHashtable);
/*    */     }
/* 55 */     throw new IllegalArgumentException("iiopURLContextFactory.getObjectInstance: argument must be a URL String or array of URLs");
/*    */   }
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
/*    */   static ResolveResult getUsingURLIgnoreRest(String paramString, Hashtable<?, ?> paramHashtable) throws NamingException {
/* 73 */     return CNCtx.createUsingURL(paramString, paramHashtable);
/*    */   }
/*    */ 
/*    */   
/*    */   private static Object getUsingURL(String paramString, Hashtable<?, ?> paramHashtable) throws NamingException {
/* 78 */     ResolveResult resolveResult = getUsingURLIgnoreRest(paramString, paramHashtable);
/*    */     
/* 80 */     Context context = (Context)resolveResult.getResolvedObj();
/*    */     try {
/* 82 */       return context.lookup(resolveResult.getRemainingName());
/*    */     } finally {
/* 84 */       context.close();
/*    */     } 
/*    */   }
/*    */   
/*    */   private static Object getUsingURLs(String[] paramArrayOfString, Hashtable<?, ?> paramHashtable) {
/* 89 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 90 */       String str = paramArrayOfString[b];
/*    */       try {
/* 92 */         Object object = getUsingURL(str, paramHashtable);
/* 93 */         if (object != null) {
/* 94 */           return object;
/*    */         }
/* 96 */       } catch (NamingException namingException) {}
/*    */     } 
/*    */     
/* 99 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/url/iiop/iiopURLContextFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */