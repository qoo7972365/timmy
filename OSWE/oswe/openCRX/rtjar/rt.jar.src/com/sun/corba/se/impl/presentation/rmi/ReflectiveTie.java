/*     */ package com.sun.corba.se.impl.presentation.rmi;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.presentation.rmi.DynamicMethodMarshaller;
/*     */ import com.sun.corba.se.spi.presentation.rmi.PresentationManager;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.rmi.Remote;
/*     */ import javax.rmi.CORBA.Tie;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.SystemException;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA.portable.ResponseHandler;
/*     */ import org.omg.CORBA.portable.UnknownException;
/*     */ import org.omg.CORBA_2_3.ORB;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ import org.omg.CORBA_2_3.portable.OutputStream;
/*     */ import org.omg.PortableServer.POA;
/*     */ import org.omg.PortableServer.POAPackage.ObjectNotActive;
/*     */ import org.omg.PortableServer.POAPackage.ServantNotActive;
/*     */ import org.omg.PortableServer.POAPackage.WrongPolicy;
/*     */ import org.omg.PortableServer.Servant;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ReflectiveTie
/*     */   extends Servant
/*     */   implements Tie
/*     */ {
/*  57 */   private Remote target = null;
/*     */   private PresentationManager pm;
/*  59 */   private PresentationManager.ClassData classData = null;
/*  60 */   private ORBUtilSystemException wrapper = null;
/*     */ 
/*     */   
/*     */   public ReflectiveTie(PresentationManager paramPresentationManager, ORBUtilSystemException paramORBUtilSystemException) {
/*  64 */     SecurityManager securityManager = System.getSecurityManager();
/*  65 */     if (securityManager != null) {
/*  66 */       securityManager.checkPermission(new DynamicAccessPermission("access"));
/*     */     }
/*  68 */     this.pm = paramPresentationManager;
/*  69 */     this.wrapper = paramORBUtilSystemException;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] _all_interfaces(POA paramPOA, byte[] paramArrayOfbyte) {
/*  75 */     return this.classData.getTypeIds();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTarget(Remote paramRemote) {
/*  80 */     this.target = paramRemote;
/*     */     
/*  82 */     if (paramRemote == null) {
/*  83 */       this.classData = null;
/*     */     } else {
/*  85 */       Class<?> clazz = paramRemote.getClass();
/*  86 */       this.classData = this.pm.getClassData(clazz);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Remote getTarget() {
/*  92 */     return this.target;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object thisObject() {
/*  97 */     return _this_object();
/*     */   }
/*     */ 
/*     */   
/*     */   public void deactivate() {
/*     */     try {
/* 103 */       _poa().deactivate_object(_poa().servant_to_id(this));
/* 104 */     } catch (WrongPolicy wrongPolicy) {
/*     */     
/* 106 */     } catch (ObjectNotActive objectNotActive) {
/*     */     
/* 108 */     } catch (ServantNotActive servantNotActive) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ORB orb() {
/* 114 */     return _orb();
/*     */   }
/*     */   
/*     */   public void orb(ORB paramORB) {
/*     */     try {
/* 119 */       ORB oRB = (ORB)paramORB;
/*     */       
/* 121 */       ((ORB)paramORB).set_delegate(this);
/* 122 */     } catch (ClassCastException classCastException) {
/* 123 */       throw this.wrapper.badOrbForServant(classCastException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public OutputStream _invoke(String paramString, InputStream paramInputStream, ResponseHandler paramResponseHandler) {
/* 130 */     Method method = null;
/* 131 */     DynamicMethodMarshaller dynamicMethodMarshaller = null;
/*     */     
/*     */     try {
/* 134 */       InputStream inputStream = (InputStream)paramInputStream;
/*     */       
/* 136 */       method = this.classData.getIDLNameTranslator().getMethod(paramString);
/* 137 */       if (method == null) {
/* 138 */         throw this.wrapper.methodNotFoundInTie(paramString, this.target
/* 139 */             .getClass().getName());
/*     */       }
/* 141 */       dynamicMethodMarshaller = this.pm.getDynamicMethodMarshaller(method);
/*     */       
/* 143 */       Object[] arrayOfObject = dynamicMethodMarshaller.readArguments(inputStream);
/*     */       
/* 145 */       Object object = method.invoke(this.target, arrayOfObject);
/*     */       
/* 147 */       OutputStream outputStream = (OutputStream)paramResponseHandler.createReply();
/*     */       
/* 149 */       dynamicMethodMarshaller.writeResult(outputStream, object);
/*     */       
/* 151 */       return (OutputStream)outputStream;
/* 152 */     } catch (IllegalAccessException illegalAccessException) {
/* 153 */       throw this.wrapper.invocationErrorInReflectiveTie(illegalAccessException, method
/* 154 */           .getName(), method
/* 155 */           .getDeclaringClass().getName());
/* 156 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 157 */       throw this.wrapper.invocationErrorInReflectiveTie(illegalArgumentException, method
/* 158 */           .getName(), method
/* 159 */           .getDeclaringClass().getName());
/* 160 */     } catch (InvocationTargetException invocationTargetException) {
/*     */ 
/*     */ 
/*     */       
/* 164 */       Throwable throwable = invocationTargetException.getCause();
/* 165 */       if (throwable instanceof SystemException)
/* 166 */         throw (SystemException)throwable; 
/* 167 */       if (throwable instanceof Exception && dynamicMethodMarshaller
/* 168 */         .isDeclaredException(throwable)) {
/* 169 */         OutputStream outputStream = (OutputStream)paramResponseHandler.createExceptionReply();
/* 170 */         dynamicMethodMarshaller.writeException(outputStream, (Exception)throwable);
/* 171 */         return (OutputStream)outputStream;
/*     */       } 
/* 173 */       throw new UnknownException(throwable);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/presentation/rmi/ReflectiveTie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */