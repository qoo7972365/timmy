/*      */ package com.sun.corba.se.impl.io;
/*      */ 
/*      */ import com.sun.corba.se.impl.logging.OMGSystemException;
/*      */ import com.sun.corba.se.impl.logging.UtilSystemException;
/*      */ import com.sun.corba.se.impl.util.Utility;
/*      */ import com.sun.org.omg.CORBA.ValueDefPackage.FullValueDescription;
/*      */ import com.sun.org.omg.SendingContext.CodeBase;
/*      */ import java.io.EOFException;
/*      */ import java.io.Externalizable;
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidClassException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.NotActiveException;
/*      */ import java.io.ObjectInputValidation;
/*      */ import java.io.OptionalDataException;
/*      */ import java.io.StreamCorruptedException;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.rmi.Remote;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Vector;
/*      */ import javax.rmi.CORBA.Util;
/*      */ import javax.rmi.CORBA.ValueHandler;
/*      */ import org.omg.CORBA.BAD_PARAM;
/*      */ import org.omg.CORBA.CompletionStatus;
/*      */ import org.omg.CORBA.MARSHAL;
/*      */ import org.omg.CORBA.ORB;
/*      */ import org.omg.CORBA.Object;
/*      */ import org.omg.CORBA.TCKind;
/*      */ import org.omg.CORBA.TypeCode;
/*      */ import org.omg.CORBA.ValueMember;
/*      */ import org.omg.CORBA.portable.IndirectionException;
/*      */ import org.omg.CORBA.portable.InputStream;
/*      */ import org.omg.CORBA.portable.ValueInputStream;
/*      */ import org.omg.CORBA_2_3.portable.InputStream;
/*      */ import sun.corba.Bridge;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class IIOPInputStream
/*      */   extends InputStreamHook
/*      */ {
/*   99 */   private static Bridge bridge = AccessController.<Bridge>doPrivileged(new PrivilegedAction<Bridge>()
/*      */       {
/*      */         public Object run() {
/*  102 */           return Bridge.get();
/*      */         }
/*      */       });
/*      */ 
/*      */   
/*  107 */   private static OMGSystemException omgWrapper = OMGSystemException.get("rpc.encoding");
/*      */   
/*  109 */   private static UtilSystemException utilWrapper = UtilSystemException.get("rpc.encoding");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  123 */   private ValueMember[] defaultReadObjectFVDMembers = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  131 */   private Object currentObject = null;
/*      */   
/*  133 */   private ObjectStreamClass currentClassDesc = null;
/*      */   
/*  135 */   private Class currentClass = null;
/*      */   
/*  137 */   private int recursionDepth = 0;
/*      */   
/*  139 */   private int simpleReadDepth = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  149 */   ActiveRecursionManager activeRecursionMgr = new ActiveRecursionManager();
/*      */   
/*  151 */   private IOException abortIOException = null;
/*      */ 
/*      */   
/*  154 */   private ClassNotFoundException abortClassNotFoundException = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  176 */   public static final TypeCode kRemoteTypeCode = ORB.init().get_primitive_tc(TCKind.tk_objref);
/*  177 */   public static final TypeCode kValueTypeCode = ORB.init().get_primitive_tc(TCKind.tk_value);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  190 */   private Object[] readObjectArgList = new Object[] { this };
/*      */ 
/*      */   
/*  193 */   private static final Constructor OPT_DATA_EXCEPTION_CTOR = getOptDataExceptionCtor(); private InputStream orbStream; private CodeBase cbSender;
/*      */   private ValueHandlerImpl vhandler;
/*      */   private Vector callbacks;
/*      */   ObjectStreamClass[] classdesc;
/*      */   Class[] classes;
/*      */   int spClass;
/*      */   private static final String kEmptyStr = "";
/*      */   private static final boolean useFVDOnly = false;
/*      */   private byte streamFormatVersion;
/*      */   
/*      */   private static Constructor getOptDataExceptionCtor() {
/*      */     try {
/*  205 */       Constructor constructor = AccessController.<Constructor>doPrivileged(new PrivilegedExceptionAction<Constructor>()
/*      */           {
/*      */ 
/*      */ 
/*      */             
/*      */             public Object run() throws NoSuchMethodException, SecurityException
/*      */             {
/*  212 */               Constructor<OptionalDataException> constructor = OptionalDataException.class.getDeclaredConstructor(new Class[] { boolean.class });
/*      */ 
/*      */ 
/*      */               
/*  216 */               constructor.setAccessible(true);
/*      */               
/*  218 */               return constructor;
/*      */             }
/*      */           });
/*  221 */       if (constructor == null)
/*      */       {
/*  223 */         throw new Error("Unable to find OptionalDataException constructor");
/*      */       }
/*  225 */       return constructor;
/*      */     }
/*  227 */     catch (Exception exception) {
/*      */       
/*  229 */       throw new ExceptionInInitializerError(exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private OptionalDataException createOptionalDataException() {
/*      */     try {
/*  239 */       OptionalDataException optionalDataException = OPT_DATA_EXCEPTION_CTOR.newInstance(new Object[] { Boolean.TRUE });
/*      */ 
/*      */       
/*  242 */       if (optionalDataException == null)
/*      */       {
/*  244 */         throw new Error("Created null OptionalDataException");
/*      */       }
/*  246 */       return optionalDataException;
/*      */     }
/*  248 */     catch (Exception exception) {
/*      */       
/*  250 */       throw new Error("Couldn't create OptionalDataException", exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected byte getStreamFormatVersion() {
/*  257 */     return this.streamFormatVersion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readFormatVersion() throws IOException {
/*  265 */     this.streamFormatVersion = this.orbStream.read_octet();
/*      */     
/*  267 */     if (this.streamFormatVersion < 1 || this.streamFormatVersion > this.vhandler
/*  268 */       .getMaximumStreamFormatVersion()) {
/*  269 */       MARSHAL mARSHAL = omgWrapper.unsupportedFormatVersion(CompletionStatus.COMPLETED_MAYBE);
/*      */ 
/*      */       
/*  272 */       IOException iOException = new IOException("Unsupported format version: " + this.streamFormatVersion);
/*      */       
/*  274 */       iOException.initCause((Throwable)mARSHAL);
/*  275 */       throw iOException;
/*      */     } 
/*      */     
/*  278 */     if (this.streamFormatVersion == 2 && 
/*  279 */       !(this.orbStream instanceof ValueInputStream)) {
/*  280 */       BAD_PARAM bAD_PARAM = omgWrapper.notAValueinputstream(CompletionStatus.COMPLETED_MAYBE);
/*      */ 
/*      */       
/*  283 */       IOException iOException = new IOException("Not a ValueInputStream");
/*  284 */       iOException.initCause((Throwable)bAD_PARAM);
/*  285 */       throw iOException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setTestFVDFlag(boolean paramBoolean) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IIOPInputStream() throws IOException {
/*  300 */     resetStream();
/*      */   }
/*      */   
/*      */   final void setOrbStream(InputStream paramInputStream) {
/*  304 */     this.orbStream = paramInputStream;
/*      */   }
/*      */   
/*      */   final InputStream getOrbStream() {
/*  308 */     return this.orbStream;
/*      */   }
/*      */ 
/*      */   
/*      */   public final void setSender(CodeBase paramCodeBase) {
/*  313 */     this.cbSender = paramCodeBase;
/*      */   }
/*      */   
/*      */   public final CodeBase getSender() {
/*  317 */     return this.cbSender;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setValueHandler(ValueHandler paramValueHandler) {
/*  323 */     this.vhandler = (ValueHandlerImpl)paramValueHandler;
/*      */   }
/*      */   
/*      */   public final ValueHandler getValueHandler() {
/*  327 */     return (ValueHandler)this.vhandler;
/*      */   }
/*      */   
/*      */   final void increaseRecursionDepth() {
/*  331 */     this.recursionDepth++;
/*      */   }
/*      */   
/*      */   final int decreaseRecursionDepth() {
/*  335 */     return --this.recursionDepth;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized Object readObjectDelegate() throws IOException {
/*      */     try {
/*  377 */       this.readObjectState.readData(this);
/*      */       
/*  379 */       return this.orbStream.read_abstract_interface();
/*  380 */     } catch (MARSHAL mARSHAL) {
/*  381 */       handleOptionalDataMarshalException(mARSHAL, true);
/*  382 */       throw mARSHAL;
/*  383 */     } catch (IndirectionException indirectionException) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  388 */       return this.activeRecursionMgr.getObject(indirectionException.offset);
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
/*      */   final synchronized Object simpleReadObject(Class paramClass, String paramString, CodeBase paramCodeBase, int paramInt) {
/*  400 */     Object object1 = this.currentObject;
/*  401 */     ObjectStreamClass objectStreamClass = this.currentClassDesc;
/*  402 */     Class clazz = this.currentClass;
/*  403 */     byte b = this.streamFormatVersion;
/*      */     
/*  405 */     this.simpleReadDepth++;
/*  406 */     Object object2 = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  413 */       if (this.vhandler.useFullValueDescription(paramClass, paramString)) {
/*  414 */         object2 = inputObjectUsingFVD(paramClass, paramString, paramCodeBase, paramInt);
/*      */       } else {
/*  416 */         object2 = inputObject(paramClass, paramString, paramCodeBase, paramInt);
/*      */       } 
/*      */       
/*  419 */       object2 = this.currentClassDesc.readResolve(object2);
/*      */     }
/*  421 */     catch (ClassNotFoundException classNotFoundException1) {
/*      */       
/*  423 */       bridge.throwException(classNotFoundException1);
/*  424 */       return null;
/*      */     }
/*  426 */     catch (IOException iOException1) {
/*      */ 
/*      */       
/*  429 */       bridge.throwException(iOException1);
/*  430 */       return null;
/*      */     } finally {
/*      */       
/*  433 */       this.simpleReadDepth--;
/*  434 */       this.currentObject = object1;
/*  435 */       this.currentClassDesc = objectStreamClass;
/*  436 */       this.currentClass = clazz;
/*  437 */       this.streamFormatVersion = b;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  444 */     IOException iOException = this.abortIOException;
/*  445 */     if (this.simpleReadDepth == 0)
/*  446 */       this.abortIOException = null; 
/*  447 */     if (iOException != null) {
/*  448 */       bridge.throwException(iOException);
/*  449 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  453 */     ClassNotFoundException classNotFoundException = this.abortClassNotFoundException;
/*  454 */     if (this.simpleReadDepth == 0)
/*  455 */       this.abortClassNotFoundException = null; 
/*  456 */     if (classNotFoundException != null) {
/*  457 */       bridge.throwException(classNotFoundException);
/*  458 */       return null;
/*      */     } 
/*      */     
/*  461 */     return object2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized void simpleSkipObject(String paramString, CodeBase paramCodeBase) {
/*  470 */     Object object = this.currentObject;
/*  471 */     ObjectStreamClass objectStreamClass = this.currentClassDesc;
/*  472 */     Class clazz = this.currentClass;
/*  473 */     byte b = this.streamFormatVersion;
/*      */     
/*  475 */     this.simpleReadDepth++;
/*  476 */     Object object1 = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  482 */       skipObjectUsingFVD(paramString, paramCodeBase);
/*      */     }
/*  484 */     catch (ClassNotFoundException classNotFoundException1) {
/*      */       
/*  486 */       bridge.throwException(classNotFoundException1);
/*      */       
/*      */       return;
/*  489 */     } catch (IOException iOException1) {
/*      */       
/*  491 */       bridge.throwException(iOException1);
/*      */       
/*      */       return;
/*      */     } finally {
/*  495 */       this.simpleReadDepth--;
/*  496 */       this.streamFormatVersion = b;
/*  497 */       this.currentObject = object;
/*  498 */       this.currentClassDesc = objectStreamClass;
/*  499 */       this.currentClass = clazz;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  506 */     IOException iOException = this.abortIOException;
/*  507 */     if (this.simpleReadDepth == 0)
/*  508 */       this.abortIOException = null; 
/*  509 */     if (iOException != null) {
/*  510 */       bridge.throwException(iOException);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  515 */     ClassNotFoundException classNotFoundException = this.abortClassNotFoundException;
/*  516 */     if (this.simpleReadDepth == 0)
/*  517 */       this.abortClassNotFoundException = null; 
/*  518 */     if (classNotFoundException != null) {
/*  519 */       bridge.throwException(classNotFoundException);
/*      */       return;
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
/*      */   protected final Object readObjectOverride() throws OptionalDataException, ClassNotFoundException, IOException {
/*  542 */     return readObjectDelegate();
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
/*      */   final synchronized void defaultReadObjectDelegate() {
/*      */     try {
/*  566 */       if (this.currentObject == null || this.currentClassDesc == null)
/*      */       {
/*  568 */         throw new NotActiveException("defaultReadObjectDelegate");
/*      */       }
/*  570 */       if (!this.currentClassDesc.forClass().isAssignableFrom(this.currentObject
/*  571 */           .getClass())) {
/*  572 */         throw new IOException("Object Type mismatch");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  579 */       if (this.defaultReadObjectFVDMembers != null && this.defaultReadObjectFVDMembers.length > 0)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  591 */         inputClassFields(this.currentObject, this.currentClass, this.currentClassDesc, this.defaultReadObjectFVDMembers, this.cbSender);
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  601 */         ObjectStreamField[] arrayOfObjectStreamField = this.currentClassDesc.getFieldsNoCopy();
/*  602 */         if (arrayOfObjectStreamField.length > 0) {
/*  603 */           inputClassFields(this.currentObject, this.currentClass, arrayOfObjectStreamField, this.cbSender);
/*      */         }
/*      */       }
/*      */     
/*  607 */     } catch (NotActiveException notActiveException) {
/*      */       
/*  609 */       bridge.throwException(notActiveException);
/*      */     }
/*  611 */     catch (IOException iOException) {
/*      */       
/*  613 */       bridge.throwException(iOException);
/*      */     }
/*  615 */     catch (ClassNotFoundException classNotFoundException) {
/*      */       
/*  617 */       bridge.throwException(classNotFoundException);
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
/*      */   public final boolean enableResolveObjectDelegate(boolean paramBoolean) {
/*  640 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mark(int paramInt) {
/*  647 */     this.orbStream.mark(paramInt);
/*      */   }
/*      */   
/*      */   public final boolean markSupported() {
/*  651 */     return this.orbStream.markSupported();
/*      */   }
/*      */   
/*      */   public final void reset() throws IOException {
/*      */     try {
/*  656 */       this.orbStream.reset();
/*  657 */     } catch (Error error) {
/*  658 */       IOException iOException = new IOException(error.getMessage());
/*  659 */       iOException.initCause(error);
/*  660 */       throw iOException;
/*      */     } 
/*      */   }
/*      */   
/*      */   public final int available() throws IOException {
/*  665 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public final void close() throws IOException {}
/*      */ 
/*      */   
/*      */   public final int read() throws IOException {
/*      */     try {
/*  674 */       this.readObjectState.readData(this);
/*      */       
/*  676 */       return this.orbStream.read_octet() << 0 & 0xFF;
/*  677 */     } catch (MARSHAL mARSHAL) {
/*  678 */       if (mARSHAL.minor == 1330446344) {
/*      */         
/*  680 */         setState(IN_READ_OBJECT_NO_MORE_OPT_DATA);
/*  681 */         return -1;
/*      */       } 
/*      */       
/*  684 */       throw mARSHAL;
/*  685 */     } catch (Error error) {
/*  686 */       IOException iOException = new IOException(error.getMessage());
/*  687 */       iOException.initCause(error);
/*  688 */       throw iOException;
/*      */     } 
/*      */   }
/*      */   
/*      */   public final int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/*      */     try {
/*  694 */       this.readObjectState.readData(this);
/*      */       
/*  696 */       this.orbStream.read_octet_array(paramArrayOfbyte, paramInt1, paramInt2);
/*  697 */       return paramInt2;
/*  698 */     } catch (MARSHAL mARSHAL) {
/*  699 */       if (mARSHAL.minor == 1330446344) {
/*      */         
/*  701 */         setState(IN_READ_OBJECT_NO_MORE_OPT_DATA);
/*  702 */         return -1;
/*      */       } 
/*      */       
/*  705 */       throw mARSHAL;
/*  706 */     } catch (Error error) {
/*  707 */       IOException iOException = new IOException(error.getMessage());
/*  708 */       iOException.initCause(error);
/*  709 */       throw iOException;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean readBoolean() throws IOException {
/*      */     try {
/*  716 */       this.readObjectState.readData(this);
/*      */       
/*  718 */       return this.orbStream.read_boolean();
/*  719 */     } catch (MARSHAL mARSHAL) {
/*  720 */       handleOptionalDataMarshalException(mARSHAL, false);
/*  721 */       throw mARSHAL;
/*      */     }
/*  723 */     catch (Error error) {
/*  724 */       IOException iOException = new IOException(error.getMessage());
/*  725 */       iOException.initCause(error);
/*  726 */       throw iOException;
/*      */     } 
/*      */   }
/*      */   
/*      */   public final byte readByte() throws IOException {
/*      */     try {
/*  732 */       this.readObjectState.readData(this);
/*      */       
/*  734 */       return this.orbStream.read_octet();
/*  735 */     } catch (MARSHAL mARSHAL) {
/*  736 */       handleOptionalDataMarshalException(mARSHAL, false);
/*  737 */       throw mARSHAL;
/*      */     }
/*  739 */     catch (Error error) {
/*  740 */       IOException iOException = new IOException(error.getMessage());
/*  741 */       iOException.initCause(error);
/*  742 */       throw iOException;
/*      */     } 
/*      */   }
/*      */   
/*      */   public final char readChar() throws IOException {
/*      */     try {
/*  748 */       this.readObjectState.readData(this);
/*      */       
/*  750 */       return this.orbStream.read_wchar();
/*  751 */     } catch (MARSHAL mARSHAL) {
/*  752 */       handleOptionalDataMarshalException(mARSHAL, false);
/*  753 */       throw mARSHAL;
/*      */     }
/*  755 */     catch (Error error) {
/*  756 */       IOException iOException = new IOException(error.getMessage());
/*  757 */       iOException.initCause(error);
/*  758 */       throw iOException;
/*      */     } 
/*      */   }
/*      */   
/*      */   public final double readDouble() throws IOException {
/*      */     try {
/*  764 */       this.readObjectState.readData(this);
/*      */       
/*  766 */       return this.orbStream.read_double();
/*  767 */     } catch (MARSHAL mARSHAL) {
/*  768 */       handleOptionalDataMarshalException(mARSHAL, false);
/*  769 */       throw mARSHAL;
/*  770 */     } catch (Error error) {
/*  771 */       IOException iOException = new IOException(error.getMessage());
/*  772 */       iOException.initCause(error);
/*  773 */       throw iOException;
/*      */     } 
/*      */   }
/*      */   
/*      */   public final float readFloat() throws IOException {
/*      */     try {
/*  779 */       this.readObjectState.readData(this);
/*      */       
/*  781 */       return this.orbStream.read_float();
/*  782 */     } catch (MARSHAL mARSHAL) {
/*  783 */       handleOptionalDataMarshalException(mARSHAL, false);
/*  784 */       throw mARSHAL;
/*  785 */     } catch (Error error) {
/*  786 */       IOException iOException = new IOException(error.getMessage());
/*  787 */       iOException.initCause(error);
/*  788 */       throw iOException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void readFully(byte[] paramArrayOfbyte) throws IOException {
/*  795 */     readFully(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*      */   }
/*      */ 
/*      */   
/*      */   public final void readFully(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/*      */     try {
/*  801 */       this.readObjectState.readData(this);
/*      */       
/*  803 */       this.orbStream.read_octet_array(paramArrayOfbyte, paramInt1, paramInt2);
/*  804 */     } catch (MARSHAL mARSHAL) {
/*  805 */       handleOptionalDataMarshalException(mARSHAL, false);
/*      */       
/*  807 */       throw mARSHAL;
/*  808 */     } catch (Error error) {
/*  809 */       IOException iOException = new IOException(error.getMessage());
/*  810 */       iOException.initCause(error);
/*  811 */       throw iOException;
/*      */     } 
/*      */   }
/*      */   
/*      */   public final int readInt() throws IOException {
/*      */     try {
/*  817 */       this.readObjectState.readData(this);
/*      */       
/*  819 */       return this.orbStream.read_long();
/*  820 */     } catch (MARSHAL mARSHAL) {
/*  821 */       handleOptionalDataMarshalException(mARSHAL, false);
/*  822 */       throw mARSHAL;
/*  823 */     } catch (Error error) {
/*  824 */       IOException iOException = new IOException(error.getMessage());
/*  825 */       iOException.initCause(error);
/*  826 */       throw iOException;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final String readLine() throws IOException {
/*  832 */     throw new IOException("Method readLine not supported");
/*      */   }
/*      */   
/*      */   public final long readLong() throws IOException {
/*      */     try {
/*  837 */       this.readObjectState.readData(this);
/*      */       
/*  839 */       return this.orbStream.read_longlong();
/*  840 */     } catch (MARSHAL mARSHAL) {
/*  841 */       handleOptionalDataMarshalException(mARSHAL, false);
/*  842 */       throw mARSHAL;
/*  843 */     } catch (Error error) {
/*  844 */       IOException iOException = new IOException(error.getMessage());
/*  845 */       iOException.initCause(error);
/*  846 */       throw iOException;
/*      */     } 
/*      */   }
/*      */   
/*      */   public final short readShort() throws IOException {
/*      */     try {
/*  852 */       this.readObjectState.readData(this);
/*      */       
/*  854 */       return this.orbStream.read_short();
/*  855 */     } catch (MARSHAL mARSHAL) {
/*  856 */       handleOptionalDataMarshalException(mARSHAL, false);
/*  857 */       throw mARSHAL;
/*  858 */     } catch (Error error) {
/*  859 */       IOException iOException = new IOException(error.getMessage());
/*  860 */       iOException.initCause(error);
/*  861 */       throw iOException;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void readStreamHeader() throws IOException, StreamCorruptedException {}
/*      */ 
/*      */   
/*      */   public final int readUnsignedByte() throws IOException {
/*      */     try {
/*  871 */       this.readObjectState.readData(this);
/*      */       
/*  873 */       return this.orbStream.read_octet() << 0 & 0xFF;
/*  874 */     } catch (MARSHAL mARSHAL) {
/*  875 */       handleOptionalDataMarshalException(mARSHAL, false);
/*  876 */       throw mARSHAL;
/*  877 */     } catch (Error error) {
/*  878 */       IOException iOException = new IOException(error.getMessage());
/*  879 */       iOException.initCause(error);
/*  880 */       throw iOException;
/*      */     } 
/*      */   }
/*      */   
/*      */   public final int readUnsignedShort() throws IOException {
/*      */     try {
/*  886 */       this.readObjectState.readData(this);
/*      */       
/*  888 */       return this.orbStream.read_ushort() << 0 & 0xFFFF;
/*  889 */     } catch (MARSHAL mARSHAL) {
/*  890 */       handleOptionalDataMarshalException(mARSHAL, false);
/*  891 */       throw mARSHAL;
/*  892 */     } catch (Error error) {
/*  893 */       IOException iOException = new IOException(error.getMessage());
/*  894 */       iOException.initCause(error);
/*  895 */       throw iOException;
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
/*      */   protected String internalReadUTF(InputStream paramInputStream) {
/*  907 */     return paramInputStream.read_wstring();
/*      */   }
/*      */   
/*      */   public final String readUTF() throws IOException {
/*      */     try {
/*  912 */       this.readObjectState.readData(this);
/*      */       
/*  914 */       return internalReadUTF((InputStream)this.orbStream);
/*  915 */     } catch (MARSHAL mARSHAL) {
/*  916 */       handleOptionalDataMarshalException(mARSHAL, false);
/*  917 */       throw mARSHAL;
/*  918 */     } catch (Error error) {
/*  919 */       IOException iOException = new IOException(error.getMessage());
/*  920 */       iOException.initCause(error);
/*  921 */       throw iOException;
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
/*      */   private void handleOptionalDataMarshalException(MARSHAL paramMARSHAL, boolean paramBoolean) throws IOException {
/*  942 */     if (paramMARSHAL.minor == 1330446344) {
/*      */       OptionalDataException optionalDataException;
/*      */ 
/*      */ 
/*      */       
/*  947 */       if (!paramBoolean) {
/*  948 */         EOFException eOFException = new EOFException("No more optional data");
/*      */       } else {
/*  950 */         optionalDataException = createOptionalDataException();
/*      */       } 
/*  952 */       optionalDataException.initCause((Throwable)paramMARSHAL);
/*      */       
/*  954 */       setState(IN_READ_OBJECT_NO_MORE_OPT_DATA);
/*      */       
/*  956 */       throw optionalDataException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized void registerValidation(ObjectInputValidation paramObjectInputValidation, int paramInt) throws NotActiveException, InvalidObjectException {
/*  964 */     throw new Error("Method registerValidation not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Class resolveClass(ObjectStreamClass paramObjectStreamClass) throws IOException, ClassNotFoundException {
/*  970 */     throw new IOException("Method resolveClass not supported");
/*      */   }
/*      */ 
/*      */   
/*      */   protected final Object resolveObject(Object paramObject) throws IOException {
/*  975 */     throw new IOException("Method resolveObject not supported");
/*      */   }
/*      */   
/*      */   public final int skipBytes(int paramInt) throws IOException {
/*      */     try {
/*  980 */       this.readObjectState.readData(this);
/*      */       
/*  982 */       byte[] arrayOfByte = new byte[paramInt];
/*  983 */       this.orbStream.read_octet_array(arrayOfByte, 0, paramInt);
/*  984 */       return paramInt;
/*  985 */     } catch (MARSHAL mARSHAL) {
/*  986 */       handleOptionalDataMarshalException(mARSHAL, false);
/*      */       
/*  988 */       throw mARSHAL;
/*  989 */     } catch (Error error) {
/*  990 */       IOException iOException = new IOException(error.getMessage());
/*  991 */       iOException.initCause(error);
/*  992 */       throw iOException;
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
/*      */   private synchronized Object inputObject(Class<?> paramClass, String paramString, CodeBase paramCodeBase, int paramInt) throws IOException, ClassNotFoundException {
/* 1007 */     this.currentClassDesc = ObjectStreamClass.lookup(paramClass);
/* 1008 */     this.currentClass = this.currentClassDesc.forClass();
/*      */     
/* 1010 */     if (this.currentClass == null)
/*      */     {
/* 1012 */       throw new ClassNotFoundException(this.currentClassDesc.getName());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1020 */       if (Enum.class.isAssignableFrom(paramClass)) {
/* 1021 */         int i = this.orbStream.read_long();
/* 1022 */         String str = (String)this.orbStream.read_value(String.class);
/* 1023 */         return Enum.valueOf((Class)paramClass, str);
/* 1024 */       }  if (this.currentClassDesc.isExternalizable()) {
/*      */         try {
/* 1026 */           this
/* 1027 */             .currentObject = (this.currentClass == null) ? null : this.currentClassDesc.newInstance();
/* 1028 */           if (this.currentObject != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1033 */             this.activeRecursionMgr.addObject(paramInt, this.currentObject);
/*      */ 
/*      */             
/* 1036 */             readFormatVersion();
/*      */             
/* 1038 */             Externalizable externalizable = (Externalizable)this.currentObject;
/* 1039 */             externalizable.readExternal(this);
/*      */           } 
/* 1041 */         } catch (InvocationTargetException invocationTargetException) {
/*      */           
/* 1043 */           InvalidClassException invalidClassException = new InvalidClassException(this.currentClass.getName(), "InvocationTargetException accessing no-arg constructor");
/*      */           
/* 1045 */           invalidClassException.initCause(invocationTargetException);
/* 1046 */           throw invalidClassException;
/* 1047 */         } catch (UnsupportedOperationException unsupportedOperationException) {
/*      */           
/* 1049 */           InvalidClassException invalidClassException = new InvalidClassException(this.currentClass.getName(), "UnsupportedOperationException accessing no-arg constructor");
/*      */           
/* 1051 */           invalidClassException.initCause(unsupportedOperationException);
/* 1052 */           throw invalidClassException;
/* 1053 */         } catch (InstantiationException instantiationException) {
/*      */           
/* 1055 */           InvalidClassException invalidClassException = new InvalidClassException(this.currentClass.getName(), "InstantiationException accessing no-arg constructor");
/*      */           
/* 1057 */           invalidClassException.initCause(instantiationException);
/* 1058 */           throw invalidClassException;
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1066 */         ObjectStreamClass objectStreamClass = this.currentClassDesc;
/* 1067 */         Class<?> clazz = this.currentClass;
/*      */         
/* 1069 */         int i = this.spClass;
/*      */         
/* 1071 */         if (this.currentClass.getName().equals("java.lang.String")) {
/* 1072 */           return readUTF();
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1103 */         objectStreamClass = this.currentClassDesc; clazz = this.currentClass;
/* 1104 */         for (; objectStreamClass != null && objectStreamClass.isSerializable(); 
/* 1105 */           objectStreamClass = objectStreamClass.getSuperclass()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1114 */           Class<?> clazz1 = objectStreamClass.forClass();
/*      */           Class<?> clazz2;
/* 1116 */           for (clazz2 = clazz; clazz2 != null && 
/* 1117 */             clazz1 != clazz2; clazz2 = clazz2.getSuperclass());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1130 */           this.spClass++;
/* 1131 */           if (this.spClass >= this.classes.length) {
/* 1132 */             int j = this.classes.length * 2;
/* 1133 */             Class[] arrayOfClass = new Class[j];
/* 1134 */             ObjectStreamClass[] arrayOfObjectStreamClass = new ObjectStreamClass[j];
/*      */             
/* 1136 */             System.arraycopy(this.classes, 0, arrayOfClass, 0, this.classes.length);
/*      */ 
/*      */             
/* 1139 */             System.arraycopy(this.classdesc, 0, arrayOfObjectStreamClass, 0, this.classes.length);
/*      */ 
/*      */ 
/*      */             
/* 1143 */             this.classes = arrayOfClass;
/* 1144 */             this.classdesc = arrayOfObjectStreamClass;
/*      */           } 
/*      */           
/* 1147 */           if (clazz2 == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1152 */             this.classdesc[this.spClass] = objectStreamClass;
/* 1153 */             this.classes[this.spClass] = null;
/*      */ 
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */             
/* 1160 */             this.classdesc[this.spClass] = objectStreamClass;
/* 1161 */             this.classes[this.spClass] = clazz2;
/* 1162 */             clazz = clazz2.getSuperclass();
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1171 */           this
/* 1172 */             .currentObject = (this.currentClass == null) ? null : this.currentClassDesc.newInstance();
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1177 */           this.activeRecursionMgr.addObject(paramInt, this.currentObject);
/* 1178 */         } catch (InvocationTargetException invocationTargetException) {
/*      */           
/* 1180 */           InvalidClassException invalidClassException = new InvalidClassException(this.currentClass.getName(), "InvocationTargetException accessing no-arg constructor");
/*      */           
/* 1182 */           invalidClassException.initCause(invocationTargetException);
/* 1183 */           throw invalidClassException;
/* 1184 */         } catch (UnsupportedOperationException unsupportedOperationException) {
/*      */           
/* 1186 */           InvalidClassException invalidClassException = new InvalidClassException(this.currentClass.getName(), "UnsupportedOperationException accessing no-arg constructor");
/*      */           
/* 1188 */           invalidClassException.initCause(unsupportedOperationException);
/* 1189 */           throw invalidClassException;
/* 1190 */         } catch (InstantiationException instantiationException) {
/*      */           
/* 1192 */           InvalidClassException invalidClassException = new InvalidClassException(this.currentClass.getName(), "InstantiationException accessing no-arg constructor");
/*      */           
/* 1194 */           invalidClassException.initCause(instantiationException);
/* 1195 */           throw invalidClassException;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1206 */           for (this.spClass = this.spClass; this.spClass > i; this.spClass--)
/*      */           {
/*      */ 
/*      */             
/* 1210 */             this.currentClassDesc = this.classdesc[this.spClass];
/* 1211 */             this.currentClass = this.classes[this.spClass];
/* 1212 */             if (this.classes[this.spClass] != null)
/*      */             {
/*      */ 
/*      */ 
/*      */               
/* 1217 */               InputStreamHook.ReadObjectState readObjectState = this.readObjectState;
/* 1218 */               setState(DEFAULT_STATE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             }
/*      */             else
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1269 */               ObjectStreamField[] arrayOfObjectStreamField = this.currentClassDesc.getFieldsNoCopy();
/* 1270 */               if (arrayOfObjectStreamField.length > 0) {
/* 1271 */                 inputClassFields((Object)null, this.currentClass, arrayOfObjectStreamField, paramCodeBase);
/*      */               }
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         } finally {
/*      */           
/* 1279 */           this.spClass = i;
/*      */         }
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     finally {
/*      */       
/* 1287 */       this.activeRecursionMgr.removeObject(paramInt);
/*      */     } 
/*      */     
/* 1290 */     return this.currentObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector getOrderedDescriptions(String paramString, CodeBase paramCodeBase) {
/* 1298 */     Vector<FullValueDescription> vector = new Vector();
/*      */     
/* 1300 */     if (paramCodeBase == null) {
/* 1301 */       return vector;
/*      */     }
/*      */     
/* 1304 */     FullValueDescription fullValueDescription = paramCodeBase.meta(paramString);
/* 1305 */     while (fullValueDescription != null) {
/* 1306 */       vector.insertElementAt(fullValueDescription, 0);
/* 1307 */       if (fullValueDescription.base_value != null && !"".equals(fullValueDescription.base_value)) {
/* 1308 */         fullValueDescription = paramCodeBase.meta(fullValueDescription.base_value); continue;
/*      */       } 
/* 1310 */       return vector;
/*      */     } 
/*      */     
/* 1313 */     return vector;
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
/*      */   private synchronized Object inputObjectUsingFVD(Class<?> paramClass, String paramString, CodeBase paramCodeBase, int paramInt) throws IOException, ClassNotFoundException {
/* 1334 */     int i = this.spClass;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1341 */       ObjectStreamClass objectStreamClass = this.currentClassDesc = ObjectStreamClass.lookup(paramClass);
/* 1342 */       Class<?> clazz = this.currentClass = paramClass;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1349 */       if (this.currentClassDesc.isExternalizable()) {
/*      */         try {
/* 1351 */           this
/* 1352 */             .currentObject = (this.currentClass == null) ? null : this.currentClassDesc.newInstance();
/* 1353 */           if (this.currentObject != null) {
/*      */ 
/*      */ 
/*      */             
/* 1357 */             this.activeRecursionMgr.addObject(paramInt, this.currentObject);
/*      */ 
/*      */             
/* 1360 */             readFormatVersion();
/*      */             
/* 1362 */             Externalizable externalizable = (Externalizable)this.currentObject;
/* 1363 */             externalizable.readExternal(this);
/*      */           } 
/* 1365 */         } catch (InvocationTargetException invocationTargetException) {
/*      */           
/* 1367 */           InvalidClassException invalidClassException = new InvalidClassException(this.currentClass.getName(), "InvocationTargetException accessing no-arg constructor");
/*      */           
/* 1369 */           invalidClassException.initCause(invocationTargetException);
/* 1370 */           throw invalidClassException;
/* 1371 */         } catch (UnsupportedOperationException unsupportedOperationException) {
/*      */           
/* 1373 */           InvalidClassException invalidClassException = new InvalidClassException(this.currentClass.getName(), "UnsupportedOperationException accessing no-arg constructor");
/*      */           
/* 1375 */           invalidClassException.initCause(unsupportedOperationException);
/* 1376 */           throw invalidClassException;
/* 1377 */         } catch (InstantiationException instantiationException) {
/*      */           
/* 1379 */           InvalidClassException invalidClassException = new InvalidClassException(this.currentClass.getName(), "InstantiationException accessing no-arg constructor");
/*      */           
/* 1381 */           invalidClassException.initCause(instantiationException);
/* 1382 */           throw invalidClassException;
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1389 */         objectStreamClass = this.currentClassDesc; clazz = this.currentClass;
/* 1390 */         for (; objectStreamClass != null && objectStreamClass.isSerializable(); 
/*      */           
/* 1392 */           objectStreamClass = objectStreamClass.getSuperclass()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1401 */           Class<?> clazz1 = objectStreamClass.forClass();
/*      */           Class<?> clazz2;
/* 1403 */           for (clazz2 = clazz; clazz2 != null && 
/* 1404 */             clazz1 != clazz2; clazz2 = clazz2.getSuperclass());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1417 */           this.spClass++;
/* 1418 */           if (this.spClass >= this.classes.length) {
/* 1419 */             int j = this.classes.length * 2;
/* 1420 */             Class[] arrayOfClass = new Class[j];
/* 1421 */             ObjectStreamClass[] arrayOfObjectStreamClass = new ObjectStreamClass[j];
/*      */             
/* 1423 */             System.arraycopy(this.classes, 0, arrayOfClass, 0, this.classes.length);
/*      */ 
/*      */             
/* 1426 */             System.arraycopy(this.classdesc, 0, arrayOfObjectStreamClass, 0, this.classes.length);
/*      */ 
/*      */ 
/*      */             
/* 1430 */             this.classes = arrayOfClass;
/* 1431 */             this.classdesc = arrayOfObjectStreamClass;
/*      */           } 
/*      */           
/* 1434 */           if (clazz2 == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1439 */             this.classdesc[this.spClass] = objectStreamClass;
/* 1440 */             this.classes[this.spClass] = null;
/*      */ 
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */             
/* 1447 */             this.classdesc[this.spClass] = objectStreamClass;
/* 1448 */             this.classes[this.spClass] = clazz2;
/* 1449 */             clazz = clazz2.getSuperclass();
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1456 */           this
/* 1457 */             .currentObject = (this.currentClass == null) ? null : this.currentClassDesc.newInstance();
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1462 */           this.activeRecursionMgr.addObject(paramInt, this.currentObject);
/* 1463 */         } catch (InvocationTargetException invocationTargetException) {
/*      */           
/* 1465 */           InvalidClassException invalidClassException = new InvalidClassException(this.currentClass.getName(), "InvocationTargetException accessing no-arg constructor");
/*      */           
/* 1467 */           invalidClassException.initCause(invocationTargetException);
/* 1468 */           throw invalidClassException;
/* 1469 */         } catch (UnsupportedOperationException unsupportedOperationException) {
/*      */           
/* 1471 */           InvalidClassException invalidClassException = new InvalidClassException(this.currentClass.getName(), "UnsupportedOperationException accessing no-arg constructor");
/*      */           
/* 1473 */           invalidClassException.initCause(unsupportedOperationException);
/* 1474 */           throw invalidClassException;
/* 1475 */         } catch (InstantiationException instantiationException) {
/*      */           
/* 1477 */           InvalidClassException invalidClassException = new InvalidClassException(this.currentClass.getName(), "InstantiationException accessing no-arg constructor");
/*      */           
/* 1479 */           invalidClassException.initCause(instantiationException);
/* 1480 */           throw invalidClassException;
/*      */         } 
/*      */         
/* 1483 */         Enumeration<FullValueDescription> enumeration = getOrderedDescriptions(paramString, paramCodeBase).elements();
/*      */         
/* 1485 */         while (enumeration.hasMoreElements() && this.spClass > i) {
/* 1486 */           FullValueDescription fullValueDescription = enumeration.nextElement();
/*      */           
/* 1488 */           String str1 = this.vhandler.getClassName(fullValueDescription.id);
/* 1489 */           String str2 = this.vhandler.getClassName(this.vhandler.getRMIRepositoryID(this.currentClass));
/*      */           
/* 1491 */           while (this.spClass > i && 
/* 1492 */             !str1.equals(str2)) {
/* 1493 */             int j = findNextClass(str1, this.classes, this.spClass, i);
/* 1494 */             if (j != -1) {
/* 1495 */               this.spClass = j;
/* 1496 */               clazz = this.currentClass = this.classes[this.spClass];
/* 1497 */               str2 = this.vhandler.getClassName(this.vhandler.getRMIRepositoryID(this.currentClass));
/*      */ 
/*      */               
/*      */               continue;
/*      */             } 
/*      */ 
/*      */             
/* 1504 */             if (fullValueDescription.is_custom) {
/*      */               
/* 1506 */               readFormatVersion();
/* 1507 */               boolean bool = readBoolean();
/*      */               
/* 1509 */               if (bool) {
/* 1510 */                 inputClassFields((Object)null, (Class)null, (ObjectStreamClass)null, fullValueDescription.members, paramCodeBase);
/*      */               }
/* 1512 */               if (getStreamFormatVersion() == 2)
/*      */               {
/* 1514 */                 ((ValueInputStream)getOrbStream()).start_value();
/* 1515 */                 ((ValueInputStream)getOrbStream()).end_value();
/*      */               
/*      */               }
/*      */ 
/*      */             
/*      */             }
/*      */             else {
/*      */ 
/*      */               
/* 1524 */               inputClassFields((Object)null, this.currentClass, (ObjectStreamClass)null, fullValueDescription.members, paramCodeBase);
/*      */             } 
/*      */             
/* 1527 */             if (enumeration.hasMoreElements()) {
/* 1528 */               fullValueDescription = enumeration.nextElement();
/* 1529 */               str1 = this.vhandler.getClassName(fullValueDescription.id); continue;
/*      */             } 
/* 1531 */             return this.currentObject;
/*      */           } 
/*      */ 
/*      */           
/* 1535 */           objectStreamClass = this.currentClassDesc = ObjectStreamClass.lookup(this.currentClass);
/*      */           
/* 1537 */           if (!str2.equals("java.lang.Object")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1544 */             InputStreamHook.ReadObjectState readObjectState = this.readObjectState;
/* 1545 */             setState(DEFAULT_STATE);
/*      */ 
/*      */             
/*      */             try {
/* 1549 */               if (fullValueDescription.is_custom) {
/*      */ 
/*      */                 
/* 1552 */                 readFormatVersion();
/*      */ 
/*      */                 
/* 1555 */                 boolean bool1 = readBoolean();
/*      */                 
/* 1557 */                 this.readObjectState.beginUnmarshalCustomValue(this, bool1, (this.currentClassDesc.readObjectMethod != null));
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1563 */               boolean bool = false;
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               try {
/* 1569 */                 if (!fullValueDescription.is_custom && this.currentClassDesc.hasReadObject()) {
/* 1570 */                   setState(IN_READ_OBJECT_REMOTE_NOT_CUSTOM_MARSHALED);
/*      */                 }
/*      */ 
/*      */ 
/*      */                 
/* 1575 */                 this.defaultReadObjectFVDMembers = fullValueDescription.members;
/* 1576 */                 bool = invokeObjectReader(this.currentClassDesc, this.currentObject, this.currentClass);
/*      */               
/*      */               }
/*      */               finally {
/*      */                 
/* 1581 */                 this.defaultReadObjectFVDMembers = null;
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1587 */               if (!bool || this.readObjectState == IN_READ_OBJECT_DEFAULTS_SENT) {
/* 1588 */                 inputClassFields(this.currentObject, this.currentClass, objectStreamClass, fullValueDescription.members, paramCodeBase);
/*      */               }
/* 1590 */               if (fullValueDescription.is_custom) {
/* 1591 */                 this.readObjectState.endUnmarshalCustomValue(this);
/*      */               }
/*      */             } finally {
/* 1594 */               setState(readObjectState);
/*      */             } 
/*      */             
/* 1597 */             clazz = this.currentClass = this.classes[--this.spClass];
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */ 
/*      */           
/* 1604 */           inputClassFields((Object)null, this.currentClass, (ObjectStreamClass)null, fullValueDescription.members, paramCodeBase);
/*      */           
/* 1606 */           while (enumeration.hasMoreElements()) {
/* 1607 */             fullValueDescription = enumeration.nextElement();
/*      */             
/* 1609 */             if (fullValueDescription.is_custom) {
/* 1610 */               skipCustomUsingFVD(fullValueDescription.members, paramCodeBase); continue;
/*      */             } 
/* 1612 */             inputClassFields((Object)null, this.currentClass, (ObjectStreamClass)null, fullValueDescription.members, paramCodeBase);
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1618 */         while (enumeration.hasMoreElements()) {
/*      */           
/* 1620 */           FullValueDescription fullValueDescription = enumeration.nextElement();
/* 1621 */           if (fullValueDescription.is_custom) {
/* 1622 */             skipCustomUsingFVD(fullValueDescription.members, paramCodeBase); continue;
/*      */           } 
/* 1624 */           throwAwayData(fullValueDescription.members, paramCodeBase);
/*      */         } 
/*      */       } 
/*      */       
/* 1628 */       return this.currentObject;
/*      */     }
/*      */     finally {
/*      */       
/* 1632 */       this.spClass = i;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1638 */       this.activeRecursionMgr.removeObject(paramInt);
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
/*      */   private Object skipObjectUsingFVD(String paramString, CodeBase paramCodeBase) throws IOException, ClassNotFoundException {
/* 1658 */     Enumeration<FullValueDescription> enumeration = getOrderedDescriptions(paramString, paramCodeBase).elements();
/*      */     
/* 1660 */     while (enumeration.hasMoreElements()) {
/* 1661 */       FullValueDescription fullValueDescription = enumeration.nextElement();
/* 1662 */       String str = this.vhandler.getClassName(fullValueDescription.id);
/*      */       
/* 1664 */       if (!str.equals("java.lang.Object")) {
/* 1665 */         if (fullValueDescription.is_custom) {
/*      */           
/* 1667 */           readFormatVersion();
/*      */           
/* 1669 */           boolean bool = readBoolean();
/*      */           
/* 1671 */           if (bool) {
/* 1672 */             inputClassFields((Object)null, (Class)null, (ObjectStreamClass)null, fullValueDescription.members, paramCodeBase);
/*      */           }
/* 1674 */           if (getStreamFormatVersion() == 2) {
/*      */             
/* 1676 */             ((ValueInputStream)getOrbStream()).start_value();
/* 1677 */             ((ValueInputStream)getOrbStream()).end_value();
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */ 
/*      */         
/* 1686 */         inputClassFields((Object)null, (Class)null, (ObjectStreamClass)null, fullValueDescription.members, paramCodeBase);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1691 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int findNextClass(String paramString, Class[] paramArrayOfClass, int paramInt1, int paramInt2) {
/* 1699 */     for (int i = paramInt1; i > paramInt2; i--) {
/* 1700 */       if (paramString.equals(paramArrayOfClass[i].getName())) {
/* 1701 */         return i;
/*      */       }
/*      */     } 
/*      */     
/* 1705 */     return -1;
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
/*      */   private boolean invokeObjectReader(ObjectStreamClass paramObjectStreamClass, Object paramObject, Class paramClass) throws InvalidClassException, StreamCorruptedException, ClassNotFoundException, IOException {
/* 1717 */     if (paramObjectStreamClass.readObjectMethod == null) {
/* 1718 */       return false;
/*      */     }
/*      */     
/*      */     try {
/* 1722 */       paramObjectStreamClass.readObjectMethod.invoke(paramObject, this.readObjectArgList);
/* 1723 */       return true;
/* 1724 */     } catch (InvocationTargetException invocationTargetException) {
/* 1725 */       Throwable throwable = invocationTargetException.getTargetException();
/* 1726 */       if (throwable instanceof ClassNotFoundException)
/* 1727 */         throw (ClassNotFoundException)throwable; 
/* 1728 */       if (throwable instanceof IOException)
/* 1729 */         throw (IOException)throwable; 
/* 1730 */       if (throwable instanceof RuntimeException)
/* 1731 */         throw (RuntimeException)throwable; 
/* 1732 */       if (throwable instanceof Error) {
/* 1733 */         throw (Error)throwable;
/*      */       }
/*      */       
/* 1736 */       throw new Error("internal error");
/* 1737 */     } catch (IllegalAccessException illegalAccessException) {
/* 1738 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resetStream() throws IOException {
/* 1747 */     if (this.classes == null) {
/* 1748 */       this.classes = new Class[20];
/*      */     } else {
/* 1750 */       for (byte b = 0; b < this.classes.length; b++)
/* 1751 */         this.classes[b] = null; 
/*      */     } 
/* 1753 */     if (this.classdesc == null) {
/* 1754 */       this.classdesc = new ObjectStreamClass[20];
/*      */     } else {
/* 1756 */       for (byte b = 0; b < this.classdesc.length; b++)
/* 1757 */         this.classdesc[b] = null; 
/*      */     } 
/* 1759 */     this.spClass = 0;
/*      */     
/* 1761 */     if (this.callbacks != null) {
/* 1762 */       this.callbacks.setSize(0);
/*      */     }
/*      */   }
/*      */   
/*      */   private void inputPrimitiveField(Object paramObject, Class paramClass, ObjectStreamField paramObjectStreamField) throws InvalidClassException, IOException {
/*      */     try {
/*      */       byte b;
/*      */       boolean bool;
/*      */       char c;
/*      */       short s;
/*      */       int i;
/*      */       long l;
/*      */       float f;
/*      */       double d;
/* 1776 */       switch (paramObjectStreamField.getTypeCode()) {
/*      */         case 'B':
/* 1778 */           b = this.orbStream.read_octet();
/* 1779 */           if (paramObjectStreamField.getField() != null) {
/* 1780 */             bridge.putByte(paramObject, paramObjectStreamField.getFieldID(), b);
/*      */           }
/*      */           return;
/*      */         
/*      */         case 'Z':
/* 1785 */           bool = this.orbStream.read_boolean();
/* 1786 */           if (paramObjectStreamField.getField() != null) {
/* 1787 */             bridge.putBoolean(paramObject, paramObjectStreamField.getFieldID(), bool);
/*      */           }
/*      */           return;
/*      */         
/*      */         case 'C':
/* 1792 */           c = this.orbStream.read_wchar();
/* 1793 */           if (paramObjectStreamField.getField() != null) {
/* 1794 */             bridge.putChar(paramObject, paramObjectStreamField.getFieldID(), c);
/*      */           }
/*      */           return;
/*      */         
/*      */         case 'S':
/* 1799 */           s = this.orbStream.read_short();
/* 1800 */           if (paramObjectStreamField.getField() != null) {
/* 1801 */             bridge.putShort(paramObject, paramObjectStreamField.getFieldID(), s);
/*      */           }
/*      */           return;
/*      */         
/*      */         case 'I':
/* 1806 */           i = this.orbStream.read_long();
/* 1807 */           if (paramObjectStreamField.getField() != null) {
/* 1808 */             bridge.putInt(paramObject, paramObjectStreamField.getFieldID(), i);
/*      */           }
/*      */           return;
/*      */         
/*      */         case 'J':
/* 1813 */           l = this.orbStream.read_longlong();
/* 1814 */           if (paramObjectStreamField.getField() != null) {
/* 1815 */             bridge.putLong(paramObject, paramObjectStreamField.getFieldID(), l);
/*      */           }
/*      */           return;
/*      */         
/*      */         case 'F':
/* 1820 */           f = this.orbStream.read_float();
/* 1821 */           if (paramObjectStreamField.getField() != null) {
/* 1822 */             bridge.putFloat(paramObject, paramObjectStreamField.getFieldID(), f);
/*      */           }
/*      */           return;
/*      */         
/*      */         case 'D':
/* 1827 */           d = this.orbStream.read_double();
/* 1828 */           if (paramObjectStreamField.getField() != null) {
/* 1829 */             bridge.putDouble(paramObject, paramObjectStreamField.getFieldID(), d);
/*      */           }
/*      */           return;
/*      */       } 
/*      */ 
/*      */       
/* 1835 */       throw new InvalidClassException(paramClass.getName());
/*      */     }
/* 1837 */     catch (IllegalArgumentException illegalArgumentException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1845 */       ClassCastException classCastException = new ClassCastException("Assigning instance of class " + paramObjectStreamField.getType().getName() + " to field " + this.currentClassDesc.getName() + '#' + paramObjectStreamField.getField().getName());
/* 1846 */       classCastException.initCause(illegalArgumentException);
/* 1847 */       throw classCastException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object inputObjectField(ValueMember paramValueMember, CodeBase paramCodeBase) throws IndirectionException, ClassNotFoundException, IOException, StreamCorruptedException {
/* 1856 */     Object object = null;
/* 1857 */     Class clazz = null;
/* 1858 */     String str1 = paramValueMember.id;
/*      */     
/*      */     try {
/* 1861 */       clazz = this.vhandler.getClassFromType(str1);
/* 1862 */     } catch (ClassNotFoundException classNotFoundException) {
/*      */       
/* 1864 */       clazz = null;
/*      */     } 
/*      */     
/* 1867 */     String str2 = null;
/* 1868 */     if (clazz != null) {
/* 1869 */       str2 = ValueUtility.getSignature(paramValueMember);
/*      */     }
/* 1871 */     if (str2 != null && (str2.equals("Ljava/lang/Object;") || str2
/* 1872 */       .equals("Ljava/io/Serializable;") || str2
/* 1873 */       .equals("Ljava/io/Externalizable;")))
/* 1874 */     { object = Util.readAny((InputStream)this.orbStream);
/*      */ 
/*      */       
/*      */        }
/*      */     
/*      */     else
/*      */     
/*      */     { 
/*      */ 
/*      */       
/* 1884 */       byte b = 2;
/*      */       
/* 1886 */       if (!this.vhandler.isSequence(str1))
/*      */       {
/* 1888 */         if (paramValueMember.type.kind().value() == kRemoteTypeCode.kind().value()) {
/*      */ 
/*      */           
/* 1891 */           b = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/* 1908 */         else if (clazz != null && clazz.isInterface() && (this.vhandler
/* 1909 */           .isAbstractBase(clazz) || 
/* 1910 */           ObjectStreamClassCorbaExt.isAbstractInterface(clazz))) {
/*      */           
/* 1912 */           b = 1;
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1921 */       switch (b)
/*      */       { case 0:
/* 1923 */           if (clazz != null) {
/* 1924 */             object = Utility.readObjectAndNarrow((InputStream)this.orbStream, clazz);
/*      */           } else {
/* 1926 */             object = this.orbStream.read_Object();
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1946 */           return object;case 1: if (clazz != null) { object = Utility.readAbstractAndNarrow(this.orbStream, clazz); } else { object = this.orbStream.read_abstract_interface(); }  return object;case 2: if (clazz != null) { object = this.orbStream.read_value(clazz); } else { object = this.orbStream.read_value(); }  return object; }  throw new StreamCorruptedException("Unknown callType: " + b); }  return object;
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
/*      */   private Object inputObjectField(ObjectStreamField paramObjectStreamField) throws InvalidClassException, StreamCorruptedException, ClassNotFoundException, IndirectionException, IOException {
/* 1960 */     if (ObjectStreamClassCorbaExt.isAny(paramObjectStreamField.getTypeString())) {
/* 1961 */       return Util.readAny((InputStream)this.orbStream);
/*      */     }
/*      */     
/* 1964 */     Object object = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1969 */     Class<?> clazz1 = paramObjectStreamField.getType();
/* 1970 */     Class<?> clazz2 = clazz1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1976 */     byte b = 2;
/* 1977 */     boolean bool = false;
/*      */     
/* 1979 */     if (clazz1.isInterface()) {
/* 1980 */       boolean bool1 = false;
/*      */       
/* 1982 */       if (Remote.class.isAssignableFrom(clazz1)) {
/*      */ 
/*      */         
/* 1985 */         b = 0;
/*      */       }
/* 1987 */       else if (Object.class.isAssignableFrom(clazz1)) {
/*      */ 
/*      */         
/* 1990 */         b = 0;
/* 1991 */         bool1 = true;
/*      */       }
/* 1993 */       else if (this.vhandler.isAbstractBase(clazz1)) {
/*      */ 
/*      */         
/* 1996 */         b = 1;
/* 1997 */         bool1 = true;
/* 1998 */       } else if (ObjectStreamClassCorbaExt.isAbstractInterface(clazz1)) {
/*      */ 
/*      */         
/* 2001 */         b = 1;
/*      */       } 
/*      */       
/* 2004 */       if (bool1) {
/*      */         try {
/* 2006 */           String str1 = Util.getCodebase(clazz1);
/* 2007 */           String str2 = this.vhandler.createForAnyType(clazz1);
/*      */           
/* 2009 */           Class<?> clazz = Utility.loadStubClass(str2, str1, clazz1);
/* 2010 */           clazz2 = clazz;
/* 2011 */         } catch (ClassNotFoundException classNotFoundException) {
/* 2012 */           bool = true;
/*      */         } 
/*      */       } else {
/* 2015 */         bool = true;
/*      */       } 
/*      */     } 
/*      */     
/* 2019 */     switch (b) {
/*      */       case 0:
/* 2021 */         if (!bool) {
/* 2022 */           object = this.orbStream.read_Object(clazz2);
/*      */         } else {
/* 2024 */           object = Utility.readObjectAndNarrow((InputStream)this.orbStream, clazz2);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2040 */         return object;case 1: if (!bool) { object = this.orbStream.read_abstract_interface(clazz2); } else { object = Utility.readAbstractAndNarrow(this.orbStream, clazz2); }  return object;case 2: object = this.orbStream.read_value(clazz2); return object;
/*      */     } 
/*      */     throw new StreamCorruptedException("Unknown callType: " + b);
/*      */   } private final boolean mustUseRemoteValueMembers() {
/* 2044 */     return (this.defaultReadObjectFVDMembers != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void readFields(Map paramMap) throws InvalidClassException, StreamCorruptedException, ClassNotFoundException, IOException {
/* 2051 */     if (mustUseRemoteValueMembers()) {
/* 2052 */       inputRemoteMembersForReadFields(paramMap);
/*      */     } else {
/* 2054 */       inputCurrentClassFieldsForReadFields(paramMap);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void inputRemoteMembersForReadFields(Map<String, Byte> paramMap) throws InvalidClassException, StreamCorruptedException, ClassNotFoundException, IOException {
/* 2063 */     ValueMember[] arrayOfValueMember = this.defaultReadObjectFVDMembers;
/*      */ 
/*      */     
/*      */     try {
/* 2067 */       for (byte b = 0; b < arrayOfValueMember.length; b++) {
/*      */         byte b1; boolean bool; char c; short s; int i; long l; float f; double d; Object object;
/* 2069 */         switch ((arrayOfValueMember[b]).type.kind().value()) {
/*      */           
/*      */           case 10:
/* 2072 */             b1 = this.orbStream.read_octet();
/* 2073 */             paramMap.put((arrayOfValueMember[b]).name, new Byte(b1));
/*      */             break;
/*      */           case 8:
/* 2076 */             bool = this.orbStream.read_boolean();
/* 2077 */             paramMap.put((arrayOfValueMember[b]).name, new Boolean(bool));
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 9:
/*      */           case 26:
/* 2086 */             c = this.orbStream.read_wchar();
/* 2087 */             paramMap.put((arrayOfValueMember[b]).name, new Character(c));
/*      */             break;
/*      */           case 2:
/* 2090 */             s = this.orbStream.read_short();
/* 2091 */             paramMap.put((arrayOfValueMember[b]).name, new Short(s));
/*      */             break;
/*      */           case 3:
/* 2094 */             i = this.orbStream.read_long();
/* 2095 */             paramMap.put((arrayOfValueMember[b]).name, new Integer(i));
/*      */             break;
/*      */           case 23:
/* 2098 */             l = this.orbStream.read_longlong();
/* 2099 */             paramMap.put((arrayOfValueMember[b]).name, new Long(l));
/*      */             break;
/*      */           case 6:
/* 2102 */             f = this.orbStream.read_float();
/* 2103 */             paramMap.put((arrayOfValueMember[b]).name, new Float(f));
/*      */             break;
/*      */           case 7:
/* 2106 */             d = this.orbStream.read_double();
/* 2107 */             paramMap.put((arrayOfValueMember[b]).name, new Double(d));
/*      */             break;
/*      */           case 14:
/*      */           case 29:
/*      */           case 30:
/* 2112 */             object = null;
/*      */             try {
/* 2114 */               object = inputObjectField(arrayOfValueMember[b], this.cbSender);
/*      */             
/*      */             }
/* 2117 */             catch (IndirectionException indirectionException) {
/*      */ 
/*      */ 
/*      */               
/* 2121 */               object = this.activeRecursionMgr.getObject(indirectionException.offset);
/*      */             } 
/*      */             
/* 2124 */             paramMap.put((arrayOfValueMember[b]).name, object);
/*      */             break;
/*      */           
/*      */           default:
/* 2128 */             throw new StreamCorruptedException("Unknown kind: " + (arrayOfValueMember[b]).type
/* 2129 */                 .kind().value());
/*      */         } 
/*      */       } 
/* 2132 */     } catch (Throwable throwable) {
/* 2133 */       StreamCorruptedException streamCorruptedException = new StreamCorruptedException(throwable.getMessage());
/* 2134 */       streamCorruptedException.initCause(throwable);
/* 2135 */       throw streamCorruptedException;
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
/*      */   private final void inputCurrentClassFieldsForReadFields(Map<String, Byte> paramMap) throws InvalidClassException, StreamCorruptedException, ClassNotFoundException, IOException {
/* 2151 */     ObjectStreamField[] arrayOfObjectStreamField = this.currentClassDesc.getFieldsNoCopy();
/*      */     
/* 2153 */     int i = arrayOfObjectStreamField.length - this.currentClassDesc.objFields;
/*      */     
/*      */     int j;
/* 2156 */     for (j = 0; j < i; j++) {
/*      */       byte b; boolean bool; char c; short s; int k; long l; float f; double d;
/* 2158 */       switch (arrayOfObjectStreamField[j].getTypeCode()) {
/*      */         case 'B':
/* 2160 */           b = this.orbStream.read_octet();
/* 2161 */           paramMap.put(arrayOfObjectStreamField[j].getName(), new Byte(b));
/*      */           break;
/*      */         
/*      */         case 'Z':
/* 2165 */           bool = this.orbStream.read_boolean();
/* 2166 */           paramMap.put(arrayOfObjectStreamField[j].getName(), new Boolean(bool));
/*      */           break;
/*      */         
/*      */         case 'C':
/* 2170 */           c = this.orbStream.read_wchar();
/* 2171 */           paramMap.put(arrayOfObjectStreamField[j].getName(), new Character(c));
/*      */           break;
/*      */         
/*      */         case 'S':
/* 2175 */           s = this.orbStream.read_short();
/* 2176 */           paramMap.put(arrayOfObjectStreamField[j].getName(), new Short(s));
/*      */           break;
/*      */         
/*      */         case 'I':
/* 2180 */           k = this.orbStream.read_long();
/* 2181 */           paramMap.put(arrayOfObjectStreamField[j].getName(), new Integer(k));
/*      */           break;
/*      */         
/*      */         case 'J':
/* 2185 */           l = this.orbStream.read_longlong();
/* 2186 */           paramMap.put(arrayOfObjectStreamField[j].getName(), new Long(l));
/*      */           break;
/*      */         
/*      */         case 'F':
/* 2190 */           f = this.orbStream.read_float();
/* 2191 */           paramMap.put(arrayOfObjectStreamField[j].getName(), new Float(f));
/*      */           break;
/*      */         
/*      */         case 'D':
/* 2195 */           d = this.orbStream.read_double();
/* 2196 */           paramMap.put(arrayOfObjectStreamField[j].getName(), new Double(d));
/*      */           break;
/*      */ 
/*      */         
/*      */         default:
/* 2201 */           throw new InvalidClassException(this.currentClassDesc.getName());
/*      */       } 
/*      */ 
/*      */     
/*      */     } 
/* 2206 */     if (this.currentClassDesc.objFields > 0) {
/* 2207 */       for (j = i; j < arrayOfObjectStreamField.length; j++) {
/* 2208 */         Object object = null;
/*      */         try {
/* 2210 */           object = inputObjectField(arrayOfObjectStreamField[j]);
/* 2211 */         } catch (IndirectionException indirectionException) {
/*      */ 
/*      */ 
/*      */           
/* 2215 */           object = this.activeRecursionMgr.getObject(indirectionException.offset);
/*      */         } 
/*      */         
/* 2218 */         paramMap.put(arrayOfObjectStreamField[j].getName(), object);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void inputClassFields(Object paramObject, Class<?> paramClass, ObjectStreamField[] paramArrayOfObjectStreamField, CodeBase paramCodeBase) throws InvalidClassException, StreamCorruptedException, ClassNotFoundException, IOException {
/* 2240 */     int i = paramArrayOfObjectStreamField.length - this.currentClassDesc.objFields;
/*      */     
/* 2242 */     if (paramObject != null) {
/* 2243 */       for (byte b = 0; b < i; b++) {
/* 2244 */         inputPrimitiveField(paramObject, paramClass, paramArrayOfObjectStreamField[b]);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/* 2249 */     if (this.currentClassDesc.objFields > 0) {
/* 2250 */       for (int j = i; j < paramArrayOfObjectStreamField.length; j++) {
/* 2251 */         Object object = null;
/*      */         
/*      */         try {
/* 2254 */           object = inputObjectField(paramArrayOfObjectStreamField[j]);
/* 2255 */         } catch (IndirectionException indirectionException) {
/*      */ 
/*      */ 
/*      */           
/* 2259 */           object = this.activeRecursionMgr.getObject(indirectionException.offset);
/*      */         } 
/*      */         
/* 2262 */         if (paramObject != null && paramArrayOfObjectStreamField[j].getField() != null) {
/*      */           
/*      */           try {
/*      */ 
/*      */             
/* 2267 */             Class<?> clazz = paramArrayOfObjectStreamField[j].getClazz();
/* 2268 */             if (object != null && 
/* 2269 */               !clazz.isAssignableFrom(object
/* 2270 */                 .getClass())) {
/* 2271 */               throw new IllegalArgumentException("Field mismatch");
/*      */             }
/* 2273 */             Field field = null;
/* 2274 */             String str = paramArrayOfObjectStreamField[j].getName();
/*      */             try {
/* 2276 */               field = getDeclaredField(paramClass, str);
/* 2277 */             } catch (PrivilegedActionException privilegedActionException) {
/* 2278 */               throw new IllegalArgumentException((NoSuchFieldException)privilegedActionException
/* 2279 */                   .getException());
/* 2280 */             } catch (SecurityException securityException) {
/* 2281 */               throw new IllegalArgumentException(securityException);
/* 2282 */             } catch (NullPointerException nullPointerException) {
/*      */             
/* 2284 */             } catch (NoSuchFieldException noSuchFieldException) {}
/*      */ 
/*      */ 
/*      */             
/* 2288 */             if (field != null) {
/*      */ 
/*      */               
/* 2291 */               Class<?> clazz1 = field.getType();
/*      */ 
/*      */ 
/*      */               
/* 2295 */               if (!clazz1.isAssignableFrom(clazz)) {
/* 2296 */                 throw new IllegalArgumentException("Field Type mismatch");
/*      */               }
/*      */               
/* 2299 */               if (object != null && !clazz.isInstance(object)) {
/* 2300 */                 throw new IllegalArgumentException();
/*      */               }
/* 2302 */               bridge.putObject(paramObject, paramArrayOfObjectStreamField[j].getFieldID(), object);
/*      */             } 
/* 2304 */           } catch (IllegalArgumentException illegalArgumentException) {
/* 2305 */             String str1 = "null";
/* 2306 */             String str2 = "null";
/* 2307 */             String str3 = "null";
/* 2308 */             if (object != null) {
/* 2309 */               str1 = object.getClass().getName();
/*      */             }
/* 2311 */             if (this.currentClassDesc != null) {
/* 2312 */               str2 = this.currentClassDesc.getName();
/*      */             }
/* 2314 */             if (paramArrayOfObjectStreamField[j] != null && paramArrayOfObjectStreamField[j].getField() != null) {
/* 2315 */               str3 = paramArrayOfObjectStreamField[j].getField().getName();
/*      */             }
/* 2317 */             ClassCastException classCastException = new ClassCastException("Assigning instance of class " + str1 + " to field " + str2 + '#' + str3);
/*      */ 
/*      */             
/* 2320 */             classCastException.initCause(illegalArgumentException);
/* 2321 */             throw classCastException;
/*      */           } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void inputClassFields(Object paramObject, Class<?> paramClass, ObjectStreamClass paramObjectStreamClass, ValueMember[] paramArrayOfValueMember, CodeBase paramCodeBase) throws InvalidClassException, StreamCorruptedException, ClassNotFoundException, IOException {
/*      */     try {
/* 2342 */       for (byte b = 0; b < paramArrayOfValueMember.length; b++) {
/*      */         try {
/* 2344 */           byte b1; boolean bool; char c; short s; int i; long l; float f; double d; Object object; switch ((paramArrayOfValueMember[b]).type.kind().value()) {
/*      */             case 10:
/* 2346 */               b1 = this.orbStream.read_octet();
/* 2347 */               if (paramObject != null && paramObjectStreamClass.hasField(paramArrayOfValueMember[b]))
/* 2348 */                 setByteField(paramObject, paramClass, (paramArrayOfValueMember[b]).name, b1); 
/*      */               break;
/*      */             case 8:
/* 2351 */               bool = this.orbStream.read_boolean();
/* 2352 */               if (paramObject != null && paramObjectStreamClass.hasField(paramArrayOfValueMember[b])) {
/* 2353 */                 setBooleanField(paramObject, paramClass, (paramArrayOfValueMember[b]).name, bool);
/*      */               }
/*      */               break;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             case 9:
/*      */             case 26:
/* 2362 */               c = this.orbStream.read_wchar();
/* 2363 */               if (paramObject != null && paramObjectStreamClass.hasField(paramArrayOfValueMember[b]))
/* 2364 */                 setCharField(paramObject, paramClass, (paramArrayOfValueMember[b]).name, c); 
/*      */               break;
/*      */             case 2:
/* 2367 */               s = this.orbStream.read_short();
/* 2368 */               if (paramObject != null && paramObjectStreamClass.hasField(paramArrayOfValueMember[b]))
/* 2369 */                 setShortField(paramObject, paramClass, (paramArrayOfValueMember[b]).name, s); 
/*      */               break;
/*      */             case 3:
/* 2372 */               i = this.orbStream.read_long();
/* 2373 */               if (paramObject != null && paramObjectStreamClass.hasField(paramArrayOfValueMember[b]))
/* 2374 */                 setIntField(paramObject, paramClass, (paramArrayOfValueMember[b]).name, i); 
/*      */               break;
/*      */             case 23:
/* 2377 */               l = this.orbStream.read_longlong();
/* 2378 */               if (paramObject != null && paramObjectStreamClass.hasField(paramArrayOfValueMember[b]))
/* 2379 */                 setLongField(paramObject, paramClass, (paramArrayOfValueMember[b]).name, l); 
/*      */               break;
/*      */             case 6:
/* 2382 */               f = this.orbStream.read_float();
/* 2383 */               if (paramObject != null && paramObjectStreamClass.hasField(paramArrayOfValueMember[b]))
/* 2384 */                 setFloatField(paramObject, paramClass, (paramArrayOfValueMember[b]).name, f); 
/*      */               break;
/*      */             case 7:
/* 2387 */               d = this.orbStream.read_double();
/* 2388 */               if (paramObject != null && paramObjectStreamClass.hasField(paramArrayOfValueMember[b]))
/* 2389 */                 setDoubleField(paramObject, paramClass, (paramArrayOfValueMember[b]).name, d); 
/*      */               break;
/*      */             case 14:
/*      */             case 29:
/*      */             case 30:
/* 2394 */               object = null;
/*      */               try {
/* 2396 */                 object = inputObjectField(paramArrayOfValueMember[b], paramCodeBase);
/* 2397 */               } catch (IndirectionException indirectionException) {
/*      */ 
/*      */ 
/*      */                 
/* 2401 */                 object = this.activeRecursionMgr.getObject(indirectionException.offset);
/*      */               } 
/*      */               
/* 2404 */               if (paramObject == null)
/*      */                 break; 
/*      */               try {
/* 2407 */                 if (paramObjectStreamClass.hasField(paramArrayOfValueMember[b])) {
/* 2408 */                   setObjectField(paramObject, paramClass, (paramArrayOfValueMember[b]).name, object);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*      */                 }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               }
/* 2424 */               catch (IllegalArgumentException illegalArgumentException) {
/*      */ 
/*      */                 
/* 2427 */                 ClassCastException classCastException = new ClassCastException("Assigning instance of class " + object.getClass().getName() + " to field " + (paramArrayOfValueMember[b]).name);
/* 2428 */                 classCastException.initCause(illegalArgumentException);
/* 2429 */                 throw classCastException;
/*      */               } 
/*      */               break;
/*      */             
/*      */             default:
/* 2434 */               throw new StreamCorruptedException("Unknown kind: " + (paramArrayOfValueMember[b]).type
/* 2435 */                   .kind().value());
/*      */           } 
/* 2437 */         } catch (IllegalArgumentException illegalArgumentException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2443 */           ClassCastException classCastException = new ClassCastException("Assigning instance of class " + (paramArrayOfValueMember[b]).id + " to field " + this.currentClassDesc.getName() + '#' + (paramArrayOfValueMember[b]).name);
/* 2444 */           classCastException.initCause(illegalArgumentException);
/* 2445 */           throw classCastException;
/*      */         } 
/*      */       } 
/* 2448 */     } catch (Throwable throwable) {
/*      */       
/* 2450 */       StreamCorruptedException streamCorruptedException = new StreamCorruptedException(throwable.getMessage());
/* 2451 */       streamCorruptedException.initCause(throwable);
/* 2452 */       throw streamCorruptedException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void skipCustomUsingFVD(ValueMember[] paramArrayOfValueMember, CodeBase paramCodeBase) throws InvalidClassException, StreamCorruptedException, ClassNotFoundException, IOException {
/* 2461 */     readFormatVersion();
/* 2462 */     boolean bool = readBoolean();
/*      */     
/* 2464 */     if (bool) {
/* 2465 */       throwAwayData(paramArrayOfValueMember, paramCodeBase);
/*      */     }
/* 2467 */     if (getStreamFormatVersion() == 2) {
/*      */       
/* 2469 */       ((ValueInputStream)getOrbStream()).start_value();
/* 2470 */       ((ValueInputStream)getOrbStream()).end_value();
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
/*      */   private void throwAwayData(ValueMember[] paramArrayOfValueMember, CodeBase paramCodeBase) throws InvalidClassException, StreamCorruptedException, ClassNotFoundException, IOException {
/* 2483 */     for (byte b = 0; b < paramArrayOfValueMember.length; b++) {
/*      */       try {
/*      */         Class clazz; String str1;
/*      */         String str2;
/* 2487 */         switch ((paramArrayOfValueMember[b]).type.kind().value()) {
/*      */           case 10:
/* 2489 */             this.orbStream.read_octet();
/*      */             break;
/*      */           case 8:
/* 2492 */             this.orbStream.read_boolean();
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 9:
/*      */           case 26:
/* 2501 */             this.orbStream.read_wchar();
/*      */             break;
/*      */           case 2:
/* 2504 */             this.orbStream.read_short();
/*      */             break;
/*      */           case 3:
/* 2507 */             this.orbStream.read_long();
/*      */             break;
/*      */           case 23:
/* 2510 */             this.orbStream.read_longlong();
/*      */             break;
/*      */           case 6:
/* 2513 */             this.orbStream.read_float();
/*      */             break;
/*      */           case 7:
/* 2516 */             this.orbStream.read_double();
/*      */             break;
/*      */           case 14:
/*      */           case 29:
/*      */           case 30:
/* 2521 */             clazz = null;
/* 2522 */             str1 = (paramArrayOfValueMember[b]).id;
/*      */             
/*      */             try {
/* 2525 */               clazz = this.vhandler.getClassFromType(str1);
/*      */             }
/* 2527 */             catch (ClassNotFoundException classNotFoundException) {
/*      */               
/* 2529 */               clazz = null;
/*      */             } 
/* 2531 */             str2 = null;
/* 2532 */             if (clazz != null) {
/* 2533 */               str2 = ValueUtility.getSignature(paramArrayOfValueMember[b]);
/*      */             }
/*      */             
/*      */             try {
/* 2537 */               if (str2 != null && (str2.equals("Ljava/lang/Object;") || str2
/* 2538 */                 .equals("Ljava/io/Serializable;") || str2
/* 2539 */                 .equals("Ljava/io/Externalizable;"))) {
/* 2540 */                 Util.readAny((InputStream)this.orbStream);
/*      */ 
/*      */ 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */ 
/*      */ 
/*      */               
/* 2549 */               byte b1 = 2;
/*      */               
/* 2551 */               if (!this.vhandler.isSequence(str1)) {
/* 2552 */                 FullValueDescription fullValueDescription = paramCodeBase.meta((paramArrayOfValueMember[b]).id);
/* 2553 */                 if (kRemoteTypeCode == (paramArrayOfValueMember[b]).type) {
/*      */ 
/*      */                   
/* 2556 */                   b1 = 0;
/* 2557 */                 } else if (fullValueDescription.is_abstract) {
/*      */ 
/*      */                   
/* 2560 */                   b1 = 1;
/*      */                 } 
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2568 */               switch (b1) {
/*      */                 case 0:
/* 2570 */                   this.orbStream.read_Object();
/*      */                   break;
/*      */                 case 1:
/* 2573 */                   this.orbStream.read_abstract_interface();
/*      */                   break;
/*      */                 case 2:
/* 2576 */                   if (clazz != null) {
/* 2577 */                     this.orbStream.read_value(clazz); break;
/*      */                   } 
/* 2579 */                   this.orbStream.read_value();
/*      */                   break;
/*      */               } 
/*      */ 
/*      */               
/* 2584 */               throw new StreamCorruptedException("Unknown callType: " + b1);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             }
/* 2590 */             catch (IndirectionException indirectionException) {
/*      */               break;
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           default:
/* 2598 */             throw new StreamCorruptedException("Unknown kind: " + (paramArrayOfValueMember[b]).type
/* 2599 */                 .kind().value());
/*      */         } 
/*      */       
/* 2602 */       } catch (IllegalArgumentException illegalArgumentException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2608 */         ClassCastException classCastException = new ClassCastException("Assigning instance of class " + (paramArrayOfValueMember[b]).id + " to field " + this.currentClassDesc.getName() + '#' + (paramArrayOfValueMember[b]).name);
/*      */         
/* 2610 */         classCastException.initCause(illegalArgumentException);
/* 2611 */         throw classCastException;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setObjectField(Object paramObject1, Class<?> paramClass, String paramString, Object paramObject2) {
/*      */     try {
/* 2619 */       Field field = getDeclaredField(paramClass, paramString);
/* 2620 */       Class<?> clazz = field.getType();
/* 2621 */       if (paramObject2 != null && !clazz.isInstance(paramObject2)) {
/* 2622 */         throw new Exception();
/*      */       }
/* 2624 */       long l = bridge.objectFieldOffset(field);
/* 2625 */       bridge.putObject(paramObject1, l, paramObject2);
/* 2626 */     } catch (Exception exception) {
/* 2627 */       if (paramObject1 != null) {
/* 2628 */         throw utilWrapper.errorSetObjectField(exception, paramString, paramObject1
/* 2629 */             .toString(), paramObject2
/* 2630 */             .toString());
/*      */       }
/* 2632 */       throw utilWrapper.errorSetObjectField(exception, paramString, "null " + paramClass
/* 2633 */           .getName() + " object", paramObject2
/* 2634 */           .toString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setBooleanField(Object paramObject, Class<?> paramClass, String paramString, boolean paramBoolean) {
/*      */     try {
/* 2642 */       Field field = getDeclaredField(paramClass, paramString);
/* 2643 */       if (field != null && field.getType() == boolean.class) {
/* 2644 */         long l = bridge.objectFieldOffset(field);
/* 2645 */         bridge.putBoolean(paramObject, l, paramBoolean);
/*      */       } else {
/* 2647 */         throw new InvalidObjectException("Field Type mismatch");
/*      */       } 
/* 2649 */     } catch (Exception exception) {
/* 2650 */       if (paramObject != null) {
/* 2651 */         throw utilWrapper.errorSetBooleanField(exception, paramString, paramObject
/* 2652 */             .toString(), new Boolean(paramBoolean));
/*      */       }
/*      */       
/* 2655 */       throw utilWrapper.errorSetBooleanField(exception, paramString, "null " + paramClass
/* 2656 */           .getName() + " object", new Boolean(paramBoolean));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setByteField(Object paramObject, Class<?> paramClass, String paramString, byte paramByte) {
/*      */     try {
/* 2665 */       Field field = getDeclaredField(paramClass, paramString);
/* 2666 */       if (field != null && field.getType() == byte.class) {
/* 2667 */         long l = bridge.objectFieldOffset(field);
/* 2668 */         bridge.putByte(paramObject, l, paramByte);
/*      */       } else {
/* 2670 */         throw new InvalidObjectException("Field Type mismatch");
/*      */       } 
/* 2672 */     } catch (Exception exception) {
/* 2673 */       if (paramObject != null) {
/* 2674 */         throw utilWrapper.errorSetByteField(exception, paramString, paramObject
/* 2675 */             .toString(), new Byte(paramByte));
/*      */       }
/*      */       
/* 2678 */       throw utilWrapper.errorSetByteField(exception, paramString, "null " + paramClass
/* 2679 */           .getName() + " object", new Byte(paramByte));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setCharField(Object paramObject, Class<?> paramClass, String paramString, char paramChar) {
/*      */     try {
/* 2688 */       Field field = getDeclaredField(paramClass, paramString);
/* 2689 */       if (field != null && field.getType() == char.class) {
/* 2690 */         long l = bridge.objectFieldOffset(field);
/* 2691 */         bridge.putChar(paramObject, l, paramChar);
/*      */       } else {
/* 2693 */         throw new InvalidObjectException("Field Type mismatch");
/*      */       } 
/* 2695 */     } catch (Exception exception) {
/* 2696 */       if (paramObject != null) {
/* 2697 */         throw utilWrapper.errorSetCharField(exception, paramString, paramObject
/* 2698 */             .toString(), new Character(paramChar));
/*      */       }
/*      */       
/* 2701 */       throw utilWrapper.errorSetCharField(exception, paramString, "null " + paramClass
/* 2702 */           .getName() + " object", new Character(paramChar));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setShortField(Object paramObject, Class<?> paramClass, String paramString, short paramShort) {
/*      */     try {
/* 2711 */       Field field = getDeclaredField(paramClass, paramString);
/* 2712 */       if (field != null && field.getType() == short.class) {
/* 2713 */         long l = bridge.objectFieldOffset(field);
/* 2714 */         bridge.putShort(paramObject, l, paramShort);
/*      */       } else {
/* 2716 */         throw new InvalidObjectException("Field Type mismatch");
/*      */       } 
/* 2718 */     } catch (Exception exception) {
/* 2719 */       if (paramObject != null) {
/* 2720 */         throw utilWrapper.errorSetShortField(exception, paramString, paramObject
/* 2721 */             .toString(), new Short(paramShort));
/*      */       }
/*      */       
/* 2724 */       throw utilWrapper.errorSetShortField(exception, paramString, "null " + paramClass
/* 2725 */           .getName() + " object", new Short(paramShort));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setIntField(Object paramObject, Class<?> paramClass, String paramString, int paramInt) {
/*      */     try {
/* 2734 */       Field field = getDeclaredField(paramClass, paramString);
/* 2735 */       if (field != null && field.getType() == int.class) {
/* 2736 */         long l = bridge.objectFieldOffset(field);
/* 2737 */         bridge.putInt(paramObject, l, paramInt);
/*      */       } else {
/* 2739 */         throw new InvalidObjectException("Field Type mismatch");
/*      */       } 
/* 2741 */     } catch (Exception exception) {
/* 2742 */       if (paramObject != null) {
/* 2743 */         throw utilWrapper.errorSetIntField(exception, paramString, paramObject
/* 2744 */             .toString(), new Integer(paramInt));
/*      */       }
/*      */       
/* 2747 */       throw utilWrapper.errorSetIntField(exception, paramString, "null " + paramClass
/* 2748 */           .getName() + " object", new Integer(paramInt));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setLongField(Object paramObject, Class<?> paramClass, String paramString, long paramLong) {
/*      */     try {
/* 2757 */       Field field = getDeclaredField(paramClass, paramString);
/* 2758 */       if (field != null && field.getType() == long.class) {
/* 2759 */         long l = bridge.objectFieldOffset(field);
/* 2760 */         bridge.putLong(paramObject, l, paramLong);
/*      */       } else {
/* 2762 */         throw new InvalidObjectException("Field Type mismatch");
/*      */       } 
/* 2764 */     } catch (Exception exception) {
/* 2765 */       if (paramObject != null) {
/* 2766 */         throw utilWrapper.errorSetLongField(exception, paramString, paramObject
/* 2767 */             .toString(), new Long(paramLong));
/*      */       }
/*      */       
/* 2770 */       throw utilWrapper.errorSetLongField(exception, paramString, "null " + paramClass
/* 2771 */           .getName() + " object", new Long(paramLong));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setFloatField(Object paramObject, Class<?> paramClass, String paramString, float paramFloat) {
/*      */     try {
/* 2780 */       Field field = getDeclaredField(paramClass, paramString);
/* 2781 */       if (field != null && field.getType() == float.class) {
/* 2782 */         long l = bridge.objectFieldOffset(field);
/* 2783 */         bridge.putFloat(paramObject, l, paramFloat);
/*      */       } else {
/* 2785 */         throw new InvalidObjectException("Field Type mismatch");
/*      */       } 
/* 2787 */     } catch (Exception exception) {
/* 2788 */       if (paramObject != null) {
/* 2789 */         throw utilWrapper.errorSetFloatField(exception, paramString, paramObject
/* 2790 */             .toString(), new Float(paramFloat));
/*      */       }
/*      */       
/* 2793 */       throw utilWrapper.errorSetFloatField(exception, paramString, "null " + paramClass
/* 2794 */           .getName() + " object", new Float(paramFloat));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setDoubleField(Object paramObject, Class<?> paramClass, String paramString, double paramDouble) {
/*      */     try {
/* 2803 */       Field field = getDeclaredField(paramClass, paramString);
/* 2804 */       if (field != null && field.getType() == double.class) {
/* 2805 */         long l = bridge.objectFieldOffset(field);
/* 2806 */         bridge.putDouble(paramObject, l, paramDouble);
/*      */       } else {
/* 2808 */         throw new InvalidObjectException("Field Type mismatch");
/*      */       } 
/* 2810 */     } catch (Exception exception) {
/* 2811 */       if (paramObject != null) {
/* 2812 */         throw utilWrapper.errorSetDoubleField(exception, paramString, paramObject
/* 2813 */             .toString(), new Double(paramDouble));
/*      */       }
/*      */       
/* 2816 */       throw utilWrapper.errorSetDoubleField(exception, paramString, "null " + paramClass
/* 2817 */           .getName() + " object", new Double(paramDouble));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Field getDeclaredField(final Class<?> c, final String fieldName) throws PrivilegedActionException, NoSuchFieldException, SecurityException {
/* 2827 */     if (System.getSecurityManager() == null) {
/* 2828 */       return c.getDeclaredField(fieldName);
/*      */     }
/* 2830 */     return 
/* 2831 */       AccessController.<Field>doPrivileged(new PrivilegedExceptionAction<Field>()
/*      */         {
/*      */           public Field run() throws NoSuchFieldException {
/* 2834 */             return c.getDeclaredField(fieldName);
/*      */           }
/*      */         });
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
/*      */   static class ActiveRecursionManager
/*      */   {
/* 2855 */     private Map<Integer, Object> offsetToObjectMap = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void addObject(int param1Int, Object param1Object) {
/* 2862 */       this.offsetToObjectMap.put(new Integer(param1Int), param1Object);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object getObject(int param1Int) throws IOException {
/* 2871 */       Integer integer = new Integer(param1Int);
/*      */       
/* 2873 */       if (!this.offsetToObjectMap.containsKey(integer))
/*      */       {
/* 2875 */         throw new IOException("Invalid indirection to offset " + param1Int);
/*      */       }
/*      */       
/* 2878 */       return this.offsetToObjectMap.get(integer);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeObject(int param1Int) {
/* 2886 */       this.offsetToObjectMap.remove(new Integer(param1Int));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsObject(int param1Int) {
/* 2893 */       return this.offsetToObjectMap.containsKey(new Integer(param1Int));
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/io/IIOPInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */