/*     */ package com.sun.xml.internal.fastinfoset.stax.factory;
/*     */ 
/*     */ import com.sun.xml.internal.fastinfoset.CommonResourceBundle;
/*     */ import com.sun.xml.internal.fastinfoset.stax.StAXDocumentSerializer;
/*     */ import com.sun.xml.internal.fastinfoset.stax.StAXManager;
/*     */ import com.sun.xml.internal.fastinfoset.stax.events.StAXEventWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Writer;
/*     */ import javax.xml.stream.XMLEventWriter;
/*     */ import javax.xml.stream.XMLOutputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StAXOutputFactory
/*     */   extends XMLOutputFactory
/*     */ {
/*  48 */   private StAXManager _manager = null;
/*     */ 
/*     */   
/*     */   public StAXOutputFactory() {
/*  52 */     this._manager = new StAXManager(2);
/*     */   }
/*     */   
/*     */   public XMLEventWriter createXMLEventWriter(Result result) throws XMLStreamException {
/*  56 */     return (XMLEventWriter)new StAXEventWriter(createXMLStreamWriter(result));
/*     */   }
/*     */   
/*     */   public XMLEventWriter createXMLEventWriter(Writer writer) throws XMLStreamException {
/*  60 */     return (XMLEventWriter)new StAXEventWriter(createXMLStreamWriter(writer));
/*     */   }
/*     */   
/*     */   public XMLEventWriter createXMLEventWriter(OutputStream outputStream) throws XMLStreamException {
/*  64 */     return (XMLEventWriter)new StAXEventWriter(createXMLStreamWriter(outputStream));
/*     */   }
/*     */   
/*     */   public XMLEventWriter createXMLEventWriter(OutputStream outputStream, String encoding) throws XMLStreamException {
/*  68 */     return (XMLEventWriter)new StAXEventWriter(createXMLStreamWriter(outputStream, encoding));
/*     */   }
/*     */   
/*     */   public XMLStreamWriter createXMLStreamWriter(Result result) throws XMLStreamException {
/*  72 */     if (result instanceof StreamResult) {
/*  73 */       StreamResult streamResult = (StreamResult)result;
/*  74 */       if (streamResult.getWriter() != null)
/*  75 */         return createXMLStreamWriter(streamResult.getWriter()); 
/*  76 */       if (streamResult.getOutputStream() != null)
/*  77 */         return createXMLStreamWriter(streamResult.getOutputStream()); 
/*  78 */       if (streamResult.getSystemId() != null) {
/*  79 */         FileWriter writer = null;
/*  80 */         boolean isError = true;
/*     */         
/*     */         try {
/*  83 */           writer = new FileWriter(new File(streamResult.getSystemId()));
/*  84 */           XMLStreamWriter streamWriter = createXMLStreamWriter(writer);
/*  85 */           isError = false;
/*     */           
/*  87 */           return streamWriter;
/*  88 */         } catch (IOException ie) {
/*  89 */           throw new XMLStreamException(ie);
/*     */         } finally {
/*  91 */           if (isError && writer != null) {
/*     */             try {
/*  93 */               writer.close();
/*  94 */             } catch (IOException iOException) {}
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 100 */       FileWriter writer = null;
/* 101 */       boolean isError = true;
/*     */ 
/*     */       
/*     */       try {
/* 105 */         writer = new FileWriter(new File(result.getSystemId()));
/* 106 */         XMLStreamWriter streamWriter = createXMLStreamWriter(writer);
/* 107 */         isError = false;
/*     */         
/* 109 */         return streamWriter;
/* 110 */       } catch (IOException ie) {
/* 111 */         throw new XMLStreamException(ie);
/*     */       } finally {
/* 113 */         if (isError && writer != null) {
/*     */           try {
/* 115 */             writer.close();
/* 116 */           } catch (IOException iOException) {}
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 121 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamWriter createXMLStreamWriter(Writer writer) throws XMLStreamException {
/* 128 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public XMLStreamWriter createXMLStreamWriter(OutputStream outputStream) throws XMLStreamException {
/* 132 */     return (XMLStreamWriter)new StAXDocumentSerializer(outputStream, new StAXManager(this._manager));
/*     */   }
/*     */   
/*     */   public XMLStreamWriter createXMLStreamWriter(OutputStream outputStream, String encoding) throws XMLStreamException {
/* 136 */     StAXDocumentSerializer serializer = new StAXDocumentSerializer(outputStream, new StAXManager(this._manager));
/* 137 */     serializer.setEncoding(encoding);
/* 138 */     return (XMLStreamWriter)serializer;
/*     */   }
/*     */   
/*     */   public Object getProperty(String name) throws IllegalArgumentException {
/* 142 */     if (name == null) {
/* 143 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.propertyNotSupported", new Object[] { null }));
/*     */     }
/* 145 */     if (this._manager.containsProperty(name))
/* 146 */       return this._manager.getProperty(name); 
/* 147 */     throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.propertyNotSupported", new Object[] { name }));
/*     */   }
/*     */   
/*     */   public boolean isPropertySupported(String name) {
/* 151 */     if (name == null) {
/* 152 */       return false;
/*     */     }
/* 154 */     return this._manager.containsProperty(name);
/*     */   }
/*     */   
/*     */   public void setProperty(String name, Object value) throws IllegalArgumentException {
/* 158 */     this._manager.setProperty(name, value);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/stax/factory/StAXOutputFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */