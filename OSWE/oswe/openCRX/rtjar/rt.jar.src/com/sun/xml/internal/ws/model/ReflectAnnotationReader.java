/*    */ package com.sun.xml.internal.ws.model;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.databinding.MetadataReader;
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.lang.reflect.Method;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ import java.util.Map;
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
/*    */ public class ReflectAnnotationReader
/*    */   implements MetadataReader
/*    */ {
/*    */   public Annotation[] getAnnotations(Method m) {
/* 44 */     return m.getAnnotations();
/*    */   }
/*    */   
/*    */   public Annotation[][] getParameterAnnotations(final Method method) {
/* 48 */     return AccessController.<Annotation[][]>doPrivileged((PrivilegedAction)new PrivilegedAction<Annotation[][]>() {
/*    */           public Annotation[][] run() {
/* 50 */             return method.getParameterAnnotations();
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public <A extends Annotation> A getAnnotation(final Class<A> annType, final Method m) {
/* 56 */     return (A)AccessController.<Annotation>doPrivileged(new PrivilegedAction<A>() {
/*    */           public A run() {
/* 58 */             return m.getAnnotation(annType);
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public <A extends Annotation> A getAnnotation(final Class<A> annType, final Class<?> cls) {
/* 64 */     return (A)AccessController.<Annotation>doPrivileged(new PrivilegedAction<A>() {
/*    */           public A run() {
/* 66 */             return (A)cls.getAnnotation(annType);
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public Annotation[] getAnnotations(final Class<?> cls) {
/* 72 */     return AccessController.<Annotation[]>doPrivileged((PrivilegedAction)new PrivilegedAction<Annotation[]>() {
/*    */           public Annotation[] run() {
/* 74 */             return cls.getAnnotations();
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public void getProperties(Map<String, Object> prop, Class<?> cls) {}
/*    */   
/*    */   public void getProperties(Map<String, Object> prop, Method method) {}
/*    */   
/*    */   public void getProperties(Map<String, Object> prop, Method method, int pos) {}
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/model/ReflectAnnotationReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */