/*    */ package com.sun.jndi.url.iiop;
/*    */ 
/*    */ import com.sun.jndi.cosnaming.CorbanameUrl;
/*    */ import com.sun.jndi.cosnaming.IiopUrl;
/*    */ import com.sun.jndi.toolkit.url.GenericURLContext;
/*    */ import java.net.MalformedURLException;
/*    */ import java.util.Hashtable;
/*    */ import javax.naming.InvalidNameException;
/*    */ import javax.naming.Name;
/*    */ import javax.naming.NamingException;
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
/*    */ public class iiopURLContext
/*    */   extends GenericURLContext
/*    */ {
/*    */   iiopURLContext(Hashtable<?, ?> paramHashtable) {
/* 46 */     super(paramHashtable);
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
/*    */   protected ResolveResult getRootURLContext(String paramString, Hashtable<?, ?> paramHashtable) throws NamingException {
/* 61 */     return iiopURLContextFactory.getUsingURLIgnoreRest(paramString, paramHashtable);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Name getURLSuffix(String paramString1, String paramString2) throws NamingException {
/*    */     try {
/* 71 */       if (paramString2.startsWith("iiop://") || paramString2.startsWith("iiopname://")) {
/* 72 */         IiopUrl iiopUrl = new IiopUrl(paramString2);
/* 73 */         return iiopUrl.getCosName();
/* 74 */       }  if (paramString2.startsWith("corbaname:")) {
/* 75 */         CorbanameUrl corbanameUrl = new CorbanameUrl(paramString2);
/* 76 */         return corbanameUrl.getCosName();
/*    */       } 
/* 78 */       throw new MalformedURLException("Not a valid URL: " + paramString2);
/*    */     }
/* 80 */     catch (MalformedURLException malformedURLException) {
/* 81 */       throw new InvalidNameException(malformedURLException.getMessage());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/url/iiop/iiopURLContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */