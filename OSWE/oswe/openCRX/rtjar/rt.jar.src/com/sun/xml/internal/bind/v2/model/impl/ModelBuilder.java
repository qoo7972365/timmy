/*     */ package com.sun.xml.internal.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.internal.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.internal.bind.util.Which;
/*     */ import com.sun.xml.internal.bind.v2.model.annotation.AnnotationReader;
/*     */ import com.sun.xml.internal.bind.v2.model.annotation.Locatable;
/*     */ import com.sun.xml.internal.bind.v2.model.core.ErrorHandler;
/*     */ import com.sun.xml.internal.bind.v2.model.core.NonElement;
/*     */ import com.sun.xml.internal.bind.v2.model.core.PropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.core.Ref;
/*     */ import com.sun.xml.internal.bind.v2.model.core.RegistryInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.core.TypeInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.core.TypeInfoSet;
/*     */ import com.sun.xml.internal.bind.v2.model.nav.Navigator;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationException;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlRegistry;
/*     */ import javax.xml.bind.annotation.XmlSchema;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelBuilder<T, C, F, M>
/*     */   implements ModelBuilderI<T, C, F, M>
/*     */ {
/*  89 */   private final Map<QName, TypeInfo> typeNames = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   final Map<String, RegistryInfoImpl<T, C, F, M>> registries = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   private final ErrorHandler proxyErrorHandler = new ErrorHandler() {
/*     */       public void error(IllegalAnnotationException e) {
/* 129 */         ModelBuilder.this.reportError(e);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelBuilder(AnnotationReader<T, C, F, M> reader, Navigator<T, C, F, M> navigator, Map<C, C> subclassReplacements, String defaultNamespaceRemap) {
/* 140 */     this.reader = reader;
/* 141 */     this.nav = navigator;
/* 142 */     this.subclassReplacements = subclassReplacements;
/* 143 */     if (defaultNamespaceRemap == null)
/* 144 */       defaultNamespaceRemap = ""; 
/* 145 */     this.defaultNsUri = defaultNamespaceRemap;
/* 146 */     reader.setErrorHandler(this.proxyErrorHandler);
/* 147 */     this.typeInfoSet = createTypeInfoSet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 156 */       XmlSchema s = null;
/* 157 */       s.location();
/* 158 */     } catch (NullPointerException nullPointerException) {
/*     */     
/* 160 */     } catch (NoSuchMethodError e) {
/*     */       Messages res;
/*     */       
/* 163 */       if (SecureLoader.getClassClassLoader(XmlSchema.class) == null) {
/* 164 */         res = Messages.INCOMPATIBLE_API_VERSION_MUSTANG;
/*     */       } else {
/* 166 */         res = Messages.INCOMPATIBLE_API_VERSION;
/*     */       } 
/*     */       
/* 169 */       throw new LinkageError(res.format(new Object[] {
/* 170 */               Which.which(XmlSchema.class), 
/* 171 */               Which.which(ModelBuilder.class)
/*     */             }));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 182 */       WhiteSpaceProcessor.isWhiteSpace("xyz");
/* 183 */     } catch (NoSuchMethodError e) {
/*     */       
/* 185 */       throw new LinkageError(Messages.RUNNING_WITH_1_0_RUNTIME.format(new Object[] {
/* 186 */               Which.which(WhiteSpaceProcessor.class), 
/* 187 */               Which.which(ModelBuilder.class)
/*     */             }));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 196 */   private static final Logger logger = Logger.getLogger(ModelBuilder.class.getName());
/*     */   final TypeInfoSetImpl<T, C, F, M> typeInfoSet;
/*     */   
/*     */   protected TypeInfoSetImpl<T, C, F, M> createTypeInfoSet() {
/* 200 */     return new TypeInfoSetImpl<>(this.nav, this.reader, BuiltinLeafInfoImpl.createLeaves(this.nav));
/*     */   }
/*     */   public final AnnotationReader<T, C, F, M> reader;
/*     */   public final Navigator<T, C, F, M> nav;
/*     */   public final String defaultNsUri;
/*     */   private final Map<C, C> subclassReplacements;
/*     */   private ErrorHandler errorHandler;
/*     */   private boolean hadError;
/*     */   public boolean hasSwaRef;
/*     */   private boolean linked;
/*     */   
/*     */   public NonElement<T, C> getClassInfo(C clazz, Locatable upstream) {
/* 212 */     return getClassInfo(clazz, false, upstream);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NonElement<T, C> getClassInfo(C clazz, boolean searchForSuperClass, Locatable upstream) {
/*     */     // Byte code:
/*     */     //   0: getstatic com/sun/xml/internal/bind/v2/model/impl/ModelBuilder.$assertionsDisabled : Z
/*     */     //   3: ifne -> 18
/*     */     //   6: aload_1
/*     */     //   7: ifnonnull -> 18
/*     */     //   10: new java/lang/AssertionError
/*     */     //   13: dup
/*     */     //   14: invokespecial <init> : ()V
/*     */     //   17: athrow
/*     */     //   18: aload_0
/*     */     //   19: getfield typeInfoSet : Lcom/sun/xml/internal/bind/v2/model/impl/TypeInfoSetImpl;
/*     */     //   22: aload_1
/*     */     //   23: invokevirtual getClassInfo : (Ljava/lang/Object;)Lcom/sun/xml/internal/bind/v2/model/core/NonElement;
/*     */     //   26: astore #4
/*     */     //   28: aload #4
/*     */     //   30: ifnull -> 36
/*     */     //   33: aload #4
/*     */     //   35: areturn
/*     */     //   36: aload_0
/*     */     //   37: getfield nav : Lcom/sun/xml/internal/bind/v2/model/nav/Navigator;
/*     */     //   40: aload_1
/*     */     //   41: invokeinterface isEnum : (Ljava/lang/Object;)Z
/*     */     //   46: ifeq -> 79
/*     */     //   49: aload_0
/*     */     //   50: aload_1
/*     */     //   51: aload_3
/*     */     //   52: invokevirtual createEnumLeafInfo : (Ljava/lang/Object;Lcom/sun/xml/internal/bind/v2/model/annotation/Locatable;)Lcom/sun/xml/internal/bind/v2/model/impl/EnumLeafInfoImpl;
/*     */     //   55: astore #5
/*     */     //   57: aload_0
/*     */     //   58: getfield typeInfoSet : Lcom/sun/xml/internal/bind/v2/model/impl/TypeInfoSetImpl;
/*     */     //   61: aload #5
/*     */     //   63: invokevirtual add : (Lcom/sun/xml/internal/bind/v2/model/impl/EnumLeafInfoImpl;)V
/*     */     //   66: aload #5
/*     */     //   68: astore #4
/*     */     //   70: aload_0
/*     */     //   71: aload #4
/*     */     //   73: invokespecial addTypeName : (Lcom/sun/xml/internal/bind/v2/model/core/NonElement;)V
/*     */     //   76: goto -> 367
/*     */     //   79: aload_0
/*     */     //   80: getfield subclassReplacements : Ljava/util/Map;
/*     */     //   83: aload_1
/*     */     //   84: invokeinterface containsKey : (Ljava/lang/Object;)Z
/*     */     //   89: istore #5
/*     */     //   91: iload #5
/*     */     //   93: ifeq -> 120
/*     */     //   96: iload_2
/*     */     //   97: ifne -> 120
/*     */     //   100: aload_0
/*     */     //   101: aload_0
/*     */     //   102: getfield subclassReplacements : Ljava/util/Map;
/*     */     //   105: aload_1
/*     */     //   106: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   111: aload_3
/*     */     //   112: invokevirtual getClassInfo : (Ljava/lang/Object;Lcom/sun/xml/internal/bind/v2/model/annotation/Locatable;)Lcom/sun/xml/internal/bind/v2/model/core/NonElement;
/*     */     //   115: astore #4
/*     */     //   117: goto -> 367
/*     */     //   120: aload_0
/*     */     //   121: getfield reader : Lcom/sun/xml/internal/bind/v2/model/annotation/AnnotationReader;
/*     */     //   124: aload_1
/*     */     //   125: ldc javax/xml/bind/annotation/XmlTransient
/*     */     //   127: invokeinterface hasClassAnnotation : (Ljava/lang/Object;Ljava/lang/Class;)Z
/*     */     //   132: ifne -> 140
/*     */     //   135: iload #5
/*     */     //   137: ifeq -> 173
/*     */     //   140: aload_0
/*     */     //   141: aload_0
/*     */     //   142: getfield nav : Lcom/sun/xml/internal/bind/v2/model/nav/Navigator;
/*     */     //   145: aload_1
/*     */     //   146: invokeinterface getSuperClass : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   151: iload_2
/*     */     //   152: new com/sun/xml/internal/bind/v2/model/annotation/ClassLocatable
/*     */     //   155: dup
/*     */     //   156: aload_3
/*     */     //   157: aload_1
/*     */     //   158: aload_0
/*     */     //   159: getfield nav : Lcom/sun/xml/internal/bind/v2/model/nav/Navigator;
/*     */     //   162: invokespecial <init> : (Lcom/sun/xml/internal/bind/v2/model/annotation/Locatable;Ljava/lang/Object;Lcom/sun/xml/internal/bind/v2/model/nav/Navigator;)V
/*     */     //   165: invokevirtual getClassInfo : (Ljava/lang/Object;ZLcom/sun/xml/internal/bind/v2/model/annotation/Locatable;)Lcom/sun/xml/internal/bind/v2/model/core/NonElement;
/*     */     //   168: astore #4
/*     */     //   170: goto -> 367
/*     */     //   173: aload_0
/*     */     //   174: aload_1
/*     */     //   175: aload_3
/*     */     //   176: invokevirtual createClassInfo : (Ljava/lang/Object;Lcom/sun/xml/internal/bind/v2/model/annotation/Locatable;)Lcom/sun/xml/internal/bind/v2/model/impl/ClassInfoImpl;
/*     */     //   179: astore #6
/*     */     //   181: aload_0
/*     */     //   182: getfield typeInfoSet : Lcom/sun/xml/internal/bind/v2/model/impl/TypeInfoSetImpl;
/*     */     //   185: aload #6
/*     */     //   187: invokevirtual add : (Lcom/sun/xml/internal/bind/v2/model/impl/ClassInfoImpl;)V
/*     */     //   190: aload #6
/*     */     //   192: invokevirtual getProperties : ()Ljava/util/List;
/*     */     //   195: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   200: astore #7
/*     */     //   202: aload #7
/*     */     //   204: invokeinterface hasNext : ()Z
/*     */     //   209: ifeq -> 351
/*     */     //   212: aload #7
/*     */     //   214: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   219: checkcast com/sun/xml/internal/bind/v2/model/core/PropertyInfo
/*     */     //   222: astore #8
/*     */     //   224: aload #8
/*     */     //   226: invokeinterface kind : ()Lcom/sun/xml/internal/bind/v2/model/core/PropertyKind;
/*     */     //   231: getstatic com/sun/xml/internal/bind/v2/model/core/PropertyKind.REFERENCE : Lcom/sun/xml/internal/bind/v2/model/core/PropertyKind;
/*     */     //   234: if_acmpne -> 309
/*     */     //   237: aload_0
/*     */     //   238: aload_1
/*     */     //   239: aload #8
/*     */     //   241: checkcast com/sun/xml/internal/bind/v2/model/annotation/Locatable
/*     */     //   244: invokespecial addToRegistry : (Ljava/lang/Object;Lcom/sun/xml/internal/bind/v2/model/annotation/Locatable;)V
/*     */     //   247: aload_0
/*     */     //   248: aload #8
/*     */     //   250: invokespecial getParametrizedTypes : (Lcom/sun/xml/internal/bind/v2/model/core/PropertyInfo;)[Ljava/lang/Class;
/*     */     //   253: astore #9
/*     */     //   255: aload #9
/*     */     //   257: ifnull -> 309
/*     */     //   260: aload #9
/*     */     //   262: astore #10
/*     */     //   264: aload #10
/*     */     //   266: arraylength
/*     */     //   267: istore #11
/*     */     //   269: iconst_0
/*     */     //   270: istore #12
/*     */     //   272: iload #12
/*     */     //   274: iload #11
/*     */     //   276: if_icmpge -> 309
/*     */     //   279: aload #10
/*     */     //   281: iload #12
/*     */     //   283: aaload
/*     */     //   284: astore #13
/*     */     //   286: aload #13
/*     */     //   288: aload_1
/*     */     //   289: if_acmpeq -> 303
/*     */     //   292: aload_0
/*     */     //   293: aload #13
/*     */     //   295: aload #8
/*     */     //   297: checkcast com/sun/xml/internal/bind/v2/model/annotation/Locatable
/*     */     //   300: invokespecial addToRegistry : (Ljava/lang/Object;Lcom/sun/xml/internal/bind/v2/model/annotation/Locatable;)V
/*     */     //   303: iinc #12, 1
/*     */     //   306: goto -> 272
/*     */     //   309: aload #8
/*     */     //   311: invokeinterface ref : ()Ljava/util/Collection;
/*     */     //   316: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   321: astore #9
/*     */     //   323: aload #9
/*     */     //   325: invokeinterface hasNext : ()Z
/*     */     //   330: ifeq -> 348
/*     */     //   333: aload #9
/*     */     //   335: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   340: checkcast com/sun/xml/internal/bind/v2/model/core/TypeInfo
/*     */     //   343: astore #10
/*     */     //   345: goto -> 323
/*     */     //   348: goto -> 202
/*     */     //   351: aload #6
/*     */     //   353: invokevirtual getBaseClass : ()Lcom/sun/xml/internal/bind/v2/model/impl/ClassInfoImpl;
/*     */     //   356: pop
/*     */     //   357: aload #6
/*     */     //   359: astore #4
/*     */     //   361: aload_0
/*     */     //   362: aload #4
/*     */     //   364: invokespecial addTypeName : (Lcom/sun/xml/internal/bind/v2/model/core/NonElement;)V
/*     */     //   367: aload_0
/*     */     //   368: getfield reader : Lcom/sun/xml/internal/bind/v2/model/annotation/AnnotationReader;
/*     */     //   371: ldc javax/xml/bind/annotation/XmlSeeAlso
/*     */     //   373: aload_1
/*     */     //   374: aload_3
/*     */     //   375: invokeinterface getClassAnnotation : (Ljava/lang/Class;Ljava/lang/Object;Lcom/sun/xml/internal/bind/v2/model/annotation/Locatable;)Ljava/lang/annotation/Annotation;
/*     */     //   380: checkcast javax/xml/bind/annotation/XmlSeeAlso
/*     */     //   383: astore #5
/*     */     //   385: aload #5
/*     */     //   387: ifnull -> 445
/*     */     //   390: aload_0
/*     */     //   391: getfield reader : Lcom/sun/xml/internal/bind/v2/model/annotation/AnnotationReader;
/*     */     //   394: aload #5
/*     */     //   396: ldc 'value'
/*     */     //   398: invokeinterface getClassArrayValue : (Ljava/lang/annotation/Annotation;Ljava/lang/String;)[Ljava/lang/Object;
/*     */     //   403: astore #6
/*     */     //   405: aload #6
/*     */     //   407: arraylength
/*     */     //   408: istore #7
/*     */     //   410: iconst_0
/*     */     //   411: istore #8
/*     */     //   413: iload #8
/*     */     //   415: iload #7
/*     */     //   417: if_icmpge -> 445
/*     */     //   420: aload #6
/*     */     //   422: iload #8
/*     */     //   424: aaload
/*     */     //   425: astore #9
/*     */     //   427: aload_0
/*     */     //   428: aload #9
/*     */     //   430: aload #5
/*     */     //   432: checkcast com/sun/xml/internal/bind/v2/model/annotation/Locatable
/*     */     //   435: invokevirtual getTypeInfo : (Ljava/lang/Object;Lcom/sun/xml/internal/bind/v2/model/annotation/Locatable;)Lcom/sun/xml/internal/bind/v2/model/core/NonElement;
/*     */     //   438: pop
/*     */     //   439: iinc #8, 1
/*     */     //   442: goto -> 413
/*     */     //   445: aload #4
/*     */     //   447: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #221	-> 0
/*     */     //   #222	-> 18
/*     */     //   #223	-> 28
/*     */     //   #224	-> 33
/*     */     //   #226	-> 36
/*     */     //   #227	-> 49
/*     */     //   #228	-> 57
/*     */     //   #229	-> 66
/*     */     //   #230	-> 70
/*     */     //   #231	-> 76
/*     */     //   #232	-> 79
/*     */     //   #233	-> 91
/*     */     //   #235	-> 100
/*     */     //   #237	-> 120
/*     */     //   #239	-> 140
/*     */     //   #242	-> 173
/*     */     //   #243	-> 181
/*     */     //   #246	-> 190
/*     */     //   #247	-> 224
/*     */     //   #249	-> 237
/*     */     //   #250	-> 247
/*     */     //   #251	-> 255
/*     */     //   #252	-> 260
/*     */     //   #253	-> 286
/*     */     //   #254	-> 292
/*     */     //   #252	-> 303
/*     */     //   #260	-> 309
/*     */     //   #261	-> 345
/*     */     //   #262	-> 348
/*     */     //   #263	-> 351
/*     */     //   #265	-> 357
/*     */     //   #266	-> 361
/*     */     //   #272	-> 367
/*     */     //   #273	-> 385
/*     */     //   #274	-> 390
/*     */     //   #275	-> 427
/*     */     //   #274	-> 439
/*     */     //   #280	-> 445
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   57	19	5	li	Lcom/sun/xml/internal/bind/v2/model/impl/EnumLeafInfoImpl;
/*     */     //   286	17	13	prmzdClass	Ljava/lang/Class;
/*     */     //   255	54	9	prmzdClasses	[Ljava/lang/Class;
/*     */     //   224	124	8	p	Lcom/sun/xml/internal/bind/v2/model/core/PropertyInfo;
/*     */     //   181	186	6	ci	Lcom/sun/xml/internal/bind/v2/model/impl/ClassInfoImpl;
/*     */     //   91	276	5	isReplaced	Z
/*     */     //   427	12	9	t	Ljava/lang/Object;
/*     */     //   0	448	0	this	Lcom/sun/xml/internal/bind/v2/model/impl/ModelBuilder;
/*     */     //   0	448	1	clazz	Ljava/lang/Object;
/*     */     //   0	448	2	searchForSuperClass	Z
/*     */     //   0	448	3	upstream	Lcom/sun/xml/internal/bind/v2/model/annotation/Locatable;
/*     */     //   28	420	4	r	Lcom/sun/xml/internal/bind/v2/model/core/NonElement;
/*     */     //   385	63	5	sa	Ljavax/xml/bind/annotation/XmlSeeAlso;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   57	19	5	li	Lcom/sun/xml/internal/bind/v2/model/impl/EnumLeafInfoImpl<TT;TC;TF;TM;>;
/*     */     //   224	124	8	p	Lcom/sun/xml/internal/bind/v2/model/core/PropertyInfo<TT;TC;>;
/*     */     //   181	186	6	ci	Lcom/sun/xml/internal/bind/v2/model/impl/ClassInfoImpl<TT;TC;TF;TM;>;
/*     */     //   427	12	9	t	TT;
/*     */     //   0	448	0	this	Lcom/sun/xml/internal/bind/v2/model/impl/ModelBuilder<TT;TC;TF;TM;>;
/*     */     //   0	448	1	clazz	TC;
/*     */     //   28	420	4	r	Lcom/sun/xml/internal/bind/v2/model/core/NonElement<TT;TC;>;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addToRegistry(C clazz, Locatable p) {
/* 289 */     String pkg = this.nav.getPackageName(clazz);
/* 290 */     if (!this.registries.containsKey(pkg)) {
/*     */       
/* 292 */       C c = (C)this.nav.loadObjectFactory(clazz, pkg);
/* 293 */       if (c != null) {
/* 294 */         addRegistry(c, p);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Class[] getParametrizedTypes(PropertyInfo p) {
/*     */     try {
/* 305 */       Type pType = ((RuntimePropertyInfo)p).getIndividualType();
/* 306 */       if (pType instanceof ParameterizedType) {
/* 307 */         ParameterizedType prmzdType = (ParameterizedType)pType;
/* 308 */         if (prmzdType.getRawType() == JAXBElement.class) {
/* 309 */           Type[] actualTypes = prmzdType.getActualTypeArguments();
/* 310 */           Class[] result = new Class[actualTypes.length];
/* 311 */           for (int i = 0; i < actualTypes.length; i++) {
/* 312 */             result[i] = (Class)actualTypes[i];
/*     */           }
/* 314 */           return result;
/*     */         } 
/*     */       } 
/* 317 */     } catch (Exception e) {
/* 318 */       logger.log(Level.FINE, "Error in ModelBuilder.getParametrizedTypes. " + e.getMessage());
/*     */     } 
/* 320 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addTypeName(NonElement<T, C> r) {
/* 327 */     QName t = r.getTypeName();
/* 328 */     if (t == null)
/*     */       return; 
/* 330 */     TypeInfo old = (TypeInfo)this.typeNames.put(t, r);
/* 331 */     if (old != null)
/*     */     {
/* 333 */       reportError(new IllegalAnnotationException(Messages.CONFLICTING_XML_TYPE_MAPPING
/* 334 */             .format(new Object[] { r.getTypeName() }, ), (Locatable)old, (Locatable)r));
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
/*     */   public NonElement<T, C> getTypeInfo(T t, Locatable upstream) {
/* 347 */     NonElement<T, C> r = this.typeInfoSet.getTypeInfo(t);
/* 348 */     if (r != null) return r;
/*     */     
/* 350 */     if (this.nav.isArray(t)) {
/*     */       
/* 352 */       ArrayInfoImpl<T, C, F, M> ai = createArrayInfo(upstream, t);
/* 353 */       addTypeName((NonElement)ai);
/* 354 */       this.typeInfoSet.add(ai);
/* 355 */       return (NonElement)ai;
/*     */     } 
/*     */     
/* 358 */     C c = (C)this.nav.asDecl(t);
/* 359 */     assert c != null : t.toString() + " must be a leaf, but we failed to recognize it.";
/* 360 */     return getClassInfo(c, upstream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NonElement<T, C> getTypeInfo(Ref<T, C> ref) {
/* 368 */     assert !ref.valueList;
/* 369 */     C c = (C)this.nav.asDecl(ref.type);
/* 370 */     if (c != null && this.reader.getClassAnnotation(XmlRegistry.class, c, null) != null) {
/* 371 */       if (!this.registries.containsKey(this.nav.getPackageName(c)))
/* 372 */         addRegistry(c, null); 
/* 373 */       return null;
/*     */     } 
/* 375 */     return getTypeInfo((T)ref.type, null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected EnumLeafInfoImpl<T, C, F, M> createEnumLeafInfo(C clazz, Locatable upstream) {
/* 380 */     return new EnumLeafInfoImpl<>(this, upstream, clazz, (T)this.nav.use(clazz));
/*     */   }
/*     */   
/*     */   protected ClassInfoImpl<T, C, F, M> createClassInfo(C clazz, Locatable upstream) {
/* 384 */     return new ClassInfoImpl<>(this, upstream, clazz);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ElementInfoImpl<T, C, F, M> createElementInfo(RegistryInfoImpl<T, C, F, M> registryInfo, M m) throws IllegalAnnotationException {
/* 389 */     return new ElementInfoImpl<>(this, registryInfo, m);
/*     */   }
/*     */   
/*     */   protected ArrayInfoImpl<T, C, F, M> createArrayInfo(Locatable upstream, T arrayType) {
/* 393 */     return new ArrayInfoImpl<>(this, upstream, arrayType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RegistryInfo<T, C> addRegistry(C registryClass, Locatable upstream) {
/* 402 */     return new RegistryInfoImpl<>(this, upstream, registryClass);
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
/*     */   public RegistryInfo<T, C> getRegistry(String packageName) {
/* 414 */     return this.registries.get(packageName);
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
/*     */   public TypeInfoSet<T, C, F, M> link() {
/* 432 */     assert !this.linked;
/* 433 */     this.linked = true;
/*     */     
/* 435 */     for (ElementInfoImpl<T, C, F, M> ei : this.typeInfoSet.getAllElements()) {
/* 436 */       ei.link();
/*     */     }
/* 438 */     for (ClassInfoImpl ci : this.typeInfoSet.beans().values()) {
/* 439 */       ci.link();
/*     */     }
/* 441 */     for (EnumLeafInfoImpl li : this.typeInfoSet.enums().values()) {
/* 442 */       li.link();
/*     */     }
/* 444 */     if (this.hadError) {
/* 445 */       return null;
/*     */     }
/* 447 */     return this.typeInfoSet;
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
/*     */   public void setErrorHandler(ErrorHandler errorHandler) {
/* 463 */     this.errorHandler = errorHandler;
/*     */   }
/*     */   
/*     */   public final void reportError(IllegalAnnotationException e) {
/* 467 */     this.hadError = true;
/* 468 */     if (this.errorHandler != null)
/* 469 */       this.errorHandler.error(e); 
/*     */   }
/*     */   
/*     */   public boolean isReplaced(C sc) {
/* 473 */     return this.subclassReplacements.containsKey(sc);
/*     */   }
/*     */ 
/*     */   
/*     */   public Navigator<T, C, F, M> getNavigator() {
/* 478 */     return this.nav;
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotationReader<T, C, F, M> getReader() {
/* 483 */     return this.reader;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/impl/ModelBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */