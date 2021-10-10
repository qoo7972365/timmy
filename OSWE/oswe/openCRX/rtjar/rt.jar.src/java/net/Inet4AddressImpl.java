/*    */ package java.net;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Enumeration;
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
/*    */ class Inet4AddressImpl
/*    */   implements InetAddressImpl
/*    */ {
/*    */   private InetAddress anyLocalAddress;
/*    */   private InetAddress loopbackAddress;
/*    */   
/*    */   public native String getLocalHostName() throws UnknownHostException;
/*    */   
/*    */   public native InetAddress[] lookupAllHostAddr(String paramString) throws UnknownHostException;
/*    */   
/*    */   public native String getHostByAddr(byte[] paramArrayOfbyte) throws UnknownHostException;
/*    */   
/*    */   private native boolean isReachable0(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2) throws IOException;
/*    */   
/*    */   public synchronized InetAddress anyLocalAddress() {
/* 41 */     if (this.anyLocalAddress == null) {
/* 42 */       this.anyLocalAddress = new Inet4Address();
/* 43 */       (this.anyLocalAddress.holder()).hostName = "0.0.0.0";
/*    */     } 
/* 45 */     return this.anyLocalAddress;
/*    */   }
/*    */   
/*    */   public synchronized InetAddress loopbackAddress() {
/* 49 */     if (this.loopbackAddress == null) {
/* 50 */       byte[] arrayOfByte = { Byte.MAX_VALUE, 0, 0, 1 };
/* 51 */       this.loopbackAddress = new Inet4Address("localhost", arrayOfByte);
/*    */     } 
/* 53 */     return this.loopbackAddress;
/*    */   }
/*    */   
/*    */   public boolean isReachable(InetAddress paramInetAddress, int paramInt1, NetworkInterface paramNetworkInterface, int paramInt2) throws IOException {
/* 57 */     byte[] arrayOfByte = null;
/* 58 */     if (paramNetworkInterface != null) {
/*    */ 
/*    */ 
/*    */       
/* 62 */       Enumeration<InetAddress> enumeration = paramNetworkInterface.getInetAddresses();
/* 63 */       InetAddress inetAddress = null;
/* 64 */       while (!(inetAddress instanceof Inet4Address) && enumeration
/* 65 */         .hasMoreElements())
/* 66 */         inetAddress = enumeration.nextElement(); 
/* 67 */       if (inetAddress instanceof Inet4Address)
/* 68 */         arrayOfByte = inetAddress.getAddress(); 
/*    */     } 
/* 70 */     return isReachable0(paramInetAddress.getAddress(), paramInt1, arrayOfByte, paramInt2);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/Inet4AddressImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */