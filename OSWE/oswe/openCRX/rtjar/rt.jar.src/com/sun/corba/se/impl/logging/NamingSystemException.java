/*     */ package com.sun.corba.se.impl.logging;
/*     */ 
/*     */ import com.sun.corba.se.spi.logging.LogWrapperBase;
/*     */ import com.sun.corba.se.spi.logging.LogWrapperFactory;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.omg.CORBA.BAD_PARAM;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.INITIALIZE;
/*     */ import org.omg.CORBA.INTERNAL;
/*     */ import org.omg.CORBA.UNKNOWN;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NamingSystemException
/*     */   extends LogWrapperBase
/*     */ {
/*     */   public NamingSystemException(Logger paramLogger) {
/*  31 */     super(paramLogger);
/*     */   }
/*     */   
/*  34 */   private static LogWrapperFactory factory = new LogWrapperFactory()
/*     */     {
/*     */       public LogWrapperBase create(Logger param1Logger) {
/*  37 */         return new NamingSystemException(param1Logger);
/*     */       }
/*     */     };
/*     */   public static final int TRANSIENT_NAME_SERVER_BAD_PORT = 1398080088; public static final int TRANSIENT_NAME_SERVER_BAD_HOST = 1398080089; public static final int OBJECT_IS_NULL = 1398080090; public static final int INS_BAD_ADDRESS = 1398080091; public static final int BIND_UPDATE_CONTEXT_FAILED = 1398080088;
/*     */   
/*     */   public static NamingSystemException get(ORB paramORB, String paramString) {
/*  43 */     return (NamingSystemException)paramORB
/*  44 */       .getLogWrapper(paramString, "NAMING", factory);
/*     */   }
/*     */   public static final int BIND_FAILURE = 1398080089; public static final int RESOLVE_CONVERSION_FAILURE = 1398080090; public static final int RESOLVE_FAILURE = 1398080091; public static final int UNBIND_FAILURE = 1398080092; public static final int TRANS_NS_CANNOT_CREATE_INITIAL_NC_SYS = 1398080138; public static final int TRANS_NS_CANNOT_CREATE_INITIAL_NC = 1398080139; public static final int NAMING_CTX_REBIND_ALREADY_BOUND = 1398080088; public static final int NAMING_CTX_REBINDCTX_ALREADY_BOUND = 1398080089; public static final int NAMING_CTX_BAD_BINDINGTYPE = 1398080090; public static final int NAMING_CTX_RESOLVE_CANNOT_NARROW_TO_CTX = 1398080091; public static final int NAMING_CTX_BINDING_ITERATOR_CREATE = 1398080092; public static final int TRANS_NC_BIND_ALREADY_BOUND = 1398080188; public static final int TRANS_NC_LIST_GOT_EXC = 1398080189; public static final int TRANS_NC_NEWCTX_GOT_EXC = 1398080190; public static final int TRANS_NC_DESTROY_GOT_EXC = 1398080191;
/*     */   public static final int INS_BAD_SCHEME_NAME = 1398080193;
/*     */   public static final int INS_BAD_SCHEME_SPECIFIC_PART = 1398080195;
/*     */   public static final int INS_OTHER = 1398080196;
/*     */   
/*     */   public static NamingSystemException get(String paramString) {
/*  52 */     return (NamingSystemException)ORB.staticGetLogWrapper(paramString, "NAMING", factory);
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
/*     */   public BAD_PARAM transientNameServerBadPort(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/*  64 */     BAD_PARAM bAD_PARAM = new BAD_PARAM(1398080088, paramCompletionStatus);
/*  65 */     if (paramThrowable != null) {
/*  66 */       bAD_PARAM.initCause(paramThrowable);
/*     */     }
/*  68 */     if (this.logger.isLoggable(Level.WARNING)) {
/*  69 */       Object[] arrayOfObject = null;
/*  70 */       doLog(Level.WARNING, "NAMING.transientNameServerBadPort", arrayOfObject, NamingSystemException.class, (Throwable)bAD_PARAM);
/*     */     } 
/*     */ 
/*     */     
/*  74 */     return bAD_PARAM;
/*     */   }
/*     */   
/*     */   public BAD_PARAM transientNameServerBadPort(CompletionStatus paramCompletionStatus) {
/*  78 */     return transientNameServerBadPort(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public BAD_PARAM transientNameServerBadPort(Throwable paramThrowable) {
/*  82 */     return transientNameServerBadPort(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public BAD_PARAM transientNameServerBadPort() {
/*  86 */     return transientNameServerBadPort(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BAD_PARAM transientNameServerBadHost(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/*  92 */     BAD_PARAM bAD_PARAM = new BAD_PARAM(1398080089, paramCompletionStatus);
/*  93 */     if (paramThrowable != null) {
/*  94 */       bAD_PARAM.initCause(paramThrowable);
/*     */     }
/*  96 */     if (this.logger.isLoggable(Level.WARNING)) {
/*  97 */       Object[] arrayOfObject = null;
/*  98 */       doLog(Level.WARNING, "NAMING.transientNameServerBadHost", arrayOfObject, NamingSystemException.class, (Throwable)bAD_PARAM);
/*     */     } 
/*     */ 
/*     */     
/* 102 */     return bAD_PARAM;
/*     */   }
/*     */   
/*     */   public BAD_PARAM transientNameServerBadHost(CompletionStatus paramCompletionStatus) {
/* 106 */     return transientNameServerBadHost(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public BAD_PARAM transientNameServerBadHost(Throwable paramThrowable) {
/* 110 */     return transientNameServerBadHost(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public BAD_PARAM transientNameServerBadHost() {
/* 114 */     return transientNameServerBadHost(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BAD_PARAM objectIsNull(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 120 */     BAD_PARAM bAD_PARAM = new BAD_PARAM(1398080090, paramCompletionStatus);
/* 121 */     if (paramThrowable != null) {
/* 122 */       bAD_PARAM.initCause(paramThrowable);
/*     */     }
/* 124 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 125 */       Object[] arrayOfObject = null;
/* 126 */       doLog(Level.WARNING, "NAMING.objectIsNull", arrayOfObject, NamingSystemException.class, (Throwable)bAD_PARAM);
/*     */     } 
/*     */ 
/*     */     
/* 130 */     return bAD_PARAM;
/*     */   }
/*     */   
/*     */   public BAD_PARAM objectIsNull(CompletionStatus paramCompletionStatus) {
/* 134 */     return objectIsNull(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public BAD_PARAM objectIsNull(Throwable paramThrowable) {
/* 138 */     return objectIsNull(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public BAD_PARAM objectIsNull() {
/* 142 */     return objectIsNull(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BAD_PARAM insBadAddress(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 148 */     BAD_PARAM bAD_PARAM = new BAD_PARAM(1398080091, paramCompletionStatus);
/* 149 */     if (paramThrowable != null) {
/* 150 */       bAD_PARAM.initCause(paramThrowable);
/*     */     }
/* 152 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 153 */       Object[] arrayOfObject = null;
/* 154 */       doLog(Level.WARNING, "NAMING.insBadAddress", arrayOfObject, NamingSystemException.class, (Throwable)bAD_PARAM);
/*     */     } 
/*     */ 
/*     */     
/* 158 */     return bAD_PARAM;
/*     */   }
/*     */   
/*     */   public BAD_PARAM insBadAddress(CompletionStatus paramCompletionStatus) {
/* 162 */     return insBadAddress(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public BAD_PARAM insBadAddress(Throwable paramThrowable) {
/* 166 */     return insBadAddress(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public BAD_PARAM insBadAddress() {
/* 170 */     return insBadAddress(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UNKNOWN bindUpdateContextFailed(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 180 */     UNKNOWN uNKNOWN = new UNKNOWN(1398080088, paramCompletionStatus);
/* 181 */     if (paramThrowable != null) {
/* 182 */       uNKNOWN.initCause(paramThrowable);
/*     */     }
/* 184 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 185 */       Object[] arrayOfObject = null;
/* 186 */       doLog(Level.WARNING, "NAMING.bindUpdateContextFailed", arrayOfObject, NamingSystemException.class, (Throwable)uNKNOWN);
/*     */     } 
/*     */ 
/*     */     
/* 190 */     return uNKNOWN;
/*     */   }
/*     */   
/*     */   public UNKNOWN bindUpdateContextFailed(CompletionStatus paramCompletionStatus) {
/* 194 */     return bindUpdateContextFailed(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public UNKNOWN bindUpdateContextFailed(Throwable paramThrowable) {
/* 198 */     return bindUpdateContextFailed(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public UNKNOWN bindUpdateContextFailed() {
/* 202 */     return bindUpdateContextFailed(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public UNKNOWN bindFailure(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 208 */     UNKNOWN uNKNOWN = new UNKNOWN(1398080089, paramCompletionStatus);
/* 209 */     if (paramThrowable != null) {
/* 210 */       uNKNOWN.initCause(paramThrowable);
/*     */     }
/* 212 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 213 */       Object[] arrayOfObject = null;
/* 214 */       doLog(Level.WARNING, "NAMING.bindFailure", arrayOfObject, NamingSystemException.class, (Throwable)uNKNOWN);
/*     */     } 
/*     */ 
/*     */     
/* 218 */     return uNKNOWN;
/*     */   }
/*     */   
/*     */   public UNKNOWN bindFailure(CompletionStatus paramCompletionStatus) {
/* 222 */     return bindFailure(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public UNKNOWN bindFailure(Throwable paramThrowable) {
/* 226 */     return bindFailure(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public UNKNOWN bindFailure() {
/* 230 */     return bindFailure(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public UNKNOWN resolveConversionFailure(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 236 */     UNKNOWN uNKNOWN = new UNKNOWN(1398080090, paramCompletionStatus);
/* 237 */     if (paramThrowable != null) {
/* 238 */       uNKNOWN.initCause(paramThrowable);
/*     */     }
/* 240 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 241 */       Object[] arrayOfObject = null;
/* 242 */       doLog(Level.WARNING, "NAMING.resolveConversionFailure", arrayOfObject, NamingSystemException.class, (Throwable)uNKNOWN);
/*     */     } 
/*     */ 
/*     */     
/* 246 */     return uNKNOWN;
/*     */   }
/*     */   
/*     */   public UNKNOWN resolveConversionFailure(CompletionStatus paramCompletionStatus) {
/* 250 */     return resolveConversionFailure(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public UNKNOWN resolveConversionFailure(Throwable paramThrowable) {
/* 254 */     return resolveConversionFailure(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public UNKNOWN resolveConversionFailure() {
/* 258 */     return resolveConversionFailure(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public UNKNOWN resolveFailure(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 264 */     UNKNOWN uNKNOWN = new UNKNOWN(1398080091, paramCompletionStatus);
/* 265 */     if (paramThrowable != null) {
/* 266 */       uNKNOWN.initCause(paramThrowable);
/*     */     }
/* 268 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 269 */       Object[] arrayOfObject = null;
/* 270 */       doLog(Level.WARNING, "NAMING.resolveFailure", arrayOfObject, NamingSystemException.class, (Throwable)uNKNOWN);
/*     */     } 
/*     */ 
/*     */     
/* 274 */     return uNKNOWN;
/*     */   }
/*     */   
/*     */   public UNKNOWN resolveFailure(CompletionStatus paramCompletionStatus) {
/* 278 */     return resolveFailure(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public UNKNOWN resolveFailure(Throwable paramThrowable) {
/* 282 */     return resolveFailure(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public UNKNOWN resolveFailure() {
/* 286 */     return resolveFailure(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public UNKNOWN unbindFailure(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 292 */     UNKNOWN uNKNOWN = new UNKNOWN(1398080092, paramCompletionStatus);
/* 293 */     if (paramThrowable != null) {
/* 294 */       uNKNOWN.initCause(paramThrowable);
/*     */     }
/* 296 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 297 */       Object[] arrayOfObject = null;
/* 298 */       doLog(Level.WARNING, "NAMING.unbindFailure", arrayOfObject, NamingSystemException.class, (Throwable)uNKNOWN);
/*     */     } 
/*     */ 
/*     */     
/* 302 */     return uNKNOWN;
/*     */   }
/*     */   
/*     */   public UNKNOWN unbindFailure(CompletionStatus paramCompletionStatus) {
/* 306 */     return unbindFailure(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public UNKNOWN unbindFailure(Throwable paramThrowable) {
/* 310 */     return unbindFailure(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public UNKNOWN unbindFailure() {
/* 314 */     return unbindFailure(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public INITIALIZE transNsCannotCreateInitialNcSys(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 324 */     INITIALIZE iNITIALIZE = new INITIALIZE(1398080138, paramCompletionStatus);
/* 325 */     if (paramThrowable != null) {
/* 326 */       iNITIALIZE.initCause(paramThrowable);
/*     */     }
/* 328 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 329 */       Object[] arrayOfObject = null;
/* 330 */       doLog(Level.WARNING, "NAMING.transNsCannotCreateInitialNcSys", arrayOfObject, NamingSystemException.class, (Throwable)iNITIALIZE);
/*     */     } 
/*     */ 
/*     */     
/* 334 */     return iNITIALIZE;
/*     */   }
/*     */   
/*     */   public INITIALIZE transNsCannotCreateInitialNcSys(CompletionStatus paramCompletionStatus) {
/* 338 */     return transNsCannotCreateInitialNcSys(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public INITIALIZE transNsCannotCreateInitialNcSys(Throwable paramThrowable) {
/* 342 */     return transNsCannotCreateInitialNcSys(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public INITIALIZE transNsCannotCreateInitialNcSys() {
/* 346 */     return transNsCannotCreateInitialNcSys(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INITIALIZE transNsCannotCreateInitialNc(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 352 */     INITIALIZE iNITIALIZE = new INITIALIZE(1398080139, paramCompletionStatus);
/* 353 */     if (paramThrowable != null) {
/* 354 */       iNITIALIZE.initCause(paramThrowable);
/*     */     }
/* 356 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 357 */       Object[] arrayOfObject = null;
/* 358 */       doLog(Level.WARNING, "NAMING.transNsCannotCreateInitialNc", arrayOfObject, NamingSystemException.class, (Throwable)iNITIALIZE);
/*     */     } 
/*     */ 
/*     */     
/* 362 */     return iNITIALIZE;
/*     */   }
/*     */   
/*     */   public INITIALIZE transNsCannotCreateInitialNc(CompletionStatus paramCompletionStatus) {
/* 366 */     return transNsCannotCreateInitialNc(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public INITIALIZE transNsCannotCreateInitialNc(Throwable paramThrowable) {
/* 370 */     return transNsCannotCreateInitialNc(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public INITIALIZE transNsCannotCreateInitialNc() {
/* 374 */     return transNsCannotCreateInitialNc(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL namingCtxRebindAlreadyBound(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 384 */     INTERNAL iNTERNAL = new INTERNAL(1398080088, paramCompletionStatus);
/* 385 */     if (paramThrowable != null) {
/* 386 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 388 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 389 */       Object[] arrayOfObject = null;
/* 390 */       doLog(Level.WARNING, "NAMING.namingCtxRebindAlreadyBound", arrayOfObject, NamingSystemException.class, (Throwable)iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 394 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL namingCtxRebindAlreadyBound(CompletionStatus paramCompletionStatus) {
/* 398 */     return namingCtxRebindAlreadyBound(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public INTERNAL namingCtxRebindAlreadyBound(Throwable paramThrowable) {
/* 402 */     return namingCtxRebindAlreadyBound(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public INTERNAL namingCtxRebindAlreadyBound() {
/* 406 */     return namingCtxRebindAlreadyBound(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL namingCtxRebindctxAlreadyBound(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 412 */     INTERNAL iNTERNAL = new INTERNAL(1398080089, paramCompletionStatus);
/* 413 */     if (paramThrowable != null) {
/* 414 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 416 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 417 */       Object[] arrayOfObject = null;
/* 418 */       doLog(Level.WARNING, "NAMING.namingCtxRebindctxAlreadyBound", arrayOfObject, NamingSystemException.class, (Throwable)iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 422 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL namingCtxRebindctxAlreadyBound(CompletionStatus paramCompletionStatus) {
/* 426 */     return namingCtxRebindctxAlreadyBound(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public INTERNAL namingCtxRebindctxAlreadyBound(Throwable paramThrowable) {
/* 430 */     return namingCtxRebindctxAlreadyBound(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public INTERNAL namingCtxRebindctxAlreadyBound() {
/* 434 */     return namingCtxRebindctxAlreadyBound(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL namingCtxBadBindingtype(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 440 */     INTERNAL iNTERNAL = new INTERNAL(1398080090, paramCompletionStatus);
/* 441 */     if (paramThrowable != null) {
/* 442 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 444 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 445 */       Object[] arrayOfObject = null;
/* 446 */       doLog(Level.WARNING, "NAMING.namingCtxBadBindingtype", arrayOfObject, NamingSystemException.class, (Throwable)iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 450 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL namingCtxBadBindingtype(CompletionStatus paramCompletionStatus) {
/* 454 */     return namingCtxBadBindingtype(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public INTERNAL namingCtxBadBindingtype(Throwable paramThrowable) {
/* 458 */     return namingCtxBadBindingtype(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public INTERNAL namingCtxBadBindingtype() {
/* 462 */     return namingCtxBadBindingtype(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL namingCtxResolveCannotNarrowToCtx(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 468 */     INTERNAL iNTERNAL = new INTERNAL(1398080091, paramCompletionStatus);
/* 469 */     if (paramThrowable != null) {
/* 470 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 472 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 473 */       Object[] arrayOfObject = null;
/* 474 */       doLog(Level.WARNING, "NAMING.namingCtxResolveCannotNarrowToCtx", arrayOfObject, NamingSystemException.class, (Throwable)iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 478 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL namingCtxResolveCannotNarrowToCtx(CompletionStatus paramCompletionStatus) {
/* 482 */     return namingCtxResolveCannotNarrowToCtx(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public INTERNAL namingCtxResolveCannotNarrowToCtx(Throwable paramThrowable) {
/* 486 */     return namingCtxResolveCannotNarrowToCtx(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public INTERNAL namingCtxResolveCannotNarrowToCtx() {
/* 490 */     return namingCtxResolveCannotNarrowToCtx(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL namingCtxBindingIteratorCreate(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 496 */     INTERNAL iNTERNAL = new INTERNAL(1398080092, paramCompletionStatus);
/* 497 */     if (paramThrowable != null) {
/* 498 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 500 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 501 */       Object[] arrayOfObject = null;
/* 502 */       doLog(Level.WARNING, "NAMING.namingCtxBindingIteratorCreate", arrayOfObject, NamingSystemException.class, (Throwable)iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 506 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL namingCtxBindingIteratorCreate(CompletionStatus paramCompletionStatus) {
/* 510 */     return namingCtxBindingIteratorCreate(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public INTERNAL namingCtxBindingIteratorCreate(Throwable paramThrowable) {
/* 514 */     return namingCtxBindingIteratorCreate(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public INTERNAL namingCtxBindingIteratorCreate() {
/* 518 */     return namingCtxBindingIteratorCreate(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL transNcBindAlreadyBound(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 524 */     INTERNAL iNTERNAL = new INTERNAL(1398080188, paramCompletionStatus);
/* 525 */     if (paramThrowable != null) {
/* 526 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 528 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 529 */       Object[] arrayOfObject = null;
/* 530 */       doLog(Level.WARNING, "NAMING.transNcBindAlreadyBound", arrayOfObject, NamingSystemException.class, (Throwable)iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 534 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL transNcBindAlreadyBound(CompletionStatus paramCompletionStatus) {
/* 538 */     return transNcBindAlreadyBound(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public INTERNAL transNcBindAlreadyBound(Throwable paramThrowable) {
/* 542 */     return transNcBindAlreadyBound(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public INTERNAL transNcBindAlreadyBound() {
/* 546 */     return transNcBindAlreadyBound(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL transNcListGotExc(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 552 */     INTERNAL iNTERNAL = new INTERNAL(1398080189, paramCompletionStatus);
/* 553 */     if (paramThrowable != null) {
/* 554 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 556 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 557 */       Object[] arrayOfObject = null;
/* 558 */       doLog(Level.WARNING, "NAMING.transNcListGotExc", arrayOfObject, NamingSystemException.class, (Throwable)iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 562 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL transNcListGotExc(CompletionStatus paramCompletionStatus) {
/* 566 */     return transNcListGotExc(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public INTERNAL transNcListGotExc(Throwable paramThrowable) {
/* 570 */     return transNcListGotExc(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public INTERNAL transNcListGotExc() {
/* 574 */     return transNcListGotExc(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL transNcNewctxGotExc(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 580 */     INTERNAL iNTERNAL = new INTERNAL(1398080190, paramCompletionStatus);
/* 581 */     if (paramThrowable != null) {
/* 582 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 584 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 585 */       Object[] arrayOfObject = null;
/* 586 */       doLog(Level.WARNING, "NAMING.transNcNewctxGotExc", arrayOfObject, NamingSystemException.class, (Throwable)iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 590 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL transNcNewctxGotExc(CompletionStatus paramCompletionStatus) {
/* 594 */     return transNcNewctxGotExc(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public INTERNAL transNcNewctxGotExc(Throwable paramThrowable) {
/* 598 */     return transNcNewctxGotExc(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public INTERNAL transNcNewctxGotExc() {
/* 602 */     return transNcNewctxGotExc(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL transNcDestroyGotExc(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 608 */     INTERNAL iNTERNAL = new INTERNAL(1398080191, paramCompletionStatus);
/* 609 */     if (paramThrowable != null) {
/* 610 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 612 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 613 */       Object[] arrayOfObject = null;
/* 614 */       doLog(Level.WARNING, "NAMING.transNcDestroyGotExc", arrayOfObject, NamingSystemException.class, (Throwable)iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 618 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL transNcDestroyGotExc(CompletionStatus paramCompletionStatus) {
/* 622 */     return transNcDestroyGotExc(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public INTERNAL transNcDestroyGotExc(Throwable paramThrowable) {
/* 626 */     return transNcDestroyGotExc(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public INTERNAL transNcDestroyGotExc() {
/* 630 */     return transNcDestroyGotExc(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL insBadSchemeName(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 636 */     INTERNAL iNTERNAL = new INTERNAL(1398080193, paramCompletionStatus);
/* 637 */     if (paramThrowable != null) {
/* 638 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 640 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 641 */       Object[] arrayOfObject = null;
/* 642 */       doLog(Level.WARNING, "NAMING.insBadSchemeName", arrayOfObject, NamingSystemException.class, (Throwable)iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 646 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL insBadSchemeName(CompletionStatus paramCompletionStatus) {
/* 650 */     return insBadSchemeName(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public INTERNAL insBadSchemeName(Throwable paramThrowable) {
/* 654 */     return insBadSchemeName(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public INTERNAL insBadSchemeName() {
/* 658 */     return insBadSchemeName(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL insBadSchemeSpecificPart(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 664 */     INTERNAL iNTERNAL = new INTERNAL(1398080195, paramCompletionStatus);
/* 665 */     if (paramThrowable != null) {
/* 666 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 668 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 669 */       Object[] arrayOfObject = null;
/* 670 */       doLog(Level.WARNING, "NAMING.insBadSchemeSpecificPart", arrayOfObject, NamingSystemException.class, (Throwable)iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 674 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL insBadSchemeSpecificPart(CompletionStatus paramCompletionStatus) {
/* 678 */     return insBadSchemeSpecificPart(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public INTERNAL insBadSchemeSpecificPart(Throwable paramThrowable) {
/* 682 */     return insBadSchemeSpecificPart(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public INTERNAL insBadSchemeSpecificPart() {
/* 686 */     return insBadSchemeSpecificPart(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public INTERNAL insOther(CompletionStatus paramCompletionStatus, Throwable paramThrowable) {
/* 692 */     INTERNAL iNTERNAL = new INTERNAL(1398080196, paramCompletionStatus);
/* 693 */     if (paramThrowable != null) {
/* 694 */       iNTERNAL.initCause(paramThrowable);
/*     */     }
/* 696 */     if (this.logger.isLoggable(Level.WARNING)) {
/* 697 */       Object[] arrayOfObject = null;
/* 698 */       doLog(Level.WARNING, "NAMING.insOther", arrayOfObject, NamingSystemException.class, (Throwable)iNTERNAL);
/*     */     } 
/*     */ 
/*     */     
/* 702 */     return iNTERNAL;
/*     */   }
/*     */   
/*     */   public INTERNAL insOther(CompletionStatus paramCompletionStatus) {
/* 706 */     return insOther(paramCompletionStatus, (Throwable)null);
/*     */   }
/*     */   
/*     */   public INTERNAL insOther(Throwable paramThrowable) {
/* 710 */     return insOther(CompletionStatus.COMPLETED_NO, paramThrowable);
/*     */   }
/*     */   
/*     */   public INTERNAL insOther() {
/* 714 */     return insOther(CompletionStatus.COMPLETED_NO, (Throwable)null);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/logging/NamingSystemException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */