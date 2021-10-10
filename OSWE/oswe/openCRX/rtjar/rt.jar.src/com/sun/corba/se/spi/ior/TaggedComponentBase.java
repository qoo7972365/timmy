/*    */ package com.sun.corba.se.spi.ior;
/*    */ 
/*    */ import com.sun.corba.se.impl.encoding.EncapsOutputStream;
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA_2_3.portable.InputStream;
/*    */ import org.omg.CORBA_2_3.portable.OutputStream;
/*    */ import org.omg.IOP.TaggedComponent;
/*    */ import org.omg.IOP.TaggedComponentHelper;
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
/*    */ public abstract class TaggedComponentBase
/*    */   extends IdentifiableBase
/*    */   implements TaggedComponent
/*    */ {
/*    */   public TaggedComponent getIOPComponent(ORB paramORB) {
/* 46 */     EncapsOutputStream encapsOutputStream = OutputStreamFactory.newEncapsOutputStream((ORB)paramORB);
/* 47 */     write((OutputStream)encapsOutputStream);
/* 48 */     InputStream inputStream = (InputStream)encapsOutputStream.create_input_stream();
/* 49 */     return TaggedComponentHelper.read((InputStream)inputStream);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/ior/TaggedComponentBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */