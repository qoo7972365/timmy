/*     */ package com.sun.corba.se.impl.encoding;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import org.omg.CORBA.INTERNAL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BufferManagerFactory
/*     */ {
/*     */   public static final int GROW = 0;
/*     */   public static final int COLLECT = 1;
/*     */   public static final int STREAM = 2;
/*     */   
/*     */   public static BufferManagerRead newBufferManagerRead(GIOPVersion paramGIOPVersion, byte paramByte, ORB paramORB) {
/*  61 */     if (paramByte != 0) {
/*  62 */       return new BufferManagerReadGrow(paramORB);
/*     */     }
/*     */     
/*  65 */     switch (paramGIOPVersion.intValue()) {
/*     */       
/*     */       case 256:
/*  68 */         return new BufferManagerReadGrow(paramORB);
/*     */ 
/*     */       
/*     */       case 257:
/*     */       case 258:
/*  73 */         return new BufferManagerReadStream(paramORB);
/*     */     } 
/*     */     
/*  76 */     throw new INTERNAL("Unknown GIOP version: " + paramGIOPVersion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferManagerRead newBufferManagerRead(int paramInt, byte paramByte, ORB paramORB) {
/*  84 */     if (paramByte != 0) {
/*  85 */       if (paramInt != 0) {
/*     */         
/*  87 */         ORBUtilSystemException oRBUtilSystemException = ORBUtilSystemException.get(paramORB, "rpc.encoding");
/*     */         
/*  89 */         throw oRBUtilSystemException.invalidBuffMgrStrategy("newBufferManagerRead");
/*     */       } 
/*  91 */       return new BufferManagerReadGrow(paramORB);
/*     */     } 
/*  93 */     switch (paramInt) {
/*     */       case 0:
/*  95 */         return new BufferManagerReadGrow(paramORB);
/*     */       case 1:
/*  97 */         throw new INTERNAL("Collect strategy invalid for reading");
/*     */       case 2:
/*  99 */         return new BufferManagerReadStream(paramORB);
/*     */     } 
/* 101 */     throw new INTERNAL("Unknown buffer manager read strategy: " + paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferManagerWrite newBufferManagerWrite(int paramInt, byte paramByte, ORB paramORB) {
/* 108 */     if (paramByte != 0) {
/* 109 */       if (paramInt != 0) {
/*     */         
/* 111 */         ORBUtilSystemException oRBUtilSystemException = ORBUtilSystemException.get(paramORB, "rpc.encoding");
/*     */         
/* 113 */         throw oRBUtilSystemException.invalidBuffMgrStrategy("newBufferManagerWrite");
/*     */       } 
/* 115 */       return new BufferManagerWriteGrow(paramORB);
/*     */     } 
/* 117 */     switch (paramInt) {
/*     */       case 0:
/* 119 */         return new BufferManagerWriteGrow(paramORB);
/*     */       case 1:
/* 121 */         return new BufferManagerWriteCollect(paramORB);
/*     */       case 2:
/* 123 */         return new BufferManagerWriteStream(paramORB);
/*     */     } 
/* 125 */     throw new INTERNAL("Unknown buffer manager write strategy: " + paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferManagerWrite newBufferManagerWrite(GIOPVersion paramGIOPVersion, byte paramByte, ORB paramORB) {
/* 132 */     if (paramByte != 0) {
/* 133 */       return new BufferManagerWriteGrow(paramORB);
/*     */     }
/* 135 */     return newBufferManagerWrite(paramORB
/* 136 */         .getORBData().getGIOPBuffMgrStrategy(paramGIOPVersion), paramByte, paramORB);
/*     */   }
/*     */ 
/*     */   
/*     */   public static BufferManagerRead defaultBufferManagerRead(ORB paramORB) {
/* 141 */     return new BufferManagerReadGrow(paramORB);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/BufferManagerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */