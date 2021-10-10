/*     */ package com.sun.corba.se.impl.logging;
/*     */ 
/*     */ import com.sun.corba.se.spi.logging.LogWrapperBase;
/*     */ import com.sun.corba.se.spi.logging.LogWrapperFactory;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.omg.CORBA.BAD_OPERATION;
/*     */ import org.omg.CORBA.BAD_PARAM;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.INTERNAL;
/*     */ import org.omg.CORBA.INV_OBJREF;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IORSystemException
/*     */   extends LogWrapperBase
/*     */ {
/*     */   public IORSystemException(Logger paramLogger) {
/*  31 */     super(paramLogger);
/*     */   }
/*     */   
/*  34 */   private static LogWrapperFactory factory = new LogWrapperFactory()
/*     */     {
/*     */       public LogWrapperBase create(Logger param1Logger) {
/*  37 */         return new IORSystemException(param1Logger);
/*     */       }
/*     */     };
/*     */   public static final int ORT_NOT_INITIALIZED = 1398080689; public static final int NULL_POA = 1398080690; public static final int BAD_MAGIC = 1398080691;
/*     */   
/*     */   public static IORSystemException get(ORB paramORB, String paramString) {
/*  43 */     return (IORSystemException)paramORB
/*  44 */       .getLogWrapper(paramString, "IOR", factory);
/*     */   }
/*     */   public static final int STRINGIFY_WRITE_ERROR = 1398080692; public static final int TAGGED_PROFILE_TEMPLATE_FACTORY_NOT_FOUND = 1398080693; public static final int INVALID_JDK1_3_1_PATCH_LEVEL = 1398080694; public static final int GET_LOCAL_SERVANT_FAILURE = 1398080695; public static final int ADAPTER_ID_NOT_AVAILABLE = 1398080689; public static final int SERVER_ID_NOT_AVAILABLE = 1398080690; public static final int ORB_ID_NOT_AVAILABLE = 1398080691; public static final int OBJECT_ADAPTER_ID_NOT_AVAILABLE = 1398080692; public static final int BAD_OID_IN_IOR_TEMPLATE_LIST = 1398080689;
/*     */   public static final int INVALID_TAGGED_PROFILE = 1398080690;
/*     */   public static final int BAD_IIOP_ADDRESS_PORT = 1398080691;
/*     */   public static final int IOR_MUST_HAVE_IIOP_PROFILE = 1398080689;
/*     */   
/*     */   public static IORSystemException get(String paramString) {
/*  52 */     return (IORSystemException)ORB.staticGetLogWrapper(paramString, "IOR", factory);
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
/*     */   public INTERNAL ortNotInitialized(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/*  64 */     INTERNAL iNTERNAL = new INTERNAL(1398080689, paramCompletionStatus);
/*  65 */     if (paramThrowable != null) {
/*  66 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/*  68 */     if (this.logger.isLoggable(Level.WARNING)) {
/*  69 */       Object[] arrayOfObject = null;
/*  70 */       doLog(Level.WARNING, "IOR.ortNotInitialized", arrayOfObject, IORSystemException.class, (Throwable)iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/*  74 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL ortNotInitialized(CompletionStatus paramCompletionStatus) {
/*  78 */     return ortNotInitialized(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public INTERNAL ortNotInitialized(Throwable paramThrowable) {
/*  82 */     return ortNotInitialized(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public INTERNAL ortNotInitialized() {
/*  86 */     return ortNotInitialized(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL nullPoa(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/*  92 */     INTERNAL iNTERNAL = new INTERNAL(1398080690, paramCompletionStatus);
/*  93 */     if (paramThrowable != null) {
/*  94 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/*  96 */     if (this.logger.isLoggable(Level.WARNING)) {
/*  97 */       Object[] arrayOfObject = null;
/*  98 */       doLog(Level.WARNING, "IOR.nullPoa", arrayOfObject, IORSystemException.class, (Throwable)iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 102 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL nullPoa(CompletionStatus paramCompletionStatus) {
/* 106 */     return nullPoa(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public INTERNAL nullPoa(Throwable paramThrowable) {
/* 110 */     return nullPoa(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public INTERNAL nullPoa() {
/* 114 */     return nullPoa(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL badMagic(CompletionStatus paramCompletionStatus, Throwable paramThrowable, Object paramObject) {
/* 120 */     INTERNAL iNTERNAL = new INTERNAL(1398080691, paramCompletionStatus);
/* 121 */     if (paramThrowable != null) {
/* 122 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 124 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 125 */       Object[] arrayOfObject = new Object[1];
/* 126 */       arrayOfObject[0] = paramObject;
/* 127 */       doLog(Level.WARNING, "IOR.badMagic", arrayOfObject, IORSystemException.class, (Throwable)iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 131 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL badMagic(CompletionStatus paramCompletionStatus, Object paramObject) {
/* 135 */     return badMagic(paramCompletionStatus, (Throwable)null, paramObject);
/*     */   }
/*     */   
/*     */   public INTERNAL badMagic(Throwable paramThrowable, Object paramObject) {
/* 139 */     return badMagic(CompletionStatus.COMPLETED_NO, paramThrowable, paramObject);
/*     */   }
/*     */   
/*     */   public INTERNAL badMagic(Object paramObject) {
/* 143 */     return badMagic(CompletionStatus.COMPLETED_NO, (Throwable)null, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL stringifyWriteError(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 149 */     INTERNAL iNTERNAL = new INTERNAL(1398080692, paramCompletionStatus);
/* 150 */     if (paramThrowable != null) {
/* 151 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 153 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 154 */       Object[] arrayOfObject = null;
/* 155 */       doLog(Level.WARNING, "IOR.stringifyWriteError", arrayOfObject, IORSystemException.class, (Throwable)iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 159 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL stringifyWriteError(CompletionStatus paramCompletionStatus) {
/* 163 */     return stringifyWriteError(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public INTERNAL stringifyWriteError(Throwable paramThrowable) {
/* 167 */     return stringifyWriteError(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public INTERNAL stringifyWriteError() {
/* 171 */     return stringifyWriteError(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL taggedProfileTemplateFactoryNotFound(CompletionStatus paramCompletionStatus, Throwable paramThrowable, Object paramObject) {
/* 177 */     INTERNAL iNTERNAL = new INTERNAL(1398080693, paramCompletionStatus);
/* 178 */     if (paramThrowable != null) {
/* 179 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 181 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 182 */       Object[] arrayOfObject = new Object[1];
/* 183 */       arrayOfObject[0] = paramObject;
/* 184 */       doLog(Level.WARNING, "IOR.taggedProfileTemplateFactoryNotFound", arrayOfObject, IORSystemException.class, (Throwable)iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 188 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL taggedProfileTemplateFactoryNotFound(CompletionStatus paramCompletionStatus, Object paramObject) {
/* 192 */     return taggedProfileTemplateFactoryNotFound(paramCompletionStatus, (Throwable)null, paramObject);
/*     */   }
/*     */   
/*     */   public INTERNAL taggedProfileTemplateFactoryNotFound(Throwable paramThrowable, Object paramObject) {
/* 196 */     return taggedProfileTemplateFactoryNotFound(CompletionStatus.COMPLETED_NO, paramThrowable, paramObject);
/*     */   }
/*     */   
/*     */   public INTERNAL taggedProfileTemplateFactoryNotFound(Object paramObject) {
/* 200 */     return taggedProfileTemplateFactoryNotFound(CompletionStatus.COMPLETED_NO, (Throwable)null, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL invalidJdk131PatchLevel(CompletionStatus paramCompletionStatus, Throwable paramThrowable, Object paramObject) {
/* 206 */     INTERNAL iNTERNAL = new INTERNAL(1398080694, paramCompletionStatus);
/* 207 */     if (paramThrowable != null) {
/* 208 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 210 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 211 */       Object[] arrayOfObject = new Object[1];
/* 212 */       arrayOfObject[0] = paramObject;
/* 213 */       doLog(Level.WARNING, "IOR.invalidJdk131PatchLevel", arrayOfObject, IORSystemException.class, (Throwable)iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 217 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL invalidJdk131PatchLevel(CompletionStatus paramCompletionStatus, Object paramObject) {
/* 221 */     return invalidJdk131PatchLevel(paramCompletionStatus, (Throwable)null, paramObject);
/*     */   }
/*     */   
/*     */   public INTERNAL invalidJdk131PatchLevel(Throwable paramThrowable, Object paramObject) {
/* 225 */     return invalidJdk131PatchLevel(CompletionStatus.COMPLETED_NO, paramThrowable, paramObject);
/*     */   }
/*     */   
/*     */   public INTERNAL invalidJdk131PatchLevel(Object paramObject) {
/* 229 */     return invalidJdk131PatchLevel(CompletionStatus.COMPLETED_NO, (Throwable)null, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL getLocalServantFailure(CompletionStatus paramCompletionStatus, Throwable paramThrowable, Object paramObject) {
/* 235 */     INTERNAL iNTERNAL = new INTERNAL(1398080695, paramCompletionStatus);
/* 236 */     if (paramThrowable != null) {
/* 237 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 239 */     if (this.logger.isLoggable(Level.FINE)) {
/* 240 */       Object[] arrayOfObject = new Object[1];
/* 241 */       arrayOfObject[0] = paramObject;
/* 242 */       doLog(Level.FINE, "IOR.getLocalServantFailure", arrayOfObject, IORSystemException.class, (Throwable)iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 246 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL getLocalServantFailure(CompletionStatus paramCompletionStatus, Object paramObject) {
/* 250 */     return getLocalServantFailure(paramCompletionStatus, (Throwable)null, paramObject);
/*     */   }
/*     */   
/*     */   public INTERNAL getLocalServantFailure(Throwable paramThrowable, Object paramObject) {
/* 254 */     return getLocalServantFailure(CompletionStatus.COMPLETED_NO, paramThrowable, paramObject);
/*     */   }
/*     */   
/*     */   public INTERNAL getLocalServantFailure(Object paramObject) {
/* 258 */     return getLocalServantFailure(CompletionStatus.COMPLETED_NO, (Throwable)null, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BAD_OPERATION adapterIdNotAvailable(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 268 */     BAD_OPERATION bAD_OPERATION = new BAD_OPERATION(1398080689, paramCompletionStatus);
/* 269 */     if (paramThrowable != null) {
/* 270 */       bAD_OPERATION.initCause(paramThrowable);
/*     */     }
/* 272 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 273 */       Object[] arrayOfObject = null;
/* 274 */       doLog(Level.WARNING, "IOR.adapterIdNotAvailable", arrayOfObject, IORSystemException.class, (Throwable)bAD_OPERATION);
/*     */     } 
/*     */ 
/*     */     
/* 278 */     return bAD_OPERATION;
/*     */   }
/*     */   
/*     */   public BAD_OPERATION adapterIdNotAvailable(CompletionStatus paramCompletionStatus) {
/* 282 */     return adapterIdNotAvailable(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public BAD_OPERATION adapterIdNotAvailable(Throwable paramThrowable) {
/* 286 */     return adapterIdNotAvailable(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public BAD_OPERATION adapterIdNotAvailable() {
/* 290 */     return adapterIdNotAvailable(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BAD_OPERATION serverIdNotAvailable(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 296 */     BAD_OPERATION bAD_OPERATION = new BAD_OPERATION(1398080690, paramCompletionStatus);
/* 297 */     if (paramThrowable != null) {
/* 298 */       bAD_OPERATION.initCause(paramThrowable);
/*     */     }
/* 300 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 301 */       Object[] arrayOfObject = null;
/* 302 */       doLog(Level.WARNING, "IOR.serverIdNotAvailable", arrayOfObject, IORSystemException.class, (Throwable)bAD_OPERATION);
/*     */     } 
/*     */ 
/*     */     
/* 306 */     return bAD_OPERATION;
/*     */   }
/*     */   
/*     */   public BAD_OPERATION serverIdNotAvailable(CompletionStatus paramCompletionStatus) {
/* 310 */     return serverIdNotAvailable(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public BAD_OPERATION serverIdNotAvailable(Throwable paramThrowable) {
/* 314 */     return serverIdNotAvailable(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public BAD_OPERATION serverIdNotAvailable() {
/* 318 */     return serverIdNotAvailable(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BAD_OPERATION orbIdNotAvailable(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 324 */     BAD_OPERATION bAD_OPERATION = new BAD_OPERATION(1398080691, paramCompletionStatus);
/* 325 */     if (paramThrowable != null) {
/* 326 */       bAD_OPERATION.initCause(paramThrowable);
/*     */     }
/* 328 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 329 */       Object[] arrayOfObject = null;
/* 330 */       doLog(Level.WARNING, "IOR.orbIdNotAvailable", arrayOfObject, IORSystemException.class, (Throwable)bAD_OPERATION);
/*     */     } 
/*     */ 
/*     */     
/* 334 */     return bAD_OPERATION;
/*     */   }
/*     */   
/*     */   public BAD_OPERATION orbIdNotAvailable(CompletionStatus paramCompletionStatus) {
/* 338 */     return orbIdNotAvailable(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public BAD_OPERATION orbIdNotAvailable(Throwable paramThrowable) {
/* 342 */     return orbIdNotAvailable(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public BAD_OPERATION orbIdNotAvailable() {
/* 346 */     return orbIdNotAvailable(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BAD_OPERATION objectAdapterIdNotAvailable(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 352 */     BAD_OPERATION bAD_OPERATION = new BAD_OPERATION(1398080692, paramCompletionStatus);
/* 353 */     if (paramThrowable != null) {
/* 354 */       bAD_OPERATION.initCause(paramThrowable);
/*     */     }
/* 356 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 357 */       Object[] arrayOfObject = null;
/* 358 */       doLog(Level.WARNING, "IOR.objectAdapterIdNotAvailable", arrayOfObject, IORSystemException.class, (Throwable)bAD_OPERATION);
/*     */     } 
/*     */ 
/*     */     
/* 362 */     return bAD_OPERATION;
/*     */   }
/*     */   
/*     */   public BAD_OPERATION objectAdapterIdNotAvailable(CompletionStatus paramCompletionStatus) {
/* 366 */     return objectAdapterIdNotAvailable(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public BAD_OPERATION objectAdapterIdNotAvailable(Throwable paramThrowable) {
/* 370 */     return objectAdapterIdNotAvailable(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public BAD_OPERATION objectAdapterIdNotAvailable() {
/* 374 */     return objectAdapterIdNotAvailable(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BAD_PARAM badOidInIorTemplateList(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 384 */     BAD_PARAM bAD_PARAM = new BAD_PARAM(1398080689, paramCompletionStatus);
/* 385 */     if (paramThrowable != null) {
/* 386 */       bAD_PARAM.initCause(paramThrowable);
/*     */     }
/* 388 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 389 */       Object[] arrayOfObject = null;
/* 390 */       doLog(Level.WARNING, "IOR.badOidInIorTemplateList", arrayOfObject, IORSystemException.class, (Throwable)bAD_PARAM);
/*     */     } 
/*     */ 
/*     */     
/* 394 */     return bAD_PARAM;
/*     */   }
/*     */   
/*     */   public BAD_PARAM badOidInIorTemplateList(CompletionStatus paramCompletionStatus) {
/* 398 */     return badOidInIorTemplateList(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public BAD_PARAM badOidInIorTemplateList(Throwable paramThrowable) {
/* 402 */     return badOidInIorTemplateList(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public BAD_PARAM badOidInIorTemplateList() {
/* 406 */     return badOidInIorTemplateList(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BAD_PARAM invalidTaggedProfile(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 412 */     BAD_PARAM bAD_PARAM = new BAD_PARAM(1398080690, paramCompletionStatus);
/* 413 */     if (paramThrowable != null) {
/* 414 */       bAD_PARAM.initCause(paramThrowable);
/*     */     }
/* 416 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 417 */       Object[] arrayOfObject = null;
/* 418 */       doLog(Level.WARNING, "IOR.invalidTaggedProfile", arrayOfObject, IORSystemException.class, (Throwable)bAD_PARAM);
/*     */     } 
/*     */ 
/*     */     
/* 422 */     return bAD_PARAM;
/*     */   }
/*     */   
/*     */   public BAD_PARAM invalidTaggedProfile(CompletionStatus paramCompletionStatus) {
/* 426 */     return invalidTaggedProfile(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public BAD_PARAM invalidTaggedProfile(Throwable paramThrowable) {
/* 430 */     return invalidTaggedProfile(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public BAD_PARAM invalidTaggedProfile() {
/* 434 */     return invalidTaggedProfile(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BAD_PARAM badIiopAddressPort(CompletionStatus paramCompletionStatus, Throwable paramThrowable, Object paramObject) {
/* 440 */     BAD_PARAM bAD_PARAM = new BAD_PARAM(1398080691, paramCompletionStatus);
/* 441 */     if (paramThrowable != null) {
/* 442 */       bAD_PARAM.initCause(paramThrowable);
/*     */     }
/* 444 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 445 */       Object[] arrayOfObject = new Object[1];
/* 446 */       arrayOfObject[0] = paramObject;
/* 447 */       doLog(Level.WARNING, "IOR.badIiopAddressPort", arrayOfObject, IORSystemException.class, (Throwable)bAD_PARAM);
/*     */     } 
/*     */ 
/*     */     
/* 451 */     return bAD_PARAM;
/*     */   }
/*     */   
/*     */   public BAD_PARAM badIiopAddressPort(CompletionStatus paramCompletionStatus, Object paramObject) {
/* 455 */     return badIiopAddressPort(paramCompletionStatus, (Throwable)null, paramObject);
/*     */   }
/*     */   
/*     */   public BAD_PARAM badIiopAddressPort(Throwable paramThrowable, Object paramObject) {
/* 459 */     return badIiopAddressPort(CompletionStatus.COMPLETED_NO, paramThrowable, paramObject);
/*     */   }
/*     */   
/*     */   public BAD_PARAM badIiopAddressPort(Object paramObject) {
/* 463 */     return badIiopAddressPort(CompletionStatus.COMPLETED_NO, (Throwable)null, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public INV_OBJREF iorMustHaveIiopProfile(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 473 */     INV_OBJREF iNV_OBJREF = new INV_OBJREF(1398080689, paramCompletionStatus);
/* 474 */     if (paramThrowable != null) {
/* 475 */       iNV_OBJREF.initCause(paramThrowable);
/*     */     }
/* 477 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 478 */       Object[] arrayOfObject = null;
/* 479 */       doLog(Level.WARNING, "IOR.iorMustHaveIiopProfile", arrayOfObject, IORSystemException.class, (Throwable)iNV_OBJREF);
/*     */     } 
/*     */ 
/*     */     
/* 483 */     return iNV_OBJREF;
/*     */   }
/*     */   
/*     */   public INV_OBJREF iorMustHaveIiopProfile(CompletionStatus paramCompletionStatus) {
/* 487 */     return iorMustHaveIiopProfile(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public INV_OBJREF iorMustHaveIiopProfile(Throwable paramThrowable) {
/* 491 */     return iorMustHaveIiopProfile(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public INV_OBJREF iorMustHaveIiopProfile() {
/* 495 */     return iorMustHaveIiopProfile(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/logging/IORSystemException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */