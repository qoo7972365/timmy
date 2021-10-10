/*     */ package com.sun.xml.internal.ws.client.sei;
/*     */ 
/*     */ import com.sun.xml.internal.ws.model.ParameterImpl;
/*     */ import com.sun.xml.internal.ws.spi.db.PropertyAccessor;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.Holder;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ public abstract class ValueSetter
/*     */ {
/*     */   private ValueSetter() {}
/*     */   
/*  74 */   private static final ValueSetter RETURN_VALUE = new ReturnValue();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   private static final ValueSetter[] POOL = new ValueSetter[16];
/*     */   
/*     */   static {
/*  82 */     for (int i = 0; i < POOL.length; i++) {
/*  83 */       POOL[i] = new Param(i);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static ValueSetter getSync(ParameterImpl p) {
/*  90 */     int idx = p.getIndex();
/*     */     
/*  92 */     if (idx == -1)
/*  93 */       return RETURN_VALUE; 
/*  94 */     if (idx < POOL.length) {
/*  95 */       return POOL[idx];
/*     */     }
/*  97 */     return new Param(idx);
/*     */   }
/*     */   
/*     */   private static final class ReturnValue
/*     */     extends ValueSetter {
/*     */     Object put(Object obj, Object[] args) {
/* 103 */       return obj;
/*     */     }
/*     */     
/*     */     private ReturnValue() {}
/*     */   }
/*     */   
/*     */   static final class Param
/*     */     extends ValueSetter {
/*     */     private final int idx;
/*     */     
/*     */     public Param(int idx) {
/* 114 */       this.idx = idx;
/*     */     }
/*     */     
/*     */     Object put(Object obj, Object[] args) {
/* 118 */       Object arg = args[this.idx];
/* 119 */       if (arg != null) {
/*     */         
/* 121 */         assert arg instanceof Holder;
/* 122 */         ((Holder)arg).value = obj;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 130 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   static final ValueSetter SINGLE_VALUE = new SingleValue();
/*     */   
/*     */   abstract Object put(Object paramObject, Object[] paramArrayOfObject);
/*     */   
/*     */   private static final class SingleValue
/*     */     extends ValueSetter
/*     */   {
/*     */     private SingleValue() {}
/*     */     
/*     */     Object put(Object obj, Object[] args) {
/* 147 */       args[0] = obj;
/* 148 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final class AsyncBeanValueSetter
/*     */     extends ValueSetter
/*     */   {
/*     */     private final PropertyAccessor accessor;
/*     */ 
/*     */     
/*     */     AsyncBeanValueSetter(ParameterImpl p, Class wrapper) {
/* 160 */       QName name = p.getName();
/*     */       try {
/* 162 */         this.accessor = p.getOwner().getBindingContext().getElementPropertyAccessor(wrapper, name
/* 163 */             .getNamespaceURI(), name.getLocalPart());
/* 164 */       } catch (JAXBException e) {
/* 165 */         throw new WebServiceException(wrapper + " do not have a property of the name " + name, e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object put(Object obj, Object[] args) {
/* 178 */       assert args != null;
/* 179 */       assert args.length == 1;
/* 180 */       assert args[0] != null;
/*     */       
/* 182 */       Object bean = args[0];
/*     */       try {
/* 184 */         this.accessor.set(bean, obj);
/* 185 */       } catch (Exception e) {
/* 186 */         throw new WebServiceException(e);
/*     */       } 
/* 188 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/sei/ValueSetter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */