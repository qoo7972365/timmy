/*     */ package sun.misc;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Stack;
/*     */ import java.util.jar.JarFile;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClassLoaderUtil
/*     */ {
/*     */   public static void releaseLoader(URLClassLoader paramURLClassLoader) {
/*  53 */     releaseLoader(paramURLClassLoader, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<IOException> releaseLoader(URLClassLoader paramURLClassLoader, List<String> paramList) {
/*  71 */     LinkedList<IOException> linkedList = new LinkedList();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  76 */       if (paramList != null) {
/*  77 */         paramList.clear();
/*     */       }
/*     */ 
/*     */       
/*  81 */       URLClassPath uRLClassPath = SharedSecrets.getJavaNetAccess().getURLClassPath(paramURLClassLoader);
/*  82 */       ArrayList<URLClassPath.Loader> arrayList = uRLClassPath.loaders;
/*  83 */       Stack<URL> stack = uRLClassPath.urls;
/*  84 */       HashMap<String, URLClassPath.Loader> hashMap = uRLClassPath.lmap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  94 */       synchronized (stack) {
/*  95 */         stack.clear();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 102 */       synchronized (hashMap) {
/* 103 */         hashMap.clear();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 129 */       synchronized (uRLClassPath) {
/* 130 */         for (URLClassPath.JarLoader jarLoader : arrayList) {
/* 131 */           if (jarLoader != null)
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 137 */             if (jarLoader instanceof URLClassPath.JarLoader) {
/* 138 */               URLClassPath.JarLoader jarLoader1 = jarLoader;
/* 139 */               JarFile jarFile = jarLoader1.getJarFile();
/*     */               try {
/* 141 */                 if (jarFile != null) {
/* 142 */                   jarFile.close();
/* 143 */                   if (paramList != null) {
/* 144 */                     paramList.add(jarFile.getName());
/*     */                   }
/*     */                 } 
/* 147 */               } catch (IOException iOException1) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 153 */                 String str1 = (jarFile == null) ? "filename not available" : jarFile.getName();
/* 154 */                 String str2 = "Error closing JAR file: " + str1;
/* 155 */                 IOException iOException2 = new IOException(str2);
/* 156 */                 iOException2.initCause(iOException1);
/* 157 */                 linkedList.add(iOException2);
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 165 */         arrayList.clear();
/*     */       } 
/* 167 */     } catch (Throwable throwable) {
/* 168 */       throw new RuntimeException(throwable);
/*     */     } 
/* 170 */     return linkedList;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/ClassLoaderUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */