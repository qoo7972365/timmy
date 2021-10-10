/*     */ package java.net;
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
/*     */ public class InterfaceAddress
/*     */ {
/*  38 */   private InetAddress address = null;
/*  39 */   private Inet4Address broadcast = null;
/*  40 */   private short maskLength = 0;
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
/*     */   public InetAddress getAddress() {
/*  55 */     return this.address;
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
/*     */   public InetAddress getBroadcast() {
/*  69 */     return this.broadcast;
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
/*     */   public short getNetworkPrefixLength() {
/*  83 */     return this.maskLength;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 102 */     if (!(paramObject instanceof InterfaceAddress)) {
/* 103 */       return false;
/*     */     }
/* 105 */     InterfaceAddress interfaceAddress = (InterfaceAddress)paramObject;
/* 106 */     if ((this.address == null) ? (interfaceAddress.address == null) : this.address.equals(interfaceAddress.address)) {
/*     */       
/* 108 */       if ((this.broadcast == null) ? (interfaceAddress.broadcast == null) : this.broadcast.equals(interfaceAddress.broadcast)) {
/*     */         
/* 110 */         if (this.maskLength != interfaceAddress.maskLength)
/* 111 */           return false; 
/* 112 */         return true;
/*     */       } 
/*     */       return false;
/*     */     } 
/*     */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 121 */     return this.address.hashCode() + ((this.broadcast != null) ? this.broadcast.hashCode() : 0) + this.maskLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 131 */     return this.address + "/" + this.maskLength + " [" + this.broadcast + "]";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/InterfaceAddress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */