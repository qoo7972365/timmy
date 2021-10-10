/*    */ package com.sun.corba.se.spi.ior;
/*    */ 
/*    */ import com.sun.corba.se.impl.ior.EncapsulationUtility;
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import java.util.Iterator;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA_2_3.portable.OutputStream;
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
/*    */ public abstract class TaggedProfileTemplateBase
/*    */   extends IdentifiableContainerBase
/*    */   implements TaggedProfileTemplate
/*    */ {
/*    */   public void write(OutputStream paramOutputStream) {
/* 42 */     EncapsulationUtility.writeEncapsulation(this, paramOutputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public TaggedComponent[] getIOPComponents(ORB paramORB, int paramInt) {
/* 47 */     byte b1 = 0;
/* 48 */     Iterator<TaggedComponent> iterator = iteratorById(paramInt);
/* 49 */     while (iterator.hasNext()) {
/* 50 */       iterator.next();
/* 51 */       b1++;
/*    */     } 
/*    */     
/* 54 */     TaggedComponent[] arrayOfTaggedComponent = new TaggedComponent[b1];
/*    */ 
/*    */     
/* 57 */     byte b2 = 0;
/* 58 */     iterator = iteratorById(paramInt);
/* 59 */     while (iterator.hasNext()) {
/* 60 */       TaggedComponent taggedComponent = iterator.next();
/* 61 */       arrayOfTaggedComponent[b2++] = taggedComponent.getIOPComponent((ORB)paramORB);
/*    */     } 
/*    */     
/* 64 */     return arrayOfTaggedComponent;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/ior/TaggedProfileTemplateBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */