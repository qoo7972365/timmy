/*      */ package sun.misc;
/*      */ 
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.DataOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.Method;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.Path;
/*      */ import java.nio.file.Paths;
/*      */ import java.nio.file.attribute.FileAttribute;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ProxyGenerator
/*      */ {
/*      */   private static final int CLASSFILE_MAJOR_VERSION = 49;
/*      */   private static final int CLASSFILE_MINOR_VERSION = 0;
/*      */   private static final int CONSTANT_UTF8 = 1;
/*      */   private static final int CONSTANT_UNICODE = 2;
/*      */   private static final int CONSTANT_INTEGER = 3;
/*      */   private static final int CONSTANT_FLOAT = 4;
/*      */   private static final int CONSTANT_LONG = 5;
/*      */   private static final int CONSTANT_DOUBLE = 6;
/*      */   private static final int CONSTANT_CLASS = 7;
/*      */   private static final int CONSTANT_STRING = 8;
/*      */   private static final int CONSTANT_FIELD = 9;
/*      */   private static final int CONSTANT_METHOD = 10;
/*      */   private static final int CONSTANT_INTERFACEMETHOD = 11;
/*      */   private static final int CONSTANT_NAMEANDTYPE = 12;
/*      */   private static final int ACC_PUBLIC = 1;
/*      */   private static final int ACC_PRIVATE = 2;
/*      */   private static final int ACC_STATIC = 8;
/*      */   private static final int ACC_FINAL = 16;
/*      */   private static final int ACC_SUPER = 32;
/*      */   private static final int opc_aconst_null = 1;
/*      */   private static final int opc_iconst_0 = 3;
/*      */   private static final int opc_bipush = 16;
/*      */   private static final int opc_sipush = 17;
/*      */   private static final int opc_ldc = 18;
/*      */   private static final int opc_ldc_w = 19;
/*      */   private static final int opc_iload = 21;
/*      */   private static final int opc_lload = 22;
/*      */   private static final int opc_fload = 23;
/*      */   private static final int opc_dload = 24;
/*      */   private static final int opc_aload = 25;
/*      */   private static final int opc_iload_0 = 26;
/*      */   private static final int opc_lload_0 = 30;
/*      */   private static final int opc_fload_0 = 34;
/*      */   private static final int opc_dload_0 = 38;
/*      */   private static final int opc_aload_0 = 42;
/*      */   private static final int opc_astore = 58;
/*      */   private static final int opc_astore_0 = 75;
/*      */   private static final int opc_aastore = 83;
/*      */   private static final int opc_pop = 87;
/*      */   private static final int opc_dup = 89;
/*      */   private static final int opc_ireturn = 172;
/*      */   private static final int opc_lreturn = 173;
/*      */   private static final int opc_freturn = 174;
/*      */   private static final int opc_dreturn = 175;
/*      */   private static final int opc_areturn = 176;
/*      */   private static final int opc_return = 177;
/*      */   private static final int opc_getstatic = 178;
/*      */   private static final int opc_putstatic = 179;
/*      */   private static final int opc_getfield = 180;
/*      */   private static final int opc_invokevirtual = 182;
/*      */   private static final int opc_invokespecial = 183;
/*      */   private static final int opc_invokestatic = 184;
/*      */   private static final int opc_invokeinterface = 185;
/*      */   private static final int opc_new = 187;
/*      */   private static final int opc_anewarray = 189;
/*      */   private static final int opc_athrow = 191;
/*      */   private static final int opc_checkcast = 192;
/*      */   private static final int opc_wide = 196;
/*      */   private static final String superclassName = "java/lang/reflect/Proxy";
/*      */   private static final String handlerFieldName = "h";
/*  315 */   private static final boolean saveGeneratedFiles = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.misc.ProxyGenerator.saveGeneratedFiles")))
/*      */     
/*  317 */     .booleanValue(); private static Method hashCodeMethod; private static Method equalsMethod;
/*      */   private static Method toStringMethod;
/*      */   private String className;
/*      */   private Class<?>[] interfaces;
/*      */   private int accessFlags;
/*      */   
/*      */   public static byte[] generateProxyClass(String paramString, Class<?>[] paramArrayOfClass) {
/*  324 */     return generateProxyClass(paramString, paramArrayOfClass, 49);
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
/*      */   public static byte[] generateProxyClass(final String name, Class<?>[] paramArrayOfClass, int paramInt) {
/*  338 */     ProxyGenerator proxyGenerator = new ProxyGenerator(name, paramArrayOfClass, paramInt);
/*  339 */     final byte[] classFile = proxyGenerator.generateClassFile();
/*      */     
/*  341 */     if (saveGeneratedFiles) {
/*  342 */       AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*      */             public Void run() {
/*      */               try {
/*      */                 Path path;
/*  346 */                 int i = name.lastIndexOf('.');
/*      */                 
/*  348 */                 if (i > 0) {
/*  349 */                   Path path1 = Paths.get(name.substring(0, i).replace('.', File.separatorChar), new String[0]);
/*  350 */                   Files.createDirectories(path1, (FileAttribute<?>[])new FileAttribute[0]);
/*  351 */                   path = path1.resolve(name.substring(i + 1, name.length()) + ".class");
/*      */                 } else {
/*  353 */                   path = Paths.get(name + ".class", new String[0]);
/*      */                 } 
/*  355 */                 Files.write(path, classFile, new java.nio.file.OpenOption[0]);
/*  356 */                 return null;
/*  357 */               } catch (IOException iOException) {
/*  358 */                 throw new InternalError("I/O exception saving generated file: " + iOException);
/*      */               } 
/*      */             }
/*      */           });
/*      */     }
/*      */ 
/*      */     
/*  365 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*      */     try {
/*  374 */       hashCodeMethod = Object.class.getMethod("hashCode", new Class[0]);
/*      */       
/*  376 */       equalsMethod = Object.class.getMethod("equals", new Class[] { Object.class });
/*  377 */       toStringMethod = Object.class.getMethod("toString", new Class[0]);
/*  378 */     } catch (NoSuchMethodException noSuchMethodException) {
/*  379 */       throw new NoSuchMethodError(noSuchMethodException.getMessage());
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
/*  393 */   private ConstantPool cp = new ConstantPool();
/*      */ 
/*      */   
/*  396 */   private List<FieldInfo> fields = new ArrayList<>();
/*      */ 
/*      */   
/*  399 */   private List<MethodInfo> methods = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  405 */   private Map<String, List<ProxyMethod>> proxyMethods = new HashMap<>();
/*      */ 
/*      */   
/*  408 */   private int proxyMethodCount = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ProxyGenerator(String paramString, Class<?>[] paramArrayOfClass, int paramInt) {
/*  418 */     this.className = paramString;
/*  419 */     this.interfaces = paramArrayOfClass;
/*  420 */     this.accessFlags = paramInt;
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
/*      */   private byte[] generateClassFile() {
/*  441 */     addProxyMethod(hashCodeMethod, Object.class);
/*  442 */     addProxyMethod(equalsMethod, Object.class);
/*  443 */     addProxyMethod(toStringMethod, Object.class);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  450 */     for (Class<?> clazz : this.interfaces) {
/*  451 */       for (Method method : clazz.getMethods()) {
/*  452 */         addProxyMethod(method, clazz);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  460 */     for (List<ProxyMethod> list : this.proxyMethods.values()) {
/*  461 */       checkReturnTypes(list);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  469 */       this.methods.add(generateConstructor());
/*      */       
/*  471 */       for (List<ProxyMethod> list : this.proxyMethods.values()) {
/*  472 */         for (ProxyMethod proxyMethod : list) {
/*      */ 
/*      */           
/*  475 */           this.fields.add(new FieldInfo(proxyMethod.methodFieldName, "Ljava/lang/reflect/Method;", 10));
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  480 */           this.methods.add(proxyMethod.generateMethod());
/*      */         } 
/*      */       } 
/*      */       
/*  484 */       this.methods.add(generateStaticInitializer());
/*      */     }
/*  486 */     catch (IOException iOException) {
/*  487 */       throw new InternalError("unexpected I/O Exception", iOException);
/*      */     } 
/*      */     
/*  490 */     if (this.methods.size() > 65535) {
/*  491 */       throw new IllegalArgumentException("method limit exceeded");
/*      */     }
/*  493 */     if (this.fields.size() > 65535) {
/*  494 */       throw new IllegalArgumentException("field limit exceeded");
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
/*  505 */     this.cp.getClass(dotToSlash(this.className));
/*  506 */     this.cp.getClass("java/lang/reflect/Proxy");
/*  507 */     for (Class<?> clazz : this.interfaces) {
/*  508 */       this.cp.getClass(dotToSlash(clazz.getName()));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  515 */     this.cp.setReadOnly();
/*      */     
/*  517 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/*  518 */     DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  526 */       dataOutputStream.writeInt(-889275714);
/*      */       
/*  528 */       dataOutputStream.writeShort(0);
/*      */       
/*  530 */       dataOutputStream.writeShort(49);
/*      */       
/*  532 */       this.cp.write(dataOutputStream);
/*      */ 
/*      */       
/*  535 */       dataOutputStream.writeShort(this.accessFlags);
/*      */       
/*  537 */       dataOutputStream.writeShort(this.cp.getClass(dotToSlash(this.className)));
/*      */       
/*  539 */       dataOutputStream.writeShort(this.cp.getClass("java/lang/reflect/Proxy"));
/*      */ 
/*      */       
/*  542 */       dataOutputStream.writeShort(this.interfaces.length);
/*      */       
/*  544 */       for (Class<?> clazz : this.interfaces) {
/*  545 */         dataOutputStream.writeShort(this.cp.getClass(
/*  546 */               dotToSlash(clazz.getName())));
/*      */       }
/*      */ 
/*      */       
/*  550 */       dataOutputStream.writeShort(this.fields.size());
/*      */       
/*  552 */       for (FieldInfo fieldInfo : this.fields) {
/*  553 */         fieldInfo.write(dataOutputStream);
/*      */       }
/*      */ 
/*      */       
/*  557 */       dataOutputStream.writeShort(this.methods.size());
/*      */       
/*  559 */       for (MethodInfo methodInfo : this.methods) {
/*  560 */         methodInfo.write(dataOutputStream);
/*      */       }
/*      */ 
/*      */       
/*  564 */       dataOutputStream.writeShort(0);
/*      */     }
/*  566 */     catch (IOException iOException) {
/*  567 */       throw new InternalError("unexpected I/O Exception", iOException);
/*      */     } 
/*      */     
/*  570 */     return byteArrayOutputStream.toByteArray();
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
/*      */   private void addProxyMethod(Method paramMethod, Class<?> paramClass) {
/*  587 */     String str1 = paramMethod.getName();
/*  588 */     Class[] arrayOfClass1 = paramMethod.getParameterTypes();
/*  589 */     Class<?> clazz = paramMethod.getReturnType();
/*  590 */     Class[] arrayOfClass2 = paramMethod.getExceptionTypes();
/*      */     
/*  592 */     String str2 = str1 + getParameterDescriptors(arrayOfClass1);
/*  593 */     List<ProxyMethod> list = this.proxyMethods.get(str2);
/*  594 */     if (list != null) {
/*  595 */       for (ProxyMethod proxyMethod : list) {
/*  596 */         if (clazz == proxyMethod.returnType) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  603 */           ArrayList<Class<?>> arrayList = new ArrayList();
/*  604 */           collectCompatibleTypes(arrayOfClass2, proxyMethod.exceptionTypes, arrayList);
/*      */           
/*  606 */           collectCompatibleTypes(proxyMethod.exceptionTypes, arrayOfClass2, arrayList);
/*      */           
/*  608 */           proxyMethod.exceptionTypes = new Class[arrayList.size()];
/*  609 */           proxyMethod
/*  610 */             .exceptionTypes = (Class[])arrayList.<Class<?>[]>toArray((Class<?>[][])proxyMethod.exceptionTypes);
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } else {
/*  615 */       list = new ArrayList(3);
/*  616 */       this.proxyMethods.put(str2, list);
/*      */     } 
/*  618 */     list.add(new ProxyMethod(str1, arrayOfClass1, clazz, arrayOfClass2, paramClass));
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
/*      */   private static void checkReturnTypes(List<ProxyMethod> paramList) {
/*  637 */     if (paramList.size() < 2) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  645 */     LinkedList<Class<?>> linkedList = new LinkedList();
/*      */ 
/*      */     
/*  648 */     label30: for (ProxyMethod proxyMethod : paramList) {
/*  649 */       Class<?> clazz = proxyMethod.returnType;
/*  650 */       if (clazz.isPrimitive()) {
/*  651 */         throw new IllegalArgumentException("methods with same signature " + 
/*      */             
/*  653 */             getFriendlyMethodSignature(proxyMethod.methodName, proxyMethod.parameterTypes) + " but incompatible return types: " + clazz
/*      */ 
/*      */             
/*  656 */             .getName() + " and others");
/*      */       }
/*  658 */       boolean bool = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  664 */       ListIterator<Class<?>> listIterator = linkedList.listIterator();
/*  665 */       while (listIterator.hasNext()) {
/*  666 */         Class<?> clazz1 = listIterator.next();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  672 */         if (clazz.isAssignableFrom(clazz1)) {
/*      */           assert false;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           continue label30;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  683 */         if (clazz1.isAssignableFrom(clazz)) {
/*      */           
/*  685 */           if (!bool) {
/*  686 */             listIterator.set(clazz);
/*  687 */             bool = true; continue;
/*      */           } 
/*  689 */           listIterator.remove();
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  699 */       if (!bool) {
/*  700 */         linkedList.add(clazz);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  708 */     if (linkedList.size() > 1) {
/*  709 */       ProxyMethod proxyMethod = paramList.get(0);
/*  710 */       throw new IllegalArgumentException("methods with same signature " + 
/*      */           
/*  712 */           getFriendlyMethodSignature(proxyMethod.methodName, proxyMethod.parameterTypes) + " but incompatible return types: " + linkedList);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class FieldInfo
/*      */   {
/*      */     public int accessFlags;
/*      */     
/*      */     public String name;
/*      */     
/*      */     public String descriptor;
/*      */ 
/*      */     
/*      */     public FieldInfo(String param1String1, String param1String2, int param1Int) {
/*  728 */       this.name = param1String1;
/*  729 */       this.descriptor = param1String2;
/*  730 */       this.accessFlags = param1Int;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  736 */       ProxyGenerator.this.cp.getUtf8(param1String1);
/*  737 */       ProxyGenerator.this.cp.getUtf8(param1String2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void write(DataOutputStream param1DataOutputStream) throws IOException {
/*  746 */       param1DataOutputStream.writeShort(this.accessFlags);
/*      */       
/*  748 */       param1DataOutputStream.writeShort(ProxyGenerator.this.cp.getUtf8(this.name));
/*      */       
/*  750 */       param1DataOutputStream.writeShort(ProxyGenerator.this.cp.getUtf8(this.descriptor));
/*      */       
/*  752 */       param1DataOutputStream.writeShort(0);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class ExceptionTableEntry
/*      */   {
/*      */     public short startPc;
/*      */     
/*      */     public short endPc;
/*      */     
/*      */     public short handlerPc;
/*      */     
/*      */     public short catchType;
/*      */ 
/*      */     
/*      */     public ExceptionTableEntry(short param1Short1, short param1Short2, short param1Short3, short param1Short4) {
/*  770 */       this.startPc = param1Short1;
/*  771 */       this.endPc = param1Short2;
/*  772 */       this.handlerPc = param1Short3;
/*  773 */       this.catchType = param1Short4;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class MethodInfo
/*      */   {
/*      */     public int accessFlags;
/*      */     
/*      */     public String name;
/*      */     
/*      */     public String descriptor;
/*      */     
/*      */     public short maxStack;
/*      */     public short maxLocals;
/*  788 */     public ByteArrayOutputStream code = new ByteArrayOutputStream();
/*  789 */     public List<ProxyGenerator.ExceptionTableEntry> exceptionTable = new ArrayList<>();
/*      */     
/*      */     public short[] declaredExceptions;
/*      */     
/*      */     public MethodInfo(String param1String1, String param1String2, int param1Int) {
/*  794 */       this.name = param1String1;
/*  795 */       this.descriptor = param1String2;
/*  796 */       this.accessFlags = param1Int;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  802 */       ProxyGenerator.this.cp.getUtf8(param1String1);
/*  803 */       ProxyGenerator.this.cp.getUtf8(param1String2);
/*  804 */       ProxyGenerator.this.cp.getUtf8("Code");
/*  805 */       ProxyGenerator.this.cp.getUtf8("Exceptions");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void write(DataOutputStream param1DataOutputStream) throws IOException {
/*  814 */       param1DataOutputStream.writeShort(this.accessFlags);
/*      */       
/*  816 */       param1DataOutputStream.writeShort(ProxyGenerator.this.cp.getUtf8(this.name));
/*      */       
/*  818 */       param1DataOutputStream.writeShort(ProxyGenerator.this.cp.getUtf8(this.descriptor));
/*      */       
/*  820 */       param1DataOutputStream.writeShort(2);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  825 */       param1DataOutputStream.writeShort(ProxyGenerator.this.cp.getUtf8("Code"));
/*      */       
/*  827 */       param1DataOutputStream.writeInt(12 + this.code.size() + 8 * this.exceptionTable.size());
/*      */       
/*  829 */       param1DataOutputStream.writeShort(this.maxStack);
/*      */       
/*  831 */       param1DataOutputStream.writeShort(this.maxLocals);
/*      */       
/*  833 */       param1DataOutputStream.writeInt(this.code.size());
/*      */       
/*  835 */       this.code.writeTo(param1DataOutputStream);
/*      */       
/*  837 */       param1DataOutputStream.writeShort(this.exceptionTable.size());
/*  838 */       for (ProxyGenerator.ExceptionTableEntry exceptionTableEntry : this.exceptionTable) {
/*      */         
/*  840 */         param1DataOutputStream.writeShort(exceptionTableEntry.startPc);
/*      */         
/*  842 */         param1DataOutputStream.writeShort(exceptionTableEntry.endPc);
/*      */         
/*  844 */         param1DataOutputStream.writeShort(exceptionTableEntry.handlerPc);
/*      */         
/*  846 */         param1DataOutputStream.writeShort(exceptionTableEntry.catchType);
/*      */       } 
/*      */       
/*  849 */       param1DataOutputStream.writeShort(0);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  854 */       param1DataOutputStream.writeShort(ProxyGenerator.this.cp.getUtf8("Exceptions"));
/*      */       
/*  856 */       param1DataOutputStream.writeInt(2 + 2 * this.declaredExceptions.length);
/*      */       
/*  858 */       param1DataOutputStream.writeShort(this.declaredExceptions.length);
/*      */       
/*  860 */       for (short s : this.declaredExceptions) {
/*  861 */         param1DataOutputStream.writeShort(s);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class ProxyMethod
/*      */   {
/*      */     public String methodName;
/*      */ 
/*      */     
/*      */     public Class<?>[] parameterTypes;
/*      */     
/*      */     public Class<?> returnType;
/*      */     
/*      */     public Class<?>[] exceptionTypes;
/*      */     
/*      */     public Class<?> fromClass;
/*      */     
/*      */     public String methodFieldName;
/*      */ 
/*      */     
/*      */     private ProxyMethod(String param1String, Class<?>[] param1ArrayOfClass1, Class<?> param1Class1, Class<?>[] param1ArrayOfClass2, Class<?> param1Class2) {
/*  885 */       this.methodName = param1String;
/*  886 */       this.parameterTypes = param1ArrayOfClass1;
/*  887 */       this.returnType = param1Class1;
/*  888 */       this.exceptionTypes = param1ArrayOfClass2;
/*  889 */       this.fromClass = param1Class2;
/*  890 */       this.methodFieldName = "m" + ProxyGenerator.this.proxyMethodCount++;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private ProxyGenerator.MethodInfo generateMethod() throws IOException {
/*  898 */       String str = ProxyGenerator.getMethodDescriptor(this.parameterTypes, this.returnType);
/*  899 */       ProxyGenerator.MethodInfo methodInfo = new ProxyGenerator.MethodInfo(this.methodName, str, 17);
/*      */ 
/*      */       
/*  902 */       int[] arrayOfInt = new int[this.parameterTypes.length];
/*  903 */       int i = 1; int j;
/*  904 */       for (j = 0; j < arrayOfInt.length; j++) {
/*  905 */         arrayOfInt[j] = i;
/*  906 */         i += ProxyGenerator.getWordsPerType(this.parameterTypes[j]);
/*      */       } 
/*  908 */       j = i;
/*  909 */       boolean bool = false;
/*      */       
/*  911 */       DataOutputStream dataOutputStream = new DataOutputStream(methodInfo.code);
/*      */       
/*  913 */       ProxyGenerator.this.code_aload(0, dataOutputStream);
/*      */       
/*  915 */       dataOutputStream.writeByte(180);
/*  916 */       dataOutputStream.writeShort(ProxyGenerator.this.cp.getFieldRef("java/lang/reflect/Proxy", "h", "Ljava/lang/reflect/InvocationHandler;"));
/*      */ 
/*      */ 
/*      */       
/*  920 */       ProxyGenerator.this.code_aload(0, dataOutputStream);
/*      */       
/*  922 */       dataOutputStream.writeByte(178);
/*  923 */       dataOutputStream.writeShort(ProxyGenerator.this.cp.getFieldRef(ProxyGenerator
/*  924 */             .dotToSlash(ProxyGenerator.this.className), this.methodFieldName, "Ljava/lang/reflect/Method;"));
/*      */ 
/*      */       
/*  927 */       if (this.parameterTypes.length > 0) {
/*      */         
/*  929 */         ProxyGenerator.this.code_ipush(this.parameterTypes.length, dataOutputStream);
/*      */         
/*  931 */         dataOutputStream.writeByte(189);
/*  932 */         dataOutputStream.writeShort(ProxyGenerator.this.cp.getClass("java/lang/Object"));
/*      */         
/*  934 */         for (byte b1 = 0; b1 < this.parameterTypes.length; b1++) {
/*      */           
/*  936 */           dataOutputStream.writeByte(89);
/*      */           
/*  938 */           ProxyGenerator.this.code_ipush(b1, dataOutputStream);
/*      */           
/*  940 */           codeWrapArgument(this.parameterTypes[b1], arrayOfInt[b1], dataOutputStream);
/*      */           
/*  942 */           dataOutputStream.writeByte(83);
/*      */         } 
/*      */       } else {
/*      */         
/*  946 */         dataOutputStream.writeByte(1);
/*      */       } 
/*      */       
/*  949 */       dataOutputStream.writeByte(185);
/*  950 */       dataOutputStream.writeShort(ProxyGenerator.this.cp.getInterfaceMethodRef("java/lang/reflect/InvocationHandler", "invoke", "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;"));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  955 */       dataOutputStream.writeByte(4);
/*  956 */       dataOutputStream.writeByte(0);
/*      */       
/*  958 */       if (this.returnType == void.class) {
/*      */         
/*  960 */         dataOutputStream.writeByte(87);
/*      */         
/*  962 */         dataOutputStream.writeByte(177);
/*      */       }
/*      */       else {
/*      */         
/*  966 */         codeUnwrapReturnValue(this.returnType, dataOutputStream);
/*      */       } 
/*      */       
/*  969 */       short s1 = (short)methodInfo.code.size(), s2 = s1;
/*      */       
/*  971 */       List list = ProxyGenerator.computeUniqueCatchList(this.exceptionTypes);
/*  972 */       if (list.size() > 0) {
/*      */         
/*  974 */         for (Class clazz : list) {
/*  975 */           methodInfo.exceptionTable.add(new ProxyGenerator.ExceptionTableEntry(bool, s2, s1, ProxyGenerator.this
/*      */                 
/*  977 */                 .cp.getClass(ProxyGenerator.dotToSlash(clazz.getName()))));
/*      */         }
/*      */         
/*  980 */         dataOutputStream.writeByte(191);
/*      */         
/*  982 */         s1 = (short)methodInfo.code.size();
/*      */         
/*  984 */         methodInfo.exceptionTable.add(new ProxyGenerator.ExceptionTableEntry(bool, s2, s1, ProxyGenerator.this
/*  985 */               .cp.getClass("java/lang/Throwable")));
/*      */         
/*  987 */         ProxyGenerator.this.code_astore(j, dataOutputStream);
/*      */         
/*  989 */         dataOutputStream.writeByte(187);
/*  990 */         dataOutputStream.writeShort(ProxyGenerator.this.cp.getClass("java/lang/reflect/UndeclaredThrowableException"));
/*      */ 
/*      */         
/*  993 */         dataOutputStream.writeByte(89);
/*      */         
/*  995 */         ProxyGenerator.this.code_aload(j, dataOutputStream);
/*      */         
/*  997 */         dataOutputStream.writeByte(183);
/*      */         
/*  999 */         dataOutputStream.writeShort(ProxyGenerator.this.cp.getMethodRef("java/lang/reflect/UndeclaredThrowableException", "<init>", "(Ljava/lang/Throwable;)V"));
/*      */ 
/*      */ 
/*      */         
/* 1003 */         dataOutputStream.writeByte(191);
/*      */       } 
/*      */       
/* 1006 */       if (methodInfo.code.size() > 65535) {
/* 1007 */         throw new IllegalArgumentException("code size limit exceeded");
/*      */       }
/*      */       
/* 1010 */       methodInfo.maxStack = 10;
/* 1011 */       methodInfo.maxLocals = (short)(j + 1);
/* 1012 */       methodInfo.declaredExceptions = new short[this.exceptionTypes.length];
/* 1013 */       for (byte b = 0; b < this.exceptionTypes.length; b++) {
/* 1014 */         methodInfo.declaredExceptions[b] = ProxyGenerator.this.cp.getClass(ProxyGenerator
/* 1015 */             .dotToSlash(this.exceptionTypes[b].getName()));
/*      */       }
/*      */       
/* 1018 */       return methodInfo;
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
/*      */     private void codeWrapArgument(Class<?> param1Class, int param1Int, DataOutputStream param1DataOutputStream) throws IOException {
/* 1032 */       if (param1Class.isPrimitive()) {
/* 1033 */         ProxyGenerator.PrimitiveTypeInfo primitiveTypeInfo = ProxyGenerator.PrimitiveTypeInfo.get(param1Class);
/*      */         
/* 1035 */         if (param1Class == int.class || param1Class == boolean.class || param1Class == byte.class || param1Class == char.class || param1Class == short.class) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1041 */           ProxyGenerator.this.code_iload(param1Int, param1DataOutputStream);
/* 1042 */         } else if (param1Class == long.class) {
/* 1043 */           ProxyGenerator.this.code_lload(param1Int, param1DataOutputStream);
/* 1044 */         } else if (param1Class == float.class) {
/* 1045 */           ProxyGenerator.this.code_fload(param1Int, param1DataOutputStream);
/* 1046 */         } else if (param1Class == double.class) {
/* 1047 */           ProxyGenerator.this.code_dload(param1Int, param1DataOutputStream);
/*      */         } else {
/* 1049 */           throw new AssertionError();
/*      */         } 
/*      */         
/* 1052 */         param1DataOutputStream.writeByte(184);
/* 1053 */         param1DataOutputStream.writeShort(ProxyGenerator.this.cp.getMethodRef(primitiveTypeInfo.wrapperClassName, "valueOf", primitiveTypeInfo.wrapperValueOfDesc));
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 1059 */         ProxyGenerator.this.code_aload(param1Int, param1DataOutputStream);
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
/*      */     private void codeUnwrapReturnValue(Class<?> param1Class, DataOutputStream param1DataOutputStream) throws IOException {
/* 1072 */       if (param1Class.isPrimitive()) {
/* 1073 */         ProxyGenerator.PrimitiveTypeInfo primitiveTypeInfo = ProxyGenerator.PrimitiveTypeInfo.get(param1Class);
/*      */         
/* 1075 */         param1DataOutputStream.writeByte(192);
/* 1076 */         param1DataOutputStream.writeShort(ProxyGenerator.this.cp.getClass(primitiveTypeInfo.wrapperClassName));
/*      */         
/* 1078 */         param1DataOutputStream.writeByte(182);
/* 1079 */         param1DataOutputStream.writeShort(ProxyGenerator.this.cp.getMethodRef(primitiveTypeInfo.wrapperClassName, primitiveTypeInfo.unwrapMethodName, primitiveTypeInfo.unwrapMethodDesc));
/*      */ 
/*      */ 
/*      */         
/* 1083 */         if (param1Class == int.class || param1Class == boolean.class || param1Class == byte.class || param1Class == char.class || param1Class == short.class) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1089 */           param1DataOutputStream.writeByte(172);
/* 1090 */         } else if (param1Class == long.class) {
/* 1091 */           param1DataOutputStream.writeByte(173);
/* 1092 */         } else if (param1Class == float.class) {
/* 1093 */           param1DataOutputStream.writeByte(174);
/* 1094 */         } else if (param1Class == double.class) {
/* 1095 */           param1DataOutputStream.writeByte(175);
/*      */         } else {
/* 1097 */           throw new AssertionError();
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1102 */         param1DataOutputStream.writeByte(192);
/* 1103 */         param1DataOutputStream.writeShort(ProxyGenerator.this.cp.getClass(ProxyGenerator.dotToSlash(param1Class.getName())));
/*      */         
/* 1105 */         param1DataOutputStream.writeByte(176);
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
/*      */     private void codeFieldInitialization(DataOutputStream param1DataOutputStream) throws IOException {
/* 1117 */       ProxyGenerator.this.codeClassForName(this.fromClass, param1DataOutputStream);
/*      */       
/* 1119 */       ProxyGenerator.this.code_ldc(ProxyGenerator.this.cp.getString(this.methodName), param1DataOutputStream);
/*      */       
/* 1121 */       ProxyGenerator.this.code_ipush(this.parameterTypes.length, param1DataOutputStream);
/*      */       
/* 1123 */       param1DataOutputStream.writeByte(189);
/* 1124 */       param1DataOutputStream.writeShort(ProxyGenerator.this.cp.getClass("java/lang/Class"));
/*      */       
/* 1126 */       for (byte b = 0; b < this.parameterTypes.length; b++) {
/*      */         
/* 1128 */         param1DataOutputStream.writeByte(89);
/*      */         
/* 1130 */         ProxyGenerator.this.code_ipush(b, param1DataOutputStream);
/*      */         
/* 1132 */         if (this.parameterTypes[b].isPrimitive()) {
/*      */           
/* 1134 */           ProxyGenerator.PrimitiveTypeInfo primitiveTypeInfo = ProxyGenerator.PrimitiveTypeInfo.get(this.parameterTypes[b]);
/*      */           
/* 1136 */           param1DataOutputStream.writeByte(178);
/* 1137 */           param1DataOutputStream.writeShort(ProxyGenerator.this.cp.getFieldRef(primitiveTypeInfo.wrapperClassName, "TYPE", "Ljava/lang/Class;"));
/*      */         }
/*      */         else {
/*      */           
/* 1141 */           ProxyGenerator.this.codeClassForName(this.parameterTypes[b], param1DataOutputStream);
/*      */         } 
/*      */         
/* 1144 */         param1DataOutputStream.writeByte(83);
/*      */       } 
/*      */       
/* 1147 */       param1DataOutputStream.writeByte(182);
/* 1148 */       param1DataOutputStream.writeShort(ProxyGenerator.this.cp.getMethodRef("java/lang/Class", "getMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1154 */       param1DataOutputStream.writeByte(179);
/* 1155 */       param1DataOutputStream.writeShort(ProxyGenerator.this.cp.getFieldRef(ProxyGenerator
/* 1156 */             .dotToSlash(ProxyGenerator.this.className), this.methodFieldName, "Ljava/lang/reflect/Method;"));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MethodInfo generateConstructor() throws IOException {
/* 1165 */     MethodInfo methodInfo = new MethodInfo("<init>", "(Ljava/lang/reflect/InvocationHandler;)V", 1);
/*      */ 
/*      */ 
/*      */     
/* 1169 */     DataOutputStream dataOutputStream = new DataOutputStream(methodInfo.code);
/*      */     
/* 1171 */     code_aload(0, dataOutputStream);
/*      */     
/* 1173 */     code_aload(1, dataOutputStream);
/*      */     
/* 1175 */     dataOutputStream.writeByte(183);
/* 1176 */     dataOutputStream.writeShort(this.cp.getMethodRef("java/lang/reflect/Proxy", "<init>", "(Ljava/lang/reflect/InvocationHandler;)V"));
/*      */ 
/*      */ 
/*      */     
/* 1180 */     dataOutputStream.writeByte(177);
/*      */     
/* 1182 */     methodInfo.maxStack = 10;
/* 1183 */     methodInfo.maxLocals = 2;
/* 1184 */     methodInfo.declaredExceptions = new short[0];
/*      */     
/* 1186 */     return methodInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MethodInfo generateStaticInitializer() throws IOException {
/* 1193 */     MethodInfo methodInfo = new MethodInfo("<clinit>", "()V", 8);
/*      */ 
/*      */     
/* 1196 */     byte b = 1;
/* 1197 */     boolean bool = false;
/*      */     
/* 1199 */     DataOutputStream dataOutputStream = new DataOutputStream(methodInfo.code);
/*      */     
/* 1201 */     for (List<ProxyMethod> list : this.proxyMethods.values()) {
/* 1202 */       for (ProxyMethod proxyMethod : list) {
/* 1203 */         proxyMethod.codeFieldInitialization(dataOutputStream);
/*      */       }
/*      */     } 
/*      */     
/* 1207 */     dataOutputStream.writeByte(177);
/*      */     
/* 1209 */     short s1 = (short)methodInfo.code.size(), s2 = s1;
/*      */     
/* 1211 */     methodInfo.exceptionTable.add(new ExceptionTableEntry(bool, s2, s1, this.cp
/*      */           
/* 1213 */           .getClass("java/lang/NoSuchMethodException")));
/*      */     
/* 1215 */     code_astore(b, dataOutputStream);
/*      */     
/* 1217 */     dataOutputStream.writeByte(187);
/* 1218 */     dataOutputStream.writeShort(this.cp.getClass("java/lang/NoSuchMethodError"));
/*      */     
/* 1220 */     dataOutputStream.writeByte(89);
/*      */     
/* 1222 */     code_aload(b, dataOutputStream);
/*      */     
/* 1224 */     dataOutputStream.writeByte(182);
/* 1225 */     dataOutputStream.writeShort(this.cp.getMethodRef("java/lang/Throwable", "getMessage", "()Ljava/lang/String;"));
/*      */ 
/*      */     
/* 1228 */     dataOutputStream.writeByte(183);
/* 1229 */     dataOutputStream.writeShort(this.cp.getMethodRef("java/lang/NoSuchMethodError", "<init>", "(Ljava/lang/String;)V"));
/*      */ 
/*      */     
/* 1232 */     dataOutputStream.writeByte(191);
/*      */     
/* 1234 */     s1 = (short)methodInfo.code.size();
/*      */     
/* 1236 */     methodInfo.exceptionTable.add(new ExceptionTableEntry(bool, s2, s1, this.cp
/*      */           
/* 1238 */           .getClass("java/lang/ClassNotFoundException")));
/*      */     
/* 1240 */     code_astore(b, dataOutputStream);
/*      */     
/* 1242 */     dataOutputStream.writeByte(187);
/* 1243 */     dataOutputStream.writeShort(this.cp.getClass("java/lang/NoClassDefFoundError"));
/*      */     
/* 1245 */     dataOutputStream.writeByte(89);
/*      */     
/* 1247 */     code_aload(b, dataOutputStream);
/*      */     
/* 1249 */     dataOutputStream.writeByte(182);
/* 1250 */     dataOutputStream.writeShort(this.cp.getMethodRef("java/lang/Throwable", "getMessage", "()Ljava/lang/String;"));
/*      */ 
/*      */     
/* 1253 */     dataOutputStream.writeByte(183);
/* 1254 */     dataOutputStream.writeShort(this.cp.getMethodRef("java/lang/NoClassDefFoundError", "<init>", "(Ljava/lang/String;)V"));
/*      */ 
/*      */ 
/*      */     
/* 1258 */     dataOutputStream.writeByte(191);
/*      */     
/* 1260 */     if (methodInfo.code.size() > 65535) {
/* 1261 */       throw new IllegalArgumentException("code size limit exceeded");
/*      */     }
/*      */     
/* 1264 */     methodInfo.maxStack = 10;
/* 1265 */     methodInfo.maxLocals = (short)(b + 1);
/* 1266 */     methodInfo.declaredExceptions = new short[0];
/*      */     
/* 1268 */     return methodInfo;
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
/*      */   private void code_iload(int paramInt, DataOutputStream paramDataOutputStream) throws IOException {
/* 1285 */     codeLocalLoadStore(paramInt, 21, 26, paramDataOutputStream);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void code_lload(int paramInt, DataOutputStream paramDataOutputStream) throws IOException {
/* 1291 */     codeLocalLoadStore(paramInt, 22, 30, paramDataOutputStream);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void code_fload(int paramInt, DataOutputStream paramDataOutputStream) throws IOException {
/* 1297 */     codeLocalLoadStore(paramInt, 23, 34, paramDataOutputStream);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void code_dload(int paramInt, DataOutputStream paramDataOutputStream) throws IOException {
/* 1303 */     codeLocalLoadStore(paramInt, 24, 38, paramDataOutputStream);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void code_aload(int paramInt, DataOutputStream paramDataOutputStream) throws IOException {
/* 1309 */     codeLocalLoadStore(paramInt, 25, 42, paramDataOutputStream);
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
/*      */   private void code_astore(int paramInt, DataOutputStream paramDataOutputStream) throws IOException {
/* 1339 */     codeLocalLoadStore(paramInt, 58, 75, paramDataOutputStream);
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
/*      */   private void codeLocalLoadStore(int paramInt1, int paramInt2, int paramInt3, DataOutputStream paramDataOutputStream) throws IOException {
/* 1355 */     assert paramInt1 >= 0 && paramInt1 <= 65535;
/* 1356 */     if (paramInt1 <= 3) {
/* 1357 */       paramDataOutputStream.writeByte(paramInt3 + paramInt1);
/* 1358 */     } else if (paramInt1 <= 255) {
/* 1359 */       paramDataOutputStream.writeByte(paramInt2);
/* 1360 */       paramDataOutputStream.writeByte(paramInt1 & 0xFF);
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1366 */       paramDataOutputStream.writeByte(196);
/* 1367 */       paramDataOutputStream.writeByte(paramInt2);
/* 1368 */       paramDataOutputStream.writeShort(paramInt1 & 0xFFFF);
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
/*      */   private void code_ldc(int paramInt, DataOutputStream paramDataOutputStream) throws IOException {
/* 1380 */     assert paramInt >= 0 && paramInt <= 65535;
/* 1381 */     if (paramInt <= 255) {
/* 1382 */       paramDataOutputStream.writeByte(18);
/* 1383 */       paramDataOutputStream.writeByte(paramInt & 0xFF);
/*      */     } else {
/* 1385 */       paramDataOutputStream.writeByte(19);
/* 1386 */       paramDataOutputStream.writeShort(paramInt & 0xFFFF);
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
/*      */   private void code_ipush(int paramInt, DataOutputStream paramDataOutputStream) throws IOException {
/* 1399 */     if (paramInt >= -1 && paramInt <= 5) {
/* 1400 */       paramDataOutputStream.writeByte(3 + paramInt);
/* 1401 */     } else if (paramInt >= -128 && paramInt <= 127) {
/* 1402 */       paramDataOutputStream.writeByte(16);
/* 1403 */       paramDataOutputStream.writeByte(paramInt & 0xFF);
/* 1404 */     } else if (paramInt >= -32768 && paramInt <= 32767) {
/* 1405 */       paramDataOutputStream.writeByte(17);
/* 1406 */       paramDataOutputStream.writeShort(paramInt & 0xFFFF);
/*      */     } else {
/* 1408 */       throw new AssertionError();
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
/*      */   private void codeClassForName(Class<?> paramClass, DataOutputStream paramDataOutputStream) throws IOException {
/* 1421 */     code_ldc(this.cp.getString(paramClass.getName()), paramDataOutputStream);
/*      */     
/* 1423 */     paramDataOutputStream.writeByte(184);
/* 1424 */     paramDataOutputStream.writeShort(this.cp.getMethodRef("java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;"));
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
/*      */   private static String dotToSlash(String paramString) {
/* 1442 */     return paramString.replace('.', '/');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getMethodDescriptor(Class<?>[] paramArrayOfClass, Class<?> paramClass) {
/* 1452 */     return getParameterDescriptors(paramArrayOfClass) + ((paramClass == void.class) ? "V" : 
/* 1453 */       getFieldType(paramClass));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getParameterDescriptors(Class<?>[] paramArrayOfClass) {
/* 1464 */     StringBuilder stringBuilder = new StringBuilder("(");
/* 1465 */     for (byte b = 0; b < paramArrayOfClass.length; b++) {
/* 1466 */       stringBuilder.append(getFieldType(paramArrayOfClass[b]));
/*      */     }
/* 1468 */     stringBuilder.append(')');
/* 1469 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getFieldType(Class<?> paramClass) {
/* 1478 */     if (paramClass.isPrimitive())
/* 1479 */       return (PrimitiveTypeInfo.get(paramClass)).baseTypeString; 
/* 1480 */     if (paramClass.isArray())
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1488 */       return paramClass.getName().replace('.', '/');
/*      */     }
/* 1490 */     return "L" + dotToSlash(paramClass.getName()) + ";";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getFriendlyMethodSignature(String paramString, Class<?>[] paramArrayOfClass) {
/* 1501 */     StringBuilder stringBuilder = new StringBuilder(paramString);
/* 1502 */     stringBuilder.append('(');
/* 1503 */     for (byte b = 0; b < paramArrayOfClass.length; b++) {
/* 1504 */       if (b > 0) {
/* 1505 */         stringBuilder.append(',');
/*      */       }
/* 1507 */       Class<?> clazz = paramArrayOfClass[b];
/* 1508 */       byte b1 = 0;
/* 1509 */       while (clazz.isArray()) {
/* 1510 */         clazz = clazz.getComponentType();
/* 1511 */         b1++;
/*      */       } 
/* 1513 */       stringBuilder.append(clazz.getName());
/* 1514 */       while (b1-- > 0) {
/* 1515 */         stringBuilder.append("[]");
/*      */       }
/*      */     } 
/* 1518 */     stringBuilder.append(')');
/* 1519 */     return stringBuilder.toString();
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
/*      */   private static int getWordsPerType(Class<?> paramClass) {
/* 1532 */     if (paramClass == long.class || paramClass == double.class) {
/* 1533 */       return 2;
/*      */     }
/* 1535 */     return 1;
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
/*      */   private static void collectCompatibleTypes(Class<?>[] paramArrayOfClass1, Class<?>[] paramArrayOfClass2, List<Class<?>> paramList) {
/* 1552 */     for (Class<?> clazz : paramArrayOfClass1) {
/* 1553 */       if (!paramList.contains(clazz)) {
/* 1554 */         for (Class<?> clazz1 : paramArrayOfClass2) {
/* 1555 */           if (clazz1.isAssignableFrom(clazz)) {
/* 1556 */             paramList.add(clazz);
/*      */             break;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static List<Class<?>> computeUniqueCatchList(Class<?>[] paramArrayOfClass) {
/* 1586 */     ArrayList<Class<Error>> arrayList = new ArrayList();
/*      */ 
/*      */     
/* 1589 */     arrayList.add(Error.class);
/* 1590 */     arrayList.add(RuntimeException.class);
/*      */ 
/*      */     
/* 1593 */     for (Class<?> clazz : paramArrayOfClass) {
/* 1594 */       if (clazz.isAssignableFrom(Throwable.class)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1600 */         arrayList.clear(); break;
/*      */       } 
/* 1602 */       if (Throwable.class.isAssignableFrom(clazz)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1612 */         byte b = 0; while (true) { if (b < arrayList.size()) {
/* 1613 */             Class<?> clazz1 = arrayList.get(b);
/* 1614 */             if (clazz1.isAssignableFrom(clazz)) {
/*      */               break;
/*      */             }
/*      */ 
/*      */ 
/*      */             
/* 1620 */             if (clazz.isAssignableFrom(clazz1)) {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1625 */               arrayList.remove(b); continue;
/*      */             } 
/* 1627 */             b++;
/*      */             
/*      */             continue;
/*      */           } 
/* 1631 */           arrayList.add(clazz); break; } 
/*      */       } 
/* 1633 */     }  return arrayList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class PrimitiveTypeInfo
/*      */   {
/*      */     public String baseTypeString;
/*      */ 
/*      */ 
/*      */     
/*      */     public String wrapperClassName;
/*      */ 
/*      */ 
/*      */     
/*      */     public String wrapperValueOfDesc;
/*      */ 
/*      */     
/*      */     public String unwrapMethodName;
/*      */ 
/*      */     
/*      */     public String unwrapMethodDesc;
/*      */ 
/*      */     
/* 1658 */     private static Map<Class<?>, PrimitiveTypeInfo> table = new HashMap<>();
/*      */     static {
/* 1660 */       add(byte.class, Byte.class);
/* 1661 */       add(char.class, Character.class);
/* 1662 */       add(double.class, Double.class);
/* 1663 */       add(float.class, Float.class);
/* 1664 */       add(int.class, Integer.class);
/* 1665 */       add(long.class, Long.class);
/* 1666 */       add(short.class, Short.class);
/* 1667 */       add(boolean.class, Boolean.class);
/*      */     }
/*      */     
/*      */     private static void add(Class<?> param1Class1, Class<?> param1Class2) {
/* 1671 */       table.put(param1Class1, new PrimitiveTypeInfo(param1Class1, param1Class2));
/*      */     }
/*      */ 
/*      */     
/*      */     private PrimitiveTypeInfo(Class<?> param1Class1, Class<?> param1Class2) {
/* 1676 */       assert param1Class1.isPrimitive();
/*      */       
/* 1678 */       this
/*      */         
/* 1680 */         .baseTypeString = Array.newInstance(param1Class1, 0).getClass().getName().substring(1);
/* 1681 */       this.wrapperClassName = ProxyGenerator.dotToSlash(param1Class2.getName());
/* 1682 */       this.wrapperValueOfDesc = "(" + this.baseTypeString + ")L" + this.wrapperClassName + ";";
/*      */       
/* 1684 */       this.unwrapMethodName = param1Class1.getName() + "Value";
/* 1685 */       this.unwrapMethodDesc = "()" + this.baseTypeString;
/*      */     }
/*      */     
/*      */     public static PrimitiveTypeInfo get(Class<?> param1Class) {
/* 1689 */       return table.get(param1Class);
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
/*      */   private static class ConstantPool
/*      */   {
/* 1719 */     private List<Entry> pool = new ArrayList<>(32);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1727 */     private Map<Object, Short> map = new HashMap<>(16);
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean readOnly = false;
/*      */ 
/*      */ 
/*      */     
/*      */     public short getUtf8(String param1String) {
/* 1736 */       if (param1String == null) {
/* 1737 */         throw new NullPointerException();
/*      */       }
/* 1739 */       return getValue(param1String);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public short getInteger(int param1Int) {
/* 1746 */       return getValue(new Integer(param1Int));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public short getFloat(float param1Float) {
/* 1753 */       return getValue(new Float(param1Float));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public short getClass(String param1String) {
/* 1760 */       short s = getUtf8(param1String);
/* 1761 */       return getIndirect(new IndirectEntry(7, s));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public short getString(String param1String) {
/* 1769 */       short s = getUtf8(param1String);
/* 1770 */       return getIndirect(new IndirectEntry(8, s));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public short getFieldRef(String param1String1, String param1String2, String param1String3) {
/* 1780 */       short s1 = getClass(param1String1);
/* 1781 */       short s2 = getNameAndType(param1String2, param1String3);
/* 1782 */       return getIndirect(new IndirectEntry(9, s1, s2));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public short getMethodRef(String param1String1, String param1String2, String param1String3) {
/* 1792 */       short s1 = getClass(param1String1);
/* 1793 */       short s2 = getNameAndType(param1String2, param1String3);
/* 1794 */       return getIndirect(new IndirectEntry(10, s1, s2));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public short getInterfaceMethodRef(String param1String1, String param1String2, String param1String3) {
/* 1804 */       short s1 = getClass(param1String1);
/* 1805 */       short s2 = getNameAndType(param1String2, param1String3);
/* 1806 */       return getIndirect(new IndirectEntry(11, s1, s2));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public short getNameAndType(String param1String1, String param1String2) {
/* 1814 */       short s1 = getUtf8(param1String1);
/* 1815 */       short s2 = getUtf8(param1String2);
/* 1816 */       return getIndirect(new IndirectEntry(12, s1, s2));
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
/*      */     public void setReadOnly() {
/* 1828 */       this.readOnly = true;
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
/*      */     public void write(OutputStream param1OutputStream) throws IOException {
/* 1840 */       DataOutputStream dataOutputStream = new DataOutputStream(param1OutputStream);
/*      */ 
/*      */       
/* 1843 */       dataOutputStream.writeShort(this.pool.size() + 1);
/*      */       
/* 1845 */       for (Entry entry : this.pool) {
/* 1846 */         entry.write(dataOutputStream);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private short addEntry(Entry param1Entry) {
/* 1854 */       this.pool.add(param1Entry);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1860 */       if (this.pool.size() >= 65535) {
/* 1861 */         throw new IllegalArgumentException("constant pool size limit exceeded");
/*      */       }
/*      */       
/* 1864 */       return (short)this.pool.size();
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
/*      */     private short getValue(Object param1Object) {
/* 1879 */       Short short_ = this.map.get(param1Object);
/* 1880 */       if (short_ != null) {
/* 1881 */         return short_.shortValue();
/*      */       }
/* 1883 */       if (this.readOnly) {
/* 1884 */         throw new InternalError("late constant pool addition: " + param1Object);
/*      */       }
/*      */       
/* 1887 */       short s = addEntry(new ValueEntry(param1Object));
/* 1888 */       this.map.put(param1Object, new Short(s));
/* 1889 */       return s;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private short getIndirect(IndirectEntry param1IndirectEntry) {
/* 1898 */       Short short_ = this.map.get(param1IndirectEntry);
/* 1899 */       if (short_ != null) {
/* 1900 */         return short_.shortValue();
/*      */       }
/* 1902 */       if (this.readOnly) {
/* 1903 */         throw new InternalError("late constant pool addition");
/*      */       }
/* 1905 */       short s = addEntry(param1IndirectEntry);
/* 1906 */       this.map.put(param1IndirectEntry, new Short(s));
/* 1907 */       return s;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private ConstantPool() {}
/*      */ 
/*      */ 
/*      */     
/*      */     private static abstract class Entry
/*      */     {
/*      */       private Entry() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public abstract void write(DataOutputStream param2DataOutputStream) throws IOException;
/*      */     }
/*      */ 
/*      */     
/*      */     private static class ValueEntry
/*      */       extends Entry
/*      */     {
/*      */       private Object value;
/*      */ 
/*      */       
/*      */       public ValueEntry(Object param2Object) {
/* 1933 */         this.value = param2Object;
/*      */       }
/*      */       
/*      */       public void write(DataOutputStream param2DataOutputStream) throws IOException {
/* 1937 */         if (this.value instanceof String) {
/* 1938 */           param2DataOutputStream.writeByte(1);
/* 1939 */           param2DataOutputStream.writeUTF((String)this.value);
/* 1940 */         } else if (this.value instanceof Integer) {
/* 1941 */           param2DataOutputStream.writeByte(3);
/* 1942 */           param2DataOutputStream.writeInt(((Integer)this.value).intValue());
/* 1943 */         } else if (this.value instanceof Float) {
/* 1944 */           param2DataOutputStream.writeByte(4);
/* 1945 */           param2DataOutputStream.writeFloat(((Float)this.value).floatValue());
/* 1946 */         } else if (this.value instanceof Long) {
/* 1947 */           param2DataOutputStream.writeByte(5);
/* 1948 */           param2DataOutputStream.writeLong(((Long)this.value).longValue());
/* 1949 */         } else if (this.value instanceof Double) {
/* 1950 */           param2DataOutputStream.writeDouble(6.0D);
/* 1951 */           param2DataOutputStream.writeDouble(((Double)this.value).doubleValue());
/*      */         } else {
/* 1953 */           throw new InternalError("bogus value entry: " + this.value);
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static class IndirectEntry
/*      */       extends Entry
/*      */     {
/*      */       private int tag;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private short index0;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private short index1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public IndirectEntry(int param2Int, short param2Short) {
/* 1983 */         this.tag = param2Int;
/* 1984 */         this.index0 = param2Short;
/* 1985 */         this.index1 = 0;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public IndirectEntry(int param2Int, short param2Short1, short param2Short2) {
/* 1993 */         this.tag = param2Int;
/* 1994 */         this.index0 = param2Short1;
/* 1995 */         this.index1 = param2Short2;
/*      */       }
/*      */       
/*      */       public void write(DataOutputStream param2DataOutputStream) throws IOException {
/* 1999 */         param2DataOutputStream.writeByte(this.tag);
/* 2000 */         param2DataOutputStream.writeShort(this.index0);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2005 */         if (this.tag == 9 || this.tag == 10 || this.tag == 11 || this.tag == 12)
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 2010 */           param2DataOutputStream.writeShort(this.index1);
/*      */         }
/*      */       }
/*      */       
/*      */       public int hashCode() {
/* 2015 */         return this.tag + this.index0 + this.index1;
/*      */       }
/*      */       
/*      */       public boolean equals(Object param2Object) {
/* 2019 */         if (param2Object instanceof IndirectEntry) {
/* 2020 */           IndirectEntry indirectEntry = (IndirectEntry)param2Object;
/* 2021 */           if (this.tag == indirectEntry.tag && this.index0 == indirectEntry.index0 && this.index1 == indirectEntry.index1)
/*      */           {
/*      */             
/* 2024 */             return true;
/*      */           }
/*      */         } 
/* 2027 */         return false;
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/ProxyGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */