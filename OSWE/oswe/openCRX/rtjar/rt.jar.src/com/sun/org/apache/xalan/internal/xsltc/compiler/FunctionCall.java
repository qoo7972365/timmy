/*      */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*      */ 
/*      */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*      */ import com.sun.org.apache.bcel.internal.generic.IFEQ;
/*      */ import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
/*      */ import com.sun.org.apache.bcel.internal.generic.INVOKESPECIAL;
/*      */ import com.sun.org.apache.bcel.internal.generic.INVOKESTATIC;
/*      */ import com.sun.org.apache.bcel.internal.generic.INVOKEVIRTUAL;
/*      */ import com.sun.org.apache.bcel.internal.generic.InstructionConstants;
/*      */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*      */ import com.sun.org.apache.bcel.internal.generic.LocalVariableGen;
/*      */ import com.sun.org.apache.bcel.internal.generic.NEW;
/*      */ import com.sun.org.apache.bcel.internal.generic.PUSH;
/*      */ import com.sun.org.apache.xalan.internal.utils.ObjectFactory;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodType;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MultiHashtable;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ObjectType;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
/*      */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Vector;
/*      */ import jdk.xml.internal.JdkXmlFeatures;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class FunctionCall
/*      */   extends Expression
/*      */ {
/*      */   private QName _fname;
/*      */   private final Vector _arguments;
/*   75 */   private static final Vector EMPTY_ARG_LIST = new Vector(0);
/*      */ 
/*      */   
/*      */   protected static final String EXT_XSLTC = "http://xml.apache.org/xalan/xsltc";
/*      */ 
/*      */   
/*      */   protected static final String JAVA_EXT_XSLTC = "http://xml.apache.org/xalan/xsltc/java";
/*      */ 
/*      */   
/*      */   protected static final String EXT_XALAN = "http://xml.apache.org/xalan";
/*      */ 
/*      */   
/*      */   protected static final String JAVA_EXT_XALAN = "http://xml.apache.org/xalan/java";
/*      */ 
/*      */   
/*      */   protected static final String JAVA_EXT_XALAN_OLD = "http://xml.apache.org/xslt/java";
/*      */ 
/*      */   
/*      */   protected static final String EXSLT_COMMON = "http://exslt.org/common";
/*      */ 
/*      */   
/*      */   protected static final String EXSLT_MATH = "http://exslt.org/math";
/*      */ 
/*      */   
/*      */   protected static final String EXSLT_SETS = "http://exslt.org/sets";
/*      */ 
/*      */   
/*      */   protected static final String EXSLT_DATETIME = "http://exslt.org/dates-and-times";
/*      */ 
/*      */   
/*      */   protected static final String EXSLT_STRINGS = "http://exslt.org/strings";
/*      */ 
/*      */   
/*      */   protected static final String XALAN_CLASSPACKAGE_NAMESPACE = "xalan://";
/*      */   
/*      */   protected static final int NAMESPACE_FORMAT_JAVA = 0;
/*      */   
/*      */   protected static final int NAMESPACE_FORMAT_CLASS = 1;
/*      */   
/*      */   protected static final int NAMESPACE_FORMAT_PACKAGE = 2;
/*      */   
/*      */   protected static final int NAMESPACE_FORMAT_CLASS_OR_PACKAGE = 3;
/*      */   
/*  118 */   private int _namespace_format = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  123 */   Expression _thisArgument = null;
/*      */ 
/*      */   
/*      */   private String _className;
/*      */   
/*      */   private Class _clazz;
/*      */   
/*      */   private Method _chosenMethod;
/*      */   
/*      */   private Constructor _chosenConstructor;
/*      */   
/*      */   private MethodType _chosenMethodType;
/*      */   
/*      */   private boolean unresolvedExternal;
/*      */   
/*      */   private boolean _isExtConstructor = false;
/*      */   
/*      */   private boolean _isStatic = false;
/*      */   
/*  142 */   private static final MultiHashtable<Type, JavaType> _internal2Java = new MultiHashtable<>();
/*      */   
/*      */   private static final Map<Class<?>, Type> JAVA2INTERNAL;
/*      */   
/*      */   private static final Map<String, String> EXTENSIONNAMESPACE;
/*      */   
/*      */   private static final Map<String, String> EXTENSIONFUNCTION;
/*      */ 
/*      */   
/*      */   static {
/*      */     Class<?> nodeClass, nodeListClass;
/*      */   }
/*      */ 
/*      */   
/*      */   static class JavaType
/*      */   {
/*      */     public Class<?> type;
/*      */     public int distance;
/*      */     
/*      */     public JavaType(Class<?> type, int distance) {
/*  162 */       this.type = type;
/*  163 */       this.distance = distance;
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  168 */       return Objects.hashCode(this.type);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object query) {
/*  173 */       if (query == null) {
/*  174 */         return false;
/*      */       }
/*  176 */       if (query.getClass().isAssignableFrom(JavaType.class)) {
/*  177 */         return ((JavaType)query).type.equals(this.type);
/*      */       }
/*  179 */       return query.equals(this.type);
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
/*      */   static {
/*      */     try {
/*  193 */       nodeClass = Class.forName("org.w3c.dom.Node");
/*  194 */       nodeListClass = Class.forName("org.w3c.dom.NodeList");
/*      */     }
/*  196 */     catch (ClassNotFoundException e) {
/*  197 */       ErrorMsg err = new ErrorMsg("CLASS_NOT_FOUND_ERR", "org.w3c.dom.Node or NodeList");
/*  198 */       throw new ExceptionInInitializerError(err.toString());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  204 */     _internal2Java.put(Type.Boolean, new JavaType(boolean.class, 0));
/*  205 */     _internal2Java.put(Type.Boolean, new JavaType(Boolean.class, 1));
/*  206 */     _internal2Java.put(Type.Boolean, new JavaType(Object.class, 2));
/*      */ 
/*      */ 
/*      */     
/*  210 */     _internal2Java.put(Type.Real, new JavaType(double.class, 0));
/*  211 */     _internal2Java.put(Type.Real, new JavaType(Double.class, 1));
/*  212 */     _internal2Java.put(Type.Real, new JavaType(float.class, 2));
/*  213 */     _internal2Java.put(Type.Real, new JavaType(long.class, 3));
/*  214 */     _internal2Java.put(Type.Real, new JavaType(int.class, 4));
/*  215 */     _internal2Java.put(Type.Real, new JavaType(short.class, 5));
/*  216 */     _internal2Java.put(Type.Real, new JavaType(byte.class, 6));
/*  217 */     _internal2Java.put(Type.Real, new JavaType(char.class, 7));
/*  218 */     _internal2Java.put(Type.Real, new JavaType(Object.class, 8));
/*      */ 
/*      */     
/*  221 */     _internal2Java.put(Type.Int, new JavaType(double.class, 0));
/*  222 */     _internal2Java.put(Type.Int, new JavaType(Double.class, 1));
/*  223 */     _internal2Java.put(Type.Int, new JavaType(float.class, 2));
/*  224 */     _internal2Java.put(Type.Int, new JavaType(long.class, 3));
/*  225 */     _internal2Java.put(Type.Int, new JavaType(int.class, 4));
/*  226 */     _internal2Java.put(Type.Int, new JavaType(short.class, 5));
/*  227 */     _internal2Java.put(Type.Int, new JavaType(byte.class, 6));
/*  228 */     _internal2Java.put(Type.Int, new JavaType(char.class, 7));
/*  229 */     _internal2Java.put(Type.Int, new JavaType(Object.class, 8));
/*      */ 
/*      */     
/*  232 */     _internal2Java.put(Type.String, new JavaType(String.class, 0));
/*  233 */     _internal2Java.put(Type.String, new JavaType(Object.class, 1));
/*      */ 
/*      */     
/*  236 */     _internal2Java.put(Type.NodeSet, new JavaType(nodeListClass, 0));
/*  237 */     _internal2Java.put(Type.NodeSet, new JavaType(nodeClass, 1));
/*  238 */     _internal2Java.put(Type.NodeSet, new JavaType(Object.class, 2));
/*  239 */     _internal2Java.put(Type.NodeSet, new JavaType(String.class, 3));
/*      */ 
/*      */     
/*  242 */     _internal2Java.put(Type.Node, new JavaType(nodeListClass, 0));
/*  243 */     _internal2Java.put(Type.Node, new JavaType(nodeClass, 1));
/*  244 */     _internal2Java.put(Type.Node, new JavaType(Object.class, 2));
/*  245 */     _internal2Java.put(Type.Node, new JavaType(String.class, 3));
/*      */ 
/*      */     
/*  248 */     _internal2Java.put(Type.ResultTree, new JavaType(nodeListClass, 0));
/*  249 */     _internal2Java.put(Type.ResultTree, new JavaType(nodeClass, 1));
/*  250 */     _internal2Java.put(Type.ResultTree, new JavaType(Object.class, 2));
/*  251 */     _internal2Java.put(Type.ResultTree, new JavaType(String.class, 3));
/*      */     
/*  253 */     _internal2Java.put(Type.Reference, new JavaType(Object.class, 0));
/*      */     
/*  255 */     _internal2Java.makeUnmodifiable();
/*      */     
/*  257 */     Map<Class<?>, Type> java2Internal = new HashMap<>();
/*  258 */     Map<String, String> extensionNamespaceTable = new HashMap<>();
/*  259 */     Map<String, String> extensionFunctionTable = new HashMap<>();
/*      */ 
/*      */     
/*  262 */     java2Internal.put(boolean.class, Type.Boolean);
/*  263 */     java2Internal.put(void.class, Type.Void);
/*  264 */     java2Internal.put(char.class, Type.Real);
/*  265 */     java2Internal.put(byte.class, Type.Real);
/*  266 */     java2Internal.put(short.class, Type.Real);
/*  267 */     java2Internal.put(int.class, Type.Real);
/*  268 */     java2Internal.put(long.class, Type.Real);
/*  269 */     java2Internal.put(float.class, Type.Real);
/*  270 */     java2Internal.put(double.class, Type.Real);
/*      */     
/*  272 */     java2Internal.put(String.class, Type.String);
/*      */     
/*  274 */     java2Internal.put(Object.class, Type.Reference);
/*      */ 
/*      */     
/*  277 */     java2Internal.put(nodeListClass, Type.NodeSet);
/*  278 */     java2Internal.put(nodeClass, Type.NodeSet);
/*      */ 
/*      */     
/*  281 */     extensionNamespaceTable.put("http://xml.apache.org/xalan", "com.sun.org.apache.xalan.internal.lib.Extensions");
/*  282 */     extensionNamespaceTable.put("http://exslt.org/common", "com.sun.org.apache.xalan.internal.lib.ExsltCommon");
/*  283 */     extensionNamespaceTable.put("http://exslt.org/math", "com.sun.org.apache.xalan.internal.lib.ExsltMath");
/*  284 */     extensionNamespaceTable.put("http://exslt.org/sets", "com.sun.org.apache.xalan.internal.lib.ExsltSets");
/*  285 */     extensionNamespaceTable.put("http://exslt.org/dates-and-times", "com.sun.org.apache.xalan.internal.lib.ExsltDatetime");
/*  286 */     extensionNamespaceTable.put("http://exslt.org/strings", "com.sun.org.apache.xalan.internal.lib.ExsltStrings");
/*      */ 
/*      */     
/*  289 */     extensionFunctionTable.put("http://exslt.org/common:nodeSet", "nodeset");
/*  290 */     extensionFunctionTable.put("http://exslt.org/common:objectType", "objectType");
/*  291 */     extensionFunctionTable.put("http://xml.apache.org/xalan:nodeset", "nodeset");
/*      */     
/*  293 */     JAVA2INTERNAL = Collections.unmodifiableMap(java2Internal);
/*  294 */     EXTENSIONNAMESPACE = Collections.unmodifiableMap(extensionNamespaceTable);
/*  295 */     EXTENSIONFUNCTION = Collections.unmodifiableMap(extensionFunctionTable);
/*      */   }
/*      */ 
/*      */   
/*      */   public FunctionCall(QName fname, Vector arguments) {
/*  300 */     this._fname = fname;
/*  301 */     this._arguments = arguments;
/*  302 */     this._type = null;
/*      */   }
/*      */   
/*      */   public FunctionCall(QName fname) {
/*  306 */     this(fname, EMPTY_ARG_LIST);
/*      */   }
/*      */   
/*      */   public String getName() {
/*  310 */     return this._fname.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setParser(Parser parser) {
/*  315 */     super.setParser(parser);
/*  316 */     if (this._arguments != null) {
/*  317 */       int n = this._arguments.size();
/*  318 */       for (int i = 0; i < n; i++) {
/*  319 */         Expression exp = this._arguments.elementAt(i);
/*  320 */         exp.setParser(parser);
/*  321 */         exp.setParent(this);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public String getClassNameFromUri(String uri) {
/*  328 */     String className = EXTENSIONNAMESPACE.get(uri);
/*      */     
/*  330 */     if (className != null) {
/*  331 */       return className;
/*      */     }
/*  333 */     if (uri.startsWith("http://xml.apache.org/xalan/xsltc/java")) {
/*  334 */       int length = "http://xml.apache.org/xalan/xsltc/java".length() + 1;
/*  335 */       return (uri.length() > length) ? uri.substring(length) : "";
/*      */     } 
/*  337 */     if (uri.startsWith("http://xml.apache.org/xalan/java")) {
/*  338 */       int length = "http://xml.apache.org/xalan/java".length() + 1;
/*  339 */       return (uri.length() > length) ? uri.substring(length) : "";
/*      */     } 
/*  341 */     if (uri.startsWith("http://xml.apache.org/xslt/java")) {
/*  342 */       int length = "http://xml.apache.org/xslt/java".length() + 1;
/*  343 */       return (uri.length() > length) ? uri.substring(length) : "";
/*      */     } 
/*      */     
/*  346 */     int index = uri.lastIndexOf('/');
/*  347 */     return (index > 0) ? uri.substring(index + 1) : uri;
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
/*      */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/*  360 */     if (this._type != null) return this._type;
/*      */     
/*  362 */     String namespace = this._fname.getNamespace();
/*  363 */     String local = this._fname.getLocalPart();
/*      */     
/*  365 */     if (isExtension()) {
/*  366 */       this._fname = new QName(null, null, local);
/*  367 */       return typeCheckStandard(stable);
/*      */     } 
/*  369 */     if (isStandard()) {
/*  370 */       return typeCheckStandard(stable);
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  375 */       this._className = getClassNameFromUri(namespace);
/*      */       
/*  377 */       int pos = local.lastIndexOf('.');
/*  378 */       if (pos > 0) {
/*  379 */         this._isStatic = true;
/*  380 */         if (this._className != null && this._className.length() > 0) {
/*  381 */           this._namespace_format = 2;
/*  382 */           this._className += "." + local.substring(0, pos);
/*      */         } else {
/*      */           
/*  385 */           this._namespace_format = 0;
/*  386 */           this._className = local.substring(0, pos);
/*      */         } 
/*      */         
/*  389 */         this._fname = new QName(namespace, null, local.substring(pos + 1));
/*      */       } else {
/*      */         
/*  392 */         if (this._className != null && this._className.length() > 0) {
/*      */           try {
/*  394 */             this._clazz = ObjectFactory.findProviderClass(this._className, true);
/*  395 */             this._namespace_format = 1;
/*      */           }
/*  397 */           catch (ClassNotFoundException e) {
/*  398 */             this._namespace_format = 2;
/*      */           } 
/*      */         } else {
/*      */           
/*  402 */           this._namespace_format = 0;
/*      */         } 
/*  404 */         if (local.indexOf('-') > 0) {
/*  405 */           local = replaceDash(local);
/*      */         }
/*      */         
/*  408 */         String extFunction = EXTENSIONFUNCTION.get(namespace + ":" + local);
/*  409 */         if (extFunction != null) {
/*  410 */           this._fname = new QName(null, null, extFunction);
/*  411 */           return typeCheckStandard(stable);
/*      */         } 
/*      */         
/*  414 */         this._fname = new QName(namespace, null, local);
/*      */       } 
/*      */       
/*  417 */       return typeCheckExternal(stable);
/*      */     }
/*  419 */     catch (TypeCheckError e) {
/*  420 */       ErrorMsg errorMsg = e.getErrorMsg();
/*  421 */       if (errorMsg == null) {
/*  422 */         String name = this._fname.getLocalPart();
/*  423 */         errorMsg = new ErrorMsg("METHOD_NOT_FOUND_ERR", name);
/*      */       } 
/*  425 */       getParser().reportError(3, errorMsg);
/*  426 */       return this._type = Type.Void;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Type typeCheckStandard(SymbolTable stable) throws TypeCheckError {
/*  437 */     this._fname.clearNamespace();
/*      */     
/*  439 */     int n = this._arguments.size();
/*  440 */     Vector argsType = typeCheckArgs(stable);
/*  441 */     MethodType args = new MethodType(Type.Void, argsType);
/*      */     
/*  443 */     MethodType ptype = lookupPrimop(stable, this._fname.getLocalPart(), args);
/*      */     
/*  445 */     if (ptype != null) {
/*  446 */       for (int i = 0; i < n; i++) {
/*  447 */         Type argType = ptype.argsType().elementAt(i);
/*  448 */         Expression exp = this._arguments.elementAt(i);
/*  449 */         if (!argType.identicalTo(exp.getType())) {
/*      */           try {
/*  451 */             this._arguments.setElementAt(new CastExpr(exp, argType), i);
/*      */           }
/*  453 */           catch (TypeCheckError e) {
/*  454 */             throw new TypeCheckError(this);
/*      */           } 
/*      */         }
/*      */       } 
/*  458 */       this._chosenMethodType = ptype;
/*  459 */       return this._type = ptype.resultType();
/*      */     } 
/*  461 */     throw new TypeCheckError(this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Type typeCheckConstructor(SymbolTable stable) throws TypeCheckError {
/*  467 */     Vector<Constructor> constructors = findConstructors();
/*  468 */     if (constructors == null)
/*      */     {
/*  470 */       throw new TypeCheckError("CONSTRUCTOR_NOT_FOUND", this._className);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  475 */     int nConstructors = constructors.size();
/*  476 */     int nArgs = this._arguments.size();
/*  477 */     Vector<Type> argsType = typeCheckArgs(stable);
/*      */ 
/*      */     
/*  480 */     int bestConstrDistance = Integer.MAX_VALUE;
/*  481 */     this._type = null;
/*  482 */     for (int i = 0; i < nConstructors; i++) {
/*      */ 
/*      */       
/*  485 */       Constructor constructor = constructors.elementAt(i);
/*  486 */       Class[] paramTypes = constructor.getParameterTypes();
/*      */ 
/*      */       
/*  489 */       int currConstrDistance = 0; int j;
/*  490 */       for (j = 0; j < nArgs; j++) {
/*      */         
/*  492 */         Class<?> extType = paramTypes[j];
/*  493 */         Type intType = argsType.elementAt(j);
/*  494 */         JavaType match = _internal2Java.maps(intType, new JavaType(extType, 0));
/*  495 */         if (match != null) {
/*  496 */           currConstrDistance += match.distance;
/*      */         }
/*  498 */         else if (intType instanceof ObjectType) {
/*  499 */           ObjectType objectType = (ObjectType)intType;
/*  500 */           if (objectType.getJavaClass() != extType)
/*      */           {
/*  502 */             if (extType.isAssignableFrom(objectType.getJavaClass())) {
/*  503 */               currConstrDistance++;
/*      */             } else {
/*  505 */               currConstrDistance = Integer.MAX_VALUE;
/*      */               
/*      */               break;
/*      */             } 
/*      */           }
/*      */         } else {
/*  511 */           currConstrDistance = Integer.MAX_VALUE;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*  516 */       if (j == nArgs && currConstrDistance < bestConstrDistance) {
/*  517 */         this._chosenConstructor = constructor;
/*  518 */         this._isExtConstructor = true;
/*  519 */         bestConstrDistance = currConstrDistance;
/*      */         
/*  521 */         this
/*  522 */           ._type = (this._clazz != null) ? Type.newObjectType(this._clazz) : Type.newObjectType(this._className);
/*      */       } 
/*      */     } 
/*      */     
/*  526 */     if (this._type != null) {
/*  527 */       return this._type;
/*      */     }
/*      */     
/*  530 */     throw new TypeCheckError("ARGUMENT_CONVERSION_ERR", getMethodSignature(argsType));
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
/*      */   public Type typeCheckExternal(SymbolTable stable) throws TypeCheckError {
/*  542 */     int nArgs = this._arguments.size();
/*  543 */     String name = this._fname.getLocalPart();
/*      */ 
/*      */     
/*  546 */     if (this._fname.getLocalPart().equals("new")) {
/*  547 */       return typeCheckConstructor(stable);
/*      */     }
/*      */ 
/*      */     
/*  551 */     boolean hasThisArgument = false;
/*      */     
/*  553 */     if (nArgs == 0) {
/*  554 */       this._isStatic = true;
/*      */     }
/*  556 */     if (!this._isStatic) {
/*  557 */       if (this._namespace_format == 0 || this._namespace_format == 2)
/*      */       {
/*  559 */         hasThisArgument = true;
/*      */       }
/*  561 */       Expression firstArg = this._arguments.elementAt(0);
/*  562 */       Type firstArgType = firstArg.typeCheck(stable);
/*      */       
/*  564 */       if (this._namespace_format == 1 && firstArgType instanceof ObjectType && this._clazz != null && this._clazz
/*      */ 
/*      */         
/*  567 */         .isAssignableFrom(((ObjectType)firstArgType).getJavaClass())) {
/*  568 */         hasThisArgument = true;
/*      */       }
/*  570 */       if (hasThisArgument) {
/*  571 */         this._thisArgument = this._arguments.elementAt(0);
/*  572 */         this._arguments.remove(0); nArgs--;
/*  573 */         if (firstArgType instanceof ObjectType) {
/*  574 */           this._className = ((ObjectType)firstArgType).getJavaClassName();
/*      */         } else {
/*      */           
/*  577 */           throw new TypeCheckError("NO_JAVA_FUNCT_THIS_REF", name);
/*      */         } 
/*      */       } 
/*  580 */     } else if (this._className.length() == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  587 */       Parser parser = getParser();
/*  588 */       if (parser != null) {
/*  589 */         reportWarning(this, parser, "FUNCTION_RESOLVE_ERR", this._fname
/*  590 */             .toString());
/*      */       }
/*  592 */       this.unresolvedExternal = true;
/*  593 */       return this._type = Type.Int;
/*      */     } 
/*      */ 
/*      */     
/*  597 */     Vector<Method> methods = findMethods();
/*      */     
/*  599 */     if (methods == null)
/*      */     {
/*  601 */       throw new TypeCheckError("METHOD_NOT_FOUND_ERR", this._className + "." + name);
/*      */     }
/*      */     
/*  604 */     Class<?> extType = null;
/*  605 */     int nMethods = methods.size();
/*  606 */     Vector<Type> argsType = typeCheckArgs(stable);
/*      */ 
/*      */     
/*  609 */     int bestMethodDistance = Integer.MAX_VALUE;
/*  610 */     this._type = null;
/*  611 */     for (int i = 0; i < nMethods; i++) {
/*      */       
/*  613 */       Method method = methods.elementAt(i);
/*  614 */       Class[] paramTypes = method.getParameterTypes();
/*      */       
/*  616 */       int currMethodDistance = 0; int j;
/*  617 */       for (j = 0; j < nArgs; j++) {
/*      */         
/*  619 */         extType = paramTypes[j];
/*  620 */         Type intType = argsType.elementAt(j);
/*  621 */         JavaType match = _internal2Java.maps(intType, new JavaType(extType, 0));
/*  622 */         if (match != null) {
/*  623 */           currMethodDistance += match.distance;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*  630 */         else if (intType instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.ReferenceType) {
/*  631 */           currMethodDistance++;
/*      */         }
/*  633 */         else if (intType instanceof ObjectType) {
/*  634 */           ObjectType object = (ObjectType)intType;
/*  635 */           if (extType.getName().equals(object.getJavaClassName())) {
/*  636 */             currMethodDistance += 0;
/*  637 */           } else if (extType.isAssignableFrom(object.getJavaClass())) {
/*  638 */             currMethodDistance++;
/*      */           } else {
/*  640 */             currMethodDistance = Integer.MAX_VALUE;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } else {
/*  645 */           currMethodDistance = Integer.MAX_VALUE;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */       
/*  651 */       if (j == nArgs) {
/*      */         
/*  653 */         extType = method.getReturnType();
/*      */         
/*  655 */         this._type = JAVA2INTERNAL.get(extType);
/*  656 */         if (this._type == null) {
/*  657 */           this._type = Type.newObjectType(extType);
/*      */         }
/*      */ 
/*      */         
/*  661 */         if (this._type != null && currMethodDistance < bestMethodDistance) {
/*  662 */           this._chosenMethod = method;
/*  663 */           bestMethodDistance = currMethodDistance;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  670 */     if (this._chosenMethod != null && this._thisArgument == null && 
/*  671 */       !Modifier.isStatic(this._chosenMethod.getModifiers())) {
/*  672 */       throw new TypeCheckError("NO_JAVA_FUNCT_THIS_REF", getMethodSignature(argsType));
/*      */     }
/*      */     
/*  675 */     if (this._type != null) {
/*  676 */       if (this._type == Type.NodeSet) {
/*  677 */         getXSLTC().setMultiDocument(true);
/*      */       }
/*  679 */       return this._type;
/*      */     } 
/*      */     
/*  682 */     throw new TypeCheckError("ARGUMENT_CONVERSION_ERR", getMethodSignature(argsType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector typeCheckArgs(SymbolTable stable) throws TypeCheckError {
/*  689 */     Vector<Type> result = new Vector();
/*  690 */     Enumeration<Expression> e = this._arguments.elements();
/*  691 */     while (e.hasMoreElements()) {
/*  692 */       Expression exp = e.nextElement();
/*  693 */       result.addElement(exp.typeCheck(stable));
/*      */     } 
/*  695 */     return result;
/*      */   }
/*      */   
/*      */   protected final Expression argument(int i) {
/*  699 */     return this._arguments.elementAt(i);
/*      */   }
/*      */   
/*      */   protected final Expression argument() {
/*  703 */     return argument(0);
/*      */   }
/*      */   
/*      */   protected final int argumentCount() {
/*  707 */     return this._arguments.size();
/*      */   }
/*      */   
/*      */   protected final void setArgument(int i, Expression exp) {
/*  711 */     this._arguments.setElementAt(exp, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void translateDesynthesized(ClassGenerator classGen, MethodGenerator methodGen) {
/*  722 */     Type type = Type.Boolean;
/*  723 */     if (this._chosenMethodType != null) {
/*  724 */       type = this._chosenMethodType.resultType();
/*      */     }
/*  726 */     InstructionList il = methodGen.getInstructionList();
/*  727 */     translate(classGen, methodGen);
/*      */     
/*  729 */     if (type instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.BooleanType || type instanceof com.sun.org.apache.xalan.internal.xsltc.compiler.util.IntType) {
/*  730 */       this._falseList.add(il.append(new IFEQ(null)));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/*  741 */     int n = argumentCount();
/*  742 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  743 */     InstructionList il = methodGen.getInstructionList();
/*  744 */     boolean isSecureProcessing = classGen.getParser().getXSLTC().isSecureProcessing();
/*      */     
/*  746 */     boolean isExtensionFunctionEnabled = classGen.getParser().getXSLTC().getFeature(JdkXmlFeatures.XmlFeature.ENABLE_EXTENSION_FUNCTION);
/*      */ 
/*      */ 
/*      */     
/*  750 */     if (isStandard() || isExtension()) {
/*  751 */       for (int i = 0; i < n; i++) {
/*  752 */         Expression exp = argument(i);
/*  753 */         exp.translate(classGen, methodGen);
/*  754 */         exp.startIterator(classGen, methodGen);
/*      */       } 
/*      */ 
/*      */       
/*  758 */       String name = this._fname.toString().replace('-', '_') + "F";
/*  759 */       String args = "";
/*      */ 
/*      */       
/*  762 */       if (name.equals("sumF")) {
/*  763 */         args = "Lcom/sun/org/apache/xalan/internal/xsltc/DOM;";
/*  764 */         il.append(methodGen.loadDOM());
/*      */       }
/*  766 */       else if (name.equals("normalize_spaceF") && 
/*  767 */         this._chosenMethodType.toSignature(args)
/*  768 */         .equals("()Ljava/lang/String;")) {
/*  769 */         args = "ILcom/sun/org/apache/xalan/internal/xsltc/DOM;";
/*  770 */         il.append(methodGen.loadContextNode());
/*  771 */         il.append(methodGen.loadDOM());
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  776 */       int index = cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary", name, this._chosenMethodType
/*  777 */           .toSignature(args));
/*  778 */       il.append(new INVOKESTATIC(index));
/*      */ 
/*      */     
/*      */     }
/*  782 */     else if (this.unresolvedExternal) {
/*  783 */       int index = cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary", "unresolved_externalF", "(Ljava/lang/String;)V");
/*      */ 
/*      */       
/*  786 */       il.append(new PUSH(cpg, this._fname.toString()));
/*  787 */       il.append(new INVOKESTATIC(index));
/*      */     }
/*  789 */     else if (this._isExtConstructor) {
/*  790 */       if (isSecureProcessing && !isExtensionFunctionEnabled) {
/*  791 */         translateUnallowedExtension(cpg, il);
/*      */       }
/*      */       
/*  794 */       String clazz = this._chosenConstructor.getDeclaringClass().getName();
/*  795 */       Class[] paramTypes = this._chosenConstructor.getParameterTypes();
/*  796 */       LocalVariableGen[] paramTemp = new LocalVariableGen[n];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       int i;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  807 */       for (i = 0; i < n; i++) {
/*  808 */         Expression exp = argument(i);
/*  809 */         Type expType = exp.getType();
/*  810 */         exp.translate(classGen, methodGen);
/*      */         
/*  812 */         exp.startIterator(classGen, methodGen);
/*  813 */         expType.translateTo(classGen, methodGen, paramTypes[i]);
/*  814 */         paramTemp[i] = methodGen
/*  815 */           .addLocalVariable("function_call_tmp" + i, expType
/*  816 */             .toJCType(), null, null);
/*      */         
/*  818 */         paramTemp[i].setStart(il
/*  819 */             .append(expType.STORE(paramTemp[i].getIndex())));
/*      */       } 
/*      */ 
/*      */       
/*  823 */       il.append(new NEW(cpg.addClass(this._className)));
/*  824 */       il.append(InstructionConstants.DUP);
/*      */       
/*  826 */       for (i = 0; i < n; i++) {
/*  827 */         Expression arg = argument(i);
/*  828 */         paramTemp[i].setEnd(il
/*  829 */             .append(arg.getType().LOAD(paramTemp[i].getIndex())));
/*      */       } 
/*      */       
/*  832 */       StringBuffer buffer = new StringBuffer();
/*  833 */       buffer.append('(');
/*  834 */       for (int j = 0; j < paramTypes.length; j++) {
/*  835 */         buffer.append(getSignature(paramTypes[j]));
/*      */       }
/*  837 */       buffer.append(')');
/*  838 */       buffer.append("V");
/*      */       
/*  840 */       int index = cpg.addMethodref(clazz, "<init>", buffer
/*      */           
/*  842 */           .toString());
/*  843 */       il.append(new INVOKESPECIAL(index));
/*      */ 
/*      */       
/*  846 */       Type.Object.translateFrom(classGen, methodGen, this._chosenConstructor
/*  847 */           .getDeclaringClass());
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  852 */       if (isSecureProcessing && !isExtensionFunctionEnabled) {
/*  853 */         translateUnallowedExtension(cpg, il);
/*      */       }
/*  855 */       String clazz = this._chosenMethod.getDeclaringClass().getName();
/*  856 */       Class[] paramTypes = this._chosenMethod.getParameterTypes();
/*      */ 
/*      */       
/*  859 */       if (this._thisArgument != null) {
/*  860 */         this._thisArgument.translate(classGen, methodGen);
/*      */       }
/*      */       
/*  863 */       for (int i = 0; i < n; i++) {
/*  864 */         Expression exp = argument(i);
/*  865 */         exp.translate(classGen, methodGen);
/*      */         
/*  867 */         exp.startIterator(classGen, methodGen);
/*  868 */         exp.getType().translateTo(classGen, methodGen, paramTypes[i]);
/*      */       } 
/*      */       
/*  871 */       StringBuffer buffer = new StringBuffer();
/*  872 */       buffer.append('(');
/*  873 */       for (int j = 0; j < paramTypes.length; j++) {
/*  874 */         buffer.append(getSignature(paramTypes[j]));
/*      */       }
/*  876 */       buffer.append(')');
/*  877 */       buffer.append(getSignature(this._chosenMethod.getReturnType()));
/*      */       
/*  879 */       if (this._thisArgument != null && this._clazz.isInterface()) {
/*  880 */         int index = cpg.addInterfaceMethodref(clazz, this._fname
/*  881 */             .getLocalPart(), buffer
/*  882 */             .toString());
/*  883 */         il.append(new INVOKEINTERFACE(index, n + 1));
/*      */       } else {
/*      */         
/*  886 */         int index = cpg.addMethodref(clazz, this._fname
/*  887 */             .getLocalPart(), buffer
/*  888 */             .toString());
/*  889 */         il.append((this._thisArgument != null) ? new INVOKEVIRTUAL(index) : new INVOKESTATIC(index));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  894 */       this._type.translateFrom(classGen, methodGen, this._chosenMethod
/*  895 */           .getReturnType());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/*  901 */     return "funcall(" + this._fname + ", " + this._arguments + ')';
/*      */   }
/*      */   
/*      */   public boolean isStandard() {
/*  905 */     String namespace = this._fname.getNamespace();
/*  906 */     return (namespace == null || namespace.equals(""));
/*      */   }
/*      */   
/*      */   public boolean isExtension() {
/*  910 */     String namespace = this._fname.getNamespace();
/*  911 */     return (namespace != null && namespace.equals("http://xml.apache.org/xalan/xsltc"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector findMethods() {
/*  921 */     Vector<Method> result = null;
/*  922 */     String namespace = this._fname.getNamespace();
/*      */     
/*  924 */     if (this._className != null && this._className.length() > 0) {
/*  925 */       int nArgs = this._arguments.size();
/*      */       try {
/*  927 */         if (this._clazz == null) {
/*  928 */           boolean isSecureProcessing = getXSLTC().isSecureProcessing();
/*      */           
/*  930 */           boolean isExtensionFunctionEnabled = getXSLTC().getFeature(JdkXmlFeatures.XmlFeature.ENABLE_EXTENSION_FUNCTION);
/*      */ 
/*      */           
/*  933 */           if (namespace != null && isSecureProcessing && isExtensionFunctionEnabled && (namespace
/*      */             
/*  935 */             .startsWith("http://xml.apache.org/xalan/java") || namespace
/*  936 */             .startsWith("http://xml.apache.org/xalan/xsltc/java") || namespace
/*  937 */             .startsWith("http://xml.apache.org/xslt/java") || namespace
/*  938 */             .startsWith("xalan://"))) {
/*  939 */             this._clazz = getXSLTC().loadExternalFunction(this._className);
/*      */           } else {
/*  941 */             this._clazz = ObjectFactory.findProviderClass(this._className, true);
/*      */           } 
/*      */           
/*  944 */           if (this._clazz == null) {
/*  945 */             ErrorMsg msg = new ErrorMsg("CLASS_NOT_FOUND_ERR", this._className);
/*      */             
/*  947 */             getParser().reportError(3, msg);
/*      */           } 
/*      */         } 
/*      */         
/*  951 */         String methodName = this._fname.getLocalPart();
/*  952 */         Method[] methods = this._clazz.getMethods();
/*      */         
/*  954 */         for (int i = 0; i < methods.length; i++) {
/*  955 */           int mods = methods[i].getModifiers();
/*      */           
/*  957 */           if (Modifier.isPublic(mods) && methods[i]
/*  958 */             .getName().equals(methodName) && (methods[i]
/*  959 */             .getParameterTypes()).length == nArgs)
/*      */           {
/*  961 */             if (result == null) {
/*  962 */               result = new Vector();
/*      */             }
/*  964 */             result.addElement(methods[i]);
/*      */           }
/*      */         
/*      */         } 
/*  968 */       } catch (ClassNotFoundException e) {
/*  969 */         ErrorMsg msg = new ErrorMsg("CLASS_NOT_FOUND_ERR", this._className);
/*  970 */         getParser().reportError(3, msg);
/*      */       } 
/*      */     } 
/*  973 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector findConstructors() {
/*  982 */     Vector<Constructor> result = null;
/*  983 */     String namespace = this._fname.getNamespace();
/*      */     
/*  985 */     int nArgs = this._arguments.size();
/*      */     try {
/*  987 */       if (this._clazz == null) {
/*  988 */         this._clazz = ObjectFactory.findProviderClass(this._className, true);
/*      */         
/*  990 */         if (this._clazz == null) {
/*  991 */           ErrorMsg msg = new ErrorMsg("CLASS_NOT_FOUND_ERR", this._className);
/*  992 */           getParser().reportError(3, msg);
/*      */         } 
/*      */       } 
/*      */       
/*  996 */       Constructor[] constructors = (Constructor[])this._clazz.getConstructors();
/*      */       
/*  998 */       for (int i = 0; i < constructors.length; i++) {
/*  999 */         int mods = constructors[i].getModifiers();
/*      */         
/* 1001 */         if (Modifier.isPublic(mods) && (constructors[i]
/* 1002 */           .getParameterTypes()).length == nArgs)
/*      */         {
/* 1004 */           if (result == null) {
/* 1005 */             result = new Vector();
/*      */           }
/* 1007 */           result.addElement(constructors[i]);
/*      */         }
/*      */       
/*      */       } 
/* 1011 */     } catch (ClassNotFoundException e) {
/* 1012 */       ErrorMsg msg = new ErrorMsg("CLASS_NOT_FOUND_ERR", this._className);
/* 1013 */       getParser().reportError(3, msg);
/*      */     } 
/*      */     
/* 1016 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final String getSignature(Class<?> clazz) {
/* 1024 */     if (clazz.isArray()) {
/* 1025 */       StringBuffer sb = new StringBuffer();
/* 1026 */       Class<?> cl = clazz;
/* 1027 */       while (cl.isArray()) {
/* 1028 */         sb.append("[");
/* 1029 */         cl = cl.getComponentType();
/*      */       } 
/* 1031 */       sb.append(getSignature(cl));
/* 1032 */       return sb.toString();
/*      */     } 
/* 1034 */     if (clazz.isPrimitive()) {
/* 1035 */       if (clazz == int.class) {
/* 1036 */         return "I";
/*      */       }
/* 1038 */       if (clazz == byte.class) {
/* 1039 */         return "B";
/*      */       }
/* 1041 */       if (clazz == long.class) {
/* 1042 */         return "J";
/*      */       }
/* 1044 */       if (clazz == float.class) {
/* 1045 */         return "F";
/*      */       }
/* 1047 */       if (clazz == double.class) {
/* 1048 */         return "D";
/*      */       }
/* 1050 */       if (clazz == short.class) {
/* 1051 */         return "S";
/*      */       }
/* 1053 */       if (clazz == char.class) {
/* 1054 */         return "C";
/*      */       }
/* 1056 */       if (clazz == boolean.class) {
/* 1057 */         return "Z";
/*      */       }
/* 1059 */       if (clazz == void.class) {
/* 1060 */         return "V";
/*      */       }
/*      */       
/* 1063 */       String name = clazz.toString();
/* 1064 */       ErrorMsg err = new ErrorMsg("UNKNOWN_SIG_TYPE_ERR", name);
/* 1065 */       throw new Error(err.toString());
/*      */     } 
/*      */ 
/*      */     
/* 1069 */     return "L" + clazz.getName().replace('.', '/') + ';';
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final String getSignature(Method meth) {
/* 1077 */     StringBuffer sb = new StringBuffer();
/* 1078 */     sb.append('(');
/* 1079 */     Class[] params = meth.getParameterTypes();
/* 1080 */     for (int j = 0; j < params.length; j++) {
/* 1081 */       sb.append(getSignature(params[j]));
/*      */     }
/* 1083 */     return sb.append(')').append(getSignature(meth.getReturnType()))
/* 1084 */       .toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final String getSignature(Constructor cons) {
/* 1091 */     StringBuffer sb = new StringBuffer();
/* 1092 */     sb.append('(');
/* 1093 */     Class[] params = cons.getParameterTypes();
/* 1094 */     for (int j = 0; j < params.length; j++) {
/* 1095 */       sb.append(getSignature(params[j]));
/*      */     }
/* 1097 */     return sb.append(")V").toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getMethodSignature(Vector<Type> argsType) {
/* 1104 */     StringBuffer buf = new StringBuffer(this._className);
/* 1105 */     buf.append('.').append(this._fname.getLocalPart()).append('(');
/*      */     
/* 1107 */     int nArgs = argsType.size();
/* 1108 */     for (int i = 0; i < nArgs; i++) {
/* 1109 */       Type intType = argsType.elementAt(i);
/* 1110 */       buf.append(intType.toString());
/* 1111 */       if (i < nArgs - 1) buf.append(", ");
/*      */     
/*      */     } 
/* 1114 */     buf.append(')');
/* 1115 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static String replaceDash(String name) {
/* 1125 */     char dash = '-';
/* 1126 */     StringBuilder buff = new StringBuilder("");
/* 1127 */     for (int i = 0; i < name.length(); i++) {
/* 1128 */       if (i > 0 && name.charAt(i - 1) == dash) {
/* 1129 */         buff.append(Character.toUpperCase(name.charAt(i)));
/* 1130 */       } else if (name.charAt(i) != dash) {
/* 1131 */         buff.append(name.charAt(i));
/*      */       } 
/* 1133 */     }  return buff.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void translateUnallowedExtension(ConstantPoolGen cpg, InstructionList il) {
/* 1142 */     int index = cpg.addMethodref("com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary", "unallowed_extension_functionF", "(Ljava/lang/String;)V");
/*      */ 
/*      */     
/* 1145 */     il.append(new PUSH(cpg, this._fname.toString()));
/* 1146 */     il.append(new INVOKESTATIC(index));
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/FunctionCall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */