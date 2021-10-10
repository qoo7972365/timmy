/*     */ package com.sun.xml.internal.ws.model.wsdl;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLExtension;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLPort;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLService;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import org.xml.sax.Locator;
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
/*     */ public final class WSDLServiceImpl
/*     */   extends AbstractExtensibleImpl
/*     */   implements EditableWSDLService
/*     */ {
/*     */   private final QName name;
/*     */   private final Map<QName, EditableWSDLPort> ports;
/*     */   private final EditableWSDLModel parent;
/*     */   
/*     */   public WSDLServiceImpl(XMLStreamReader xsr, EditableWSDLModel parent, QName name) {
/*  53 */     super(xsr);
/*  54 */     this.parent = parent;
/*  55 */     this.name = name;
/*  56 */     this.ports = new LinkedHashMap<>();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public EditableWSDLModel getParent() {
/*  61 */     return this.parent;
/*     */   }
/*     */   
/*     */   public QName getName() {
/*  65 */     return this.name;
/*     */   }
/*     */   
/*     */   public EditableWSDLPort get(QName portName) {
/*  69 */     return this.ports.get(portName);
/*     */   }
/*     */   
/*     */   public EditableWSDLPort getFirstPort() {
/*  73 */     if (this.ports.isEmpty()) {
/*  74 */       return null;
/*     */     }
/*  76 */     return this.ports.values().iterator().next();
/*     */   }
/*     */   
/*     */   public Iterable<EditableWSDLPort> getPorts() {
/*  80 */     return this.ports.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public EditableWSDLPort getMatchingPort(QName portTypeName) {
/*  88 */     for (EditableWSDLPort port : getPorts()) {
/*  89 */       QName ptName = port.getBinding().getPortTypeName();
/*  90 */       assert ptName != null;
/*  91 */       if (ptName.equals(portTypeName))
/*  92 */         return port; 
/*     */     } 
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(QName portName, EditableWSDLPort port) {
/* 105 */     if (portName == null || port == null)
/* 106 */       throw new NullPointerException(); 
/* 107 */     this.ports.put(portName, port);
/*     */   }
/*     */   
/*     */   public void freeze(EditableWSDLModel root) {
/* 111 */     for (EditableWSDLPort port : this.ports.values())
/* 112 */       port.freeze(root); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/model/wsdl/WSDLServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */