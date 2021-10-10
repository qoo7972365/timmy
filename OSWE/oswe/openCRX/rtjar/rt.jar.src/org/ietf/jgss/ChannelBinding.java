/*     */ package org.ietf.jgss;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import java.util.Arrays;
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
/*     */ public class ChannelBinding
/*     */ {
/*     */   private InetAddress initiator;
/*     */   private InetAddress acceptor;
/*     */   private byte[] appData;
/*     */   
/*     */   public ChannelBinding(InetAddress paramInetAddress1, InetAddress paramInetAddress2, byte[] paramArrayOfbyte) {
/* 104 */     this.initiator = paramInetAddress1;
/* 105 */     this.acceptor = paramInetAddress2;
/*     */     
/* 107 */     if (paramArrayOfbyte != null) {
/* 108 */       this.appData = new byte[paramArrayOfbyte.length];
/* 109 */       System.arraycopy(paramArrayOfbyte, 0, this.appData, 0, paramArrayOfbyte.length);
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
/*     */   public ChannelBinding(byte[] paramArrayOfbyte) {
/* 121 */     this(null, null, paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InetAddress getInitiatorAddress() {
/* 131 */     return this.initiator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InetAddress getAcceptorAddress() {
/* 141 */     return this.acceptor;
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
/*     */   public byte[] getApplicationData() {
/* 153 */     if (this.appData == null) {
/* 154 */       return null;
/*     */     }
/*     */     
/* 157 */     byte[] arrayOfByte = new byte[this.appData.length];
/* 158 */     System.arraycopy(this.appData, 0, arrayOfByte, 0, this.appData.length);
/* 159 */     return arrayOfByte;
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
/*     */   public boolean equals(Object paramObject) {
/* 172 */     if (this == paramObject) {
/* 173 */       return true;
/*     */     }
/* 175 */     if (!(paramObject instanceof ChannelBinding)) {
/* 176 */       return false;
/*     */     }
/* 178 */     ChannelBinding channelBinding = (ChannelBinding)paramObject;
/*     */     
/* 180 */     if ((this.initiator != null && channelBinding.initiator == null) || (this.initiator == null && channelBinding.initiator != null))
/*     */     {
/* 182 */       return false;
/*     */     }
/* 184 */     if (this.initiator != null && !this.initiator.equals(channelBinding.initiator)) {
/* 185 */       return false;
/*     */     }
/* 187 */     if ((this.acceptor != null && channelBinding.acceptor == null) || (this.acceptor == null && channelBinding.acceptor != null))
/*     */     {
/* 189 */       return false;
/*     */     }
/* 191 */     if (this.acceptor != null && !this.acceptor.equals(channelBinding.acceptor)) {
/* 192 */       return false;
/*     */     }
/* 194 */     return Arrays.equals(this.appData, channelBinding.appData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 203 */     if (this.initiator != null)
/* 204 */       return this.initiator.hashCode(); 
/* 205 */     if (this.acceptor != null)
/* 206 */       return this.acceptor.hashCode(); 
/* 207 */     if (this.appData != null) {
/* 208 */       return (new String(this.appData)).hashCode();
/*     */     }
/* 210 */     return 1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/ietf/jgss/ChannelBinding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */