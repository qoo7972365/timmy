/*     */ package com.sun.xml.internal.ws.model;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.bind.v2.model.annotation.AnnotationReader;
/*     */ import com.sun.xml.internal.bind.v2.model.nav.Navigator;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingHelper;
/*     */ import com.sun.xml.internal.ws.util.StringUtils;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.annotation.XmlAttachmentRef;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlList;
/*     */ import javax.xml.bind.annotation.XmlMimeType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractWrapperBeanGenerator<T, C, M, A extends Comparable>
/*     */ {
/*  58 */   private static final Logger LOGGER = Logger.getLogger(AbstractWrapperBeanGenerator.class.getName());
/*     */   
/*     */   private static final String RETURN = "return";
/*     */   
/*     */   private static final String EMTPY_NAMESPACE_ID = "";
/*  63 */   private static final Class[] jaxbAnns = new Class[] { XmlAttachmentRef.class, XmlMimeType.class, XmlJavaTypeAdapter.class, XmlList.class, XmlElement.class };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private static final Set<String> skipProperties = new HashSet<>();
/*     */   static {
/*  70 */     skipProperties.add("getCause");
/*  71 */     skipProperties.add("getLocalizedMessage");
/*  72 */     skipProperties.add("getClass");
/*  73 */     skipProperties.add("getStackTrace");
/*  74 */     skipProperties.add("getSuppressed");
/*     */   }
/*     */ 
/*     */   
/*     */   private final AnnotationReader<T, C, ?, M> annReader;
/*     */   private final Navigator<T, C, ?, M> nav;
/*     */   private final BeanMemberFactory<T, A> factory;
/*     */   
/*     */   protected AbstractWrapperBeanGenerator(AnnotationReader<T, C, ?, M> annReader, Navigator<T, C, ?, M> nav, BeanMemberFactory<T, A> factory) {
/*  83 */     this.annReader = annReader;
/*  84 */     this.nav = nav;
/*  85 */     this.factory = factory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Annotation> collectJAXBAnnotations(M method) {
/*  94 */     List<Annotation> jaxbAnnotation = new ArrayList<>();
/*  95 */     for (Class jaxbClass : jaxbAnns) {
/*  96 */       Annotation ann = this.annReader.getMethodAnnotation(jaxbClass, method, null);
/*  97 */       if (ann != null) {
/*  98 */         jaxbAnnotation.add(ann);
/*     */       }
/*     */     } 
/* 101 */     return jaxbAnnotation;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<Annotation> collectJAXBAnnotations(M method, int paramIndex) {
/* 106 */     List<Annotation> jaxbAnnotation = new ArrayList<>();
/* 107 */     for (Class jaxbClass : jaxbAnns) {
/* 108 */       Annotation ann = this.annReader.getMethodParameterAnnotation(jaxbClass, method, paramIndex, null);
/* 109 */       if (ann != null) {
/* 110 */         jaxbAnnotation.add(ann);
/*     */       }
/*     */     } 
/* 113 */     return jaxbAnnotation;
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
/*     */   
/*     */   public List<A> collectRequestBeanMembers(M method) {
/*     */     // Byte code:
/*     */     //   0: new java/util/ArrayList
/*     */     //   3: dup
/*     */     //   4: invokespecial <init> : ()V
/*     */     //   7: astore_2
/*     */     //   8: iconst_m1
/*     */     //   9: istore_3
/*     */     //   10: aload_0
/*     */     //   11: getfield nav : Lcom/sun/xml/internal/bind/v2/model/nav/Navigator;
/*     */     //   14: aload_1
/*     */     //   15: invokeinterface getMethodParameters : (Ljava/lang/Object;)[Ljava/lang/Object;
/*     */     //   20: astore #4
/*     */     //   22: aload #4
/*     */     //   24: arraylength
/*     */     //   25: istore #5
/*     */     //   27: iconst_0
/*     */     //   28: istore #6
/*     */     //   30: iload #6
/*     */     //   32: iload #5
/*     */     //   34: if_icmpge -> 265
/*     */     //   37: aload #4
/*     */     //   39: iload #6
/*     */     //   41: aaload
/*     */     //   42: astore #7
/*     */     //   44: iinc #3, 1
/*     */     //   47: aload_0
/*     */     //   48: getfield annReader : Lcom/sun/xml/internal/bind/v2/model/annotation/AnnotationReader;
/*     */     //   51: ldc javax/jws/WebParam
/*     */     //   53: aload_1
/*     */     //   54: iload_3
/*     */     //   55: aconst_null
/*     */     //   56: invokeinterface getMethodParameterAnnotation : (Ljava/lang/Class;Ljava/lang/Object;ILcom/sun/xml/internal/bind/v2/model/annotation/Locatable;)Ljava/lang/annotation/Annotation;
/*     */     //   61: checkcast javax/jws/WebParam
/*     */     //   64: astore #8
/*     */     //   66: aload #8
/*     */     //   68: ifnull -> 100
/*     */     //   71: aload #8
/*     */     //   73: invokeinterface header : ()Z
/*     */     //   78: ifne -> 259
/*     */     //   81: aload #8
/*     */     //   83: invokeinterface mode : ()Ljavax/jws/WebParam$Mode;
/*     */     //   88: getstatic javax/jws/WebParam$Mode.OUT : Ljavax/jws/WebParam$Mode;
/*     */     //   91: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   94: ifeq -> 100
/*     */     //   97: goto -> 259
/*     */     //   100: aload_0
/*     */     //   101: aload #7
/*     */     //   103: invokevirtual getHolderValueType : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   106: astore #9
/*     */     //   108: aload #9
/*     */     //   110: ifnull -> 118
/*     */     //   113: aload #9
/*     */     //   115: goto -> 124
/*     */     //   118: aload_0
/*     */     //   119: aload #7
/*     */     //   121: invokevirtual getSafeType : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   124: astore #10
/*     */     //   126: aload #8
/*     */     //   128: ifnull -> 154
/*     */     //   131: aload #8
/*     */     //   133: invokeinterface name : ()Ljava/lang/String;
/*     */     //   138: invokevirtual length : ()I
/*     */     //   141: ifle -> 154
/*     */     //   144: aload #8
/*     */     //   146: invokeinterface name : ()Ljava/lang/String;
/*     */     //   151: goto -> 173
/*     */     //   154: new java/lang/StringBuilder
/*     */     //   157: dup
/*     */     //   158: invokespecial <init> : ()V
/*     */     //   161: ldc 'arg'
/*     */     //   163: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   166: iload_3
/*     */     //   167: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*     */     //   170: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   173: astore #11
/*     */     //   175: aload #8
/*     */     //   177: ifnull -> 203
/*     */     //   180: aload #8
/*     */     //   182: invokeinterface targetNamespace : ()Ljava/lang/String;
/*     */     //   187: invokevirtual length : ()I
/*     */     //   190: ifle -> 203
/*     */     //   193: aload #8
/*     */     //   195: invokeinterface targetNamespace : ()Ljava/lang/String;
/*     */     //   200: goto -> 205
/*     */     //   203: ldc ''
/*     */     //   205: astore #12
/*     */     //   207: aload_0
/*     */     //   208: aload_1
/*     */     //   209: iload_3
/*     */     //   210: invokespecial collectJAXBAnnotations : (Ljava/lang/Object;I)Ljava/util/List;
/*     */     //   213: astore #13
/*     */     //   215: aload_0
/*     */     //   216: aload #13
/*     */     //   218: aload #11
/*     */     //   220: aload #12
/*     */     //   222: aload #10
/*     */     //   224: invokespecial processXmlElement : (Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
/*     */     //   227: aload_0
/*     */     //   228: getfield factory : Lcom/sun/xml/internal/ws/model/AbstractWrapperBeanGenerator$BeanMemberFactory;
/*     */     //   231: aload #10
/*     */     //   233: aload #11
/*     */     //   235: invokestatic getPropertyName : (Ljava/lang/String;)Ljava/lang/String;
/*     */     //   238: aload #13
/*     */     //   240: invokeinterface createWrapperBeanMember : (Ljava/lang/Object;Ljava/lang/String;Ljava/util/List;)Ljava/lang/Object;
/*     */     //   245: checkcast java/lang/Comparable
/*     */     //   248: astore #14
/*     */     //   250: aload_2
/*     */     //   251: aload #14
/*     */     //   253: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   258: pop
/*     */     //   259: iinc #6, 1
/*     */     //   262: goto -> 30
/*     */     //   265: aload_2
/*     */     //   266: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #138	-> 0
/*     */     //   #139	-> 8
/*     */     //   #141	-> 10
/*     */     //   #142	-> 44
/*     */     //   #143	-> 47
/*     */     //   #144	-> 66
/*     */     //   #145	-> 97
/*     */     //   #147	-> 100
/*     */     //   #153	-> 108
/*     */     //   #154	-> 126
/*     */     //   #155	-> 146
/*     */     //   #156	-> 175
/*     */     //   #157	-> 195
/*     */     //   #160	-> 207
/*     */     //   #163	-> 215
/*     */     //   #164	-> 227
/*     */     //   #165	-> 235
/*     */     //   #164	-> 240
/*     */     //   #166	-> 250
/*     */     //   #141	-> 259
/*     */     //   #168	-> 265
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   66	193	8	webParam	Ljavax/jws/WebParam;
/*     */     //   108	151	9	holderType	Ljava/lang/Object;
/*     */     //   126	133	10	paramType	Ljava/lang/Object;
/*     */     //   175	84	11	paramName	Ljava/lang/String;
/*     */     //   207	52	12	paramNamespace	Ljava/lang/String;
/*     */     //   215	44	13	jaxbAnnotation	Ljava/util/List;
/*     */     //   250	9	14	member	Ljava/lang/Comparable;
/*     */     //   44	215	7	param	Ljava/lang/Object;
/*     */     //   0	267	0	this	Lcom/sun/xml/internal/ws/model/AbstractWrapperBeanGenerator;
/*     */     //   0	267	1	method	Ljava/lang/Object;
/*     */     //   8	259	2	requestMembers	Ljava/util/List;
/*     */     //   10	257	3	paramIndex	I
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   108	151	9	holderType	TT;
/*     */     //   126	133	10	paramType	TT;
/*     */     //   215	44	13	jaxbAnnotation	Ljava/util/List<Ljava/lang/annotation/Annotation;>;
/*     */     //   250	9	14	member	TA;
/*     */     //   44	215	7	param	TT;
/*     */     //   0	267	0	this	Lcom/sun/xml/internal/ws/model/AbstractWrapperBeanGenerator<TT;TC;TM;TA;>;
/*     */     //   0	267	1	method	TM;
/*     */     //   8	259	2	requestMembers	Ljava/util/List<TA;>;
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
/*     */   public List<A> collectResponseBeanMembers(M method) {
/*     */     // Byte code:
/*     */     //   0: new java/util/ArrayList
/*     */     //   3: dup
/*     */     //   4: invokespecial <init> : ()V
/*     */     //   7: astore_2
/*     */     //   8: ldc 'return'
/*     */     //   10: astore_3
/*     */     //   11: ldc ''
/*     */     //   13: astore #4
/*     */     //   15: iconst_0
/*     */     //   16: istore #5
/*     */     //   18: aload_0
/*     */     //   19: getfield annReader : Lcom/sun/xml/internal/bind/v2/model/annotation/AnnotationReader;
/*     */     //   22: ldc javax/jws/WebResult
/*     */     //   24: aload_1
/*     */     //   25: aconst_null
/*     */     //   26: invokeinterface getMethodAnnotation : (Ljava/lang/Class;Ljava/lang/Object;Lcom/sun/xml/internal/bind/v2/model/annotation/Locatable;)Ljava/lang/annotation/Annotation;
/*     */     //   31: checkcast javax/jws/WebResult
/*     */     //   34: astore #6
/*     */     //   36: aload #6
/*     */     //   38: ifnull -> 93
/*     */     //   41: aload #6
/*     */     //   43: invokeinterface name : ()Ljava/lang/String;
/*     */     //   48: invokevirtual length : ()I
/*     */     //   51: ifle -> 62
/*     */     //   54: aload #6
/*     */     //   56: invokeinterface name : ()Ljava/lang/String;
/*     */     //   61: astore_3
/*     */     //   62: aload #6
/*     */     //   64: invokeinterface targetNamespace : ()Ljava/lang/String;
/*     */     //   69: invokevirtual length : ()I
/*     */     //   72: ifle -> 84
/*     */     //   75: aload #6
/*     */     //   77: invokeinterface targetNamespace : ()Ljava/lang/String;
/*     */     //   82: astore #4
/*     */     //   84: aload #6
/*     */     //   86: invokeinterface header : ()Z
/*     */     //   91: istore #5
/*     */     //   93: aload_0
/*     */     //   94: aload_0
/*     */     //   95: getfield nav : Lcom/sun/xml/internal/bind/v2/model/nav/Navigator;
/*     */     //   98: aload_1
/*     */     //   99: invokeinterface getReturnType : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   104: invokevirtual getSafeType : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   107: astore #7
/*     */     //   109: aload_0
/*     */     //   110: aload #7
/*     */     //   112: invokevirtual isVoidType : (Ljava/lang/Object;)Z
/*     */     //   115: ifne -> 165
/*     */     //   118: iload #5
/*     */     //   120: ifne -> 165
/*     */     //   123: aload_0
/*     */     //   124: aload_1
/*     */     //   125: invokespecial collectJAXBAnnotations : (Ljava/lang/Object;)Ljava/util/List;
/*     */     //   128: astore #8
/*     */     //   130: aload_0
/*     */     //   131: aload #8
/*     */     //   133: aload_3
/*     */     //   134: aload #4
/*     */     //   136: aload #7
/*     */     //   138: invokespecial processXmlElement : (Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
/*     */     //   141: aload_2
/*     */     //   142: aload_0
/*     */     //   143: getfield factory : Lcom/sun/xml/internal/ws/model/AbstractWrapperBeanGenerator$BeanMemberFactory;
/*     */     //   146: aload #7
/*     */     //   148: aload_3
/*     */     //   149: invokestatic getPropertyName : (Ljava/lang/String;)Ljava/lang/String;
/*     */     //   152: aload #8
/*     */     //   154: invokeinterface createWrapperBeanMember : (Ljava/lang/Object;Ljava/lang/String;Ljava/util/List;)Ljava/lang/Object;
/*     */     //   159: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   164: pop
/*     */     //   165: iconst_m1
/*     */     //   166: istore #8
/*     */     //   168: aload_0
/*     */     //   169: getfield nav : Lcom/sun/xml/internal/bind/v2/model/nav/Navigator;
/*     */     //   172: aload_1
/*     */     //   173: invokeinterface getMethodParameters : (Ljava/lang/Object;)[Ljava/lang/Object;
/*     */     //   178: astore #9
/*     */     //   180: aload #9
/*     */     //   182: arraylength
/*     */     //   183: istore #10
/*     */     //   185: iconst_0
/*     */     //   186: istore #11
/*     */     //   188: iload #11
/*     */     //   190: iload #10
/*     */     //   192: if_icmpge -> 397
/*     */     //   195: aload #9
/*     */     //   197: iload #11
/*     */     //   199: aaload
/*     */     //   200: astore #12
/*     */     //   202: iinc #8, 1
/*     */     //   205: aload_0
/*     */     //   206: aload #12
/*     */     //   208: invokevirtual getHolderValueType : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   211: astore #13
/*     */     //   213: aload_0
/*     */     //   214: getfield annReader : Lcom/sun/xml/internal/bind/v2/model/annotation/AnnotationReader;
/*     */     //   217: ldc javax/jws/WebParam
/*     */     //   219: aload_1
/*     */     //   220: iload #8
/*     */     //   222: aconst_null
/*     */     //   223: invokeinterface getMethodParameterAnnotation : (Ljava/lang/Class;Ljava/lang/Object;ILcom/sun/xml/internal/bind/v2/model/annotation/Locatable;)Ljava/lang/annotation/Annotation;
/*     */     //   228: checkcast javax/jws/WebParam
/*     */     //   231: astore #14
/*     */     //   233: aload #13
/*     */     //   235: ifnull -> 391
/*     */     //   238: aload #14
/*     */     //   240: ifnull -> 256
/*     */     //   243: aload #14
/*     */     //   245: invokeinterface header : ()Z
/*     */     //   250: ifeq -> 256
/*     */     //   253: goto -> 391
/*     */     //   256: aload #14
/*     */     //   258: ifnull -> 284
/*     */     //   261: aload #14
/*     */     //   263: invokeinterface name : ()Ljava/lang/String;
/*     */     //   268: invokevirtual length : ()I
/*     */     //   271: ifle -> 284
/*     */     //   274: aload #14
/*     */     //   276: invokeinterface name : ()Ljava/lang/String;
/*     */     //   281: goto -> 304
/*     */     //   284: new java/lang/StringBuilder
/*     */     //   287: dup
/*     */     //   288: invokespecial <init> : ()V
/*     */     //   291: ldc 'arg'
/*     */     //   293: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   296: iload #8
/*     */     //   298: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*     */     //   301: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   304: astore #15
/*     */     //   306: aload #14
/*     */     //   308: ifnull -> 334
/*     */     //   311: aload #14
/*     */     //   313: invokeinterface targetNamespace : ()Ljava/lang/String;
/*     */     //   318: invokevirtual length : ()I
/*     */     //   321: ifle -> 334
/*     */     //   324: aload #14
/*     */     //   326: invokeinterface targetNamespace : ()Ljava/lang/String;
/*     */     //   331: goto -> 336
/*     */     //   334: ldc ''
/*     */     //   336: astore #16
/*     */     //   338: aload_0
/*     */     //   339: aload_1
/*     */     //   340: iload #8
/*     */     //   342: invokespecial collectJAXBAnnotations : (Ljava/lang/Object;I)Ljava/util/List;
/*     */     //   345: astore #17
/*     */     //   347: aload_0
/*     */     //   348: aload #17
/*     */     //   350: aload #15
/*     */     //   352: aload #16
/*     */     //   354: aload #13
/*     */     //   356: invokespecial processXmlElement : (Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
/*     */     //   359: aload_0
/*     */     //   360: getfield factory : Lcom/sun/xml/internal/ws/model/AbstractWrapperBeanGenerator$BeanMemberFactory;
/*     */     //   363: aload #13
/*     */     //   365: aload #15
/*     */     //   367: invokestatic getPropertyName : (Ljava/lang/String;)Ljava/lang/String;
/*     */     //   370: aload #17
/*     */     //   372: invokeinterface createWrapperBeanMember : (Ljava/lang/Object;Ljava/lang/String;Ljava/util/List;)Ljava/lang/Object;
/*     */     //   377: checkcast java/lang/Comparable
/*     */     //   380: astore #18
/*     */     //   382: aload_2
/*     */     //   383: aload #18
/*     */     //   385: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   390: pop
/*     */     //   391: iinc #11, 1
/*     */     //   394: goto -> 188
/*     */     //   397: aload_2
/*     */     //   398: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #182	-> 0
/*     */     //   #185	-> 8
/*     */     //   #186	-> 11
/*     */     //   #187	-> 15
/*     */     //   #188	-> 18
/*     */     //   #189	-> 36
/*     */     //   #190	-> 41
/*     */     //   #191	-> 54
/*     */     //   #193	-> 62
/*     */     //   #194	-> 75
/*     */     //   #196	-> 84
/*     */     //   #198	-> 93
/*     */     //   #199	-> 109
/*     */     //   #200	-> 123
/*     */     //   #201	-> 130
/*     */     //   #202	-> 141
/*     */     //   #206	-> 165
/*     */     //   #207	-> 168
/*     */     //   #208	-> 202
/*     */     //   #210	-> 205
/*     */     //   #211	-> 213
/*     */     //   #212	-> 233
/*     */     //   #213	-> 253
/*     */     //   #216	-> 256
/*     */     //   #217	-> 276
/*     */     //   #218	-> 306
/*     */     //   #219	-> 326
/*     */     //   #220	-> 338
/*     */     //   #221	-> 347
/*     */     //   #222	-> 359
/*     */     //   #223	-> 367
/*     */     //   #222	-> 372
/*     */     //   #224	-> 382
/*     */     //   #207	-> 391
/*     */     //   #227	-> 397
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   130	35	8	jaxbRespAnnotations	Ljava/util/List;
/*     */     //   213	178	13	paramType	Ljava/lang/Object;
/*     */     //   233	158	14	webParam	Ljavax/jws/WebParam;
/*     */     //   306	85	15	paramName	Ljava/lang/String;
/*     */     //   338	53	16	paramNamespace	Ljava/lang/String;
/*     */     //   347	44	17	jaxbAnnotation	Ljava/util/List;
/*     */     //   382	9	18	member	Ljava/lang/Comparable;
/*     */     //   202	189	12	param	Ljava/lang/Object;
/*     */     //   0	399	0	this	Lcom/sun/xml/internal/ws/model/AbstractWrapperBeanGenerator;
/*     */     //   0	399	1	method	Ljava/lang/Object;
/*     */     //   8	391	2	responseMembers	Ljava/util/List;
/*     */     //   11	388	3	responseElementName	Ljava/lang/String;
/*     */     //   15	384	4	responseNamespace	Ljava/lang/String;
/*     */     //   18	381	5	isResultHeader	Z
/*     */     //   36	363	6	webResult	Ljavax/jws/WebResult;
/*     */     //   109	290	7	returnType	Ljava/lang/Object;
/*     */     //   168	231	8	paramIndex	I
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   130	35	8	jaxbRespAnnotations	Ljava/util/List<Ljava/lang/annotation/Annotation;>;
/*     */     //   213	178	13	paramType	TT;
/*     */     //   347	44	17	jaxbAnnotation	Ljava/util/List<Ljava/lang/annotation/Annotation;>;
/*     */     //   382	9	18	member	TA;
/*     */     //   202	189	12	param	TT;
/*     */     //   0	399	0	this	Lcom/sun/xml/internal/ws/model/AbstractWrapperBeanGenerator<TT;TC;TM;TA;>;
/*     */     //   0	399	1	method	TM;
/*     */     //   8	391	2	responseMembers	Ljava/util/List<TA;>;
/*     */     //   109	290	7	returnType	TT;
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
/*     */   private void processXmlElement(List<Annotation> jaxb, String elemName, String elemNS, T type) {
/* 231 */     XmlElement elemAnn = null;
/* 232 */     for (Annotation a : jaxb) {
/* 233 */       if (a.annotationType() == XmlElement.class) {
/* 234 */         elemAnn = (XmlElement)a;
/* 235 */         jaxb.remove(a);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 240 */     String name = (elemAnn != null && !elemAnn.name().equals("##default")) ? elemAnn.name() : elemName;
/*     */ 
/*     */     
/* 243 */     String ns = (elemAnn != null && !elemAnn.namespace().equals("##default")) ? elemAnn.namespace() : elemNS;
/*     */ 
/*     */     
/* 246 */     boolean nillable = (this.nav.isArray(type) || (elemAnn != null && elemAnn.nillable()));
/*     */     
/* 248 */     boolean required = (elemAnn != null && elemAnn.required());
/* 249 */     XmlElementHandler handler = new XmlElementHandler(name, ns, nillable, required);
/* 250 */     XmlElement elem = (XmlElement)Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { XmlElement.class }, handler);
/* 251 */     jaxb.add(elem);
/*     */   }
/*     */   
/*     */   private static class XmlElementHandler
/*     */     implements InvocationHandler
/*     */   {
/*     */     private String name;
/*     */     private String namespace;
/*     */     private boolean nillable;
/*     */     private boolean required;
/*     */     
/*     */     XmlElementHandler(String name, String namespace, boolean nillable, boolean required) {
/* 263 */       this.name = name;
/* 264 */       this.namespace = namespace;
/* 265 */       this.nillable = nillable;
/* 266 */       this.required = required;
/*     */     }
/*     */     
/*     */     public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 270 */       String methodName = method.getName();
/* 271 */       if (methodName.equals("name"))
/* 272 */         return this.name; 
/* 273 */       if (methodName.equals("namespace"))
/* 274 */         return this.namespace; 
/* 275 */       if (methodName.equals("nillable"))
/* 276 */         return Boolean.valueOf(this.nillable); 
/* 277 */       if (methodName.equals("required")) {
/* 278 */         return Boolean.valueOf(this.required);
/*     */       }
/* 280 */       throw new WebServiceException("Not handling " + methodName);
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
/*     */   public Collection<A> collectExceptionBeanMembers(C exception) {
/* 301 */     return collectExceptionBeanMembers(exception, true);
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
/*     */   public Collection<A> collectExceptionBeanMembers(C exception, boolean decapitalize) {
/* 322 */     TreeMap<String, A> fields = new TreeMap<>();
/* 323 */     getExceptionProperties(exception, fields, decapitalize);
/*     */ 
/*     */     
/* 326 */     XmlType xmlType = (XmlType)this.annReader.getClassAnnotation(XmlType.class, exception, null);
/* 327 */     if (xmlType != null) {
/* 328 */       String[] propOrder = xmlType.propOrder();
/*     */       
/* 330 */       if (propOrder.length > 0 && propOrder[0].length() != 0) {
/* 331 */         List<A> list = new ArrayList<>();
/* 332 */         for (String prop : propOrder) {
/* 333 */           Comparable comparable = (Comparable)fields.get(prop);
/* 334 */           if (comparable != null) {
/* 335 */             list.add((A)comparable);
/*     */           } else {
/* 337 */             throw new WebServiceException("Exception " + exception + " has @XmlType and its propOrder contains unknown property " + prop);
/*     */           } 
/*     */         } 
/*     */         
/* 341 */         return list;
/*     */       } 
/*     */     } 
/*     */     
/* 345 */     return fields.values();
/*     */   }
/*     */ 
/*     */   
/*     */   private void getExceptionProperties(C exception, TreeMap<String, A> fields, boolean decapitalize) {
/* 350 */     C sc = (C)this.nav.getSuperClass(exception);
/* 351 */     if (sc != null) {
/* 352 */       getExceptionProperties(sc, fields, decapitalize);
/*     */     }
/* 354 */     Collection<? extends M> methods = this.nav.getDeclaredMethods(exception);
/*     */     
/* 356 */     for (M method : methods) {
/*     */ 
/*     */ 
/*     */       
/* 360 */       if (!this.nav.isPublicMethod(method) || (this.nav
/* 361 */         .isStaticMethod(method) && this.nav.isFinalMethod(method))) {
/*     */         continue;
/*     */       }
/*     */       
/* 365 */       if (!this.nav.isPublicMethod(method)) {
/*     */         continue;
/*     */       }
/*     */       
/* 369 */       String name = this.nav.getMethodName(method);
/*     */       
/* 371 */       if ((!name.startsWith("get") && !name.startsWith("is")) || skipProperties.contains(name) || name
/* 372 */         .equals("get") || name.equals("is")) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 377 */       T returnType = getSafeType((T)this.nav.getReturnType(method));
/* 378 */       if ((this.nav.getMethodParameters(method)).length == 0) {
/* 379 */         String fieldName = name.startsWith("get") ? name.substring(3) : name.substring(2);
/* 380 */         if (decapitalize) fieldName = StringUtils.decapitalize(fieldName); 
/* 381 */         fields.put(fieldName, this.factory.createWrapperBeanMember(returnType, fieldName, Collections.emptyList()));
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
/*     */   private static String getPropertyName(String name) {
/* 393 */     String propertyName = BindingHelper.mangleNameToVariableName(name);
/*     */ 
/*     */     
/* 396 */     return getJavaReservedVarialbeName(propertyName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private static String getJavaReservedVarialbeName(@NotNull String name) {
/* 405 */     String reservedName = reservedWords.get(name);
/* 406 */     return (reservedName == null) ? name : reservedName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 412 */   private static final Map<String, String> reservedWords = new HashMap<>(); static {
/* 413 */     reservedWords.put("abstract", "_abstract");
/* 414 */     reservedWords.put("assert", "_assert");
/* 415 */     reservedWords.put("boolean", "_boolean");
/* 416 */     reservedWords.put("break", "_break");
/* 417 */     reservedWords.put("byte", "_byte");
/* 418 */     reservedWords.put("case", "_case");
/* 419 */     reservedWords.put("catch", "_catch");
/* 420 */     reservedWords.put("char", "_char");
/* 421 */     reservedWords.put("class", "_class");
/* 422 */     reservedWords.put("const", "_const");
/* 423 */     reservedWords.put("continue", "_continue");
/* 424 */     reservedWords.put("default", "_default");
/* 425 */     reservedWords.put("do", "_do");
/* 426 */     reservedWords.put("double", "_double");
/* 427 */     reservedWords.put("else", "_else");
/* 428 */     reservedWords.put("extends", "_extends");
/* 429 */     reservedWords.put("false", "_false");
/* 430 */     reservedWords.put("final", "_final");
/* 431 */     reservedWords.put("finally", "_finally");
/* 432 */     reservedWords.put("float", "_float");
/* 433 */     reservedWords.put("for", "_for");
/* 434 */     reservedWords.put("goto", "_goto");
/* 435 */     reservedWords.put("if", "_if");
/* 436 */     reservedWords.put("implements", "_implements");
/* 437 */     reservedWords.put("import", "_import");
/* 438 */     reservedWords.put("instanceof", "_instanceof");
/* 439 */     reservedWords.put("int", "_int");
/* 440 */     reservedWords.put("interface", "_interface");
/* 441 */     reservedWords.put("long", "_long");
/* 442 */     reservedWords.put("native", "_native");
/* 443 */     reservedWords.put("new", "_new");
/* 444 */     reservedWords.put("null", "_null");
/* 445 */     reservedWords.put("package", "_package");
/* 446 */     reservedWords.put("private", "_private");
/* 447 */     reservedWords.put("protected", "_protected");
/* 448 */     reservedWords.put("public", "_public");
/* 449 */     reservedWords.put("return", "_return");
/* 450 */     reservedWords.put("short", "_short");
/* 451 */     reservedWords.put("static", "_static");
/* 452 */     reservedWords.put("strictfp", "_strictfp");
/* 453 */     reservedWords.put("super", "_super");
/* 454 */     reservedWords.put("switch", "_switch");
/* 455 */     reservedWords.put("synchronized", "_synchronized");
/* 456 */     reservedWords.put("this", "_this");
/* 457 */     reservedWords.put("throw", "_throw");
/* 458 */     reservedWords.put("throws", "_throws");
/* 459 */     reservedWords.put("transient", "_transient");
/* 460 */     reservedWords.put("true", "_true");
/* 461 */     reservedWords.put("try", "_try");
/* 462 */     reservedWords.put("void", "_void");
/* 463 */     reservedWords.put("volatile", "_volatile");
/* 464 */     reservedWords.put("while", "_while");
/* 465 */     reservedWords.put("enum", "_enum");
/*     */   }
/*     */   
/*     */   protected abstract T getSafeType(T paramT);
/*     */   
/*     */   protected abstract T getHolderValueType(T paramT);
/*     */   
/*     */   protected abstract boolean isVoidType(T paramT);
/*     */   
/*     */   public static interface BeanMemberFactory<T, A> {
/*     */     A createWrapperBeanMember(T param1T, String param1String, List<Annotation> param1List);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/model/AbstractWrapperBeanGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */