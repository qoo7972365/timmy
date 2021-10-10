/*     */ package com.sun.corba.se.impl.ior;
/*     */ 
/*     */ import com.sun.corba.se.spi.ior.IORFactories;
/*     */ import com.sun.corba.se.spi.ior.IORFactory;
/*     */ import com.sun.corba.se.spi.ior.IORTemplate;
/*     */ import com.sun.corba.se.spi.ior.IORTemplateList;
/*     */ import com.sun.corba.se.spi.ior.ObjectAdapterId;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA.portable.StreamableValue;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ import org.omg.CORBA_2_3.portable.OutputStream;
/*     */ import org.omg.PortableInterceptor.ObjectReferenceTemplate;
/*     */ import org.omg.PortableInterceptor.ObjectReferenceTemplateHelper;
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
/*     */ public class ObjectReferenceTemplateImpl
/*     */   extends ObjectReferenceProducerBase
/*     */   implements ObjectReferenceTemplate, StreamableValue
/*     */ {
/*     */   private transient IORTemplate iorTemplate;
/*     */   public static final String repositoryId = "IDL:com/sun/corba/se/impl/ior/ObjectReferenceTemplateImpl:1.0";
/*     */   
/*     */   public ObjectReferenceTemplateImpl(InputStream paramInputStream) {
/*  69 */     super((ORB)paramInputStream.orb());
/*  70 */     _read(paramInputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectReferenceTemplateImpl(ORB paramORB, IORTemplate paramIORTemplate) {
/*  75 */     super(paramORB);
/*  76 */     this.iorTemplate = paramIORTemplate;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  81 */     if (!(paramObject instanceof ObjectReferenceTemplateImpl)) {
/*  82 */       return false;
/*     */     }
/*  84 */     ObjectReferenceTemplateImpl objectReferenceTemplateImpl = (ObjectReferenceTemplateImpl)paramObject;
/*     */     
/*  86 */     return (this.iorTemplate != null && this.iorTemplate
/*  87 */       .equals(objectReferenceTemplateImpl.iorTemplate));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  92 */     return this.iorTemplate.hashCode();
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
/* 105 */     return new String[] { "IDL:com/sun/corba/se/impl/ior/ObjectReferenceTemplateImpl:1.0" };
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeCode _type() {
/* 110 */     return ObjectReferenceTemplateHelper.type();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void _read(InputStream paramInputStream) {
/* 118 */     InputStream inputStream = (InputStream)paramInputStream;
/*     */     
/* 120 */     this.iorTemplate = IORFactories.makeIORTemplate(inputStream);
/* 121 */     this.orb = (ORB)inputStream.orb();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void _write(OutputStream paramOutputStream) {
/* 128 */     OutputStream outputStream = (OutputStream)paramOutputStream;
/*     */ 
/*     */     
/* 131 */     this.iorTemplate.write(outputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   public String server_id() {
/* 136 */     int i = this.iorTemplate.getObjectKeyTemplate().getServerId();
/* 137 */     return Integer.toString(i);
/*     */   }
/*     */ 
/*     */   
/*     */   public String orb_id() {
/* 142 */     return this.iorTemplate.getObjectKeyTemplate().getORBId();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] adapter_name() {
/* 148 */     ObjectAdapterId objectAdapterId = this.iorTemplate.getObjectKeyTemplate().getObjectAdapterId();
/*     */     
/* 150 */     return objectAdapterId.getAdapterName();
/*     */   }
/*     */ 
/*     */   
/*     */   public IORFactory getIORFactory() {
/* 155 */     return (IORFactory)this.iorTemplate;
/*     */   }
/*     */ 
/*     */   
/*     */   public IORTemplateList getIORTemplateList() {
/* 160 */     IORTemplateList iORTemplateList = IORFactories.makeIORTemplateList();
/* 161 */     iORTemplateList.add(this.iorTemplate);
/* 162 */     iORTemplateList.makeImmutable();
/* 163 */     return iORTemplateList;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/ObjectReferenceTemplateImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */