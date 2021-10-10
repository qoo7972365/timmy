/*     */ package com.sun.xml.internal.messaging.saaj.util.transform;
/*     */ 
/*     */ import com.sun.xml.internal.messaging.saaj.util.FastInfosetReflection;
/*     */ import com.sun.xml.internal.messaging.saaj.util.XMLDeclarationParser;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PushbackReader;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.Properties;
/*     */ import javax.xml.transform.ErrorListener;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.URIResolver;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import org.w3c.dom.Document;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EfficientStreamingTransformer
/*     */   extends Transformer
/*     */ {
/*  68 */   private final TransformerFactory transformerFactory = TransformerFactory.newInstance();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   private Transformer m_realTransformer = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   private Object m_fiDOMDocumentParser = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   private Object m_fiDOMDocumentSerializer = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void materialize() throws TransformerException {
/* 105 */     if (this.m_realTransformer == null) {
/* 106 */       this.m_realTransformer = this.transformerFactory.newTransformer();
/*     */     }
/*     */   }
/*     */   
/*     */   public void clearParameters() {
/* 111 */     if (this.m_realTransformer != null)
/* 112 */       this.m_realTransformer.clearParameters(); 
/*     */   }
/*     */   
/*     */   public ErrorListener getErrorListener() {
/*     */     try {
/* 117 */       materialize();
/* 118 */       return this.m_realTransformer.getErrorListener();
/* 119 */     } catch (TransformerException transformerException) {
/*     */ 
/*     */       
/* 122 */       return null;
/*     */     } 
/*     */   }
/*     */   public Properties getOutputProperties() {
/*     */     try {
/* 127 */       materialize();
/* 128 */       return this.m_realTransformer.getOutputProperties();
/* 129 */     } catch (TransformerException transformerException) {
/*     */ 
/*     */       
/* 132 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getOutputProperty(String str) throws IllegalArgumentException {
/*     */     try {
/* 138 */       materialize();
/* 139 */       return this.m_realTransformer.getOutputProperty(str);
/* 140 */     } catch (TransformerException transformerException) {
/*     */ 
/*     */       
/* 143 */       return null;
/*     */     } 
/*     */   }
/*     */   public Object getParameter(String str) {
/*     */     try {
/* 148 */       materialize();
/* 149 */       return this.m_realTransformer.getParameter(str);
/* 150 */     } catch (TransformerException transformerException) {
/*     */ 
/*     */       
/* 153 */       return null;
/*     */     } 
/*     */   }
/*     */   public URIResolver getURIResolver() {
/*     */     try {
/* 158 */       materialize();
/* 159 */       return this.m_realTransformer.getURIResolver();
/* 160 */     } catch (TransformerException transformerException) {
/*     */ 
/*     */       
/* 163 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setErrorListener(ErrorListener errorListener) throws IllegalArgumentException {
/*     */     try {
/* 170 */       materialize();
/* 171 */       this.m_realTransformer.setErrorListener(errorListener);
/* 172 */     } catch (TransformerException transformerException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOutputProperties(Properties properties) throws IllegalArgumentException {
/*     */     try {
/* 180 */       materialize();
/* 181 */       this.m_realTransformer.setOutputProperties(properties);
/* 182 */     } catch (TransformerException transformerException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOutputProperty(String str, String str1) throws IllegalArgumentException {
/*     */     try {
/* 190 */       materialize();
/* 191 */       this.m_realTransformer.setOutputProperty(str, str1);
/* 192 */     } catch (TransformerException transformerException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParameter(String str, Object obj) {
/*     */     try {
/* 199 */       materialize();
/* 200 */       this.m_realTransformer.setParameter(str, obj);
/* 201 */     } catch (TransformerException transformerException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setURIResolver(URIResolver uRIResolver) {
/*     */     try {
/* 208 */       materialize();
/* 209 */       this.m_realTransformer.setURIResolver(uRIResolver);
/* 210 */     } catch (TransformerException transformerException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InputStream getInputStreamFromSource(StreamSource s) throws TransformerException {
/* 218 */     InputStream stream = s.getInputStream();
/* 219 */     if (stream != null) {
/* 220 */       return stream;
/*     */     }
/* 222 */     if (s.getReader() != null) {
/* 223 */       return null;
/*     */     }
/* 225 */     String systemId = s.getSystemId();
/* 226 */     if (systemId != null) {
/*     */       try {
/* 228 */         String fileURL = systemId;
/*     */         
/* 230 */         if (systemId.startsWith("file:///")) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 239 */           String absolutePath = systemId.substring(7);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 246 */           boolean hasDriveDesignator = (absolutePath.indexOf(":") > 0);
/* 247 */           if (hasDriveDesignator) {
/* 248 */             String driveDesignatedPath = absolutePath.substring(1);
/*     */ 
/*     */             
/* 251 */             fileURL = driveDesignatedPath;
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 256 */             fileURL = absolutePath;
/*     */           } 
/*     */         } 
/*     */         
/*     */         try {
/* 261 */           return new FileInputStream(new File(new URI(fileURL)));
/* 262 */         } catch (URISyntaxException ex) {
/* 263 */           throw new TransformerException(ex);
/*     */         } 
/* 265 */       } catch (IOException e) {
/* 266 */         throw new TransformerException(e.toString());
/*     */       } 
/*     */     }
/*     */     
/* 270 */     throw new TransformerException("Unexpected StreamSource object");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void transform(Source source, Result result) throws TransformerException {
/* 281 */     if (source instanceof StreamSource && result instanceof StreamResult) {
/*     */       
/*     */       try {
/* 284 */         StreamSource streamSource = (StreamSource)source;
/* 285 */         InputStream is = getInputStreamFromSource(streamSource);
/*     */         
/* 287 */         OutputStream os = ((StreamResult)result).getOutputStream();
/* 288 */         if (os == null)
/*     */         {
/*     */           
/* 291 */           throw new TransformerException("Unexpected StreamResult object contains null OutputStream");
/*     */         }
/* 293 */         if (is != null) {
/* 294 */           if (is.markSupported()) {
/* 295 */             is.mark(2147483647);
/*     */           }
/* 297 */           byte[] b = new byte[8192]; int num;
/* 298 */           while ((num = is.read(b)) != -1) {
/* 299 */             os.write(b, 0, num);
/*     */           }
/* 301 */           if (is.markSupported()) {
/* 302 */             is.reset();
/*     */           }
/*     */           return;
/*     */         } 
/* 306 */         Reader reader = streamSource.getReader();
/* 307 */         if (reader != null) {
/*     */           
/* 309 */           if (reader.markSupported()) {
/* 310 */             reader.mark(2147483647);
/*     */           }
/* 312 */           PushbackReader pushbackReader = new PushbackReader(reader, 4096);
/*     */           
/* 314 */           XMLDeclarationParser ev = new XMLDeclarationParser(pushbackReader);
/*     */           
/*     */           try {
/* 317 */             ev.parse();
/* 318 */           } catch (Exception ex) {
/* 319 */             throw new TransformerException("Unable to run the JAXP transformer on a stream " + ex
/*     */                 
/* 321 */                 .getMessage());
/*     */           } 
/* 323 */           Writer writer = new OutputStreamWriter(os);
/*     */           
/* 325 */           ev.writeTo(writer);
/*     */ 
/*     */           
/* 328 */           char[] ac = new char[8192]; int num;
/* 329 */           while ((num = pushbackReader.read(ac)) != -1) {
/* 330 */             writer.write(ac, 0, num);
/*     */           }
/* 332 */           writer.flush();
/*     */           
/* 334 */           if (reader.markSupported())
/* 335 */             reader.reset(); 
/*     */           return;
/*     */         } 
/* 338 */       } catch (IOException e) {
/* 339 */         e.printStackTrace();
/* 340 */         throw new TransformerException(e.toString());
/*     */       } 
/*     */       
/* 343 */       throw new TransformerException("Unexpected StreamSource object");
/*     */     } 
/*     */     
/* 346 */     if (FastInfosetReflection.isFastInfosetSource(source) && result instanceof DOMResult) {
/*     */       
/*     */       try {
/*     */ 
/*     */         
/* 351 */         if (this.m_fiDOMDocumentParser == null) {
/* 352 */           this.m_fiDOMDocumentParser = FastInfosetReflection.DOMDocumentParser_new();
/*     */         }
/*     */ 
/*     */         
/* 356 */         FastInfosetReflection.DOMDocumentParser_parse(this.m_fiDOMDocumentParser, (Document)((DOMResult)result)
/*     */             
/* 358 */             .getNode(), 
/* 359 */             FastInfosetReflection.FastInfosetSource_getInputStream(source));
/*     */ 
/*     */ 
/*     */         
/*     */         return;
/* 364 */       } catch (Exception e) {
/* 365 */         throw new TransformerException(e);
/*     */       } 
/*     */     }
/*     */     
/* 369 */     if (source instanceof DOMSource && 
/* 370 */       FastInfosetReflection.isFastInfosetResult(result)) {
/*     */       
/*     */       try {
/*     */         
/* 374 */         if (this.m_fiDOMDocumentSerializer == null) {
/* 375 */           this.m_fiDOMDocumentSerializer = FastInfosetReflection.DOMDocumentSerializer_new();
/*     */         }
/*     */ 
/*     */         
/* 379 */         FastInfosetReflection.DOMDocumentSerializer_setOutputStream(this.m_fiDOMDocumentSerializer, 
/*     */             
/* 381 */             FastInfosetReflection.FastInfosetResult_getOutputStream(result));
/*     */ 
/*     */         
/* 384 */         FastInfosetReflection.DOMDocumentSerializer_serialize(this.m_fiDOMDocumentSerializer, ((DOMSource)source)
/*     */             
/* 386 */             .getNode());
/*     */ 
/*     */ 
/*     */         
/*     */         return;
/* 391 */       } catch (Exception e) {
/* 392 */         throw new TransformerException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 398 */     materialize();
/* 399 */     this.m_realTransformer.transform(source, result);
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
/*     */   public static Transformer newTransformer() {
/* 420 */     return new EfficientStreamingTransformer();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/util/transform/EfficientStreamingTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */