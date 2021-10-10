/*    */ package com.sun.corba.se.impl.ior;
/*    */ 
/*    */ import com.sun.corba.se.impl.encoding.EncapsOutputStream;
/*    */ import com.sun.corba.se.spi.ior.ObjectId;
/*    */ import com.sun.corba.se.spi.ior.ObjectKey;
/*    */ import com.sun.corba.se.spi.ior.ObjectKeyTemplate;
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import com.sun.corba.se.spi.protocol.CorbaServerRequestDispatcher;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA_2_3.portable.OutputStream;
/*    */ import sun.corba.OutputStreamFactory;
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
/*    */ public class ObjectKeyImpl
/*    */   implements ObjectKey
/*    */ {
/*    */   private ObjectKeyTemplate oktemp;
/*    */   private ObjectId id;
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 50 */     if (paramObject == null) {
/* 51 */       return false;
/*    */     }
/* 53 */     if (!(paramObject instanceof ObjectKeyImpl)) {
/* 54 */       return false;
/*    */     }
/* 56 */     ObjectKeyImpl objectKeyImpl = (ObjectKeyImpl)paramObject;
/*    */     
/* 58 */     return (this.oktemp.equals(objectKeyImpl.oktemp) && this.id
/* 59 */       .equals(objectKeyImpl.id));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 64 */     return this.oktemp.hashCode() ^ this.id.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectKeyTemplate getTemplate() {
/* 69 */     return this.oktemp;
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectId getId() {
/* 74 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectKeyImpl(ObjectKeyTemplate paramObjectKeyTemplate, ObjectId paramObjectId) {
/* 79 */     this.oktemp = paramObjectKeyTemplate;
/* 80 */     this.id = paramObjectId;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(OutputStream paramOutputStream) {
/* 85 */     this.oktemp.write(this.id, paramOutputStream);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] getBytes(ORB paramORB) {
/* 91 */     EncapsOutputStream encapsOutputStream = OutputStreamFactory.newEncapsOutputStream((ORB)paramORB);
/* 92 */     write((OutputStream)encapsOutputStream);
/* 93 */     return encapsOutputStream.toByteArray();
/*    */   }
/*    */ 
/*    */   
/*    */   public CorbaServerRequestDispatcher getServerRequestDispatcher(ORB paramORB) {
/* 98 */     return this.oktemp.getServerRequestDispatcher(paramORB, this.id);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/ObjectKeyImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */