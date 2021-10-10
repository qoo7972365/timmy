/*     */ package sun.applet;
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
/*     */ class AppletObjectInputStream
/*     */   extends ObjectInputStream
/*     */ {
/*     */   private AppletClassLoader loader;
/*     */   
/*     */   public AppletObjectInputStream(InputStream paramInputStream, AppletClassLoader paramAppletClassLoader) throws IOException, StreamCorruptedException {
/*  50 */     super(paramInputStream);
/*  51 */     if (paramAppletClassLoader == null) {
/*  52 */       throw new AppletIllegalArgumentException("appletillegalargumentexception.objectinputstream");
/*     */     }
/*     */     
/*  55 */     this.loader = paramAppletClassLoader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Class primitiveType(char paramChar) {
/*  63 */     switch (paramChar) { case 'B':
/*  64 */         return byte.class;
/*  65 */       case 'C': return char.class;
/*  66 */       case 'D': return double.class;
/*  67 */       case 'F': return float.class;
/*  68 */       case 'I': return int.class;
/*  69 */       case 'J': return long.class;
/*  70 */       case 'S': return short.class;
/*  71 */       case 'Z': return boolean.class; }
/*  72 */      return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Class resolveClass(ObjectStreamClass paramObjectStreamClass) throws IOException, ClassNotFoundException {
/*  82 */     String str = paramObjectStreamClass.getName();
/*  83 */     if (str.startsWith("[")) {
/*     */       Class<?> clazz;
/*     */       
/*     */       byte b1;
/*  87 */       for (b1 = 1; str.charAt(b1) == '['; b1++);
/*  88 */       if (str.charAt(b1) == 'L') {
/*  89 */         clazz = this.loader.loadClass(str.substring(b1 + 1, str
/*  90 */               .length() - 1));
/*     */       } else {
/*  92 */         if (str.length() != b1 + 1) {
/*  93 */           throw new ClassNotFoundException(str);
/*     */         }
/*  95 */         clazz = primitiveType(str.charAt(b1));
/*     */       } 
/*  97 */       int[] arrayOfInt = new int[b1];
/*  98 */       for (byte b2 = 0; b2 < b1; b2++) {
/*  99 */         arrayOfInt[b2] = 0;
/*     */       }
/* 101 */       return Array.newInstance(clazz, arrayOfInt).getClass();
/*     */     } 
/* 103 */     return this.loader.loadClass(str);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/applet/AppletObjectInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */