/*     */ package com.sun.xml.internal.ws.wsdl.writer;
/*     */ 
/*     */ import com.sun.xml.internal.txw2.TypedXmlWriter;
/*     */ import com.sun.xml.internal.ws.addressing.W3CAddressingMetadataConstants;
/*     */ import com.sun.xml.internal.ws.addressing.WsaActionUtil;
/*     */ import com.sun.xml.internal.ws.api.model.CheckedException;
/*     */ import com.sun.xml.internal.ws.api.model.JavaMethod;
/*     */ import com.sun.xml.internal.ws.api.wsdl.writer.WSDLGenExtnContext;
/*     */ import com.sun.xml.internal.ws.api.wsdl.writer.WSDLGeneratorExtension;
/*     */ import com.sun.xml.internal.ws.model.CheckedExceptionImpl;
/*     */ import com.sun.xml.internal.ws.model.JavaMethodImpl;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class W3CAddressingMetadataWSDLGeneratorExtension
/*     */   extends WSDLGeneratorExtension
/*     */ {
/*     */   public void start(WSDLGenExtnContext ctxt) {
/*  51 */     TypedXmlWriter root = ctxt.getRoot();
/*  52 */     root._namespace("http://www.w3.org/2007/05/addressing/metadata", "wsam");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addOperationInputExtension(TypedXmlWriter input, JavaMethod method) {
/*  58 */     input._attribute(W3CAddressingMetadataConstants.WSAM_ACTION_QNAME, getInputAction(method));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addOperationOutputExtension(TypedXmlWriter output, JavaMethod method) {
/*  64 */     output._attribute(W3CAddressingMetadataConstants.WSAM_ACTION_QNAME, getOutputAction(method));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addOperationFaultExtension(TypedXmlWriter fault, JavaMethod method, CheckedException ce) {
/*  70 */     fault._attribute(W3CAddressingMetadataConstants.WSAM_ACTION_QNAME, getFaultAction(method, ce));
/*     */   }
/*     */ 
/*     */   
/*     */   private static final String getInputAction(JavaMethod method) {
/*  75 */     String inputaction = ((JavaMethodImpl)method).getInputAction();
/*  76 */     if (inputaction.equals(""))
/*     */     {
/*  78 */       inputaction = getDefaultInputAction(method);
/*     */     }
/*  80 */     return inputaction;
/*     */   }
/*     */   
/*     */   protected static final String getDefaultInputAction(JavaMethod method) {
/*  84 */     String tns = method.getOwner().getTargetNamespace();
/*  85 */     String delim = getDelimiter(tns);
/*  86 */     if (tns.endsWith(delim)) {
/*  87 */       tns = tns.substring(0, tns.length() - 1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  92 */     String name = method.getMEP().isOneWay() ? method.getOperationName() : (method.getOperationName() + "Request");
/*     */     
/*  94 */     return tns + delim + method
/*  95 */       .getOwner().getPortTypeName().getLocalPart() + delim + name;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final String getOutputAction(JavaMethod method) {
/* 100 */     String outputaction = ((JavaMethodImpl)method).getOutputAction();
/* 101 */     if (outputaction.equals(""))
/* 102 */       outputaction = getDefaultOutputAction(method); 
/* 103 */     return outputaction;
/*     */   }
/*     */   
/*     */   protected static final String getDefaultOutputAction(JavaMethod method) {
/* 107 */     String tns = method.getOwner().getTargetNamespace();
/* 108 */     String delim = getDelimiter(tns);
/* 109 */     if (tns.endsWith(delim)) {
/* 110 */       tns = tns.substring(0, tns.length() - 1);
/*     */     }
/*     */ 
/*     */     
/* 114 */     String name = method.getOperationName() + "Response";
/*     */     
/* 116 */     return tns + delim + method
/* 117 */       .getOwner().getPortTypeName().getLocalPart() + delim + name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String getDelimiter(String tns) {
/* 123 */     String delim = "/";
/*     */     
/*     */     try {
/* 126 */       URI uri = new URI(tns);
/* 127 */       if (uri.getScheme() != null && uri.getScheme().equalsIgnoreCase("urn"))
/* 128 */         delim = ":"; 
/* 129 */     } catch (URISyntaxException e) {
/* 130 */       LOGGER.warning("TargetNamespace of WebService is not a valid URI");
/*     */     } 
/* 132 */     return delim;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String getFaultAction(JavaMethod method, CheckedException ce) {
/* 138 */     String faultaction = ((CheckedExceptionImpl)ce).getFaultAction();
/* 139 */     if (faultaction.equals("")) {
/* 140 */       faultaction = getDefaultFaultAction(method, ce);
/*     */     }
/* 142 */     return faultaction;
/*     */   }
/*     */   
/*     */   protected static final String getDefaultFaultAction(JavaMethod method, CheckedException ce) {
/* 146 */     return WsaActionUtil.getDefaultFaultAction(method, ce);
/*     */   }
/*     */ 
/*     */   
/* 150 */   private static final Logger LOGGER = Logger.getLogger(W3CAddressingMetadataWSDLGeneratorExtension.class.getName());
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/wsdl/writer/W3CAddressingMetadataWSDLGeneratorExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */