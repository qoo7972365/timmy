/*     */ package javax.xml.bind;
/*     */ 
/*     */ import java.beans.Introspector;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JAXB
/*     */ {
/*     */   private static volatile WeakReference<Cache> cache;
/*     */   
/*     */   private static final class Cache
/*     */   {
/*     */     final Class type;
/*     */     final JAXBContext context;
/*     */     
/*     */     public Cache(Class type) throws JAXBException {
/* 111 */       this.type = type;
/* 112 */       this.context = JAXBContext.newInstance(new Class[] { type });
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
/*     */   private static <T> JAXBContext getContext(Class<T> type) throws JAXBException {
/* 131 */     WeakReference<Cache> c = cache;
/* 132 */     if (c != null) {
/* 133 */       Cache cache = c.get();
/* 134 */       if (cache != null && cache.type == type) {
/* 135 */         return cache.context;
/*     */       }
/*     */     } 
/*     */     
/* 139 */     Cache d = new Cache(type);
/* 140 */     cache = new WeakReference<>(d);
/*     */     
/* 142 */     return d.context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T unmarshal(File xml, Class<T> type) {
/*     */     try {
/* 153 */       JAXBElement<T> item = getContext(type).createUnmarshaller().unmarshal(new StreamSource(xml), type);
/* 154 */       return item.getValue();
/* 155 */     } catch (JAXBException e) {
/* 156 */       throw new DataBindingException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T unmarshal(URL xml, Class<T> type) {
/*     */     try {
/* 168 */       JAXBElement<T> item = getContext(type).createUnmarshaller().unmarshal(toSource(xml), type);
/* 169 */       return item.getValue();
/* 170 */     } catch (JAXBException e) {
/* 171 */       throw new DataBindingException(e);
/* 172 */     } catch (IOException e) {
/* 173 */       throw new DataBindingException(e);
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
/*     */   public static <T> T unmarshal(URI xml, Class<T> type) {
/*     */     try {
/* 186 */       JAXBElement<T> item = getContext(type).createUnmarshaller().unmarshal(toSource(xml), type);
/* 187 */       return item.getValue();
/* 188 */     } catch (JAXBException e) {
/* 189 */       throw new DataBindingException(e);
/* 190 */     } catch (IOException e) {
/* 191 */       throw new DataBindingException(e);
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
/*     */   public static <T> T unmarshal(String xml, Class<T> type) {
/*     */     try {
/* 205 */       JAXBElement<T> item = getContext(type).createUnmarshaller().unmarshal(toSource(xml), type);
/* 206 */       return item.getValue();
/* 207 */     } catch (JAXBException e) {
/* 208 */       throw new DataBindingException(e);
/* 209 */     } catch (IOException e) {
/* 210 */       throw new DataBindingException(e);
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
/*     */   public static <T> T unmarshal(InputStream xml, Class<T> type) {
/*     */     try {
/* 223 */       JAXBElement<T> item = getContext(type).createUnmarshaller().unmarshal(toSource(xml), type);
/* 224 */       return item.getValue();
/* 225 */     } catch (JAXBException e) {
/* 226 */       throw new DataBindingException(e);
/* 227 */     } catch (IOException e) {
/* 228 */       throw new DataBindingException(e);
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
/*     */   public static <T> T unmarshal(Reader xml, Class<T> type) {
/*     */     try {
/* 242 */       JAXBElement<T> item = getContext(type).createUnmarshaller().unmarshal(toSource(xml), type);
/* 243 */       return item.getValue();
/* 244 */     } catch (JAXBException e) {
/* 245 */       throw new DataBindingException(e);
/* 246 */     } catch (IOException e) {
/* 247 */       throw new DataBindingException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T unmarshal(Source xml, Class<T> type) {
/*     */     try {
/* 259 */       JAXBElement<T> item = getContext(type).createUnmarshaller().unmarshal(toSource(xml), type);
/* 260 */       return item.getValue();
/* 261 */     } catch (JAXBException e) {
/* 262 */       throw new DataBindingException(e);
/* 263 */     } catch (IOException e) {
/* 264 */       throw new DataBindingException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Source toSource(Object xml) throws IOException {
/* 275 */     if (xml == null) {
/* 276 */       throw new IllegalArgumentException("no XML is given");
/*     */     }
/* 278 */     if (xml instanceof String) {
/*     */       try {
/* 280 */         xml = new URI((String)xml);
/* 281 */       } catch (URISyntaxException e) {
/* 282 */         xml = new File((String)xml);
/*     */       } 
/*     */     }
/* 285 */     if (xml instanceof File) {
/* 286 */       File file = (File)xml;
/* 287 */       return new StreamSource(file);
/*     */     } 
/* 289 */     if (xml instanceof URI) {
/* 290 */       URI uri = (URI)xml;
/* 291 */       xml = uri.toURL();
/*     */     } 
/* 293 */     if (xml instanceof URL) {
/* 294 */       URL url = (URL)xml;
/* 295 */       return new StreamSource(url.toExternalForm());
/*     */     } 
/* 297 */     if (xml instanceof InputStream) {
/* 298 */       InputStream in = (InputStream)xml;
/* 299 */       return new StreamSource(in);
/*     */     } 
/* 301 */     if (xml instanceof Reader) {
/* 302 */       Reader r = (Reader)xml;
/* 303 */       return new StreamSource(r);
/*     */     } 
/* 305 */     if (xml instanceof Source) {
/* 306 */       return (Source)xml;
/*     */     }
/* 308 */     throw new IllegalArgumentException("I don't understand how to handle " + xml.getClass());
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
/*     */   public static void marshal(Object jaxbObject, File xml) {
/* 332 */     _marshal(jaxbObject, xml);
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
/*     */   public static void marshal(Object jaxbObject, URL xml) {
/* 359 */     _marshal(jaxbObject, xml);
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
/*     */   public static void marshal(Object jaxbObject, URI xml) {
/* 383 */     _marshal(jaxbObject, xml);
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
/*     */   public static void marshal(Object jaxbObject, String xml) {
/* 408 */     _marshal(jaxbObject, xml);
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
/*     */   public static void marshal(Object jaxbObject, OutputStream xml) {
/* 432 */     _marshal(jaxbObject, xml);
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
/*     */   public static void marshal(Object jaxbObject, Writer xml) {
/* 456 */     _marshal(jaxbObject, xml);
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
/*     */   public static void marshal(Object jaxbObject, Result xml) {
/* 479 */     _marshal(jaxbObject, xml);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void _marshal(Object jaxbObject, Object xml) {
/*     */     try {
/*     */       JAXBContext context;
/* 558 */       if (jaxbObject instanceof JAXBElement) {
/* 559 */         context = getContext(((JAXBElement)jaxbObject).getDeclaredType());
/*     */       } else {
/* 561 */         Class<?> clazz = jaxbObject.getClass();
/* 562 */         XmlRootElement r = clazz.<XmlRootElement>getAnnotation(XmlRootElement.class);
/* 563 */         context = getContext(clazz);
/* 564 */         if (r == null)
/*     */         {
/* 566 */           jaxbObject = new JAXBElement(new QName(inferName(clazz)), clazz, jaxbObject);
/*     */         }
/*     */       } 
/*     */       
/* 570 */       Marshaller m = context.createMarshaller();
/* 571 */       m.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
/* 572 */       m.marshal(jaxbObject, toResult(xml));
/* 573 */     } catch (JAXBException e) {
/* 574 */       throw new DataBindingException(e);
/* 575 */     } catch (IOException e) {
/* 576 */       throw new DataBindingException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String inferName(Class clazz) {
/* 581 */     return Introspector.decapitalize(clazz.getSimpleName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Result toResult(Object xml) throws IOException {
/* 589 */     if (xml == null) {
/* 590 */       throw new IllegalArgumentException("no XML is given");
/*     */     }
/* 592 */     if (xml instanceof String) {
/*     */       try {
/* 594 */         xml = new URI((String)xml);
/* 595 */       } catch (URISyntaxException e) {
/* 596 */         xml = new File((String)xml);
/*     */       } 
/*     */     }
/* 599 */     if (xml instanceof File) {
/* 600 */       File file = (File)xml;
/* 601 */       return new StreamResult(file);
/*     */     } 
/* 603 */     if (xml instanceof URI) {
/* 604 */       URI uri = (URI)xml;
/* 605 */       xml = uri.toURL();
/*     */     } 
/* 607 */     if (xml instanceof URL) {
/* 608 */       URL url = (URL)xml;
/* 609 */       URLConnection con = url.openConnection();
/* 610 */       con.setDoOutput(true);
/* 611 */       con.setDoInput(false);
/* 612 */       con.connect();
/* 613 */       return new StreamResult(con.getOutputStream());
/*     */     } 
/* 615 */     if (xml instanceof OutputStream) {
/* 616 */       OutputStream os = (OutputStream)xml;
/* 617 */       return new StreamResult(os);
/*     */     } 
/* 619 */     if (xml instanceof Writer) {
/* 620 */       Writer w = (Writer)xml;
/* 621 */       return new StreamResult(w);
/*     */     } 
/* 623 */     if (xml instanceof Result) {
/* 624 */       return (Result)xml;
/*     */     }
/* 626 */     throw new IllegalArgumentException("I don't understand how to handle " + xml.getClass());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/bind/JAXB.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */