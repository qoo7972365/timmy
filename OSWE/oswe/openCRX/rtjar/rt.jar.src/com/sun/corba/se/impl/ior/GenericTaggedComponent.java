/*    */ package com.sun.corba.se.impl.ior;
/*    */ 
/*    */ import com.sun.corba.se.spi.ior.TaggedComponent;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA_2_3.portable.InputStream;
/*    */ import org.omg.IOP.TaggedComponent;
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
/*    */ public class GenericTaggedComponent
/*    */   extends GenericIdentifiable
/*    */   implements TaggedComponent
/*    */ {
/*    */   public GenericTaggedComponent(int paramInt, InputStream paramInputStream) {
/* 45 */     super(paramInt, paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public GenericTaggedComponent(int paramInt, byte[] paramArrayOfbyte) {
/* 50 */     super(paramInt, paramArrayOfbyte);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TaggedComponent getIOPComponent(ORB paramORB) {
/* 60 */     return new TaggedComponent(getId(), 
/* 61 */         getData());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/GenericTaggedComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */