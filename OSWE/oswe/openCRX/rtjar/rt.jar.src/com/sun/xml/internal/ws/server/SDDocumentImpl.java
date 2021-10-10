/*     */ package com.sun.xml.internal.ws.server;
/*     */ 
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.server.DocumentAddressResolver;
/*     */ import com.sun.xml.internal.ws.api.server.PortAddressResolver;
/*     */ import com.sun.xml.internal.ws.api.server.SDDocument;
/*     */ import com.sun.xml.internal.ws.api.server.SDDocumentFilter;
/*     */ import com.sun.xml.internal.ws.api.server.SDDocumentSource;
/*     */ import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory;
/*     */ import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil;
/*     */ import com.sun.xml.internal.ws.util.RuntimeVersion;
/*     */ import com.sun.xml.internal.ws.util.xml.XMLStreamReaderToXMLStreamWriter;
/*     */ import com.sun.xml.internal.ws.wsdl.SDDocumentResolver;
/*     */ import com.sun.xml.internal.ws.wsdl.parser.ParserUtil;
/*     */ import com.sun.xml.internal.ws.wsdl.parser.WSDLConstants;
/*     */ import com.sun.xml.internal.ws.wsdl.writer.DocumentLocationResolver;
/*     */ import com.sun.xml.internal.ws.wsdl.writer.WSDLPatcher;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
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
/*     */ public class SDDocumentImpl
/*     */   extends SDDocumentSource
/*     */   implements SDDocument
/*     */ {
/*     */   private static final String NS_XSD = "http://www.w3.org/2001/XMLSchema";
/*  65 */   private static final QName SCHEMA_INCLUDE_QNAME = new QName("http://www.w3.org/2001/XMLSchema", "include");
/*  66 */   private static final QName SCHEMA_IMPORT_QNAME = new QName("http://www.w3.org/2001/XMLSchema", "import");
/*  67 */   private static final QName SCHEMA_REDEFINE_QNAME = new QName("http://www.w3.org/2001/XMLSchema", "redefine");
/*  68 */   private static final String VERSION_COMMENT = " Published by JAX-WS RI (http://jax-ws.java.net). RI's version is " + RuntimeVersion.VERSION + ". ";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final QName rootName;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final SDDocumentSource source;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   List<SDDocumentFilter> filters;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   SDDocumentResolver sddocResolver;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final URL url;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Set<String> imports;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SDDocumentImpl create(SDDocumentSource src, QName serviceName, QName portTypeName) {
/* 105 */     URL systemId = src.getSystemId();
/*     */ 
/*     */     
/*     */     try {
/* 109 */       XMLStreamReader reader = src.read();
/*     */       try {
/* 111 */         XMLStreamReaderUtil.nextElementContent(reader);
/*     */         
/* 113 */         QName rootName = reader.getName();
/* 114 */         if (rootName.equals(WSDLConstants.QNAME_SCHEMA)) {
/* 115 */           String tns = ParserUtil.getMandatoryNonEmptyAttribute(reader, "targetNamespace");
/* 116 */           Set<String> importedDocs = new HashSet<>();
/* 117 */           while (XMLStreamReaderUtil.nextContent(reader) != 8) {
/* 118 */             if (reader.getEventType() != 1)
/*     */               continue; 
/* 120 */             QName name = reader.getName();
/* 121 */             if (SCHEMA_INCLUDE_QNAME.equals(name) || SCHEMA_IMPORT_QNAME.equals(name) || SCHEMA_REDEFINE_QNAME
/* 122 */               .equals(name)) {
/* 123 */               String importedDoc = reader.getAttributeValue(null, "schemaLocation");
/* 124 */               if (importedDoc != null) {
/* 125 */                 importedDocs.add((new URL(src.getSystemId(), importedDoc)).toString());
/*     */               }
/*     */             } 
/*     */           } 
/* 129 */           return new SchemaImpl(rootName, systemId, src, tns, importedDocs);
/* 130 */         }  if (rootName.equals(WSDLConstants.QNAME_DEFINITIONS)) {
/* 131 */           String tns = ParserUtil.getMandatoryNonEmptyAttribute(reader, "targetNamespace");
/*     */           
/* 133 */           boolean hasPortType = false;
/* 134 */           boolean hasService = false;
/* 135 */           Set<String> importedDocs = new HashSet<>();
/* 136 */           Set<QName> allServices = new HashSet<>();
/*     */ 
/*     */           
/* 139 */           while (XMLStreamReaderUtil.nextContent(reader) != 8) {
/* 140 */             if (reader.getEventType() != 1) {
/*     */               continue;
/*     */             }
/* 143 */             QName name = reader.getName();
/* 144 */             if (WSDLConstants.QNAME_PORT_TYPE.equals(name)) {
/* 145 */               String pn = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/* 146 */               if (portTypeName != null && 
/* 147 */                 portTypeName.getLocalPart().equals(pn) && portTypeName.getNamespaceURI().equals(tns))
/* 148 */                 hasPortType = true; 
/*     */               continue;
/*     */             } 
/* 151 */             if (WSDLConstants.QNAME_SERVICE.equals(name)) {
/* 152 */               String sn = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/* 153 */               QName sqn = new QName(tns, sn);
/* 154 */               allServices.add(sqn);
/* 155 */               if (serviceName.equals(sqn))
/* 156 */                 hasService = true;  continue;
/*     */             } 
/* 158 */             if (WSDLConstants.QNAME_IMPORT.equals(name)) {
/* 159 */               String importedDoc = reader.getAttributeValue(null, "location");
/* 160 */               if (importedDoc != null)
/* 161 */                 importedDocs.add((new URL(src.getSystemId(), importedDoc)).toString());  continue;
/*     */             } 
/* 163 */             if (SCHEMA_INCLUDE_QNAME.equals(name) || SCHEMA_IMPORT_QNAME.equals(name) || SCHEMA_REDEFINE_QNAME
/* 164 */               .equals(name)) {
/* 165 */               String importedDoc = reader.getAttributeValue(null, "schemaLocation");
/* 166 */               if (importedDoc != null) {
/* 167 */                 importedDocs.add((new URL(src.getSystemId(), importedDoc)).toString());
/*     */               }
/*     */             } 
/*     */           } 
/* 171 */           return new WSDLImpl(rootName, systemId, src, tns, hasPortType, hasService, importedDocs, allServices);
/*     */         } 
/*     */         
/* 174 */         return new SDDocumentImpl(rootName, systemId, src);
/*     */       } finally {
/*     */         
/* 177 */         reader.close();
/*     */       } 
/* 179 */     } catch (WebServiceException e) {
/* 180 */       throw new ServerRtException("runtime.parser.wsdl", new Object[] { systemId, e });
/* 181 */     } catch (IOException e) {
/* 182 */       throw new ServerRtException("runtime.parser.wsdl", new Object[] { systemId, e });
/* 183 */     } catch (XMLStreamException e) {
/* 184 */       throw new ServerRtException("runtime.parser.wsdl", new Object[] { systemId, e });
/*     */     } 
/*     */   }
/*     */   
/*     */   protected SDDocumentImpl(QName rootName, URL url, SDDocumentSource source) {
/* 189 */     this(rootName, url, source, new HashSet<>());
/*     */   }
/*     */   
/*     */   protected SDDocumentImpl(QName rootName, URL url, SDDocumentSource source, Set<String> imports) {
/* 193 */     if (url == null) {
/* 194 */       throw new IllegalArgumentException("Cannot construct SDDocument with null URL.");
/*     */     }
/* 196 */     this.rootName = rootName;
/* 197 */     this.source = source;
/* 198 */     this.url = url;
/* 199 */     this.imports = imports;
/*     */   }
/*     */   
/*     */   void setFilters(List<SDDocumentFilter> filters) {
/* 203 */     this.filters = filters;
/*     */   }
/*     */   
/*     */   void setResolver(SDDocumentResolver sddocResolver) {
/* 207 */     this.sddocResolver = sddocResolver;
/*     */   }
/*     */   
/*     */   public QName getRootName() {
/* 211 */     return this.rootName;
/*     */   }
/*     */   
/*     */   public boolean isWSDL() {
/* 215 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isSchema() {
/* 219 */     return false;
/*     */   }
/*     */   
/*     */   public URL getURL() {
/* 223 */     return this.url;
/*     */   }
/*     */   
/*     */   public XMLStreamReader read(XMLInputFactory xif) throws IOException, XMLStreamException {
/* 227 */     return this.source.read(xif);
/*     */   }
/*     */   
/*     */   public XMLStreamReader read() throws IOException, XMLStreamException {
/* 231 */     return this.source.read();
/*     */   }
/*     */   
/*     */   public URL getSystemId() {
/* 235 */     return this.url;
/*     */   }
/*     */   
/*     */   public Set<String> getImports() {
/* 239 */     return this.imports;
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) throws IOException {
/* 243 */     XMLStreamWriter w = null;
/*     */     
/*     */     try {
/* 246 */       w = XMLStreamWriterFactory.create(os, "UTF-8");
/* 247 */       w.writeStartDocument("UTF-8", "1.0");
/* 248 */       (new XMLStreamReaderToXMLStreamWriter()).bridge(this.source.read(), w);
/* 249 */       w.writeEndDocument();
/* 250 */     } catch (XMLStreamException e) {
/* 251 */       IOException ioe = new IOException(e.getMessage());
/* 252 */       ioe.initCause(e);
/* 253 */       throw ioe;
/*     */     } finally {
/*     */       try {
/* 256 */         if (w != null)
/* 257 */           w.close(); 
/* 258 */       } catch (XMLStreamException e) {
/* 259 */         IOException ioe = new IOException(e.getMessage());
/* 260 */         ioe.initCause(e);
/* 261 */         throw ioe;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(PortAddressResolver portAddressResolver, DocumentAddressResolver resolver, OutputStream os) throws IOException {
/* 268 */     XMLStreamWriter w = null;
/*     */     
/*     */     try {
/* 271 */       w = XMLStreamWriterFactory.create(os, "UTF-8");
/* 272 */       w.writeStartDocument("UTF-8", "1.0");
/* 273 */       writeTo(portAddressResolver, resolver, w);
/* 274 */       w.writeEndDocument();
/* 275 */     } catch (XMLStreamException e) {
/* 276 */       IOException ioe = new IOException(e.getMessage());
/* 277 */       ioe.initCause(e);
/* 278 */       throw ioe;
/*     */     } finally {
/*     */       try {
/* 281 */         if (w != null)
/* 282 */           w.close(); 
/* 283 */       } catch (XMLStreamException e) {
/* 284 */         IOException ioe = new IOException(e.getMessage());
/* 285 */         ioe.initCause(e);
/* 286 */         throw ioe;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(PortAddressResolver portAddressResolver, DocumentAddressResolver resolver, XMLStreamWriter out) throws XMLStreamException, IOException {
/* 292 */     if (this.filters != null) {
/* 293 */       for (SDDocumentFilter f : this.filters) {
/* 294 */         out = f.filter(this, out);
/*     */       }
/*     */     }
/*     */     
/* 298 */     XMLStreamReader xsr = this.source.read();
/*     */     try {
/* 300 */       out.writeComment(VERSION_COMMENT);
/* 301 */       (new WSDLPatcher(portAddressResolver, new DocumentLocationResolverImpl(resolver))).bridge(xsr, out);
/*     */     } finally {
/* 303 */       xsr.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class SchemaImpl
/*     */     extends SDDocumentImpl
/*     */     implements SDDocument.Schema
/*     */   {
/*     */     private final String targetNamespace;
/*     */ 
/*     */ 
/*     */     
/*     */     public SchemaImpl(QName rootName, URL url, SDDocumentSource source, String targetNamespace, Set<String> imports) {
/* 318 */       super(rootName, url, source, imports);
/* 319 */       this.targetNamespace = targetNamespace;
/*     */     }
/*     */     
/*     */     public String getTargetNamespace() {
/* 323 */       return this.targetNamespace;
/*     */     }
/*     */     
/*     */     public boolean isSchema() {
/* 327 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class WSDLImpl
/*     */     extends SDDocumentImpl
/*     */     implements SDDocument.WSDL {
/*     */     private final String targetNamespace;
/*     */     private final boolean hasPortType;
/*     */     private final boolean hasService;
/*     */     private final Set<QName> allServices;
/*     */     
/*     */     public WSDLImpl(QName rootName, URL url, SDDocumentSource source, String targetNamespace, boolean hasPortType, boolean hasService, Set<String> imports, Set<QName> allServices) {
/* 340 */       super(rootName, url, source, imports);
/* 341 */       this.targetNamespace = targetNamespace;
/* 342 */       this.hasPortType = hasPortType;
/* 343 */       this.hasService = hasService;
/* 344 */       this.allServices = allServices;
/*     */     }
/*     */     
/*     */     public String getTargetNamespace() {
/* 348 */       return this.targetNamespace;
/*     */     }
/*     */     
/*     */     public boolean hasPortType() {
/* 352 */       return this.hasPortType;
/*     */     }
/*     */     
/*     */     public boolean hasService() {
/* 356 */       return this.hasService;
/*     */     }
/*     */     
/*     */     public Set<QName> getAllServices() {
/* 360 */       return this.allServices;
/*     */     }
/*     */     
/*     */     public boolean isWSDL() {
/* 364 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   private class DocumentLocationResolverImpl implements DocumentLocationResolver {
/*     */     private DocumentAddressResolver delegate;
/*     */     
/*     */     DocumentLocationResolverImpl(DocumentAddressResolver delegate) {
/* 372 */       this.delegate = delegate;
/*     */     }
/*     */     
/*     */     public String getLocationFor(String namespaceURI, String systemId) {
/* 376 */       if (SDDocumentImpl.this.sddocResolver == null) {
/* 377 */         return systemId;
/*     */       }
/*     */       try {
/* 380 */         URL ref = new URL(SDDocumentImpl.this.getURL(), systemId);
/* 381 */         SDDocument refDoc = SDDocumentImpl.this.sddocResolver.resolve(ref.toExternalForm());
/* 382 */         if (refDoc == null) {
/* 383 */           return systemId;
/*     */         }
/* 385 */         return this.delegate.getRelativeAddressFor(SDDocumentImpl.this, refDoc);
/* 386 */       } catch (MalformedURLException mue) {
/* 387 */         return null;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/SDDocumentImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */