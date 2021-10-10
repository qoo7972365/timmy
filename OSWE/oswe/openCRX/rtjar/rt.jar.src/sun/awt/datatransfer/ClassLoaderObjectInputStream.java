/*     */ package sun.awt.datatransfer;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectStreamClass;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ final class ClassLoaderObjectInputStream
/*     */   extends ObjectInputStream
/*     */ {
/*     */   private final Map<Set<String>, ClassLoader> map;
/*     */   
/*     */   ClassLoaderObjectInputStream(InputStream paramInputStream, Map<Set<String>, ClassLoader> paramMap) throws IOException {
/* 154 */     super(paramInputStream);
/* 155 */     if (paramMap == null) {
/* 156 */       throw new NullPointerException("Null map");
/*     */     }
/* 158 */     this.map = paramMap;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Class<?> resolveClass(ObjectStreamClass paramObjectStreamClass) throws IOException, ClassNotFoundException {
/* 163 */     String str = paramObjectStreamClass.getName();
/*     */     
/* 165 */     HashSet<String> hashSet = new HashSet(1);
/* 166 */     hashSet.add(str);
/*     */     
/* 168 */     ClassLoader classLoader = this.map.get(hashSet);
/* 169 */     if (classLoader != null) {
/* 170 */       return Class.forName(str, false, classLoader);
/*     */     }
/* 172 */     return super.resolveClass(paramObjectStreamClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Class<?> resolveProxyClass(String[] paramArrayOfString) throws IOException, ClassNotFoundException {
/* 179 */     HashSet<String> hashSet = new HashSet(paramArrayOfString.length);
/* 180 */     for (byte b1 = 0; b1 < paramArrayOfString.length; b1++) {
/* 181 */       hashSet.add(paramArrayOfString[b1]);
/*     */     }
/*     */     
/* 184 */     ClassLoader classLoader1 = this.map.get(hashSet);
/* 185 */     if (classLoader1 == null) {
/* 186 */       return super.resolveProxyClass(paramArrayOfString);
/*     */     }
/*     */ 
/*     */     
/* 190 */     ClassLoader classLoader2 = null;
/* 191 */     boolean bool = false;
/*     */ 
/*     */     
/* 194 */     Class[] arrayOfClass = new Class[paramArrayOfString.length];
/* 195 */     for (byte b2 = 0; b2 < paramArrayOfString.length; b2++) {
/* 196 */       Class<?> clazz = Class.forName(paramArrayOfString[b2], false, classLoader1);
/* 197 */       if ((clazz.getModifiers() & 0x1) == 0) {
/* 198 */         if (bool) {
/* 199 */           if (classLoader2 != clazz.getClassLoader()) {
/* 200 */             throw new IllegalAccessError("conflicting non-public interface class loaders");
/*     */           }
/*     */         } else {
/*     */           
/* 204 */           classLoader2 = clazz.getClassLoader();
/* 205 */           bool = true;
/*     */         } 
/*     */       }
/* 208 */       arrayOfClass[b2] = clazz;
/*     */     } 
/*     */     try {
/* 211 */       return Proxy.getProxyClass(bool ? classLoader2 : classLoader1, arrayOfClass);
/*     */     
/*     */     }
/* 214 */     catch (IllegalArgumentException illegalArgumentException) {
/* 215 */       throw new ClassNotFoundException(null, illegalArgumentException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/datatransfer/ClassLoaderObjectInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */