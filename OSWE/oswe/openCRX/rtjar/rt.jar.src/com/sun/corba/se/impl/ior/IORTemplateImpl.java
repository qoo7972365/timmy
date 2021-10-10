/*     */ package com.sun.corba.se.impl.ior;
/*     */ 
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.ior.IORFactory;
/*     */ import com.sun.corba.se.spi.ior.IORTemplate;
/*     */ import com.sun.corba.se.spi.ior.IdentifiableContainerBase;
/*     */ import com.sun.corba.se.spi.ior.IdentifiableFactoryFinder;
/*     */ import com.sun.corba.se.spi.ior.ObjectId;
/*     */ import com.sun.corba.se.spi.ior.ObjectKeyTemplate;
/*     */ import com.sun.corba.se.spi.ior.TaggedProfileTemplate;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ import org.omg.CORBA_2_3.portable.OutputStream;
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
/*     */ public class IORTemplateImpl
/*     */   extends IdentifiableContainerBase
/*     */   implements IORTemplate
/*     */ {
/*     */   private ObjectKeyTemplate oktemp;
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  58 */     if (paramObject == null) {
/*  59 */       return false;
/*     */     }
/*  61 */     if (!(paramObject instanceof IORTemplateImpl)) {
/*  62 */       return false;
/*     */     }
/*  64 */     IORTemplateImpl iORTemplateImpl = (IORTemplateImpl)paramObject;
/*     */     
/*  66 */     return (super.equals(paramObject) && this.oktemp.equals(iORTemplateImpl.getObjectKeyTemplate()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  71 */     return super.hashCode() ^ this.oktemp.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectKeyTemplate getObjectKeyTemplate() {
/*  76 */     return this.oktemp;
/*     */   }
/*     */ 
/*     */   
/*     */   public IORTemplateImpl(ObjectKeyTemplate paramObjectKeyTemplate) {
/*  81 */     this.oktemp = paramObjectKeyTemplate;
/*     */   }
/*     */ 
/*     */   
/*     */   public IOR makeIOR(ORB paramORB, String paramString, ObjectId paramObjectId) {
/*  86 */     return new IORImpl(paramORB, paramString, this, paramObjectId);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEquivalent(IORFactory paramIORFactory) {
/*  91 */     if (!(paramIORFactory instanceof IORTemplate)) {
/*  92 */       return false;
/*     */     }
/*  94 */     IORTemplate iORTemplate = (IORTemplate)paramIORFactory;
/*     */     
/*  96 */     Iterator<TaggedProfileTemplate> iterator1 = iterator();
/*  97 */     Iterator<TaggedProfileTemplate> iterator2 = iORTemplate.iterator();
/*  98 */     while (iterator1.hasNext() && iterator2.hasNext()) {
/*     */       
/* 100 */       TaggedProfileTemplate taggedProfileTemplate1 = iterator1.next();
/*     */       
/* 102 */       TaggedProfileTemplate taggedProfileTemplate2 = iterator2.next();
/* 103 */       if (!taggedProfileTemplate1.isEquivalent(taggedProfileTemplate2)) {
/* 104 */         return false;
/*     */       }
/*     */     } 
/* 107 */     return (iterator1.hasNext() == iterator2.hasNext() && 
/* 108 */       getObjectKeyTemplate().equals(iORTemplate.getObjectKeyTemplate()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void makeImmutable() {
/* 117 */     makeElementsImmutable();
/* 118 */     super.makeImmutable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(OutputStream paramOutputStream) {
/* 123 */     this.oktemp.write(paramOutputStream);
/* 124 */     EncapsulationUtility.writeIdentifiableSequence((List)this, paramOutputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   public IORTemplateImpl(InputStream paramInputStream) {
/* 129 */     ORB oRB = (ORB)paramInputStream.orb();
/*     */     
/* 131 */     IdentifiableFactoryFinder identifiableFactoryFinder = oRB.getTaggedProfileTemplateFactoryFinder();
/*     */     
/* 133 */     this.oktemp = oRB.getObjectKeyFactory().createTemplate(paramInputStream);
/* 134 */     EncapsulationUtility.readIdentifiableSequence((List)this, identifiableFactoryFinder, paramInputStream);
/*     */     
/* 136 */     makeImmutable();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/IORTemplateImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */