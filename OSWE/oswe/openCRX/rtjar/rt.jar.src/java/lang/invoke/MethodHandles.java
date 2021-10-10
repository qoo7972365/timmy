/*      */ package java.lang.invoke;
/*      */ 
/*      */ import java.lang.invoke.BoundMethodHandle;
/*      */ import java.lang.invoke.DirectMethodHandle;
/*      */ import java.lang.invoke.InfoFromMemberName;
/*      */ import java.lang.invoke.LambdaForm;
/*      */ import java.lang.invoke.MemberName;
/*      */ import java.lang.invoke.MethodHandle;
/*      */ import java.lang.invoke.MethodHandleImpl;
/*      */ import java.lang.invoke.MethodHandleInfo;
/*      */ import java.lang.invoke.MethodHandleNatives;
/*      */ import java.lang.invoke.MethodHandleStatics;
/*      */ import java.lang.invoke.MethodHandles;
/*      */ import java.lang.invoke.MethodType;
/*      */ import java.lang.invoke.WrongMethodTypeException;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.lang.reflect.ReflectPermission;
/*      */ import java.security.Permission;
/*      */ import java.util.Arrays;
/*      */ import java.util.BitSet;
/*      */ import java.util.List;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import sun.invoke.util.ValueConversions;
/*      */ import sun.invoke.util.VerifyAccess;
/*      */ import sun.invoke.util.Wrapper;
/*      */ import sun.misc.VM;
/*      */ import sun.reflect.CallerSensitive;
/*      */ import sun.reflect.Reflection;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ import sun.security.util.SecurityConstants;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MethodHandles
/*      */ {
/*   65 */   private static final MemberName.Factory IMPL_NAMES = MemberName.getFactory(); static {
/*   66 */     MethodHandleImpl.initStatics();
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
/*      */   @CallerSensitive
/*      */   public static Lookup lookup() {
/*   94 */     return new Lookup(Reflection.getCallerClass());
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
/*      */   public static Lookup publicLookup() {
/*  118 */     return Lookup.PUBLIC_LOOKUP;
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
/*      */   public static <T extends java.lang.reflect.Member> T reflectAs(Class<T> paramClass, MethodHandle paramMethodHandle) {
/*  145 */     SecurityManager securityManager = System.getSecurityManager();
/*  146 */     if (securityManager != null) securityManager.checkPermission(ACCESS_PERMISSION); 
/*  147 */     Lookup lookup = Lookup.IMPL_LOOKUP;
/*  148 */     return lookup.revealDirect(paramMethodHandle).reflectAs(paramClass, lookup);
/*      */   }
/*      */   
/*  151 */   private static final Permission ACCESS_PERMISSION = new ReflectPermission("suppressAccessChecks");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class Lookup
/*      */   {
/*      */     private final Class<?> lookupClass;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final int allowedModes;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int PUBLIC = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int PRIVATE = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int PROTECTED = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final int PACKAGE = 8;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int ALL_MODES = 15;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int TRUSTED = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static int fixmods(int param1Int) {
/*  552 */       param1Int &= 0x7;
/*  553 */       return (param1Int != 0) ? param1Int : 8;
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
/*      */     public Class<?> lookupClass() {
/*  566 */       return this.lookupClass;
/*      */     }
/*      */ 
/*      */     
/*      */     private Class<?> lookupClassOrNull() {
/*  571 */       return (this.allowedModes == -1) ? null : this.lookupClass;
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
/*      */     public int lookupModes() {
/*  593 */       return this.allowedModes & 0xF;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Lookup(Class<?> param1Class) {
/*  602 */       this(param1Class, 15);
/*      */       
/*  604 */       checkUnprivilegedlookupClass(param1Class, 15);
/*      */     }
/*      */     
/*      */     private Lookup(Class<?> param1Class, int param1Int) {
/*  608 */       this.lookupClass = param1Class;
/*  609 */       this.allowedModes = param1Int;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Lookup in(Class<?> param1Class) {
/*  637 */       param1Class.getClass();
/*  638 */       if (this.allowedModes == -1)
/*  639 */         return new Lookup(param1Class, 15); 
/*  640 */       if (param1Class == this.lookupClass)
/*  641 */         return this; 
/*  642 */       int i = this.allowedModes & 0xB;
/*  643 */       if ((i & 0x8) != 0 && 
/*  644 */         !VerifyAccess.isSamePackage(this.lookupClass, param1Class)) {
/*  645 */         i &= 0xFFFFFFF5;
/*      */       }
/*      */       
/*  648 */       if ((i & 0x2) != 0 && 
/*  649 */         !VerifyAccess.isSamePackageMember(this.lookupClass, param1Class)) {
/*  650 */         i &= 0xFFFFFFFD;
/*      */       }
/*  652 */       if ((i & 0x1) != 0 && 
/*  653 */         !VerifyAccess.isClassAccessible(param1Class, this.lookupClass, this.allowedModes))
/*      */       {
/*      */         
/*  656 */         i = 0;
/*      */       }
/*  658 */       checkUnprivilegedlookupClass(param1Class, i);
/*  659 */       return new Lookup(param1Class, i);
/*      */     }
/*      */     
/*      */     static {
/*  663 */       MethodHandles.IMPL_NAMES.getClass();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  669 */     static final Lookup PUBLIC_LOOKUP = new Lookup(Object.class, 1);
/*      */ 
/*      */     
/*  672 */     static final Lookup IMPL_LOOKUP = new Lookup(Object.class, -1); private static final boolean ALLOW_NESTMATE_ACCESS = false;
/*      */     
/*      */     private static void checkUnprivilegedlookupClass(Class<?> param1Class, int param1Int) {
/*  675 */       String str = param1Class.getName();
/*  676 */       if (str.startsWith("java.lang.invoke.")) {
/*  677 */         throw MethodHandleStatics.newIllegalArgumentException("illegal lookupClass: " + param1Class);
/*      */       }
/*      */ 
/*      */       
/*  681 */       if (param1Int == 15 && param1Class.getClassLoader() == null && (
/*  682 */         str.startsWith("java.") || (str
/*  683 */         .startsWith("sun.") && 
/*  684 */         !str.startsWith("sun.invoke.") && 
/*  685 */         !str.equals("sun.reflect.ReflectionFactory")))) {
/*  686 */         throw MethodHandleStatics.newIllegalArgumentException("illegal lookupClass: " + param1Class);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  721 */       String str = this.lookupClass.getName();
/*  722 */       switch (this.allowedModes) {
/*      */         case 0:
/*  724 */           return str + "/noaccess";
/*      */         case 1:
/*  726 */           return str + "/public";
/*      */         case 9:
/*  728 */           return str + "/package";
/*      */         case 11:
/*  730 */           return str + "/private";
/*      */         case 15:
/*  732 */           return str;
/*      */         case -1:
/*  734 */           return "/trusted";
/*      */       } 
/*  736 */       str = str + "/" + Integer.toHexString(this.allowedModes);
/*  737 */       assert false : str;
/*  738 */       return str;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MethodHandle findStatic(Class<?> param1Class, String param1String, MethodType param1MethodType) throws NoSuchMethodException, IllegalAccessException {
/*  780 */       MemberName memberName = resolveOrFail((byte)6, param1Class, param1String, param1MethodType);
/*  781 */       return getDirectMethod((byte)6, param1Class, memberName, findBoundCallerClass(memberName));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MethodHandle findVirtual(Class<?> param1Class, String param1String, MethodType param1MethodType) throws NoSuchMethodException, IllegalAccessException {
/*  856 */       if (param1Class == MethodHandle.class) {
/*  857 */         MethodHandle methodHandle = findVirtualForMH(param1String, param1MethodType);
/*  858 */         if (methodHandle != null) return methodHandle; 
/*      */       } 
/*  860 */       byte b = param1Class.isInterface() ? 9 : 5;
/*  861 */       MemberName memberName = resolveOrFail(b, param1Class, param1String, param1MethodType);
/*  862 */       return getDirectMethod(b, param1Class, memberName, findBoundCallerClass(memberName));
/*      */     }
/*      */     
/*      */     private MethodHandle findVirtualForMH(String param1String, MethodType param1MethodType) {
/*  866 */       if ("invoke".equals(param1String))
/*  867 */         return MethodHandles.invoker(param1MethodType); 
/*  868 */       if ("invokeExact".equals(param1String))
/*  869 */         return MethodHandles.exactInvoker(param1MethodType); 
/*  870 */       assert !MemberName.isMethodHandleInvokeName(param1String);
/*  871 */       return null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MethodHandle findConstructor(Class<?> param1Class, MethodType param1MethodType) throws NoSuchMethodException, IllegalAccessException {
/*  920 */       if (param1Class.isArray()) {
/*  921 */         throw new NoSuchMethodException("no constructor for array class: " + param1Class.getName());
/*      */       }
/*  923 */       String str = "<init>";
/*  924 */       MemberName memberName = resolveOrFail((byte)8, param1Class, str, param1MethodType);
/*  925 */       return getDirectConstructor(param1Class, memberName);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MethodHandle findSpecial(Class<?> param1Class1, String param1String, MethodType param1MethodType, Class<?> param1Class2) throws NoSuchMethodException, IllegalAccessException {
/* 1002 */       checkSpecialCaller(param1Class2);
/* 1003 */       Lookup lookup = in(param1Class2);
/* 1004 */       MemberName memberName = lookup.resolveOrFail((byte)7, param1Class1, param1String, param1MethodType);
/* 1005 */       return lookup.getDirectMethod((byte)7, param1Class1, memberName, findBoundCallerClass(memberName));
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
/*      */     public MethodHandle findGetter(Class<?> param1Class1, String param1String, Class<?> param1Class2) throws NoSuchFieldException, IllegalAccessException {
/* 1026 */       MemberName memberName = resolveOrFail((byte)1, param1Class1, param1String, param1Class2);
/* 1027 */       return getDirectField((byte)1, param1Class1, memberName);
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
/*      */     public MethodHandle findSetter(Class<?> param1Class1, String param1String, Class<?> param1Class2) throws NoSuchFieldException, IllegalAccessException {
/* 1048 */       MemberName memberName = resolveOrFail((byte)3, param1Class1, param1String, param1Class2);
/* 1049 */       return getDirectField((byte)3, param1Class1, memberName);
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
/*      */     public MethodHandle findStaticGetter(Class<?> param1Class1, String param1String, Class<?> param1Class2) throws NoSuchFieldException, IllegalAccessException {
/* 1072 */       MemberName memberName = resolveOrFail((byte)2, param1Class1, param1String, param1Class2);
/* 1073 */       return getDirectField((byte)2, param1Class1, memberName);
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
/*      */     public MethodHandle findStaticSetter(Class<?> param1Class1, String param1String, Class<?> param1Class2) throws NoSuchFieldException, IllegalAccessException {
/* 1096 */       MemberName memberName = resolveOrFail((byte)4, param1Class1, param1String, param1Class2);
/* 1097 */       return getDirectField((byte)4, param1Class1, memberName);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MethodHandle bind(Object param1Object, String param1String, MethodType param1MethodType) throws NoSuchMethodException, IllegalAccessException {
/* 1149 */       Class<?> clazz = param1Object.getClass();
/* 1150 */       MemberName memberName = resolveOrFail((byte)7, clazz, param1String, param1MethodType);
/* 1151 */       MethodHandle methodHandle = getDirectMethodNoRestrict((byte)7, clazz, memberName, findBoundCallerClass(memberName));
/* 1152 */       return methodHandle.bindArgumentL(0, param1Object).setVarargs(memberName);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MethodHandle unreflect(Method param1Method) throws IllegalAccessException {
/* 1182 */       if (param1Method.getDeclaringClass() == MethodHandle.class) {
/* 1183 */         MethodHandle methodHandle = unreflectForMH(param1Method);
/* 1184 */         if (methodHandle != null) return methodHandle; 
/*      */       } 
/* 1186 */       MemberName memberName = new MemberName(param1Method);
/* 1187 */       byte b = memberName.getReferenceKind();
/* 1188 */       if (b == 7)
/* 1189 */         b = 5; 
/* 1190 */       assert memberName.isMethod();
/* 1191 */       Lookup lookup = param1Method.isAccessible() ? IMPL_LOOKUP : this;
/* 1192 */       return lookup.getDirectMethodNoSecurityManager(b, memberName.getDeclaringClass(), memberName, findBoundCallerClass(memberName));
/*      */     }
/*      */     
/*      */     private MethodHandle unreflectForMH(Method param1Method) {
/* 1196 */       if (MemberName.isMethodHandleInvokeName(param1Method.getName()))
/* 1197 */         return MethodHandleImpl.fakeMethodHandleInvoke(new MemberName(param1Method)); 
/* 1198 */       return null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MethodHandle unreflectSpecial(Method param1Method, Class<?> param1Class) throws IllegalAccessException {
/* 1231 */       checkSpecialCaller(param1Class);
/* 1232 */       Lookup lookup = in(param1Class);
/* 1233 */       MemberName memberName = new MemberName(param1Method, true);
/* 1234 */       assert memberName.isMethod();
/*      */       
/* 1236 */       return lookup.getDirectMethodNoSecurityManager((byte)7, memberName.getDeclaringClass(), memberName, findBoundCallerClass(memberName));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MethodHandle unreflectConstructor(Constructor<?> param1Constructor) throws IllegalAccessException {
/* 1264 */       MemberName memberName = new MemberName(param1Constructor);
/* 1265 */       assert memberName.isConstructor();
/* 1266 */       Lookup lookup = param1Constructor.isAccessible() ? IMPL_LOOKUP : this;
/* 1267 */       return lookup.getDirectConstructorNoSecurityManager(memberName.getDeclaringClass(), memberName);
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
/*      */     public MethodHandle unreflectGetter(Field param1Field) throws IllegalAccessException {
/* 1289 */       return unreflectField(param1Field, false);
/*      */     }
/*      */     private MethodHandle unreflectField(Field param1Field, boolean param1Boolean) throws IllegalAccessException {
/* 1292 */       MemberName memberName = new MemberName(param1Field, param1Boolean); assert false;
/* 1293 */       throw new AssertionError();
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
/*      */ 
/*      */ 
/*      */     
/*      */     public MethodHandle unreflectSetter(Field param1Field) throws IllegalAccessException {
/* 1319 */       return unreflectField(param1Field, true);
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
/*      */     public MethodHandleInfo revealDirect(MethodHandle param1MethodHandle) {
/* 1341 */       MemberName memberName = param1MethodHandle.internalMemberName();
/* 1342 */       if (memberName == null || (!memberName.isResolved() && !memberName.isMethodHandleInvoke()))
/* 1343 */         throw MethodHandleStatics.newIllegalArgumentException("not a direct method handle"); 
/* 1344 */       Class<?> clazz = memberName.getDeclaringClass();
/* 1345 */       byte b = memberName.getReferenceKind();
/* 1346 */       assert MethodHandleNatives.refKindIsValid(b);
/* 1347 */       if (b == 7 && !param1MethodHandle.isInvokeSpecial())
/*      */       {
/*      */ 
/*      */         
/* 1351 */         b = 5; } 
/* 1352 */       if (b == 5 && clazz.isInterface())
/*      */       {
/* 1354 */         b = 9;
/*      */       }
/*      */       try {
/* 1357 */         checkAccess(b, clazz, memberName);
/* 1358 */         checkSecurityManager(clazz, memberName);
/* 1359 */       } catch (IllegalAccessException illegalAccessException) {
/* 1360 */         throw new IllegalArgumentException(illegalAccessException);
/*      */       } 
/* 1362 */       if (this.allowedModes != -1 && memberName.isCallerSensitive()) {
/* 1363 */         Class<?> clazz1 = param1MethodHandle.internalCallerClass();
/* 1364 */         if (!hasPrivateAccess() || clazz1 != lookupClass()) {
/* 1365 */           throw new IllegalArgumentException("method handle is caller sensitive: " + clazz1);
/*      */         }
/*      */       } 
/* 1368 */       return new InfoFromMemberName(this, memberName, b);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     MemberName resolveOrFail(byte param1Byte, Class<?> param1Class1, String param1String, Class<?> param1Class2) throws NoSuchFieldException, IllegalAccessException {
/* 1374 */       checkSymbolicClass(param1Class1);
/* 1375 */       param1String.getClass();
/* 1376 */       param1Class2.getClass();
/* 1377 */       return MethodHandles.IMPL_NAMES.resolveOrFail(param1Byte, new MemberName(param1Class1, param1String, param1Class2, param1Byte), lookupClassOrNull(), NoSuchFieldException.class);
/*      */     }
/*      */ 
/*      */     
/*      */     MemberName resolveOrFail(byte param1Byte, Class<?> param1Class, String param1String, MethodType param1MethodType) throws NoSuchMethodException, IllegalAccessException {
/* 1382 */       checkSymbolicClass(param1Class);
/* 1383 */       param1String.getClass();
/* 1384 */       param1MethodType.getClass();
/* 1385 */       checkMethodName(param1Byte, param1String);
/* 1386 */       return MethodHandles.IMPL_NAMES.resolveOrFail(param1Byte, new MemberName(param1Class, param1String, param1MethodType, param1Byte), lookupClassOrNull(), NoSuchMethodException.class);
/*      */     }
/*      */ 
/*      */     
/*      */     MemberName resolveOrFail(byte param1Byte, MemberName param1MemberName) throws ReflectiveOperationException {
/* 1391 */       checkSymbolicClass(param1MemberName.getDeclaringClass());
/* 1392 */       param1MemberName.getName().getClass();
/* 1393 */       param1MemberName.getType().getClass();
/* 1394 */       return MethodHandles.IMPL_NAMES.resolveOrFail(param1Byte, param1MemberName, lookupClassOrNull(), ReflectiveOperationException.class);
/*      */     }
/*      */ 
/*      */     
/*      */     void checkSymbolicClass(Class<?> param1Class) throws IllegalAccessException {
/* 1399 */       param1Class.getClass();
/* 1400 */       Class<?> clazz = lookupClassOrNull();
/* 1401 */       if (clazz != null && !VerifyAccess.isClassAccessible(param1Class, clazz, this.allowedModes)) {
/* 1402 */         throw (new MemberName(param1Class)).makeAccessException("symbolic reference class is not public", this);
/*      */       }
/*      */     }
/*      */     
/*      */     void checkMethodName(byte param1Byte, String param1String) throws NoSuchMethodException {
/* 1407 */       if (param1String.startsWith("<") && param1Byte != 8) {
/* 1408 */         throw new NoSuchMethodException("illegal method name: " + param1String);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Class<?> findBoundCallerClass(MemberName param1MemberName) throws IllegalAccessException {
/* 1418 */       Class<?> clazz = null;
/* 1419 */       if (MethodHandleNatives.isCallerSensitive(param1MemberName))
/*      */       {
/* 1421 */         if (hasPrivateAccess()) {
/* 1422 */           clazz = this.lookupClass;
/*      */         } else {
/* 1424 */           throw new IllegalAccessException("Attempt to lookup caller-sensitive method using restricted lookup object");
/*      */         } 
/*      */       }
/* 1427 */       return clazz;
/*      */     }
/*      */     
/*      */     private boolean hasPrivateAccess() {
/* 1431 */       return ((this.allowedModes & 0x2) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void checkSecurityManager(Class<?> param1Class, MemberName param1MemberName) {
/* 1440 */       SecurityManager securityManager = System.getSecurityManager();
/* 1441 */       if (securityManager == null)
/* 1442 */         return;  if (this.allowedModes == -1) {
/*      */         return;
/*      */       }
/* 1445 */       boolean bool = hasPrivateAccess();
/* 1446 */       if (!bool || 
/* 1447 */         !VerifyAccess.classLoaderIsAncestor(this.lookupClass, param1Class)) {
/* 1448 */         ReflectUtil.checkPackageAccess(param1Class);
/*      */       }
/*      */ 
/*      */       
/* 1452 */       if (param1MemberName.isPublic())
/* 1453 */         return;  if (!bool) {
/* 1454 */         securityManager.checkPermission(SecurityConstants.CHECK_MEMBER_ACCESS_PERMISSION);
/*      */       }
/*      */ 
/*      */       
/* 1458 */       Class<?> clazz = param1MemberName.getDeclaringClass();
/* 1459 */       if (!bool && clazz != param1Class)
/* 1460 */         ReflectUtil.checkPackageAccess(clazz); 
/*      */     }
/*      */     
/*      */     void checkMethod(byte param1Byte, Class<?> param1Class, MemberName param1MemberName) throws IllegalAccessException {
/*      */       String str;
/* 1465 */       boolean bool = (param1Byte == 6);
/*      */       
/* 1467 */       if (param1MemberName.isConstructor()) {
/* 1468 */         str = "expected a method, not a constructor";
/* 1469 */       } else if (!param1MemberName.isMethod()) {
/* 1470 */         str = "expected a method";
/* 1471 */       } else if (bool != param1MemberName.isStatic()) {
/* 1472 */         str = bool ? "expected a static method" : "expected a non-static method";
/*      */       } else {
/* 1474 */         checkAccess(param1Byte, param1Class, param1MemberName); return;
/* 1475 */       }  throw param1MemberName.makeAccessException(str, this);
/*      */     }
/*      */     void checkField(byte param1Byte, Class<?> param1Class, MemberName param1MemberName) throws IllegalAccessException {
/*      */       String str;
/* 1479 */       boolean bool = !MethodHandleNatives.refKindHasReceiver(param1Byte);
/*      */       
/* 1481 */       if (bool != param1MemberName.isStatic()) {
/* 1482 */         str = bool ? "expected a static field" : "expected a non-static field";
/*      */       } else {
/* 1484 */         checkAccess(param1Byte, param1Class, param1MemberName); return;
/* 1485 */       }  throw param1MemberName.makeAccessException(str, this);
/*      */     }
/*      */ 
/*      */     
/*      */     void checkAccess(byte param1Byte, Class<?> param1Class, MemberName param1MemberName) throws IllegalAccessException {
/* 1490 */       assert param1MemberName.referenceKindIsConsistentWith(param1Byte) && 
/* 1491 */         MethodHandleNatives.refKindIsValid(param1Byte) && 
/* 1492 */         MethodHandleNatives.refKindIsField(param1Byte) == param1MemberName.isField();
/* 1493 */       int i = this.allowedModes;
/* 1494 */       if (i == -1)
/* 1495 */         return;  int j = param1MemberName.getModifiers();
/* 1496 */       if (Modifier.isProtected(j) && param1Byte == 5 && param1MemberName
/*      */         
/* 1498 */         .getDeclaringClass() == Object.class && param1MemberName
/* 1499 */         .getName().equals("clone") && param1Class
/* 1500 */         .isArray())
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
/*      */ 
/*      */         
/* 1514 */         j ^= 0x5;
/*      */       }
/* 1516 */       if (Modifier.isProtected(j) && param1Byte == 8)
/*      */       {
/* 1518 */         j ^= 0x4;
/*      */       }
/* 1520 */       if (Modifier.isFinal(j) && 
/* 1521 */         MethodHandleNatives.refKindIsSetter(param1Byte))
/* 1522 */         throw param1MemberName.makeAccessException("unexpected set of a final field", this); 
/* 1523 */       if (Modifier.isPublic(j) && Modifier.isPublic(param1Class.getModifiers()) && i != 0)
/*      */         return; 
/* 1525 */       int k = fixmods(j);
/* 1526 */       if ((k & i) != 0) {
/* 1527 */         if (VerifyAccess.isMemberAccessible(param1Class, param1MemberName.getDeclaringClass(), j, 
/* 1528 */             lookupClass(), i)) {
/*      */           return;
/*      */         }
/*      */       }
/* 1532 */       else if ((k & 0x4) != 0 && (i & 0x8) != 0 && 
/* 1533 */         VerifyAccess.isSamePackage(param1MemberName.getDeclaringClass(), lookupClass())) {
/*      */         return;
/*      */       } 
/* 1536 */       throw param1MemberName.makeAccessException(accessFailedMessage(param1Class, param1MemberName), this);
/*      */     }
/*      */     
/*      */     String accessFailedMessage(Class<?> param1Class, MemberName param1MemberName) {
/* 1540 */       Class<?> clazz = param1MemberName.getDeclaringClass();
/* 1541 */       int i = param1MemberName.getModifiers();
/*      */ 
/*      */ 
/*      */       
/* 1545 */       boolean bool = (Modifier.isPublic(clazz.getModifiers()) && (clazz == param1Class || Modifier.isPublic(param1Class.getModifiers()))) ? true : false;
/* 1546 */       if (!bool && (this.allowedModes & 0x8) != 0)
/*      */       {
/*      */         
/* 1549 */         bool = (VerifyAccess.isClassAccessible(clazz, lookupClass(), 15) && (clazz == param1Class || VerifyAccess.isClassAccessible(param1Class, lookupClass(), 15))) ? true : false;
/*      */       }
/* 1551 */       if (!bool)
/* 1552 */         return "class is not public"; 
/* 1553 */       if (Modifier.isPublic(i))
/* 1554 */         return "access to public member failed"; 
/* 1555 */       if (Modifier.isPrivate(i))
/* 1556 */         return "member is private"; 
/* 1557 */       if (Modifier.isProtected(i))
/* 1558 */         return "member is protected"; 
/* 1559 */       return "member is private to package";
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void checkSpecialCaller(Class<?> param1Class) throws IllegalAccessException {
/* 1565 */       int i = this.allowedModes;
/* 1566 */       if (i == -1)
/* 1567 */         return;  if (!hasPrivateAccess() || param1Class != 
/* 1568 */         lookupClass())
/*      */       {
/*      */         
/* 1571 */         throw (new MemberName(param1Class))
/* 1572 */           .makeAccessException("no private access for invokespecial", this);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private boolean restrictProtectedReceiver(MemberName param1MemberName) {
/* 1578 */       if (param1MemberName.isProtected() && !param1MemberName.isStatic() && this.allowedModes != -1 && param1MemberName
/*      */         
/* 1580 */         .getDeclaringClass() != lookupClass() && 
/* 1581 */         !VerifyAccess.isSamePackage(param1MemberName.getDeclaringClass(), lookupClass()))
/*      */       {
/*      */ 
/*      */         
/* 1585 */         return true; } 
/*      */       return false;
/*      */     } private MethodHandle restrictReceiver(MemberName param1MemberName, DirectMethodHandle param1DirectMethodHandle, Class<?> param1Class) throws IllegalAccessException {
/* 1588 */       assert !param1MemberName.isStatic();
/*      */       
/* 1590 */       if (!param1MemberName.getDeclaringClass().isAssignableFrom(param1Class)) {
/* 1591 */         throw param1MemberName.makeAccessException("caller class must be a subclass below the method", param1Class);
/*      */       }
/* 1593 */       MethodType methodType1 = param1DirectMethodHandle.type();
/* 1594 */       if (methodType1.parameterType(0) == param1Class) return param1DirectMethodHandle; 
/* 1595 */       MethodType methodType2 = methodType1.changeParameterType(0, param1Class);
/* 1596 */       assert !param1DirectMethodHandle.isVarargsCollector();
/* 1597 */       assert param1DirectMethodHandle.viewAsTypeChecks(methodType2, true);
/* 1598 */       return param1DirectMethodHandle.copyWith(methodType2, param1DirectMethodHandle.form);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private MethodHandle getDirectMethod(byte param1Byte, Class<?> param1Class1, MemberName param1MemberName, Class<?> param1Class2) throws IllegalAccessException {
/* 1605 */       return getDirectMethodCommon(param1Byte, param1Class1, param1MemberName, true, true, param1Class2);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private MethodHandle getDirectMethodNoRestrict(byte param1Byte, Class<?> param1Class1, MemberName param1MemberName, Class<?> param1Class2) throws IllegalAccessException {
/* 1611 */       return getDirectMethodCommon(param1Byte, param1Class1, param1MemberName, true, false, param1Class2);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private MethodHandle getDirectMethodNoSecurityManager(byte param1Byte, Class<?> param1Class1, MemberName param1MemberName, Class<?> param1Class2) throws IllegalAccessException {
/* 1617 */       return getDirectMethodCommon(param1Byte, param1Class1, param1MemberName, false, true, param1Class2);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private MethodHandle getDirectMethodCommon(byte param1Byte, Class<?> param1Class1, MemberName param1MemberName, boolean param1Boolean1, boolean param1Boolean2, Class<?> param1Class2) throws IllegalAccessException {
/* 1623 */       checkMethod(param1Byte, param1Class1, param1MemberName);
/*      */       
/* 1625 */       if (param1Boolean1)
/* 1626 */         checkSecurityManager(param1Class1, param1MemberName); 
/* 1627 */       assert !param1MemberName.isMethodHandleInvoke();
/*      */       
/* 1629 */       if (param1Byte == 7 && param1Class1 != 
/* 1630 */         lookupClass() && 
/* 1631 */         !param1Class1.isInterface() && param1Class1 != 
/* 1632 */         lookupClass().getSuperclass() && param1Class1
/* 1633 */         .isAssignableFrom(lookupClass())) {
/* 1634 */         MemberName memberName; assert !param1MemberName.getName().equals("<init>");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1642 */         Class<?> clazz = lookupClass();
/*      */         
/*      */         do {
/* 1645 */           clazz = clazz.getSuperclass();
/*      */ 
/*      */           
/* 1648 */           memberName = new MemberName(clazz, param1MemberName.getName(), param1MemberName.getMethodType(), (byte)7);
/*      */           
/* 1650 */           memberName = MethodHandles.IMPL_NAMES.resolveOrNull(param1Byte, memberName, lookupClassOrNull());
/* 1651 */         } while (memberName == null && param1Class1 != clazz);
/*      */         
/* 1653 */         if (memberName == null) throw new InternalError(param1MemberName.toString()); 
/* 1654 */         param1MemberName = memberName;
/* 1655 */         param1Class1 = clazz;
/*      */         
/* 1657 */         checkMethod(param1Byte, param1Class1, param1MemberName);
/*      */       } 
/*      */       
/* 1660 */       DirectMethodHandle directMethodHandle = DirectMethodHandle.make(param1Byte, param1Class1, param1MemberName);
/* 1661 */       MethodHandle methodHandle = directMethodHandle;
/*      */       
/* 1663 */       if (param1Boolean2 && (param1Byte == 7 || (
/*      */         
/* 1665 */         MethodHandleNatives.refKindHasReceiver(param1Byte) && 
/* 1666 */         restrictProtectedReceiver(param1MemberName)))) {
/* 1667 */         methodHandle = restrictReceiver(param1MemberName, directMethodHandle, lookupClass());
/*      */       }
/* 1669 */       methodHandle = maybeBindCaller(param1MemberName, methodHandle, param1Class2);
/* 1670 */       methodHandle = methodHandle.setVarargs(param1MemberName);
/* 1671 */       return methodHandle;
/*      */     }
/*      */ 
/*      */     
/*      */     private MethodHandle maybeBindCaller(MemberName param1MemberName, MethodHandle param1MethodHandle, Class<?> param1Class) throws IllegalAccessException {
/* 1676 */       if (this.allowedModes == -1 || !MethodHandleNatives.isCallerSensitive(param1MemberName))
/* 1677 */         return param1MethodHandle; 
/* 1678 */       Class<?> clazz = this.lookupClass;
/* 1679 */       if (!hasPrivateAccess())
/* 1680 */         clazz = param1Class; 
/* 1681 */       return MethodHandleImpl.bindCaller(param1MethodHandle, clazz);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private MethodHandle getDirectField(byte param1Byte, Class<?> param1Class, MemberName param1MemberName) throws IllegalAccessException {
/* 1688 */       return getDirectFieldCommon(param1Byte, param1Class, param1MemberName, true);
/*      */     }
/*      */ 
/*      */     
/*      */     private MethodHandle getDirectFieldNoSecurityManager(byte param1Byte, Class<?> param1Class, MemberName param1MemberName) throws IllegalAccessException {
/* 1693 */       return getDirectFieldCommon(param1Byte, param1Class, param1MemberName, false);
/*      */     }
/*      */ 
/*      */     
/*      */     private MethodHandle getDirectFieldCommon(byte param1Byte, Class<?> param1Class, MemberName param1MemberName, boolean param1Boolean) throws IllegalAccessException {
/* 1698 */       checkField(param1Byte, param1Class, param1MemberName);
/*      */       
/* 1700 */       if (param1Boolean)
/* 1701 */         checkSecurityManager(param1Class, param1MemberName); 
/* 1702 */       DirectMethodHandle directMethodHandle = DirectMethodHandle.make(param1Class, param1MemberName);
/*      */       
/* 1704 */       boolean bool = (MethodHandleNatives.refKindHasReceiver(param1Byte) && restrictProtectedReceiver(param1MemberName)) ? true : false;
/* 1705 */       if (bool)
/* 1706 */         return restrictReceiver(param1MemberName, directMethodHandle, lookupClass()); 
/* 1707 */       return directMethodHandle;
/*      */     }
/*      */ 
/*      */     
/*      */     private MethodHandle getDirectConstructor(Class<?> param1Class, MemberName param1MemberName) throws IllegalAccessException {
/* 1712 */       return getDirectConstructorCommon(param1Class, param1MemberName, true);
/*      */     }
/*      */ 
/*      */     
/*      */     private MethodHandle getDirectConstructorNoSecurityManager(Class<?> param1Class, MemberName param1MemberName) throws IllegalAccessException {
/* 1717 */       return getDirectConstructorCommon(param1Class, param1MemberName, false);
/*      */     }
/*      */ 
/*      */     
/*      */     private MethodHandle getDirectConstructorCommon(Class<?> param1Class, MemberName param1MemberName, boolean param1Boolean) throws IllegalAccessException {
/* 1722 */       assert param1MemberName.isConstructor();
/* 1723 */       checkAccess((byte)8, param1Class, param1MemberName);
/*      */       
/* 1725 */       if (param1Boolean)
/* 1726 */         checkSecurityManager(param1Class, param1MemberName); 
/* 1727 */       assert !MethodHandleNatives.isCallerSensitive(param1MemberName);
/* 1728 */       return DirectMethodHandle.make(param1MemberName).setVarargs(param1MemberName);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     MethodHandle linkMethodHandleConstant(byte param1Byte, Class<?> param1Class, String param1String, Object param1Object) throws ReflectiveOperationException {
/* 1735 */       if (!(param1Object instanceof Class) && !(param1Object instanceof MethodType))
/* 1736 */         throw new InternalError("unresolved MemberName"); 
/* 1737 */       MemberName memberName1 = new MemberName(param1Byte, param1Class, param1String, param1Object);
/* 1738 */       MethodHandle methodHandle = LOOKASIDE_TABLE.get(memberName1);
/* 1739 */       if (methodHandle != null) {
/* 1740 */         checkSymbolicClass(param1Class);
/* 1741 */         return methodHandle;
/*      */       } 
/*      */       
/* 1744 */       if (param1Class == MethodHandle.class && param1Byte == 5) {
/* 1745 */         methodHandle = findVirtualForMH(memberName1.getName(), memberName1.getMethodType());
/* 1746 */         if (methodHandle != null) {
/* 1747 */           return methodHandle;
/*      */         }
/*      */       } 
/* 1750 */       MemberName memberName2 = resolveOrFail(param1Byte, memberName1);
/* 1751 */       methodHandle = getDirectMethodForConstant(param1Byte, param1Class, memberName2);
/* 1752 */       if (methodHandle instanceof DirectMethodHandle && 
/* 1753 */         canBeCached(param1Byte, param1Class, memberName2)) {
/* 1754 */         MemberName memberName = methodHandle.internalMemberName();
/* 1755 */         if (memberName != null) {
/* 1756 */           memberName = memberName.asNormalOriginal();
/*      */         }
/* 1758 */         if (memberName1.equals(memberName)) {
/* 1759 */           LOOKASIDE_TABLE.put(memberName, (DirectMethodHandle)methodHandle);
/*      */         }
/*      */       } 
/* 1762 */       return methodHandle;
/*      */     }
/*      */     
/*      */     private boolean canBeCached(byte param1Byte, Class<?> param1Class, MemberName param1MemberName) {
/* 1766 */       if (param1Byte == 7) {
/* 1767 */         return false;
/*      */       }
/* 1769 */       if (!Modifier.isPublic(param1Class.getModifiers()) || 
/* 1770 */         !Modifier.isPublic(param1MemberName.getDeclaringClass().getModifiers()) || 
/* 1771 */         !param1MemberName.isPublic() || param1MemberName
/* 1772 */         .isCallerSensitive()) {
/* 1773 */         return false;
/*      */       }
/* 1775 */       ClassLoader classLoader = param1Class.getClassLoader();
/* 1776 */       if (!VM.isSystemDomainLoader(classLoader)) {
/* 1777 */         ClassLoader classLoader1 = ClassLoader.getSystemClassLoader();
/* 1778 */         boolean bool = false;
/* 1779 */         while (classLoader1 != null) {
/* 1780 */           if (classLoader == classLoader1) { bool = true; break; }
/* 1781 */            classLoader1 = classLoader1.getParent();
/*      */         } 
/* 1783 */         if (!bool) {
/* 1784 */           return false;
/*      */         }
/*      */       } 
/*      */       try {
/* 1788 */         MemberName memberName = MethodHandles.publicLookup().resolveOrFail(param1Byte, new MemberName(param1Byte, param1Class, param1MemberName
/* 1789 */               .getName(), param1MemberName.getType()));
/* 1790 */         checkSecurityManager(param1Class, memberName);
/* 1791 */       } catch (ReflectiveOperationException|SecurityException reflectiveOperationException) {
/* 1792 */         return false;
/*      */       } 
/* 1794 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     private MethodHandle getDirectMethodForConstant(byte param1Byte, Class<?> param1Class, MemberName param1MemberName) throws ReflectiveOperationException {
/* 1799 */       if (MethodHandleNatives.refKindIsField(param1Byte))
/* 1800 */         return getDirectFieldNoSecurityManager(param1Byte, param1Class, param1MemberName); 
/* 1801 */       if (MethodHandleNatives.refKindIsMethod(param1Byte))
/* 1802 */         return getDirectMethodNoSecurityManager(param1Byte, param1Class, param1MemberName, this.lookupClass); 
/* 1803 */       if (param1Byte == 8) {
/* 1804 */         return getDirectConstructorNoSecurityManager(param1Class, param1MemberName);
/*      */       }
/*      */       
/* 1807 */       throw MethodHandleStatics.newIllegalArgumentException("bad MethodHandle constant #" + param1MemberName);
/*      */     }
/*      */     
/* 1810 */     static ConcurrentHashMap<MemberName, DirectMethodHandle> LOOKASIDE_TABLE = new ConcurrentHashMap<>();
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
/*      */   public static MethodHandle arrayElementGetter(Class<?> paramClass) throws IllegalArgumentException {
/* 1825 */     return MethodHandleImpl.makeArrayElementAccessor(paramClass, false);
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
/*      */   public static MethodHandle arrayElementSetter(Class<?> paramClass) throws IllegalArgumentException {
/* 1840 */     return MethodHandleImpl.makeArrayElementAccessor(paramClass, true);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MethodHandle spreadInvoker(MethodType paramMethodType, int paramInt) {
/* 1893 */     if (paramInt < 0 || paramInt > paramMethodType.parameterCount())
/* 1894 */       throw MethodHandleStatics.newIllegalArgumentException("bad argument count", Integer.valueOf(paramInt)); 
/* 1895 */     paramMethodType = paramMethodType.asSpreaderType(Object[].class, paramMethodType.parameterCount() - paramInt);
/* 1896 */     return paramMethodType.invokers().spreadInvoker(paramInt);
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
/*      */   public static MethodHandle exactInvoker(MethodType paramMethodType) {
/* 1936 */     return paramMethodType.invokers().exactInvoker();
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
/*      */   public static MethodHandle invoker(MethodType paramMethodType) {
/* 1975 */     return paramMethodType.invokers().genericInvoker();
/*      */   }
/*      */ 
/*      */   
/*      */   static MethodHandle basicInvoker(MethodType paramMethodType) {
/* 1980 */     return paramMethodType.invokers().basicInvoker();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MethodHandle explicitCastArguments(MethodHandle paramMethodHandle, MethodType paramMethodType) {
/* 2031 */     explicitCastArgumentsChecks(paramMethodHandle, paramMethodType);
/*      */     
/* 2033 */     MethodType methodType = paramMethodHandle.type();
/* 2034 */     if (methodType == paramMethodType) return paramMethodHandle; 
/* 2035 */     if (methodType.explicitCastEquivalentToAsType(paramMethodType)) {
/* 2036 */       return paramMethodHandle.asFixedArity().asType(paramMethodType);
/*      */     }
/* 2038 */     return MethodHandleImpl.makePairwiseConvert(paramMethodHandle, paramMethodType, false);
/*      */   }
/*      */   
/*      */   private static void explicitCastArgumentsChecks(MethodHandle paramMethodHandle, MethodType paramMethodType) {
/* 2042 */     if (paramMethodHandle.type().parameterCount() != paramMethodType.parameterCount()) {
/* 2043 */       throw new WrongMethodTypeException("cannot explicitly cast " + paramMethodHandle + " to " + paramMethodType);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MethodHandle permuteArguments(MethodHandle paramMethodHandle, MethodType paramMethodType, int... paramVarArgs) {
/* 2108 */     paramVarArgs = (int[])paramVarArgs.clone();
/* 2109 */     MethodType methodType = paramMethodHandle.type();
/* 2110 */     permuteArgumentChecks(paramVarArgs, paramMethodType, methodType);
/*      */     
/* 2112 */     int[] arrayOfInt = paramVarArgs;
/* 2113 */     BoundMethodHandle boundMethodHandle = paramMethodHandle.rebind();
/* 2114 */     LambdaForm lambdaForm = boundMethodHandle.form;
/* 2115 */     int i = paramMethodType.parameterCount();
/*      */ 
/*      */     
/*      */     int j;
/*      */     
/* 2120 */     while ((j = findFirstDupOrDrop(paramVarArgs, i)) != 0) {
/* 2121 */       if (j > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2128 */         int k = j, m = k, n = paramVarArgs[k];
/* 2129 */         boolean bool = false; int i1;
/* 2130 */         while ((i1 = paramVarArgs[--m]) != n) {
/*      */ 
/*      */           
/* 2133 */           if (n > i1) bool = true; 
/*      */         } 
/* 2135 */         if (!bool) {
/* 2136 */           k = m;
/* 2137 */           m = j;
/*      */         } 
/* 2139 */         lambdaForm = lambdaForm.editor().dupArgumentForm(1 + k, 1 + m);
/* 2140 */         assert paramVarArgs[k] == paramVarArgs[m];
/* 2141 */         methodType = methodType.dropParameterTypes(m, m + 1);
/*      */         
/* 2143 */         i1 = m + 1;
/* 2144 */         System.arraycopy(paramVarArgs, i1, paramVarArgs, m, paramVarArgs.length - i1);
/* 2145 */         paramVarArgs = Arrays.copyOf(paramVarArgs, paramVarArgs.length - 1);
/*      */       } else {
/* 2147 */         int k = j ^ 0xFFFFFFFF; byte b = 0;
/* 2148 */         while (b < paramVarArgs.length && paramVarArgs[b] < k)
/*      */         {
/*      */           
/* 2151 */           b++;
/*      */         }
/* 2153 */         Class<?> clazz = paramMethodType.parameterType(k);
/* 2154 */         lambdaForm = lambdaForm.editor().addArgumentForm(1 + b, LambdaForm.BasicType.basicType(clazz));
/* 2155 */         methodType = methodType.insertParameterTypes(b, new Class[] { clazz });
/*      */         
/* 2157 */         int m = b + 1;
/* 2158 */         paramVarArgs = Arrays.copyOf(paramVarArgs, paramVarArgs.length + 1);
/* 2159 */         System.arraycopy(paramVarArgs, b, paramVarArgs, m, paramVarArgs.length - m);
/* 2160 */         paramVarArgs[b] = k;
/*      */       } 
/* 2162 */       assert permuteArgumentChecks(paramVarArgs, paramMethodType, methodType);
/*      */     } 
/* 2164 */     assert paramVarArgs.length == i;
/*      */     
/* 2166 */     lambdaForm = lambdaForm.editor().permuteArgumentsForm(1, paramVarArgs);
/* 2167 */     if (paramMethodType == boundMethodHandle.type() && lambdaForm == boundMethodHandle.internalForm())
/* 2168 */       return boundMethodHandle; 
/* 2169 */     return boundMethodHandle.copyWith(paramMethodType, lambdaForm);
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
/*      */   private static int findFirstDupOrDrop(int[] paramArrayOfint, int paramInt) {
/* 2181 */     if (paramInt < 63) {
/* 2182 */       long l1 = 0L;
/* 2183 */       for (byte b = 0; b < paramArrayOfint.length; b++) {
/* 2184 */         int k = paramArrayOfint[b];
/* 2185 */         if (k >= paramInt) {
/* 2186 */           return paramArrayOfint.length;
/*      */         }
/* 2188 */         long l = 1L << k;
/* 2189 */         if ((l1 & l) != 0L) {
/* 2190 */           return b;
/*      */         }
/* 2192 */         l1 |= l;
/*      */       } 
/* 2194 */       if (l1 == (1L << paramInt) - 1L) {
/* 2195 */         assert Long.numberOfTrailingZeros(Long.lowestOneBit(l1 ^ 0xFFFFFFFFFFFFFFFFL)) == paramInt;
/* 2196 */         return 0;
/*      */       } 
/*      */       
/* 2199 */       long l2 = Long.lowestOneBit(l1 ^ 0xFFFFFFFFFFFFFFFFL);
/* 2200 */       int j = Long.numberOfTrailingZeros(l2);
/* 2201 */       assert j <= paramInt;
/* 2202 */       if (j == paramInt) {
/* 2203 */         return 0;
/*      */       }
/* 2205 */       return j ^ 0xFFFFFFFF;
/*      */     } 
/*      */     
/* 2208 */     BitSet bitSet = new BitSet(paramInt); int i;
/* 2209 */     for (i = 0; i < paramArrayOfint.length; i++) {
/* 2210 */       int j = paramArrayOfint[i];
/* 2211 */       if (j >= paramInt) {
/* 2212 */         return paramArrayOfint.length;
/*      */       }
/* 2214 */       if (bitSet.get(j)) {
/* 2215 */         return i;
/*      */       }
/* 2217 */       bitSet.set(j);
/*      */     } 
/* 2219 */     i = bitSet.nextClearBit(0);
/* 2220 */     assert i <= paramInt;
/* 2221 */     if (i == paramInt) {
/* 2222 */       return 0;
/*      */     }
/* 2224 */     return i ^ 0xFFFFFFFF;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean permuteArgumentChecks(int[] paramArrayOfint, MethodType paramMethodType1, MethodType paramMethodType2) {
/* 2229 */     if (paramMethodType1.returnType() != paramMethodType2.returnType()) {
/* 2230 */       throw MethodHandleStatics.newIllegalArgumentException("return types do not match", paramMethodType2, paramMethodType1);
/*      */     }
/* 2232 */     if (paramArrayOfint.length == paramMethodType2.parameterCount()) {
/* 2233 */       int i = paramMethodType1.parameterCount();
/* 2234 */       boolean bool = false;
/* 2235 */       for (byte b = 0; b < paramArrayOfint.length; b++) {
/* 2236 */         int j = paramArrayOfint[b];
/* 2237 */         if (j < 0 || j >= i) {
/* 2238 */           bool = true; break;
/*      */         } 
/* 2240 */         Class<?> clazz1 = paramMethodType1.parameterType(j);
/* 2241 */         Class<?> clazz2 = paramMethodType2.parameterType(b);
/* 2242 */         if (clazz1 != clazz2) {
/* 2243 */           throw MethodHandleStatics.newIllegalArgumentException("parameter types do not match after reorder", paramMethodType2, paramMethodType1);
/*      */         }
/*      */       } 
/* 2246 */       if (!bool) return true; 
/*      */     } 
/* 2248 */     throw MethodHandleStatics.newIllegalArgumentException("bad reorder array: " + Arrays.toString(paramArrayOfint));
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
/*      */   public static MethodHandle constant(Class<?> paramClass, Object paramObject) {
/* 2268 */     if (paramClass.isPrimitive()) {
/* 2269 */       if (paramClass == void.class)
/* 2270 */         throw MethodHandleStatics.newIllegalArgumentException("void type"); 
/* 2271 */       Wrapper wrapper = Wrapper.forPrimitiveType(paramClass);
/* 2272 */       paramObject = wrapper.convert(paramObject, paramClass);
/* 2273 */       if (wrapper.zero().equals(paramObject))
/* 2274 */         return zero(wrapper, paramClass); 
/* 2275 */       return insertArguments(identity(paramClass), 0, new Object[] { paramObject });
/*      */     } 
/* 2277 */     if (paramObject == null)
/* 2278 */       return zero(Wrapper.OBJECT, paramClass); 
/* 2279 */     return identity(paramClass).bindTo(paramObject);
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
/*      */   public static MethodHandle identity(Class<?> paramClass) {
/* 2292 */     Wrapper wrapper = paramClass.isPrimitive() ? Wrapper.forPrimitiveType(paramClass) : Wrapper.OBJECT;
/* 2293 */     int i = wrapper.ordinal();
/* 2294 */     MethodHandle methodHandle = IDENTITY_MHS[i];
/* 2295 */     if (methodHandle == null) {
/* 2296 */       methodHandle = setCachedMethodHandle(IDENTITY_MHS, i, makeIdentity(wrapper.primitiveType()));
/*      */     }
/* 2298 */     if (methodHandle.type().returnType() == paramClass) {
/* 2299 */       return methodHandle;
/*      */     }
/* 2301 */     assert wrapper == Wrapper.OBJECT;
/* 2302 */     return makeIdentity(paramClass);
/*      */   }
/* 2304 */   private static final MethodHandle[] IDENTITY_MHS = new MethodHandle[(Wrapper.values()).length];
/*      */   private static MethodHandle makeIdentity(Class<?> paramClass) {
/* 2306 */     MethodType methodType = MethodType.methodType(paramClass, paramClass);
/* 2307 */     LambdaForm lambdaForm = LambdaForm.identityForm(LambdaForm.BasicType.basicType(paramClass));
/* 2308 */     return MethodHandleImpl.makeIntrinsic(methodType, lambdaForm, MethodHandleImpl.Intrinsic.IDENTITY);
/*      */   }
/*      */   
/*      */   private static MethodHandle zero(Wrapper paramWrapper, Class<?> paramClass) {
/* 2312 */     int i = paramWrapper.ordinal();
/* 2313 */     MethodHandle methodHandle = ZERO_MHS[i];
/* 2314 */     if (methodHandle == null) {
/* 2315 */       methodHandle = setCachedMethodHandle(ZERO_MHS, i, makeZero(paramWrapper.primitiveType()));
/*      */     }
/* 2317 */     if (methodHandle.type().returnType() == paramClass)
/* 2318 */       return methodHandle; 
/* 2319 */     assert paramWrapper == Wrapper.OBJECT;
/* 2320 */     return makeZero(paramClass);
/*      */   }
/* 2322 */   private static final MethodHandle[] ZERO_MHS = new MethodHandle[(Wrapper.values()).length];
/*      */   private static MethodHandle makeZero(Class<?> paramClass) {
/* 2324 */     MethodType methodType = MethodType.methodType(paramClass);
/* 2325 */     LambdaForm lambdaForm = LambdaForm.zeroForm(LambdaForm.BasicType.basicType(paramClass));
/* 2326 */     return MethodHandleImpl.makeIntrinsic(methodType, lambdaForm, MethodHandleImpl.Intrinsic.ZERO);
/*      */   }
/*      */ 
/*      */   
/*      */   private static synchronized MethodHandle setCachedMethodHandle(MethodHandle[] paramArrayOfMethodHandle, int paramInt, MethodHandle paramMethodHandle) {
/* 2331 */     MethodHandle methodHandle = paramArrayOfMethodHandle[paramInt];
/* 2332 */     if (methodHandle != null) return methodHandle; 
/* 2333 */     paramArrayOfMethodHandle[paramInt] = paramMethodHandle; return paramMethodHandle;
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
/*      */   public static MethodHandle insertArguments(MethodHandle paramMethodHandle, int paramInt, Object... paramVarArgs) {
/* 2368 */     int i = paramVarArgs.length;
/* 2369 */     Class[] arrayOfClass = insertArgumentsChecks(paramMethodHandle, i, paramInt);
/* 2370 */     if (i == 0) return paramMethodHandle; 
/* 2371 */     BoundMethodHandle boundMethodHandle = paramMethodHandle.rebind();
/* 2372 */     for (byte b = 0; b < i; b++) {
/* 2373 */       Object object = paramVarArgs[b];
/* 2374 */       Class<?> clazz = arrayOfClass[paramInt + b];
/* 2375 */       if (clazz.isPrimitive()) {
/* 2376 */         boundMethodHandle = insertArgumentPrimitive(boundMethodHandle, paramInt, clazz, object);
/*      */       } else {
/* 2378 */         object = clazz.cast(object);
/* 2379 */         boundMethodHandle = boundMethodHandle.bindArgumentL(paramInt, object);
/*      */       } 
/*      */     } 
/* 2382 */     return boundMethodHandle;
/*      */   }
/*      */ 
/*      */   
/*      */   private static BoundMethodHandle insertArgumentPrimitive(BoundMethodHandle paramBoundMethodHandle, int paramInt, Class<?> paramClass, Object paramObject) {
/* 2387 */     Wrapper wrapper = Wrapper.forPrimitiveType(paramClass);
/*      */     
/* 2389 */     paramObject = wrapper.convert(paramObject, paramClass);
/* 2390 */     switch (wrapper) { case INT:
/* 2391 */         return paramBoundMethodHandle.bindArgumentI(paramInt, ((Integer)paramObject).intValue());
/* 2392 */       case LONG: return paramBoundMethodHandle.bindArgumentJ(paramInt, ((Long)paramObject).longValue());
/* 2393 */       case FLOAT: return paramBoundMethodHandle.bindArgumentF(paramInt, ((Float)paramObject).floatValue());
/* 2394 */       case DOUBLE: return paramBoundMethodHandle.bindArgumentD(paramInt, ((Double)paramObject).doubleValue()); }
/* 2395 */      return paramBoundMethodHandle.bindArgumentI(paramInt, ValueConversions.widenSubword(paramObject));
/*      */   }
/*      */ 
/*      */   
/*      */   private static Class<?>[] insertArgumentsChecks(MethodHandle paramMethodHandle, int paramInt1, int paramInt2) throws RuntimeException {
/* 2400 */     MethodType methodType = paramMethodHandle.type();
/* 2401 */     int i = methodType.parameterCount();
/* 2402 */     int j = i - paramInt1;
/* 2403 */     if (j < 0)
/* 2404 */       throw MethodHandleStatics.newIllegalArgumentException("too many values to insert"); 
/* 2405 */     if (paramInt2 < 0 || paramInt2 > j)
/* 2406 */       throw MethodHandleStatics.newIllegalArgumentException("no argument type to append"); 
/* 2407 */     return methodType.ptypes();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MethodHandle dropArguments(MethodHandle paramMethodHandle, int paramInt, List<Class<?>> paramList) {
/* 2454 */     paramList = copyTypes(paramList);
/* 2455 */     MethodType methodType1 = paramMethodHandle.type();
/* 2456 */     int i = dropArgumentChecks(methodType1, paramInt, paramList);
/* 2457 */     MethodType methodType2 = methodType1.insertParameterTypes(paramInt, paramList);
/* 2458 */     if (i == 0) return paramMethodHandle; 
/* 2459 */     BoundMethodHandle boundMethodHandle = paramMethodHandle.rebind();
/* 2460 */     LambdaForm lambdaForm = boundMethodHandle.form;
/* 2461 */     int j = 1 + paramInt;
/* 2462 */     for (Class<?> clazz : paramList) {
/* 2463 */       lambdaForm = lambdaForm.editor().addArgumentForm(j++, LambdaForm.BasicType.basicType(clazz));
/*      */     }
/* 2465 */     boundMethodHandle = boundMethodHandle.copyWith(methodType2, lambdaForm);
/* 2466 */     return boundMethodHandle;
/*      */   }
/*      */   
/*      */   private static List<Class<?>> copyTypes(List<Class<?>> paramList) {
/* 2470 */     Object[] arrayOfObject = paramList.toArray();
/* 2471 */     return Arrays.asList(Arrays.copyOf(arrayOfObject, arrayOfObject.length, (Class)Class[].class));
/*      */   }
/*      */   
/*      */   private static int dropArgumentChecks(MethodType paramMethodType, int paramInt, List<Class<?>> paramList) {
/* 2475 */     int i = paramList.size();
/* 2476 */     MethodType.checkSlotCount(i);
/* 2477 */     int j = paramMethodType.parameterCount();
/* 2478 */     int k = j + i;
/* 2479 */     if (paramInt < 0 || paramInt > j) {
/* 2480 */       throw MethodHandleStatics.newIllegalArgumentException("no argument type to remove" + 
/* 2481 */           Arrays.asList(new Object[] { paramMethodType, Integer.valueOf(paramInt), paramList, Integer.valueOf(k), Integer.valueOf(j) }));
/*      */     }
/* 2483 */     return i;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MethodHandle dropArguments(MethodHandle paramMethodHandle, int paramInt, Class<?>... paramVarArgs) {
/* 2535 */     return dropArguments(paramMethodHandle, paramInt, Arrays.asList(paramVarArgs));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MethodHandle filterArguments(MethodHandle paramMethodHandle, int paramInt, MethodHandle... paramVarArgs) {
/* 2605 */     filterArgumentsCheckArity(paramMethodHandle, paramInt, paramVarArgs);
/* 2606 */     MethodHandle methodHandle = paramMethodHandle;
/* 2607 */     int i = paramInt - 1;
/* 2608 */     for (MethodHandle methodHandle1 : paramVarArgs) {
/* 2609 */       i++;
/* 2610 */       if (methodHandle1 != null)
/* 2611 */         methodHandle = filterArgument(methodHandle, i, methodHandle1); 
/*      */     } 
/* 2613 */     return methodHandle;
/*      */   }
/*      */ 
/*      */   
/*      */   static MethodHandle filterArgument(MethodHandle paramMethodHandle1, int paramInt, MethodHandle paramMethodHandle2) {
/* 2618 */     filterArgumentChecks(paramMethodHandle1, paramInt, paramMethodHandle2);
/* 2619 */     MethodType methodType1 = paramMethodHandle1.type();
/* 2620 */     MethodType methodType2 = paramMethodHandle2.type();
/* 2621 */     BoundMethodHandle boundMethodHandle = paramMethodHandle1.rebind();
/* 2622 */     Class<?> clazz = methodType2.parameterType(0);
/* 2623 */     LambdaForm lambdaForm = boundMethodHandle.editor().filterArgumentForm(1 + paramInt, LambdaForm.BasicType.basicType(clazz));
/* 2624 */     MethodType methodType3 = methodType1.changeParameterType(paramInt, clazz);
/* 2625 */     boundMethodHandle = boundMethodHandle.copyWithExtendL(methodType3, lambdaForm, paramMethodHandle2);
/* 2626 */     return boundMethodHandle;
/*      */   }
/*      */   
/*      */   private static void filterArgumentsCheckArity(MethodHandle paramMethodHandle, int paramInt, MethodHandle[] paramArrayOfMethodHandle) {
/* 2630 */     MethodType methodType = paramMethodHandle.type();
/* 2631 */     int i = methodType.parameterCount();
/* 2632 */     if (paramInt + paramArrayOfMethodHandle.length > i)
/* 2633 */       throw MethodHandleStatics.newIllegalArgumentException("too many filters"); 
/*      */   }
/*      */   
/*      */   private static void filterArgumentChecks(MethodHandle paramMethodHandle1, int paramInt, MethodHandle paramMethodHandle2) throws RuntimeException {
/* 2637 */     MethodType methodType1 = paramMethodHandle1.type();
/* 2638 */     MethodType methodType2 = paramMethodHandle2.type();
/* 2639 */     if (methodType2.parameterCount() != 1 || methodType2
/* 2640 */       .returnType() != methodType1.parameterType(paramInt)) {
/* 2641 */       throw MethodHandleStatics.newIllegalArgumentException("target and filter types do not match", methodType1, methodType2);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MethodHandle collectArguments(MethodHandle paramMethodHandle1, int paramInt, MethodHandle paramMethodHandle2) {
/* 2752 */     MethodType methodType1 = collectArgumentsChecks(paramMethodHandle1, paramInt, paramMethodHandle2);
/* 2753 */     MethodType methodType2 = paramMethodHandle2.type();
/* 2754 */     BoundMethodHandle boundMethodHandle = paramMethodHandle1.rebind();
/*      */     
/* 2756 */     if (methodType2.returnType().isArray() && paramMethodHandle2.intrinsicName() == MethodHandleImpl.Intrinsic.NEW_ARRAY) {
/* 2757 */       LambdaForm lambdaForm1 = boundMethodHandle.editor().collectArgumentArrayForm(1 + paramInt, paramMethodHandle2);
/* 2758 */       if (lambdaForm1 != null) {
/* 2759 */         return boundMethodHandle.copyWith(methodType1, lambdaForm1);
/*      */       }
/*      */     } 
/* 2762 */     LambdaForm lambdaForm = boundMethodHandle.editor().collectArgumentsForm(1 + paramInt, methodType2.basicType());
/* 2763 */     return boundMethodHandle.copyWithExtendL(methodType1, lambdaForm, paramMethodHandle2);
/*      */   }
/*      */   
/*      */   private static MethodType collectArgumentsChecks(MethodHandle paramMethodHandle1, int paramInt, MethodHandle paramMethodHandle2) throws RuntimeException {
/* 2767 */     MethodType methodType1 = paramMethodHandle1.type();
/* 2768 */     MethodType methodType2 = paramMethodHandle2.type();
/* 2769 */     Class<?> clazz = methodType2.returnType();
/* 2770 */     List<Class<?>> list = methodType2.parameterList();
/* 2771 */     if (clazz == void.class) {
/* 2772 */       return methodType1.insertParameterTypes(paramInt, list);
/*      */     }
/* 2774 */     if (clazz != methodType1.parameterType(paramInt)) {
/* 2775 */       throw MethodHandleStatics.newIllegalArgumentException("target and filter types do not match", methodType1, methodType2);
/*      */     }
/* 2777 */     return methodType1.dropParameterTypes(paramInt, paramInt + 1).insertParameterTypes(paramInt, list);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MethodHandle filterReturnValue(MethodHandle paramMethodHandle1, MethodHandle paramMethodHandle2) {
/* 2839 */     MethodType methodType1 = paramMethodHandle1.type();
/* 2840 */     MethodType methodType2 = paramMethodHandle2.type();
/* 2841 */     filterReturnValueChecks(methodType1, methodType2);
/* 2842 */     BoundMethodHandle boundMethodHandle = paramMethodHandle1.rebind();
/* 2843 */     LambdaForm.BasicType basicType = LambdaForm.BasicType.basicType(methodType2.returnType());
/* 2844 */     LambdaForm lambdaForm = boundMethodHandle.editor().filterReturnForm(basicType, false);
/* 2845 */     MethodType methodType3 = methodType1.changeReturnType(methodType2.returnType());
/* 2846 */     boundMethodHandle = boundMethodHandle.copyWithExtendL(methodType3, lambdaForm, paramMethodHandle2);
/* 2847 */     return boundMethodHandle;
/*      */   }
/*      */   
/*      */   private static void filterReturnValueChecks(MethodType paramMethodType1, MethodType paramMethodType2) throws RuntimeException {
/* 2851 */     Class<?> clazz = paramMethodType1.returnType();
/* 2852 */     int i = paramMethodType2.parameterCount();
/* 2853 */     if ((i == 0) ? (clazz != void.class) : (clazz != paramMethodType2
/*      */       
/* 2855 */       .parameterType(0) || i != 1)) {
/* 2856 */       throw MethodHandleStatics.newIllegalArgumentException("target and filter types do not match", paramMethodType1, paramMethodType2);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MethodHandle foldArguments(MethodHandle paramMethodHandle1, MethodHandle paramMethodHandle2) {
/* 2937 */     byte b = 0;
/* 2938 */     MethodType methodType1 = paramMethodHandle1.type();
/* 2939 */     MethodType methodType2 = paramMethodHandle2.type();
/* 2940 */     Class<?> clazz = foldArgumentChecks(b, methodType1, methodType2);
/* 2941 */     BoundMethodHandle boundMethodHandle = paramMethodHandle1.rebind();
/* 2942 */     boolean bool = (clazz == void.class) ? true : false;
/*      */     
/* 2944 */     LambdaForm lambdaForm = boundMethodHandle.editor().foldArgumentsForm(1 + b, bool, methodType2.basicType());
/* 2945 */     MethodType methodType3 = methodType1;
/* 2946 */     if (!bool)
/* 2947 */       methodType3 = methodType3.dropParameterTypes(b, b + 1); 
/* 2948 */     boundMethodHandle = boundMethodHandle.copyWithExtendL(methodType3, lambdaForm, paramMethodHandle2);
/* 2949 */     return boundMethodHandle;
/*      */   }
/*      */   
/*      */   private static Class<?> foldArgumentChecks(int paramInt, MethodType paramMethodType1, MethodType paramMethodType2) {
/* 2953 */     int i = paramMethodType2.parameterCount();
/* 2954 */     Class<?> clazz = paramMethodType2.returnType();
/* 2955 */     byte b = (clazz == void.class) ? 0 : 1;
/* 2956 */     int j = paramInt + b;
/* 2957 */     boolean bool = (paramMethodType1.parameterCount() >= j + i) ? true : false;
/* 2958 */     if (bool && 
/* 2959 */       !paramMethodType2.parameterList().equals(paramMethodType1.parameterList().subList(j, j + i)))
/*      */     {
/* 2961 */       bool = false; } 
/* 2962 */     if (bool && b != 0 && paramMethodType2.returnType() != paramMethodType1.parameterType(0))
/* 2963 */       bool = false; 
/* 2964 */     if (!bool)
/* 2965 */       throw misMatchedTypes("target and combiner types", paramMethodType1, paramMethodType2); 
/* 2966 */     return clazz;
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
/*      */   public static MethodHandle guardWithTest(MethodHandle paramMethodHandle1, MethodHandle paramMethodHandle2, MethodHandle paramMethodHandle3) {
/* 3005 */     MethodType methodType1 = paramMethodHandle1.type();
/* 3006 */     MethodType methodType2 = paramMethodHandle2.type();
/* 3007 */     MethodType methodType3 = paramMethodHandle3.type();
/* 3008 */     if (!methodType2.equals(methodType3))
/* 3009 */       throw misMatchedTypes("target and fallback types", methodType2, methodType3); 
/* 3010 */     if (methodType1.returnType() != boolean.class)
/* 3011 */       throw MethodHandleStatics.newIllegalArgumentException("guard type is not a predicate " + methodType1); 
/* 3012 */     List<Class<?>> list1 = methodType2.parameterList();
/* 3013 */     List<Class<?>> list2 = methodType1.parameterList();
/* 3014 */     if (!list1.equals(list2)) {
/* 3015 */       int i = list2.size(), j = list1.size();
/* 3016 */       if (i >= j || !list1.subList(0, i).equals(list2))
/* 3017 */         throw misMatchedTypes("target and test types", methodType2, methodType1); 
/* 3018 */       paramMethodHandle1 = dropArguments(paramMethodHandle1, i, list1.subList(i, j));
/* 3019 */       methodType1 = paramMethodHandle1.type();
/*      */     } 
/* 3021 */     return MethodHandleImpl.makeGuardWithTest(paramMethodHandle1, paramMethodHandle2, paramMethodHandle3);
/*      */   }
/*      */   
/*      */   static RuntimeException misMatchedTypes(String paramString, MethodType paramMethodType1, MethodType paramMethodType2) {
/* 3025 */     return MethodHandleStatics.newIllegalArgumentException(paramString + " must match: " + paramMethodType1 + " != " + paramMethodType2);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MethodHandle catchException(MethodHandle paramMethodHandle1, Class<? extends Throwable> paramClass, MethodHandle paramMethodHandle2) {
/* 3075 */     MethodType methodType1 = paramMethodHandle1.type();
/* 3076 */     MethodType methodType2 = paramMethodHandle2.type();
/* 3077 */     if (methodType2.parameterCount() < 1 || 
/* 3078 */       !methodType2.parameterType(0).isAssignableFrom(paramClass))
/* 3079 */       throw MethodHandleStatics.newIllegalArgumentException("handler does not accept exception type " + paramClass); 
/* 3080 */     if (methodType2.returnType() != methodType1.returnType())
/* 3081 */       throw misMatchedTypes("target and handler return types", methodType1, methodType2); 
/* 3082 */     List<Class<?>> list1 = methodType1.parameterList();
/* 3083 */     List<Class<?>> list2 = methodType2.parameterList();
/* 3084 */     list2 = list2.subList(1, list2.size());
/* 3085 */     if (!list1.equals(list2)) {
/* 3086 */       int i = list2.size(), j = list1.size();
/* 3087 */       if (i >= j || !list1.subList(0, i).equals(list2))
/* 3088 */         throw misMatchedTypes("target and handler types", methodType1, methodType2); 
/* 3089 */       paramMethodHandle2 = dropArguments(paramMethodHandle2, 1 + i, list1.subList(i, j));
/* 3090 */       methodType2 = paramMethodHandle2.type();
/*      */     } 
/* 3092 */     return MethodHandleImpl.makeGuardWithCatch(paramMethodHandle1, paramClass, paramMethodHandle2);
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
/*      */   public static MethodHandle throwException(Class<?> paramClass, Class<? extends Throwable> paramClass1) {
/* 3109 */     if (!Throwable.class.isAssignableFrom(paramClass1))
/* 3110 */       throw new ClassCastException(paramClass1.getName()); 
/* 3111 */     return MethodHandleImpl.throwException(MethodType.methodType(paramClass, paramClass1));
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/MethodHandles.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */