/*     */ package com.sun.corba.se.impl.presentation.rmi;
/*     */ 
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.presentation.rmi.DynamicMethodMarshaller;
/*     */ import java.io.Externalizable;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Method;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import javax.rmi.CORBA.Util;
/*     */ import javax.rmi.PortableRemoteObject;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.portable.ApplicationException;
/*     */ import org.omg.CORBA.portable.IDLEntity;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ import org.omg.CORBA_2_3.portable.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DynamicMethodMarshallerImpl
/*     */   implements DynamicMethodMarshaller
/*     */ {
/*     */   Method method;
/*     */   ExceptionHandler ehandler;
/*     */   boolean hasArguments = true;
/*     */   boolean hasVoidResult = true;
/*     */   boolean needsArgumentCopy;
/*     */   boolean needsResultCopy;
/*  56 */   ReaderWriter[] argRWs = null;
/*  57 */   ReaderWriter resultRW = null;
/*     */ 
/*     */   
/*     */   private static boolean isAnyClass(Class paramClass) {
/*  61 */     return (paramClass.equals(Object.class) || paramClass.equals(Serializable.class) || paramClass
/*  62 */       .equals(Externalizable.class));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isAbstractInterface(Class<?> paramClass) {
/*  73 */     if (IDLEntity.class.isAssignableFrom(paramClass)) {
/*  74 */       return paramClass.isInterface();
/*     */     }
/*  76 */     return (paramClass.isInterface() && allMethodsThrowRemoteException(paramClass));
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean allMethodsThrowRemoteException(Class paramClass) {
/*  81 */     Method[] arrayOfMethod = paramClass.getMethods();
/*     */ 
/*     */ 
/*     */     
/*  85 */     for (byte b = 0; b < arrayOfMethod.length; b++) {
/*  86 */       Method method = arrayOfMethod[b];
/*  87 */       if (method.getDeclaringClass() != Object.class && 
/*  88 */         !throwsRemote(method)) {
/*  89 */         return false;
/*     */       }
/*     */     } 
/*  92 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean throwsRemote(Method paramMethod) {
/*  97 */     Class[] arrayOfClass = paramMethod.getExceptionTypes();
/*     */ 
/*     */     
/* 100 */     for (byte b = 0; b < arrayOfClass.length; b++) {
/* 101 */       Class<?> clazz = arrayOfClass[b];
/* 102 */       if (RemoteException.class.isAssignableFrom(clazz)) {
/* 103 */         return true;
/*     */       }
/*     */     } 
/* 106 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static abstract class ReaderWriterBase
/*     */     implements ReaderWriter
/*     */   {
/*     */     String name;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ReaderWriterBase(String param1String) {
/* 122 */       this.name = param1String;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 127 */       return "ReaderWriter[" + this.name + "]";
/*     */     }
/*     */   }
/*     */   
/* 131 */   private static ReaderWriter booleanRW = new ReaderWriterBase("boolean")
/*     */     {
/*     */       public Object read(InputStream param1InputStream)
/*     */       {
/* 135 */         boolean bool = param1InputStream.read_boolean();
/* 136 */         return new Boolean(bool);
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(OutputStream param1OutputStream, Object param1Object) {
/* 141 */         Boolean bool = (Boolean)param1Object;
/* 142 */         param1OutputStream.write_boolean(bool.booleanValue());
/*     */       }
/*     */     };
/*     */   
/* 146 */   private static ReaderWriter byteRW = new ReaderWriterBase("byte")
/*     */     {
/*     */       public Object read(InputStream param1InputStream)
/*     */       {
/* 150 */         byte b = param1InputStream.read_octet();
/* 151 */         return new Byte(b);
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(OutputStream param1OutputStream, Object param1Object) {
/* 156 */         Byte byte_ = (Byte)param1Object;
/* 157 */         param1OutputStream.write_octet(byte_.byteValue());
/*     */       }
/*     */     };
/*     */   
/* 161 */   private static ReaderWriter charRW = new ReaderWriterBase("char")
/*     */     {
/*     */       public Object read(InputStream param1InputStream)
/*     */       {
/* 165 */         char c = param1InputStream.read_wchar();
/* 166 */         return new Character(c);
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(OutputStream param1OutputStream, Object param1Object) {
/* 171 */         Character character = (Character)param1Object;
/* 172 */         param1OutputStream.write_wchar(character.charValue());
/*     */       }
/*     */     };
/*     */   
/* 176 */   private static ReaderWriter shortRW = new ReaderWriterBase("short")
/*     */     {
/*     */       public Object read(InputStream param1InputStream)
/*     */       {
/* 180 */         short s = param1InputStream.read_short();
/* 181 */         return new Short(s);
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(OutputStream param1OutputStream, Object param1Object) {
/* 186 */         Short short_ = (Short)param1Object;
/* 187 */         param1OutputStream.write_short(short_.shortValue());
/*     */       }
/*     */     };
/*     */   
/* 191 */   private static ReaderWriter intRW = new ReaderWriterBase("int")
/*     */     {
/*     */       public Object read(InputStream param1InputStream)
/*     */       {
/* 195 */         int i = param1InputStream.read_long();
/* 196 */         return new Integer(i);
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(OutputStream param1OutputStream, Object param1Object) {
/* 201 */         Integer integer = (Integer)param1Object;
/* 202 */         param1OutputStream.write_long(integer.intValue());
/*     */       }
/*     */     };
/*     */   
/* 206 */   private static ReaderWriter longRW = new ReaderWriterBase("long")
/*     */     {
/*     */       public Object read(InputStream param1InputStream)
/*     */       {
/* 210 */         long l = param1InputStream.read_longlong();
/* 211 */         return new Long(l);
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(OutputStream param1OutputStream, Object param1Object) {
/* 216 */         Long long_ = (Long)param1Object;
/* 217 */         param1OutputStream.write_longlong(long_.longValue());
/*     */       }
/*     */     };
/*     */   
/* 221 */   private static ReaderWriter floatRW = new ReaderWriterBase("float")
/*     */     {
/*     */       public Object read(InputStream param1InputStream)
/*     */       {
/* 225 */         float f = param1InputStream.read_float();
/* 226 */         return new Float(f);
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(OutputStream param1OutputStream, Object param1Object) {
/* 231 */         Float float_ = (Float)param1Object;
/* 232 */         param1OutputStream.write_float(float_.floatValue());
/*     */       }
/*     */     };
/*     */   
/* 236 */   private static ReaderWriter doubleRW = new ReaderWriterBase("double")
/*     */     {
/*     */       public Object read(InputStream param1InputStream)
/*     */       {
/* 240 */         double d = param1InputStream.read_double();
/* 241 */         return new Double(d);
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(OutputStream param1OutputStream, Object param1Object) {
/* 246 */         Double double_ = (Double)param1Object;
/* 247 */         param1OutputStream.write_double(double_.doubleValue());
/*     */       }
/*     */     };
/*     */   
/* 251 */   private static ReaderWriter corbaObjectRW = new ReaderWriterBase("org.omg.CORBA.Object")
/*     */     {
/*     */       
/*     */       public Object read(InputStream param1InputStream)
/*     */       {
/* 256 */         return param1InputStream.read_Object();
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(OutputStream param1OutputStream, Object param1Object) {
/* 261 */         param1OutputStream.write_Object((Object)param1Object);
/*     */       }
/*     */     };
/*     */   
/* 265 */   private static ReaderWriter anyRW = new ReaderWriterBase("any")
/*     */     {
/*     */       public Object read(InputStream param1InputStream)
/*     */       {
/* 269 */         return Util.readAny((InputStream)param1InputStream);
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(OutputStream param1OutputStream, Object param1Object) {
/* 274 */         Util.writeAny((OutputStream)param1OutputStream, param1Object);
/*     */       }
/*     */     };
/*     */   
/* 278 */   private static ReaderWriter abstractInterfaceRW = new ReaderWriterBase("abstract_interface")
/*     */     {
/*     */       
/*     */       public Object read(InputStream param1InputStream)
/*     */       {
/* 283 */         return param1InputStream.read_abstract_interface();
/*     */       }
/*     */ 
/*     */       
/*     */       public void write(OutputStream param1OutputStream, Object param1Object) {
/* 288 */         Util.writeAbstractObject((OutputStream)param1OutputStream, param1Object);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */   
/*     */   public static ReaderWriter makeReaderWriter(final Class<?> cls) {
/* 295 */     if (cls.equals(boolean.class))
/* 296 */       return booleanRW; 
/* 297 */     if (cls.equals(byte.class))
/* 298 */       return byteRW; 
/* 299 */     if (cls.equals(char.class))
/* 300 */       return charRW; 
/* 301 */     if (cls.equals(short.class))
/* 302 */       return shortRW; 
/* 303 */     if (cls.equals(int.class))
/* 304 */       return intRW; 
/* 305 */     if (cls.equals(long.class))
/* 306 */       return longRW; 
/* 307 */     if (cls.equals(float.class))
/* 308 */       return floatRW; 
/* 309 */     if (cls.equals(double.class))
/* 310 */       return doubleRW; 
/* 311 */     if (Remote.class.isAssignableFrom(cls))
/* 312 */       return new ReaderWriterBase("remote(" + cls.getName() + ")")
/*     */         {
/*     */           public Object read(InputStream param1InputStream)
/*     */           {
/* 316 */             return PortableRemoteObject.narrow(param1InputStream.read_Object(), cls);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void write(OutputStream param1OutputStream, Object param1Object) {
/* 322 */             Util.writeRemoteObject((OutputStream)param1OutputStream, param1Object);
/*     */           }
/*     */         }; 
/* 325 */     if (cls.equals(Object.class))
/* 326 */       return corbaObjectRW; 
/* 327 */     if (Object.class.isAssignableFrom(cls))
/* 328 */       return new ReaderWriterBase("org.omg.CORBA.Object(" + cls
/* 329 */           .getName() + ")")
/*     */         {
/*     */           public Object read(InputStream param1InputStream)
/*     */           {
/* 333 */             return param1InputStream.read_Object(cls);
/*     */           }
/*     */ 
/*     */           
/*     */           public void write(OutputStream param1OutputStream, Object param1Object) {
/* 338 */             param1OutputStream.write_Object((Object)param1Object);
/*     */           }
/*     */         }; 
/* 341 */     if (isAnyClass(cls))
/* 342 */       return anyRW; 
/* 343 */     if (isAbstractInterface(cls)) {
/* 344 */       return abstractInterfaceRW;
/*     */     }
/*     */     
/* 347 */     return new ReaderWriterBase("value(" + cls.getName() + ")")
/*     */       {
/*     */         public Object read(InputStream param1InputStream)
/*     */         {
/* 351 */           return param1InputStream.read_value(cls);
/*     */         }
/*     */ 
/*     */         
/*     */         public void write(OutputStream param1OutputStream, Object param1Object) {
/* 356 */           param1OutputStream.write_value((Serializable)param1Object, cls);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public DynamicMethodMarshallerImpl(Method paramMethod) {
/* 363 */     this.method = paramMethod;
/* 364 */     this.ehandler = new ExceptionHandlerImpl(paramMethod.getExceptionTypes());
/* 365 */     this.needsArgumentCopy = false;
/*     */     
/* 367 */     Class[] arrayOfClass = paramMethod.getParameterTypes();
/* 368 */     this.hasArguments = (arrayOfClass.length > 0);
/* 369 */     if (this.hasArguments) {
/* 370 */       this.argRWs = new ReaderWriter[arrayOfClass.length];
/* 371 */       for (byte b = 0; b < arrayOfClass.length; b++) {
/*     */ 
/*     */ 
/*     */         
/* 375 */         if (!arrayOfClass[b].isPrimitive())
/* 376 */           this.needsArgumentCopy = true; 
/* 377 */         this.argRWs[b] = makeReaderWriter(arrayOfClass[b]);
/*     */       } 
/*     */     } 
/*     */     
/* 381 */     Class<?> clazz = paramMethod.getReturnType();
/* 382 */     this.needsResultCopy = false;
/* 383 */     this.hasVoidResult = clazz.equals(void.class);
/* 384 */     if (!this.hasVoidResult) {
/* 385 */       this.needsResultCopy = !clazz.isPrimitive();
/* 386 */       this.resultRW = makeReaderWriter(clazz);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Method getMethod() {
/* 392 */     return this.method;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] copyArguments(Object[] paramArrayOfObject, ORB paramORB) throws RemoteException {
/* 398 */     if (this.needsArgumentCopy) {
/* 399 */       return Util.copyObjects(paramArrayOfObject, (ORB)paramORB);
/*     */     }
/* 401 */     return paramArrayOfObject;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] readArguments(InputStream paramInputStream) {
/* 406 */     Object[] arrayOfObject = null;
/*     */     
/* 408 */     if (this.hasArguments) {
/* 409 */       arrayOfObject = new Object[this.argRWs.length];
/* 410 */       for (byte b = 0; b < this.argRWs.length; b++) {
/* 411 */         arrayOfObject[b] = this.argRWs[b].read(paramInputStream);
/*     */       }
/*     */     } 
/* 414 */     return arrayOfObject;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeArguments(OutputStream paramOutputStream, Object[] paramArrayOfObject) {
/* 419 */     if (this.hasArguments) {
/* 420 */       if (paramArrayOfObject.length != this.argRWs.length) {
/* 421 */         throw new IllegalArgumentException("Expected " + this.argRWs.length + " arguments, but got " + paramArrayOfObject.length + " arguments.");
/*     */       }
/*     */       
/* 424 */       for (byte b = 0; b < this.argRWs.length; b++) {
/* 425 */         this.argRWs[b].write(paramOutputStream, paramArrayOfObject[b]);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object copyResult(Object paramObject, ORB paramORB) throws RemoteException {
/* 431 */     if (this.needsResultCopy) {
/* 432 */       return Util.copyObject(paramObject, (ORB)paramORB);
/*     */     }
/* 434 */     return paramObject;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object readResult(InputStream paramInputStream) {
/* 439 */     if (this.hasVoidResult) {
/* 440 */       return null;
/*     */     }
/* 442 */     return this.resultRW.read(paramInputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeResult(OutputStream paramOutputStream, Object paramObject) {
/* 447 */     if (!this.hasVoidResult) {
/* 448 */       this.resultRW.write(paramOutputStream, paramObject);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isDeclaredException(Throwable paramThrowable) {
/* 453 */     return this.ehandler.isDeclaredException(paramThrowable.getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeException(OutputStream paramOutputStream, Exception paramException) {
/* 458 */     this.ehandler.writeException(paramOutputStream, paramException);
/*     */   }
/*     */ 
/*     */   
/*     */   public Exception readException(ApplicationException paramApplicationException) {
/* 463 */     return this.ehandler.readException(paramApplicationException);
/*     */   }
/*     */   
/*     */   public static interface ReaderWriter {
/*     */     Object read(InputStream param1InputStream);
/*     */     
/*     */     void write(OutputStream param1OutputStream, Object param1Object);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/presentation/rmi/DynamicMethodMarshallerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */