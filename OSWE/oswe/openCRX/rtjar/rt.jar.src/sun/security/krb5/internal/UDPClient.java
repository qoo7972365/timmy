/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.SocketException;
/*     */ import java.net.UnknownHostException;
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
/*     */ class UDPClient
/*     */   extends NetClient
/*     */ {
/*     */   InetAddress iaddr;
/*     */   int iport;
/* 181 */   int bufSize = 65507;
/*     */   
/*     */   DatagramSocket dgSocket;
/*     */   DatagramPacket dgPacketIn;
/*     */   
/*     */   UDPClient(String paramString, int paramInt1, int paramInt2) throws UnknownHostException, SocketException {
/* 187 */     this.iaddr = InetAddress.getByName(paramString);
/* 188 */     this.iport = paramInt1;
/* 189 */     this.dgSocket = new DatagramSocket();
/* 190 */     this.dgSocket.setSoTimeout(paramInt2);
/* 191 */     this.dgSocket.connect(this.iaddr, this.iport);
/*     */   }
/*     */ 
/*     */   
/*     */   public void send(byte[] paramArrayOfbyte) throws IOException {
/* 196 */     DatagramPacket datagramPacket = new DatagramPacket(paramArrayOfbyte, paramArrayOfbyte.length, this.iaddr, this.iport);
/*     */     
/* 198 */     this.dgSocket.send(datagramPacket);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] receive() throws IOException {
/* 203 */     byte[] arrayOfByte1 = new byte[this.bufSize];
/* 204 */     this.dgPacketIn = new DatagramPacket(arrayOfByte1, arrayOfByte1.length);
/*     */     try {
/* 206 */       this.dgSocket.receive(this.dgPacketIn);
/*     */     }
/* 208 */     catch (SocketException socketException) {
/* 209 */       if (socketException instanceof java.net.PortUnreachableException) {
/* 210 */         throw socketException;
/*     */       }
/* 212 */       this.dgSocket.receive(this.dgPacketIn);
/*     */     } 
/* 214 */     byte[] arrayOfByte2 = new byte[this.dgPacketIn.getLength()];
/* 215 */     System.arraycopy(this.dgPacketIn.getData(), 0, arrayOfByte2, 0, this.dgPacketIn
/* 216 */         .getLength());
/* 217 */     return arrayOfByte2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 222 */     this.dgSocket.close();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/UDPClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */