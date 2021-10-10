/*    */ package com.sun.corba.se.impl.ior;
/*    */ 
/*    */ import com.sun.corba.se.spi.activation.POANameHelper;
/*    */ import com.sun.corba.se.spi.ior.ObjectAdapterId;
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import com.sun.corba.se.spi.orb.ORBVersionFactory;
/*    */ import org.omg.CORBA.OctetSeqHolder;
/*    */ import org.omg.CORBA.portable.InputStream;
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
/*    */ public final class POAObjectKeyTemplate
/*    */   extends NewObjectKeyTemplateBase
/*    */ {
/*    */   public POAObjectKeyTemplate(ORB paramORB, int paramInt1, int paramInt2, InputStream paramInputStream) {
/* 54 */     super(paramORB, paramInt1, paramInt2, paramInputStream.read_long(), paramInputStream.read_string(), new ObjectAdapterIdArray(
/* 55 */           POANameHelper.read((InputStream)paramInputStream)));
/*    */     
/* 57 */     setORBVersion(paramInputStream);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public POAObjectKeyTemplate(ORB paramORB, int paramInt1, int paramInt2, InputStream paramInputStream, OctetSeqHolder paramOctetSeqHolder) {
/* 66 */     super(paramORB, paramInt1, paramInt2, paramInputStream.read_long(), paramInputStream.read_string(), new ObjectAdapterIdArray(
/* 67 */           POANameHelper.read((InputStream)paramInputStream)));
/*    */     
/* 69 */     paramOctetSeqHolder.value = readObjectKey(paramInputStream);
/*    */     
/* 71 */     setORBVersion(paramInputStream);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public POAObjectKeyTemplate(ORB paramORB, int paramInt1, int paramInt2, String paramString, ObjectAdapterId paramObjectAdapterId) {
/* 77 */     super(paramORB, -1347695872, paramInt1, paramInt2, paramString, paramObjectAdapterId);
/*    */ 
/*    */     
/* 80 */     setORBVersion(ORBVersionFactory.getORBVersion());
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeTemplate(OutputStream paramOutputStream) {
/* 85 */     paramOutputStream.write_long(getMagic());
/* 86 */     paramOutputStream.write_long(getSubcontractId());
/* 87 */     paramOutputStream.write_long(getServerId());
/* 88 */     paramOutputStream.write_string(getORBId());
/* 89 */     getObjectAdapterId().write(paramOutputStream);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/POAObjectKeyTemplate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */