/*     */ package sun.awt.datatransfer;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashMap;
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
/*     */ final class ClassLoaderObjectOutputStream
/*     */   extends ObjectOutputStream
/*     */ {
/* 106 */   private final Map<Set<String>, ClassLoader> map = new HashMap<>();
/*     */ 
/*     */   
/*     */   ClassLoaderObjectOutputStream(OutputStream paramOutputStream) throws IOException {
/* 110 */     super(paramOutputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void annotateClass(final Class<?> cl) throws IOException {
/* 115 */     ClassLoader classLoader = AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>() {
/*     */           public Object run() {
/* 117 */             return cl.getClassLoader();
/*     */           }
/*     */         });
/*     */     
/* 121 */     HashSet<String> hashSet = new HashSet(1);
/* 122 */     hashSet.add(cl.getName());
/*     */     
/* 124 */     this.map.put(hashSet, classLoader);
/*     */   }
/*     */   
/*     */   protected void annotateProxyClass(final Class<?> cl) throws IOException {
/* 128 */     ClassLoader classLoader = AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>() {
/*     */           public Object run() {
/* 130 */             return cl.getClassLoader();
/*     */           }
/*     */         });
/*     */     
/* 134 */     Class[] arrayOfClass = cl.getInterfaces();
/* 135 */     HashSet<String> hashSet = new HashSet(arrayOfClass.length);
/* 136 */     for (byte b = 0; b < arrayOfClass.length; b++) {
/* 137 */       hashSet.add(arrayOfClass[b].getName());
/*     */     }
/*     */     
/* 140 */     this.map.put(hashSet, classLoader);
/*     */   }
/*     */   
/*     */   Map<Set<String>, ClassLoader> getClassLoaderMap() {
/* 144 */     return new HashMap<>(this.map);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/datatransfer/ClassLoaderObjectOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */