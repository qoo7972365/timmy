/*     */ package com.sun.corba.se.spi.ior;
/*     */ 
/*     */ import com.sun.corba.se.impl.ior.IORImpl;
/*     */ import com.sun.corba.se.impl.ior.IORTemplateImpl;
/*     */ import com.sun.corba.se.impl.ior.IORTemplateListImpl;
/*     */ import com.sun.corba.se.impl.ior.ObjectIdImpl;
/*     */ import com.sun.corba.se.impl.ior.ObjectKeyFactoryImpl;
/*     */ import com.sun.corba.se.impl.ior.ObjectKeyImpl;
/*     */ import com.sun.corba.se.impl.ior.ObjectReferenceFactoryImpl;
/*     */ import com.sun.corba.se.impl.ior.ObjectReferenceProducerBase;
/*     */ import com.sun.corba.se.impl.ior.ObjectReferenceTemplateImpl;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.io.Serializable;
/*     */ import org.omg.CORBA.BAD_PARAM;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.ValueFactory;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ import org.omg.PortableInterceptor.ObjectReferenceFactory;
/*     */ import org.omg.PortableInterceptor.ObjectReferenceTemplate;
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
/*     */ public class IORFactories
/*     */ {
/*     */   public static ObjectId makeObjectId(byte[] paramArrayOfbyte) {
/*  69 */     return (ObjectId)new ObjectIdImpl(paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ObjectKey makeObjectKey(ObjectKeyTemplate paramObjectKeyTemplate, ObjectId paramObjectId) {
/*  77 */     return (ObjectKey)new ObjectKeyImpl(paramObjectKeyTemplate, paramObjectId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IOR makeIOR(ORB paramORB, String paramString) {
/*  84 */     return (IOR)new IORImpl(paramORB, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IOR makeIOR(ORB paramORB) {
/*  91 */     return (IOR)new IORImpl(paramORB);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IOR makeIOR(InputStream paramInputStream) {
/*  98 */     return (IOR)new IORImpl(paramInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IORTemplate makeIORTemplate(ObjectKeyTemplate paramObjectKeyTemplate) {
/* 106 */     return (IORTemplate)new IORTemplateImpl(paramObjectKeyTemplate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IORTemplate makeIORTemplate(InputStream paramInputStream) {
/* 113 */     return (IORTemplate)new IORTemplateImpl(paramInputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   public static IORTemplateList makeIORTemplateList() {
/* 118 */     return (IORTemplateList)new IORTemplateListImpl();
/*     */   }
/*     */ 
/*     */   
/*     */   public static IORTemplateList makeIORTemplateList(InputStream paramInputStream) {
/* 123 */     return (IORTemplateList)new IORTemplateListImpl(paramInputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   public static IORFactory getIORFactory(ObjectReferenceTemplate paramObjectReferenceTemplate) {
/* 128 */     if (paramObjectReferenceTemplate instanceof ObjectReferenceTemplateImpl) {
/* 129 */       ObjectReferenceTemplateImpl objectReferenceTemplateImpl = (ObjectReferenceTemplateImpl)paramObjectReferenceTemplate;
/*     */       
/* 131 */       return objectReferenceTemplateImpl.getIORFactory();
/*     */     } 
/*     */     
/* 134 */     throw new BAD_PARAM();
/*     */   }
/*     */ 
/*     */   
/*     */   public static IORTemplateList getIORTemplateList(ObjectReferenceFactory paramObjectReferenceFactory) {
/* 139 */     if (paramObjectReferenceFactory instanceof ObjectReferenceProducerBase) {
/* 140 */       ObjectReferenceProducerBase objectReferenceProducerBase = (ObjectReferenceProducerBase)paramObjectReferenceFactory;
/*     */       
/* 142 */       return objectReferenceProducerBase.getIORTemplateList();
/*     */     } 
/*     */     
/* 145 */     throw new BAD_PARAM();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ObjectReferenceTemplate makeObjectReferenceTemplate(ORB paramORB, IORTemplate paramIORTemplate) {
/* 151 */     return (ObjectReferenceTemplate)new ObjectReferenceTemplateImpl(paramORB, paramIORTemplate);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ObjectReferenceFactory makeObjectReferenceFactory(ORB paramORB, IORTemplateList paramIORTemplateList) {
/* 157 */     return (ObjectReferenceFactory)new ObjectReferenceFactoryImpl(paramORB, paramIORTemplateList);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ObjectKeyFactory makeObjectKeyFactory(ORB paramORB) {
/* 162 */     return (ObjectKeyFactory)new ObjectKeyFactoryImpl(paramORB);
/*     */   }
/*     */ 
/*     */   
/*     */   public static IOR getIOR(Object paramObject) {
/* 167 */     return ORBUtility.getIOR(paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object makeObjectReference(IOR paramIOR) {
/* 172 */     return ORBUtility.makeObjectReference(paramIOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerValueFactories(ORB paramORB) {
/* 183 */     ValueFactory valueFactory = new ValueFactory()
/*     */       {
/*     */         public Serializable read_value(InputStream param1InputStream) {
/* 186 */           return (Serializable)new ObjectReferenceTemplateImpl((InputStream)param1InputStream);
/*     */         }
/*     */       };
/*     */     
/* 190 */     paramORB.register_value_factory("IDL:com/sun/corba/se/impl/ior/ObjectReferenceTemplateImpl:1.0", valueFactory);
/*     */ 
/*     */ 
/*     */     
/* 194 */     valueFactory = new ValueFactory()
/*     */       {
/*     */         public Serializable read_value(InputStream param1InputStream) {
/* 197 */           return (Serializable)new ObjectReferenceFactoryImpl((InputStream)param1InputStream);
/*     */         }
/*     */       };
/*     */     
/* 201 */     paramORB.register_value_factory("IDL:com/sun/corba/se/impl/ior/ObjectReferenceFactoryImpl:1.0", valueFactory);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/ior/IORFactories.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */