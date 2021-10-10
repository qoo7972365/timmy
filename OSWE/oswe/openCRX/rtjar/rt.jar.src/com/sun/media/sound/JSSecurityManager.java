/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.ServiceLoader;
/*     */ import javax.sound.sampled.AudioPermission;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class JSSecurityManager
/*     */ {
/*     */   private static boolean hasSecurityManager() {
/*  64 */     return (System.getSecurityManager() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void checkRecordPermission() throws SecurityException {
/*  70 */     SecurityManager securityManager = System.getSecurityManager();
/*  71 */     if (securityManager != null) {
/*  72 */       securityManager.checkPermission(new AudioPermission("record"));
/*     */     }
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
/*     */   static void loadProperties(final Properties properties, final String filename) {
/*  89 */     if (hasSecurityManager()) {
/*     */       
/*     */       try {
/*  92 */         PrivilegedAction<Void> privilegedAction = new PrivilegedAction<Void>() {
/*     */             public Void run() {
/*  94 */               JSSecurityManager.loadPropertiesImpl(properties, filename);
/*  95 */               return null;
/*     */             }
/*     */           };
/*  98 */         AccessController.doPrivileged(privilegedAction);
/*     */       }
/* 100 */       catch (Exception exception) {
/*     */ 
/*     */         
/* 103 */         loadPropertiesImpl(properties, filename);
/*     */       } 
/*     */     } else {
/*     */       
/* 107 */       loadPropertiesImpl(properties, filename);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void loadPropertiesImpl(Properties paramProperties, String paramString) {
/* 115 */     String str = System.getProperty("java.home");
/*     */     try {
/* 117 */       if (str == null) {
/* 118 */         throw new Error("Can't find java.home ??");
/*     */       }
/* 120 */       File file = new File(str, "lib");
/* 121 */       file = new File(file, paramString);
/* 122 */       str = file.getCanonicalPath();
/* 123 */       FileInputStream fileInputStream = new FileInputStream(str);
/* 124 */       BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
/*     */       try {
/* 126 */         paramProperties.load(bufferedInputStream);
/*     */       } finally {
/* 128 */         if (fileInputStream != null) {
/* 129 */           fileInputStream.close();
/*     */         }
/*     */       } 
/* 132 */     } catch (Throwable throwable) {}
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
/*     */   static Thread createThread(Runnable paramRunnable, String paramString, boolean paramBoolean1, int paramInt, boolean paramBoolean2) {
/* 147 */     Thread thread = new Thread(paramRunnable);
/* 148 */     if (paramString != null) {
/* 149 */       thread.setName(paramString);
/*     */     }
/* 151 */     thread.setDaemon(paramBoolean1);
/* 152 */     if (paramInt >= 0) {
/* 153 */       thread.setPriority(paramInt);
/*     */     }
/* 155 */     if (paramBoolean2) {
/* 156 */       thread.start();
/*     */     }
/* 158 */     return thread;
/*     */   }
/*     */   
/*     */   static synchronized <T> List<T> getProviders(final Class<T> providerClass) {
/* 162 */     ArrayList<T> arrayList = new ArrayList(7);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 167 */     PrivilegedAction<Iterator<T>> privilegedAction = (PrivilegedAction)new PrivilegedAction<Iterator<Iterator<T>>>()
/*     */       {
/*     */         public Iterator<T> run()
/*     */         {
/* 171 */           return ServiceLoader.<T>load(providerClass).iterator();
/*     */         }
/*     */       };
/* 174 */     final Iterator<Object> ps = AccessController.<Iterator>doPrivileged((PrivilegedAction)privilegedAction);
/*     */ 
/*     */ 
/*     */     
/* 178 */     PrivilegedAction<Boolean> privilegedAction1 = new PrivilegedAction<Boolean>() {
/*     */         public Boolean run() {
/* 180 */           return Boolean.valueOf(ps.hasNext());
/*     */         }
/*     */       };
/*     */     
/* 184 */     while (((Boolean)AccessController.<Boolean>doPrivileged(privilegedAction1)).booleanValue()) {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 189 */         Object object = iterator.next();
/* 190 */         if (providerClass.isInstance(object))
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 195 */           arrayList.add(0, object);
/*     */         }
/* 197 */       } catch (Throwable throwable) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 202 */     return arrayList;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/JSSecurityManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */