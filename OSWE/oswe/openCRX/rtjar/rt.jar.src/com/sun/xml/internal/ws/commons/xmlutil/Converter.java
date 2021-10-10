/*     */ package com.sun.xml.internal.ws.commons.xmlutil;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.logging.Logger;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Messages;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.util.xml.XmlUtil;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.stream.XMLOutputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Converter
/*     */ {
/*     */   public static final String UTF_8 = "UTF-8";
/*  55 */   private static final Logger LOGGER = Logger.getLogger(Converter.class);
/*  56 */   private static final ContextClassloaderLocal<XMLOutputFactory> xmlOutputFactory = new ContextClassloaderLocal<XMLOutputFactory>()
/*     */     {
/*     */       protected XMLOutputFactory initialValue() throws Exception {
/*  59 */         return XMLOutputFactory.newInstance();
/*     */       }
/*     */     };
/*  62 */   private static final AtomicBoolean logMissingStaxUtilsWarning = new AtomicBoolean(false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toString(Throwable throwable) {
/*  71 */     if (throwable == null) {
/*  72 */       return "[ No exception ]";
/*     */     }
/*     */     
/*  75 */     StringWriter stringOut = new StringWriter();
/*  76 */     throwable.printStackTrace(new PrintWriter(stringOut));
/*     */     
/*  78 */     return stringOut.toString();
/*     */   }
/*     */   
/*     */   public static String toString(Packet packet) {
/*  82 */     if (packet == null)
/*  83 */       return "[ Null packet ]"; 
/*  84 */     if (packet.getMessage() == null) {
/*  85 */       return "[ Empty packet ]";
/*     */     }
/*     */     
/*  88 */     return toString(packet.getMessage());
/*     */   }
/*     */   
/*     */   public static String toStringNoIndent(Packet packet) {
/*  92 */     if (packet == null)
/*  93 */       return "[ Null packet ]"; 
/*  94 */     if (packet.getMessage() == null) {
/*  95 */       return "[ Empty packet ]";
/*     */     }
/*     */     
/*  98 */     return toStringNoIndent(packet.getMessage());
/*     */   }
/*     */   
/*     */   public static String toString(Message message) {
/* 102 */     return toString(message, true);
/*     */   }
/*     */   
/*     */   public static String toStringNoIndent(Message message) {
/* 106 */     return toString(message, false);
/*     */   }
/*     */   
/*     */   private static String toString(Message message, boolean createIndenter) {
/* 110 */     if (message == null) {
/* 111 */       return "[ Null message ]";
/*     */     }
/* 113 */     StringWriter stringOut = null;
/*     */     try {
/* 115 */       stringOut = new StringWriter();
/* 116 */       XMLStreamWriter writer = null;
/*     */       try {
/* 118 */         writer = ((XMLOutputFactory)xmlOutputFactory.get()).createXMLStreamWriter(stringOut);
/* 119 */         if (createIndenter) {
/* 120 */           writer = createIndenter(writer);
/*     */         }
/* 122 */         message.copy().writeTo(writer);
/* 123 */       } catch (Exception e) {
/* 124 */         LOGGER.log(Level.WARNING, "Unexpected exception occured while dumping message", e);
/*     */       } finally {
/* 126 */         if (writer != null) {
/*     */           try {
/* 128 */             writer.close();
/* 129 */           } catch (XMLStreamException ignored) {
/* 130 */             LOGGER.fine("Unexpected exception occured while closing XMLStreamWriter", ignored);
/*     */           } 
/*     */         }
/*     */       } 
/* 134 */       return stringOut.toString();
/*     */     } finally {
/* 136 */       if (stringOut != null) {
/*     */         try {
/* 138 */           stringOut.close();
/* 139 */         } catch (IOException ex) {
/* 140 */           LOGGER.finest("An exception occured when trying to close StringWriter", ex);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static byte[] toBytes(Message message, String encoding) throws XMLStreamException {
/* 147 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*     */     
/*     */     try {
/* 150 */       if (message != null) {
/* 151 */         XMLStreamWriter xsw = ((XMLOutputFactory)xmlOutputFactory.get()).createXMLStreamWriter(baos, encoding);
/*     */         try {
/* 153 */           message.writeTo(xsw);
/*     */         } finally {
/*     */           try {
/* 156 */             xsw.close();
/* 157 */           } catch (XMLStreamException ex) {
/* 158 */             LOGGER.warning("Unexpected exception occured while closing XMLStreamWriter", ex);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 163 */       return baos.toByteArray();
/*     */     } finally {
/*     */       try {
/* 166 */         baos.close();
/* 167 */       } catch (IOException ex) {
/* 168 */         LOGGER.warning("Unexpected exception occured while closing ByteArrayOutputStream", ex);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message toMessage(@NotNull InputStream dataStream, String encoding) throws XMLStreamException {
/* 183 */     XMLStreamReader xsr = XmlUtil.newXMLInputFactory(true).createXMLStreamReader(dataStream, encoding);
/* 184 */     return Messages.create(xsr);
/*     */   }
/*     */   
/*     */   public static String messageDataToString(byte[] data, String encoding) {
/*     */     try {
/* 189 */       return toString(toMessage(new ByteArrayInputStream(data), encoding));
/*     */     }
/* 191 */     catch (XMLStreamException ex) {
/* 192 */       LOGGER.warning("Unexpected exception occured while converting message data to string", ex);
/* 193 */       return "[ Message Data Conversion Failed ]";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static XMLStreamWriter createIndenter(XMLStreamWriter writer) {
/*     */     try {
/* 205 */       Class<?> clazz = Converter.class.getClassLoader().loadClass("javanet.staxutils.IndentingXMLStreamWriter");
/* 206 */       Constructor<?> c = clazz.getConstructor(new Class[] { XMLStreamWriter.class });
/* 207 */       writer = XMLStreamWriter.class.cast(c.newInstance(new Object[] { writer }));
/* 208 */     } catch (Exception ex) {
/*     */ 
/*     */       
/* 211 */       if (logMissingStaxUtilsWarning.compareAndSet(false, true)) {
/* 212 */         LOGGER.log(Level.WARNING, "Put stax-utils.jar to the classpath to indent the dump output", ex);
/*     */       }
/*     */     } 
/* 215 */     return writer;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/commons/xmlutil/Converter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */