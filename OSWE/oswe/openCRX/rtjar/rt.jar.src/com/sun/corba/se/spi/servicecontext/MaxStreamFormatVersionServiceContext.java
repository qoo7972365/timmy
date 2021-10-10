/*    */ package com.sun.corba.se.spi.servicecontext;
/*    */ 
/*    */ import com.sun.corba.se.impl.orbutil.ORBUtility;
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
/*    */ public class MaxStreamFormatVersionServiceContext
/*    */   extends ServiceContext
/*    */ {
/*    */   private byte maxStreamFormatVersion;
/* 46 */   public static final MaxStreamFormatVersionServiceContext singleton = new MaxStreamFormatVersionServiceContext();
/*    */   public static final int SERVICE_CONTEXT_ID = 17;
/*    */   
/*    */   public MaxStreamFormatVersionServiceContext() {
/* 50 */     this.maxStreamFormatVersion = ORBUtility.getMaxStreamFormatVersion();
/*    */   }
/*    */   
/*    */   public MaxStreamFormatVersionServiceContext(byte paramByte) {
/* 54 */     this.maxStreamFormatVersion = paramByte;
/*    */   }
/*    */ 
/*    */   
/*    */   public MaxStreamFormatVersionServiceContext(InputStream paramInputStream, GIOPVersion paramGIOPVersion) {
/* 59 */     super(paramInputStream, paramGIOPVersion);
/*    */     
/* 61 */     this.maxStreamFormatVersion = paramInputStream.read_octet();
/*    */   }
/*    */   
/*    */   public int getId() {
/* 65 */     return 17;
/*    */   }
/*    */   
/*    */   public void writeData(OutputStream paramOutputStream) throws SystemException {
/* 69 */     paramOutputStream.write_octet(this.maxStreamFormatVersion);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getMaximumStreamFormatVersion() {
/* 74 */     return this.maxStreamFormatVersion;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 79 */     return "MaxStreamFormatVersionServiceContext[" + this.maxStreamFormatVersion + "]";
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/servicecontext/MaxStreamFormatVersionServiceContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */