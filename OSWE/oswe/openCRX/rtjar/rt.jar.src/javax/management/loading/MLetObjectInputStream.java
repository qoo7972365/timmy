/*     */ package javax.management.loading;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectStreamClass;
/*     */ import java.io.StreamCorruptedException;
/*     */ import java.lang.reflect.Array;
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
/*     */ class MLetObjectInputStream
/*     */   extends ObjectInputStream
/*     */ {
/*     */   private MLet loader;
/*     */   
/*     */   public MLetObjectInputStream(InputStream paramInputStream, MLet paramMLet) throws IOException, StreamCorruptedException {
/*  51 */     super(paramInputStream);
/*  52 */     if (paramMLet == null) {
/*  53 */       throw new IllegalArgumentException("Illegal null argument to MLetObjectInputStream");
/*     */     }
/*  55 */     this.loader = paramMLet;
/*     */   }
/*     */   
/*     */   private Class<?> primitiveType(char paramChar) {
/*  59 */     switch (paramChar) {
/*     */       case 'B':
/*  61 */         return byte.class;
/*     */       
/*     */       case 'C':
/*  64 */         return char.class;
/*     */       
/*     */       case 'D':
/*  67 */         return double.class;
/*     */       
/*     */       case 'F':
/*  70 */         return float.class;
/*     */       
/*     */       case 'I':
/*  73 */         return int.class;
/*     */       
/*     */       case 'J':
/*  76 */         return long.class;
/*     */       
/*     */       case 'S':
/*  79 */         return short.class;
/*     */       
/*     */       case 'Z':
/*  82 */         return boolean.class;
/*     */     } 
/*  84 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Class<?> resolveClass(ObjectStreamClass paramObjectStreamClass) throws IOException, ClassNotFoundException {
/*  94 */     String str = paramObjectStreamClass.getName();
/*  95 */     if (str.startsWith("[")) {
/*     */       Class<?> clazz; byte b1;
/*  97 */       for (b1 = 1; str.charAt(b1) == '['; b1++);
/*     */       
/*  99 */       if (str.charAt(b1) == 'L') {
/* 100 */         clazz = this.loader.loadClass(str.substring(b1 + 1, str.length() - 1));
/*     */       } else {
/* 102 */         if (str.length() != b1 + 1)
/* 103 */           throw new ClassNotFoundException(str); 
/* 104 */         clazz = primitiveType(str.charAt(b1));
/*     */       } 
/* 106 */       int[] arrayOfInt = new int[b1];
/* 107 */       for (byte b2 = 0; b2 < b1; b2++) {
/* 108 */         arrayOfInt[b2] = 0;
/*     */       }
/* 110 */       return Array.newInstance(clazz, arrayOfInt).getClass();
/*     */     } 
/* 112 */     return this.loader.loadClass(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassLoader getClassLoader() {
/* 120 */     return this.loader;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/loading/MLetObjectInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */