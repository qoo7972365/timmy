/*     */ package com.sun.corba.se.spi.servicecontext;
/*     */ 
/*     */ import com.sun.corba.se.impl.encoding.EncapsOutputStream;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import org.omg.CORBA.SystemException;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ import org.omg.CORBA_2_3.portable.OutputStream;
/*     */ import sun.corba.OutputStreamFactory;
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
/*     */ public abstract class ServiceContext
/*     */ {
/*     */   protected ServiceContext() {}
/*     */   
/*     */   private void dprint(String paramString) {
/*  70 */     ORBUtility.dprint(this, paramString);
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
/*     */   protected ServiceContext(InputStream paramInputStream, GIOPVersion paramGIOPVersion) throws SystemException {
/*  82 */     this.in = paramInputStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getId();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(OutputStream paramOutputStream, GIOPVersion paramGIOPVersion) throws SystemException {
/*  96 */     EncapsOutputStream encapsOutputStream = OutputStreamFactory.newEncapsOutputStream((ORB)paramOutputStream.orb(), paramGIOPVersion);
/*  97 */     encapsOutputStream.putEndian();
/*  98 */     writeData((OutputStream)encapsOutputStream);
/*  99 */     byte[] arrayOfByte = encapsOutputStream.toByteArray();
/*     */     
/* 101 */     paramOutputStream.write_long(getId());
/* 102 */     paramOutputStream.write_long(arrayOfByte.length);
/* 103 */     paramOutputStream.write_octet_array(arrayOfByte, 0, arrayOfByte.length);
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
/* 115 */   protected InputStream in = null;
/*     */ 
/*     */   
/*     */   public String toString() {
/* 119 */     return "ServiceContext[ id=" + getId() + " ]";
/*     */   }
/*     */   
/*     */   protected abstract void writeData(OutputStream paramOutputStream);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/servicecontext/ServiceContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */