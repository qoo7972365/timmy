/*     */ package com.sun.corba.se.impl.interceptors;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.InterceptorsSystemException;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import org.omg.PortableInterceptor.ClientRequestInterceptor;
/*     */ import org.omg.PortableInterceptor.IORInterceptor;
/*     */ import org.omg.PortableInterceptor.Interceptor;
/*     */ import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;
/*     */ import org.omg.PortableInterceptor.ServerRequestInterceptor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InterceptorList
/*     */ {
/*     */   static final int INTERCEPTOR_TYPE_CLIENT = 0;
/*     */   static final int INTERCEPTOR_TYPE_SERVER = 1;
/*     */   static final int INTERCEPTOR_TYPE_IOR = 2;
/*     */   static final int NUM_INTERCEPTOR_TYPES = 3;
/*  66 */   static final Class[] classTypes = new Class[] { ClientRequestInterceptor.class, ServerRequestInterceptor.class, IORInterceptor.class };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean locked = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InterceptorsSystemException wrapper;
/*     */ 
/*     */ 
/*     */   
/*  80 */   private Interceptor[][] interceptors = new Interceptor[3][];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   InterceptorList(InterceptorsSystemException paramInterceptorsSystemException) {
/*  88 */     this.wrapper = paramInterceptorsSystemException;
/*     */     
/*  90 */     initInterceptorArrays();
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
/*     */   
/*     */   void register_interceptor(Interceptor paramInterceptor, int paramInt) throws DuplicateName {
/* 109 */     if (this.locked) {
/* 110 */       throw this.wrapper.interceptorListLocked();
/*     */     }
/*     */ 
/*     */     
/* 114 */     String str = paramInterceptor.name();
/* 115 */     boolean bool = str.equals("");
/* 116 */     boolean bool1 = false;
/* 117 */     Interceptor[] arrayOfInterceptor = this.interceptors[paramInt];
/*     */ 
/*     */ 
/*     */     
/* 121 */     if (!bool) {
/* 122 */       int i = arrayOfInterceptor.length;
/*     */ 
/*     */ 
/*     */       
/* 126 */       for (byte b = 0; b < i; b++) {
/* 127 */         Interceptor interceptor = arrayOfInterceptor[b];
/* 128 */         if (interceptor.name().equals(str)) {
/* 129 */           bool1 = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 135 */     if (!bool1) {
/* 136 */       growInterceptorArray(paramInt);
/* 137 */       this.interceptors[paramInt][(this.interceptors[paramInt]).length - 1] = paramInterceptor;
/*     */     } else {
/*     */       
/* 140 */       throw new DuplicateName(str);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void lock() {
/* 150 */     this.locked = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Interceptor[] getInterceptors(int paramInt) {
/* 158 */     return this.interceptors[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean hasInterceptorsOfType(int paramInt) {
/* 166 */     return ((this.interceptors[paramInt]).length > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initInterceptorArrays() {
/* 174 */     for (byte b = 0; b < 3; b++) {
/* 175 */       Class<?> clazz = classTypes[b];
/*     */ 
/*     */       
/* 178 */       this.interceptors[b] = 
/* 179 */         (Interceptor[])Array.newInstance(clazz, 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void growInterceptorArray(int paramInt) {
/* 187 */     Class<?> clazz = classTypes[paramInt];
/* 188 */     int i = (this.interceptors[paramInt]).length;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 194 */     Interceptor[] arrayOfInterceptor = (Interceptor[])Array.newInstance(clazz, i + 1);
/* 195 */     System.arraycopy(this.interceptors[paramInt], 0, arrayOfInterceptor, 0, i);
/*     */     
/* 197 */     this.interceptors[paramInt] = arrayOfInterceptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void destroyAll() {
/* 205 */     int i = this.interceptors.length;
/*     */     
/* 207 */     for (byte b = 0; b < i; b++) {
/* 208 */       int j = (this.interceptors[b]).length;
/* 209 */       for (byte b1 = 0; b1 < j; b1++) {
/* 210 */         this.interceptors[b][b1].destroy();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void sortInterceptors() {
/* 219 */     ArrayList<Interceptor> arrayList1 = null;
/* 220 */     ArrayList<Interceptor> arrayList2 = null;
/*     */     
/* 222 */     int i = this.interceptors.length;
/*     */     
/* 224 */     for (byte b = 0; b < i; b++) {
/* 225 */       int j = (this.interceptors[b]).length;
/* 226 */       if (j > 0) {
/*     */         
/* 228 */         arrayList1 = new ArrayList();
/* 229 */         arrayList2 = new ArrayList();
/*     */       } 
/* 231 */       for (byte b1 = 0; b1 < j; b1++) {
/* 232 */         Interceptor interceptor = this.interceptors[b][b1];
/* 233 */         if (interceptor instanceof Comparable) {
/* 234 */           arrayList1.add(interceptor);
/*     */         } else {
/* 236 */           arrayList2.add(interceptor);
/*     */         } 
/*     */       } 
/* 239 */       if (j > 0 && arrayList1.size() > 0) {
/*     */ 
/*     */ 
/*     */         
/* 243 */         Collections.sort(arrayList1);
/* 244 */         Iterator<Interceptor> iterator1 = arrayList1.iterator();
/* 245 */         Iterator<Interceptor> iterator2 = arrayList2.iterator();
/* 246 */         for (byte b2 = 0; b2 < j; b2++) {
/* 247 */           if (iterator1.hasNext()) {
/* 248 */             this.interceptors[b][b2] = iterator1
/* 249 */               .next();
/* 250 */           } else if (iterator2.hasNext()) {
/* 251 */             this.interceptors[b][b2] = iterator2
/* 252 */               .next();
/*     */           } else {
/* 254 */             throw this.wrapper.sortSizeMismatch();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/interceptors/InterceptorList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */