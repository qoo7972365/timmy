/*    */ package com.sun.corba.se.impl.ior;
/*    */ 
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import com.sun.corba.se.spi.orb.ORBVersion;
/*    */ import com.sun.corba.se.spi.orb.ORBVersionFactory;
/*    */ import org.omg.CORBA.INTERNAL;
/*    */ import org.omg.CORBA.OctetSeqHolder;
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
/*    */ public final class OldPOAObjectKeyTemplate
/*    */   extends OldObjectKeyTemplateBase
/*    */ {
/*    */   public OldPOAObjectKeyTemplate(ORB paramORB, int paramInt1, int paramInt2, InputStream paramInputStream) {
/* 49 */     this(paramORB, paramInt1, paramInt2, paramInputStream.read_long(), paramInputStream.read_long(), paramInputStream.read_long());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public OldPOAObjectKeyTemplate(ORB paramORB, int paramInt1, int paramInt2, InputStream paramInputStream, OctetSeqHolder paramOctetSeqHolder) {
/* 58 */     this(paramORB, paramInt1, paramInt2, paramInputStream);
/* 59 */     paramOctetSeqHolder.value = readObjectKey(paramInputStream);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public OldPOAObjectKeyTemplate(ORB paramORB, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 65 */     super(paramORB, paramInt1, paramInt2, paramInt3, 
/* 66 */         Integer.toString(paramInt4), new ObjectAdapterIdNumber(paramInt5));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeTemplate(OutputStream paramOutputStream) {
/* 72 */     paramOutputStream.write_long(getMagic());
/* 73 */     paramOutputStream.write_long(getSubcontractId());
/* 74 */     paramOutputStream.write_long(getServerId());
/*    */     
/* 76 */     int i = Integer.parseInt(getORBId());
/* 77 */     paramOutputStream.write_long(i);
/*    */     
/* 79 */     ObjectAdapterIdNumber objectAdapterIdNumber = (ObjectAdapterIdNumber)getObjectAdapterId();
/* 80 */     int j = objectAdapterIdNumber.getOldPOAId();
/* 81 */     paramOutputStream.write_long(j);
/*    */   }
/*    */ 
/*    */   
/*    */   public ORBVersion getORBVersion() {
/* 86 */     if (getMagic() == -1347695874)
/* 87 */       return ORBVersionFactory.getOLD(); 
/* 88 */     if (getMagic() == -1347695873) {
/* 89 */       return ORBVersionFactory.getNEW();
/*    */     }
/* 91 */     throw new INTERNAL();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/OldPOAObjectKeyTemplate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */