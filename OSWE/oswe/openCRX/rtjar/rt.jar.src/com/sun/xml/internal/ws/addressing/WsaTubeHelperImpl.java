/*    */ package com.sun.xml.internal.ws.addressing;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.WSBinding;
/*    */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*    */ import javax.xml.bind.JAXBContext;
/*    */ import javax.xml.bind.JAXBException;
/*    */ import javax.xml.bind.Marshaller;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.ws.WebServiceException;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WsaTubeHelperImpl
/*    */   extends WsaTubeHelper
/*    */ {
/*    */   static final JAXBContext jc;
/*    */   
/*    */   static {
/*    */     try {
/* 47 */       jc = JAXBContext.newInstance(new Class[] { ProblemAction.class, ProblemHeaderQName.class });
/*    */     }
/* 49 */     catch (JAXBException e) {
/* 50 */       throw new WebServiceException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public WsaTubeHelperImpl(WSDLPort wsdlPort, SEIModel seiModel, WSBinding binding) {
/* 55 */     super(binding, seiModel, wsdlPort);
/*    */   }
/*    */   
/*    */   private Marshaller createMarshaller() throws JAXBException {
/* 59 */     Marshaller marshaller = jc.createMarshaller();
/* 60 */     marshaller.setProperty("jaxb.fragment", Boolean.TRUE);
/* 61 */     return marshaller;
/*    */   }
/*    */ 
/*    */   
/*    */   public final void getProblemActionDetail(String action, Element element) {
/* 66 */     ProblemAction pa = new ProblemAction(action);
/*    */     try {
/* 68 */       createMarshaller().marshal(pa, element);
/* 69 */     } catch (JAXBException e) {
/* 70 */       throw new WebServiceException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public final void getInvalidMapDetail(QName name, Element element) {
/* 76 */     ProblemHeaderQName phq = new ProblemHeaderQName(name);
/*    */     try {
/* 78 */       createMarshaller().marshal(phq, element);
/* 79 */     } catch (JAXBException e) {
/* 80 */       throw new WebServiceException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public final void getMapRequiredDetail(QName name, Element element) {
/* 86 */     getInvalidMapDetail(name, element);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/addressing/WsaTubeHelperImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */