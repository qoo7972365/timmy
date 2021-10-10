/*    */ package com.sun.corba.se.impl.ior;
/*    */ 
/*    */ import com.sun.corba.se.spi.ior.ObjectAdapterId;
/*    */ import com.sun.corba.se.spi.ior.ObjectId;
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import com.sun.corba.se.spi.orb.ORBVersion;
/*    */ import com.sun.corba.se.spi.orb.ORBVersionFactory;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
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
/*    */ public abstract class NewObjectKeyTemplateBase
/*    */   extends ObjectKeyTemplateBase
/*    */ {
/*    */   public NewObjectKeyTemplateBase(ORB paramORB, int paramInt1, int paramInt2, int paramInt3, String paramString, ObjectAdapterId paramObjectAdapterId) {
/* 50 */     super(paramORB, paramInt1, paramInt2, paramInt3, paramString, paramObjectAdapterId);
/*    */ 
/*    */     
/* 53 */     if (paramInt1 != -1347695872) {
/* 54 */       throw this.wrapper.badMagic(new Integer(paramInt1));
/*    */     }
/*    */   }
/*    */   
/*    */   public void write(ObjectId paramObjectId, OutputStream paramOutputStream) {
/* 59 */     super.write(paramObjectId, paramOutputStream);
/* 60 */     getORBVersion().write((OutputStream)paramOutputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(OutputStream paramOutputStream) {
/* 65 */     super.write(paramOutputStream);
/* 66 */     getORBVersion().write((OutputStream)paramOutputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setORBVersion(InputStream paramInputStream) {
/* 71 */     ORBVersion oRBVersion = ORBVersionFactory.create((InputStream)paramInputStream);
/* 72 */     setORBVersion(oRBVersion);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/NewObjectKeyTemplateBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */