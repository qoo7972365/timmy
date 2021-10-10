/*     */ package sun.rmi.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutput;
/*     */ import java.lang.reflect.Method;
/*     */ import java.rmi.MarshalException;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.server.Operation;
/*     */ import java.rmi.server.RemoteCall;
/*     */ import java.rmi.server.RemoteObject;
/*     */ import java.rmi.server.RemoteRef;
/*     */ import java.security.AccessController;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.rmi.runtime.Log;
/*     */ import sun.rmi.transport.Connection;
/*     */ import sun.rmi.transport.LiveRef;
/*     */ import sun.rmi.transport.StreamRemoteCall;
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
/*     */ public class UnicastRef
/*     */   implements RemoteRef
/*     */ {
/*  62 */   public static final Log clientRefLog = Log.getLog("sun.rmi.client.ref", "transport", Util.logLevel);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public static final Log clientCallLog = Log.getLog("sun.rmi.client.call", "RMI", (
/*  69 */       (Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.rmi.client.logCalls"))).booleanValue());
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 8258372400816541186L;
/*     */ 
/*     */   
/*     */   protected LiveRef ref;
/*     */ 
/*     */ 
/*     */   
/*     */   public UnicastRef() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public UnicastRef(LiveRef paramLiveRef) {
/*  85 */     this.ref = paramLiveRef;
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
/*     */   public LiveRef getLiveRef() {
/*  97 */     return this.ref;
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
/*     */ 
/*     */   
/*     */   public Object invoke(Remote paramRemote, Method paramMethod, Object[] paramArrayOfObject, long paramLong) throws Exception {
/* 124 */     if (clientRefLog.isLoggable(Log.VERBOSE)) {
/* 125 */       clientRefLog.log(Log.VERBOSE, "method: " + paramMethod);
/*     */     }
/*     */     
/* 128 */     if (clientCallLog.isLoggable(Log.VERBOSE)) {
/* 129 */       logClientCall(paramRemote, paramMethod);
/*     */     }
/*     */     
/* 132 */     Connection connection = this.ref.getChannel().newConnection();
/* 133 */     StreamRemoteCall streamRemoteCall = null;
/* 134 */     boolean bool1 = true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     boolean bool2 = false;
/*     */     
/*     */     try {
/* 142 */       if (clientRefLog.isLoggable(Log.VERBOSE)) {
/* 143 */         clientRefLog.log(Log.VERBOSE, "opnum = " + paramLong);
/*     */       }
/*     */ 
/*     */       
/* 147 */       streamRemoteCall = new StreamRemoteCall(connection, this.ref.getObjID(), -1, paramLong);
/*     */ 
/*     */       
/*     */       try {
/* 151 */         ObjectOutput objectOutput = streamRemoteCall.getOutputStream();
/* 152 */         marshalCustomCallData(objectOutput);
/* 153 */         Class[] arrayOfClass = paramMethod.getParameterTypes();
/* 154 */         for (byte b = 0; b < arrayOfClass.length; b++) {
/* 155 */           marshalValue(arrayOfClass[b], paramArrayOfObject[b], objectOutput);
/*     */         }
/* 157 */       } catch (IOException iOException) {
/* 158 */         clientRefLog.log(Log.BRIEF, "IOException marshalling arguments: ", iOException);
/*     */         
/* 160 */         throw new MarshalException("error marshalling arguments", iOException);
/*     */       } 
/*     */ 
/*     */       
/* 164 */       streamRemoteCall.executeCall();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 212 */     catch (RuntimeException runtimeException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 221 */       if (streamRemoteCall == null || streamRemoteCall
/* 222 */         .getServerException() != runtimeException)
/*     */       {
/* 224 */         bool1 = false;
/*     */       }
/* 226 */       throw runtimeException;
/*     */     }
/* 228 */     catch (RemoteException remoteException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 236 */       bool1 = false;
/* 237 */       throw remoteException;
/*     */     }
/* 239 */     catch (Error error) {
/*     */ 
/*     */ 
/*     */       
/* 243 */       bool1 = false;
/* 244 */       throw error;
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 251 */       if (!bool2) {
/* 252 */         if (clientRefLog.isLoggable(Log.BRIEF)) {
/* 253 */           clientRefLog.log(Log.BRIEF, "free connection (reuse = " + bool1 + ")");
/*     */         }
/*     */         
/* 256 */         this.ref.getChannel().free(connection, bool1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void marshalCustomCallData(ObjectOutput paramObjectOutput) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void marshalValue(Class<?> paramClass, Object paramObject, ObjectOutput paramObjectOutput) throws IOException {
/* 272 */     if (paramClass.isPrimitive()) {
/* 273 */       if (paramClass == int.class) {
/* 274 */         paramObjectOutput.writeInt(((Integer)paramObject).intValue());
/* 275 */       } else if (paramClass == boolean.class) {
/* 276 */         paramObjectOutput.writeBoolean(((Boolean)paramObject).booleanValue());
/* 277 */       } else if (paramClass == byte.class) {
/* 278 */         paramObjectOutput.writeByte(((Byte)paramObject).byteValue());
/* 279 */       } else if (paramClass == char.class) {
/* 280 */         paramObjectOutput.writeChar(((Character)paramObject).charValue());
/* 281 */       } else if (paramClass == short.class) {
/* 282 */         paramObjectOutput.writeShort(((Short)paramObject).shortValue());
/* 283 */       } else if (paramClass == long.class) {
/* 284 */         paramObjectOutput.writeLong(((Long)paramObject).longValue());
/* 285 */       } else if (paramClass == float.class) {
/* 286 */         paramObjectOutput.writeFloat(((Float)paramObject).floatValue());
/* 287 */       } else if (paramClass == double.class) {
/* 288 */         paramObjectOutput.writeDouble(((Double)paramObject).doubleValue());
/*     */       } else {
/* 290 */         throw new Error("Unrecognized primitive type: " + paramClass);
/*     */       } 
/*     */     } else {
/* 293 */       paramObjectOutput.writeObject(paramObject);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Object unmarshalValue(Class<?> paramClass, ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/* 304 */     if (paramClass.isPrimitive()) {
/* 305 */       if (paramClass == int.class)
/* 306 */         return Integer.valueOf(paramObjectInput.readInt()); 
/* 307 */       if (paramClass == boolean.class)
/* 308 */         return Boolean.valueOf(paramObjectInput.readBoolean()); 
/* 309 */       if (paramClass == byte.class)
/* 310 */         return Byte.valueOf(paramObjectInput.readByte()); 
/* 311 */       if (paramClass == char.class)
/* 312 */         return Character.valueOf(paramObjectInput.readChar()); 
/* 313 */       if (paramClass == short.class)
/* 314 */         return Short.valueOf(paramObjectInput.readShort()); 
/* 315 */       if (paramClass == long.class)
/* 316 */         return Long.valueOf(paramObjectInput.readLong()); 
/* 317 */       if (paramClass == float.class)
/* 318 */         return Float.valueOf(paramObjectInput.readFloat()); 
/* 319 */       if (paramClass == double.class) {
/* 320 */         return Double.valueOf(paramObjectInput.readDouble());
/*     */       }
/* 322 */       throw new Error("Unrecognized primitive type: " + paramClass);
/*     */     } 
/* 324 */     if (paramClass == String.class && paramObjectInput instanceof ObjectInputStream) {
/* 325 */       return SharedSecrets.getJavaObjectInputStreamReadString().readString((ObjectInputStream)paramObjectInput);
/*     */     }
/* 327 */     return paramObjectInput.readObject();
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
/*     */   public RemoteCall newCall(RemoteObject paramRemoteObject, Operation[] paramArrayOfOperation, int paramInt, long paramLong) throws RemoteException {
/* 341 */     clientRefLog.log(Log.BRIEF, "get connection");
/*     */     
/* 343 */     Connection connection = this.ref.getChannel().newConnection();
/*     */     try {
/* 345 */       clientRefLog.log(Log.VERBOSE, "create call context");
/*     */ 
/*     */       
/* 348 */       if (clientCallLog.isLoggable(Log.VERBOSE)) {
/* 349 */         logClientCall(paramRemoteObject, paramArrayOfOperation[paramInt]);
/*     */       }
/*     */ 
/*     */       
/* 353 */       StreamRemoteCall streamRemoteCall = new StreamRemoteCall(connection, this.ref.getObjID(), paramInt, paramLong);
/*     */       try {
/* 355 */         marshalCustomCallData(streamRemoteCall.getOutputStream());
/* 356 */       } catch (IOException iOException) {
/* 357 */         throw new MarshalException("error marshaling custom call data");
/*     */       } 
/*     */       
/* 360 */       return streamRemoteCall;
/* 361 */     } catch (RemoteException remoteException) {
/* 362 */       this.ref.getChannel().free(connection, false);
/* 363 */       throw remoteException;
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
/*     */   public void invoke(RemoteCall paramRemoteCall) throws Exception {
/*     */     try {
/* 378 */       clientRefLog.log(Log.VERBOSE, "execute call");
/*     */       
/* 380 */       paramRemoteCall.executeCall();
/*     */     }
/* 382 */     catch (RemoteException remoteException) {
/*     */ 
/*     */ 
/*     */       
/* 386 */       clientRefLog.log(Log.BRIEF, "exception: ", remoteException);
/* 387 */       free(paramRemoteCall, false);
/* 388 */       throw remoteException;
/*     */     }
/* 390 */     catch (Error error) {
/*     */ 
/*     */ 
/*     */       
/* 394 */       clientRefLog.log(Log.BRIEF, "error: ", error);
/* 395 */       free(paramRemoteCall, false);
/* 396 */       throw error;
/*     */     }
/* 398 */     catch (RuntimeException runtimeException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 404 */       clientRefLog.log(Log.BRIEF, "exception: ", runtimeException);
/* 405 */       free(paramRemoteCall, false);
/* 406 */       throw runtimeException;
/*     */     }
/* 408 */     catch (Exception exception) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 413 */       clientRefLog.log(Log.BRIEF, "exception: ", exception);
/* 414 */       free(paramRemoteCall, true);
/*     */       
/* 416 */       throw exception;
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
/*     */   private void free(RemoteCall paramRemoteCall, boolean paramBoolean) throws RemoteException {
/* 431 */     Connection connection = ((StreamRemoteCall)paramRemoteCall).getConnection();
/* 432 */     this.ref.getChannel().free(connection, paramBoolean);
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
/*     */   public void done(RemoteCall paramRemoteCall) throws RemoteException {
/* 446 */     clientRefLog.log(Log.BRIEF, "free connection (reuse = true)");
/*     */ 
/*     */     
/* 449 */     free(paramRemoteCall, true);
/*     */     
/*     */     try {
/* 452 */       paramRemoteCall.done();
/* 453 */     } catch (IOException iOException) {}
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
/*     */   void logClientCall(Object paramObject1, Object paramObject2) {
/* 467 */     clientCallLog.log(Log.VERBOSE, "outbound call: " + this.ref + " : " + paramObject1
/* 468 */         .getClass().getName() + this.ref
/* 469 */         .getObjID().toString() + ": " + paramObject2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRefClass(ObjectOutput paramObjectOutput) {
/* 476 */     return "UnicastRef";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeExternal(ObjectOutput paramObjectOutput) throws IOException {
/* 483 */     this.ref.write(paramObjectOutput, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readExternal(ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/* 494 */     this.ref = LiveRef.read(paramObjectInput, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String remoteToString() {
/* 502 */     return Util.getUnqualifiedName(getClass()) + " [liveRef: " + this.ref + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int remoteHashCode() {
/* 509 */     return this.ref.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remoteEquals(RemoteRef paramRemoteRef) {
/* 515 */     if (paramRemoteRef instanceof UnicastRef)
/* 516 */       return this.ref.remoteEquals(((UnicastRef)paramRemoteRef).ref); 
/* 517 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/server/UnicastRef.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */