/*     */ package com.sun.org.apache.xml.internal.serialize;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.dom.DOMMessageFormatter;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.io.Writer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SerializerFactoryImpl
/*     */   extends SerializerFactory
/*     */ {
/*     */   private String _method;
/*     */   
/*     */   SerializerFactoryImpl(String method) {
/*  48 */     this._method = method;
/*  49 */     if (!this._method.equals("xml") && 
/*  50 */       !this._method.equals("html") && 
/*  51 */       !this._method.equals("xhtml") && 
/*  52 */       !this._method.equals("text")) {
/*  53 */       String msg = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "MethodNotSupported", new Object[] { method });
/*  54 */       throw new IllegalArgumentException(msg);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Serializer makeSerializer(OutputFormat format) {
/*  63 */     Serializer serializer = getSerializer(format);
/*  64 */     serializer.setOutputFormat(format);
/*  65 */     return serializer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Serializer makeSerializer(Writer writer, OutputFormat format) {
/*  75 */     Serializer serializer = getSerializer(format);
/*  76 */     serializer.setOutputCharStream(writer);
/*  77 */     return serializer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Serializer makeSerializer(OutputStream output, OutputFormat format) throws UnsupportedEncodingException {
/*  87 */     Serializer serializer = getSerializer(format);
/*  88 */     serializer.setOutputByteStream(output);
/*  89 */     return serializer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Serializer getSerializer(OutputFormat format) {
/*  95 */     if (this._method.equals("xml"))
/*  96 */       return new XMLSerializer(format); 
/*  97 */     if (this._method.equals("html"))
/*  98 */       return new HTMLSerializer(format); 
/*  99 */     if (this._method.equals("xhtml"))
/* 100 */       return new XHTMLSerializer(format); 
/* 101 */     if (this._method.equals("text")) {
/* 102 */       return new TextSerializer();
/*     */     }
/* 104 */     String msg = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "MethodNotSupported", new Object[] { this._method });
/* 105 */     throw new IllegalStateException(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getSupportedMethod() {
/* 112 */     return this._method;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/serialize/SerializerFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */