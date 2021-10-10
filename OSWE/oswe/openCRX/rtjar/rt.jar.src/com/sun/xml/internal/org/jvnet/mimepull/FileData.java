/*    */ package com.sun.xml.internal.org.jvnet.mimepull;
/*    */ 
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
/*    */ final class FileData
/*    */   implements Data
/*    */ {
/*    */   private final DataFile file;
/*    */   private final long pointer;
/*    */   private final int length;
/*    */   
/*    */   FileData(DataFile file, ByteBuffer buf) {
/* 42 */     this(file, file.writeTo(buf.array(), 0, buf.limit()), buf.limit());
/*    */   }
/*    */   
/*    */   FileData(DataFile file, long pointer, int length) {
/* 46 */     this.file = file;
/* 47 */     this.pointer = pointer;
/* 48 */     this.length = length;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] read() {
/* 53 */     byte[] buf = new byte[this.length];
/* 54 */     this.file.read(this.pointer, buf, 0, this.length);
/* 55 */     return buf;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long writeTo(DataFile file) {
/* 63 */     throw new IllegalStateException();
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 68 */     return this.length;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Data createNext(DataHead dataHead, ByteBuffer buf) {
/* 76 */     return new FileData(this.file, buf);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/mimepull/FileData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */