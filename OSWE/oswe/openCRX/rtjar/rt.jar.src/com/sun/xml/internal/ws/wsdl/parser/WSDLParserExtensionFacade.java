/*     */ package com.sun.xml.internal.ws.wsdl.parser;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundFault;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundPortType;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLFault;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLInput;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLMessage;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLOutput;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLPort;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLPortType;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLService;
/*     */ import com.sun.xml.internal.ws.api.wsdl.parser.WSDLParserExtension;
/*     */ import com.sun.xml.internal.ws.api.wsdl.parser.WSDLParserExtensionContext;
/*     */ import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.helpers.LocatorImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class WSDLParserExtensionFacade
/*     */   extends WSDLParserExtension
/*     */ {
/*     */   private final WSDLParserExtension[] extensions;
/*     */   
/*     */   WSDLParserExtensionFacade(WSDLParserExtension... extensions) {
/*  57 */     assert extensions != null;
/*  58 */     this.extensions = extensions;
/*     */   }
/*     */   
/*     */   public void start(WSDLParserExtensionContext context) {
/*  62 */     for (WSDLParserExtension e : this.extensions) {
/*  63 */       e.start(context);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean serviceElements(EditableWSDLService service, XMLStreamReader reader) {
/*  68 */     for (WSDLParserExtension e : this.extensions) {
/*  69 */       if (e.serviceElements(service, reader))
/*  70 */         return true; 
/*     */     } 
/*  72 */     XMLStreamReaderUtil.skipElement(reader);
/*  73 */     return true;
/*     */   }
/*     */   
/*     */   public void serviceAttributes(EditableWSDLService service, XMLStreamReader reader) {
/*  77 */     for (WSDLParserExtension e : this.extensions)
/*  78 */       e.serviceAttributes(service, reader); 
/*     */   }
/*     */   
/*     */   public boolean portElements(EditableWSDLPort port, XMLStreamReader reader) {
/*  82 */     for (WSDLParserExtension e : this.extensions) {
/*  83 */       if (e.portElements(port, reader)) {
/*  84 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  88 */     if (isRequiredExtension(reader)) {
/*  89 */       port.addNotUnderstoodExtension(reader.getName(), getLocator(reader));
/*     */     }
/*  91 */     XMLStreamReaderUtil.skipElement(reader);
/*  92 */     return true;
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationInput(EditableWSDLOperation op, XMLStreamReader reader) {
/*  96 */     for (WSDLParserExtension e : this.extensions) {
/*  97 */       e.portTypeOperationInput(op, reader);
/*     */     }
/*  99 */     return false;
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationOutput(EditableWSDLOperation op, XMLStreamReader reader) {
/* 103 */     for (WSDLParserExtension e : this.extensions) {
/* 104 */       e.portTypeOperationOutput(op, reader);
/*     */     }
/* 106 */     return false;
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationFault(EditableWSDLOperation op, XMLStreamReader reader) {
/* 110 */     for (WSDLParserExtension e : this.extensions) {
/* 111 */       e.portTypeOperationFault(op, reader);
/*     */     }
/* 113 */     return false;
/*     */   }
/*     */   
/*     */   public void portAttributes(EditableWSDLPort port, XMLStreamReader reader) {
/* 117 */     for (WSDLParserExtension e : this.extensions)
/* 118 */       e.portAttributes(port, reader); 
/*     */   }
/*     */   
/*     */   public boolean definitionsElements(XMLStreamReader reader) {
/* 122 */     for (WSDLParserExtension e : this.extensions) {
/* 123 */       if (e.definitionsElements(reader)) {
/* 124 */         return true;
/*     */       }
/*     */     } 
/* 127 */     XMLStreamReaderUtil.skipElement(reader);
/* 128 */     return true;
/*     */   }
/*     */   
/*     */   public boolean bindingElements(EditableWSDLBoundPortType binding, XMLStreamReader reader) {
/* 132 */     for (WSDLParserExtension e : this.extensions) {
/* 133 */       if (e.bindingElements(binding, reader)) {
/* 134 */         return true;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 139 */     if (isRequiredExtension(reader)) {
/* 140 */       binding.addNotUnderstoodExtension(reader
/* 141 */           .getName(), getLocator(reader));
/*     */     }
/* 143 */     XMLStreamReaderUtil.skipElement(reader);
/* 144 */     return true;
/*     */   }
/*     */   
/*     */   public void bindingAttributes(EditableWSDLBoundPortType binding, XMLStreamReader reader) {
/* 148 */     for (WSDLParserExtension e : this.extensions) {
/* 149 */       e.bindingAttributes(binding, reader);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean portTypeElements(EditableWSDLPortType portType, XMLStreamReader reader) {
/* 154 */     for (WSDLParserExtension e : this.extensions) {
/* 155 */       if (e.portTypeElements(portType, reader)) {
/* 156 */         return true;
/*     */       }
/*     */     } 
/* 159 */     XMLStreamReaderUtil.skipElement(reader);
/* 160 */     return true;
/*     */   }
/*     */   
/*     */   public void portTypeAttributes(EditableWSDLPortType portType, XMLStreamReader reader) {
/* 164 */     for (WSDLParserExtension e : this.extensions) {
/* 165 */       e.portTypeAttributes(portType, reader);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationElements(EditableWSDLOperation operation, XMLStreamReader reader) {
/* 170 */     for (WSDLParserExtension e : this.extensions) {
/* 171 */       if (e.portTypeOperationElements(operation, reader)) {
/* 172 */         return true;
/*     */       }
/*     */     } 
/* 175 */     XMLStreamReaderUtil.skipElement(reader);
/* 176 */     return true;
/*     */   }
/*     */   
/*     */   public void portTypeOperationAttributes(EditableWSDLOperation operation, XMLStreamReader reader) {
/* 180 */     for (WSDLParserExtension e : this.extensions) {
/* 181 */       e.portTypeOperationAttributes(operation, reader);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean bindingOperationElements(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
/* 186 */     for (WSDLParserExtension e : this.extensions) {
/* 187 */       if (e.bindingOperationElements(operation, reader)) {
/* 188 */         return true;
/*     */       }
/*     */     } 
/* 191 */     XMLStreamReaderUtil.skipElement(reader);
/* 192 */     return true;
/*     */   }
/*     */   
/*     */   public void bindingOperationAttributes(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
/* 196 */     for (WSDLParserExtension e : this.extensions) {
/* 197 */       e.bindingOperationAttributes(operation, reader);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean messageElements(EditableWSDLMessage msg, XMLStreamReader reader) {
/* 202 */     for (WSDLParserExtension e : this.extensions) {
/* 203 */       if (e.messageElements(msg, reader)) {
/* 204 */         return true;
/*     */       }
/*     */     } 
/* 207 */     XMLStreamReaderUtil.skipElement(reader);
/* 208 */     return true;
/*     */   }
/*     */   
/*     */   public void messageAttributes(EditableWSDLMessage msg, XMLStreamReader reader) {
/* 212 */     for (WSDLParserExtension e : this.extensions) {
/* 213 */       e.messageAttributes(msg, reader);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationInputElements(EditableWSDLInput input, XMLStreamReader reader) {
/* 218 */     for (WSDLParserExtension e : this.extensions) {
/* 219 */       if (e.portTypeOperationInputElements(input, reader)) {
/* 220 */         return true;
/*     */       }
/*     */     } 
/* 223 */     XMLStreamReaderUtil.skipElement(reader);
/* 224 */     return true;
/*     */   }
/*     */   
/*     */   public void portTypeOperationInputAttributes(EditableWSDLInput input, XMLStreamReader reader) {
/* 228 */     for (WSDLParserExtension e : this.extensions) {
/* 229 */       e.portTypeOperationInputAttributes(input, reader);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationOutputElements(EditableWSDLOutput output, XMLStreamReader reader) {
/* 234 */     for (WSDLParserExtension e : this.extensions) {
/* 235 */       if (e.portTypeOperationOutputElements(output, reader)) {
/* 236 */         return true;
/*     */       }
/*     */     } 
/* 239 */     XMLStreamReaderUtil.skipElement(reader);
/* 240 */     return true;
/*     */   }
/*     */   
/*     */   public void portTypeOperationOutputAttributes(EditableWSDLOutput output, XMLStreamReader reader) {
/* 244 */     for (WSDLParserExtension e : this.extensions) {
/* 245 */       e.portTypeOperationOutputAttributes(output, reader);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean portTypeOperationFaultElements(EditableWSDLFault fault, XMLStreamReader reader) {
/* 250 */     for (WSDLParserExtension e : this.extensions) {
/* 251 */       if (e.portTypeOperationFaultElements(fault, reader)) {
/* 252 */         return true;
/*     */       }
/*     */     } 
/* 255 */     XMLStreamReaderUtil.skipElement(reader);
/* 256 */     return true;
/*     */   }
/*     */   
/*     */   public void portTypeOperationFaultAttributes(EditableWSDLFault fault, XMLStreamReader reader) {
/* 260 */     for (WSDLParserExtension e : this.extensions) {
/* 261 */       e.portTypeOperationFaultAttributes(fault, reader);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean bindingOperationInputElements(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
/* 266 */     for (WSDLParserExtension e : this.extensions) {
/* 267 */       if (e.bindingOperationInputElements(operation, reader)) {
/* 268 */         return true;
/*     */       }
/*     */     } 
/* 271 */     XMLStreamReaderUtil.skipElement(reader);
/* 272 */     return true;
/*     */   }
/*     */   
/*     */   public void bindingOperationInputAttributes(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
/* 276 */     for (WSDLParserExtension e : this.extensions) {
/* 277 */       e.bindingOperationInputAttributes(operation, reader);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean bindingOperationOutputElements(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
/* 282 */     for (WSDLParserExtension e : this.extensions) {
/* 283 */       if (e.bindingOperationOutputElements(operation, reader)) {
/* 284 */         return true;
/*     */       }
/*     */     } 
/* 287 */     XMLStreamReaderUtil.skipElement(reader);
/* 288 */     return true;
/*     */   }
/*     */   
/*     */   public void bindingOperationOutputAttributes(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
/* 292 */     for (WSDLParserExtension e : this.extensions) {
/* 293 */       e.bindingOperationOutputAttributes(operation, reader);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean bindingOperationFaultElements(EditableWSDLBoundFault fault, XMLStreamReader reader) {
/* 298 */     for (WSDLParserExtension e : this.extensions) {
/* 299 */       if (e.bindingOperationFaultElements(fault, reader)) {
/* 300 */         return true;
/*     */       }
/*     */     } 
/* 303 */     XMLStreamReaderUtil.skipElement(reader);
/* 304 */     return true;
/*     */   }
/*     */   
/*     */   public void bindingOperationFaultAttributes(EditableWSDLBoundFault fault, XMLStreamReader reader) {
/* 308 */     for (WSDLParserExtension e : this.extensions) {
/* 309 */       e.bindingOperationFaultAttributes(fault, reader);
/*     */     }
/*     */   }
/*     */   
/*     */   public void finished(WSDLParserExtensionContext context) {
/* 314 */     for (WSDLParserExtension e : this.extensions) {
/* 315 */       e.finished(context);
/*     */     }
/*     */   }
/*     */   
/*     */   public void postFinished(WSDLParserExtensionContext context) {
/* 320 */     for (WSDLParserExtension e : this.extensions) {
/* 321 */       e.postFinished(context);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isRequiredExtension(XMLStreamReader reader) {
/* 331 */     String required = reader.getAttributeValue("http://schemas.xmlsoap.org/wsdl/", "required");
/* 332 */     if (required != null)
/* 333 */       return Boolean.parseBoolean(required); 
/* 334 */     return false;
/*     */   }
/*     */   
/*     */   private Locator getLocator(XMLStreamReader reader) {
/* 338 */     Location location = reader.getLocation();
/* 339 */     LocatorImpl loc = new LocatorImpl();
/* 340 */     loc.setSystemId(location.getSystemId());
/* 341 */     loc.setLineNumber(location.getLineNumber());
/* 342 */     return loc;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/wsdl/parser/WSDLParserExtensionFacade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */