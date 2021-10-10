/*     */ package com.sun.jndi.dns;
/*     */ 
/*     */ import javax.naming.CommunicationException;
/*     */ import javax.naming.NameNotFoundException;
/*     */ import javax.naming.NamingException;
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
/*     */ class Resolver
/*     */ {
/*     */   private DnsClient dnsClient;
/*     */   private int timeout;
/*     */   private int retries;
/*     */   
/*     */   Resolver(String[] paramArrayOfString, int paramInt1, int paramInt2) throws NamingException {
/*  59 */     this.timeout = paramInt1;
/*  60 */     this.retries = paramInt2;
/*  61 */     this.dnsClient = new DnsClient(paramArrayOfString, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void close() {
/*  65 */     this.dnsClient.close();
/*  66 */     this.dnsClient = null;
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
/*     */   
/*     */   ResourceRecords query(DnsName paramDnsName, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2) throws NamingException {
/*  81 */     return this.dnsClient.query(paramDnsName, paramInt1, paramInt2, paramBoolean1, paramBoolean2);
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
/*     */   ResourceRecords queryZone(DnsName paramDnsName, int paramInt, boolean paramBoolean) throws NamingException {
/*  93 */     DnsClient dnsClient = new DnsClient(findNameServers(paramDnsName, paramBoolean), this.timeout, this.retries);
/*     */     try {
/*  95 */       return dnsClient.queryZone(paramDnsName, paramInt, paramBoolean);
/*     */     } finally {
/*  97 */       dnsClient.close();
/*     */     } 
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
/*     */   DnsName findZoneName(DnsName paramDnsName, int paramInt, boolean paramBoolean) throws NamingException {
/* 111 */     paramDnsName = (DnsName)paramDnsName.clone();
/* 112 */     while (paramDnsName.size() > 1) {
/* 113 */       ResourceRecords resourceRecords = null;
/*     */       try {
/* 115 */         resourceRecords = query(paramDnsName, paramInt, 6, paramBoolean, false);
/*     */       }
/* 117 */       catch (NameNotFoundException nameNotFoundException) {
/* 118 */         throw nameNotFoundException;
/* 119 */       } catch (NamingException namingException) {}
/*     */ 
/*     */       
/* 122 */       if (resourceRecords != null) {
/* 123 */         if (resourceRecords.answer.size() > 0) {
/* 124 */           return paramDnsName;
/*     */         }
/*     */         
/* 127 */         for (byte b = 0; b < resourceRecords.authority.size(); b++) {
/* 128 */           ResourceRecord resourceRecord = resourceRecords.authority.elementAt(b);
/* 129 */           if (resourceRecord.getType() == 6) {
/* 130 */             DnsName dnsName = resourceRecord.getName();
/* 131 */             if (paramDnsName.endsWith(dnsName)) {
/* 132 */               return dnsName;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/* 137 */       paramDnsName.remove(paramDnsName.size() - 1);
/*     */     } 
/* 139 */     return paramDnsName;
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
/*     */   ResourceRecord findSoa(DnsName paramDnsName, int paramInt, boolean paramBoolean) throws NamingException {
/* 151 */     ResourceRecords resourceRecords = query(paramDnsName, paramInt, 6, paramBoolean, false);
/*     */     
/* 153 */     for (byte b = 0; b < resourceRecords.answer.size(); b++) {
/* 154 */       ResourceRecord resourceRecord = resourceRecords.answer.elementAt(b);
/* 155 */       if (resourceRecord.getType() == 6) {
/* 156 */         return resourceRecord;
/*     */       }
/*     */     } 
/* 159 */     return null;
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
/*     */   private String[] findNameServers(DnsName paramDnsName, boolean paramBoolean) throws NamingException {
/* 173 */     ResourceRecords resourceRecords = query(paramDnsName, 1, 2, paramBoolean, false);
/*     */     
/* 175 */     String[] arrayOfString = new String[resourceRecords.answer.size()];
/* 176 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 177 */       ResourceRecord resourceRecord = resourceRecords.answer.elementAt(b);
/* 178 */       if (resourceRecord.getType() != 2) {
/* 179 */         throw new CommunicationException("Corrupted DNS message");
/*     */       }
/* 181 */       arrayOfString[b] = (String)resourceRecord.getRdata();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 186 */       arrayOfString[b] = arrayOfString[b].substring(0, arrayOfString[b].length() - 1);
/*     */     } 
/* 188 */     return arrayOfString;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/dns/Resolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */