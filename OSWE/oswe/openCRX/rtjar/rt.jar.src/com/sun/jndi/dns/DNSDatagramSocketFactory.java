/*     */ package com.sun.jndi.dns;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.ProtocolFamily;
/*     */ import java.net.SocketException;
/*     */ import java.nio.channels.DatagramChannel;
/*     */ import java.util.Objects;
/*     */ import java.util.Random;
/*     */ import sun.net.PortConfig;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DNSDatagramSocketFactory
/*     */ {
/*     */   static final int DEVIATION = 3;
/*     */   static final int THRESHOLD = 6;
/*     */   static final int BIT_DEVIATION = 2;
/*     */   static final int HISTORY = 32;
/*     */   static final int MAX_RANDOM_TRIES = 5;
/*     */   
/*     */   static final class EphemeralPortRange
/*     */   {
/*  48 */     static final int LOWER = PortConfig.getLower();
/*  49 */     static final int UPPER = PortConfig.getUpper();
/*  50 */     static final int RANGE = UPPER - LOWER + 1;
/*     */   }
/*     */   
/*     */   static final class PortHistory {
/*     */     final int capacity;
/*     */     final int[] ports;
/*     */     final Random random;
/*     */     int index;
/*     */     
/*     */     PortHistory(int param1Int, Random param1Random) {
/*  60 */       this.random = param1Random;
/*  61 */       this.capacity = param1Int;
/*  62 */       this.ports = new int[param1Int];
/*     */     }
/*     */     
/*     */     public boolean contains(int param1Int) {
/*  66 */       int i = 0;
/*  67 */       for (byte b = 0; b < this.capacity && (
/*  68 */         i = this.ports[b]) != 0 && i != param1Int; b++);
/*     */       
/*  70 */       return (i == param1Int);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(int param1Int) {
/*  75 */       if (this.ports[this.index] != 0) {
/*     */         
/*  77 */         this.ports[this.random.nextInt(this.capacity)] = param1Int;
/*     */       } else {
/*  79 */         this.ports[this.index] = param1Int;
/*     */       } 
/*  81 */       if (++this.index == this.capacity) this.index = 0; 
/*  82 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean offer(int param1Int) {
/*  88 */       if (contains(param1Int)) return false; 
/*  89 */       return add(param1Int);
/*     */     }
/*     */   }
/*     */   
/*  93 */   int lastport = 0;
/*     */   int suitablePortCount;
/*     */   int unsuitablePortCount;
/*     */   final ProtocolFamily family;
/*     */   final int thresholdCount;
/*     */   final int deviation;
/*     */   final Random random;
/*     */   final PortHistory history;
/*     */   
/*     */   DNSDatagramSocketFactory() {
/* 103 */     this(new Random());
/*     */   }
/*     */   
/*     */   DNSDatagramSocketFactory(Random paramRandom) {
/* 107 */     this(Objects.<Random>requireNonNull(paramRandom), null, 3, 6);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   DNSDatagramSocketFactory(Random paramRandom, ProtocolFamily paramProtocolFamily, int paramInt1, int paramInt2) {
/* 113 */     this.random = Objects.<Random>requireNonNull(paramRandom);
/* 114 */     this.history = new PortHistory(32, paramRandom);
/* 115 */     this.family = paramProtocolFamily;
/* 116 */     this.deviation = Math.max(1, paramInt1);
/* 117 */     this.thresholdCount = Math.max(2, paramInt2);
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
/*     */   public synchronized DatagramSocket open() throws SocketException {
/* 131 */     int i = this.lastport;
/*     */ 
/*     */     
/* 134 */     boolean bool1 = (this.unsuitablePortCount > this.thresholdCount) ? true : false;
/* 135 */     if (bool1) {
/*     */ 
/*     */       
/* 138 */       DatagramSocket datagramSocket = openRandom();
/* 139 */       if (datagramSocket != null) return datagramSocket;
/*     */ 
/*     */ 
/*     */       
/* 143 */       this.unsuitablePortCount = 0; this.suitablePortCount = 0; i = 0;
/*     */     } 
/*     */ 
/*     */     
/* 147 */     DatagramSocket datagramSocket1 = openDefault();
/* 148 */     this.lastport = datagramSocket1.getLocalPort();
/* 149 */     if (i == 0) {
/* 150 */       this.history.offer(this.lastport);
/* 151 */       return datagramSocket1;
/*     */     } 
/*     */     
/* 154 */     bool1 = (this.suitablePortCount > this.thresholdCount) ? true : false;
/*     */     
/* 156 */     boolean bool2 = (Integer.bitCount(i ^ this.lastport) > 2 && Math.abs(this.lastport - i) > this.deviation) ? true : false;
/* 157 */     boolean bool = this.history.contains(this.lastport);
/* 158 */     boolean bool3 = (bool1 || (bool2 && !bool)) ? true : false;
/* 159 */     if (bool3 && !bool) this.history.add(this.lastport);
/*     */     
/* 161 */     if (bool3) {
/* 162 */       if (!bool1) {
/* 163 */         this.suitablePortCount++;
/* 164 */       } else if (!bool2 || bool) {
/* 165 */         this.unsuitablePortCount = 1;
/* 166 */         this.suitablePortCount = this.thresholdCount / 2;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 171 */       return datagramSocket1;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 176 */     assert !bool1;
/* 177 */     DatagramSocket datagramSocket2 = openRandom();
/* 178 */     if (datagramSocket2 == null) return datagramSocket1; 
/* 179 */     this.unsuitablePortCount++;
/* 180 */     datagramSocket1.close();
/* 181 */     return datagramSocket2;
/*     */   }
/*     */   
/*     */   private DatagramSocket openDefault() throws SocketException {
/* 185 */     if (this.family != null) {
/*     */       try {
/* 187 */         DatagramChannel datagramChannel = DatagramChannel.open(this.family);
/*     */         try {
/* 189 */           DatagramSocket datagramSocket = datagramChannel.socket();
/* 190 */           datagramSocket.bind(null);
/* 191 */           return datagramSocket;
/* 192 */         } catch (Throwable throwable) {
/* 193 */           datagramChannel.close();
/* 194 */           throw throwable;
/*     */         } 
/* 196 */       } catch (SocketException socketException) {
/* 197 */         throw socketException;
/* 198 */       } catch (IOException iOException) {
/* 199 */         SocketException socketException = new SocketException(iOException.getMessage());
/* 200 */         socketException.initCause(iOException);
/* 201 */         throw socketException;
/*     */       } 
/*     */     }
/* 204 */     return new DatagramSocket();
/*     */   }
/*     */   
/*     */   synchronized boolean isUsingNativePortRandomization() {
/* 208 */     return (this.unsuitablePortCount <= this.thresholdCount && this.suitablePortCount > this.thresholdCount);
/*     */   }
/*     */ 
/*     */   
/*     */   synchronized boolean isUsingJavaPortRandomization() {
/* 213 */     return (this.unsuitablePortCount > this.thresholdCount);
/*     */   }
/*     */   
/*     */   synchronized boolean isUndecided() {
/* 217 */     return (!isUsingJavaPortRandomization() && 
/* 218 */       !isUsingNativePortRandomization());
/*     */   }
/*     */   
/*     */   private DatagramSocket openRandom() {
/* 222 */     byte b = 5;
/* 223 */     while (b-- > 0) {
/*     */       
/* 225 */       int i = EphemeralPortRange.LOWER + this.random.nextInt(EphemeralPortRange.RANGE);
/*     */       try {
/* 227 */         if (this.family != null) {
/* 228 */           DatagramChannel datagramChannel = DatagramChannel.open(this.family);
/*     */           try {
/* 230 */             DatagramSocket datagramSocket = datagramChannel.socket();
/* 231 */             datagramSocket.bind(new InetSocketAddress(i));
/* 232 */             return datagramSocket;
/* 233 */           } catch (Throwable throwable) {
/* 234 */             datagramChannel.close();
/* 235 */             throw throwable;
/*     */           } 
/*     */         } 
/* 238 */         return new DatagramSocket(i);
/* 239 */       } catch (IOException iOException) {}
/*     */     } 
/*     */ 
/*     */     
/* 243 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/dns/DNSDatagramSocketFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */