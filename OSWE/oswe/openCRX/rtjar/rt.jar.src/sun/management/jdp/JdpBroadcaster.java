/*     */ package sun.management.jdp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.StandardProtocolFamily;
/*     */ import java.net.StandardSocketOptions;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.DatagramChannel;
/*     */ import java.nio.channels.UnsupportedAddressTypeException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JdpBroadcaster
/*     */ {
/*     */   private final InetAddress addr;
/*     */   private final int port;
/*     */   private final DatagramChannel channel;
/*     */   
/*     */   public JdpBroadcaster(InetAddress paramInetAddress1, InetAddress paramInetAddress2, int paramInt1, int paramInt2) throws IOException, JdpException {
/*  67 */     this.addr = paramInetAddress1;
/*  68 */     this.port = paramInt1;
/*     */     
/*  70 */     StandardProtocolFamily standardProtocolFamily = (paramInetAddress1 instanceof java.net.Inet6Address) ? StandardProtocolFamily.INET6 : StandardProtocolFamily.INET;
/*     */ 
/*     */     
/*  73 */     this.channel = DatagramChannel.open(standardProtocolFamily);
/*  74 */     this.channel.setOption(StandardSocketOptions.SO_REUSEADDR, Boolean.valueOf(true));
/*  75 */     this.channel.setOption(StandardSocketOptions.IP_MULTICAST_TTL, Integer.valueOf(paramInt2));
/*     */ 
/*     */ 
/*     */     
/*  79 */     if (paramInetAddress2 != null) {
/*     */       
/*  81 */       NetworkInterface networkInterface = NetworkInterface.getByInetAddress(paramInetAddress2);
/*     */       try {
/*  83 */         this.channel.bind(new InetSocketAddress(paramInetAddress2, 0));
/*  84 */       } catch (UnsupportedAddressTypeException unsupportedAddressTypeException) {
/*  85 */         throw new JdpException("Unable to bind to source address");
/*     */       } 
/*  87 */       this.channel.setOption(StandardSocketOptions.IP_MULTICAST_IF, networkInterface);
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
/*     */   public JdpBroadcaster(InetAddress paramInetAddress, int paramInt1, int paramInt2) throws IOException, JdpException {
/* 101 */     this(paramInetAddress, null, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPacket(JdpPacket paramJdpPacket) throws IOException {
/* 112 */     byte[] arrayOfByte = paramJdpPacket.getPacketData();
/*     */     
/* 114 */     ByteBuffer byteBuffer = ByteBuffer.wrap(arrayOfByte);
/* 115 */     this.channel.send(byteBuffer, new InetSocketAddress(this.addr, this.port));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown() throws IOException {
/* 124 */     this.channel.close();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/jdp/JdpBroadcaster.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */