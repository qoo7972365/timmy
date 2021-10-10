/*     */ package com.sun.corba.se.impl.ior;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.spi.activation.Activator;
/*     */ import com.sun.corba.se.spi.activation._ActivatorStub;
/*     */ import com.sun.corba.se.spi.activation._InitialNameServiceStub;
/*     */ import com.sun.corba.se.spi.activation._LocatorStub;
/*     */ import com.sun.corba.se.spi.activation._RepositoryStub;
/*     */ import com.sun.corba.se.spi.activation._ServerManagerStub;
/*     */ import com.sun.corba.se.spi.activation._ServerStub;
/*     */ import com.sun.corba.se.spi.ior.IORTypeCheckRegistry;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.omg.CosNaming.BindingIterator;
/*     */ import org.omg.CosNaming.NamingContext;
/*     */ import org.omg.CosNaming.NamingContextExt;
/*     */ import org.omg.CosNaming._BindingIteratorStub;
/*     */ import org.omg.CosNaming._NamingContextExtStub;
/*     */ import org.omg.CosNaming._NamingContextStub;
/*     */ import org.omg.DynamicAny.DynAny;
/*     */ import org.omg.DynamicAny.DynAnyFactory;
/*     */ import org.omg.DynamicAny.DynArray;
/*     */ import org.omg.DynamicAny.DynEnum;
/*     */ import org.omg.DynamicAny.DynFixed;
/*     */ import org.omg.DynamicAny.DynSequence;
/*     */ import org.omg.DynamicAny.DynStruct;
/*     */ import org.omg.DynamicAny.DynUnion;
/*     */ import org.omg.DynamicAny.DynValue;
/*     */ import org.omg.DynamicAny._DynAnyFactoryStub;
/*     */ import org.omg.DynamicAny._DynAnyStub;
/*     */ import org.omg.DynamicAny._DynArrayStub;
/*     */ import org.omg.DynamicAny._DynEnumStub;
/*     */ import org.omg.DynamicAny._DynFixedStub;
/*     */ import org.omg.DynamicAny._DynStructStub;
/*     */ import org.omg.DynamicAny._DynUnionStub;
/*     */ import org.omg.PortableServer.ServantActivator;
/*     */ import org.omg.PortableServer.ServantLocator;
/*     */ import org.omg.PortableServer._ServantActivatorStub;
/*     */ import org.omg.PortableServer._ServantLocatorStub;
/*     */ 
/*     */ public class IORTypeCheckRegistryImpl implements IORTypeCheckRegistry {
/*  44 */   private static final Set<String> builtinIorTypeNames = initBuiltinIorTypeNames(); private final Set<String> iorTypeNames;
/*     */   private ORB theOrb;
/*     */   
/*     */   public IORTypeCheckRegistryImpl(String paramString, ORB paramORB) {
/*  48 */     this.theOrb = paramORB;
/*  49 */     this.iorTypeNames = parseIorClassNameList(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidIORType(String paramString) {
/*  60 */     dprintTransport(".isValidIORType : iorClassName == " + paramString);
/*  61 */     return validateIorTypeByName(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean validateIorTypeByName(String paramString) {
/*  66 */     dprintTransport(".validateIorTypeByName : iorClassName == " + paramString);
/*     */ 
/*     */     
/*  69 */     boolean bool = checkIorTypeNames(paramString);
/*     */     
/*  71 */     if (!bool) {
/*  72 */       bool = checkBuiltinClassNames(paramString);
/*     */     }
/*     */     
/*  75 */     dprintTransport(".validateIorTypeByName : isValidType == " + bool);
/*  76 */     return bool;
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
/*     */   private boolean checkIorTypeNames(String paramString) {
/*  90 */     return (this.iorTypeNames != null && this.iorTypeNames.contains(paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkBuiltinClassNames(String paramString) {
/*  99 */     return builtinIorTypeNames.contains(paramString);
/*     */   }
/*     */   
/*     */   private Set<String> parseIorClassNameList(String paramString) {
/* 103 */     Set<?> set = null;
/* 104 */     if (paramString != null) {
/* 105 */       String[] arrayOfString = paramString.split(";");
/* 106 */       set = Collections.unmodifiableSet(new HashSet(
/* 107 */             Arrays.asList((Object[])arrayOfString)));
/* 108 */       if (this.theOrb.orbInitDebugFlag) {
/* 109 */         dprintConfiguredIorTypeNames();
/*     */       }
/*     */     } 
/* 112 */     return (Set)set;
/*     */   }
/*     */   
/*     */   private static Set<String> initBuiltinIorTypeNames() {
/* 116 */     Set<Class<?>> set = initBuiltInCorbaStubTypes();
/* 117 */     String[] arrayOfString = new String[set.size()];
/* 118 */     byte b = 0;
/* 119 */     for (Class<?> clazz : set) {
/* 120 */       arrayOfString[b++] = clazz.getName();
/*     */     }
/* 122 */     return Collections.unmodifiableSet(new HashSet<>(
/* 123 */           Arrays.asList(arrayOfString)));
/*     */   }
/*     */   
/*     */   private static Set<Class<?>> initBuiltInCorbaStubTypes() {
/* 127 */     Class[] arrayOfClass = { Activator.class, _ActivatorStub.class, _InitialNameServiceStub.class, _LocatorStub.class, _RepositoryStub.class, _ServerManagerStub.class, _ServerStub.class, BindingIterator.class, _BindingIteratorStub.class, NamingContextExt.class, _NamingContextExtStub.class, NamingContext.class, _NamingContextStub.class, DynAnyFactory.class, _DynAnyFactoryStub.class, DynAny.class, _DynAnyStub.class, DynArray.class, _DynArrayStub.class, DynEnum.class, _DynEnumStub.class, DynFixed.class, _DynFixedStub.class, DynSequence.class, _DynSequenceStub.class, DynStruct.class, _DynStructStub.class, DynUnion.class, _DynUnionStub.class, _DynValueStub.class, DynValue.class, ServantActivator.class, _ServantActivatorStub.class, ServantLocator.class, _ServantLocatorStub.class };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 163 */     return new HashSet<>(
/* 164 */         Arrays.asList(arrayOfClass));
/*     */   }
/*     */   
/*     */   private void dprintConfiguredIorTypeNames() {
/* 168 */     if (this.iorTypeNames != null) {
/* 169 */       for (String str : this.iorTypeNames) {
/* 170 */         ORBUtility.dprint(this, ".dprintConfiguredIorTypeNames: " + str);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void dprintTransport(String paramString) {
/* 176 */     if (this.theOrb.transportDebugFlag)
/* 177 */       ORBUtility.dprint(this, paramString); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/IORTypeCheckRegistryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */