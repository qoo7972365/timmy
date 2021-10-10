/*     */ package com.sun.corba.se.impl.encoding;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.impl.protocol.giopmsgheaders.FragmentMessage;
/*     */ import com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;
/*     */ import com.sun.corba.se.pept.encoding.OutputObject;
/*     */ import com.sun.corba.se.pept.transport.ByteBufferPool;
/*     */ import com.sun.corba.se.pept.transport.Connection;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.util.Iterator;
/*     */ import org.omg.CORBA.ORB;
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
/*     */ 
/*     */ public class BufferManagerWriteCollect
/*     */   extends BufferManagerWrite
/*     */ {
/*  51 */   private BufferQueue queue = new BufferQueue();
/*     */   
/*     */   private boolean sentFragment = false;
/*     */   
/*     */   private boolean debug = false;
/*     */ 
/*     */   
/*     */   BufferManagerWriteCollect(ORB paramORB) {
/*  59 */     super(paramORB);
/*  60 */     if (paramORB != null)
/*  61 */       this.debug = paramORB.transportDebugFlag; 
/*     */   }
/*     */   
/*     */   public boolean sentFragment() {
/*  65 */     return this.sentFragment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBufferSize() {
/*  73 */     return this.orb.getORBData().getGIOPFragmentSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void overflow(ByteBufferWithInfo paramByteBufferWithInfo) {
/*  81 */     MessageBase.setFlag(paramByteBufferWithInfo.byteBuffer, 2);
/*     */ 
/*     */     
/*  84 */     this.queue.enqueue(paramByteBufferWithInfo);
/*     */ 
/*     */     
/*  87 */     ByteBufferWithInfo byteBufferWithInfo = new ByteBufferWithInfo((ORB)this.orb, this);
/*  88 */     byteBufferWithInfo.fragmented = true;
/*     */ 
/*     */     
/*  91 */     ((CDROutputObject)this.outputObject).setByteBufferWithInfo(byteBufferWithInfo);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     FragmentMessage fragmentMessage = ((CDROutputObject)this.outputObject).getMessageHeader().createFragmentMessage();
/*     */     
/* 103 */     fragmentMessage.write((OutputStream)this.outputObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendMessage() {
/* 110 */     this.queue.enqueue(((CDROutputObject)this.outputObject).getByteBufferWithInfo());
/*     */     
/* 112 */     Iterator<ByteBufferWithInfo> iterator = iterator();
/*     */ 
/*     */ 
/*     */     
/* 116 */     Connection connection = ((OutputObject)this.outputObject).getMessageMediator().getConnection();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     connection.writeLock();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 130 */       ByteBufferPool byteBufferPool = this.orb.getByteBufferPool();
/*     */       
/* 132 */       while (iterator.hasNext()) {
/*     */         
/* 134 */         ByteBufferWithInfo byteBufferWithInfo = iterator.next();
/* 135 */         ((CDROutputObject)this.outputObject).setByteBufferWithInfo(byteBufferWithInfo);
/*     */         
/* 137 */         connection.sendWithoutLock((OutputObject)this.outputObject);
/*     */         
/* 139 */         this.sentFragment = true;
/*     */ 
/*     */ 
/*     */         
/* 143 */         if (this.debug) {
/*     */ 
/*     */           
/* 146 */           int i = System.identityHashCode(byteBufferWithInfo.byteBuffer);
/* 147 */           StringBuffer stringBuffer = new StringBuffer(80);
/* 148 */           stringBuffer.append("sendMessage() - releasing ByteBuffer id (");
/* 149 */           stringBuffer.append(i).append(") to ByteBufferPool.");
/* 150 */           String str = stringBuffer.toString();
/* 151 */           dprint(str);
/*     */         } 
/* 153 */         byteBufferPool.releaseByteBuffer(byteBufferWithInfo.byteBuffer);
/* 154 */         byteBufferWithInfo.byteBuffer = null;
/* 155 */         byteBufferWithInfo = null;
/*     */       } 
/*     */       
/* 158 */       this.sentFullMessage = true;
/*     */     }
/*     */     finally {
/*     */       
/* 162 */       connection.writeUnlock();
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
/*     */   public void close() {
/* 178 */     Iterator<ByteBufferWithInfo> iterator = iterator();
/*     */     
/* 180 */     ByteBufferPool byteBufferPool = this.orb.getByteBufferPool();
/*     */     
/* 182 */     while (iterator.hasNext()) {
/*     */       
/* 184 */       ByteBufferWithInfo byteBufferWithInfo = iterator.next();
/* 185 */       if (byteBufferWithInfo != null && byteBufferWithInfo.byteBuffer != null) {
/*     */         
/* 187 */         if (this.debug) {
/*     */ 
/*     */           
/* 190 */           int i = System.identityHashCode(byteBufferWithInfo.byteBuffer);
/* 191 */           StringBuffer stringBuffer = new StringBuffer(80);
/* 192 */           stringBuffer.append("close() - releasing ByteBuffer id (");
/* 193 */           stringBuffer.append(i).append(") to ByteBufferPool.");
/* 194 */           String str = stringBuffer.toString();
/* 195 */           dprint(str);
/*     */         } 
/* 197 */         byteBufferPool.releaseByteBuffer(byteBufferWithInfo.byteBuffer);
/* 198 */         byteBufferWithInfo.byteBuffer = null;
/* 199 */         byteBufferWithInfo = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void dprint(String paramString) {
/* 206 */     ORBUtility.dprint("BufferManagerWriteCollect", paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   private Iterator iterator() {
/* 211 */     return new BufferManagerWriteCollectIterator();
/*     */   }
/*     */   
/*     */   private class BufferManagerWriteCollectIterator implements Iterator {
/*     */     private BufferManagerWriteCollectIterator() {}
/*     */     
/*     */     public boolean hasNext() {
/* 218 */       return (BufferManagerWriteCollect.this.queue.size() != 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object next() {
/* 223 */       return BufferManagerWriteCollect.this.queue.dequeue();
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 228 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/BufferManagerWriteCollect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */