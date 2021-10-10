/*    */ package com.sun.corba.se.spi.servicecontext;
/*    */ 
/*    */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*    */ import org.omg.CORBA.SystemException;
/*    */ import org.omg.CORBA_2_3.portable.InputStream;
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
/*    */ public class UnknownServiceContext
/*    */   extends ServiceContext
/*    */ {
/*    */   private int id;
/*    */   private byte[] data;
/*    */   
/*    */   public UnknownServiceContext(int paramInt, byte[] paramArrayOfbyte) {
/* 69 */     this.id = -1;
/* 70 */     this.data = null; this.id = paramInt; this.data = paramArrayOfbyte; } public UnknownServiceContext(int paramInt, InputStream paramInputStream) { this.id = -1; this.data = null;
/*    */     this.id = paramInt;
/*    */     int i = paramInputStream.read_long();
/*    */     this.data = new byte[i];
/*    */     paramInputStream.read_octet_array(this.data, 0, i); }
/*    */ 
/*    */   
/*    */   public int getId() {
/*    */     return this.id;
/*    */   }
/*    */   
/*    */   public void writeData(OutputStream paramOutputStream) throws SystemException {}
/*    */   
/*    */   public void write(OutputStream paramOutputStream, GIOPVersion paramGIOPVersion) throws SystemException {
/*    */     paramOutputStream.write_long(this.id);
/*    */     paramOutputStream.write_long(this.data.length);
/*    */     paramOutputStream.write_octet_array(this.data, 0, this.data.length);
/*    */   }
/*    */   
/*    */   public byte[] getData() {
/*    */     return this.data;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/servicecontext/UnknownServiceContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */