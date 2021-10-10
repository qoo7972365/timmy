/*      */ package com.sun.xml.internal.bind.v2.model.impl;
/*      */ 
/*      */ import com.sun.istack.internal.ByteArrayDataSource;
/*      */ import com.sun.xml.internal.bind.DatatypeConverterImpl;
/*      */ import com.sun.xml.internal.bind.WhiteSpaceProcessor;
/*      */ import com.sun.xml.internal.bind.api.AccessorException;
/*      */ import com.sun.xml.internal.bind.v2.TODO;
/*      */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeBuiltinLeafInfo;
/*      */ import com.sun.xml.internal.bind.v2.runtime.Name;
/*      */ import com.sun.xml.internal.bind.v2.runtime.Transducer;
/*      */ import com.sun.xml.internal.bind.v2.runtime.XMLSerializer;
/*      */ import com.sun.xml.internal.bind.v2.runtime.output.Pcdata;
/*      */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Base64Data;
/*      */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*      */ import com.sun.xml.internal.bind.v2.util.ByteArrayOutputStreamEx;
/*      */ import com.sun.xml.internal.bind.v2.util.DataSourceSource;
/*      */ import java.awt.Component;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Image;
/*      */ import java.awt.MediaTracker;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.lang.reflect.Type;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.net.URL;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.UUID;
/*      */ import javax.activation.DataHandler;
/*      */ import javax.activation.DataSource;
/*      */ import javax.activation.MimeType;
/*      */ import javax.activation.MimeTypeParseException;
/*      */ import javax.imageio.ImageIO;
/*      */ import javax.imageio.ImageWriter;
/*      */ import javax.imageio.stream.ImageOutputStream;
/*      */ import javax.xml.bind.MarshalException;
/*      */ import javax.xml.bind.ValidationEvent;
/*      */ import javax.xml.bind.helpers.ValidationEventImpl;
/*      */ import javax.xml.datatype.DatatypeConstants;
/*      */ import javax.xml.datatype.Duration;
/*      */ import javax.xml.datatype.XMLGregorianCalendar;
/*      */ import javax.xml.namespace.NamespaceContext;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.stream.XMLStreamException;
/*      */ import javax.xml.transform.Source;
/*      */ import javax.xml.transform.Transformer;
/*      */ import javax.xml.transform.TransformerException;
/*      */ import javax.xml.transform.stream.StreamResult;
/*      */ import org.xml.sax.SAXException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class RuntimeBuiltinLeafInfoImpl<T>
/*      */   extends BuiltinLeafInfoImpl<Type, Class>
/*      */   implements RuntimeBuiltinLeafInfo, Transducer<T>
/*      */ {
/*      */   private RuntimeBuiltinLeafInfoImpl(Class type, QName... typeNames) {
/*  109 */     super(type, typeNames);
/*  110 */     LEAVES.put(type, this);
/*      */   }
/*      */   
/*      */   public final Class getClazz() {
/*  114 */     return (Class)getType();
/*      */   }
/*      */ 
/*      */   
/*      */   public final Transducer getTransducer() {
/*  119 */     return this;
/*      */   }
/*      */   
/*      */   public boolean useNamespace() {
/*  123 */     return false;
/*      */   }
/*      */   
/*      */   public final boolean isDefault() {
/*  127 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void declareNamespace(T o, XMLSerializer w) throws AccessorException {}
/*      */   
/*      */   public QName getTypeName(T instance) {
/*  134 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private static abstract class StringImpl<T>
/*      */     extends RuntimeBuiltinLeafInfoImpl<T>
/*      */   {
/*      */     protected StringImpl(Class type, QName... typeNames) {
/*  142 */       super(type, typeNames);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void writeText(XMLSerializer w, T o, String fieldName) throws IOException, SAXException, XMLStreamException, AccessorException {
/*  148 */       w.text(print(o), fieldName);
/*      */     }
/*      */     
/*      */     public void writeLeafElement(XMLSerializer w, Name tagName, T o, String fieldName) throws IOException, SAXException, XMLStreamException, AccessorException {
/*  152 */       w.leafElement(tagName, print(o), fieldName);
/*      */     }
/*      */     
/*      */     public abstract String print(T param1T) throws AccessorException;
/*      */   }
/*      */   
/*      */   private static abstract class PcdataImpl<T>
/*      */     extends RuntimeBuiltinLeafInfoImpl<T> {
/*      */     protected PcdataImpl(Class type, QName... typeNames) {
/*  161 */       super(type, typeNames);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final void writeText(XMLSerializer w, T o, String fieldName) throws IOException, SAXException, XMLStreamException, AccessorException {
/*  167 */       w.text(print(o), fieldName);
/*      */     }
/*      */     
/*      */     public final void writeLeafElement(XMLSerializer w, Name tagName, T o, String fieldName) throws IOException, SAXException, XMLStreamException, AccessorException {
/*  171 */       w.leafElement(tagName, print(o), fieldName);
/*      */     }
/*      */ 
/*      */     
/*      */     public abstract Pcdata print(T param1T) throws AccessorException;
/*      */   }
/*      */ 
/*      */   
/*  179 */   public static final Map<Type, RuntimeBuiltinLeafInfoImpl<?>> LEAVES = new HashMap<>(); public static final RuntimeBuiltinLeafInfoImpl<String> STRING;
/*      */   
/*      */   private static QName createXS(String typeName) {
/*  182 */     return new QName("http://www.w3.org/2001/XMLSchema", typeName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String DATE = "date";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final List<RuntimeBuiltinLeafInfoImpl<?>> builtinBeanInfos;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String MAP_ANYURI_TO_URI = "mapAnyUriToUri";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  202 */     String MAP_ANYURI_TO_URI_VALUE = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*      */         {
/*      */           public String run()
/*      */           {
/*  206 */             return System.getProperty("mapAnyUriToUri");
/*      */           }
/*      */         });
/*      */     
/*  210 */     (new QName[10])[0] = 
/*  211 */       createXS("string"); (new QName[10])[1] = 
/*  212 */       createXS("anySimpleType"); (new QName[10])[2] = 
/*  213 */       createXS("normalizedString"); (new QName[10])[3] = 
/*  214 */       createXS("anyURI"); (new QName[10])[4] = 
/*  215 */       createXS("token"); (new QName[10])[5] = 
/*  216 */       createXS("language"); (new QName[10])[6] = 
/*  217 */       createXS("Name"); (new QName[10])[7] = 
/*  218 */       createXS("NCName"); (new QName[10])[8] = 
/*  219 */       createXS("NMTOKEN"); (new QName[10])[9] = 
/*  220 */       createXS("ENTITY"); (new QName[9])[0] = 
/*      */ 
/*      */       
/*  223 */       createXS("string"); (new QName[9])[1] = 
/*  224 */       createXS("anySimpleType"); (new QName[9])[2] = 
/*  225 */       createXS("normalizedString"); (new QName[9])[3] = 
/*  226 */       createXS("token"); (new QName[9])[4] = 
/*  227 */       createXS("language"); (new QName[9])[5] = 
/*  228 */       createXS("Name"); (new QName[9])[6] = 
/*  229 */       createXS("NCName"); (new QName[9])[7] = 
/*  230 */       createXS("NMTOKEN"); (new QName[9])[8] = 
/*  231 */       createXS("ENTITY"); QName[] qnames = (MAP_ANYURI_TO_URI_VALUE == null) ? new QName[10] : new QName[9];
/*      */     
/*  233 */     STRING = new StringImplImpl(String.class, qnames);
/*      */     
/*  235 */     ArrayList<RuntimeBuiltinLeafInfoImpl<?>> secondaryList = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  253 */     secondaryList.add(new StringImpl<Character>(Character.class, new QName[] {
/*  254 */             createXS("unsignedShort")
/*      */           }) {
/*      */           public Character parse(CharSequence text) {
/*  257 */             return Character.valueOf((char)DatatypeConverterImpl._parseInt(text));
/*      */           }
/*      */           public String print(Character v) {
/*  260 */             return Integer.toString(v.charValue());
/*      */           }
/*      */         });
/*  263 */     secondaryList.add(new StringImpl<Calendar>(Calendar.class, new QName[] { DatatypeConstants.DATETIME })
/*      */         {
/*      */           public Calendar parse(CharSequence text) {
/*  266 */             return DatatypeConverterImpl._parseDateTime(text.toString());
/*      */           }
/*      */           public String print(Calendar v) {
/*  269 */             return DatatypeConverterImpl._printDateTime(v);
/*      */           }
/*      */         });
/*  272 */     secondaryList.add(new StringImpl<GregorianCalendar>(GregorianCalendar.class, new QName[] { DatatypeConstants.DATETIME })
/*      */         {
/*      */           public GregorianCalendar parse(CharSequence text) {
/*  275 */             return DatatypeConverterImpl._parseDateTime(text.toString());
/*      */           }
/*      */           public String print(GregorianCalendar v) {
/*  278 */             return DatatypeConverterImpl._printDateTime(v);
/*      */           }
/*      */         });
/*  281 */     secondaryList.add(new StringImpl<Date>(Date.class, new QName[] { DatatypeConstants.DATETIME })
/*      */         {
/*      */           public Date parse(CharSequence text) {
/*  284 */             return DatatypeConverterImpl._parseDateTime(text.toString()).getTime();
/*      */           }
/*      */           public String print(Date v) {
/*  287 */             XMLSerializer xs = XMLSerializer.getInstance();
/*  288 */             QName type = xs.getSchemaType();
/*  289 */             GregorianCalendar cal = new GregorianCalendar(0, 0, 0);
/*  290 */             cal.setTime(v);
/*  291 */             if (type != null && "http://www.w3.org/2001/XMLSchema".equals(type.getNamespaceURI()) && "date"
/*  292 */               .equals(type.getLocalPart())) {
/*  293 */               return DatatypeConverterImpl._printDate(cal);
/*      */             }
/*  295 */             return DatatypeConverterImpl._printDateTime(cal);
/*      */           }
/*      */         });
/*      */     
/*  299 */     secondaryList.add(new StringImpl<File>(File.class, new QName[] {
/*  300 */             createXS("string") }) {
/*      */           public File parse(CharSequence text) {
/*  302 */             return new File(WhiteSpaceProcessor.trim(text).toString());
/*      */           }
/*      */           public String print(File v) {
/*  305 */             return v.getPath();
/*      */           }
/*      */         });
/*  308 */     secondaryList.add(new StringImpl<URL>(URL.class, new QName[] {
/*  309 */             createXS("anyURI") }) {
/*      */           public URL parse(CharSequence text) throws SAXException {
/*  311 */             TODO.checkSpec("JSR222 Issue #42");
/*      */             try {
/*  313 */               return new URL(WhiteSpaceProcessor.trim(text).toString());
/*  314 */             } catch (MalformedURLException e) {
/*  315 */               UnmarshallingContext.getInstance().handleError(e);
/*  316 */               return null;
/*      */             } 
/*      */           }
/*      */           public String print(URL v) {
/*  320 */             return v.toExternalForm();
/*      */           }
/*      */         });
/*  323 */     if (MAP_ANYURI_TO_URI_VALUE == null) {
/*  324 */       secondaryList.add(new StringImpl<URI>(URI.class, new QName[] {
/*  325 */               createXS("string") }) {
/*      */             public URI parse(CharSequence text) throws SAXException {
/*      */               try {
/*  328 */                 return new URI(text.toString());
/*  329 */               } catch (URISyntaxException e) {
/*  330 */                 UnmarshallingContext.getInstance().handleError(e);
/*  331 */                 return null;
/*      */               } 
/*      */             }
/*      */             
/*      */             public String print(URI v) {
/*  336 */               return v.toString();
/*      */             }
/*      */           });
/*      */     }
/*  340 */     secondaryList.add(new StringImpl<Class>(Class.class, new QName[] {
/*  341 */             createXS("string") }) {
/*      */           public Class parse(CharSequence text) throws SAXException {
/*  343 */             TODO.checkSpec("JSR222 Issue #42");
/*      */             try {
/*  345 */               String name = WhiteSpaceProcessor.trim(text).toString();
/*  346 */               ClassLoader cl = (UnmarshallingContext.getInstance()).classLoader;
/*  347 */               if (cl == null) {
/*  348 */                 cl = Thread.currentThread().getContextClassLoader();
/*      */               }
/*  350 */               if (cl != null) {
/*  351 */                 return cl.loadClass(name);
/*      */               }
/*  353 */               return Class.forName(name);
/*  354 */             } catch (ClassNotFoundException e) {
/*  355 */               UnmarshallingContext.getInstance().handleError(e);
/*  356 */               return null;
/*      */             } 
/*      */           }
/*      */           public String print(Class v) {
/*  360 */             return v.getName();
/*      */           }
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  368 */     secondaryList.add(new PcdataImpl<Image>(Image.class, new QName[] {
/*  369 */             createXS("base64Binary") }) {
/*      */           public Image parse(CharSequence text) throws SAXException {
/*      */             try {
/*      */               InputStream is;
/*  373 */               if (text instanceof Base64Data) {
/*  374 */                 is = ((Base64Data)text).getInputStream();
/*      */               } else {
/*  376 */                 is = new ByteArrayInputStream(RuntimeBuiltinLeafInfoImpl.decodeBase64(text));
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               try {
/*  383 */                 return ImageIO.read(is);
/*      */               } finally {
/*  385 */                 is.close();
/*      */               } 
/*  387 */             } catch (IOException e) {
/*  388 */               UnmarshallingContext.getInstance().handleError(e);
/*  389 */               return null;
/*      */             } 
/*      */           }
/*      */           
/*      */           private BufferedImage convertToBufferedImage(Image image) throws IOException {
/*  394 */             if (image instanceof BufferedImage) {
/*  395 */               return (BufferedImage)image;
/*      */             }
/*      */             
/*  398 */             MediaTracker tracker = new MediaTracker(new Component() {  });
/*  399 */             tracker.addImage(image, 0);
/*      */             try {
/*  401 */               tracker.waitForAll();
/*  402 */             } catch (InterruptedException e) {
/*  403 */               throw new IOException(e.getMessage());
/*      */             } 
/*      */ 
/*      */             
/*  407 */             BufferedImage bufImage = new BufferedImage(image.getWidth(null), image.getHeight(null), 2);
/*      */ 
/*      */             
/*  410 */             Graphics g = bufImage.createGraphics();
/*  411 */             g.drawImage(image, 0, 0, null);
/*  412 */             return bufImage;
/*      */           }
/*      */ 
/*      */           
/*      */           public Base64Data print(Image v) {
/*  417 */             ByteArrayOutputStreamEx imageData = new ByteArrayOutputStreamEx();
/*  418 */             XMLSerializer xs = XMLSerializer.getInstance();
/*      */             
/*  420 */             String mimeType = xs.getXMIMEContentType();
/*  421 */             if (mimeType == null || mimeType.startsWith("image/*"))
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  427 */               mimeType = "image/png";
/*      */             }
/*      */             try {
/*  430 */               Iterator<ImageWriter> itr = ImageIO.getImageWritersByMIMEType(mimeType);
/*  431 */               if (itr.hasNext()) {
/*  432 */                 ImageWriter w = itr.next();
/*  433 */                 ImageOutputStream os = ImageIO.createImageOutputStream(imageData);
/*  434 */                 w.setOutput(os);
/*  435 */                 w.write(convertToBufferedImage(v));
/*  436 */                 os.close();
/*  437 */                 w.dispose();
/*      */               } else {
/*      */                 
/*  440 */                 xs.handleEvent((ValidationEvent)new ValidationEventImpl(1, Messages.NO_IMAGE_WRITER
/*      */                       
/*  442 */                       .format(new Object[] { mimeType }, ), xs
/*  443 */                       .getCurrentLocation(null)));
/*      */                 
/*  445 */                 throw new RuntimeException("no encoder for MIME type " + mimeType);
/*      */               } 
/*  447 */             } catch (IOException e) {
/*  448 */               xs.handleError(e);
/*      */               
/*  450 */               throw new RuntimeException(e);
/*      */             } 
/*  452 */             Base64Data bd = new Base64Data();
/*  453 */             imageData.set(bd, mimeType);
/*  454 */             return bd;
/*      */           }
/*      */         });
/*  457 */     secondaryList.add(new PcdataImpl<DataHandler>(DataHandler.class, new QName[] {
/*  458 */             createXS("base64Binary") }) {
/*      */           public DataHandler parse(CharSequence text) {
/*  460 */             if (text instanceof Base64Data) {
/*  461 */               return ((Base64Data)text).getDataHandler();
/*      */             }
/*  463 */             return new DataHandler((DataSource)new ByteArrayDataSource(RuntimeBuiltinLeafInfoImpl.decodeBase64(text), 
/*  464 */                   UnmarshallingContext.getInstance().getXMIMEContentType()));
/*      */           }
/*      */           
/*      */           public Base64Data print(DataHandler v) {
/*  468 */             Base64Data bd = new Base64Data();
/*  469 */             bd.set(v);
/*  470 */             return bd;
/*      */           }
/*      */         });
/*  473 */     secondaryList.add(new PcdataImpl<Source>(Source.class, new QName[] {
/*  474 */             createXS("base64Binary") }) {
/*      */           public Source parse(CharSequence text) throws SAXException {
/*      */             try {
/*  477 */               if (text instanceof Base64Data) {
/*  478 */                 return (Source)new DataSourceSource(((Base64Data)text).getDataHandler());
/*      */               }
/*  480 */               return (Source)new DataSourceSource((DataSource)new ByteArrayDataSource(RuntimeBuiltinLeafInfoImpl.decodeBase64(text), 
/*  481 */                     UnmarshallingContext.getInstance().getXMIMEContentType()));
/*  482 */             } catch (MimeTypeParseException e) {
/*  483 */               UnmarshallingContext.getInstance().handleError((Exception)e);
/*  484 */               return null;
/*      */             } 
/*      */           }
/*      */           
/*      */           public Base64Data print(Source v) {
/*  489 */             XMLSerializer xs = XMLSerializer.getInstance();
/*  490 */             Base64Data bd = new Base64Data();
/*      */             
/*  492 */             String contentType = xs.getXMIMEContentType();
/*  493 */             MimeType mt = null;
/*  494 */             if (contentType != null) {
/*      */               try {
/*  496 */                 mt = new MimeType(contentType);
/*  497 */               } catch (MimeTypeParseException e) {
/*  498 */                 xs.handleError((Exception)e);
/*      */               } 
/*      */             }
/*      */             
/*  502 */             if (v instanceof DataSourceSource) {
/*      */ 
/*      */               
/*  505 */               DataSource ds = ((DataSourceSource)v).getDataSource();
/*      */               
/*  507 */               String dsct = ds.getContentType();
/*  508 */               if (dsct != null && (contentType == null || contentType.equals(dsct))) {
/*  509 */                 bd.set(new DataHandler(ds));
/*  510 */                 return bd;
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  517 */             String charset = null;
/*  518 */             if (mt != null)
/*  519 */               charset = mt.getParameter("charset"); 
/*  520 */             if (charset == null) {
/*  521 */               charset = "UTF-8";
/*      */             }
/*      */             try {
/*  524 */               ByteArrayOutputStreamEx baos = new ByteArrayOutputStreamEx();
/*  525 */               Transformer tr = xs.getIdentityTransformer();
/*  526 */               String defaultEncoding = tr.getOutputProperty("encoding");
/*  527 */               tr.setOutputProperty("encoding", charset);
/*  528 */               tr.transform(v, new StreamResult(new OutputStreamWriter((OutputStream)baos, charset)));
/*  529 */               tr.setOutputProperty("encoding", defaultEncoding);
/*  530 */               baos.set(bd, "application/xml; charset=" + charset);
/*  531 */               return bd;
/*  532 */             } catch (TransformerException e) {
/*      */               
/*  534 */               xs.handleError(e);
/*  535 */             } catch (UnsupportedEncodingException e) {
/*  536 */               xs.handleError(e);
/*      */             } 
/*      */ 
/*      */             
/*  540 */             bd.set(new byte[0], "application/xml");
/*  541 */             return bd;
/*      */           }
/*      */         });
/*  544 */     secondaryList.add(new StringImpl<XMLGregorianCalendar>(XMLGregorianCalendar.class, new QName[] {
/*      */             
/*  546 */             createXS("anySimpleType"), DatatypeConstants.DATE, DatatypeConstants.DATETIME, DatatypeConstants.TIME, DatatypeConstants.GMONTH, DatatypeConstants.GDAY, DatatypeConstants.GYEAR, DatatypeConstants.GYEARMONTH, DatatypeConstants.GMONTHDAY
/*      */           })
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public String print(XMLGregorianCalendar cal)
/*      */           {
/*  557 */             XMLSerializer xs = XMLSerializer.getInstance();
/*      */             
/*  559 */             QName type = xs.getSchemaType();
/*  560 */             if (type != null) {
/*      */               try {
/*  562 */                 RuntimeBuiltinLeafInfoImpl.checkXmlGregorianCalendarFieldRef(type, cal);
/*  563 */                 String format = (String)RuntimeBuiltinLeafInfoImpl.xmlGregorianCalendarFormatString.get(type);
/*  564 */                 if (format != null) {
/*  565 */                   return format(format, cal);
/*      */                 }
/*  567 */               } catch (MarshalException e) {
/*      */                 
/*  569 */                 xs.handleEvent((ValidationEvent)new ValidationEventImpl(0, e.getMessage(), xs
/*  570 */                       .getCurrentLocation(null)));
/*  571 */                 return "";
/*      */               } 
/*      */             }
/*  574 */             return cal.toXMLFormat();
/*      */           }
/*      */           
/*      */           public XMLGregorianCalendar parse(CharSequence lexical) throws SAXException {
/*      */             try {
/*  579 */               return DatatypeConverterImpl.getDatatypeFactory()
/*  580 */                 .newXMLGregorianCalendar(lexical.toString().trim());
/*  581 */             } catch (Exception e) {
/*  582 */               UnmarshallingContext.getInstance().handleError(e);
/*  583 */               return null;
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           private String format(String format, XMLGregorianCalendar value) {
/*  589 */             StringBuilder buf = new StringBuilder();
/*  590 */             int fidx = 0, flen = format.length();
/*      */             
/*  592 */             while (fidx < flen) {
/*  593 */               int offset; char fch = format.charAt(fidx++);
/*  594 */               if (fch != '%') {
/*  595 */                 buf.append(fch);
/*      */                 
/*      */                 continue;
/*      */               } 
/*  599 */               switch (format.charAt(fidx++)) {
/*      */                 case 'Y':
/*  601 */                   printNumber(buf, value.getEonAndYear(), 4);
/*      */                   continue;
/*      */                 case 'M':
/*  604 */                   printNumber(buf, value.getMonth(), 2);
/*      */                   continue;
/*      */                 case 'D':
/*  607 */                   printNumber(buf, value.getDay(), 2);
/*      */                   continue;
/*      */                 case 'h':
/*  610 */                   printNumber(buf, value.getHour(), 2);
/*      */                   continue;
/*      */                 case 'm':
/*  613 */                   printNumber(buf, value.getMinute(), 2);
/*      */                   continue;
/*      */                 case 's':
/*  616 */                   printNumber(buf, value.getSecond(), 2);
/*  617 */                   if (value.getFractionalSecond() != null) {
/*  618 */                     String frac = value.getFractionalSecond().toPlainString();
/*      */                     
/*  620 */                     buf.append(frac.substring(1, frac.length()));
/*      */                   } 
/*      */                   continue;
/*      */                 case 'z':
/*  624 */                   offset = value.getTimezone();
/*  625 */                   if (offset == 0) {
/*  626 */                     buf.append('Z'); continue;
/*  627 */                   }  if (offset != Integer.MIN_VALUE) {
/*  628 */                     if (offset < 0) {
/*  629 */                       buf.append('-');
/*  630 */                       offset *= -1;
/*      */                     } else {
/*  632 */                       buf.append('+');
/*      */                     } 
/*  634 */                     printNumber(buf, offset / 60, 2);
/*  635 */                     buf.append(':');
/*  636 */                     printNumber(buf, offset % 60, 2);
/*      */                   } 
/*      */                   continue;
/*      */               } 
/*  640 */               throw new InternalError();
/*      */             } 
/*      */ 
/*      */             
/*  644 */             return buf.toString();
/*      */           }
/*      */           private void printNumber(StringBuilder out, BigInteger number, int nDigits) {
/*  647 */             String s = number.toString();
/*  648 */             for (int i = s.length(); i < nDigits; i++)
/*  649 */               out.append('0'); 
/*  650 */             out.append(s);
/*      */           }
/*      */           private void printNumber(StringBuilder out, int number, int nDigits) {
/*  653 */             String s = String.valueOf(number);
/*  654 */             for (int i = s.length(); i < nDigits; i++)
/*  655 */               out.append('0'); 
/*  656 */             out.append(s);
/*      */           }
/*      */           
/*      */           public QName getTypeName(XMLGregorianCalendar cal) {
/*  660 */             return cal.getXMLSchemaType();
/*      */           }
/*      */         });
/*      */     
/*  664 */     ArrayList<RuntimeBuiltinLeafInfoImpl<?>> primaryList = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  669 */     primaryList.add(STRING);
/*  670 */     primaryList.add(new StringImpl<Boolean>(Boolean.class, new QName[] {
/*  671 */             createXS("boolean")
/*      */           }) {
/*      */           public Boolean parse(CharSequence text) {
/*  674 */             return DatatypeConverterImpl._parseBoolean(text);
/*      */           }
/*      */           
/*      */           public String print(Boolean v) {
/*  678 */             return v.toString();
/*      */           }
/*      */         });
/*  681 */     primaryList.add(new PcdataImpl<byte[]>(byte[].class, new QName[] {
/*  682 */             createXS("base64Binary"), 
/*  683 */             createXS("hexBinary")
/*      */           }) {
/*      */           public byte[] parse(CharSequence text) {
/*  686 */             return RuntimeBuiltinLeafInfoImpl.decodeBase64(text);
/*      */           }
/*      */           
/*      */           public Base64Data print(byte[] v) {
/*  690 */             XMLSerializer w = XMLSerializer.getInstance();
/*  691 */             Base64Data bd = new Base64Data();
/*  692 */             String mimeType = w.getXMIMEContentType();
/*  693 */             bd.set(v, mimeType);
/*  694 */             return bd;
/*      */           }
/*      */         });
/*  697 */     primaryList.add(new StringImpl<Byte>(Byte.class, new QName[] {
/*  698 */             createXS("byte")
/*      */           }) {
/*      */           public Byte parse(CharSequence text) {
/*  701 */             return Byte.valueOf(DatatypeConverterImpl._parseByte(text));
/*      */           }
/*      */           
/*      */           public String print(Byte v) {
/*  705 */             return DatatypeConverterImpl._printByte(v.byteValue());
/*      */           }
/*      */         });
/*  708 */     primaryList.add(new StringImpl<Short>(Short.class, new QName[] {
/*  709 */             createXS("short"), 
/*  710 */             createXS("unsignedByte")
/*      */           }) {
/*      */           public Short parse(CharSequence text) {
/*  713 */             return Short.valueOf(DatatypeConverterImpl._parseShort(text));
/*      */           }
/*      */           
/*      */           public String print(Short v) {
/*  717 */             return DatatypeConverterImpl._printShort(v.shortValue());
/*      */           }
/*      */         });
/*  720 */     primaryList.add(new StringImpl<Integer>(Integer.class, new QName[] {
/*  721 */             createXS("int"), 
/*  722 */             createXS("unsignedShort")
/*      */           }) {
/*      */           public Integer parse(CharSequence text) {
/*  725 */             return Integer.valueOf(DatatypeConverterImpl._parseInt(text));
/*      */           }
/*      */           
/*      */           public String print(Integer v) {
/*  729 */             return DatatypeConverterImpl._printInt(v.intValue());
/*      */           }
/*      */         });
/*  732 */     primaryList.add(new StringImpl<Long>(Long.class, new QName[] {
/*      */             
/*  734 */             createXS("long"), 
/*  735 */             createXS("unsignedInt")
/*      */           }) {
/*      */           public Long parse(CharSequence text) {
/*  738 */             return Long.valueOf(DatatypeConverterImpl._parseLong(text));
/*      */           }
/*      */           
/*      */           public String print(Long v) {
/*  742 */             return DatatypeConverterImpl._printLong(v.longValue());
/*      */           }
/*      */         });
/*  745 */     primaryList.add(new StringImpl<Float>(Float.class, new QName[] {
/*      */             
/*  747 */             createXS("float")
/*      */           }) {
/*      */           public Float parse(CharSequence text) {
/*  750 */             return Float.valueOf(DatatypeConverterImpl._parseFloat(text.toString()));
/*      */           }
/*      */           
/*      */           public String print(Float v) {
/*  754 */             return DatatypeConverterImpl._printFloat(v.floatValue());
/*      */           }
/*      */         });
/*  757 */     primaryList.add(new StringImpl<Double>(Double.class, new QName[] {
/*      */             
/*  759 */             createXS("double")
/*      */           }) {
/*      */           public Double parse(CharSequence text) {
/*  762 */             return Double.valueOf(DatatypeConverterImpl._parseDouble(text));
/*      */           }
/*      */           
/*      */           public String print(Double v) {
/*  766 */             return DatatypeConverterImpl._printDouble(v.doubleValue());
/*      */           }
/*      */         });
/*  769 */     primaryList.add(new StringImpl<BigInteger>(BigInteger.class, new QName[] {
/*      */             
/*  771 */             createXS("integer"), 
/*  772 */             createXS("positiveInteger"), 
/*  773 */             createXS("negativeInteger"), 
/*  774 */             createXS("nonPositiveInteger"), 
/*  775 */             createXS("nonNegativeInteger"), 
/*  776 */             createXS("unsignedLong")
/*      */           }) {
/*      */           public BigInteger parse(CharSequence text) {
/*  779 */             return DatatypeConverterImpl._parseInteger(text);
/*      */           }
/*      */           
/*      */           public String print(BigInteger v) {
/*  783 */             return DatatypeConverterImpl._printInteger(v);
/*      */           }
/*      */         });
/*  786 */     primaryList.add(new StringImpl<BigDecimal>(BigDecimal.class, new QName[] {
/*      */             
/*  788 */             createXS("decimal")
/*      */           }) {
/*      */           public BigDecimal parse(CharSequence text) {
/*  791 */             return DatatypeConverterImpl._parseDecimal(text.toString());
/*      */           }
/*      */           
/*      */           public String print(BigDecimal v) {
/*  795 */             return DatatypeConverterImpl._printDecimal(v);
/*      */           }
/*      */         });
/*      */     
/*  799 */     primaryList.add(new StringImpl<QName>(QName.class, new QName[] {
/*      */             
/*  801 */             createXS("QName")
/*      */           }) {
/*      */           public QName parse(CharSequence text) throws SAXException {
/*      */             try {
/*  805 */               return DatatypeConverterImpl._parseQName(text.toString(), (NamespaceContext)UnmarshallingContext.getInstance());
/*  806 */             } catch (IllegalArgumentException e) {
/*  807 */               UnmarshallingContext.getInstance().handleError(e);
/*  808 */               return null;
/*      */             } 
/*      */           }
/*      */           
/*      */           public String print(QName v) {
/*  813 */             return DatatypeConverterImpl._printQName(v, (NamespaceContext)XMLSerializer.getInstance().getNamespaceContext());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean useNamespace() {
/*  818 */             return true;
/*      */           }
/*      */ 
/*      */           
/*      */           public void declareNamespace(QName v, XMLSerializer w) {
/*  823 */             w.getNamespaceContext().declareNamespace(v.getNamespaceURI(), v.getPrefix(), false);
/*      */           }
/*      */         });
/*  826 */     if (MAP_ANYURI_TO_URI_VALUE != null) {
/*  827 */       primaryList.add(new StringImpl<URI>(URI.class, new QName[] {
/*  828 */               createXS("anyURI") }) {
/*      */             public URI parse(CharSequence text) throws SAXException {
/*      */               try {
/*  831 */                 return new URI(text.toString());
/*  832 */               } catch (URISyntaxException e) {
/*  833 */                 UnmarshallingContext.getInstance().handleError(e);
/*  834 */                 return null;
/*      */               } 
/*      */             }
/*      */             
/*      */             public String print(URI v) {
/*  839 */               return v.toString();
/*      */             }
/*      */           });
/*      */     }
/*  843 */     primaryList.add(new StringImpl<Duration>(Duration.class, new QName[] {
/*  844 */             createXS("duration") }) {
/*      */           public String print(Duration duration) {
/*  846 */             return duration.toString();
/*      */           }
/*      */           
/*      */           public Duration parse(CharSequence lexical) {
/*  850 */             TODO.checkSpec("JSR222 Issue #42");
/*  851 */             return DatatypeConverterImpl.getDatatypeFactory().newDuration(lexical.toString());
/*      */           }
/*      */         });
/*      */     
/*  855 */     primaryList.add(new StringImpl<Void>(Void.class, new QName[0])
/*      */         {
/*      */ 
/*      */           
/*      */           public String print(Void value)
/*      */           {
/*  861 */             return "";
/*      */           }
/*      */           
/*      */           public Void parse(CharSequence lexical) {
/*  865 */             return null;
/*      */           }
/*      */         });
/*      */     
/*  869 */     List<RuntimeBuiltinLeafInfoImpl<?>> l = new ArrayList<>(secondaryList.size() + primaryList.size() + 1);
/*  870 */     l.addAll(secondaryList);
/*      */ 
/*      */     
/*      */     try {
/*  874 */       l.add(new UUIDImpl());
/*  875 */     } catch (LinkageError linkageError) {}
/*      */ 
/*      */ 
/*      */     
/*  879 */     l.addAll(primaryList);
/*      */     
/*  881 */     builtinBeanInfos = Collections.unmodifiableList(l);
/*      */   }
/*      */   
/*      */   private static byte[] decodeBase64(CharSequence text) {
/*  885 */     if (text instanceof Base64Data) {
/*  886 */       Base64Data base64Data = (Base64Data)text;
/*  887 */       return base64Data.getExact();
/*      */     } 
/*  889 */     return DatatypeConverterImpl._parseBase64Binary(text.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkXmlGregorianCalendarFieldRef(QName type, XMLGregorianCalendar cal) throws MarshalException {
/*  895 */     StringBuilder buf = new StringBuilder();
/*  896 */     int bitField = ((Integer)xmlGregorianCalendarFieldRef.get(type)).intValue();
/*  897 */     int l = 1;
/*  898 */     int pos = 0;
/*  899 */     while (bitField != 0) {
/*  900 */       int bit = bitField & 0x1;
/*  901 */       bitField >>>= 4;
/*  902 */       pos++;
/*      */       
/*  904 */       if (bit == 1) {
/*  905 */         switch (pos) {
/*      */           case 1:
/*  907 */             if (cal.getSecond() == Integer.MIN_VALUE) {
/*  908 */               buf.append("  ").append(Messages.XMLGREGORIANCALENDAR_SEC);
/*      */             }
/*      */           
/*      */           case 2:
/*  912 */             if (cal.getMinute() == Integer.MIN_VALUE) {
/*  913 */               buf.append("  ").append(Messages.XMLGREGORIANCALENDAR_MIN);
/*      */             }
/*      */           
/*      */           case 3:
/*  917 */             if (cal.getHour() == Integer.MIN_VALUE) {
/*  918 */               buf.append("  ").append(Messages.XMLGREGORIANCALENDAR_HR);
/*      */             }
/*      */           
/*      */           case 4:
/*  922 */             if (cal.getDay() == Integer.MIN_VALUE) {
/*  923 */               buf.append("  ").append(Messages.XMLGREGORIANCALENDAR_DAY);
/*      */             }
/*      */           
/*      */           case 5:
/*  927 */             if (cal.getMonth() == Integer.MIN_VALUE) {
/*  928 */               buf.append("  ").append(Messages.XMLGREGORIANCALENDAR_MONTH);
/*      */             }
/*      */           
/*      */           case 6:
/*  932 */             if (cal.getYear() == Integer.MIN_VALUE) {
/*  933 */               buf.append("  ").append(Messages.XMLGREGORIANCALENDAR_YEAR);
/*      */             }
/*      */         } 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */     } 
/*  941 */     if (buf.length() > 0) {
/*  942 */       throw new MarshalException(Messages.XMLGREGORIANCALENDAR_INVALID
/*  943 */           .format(new Object[] { type.getLocalPart() }) + buf
/*  944 */           .toString());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  951 */   private static final Map<QName, String> xmlGregorianCalendarFormatString = new HashMap<>();
/*      */   
/*      */   static {
/*  954 */     Map<QName, String> m = xmlGregorianCalendarFormatString;
/*      */     
/*  956 */     m.put(DatatypeConstants.DATETIME, "%Y-%M-%DT%h:%m:%s%z");
/*  957 */     m.put(DatatypeConstants.DATE, "%Y-%M-%D%z");
/*  958 */     m.put(DatatypeConstants.TIME, "%h:%m:%s%z");
/*  959 */     m.put(DatatypeConstants.GMONTH, "--%M--%z");
/*  960 */     m.put(DatatypeConstants.GDAY, "---%D%z");
/*  961 */     m.put(DatatypeConstants.GYEAR, "%Y%z");
/*  962 */     m.put(DatatypeConstants.GYEARMONTH, "%Y-%M%z");
/*  963 */     m.put(DatatypeConstants.GMONTHDAY, "--%M-%D%z");
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
/*  976 */   private static final Map<QName, Integer> xmlGregorianCalendarFieldRef = new HashMap<>();
/*      */   
/*      */   static {
/*  979 */     Map<QName, Integer> f = xmlGregorianCalendarFieldRef;
/*  980 */     f.put(DatatypeConstants.DATETIME, Integer.valueOf(17895697));
/*  981 */     f.put(DatatypeConstants.DATE, Integer.valueOf(17895424));
/*  982 */     f.put(DatatypeConstants.TIME, Integer.valueOf(16777489));
/*  983 */     f.put(DatatypeConstants.GDAY, Integer.valueOf(16781312));
/*  984 */     f.put(DatatypeConstants.GMONTH, Integer.valueOf(16842752));
/*  985 */     f.put(DatatypeConstants.GYEAR, Integer.valueOf(17825792));
/*  986 */     f.put(DatatypeConstants.GYEARMONTH, Integer.valueOf(17891328));
/*  987 */     f.put(DatatypeConstants.GMONTHDAY, Integer.valueOf(16846848));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class UUIDImpl
/*      */     extends StringImpl<UUID>
/*      */   {
/*      */     public UUIDImpl() {
/*  997 */       super(UUID.class, new QName[] { RuntimeBuiltinLeafInfoImpl.access$400("string") });
/*      */     }
/*      */     
/*      */     public UUID parse(CharSequence text) throws SAXException {
/* 1001 */       TODO.checkSpec("JSR222 Issue #42");
/*      */       try {
/* 1003 */         return UUID.fromString(WhiteSpaceProcessor.trim(text).toString());
/* 1004 */       } catch (IllegalArgumentException e) {
/* 1005 */         UnmarshallingContext.getInstance().handleError(e);
/* 1006 */         return null;
/*      */       } 
/*      */     }
/*      */     
/*      */     public String print(UUID v) {
/* 1011 */       return v.toString();
/*      */     }
/*      */   }
/*      */   
/*      */   private static class StringImplImpl
/*      */     extends StringImpl<String> {
/*      */     public StringImplImpl(Class type, QName[] typeNames) {
/* 1018 */       super(type, typeNames);
/*      */     }
/*      */     
/*      */     public String parse(CharSequence text) {
/* 1022 */       return text.toString();
/*      */     }
/*      */     
/*      */     public String print(String s) {
/* 1026 */       return s;
/*      */     }
/*      */ 
/*      */     
/*      */     public final void writeText(XMLSerializer w, String o, String fieldName) throws IOException, SAXException, XMLStreamException {
/* 1031 */       w.text(o, fieldName);
/*      */     }
/*      */ 
/*      */     
/*      */     public final void writeLeafElement(XMLSerializer w, Name tagName, String o, String fieldName) throws IOException, SAXException, XMLStreamException {
/* 1036 */       w.leafElement(tagName, o, fieldName);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/impl/RuntimeBuiltinLeafInfoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */