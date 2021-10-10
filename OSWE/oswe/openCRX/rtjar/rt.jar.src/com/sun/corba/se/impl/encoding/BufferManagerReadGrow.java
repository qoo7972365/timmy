/*    */ package com.sun.corba.se.impl.encoding;
/*    */ 
/*    */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*    */ import com.sun.corba.se.impl.protocol.giopmsgheaders.FragmentMessage;
/*    */ import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import java.nio.ByteBuffer;
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
/*    */ public class BufferManagerReadGrow
/*    */   implements BufferManagerRead, MarkAndResetHandler
/*    */ {
/*    */   private ORB orb;
/*    */   private ORBUtilSystemException wrapper;
/*    */   private Object streamMemento;
/*    */   private RestorableInputStream inputStream;
/*    */   private boolean markEngaged;
/*    */   
/*    */   BufferManagerReadGrow(ORB paramORB) {
/* 72 */     this.markEngaged = false;
/*    */     this.orb = paramORB;
/*    */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.encoding"); } public void processFragment(ByteBuffer paramByteBuffer, FragmentMessage paramFragmentMessage) {} public void init(Message paramMessage) {} public MarkAndResetHandler getMarkAndResetHandler() {
/* 75 */     return this;
/*    */   } public ByteBufferWithInfo underflow(ByteBufferWithInfo paramByteBufferWithInfo) {
/*    */     throw this.wrapper.unexpectedEof();
/*    */   } public void cancelProcessing(int paramInt) {} public void mark(RestorableInputStream paramRestorableInputStream) {
/* 79 */     this.markEngaged = true;
/* 80 */     this.inputStream = paramRestorableInputStream;
/* 81 */     this.streamMemento = this.inputStream.createStreamMemento();
/*    */   }
/*    */ 
/*    */   
/*    */   public void fragmentationOccured(ByteBufferWithInfo paramByteBufferWithInfo) {}
/*    */ 
/*    */   
/*    */   public void reset() {
/* 89 */     if (!this.markEngaged) {
/*    */       return;
/*    */     }
/* 92 */     this.markEngaged = false;
/* 93 */     this.inputStream.restoreInternalState(this.streamMemento);
/* 94 */     this.streamMemento = null;
/*    */   }
/*    */   
/*    */   public void close(ByteBufferWithInfo paramByteBufferWithInfo) {}
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/BufferManagerReadGrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */