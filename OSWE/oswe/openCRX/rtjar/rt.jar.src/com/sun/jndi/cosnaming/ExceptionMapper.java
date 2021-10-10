/*     */ package com.sun.jndi.cosnaming;
/*     */ 
/*     */ import com.sun.jndi.toolkit.corba.CorbaUtils;
/*     */ import javax.naming.CannotProceedException;
/*     */ import javax.naming.CompositeName;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.ContextNotEmptyException;
/*     */ import javax.naming.InvalidNameException;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NameAlreadyBoundException;
/*     */ import javax.naming.NameNotFoundException;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.RefAddr;
/*     */ import javax.naming.Reference;
/*     */ import javax.naming.spi.NamingManager;
/*     */ import org.omg.CosNaming.NameComponent;
/*     */ import org.omg.CosNaming.NamingContext;
/*     */ import org.omg.CosNaming.NamingContextPackage.CannotProceed;
/*     */ import org.omg.CosNaming.NamingContextPackage.NotFound;
/*     */ import org.omg.CosNaming.NamingContextPackage.NotFoundReason;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ExceptionMapper
/*     */ {
/*     */   private static final boolean debug = false;
/*     */   
/*     */   public static final NamingException mapException(Exception paramException, CNCtx paramCNCtx, NameComponent[] paramArrayOfNameComponent) throws NamingException {
/*     */     NamingException namingException;
/*  49 */     if (paramException instanceof NamingException) {
/*  50 */       return (NamingException)paramException;
/*     */     }
/*     */     
/*  53 */     if (paramException instanceof RuntimeException) {
/*  54 */       throw (RuntimeException)paramException;
/*     */     }
/*     */ 
/*     */     
/*  58 */     if (paramException instanceof NotFound) {
/*  59 */       if (paramCNCtx.federation) {
/*  60 */         return tryFed((NotFound)paramException, paramCNCtx, paramArrayOfNameComponent);
/*     */       }
/*     */       
/*  63 */       namingException = new NameNotFoundException();
/*     */     
/*     */     }
/*  66 */     else if (paramException instanceof CannotProceed) {
/*     */       
/*  68 */       namingException = new CannotProceedException();
/*  69 */       NamingContext namingContext = ((CannotProceed)paramException).cxt;
/*  70 */       NameComponent[] arrayOfNameComponent = ((CannotProceed)paramException).rest_of_name;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  75 */       if (paramArrayOfNameComponent != null && paramArrayOfNameComponent.length > arrayOfNameComponent.length) {
/*  76 */         NameComponent[] arrayOfNameComponent1 = new NameComponent[paramArrayOfNameComponent.length - arrayOfNameComponent.length];
/*     */         
/*  78 */         System.arraycopy(paramArrayOfNameComponent, 0, arrayOfNameComponent1, 0, arrayOfNameComponent1.length);
/*     */ 
/*     */ 
/*     */         
/*  82 */         namingException.setResolvedObj(new CNCtx(paramCNCtx._orb, paramCNCtx.orbTracker, namingContext, paramCNCtx._env, paramCNCtx
/*     */               
/*  84 */               .makeFullName(arrayOfNameComponent1)));
/*     */       } else {
/*  86 */         namingException.setResolvedObj(paramCNCtx);
/*     */       } 
/*     */       
/*  89 */       namingException.setRemainingName(CNNameParser.cosNameToName(arrayOfNameComponent));
/*     */     }
/*  91 */     else if (paramException instanceof org.omg.CosNaming.NamingContextPackage.InvalidName) {
/*  92 */       namingException = new InvalidNameException();
/*  93 */     } else if (paramException instanceof org.omg.CosNaming.NamingContextPackage.AlreadyBound) {
/*  94 */       namingException = new NameAlreadyBoundException();
/*  95 */     } else if (paramException instanceof org.omg.CosNaming.NamingContextPackage.NotEmpty) {
/*  96 */       namingException = new ContextNotEmptyException();
/*     */     } else {
/*  98 */       namingException = new NamingException("Unknown reasons");
/*     */     } 
/*     */     
/* 101 */     namingException.setRootCause(paramException);
/* 102 */     return namingException;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final NamingException tryFed(NotFound paramNotFound, CNCtx paramCNCtx, NameComponent[] paramArrayOfNameComponent) throws NamingException {
/* 107 */     NameComponent[] arrayOfNameComponent1 = paramNotFound.rest_of_name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     if (arrayOfNameComponent1.length == 1 && paramArrayOfNameComponent != null) {
/*     */       
/* 120 */       NameComponent nameComponent = paramArrayOfNameComponent[paramArrayOfNameComponent.length - 1];
/* 121 */       if (!(arrayOfNameComponent1[0]).id.equals(nameComponent.id) || (arrayOfNameComponent1[0]).kind == null || 
/*     */         
/* 123 */         !(arrayOfNameComponent1[0]).kind.equals(nameComponent.kind)) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 128 */         NameNotFoundException nameNotFoundException = new NameNotFoundException();
/* 129 */         nameNotFoundException.setRemainingName(CNNameParser.cosNameToName(arrayOfNameComponent1));
/* 130 */         nameNotFoundException.setRootCause((Throwable)paramNotFound);
/* 131 */         throw nameNotFoundException;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 137 */     NameComponent[] arrayOfNameComponent2 = null;
/* 138 */     int i = 0;
/* 139 */     if (paramArrayOfNameComponent != null && paramArrayOfNameComponent.length >= arrayOfNameComponent1.length) {
/*     */       
/* 141 */       if (paramNotFound.why == NotFoundReason.not_context) {
/*     */ 
/*     */         
/* 144 */         i = paramArrayOfNameComponent.length - arrayOfNameComponent1.length - 1;
/*     */ 
/*     */         
/* 147 */         if (arrayOfNameComponent1.length == 1) {
/*     */           
/* 149 */           arrayOfNameComponent1 = null;
/*     */         } else {
/* 151 */           NameComponent[] arrayOfNameComponent = new NameComponent[arrayOfNameComponent1.length - 1];
/* 152 */           System.arraycopy(arrayOfNameComponent1, 1, arrayOfNameComponent, 0, arrayOfNameComponent.length);
/* 153 */           arrayOfNameComponent1 = arrayOfNameComponent;
/*     */         } 
/*     */       } else {
/* 156 */         i = paramArrayOfNameComponent.length - arrayOfNameComponent1.length;
/*     */       } 
/*     */       
/* 159 */       if (i > 0) {
/* 160 */         arrayOfNameComponent2 = new NameComponent[i];
/* 161 */         System.arraycopy(paramArrayOfNameComponent, 0, arrayOfNameComponent2, 0, i);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 166 */     CannotProceedException cannotProceedException = new CannotProceedException();
/* 167 */     cannotProceedException.setRootCause((Throwable)paramNotFound);
/* 168 */     if (arrayOfNameComponent1 != null && arrayOfNameComponent1.length > 0) {
/* 169 */       cannotProceedException.setRemainingName(CNNameParser.cosNameToName(arrayOfNameComponent1));
/*     */     }
/* 171 */     cannotProceedException.setEnvironment(paramCNCtx._env);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 179 */     final Object resolvedObj = (arrayOfNameComponent2 != null) ? paramCNCtx.callResolve(arrayOfNameComponent2) : paramCNCtx;
/*     */     
/* 181 */     if (object1 instanceof Context) {
/*     */ 
/*     */ 
/*     */       
/* 185 */       RefAddr refAddr = new RefAddr("nns") {
/*     */           public Object getContent() {
/* 187 */             return resolvedObj;
/*     */           }
/*     */           
/*     */           private static final long serialVersionUID = 669984699392133792L;
/*     */         };
/* 192 */       Reference reference = new Reference("java.lang.Object", refAddr);
/*     */ 
/*     */       
/* 195 */       CompositeName compositeName = new CompositeName();
/* 196 */       compositeName.add("");
/*     */       
/* 198 */       cannotProceedException.setResolvedObj(reference);
/* 199 */       cannotProceedException.setAltName(compositeName);
/* 200 */       cannotProceedException.setAltNameCtx((Context)object1);
/*     */       
/* 202 */       return cannotProceedException;
/*     */     } 
/*     */ 
/*     */     
/* 206 */     Name name = CNNameParser.cosNameToName(arrayOfNameComponent2);
/* 207 */     Object object2 = null;
/*     */     
/*     */     try {
/* 210 */       if (CorbaUtils.isObjectFactoryTrusted(object1)) {
/* 211 */         object2 = NamingManager.getObjectInstance(object1, name, paramCNCtx, paramCNCtx._env);
/*     */       }
/*     */     }
/* 214 */     catch (NamingException namingException) {
/* 215 */       throw namingException;
/* 216 */     } catch (Exception exception) {
/* 217 */       NamingException namingException = new NamingException("problem generating object using object factory");
/*     */       
/* 219 */       namingException.setRootCause(exception);
/* 220 */       throw namingException;
/*     */     } 
/*     */ 
/*     */     
/* 224 */     if (object2 instanceof Context) {
/* 225 */       cannotProceedException.setResolvedObj(object2);
/*     */     } else {
/*     */       
/* 228 */       name.add("");
/* 229 */       cannotProceedException.setAltName(name);
/*     */ 
/*     */       
/* 232 */       final Object rf2 = object2;
/* 233 */       RefAddr refAddr = new RefAddr("nns") {
/*     */           public Object getContent() {
/* 235 */             return rf2;
/*     */           }
/*     */           
/*     */           private static final long serialVersionUID = -785132553978269772L;
/*     */         };
/* 240 */       Reference reference = new Reference("java.lang.Object", refAddr);
/* 241 */       cannotProceedException.setResolvedObj(reference);
/* 242 */       cannotProceedException.setAltNameCtx(paramCNCtx);
/*     */     } 
/* 244 */     return cannotProceedException;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/cosnaming/ExceptionMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */