/*     */ package com.sun.jndi.dns;
/*     */ 
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.Date;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ZoneNode
/*     */   extends NameNode
/*     */ {
/*  58 */   private SoftReference<NameNode> contentsRef = null;
/*  59 */   private long serialNumber = -1L;
/*  60 */   private Date expiration = null;
/*     */   
/*     */   ZoneNode(String paramString) {
/*  63 */     super(paramString);
/*     */   }
/*     */   
/*     */   protected NameNode newNameNode(String paramString) {
/*  67 */     return new ZoneNode(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void depopulate() {
/*  75 */     this.contentsRef = null;
/*  76 */     this.serialNumber = -1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized boolean isPopulated() {
/*  83 */     return (getContents() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized NameNode getContents() {
/*  90 */     return (this.contentsRef != null) ? this.contentsRef
/*  91 */       .get() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized boolean isExpired() {
/*  99 */     return (this.expiration != null && this.expiration.before(new Date()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ZoneNode getDeepestPopulated(DnsName paramDnsName) {
/* 109 */     ZoneNode zoneNode1 = this;
/* 110 */     ZoneNode zoneNode2 = isPopulated() ? this : null;
/* 111 */     for (byte b = 1; b < paramDnsName.size(); b++) {
/* 112 */       zoneNode1 = (ZoneNode)zoneNode1.get(paramDnsName.getKey(b));
/* 113 */       if (zoneNode1 == null)
/*     */         break; 
/* 115 */       if (zoneNode1.isPopulated()) {
/* 116 */         zoneNode2 = zoneNode1;
/*     */       }
/*     */     } 
/* 119 */     return zoneNode2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   NameNode populate(DnsName paramDnsName, ResourceRecords paramResourceRecords) {
/* 130 */     NameNode nameNode = new NameNode(null);
/*     */     
/* 132 */     for (byte b = 0; b < paramResourceRecords.answer.size(); b++) {
/* 133 */       ResourceRecord resourceRecord1 = paramResourceRecords.answer.elementAt(b);
/* 134 */       DnsName dnsName = resourceRecord1.getName();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 139 */       if (dnsName.size() > paramDnsName.size() && dnsName.startsWith(paramDnsName)) {
/* 140 */         NameNode nameNode1 = nameNode.add(dnsName, paramDnsName.size());
/* 141 */         if (resourceRecord1.getType() == 2) {
/* 142 */           nameNode1.setZoneCut(true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 147 */     ResourceRecord resourceRecord = paramResourceRecords.answer.firstElement();
/* 148 */     synchronized (this) {
/* 149 */       this.contentsRef = new SoftReference<>(nameNode);
/* 150 */       this.serialNumber = getSerialNumber(resourceRecord);
/* 151 */       setExpiration(getMinimumTtl(resourceRecord));
/* 152 */       return nameNode;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setExpiration(long paramLong) {
/* 160 */     this.expiration = new Date(System.currentTimeMillis() + 1000L * paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long getMinimumTtl(ResourceRecord paramResourceRecord) {
/* 168 */     String str = (String)paramResourceRecord.getRdata();
/* 169 */     int i = str.lastIndexOf(' ') + 1;
/* 170 */     return Long.parseLong(str.substring(i));
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
/*     */   int compareSerialNumberTo(ResourceRecord paramResourceRecord) {
/* 184 */     return ResourceRecord.compareSerialNumbers(this.serialNumber, 
/* 185 */         getSerialNumber(paramResourceRecord));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long getSerialNumber(ResourceRecord paramResourceRecord) {
/* 192 */     String str = (String)paramResourceRecord.getRdata();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 197 */     int i = str.length();
/* 198 */     int j = -1;
/* 199 */     for (byte b = 0; b < 5; b++) {
/* 200 */       j = i;
/* 201 */       i = str.lastIndexOf(' ', j - 1);
/*     */     } 
/* 203 */     return Long.parseLong(str.substring(i + 1, j));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/dns/ZoneNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */