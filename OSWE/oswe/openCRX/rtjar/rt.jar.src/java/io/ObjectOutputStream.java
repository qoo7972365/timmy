/*      */ package java.io;
/*      */ 
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ import sun.security.action.GetBooleanAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ObjectOutputStream
/*      */   extends OutputStream
/*      */   implements ObjectOutput, ObjectStreamConstants
/*      */ {
/*      */   private final BlockDataOutputStream bout;
/*      */   private final HandleTable handles;
/*      */   private final ReplaceTable subs;
/*      */   
/*      */   private static class Caches
/*      */   {
/*  168 */     static final ConcurrentMap<ObjectStreamClass.WeakClassKey, Boolean> subclassAudits = new ConcurrentHashMap<>();
/*      */ 
/*      */ 
/*      */     
/*  172 */     static final ReferenceQueue<Class<?>> subclassAuditsQueue = new ReferenceQueue<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  183 */   private int protocol = 2;
/*      */ 
/*      */ 
/*      */   
/*      */   private int depth;
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] primVals;
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean enableOverride;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean enableReplace;
/*      */ 
/*      */ 
/*      */   
/*      */   private SerialCallbackContext curContext;
/*      */ 
/*      */ 
/*      */   
/*      */   private PutFieldImpl curPut;
/*      */ 
/*      */   
/*      */   private final DebugTraceInfoStack debugInfoStack;
/*      */ 
/*      */   
/*  213 */   private static final boolean extendedDebugInfo = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.io.serialization.extendedDebugInfo")))
/*      */     
/*  215 */     .booleanValue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectOutputStream(OutputStream paramOutputStream) throws IOException {
/*  241 */     verifySubclass();
/*  242 */     this.bout = new BlockDataOutputStream(paramOutputStream);
/*  243 */     this.handles = new HandleTable(10, 3.0F);
/*  244 */     this.subs = new ReplaceTable(10, 3.0F);
/*  245 */     this.enableOverride = false;
/*  246 */     writeStreamHeader();
/*  247 */     this.bout.setBlockDataMode(true);
/*  248 */     if (extendedDebugInfo) {
/*  249 */       this.debugInfoStack = new DebugTraceInfoStack();
/*      */     } else {
/*  251 */       this.debugInfoStack = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ObjectOutputStream() throws IOException, SecurityException {
/*  273 */     SecurityManager securityManager = System.getSecurityManager();
/*  274 */     if (securityManager != null) {
/*  275 */       securityManager.checkPermission(SUBCLASS_IMPLEMENTATION_PERMISSION);
/*      */     }
/*  277 */     this.bout = null;
/*  278 */     this.handles = null;
/*  279 */     this.subs = null;
/*  280 */     this.enableOverride = true;
/*  281 */     this.debugInfoStack = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void useProtocolVersion(int paramInt) throws IOException {
/*  305 */     if (this.handles.size() != 0)
/*      */     {
/*  307 */       throw new IllegalStateException("stream non-empty");
/*      */     }
/*  309 */     switch (paramInt) {
/*      */       case 1:
/*      */       case 2:
/*  312 */         this.protocol = paramInt;
/*      */         return;
/*      */     } 
/*      */     
/*  316 */     throw new IllegalArgumentException("unknown version: " + paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void writeObject(Object paramObject) throws IOException {
/*  343 */     if (this.enableOverride) {
/*  344 */       writeObjectOverride(paramObject);
/*      */       return;
/*      */     } 
/*      */     try {
/*  348 */       writeObject0(paramObject, false);
/*  349 */     } catch (IOException iOException) {
/*  350 */       if (this.depth == 0) {
/*  351 */         writeFatalException(iOException);
/*      */       }
/*  353 */       throw iOException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeObjectOverride(Object paramObject) throws IOException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeUnshared(Object paramObject) throws IOException {
/*      */     try {
/*  415 */       writeObject0(paramObject, true);
/*  416 */     } catch (IOException iOException) {
/*  417 */       if (this.depth == 0) {
/*  418 */         writeFatalException(iOException);
/*      */       }
/*  420 */       throw iOException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void defaultWriteObject() throws IOException {
/*  434 */     SerialCallbackContext serialCallbackContext = this.curContext;
/*  435 */     if (serialCallbackContext == null) {
/*  436 */       throw new NotActiveException("not in call to writeObject");
/*      */     }
/*  438 */     Object object = serialCallbackContext.getObj();
/*  439 */     ObjectStreamClass objectStreamClass = serialCallbackContext.getDesc();
/*  440 */     this.bout.setBlockDataMode(false);
/*  441 */     defaultWriteFields(object, objectStreamClass);
/*  442 */     this.bout.setBlockDataMode(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PutField putFields() throws IOException {
/*  456 */     if (this.curPut == null) {
/*  457 */       SerialCallbackContext serialCallbackContext = this.curContext;
/*  458 */       if (serialCallbackContext == null) {
/*  459 */         throw new NotActiveException("not in call to writeObject");
/*      */       }
/*  461 */       Object object = serialCallbackContext.getObj();
/*  462 */       ObjectStreamClass objectStreamClass = serialCallbackContext.getDesc();
/*  463 */       this.curPut = new PutFieldImpl(objectStreamClass);
/*      */     } 
/*  465 */     return this.curPut;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeFields() throws IOException {
/*  478 */     if (this.curPut == null) {
/*  479 */       throw new NotActiveException("no current PutField object");
/*      */     }
/*  481 */     this.bout.setBlockDataMode(false);
/*  482 */     this.curPut.writeFields();
/*  483 */     this.bout.setBlockDataMode(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reset() throws IOException {
/*  497 */     if (this.depth != 0) {
/*  498 */       throw new IOException("stream active");
/*      */     }
/*  500 */     this.bout.setBlockDataMode(false);
/*  501 */     this.bout.writeByte(121);
/*  502 */     clear();
/*  503 */     this.bout.setBlockDataMode(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void annotateClass(Class<?> paramClass) throws IOException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void annotateProxyClass(Class<?> paramClass) throws IOException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object replaceObject(Object paramObject) throws IOException {
/*  588 */     return paramObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean enableReplaceObject(boolean paramBoolean) throws SecurityException {
/*  614 */     if (paramBoolean == this.enableReplace) {
/*  615 */       return paramBoolean;
/*      */     }
/*  617 */     if (paramBoolean) {
/*  618 */       SecurityManager securityManager = System.getSecurityManager();
/*  619 */       if (securityManager != null) {
/*  620 */         securityManager.checkPermission(SUBSTITUTION_PERMISSION);
/*      */       }
/*      */     } 
/*  623 */     this.enableReplace = paramBoolean;
/*  624 */     return !this.enableReplace;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeStreamHeader() throws IOException {
/*  636 */     this.bout.writeShort(-21267);
/*  637 */     this.bout.writeShort(5);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeClassDescriptor(ObjectStreamClass paramObjectStreamClass) throws IOException {
/*  668 */     paramObjectStreamClass.writeNonProxy(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void write(int paramInt) throws IOException {
/*  679 */     this.bout.write(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void write(byte[] paramArrayOfbyte) throws IOException {
/*  690 */     this.bout.write(paramArrayOfbyte, 0, paramArrayOfbyte.length, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/*  702 */     if (paramArrayOfbyte == null) {
/*  703 */       throw new NullPointerException();
/*      */     }
/*  705 */     int i = paramInt1 + paramInt2;
/*  706 */     if (paramInt1 < 0 || paramInt2 < 0 || i > paramArrayOfbyte.length || i < 0) {
/*  707 */       throw new IndexOutOfBoundsException();
/*      */     }
/*  709 */     this.bout.write(paramArrayOfbyte, paramInt1, paramInt2, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flush() throws IOException {
/*  719 */     this.bout.flush();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drain() throws IOException {
/*  730 */     this.bout.drain();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() throws IOException {
/*  740 */     flush();
/*  741 */     clear();
/*  742 */     this.bout.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeBoolean(boolean paramBoolean) throws IOException {
/*  753 */     this.bout.writeBoolean(paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeByte(int paramInt) throws IOException {
/*  764 */     this.bout.writeByte(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeShort(int paramInt) throws IOException {
/*  775 */     this.bout.writeShort(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeChar(int paramInt) throws IOException {
/*  786 */     this.bout.writeChar(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeInt(int paramInt) throws IOException {
/*  797 */     this.bout.writeInt(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeLong(long paramLong) throws IOException {
/*  808 */     this.bout.writeLong(paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeFloat(float paramFloat) throws IOException {
/*  819 */     this.bout.writeFloat(paramFloat);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeDouble(double paramDouble) throws IOException {
/*  830 */     this.bout.writeDouble(paramDouble);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeBytes(String paramString) throws IOException {
/*  841 */     this.bout.writeBytes(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeChars(String paramString) throws IOException {
/*  852 */     this.bout.writeChars(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeUTF(String paramString) throws IOException {
/*  869 */     this.bout.writeUTF(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static abstract class PutField
/*      */   {
/*      */     public abstract void put(String param1String, boolean param1Boolean);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract void put(String param1String, byte param1Byte);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract void put(String param1String, char param1Char);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract void put(String param1String, short param1Short);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract void put(String param1String, int param1Int);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract void put(String param1String, long param1Long);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract void put(String param1String, float param1Float);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract void put(String param1String, double param1Double);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract void put(String param1String, Object param1Object);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public abstract void write(ObjectOutput param1ObjectOutput) throws IOException;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getProtocolVersion() {
/* 1016 */     return this.protocol;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void writeTypeString(String paramString) throws IOException {
/* 1025 */     if (paramString == null)
/* 1026 */     { writeNull(); }
/* 1027 */     else { int i; if ((i = this.handles.lookup(paramString)) != -1) {
/* 1028 */         writeHandle(i);
/*      */       } else {
/* 1030 */         writeString(paramString, false);
/*      */       }  }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void verifySubclass() {
/* 1041 */     Class<?> clazz = getClass();
/* 1042 */     if (clazz == ObjectOutputStream.class) {
/*      */       return;
/*      */     }
/* 1045 */     SecurityManager securityManager = System.getSecurityManager();
/* 1046 */     if (securityManager == null) {
/*      */       return;
/*      */     }
/* 1049 */     ObjectStreamClass.processQueue(Caches.subclassAuditsQueue, (ConcurrentMap)Caches.subclassAudits);
/* 1050 */     ObjectStreamClass.WeakClassKey weakClassKey = new ObjectStreamClass.WeakClassKey(clazz, Caches.subclassAuditsQueue);
/* 1051 */     Boolean bool = Caches.subclassAudits.get(weakClassKey);
/* 1052 */     if (bool == null) {
/* 1053 */       bool = Boolean.valueOf(auditSubclass(clazz));
/* 1054 */       Caches.subclassAudits.putIfAbsent(weakClassKey, bool);
/*      */     } 
/* 1056 */     if (bool.booleanValue()) {
/*      */       return;
/*      */     }
/* 1059 */     securityManager.checkPermission(SUBCLASS_IMPLEMENTATION_PERMISSION);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean auditSubclass(final Class<?> subcl) {
/* 1068 */     Boolean bool = AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*      */         {
/*      */           public Boolean run() {
/* 1071 */             Class<ObjectOutputStream> clazz = subcl;
/* 1072 */             for (; clazz != ObjectOutputStream.class; 
/* 1073 */               clazz = (Class)clazz.getSuperclass()) {
/*      */               
/*      */               try {
/* 1076 */                 clazz.getDeclaredMethod("writeUnshared", new Class[] { Object.class });
/*      */                 
/* 1078 */                 return Boolean.FALSE;
/* 1079 */               } catch (NoSuchMethodException noSuchMethodException) {
/*      */                 
/*      */                 try {
/* 1082 */                   clazz.getDeclaredMethod("putFields", (Class[])null);
/* 1083 */                   return Boolean.FALSE;
/* 1084 */                 } catch (NoSuchMethodException noSuchMethodException1) {}
/*      */               } 
/*      */             } 
/* 1087 */             return Boolean.TRUE;
/*      */           }
/*      */         });
/*      */     
/* 1091 */     return bool.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void clear() {
/* 1098 */     this.subs.clear();
/* 1099 */     this.handles.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject0(Object paramObject, boolean paramBoolean) throws IOException {
/* 1108 */     boolean bool = this.bout.setBlockDataMode(false);
/* 1109 */     this.depth++;
/*      */     
/*      */     try {
/*      */       ObjectStreamClass objectStreamClass;
/* 1113 */       if ((paramObject = this.subs.lookup(paramObject)) == null) {
/* 1114 */         writeNull(); return;
/*      */       }  int i;
/* 1116 */       if (!paramBoolean && (i = this.handles.lookup(paramObject)) != -1) {
/* 1117 */         writeHandle(i); return;
/*      */       } 
/* 1119 */       if (paramObject instanceof Class) {
/* 1120 */         writeClass((Class)paramObject, paramBoolean); return;
/*      */       } 
/* 1122 */       if (paramObject instanceof ObjectStreamClass) {
/* 1123 */         writeClassDesc((ObjectStreamClass)paramObject, paramBoolean);
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1128 */       Object object = paramObject;
/* 1129 */       Class<?> clazz = paramObject.getClass();
/*      */ 
/*      */ 
/*      */       
/*      */       while (true) {
/* 1134 */         objectStreamClass = ObjectStreamClass.lookup(clazz, true); Class<?> clazz1;
/* 1135 */         if (!objectStreamClass.hasWriteReplaceMethod() || (
/* 1136 */           paramObject = objectStreamClass.invokeWriteReplace(paramObject)) == null || (
/* 1137 */           clazz1 = paramObject.getClass()) == clazz) {
/*      */           break;
/*      */         }
/*      */         
/* 1141 */         clazz = clazz1;
/*      */       } 
/* 1143 */       if (this.enableReplace) {
/* 1144 */         Object object1 = replaceObject(paramObject);
/* 1145 */         if (object1 != paramObject && object1 != null) {
/* 1146 */           clazz = object1.getClass();
/* 1147 */           objectStreamClass = ObjectStreamClass.lookup(clazz, true);
/*      */         } 
/* 1149 */         paramObject = object1;
/*      */       } 
/*      */ 
/*      */       
/* 1153 */       if (paramObject != object) {
/* 1154 */         this.subs.assign(object, paramObject);
/* 1155 */         if (paramObject == null) {
/* 1156 */           writeNull(); return;
/*      */         } 
/* 1158 */         if (!paramBoolean && (i = this.handles.lookup(paramObject)) != -1) {
/* 1159 */           writeHandle(i); return;
/*      */         } 
/* 1161 */         if (paramObject instanceof Class) {
/* 1162 */           writeClass((Class)paramObject, paramBoolean); return;
/*      */         } 
/* 1164 */         if (paramObject instanceof ObjectStreamClass) {
/* 1165 */           writeClassDesc((ObjectStreamClass)paramObject, paramBoolean);
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */       
/* 1171 */       if (paramObject instanceof String) {
/* 1172 */         writeString((String)paramObject, paramBoolean);
/* 1173 */       } else if (clazz.isArray()) {
/* 1174 */         writeArray(paramObject, objectStreamClass, paramBoolean);
/* 1175 */       } else if (paramObject instanceof Enum) {
/* 1176 */         writeEnum((Enum)paramObject, objectStreamClass, paramBoolean);
/* 1177 */       } else if (paramObject instanceof Serializable) {
/* 1178 */         writeOrdinaryObject(paramObject, objectStreamClass, paramBoolean);
/*      */       } else {
/* 1180 */         if (extendedDebugInfo) {
/* 1181 */           throw new NotSerializableException(clazz
/* 1182 */               .getName() + "\n" + this.debugInfoStack.toString());
/*      */         }
/* 1184 */         throw new NotSerializableException(clazz.getName());
/*      */       } 
/*      */     } finally {
/*      */       
/* 1188 */       this.depth--;
/* 1189 */       this.bout.setBlockDataMode(bool);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeNull() throws IOException {
/* 1197 */     this.bout.writeByte(112);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeHandle(int paramInt) throws IOException {
/* 1204 */     this.bout.writeByte(113);
/* 1205 */     this.bout.writeInt(8257536 + paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeClass(Class<?> paramClass, boolean paramBoolean) throws IOException {
/* 1212 */     this.bout.writeByte(118);
/* 1213 */     writeClassDesc(ObjectStreamClass.lookup(paramClass, true), false);
/* 1214 */     this.handles.assign(paramBoolean ? null : paramClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeClassDesc(ObjectStreamClass paramObjectStreamClass, boolean paramBoolean) throws IOException {
/* 1224 */     if (paramObjectStreamClass == null)
/* 1225 */     { writeNull(); }
/* 1226 */     else { int i; if (!paramBoolean && (i = this.handles.lookup(paramObjectStreamClass)) != -1) {
/* 1227 */         writeHandle(i);
/* 1228 */       } else if (paramObjectStreamClass.isProxy()) {
/* 1229 */         writeProxyDesc(paramObjectStreamClass, paramBoolean);
/*      */       } else {
/* 1231 */         writeNonProxyDesc(paramObjectStreamClass, paramBoolean);
/*      */       }  }
/*      */   
/*      */   }
/*      */   
/*      */   private boolean isCustomSubclass() {
/* 1237 */     return 
/* 1238 */       (getClass().getClassLoader() != ObjectOutputStream.class.getClassLoader());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeProxyDesc(ObjectStreamClass paramObjectStreamClass, boolean paramBoolean) throws IOException {
/* 1247 */     this.bout.writeByte(125);
/* 1248 */     this.handles.assign(paramBoolean ? null : paramObjectStreamClass);
/*      */     
/* 1250 */     Class<?> clazz = paramObjectStreamClass.forClass();
/* 1251 */     Class[] arrayOfClass = clazz.getInterfaces();
/* 1252 */     this.bout.writeInt(arrayOfClass.length);
/* 1253 */     for (byte b = 0; b < arrayOfClass.length; b++) {
/* 1254 */       this.bout.writeUTF(arrayOfClass[b].getName());
/*      */     }
/*      */     
/* 1257 */     this.bout.setBlockDataMode(true);
/* 1258 */     if (clazz != null && isCustomSubclass()) {
/* 1259 */       ReflectUtil.checkPackageAccess(clazz);
/*      */     }
/* 1261 */     annotateProxyClass(clazz);
/* 1262 */     this.bout.setBlockDataMode(false);
/* 1263 */     this.bout.writeByte(120);
/*      */     
/* 1265 */     writeClassDesc(paramObjectStreamClass.getSuperDesc(), false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeNonProxyDesc(ObjectStreamClass paramObjectStreamClass, boolean paramBoolean) throws IOException {
/* 1275 */     this.bout.writeByte(114);
/* 1276 */     this.handles.assign(paramBoolean ? null : paramObjectStreamClass);
/*      */     
/* 1278 */     if (this.protocol == 1) {
/*      */       
/* 1280 */       paramObjectStreamClass.writeNonProxy(this);
/*      */     } else {
/* 1282 */       writeClassDescriptor(paramObjectStreamClass);
/*      */     } 
/*      */     
/* 1285 */     Class<?> clazz = paramObjectStreamClass.forClass();
/* 1286 */     this.bout.setBlockDataMode(true);
/* 1287 */     if (clazz != null && isCustomSubclass()) {
/* 1288 */       ReflectUtil.checkPackageAccess(clazz);
/*      */     }
/* 1290 */     annotateClass(clazz);
/* 1291 */     this.bout.setBlockDataMode(false);
/* 1292 */     this.bout.writeByte(120);
/*      */     
/* 1294 */     writeClassDesc(paramObjectStreamClass.getSuperDesc(), false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeString(String paramString, boolean paramBoolean) throws IOException {
/* 1302 */     this.handles.assign(paramBoolean ? null : paramString);
/* 1303 */     long l = this.bout.getUTFLength(paramString);
/* 1304 */     if (l <= 65535L) {
/* 1305 */       this.bout.writeByte(116);
/* 1306 */       this.bout.writeUTF(paramString, l);
/*      */     } else {
/* 1308 */       this.bout.writeByte(124);
/* 1309 */       this.bout.writeLongUTF(paramString, l);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeArray(Object paramObject, ObjectStreamClass paramObjectStreamClass, boolean paramBoolean) throws IOException {
/* 1321 */     this.bout.writeByte(117);
/* 1322 */     writeClassDesc(paramObjectStreamClass, false);
/* 1323 */     this.handles.assign(paramBoolean ? null : paramObject);
/*      */     
/* 1325 */     Class<?> clazz = paramObjectStreamClass.forClass().getComponentType();
/* 1326 */     if (clazz.isPrimitive()) {
/* 1327 */       if (clazz == int.class) {
/* 1328 */         int[] arrayOfInt = (int[])paramObject;
/* 1329 */         this.bout.writeInt(arrayOfInt.length);
/* 1330 */         this.bout.writeInts(arrayOfInt, 0, arrayOfInt.length);
/* 1331 */       } else if (clazz == byte.class) {
/* 1332 */         byte[] arrayOfByte = (byte[])paramObject;
/* 1333 */         this.bout.writeInt(arrayOfByte.length);
/* 1334 */         this.bout.write(arrayOfByte, 0, arrayOfByte.length, true);
/* 1335 */       } else if (clazz == long.class) {
/* 1336 */         long[] arrayOfLong = (long[])paramObject;
/* 1337 */         this.bout.writeInt(arrayOfLong.length);
/* 1338 */         this.bout.writeLongs(arrayOfLong, 0, arrayOfLong.length);
/* 1339 */       } else if (clazz == float.class) {
/* 1340 */         float[] arrayOfFloat = (float[])paramObject;
/* 1341 */         this.bout.writeInt(arrayOfFloat.length);
/* 1342 */         this.bout.writeFloats(arrayOfFloat, 0, arrayOfFloat.length);
/* 1343 */       } else if (clazz == double.class) {
/* 1344 */         double[] arrayOfDouble = (double[])paramObject;
/* 1345 */         this.bout.writeInt(arrayOfDouble.length);
/* 1346 */         this.bout.writeDoubles(arrayOfDouble, 0, arrayOfDouble.length);
/* 1347 */       } else if (clazz == short.class) {
/* 1348 */         short[] arrayOfShort = (short[])paramObject;
/* 1349 */         this.bout.writeInt(arrayOfShort.length);
/* 1350 */         this.bout.writeShorts(arrayOfShort, 0, arrayOfShort.length);
/* 1351 */       } else if (clazz == char.class) {
/* 1352 */         char[] arrayOfChar = (char[])paramObject;
/* 1353 */         this.bout.writeInt(arrayOfChar.length);
/* 1354 */         this.bout.writeChars(arrayOfChar, 0, arrayOfChar.length);
/* 1355 */       } else if (clazz == boolean.class) {
/* 1356 */         boolean[] arrayOfBoolean = (boolean[])paramObject;
/* 1357 */         this.bout.writeInt(arrayOfBoolean.length);
/* 1358 */         this.bout.writeBooleans(arrayOfBoolean, 0, arrayOfBoolean.length);
/*      */       } else {
/* 1360 */         throw new InternalError();
/*      */       } 
/*      */     } else {
/* 1363 */       Object[] arrayOfObject = (Object[])paramObject;
/* 1364 */       int i = arrayOfObject.length;
/* 1365 */       this.bout.writeInt(i);
/* 1366 */       if (extendedDebugInfo) {
/* 1367 */         this.debugInfoStack.push("array (class \"" + paramObject
/* 1368 */             .getClass().getName() + "\", size: " + i + ")");
/*      */       }
/*      */       
/*      */       try {
/* 1372 */         for (byte b = 0; b < i; b++) {
/* 1373 */           if (extendedDebugInfo);
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       finally {
/*      */ 
/*      */ 
/*      */         
/* 1386 */         if (extendedDebugInfo) {
/* 1387 */           this.debugInfoStack.pop();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeEnum(Enum<?> paramEnum, ObjectStreamClass paramObjectStreamClass, boolean paramBoolean) throws IOException {
/* 1401 */     this.bout.writeByte(126);
/* 1402 */     ObjectStreamClass objectStreamClass = paramObjectStreamClass.getSuperDesc();
/* 1403 */     writeClassDesc((objectStreamClass.forClass() == Enum.class) ? paramObjectStreamClass : objectStreamClass, false);
/* 1404 */     this.handles.assign(paramBoolean ? null : paramEnum);
/* 1405 */     writeString(paramEnum.name(), false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeOrdinaryObject(Object paramObject, ObjectStreamClass paramObjectStreamClass, boolean paramBoolean) throws IOException {
/* 1418 */     if (extendedDebugInfo) {
/* 1419 */       this.debugInfoStack.push(((this.depth == 1) ? "root " : "") + "object (class \"" + paramObject
/*      */           
/* 1421 */           .getClass().getName() + "\", " + paramObject.toString() + ")");
/*      */     }
/*      */     try {
/* 1424 */       paramObjectStreamClass.checkSerialize();
/*      */       
/* 1426 */       this.bout.writeByte(115);
/* 1427 */       writeClassDesc(paramObjectStreamClass, false);
/* 1428 */       this.handles.assign(paramBoolean ? null : paramObject);
/* 1429 */       if (paramObjectStreamClass.isExternalizable() && !paramObjectStreamClass.isProxy()) {
/* 1430 */         writeExternalData((Externalizable)paramObject);
/*      */       } else {
/* 1432 */         writeSerialData(paramObject, paramObjectStreamClass);
/*      */       } 
/*      */     } finally {
/* 1435 */       if (extendedDebugInfo) {
/* 1436 */         this.debugInfoStack.pop();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeExternalData(Externalizable paramExternalizable) throws IOException {
/* 1446 */     PutFieldImpl putFieldImpl = this.curPut;
/* 1447 */     this.curPut = null;
/*      */     
/* 1449 */     if (extendedDebugInfo) {
/* 1450 */       this.debugInfoStack.push("writeExternal data");
/*      */     }
/* 1452 */     SerialCallbackContext serialCallbackContext = this.curContext;
/*      */     try {
/* 1454 */       this.curContext = null;
/* 1455 */       if (this.protocol == 1) {
/* 1456 */         paramExternalizable.writeExternal(this);
/*      */       } else {
/* 1458 */         this.bout.setBlockDataMode(true);
/* 1459 */         paramExternalizable.writeExternal(this);
/* 1460 */         this.bout.setBlockDataMode(false);
/* 1461 */         this.bout.writeByte(120);
/*      */       } 
/*      */     } finally {
/* 1464 */       this.curContext = serialCallbackContext;
/* 1465 */       if (extendedDebugInfo) {
/* 1466 */         this.debugInfoStack.pop();
/*      */       }
/*      */     } 
/*      */     
/* 1470 */     this.curPut = putFieldImpl;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeSerialData(Object paramObject, ObjectStreamClass paramObjectStreamClass) throws IOException {
/* 1480 */     ObjectStreamClass.ClassDataSlot[] arrayOfClassDataSlot = paramObjectStreamClass.getClassDataLayout();
/* 1481 */     for (byte b = 0; b < arrayOfClassDataSlot.length; b++) {
/* 1482 */       ObjectStreamClass objectStreamClass = (arrayOfClassDataSlot[b]).desc;
/* 1483 */       if (objectStreamClass.hasWriteObjectMethod()) {
/* 1484 */         PutFieldImpl putFieldImpl = this.curPut;
/* 1485 */         this.curPut = null;
/* 1486 */         SerialCallbackContext serialCallbackContext = this.curContext;
/*      */         
/* 1488 */         if (extendedDebugInfo) {
/* 1489 */           this.debugInfoStack.push("custom writeObject data (class \"" + objectStreamClass
/*      */               
/* 1491 */               .getName() + "\")");
/*      */         }
/*      */         try {
/* 1494 */           this.curContext = new SerialCallbackContext(paramObject, objectStreamClass);
/* 1495 */           this.bout.setBlockDataMode(true);
/* 1496 */           objectStreamClass.invokeWriteObject(paramObject, this);
/* 1497 */           this.bout.setBlockDataMode(false);
/* 1498 */           this.bout.writeByte(120);
/*      */         } finally {
/* 1500 */           this.curContext.setUsed();
/* 1501 */           this.curContext = serialCallbackContext;
/* 1502 */           if (extendedDebugInfo) {
/* 1503 */             this.debugInfoStack.pop();
/*      */           }
/*      */         } 
/*      */         
/* 1507 */         this.curPut = putFieldImpl;
/*      */       } else {
/* 1509 */         defaultWriteFields(paramObject, objectStreamClass);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void defaultWriteFields(Object paramObject, ObjectStreamClass paramObjectStreamClass) throws IOException {
/* 1522 */     Class<?> clazz = paramObjectStreamClass.forClass();
/* 1523 */     if (clazz != null && paramObject != null && !clazz.isInstance(paramObject)) {
/* 1524 */       throw new ClassCastException();
/*      */     }
/*      */     
/* 1527 */     paramObjectStreamClass.checkDefaultSerialize();
/*      */     
/* 1529 */     int i = paramObjectStreamClass.getPrimDataSize();
/* 1530 */     if (this.primVals == null || this.primVals.length < i) {
/* 1531 */       this.primVals = new byte[i];
/*      */     }
/* 1533 */     paramObjectStreamClass.getPrimFieldValues(paramObject, this.primVals);
/* 1534 */     this.bout.write(this.primVals, 0, i, false);
/*      */     
/* 1536 */     ObjectStreamField[] arrayOfObjectStreamField = paramObjectStreamClass.getFields(false);
/* 1537 */     Object[] arrayOfObject = new Object[paramObjectStreamClass.getNumObjFields()];
/* 1538 */     int j = arrayOfObjectStreamField.length - arrayOfObject.length;
/* 1539 */     paramObjectStreamClass.getObjFieldValues(paramObject, arrayOfObject);
/* 1540 */     for (byte b = 0; b < arrayOfObject.length; b++) {
/* 1541 */       if (extendedDebugInfo);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeFatalException(IOException paramIOException) throws IOException {
/* 1573 */     clear();
/* 1574 */     boolean bool = this.bout.setBlockDataMode(false);
/*      */     try {
/* 1576 */       this.bout.writeByte(123);
/* 1577 */       writeObject0(paramIOException, false);
/* 1578 */       clear();
/*      */     } finally {
/* 1580 */       this.bout.setBlockDataMode(bool);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native void floatsToBytes(float[] paramArrayOffloat, int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static native void doublesToBytes(double[] paramArrayOfdouble, int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class PutFieldImpl
/*      */     extends PutField
/*      */   {
/*      */     private final ObjectStreamClass desc;
/*      */ 
/*      */ 
/*      */     
/*      */     private final byte[] primVals;
/*      */ 
/*      */ 
/*      */     
/*      */     private final Object[] objVals;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     PutFieldImpl(ObjectStreamClass param1ObjectStreamClass) {
/* 1617 */       this.desc = param1ObjectStreamClass;
/* 1618 */       this.primVals = new byte[param1ObjectStreamClass.getPrimDataSize()];
/* 1619 */       this.objVals = new Object[param1ObjectStreamClass.getNumObjFields()];
/*      */     }
/*      */     
/*      */     public void put(String param1String, boolean param1Boolean) {
/* 1623 */       Bits.putBoolean(this.primVals, getFieldOffset(param1String, boolean.class), param1Boolean);
/*      */     }
/*      */     
/*      */     public void put(String param1String, byte param1Byte) {
/* 1627 */       this.primVals[getFieldOffset(param1String, byte.class)] = param1Byte;
/*      */     }
/*      */     
/*      */     public void put(String param1String, char param1Char) {
/* 1631 */       Bits.putChar(this.primVals, getFieldOffset(param1String, char.class), param1Char);
/*      */     }
/*      */     
/*      */     public void put(String param1String, short param1Short) {
/* 1635 */       Bits.putShort(this.primVals, getFieldOffset(param1String, short.class), param1Short);
/*      */     }
/*      */     
/*      */     public void put(String param1String, int param1Int) {
/* 1639 */       Bits.putInt(this.primVals, getFieldOffset(param1String, int.class), param1Int);
/*      */     }
/*      */     
/*      */     public void put(String param1String, float param1Float) {
/* 1643 */       Bits.putFloat(this.primVals, getFieldOffset(param1String, float.class), param1Float);
/*      */     }
/*      */     
/*      */     public void put(String param1String, long param1Long) {
/* 1647 */       Bits.putLong(this.primVals, getFieldOffset(param1String, long.class), param1Long);
/*      */     }
/*      */     
/*      */     public void put(String param1String, double param1Double) {
/* 1651 */       Bits.putDouble(this.primVals, getFieldOffset(param1String, double.class), param1Double);
/*      */     }
/*      */     
/*      */     public void put(String param1String, Object param1Object) {
/* 1655 */       this.objVals[getFieldOffset(param1String, Object.class)] = param1Object;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void write(ObjectOutput param1ObjectOutput) throws IOException {
/* 1675 */       if (ObjectOutputStream.this != param1ObjectOutput) {
/* 1676 */         throw new IllegalArgumentException("wrong stream");
/*      */       }
/* 1678 */       param1ObjectOutput.write(this.primVals, 0, this.primVals.length);
/*      */       
/* 1680 */       ObjectStreamField[] arrayOfObjectStreamField = this.desc.getFields(false);
/* 1681 */       int i = arrayOfObjectStreamField.length - this.objVals.length;
/*      */       
/* 1683 */       for (byte b = 0; b < this.objVals.length; b++) {
/* 1684 */         if (arrayOfObjectStreamField[i + b].isUnshared()) {
/* 1685 */           throw new IOException("cannot write unshared object");
/*      */         }
/* 1687 */         param1ObjectOutput.writeObject(this.objVals[b]);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void writeFields() throws IOException {
/* 1695 */       ObjectOutputStream.this.bout.write(this.primVals, 0, this.primVals.length, false);
/*      */       
/* 1697 */       ObjectStreamField[] arrayOfObjectStreamField = this.desc.getFields(false);
/* 1698 */       int i = arrayOfObjectStreamField.length - this.objVals.length;
/* 1699 */       for (byte b = 0; b < this.objVals.length; b++) {
/* 1700 */         if (ObjectOutputStream.extendedDebugInfo);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int getFieldOffset(String param1String, Class<?> param1Class) {
/* 1724 */       ObjectStreamField objectStreamField = this.desc.getField(param1String, param1Class);
/* 1725 */       if (objectStreamField == null) {
/* 1726 */         throw new IllegalArgumentException("no such field " + param1String + " with type " + param1Class);
/*      */       }
/*      */       
/* 1729 */       return objectStreamField.getOffset();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class BlockDataOutputStream
/*      */     extends OutputStream
/*      */     implements DataOutput
/*      */   {
/*      */     private static final int MAX_BLOCK_SIZE = 1024;
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int MAX_HEADER_SIZE = 5;
/*      */ 
/*      */     
/*      */     private static final int CHAR_BUF_SIZE = 256;
/*      */ 
/*      */     
/* 1750 */     private final byte[] buf = new byte[1024];
/*      */     
/* 1752 */     private final byte[] hbuf = new byte[5];
/*      */     
/* 1754 */     private final char[] cbuf = new char[256];
/*      */ 
/*      */     
/*      */     private boolean blkmode = false;
/*      */     
/* 1759 */     private int pos = 0;
/*      */ 
/*      */ 
/*      */     
/*      */     private final OutputStream out;
/*      */ 
/*      */     
/*      */     private final DataOutputStream dout;
/*      */ 
/*      */ 
/*      */     
/*      */     BlockDataOutputStream(OutputStream param1OutputStream) {
/* 1771 */       this.out = param1OutputStream;
/* 1772 */       this.dout = new DataOutputStream(this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean setBlockDataMode(boolean param1Boolean) throws IOException {
/* 1783 */       if (this.blkmode == param1Boolean) {
/* 1784 */         return this.blkmode;
/*      */       }
/* 1786 */       drain();
/* 1787 */       this.blkmode = param1Boolean;
/* 1788 */       return !this.blkmode;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean getBlockDataMode() {
/* 1796 */       return this.blkmode;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void write(int param1Int) throws IOException {
/* 1807 */       if (this.pos >= 1024) {
/* 1808 */         drain();
/*      */       }
/* 1810 */       this.buf[this.pos++] = (byte)param1Int;
/*      */     }
/*      */     
/*      */     public void write(byte[] param1ArrayOfbyte) throws IOException {
/* 1814 */       write(param1ArrayOfbyte, 0, param1ArrayOfbyte.length, false);
/*      */     }
/*      */     
/*      */     public void write(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 1818 */       write(param1ArrayOfbyte, param1Int1, param1Int2, false);
/*      */     }
/*      */     
/*      */     public void flush() throws IOException {
/* 1822 */       drain();
/* 1823 */       this.out.flush();
/*      */     }
/*      */     
/*      */     public void close() throws IOException {
/* 1827 */       flush();
/* 1828 */       this.out.close();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void write(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2, boolean param1Boolean) throws IOException {
/* 1840 */       if (!param1Boolean && !this.blkmode) {
/* 1841 */         drain();
/* 1842 */         this.out.write(param1ArrayOfbyte, param1Int1, param1Int2);
/*      */         
/*      */         return;
/*      */       } 
/* 1846 */       while (param1Int2 > 0) {
/* 1847 */         if (this.pos >= 1024) {
/* 1848 */           drain();
/*      */         }
/* 1850 */         if (param1Int2 >= 1024 && !param1Boolean && this.pos == 0) {
/*      */           
/* 1852 */           writeBlockHeader(1024);
/* 1853 */           this.out.write(param1ArrayOfbyte, param1Int1, 1024);
/* 1854 */           param1Int1 += 1024;
/* 1855 */           param1Int2 -= 1024; continue;
/*      */         } 
/* 1857 */         int i = Math.min(param1Int2, 1024 - this.pos);
/* 1858 */         System.arraycopy(param1ArrayOfbyte, param1Int1, this.buf, this.pos, i);
/* 1859 */         this.pos += i;
/* 1860 */         param1Int1 += i;
/* 1861 */         param1Int2 -= i;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void drain() throws IOException {
/* 1871 */       if (this.pos == 0) {
/*      */         return;
/*      */       }
/* 1874 */       if (this.blkmode) {
/* 1875 */         writeBlockHeader(this.pos);
/*      */       }
/* 1877 */       this.out.write(this.buf, 0, this.pos);
/* 1878 */       this.pos = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeBlockHeader(int param1Int) throws IOException {
/* 1887 */       if (param1Int <= 255) {
/* 1888 */         this.hbuf[0] = 119;
/* 1889 */         this.hbuf[1] = (byte)param1Int;
/* 1890 */         this.out.write(this.hbuf, 0, 2);
/*      */       } else {
/* 1892 */         this.hbuf[0] = 122;
/* 1893 */         Bits.putInt(this.hbuf, 1, param1Int);
/* 1894 */         this.out.write(this.hbuf, 0, 5);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void writeBoolean(boolean param1Boolean) throws IOException {
/* 1907 */       if (this.pos >= 1024) {
/* 1908 */         drain();
/*      */       }
/* 1910 */       Bits.putBoolean(this.buf, this.pos++, param1Boolean);
/*      */     }
/*      */     
/*      */     public void writeByte(int param1Int) throws IOException {
/* 1914 */       if (this.pos >= 1024) {
/* 1915 */         drain();
/*      */       }
/* 1917 */       this.buf[this.pos++] = (byte)param1Int;
/*      */     }
/*      */     
/*      */     public void writeChar(int param1Int) throws IOException {
/* 1921 */       if (this.pos + 2 <= 1024) {
/* 1922 */         Bits.putChar(this.buf, this.pos, (char)param1Int);
/* 1923 */         this.pos += 2;
/*      */       } else {
/* 1925 */         this.dout.writeChar(param1Int);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void writeShort(int param1Int) throws IOException {
/* 1930 */       if (this.pos + 2 <= 1024) {
/* 1931 */         Bits.putShort(this.buf, this.pos, (short)param1Int);
/* 1932 */         this.pos += 2;
/*      */       } else {
/* 1934 */         this.dout.writeShort(param1Int);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void writeInt(int param1Int) throws IOException {
/* 1939 */       if (this.pos + 4 <= 1024) {
/* 1940 */         Bits.putInt(this.buf, this.pos, param1Int);
/* 1941 */         this.pos += 4;
/*      */       } else {
/* 1943 */         this.dout.writeInt(param1Int);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void writeFloat(float param1Float) throws IOException {
/* 1948 */       if (this.pos + 4 <= 1024) {
/* 1949 */         Bits.putFloat(this.buf, this.pos, param1Float);
/* 1950 */         this.pos += 4;
/*      */       } else {
/* 1952 */         this.dout.writeFloat(param1Float);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void writeLong(long param1Long) throws IOException {
/* 1957 */       if (this.pos + 8 <= 1024) {
/* 1958 */         Bits.putLong(this.buf, this.pos, param1Long);
/* 1959 */         this.pos += 8;
/*      */       } else {
/* 1961 */         this.dout.writeLong(param1Long);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void writeDouble(double param1Double) throws IOException {
/* 1966 */       if (this.pos + 8 <= 1024) {
/* 1967 */         Bits.putDouble(this.buf, this.pos, param1Double);
/* 1968 */         this.pos += 8;
/*      */       } else {
/* 1970 */         this.dout.writeDouble(param1Double);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void writeBytes(String param1String) throws IOException {
/* 1975 */       int i = param1String.length();
/* 1976 */       byte b = 0;
/* 1977 */       int j = 0; int k;
/* 1978 */       for (k = 0; k < i; ) {
/* 1979 */         if (b >= j) {
/* 1980 */           b = 0;
/* 1981 */           j = Math.min(i - k, 256);
/* 1982 */           param1String.getChars(k, k + j, this.cbuf, 0);
/*      */         } 
/* 1984 */         if (this.pos >= 1024) {
/* 1985 */           drain();
/*      */         }
/* 1987 */         int m = Math.min(j - b, 1024 - this.pos);
/* 1988 */         int n = this.pos + m;
/* 1989 */         while (this.pos < n) {
/* 1990 */           this.buf[this.pos++] = (byte)this.cbuf[b++];
/*      */         }
/* 1992 */         k += m;
/*      */       } 
/*      */     }
/*      */     
/*      */     public void writeChars(String param1String) throws IOException {
/* 1997 */       int i = param1String.length();
/* 1998 */       for (int j = 0; j < i; ) {
/* 1999 */         int k = Math.min(i - j, 256);
/* 2000 */         param1String.getChars(j, j + k, this.cbuf, 0);
/* 2001 */         writeChars(this.cbuf, 0, k);
/* 2002 */         j += k;
/*      */       } 
/*      */     }
/*      */     
/*      */     public void writeUTF(String param1String) throws IOException {
/* 2007 */       writeUTF(param1String, getUTFLength(param1String));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void writeBooleans(boolean[] param1ArrayOfboolean, int param1Int1, int param1Int2) throws IOException {
/* 2020 */       int i = param1Int1 + param1Int2;
/* 2021 */       while (param1Int1 < i) {
/* 2022 */         if (this.pos >= 1024) {
/* 2023 */           drain();
/*      */         }
/* 2025 */         int j = Math.min(i, param1Int1 + 1024 - this.pos);
/* 2026 */         while (param1Int1 < j) {
/* 2027 */           Bits.putBoolean(this.buf, this.pos++, param1ArrayOfboolean[param1Int1++]);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     void writeChars(char[] param1ArrayOfchar, int param1Int1, int param1Int2) throws IOException {
/* 2033 */       char c = 'Ͼ';
/* 2034 */       int i = param1Int1 + param1Int2;
/* 2035 */       while (param1Int1 < i) {
/* 2036 */         if (this.pos <= c) {
/* 2037 */           int j = 1024 - this.pos >> 1;
/* 2038 */           int k = Math.min(i, param1Int1 + j);
/* 2039 */           while (param1Int1 < k) {
/* 2040 */             Bits.putChar(this.buf, this.pos, param1ArrayOfchar[param1Int1++]);
/* 2041 */             this.pos += 2;
/*      */           }  continue;
/*      */         } 
/* 2044 */         this.dout.writeChar(param1ArrayOfchar[param1Int1++]);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     void writeShorts(short[] param1ArrayOfshort, int param1Int1, int param1Int2) throws IOException {
/* 2050 */       char c = 'Ͼ';
/* 2051 */       int i = param1Int1 + param1Int2;
/* 2052 */       while (param1Int1 < i) {
/* 2053 */         if (this.pos <= c) {
/* 2054 */           int j = 1024 - this.pos >> 1;
/* 2055 */           int k = Math.min(i, param1Int1 + j);
/* 2056 */           while (param1Int1 < k) {
/* 2057 */             Bits.putShort(this.buf, this.pos, param1ArrayOfshort[param1Int1++]);
/* 2058 */             this.pos += 2;
/*      */           }  continue;
/*      */         } 
/* 2061 */         this.dout.writeShort(param1ArrayOfshort[param1Int1++]);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     void writeInts(int[] param1ArrayOfint, int param1Int1, int param1Int2) throws IOException {
/* 2067 */       char c = 'ϼ';
/* 2068 */       int i = param1Int1 + param1Int2;
/* 2069 */       while (param1Int1 < i) {
/* 2070 */         if (this.pos <= c) {
/* 2071 */           int j = 1024 - this.pos >> 2;
/* 2072 */           int k = Math.min(i, param1Int1 + j);
/* 2073 */           while (param1Int1 < k) {
/* 2074 */             Bits.putInt(this.buf, this.pos, param1ArrayOfint[param1Int1++]);
/* 2075 */             this.pos += 4;
/*      */           }  continue;
/*      */         } 
/* 2078 */         this.dout.writeInt(param1ArrayOfint[param1Int1++]);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     void writeFloats(float[] param1ArrayOffloat, int param1Int1, int param1Int2) throws IOException {
/* 2084 */       char c = 'ϼ';
/* 2085 */       int i = param1Int1 + param1Int2;
/* 2086 */       while (param1Int1 < i) {
/* 2087 */         if (this.pos <= c) {
/* 2088 */           int j = 1024 - this.pos >> 2;
/* 2089 */           int k = Math.min(i - param1Int1, j);
/* 2090 */           ObjectOutputStream.floatsToBytes(param1ArrayOffloat, param1Int1, this.buf, this.pos, k);
/* 2091 */           param1Int1 += k;
/* 2092 */           this.pos += k << 2; continue;
/*      */         } 
/* 2094 */         this.dout.writeFloat(param1ArrayOffloat[param1Int1++]);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     void writeLongs(long[] param1ArrayOflong, int param1Int1, int param1Int2) throws IOException {
/* 2100 */       char c = 'ϸ';
/* 2101 */       int i = param1Int1 + param1Int2;
/* 2102 */       while (param1Int1 < i) {
/* 2103 */         if (this.pos <= c) {
/* 2104 */           int j = 1024 - this.pos >> 3;
/* 2105 */           int k = Math.min(i, param1Int1 + j);
/* 2106 */           while (param1Int1 < k) {
/* 2107 */             Bits.putLong(this.buf, this.pos, param1ArrayOflong[param1Int1++]);
/* 2108 */             this.pos += 8;
/*      */           }  continue;
/*      */         } 
/* 2111 */         this.dout.writeLong(param1ArrayOflong[param1Int1++]);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     void writeDoubles(double[] param1ArrayOfdouble, int param1Int1, int param1Int2) throws IOException {
/* 2117 */       char c = 'ϸ';
/* 2118 */       int i = param1Int1 + param1Int2;
/* 2119 */       while (param1Int1 < i) {
/* 2120 */         if (this.pos <= c) {
/* 2121 */           int j = 1024 - this.pos >> 3;
/* 2122 */           int k = Math.min(i - param1Int1, j);
/* 2123 */           ObjectOutputStream.doublesToBytes(param1ArrayOfdouble, param1Int1, this.buf, this.pos, k);
/* 2124 */           param1Int1 += k;
/* 2125 */           this.pos += k << 3; continue;
/*      */         } 
/* 2127 */         this.dout.writeDouble(param1ArrayOfdouble[param1Int1++]);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     long getUTFLength(String param1String) {
/* 2136 */       int i = param1String.length();
/* 2137 */       long l = 0L; int j;
/* 2138 */       for (j = 0; j < i; ) {
/* 2139 */         int k = Math.min(i - j, 256);
/* 2140 */         param1String.getChars(j, j + k, this.cbuf, 0);
/* 2141 */         for (byte b = 0; b < k; b++) {
/* 2142 */           char c = this.cbuf[b];
/* 2143 */           if (c >= '\001' && c <= '') {
/* 2144 */             l++;
/* 2145 */           } else if (c > '߿') {
/* 2146 */             l += 3L;
/*      */           } else {
/* 2148 */             l += 2L;
/*      */           } 
/*      */         } 
/* 2151 */         j += k;
/*      */       } 
/* 2153 */       return l;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void writeUTF(String param1String, long param1Long) throws IOException {
/* 2163 */       if (param1Long > 65535L) {
/* 2164 */         throw new UTFDataFormatException();
/*      */       }
/* 2166 */       writeShort((int)param1Long);
/* 2167 */       if (param1Long == param1String.length()) {
/* 2168 */         writeBytes(param1String);
/*      */       } else {
/* 2170 */         writeUTFBody(param1String);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void writeLongUTF(String param1String) throws IOException {
/* 2180 */       writeLongUTF(param1String, getUTFLength(param1String));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void writeLongUTF(String param1String, long param1Long) throws IOException {
/* 2188 */       writeLong(param1Long);
/* 2189 */       if (param1Long == param1String.length()) {
/* 2190 */         writeBytes(param1String);
/*      */       } else {
/* 2192 */         writeUTFBody(param1String);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeUTFBody(String param1String) throws IOException {
/* 2201 */       char c = 'Ͻ';
/* 2202 */       int i = param1String.length(); int j;
/* 2203 */       for (j = 0; j < i; ) {
/* 2204 */         int k = Math.min(i - j, 256);
/* 2205 */         param1String.getChars(j, j + k, this.cbuf, 0);
/* 2206 */         for (byte b = 0; b < k; b++) {
/* 2207 */           char c1 = this.cbuf[b];
/* 2208 */           if (this.pos <= c) {
/* 2209 */             if (c1 <= '' && c1 != '\000') {
/* 2210 */               this.buf[this.pos++] = (byte)c1;
/* 2211 */             } else if (c1 > '߿') {
/* 2212 */               this.buf[this.pos + 2] = (byte)(0x80 | c1 >> 0 & 0x3F);
/* 2213 */               this.buf[this.pos + 1] = (byte)(0x80 | c1 >> 6 & 0x3F);
/* 2214 */               this.buf[this.pos + 0] = (byte)(0xE0 | c1 >> 12 & 0xF);
/* 2215 */               this.pos += 3;
/*      */             } else {
/* 2217 */               this.buf[this.pos + 1] = (byte)(0x80 | c1 >> 0 & 0x3F);
/* 2218 */               this.buf[this.pos + 0] = (byte)(0xC0 | c1 >> 6 & 0x1F);
/* 2219 */               this.pos += 2;
/*      */             }
/*      */           
/* 2222 */           } else if (c1 <= '' && c1 != '\000') {
/* 2223 */             write(c1);
/* 2224 */           } else if (c1 > '߿') {
/* 2225 */             write(0xE0 | c1 >> 12 & 0xF);
/* 2226 */             write(0x80 | c1 >> 6 & 0x3F);
/* 2227 */             write(0x80 | c1 >> 0 & 0x3F);
/*      */           } else {
/* 2229 */             write(0xC0 | c1 >> 6 & 0x1F);
/* 2230 */             write(0x80 | c1 >> 0 & 0x3F);
/*      */           } 
/*      */         } 
/*      */         
/* 2234 */         j += k;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class HandleTable
/*      */   {
/*      */     private int size;
/*      */ 
/*      */     
/*      */     private int threshold;
/*      */ 
/*      */     
/*      */     private final float loadFactor;
/*      */ 
/*      */     
/*      */     private int[] spine;
/*      */ 
/*      */     
/*      */     private int[] next;
/*      */ 
/*      */     
/*      */     private Object[] objs;
/*      */ 
/*      */     
/*      */     HandleTable(int param1Int, float param1Float) {
/* 2262 */       this.loadFactor = param1Float;
/* 2263 */       this.spine = new int[param1Int];
/* 2264 */       this.next = new int[param1Int];
/* 2265 */       this.objs = new Object[param1Int];
/* 2266 */       this.threshold = (int)(param1Int * param1Float);
/* 2267 */       clear();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int assign(Object param1Object) {
/* 2275 */       if (this.size >= this.next.length) {
/* 2276 */         growEntries();
/*      */       }
/* 2278 */       if (this.size >= this.threshold) {
/* 2279 */         growSpine();
/*      */       }
/* 2281 */       insert(param1Object, this.size);
/* 2282 */       return this.size++;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int lookup(Object param1Object) {
/* 2290 */       if (this.size == 0) {
/* 2291 */         return -1;
/*      */       }
/* 2293 */       int i = hash(param1Object) % this.spine.length;
/* 2294 */       for (int j = this.spine[i]; j >= 0; j = this.next[j]) {
/* 2295 */         if (this.objs[j] == param1Object) {
/* 2296 */           return j;
/*      */         }
/*      */       } 
/* 2299 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void clear() {
/* 2306 */       Arrays.fill(this.spine, -1);
/* 2307 */       Arrays.fill(this.objs, 0, this.size, (Object)null);
/* 2308 */       this.size = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int size() {
/* 2315 */       return this.size;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void insert(Object param1Object, int param1Int) {
/* 2323 */       int i = hash(param1Object) % this.spine.length;
/* 2324 */       this.objs[param1Int] = param1Object;
/* 2325 */       this.next[param1Int] = this.spine[i];
/* 2326 */       this.spine[i] = param1Int;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void growSpine() {
/* 2334 */       this.spine = new int[(this.spine.length << 1) + 1];
/* 2335 */       this.threshold = (int)(this.spine.length * this.loadFactor);
/* 2336 */       Arrays.fill(this.spine, -1);
/* 2337 */       for (byte b = 0; b < this.size; b++) {
/* 2338 */         insert(this.objs[b], b);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void growEntries() {
/* 2346 */       int i = (this.next.length << 1) + 1;
/* 2347 */       int[] arrayOfInt = new int[i];
/* 2348 */       System.arraycopy(this.next, 0, arrayOfInt, 0, this.size);
/* 2349 */       this.next = arrayOfInt;
/*      */       
/* 2351 */       Object[] arrayOfObject = new Object[i];
/* 2352 */       System.arraycopy(this.objs, 0, arrayOfObject, 0, this.size);
/* 2353 */       this.objs = arrayOfObject;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int hash(Object param1Object) {
/* 2360 */       return System.identityHashCode(param1Object) & Integer.MAX_VALUE;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class ReplaceTable
/*      */   {
/*      */     private final ObjectOutputStream.HandleTable htab;
/*      */ 
/*      */ 
/*      */     
/*      */     private Object[] reps;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ReplaceTable(int param1Int, float param1Float) {
/* 2379 */       this.htab = new ObjectOutputStream.HandleTable(param1Int, param1Float);
/* 2380 */       this.reps = new Object[param1Int];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void assign(Object param1Object1, Object param1Object2) {
/* 2387 */       int i = this.htab.assign(param1Object1);
/* 2388 */       while (i >= this.reps.length) {
/* 2389 */         grow();
/*      */       }
/* 2391 */       this.reps[i] = param1Object2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object lookup(Object param1Object) {
/* 2399 */       int i = this.htab.lookup(param1Object);
/* 2400 */       return (i >= 0) ? this.reps[i] : param1Object;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void clear() {
/* 2407 */       Arrays.fill(this.reps, 0, this.htab.size(), (Object)null);
/* 2408 */       this.htab.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int size() {
/* 2415 */       return this.htab.size();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void grow() {
/* 2422 */       Object[] arrayOfObject = new Object[(this.reps.length << 1) + 1];
/* 2423 */       System.arraycopy(this.reps, 0, arrayOfObject, 0, this.reps.length);
/* 2424 */       this.reps = arrayOfObject;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class DebugTraceInfoStack
/*      */   {
/* 2436 */     private final List<String> stack = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void clear() {
/* 2443 */       this.stack.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pop() {
/* 2450 */       this.stack.remove(this.stack.size() - 1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void push(String param1String) {
/* 2457 */       this.stack.add("\t- " + param1String);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 2464 */       StringBuilder stringBuilder = new StringBuilder();
/* 2465 */       if (!this.stack.isEmpty()) {
/* 2466 */         for (int i = this.stack.size(); i > 0; i--) {
/* 2467 */           stringBuilder.append((String)this.stack.get(i - 1) + ((i != 1) ? "\n" : ""));
/*      */         }
/*      */       }
/* 2470 */       return stringBuilder.toString();
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/ObjectOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */