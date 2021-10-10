/*     */ package com.sun.corba.se.impl.javax.rmi.CORBA;
/*     */ 
/*     */ import com.sun.corba.se.impl.corba.AnyImpl;
/*     */ import com.sun.corba.se.impl.io.ValueHandlerImpl;
/*     */ import com.sun.corba.se.impl.logging.UtilSystemException;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.impl.util.IdentityHashtable;
/*     */ import com.sun.corba.se.impl.util.JDKBridge;
/*     */ import com.sun.corba.se.impl.util.Utility;
/*     */ import com.sun.corba.se.pept.transport.ContactInfoList;
/*     */ import com.sun.corba.se.spi.copyobject.CopierManager;
/*     */ import com.sun.corba.se.spi.copyobject.ObjectCopier;
/*     */ import com.sun.corba.se.spi.copyobject.ReflectiveCopyException;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.orb.ORBVersionFactory;
/*     */ import com.sun.corba.se.spi.protocol.CorbaClientDelegate;
/*     */ import com.sun.corba.se.spi.protocol.LocalClientRequestDispatcher;
/*     */ import com.sun.corba.se.spi.transport.CorbaContactInfoList;
/*     */ import java.io.NotSerializableException;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.rmi.AccessException;
/*     */ import java.rmi.MarshalException;
/*     */ import java.rmi.NoSuchObjectException;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.ServerError;
/*     */ import java.rmi.ServerException;
/*     */ import java.rmi.UnexpectedException;
/*     */ import java.rmi.server.RMIClassLoader;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.EmptyStackException;
/*     */ import java.util.Enumeration;
/*     */ import javax.rmi.CORBA.Stub;
/*     */ import javax.rmi.CORBA.Tie;
/*     */ import javax.rmi.CORBA.UtilDelegate;
/*     */ import javax.rmi.CORBA.ValueHandler;
/*     */ import javax.transaction.InvalidTransactionException;
/*     */ import javax.transaction.TransactionRequiredException;
/*     */ import javax.transaction.TransactionRolledbackException;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.BAD_OPERATION;
/*     */ import org.omg.CORBA.OBJ_ADAPTER;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.SystemException;
/*     */ import org.omg.CORBA.TCKind;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.portable.Delegate;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA.portable.UnknownException;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ import org.omg.CORBA_2_3.portable.OutputStream;
/*     */ import sun.corba.SharedSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Util
/*     */   implements UtilDelegate
/*     */ {
/* 123 */   private static KeepAlive keepAlive = null;
/*     */ 
/*     */   
/* 126 */   private static IdentityHashtable exportedServants = new IdentityHashtable();
/*     */ 
/*     */   
/* 129 */   private static final ValueHandlerImpl valueHandlerSingleton = SharedSecrets.getJavaCorbaAccess().newValueHandlerImpl();
/*     */   
/* 131 */   private UtilSystemException utilWrapper = UtilSystemException.get("rpc.encoding");
/*     */ 
/*     */   
/* 134 */   private static Util instance = null;
/*     */   
/*     */   public Util() {
/* 137 */     setInstance(this);
/*     */   }
/*     */   
/*     */   private static void setInstance(Util paramUtil) {
/* 141 */     assert instance == null : "Instance already defined";
/* 142 */     instance = paramUtil;
/*     */   }
/*     */   
/*     */   public static Util getInstance() {
/* 146 */     return instance;
/*     */   }
/*     */   
/*     */   public static boolean isInstanceDefined() {
/* 150 */     return (instance != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregisterTargetsForORB(ORB paramORB) {
/* 157 */     for (Enumeration<Object> enumeration = exportedServants.keys(); enumeration.hasMoreElements(); ) {
/*     */       
/* 159 */       Tie tie = (Tie)enumeration.nextElement();
/* 160 */       Remote remote = (tie instanceof Tie) ? ((Tie)tie).getTarget() : (Remote)tie;
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 165 */         if (paramORB == getTie(remote).orb()) {
/*     */           try {
/* 167 */             unexportObject(remote);
/* 168 */           } catch (NoSuchObjectException noSuchObjectException) {}
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 173 */       catch (BAD_OPERATION bAD_OPERATION) {}
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
/*     */   public RemoteException mapSystemException(SystemException paramSystemException) {
/*     */     String str3;
/* 186 */     if (paramSystemException instanceof UnknownException) {
/* 187 */       Throwable throwable = ((UnknownException)paramSystemException).originalEx;
/* 188 */       if (throwable instanceof Error)
/* 189 */         return new ServerError("Error occurred in server thread", (Error)throwable); 
/* 190 */       if (throwable instanceof RemoteException) {
/* 191 */         return new ServerException("RemoteException occurred in server thread", (Exception)throwable);
/*     */       }
/* 193 */       if (throwable instanceof RuntimeException) {
/* 194 */         throw (RuntimeException)throwable;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 199 */     String str1 = paramSystemException.getClass().getName();
/* 200 */     String str2 = str1.substring(str1.lastIndexOf('.') + 1);
/*     */     
/* 202 */     switch (paramSystemException.completed.value()) {
/*     */       case 0:
/* 204 */         str3 = "Yes";
/*     */         break;
/*     */       case 1:
/* 207 */         str3 = "No";
/*     */         break;
/*     */       
/*     */       default:
/* 211 */         str3 = "Maybe";
/*     */         break;
/*     */     } 
/*     */     
/* 215 */     String str4 = "CORBA " + str2 + " " + paramSystemException.minor + " " + str3;
/*     */ 
/*     */     
/* 218 */     if (paramSystemException instanceof org.omg.CORBA.COMM_FAILURE)
/* 219 */       return new MarshalException(str4, (Exception)paramSystemException); 
/* 220 */     if (paramSystemException instanceof org.omg.CORBA.INV_OBJREF) {
/* 221 */       NoSuchObjectException noSuchObjectException = new NoSuchObjectException(str4);
/* 222 */       noSuchObjectException.detail = (Throwable)paramSystemException;
/* 223 */       return noSuchObjectException;
/* 224 */     }  if (paramSystemException instanceof org.omg.CORBA.NO_PERMISSION)
/* 225 */       return new AccessException(str4, (Exception)paramSystemException); 
/* 226 */     if (paramSystemException instanceof org.omg.CORBA.MARSHAL)
/* 227 */       return new MarshalException(str4, (Exception)paramSystemException); 
/* 228 */     if (paramSystemException instanceof org.omg.CORBA.OBJECT_NOT_EXIST) {
/* 229 */       NoSuchObjectException noSuchObjectException = new NoSuchObjectException(str4);
/* 230 */       noSuchObjectException.detail = (Throwable)paramSystemException;
/* 231 */       return noSuchObjectException;
/* 232 */     }  if (paramSystemException instanceof org.omg.CORBA.TRANSACTION_REQUIRED) {
/* 233 */       TransactionRequiredException transactionRequiredException = new TransactionRequiredException(str4);
/* 234 */       ((RemoteException)transactionRequiredException).detail = (Throwable)paramSystemException;
/* 235 */       return (RemoteException)transactionRequiredException;
/* 236 */     }  if (paramSystemException instanceof org.omg.CORBA.TRANSACTION_ROLLEDBACK) {
/* 237 */       TransactionRolledbackException transactionRolledbackException = new TransactionRolledbackException(str4);
/* 238 */       ((RemoteException)transactionRolledbackException).detail = (Throwable)paramSystemException;
/* 239 */       return (RemoteException)transactionRolledbackException;
/* 240 */     }  if (paramSystemException instanceof org.omg.CORBA.INVALID_TRANSACTION) {
/* 241 */       InvalidTransactionException invalidTransactionException = new InvalidTransactionException(str4);
/* 242 */       ((RemoteException)invalidTransactionException).detail = (Throwable)paramSystemException;
/* 243 */       return (RemoteException)invalidTransactionException;
/* 244 */     }  if (paramSystemException instanceof org.omg.CORBA.BAD_PARAM) {
/* 245 */       NotSerializableException notSerializableException; SystemException systemException = paramSystemException;
/*     */ 
/*     */ 
/*     */       
/* 249 */       if (paramSystemException.minor == 1398079489 || paramSystemException.minor == 1330446342) {
/*     */ 
/*     */         
/* 252 */         if (paramSystemException.getMessage() != null) {
/* 253 */           notSerializableException = new NotSerializableException(paramSystemException.getMessage());
/*     */         } else {
/* 255 */           notSerializableException = new NotSerializableException();
/*     */         } 
/* 257 */         notSerializableException.initCause((Throwable)paramSystemException);
/*     */       } 
/*     */       
/* 260 */       return new MarshalException(str4, notSerializableException);
/* 261 */     }  if (paramSystemException instanceof org.omg.CORBA.ACTIVITY_REQUIRED) {
/*     */       try {
/* 263 */         Class clazz = SharedSecrets.getJavaCorbaAccess().loadClass("javax.activity.ActivityRequiredException");
/*     */         
/* 265 */         Class[] arrayOfClass = new Class[2];
/* 266 */         arrayOfClass[0] = String.class;
/* 267 */         arrayOfClass[1] = Throwable.class;
/* 268 */         Constructor<RemoteException> constructor = clazz.getConstructor(arrayOfClass);
/* 269 */         Object[] arrayOfObject = new Object[2];
/* 270 */         arrayOfObject[0] = str4;
/* 271 */         arrayOfObject[1] = paramSystemException;
/* 272 */         return constructor.newInstance(arrayOfObject);
/* 273 */       } catch (Throwable throwable) {
/* 274 */         this.utilWrapper.classNotFound(throwable, "javax.activity.ActivityRequiredException");
/*     */       }
/*     */     
/* 277 */     } else if (paramSystemException instanceof org.omg.CORBA.ACTIVITY_COMPLETED) {
/*     */       try {
/* 279 */         Class clazz = SharedSecrets.getJavaCorbaAccess().loadClass("javax.activity.ActivityCompletedException");
/*     */         
/* 281 */         Class[] arrayOfClass = new Class[2];
/* 282 */         arrayOfClass[0] = String.class;
/* 283 */         arrayOfClass[1] = Throwable.class;
/* 284 */         Constructor<RemoteException> constructor = clazz.getConstructor(arrayOfClass);
/* 285 */         Object[] arrayOfObject = new Object[2];
/* 286 */         arrayOfObject[0] = str4;
/* 287 */         arrayOfObject[1] = paramSystemException;
/* 288 */         return constructor.newInstance(arrayOfObject);
/* 289 */       } catch (Throwable throwable) {
/* 290 */         this.utilWrapper.classNotFound(throwable, "javax.activity.ActivityCompletedException");
/*     */       }
/*     */     
/* 293 */     } else if (paramSystemException instanceof org.omg.CORBA.INVALID_ACTIVITY) {
/*     */       try {
/* 295 */         Class clazz = SharedSecrets.getJavaCorbaAccess().loadClass("javax.activity.InvalidActivityException");
/*     */         
/* 297 */         Class[] arrayOfClass = new Class[2];
/* 298 */         arrayOfClass[0] = String.class;
/* 299 */         arrayOfClass[1] = Throwable.class;
/* 300 */         Constructor<RemoteException> constructor = clazz.getConstructor(arrayOfClass);
/* 301 */         Object[] arrayOfObject = new Object[2];
/* 302 */         arrayOfObject[0] = str4;
/* 303 */         arrayOfObject[1] = paramSystemException;
/* 304 */         return constructor.newInstance(arrayOfObject);
/* 305 */       } catch (Throwable throwable) {
/* 306 */         this.utilWrapper.classNotFound(throwable, "javax.activity.InvalidActivityException");
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 312 */     return new RemoteException(str4, (Throwable)paramSystemException);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeAny(OutputStream paramOutputStream, Object paramObject) {
/* 323 */     ORB oRB = paramOutputStream.orb();
/*     */ 
/*     */     
/* 326 */     Any any = oRB.create_any();
/*     */ 
/*     */     
/* 329 */     Object object = Utility.autoConnect(paramObject, oRB, false);
/*     */     
/* 331 */     if (object instanceof Object) {
/* 332 */       any.insert_Object((Object)object);
/*     */     }
/* 334 */     else if (object == null) {
/*     */ 
/*     */       
/* 337 */       any.insert_Value(null, createTypeCodeForNull(oRB));
/*     */     }
/* 339 */     else if (object instanceof Serializable) {
/*     */ 
/*     */       
/* 342 */       TypeCode typeCode = createTypeCode((Serializable)object, any, oRB);
/* 343 */       if (typeCode == null)
/* 344 */       { any.insert_Value((Serializable)object); }
/*     */       else
/* 346 */       { any.insert_Value((Serializable)object, typeCode); } 
/* 347 */     } else if (object instanceof Remote) {
/* 348 */       ORBUtility.throwNotSerializableForCorba(object.getClass().getName());
/*     */     } else {
/* 350 */       ORBUtility.throwNotSerializableForCorba(object.getClass().getName());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 355 */     paramOutputStream.write_any(any);
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
/*     */ 
/*     */ 
/*     */   
/*     */   private TypeCode createTypeCode(Serializable paramSerializable, Any paramAny, ORB paramORB) {
/* 377 */     if (paramAny instanceof AnyImpl && paramORB instanceof ORB) {
/*     */ 
/*     */       
/* 380 */       AnyImpl anyImpl = (AnyImpl)paramAny;
/*     */ 
/*     */       
/* 383 */       ORB oRB = (ORB)paramORB;
/*     */       
/* 385 */       return anyImpl.createTypeCodeForClass(paramSerializable.getClass(), oRB);
/*     */     } 
/*     */     
/* 388 */     return null;
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
/*     */   private TypeCode createTypeCodeForNull(ORB paramORB) {
/* 400 */     if (paramORB instanceof ORB) {
/*     */       
/* 402 */       ORB oRB = (ORB)paramORB;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 409 */       if (!ORBVersionFactory.getFOREIGN().equals(oRB.getORBVersion()) && 
/* 410 */         ORBVersionFactory.getNEWER().compareTo(oRB.getORBVersion()) > 0)
/*     */       {
/* 412 */         return paramORB.get_primitive_tc(TCKind.tk_value);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 419 */     String str = "IDL:omg.org/CORBA/AbstractBase:1.0";
/*     */     
/* 421 */     return paramORB.create_abstract_interface_tc(str, "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object readAny(InputStream paramInputStream) {
/* 431 */     Any any = paramInputStream.read_any();
/* 432 */     if (any.type().kind().value() == 14) {
/* 433 */       return any.extract_Object();
/*     */     }
/* 435 */     return any.extract_Value();
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
/*     */   public void writeRemoteObject(OutputStream paramOutputStream, Object paramObject) {
/* 452 */     Object object = Utility.autoConnect(paramObject, paramOutputStream.orb(), false);
/* 453 */     paramOutputStream.write_Object((Object)object);
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
/*     */   public void writeAbstractObject(OutputStream paramOutputStream, Object paramObject) {
/* 470 */     Object object = Utility.autoConnect(paramObject, paramOutputStream.orb(), false);
/* 471 */     ((OutputStream)paramOutputStream).write_abstract_interface(object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerTarget(Tie paramTie, Remote paramRemote) {
/* 482 */     synchronized (exportedServants) {
/*     */       
/* 484 */       if (lookupTie(paramRemote) == null) {
/*     */         
/* 486 */         exportedServants.put(paramRemote, paramTie);
/* 487 */         paramTie.setTarget(paramRemote);
/*     */ 
/*     */         
/* 490 */         if (keepAlive == null) {
/*     */ 
/*     */           
/* 493 */           keepAlive = AccessController.<KeepAlive>doPrivileged(new PrivilegedAction<KeepAlive>() {
/*     */                 public Object run() {
/* 495 */                   return new KeepAlive();
/*     */                 }
/*     */               });
/* 498 */           keepAlive.start();
/*     */         } 
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
/*     */   
/*     */   public void unexportObject(Remote paramRemote) throws NoSuchObjectException {
/* 512 */     synchronized (exportedServants) {
/* 513 */       Tie tie = lookupTie(paramRemote);
/* 514 */       if (tie != null) {
/* 515 */         exportedServants.remove(paramRemote);
/* 516 */         Utility.purgeStubForTie(tie);
/* 517 */         Utility.purgeTieAndServant(tie);
/*     */         try {
/* 519 */           cleanUpTie(tie);
/* 520 */         } catch (BAD_OPERATION bAD_OPERATION) {
/*     */         
/* 522 */         } catch (OBJ_ADAPTER oBJ_ADAPTER) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 528 */         if (exportedServants.isEmpty()) {
/* 529 */           keepAlive.quit();
/* 530 */           keepAlive = null;
/*     */         } 
/*     */       } else {
/* 533 */         throw new NoSuchObjectException("Tie not found");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void cleanUpTie(Tie paramTie) throws NoSuchObjectException {
/* 541 */     paramTie.setTarget(null);
/* 542 */     paramTie.deactivate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tie getTie(Remote paramRemote) {
/* 551 */     synchronized (exportedServants) {
/* 552 */       return lookupTie(paramRemote);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Tie lookupTie(Remote paramRemote) {
/* 561 */     Tie tie = (Tie)exportedServants.get(paramRemote);
/* 562 */     if (tie == null && paramRemote instanceof Tie && 
/* 563 */       exportedServants.contains(paramRemote)) {
/* 564 */       tie = (Tie)paramRemote;
/*     */     }
/*     */     
/* 567 */     return tie;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueHandler createValueHandler() {
/* 577 */     return (ValueHandler)valueHandlerSingleton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCodebase(Class<?> paramClass) {
/* 586 */     return RMIClassLoader.getClassAnnotation(paramClass);
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
/*     */   public Class loadClass(String paramString1, String paramString2, ClassLoader paramClassLoader) throws ClassNotFoundException {
/* 602 */     return JDKBridge.loadClass(paramString1, paramString2, paramClassLoader);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocal(Stub paramStub) throws RemoteException {
/* 627 */     boolean bool = false;
/*     */     
/*     */     try {
/* 630 */       Delegate delegate = paramStub._get_delegate();
/* 631 */       if (delegate instanceof CorbaClientDelegate) {
/*     */         
/* 633 */         CorbaClientDelegate corbaClientDelegate = (CorbaClientDelegate)delegate;
/* 634 */         ContactInfoList contactInfoList = corbaClientDelegate.getContactInfoList();
/* 635 */         if (contactInfoList instanceof CorbaContactInfoList) {
/* 636 */           CorbaContactInfoList corbaContactInfoList = (CorbaContactInfoList)contactInfoList;
/* 637 */           LocalClientRequestDispatcher localClientRequestDispatcher = corbaContactInfoList.getLocalClientRequestDispatcher();
/* 638 */           bool = localClientRequestDispatcher.useLocalInvocation(null);
/*     */         } 
/*     */       } else {
/*     */         
/* 642 */         bool = delegate.is_local((Object)paramStub);
/*     */       } 
/* 644 */     } catch (SystemException systemException) {
/* 645 */       throw javax.rmi.CORBA.Util.mapSystemException(systemException);
/*     */     } 
/*     */     
/* 648 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RemoteException wrapException(Throwable paramThrowable) {
/* 659 */     if (paramThrowable instanceof SystemException) {
/* 660 */       return mapSystemException((SystemException)paramThrowable);
/*     */     }
/*     */     
/* 663 */     if (paramThrowable instanceof Error)
/* 664 */       return new ServerError("Error occurred in server thread", (Error)paramThrowable); 
/* 665 */     if (paramThrowable instanceof RemoteException) {
/* 666 */       return new ServerException("RemoteException occurred in server thread", (Exception)paramThrowable);
/*     */     }
/* 668 */     if (paramThrowable instanceof RuntimeException) {
/* 669 */       throw (RuntimeException)paramThrowable;
/*     */     }
/*     */     
/* 672 */     if (paramThrowable instanceof Exception) {
/* 673 */       return new UnexpectedException(paramThrowable.toString(), (Exception)paramThrowable);
/*     */     }
/* 675 */     return new UnexpectedException(paramThrowable.toString());
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
/*     */   public Object[] copyObjects(Object[] paramArrayOfObject, ORB paramORB) throws RemoteException {
/* 690 */     if (paramArrayOfObject == null)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 695 */       throw new NullPointerException();
/*     */     }
/* 697 */     Class<?> clazz = paramArrayOfObject.getClass().getComponentType();
/* 698 */     if (Remote.class.isAssignableFrom(clazz) && !clazz.isInterface()) {
/*     */ 
/*     */ 
/*     */       
/* 702 */       Remote[] arrayOfRemote = new Remote[paramArrayOfObject.length];
/* 703 */       System.arraycopy(paramArrayOfObject, 0, arrayOfRemote, 0, paramArrayOfObject.length);
/* 704 */       return (Object[])copyObject(arrayOfRemote, paramORB);
/*     */     } 
/* 706 */     return (Object[])copyObject(paramArrayOfObject, paramORB);
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
/*     */   public Object copyObject(Object paramObject, ORB paramORB) throws RemoteException {
/* 720 */     if (paramORB instanceof ORB) {
/* 721 */       ORB oRB = (ORB)paramORB;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 727 */         return oRB.peekInvocationInfo().getCopierFactory().make().copy(paramObject);
/* 728 */       } catch (EmptyStackException emptyStackException) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 733 */         CopierManager copierManager = oRB.getCopierManager();
/* 734 */         ObjectCopier objectCopier = copierManager.getDefaultObjectCopierFactory().make();
/* 735 */         return objectCopier.copy(paramObject);
/*     */       }
/* 737 */       catch (ReflectiveCopyException reflectiveCopyException) {
/* 738 */         RemoteException remoteException = new RemoteException();
/* 739 */         remoteException.initCause((Throwable)reflectiveCopyException);
/* 740 */         throw remoteException;
/*     */       } 
/*     */     } 
/*     */     
/* 744 */     OutputStream outputStream = (OutputStream)paramORB.create_output_stream();
/* 745 */     outputStream.write_value((Serializable)paramObject);
/*     */     
/* 747 */     InputStream inputStream = (InputStream)outputStream.create_input_stream();
/* 748 */     return inputStream.read_value();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/javax/rmi/CORBA/Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */