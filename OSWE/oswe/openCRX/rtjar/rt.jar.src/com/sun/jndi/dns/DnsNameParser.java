/*    */ package com.sun.jndi.dns;
/*    */ 
/*    */ import javax.naming.Name;
/*    */ import javax.naming.NameParser;
/*    */ import javax.naming.NamingException;
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
/*    */ class DnsNameParser
/*    */   implements NameParser
/*    */ {
/*    */   public Name parse(String paramString) throws NamingException {
/* 42 */     return new DnsName(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 49 */     return paramObject instanceof DnsNameParser;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 53 */     return DnsNameParser.class.hashCode() + 1;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/dns/DnsNameParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */