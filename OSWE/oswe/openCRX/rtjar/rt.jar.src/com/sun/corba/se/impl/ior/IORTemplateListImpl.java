/*     */ package com.sun.corba.se.impl.ior;
/*     */ 
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.ior.IORFactories;
/*     */ import com.sun.corba.se.spi.ior.IORFactory;
/*     */ import com.sun.corba.se.spi.ior.IORTemplate;
/*     */ import com.sun.corba.se.spi.ior.IORTemplateList;
/*     */ import com.sun.corba.se.spi.ior.ObjectId;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
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
/*     */ public class IORTemplateListImpl
/*     */   extends FreezableList
/*     */   implements IORTemplateList
/*     */ {
/*     */   public Object set(int paramInt, Object paramObject) {
/*  52 */     if (paramObject instanceof IORTemplate)
/*  53 */       return super.set(paramInt, paramObject); 
/*  54 */     if (paramObject instanceof IORTemplateList) {
/*  55 */       Object object = remove(paramInt);
/*  56 */       add(paramInt, paramObject);
/*  57 */       return object;
/*     */     } 
/*  59 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(int paramInt, Object paramObject) {
/*  64 */     if (paramObject instanceof IORTemplate) {
/*  65 */       super.add(paramInt, paramObject);
/*  66 */     } else if (paramObject instanceof IORTemplateList) {
/*  67 */       IORTemplateList iORTemplateList = (IORTemplateList)paramObject;
/*  68 */       addAll(paramInt, (Collection<? extends E>)iORTemplateList);
/*     */     } else {
/*  70 */       throw new IllegalArgumentException();
/*     */     } 
/*     */   }
/*     */   
/*     */   public IORTemplateListImpl() {
/*  75 */     super(new ArrayList());
/*     */   }
/*     */ 
/*     */   
/*     */   public IORTemplateListImpl(InputStream paramInputStream) {
/*  80 */     this();
/*  81 */     int i = paramInputStream.read_long();
/*  82 */     for (byte b = 0; b < i; b++) {
/*  83 */       IORTemplate iORTemplate = IORFactories.makeIORTemplate(paramInputStream);
/*  84 */       add((E)iORTemplate);
/*     */     } 
/*     */     
/*  87 */     makeImmutable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void makeImmutable() {
/*  92 */     makeElementsImmutable();
/*  93 */     super.makeImmutable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(OutputStream paramOutputStream) {
/*  98 */     paramOutputStream.write_long(size());
/*  99 */     Iterator<E> iterator = iterator();
/* 100 */     while (iterator.hasNext()) {
/* 101 */       IORTemplate iORTemplate = (IORTemplate)iterator.next();
/* 102 */       iORTemplate.write(paramOutputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IOR makeIOR(ORB paramORB, String paramString, ObjectId paramObjectId) {
/* 108 */     return new IORImpl(paramORB, paramString, this, paramObjectId);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEquivalent(IORFactory paramIORFactory) {
/* 113 */     if (!(paramIORFactory instanceof IORTemplateList)) {
/* 114 */       return false;
/*     */     }
/* 116 */     IORTemplateList iORTemplateList = (IORTemplateList)paramIORFactory;
/*     */     
/* 118 */     Iterator<E> iterator = iterator();
/* 119 */     Iterator<IORTemplate> iterator1 = iORTemplateList.iterator();
/* 120 */     while (iterator.hasNext() && iterator1.hasNext()) {
/* 121 */       IORTemplate iORTemplate1 = (IORTemplate)iterator.next();
/* 122 */       IORTemplate iORTemplate2 = iterator1.next();
/* 123 */       if (!iORTemplate1.isEquivalent((IORFactory)iORTemplate2)) {
/* 124 */         return false;
/*     */       }
/*     */     } 
/* 127 */     return (iterator.hasNext() == iterator1.hasNext());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/IORTemplateListImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */