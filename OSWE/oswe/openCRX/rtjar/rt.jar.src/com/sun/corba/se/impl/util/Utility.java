/*     */ package com.sun.corba.se.impl.util;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.OMGSystemException;
/*     */ import com.sun.corba.se.impl.logging.UtilSystemException;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.presentation.rmi.PresentationManager;
/*     */ import com.sun.corba.se.spi.presentation.rmi.StubAdapter;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import javax.rmi.CORBA.Tie;
/*     */ import javax.rmi.CORBA.Util;
/*     */ import javax.rmi.PortableRemoteObject;
/*     */ import org.omg.CORBA.BAD_INV_ORDER;
/*     */ import org.omg.CORBA.BAD_OPERATION;
/*     */ import org.omg.CORBA.BAD_PARAM;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.SystemException;
/*     */ import org.omg.CORBA.portable.BoxedValueHelper;
/*     */ import org.omg.CORBA.portable.Delegate;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.ValueFactory;
/*     */ import org.omg.CORBA_2_3.ORB;
/*     */ import org.omg.CORBA_2_3.portable.Delegate;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ import org.omg.PortableServer.Servant;
/*     */ import org.omg.stub.java.rmi._Remote_Stub;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Utility
/*     */ {
/*     */   public static final String STUB_PREFIX = "_";
/*     */   public static final String RMI_STUB_SUFFIX = "_Stub";
/*     */   public static final String DYNAMIC_STUB_SUFFIX = "_DynamicStub";
/*     */   public static final String IDL_STUB_SUFFIX = "Stub";
/*     */   public static final String TIE_SUFIX = "_Tie";
/*  90 */   private static IdentityHashtable tieCache = new IdentityHashtable();
/*  91 */   private static IdentityHashtable tieToStubCache = new IdentityHashtable();
/*  92 */   private static IdentityHashtable stubToTieCache = new IdentityHashtable();
/*  93 */   private static Object CACHE_MISS = new Object();
/*  94 */   private static UtilSystemException wrapper = UtilSystemException.get("util");
/*     */   
/*  96 */   private static OMGSystemException omgWrapper = OMGSystemException.get("util");
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
/*     */   public static Object autoConnect(Object paramObject, ORB paramORB, boolean paramBoolean) {
/* 113 */     if (paramObject == null) {
/* 114 */       return paramObject;
/*     */     }
/*     */     
/* 117 */     if (StubAdapter.isStub(paramObject)) {
/*     */       try {
/* 119 */         StubAdapter.getDelegate(paramObject);
/* 120 */       } catch (BAD_OPERATION bAD_OPERATION) {
/*     */         try {
/* 122 */           StubAdapter.connect(paramObject, paramORB);
/* 123 */         } catch (RemoteException remoteException) {
/*     */ 
/*     */           
/* 126 */           throw wrapper.objectNotConnected(remoteException, paramObject
/* 127 */               .getClass().getName());
/*     */         } 
/*     */       } 
/*     */       
/* 131 */       return paramObject;
/*     */     } 
/*     */     
/* 134 */     if (paramObject instanceof Remote) {
/* 135 */       Remote remote = (Remote)paramObject;
/* 136 */       Tie tie = Util.getTie(remote);
/* 137 */       if (tie != null) {
/*     */         try {
/* 139 */           tie.orb();
/* 140 */         } catch (SystemException systemException) {
/* 141 */           tie.orb(paramORB);
/*     */         } 
/*     */         
/* 144 */         if (paramBoolean) {
/* 145 */           Remote remote1 = loadStub(tie, null, null, true);
/* 146 */           if (remote1 != null) {
/* 147 */             return remote1;
/*     */           }
/* 149 */           throw wrapper.couldNotLoadStub(paramObject.getClass().getName());
/*     */         } 
/*     */         
/* 152 */         return StubAdapter.activateTie(tie);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 158 */       throw wrapper.objectNotExported(paramObject.getClass().getName());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 164 */     return paramObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Tie loadTie(Remote paramRemote) {
/* 172 */     Tie tie = null;
/* 173 */     Class<?> clazz = paramRemote.getClass();
/*     */ 
/*     */ 
/*     */     
/* 177 */     synchronized (tieCache) {
/*     */       
/* 179 */       Object object = tieCache.get(paramRemote);
/*     */       
/* 181 */       if (object == null) {
/*     */ 
/*     */         
/*     */         try {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 189 */           tie = loadTie(clazz);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 196 */           while (tie == null && (
/* 197 */             clazz = clazz.getSuperclass()) != null && clazz != PortableRemoteObject.class && clazz != Object.class)
/*     */           {
/*     */ 
/*     */             
/* 201 */             tie = loadTie(clazz);
/*     */           }
/* 203 */         } catch (Exception exception) {
/* 204 */           wrapper.loadTieFailed(exception, clazz.getName());
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 209 */         if (tie == null)
/*     */         {
/*     */ 
/*     */           
/* 213 */           tieCache.put(paramRemote, CACHE_MISS);
/*     */         
/*     */         }
/*     */         else
/*     */         {
/*     */           
/* 219 */           tieCache.put(paramRemote, tie);
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 226 */       else if (object != CACHE_MISS) {
/*     */         try {
/* 228 */           tie = (Tie)object.getClass().newInstance();
/* 229 */         } catch (Exception exception) {}
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 235 */     return tie;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Tie loadTie(Class paramClass) {
/* 243 */     return ORB.getStubFactoryFactory()
/* 244 */       .getTie(paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearCaches() {
/* 252 */     synchronized (tieToStubCache) {
/* 253 */       tieToStubCache.clear();
/*     */     } 
/* 255 */     synchronized (tieCache) {
/* 256 */       tieCache.clear();
/*     */     } 
/* 258 */     synchronized (stubToTieCache) {
/* 259 */       stubToTieCache.clear();
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
/*     */   static Class loadClassOfType(String paramString1, String paramString2, ClassLoader paramClassLoader1, Class paramClass, ClassLoader paramClassLoader2) throws ClassNotFoundException {
/* 275 */     Class<?> clazz = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*     */       try {
/* 286 */         if (!PackagePrefixChecker.hasOffendingPrefix(
/* 287 */             PackagePrefixChecker.withoutPackagePrefix(paramString1))) {
/* 288 */           clazz = Util.loadClass(
/* 289 */               PackagePrefixChecker.withoutPackagePrefix(paramString1), paramString2, paramClassLoader1);
/*     */         }
/*     */         else {
/*     */           
/* 293 */           clazz = Util.loadClass(paramString1, paramString2, paramClassLoader1);
/*     */         }
/*     */       
/* 296 */       } catch (ClassNotFoundException classNotFoundException) {
/* 297 */         clazz = Util.loadClass(paramString1, paramString2, paramClassLoader1);
/*     */       } 
/*     */       
/* 300 */       if (paramClass == null)
/* 301 */         return clazz; 
/* 302 */     } catch (ClassNotFoundException classNotFoundException) {
/* 303 */       if (paramClass == null) {
/* 304 */         throw classNotFoundException;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 314 */     if (clazz == null || !paramClass.isAssignableFrom(clazz)) {
/* 315 */       if (paramClass.getClassLoader() != paramClassLoader2) {
/* 316 */         throw new IllegalArgumentException("expectedTypeClassLoader not class loader of expected Type.");
/*     */       }
/*     */ 
/*     */       
/* 320 */       if (paramClassLoader2 != null) {
/* 321 */         clazz = paramClassLoader2.loadClass(paramString1);
/*     */       } else {
/* 323 */         ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/* 324 */         if (classLoader == null) {
/* 325 */           classLoader = ClassLoader.getSystemClassLoader();
/*     */         }
/* 327 */         clazz = classLoader.loadClass(paramString1);
/*     */       } 
/*     */     } 
/*     */     
/* 331 */     return clazz;
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
/*     */   public static Class loadClassForClass(String paramString1, String paramString2, ClassLoader paramClassLoader1, Class<?> paramClass, ClassLoader paramClassLoader2) throws ClassNotFoundException {
/* 349 */     if (paramClass == null) {
/* 350 */       return Util.loadClass(paramString1, paramString2, paramClassLoader1);
/*     */     }
/* 352 */     Class<?> clazz = null;
/*     */     try {
/* 354 */       clazz = Util.loadClass(paramString1, paramString2, paramClassLoader1);
/* 355 */     } catch (ClassNotFoundException classNotFoundException) {
/* 356 */       if (paramClass.getClassLoader() == null) {
/* 357 */         throw classNotFoundException;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 367 */     if (clazz == null || (clazz
/* 368 */       .getClassLoader() != null && clazz
/* 369 */       .getClassLoader().loadClass(paramClass.getName()) != paramClass)) {
/*     */ 
/*     */       
/* 372 */       if (paramClass.getClassLoader() != paramClassLoader2) {
/* 373 */         throw new IllegalArgumentException("relatedTypeClassLoader not class loader of relatedType.");
/*     */       }
/*     */       
/* 376 */       if (paramClassLoader2 != null) {
/* 377 */         clazz = paramClassLoader2.loadClass(paramString1);
/*     */       }
/*     */     } 
/* 380 */     return clazz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BoxedValueHelper getHelper(Class paramClass, String paramString1, String paramString2) {
/* 391 */     String str = null;
/* 392 */     if (paramClass != null) {
/* 393 */       str = paramClass.getName();
/* 394 */       if (paramString1 == null)
/* 395 */         paramString1 = Util.getCodebase(paramClass); 
/*     */     } else {
/* 397 */       if (paramString2 != null)
/* 398 */         str = RepositoryId.cache.getId(paramString2).getClassName(); 
/* 399 */       if (str == null) {
/* 400 */         throw wrapper.unableLocateValueHelper(CompletionStatus.COMPLETED_MAYBE);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 406 */       ClassLoader classLoader = (paramClass == null) ? null : paramClass.getClassLoader();
/*     */       
/* 408 */       Class<BoxedValueHelper> clazz = loadClassForClass(str + "Helper", paramString1, classLoader, paramClass, classLoader);
/*     */       
/* 410 */       return clazz.newInstance();
/*     */     }
/* 412 */     catch (ClassNotFoundException classNotFoundException) {
/* 413 */       throw wrapper.unableLocateValueHelper(CompletionStatus.COMPLETED_MAYBE, classNotFoundException);
/*     */     }
/* 415 */     catch (IllegalAccessException illegalAccessException) {
/* 416 */       throw wrapper.unableLocateValueHelper(CompletionStatus.COMPLETED_MAYBE, illegalAccessException);
/*     */     }
/* 418 */     catch (InstantiationException instantiationException) {
/* 419 */       throw wrapper.unableLocateValueHelper(CompletionStatus.COMPLETED_MAYBE, instantiationException);
/*     */     }
/* 421 */     catch (ClassCastException classCastException) {
/* 422 */       throw wrapper.unableLocateValueHelper(CompletionStatus.COMPLETED_MAYBE, classCastException);
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
/*     */   public static ValueFactory getFactory(Class paramClass, String paramString1, ORB paramORB, String paramString2) {
/* 435 */     ValueFactory valueFactory = null;
/* 436 */     if (paramORB != null && paramString2 != null) {
/*     */       try {
/* 438 */         valueFactory = ((ORB)paramORB).lookup_value_factory(paramString2);
/*     */       }
/* 440 */       catch (BAD_PARAM bAD_PARAM) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 445 */     String str = null;
/* 446 */     if (paramClass != null) {
/* 447 */       str = paramClass.getName();
/* 448 */       if (paramString1 == null)
/* 449 */         paramString1 = Util.getCodebase(paramClass); 
/*     */     } else {
/* 451 */       if (paramString2 != null)
/* 452 */         str = RepositoryId.cache.getId(paramString2).getClassName(); 
/* 453 */       if (str == null) {
/* 454 */         throw omgWrapper.unableLocateValueFactory(CompletionStatus.COMPLETED_MAYBE);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 460 */     if (valueFactory != null && (
/* 461 */       !valueFactory.getClass().getName().equals(str + "DefaultFactory") || (paramClass == null && paramString1 == null)))
/*     */     {
/* 463 */       return valueFactory;
/*     */     }
/*     */     
/*     */     try {
/* 467 */       ClassLoader classLoader = (paramClass == null) ? null : paramClass.getClassLoader();
/*     */       
/* 469 */       Class<ValueFactory> clazz = loadClassForClass(str + "DefaultFactory", paramString1, classLoader, paramClass, classLoader);
/*     */       
/* 471 */       return clazz.newInstance();
/*     */     }
/* 473 */     catch (ClassNotFoundException classNotFoundException) {
/* 474 */       throw omgWrapper.unableLocateValueFactory(CompletionStatus.COMPLETED_MAYBE, classNotFoundException);
/*     */     }
/* 476 */     catch (IllegalAccessException illegalAccessException) {
/* 477 */       throw omgWrapper.unableLocateValueFactory(CompletionStatus.COMPLETED_MAYBE, illegalAccessException);
/*     */     }
/* 479 */     catch (InstantiationException instantiationException) {
/* 480 */       throw omgWrapper.unableLocateValueFactory(CompletionStatus.COMPLETED_MAYBE, instantiationException);
/*     */     }
/* 482 */     catch (ClassCastException classCastException) {
/* 483 */       throw omgWrapper.unableLocateValueFactory(CompletionStatus.COMPLETED_MAYBE, classCastException);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static Remote loadStub(Tie paramTie, PresentationManager.StubFactory paramStubFactory, String paramString, boolean paramBoolean) {
/* 503 */     StubEntry stubEntry = null;
/*     */ 
/*     */     
/* 506 */     synchronized (tieToStubCache) {
/* 507 */       Object object = tieToStubCache.get(paramTie);
/* 508 */       if (object == null) {
/*     */         
/* 510 */         stubEntry = loadStubAndUpdateCache(paramTie, paramStubFactory, paramString, paramBoolean);
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 515 */       else if (object != CACHE_MISS) {
/*     */         
/* 517 */         stubEntry = (StubEntry)object;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 525 */         if (!stubEntry.mostDerived && paramBoolean) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 531 */           stubEntry = loadStubAndUpdateCache(paramTie, null, paramString, true);
/*     */         }
/* 533 */         else if (paramStubFactory != null && 
/* 534 */           !StubAdapter.getTypeIds(stubEntry.stub)[0].equals(paramStubFactory
/* 535 */             .getTypeIds()[0])) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 540 */           stubEntry = loadStubAndUpdateCache(paramTie, null, paramString, true);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 545 */           if (stubEntry == null) {
/* 546 */             stubEntry = loadStubAndUpdateCache(paramTie, paramStubFactory, paramString, paramBoolean);
/*     */           }
/*     */         } else {
/*     */ 
/*     */           
/*     */           try {
/* 552 */             Delegate delegate = StubAdapter.getDelegate(stubEntry.stub);
/*     */           }
/* 554 */           catch (Exception exception) {
/*     */             
/*     */             try {
/* 557 */               Delegate delegate = StubAdapter.getDelegate(paramTie);
/*     */               
/* 559 */               StubAdapter.setDelegate(stubEntry.stub, delegate);
/*     */             }
/* 561 */             catch (Exception exception1) {}
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 568 */     if (stubEntry != null) {
/* 569 */       return (Remote)stubEntry.stub;
/*     */     }
/* 571 */     return null;
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
/*     */   private static StubEntry loadStubAndUpdateCache(Tie paramTie, PresentationManager.StubFactory paramStubFactory, String paramString, boolean paramBoolean) {
/* 589 */     Object object = null;
/* 590 */     StubEntry stubEntry = null;
/* 591 */     boolean bool = StubAdapter.isStub(paramTie);
/*     */     
/* 593 */     if (paramStubFactory != null) {
/*     */       try {
/* 595 */         object = paramStubFactory.makeStub();
/* 596 */       } catch (Throwable throwable) {
/* 597 */         wrapper.stubFactoryCouldNotMakeStub(throwable);
/* 598 */         if (throwable instanceof ThreadDeath) {
/* 599 */           throw (ThreadDeath)throwable;
/*     */         }
/*     */       } 
/*     */     } else {
/* 603 */       String[] arrayOfString = null;
/* 604 */       if (bool) {
/* 605 */         arrayOfString = StubAdapter.getTypeIds(paramTie);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 610 */         arrayOfString = ((Servant)paramTie)._all_interfaces(null, null);
/*     */       } 
/*     */       
/* 613 */       if (paramString == null) {
/* 614 */         paramString = Util.getCodebase(paramTie.getClass());
/*     */       }
/*     */       
/* 617 */       if (arrayOfString.length == 0) {
/* 618 */         _Remote_Stub _Remote_Stub = new _Remote_Stub();
/*     */       } else {
/*     */         
/* 621 */         for (byte b = 0; b < arrayOfString.length; b++) {
/* 622 */           if (arrayOfString[b].length() == 0) {
/* 623 */             _Remote_Stub _Remote_Stub = new _Remote_Stub();
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/*     */           try {
/* 629 */             PresentationManager.StubFactoryFactory stubFactoryFactory = ORB.getStubFactoryFactory();
/* 630 */             RepositoryId repositoryId = RepositoryId.cache.getId(arrayOfString[b]);
/* 631 */             String str = repositoryId.getClassName();
/* 632 */             boolean bool1 = repositoryId.isIDLType();
/* 633 */             paramStubFactory = stubFactoryFactory.createStubFactory(str, bool1, paramString, null, paramTie
/*     */                 
/* 635 */                 .getClass().getClassLoader());
/* 636 */             object = paramStubFactory.makeStub();
/*     */             break;
/* 638 */           } catch (Exception exception) {
/* 639 */             wrapper.errorInMakeStubFromRepositoryId(exception);
/*     */ 
/*     */             
/* 642 */             if (paramBoolean)
/*     */               break; 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 648 */     if (object == null) {
/*     */       
/* 650 */       tieToStubCache.put(paramTie, CACHE_MISS);
/*     */     } else {
/* 652 */       if (bool) {
/*     */         try {
/* 654 */           Delegate delegate = StubAdapter.getDelegate(paramTie);
/* 655 */           StubAdapter.setDelegate(object, delegate);
/* 656 */         } catch (Exception exception) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 662 */           synchronized (stubToTieCache) {
/* 663 */             stubToTieCache.put(object, paramTie);
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         
/*     */         try {
/* 669 */           Delegate delegate = StubAdapter.getDelegate(paramTie);
/* 670 */           StubAdapter.setDelegate(object, delegate);
/* 671 */         } catch (BAD_INV_ORDER bAD_INV_ORDER) {
/* 672 */           synchronized (stubToTieCache) {
/* 673 */             stubToTieCache.put(object, paramTie);
/*     */           } 
/* 675 */         } catch (Exception exception) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 681 */           throw wrapper.noPoa(exception);
/*     */         } 
/*     */       } 
/*     */       
/* 685 */       stubEntry = new StubEntry(object, paramBoolean);
/* 686 */       tieToStubCache.put(paramTie, stubEntry);
/*     */     } 
/*     */     
/* 689 */     return stubEntry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Tie getAndForgetTie(Object paramObject) {
/* 698 */     synchronized (stubToTieCache) {
/* 699 */       return (Tie)stubToTieCache.remove(paramObject);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void purgeStubForTie(Tie paramTie) {
/*     */     StubEntry stubEntry;
/* 708 */     synchronized (tieToStubCache) {
/* 709 */       stubEntry = (StubEntry)tieToStubCache.remove(paramTie);
/*     */     } 
/* 711 */     if (stubEntry != null) {
/* 712 */       synchronized (stubToTieCache) {
/* 713 */         stubToTieCache.remove(stubEntry.stub);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void purgeTieAndServant(Tie paramTie) {
/* 722 */     synchronized (tieCache) {
/* 723 */       Remote remote = paramTie.getTarget();
/* 724 */       if (remote != null) {
/* 725 */         tieCache.remove(remote);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String stubNameFromRepID(String paramString) {
/* 737 */     RepositoryId repositoryId = RepositoryId.cache.getId(paramString);
/* 738 */     String str = repositoryId.getClassName();
/*     */     
/* 740 */     if (repositoryId.isIDLType()) {
/* 741 */       str = idlStubName(str);
/*     */     } else {
/* 743 */       str = stubName(str);
/*     */     } 
/* 745 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Remote loadStub(Object paramObject, Class paramClass) {
/* 755 */     Remote remote = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 760 */       String str = null;
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 765 */         Delegate delegate = StubAdapter.getDelegate(paramObject);
/*     */         
/* 767 */         str = ((Delegate)delegate).get_codebase(paramObject);
/*     */       }
/* 769 */       catch (ClassCastException classCastException) {
/* 770 */         wrapper.classCastExceptionInLoadStub(classCastException);
/*     */       } 
/*     */ 
/*     */       
/* 774 */       PresentationManager.StubFactoryFactory stubFactoryFactory = ORB.getStubFactoryFactory();
/* 775 */       PresentationManager.StubFactory stubFactory = stubFactoryFactory.createStubFactory(paramClass
/* 776 */           .getName(), false, str, paramClass, paramClass
/* 777 */           .getClassLoader());
/* 778 */       remote = (Remote)stubFactory.makeStub();
/* 779 */       StubAdapter.setDelegate(remote, 
/* 780 */           StubAdapter.getDelegate(paramObject));
/* 781 */     } catch (Exception exception) {
/* 782 */       wrapper.exceptionInLoadStub(exception);
/*     */     } 
/*     */     
/* 785 */     return remote;
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
/*     */   public static Class loadStubClass(String paramString1, String paramString2, Class paramClass) throws ClassNotFoundException {
/* 801 */     if (paramString1.length() == 0) {
/* 802 */       throw new ClassNotFoundException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 808 */     String str = stubNameFromRepID(paramString1);
/*     */     
/* 810 */     ClassLoader classLoader = (paramClass == null) ? null : paramClass.getClassLoader();
/*     */     
/*     */     try {
/* 813 */       return loadClassOfType(str, paramString2, classLoader, paramClass, classLoader);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 818 */     catch (ClassNotFoundException classNotFoundException) {
/* 819 */       return loadClassOfType(PackagePrefixChecker.packagePrefix() + str, paramString2, classLoader, paramClass, classLoader);
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
/*     */   public static String stubName(String paramString) {
/* 832 */     return stubName(paramString, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String dynamicStubName(String paramString) {
/* 837 */     return stubName(paramString, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String stubName(String paramString, boolean paramBoolean) {
/* 843 */     String str = stubNameForCompiler(paramString, paramBoolean);
/* 844 */     if (PackagePrefixChecker.hasOffendingPrefix(str))
/* 845 */       str = PackagePrefixChecker.packagePrefix() + str; 
/* 846 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String stubNameForCompiler(String paramString) {
/* 851 */     return stubNameForCompiler(paramString, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String stubNameForCompiler(String paramString, boolean paramBoolean) {
/* 857 */     int i = paramString.indexOf('$');
/* 858 */     if (i < 0) {
/* 859 */       i = paramString.lastIndexOf('.');
/*     */     }
/*     */     
/* 862 */     String str = paramBoolean ? "_DynamicStub" : "_Stub";
/*     */ 
/*     */     
/* 865 */     if (i > 0) {
/* 866 */       return paramString.substring(0, i + 1) + "_" + paramString
/* 867 */         .substring(i + 1) + str;
/*     */     }
/* 869 */     return "_" + paramString + str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String tieName(String paramString) {
/* 878 */     return 
/* 879 */       PackagePrefixChecker.hasOffendingPrefix(tieNameForCompiler(paramString)) ? (
/* 880 */       PackagePrefixChecker.packagePrefix() + tieNameForCompiler(paramString)) : 
/* 881 */       tieNameForCompiler(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String tieNameForCompiler(String paramString) {
/* 886 */     int i = paramString.indexOf('$');
/* 887 */     if (i < 0) {
/* 888 */       i = paramString.lastIndexOf('.');
/*     */     }
/* 890 */     if (i > 0) {
/* 891 */       return paramString.substring(0, i + 1) + "_" + paramString
/*     */         
/* 893 */         .substring(i + 1) + "_Tie";
/*     */     }
/*     */     
/* 896 */     return "_" + paramString + "_Tie";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void throwNotSerializableForCorba(String paramString) {
/* 906 */     throw omgWrapper.notSerializable(CompletionStatus.COMPLETED_MAYBE, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String idlStubName(String paramString) {
/* 915 */     String str = null;
/* 916 */     int i = paramString.lastIndexOf('.');
/* 917 */     if (i > 0) {
/*     */ 
/*     */       
/* 920 */       str = paramString.substring(0, i + 1) + "_" + paramString.substring(i + 1) + "Stub";
/*     */     } else {
/*     */       
/* 923 */       str = "_" + paramString + "Stub";
/*     */     } 
/*     */ 
/*     */     
/* 927 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void printStackTrace() {
/* 932 */     Throwable throwable = new Throwable("Printing stack trace:");
/* 933 */     throwable.fillInStackTrace();
/* 934 */     throwable.printStackTrace();
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
/*     */   public static Object readObjectAndNarrow(InputStream paramInputStream, Class paramClass) throws ClassCastException {
/* 947 */     Object object = paramInputStream.read_Object();
/* 948 */     if (object != null) {
/* 949 */       return PortableRemoteObject.narrow(object, paramClass);
/*     */     }
/* 951 */     return null;
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
/*     */   public static Object readAbstractAndNarrow(InputStream paramInputStream, Class paramClass) throws ClassCastException {
/* 964 */     Object object = paramInputStream.read_abstract_interface();
/* 965 */     if (object != null) {
/* 966 */       return PortableRemoteObject.narrow(object, paramClass);
/*     */     }
/* 968 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int hexOf(char paramChar) {
/* 978 */     int i = paramChar - 48;
/* 979 */     if (i >= 0 && i <= 9) {
/* 980 */       return i;
/*     */     }
/* 982 */     i = paramChar - 97 + 10;
/* 983 */     if (i >= 10 && i <= 15) {
/* 984 */       return i;
/*     */     }
/* 986 */     i = paramChar - 65 + 10;
/* 987 */     if (i >= 10 && i <= 15) {
/* 988 */       return i;
/*     */     }
/* 990 */     throw wrapper.badHexDigit();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/util/Utility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */