/*     */ package sun.rmi.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutput;
/*     */ import java.io.ObjectStreamClass;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.rmi.AccessException;
/*     */ import java.rmi.MarshalException;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.ServerError;
/*     */ import java.rmi.ServerException;
/*     */ import java.rmi.UnmarshalException;
/*     */ import java.rmi.server.ExportException;
/*     */ import java.rmi.server.Operation;
/*     */ import java.rmi.server.RemoteCall;
/*     */ import java.rmi.server.RemoteRef;
/*     */ import java.rmi.server.RemoteStub;
/*     */ import java.rmi.server.ServerNotActiveException;
/*     */ import java.rmi.server.ServerRef;
/*     */ import java.rmi.server.Skeleton;
/*     */ import java.rmi.server.SkeletonNotFoundException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import sun.misc.ObjectInputFilter;
/*     */ import sun.rmi.runtime.Log;
/*     */ import sun.rmi.transport.LiveRef;
/*     */ import sun.rmi.transport.StreamRemoteCall;
/*     */ import sun.rmi.transport.Target;
/*     */ import sun.rmi.transport.tcp.TCPTransport;
/*     */ import sun.security.action.GetBooleanAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UnicastServerRef
/*     */   extends UnicastRef
/*     */   implements ServerRef, Dispatcher
/*     */ {
/*  85 */   public static final boolean logCalls = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("java.rmi.server.logCalls"))).booleanValue();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public static final Log callLog = Log.getLog("sun.rmi.server.call", "RMI", logCalls);
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -7384275867073752268L;
/*     */ 
/*     */   
/*  97 */   private static final boolean wantExceptionLog = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.rmi.server.exceptionTrace"))).booleanValue();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean forceStubUse = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   private static final boolean suppressStackTraces = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.rmi.server.suppressStackTraces"))).booleanValue();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Skeleton skel;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final transient ObjectInputFilter filter;
/*     */ 
/*     */ 
/*     */   
/* 121 */   private transient Map<Long, Method> hashToMethod_Map = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   private static final WeakClassHashMap<Map<Long, Method>> hashToMethod_Maps = new HashToMethod_Maps();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   private static final Map<Class<?>, ?> withoutSkeletons = Collections.synchronizedMap(new WeakHashMap<>());
/*     */   
/* 134 */   private final AtomicInteger methodCallIDCount = new AtomicInteger(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnicastServerRef() {
/* 141 */     this.filter = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnicastServerRef(LiveRef paramLiveRef) {
/* 150 */     super(paramLiveRef);
/* 151 */     this.filter = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnicastServerRef(LiveRef paramLiveRef, ObjectInputFilter paramObjectInputFilter) {
/* 159 */     super(paramLiveRef);
/* 160 */     this.filter = paramObjectInputFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnicastServerRef(int paramInt) {
/* 168 */     super(new LiveRef(paramInt));
/* 169 */     this.filter = null;
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
/*     */   public UnicastServerRef(boolean paramBoolean) {
/* 186 */     this(0);
/* 187 */     this.forceStubUse = paramBoolean;
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
/*     */   public RemoteStub exportObject(Remote paramRemote, Object paramObject) throws RemoteException {
/* 207 */     this.forceStubUse = true;
/* 208 */     return (RemoteStub)exportObject(paramRemote, paramObject, false);
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
/*     */   public Remote exportObject(Remote paramRemote, Object paramObject, boolean paramBoolean) throws RemoteException {
/*     */     Remote remote;
/* 222 */     Class<?> clazz = paramRemote.getClass();
/*     */ 
/*     */     
/*     */     try {
/* 226 */       remote = Util.createProxy(clazz, getClientRef(), this.forceStubUse);
/* 227 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 228 */       throw new ExportException("remote object implements illegal remote interface", illegalArgumentException);
/*     */     } 
/*     */     
/* 231 */     if (remote instanceof RemoteStub) {
/* 232 */       setSkeleton(paramRemote);
/*     */     }
/*     */ 
/*     */     
/* 236 */     Target target = new Target(paramRemote, this, remote, this.ref.getObjID(), paramBoolean);
/* 237 */     this.ref.exportObject(target);
/* 238 */     this.hashToMethod_Map = hashToMethod_Maps.get(clazz);
/* 239 */     return remote;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getClientHost() throws ServerNotActiveException {
/* 250 */     return TCPTransport.getClientHost();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSkeleton(Remote paramRemote) throws RemoteException {
/* 257 */     if (!withoutSkeletons.containsKey(paramRemote.getClass())) {
/*     */       try {
/* 259 */         this.skel = Util.createSkeleton(paramRemote);
/* 260 */       } catch (SkeletonNotFoundException skeletonNotFoundException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 267 */         withoutSkeletons.put(paramRemote.getClass(), null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispatch(Remote paramRemote, RemoteCall paramRemoteCall) throws IOException {
/*     */     try {
/*     */       int i;
/*     */       long l;
/*     */       ObjectInput objectInput;
/*     */       Object object;
/*     */       try {
/* 293 */         objectInput = paramRemoteCall.getInputStream();
/* 294 */         i = objectInput.readInt();
/* 295 */       } catch (Exception exception) {
/* 296 */         throw new UnmarshalException("error unmarshalling call header", exception);
/*     */       } 
/*     */       
/* 299 */       if (this.skel != null) {
/*     */         
/* 301 */         oldDispatch(paramRemote, paramRemoteCall, i);
/*     */         return;
/*     */       } 
/* 304 */       if (i >= 0) {
/* 305 */         throw new UnmarshalException("skeleton class not found but required for client version");
/*     */       }
/*     */       
/*     */       try {
/* 309 */         l = objectInput.readLong();
/* 310 */       } catch (Exception exception) {
/* 311 */         throw new UnmarshalException("error unmarshalling call header", exception);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 322 */       MarshalInputStream marshalInputStream = (MarshalInputStream)objectInput;
/* 323 */       marshalInputStream.skipDefaultResolveClass();
/*     */       
/* 325 */       Method method = this.hashToMethod_Map.get(Long.valueOf(l));
/* 326 */       if (method == null) {
/* 327 */         throw new UnmarshalException("unrecognized method hash: method not supported by remote object");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 332 */       logCall(paramRemote, method);
/*     */ 
/*     */       
/* 335 */       Object[] arrayOfObject = null;
/*     */       try {
/* 337 */         unmarshalCustomCallData(objectInput);
/* 338 */         arrayOfObject = unmarshalParameters(paramRemote, method, marshalInputStream);
/*     */       }
/* 340 */       catch (AccessException null) {
/*     */ 
/*     */         
/* 343 */         ((StreamRemoteCall)paramRemoteCall).discardPendingRefs();
/* 344 */         throw object;
/* 345 */       } catch (IOException|ClassNotFoundException null) {
/*     */         
/* 347 */         ((StreamRemoteCall)paramRemoteCall).discardPendingRefs();
/* 348 */         throw new UnmarshalException("error unmarshalling arguments", object);
/*     */       } finally {
/*     */         
/* 351 */         paramRemoteCall.releaseInputStream();
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 357 */         object = method.invoke(paramRemote, arrayOfObject);
/* 358 */       } catch (InvocationTargetException invocationTargetException) {
/* 359 */         throw invocationTargetException.getTargetException();
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 364 */         ObjectOutput objectOutput = paramRemoteCall.getResultStream(true);
/* 365 */         Class<?> clazz = method.getReturnType();
/* 366 */         if (clazz != void.class) {
/* 367 */           marshalValue(clazz, object, objectOutput);
/*     */         }
/* 369 */       } catch (IOException iOException) {
/* 370 */         throw new MarshalException("error marshalling return", iOException);
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 380 */     catch (Throwable throwable1) {
/* 381 */       Throwable throwable2 = throwable1;
/* 382 */       logCallException(throwable1);
/*     */       
/* 384 */       ObjectOutput objectOutput = paramRemoteCall.getResultStream(false);
/* 385 */       if (throwable1 instanceof Error) {
/* 386 */         throwable1 = new ServerError("Error occurred in server thread", (Error)throwable1);
/*     */       }
/* 388 */       else if (throwable1 instanceof RemoteException) {
/* 389 */         throwable1 = new ServerException("RemoteException occurred in server thread", (Exception)throwable1);
/*     */       } 
/*     */ 
/*     */       
/* 393 */       if (suppressStackTraces) {
/* 394 */         clearStackTraces(throwable1);
/*     */       }
/* 396 */       objectOutput.writeObject(throwable1);
/*     */ 
/*     */ 
/*     */       
/* 400 */       if (throwable2 instanceof AccessException) {
/* 401 */         throw new IOException("Connection is not reusable", throwable2);
/*     */       }
/*     */     } finally {
/* 404 */       paramRemoteCall.releaseInputStream();
/* 405 */       paramRemoteCall.releaseOutputStream();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void unmarshalCustomCallData(ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/* 415 */     if (this.filter != null && paramObjectInput instanceof ObjectInputStream) {
/*     */ 
/*     */       
/* 418 */       final ObjectInputStream ois = (ObjectInputStream)paramObjectInput;
/*     */       
/* 420 */       AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */           {
/*     */             public Void run() {
/* 423 */               ObjectInputFilter.Config.setObjectInputFilter(ois, UnicastServerRef.this.filter);
/* 424 */               return null;
/*     */             }
/*     */           });
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
/*     */ 
/*     */ 
/*     */   
/*     */   private void oldDispatch(Remote paramRemote, RemoteCall paramRemoteCall, int paramInt) throws Exception {
/*     */     long l;
/* 450 */     ObjectInput objectInput = paramRemoteCall.getInputStream();
/*     */     try {
/* 452 */       Class<?> clazz = Class.forName("sun.rmi.transport.DGCImpl_Skel");
/* 453 */       if (clazz.isAssignableFrom(this.skel.getClass())) {
/* 454 */         ((MarshalInputStream)objectInput).useCodebaseOnly();
/*     */       }
/* 456 */     } catch (ClassNotFoundException classNotFoundException) {}
/*     */     
/*     */     try {
/* 459 */       l = objectInput.readLong();
/* 460 */     } catch (Exception exception) {
/* 461 */       throw new UnmarshalException("error unmarshalling call header", exception);
/*     */     } 
/*     */ 
/*     */     
/* 465 */     Operation[] arrayOfOperation = this.skel.getOperations();
/* 466 */     logCall(paramRemote, (paramInt >= 0 && paramInt < arrayOfOperation.length) ? arrayOfOperation[paramInt] : ("op: " + paramInt));
/* 467 */     unmarshalCustomCallData(objectInput);
/*     */     
/* 469 */     this.skel.dispatch(paramRemote, paramRemoteCall, paramInt, l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearStackTraces(Throwable paramThrowable) {
/* 478 */     StackTraceElement[] arrayOfStackTraceElement = new StackTraceElement[0];
/* 479 */     while (paramThrowable != null) {
/* 480 */       paramThrowable.setStackTrace(arrayOfStackTraceElement);
/* 481 */       paramThrowable = paramThrowable.getCause();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void logCall(Remote paramRemote, Object paramObject) {
/* 490 */     if (callLog.isLoggable(Log.VERBOSE)) {
/*     */       String str;
/*     */       try {
/* 493 */         str = getClientHost();
/* 494 */       } catch (ServerNotActiveException serverNotActiveException) {
/* 495 */         str = "(local)";
/*     */       } 
/* 497 */       callLog.log(Log.VERBOSE, "[" + str + ": " + paramRemote
/* 498 */           .getClass().getName() + this.ref
/* 499 */           .getObjID().toString() + ": " + paramObject + "]");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void logCallException(Throwable paramThrowable) {
/* 509 */     if (callLog.isLoggable(Log.BRIEF)) {
/* 510 */       String str = "";
/*     */       try {
/* 512 */         str = "[" + getClientHost() + "] ";
/* 513 */       } catch (ServerNotActiveException serverNotActiveException) {}
/*     */       
/* 515 */       callLog.log(Log.BRIEF, str + "exception: ", paramThrowable);
/*     */     } 
/*     */ 
/*     */     
/* 519 */     if (wantExceptionLog) {
/* 520 */       PrintStream printStream = System.err;
/* 521 */       synchronized (printStream) {
/* 522 */         printStream.println();
/* 523 */         printStream.println("Exception dispatching call to " + this.ref
/* 524 */             .getObjID() + " in thread \"" + 
/* 525 */             Thread.currentThread().getName() + "\" at " + new Date() + ":");
/*     */         
/* 527 */         paramThrowable.printStackTrace(printStream);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRefClass(ObjectOutput paramObjectOutput) {
/* 536 */     return "UnicastServerRef";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected RemoteRef getClientRef() {
/* 546 */     return new UnicastRef(this.ref);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeExternal(ObjectOutput paramObjectOutput) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readExternal(ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/* 564 */     this.ref = null;
/* 565 */     this.skel = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class HashToMethod_Maps
/*     */     extends WeakClassHashMap<Map<Long, Method>>
/*     */   {
/*     */     protected Map<Long, Method> computeValue(Class<?> param1Class) {
/* 579 */       HashMap<Object, Object> hashMap = new HashMap<>();
/* 580 */       Class<?> clazz = param1Class;
/* 581 */       for (; clazz != null; 
/* 582 */         clazz = clazz.getSuperclass()) {
/*     */         
/* 584 */         for (Class<?> clazz1 : clazz.getInterfaces()) {
/* 585 */           if (Remote.class.isAssignableFrom(clazz1)) {
/* 586 */             for (Method method1 : clazz1.getMethods()) {
/* 587 */               final Method m = method1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 593 */               AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */                   {
/*     */                     public Void run() {
/* 596 */                       m.setAccessible(true);
/* 597 */                       return null;
/*     */                     }
/*     */                   });
/* 600 */               hashMap.put(Long.valueOf(Util.computeMethodHash(method2)), method2);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/* 605 */       return (Map)hashMap;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object[] unmarshalParameters(Object paramObject, Method paramMethod, MarshalInputStream paramMarshalInputStream) throws IOException, ClassNotFoundException {
/* 615 */     return (paramObject instanceof DeserializationChecker) ? 
/* 616 */       unmarshalParametersChecked((DeserializationChecker)paramObject, paramMethod, paramMarshalInputStream) : 
/* 617 */       unmarshalParametersUnchecked(paramMethod, paramMarshalInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object[] unmarshalParametersUnchecked(Method paramMethod, ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/* 626 */     Class[] arrayOfClass = paramMethod.getParameterTypes();
/* 627 */     Object[] arrayOfObject = new Object[arrayOfClass.length];
/* 628 */     for (byte b = 0; b < arrayOfClass.length; b++) {
/* 629 */       arrayOfObject[b] = unmarshalValue(arrayOfClass[b], paramObjectInput);
/*     */     }
/* 631 */     return arrayOfObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object[] unmarshalParametersChecked(DeserializationChecker paramDeserializationChecker, Method paramMethod, MarshalInputStream paramMarshalInputStream) throws IOException, ClassNotFoundException {
/* 642 */     int i = this.methodCallIDCount.getAndIncrement();
/* 643 */     MyChecker myChecker = new MyChecker(paramDeserializationChecker, paramMethod, i);
/* 644 */     paramMarshalInputStream.setStreamChecker(myChecker);
/*     */     try {
/* 646 */       Class[] arrayOfClass = paramMethod.getParameterTypes();
/* 647 */       Object[] arrayOfObject = new Object[arrayOfClass.length];
/* 648 */       for (byte b = 0; b < arrayOfClass.length; b++) {
/* 649 */         myChecker.setIndex(b);
/* 650 */         arrayOfObject[b] = unmarshalValue(arrayOfClass[b], paramMarshalInputStream);
/*     */       } 
/* 652 */       myChecker.end(i);
/* 653 */       return arrayOfObject;
/*     */     } finally {
/* 655 */       paramMarshalInputStream.setStreamChecker(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class MyChecker implements MarshalInputStream.StreamChecker {
/*     */     private final DeserializationChecker descriptorCheck;
/*     */     private final Method method;
/*     */     private final int callID;
/*     */     private int parameterIndex;
/*     */     
/*     */     MyChecker(DeserializationChecker param1DeserializationChecker, Method param1Method, int param1Int) {
/* 666 */       this.descriptorCheck = param1DeserializationChecker;
/* 667 */       this.method = param1Method;
/* 668 */       this.callID = param1Int;
/*     */     }
/*     */ 
/*     */     
/*     */     public void validateDescriptor(ObjectStreamClass param1ObjectStreamClass) {
/* 673 */       this.descriptorCheck.check(this.method, param1ObjectStreamClass, this.parameterIndex, this.callID);
/*     */     }
/*     */ 
/*     */     
/*     */     public void checkProxyInterfaceNames(String[] param1ArrayOfString) {
/* 678 */       this.descriptorCheck.checkProxyClass(this.method, param1ArrayOfString, this.parameterIndex, this.callID);
/*     */     }
/*     */     
/*     */     void setIndex(int param1Int) {
/* 682 */       this.parameterIndex = param1Int;
/*     */     }
/*     */     
/*     */     void end(int param1Int) {
/* 686 */       this.descriptorCheck.end(param1Int);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/server/UnicastServerRef.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */