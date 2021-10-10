/*     */ package com.sun.corba.se.impl.resolver;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.ior.IORFactories;
/*     */ import com.sun.corba.se.spi.ior.IORTemplate;
/*     */ import com.sun.corba.se.spi.ior.ObjectKey;
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPAddress;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPFactories;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPProfileTemplate;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.resolver.Resolver;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.portable.ApplicationException;
/*     */ import org.omg.CORBA.portable.Delegate;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA.portable.RemarshalException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BootstrapResolverImpl
/*     */   implements Resolver
/*     */ {
/*     */   private Delegate bootstrapDelegate;
/*     */   private ORBUtilSystemException wrapper;
/*     */   
/*     */   public BootstrapResolverImpl(ORB paramORB, String paramString, int paramInt) {
/*  54 */     this.wrapper = ORBUtilSystemException.get(paramORB, "orb.resolver");
/*     */ 
/*     */ 
/*     */     
/*  58 */     byte[] arrayOfByte = "INIT".getBytes();
/*  59 */     ObjectKey objectKey = paramORB.getObjectKeyFactory().create(arrayOfByte);
/*     */     
/*  61 */     IIOPAddress iIOPAddress = IIOPFactories.makeIIOPAddress(paramORB, paramString, paramInt);
/*  62 */     IIOPProfileTemplate iIOPProfileTemplate = IIOPFactories.makeIIOPProfileTemplate(paramORB, GIOPVersion.V1_0, iIOPAddress);
/*     */ 
/*     */     
/*  65 */     IORTemplate iORTemplate = IORFactories.makeIORTemplate(objectKey.getTemplate());
/*  66 */     iORTemplate.add(iIOPProfileTemplate);
/*     */     
/*  68 */     IOR iOR = iORTemplate.makeIOR(paramORB, "", objectKey
/*  69 */         .getId());
/*     */     
/*  71 */     this.bootstrapDelegate = (Delegate)ORBUtility.makeClientDelegate(iOR);
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
/*     */   private InputStream invoke(String paramString1, String paramString2) {
/*  84 */     boolean bool = true;
/*     */ 
/*     */ 
/*     */     
/*  88 */     InputStream inputStream = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     while (bool) {
/*  96 */       Object object = null;
/*  97 */       bool = false;
/*     */       
/*  99 */       OutputStream outputStream = this.bootstrapDelegate.request(object, paramString1, true);
/*     */ 
/*     */       
/* 102 */       if (paramString2 != null) {
/* 103 */         outputStream.write_string(paramString2);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 114 */         inputStream = this.bootstrapDelegate.invoke(object, outputStream);
/* 115 */       } catch (ApplicationException applicationException) {
/* 116 */         throw this.wrapper.bootstrapApplicationException(applicationException);
/* 117 */       } catch (RemarshalException remarshalException) {
/*     */         
/* 119 */         bool = true;
/*     */       } 
/*     */     } 
/*     */     
/* 123 */     return inputStream;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object resolve(String paramString) {
/* 128 */     InputStream inputStream = null;
/* 129 */     Object object = null;
/*     */     
/*     */     try {
/* 132 */       inputStream = invoke("get", paramString);
/*     */       
/* 134 */       object = inputStream.read_Object();
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 139 */       this.bootstrapDelegate.releaseReply(null, inputStream);
/*     */     } 
/*     */     
/* 142 */     return object;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set list() {
/* 147 */     InputStream inputStream = null;
/* 148 */     HashSet<String> hashSet = new HashSet();
/*     */     
/*     */     try {
/* 151 */       inputStream = invoke("list", null);
/*     */       
/* 153 */       int i = inputStream.read_long();
/* 154 */       for (byte b = 0; b < i; b++) {
/* 155 */         hashSet.add(inputStream.read_string());
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 160 */       this.bootstrapDelegate.releaseReply(null, inputStream);
/*     */     } 
/*     */     
/* 163 */     return hashSet;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/resolver/BootstrapResolverImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */