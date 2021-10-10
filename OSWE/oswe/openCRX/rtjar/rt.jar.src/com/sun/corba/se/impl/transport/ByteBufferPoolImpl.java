/*     */ package com.sun.corba.se.impl.transport;
/*     */ 
/*     */ import com.sun.corba.se.pept.transport.ByteBufferPool;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ByteBufferPoolImpl
/*     */   implements ByteBufferPool
/*     */ {
/*     */   private ORB itsOrb;
/*     */   private int itsByteBufferSize;
/*     */   private ArrayList itsPool;
/*  44 */   private int itsObjectCounter = 0;
/*     */ 
/*     */   
/*     */   private boolean debug;
/*     */ 
/*     */   
/*     */   public ByteBufferPoolImpl(ORB paramORB) {
/*  51 */     this.itsByteBufferSize = paramORB.getORBData().getGIOPFragmentSize();
/*  52 */     this.itsPool = new ArrayList();
/*  53 */     this.itsOrb = paramORB;
/*  54 */     this.debug = paramORB.transportDebugFlag;
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
/*     */   public ByteBuffer getByteBuffer(int paramInt) {
/*  75 */     ByteBuffer byteBuffer = null;
/*     */     
/*  77 */     if (paramInt <= this.itsByteBufferSize && 
/*  78 */       !this.itsOrb.getORBData().disableDirectByteBufferUse()) {
/*     */       int i;
/*     */ 
/*     */       
/*  82 */       synchronized (this.itsPool) {
/*     */         
/*  84 */         i = this.itsPool.size();
/*  85 */         if (i > 0) {
/*     */           
/*  87 */           byteBuffer = this.itsPool.remove(i - 1);
/*     */ 
/*     */           
/*  90 */           byteBuffer.clear();
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  98 */       if (i <= 0)
/*     */       {
/* 100 */         byteBuffer = ByteBuffer.allocateDirect(this.itsByteBufferSize);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 106 */       this.itsObjectCounter++;
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 112 */       byteBuffer = ByteBuffer.allocate(paramInt);
/*     */     } 
/*     */     
/* 115 */     return byteBuffer;
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
/*     */   public void releaseByteBuffer(ByteBuffer paramByteBuffer) {
/* 140 */     if (paramByteBuffer.isDirect()) {
/*     */       
/* 142 */       synchronized (this.itsPool) {
/*     */ 
/*     */ 
/*     */         
/* 146 */         boolean bool = false;
/* 147 */         int i = 0;
/*     */         
/* 149 */         if (this.debug)
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 154 */           for (byte b = 0; b < this.itsPool.size() && !bool; b++) {
/*     */             
/* 156 */             ByteBuffer byteBuffer = this.itsPool.get(b);
/* 157 */             if (paramByteBuffer == byteBuffer) {
/*     */               
/* 159 */               bool = true;
/* 160 */               i = System.identityHashCode(paramByteBuffer);
/*     */             } 
/*     */           } 
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 168 */         if (!bool || !this.debug) {
/*     */ 
/*     */           
/* 171 */           this.itsPool.add(paramByteBuffer);
/*     */         }
/*     */         else {
/*     */           
/* 175 */           String str = Thread.currentThread().getName();
/* 176 */           Throwable throwable = new Throwable(str + ": Duplicate ByteBuffer reference (" + i + ")");
/*     */ 
/*     */ 
/*     */           
/* 180 */           throwable.printStackTrace(System.out);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 187 */       this.itsObjectCounter--;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 192 */       paramByteBuffer = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int activeCount() {
/* 203 */     return this.itsObjectCounter;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/transport/ByteBufferPoolImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */