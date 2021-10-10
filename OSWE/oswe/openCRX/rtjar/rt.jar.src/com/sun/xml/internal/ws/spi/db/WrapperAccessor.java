/*    */ package com.sun.xml.internal.ws.spi.db;
/*    */ 
/*    */ import java.util.Map;
/*    */ import javax.xml.namespace.QName;
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
/*    */ public abstract class WrapperAccessor
/*    */ {
/*    */   protected Map<Object, PropertySetter> propertySetters;
/*    */   protected Map<Object, PropertyGetter> propertyGetters;
/*    */   protected boolean elementLocalNameCollision;
/*    */   
/*    */   protected PropertySetter getPropertySetter(QName name) {
/* 43 */     Object key = this.elementLocalNameCollision ? name : name.getLocalPart();
/* 44 */     return this.propertySetters.get(key);
/*    */   }
/*    */   protected PropertyGetter getPropertyGetter(QName name) {
/* 47 */     Object key = this.elementLocalNameCollision ? name : name.getLocalPart();
/* 48 */     return this.propertyGetters.get(key);
/*    */   }
/*    */   
/*    */   public PropertyAccessor getPropertyAccessor(String ns, String name) {
/* 52 */     QName n = new QName(ns, name);
/* 53 */     final PropertySetter setter = getPropertySetter(n);
/* 54 */     final PropertyGetter getter = getPropertyGetter(n);
/* 55 */     return new PropertyAccessor<Object, Object>() {
/*    */         public Object get(Object bean) throws DatabindingException {
/* 57 */           return getter.get(bean);
/*    */         }
/*    */         
/*    */         public void set(Object bean, Object value) throws DatabindingException {
/* 61 */           setter.set(bean, value);
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/spi/db/WrapperAccessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */