/*     */ package com.sun.corba.se.impl.encoding;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.pept.encoding.OutputObject;
/*     */ import com.sun.corba.se.pept.transport.Connection;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.orb.ORBData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BufferManagerWriteGrow
/*     */   extends BufferManagerWrite
/*     */ {
/*     */   BufferManagerWriteGrow(ORB paramORB) {
/*  41 */     super(paramORB);
/*     */   }
/*     */   
/*     */   public boolean sentFragment() {
/*  45 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBufferSize() {
/*  53 */     ORBData oRBData = null;
/*  54 */     int i = 1024;
/*  55 */     if (this.orb != null) {
/*  56 */       oRBData = this.orb.getORBData();
/*  57 */       if (oRBData != null) {
/*  58 */         i = oRBData.getGIOPBufferSize();
/*  59 */         dprint("BufferManagerWriteGrow.getBufferSize: bufferSize == " + i);
/*     */       } else {
/*  61 */         dprint("BufferManagerWriteGrow.getBufferSize: orbData reference is NULL");
/*     */       } 
/*     */     } else {
/*  64 */       dprint("BufferManagerWriteGrow.getBufferSize: orb reference is NULL");
/*     */     } 
/*  66 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void overflow(ByteBufferWithInfo paramByteBufferWithInfo) {
/*  75 */     paramByteBufferWithInfo.growBuffer(this.orb);
/*     */ 
/*     */     
/*  78 */     paramByteBufferWithInfo.fragmented = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendMessage() {
/*  84 */     Connection connection = ((OutputObject)this.outputObject).getMessageMediator().getConnection();
/*     */     
/*  86 */     connection.writeLock();
/*     */ 
/*     */     
/*     */     try {
/*  90 */       connection.sendWithoutLock((OutputObject)this.outputObject);
/*     */       
/*  92 */       this.sentFullMessage = true;
/*     */     }
/*     */     finally {
/*     */       
/*  96 */       connection.writeUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {}
/*     */ 
/*     */ 
/*     */   
/*     */   private void dprint(String paramString) {
/* 108 */     if (this.orb.transportDebugFlag)
/* 109 */       ORBUtility.dprint(this, paramString); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/BufferManagerWriteGrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */