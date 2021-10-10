/*     */ package java.rmi.server;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.security.SecureRandom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class UID
/*     */   implements Serializable
/*     */ {
/*     */   private static int hostUnique;
/*     */   private static boolean hostUniqueSet = false;
/*  76 */   private static final Object lock = new Object();
/*  77 */   private static long lastTime = System.currentTimeMillis();
/*  78 */   private static short lastCount = Short.MIN_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 1086053664494604050L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int unique;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final long time;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final short count;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UID() {
/* 110 */     synchronized (lock) {
/* 111 */       if (!hostUniqueSet) {
/* 112 */         hostUnique = (new SecureRandom()).nextInt();
/* 113 */         hostUniqueSet = true;
/*     */       } 
/* 115 */       this.unique = hostUnique;
/* 116 */       if (lastCount == Short.MAX_VALUE) {
/* 117 */         boolean bool = Thread.interrupted();
/* 118 */         boolean bool1 = false;
/* 119 */         while (!bool1) {
/* 120 */           long l = System.currentTimeMillis();
/* 121 */           if (l == lastTime) {
/*     */             
/*     */             try {
/* 124 */               Thread.sleep(1L);
/* 125 */             } catch (InterruptedException interruptedException) {
/* 126 */               bool = true;
/*     */             } 
/*     */             
/*     */             continue;
/*     */           } 
/* 131 */           lastTime = (l < lastTime) ? (lastTime + 1L) : l;
/* 132 */           lastCount = Short.MIN_VALUE;
/* 133 */           bool1 = true;
/*     */         } 
/*     */         
/* 136 */         if (bool) {
/* 137 */           Thread.currentThread().interrupt();
/*     */         }
/*     */       } 
/* 140 */       this.time = lastTime;
/* 141 */       this.count = lastCount = (short)(lastCount + 1);
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
/*     */ 
/*     */   
/*     */   public UID(short paramShort) {
/* 157 */     this.unique = 0;
/* 158 */     this.time = 0L;
/* 159 */     this.count = paramShort;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private UID(int paramInt, long paramLong, short paramShort) {
/* 166 */     this.unique = paramInt;
/* 167 */     this.time = paramLong;
/* 168 */     this.count = paramShort;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 177 */     return (int)this.time + this.count;
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
/*     */   public boolean equals(Object paramObject) {
/* 195 */     if (paramObject instanceof UID) {
/* 196 */       UID uID = (UID)paramObject;
/* 197 */       return (this.unique == uID.unique && this.count == uID.count && this.time == uID.time);
/*     */     } 
/*     */ 
/*     */     
/* 201 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 211 */     return Integer.toString(this.unique, 16) + ":" + 
/* 212 */       Long.toString(this.time, 16) + ":" + 
/* 213 */       Integer.toString(this.count, 16);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(DataOutput paramDataOutput) throws IOException {
/* 235 */     paramDataOutput.writeInt(this.unique);
/* 236 */     paramDataOutput.writeLong(this.time);
/* 237 */     paramDataOutput.writeShort(this.count);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UID read(DataInput paramDataInput) throws IOException {
/* 264 */     int i = paramDataInput.readInt();
/* 265 */     long l = paramDataInput.readLong();
/* 266 */     short s = paramDataInput.readShort();
/* 267 */     return new UID(i, l, s);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/rmi/server/UID.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */