/*    */ package com.sun.xml.internal.bind.v2.model.impl;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.model.annotation.Locatable;
/*    */ import com.sun.xml.internal.bind.v2.model.core.NonElement;
/*    */ import com.sun.xml.internal.bind.v2.model.nav.Navigator;
/*    */ import com.sun.xml.internal.bind.v2.runtime.Location;
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
/*    */ 
/*    */ 
/*    */ class AnyTypeImpl<T, C>
/*    */   implements NonElement<T, C>
/*    */ {
/*    */   private final T type;
/*    */   private final Navigator<T, C, ?, ?> nav;
/*    */   
/*    */   public AnyTypeImpl(Navigator<T, C, ?, ?> nav) {
/* 48 */     this.type = (T)nav.ref(Object.class);
/* 49 */     this.nav = nav;
/*    */   }
/*    */   
/*    */   public QName getTypeName() {
/* 53 */     return ANYTYPE_NAME;
/*    */   }
/*    */   
/*    */   public T getType() {
/* 57 */     return this.type;
/*    */   }
/*    */   
/*    */   public Locatable getUpstream() {
/* 61 */     return null;
/*    */   }
/*    */   
/*    */   public boolean isSimpleType() {
/* 65 */     return false;
/*    */   }
/*    */   
/*    */   public Location getLocation() {
/* 69 */     return this.nav.getClassLocation(this.nav.asDecl(Object.class));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final boolean canBeReferencedByIDREF() {
/* 79 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/impl/AnyTypeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */