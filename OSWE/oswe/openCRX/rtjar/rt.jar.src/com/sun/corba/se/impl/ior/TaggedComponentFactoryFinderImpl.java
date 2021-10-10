/*    */ package com.sun.corba.se.impl.ior;
/*    */ 
/*    */ import com.sun.corba.se.impl.encoding.EncapsOutputStream;
/*    */ import com.sun.corba.se.spi.ior.Identifiable;
/*    */ import com.sun.corba.se.spi.ior.TaggedComponent;
/*    */ import com.sun.corba.se.spi.ior.TaggedComponentFactoryFinder;
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA_2_3.portable.InputStream;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TaggedComponentFactoryFinderImpl
/*    */   extends IdentifiableFactoryFinderBase
/*    */   implements TaggedComponentFactoryFinder
/*    */ {
/*    */   public TaggedComponentFactoryFinderImpl(ORB paramORB) {
/* 54 */     super(paramORB);
/*    */   }
/*    */   
/*    */   public Identifiable handleMissingFactory(int paramInt, InputStream paramInputStream) {
/* 58 */     return new GenericTaggedComponent(paramInt, paramInputStream);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TaggedComponent create(ORB paramORB, TaggedComponent paramTaggedComponent) {
/* 65 */     EncapsOutputStream encapsOutputStream = OutputStreamFactory.newEncapsOutputStream((ORB)paramORB);
/* 66 */     TaggedComponentHelper.write((OutputStream)encapsOutputStream, paramTaggedComponent);
/* 67 */     InputStream inputStream = (InputStream)encapsOutputStream.create_input_stream();
/*    */     
/* 69 */     inputStream.read_ulong();
/*    */     
/* 71 */     return (TaggedComponent)create(paramTaggedComponent.tag, inputStream);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/TaggedComponentFactoryFinderImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */