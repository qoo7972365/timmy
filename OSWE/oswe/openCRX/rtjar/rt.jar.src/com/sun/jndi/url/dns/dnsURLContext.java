/*    */ package com.sun.jndi.url.dns;
/*    */ 
/*    */ import com.sun.jndi.dns.DnsContextFactory;
/*    */ import com.sun.jndi.dns.DnsUrl;
/*    */ import com.sun.jndi.toolkit.url.GenericURLDirContext;
/*    */ import java.net.MalformedURLException;
/*    */ import java.util.Hashtable;
/*    */ import javax.naming.CompositeName;
/*    */ import javax.naming.InvalidNameException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class dnsURLContext
/*    */   extends GenericURLDirContext
/*    */ {
/*    */   public dnsURLContext(Hashtable<?, ?> paramHashtable) {
/* 49 */     super(paramHashtable);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResolveResult getRootURLContext(String paramString, Hashtable<?, ?> paramHashtable) throws NamingException {
/*    */     DnsUrl dnsUrl;
/*    */     try {
/* 62 */       dnsUrl = new DnsUrl(paramString);
/* 63 */     } catch (MalformedURLException malformedURLException) {
/* 64 */       throw new InvalidNameException(malformedURLException.getMessage());
/*    */     } 
/*    */     
/* 67 */     DnsUrl[] arrayOfDnsUrl = { dnsUrl };
/* 68 */     String str = dnsUrl.getDomain();
/*    */     
/* 70 */     return new ResolveResult(
/* 71 */         DnsContextFactory.getContext(".", arrayOfDnsUrl, paramHashtable), (new CompositeName())
/* 72 */         .add(str));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/url/dns/dnsURLContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */