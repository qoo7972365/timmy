/*     */ package com.sun.corba.se.spi.servicecontext;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Modifier;
/*     */ import org.omg.CORBA.BAD_PARAM;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServiceContextData
/*     */ {
/*     */   private Class scClass;
/*     */   private Constructor scConstructor;
/*     */   private int scId;
/*     */   
/*     */   private void dprint(String paramString) {
/*  44 */     ORBUtility.dprint(this, paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   private void throwBadParam(String paramString, Throwable paramThrowable) {
/*  49 */     BAD_PARAM bAD_PARAM = new BAD_PARAM(paramString);
/*  50 */     if (paramThrowable != null)
/*  51 */       bAD_PARAM.initCause(paramThrowable); 
/*  52 */     throw bAD_PARAM;
/*     */   }
/*     */ 
/*     */   
/*     */   public ServiceContextData(Class paramClass) {
/*  57 */     if (ORB.ORBInitDebug) {
/*  58 */       dprint("ServiceContextData constructor called for class " + paramClass);
/*     */     }
/*  60 */     this.scClass = paramClass;
/*     */     
/*     */     try {
/*  63 */       if (ORB.ORBInitDebug) {
/*  64 */         dprint("Finding constructor for " + paramClass);
/*     */       }
/*     */       
/*  67 */       Class[] arrayOfClass = new Class[2];
/*  68 */       arrayOfClass[0] = InputStream.class;
/*  69 */       arrayOfClass[1] = GIOPVersion.class;
/*     */       try {
/*  71 */         this.scConstructor = paramClass.getConstructor(arrayOfClass);
/*  72 */       } catch (NoSuchMethodException noSuchMethodException) {
/*  73 */         throwBadParam("Class does not have an InputStream constructor", noSuchMethodException);
/*     */       } 
/*     */       
/*  76 */       if (ORB.ORBInitDebug) {
/*  77 */         dprint("Finding SERVICE_CONTEXT_ID field in " + paramClass);
/*     */       }
/*     */       
/*  80 */       Field field = null;
/*     */       try {
/*  82 */         field = paramClass.getField("SERVICE_CONTEXT_ID");
/*  83 */       } catch (NoSuchFieldException noSuchFieldException) {
/*  84 */         throwBadParam("Class does not have a SERVICE_CONTEXT_ID member", noSuchFieldException);
/*  85 */       } catch (SecurityException securityException) {
/*  86 */         throwBadParam("Could not access SERVICE_CONTEXT_ID member", securityException);
/*     */       } 
/*     */       
/*  89 */       if (ORB.ORBInitDebug) {
/*  90 */         dprint("Checking modifiers of SERVICE_CONTEXT_ID field in " + paramClass);
/*     */       }
/*  92 */       int i = field.getModifiers();
/*  93 */       if (!Modifier.isPublic(i) || !Modifier.isStatic(i) || 
/*  94 */         !Modifier.isFinal(i)) {
/*  95 */         throwBadParam("SERVICE_CONTEXT_ID field is not public static final", null);
/*     */       }
/*  97 */       if (ORB.ORBInitDebug) {
/*  98 */         dprint("Getting value of SERVICE_CONTEXT_ID in " + paramClass);
/*     */       }
/*     */       try {
/* 101 */         this.scId = field.getInt(null);
/* 102 */       } catch (IllegalArgumentException illegalArgumentException) {
/* 103 */         throwBadParam("SERVICE_CONTEXT_ID not convertible to int", illegalArgumentException);
/* 104 */       } catch (IllegalAccessException illegalAccessException) {
/* 105 */         throwBadParam("Could not access value of SERVICE_CONTEXT_ID", illegalAccessException);
/*     */       } 
/* 107 */     } catch (BAD_PARAM bAD_PARAM) {
/* 108 */       if (ORB.ORBInitDebug)
/* 109 */         dprint("Exception in ServiceContextData constructor: " + bAD_PARAM); 
/* 110 */       throw bAD_PARAM;
/* 111 */     } catch (Throwable throwable) {
/* 112 */       if (ORB.ORBInitDebug) {
/* 113 */         dprint("Unexpected Exception in ServiceContextData constructor: " + throwable);
/*     */       }
/*     */     } 
/*     */     
/* 117 */     if (ORB.ORBInitDebug) {
/* 118 */       dprint("ServiceContextData constructor completed");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServiceContext makeServiceContext(InputStream paramInputStream, GIOPVersion paramGIOPVersion) {
/* 126 */     Object[] arrayOfObject = new Object[2];
/* 127 */     arrayOfObject[0] = paramInputStream;
/* 128 */     arrayOfObject[1] = paramGIOPVersion;
/* 129 */     ServiceContext serviceContext = null;
/*     */     
/*     */     try {
/* 132 */       serviceContext = this.scConstructor.newInstance(arrayOfObject);
/* 133 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 134 */       throwBadParam("InputStream constructor argument error", illegalArgumentException);
/* 135 */     } catch (IllegalAccessException illegalAccessException) {
/* 136 */       throwBadParam("InputStream constructor argument error", illegalAccessException);
/* 137 */     } catch (InstantiationException instantiationException) {
/* 138 */       throwBadParam("InputStream constructor called for abstract class", instantiationException);
/* 139 */     } catch (InvocationTargetException invocationTargetException) {
/* 140 */       throwBadParam("InputStream constructor threw exception " + invocationTargetException
/* 141 */           .getTargetException(), invocationTargetException);
/*     */     } 
/*     */     
/* 144 */     return serviceContext;
/*     */   }
/*     */ 
/*     */   
/*     */   int getId() {
/* 149 */     return this.scId;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 154 */     return "ServiceContextData[ scClass=" + this.scClass + " scConstructor=" + this.scConstructor + " scId=" + this.scId + " ]";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/servicecontext/ServiceContextData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */