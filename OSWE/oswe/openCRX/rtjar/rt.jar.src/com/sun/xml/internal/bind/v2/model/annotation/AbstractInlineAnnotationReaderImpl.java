/*    */ package com.sun.xml.internal.bind.v2.model.annotation;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.model.core.ErrorHandler;
/*    */ import com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationException;
/*    */ import java.lang.annotation.Annotation;
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
/*    */ public abstract class AbstractInlineAnnotationReaderImpl<T, C, F, M>
/*    */   implements AnnotationReader<T, C, F, M>
/*    */ {
/*    */   private ErrorHandler errorHandler;
/*    */   
/*    */   public void setErrorHandler(ErrorHandler errorHandler) {
/* 47 */     if (errorHandler == null)
/* 48 */       throw new IllegalArgumentException(); 
/* 49 */     this.errorHandler = errorHandler;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final ErrorHandler getErrorHandler() {
/* 56 */     assert this.errorHandler != null : "error handler must be set before use";
/* 57 */     return this.errorHandler;
/*    */   }
/*    */   
/*    */   public final <A extends Annotation> A getMethodAnnotation(Class<A> annotation, M getter, M setter, Locatable srcPos) {
/* 61 */     A a1 = (getter == null) ? null : (A)getMethodAnnotation(annotation, getter, srcPos);
/* 62 */     A a2 = (setter == null) ? null : (A)getMethodAnnotation(annotation, setter, srcPos);
/*    */     
/* 64 */     if (a1 == null) {
/* 65 */       if (a2 == null) {
/* 66 */         return null;
/*    */       }
/* 68 */       return a2;
/*    */     } 
/* 70 */     if (a2 == null) {
/* 71 */       return a1;
/*    */     }
/*    */     
/* 74 */     getErrorHandler().error(new IllegalAnnotationException(Messages.DUPLICATE_ANNOTATIONS
/* 75 */           .format(new Object[] {
/* 76 */               annotation.getName(), fullName(getter), fullName(setter)
/*    */             }, ), (Annotation)a1, (Annotation)a2));
/*    */     
/* 79 */     return a1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasMethodAnnotation(Class<? extends Annotation> annotation, String propertyName, M getter, M setter, Locatable srcPos) {
/* 85 */     boolean x = (getter != null && hasMethodAnnotation(annotation, getter));
/* 86 */     boolean y = (setter != null && hasMethodAnnotation(annotation, setter));
/*    */     
/* 88 */     if (x && y)
/*    */     {
/* 90 */       getMethodAnnotation(annotation, getter, setter, srcPos);
/*    */     }
/*    */     
/* 93 */     return (x || y);
/*    */   }
/*    */   
/*    */   protected abstract String fullName(M paramM);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/annotation/AbstractInlineAnnotationReaderImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */