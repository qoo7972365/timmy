/*     */ package com.sun.corba.se.impl.encoding;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BufferManagerWrite
/*     */ {
/*     */   protected ORB orb;
/*     */   protected ORBUtilSystemException wrapper;
/*     */   protected Object outputObject;
/*     */   protected boolean sentFullMessage;
/*     */   
/*     */   BufferManagerWrite(ORB paramORB) {
/* 149 */     this.sentFullMessage = false;
/*     */     this.orb = paramORB;
/*     */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.encoding");
/*     */   }
/*     */   
/*     */   public abstract boolean sentFragment();
/*     */   
/*     */   public boolean sentFullMessage() {
/*     */     return this.sentFullMessage;
/*     */   }
/*     */   
/*     */   public abstract int getBufferSize();
/*     */   
/*     */   public abstract void overflow(ByteBufferWithInfo paramByteBufferWithInfo);
/*     */   
/*     */   public abstract void sendMessage();
/*     */   
/*     */   public void setOutputObject(Object paramObject) {
/*     */     this.outputObject = paramObject;
/*     */   }
/*     */   
/*     */   public abstract void close();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/BufferManagerWrite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */