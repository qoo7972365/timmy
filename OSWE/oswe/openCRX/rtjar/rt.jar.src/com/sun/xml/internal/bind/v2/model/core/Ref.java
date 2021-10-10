/*    */ package com.sun.xml.internal.bind.v2.model.core;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.model.annotation.AnnotationReader;
/*    */ import com.sun.xml.internal.bind.v2.model.impl.ModelBuilderI;
/*    */ import com.sun.xml.internal.bind.v2.model.nav.Navigator;
/*    */ import javax.xml.bind.annotation.XmlList;
/*    */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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
/*    */ public final class Ref<T, C>
/*    */ {
/*    */   public final T type;
/*    */   public final Adapter<T, C> adapter;
/*    */   public final boolean valueList;
/*    */   
/*    */   public Ref(T type) {
/* 60 */     this(type, null, false);
/*    */   }
/*    */   public Ref(T type, Adapter<T, C> adapter, boolean valueList) {
/*    */     TypeT typeT;
/* 64 */     this.adapter = adapter;
/* 65 */     if (adapter != null)
/* 66 */       typeT = adapter.defaultType; 
/* 67 */     this.type = (T)typeT;
/* 68 */     this.valueList = valueList;
/*    */   }
/*    */   
/*    */   public Ref(ModelBuilderI<T, C, ?, ?> builder, T type, XmlJavaTypeAdapter xjta, XmlList xl) {
/* 72 */     this(builder.getReader(), builder.getNavigator(), type, xjta, xl);
/*    */   }
/*    */ 
/*    */   
/*    */   public Ref(AnnotationReader<T, C, ?, ?> reader, Navigator<T, C, ?, ?> nav, T type, XmlJavaTypeAdapter xjta, XmlList xl) {
/*    */     TypeT typeT;
/* 78 */     Adapter<T, C> adapter = null;
/* 79 */     if (xjta != null) {
/* 80 */       adapter = new Adapter<>(xjta, reader, nav);
/* 81 */       typeT = adapter.defaultType;
/*    */     } 
/*    */     
/* 84 */     this.type = (T)typeT;
/* 85 */     this.adapter = adapter;
/* 86 */     this.valueList = (xl != null);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/core/Ref.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */