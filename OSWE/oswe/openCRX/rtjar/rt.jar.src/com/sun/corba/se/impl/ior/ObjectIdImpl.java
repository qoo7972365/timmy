/*    */ package com.sun.corba.se.impl.ior;
/*    */ 
/*    */ import com.sun.corba.se.spi.ior.ObjectId;
/*    */ import java.util.Arrays;
/*    */ import org.omg.CORBA_2_3.portable.OutputStream;
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
/*    */ public final class ObjectIdImpl
/*    */   implements ObjectId
/*    */ {
/*    */   private byte[] id;
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 41 */     if (!(paramObject instanceof ObjectIdImpl)) {
/* 42 */       return false;
/*    */     }
/* 44 */     ObjectIdImpl objectIdImpl = (ObjectIdImpl)paramObject;
/*    */     
/* 46 */     return Arrays.equals(this.id, objectIdImpl.id);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 51 */     int i = 17;
/* 52 */     for (byte b = 0; b < this.id.length; b++)
/* 53 */       i = 37 * i + this.id[b]; 
/* 54 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectIdImpl(byte[] paramArrayOfbyte) {
/* 59 */     this.id = paramArrayOfbyte;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] getId() {
/* 64 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(OutputStream paramOutputStream) {
/* 69 */     paramOutputStream.write_long(this.id.length);
/* 70 */     paramOutputStream.write_octet_array(this.id, 0, this.id.length);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/ObjectIdImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */