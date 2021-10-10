/*     */ package com.sun.corba.se.impl.presentation.rmi;
/*     */ 
/*     */ import com.sun.corba.se.pept.transport.ContactInfoList;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.orbutil.proxy.LinkedInvocationHandler;
/*     */ import com.sun.corba.se.spi.presentation.rmi.DynamicMethodMarshaller;
/*     */ import com.sun.corba.se.spi.presentation.rmi.PresentationManager;
/*     */ import com.sun.corba.se.spi.presentation.rmi.StubAdapter;
/*     */ import com.sun.corba.se.spi.protocol.CorbaClientDelegate;
/*     */ import com.sun.corba.se.spi.protocol.LocalClientRequestDispatcher;
/*     */ import com.sun.corba.se.spi.transport.CorbaContactInfoList;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import javax.rmi.CORBA.Util;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.SystemException;
/*     */ import org.omg.CORBA.portable.ApplicationException;
/*     */ import org.omg.CORBA.portable.Delegate;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA.portable.RemarshalException;
/*     */ import org.omg.CORBA.portable.ServantObject;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ import org.omg.CORBA_2_3.portable.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StubInvocationHandlerImpl
/*     */   implements LinkedInvocationHandler
/*     */ {
/*     */   private transient PresentationManager.ClassData classData;
/*     */   private transient PresentationManager pm;
/*     */   private transient Object stub;
/*     */   private transient Proxy self;
/*     */   
/*     */   public void setProxy(Proxy paramProxy) {
/*  80 */     this.self = paramProxy;
/*     */   }
/*     */ 
/*     */   
/*     */   public Proxy getProxy() {
/*  85 */     return this.self;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public StubInvocationHandlerImpl(PresentationManager paramPresentationManager, PresentationManager.ClassData paramClassData, Object paramObject) {
/*  91 */     SecurityManager securityManager = System.getSecurityManager();
/*  92 */     if (securityManager != null) {
/*  93 */       securityManager.checkPermission(new DynamicAccessPermission("access"));
/*     */     }
/*  95 */     this.classData = paramClassData;
/*  96 */     this.pm = paramPresentationManager;
/*  97 */     this.stub = paramObject;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isLocal() {
/* 102 */     boolean bool = false;
/* 103 */     Delegate delegate = StubAdapter.getDelegate(this.stub);
/*     */     
/* 105 */     if (delegate instanceof CorbaClientDelegate) {
/* 106 */       CorbaClientDelegate corbaClientDelegate = (CorbaClientDelegate)delegate;
/* 107 */       ContactInfoList contactInfoList = corbaClientDelegate.getContactInfoList();
/* 108 */       if (contactInfoList instanceof CorbaContactInfoList) {
/* 109 */         CorbaContactInfoList corbaContactInfoList = (CorbaContactInfoList)contactInfoList;
/*     */         
/* 111 */         LocalClientRequestDispatcher localClientRequestDispatcher = corbaContactInfoList.getLocalClientRequestDispatcher();
/* 112 */         bool = localClientRequestDispatcher.useLocalInvocation(null);
/*     */       } 
/*     */     } 
/*     */     
/* 116 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object invoke(Object paramObject, final Method method, Object[] paramArrayOfObject) throws Throwable {
/* 127 */     String str = this.classData.getIDLNameTranslator().getIDLName(method);
/*     */     
/* 129 */     DynamicMethodMarshaller dynamicMethodMarshaller = this.pm.getDynamicMethodMarshaller(method);
/*     */     
/* 131 */     Delegate delegate = null;
/*     */     try {
/* 133 */       delegate = StubAdapter.getDelegate(this.stub);
/* 134 */     } catch (SystemException systemException) {
/* 135 */       throw Util.mapSystemException(systemException);
/*     */     } 
/*     */     
/* 138 */     if (!isLocal()) {
/*     */       try {
/* 140 */         InputStream inputStream = null;
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 145 */           OutputStream outputStream = (OutputStream)delegate.request(this.stub, str, true);
/*     */ 
/*     */           
/* 148 */           dynamicMethodMarshaller.writeArguments(outputStream, paramArrayOfObject);
/*     */ 
/*     */ 
/*     */           
/* 152 */           inputStream = (InputStream)delegate.invoke(this.stub, (OutputStream)outputStream);
/*     */ 
/*     */           
/* 155 */           return dynamicMethodMarshaller.readResult(inputStream);
/* 156 */         } catch (ApplicationException applicationException) {
/* 157 */           throw dynamicMethodMarshaller.readException(applicationException);
/* 158 */         } catch (RemarshalException remarshalException) {
/* 159 */           return invoke(paramObject, method, paramArrayOfObject);
/*     */         } finally {
/* 161 */           delegate.releaseReply(this.stub, (InputStream)inputStream);
/*     */         } 
/* 163 */       } catch (SystemException systemException) {
/* 164 */         throw Util.mapSystemException(systemException);
/*     */       } 
/*     */     }
/*     */     
/* 168 */     ORB oRB = (ORB)delegate.orb(this.stub);
/* 169 */     ServantObject servantObject = delegate.servant_preinvoke(this.stub, str, method
/* 170 */         .getDeclaringClass());
/* 171 */     if (servantObject == null) {
/* 172 */       return invoke(this.stub, method, paramArrayOfObject);
/*     */     }
/*     */     try {
/* 175 */       Object[] arrayOfObject = dynamicMethodMarshaller.copyArguments(paramArrayOfObject, oRB);
/*     */       
/* 177 */       if (!method.isAccessible())
/*     */       {
/*     */ 
/*     */         
/* 181 */         AccessController.doPrivileged(new PrivilegedAction() {
/*     */               public Object run() {
/* 183 */                 method.setAccessible(true);
/* 184 */                 return null;
/*     */               }
/*     */             });
/*     */       }
/*     */       
/* 189 */       Object object = method.invoke(servantObject.servant, arrayOfObject);
/*     */       
/* 191 */       return dynamicMethodMarshaller.copyResult(object, oRB);
/* 192 */     } catch (InvocationTargetException invocationTargetException) {
/* 193 */       Throwable throwable1 = invocationTargetException.getCause();
/*     */       
/* 195 */       Throwable throwable2 = (Throwable)Util.copyObject(throwable1, (ORB)oRB);
/* 196 */       if (dynamicMethodMarshaller.isDeclaredException(throwable2)) {
/* 197 */         throw throwable2;
/*     */       }
/* 199 */       throw Util.wrapException(throwable2);
/* 200 */     } catch (Throwable throwable) {
/* 201 */       if (throwable instanceof ThreadDeath) {
/* 202 */         throw (ThreadDeath)throwable;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 207 */       throw Util.wrapException(throwable);
/*     */     } finally {
/* 209 */       delegate.servant_postinvoke(this.stub, servantObject);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/presentation/rmi/StubInvocationHandlerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */