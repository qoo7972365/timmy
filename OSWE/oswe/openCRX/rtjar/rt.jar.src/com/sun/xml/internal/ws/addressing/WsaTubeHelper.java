/*     */ package com.sun.xml.internal.ws.addressing;
/*     */ 
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.addressing.model.InvalidAddressingHeaderException;
/*     */ import com.sun.xml.internal.ws.addressing.model.MissingAddressingHeaderException;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.internal.ws.api.message.AddressingUtils;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.JavaMethod;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*     */ import com.sun.xml.internal.ws.api.model.WSDLOperationMapping;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLFault;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLOperation;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.model.CheckedExceptionImpl;
/*     */ import com.sun.xml.internal.ws.model.JavaMethodImpl;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Detail;
/*     */ import javax.xml.soap.SOAPConstants;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFactory;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WsaTubeHelper
/*     */ {
/*     */   protected SEIModel seiModel;
/*     */   protected WSDLPort wsdlPort;
/*     */   protected WSBinding binding;
/*     */   protected final SOAPVersion soapVer;
/*     */   protected final AddressingVersion addVer;
/*     */   
/*     */   public WsaTubeHelper(WSBinding binding, SEIModel seiModel, WSDLPort wsdlPort) {
/*  64 */     this.binding = binding;
/*  65 */     this.wsdlPort = wsdlPort;
/*  66 */     this.seiModel = seiModel;
/*  67 */     this.soapVer = binding.getSOAPVersion();
/*  68 */     this.addVer = binding.getAddressingVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFaultAction(Packet requestPacket, Packet responsePacket) {
/*  73 */     String action = null;
/*  74 */     if (this.seiModel != null) {
/*  75 */       action = getFaultActionFromSEIModel(requestPacket, responsePacket);
/*     */     }
/*  77 */     if (action != null) {
/*  78 */       return action;
/*     */     }
/*  80 */     action = this.addVer.getDefaultFaultAction();
/*     */     
/*  82 */     if (this.wsdlPort != null) {
/*  83 */       WSDLOperationMapping wsdlOp = requestPacket.getWSDLOperationMapping();
/*  84 */       if (wsdlOp != null) {
/*  85 */         WSDLBoundOperation wbo = wsdlOp.getWSDLBoundOperation();
/*  86 */         return getFaultAction(wbo, responsePacket);
/*     */       } 
/*     */     } 
/*  89 */     return action;
/*     */   }
/*     */   
/*     */   String getFaultActionFromSEIModel(Packet requestPacket, Packet responsePacket) {
/*  93 */     String action = null;
/*  94 */     if (this.seiModel == null || this.wsdlPort == null) {
/*  95 */       return action;
/*     */     }
/*     */     
/*     */     try {
/*  99 */       SOAPMessage sm = responsePacket.getMessage().copy().readAsSOAPMessage();
/* 100 */       if (sm == null) {
/* 101 */         return action;
/*     */       }
/*     */       
/* 104 */       if (sm.getSOAPBody() == null) {
/* 105 */         return action;
/*     */       }
/*     */       
/* 108 */       if (sm.getSOAPBody().getFault() == null) {
/* 109 */         return action;
/*     */       }
/*     */       
/* 112 */       Detail detail = sm.getSOAPBody().getFault().getDetail();
/* 113 */       if (detail == null) {
/* 114 */         return action;
/*     */       }
/*     */       
/* 117 */       String ns = detail.getFirstChild().getNamespaceURI();
/* 118 */       String name = detail.getFirstChild().getLocalName();
/*     */       
/* 120 */       WSDLOperationMapping wsdlOp = requestPacket.getWSDLOperationMapping();
/* 121 */       JavaMethodImpl jm = (wsdlOp != null) ? (JavaMethodImpl)wsdlOp.getJavaMethod() : null;
/* 122 */       if (jm != null) {
/* 123 */         for (CheckedExceptionImpl ce : jm.getCheckedExceptions()) {
/* 124 */           if ((ce.getDetailType()).tagName.getLocalPart().equals(name) && 
/* 125 */             (ce.getDetailType()).tagName.getNamespaceURI().equals(ns)) {
/* 126 */             return ce.getFaultAction();
/*     */           }
/*     */         } 
/*     */       }
/* 130 */       return action;
/* 131 */     } catch (SOAPException e) {
/* 132 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   String getFaultAction(@Nullable WSDLBoundOperation wbo, Packet responsePacket) {
/* 137 */     String action = AddressingUtils.getAction(responsePacket.getMessage().getHeaders(), this.addVer, this.soapVer);
/* 138 */     if (action != null) {
/* 139 */       return action;
/*     */     }
/*     */     
/* 142 */     action = this.addVer.getDefaultFaultAction();
/* 143 */     if (wbo == null) {
/* 144 */       return action;
/*     */     }
/*     */     
/*     */     try {
/* 148 */       SOAPMessage sm = responsePacket.getMessage().copy().readAsSOAPMessage();
/* 149 */       if (sm == null) {
/* 150 */         return action;
/*     */       }
/*     */       
/* 153 */       if (sm.getSOAPBody() == null) {
/* 154 */         return action;
/*     */       }
/*     */       
/* 157 */       if (sm.getSOAPBody().getFault() == null) {
/* 158 */         return action;
/*     */       }
/*     */       
/* 161 */       Detail detail = sm.getSOAPBody().getFault().getDetail();
/* 162 */       if (detail == null) {
/* 163 */         return action;
/*     */       }
/*     */       
/* 166 */       String ns = detail.getFirstChild().getNamespaceURI();
/* 167 */       String name = detail.getFirstChild().getLocalName();
/*     */       
/* 169 */       WSDLOperation o = wbo.getOperation();
/*     */       
/* 171 */       WSDLFault fault = o.getFault(new QName(ns, name));
/* 172 */       if (fault == null) {
/* 173 */         return action;
/*     */       }
/*     */       
/* 176 */       action = fault.getAction();
/*     */       
/* 178 */       return action;
/* 179 */     } catch (SOAPException e) {
/* 180 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getInputAction(Packet packet) {
/* 185 */     String action = null;
/*     */     
/* 187 */     if (this.wsdlPort != null) {
/* 188 */       WSDLOperationMapping wsdlOp = packet.getWSDLOperationMapping();
/* 189 */       if (wsdlOp != null) {
/* 190 */         WSDLBoundOperation wbo = wsdlOp.getWSDLBoundOperation();
/* 191 */         WSDLOperation op = wbo.getOperation();
/* 192 */         action = op.getInput().getAction();
/*     */       } 
/*     */     } 
/*     */     
/* 196 */     return action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEffectiveInputAction(Packet packet) {
/*     */     String action;
/* 208 */     if (packet.soapAction != null && !packet.soapAction.equals("")) {
/* 209 */       return packet.soapAction;
/*     */     }
/*     */ 
/*     */     
/* 213 */     if (this.wsdlPort != null) {
/* 214 */       WSDLOperationMapping wsdlOp = packet.getWSDLOperationMapping();
/* 215 */       if (wsdlOp != null) {
/* 216 */         WSDLBoundOperation wbo = wsdlOp.getWSDLBoundOperation();
/* 217 */         WSDLOperation op = wbo.getOperation();
/* 218 */         action = op.getInput().getAction();
/*     */       } else {
/* 220 */         action = packet.soapAction;
/*     */       } 
/*     */     } else {
/* 223 */       action = packet.soapAction;
/*     */     } 
/* 225 */     return action;
/*     */   }
/*     */   
/*     */   public boolean isInputActionDefault(Packet packet) {
/* 229 */     if (this.wsdlPort == null) {
/* 230 */       return false;
/*     */     }
/* 232 */     WSDLOperationMapping wsdlOp = packet.getWSDLOperationMapping();
/* 233 */     if (wsdlOp == null) {
/* 234 */       return false;
/*     */     }
/* 236 */     WSDLBoundOperation wbo = wsdlOp.getWSDLBoundOperation();
/* 237 */     WSDLOperation op = wbo.getOperation();
/* 238 */     return op.getInput().isDefaultAction();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSOAPAction(Packet packet) {
/* 243 */     String action = "";
/*     */     
/* 245 */     if (packet == null || packet.getMessage() == null) {
/* 246 */       return action;
/*     */     }
/*     */     
/* 249 */     if (this.wsdlPort == null) {
/* 250 */       return action;
/*     */     }
/*     */     
/* 253 */     WSDLOperationMapping wsdlOp = packet.getWSDLOperationMapping();
/* 254 */     if (wsdlOp == null) {
/* 255 */       return action;
/*     */     }
/*     */     
/* 258 */     WSDLBoundOperation op = wsdlOp.getWSDLBoundOperation();
/* 259 */     action = op.getSOAPAction();
/* 260 */     return action;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getOutputAction(Packet packet) {
/* 265 */     String action = null;
/* 266 */     WSDLOperationMapping wsdlOp = packet.getWSDLOperationMapping();
/* 267 */     if (wsdlOp != null) {
/* 268 */       JavaMethod javaMethod = wsdlOp.getJavaMethod();
/* 269 */       if (javaMethod != null) {
/* 270 */         JavaMethodImpl jm = (JavaMethodImpl)javaMethod;
/* 271 */         if (jm != null && jm.getOutputAction() != null && !jm.getOutputAction().equals("")) {
/* 272 */           return jm.getOutputAction();
/*     */         }
/*     */       } 
/* 275 */       WSDLBoundOperation wbo = wsdlOp.getWSDLBoundOperation();
/* 276 */       if (wbo != null) return getOutputAction(wbo); 
/*     */     } 
/* 278 */     return action;
/*     */   }
/*     */   
/*     */   String getOutputAction(@Nullable WSDLBoundOperation wbo) {
/* 282 */     String action = "http://jax-ws.dev.java.net/addressing/output-action-not-set";
/* 283 */     if (wbo != null) {
/* 284 */       WSDLOutput op = wbo.getOperation().getOutput();
/* 285 */       if (op != null) {
/* 286 */         action = op.getAction();
/*     */       }
/*     */     } 
/* 289 */     return action;
/*     */   }
/*     */   
/*     */   public SOAPFault createInvalidAddressingHeaderFault(InvalidAddressingHeaderException e, AddressingVersion av) {
/* 293 */     QName name = e.getProblemHeader();
/* 294 */     QName subsubcode = e.getSubsubcode();
/* 295 */     QName subcode = av.invalidMapTag;
/* 296 */     String faultstring = String.format(av.getInvalidMapText(), new Object[] { name, subsubcode });
/*     */     
/*     */     try {
/*     */       SOAPFault fault;
/*     */       
/* 301 */       if (this.soapVer == SOAPVersion.SOAP_12) {
/* 302 */         SOAPFactory factory = SOAPVersion.SOAP_12.getSOAPFactory();
/* 303 */         fault = factory.createFault();
/* 304 */         fault.setFaultCode(SOAPConstants.SOAP_SENDER_FAULT);
/* 305 */         fault.appendFaultSubcode(subcode);
/* 306 */         fault.appendFaultSubcode(subsubcode);
/* 307 */         getInvalidMapDetail(name, (Element)fault.addDetail());
/*     */       } else {
/* 309 */         SOAPFactory factory = SOAPVersion.SOAP_11.getSOAPFactory();
/* 310 */         fault = factory.createFault();
/* 311 */         fault.setFaultCode(subsubcode);
/*     */       } 
/*     */       
/* 314 */       fault.setFaultString(faultstring);
/*     */       
/* 316 */       return fault;
/* 317 */     } catch (SOAPException se) {
/* 318 */       throw new WebServiceException(se);
/*     */     } 
/*     */   }
/*     */   
/*     */   public SOAPFault newMapRequiredFault(MissingAddressingHeaderException e) {
/* 323 */     QName subcode = this.addVer.mapRequiredTag;
/* 324 */     QName subsubcode = this.addVer.mapRequiredTag;
/* 325 */     String faultstring = this.addVer.getMapRequiredText();
/*     */     
/*     */     try {
/*     */       SOAPFault fault;
/*     */       
/* 330 */       if (this.soapVer == SOAPVersion.SOAP_12) {
/* 331 */         SOAPFactory factory = SOAPVersion.SOAP_12.getSOAPFactory();
/* 332 */         fault = factory.createFault();
/* 333 */         fault.setFaultCode(SOAPConstants.SOAP_SENDER_FAULT);
/* 334 */         fault.appendFaultSubcode(subcode);
/* 335 */         fault.appendFaultSubcode(subsubcode);
/* 336 */         getMapRequiredDetail(e.getMissingHeaderQName(), (Element)fault.addDetail());
/*     */       } else {
/* 338 */         SOAPFactory factory = SOAPVersion.SOAP_11.getSOAPFactory();
/* 339 */         fault = factory.createFault();
/* 340 */         fault.setFaultCode(subsubcode);
/*     */       } 
/*     */       
/* 343 */       fault.setFaultString(faultstring);
/*     */       
/* 345 */       return fault;
/* 346 */     } catch (SOAPException se) {
/* 347 */       throw new WebServiceException(se);
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract void getProblemActionDetail(String paramString, Element paramElement);
/*     */   
/*     */   public abstract void getInvalidMapDetail(QName paramQName, Element paramElement);
/*     */   
/*     */   public abstract void getMapRequiredDetail(QName paramQName, Element paramElement);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/addressing/WsaTubeHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */