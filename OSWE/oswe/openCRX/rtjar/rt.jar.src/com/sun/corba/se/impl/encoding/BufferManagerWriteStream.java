/*     */ package com.sun.corba.se.impl.encoding;
/*     */ 
/*     */ import com.sun.corba.se.impl.protocol.giopmsgheaders.FragmentMessage;
/*     */ import com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;
/*     */ import com.sun.corba.se.pept.encoding.OutputObject;
/*     */ import com.sun.corba.se.pept.transport.Connection;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import org.omg.CORBA.SystemException;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BufferManagerWriteStream
/*     */   extends BufferManagerWrite
/*     */ {
/*  47 */   private int fragmentCount = 0;
/*     */ 
/*     */   
/*     */   BufferManagerWriteStream(ORB paramORB) {
/*  51 */     super(paramORB);
/*     */   }
/*     */   
/*     */   public boolean sentFragment() {
/*  55 */     return (this.fragmentCount > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBufferSize() {
/*  63 */     return this.orb.getORBData().getGIOPFragmentSize();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void overflow(ByteBufferWithInfo paramByteBufferWithInfo) {
/*  69 */     MessageBase.setFlag(paramByteBufferWithInfo.byteBuffer, 2);
/*     */     
/*     */     try {
/*  72 */       sendFragment(false);
/*  73 */     } catch (SystemException systemException) {
/*  74 */       this.orb.getPIHandler().invokeClientPIEndingPoint(2, (Exception)systemException);
/*     */       
/*  76 */       throw systemException;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     paramByteBufferWithInfo.position(0);
/*  85 */     paramByteBufferWithInfo.buflen = paramByteBufferWithInfo.byteBuffer.limit();
/*  86 */     paramByteBufferWithInfo.fragmented = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  93 */     FragmentMessage fragmentMessage = ((CDROutputObject)this.outputObject).getMessageHeader().createFragmentMessage();
/*     */     
/*  95 */     fragmentMessage.write((OutputStream)this.outputObject);
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendFragment(boolean paramBoolean) {
/* 100 */     Connection connection = ((OutputObject)this.outputObject).getMessageMediator().getConnection();
/*     */ 
/*     */ 
/*     */     
/* 104 */     connection.writeLock();
/*     */ 
/*     */     
/*     */     try {
/* 108 */       connection.sendWithoutLock((OutputObject)this.outputObject);
/*     */       
/* 110 */       this.fragmentCount++;
/*     */     }
/*     */     finally {
/*     */       
/* 114 */       connection.writeUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendMessage() {
/* 122 */     sendFragment(true);
/*     */     
/* 124 */     this.sentFullMessage = true;
/*     */   }
/*     */   
/*     */   public void close() {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/BufferManagerWriteStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */