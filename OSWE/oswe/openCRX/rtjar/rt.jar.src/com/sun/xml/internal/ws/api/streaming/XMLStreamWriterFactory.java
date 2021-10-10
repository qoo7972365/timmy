/*     */ package com.sun.xml.internal.ws.api.streaming;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.encoding.HasEncoding;
/*     */ import com.sun.xml.internal.ws.streaming.XMLReaderException;
/*     */ import com.sun.xml.internal.ws.util.xml.XMLStreamWriterFilter;
/*     */ import java.io.OutputStream;
/*     */ import java.io.StringWriter;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.stream.XMLOutputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.stream.StreamResult;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class XMLStreamWriterFactory
/*     */ {
/*  60 */   private static final Logger LOGGER = Logger.getLogger(XMLStreamWriterFactory.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   private static volatile ContextClassloaderLocal<XMLStreamWriterFactory> writerFactory = new ContextClassloaderLocal<XMLStreamWriterFactory>()
/*     */     {
/*     */       
/*     */       protected XMLStreamWriterFactory initialValue()
/*     */       {
/*  70 */         XMLOutputFactory xof = null;
/*  71 */         if (Boolean.getBoolean(XMLStreamWriterFactory.class.getName() + ".woodstox")) {
/*     */           try {
/*  73 */             xof = (XMLOutputFactory)Class.forName("com.ctc.wstx.stax.WstxOutputFactory").newInstance();
/*  74 */           } catch (Exception exception) {}
/*     */         }
/*     */ 
/*     */         
/*  78 */         if (xof == null) {
/*  79 */           xof = XMLOutputFactory.newInstance();
/*     */         }
/*     */         
/*  82 */         XMLStreamWriterFactory f = null;
/*     */ 
/*     */ 
/*     */         
/*  86 */         if (!Boolean.getBoolean(XMLStreamWriterFactory.class.getName() + ".noPool")) {
/*     */           try {
/*  88 */             Class<?> clazz = xof.createXMLStreamWriter(new StringWriter()).getClass();
/*  89 */             if (clazz.getName().startsWith("com.sun.xml.internal.stream.")) {
/*  90 */               f = new XMLStreamWriterFactory.Zephyr(xof, clazz);
/*     */             }
/*  92 */           } catch (XMLStreamException ex) {
/*  93 */             Logger.getLogger(XMLStreamWriterFactory.class.getName()).log(Level.INFO, (String)null, ex);
/*  94 */           } catch (NoSuchMethodException ex) {
/*  95 */             Logger.getLogger(XMLStreamWriterFactory.class.getName()).log(Level.INFO, (String)null, ex);
/*     */           } 
/*     */         }
/*     */         
/*  99 */         if (f == null)
/*     */         {
/* 101 */           if (xof.getClass().getName().equals("com.ctc.wstx.stax.WstxOutputFactory"))
/* 102 */             f = new XMLStreamWriterFactory.NoLock(xof); 
/*     */         }
/* 104 */         if (f == null) {
/* 105 */           f = new XMLStreamWriterFactory.Default(xof);
/*     */         }
/* 107 */         if (XMLStreamWriterFactory.LOGGER.isLoggable(Level.FINE)) {
/* 108 */           XMLStreamWriterFactory.LOGGER.log(Level.FINE, "XMLStreamWriterFactory instance is = {0}", f);
/*     */         }
/* 110 */         return f;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void recycle(XMLStreamWriter r) {
/* 156 */     get().doRecycle(r);
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
/*     */   @NotNull
/*     */   public static XMLStreamWriterFactory get() {
/* 175 */     return writerFactory.get();
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
/*     */   public static void set(@NotNull XMLStreamWriterFactory f) {
/* 187 */     if (f == null) throw new IllegalArgumentException(); 
/* 188 */     writerFactory.set(f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XMLStreamWriter create(OutputStream out) {
/* 195 */     return get().doCreate(out);
/*     */   }
/*     */   
/*     */   public static XMLStreamWriter create(OutputStream out, String encoding) {
/* 199 */     return get().doCreate(out, encoding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XMLStreamWriter createXMLStreamWriter(OutputStream out) {
/* 207 */     return create(out);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XMLStreamWriter createXMLStreamWriter(OutputStream out, String encoding) {
/* 215 */     return create(out, encoding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XMLStreamWriter createXMLStreamWriter(OutputStream out, String encoding, boolean declare) {
/* 223 */     return create(out, encoding);
/*     */   }
/*     */   
/*     */   public abstract XMLStreamWriter doCreate(OutputStream paramOutputStream);
/*     */   
/*     */   public abstract XMLStreamWriter doCreate(OutputStream paramOutputStream, String paramString);
/*     */   
/*     */   public abstract void doRecycle(XMLStreamWriter paramXMLStreamWriter);
/*     */   
/*     */   public static interface RecycleAware {
/*     */     void onRecycled(); }
/*     */   
/*     */   public static final class Default extends XMLStreamWriterFactory { private final XMLOutputFactory xof;
/*     */     
/*     */     public Default(XMLOutputFactory xof) {
/* 238 */       this.xof = xof;
/*     */     }
/*     */ 
/*     */     
/*     */     public XMLStreamWriter doCreate(OutputStream out) {
/* 243 */       return doCreate(out, "UTF-8");
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized XMLStreamWriter doCreate(OutputStream out, String encoding) {
/*     */       try {
/* 249 */         XMLStreamWriter writer = this.xof.createXMLStreamWriter(out, encoding);
/* 250 */         return (XMLStreamWriter)new XMLStreamWriterFactory.HasEncodingWriter(writer, encoding);
/* 251 */       } catch (XMLStreamException e) {
/* 252 */         throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void doRecycle(XMLStreamWriter r) {} }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Zephyr
/*     */     extends XMLStreamWriterFactory
/*     */   {
/*     */     private final XMLOutputFactory xof;
/*     */ 
/*     */     
/* 270 */     private final ThreadLocal<XMLStreamWriter> pool = new ThreadLocal<>();
/*     */     private final Method resetMethod;
/*     */     private final Method setOutputMethod;
/*     */     private final Class zephyrClass;
/*     */     
/*     */     public static XMLStreamWriterFactory newInstance(XMLOutputFactory xof) {
/*     */       try {
/* 277 */         Class<?> clazz = xof.createXMLStreamWriter(new StringWriter()).getClass();
/*     */         
/* 279 */         if (!clazz.getName().startsWith("com.sun.xml.internal.stream.")) {
/* 280 */           return null;
/*     */         }
/* 282 */         return new Zephyr(xof, clazz);
/* 283 */       } catch (XMLStreamException e) {
/* 284 */         return null;
/* 285 */       } catch (NoSuchMethodException e) {
/* 286 */         return null;
/*     */       } 
/*     */     }
/*     */     
/*     */     private Zephyr(XMLOutputFactory xof, Class clazz) throws NoSuchMethodException {
/* 291 */       this.xof = xof;
/*     */       
/* 293 */       this.zephyrClass = clazz;
/* 294 */       this.setOutputMethod = clazz.getMethod("setOutput", new Class[] { StreamResult.class, String.class });
/* 295 */       this.resetMethod = clazz.getMethod("reset", new Class[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private XMLStreamWriter fetch() {
/* 302 */       XMLStreamWriter sr = this.pool.get();
/* 303 */       if (sr == null) return null; 
/* 304 */       this.pool.set(null);
/* 305 */       return sr;
/*     */     }
/*     */ 
/*     */     
/*     */     public XMLStreamWriter doCreate(OutputStream out) {
/* 310 */       return doCreate(out, "UTF-8");
/*     */     }
/*     */ 
/*     */     
/*     */     public XMLStreamWriter doCreate(OutputStream out, String encoding) {
/* 315 */       XMLStreamWriter xsw = fetch();
/* 316 */       if (xsw != null) {
/*     */         
/*     */         try {
/* 319 */           this.resetMethod.invoke(xsw, new Object[0]);
/* 320 */           this.setOutputMethod.invoke(xsw, new Object[] { new StreamResult(out), encoding });
/* 321 */         } catch (IllegalAccessException e) {
/* 322 */           throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/* 323 */         } catch (InvocationTargetException e) {
/* 324 */           throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/*     */         } 
/*     */       } else {
/*     */         
/*     */         try {
/* 329 */           xsw = this.xof.createXMLStreamWriter(out, encoding);
/* 330 */         } catch (XMLStreamException e) {
/* 331 */           throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/*     */         } 
/*     */       } 
/* 334 */       return (XMLStreamWriter)new XMLStreamWriterFactory.HasEncodingWriter(xsw, encoding);
/*     */     }
/*     */ 
/*     */     
/*     */     public void doRecycle(XMLStreamWriter r) {
/* 339 */       if (r instanceof XMLStreamWriterFactory.HasEncodingWriter) {
/* 340 */         r = ((XMLStreamWriterFactory.HasEncodingWriter)r).getWriter();
/*     */       }
/* 342 */       if (this.zephyrClass.isInstance(r)) {
/*     */         
/*     */         try {
/* 345 */           r.close();
/* 346 */         } catch (XMLStreamException e) {
/* 347 */           throw new WebServiceException(e);
/*     */         } 
/* 349 */         this.pool.set(r);
/*     */       } 
/* 351 */       if (r instanceof XMLStreamWriterFactory.RecycleAware) {
/* 352 */         ((XMLStreamWriterFactory.RecycleAware)r).onRecycled();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class NoLock
/*     */     extends XMLStreamWriterFactory
/*     */   {
/*     */     private final XMLOutputFactory xof;
/*     */     
/*     */     public NoLock(XMLOutputFactory xof) {
/* 364 */       this.xof = xof;
/*     */     }
/*     */ 
/*     */     
/*     */     public XMLStreamWriter doCreate(OutputStream out) {
/* 369 */       return doCreate(out, "utf-8");
/*     */     }
/*     */ 
/*     */     
/*     */     public XMLStreamWriter doCreate(OutputStream out, String encoding) {
/*     */       try {
/* 375 */         XMLStreamWriter writer = this.xof.createXMLStreamWriter(out, encoding);
/* 376 */         return (XMLStreamWriter)new XMLStreamWriterFactory.HasEncodingWriter(writer, encoding);
/* 377 */       } catch (XMLStreamException e) {
/* 378 */         throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void doRecycle(XMLStreamWriter r) {}
/*     */   }
/*     */   
/*     */   public static class HasEncodingWriter
/*     */     extends XMLStreamWriterFilter
/*     */     implements HasEncoding
/*     */   {
/*     */     private final String encoding;
/*     */     
/*     */     HasEncodingWriter(XMLStreamWriter writer, String encoding) {
/* 393 */       super(writer);
/* 394 */       this.encoding = encoding;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getEncoding() {
/* 399 */       return this.encoding;
/*     */     }
/*     */     
/*     */     public XMLStreamWriter getWriter() {
/* 403 */       return this.writer;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/streaming/XMLStreamWriterFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */