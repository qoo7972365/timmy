/*     */ package com.sun.xml.internal.ws.wsdl.writer;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.addressing.W3CAddressingConstants;
/*     */ import com.sun.xml.internal.ws.addressing.v200408.MemberSubmissionAddressingConstants;
/*     */ import com.sun.xml.internal.ws.api.server.PortAddressResolver;
/*     */ import com.sun.xml.internal.ws.util.xml.XMLStreamReaderToXMLStreamWriter;
/*     */ import com.sun.xml.internal.ws.wsdl.parser.WSDLConstants;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WSDLPatcher
/*     */   extends XMLStreamReaderToXMLStreamWriter
/*     */ {
/*     */   private static final String NS_XSD = "http://www.w3.org/2001/XMLSchema";
/*  50 */   private static final QName SCHEMA_INCLUDE_QNAME = new QName("http://www.w3.org/2001/XMLSchema", "include");
/*  51 */   private static final QName SCHEMA_IMPORT_QNAME = new QName("http://www.w3.org/2001/XMLSchema", "import");
/*  52 */   private static final QName SCHEMA_REDEFINE_QNAME = new QName("http://www.w3.org/2001/XMLSchema", "redefine");
/*     */   
/*  54 */   private static final Logger logger = Logger.getLogger("com.sun.xml.internal.ws.wsdl.patcher");
/*     */ 
/*     */ 
/*     */   
/*     */   private final DocumentLocationResolver docResolver;
/*     */ 
/*     */   
/*     */   private final PortAddressResolver portAddressResolver;
/*     */ 
/*     */   
/*     */   private String targetNamespace;
/*     */ 
/*     */   
/*     */   private QName serviceName;
/*     */ 
/*     */   
/*     */   private QName portName;
/*     */ 
/*     */   
/*     */   private String portAddress;
/*     */ 
/*     */   
/*     */   private boolean inEpr;
/*     */ 
/*     */   
/*     */   private boolean inEprAddress;
/*     */ 
/*     */ 
/*     */   
/*     */   public WSDLPatcher(@NotNull PortAddressResolver portAddressResolver, @NotNull DocumentLocationResolver docResolver) {
/*  84 */     this.portAddressResolver = portAddressResolver;
/*  85 */     this.docResolver = docResolver;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleAttribute(int i) throws XMLStreamException {
/*  90 */     QName name = this.in.getName();
/*  91 */     String attLocalName = this.in.getAttributeLocalName(i);
/*     */     
/*  93 */     if ((name.equals(SCHEMA_INCLUDE_QNAME) && attLocalName.equals("schemaLocation")) || (name
/*  94 */       .equals(SCHEMA_IMPORT_QNAME) && attLocalName.equals("schemaLocation")) || (name
/*  95 */       .equals(SCHEMA_REDEFINE_QNAME) && attLocalName.equals("schemaLocation")) || (name
/*  96 */       .equals(WSDLConstants.QNAME_IMPORT) && attLocalName.equals("location"))) {
/*     */ 
/*     */       
/*  99 */       String relPath = this.in.getAttributeValue(i);
/* 100 */       String actualPath = getPatchedImportLocation(relPath);
/* 101 */       if (actualPath == null) {
/*     */         return;
/*     */       }
/*     */       
/* 105 */       logger.fine("Fixing the relative location:" + relPath + " with absolute location:" + actualPath);
/*     */       
/* 107 */       writeAttribute(i, actualPath);
/*     */       
/*     */       return;
/*     */     } 
/* 111 */     if (name.equals(WSDLConstants.NS_SOAP_BINDING_ADDRESS) || name
/* 112 */       .equals(WSDLConstants.NS_SOAP12_BINDING_ADDRESS))
/*     */     {
/* 114 */       if (attLocalName.equals("location")) {
/* 115 */         this.portAddress = this.in.getAttributeValue(i);
/* 116 */         String value = getAddressLocation();
/* 117 */         if (value != null) {
/* 118 */           logger.fine("Service:" + this.serviceName + " port:" + this.portName + " current address " + this.portAddress + " Patching it with " + value);
/*     */           
/* 120 */           writeAttribute(i, value);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     }
/* 126 */     super.handleAttribute(i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeAttribute(int i, String value) throws XMLStreamException {
/* 136 */     String nsUri = this.in.getAttributeNamespace(i);
/* 137 */     if (nsUri != null) {
/* 138 */       this.out.writeAttribute(this.in.getAttributePrefix(i), nsUri, this.in.getAttributeLocalName(i), value);
/*     */     } else {
/* 140 */       this.out.writeAttribute(this.in.getAttributeLocalName(i), value);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void handleStartElement() throws XMLStreamException {
/* 145 */     QName name = this.in.getName();
/*     */     
/* 147 */     if (name.equals(WSDLConstants.QNAME_DEFINITIONS)) {
/* 148 */       String value = this.in.getAttributeValue(null, "targetNamespace");
/* 149 */       if (value != null) {
/* 150 */         this.targetNamespace = value;
/*     */       }
/* 152 */     } else if (name.equals(WSDLConstants.QNAME_SERVICE)) {
/* 153 */       String value = this.in.getAttributeValue(null, "name");
/* 154 */       if (value != null) {
/* 155 */         this.serviceName = new QName(this.targetNamespace, value);
/*     */       }
/* 157 */     } else if (name.equals(WSDLConstants.QNAME_PORT)) {
/* 158 */       String value = this.in.getAttributeValue(null, "name");
/* 159 */       if (value != null) {
/* 160 */         this.portName = new QName(this.targetNamespace, value);
/*     */       }
/* 162 */     } else if (name.equals(W3CAddressingConstants.WSA_EPR_QNAME) || name
/* 163 */       .equals(MemberSubmissionAddressingConstants.WSA_EPR_QNAME)) {
/* 164 */       if (this.serviceName != null && this.portName != null) {
/* 165 */         this.inEpr = true;
/*     */       }
/* 167 */     } else if ((name.equals(W3CAddressingConstants.WSA_ADDRESS_QNAME) || name
/* 168 */       .equals(MemberSubmissionAddressingConstants.WSA_ADDRESS_QNAME)) && 
/* 169 */       this.inEpr) {
/* 170 */       this.inEprAddress = true;
/*     */     } 
/*     */     
/* 173 */     super.handleStartElement();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleEndElement() throws XMLStreamException {
/* 178 */     QName name = this.in.getName();
/* 179 */     if (name.equals(WSDLConstants.QNAME_SERVICE)) {
/* 180 */       this.serviceName = null;
/* 181 */     } else if (name.equals(WSDLConstants.QNAME_PORT)) {
/* 182 */       this.portName = null;
/* 183 */     } else if (name.equals(W3CAddressingConstants.WSA_EPR_QNAME) || name
/* 184 */       .equals(MemberSubmissionAddressingConstants.WSA_EPR_QNAME)) {
/* 185 */       if (this.inEpr) {
/* 186 */         this.inEpr = false;
/*     */       }
/* 188 */     } else if ((name.equals(W3CAddressingConstants.WSA_ADDRESS_QNAME) || name
/* 189 */       .equals(MemberSubmissionAddressingConstants.WSA_ADDRESS_QNAME)) && 
/* 190 */       this.inEprAddress) {
/* 191 */       String value = getAddressLocation();
/* 192 */       if (value != null) {
/* 193 */         logger.fine("Fixing EPR Address for service:" + this.serviceName + " port:" + this.portName + " address with " + value);
/*     */         
/* 195 */         this.out.writeCharacters(value);
/*     */       } 
/* 197 */       this.inEprAddress = false;
/*     */     } 
/*     */     
/* 200 */     super.handleEndElement();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleCharacters() throws XMLStreamException {
/* 206 */     if (this.inEprAddress) {
/* 207 */       String value = getAddressLocation();
/* 208 */       if (value != null) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 213 */     super.handleCharacters();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private String getPatchedImportLocation(String relPath) {
/* 224 */     return this.docResolver.getLocationFor(null, relPath);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getAddressLocation() {
/* 234 */     return (this.portAddressResolver == null || this.portName == null) ? null : this.portAddressResolver
/* 235 */       .getAddressFor(this.serviceName, this.portName.getLocalPart(), this.portAddress);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/wsdl/writer/WSDLPatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */