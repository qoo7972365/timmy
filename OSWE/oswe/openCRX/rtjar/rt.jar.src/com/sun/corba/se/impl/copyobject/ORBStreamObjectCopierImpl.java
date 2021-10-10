/*    */ package com.sun.corba.se.impl.copyobject;
/*    */ 
/*    */ import com.sun.corba.se.impl.util.Utility;
/*    */ import com.sun.corba.se.spi.copyobject.ObjectCopier;
/*    */ import java.io.Serializable;
/*    */ import org.omg.CORBA.ORB;
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
/*    */ public class ORBStreamObjectCopierImpl
/*    */   implements ObjectCopier
/*    */ {
/*    */   private ORB orb;
/*    */   
/*    */   public ORBStreamObjectCopierImpl(ORB paramORB) {
/* 46 */     this.orb = paramORB;
/*    */   }
/*    */   
/*    */   public Object copy(Object paramObject) {
/* 50 */     if (paramObject instanceof java.rmi.Remote)
/*    */     {
/*    */       
/* 53 */       return Utility.autoConnect(paramObject, this.orb, true);
/*    */     }
/*    */     
/* 56 */     OutputStream outputStream = (OutputStream)this.orb.create_output_stream();
/* 57 */     outputStream.write_value((Serializable)paramObject);
/* 58 */     InputStream inputStream = (InputStream)outputStream.create_input_stream();
/* 59 */     return inputStream.read_value();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/copyobject/ORBStreamObjectCopierImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */