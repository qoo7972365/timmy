/*    */ package com.sun.org.apache.bcel.internal.util;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.DataInputStream;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ByteSequence
/*    */   extends DataInputStream
/*    */ {
/*    */   private ByteArrayStream byte_stream;
/*    */   
/*    */   public ByteSequence(byte[] bytes) {
/* 73 */     super(new ByteArrayStream(bytes));
/* 74 */     this.byte_stream = (ByteArrayStream)this.in;
/*    */   }
/*    */   
/* 77 */   public final int getIndex() { return this.byte_stream.getPosition(); } final void unreadByte() {
/* 78 */     this.byte_stream.unreadByte();
/*    */   }
/*    */   private static final class ByteArrayStream extends ByteArrayInputStream {
/* 81 */     ByteArrayStream(byte[] bytes) { super(bytes); }
/* 82 */     final int getPosition() { return this.pos; } final void unreadByte() {
/* 83 */       if (this.pos > 0) this.pos--; 
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/util/ByteSequence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */