/*    */ package com.sun.jmx.mbeanserver;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectStreamClass;
/*    */ import sun.reflect.misc.ReflectUtil;
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
/*    */ class ObjectInputStreamWithLoader
/*    */   extends ObjectInputStream
/*    */ {
/*    */   private ClassLoader loader;
/*    */   
/*    */   public ObjectInputStreamWithLoader(InputStream paramInputStream, ClassLoader paramClassLoader) throws IOException {
/* 53 */     super(paramInputStream);
/* 54 */     this.loader = paramClassLoader;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected Class<?> resolveClass(ObjectStreamClass paramObjectStreamClass) throws IOException, ClassNotFoundException {
/* 60 */     if (this.loader == null) {
/* 61 */       return super.resolveClass(paramObjectStreamClass);
/*    */     }
/* 63 */     String str = paramObjectStreamClass.getName();
/* 64 */     ReflectUtil.checkPackageAccess(str);
/*    */     
/* 66 */     return Class.forName(str, false, this.loader);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/mbeanserver/ObjectInputStreamWithLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */