/*     */ package com.sun.corba.se.impl.ior;
/*     */ 
/*     */ import com.sun.corba.se.spi.ior.IORFactories;
/*     */ import com.sun.corba.se.spi.ior.IORFactory;
/*     */ import com.sun.corba.se.spi.ior.IORTemplateList;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA.portable.StreamableValue;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ import org.omg.CORBA_2_3.portable.OutputStream;
/*     */ import org.omg.PortableInterceptor.ObjectReferenceFactory;
/*     */ import org.omg.PortableInterceptor.ObjectReferenceFactoryHelper;
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
/*     */ public class ObjectReferenceFactoryImpl
/*     */   extends ObjectReferenceProducerBase
/*     */   implements ObjectReferenceFactory, StreamableValue
/*     */ {
/*     */   private transient IORTemplateList iorTemplates;
/*     */   public static final String repositoryId = "IDL:com/sun/corba/se/impl/ior/ObjectReferenceFactoryImpl:1.0";
/*     */   
/*     */   public ObjectReferenceFactoryImpl(InputStream paramInputStream) {
/*  68 */     super((ORB)paramInputStream.orb());
/*  69 */     _read(paramInputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectReferenceFactoryImpl(ORB paramORB, IORTemplateList paramIORTemplateList) {
/*  74 */     super(paramORB);
/*  75 */     this.iorTemplates = paramIORTemplateList;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  80 */     if (!(paramObject instanceof ObjectReferenceFactoryImpl)) {
/*  81 */       return false;
/*     */     }
/*  83 */     ObjectReferenceFactoryImpl objectReferenceFactoryImpl = (ObjectReferenceFactoryImpl)paramObject;
/*     */     
/*  85 */     return (this.iorTemplates != null && this.iorTemplates
/*  86 */       .equals(objectReferenceFactoryImpl.iorTemplates));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  91 */     return this.iorTemplates.hashCode();
/*     */   }
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
/*     */   public String[] _truncatable_ids() {
/* 104 */     return new String[] { "IDL:com/sun/corba/se/impl/ior/ObjectReferenceFactoryImpl:1.0" };
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeCode _type() {
/* 109 */     return ObjectReferenceFactoryHelper.type();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void _read(InputStream paramInputStream) {
/* 117 */     InputStream inputStream = (InputStream)paramInputStream;
/*     */ 
/*     */     
/* 120 */     this.iorTemplates = IORFactories.makeIORTemplateList(inputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void _write(OutputStream paramOutputStream) {
/* 127 */     OutputStream outputStream = (OutputStream)paramOutputStream;
/*     */ 
/*     */     
/* 130 */     this.iorTemplates.write(outputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   public IORFactory getIORFactory() {
/* 135 */     return (IORFactory)this.iorTemplates;
/*     */   }
/*     */ 
/*     */   
/*     */   public IORTemplateList getIORTemplateList() {
/* 140 */     return this.iorTemplates;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/ObjectReferenceFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */