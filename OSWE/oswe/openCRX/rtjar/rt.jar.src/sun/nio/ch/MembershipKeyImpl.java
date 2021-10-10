/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.nio.channels.MembershipKey;
/*     */ import java.nio.channels.MulticastChannel;
/*     */ import java.util.HashSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MembershipKeyImpl
/*     */   extends MembershipKey
/*     */ {
/*     */   private final MulticastChannel ch;
/*     */   private final InetAddress group;
/*     */   private final NetworkInterface interf;
/*     */   private final InetAddress source;
/*     */   private volatile boolean valid = true;
/*  50 */   private Object stateLock = new Object();
/*     */ 
/*     */ 
/*     */   
/*     */   private HashSet<InetAddress> blockedSet;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MembershipKeyImpl(MulticastChannel paramMulticastChannel, InetAddress paramInetAddress1, NetworkInterface paramNetworkInterface, InetAddress paramInetAddress2) {
/*  60 */     this.ch = paramMulticastChannel;
/*  61 */     this.group = paramInetAddress1;
/*  62 */     this.interf = paramNetworkInterface;
/*  63 */     this.source = paramInetAddress2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class Type4
/*     */     extends MembershipKeyImpl
/*     */   {
/*     */     private final int groupAddress;
/*     */ 
/*     */     
/*     */     private final int interfAddress;
/*     */ 
/*     */     
/*     */     private final int sourceAddress;
/*     */ 
/*     */ 
/*     */     
/*     */     Type4(MulticastChannel param1MulticastChannel, InetAddress param1InetAddress1, NetworkInterface param1NetworkInterface, InetAddress param1InetAddress2, int param1Int1, int param1Int2, int param1Int3) {
/*  82 */       super(param1MulticastChannel, param1InetAddress1, param1NetworkInterface, param1InetAddress2);
/*  83 */       this.groupAddress = param1Int1;
/*  84 */       this.interfAddress = param1Int2;
/*  85 */       this.sourceAddress = param1Int3;
/*     */     }
/*     */     
/*     */     int groupAddress() {
/*  89 */       return this.groupAddress;
/*     */     }
/*     */     
/*     */     int interfaceAddress() {
/*  93 */       return this.interfAddress;
/*     */     }
/*     */     
/*     */     int source() {
/*  97 */       return this.sourceAddress;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class Type6
/*     */     extends MembershipKeyImpl
/*     */   {
/*     */     private final byte[] groupAddress;
/*     */ 
/*     */     
/*     */     private final int index;
/*     */ 
/*     */     
/*     */     private final byte[] sourceAddress;
/*     */ 
/*     */ 
/*     */     
/*     */     Type6(MulticastChannel param1MulticastChannel, InetAddress param1InetAddress1, NetworkInterface param1NetworkInterface, InetAddress param1InetAddress2, byte[] param1ArrayOfbyte1, int param1Int, byte[] param1ArrayOfbyte2) {
/* 117 */       super(param1MulticastChannel, param1InetAddress1, param1NetworkInterface, param1InetAddress2);
/* 118 */       this.groupAddress = param1ArrayOfbyte1;
/* 119 */       this.index = param1Int;
/* 120 */       this.sourceAddress = param1ArrayOfbyte2;
/*     */     }
/*     */     
/*     */     byte[] groupAddress() {
/* 124 */       return this.groupAddress;
/*     */     }
/*     */     
/*     */     int index() {
/* 128 */       return this.index;
/*     */     }
/*     */     
/*     */     byte[] source() {
/* 132 */       return this.sourceAddress;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isValid() {
/* 137 */     return this.valid;
/*     */   }
/*     */ 
/*     */   
/*     */   void invalidate() {
/* 142 */     this.valid = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drop() {
/* 147 */     ((DatagramChannelImpl)this.ch).drop(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public MulticastChannel channel() {
/* 152 */     return this.ch;
/*     */   }
/*     */ 
/*     */   
/*     */   public InetAddress group() {
/* 157 */     return this.group;
/*     */   }
/*     */ 
/*     */   
/*     */   public NetworkInterface networkInterface() {
/* 162 */     return this.interf;
/*     */   }
/*     */ 
/*     */   
/*     */   public InetAddress sourceAddress() {
/* 167 */     return this.source;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MembershipKey block(InetAddress paramInetAddress) throws IOException {
/* 174 */     if (this.source != null) {
/* 175 */       throw new IllegalStateException("key is source-specific");
/*     */     }
/* 177 */     synchronized (this.stateLock) {
/* 178 */       if (this.blockedSet != null && this.blockedSet.contains(paramInetAddress))
/*     */       {
/* 180 */         return this;
/*     */       }
/*     */       
/* 183 */       ((DatagramChannelImpl)this.ch).block(this, paramInetAddress);
/*     */ 
/*     */       
/* 186 */       if (this.blockedSet == null)
/* 187 */         this.blockedSet = new HashSet<>(); 
/* 188 */       this.blockedSet.add(paramInetAddress);
/*     */     } 
/* 190 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public MembershipKey unblock(InetAddress paramInetAddress) {
/* 195 */     synchronized (this.stateLock) {
/* 196 */       if (this.blockedSet == null || !this.blockedSet.contains(paramInetAddress)) {
/* 197 */         throw new IllegalStateException("not blocked");
/*     */       }
/* 199 */       ((DatagramChannelImpl)this.ch).unblock(this, paramInetAddress);
/*     */       
/* 201 */       this.blockedSet.remove(paramInetAddress);
/*     */     } 
/* 203 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 208 */     StringBuilder stringBuilder = new StringBuilder(64);
/* 209 */     stringBuilder.append('<');
/* 210 */     stringBuilder.append(this.group.getHostAddress());
/* 211 */     stringBuilder.append(',');
/* 212 */     stringBuilder.append(this.interf.getName());
/* 213 */     if (this.source != null) {
/* 214 */       stringBuilder.append(',');
/* 215 */       stringBuilder.append(this.source.getHostAddress());
/*     */     } 
/* 217 */     stringBuilder.append('>');
/* 218 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/MembershipKeyImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */