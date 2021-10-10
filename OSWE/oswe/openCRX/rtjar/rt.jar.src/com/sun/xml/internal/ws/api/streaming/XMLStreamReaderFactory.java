/*     */ package com.sun.xml.internal.ws.api.streaming;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.resources.StreamingMessages;
/*     */ import com.sun.xml.internal.ws.streaming.XMLReaderException;
/*     */ import com.sun.xml.internal.ws.util.xml.XmlUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import org.xml.sax.InputSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class XMLStreamReaderFactory
/*     */ {
/*  64 */   private static final Logger LOGGER = Logger.getLogger(XMLStreamReaderFactory.class.getName());
/*     */ 
/*     */   
/*     */   private static final String CLASS_NAME_OF_WSTXINPUTFACTORY = "com.ctc.wstx.stax.WstxInputFactory";
/*     */ 
/*     */ 
/*     */   
/*  71 */   private static volatile ContextClassloaderLocal<XMLStreamReaderFactory> streamReader = new ContextClassloaderLocal<XMLStreamReaderFactory>()
/*     */     {
/*     */ 
/*     */       
/*     */       protected XMLStreamReaderFactory initialValue()
/*     */       {
/*  77 */         XMLInputFactory xif = XMLStreamReaderFactory.getXMLInputFactory();
/*  78 */         XMLStreamReaderFactory f = null;
/*     */ 
/*     */ 
/*     */         
/*  82 */         if (!XMLStreamReaderFactory.getProperty(XMLStreamReaderFactory.class.getName() + ".noPool").booleanValue()) {
/*  83 */           f = XMLStreamReaderFactory.Zephyr.newInstance(xif);
/*     */         }
/*     */         
/*  86 */         if (f == null)
/*     */         {
/*  88 */           if (xif.getClass().getName().equals("com.ctc.wstx.stax.WstxInputFactory")) {
/*  89 */             f = new XMLStreamReaderFactory.Woodstox(xif);
/*     */           }
/*     */         }
/*     */         
/*  93 */         if (f == null) {
/*  94 */           f = new XMLStreamReaderFactory.Default();
/*     */         }
/*     */         
/*  97 */         if (XMLStreamReaderFactory.LOGGER.isLoggable(Level.FINE)) {
/*  98 */           XMLStreamReaderFactory.LOGGER.log(Level.FINE, "XMLStreamReaderFactory instance is = {0}", f);
/*     */         }
/* 100 */         return f;
/*     */       }
/*     */     };
/*     */   
/*     */   private static XMLInputFactory getXMLInputFactory() {
/* 105 */     XMLInputFactory xif = null;
/* 106 */     if (getProperty(XMLStreamReaderFactory.class.getName() + ".woodstox").booleanValue()) {
/*     */       try {
/* 108 */         xif = (XMLInputFactory)Class.forName("com.ctc.wstx.stax.WstxInputFactory").newInstance();
/* 109 */       } catch (Exception e) {
/* 110 */         if (LOGGER.isLoggable(Level.WARNING)) {
/* 111 */           LOGGER.log(Level.WARNING, StreamingMessages.WOODSTOX_CANT_LOAD("com.ctc.wstx.stax.WstxInputFactory"), e);
/*     */         }
/*     */       } 
/*     */     }
/* 115 */     if (xif == null) {
/* 116 */       xif = XmlUtil.newXMLInputFactory(true);
/*     */     }
/* 118 */     xif.setProperty("javax.xml.stream.isNamespaceAware", Boolean.valueOf(true));
/* 119 */     xif.setProperty("javax.xml.stream.supportDTD", Boolean.valueOf(false));
/* 120 */     xif.setProperty("javax.xml.stream.isCoalescing", Boolean.valueOf(true));
/*     */     
/* 122 */     return xif;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void set(XMLStreamReaderFactory f) {
/* 130 */     if (f == null) {
/* 131 */       throw new IllegalArgumentException();
/*     */     }
/* 133 */     streamReader.set(f);
/*     */   }
/*     */   
/*     */   public static XMLStreamReaderFactory get() {
/* 137 */     return streamReader.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public static XMLStreamReader create(InputSource source, boolean rejectDTDs) {
/*     */     try {
/* 143 */       if (source.getCharacterStream() != null) {
/* 144 */         return get().doCreate(source.getSystemId(), source.getCharacterStream(), rejectDTDs);
/*     */       }
/*     */ 
/*     */       
/* 148 */       if (source.getByteStream() != null) {
/* 149 */         return get().doCreate(source.getSystemId(), source.getByteStream(), rejectDTDs);
/*     */       }
/*     */ 
/*     */       
/* 153 */       return get().doCreate(source.getSystemId(), (new URL(source.getSystemId())).openStream(), rejectDTDs);
/* 154 */     } catch (IOException e) {
/* 155 */       throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/*     */     } 
/*     */   }
/*     */   
/*     */   public static XMLStreamReader create(@Nullable String systemId, InputStream in, boolean rejectDTDs) {
/* 160 */     return get().doCreate(systemId, in, rejectDTDs);
/*     */   }
/*     */   
/*     */   public static XMLStreamReader create(@Nullable String systemId, InputStream in, @Nullable String encoding, boolean rejectDTDs) {
/* 164 */     return (encoding == null) ? 
/* 165 */       create(systemId, in, rejectDTDs) : 
/* 166 */       get().doCreate(systemId, in, encoding, rejectDTDs);
/*     */   }
/*     */   
/*     */   public static XMLStreamReader create(@Nullable String systemId, Reader reader, boolean rejectDTDs) {
/* 170 */     return get().doCreate(systemId, reader, rejectDTDs);
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
/*     */   public static void recycle(XMLStreamReader r) {
/* 197 */     get().doRecycle(r);
/* 198 */     if (r instanceof RecycleAware) {
/* 199 */       ((RecycleAware)r).onRecycled();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private XMLStreamReader doCreate(String systemId, InputStream in, @NotNull String encoding, boolean rejectDTDs) {
/*     */     Reader reader;
/*     */     try {
/* 210 */       reader = new InputStreamReader(in, encoding);
/* 211 */     } catch (UnsupportedEncodingException ue) {
/* 212 */       throw new XMLReaderException("stax.cantCreate", new Object[] { ue });
/*     */     } 
/* 214 */     return doCreate(systemId, reader, rejectDTDs);
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
/*     */   private static final class Zephyr
/*     */     extends XMLStreamReaderFactory
/*     */   {
/*     */     private final XMLInputFactory xif;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 239 */     private final ThreadLocal<XMLStreamReader> pool = new ThreadLocal<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Method setInputSourceMethod;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Method resetMethod;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Class zephyrClass;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public static XMLStreamReaderFactory newInstance(XMLInputFactory xif) {
/*     */       try {
/* 264 */         Class<?> clazz = xif.createXMLStreamReader(new StringReader("<foo/>")).getClass();
/*     */ 
/*     */         
/* 267 */         if (!clazz.getName().startsWith("com.sun.xml.internal.stream."))
/* 268 */           return null; 
/* 269 */         return new Zephyr(xif, clazz);
/* 270 */       } catch (NoSuchMethodException e) {
/* 271 */         return null;
/* 272 */       } catch (XMLStreamException e) {
/* 273 */         return null;
/*     */       } 
/*     */     }
/*     */     
/*     */     public Zephyr(XMLInputFactory xif, Class clazz) throws NoSuchMethodException {
/* 278 */       this.zephyrClass = clazz;
/* 279 */       this.setInputSourceMethod = clazz.getMethod("setInputSource", new Class[] { InputSource.class });
/* 280 */       this.resetMethod = clazz.getMethod("reset", new Class[0]);
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 285 */         xif.setProperty("reuse-instance", Boolean.valueOf(false));
/* 286 */       } catch (IllegalArgumentException illegalArgumentException) {}
/*     */ 
/*     */       
/* 289 */       this.xif = xif;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private XMLStreamReader fetch() {
/* 296 */       XMLStreamReader sr = this.pool.get();
/* 297 */       if (sr == null) return null; 
/* 298 */       this.pool.set(null);
/* 299 */       return sr;
/*     */     }
/*     */ 
/*     */     
/*     */     public void doRecycle(XMLStreamReader r) {
/* 304 */       if (this.zephyrClass.isInstance(r)) {
/* 305 */         this.pool.set(r);
/*     */       }
/*     */     }
/*     */     
/*     */     public XMLStreamReader doCreate(String systemId, InputStream in, boolean rejectDTDs) {
/*     */       try {
/* 311 */         XMLStreamReader xsr = fetch();
/* 312 */         if (xsr == null) {
/* 313 */           return this.xif.createXMLStreamReader(systemId, in);
/*     */         }
/*     */         
/* 316 */         InputSource is = new InputSource(systemId);
/* 317 */         is.setByteStream(in);
/* 318 */         reuse(xsr, is);
/* 319 */         return xsr;
/* 320 */       } catch (IllegalAccessException e) {
/* 321 */         throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/* 322 */       } catch (InvocationTargetException e) {
/* 323 */         throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/* 324 */       } catch (XMLStreamException e) {
/* 325 */         throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public XMLStreamReader doCreate(String systemId, Reader in, boolean rejectDTDs) {
/*     */       try {
/* 332 */         XMLStreamReader xsr = fetch();
/* 333 */         if (xsr == null) {
/* 334 */           return this.xif.createXMLStreamReader(systemId, in);
/*     */         }
/*     */         
/* 337 */         InputSource is = new InputSource(systemId);
/* 338 */         is.setCharacterStream(in);
/* 339 */         reuse(xsr, is);
/* 340 */         return xsr;
/* 341 */       } catch (IllegalAccessException e) {
/* 342 */         throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/* 343 */       } catch (InvocationTargetException e) {
/* 344 */         Throwable cause = e.getCause();
/* 345 */         if (cause == null) {
/* 346 */           cause = e;
/*     */         }
/* 348 */         throw new XMLReaderException("stax.cantCreate", new Object[] { cause });
/* 349 */       } catch (XMLStreamException e) {
/* 350 */         throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/*     */       } 
/*     */     }
/*     */     
/*     */     private void reuse(XMLStreamReader xsr, InputSource in) throws IllegalAccessException, InvocationTargetException {
/* 355 */       this.resetMethod.invoke(xsr, new Object[0]);
/* 356 */       this.setInputSourceMethod.invoke(xsr, new Object[] { in });
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
/*     */   public static final class Default
/*     */     extends XMLStreamReaderFactory
/*     */   {
/* 372 */     private final ThreadLocal<XMLInputFactory> xif = new ThreadLocal<XMLInputFactory>()
/*     */       {
/*     */         public XMLInputFactory initialValue() {
/* 375 */           return XMLStreamReaderFactory.getXMLInputFactory();
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*     */     public XMLStreamReader doCreate(String systemId, InputStream in, boolean rejectDTDs) {
/*     */       try {
/* 382 */         return ((XMLInputFactory)this.xif.get()).createXMLStreamReader(systemId, in);
/* 383 */       } catch (XMLStreamException e) {
/* 384 */         throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public XMLStreamReader doCreate(String systemId, Reader in, boolean rejectDTDs) {
/*     */       try {
/* 391 */         return ((XMLInputFactory)this.xif.get()).createXMLStreamReader(systemId, in);
/* 392 */       } catch (XMLStreamException e) {
/* 393 */         throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void doRecycle(XMLStreamReader r) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class NoLock
/*     */     extends XMLStreamReaderFactory
/*     */   {
/*     */     private final XMLInputFactory xif;
/*     */ 
/*     */ 
/*     */     
/*     */     public NoLock(XMLInputFactory xif) {
/* 414 */       this.xif = xif;
/*     */     }
/*     */ 
/*     */     
/*     */     public XMLStreamReader doCreate(String systemId, InputStream in, boolean rejectDTDs) {
/*     */       try {
/* 420 */         return this.xif.createXMLStreamReader(systemId, in);
/* 421 */       } catch (XMLStreamException e) {
/* 422 */         throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public XMLStreamReader doCreate(String systemId, Reader in, boolean rejectDTDs) {
/*     */       try {
/* 429 */         return this.xif.createXMLStreamReader(systemId, in);
/* 430 */       } catch (XMLStreamException e) {
/* 431 */         throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void doRecycle(XMLStreamReader r) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Woodstox
/*     */     extends NoLock
/*     */   {
/*     */     public static final String PROPERTY_MAX_ATTRIBUTES_PER_ELEMENT = "xml.ws.maximum.AttributesPerElement";
/*     */ 
/*     */     
/*     */     public static final String PROPERTY_MAX_ATTRIBUTE_SIZE = "xml.ws.maximum.AttributeSize";
/*     */     
/*     */     public static final String PROPERTY_MAX_CHILDREN_PER_ELEMENT = "xml.ws.maximum.ChildrenPerElement";
/*     */     
/*     */     public static final String PROPERTY_MAX_ELEMENT_COUNT = "xml.ws.maximum.ElementCount";
/*     */     
/*     */     public static final String PROPERTY_MAX_ELEMENT_DEPTH = "xml.ws.maximum.ElementDepth";
/*     */     
/*     */     public static final String PROPERTY_MAX_CHARACTERS = "xml.ws.maximum.Characters";
/*     */     
/*     */     private static final int DEFAULT_MAX_ATTRIBUTES_PER_ELEMENT = 500;
/*     */     
/*     */     private static final int DEFAULT_MAX_ATTRIBUTE_SIZE = 524288;
/*     */     
/*     */     private static final int DEFAULT_MAX_CHILDREN_PER_ELEMENT = 2147483647;
/*     */     
/*     */     private static final int DEFAULT_MAX_ELEMENT_DEPTH = 500;
/*     */     
/*     */     private static final long DEFAULT_MAX_ELEMENT_COUNT = 2147483647L;
/*     */     
/*     */     private static final long DEFAULT_MAX_CHARACTERS = 9223372036854775807L;
/*     */     
/* 470 */     private int maxAttributesPerElement = 500;
/* 471 */     private int maxAttributeSize = 524288;
/* 472 */     private int maxChildrenPerElement = Integer.MAX_VALUE;
/* 473 */     private int maxElementDepth = 500;
/* 474 */     private long maxElementCount = 2147483647L;
/* 475 */     private long maxCharacters = Long.MAX_VALUE;
/*     */     
/*     */     private static final String P_MAX_ATTRIBUTES_PER_ELEMENT = "com.ctc.wstx.maxAttributesPerElement";
/*     */     
/*     */     private static final String P_MAX_ATTRIBUTE_SIZE = "com.ctc.wstx.maxAttributeSize";
/*     */     private static final String P_MAX_CHILDREN_PER_ELEMENT = "com.ctc.wstx.maxChildrenPerElement";
/*     */     private static final String P_MAX_ELEMENT_COUNT = "com.ctc.wstx.maxElementCount";
/*     */     private static final String P_MAX_ELEMENT_DEPTH = "com.ctc.wstx.maxElementDepth";
/*     */     private static final String P_MAX_CHARACTERS = "com.ctc.wstx.maxCharacters";
/*     */     private static final String P_INTERN_NSURIS = "org.codehaus.stax2.internNsUris";
/*     */     
/*     */     public Woodstox(XMLInputFactory xif) {
/* 487 */       super(xif);
/*     */       
/* 489 */       if (xif.isPropertySupported("org.codehaus.stax2.internNsUris")) {
/* 490 */         xif.setProperty("org.codehaus.stax2.internNsUris", Boolean.valueOf(true));
/* 491 */         if (XMLStreamReaderFactory.LOGGER.isLoggable(Level.FINE)) {
/* 492 */           XMLStreamReaderFactory.LOGGER.log(Level.FINE, "org.codehaus.stax2.internNsUris is {0}", Boolean.valueOf(true));
/*     */         }
/*     */       } 
/*     */       
/* 496 */       if (xif.isPropertySupported("com.ctc.wstx.maxAttributesPerElement")) {
/* 497 */         this.maxAttributesPerElement = Integer.valueOf(XMLStreamReaderFactory.buildIntegerValue("xml.ws.maximum.AttributesPerElement", 500)).intValue();
/*     */ 
/*     */         
/* 500 */         xif.setProperty("com.ctc.wstx.maxAttributesPerElement", Integer.valueOf(this.maxAttributesPerElement));
/* 501 */         if (XMLStreamReaderFactory.LOGGER.isLoggable(Level.FINE)) {
/* 502 */           XMLStreamReaderFactory.LOGGER.log(Level.FINE, "com.ctc.wstx.maxAttributesPerElement is {0}", Integer.valueOf(this.maxAttributesPerElement));
/*     */         }
/*     */       } 
/*     */       
/* 506 */       if (xif.isPropertySupported("com.ctc.wstx.maxAttributeSize")) {
/* 507 */         this.maxAttributeSize = Integer.valueOf(XMLStreamReaderFactory.buildIntegerValue("xml.ws.maximum.AttributeSize", 524288)).intValue();
/*     */ 
/*     */         
/* 510 */         xif.setProperty("com.ctc.wstx.maxAttributeSize", Integer.valueOf(this.maxAttributeSize));
/* 511 */         if (XMLStreamReaderFactory.LOGGER.isLoggable(Level.FINE)) {
/* 512 */           XMLStreamReaderFactory.LOGGER.log(Level.FINE, "com.ctc.wstx.maxAttributeSize is {0}", Integer.valueOf(this.maxAttributeSize));
/*     */         }
/*     */       } 
/*     */       
/* 516 */       if (xif.isPropertySupported("com.ctc.wstx.maxChildrenPerElement")) {
/* 517 */         this.maxChildrenPerElement = Integer.valueOf(XMLStreamReaderFactory.buildIntegerValue("xml.ws.maximum.ChildrenPerElement", 2147483647)).intValue();
/*     */ 
/*     */         
/* 520 */         xif.setProperty("com.ctc.wstx.maxChildrenPerElement", Integer.valueOf(this.maxChildrenPerElement));
/* 521 */         if (XMLStreamReaderFactory.LOGGER.isLoggable(Level.FINE)) {
/* 522 */           XMLStreamReaderFactory.LOGGER.log(Level.FINE, "com.ctc.wstx.maxChildrenPerElement is {0}", Integer.valueOf(this.maxChildrenPerElement));
/*     */         }
/*     */       } 
/*     */       
/* 526 */       if (xif.isPropertySupported("com.ctc.wstx.maxElementDepth")) {
/* 527 */         this.maxElementDepth = Integer.valueOf(XMLStreamReaderFactory.buildIntegerValue("xml.ws.maximum.ElementDepth", 500)).intValue();
/*     */ 
/*     */         
/* 530 */         xif.setProperty("com.ctc.wstx.maxElementDepth", Integer.valueOf(this.maxElementDepth));
/* 531 */         if (XMLStreamReaderFactory.LOGGER.isLoggable(Level.FINE)) {
/* 532 */           XMLStreamReaderFactory.LOGGER.log(Level.FINE, "com.ctc.wstx.maxElementDepth is {0}", Integer.valueOf(this.maxElementDepth));
/*     */         }
/*     */       } 
/*     */       
/* 536 */       if (xif.isPropertySupported("com.ctc.wstx.maxElementCount")) {
/* 537 */         this.maxElementCount = Long.valueOf(XMLStreamReaderFactory.buildLongValue("xml.ws.maximum.ElementCount", 2147483647L)).longValue();
/*     */ 
/*     */         
/* 540 */         xif.setProperty("com.ctc.wstx.maxElementCount", Long.valueOf(this.maxElementCount));
/* 541 */         if (XMLStreamReaderFactory.LOGGER.isLoggable(Level.FINE)) {
/* 542 */           XMLStreamReaderFactory.LOGGER.log(Level.FINE, "com.ctc.wstx.maxElementCount is {0}", Long.valueOf(this.maxElementCount));
/*     */         }
/*     */       } 
/*     */       
/* 546 */       if (xif.isPropertySupported("com.ctc.wstx.maxCharacters")) {
/* 547 */         this.maxCharacters = Long.valueOf(XMLStreamReaderFactory.buildLongValue("xml.ws.maximum.Characters", Long.MAX_VALUE)).longValue();
/*     */ 
/*     */         
/* 550 */         xif.setProperty("com.ctc.wstx.maxCharacters", Long.valueOf(this.maxCharacters));
/* 551 */         if (XMLStreamReaderFactory.LOGGER.isLoggable(Level.FINE)) {
/* 552 */           XMLStreamReaderFactory.LOGGER.log(Level.FINE, "com.ctc.wstx.maxCharacters is {0}", Long.valueOf(this.maxCharacters));
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public XMLStreamReader doCreate(String systemId, InputStream in, boolean rejectDTDs) {
/* 559 */       return super.doCreate(systemId, in, rejectDTDs);
/*     */     }
/*     */ 
/*     */     
/*     */     public XMLStreamReader doCreate(String systemId, Reader in, boolean rejectDTDs) {
/* 564 */       return super.doCreate(systemId, in, rejectDTDs);
/*     */     }
/*     */   }
/*     */   
/*     */   private static int buildIntegerValue(String propertyName, int defaultValue) {
/* 569 */     String propVal = System.getProperty(propertyName);
/* 570 */     if (propVal != null && propVal.length() > 0) {
/*     */       try {
/* 572 */         Integer value = Integer.valueOf(Integer.parseInt(propVal));
/* 573 */         if (value.intValue() > 0)
/*     */         {
/* 575 */           return value.intValue();
/*     */         }
/* 577 */       } catch (NumberFormatException nfe) {
/* 578 */         if (LOGGER.isLoggable(Level.WARNING)) {
/* 579 */           LOGGER.log(Level.WARNING, StreamingMessages.INVALID_PROPERTY_VALUE_INTEGER(propertyName, propVal, Integer.toString(defaultValue)), nfe);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 584 */     return defaultValue;
/*     */   }
/*     */   
/*     */   private static long buildLongValue(String propertyName, long defaultValue) {
/* 588 */     String propVal = System.getProperty(propertyName);
/* 589 */     if (propVal != null && propVal.length() > 0) {
/*     */       try {
/* 591 */         long value = Long.parseLong(propVal);
/* 592 */         if (value > 0L)
/*     */         {
/* 594 */           return value;
/*     */         }
/* 596 */       } catch (NumberFormatException nfe) {
/*     */         
/* 598 */         if (LOGGER.isLoggable(Level.WARNING)) {
/* 599 */           LOGGER.log(Level.WARNING, StreamingMessages.INVALID_PROPERTY_VALUE_LONG(propertyName, propVal, Long.toString(defaultValue)), nfe);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 604 */     return defaultValue;
/*     */   }
/*     */   
/*     */   private static Boolean getProperty(final String prop) {
/* 608 */     return AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*     */         {
/*     */           public Boolean run()
/*     */           {
/* 612 */             String value = System.getProperty(prop);
/* 613 */             return (value != null) ? Boolean.valueOf(value) : Boolean.FALSE;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public abstract XMLStreamReader doCreate(String paramString, InputStream paramInputStream, boolean paramBoolean);
/*     */   
/*     */   public abstract XMLStreamReader doCreate(String paramString, Reader paramReader, boolean paramBoolean);
/*     */   
/*     */   public abstract void doRecycle(XMLStreamReader paramXMLStreamReader);
/*     */   
/*     */   public static interface RecycleAware {
/*     */     void onRecycled();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/streaming/XMLStreamReaderFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */