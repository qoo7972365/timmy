/*     */ package com.sun.xml.internal.ws.wsdl.parser;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLFeaturedObject;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundPortType;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLFault;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLInput;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLOutput;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLPort;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLPortType;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLService;
/*     */ import com.sun.xml.internal.ws.api.wsdl.parser.WSDLParserExtension;
/*     */ import com.sun.xml.internal.ws.api.wsdl.parser.WSDLParserExtensionContext;
/*     */ import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.soap.AddressingFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class W3CAddressingWSDLParserExtension
/*     */   extends WSDLParserExtension
/*     */ {
/*     */   protected static final String COLON_DELIMITER = ":";
/*     */   protected static final String SLASH_DELIMITER = "/";
/*     */   
/*     */   public boolean bindingElements(EditableWSDLBoundPortType binding, XMLStreamReader reader) {
/*  50 */     return addressibleElement(reader, (WSDLFeaturedObject)binding);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean portElements(EditableWSDLPort port, XMLStreamReader reader) {
/*  55 */     return addressibleElement(reader, (WSDLFeaturedObject)port);
/*     */   }
/*     */   
/*     */   private boolean addressibleElement(XMLStreamReader reader, WSDLFeaturedObject binding) {
/*  59 */     QName ua = reader.getName();
/*  60 */     if (ua.equals(AddressingVersion.W3C.wsdlExtensionTag)) {
/*  61 */       String required = reader.getAttributeValue("http://schemas.xmlsoap.org/wsdl/", "required");
/*  62 */       binding.addFeature((WebServiceFeature)new AddressingFeature(true, Boolean.parseBoolean(required)));
/*  63 */       XMLStreamReaderUtil.skipElement(reader);
/*  64 */       return true;
/*     */     } 
/*     */     
/*  67 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean bindingOperationElements(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
/*  72 */     EditableWSDLBoundOperation edit = operation;
/*     */     
/*  74 */     QName anon = reader.getName();
/*  75 */     if (anon.equals(AddressingVersion.W3C.wsdlAnonymousTag)) {
/*     */       try {
/*  77 */         String value = reader.getElementText();
/*  78 */         if (value == null || value.trim().equals("")) {
/*  79 */           throw new WebServiceException("Null values not permitted in wsaw:Anonymous.");
/*     */         }
/*     */         
/*  82 */         if (value.equals("optional")) {
/*  83 */           edit.setAnonymous(WSDLBoundOperation.ANONYMOUS.optional);
/*  84 */         } else if (value.equals("required")) {
/*  85 */           edit.setAnonymous(WSDLBoundOperation.ANONYMOUS.required);
/*  86 */         } else if (value.equals("prohibited")) {
/*  87 */           edit.setAnonymous(WSDLBoundOperation.ANONYMOUS.prohibited);
/*     */         } else {
/*  89 */           throw new WebServiceException("wsaw:Anonymous value \"" + value + "\" not understood.");
/*     */         }
/*     */       
/*     */       }
/*  93 */       catch (XMLStreamException e) {
/*  94 */         throw new WebServiceException(e);
/*     */       } 
/*     */       
/*  97 */       return true;
/*     */     } 
/*     */     
/* 100 */     return false;
/*     */   }
/*     */   
/*     */   public void portTypeOperationInputAttributes(EditableWSDLInput input, XMLStreamReader reader) {
/* 104 */     String action = ParserUtil.getAttribute(reader, getWsdlActionTag());
/* 105 */     if (action != null) {
/* 106 */       input.setAction(action);
/* 107 */       input.setDefaultAction(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void portTypeOperationOutputAttributes(EditableWSDLOutput output, XMLStreamReader reader) {
/* 113 */     String action = ParserUtil.getAttribute(reader, getWsdlActionTag());
/* 114 */     if (action != null) {
/* 115 */       output.setAction(action);
/* 116 */       output.setDefaultAction(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void portTypeOperationFaultAttributes(EditableWSDLFault fault, XMLStreamReader reader) {
/* 122 */     String action = ParserUtil.getAttribute(reader, getWsdlActionTag());
/* 123 */     if (action != null) {
/* 124 */       fault.setAction(action);
/* 125 */       fault.setDefaultAction(false);
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
/*     */   public void finished(WSDLParserExtensionContext context) {
/* 141 */     EditableWSDLModel model = context.getWSDLModel();
/* 142 */     for (EditableWSDLService service : model.getServices().values()) {
/* 143 */       for (EditableWSDLPort port : service.getPorts()) {
/* 144 */         EditableWSDLBoundPortType binding = port.getBinding();
/*     */ 
/*     */         
/* 147 */         populateActions(binding);
/*     */ 
/*     */         
/* 150 */         patchAnonymousDefault(binding);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String getNamespaceURI() {
/* 156 */     return AddressingVersion.W3C.wsdlNsUri;
/*     */   }
/*     */   
/*     */   protected QName getWsdlActionTag() {
/* 160 */     return AddressingVersion.W3C.wsdlActionTag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void populateActions(EditableWSDLBoundPortType binding) {
/* 168 */     EditableWSDLPortType porttype = binding.getPortType();
/* 169 */     for (EditableWSDLOperation o : porttype.getOperations()) {
/*     */ 
/*     */       
/* 172 */       EditableWSDLBoundOperation wboi = binding.get(o.getName());
/*     */       
/* 174 */       if (wboi == null) {
/*     */         
/* 176 */         o.getInput().setAction(defaultInputAction(o));
/*     */         continue;
/*     */       } 
/* 179 */       String soapAction = wboi.getSOAPAction();
/* 180 */       if (o.getInput().getAction() == null || o.getInput().getAction().equals(""))
/*     */       {
/*     */         
/* 183 */         if (soapAction != null && !soapAction.equals("")) {
/*     */           
/* 185 */           o.getInput().setAction(soapAction);
/*     */         } else {
/*     */           
/* 188 */           o.getInput().setAction(defaultInputAction(o));
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 193 */       if (o.getOutput() == null) {
/*     */         continue;
/*     */       }
/* 196 */       if (o.getOutput().getAction() == null || o.getOutput().getAction().equals("")) {
/* 197 */         o.getOutput().setAction(defaultOutputAction(o));
/*     */       }
/*     */       
/* 200 */       if (o.getFaults() == null || !o.getFaults().iterator().hasNext()) {
/*     */         continue;
/*     */       }
/* 203 */       for (EditableWSDLFault f : o.getFaults()) {
/* 204 */         if (f.getAction() == null || f.getAction().equals("")) {
/* 205 */           f.setAction(defaultFaultAction(f.getName(), o));
/*     */         }
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
/*     */   protected void patchAnonymousDefault(EditableWSDLBoundPortType binding) {
/* 218 */     for (EditableWSDLBoundOperation wbo : binding.getBindingOperations()) {
/* 219 */       if (wbo.getAnonymous() == null)
/* 220 */         wbo.setAnonymous(WSDLBoundOperation.ANONYMOUS.optional); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private String defaultInputAction(EditableWSDLOperation o) {
/* 225 */     return buildAction(o.getInput().getName(), o, false);
/*     */   }
/*     */   
/*     */   private String defaultOutputAction(EditableWSDLOperation o) {
/* 229 */     return buildAction(o.getOutput().getName(), o, false);
/*     */   }
/*     */   
/*     */   private String defaultFaultAction(String name, EditableWSDLOperation o) {
/* 233 */     return buildAction(name, o, true);
/*     */   }
/*     */   
/*     */   protected static final String buildAction(String name, EditableWSDLOperation o, boolean isFault) {
/* 237 */     String tns = o.getName().getNamespaceURI();
/*     */     
/* 239 */     String delim = "/";
/*     */ 
/*     */     
/* 242 */     if (!tns.startsWith("http")) {
/* 243 */       delim = ":";
/*     */     }
/* 245 */     if (tns.endsWith(delim)) {
/* 246 */       tns = tns.substring(0, tns.length() - 1);
/*     */     }
/* 248 */     if (o.getPortTypeName() == null) {
/* 249 */       throw new WebServiceException("\"" + o.getName() + "\" operation's owning portType name is null.");
/*     */     }
/* 251 */     return tns + delim + o
/*     */       
/* 253 */       .getPortTypeName().getLocalPart() + delim + (isFault ? (o
/*     */       
/* 255 */       .getName().getLocalPart() + delim + "Fault" + delim) : "") + name;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/wsdl/parser/W3CAddressingWSDLParserExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */