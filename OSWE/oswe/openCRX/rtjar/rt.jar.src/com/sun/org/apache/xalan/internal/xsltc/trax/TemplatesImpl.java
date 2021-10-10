/*     */ package com.sun.org.apache.xalan.internal.xsltc.trax;
/*     */ 
/*     */ import com.sun.org.apache.xalan.internal.utils.ObjectFactory;
/*     */ import com.sun.org.apache.xalan.internal.utils.SecuritySupport;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.DOM;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.Translet;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
/*     */ import java.io.IOException;
/*     */ import java.io.NotSerializableException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.io.Serializable;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.xml.transform.Templates;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.URIResolver;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TemplatesImpl
/*     */   implements Templates, Serializable
/*     */ {
/*     */   static final long serialVersionUID = 673094361519270707L;
/*     */   public static final String DESERIALIZE_TRANSLET = "jdk.xml.enableTemplatesImplDeserialization";
/*  65 */   private static String ABSTRACT_TRANSLET = "com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   private String _name = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private byte[][] _bytecodes = (byte[][])null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   private Class[] _class = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   private int _transletIndex = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   private transient Map<String, Class<?>> _auxClasses = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Properties _outputProperties;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int _indentNumber;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   private transient URIResolver _uriResolver = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   private transient ThreadLocal _sdom = new ThreadLocal();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   private transient TransformerFactoryImpl _tfactory = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient boolean _overrideDefaultParser;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   private transient String _accessExternalStylesheet = "all";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 145 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("_name", String.class), new ObjectStreamField("_bytecodes", byte[][].class), new ObjectStreamField("_class", Class[].class), new ObjectStreamField("_transletIndex", int.class), new ObjectStreamField("_outputProperties", Properties.class), new ObjectStreamField("_indentNumber", int.class) };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class TransletClassLoader
/*     */     extends ClassLoader
/*     */   {
/*     */     private final Map<String, Class> _loadedExternalExtensionFunctions;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     TransletClassLoader(ClassLoader parent) {
/* 159 */       super(parent);
/* 160 */       this._loadedExternalExtensionFunctions = null;
/*     */     }
/*     */     
/*     */     TransletClassLoader(ClassLoader parent, Map<String, Class<?>> mapEF) {
/* 164 */       super(parent);
/* 165 */       this._loadedExternalExtensionFunctions = mapEF;
/*     */     }
/*     */     
/*     */     public Class<?> loadClass(String name) throws ClassNotFoundException {
/* 169 */       Class<?> ret = null;
/*     */ 
/*     */       
/* 172 */       if (this._loadedExternalExtensionFunctions != null) {
/* 173 */         ret = this._loadedExternalExtensionFunctions.get(name);
/*     */       }
/* 175 */       if (ret == null) {
/* 176 */         ret = super.loadClass(name);
/*     */       }
/* 178 */       return ret;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Class defineClass(byte[] b) {
/* 185 */       return defineClass(null, b, 0, b.length);
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
/*     */   protected TemplatesImpl(byte[][] bytecodes, String transletName, Properties outputProperties, int indentNumber, TransformerFactoryImpl tfactory) {
/* 199 */     this._bytecodes = bytecodes;
/* 200 */     init(transletName, outputProperties, indentNumber, tfactory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TemplatesImpl(Class[] transletClasses, String transletName, Properties outputProperties, int indentNumber, TransformerFactoryImpl tfactory) {
/* 210 */     this._class = transletClasses;
/* 211 */     this._transletIndex = 0;
/* 212 */     init(transletName, outputProperties, indentNumber, tfactory);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(String transletName, Properties outputProperties, int indentNumber, TransformerFactoryImpl tfactory) {
/* 218 */     this._name = transletName;
/* 219 */     this._outputProperties = outputProperties;
/* 220 */     this._indentNumber = indentNumber;
/* 221 */     this._tfactory = tfactory;
/* 222 */     this._overrideDefaultParser = tfactory.overrideDefaultParser();
/* 223 */     this._accessExternalStylesheet = (String)tfactory.getAttribute("http://javax.xml.XMLConstants/property/accessExternalStylesheet");
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
/*     */   private void readObject(ObjectInputStream is) throws IOException, ClassNotFoundException {
/* 243 */     SecurityManager security = System.getSecurityManager();
/* 244 */     if (security != null) {
/* 245 */       String temp = SecuritySupport.getSystemProperty("jdk.xml.enableTemplatesImplDeserialization");
/* 246 */       if (temp == null || (temp.length() != 0 && !temp.equalsIgnoreCase("true"))) {
/* 247 */         ErrorMsg err = new ErrorMsg("DESERIALIZE_TEMPLATES_ERR");
/* 248 */         throw new UnsupportedOperationException(err.toString());
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 253 */     ObjectInputStream.GetField gf = is.readFields();
/* 254 */     this._name = (String)gf.get("_name", (Object)null);
/* 255 */     this._bytecodes = (byte[][])gf.get("_bytecodes", (Object)null);
/* 256 */     this._class = (Class[])gf.get("_class", (Object)null);
/* 257 */     this._transletIndex = gf.get("_transletIndex", -1);
/*     */     
/* 259 */     this._outputProperties = (Properties)gf.get("_outputProperties", (Object)null);
/* 260 */     this._indentNumber = gf.get("_indentNumber", 0);
/*     */     
/* 262 */     if (is.readBoolean()) {
/* 263 */       this._uriResolver = (URIResolver)is.readObject();
/*     */     }
/*     */     
/* 266 */     this._tfactory = new TransformerFactoryImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream os) throws IOException, ClassNotFoundException {
/* 277 */     if (this._auxClasses != null)
/*     */     {
/* 279 */       throw new NotSerializableException("com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 284 */     ObjectOutputStream.PutField pf = os.putFields();
/* 285 */     pf.put("_name", this._name);
/* 286 */     pf.put("_bytecodes", this._bytecodes);
/* 287 */     pf.put("_class", this._class);
/* 288 */     pf.put("_transletIndex", this._transletIndex);
/* 289 */     pf.put("_outputProperties", this._outputProperties);
/* 290 */     pf.put("_indentNumber", this._indentNumber);
/* 291 */     os.writeFields();
/*     */     
/* 293 */     if (this._uriResolver instanceof Serializable) {
/* 294 */       os.writeBoolean(true);
/* 295 */       os.writeObject(this._uriResolver);
/*     */     } else {
/*     */       
/* 298 */       os.writeBoolean(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean overrideDefaultParser() {
/* 306 */     return this._overrideDefaultParser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setURIResolver(URIResolver resolver) {
/* 313 */     this._uriResolver = resolver;
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
/*     */   private synchronized void setTransletBytecodes(byte[][] bytecodes) {
/* 326 */     this._bytecodes = bytecodes;
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
/*     */   private synchronized byte[][] getTransletBytecodes() {
/* 338 */     return this._bytecodes;
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
/*     */   private synchronized Class[] getTransletClasses() {
/*     */     try {
/* 351 */       if (this._class == null) defineTransletClasses();
/*     */     
/* 353 */     } catch (TransformerConfigurationException transformerConfigurationException) {}
/*     */ 
/*     */     
/* 356 */     return this._class;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int getTransletIndex() {
/*     */     try {
/* 364 */       if (this._class == null) defineTransletClasses();
/*     */     
/* 366 */     } catch (TransformerConfigurationException transformerConfigurationException) {}
/*     */ 
/*     */     
/* 369 */     return this._transletIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized void setTransletName(String name) {
/* 376 */     this._name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized String getTransletName() {
/* 383 */     return this._name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void defineTransletClasses() throws TransformerConfigurationException {
/* 393 */     if (this._bytecodes == null) {
/* 394 */       ErrorMsg err = new ErrorMsg("NO_TRANSLET_CLASS_ERR");
/* 395 */       throw new TransformerConfigurationException(err.toString());
/*     */     } 
/*     */ 
/*     */     
/* 399 */     TransletClassLoader loader = AccessController.<TransletClassLoader>doPrivileged(new PrivilegedAction<TransletClassLoader>() {
/*     */           public Object run() {
/* 401 */             return new TemplatesImpl.TransletClassLoader(ObjectFactory.findClassLoader(), TemplatesImpl.this._tfactory.getExternalExtensionsMap());
/*     */           }
/*     */         });
/*     */     
/*     */     try {
/* 406 */       int classCount = this._bytecodes.length;
/* 407 */       this._class = new Class[classCount];
/*     */       
/* 409 */       if (classCount > 1) {
/* 410 */         this._auxClasses = new HashMap<>();
/*     */       }
/*     */       
/* 413 */       for (int i = 0; i < classCount; i++) {
/* 414 */         this._class[i] = loader.defineClass(this._bytecodes[i]);
/* 415 */         Class<?> superClass = this._class[i].getSuperclass();
/*     */ 
/*     */         
/* 418 */         if (superClass.getName().equals(ABSTRACT_TRANSLET)) {
/* 419 */           this._transletIndex = i;
/*     */         } else {
/*     */           
/* 422 */           this._auxClasses.put(this._class[i].getName(), this._class[i]);
/*     */         } 
/*     */       } 
/*     */       
/* 426 */       if (this._transletIndex < 0) {
/* 427 */         ErrorMsg err = new ErrorMsg("NO_MAIN_TRANSLET_ERR", this._name);
/* 428 */         throw new TransformerConfigurationException(err.toString());
/*     */       }
/*     */     
/* 431 */     } catch (ClassFormatError e) {
/* 432 */       ErrorMsg err = new ErrorMsg("TRANSLET_CLASS_ERR", this._name);
/* 433 */       throw new TransformerConfigurationException(err.toString());
/*     */     }
/* 435 */     catch (LinkageError e) {
/* 436 */       ErrorMsg err = new ErrorMsg("TRANSLET_OBJECT_ERR", this._name);
/* 437 */       throw new TransformerConfigurationException(err.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Translet getTransletInstance() throws TransformerConfigurationException {
/*     */     try {
/* 449 */       if (this._name == null) return null;
/*     */       
/* 451 */       if (this._class == null) defineTransletClasses();
/*     */ 
/*     */ 
/*     */       
/* 455 */       AbstractTranslet translet = this._class[this._transletIndex].newInstance();
/* 456 */       translet.postInitialization();
/* 457 */       translet.setTemplates(this);
/* 458 */       translet.setOverrideDefaultParser(this._overrideDefaultParser);
/* 459 */       translet.setAllowedProtocols(this._accessExternalStylesheet);
/* 460 */       if (this._auxClasses != null) {
/* 461 */         translet.setAuxiliaryClasses(this._auxClasses);
/*     */       }
/*     */       
/* 464 */       return translet;
/*     */     }
/* 466 */     catch (InstantiationException e) {
/* 467 */       ErrorMsg err = new ErrorMsg("TRANSLET_OBJECT_ERR", this._name);
/* 468 */       throw new TransformerConfigurationException(err.toString());
/*     */     }
/* 470 */     catch (IllegalAccessException e) {
/* 471 */       ErrorMsg err = new ErrorMsg("TRANSLET_OBJECT_ERR", this._name);
/* 472 */       throw new TransformerConfigurationException(err.toString());
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
/*     */   public synchronized Transformer newTransformer() throws TransformerConfigurationException {
/* 486 */     TransformerImpl transformer = new TransformerImpl(getTransletInstance(), this._outputProperties, this._indentNumber, this._tfactory);
/*     */ 
/*     */     
/* 489 */     if (this._uriResolver != null) {
/* 490 */       transformer.setURIResolver(this._uriResolver);
/*     */     }
/*     */     
/* 493 */     if (this._tfactory.getFeature("http://javax.xml.XMLConstants/feature/secure-processing")) {
/* 494 */       transformer.setSecureProcessing(true);
/*     */     }
/* 496 */     return transformer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Properties getOutputProperties() {
/*     */     try {
/* 507 */       return newTransformer().getOutputProperties();
/*     */     }
/* 509 */     catch (TransformerConfigurationException e) {
/* 510 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOM getStylesheetDOM() {
/* 518 */     return this._sdom.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStylesheetDOM(DOM sdom) {
/* 525 */     this._sdom.set(sdom);
/*     */   }
/*     */   
/*     */   public TemplatesImpl() {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/trax/TemplatesImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */