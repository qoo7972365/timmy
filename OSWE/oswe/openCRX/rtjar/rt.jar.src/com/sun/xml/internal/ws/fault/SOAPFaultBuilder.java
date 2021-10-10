/*     */ package com.sun.xml.internal.ws.fault;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.model.ExceptionType;
/*     */ import com.sun.xml.internal.ws.encoding.soap.SerializationException;
/*     */ import com.sun.xml.internal.ws.message.FaultMessage;
/*     */ import com.sun.xml.internal.ws.message.jaxb.JAXBMessage;
/*     */ import com.sun.xml.internal.ws.model.CheckedExceptionImpl;
/*     */ import com.sun.xml.internal.ws.spi.db.XMLBridge;
/*     */ import com.sun.xml.internal.ws.util.DOMUtil;
/*     */ import com.sun.xml.internal.ws.util.StringUtils;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.ReflectPermission;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permissions;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.annotation.XmlTransient;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Detail;
/*     */ import javax.xml.soap.DetailEntry;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.ws.ProtocolException;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.soap.SOAPFaultException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SOAPFaultBuilder
/*     */ {
/*     */   private static final JAXBContext JAXB_CONTEXT;
/*     */   
/*     */   @XmlTransient
/*     */   @Nullable
/*     */   public QName getFirstDetailEntryName() {
/*  89 */     DetailType dt = getDetail();
/*  90 */     if (dt != null) {
/*  91 */       Node entry = dt.getDetail(0);
/*  92 */       if (entry != null) {
/*  93 */         return new QName(entry.getNamespaceURI(), entry.getLocalName());
/*     */       }
/*     */     } 
/*  96 */     return null;
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
/*     */   public Throwable createException(Map<QName, CheckedExceptionImpl> exceptions) throws JAXBException {
/* 108 */     DetailType dt = getDetail();
/* 109 */     Node detail = null;
/* 110 */     if (dt != null) detail = dt.getDetail(0);
/*     */ 
/*     */     
/* 113 */     if (detail == null || exceptions == null)
/*     */     {
/*     */       
/* 116 */       return attachServerException(getProtocolException());
/*     */     }
/*     */ 
/*     */     
/* 120 */     QName detailName = new QName(detail.getNamespaceURI(), detail.getLocalName());
/* 121 */     CheckedExceptionImpl ce = exceptions.get(detailName);
/* 122 */     if (ce == null)
/*     */     {
/* 124 */       return attachServerException(getProtocolException());
/*     */     }
/*     */ 
/*     */     
/* 128 */     if (ce.getExceptionType().equals(ExceptionType.UserDefined)) {
/* 129 */       return attachServerException(createUserDefinedException(ce));
/*     */     }
/*     */     
/* 132 */     Class exceptionClass = ce.getExceptionClass();
/*     */     try {
/* 134 */       Constructor<Exception> constructor = exceptionClass.getConstructor(new Class[] { String.class, (Class)(ce.getDetailType()).type });
/* 135 */       Exception exception = constructor.newInstance(new Object[] { getFaultString(), getJAXBObject(detail, ce) });
/* 136 */       return attachServerException(exception);
/* 137 */     } catch (Exception e) {
/* 138 */       throw new WebServiceException(e);
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
/*     */   @NotNull
/*     */   public static Message createSOAPFaultMessage(@NotNull SOAPVersion soapVersion, @NotNull ProtocolException ex, @Nullable QName faultcode) {
/* 152 */     Object detail = getFaultDetail(null, (Throwable)ex);
/* 153 */     if (soapVersion == SOAPVersion.SOAP_12)
/* 154 */       return createSOAP12Fault(soapVersion, (Throwable)ex, detail, null, faultcode); 
/* 155 */     return createSOAP11Fault(soapVersion, (Throwable)ex, detail, null, faultcode);
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
/*     */   public static Message createSOAPFaultMessage(SOAPVersion soapVersion, CheckedExceptionImpl ceModel, Throwable ex) {
/* 186 */     Throwable t = (ex instanceof InvocationTargetException) ? ((InvocationTargetException)ex).getTargetException() : ex;
/*     */ 
/*     */     
/* 189 */     return createSOAPFaultMessage(soapVersion, ceModel, t, (QName)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message createSOAPFaultMessage(SOAPVersion soapVersion, CheckedExceptionImpl ceModel, Throwable ex, QName faultCode) {
/* 198 */     Object detail = getFaultDetail(ceModel, ex);
/* 199 */     if (soapVersion == SOAPVersion.SOAP_12)
/* 200 */       return createSOAP12Fault(soapVersion, ex, detail, ceModel, faultCode); 
/* 201 */     return createSOAP11Fault(soapVersion, ex, detail, ceModel, faultCode);
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
/*     */   public static Message createSOAPFaultMessage(SOAPVersion soapVersion, String faultString, QName faultCode) {
/* 228 */     if (faultCode == null)
/* 229 */       faultCode = getDefaultFaultCode(soapVersion); 
/* 230 */     return createSOAPFaultMessage(soapVersion, faultString, faultCode, (Element)null);
/*     */   }
/*     */   
/*     */   public static Message createSOAPFaultMessage(SOAPVersion soapVersion, SOAPFault fault) {
/* 234 */     switch (soapVersion) {
/*     */       case SOAP_11:
/* 236 */         return JAXBMessage.create(JAXB_CONTEXT, new SOAP11Fault(fault), soapVersion);
/*     */       case SOAP_12:
/* 238 */         return JAXBMessage.create(JAXB_CONTEXT, new SOAP12Fault(fault), soapVersion);
/*     */     } 
/* 240 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */   
/*     */   private static Message createSOAPFaultMessage(SOAPVersion soapVersion, String faultString, QName faultCode, Element detail) {
/* 245 */     switch (soapVersion) {
/*     */       case SOAP_11:
/* 247 */         return JAXBMessage.create(JAXB_CONTEXT, new SOAP11Fault(faultCode, faultString, null, detail), soapVersion);
/*     */       case SOAP_12:
/* 249 */         return JAXBMessage.create(JAXB_CONTEXT, new SOAP12Fault(faultCode, faultString, detail), soapVersion);
/*     */     } 
/* 251 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void captureStackTrace(@Nullable Throwable t) {
/* 260 */     if (t == null)
/* 261 */       return;  if (!captureStackTrace)
/*     */       return; 
/*     */     try {
/* 264 */       Document d = DOMUtil.createDom();
/* 265 */       ExceptionBean.marshal(t, d);
/*     */       
/* 267 */       DetailType detail = getDetail();
/* 268 */       if (detail == null) {
/* 269 */         setDetail(detail = new DetailType());
/*     */       }
/* 271 */       detail.getDetails().add(d.getDocumentElement());
/* 272 */     } catch (JAXBException e) {
/*     */       
/* 274 */       logger.log(Level.WARNING, "Unable to capture the stack trace into XML", (Throwable)e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <T extends Throwable> T attachServerException(T t) {
/* 282 */     DetailType detail = getDetail();
/* 283 */     if (detail == null) return t;
/*     */     
/* 285 */     for (Element n : detail.getDetails()) {
/* 286 */       if (ExceptionBean.isStackTraceXml(n)) {
/*     */         try {
/* 288 */           t.initCause((Throwable)ExceptionBean.unmarshal(n));
/* 289 */         } catch (JAXBException e) {
/*     */           
/* 291 */           logger.log(Level.WARNING, "Unable to read the capture stack trace in the fault", (Throwable)e);
/*     */         } 
/* 293 */         return t;
/*     */       } 
/*     */     } 
/*     */     
/* 297 */     return t;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Object getJAXBObject(Node jaxbBean, CheckedExceptionImpl ce) throws JAXBException {
/* 303 */     XMLBridge bridge = ce.getBond();
/* 304 */     return bridge.unmarshal(jaxbBean, null);
/*     */   }
/*     */   
/*     */   private Exception createUserDefinedException(CheckedExceptionImpl ce) {
/* 308 */     Class exceptionClass = ce.getExceptionClass();
/* 309 */     Class detailBean = ce.getDetailBean();
/*     */     try {
/* 311 */       Node detailNode = getDetail().getDetails().get(0);
/* 312 */       Object jaxbDetail = getJAXBObject(detailNode, ce);
/*     */       
/*     */       try {
/* 315 */         Constructor<Exception> exConstructor = exceptionClass.getConstructor(new Class[] { String.class, detailBean });
/* 316 */         return exConstructor.newInstance(new Object[] { getFaultString(), jaxbDetail });
/* 317 */       } catch (NoSuchMethodException e) {
/* 318 */         Constructor<Exception> exConstructor = exceptionClass.getConstructor(new Class[] { String.class });
/* 319 */         return exConstructor.newInstance(new Object[] { getFaultString() });
/*     */       } 
/* 321 */     } catch (Exception e) {
/* 322 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String getWriteMethod(Field f) {
/* 327 */     return "set" + StringUtils.capitalize(f.getName());
/*     */   }
/*     */   
/*     */   private static Object getFaultDetail(CheckedExceptionImpl ce, Throwable exception) {
/* 331 */     if (ce == null)
/* 332 */       return null; 
/* 333 */     if (ce.getExceptionType().equals(ExceptionType.UserDefined)) {
/* 334 */       return createDetailFromUserDefinedException(ce, exception);
/*     */     }
/*     */     try {
/* 337 */       Method m = exception.getClass().getMethod("getFaultInfo", new Class[0]);
/* 338 */       return m.invoke(exception, new Object[0]);
/* 339 */     } catch (Exception e) {
/* 340 */       throw new SerializationException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Object createDetailFromUserDefinedException(CheckedExceptionImpl ce, Object exception) {
/* 345 */     Class detailBean = ce.getDetailBean();
/* 346 */     Field[] fields = detailBean.getDeclaredFields();
/*     */     try {
/* 348 */       Object detail = detailBean.newInstance();
/* 349 */       for (Field f : fields) {
/* 350 */         Method em = exception.getClass().getMethod(getReadMethod(f), new Class[0]);
/*     */         try {
/* 352 */           Method sm = detailBean.getMethod(getWriteMethod(f), new Class[] { em.getReturnType() });
/* 353 */           sm.invoke(detail, new Object[] { em.invoke(exception, new Object[0]) });
/* 354 */         } catch (NoSuchMethodException ne) {
/*     */           
/* 356 */           Field sf = detailBean.getField(f.getName());
/* 357 */           sf.set(detail, em.invoke(exception, new Object[0]));
/*     */         } 
/*     */       } 
/* 360 */       return detail;
/* 361 */     } catch (Exception e) {
/* 362 */       throw new SerializationException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String getReadMethod(Field f) {
/* 367 */     if (f.getType().isAssignableFrom(boolean.class))
/* 368 */       return "is" + StringUtils.capitalize(f.getName()); 
/* 369 */     return "get" + StringUtils.capitalize(f.getName());
/*     */   }
/*     */   
/*     */   private static Message createSOAP11Fault(SOAPVersion soapVersion, Throwable e, Object detail, CheckedExceptionImpl ce, QName faultCode) {
/* 373 */     SOAPFaultException soapFaultException = null;
/* 374 */     String faultString = null;
/* 375 */     String faultActor = null;
/* 376 */     Throwable cause = e.getCause();
/* 377 */     if (e instanceof SOAPFaultException) {
/* 378 */       soapFaultException = (SOAPFaultException)e;
/* 379 */     } else if (cause != null && cause instanceof SOAPFaultException) {
/* 380 */       soapFaultException = (SOAPFaultException)e.getCause();
/*     */     } 
/* 382 */     if (soapFaultException != null) {
/* 383 */       QName soapFaultCode = soapFaultException.getFault().getFaultCodeAsQName();
/* 384 */       if (soapFaultCode != null) {
/* 385 */         faultCode = soapFaultCode;
/*     */       }
/* 387 */       faultString = soapFaultException.getFault().getFaultString();
/* 388 */       faultActor = soapFaultException.getFault().getFaultActor();
/*     */     } 
/*     */     
/* 391 */     if (faultCode == null) {
/* 392 */       faultCode = getDefaultFaultCode(soapVersion);
/*     */     }
/*     */     
/* 395 */     if (faultString == null) {
/* 396 */       faultString = e.getMessage();
/* 397 */       if (faultString == null) {
/* 398 */         faultString = e.toString();
/*     */       }
/*     */     } 
/* 401 */     Element detailNode = null;
/* 402 */     QName firstEntry = null;
/* 403 */     if (detail == null && soapFaultException != null) {
/* 404 */       Detail detail1 = soapFaultException.getFault().getDetail();
/* 405 */       firstEntry = getFirstDetailEntryName(detail1);
/* 406 */     } else if (ce != null) {
/*     */       try {
/* 408 */         DOMResult dr = new DOMResult();
/* 409 */         ce.getBond().marshal(detail, dr);
/* 410 */         detailNode = (Element)dr.getNode().getFirstChild();
/* 411 */         firstEntry = getFirstDetailEntryName(detailNode);
/* 412 */       } catch (JAXBException e1) {
/*     */         
/* 414 */         faultString = e.getMessage();
/* 415 */         faultCode = getDefaultFaultCode(soapVersion);
/*     */       } 
/*     */     } 
/* 418 */     SOAP11Fault soap11Fault = new SOAP11Fault(faultCode, faultString, faultActor, detailNode);
/*     */ 
/*     */     
/* 421 */     if (ce == null) {
/* 422 */       soap11Fault.captureStackTrace(e);
/*     */     }
/* 424 */     Message msg = JAXBMessage.create(JAXB_CONTEXT, soap11Fault, soapVersion);
/* 425 */     return (Message)new FaultMessage(msg, firstEntry);
/*     */   }
/*     */   @Nullable
/*     */   private static QName getFirstDetailEntryName(@Nullable Detail detail) {
/* 429 */     if (detail != null) {
/* 430 */       Iterator<DetailEntry> it = detail.getDetailEntries();
/* 431 */       if (it.hasNext()) {
/* 432 */         DetailEntry entry = it.next();
/* 433 */         return getFirstDetailEntryName((Element)entry);
/*     */       } 
/*     */     } 
/* 436 */     return null;
/*     */   }
/*     */   @NotNull
/*     */   private static QName getFirstDetailEntryName(@NotNull Element entry) {
/* 440 */     return new QName(entry.getNamespaceURI(), entry.getLocalName());
/*     */   }
/*     */   
/*     */   private static Message createSOAP12Fault(SOAPVersion soapVersion, Throwable e, Object detail, CheckedExceptionImpl ce, QName faultCode) {
/* 444 */     SOAPFaultException soapFaultException = null;
/* 445 */     CodeType code = null;
/* 446 */     String faultString = null;
/* 447 */     String faultRole = null;
/* 448 */     String faultNode = null;
/* 449 */     Throwable cause = e.getCause();
/* 450 */     if (e instanceof SOAPFaultException) {
/* 451 */       soapFaultException = (SOAPFaultException)e;
/* 452 */     } else if (cause != null && cause instanceof SOAPFaultException) {
/* 453 */       soapFaultException = (SOAPFaultException)e.getCause();
/*     */     } 
/* 455 */     if (soapFaultException != null) {
/* 456 */       SOAPFault fault = soapFaultException.getFault();
/* 457 */       QName soapFaultCode = fault.getFaultCodeAsQName();
/* 458 */       if (soapFaultCode != null) {
/* 459 */         faultCode = soapFaultCode;
/* 460 */         code = new CodeType(faultCode);
/* 461 */         Iterator<QName> iter = fault.getFaultSubcodes();
/* 462 */         boolean first = true;
/* 463 */         SubcodeType subcode = null;
/* 464 */         while (iter.hasNext()) {
/* 465 */           QName value = iter.next();
/* 466 */           if (first) {
/* 467 */             SubcodeType sct = new SubcodeType(value);
/* 468 */             code.setSubcode(sct);
/* 469 */             subcode = sct;
/* 470 */             first = false;
/*     */             continue;
/*     */           } 
/* 473 */           subcode = fillSubcodes(subcode, value);
/*     */         } 
/*     */       } 
/* 476 */       faultString = soapFaultException.getFault().getFaultString();
/* 477 */       faultRole = soapFaultException.getFault().getFaultActor();
/* 478 */       faultNode = soapFaultException.getFault().getFaultNode();
/*     */     } 
/*     */     
/* 481 */     if (faultCode == null) {
/* 482 */       faultCode = getDefaultFaultCode(soapVersion);
/* 483 */       code = new CodeType(faultCode);
/* 484 */     } else if (code == null) {
/* 485 */       code = new CodeType(faultCode);
/*     */     } 
/*     */     
/* 488 */     if (faultString == null) {
/* 489 */       faultString = e.getMessage();
/* 490 */       if (faultString == null) {
/* 491 */         faultString = e.toString();
/*     */       }
/*     */     } 
/*     */     
/* 495 */     ReasonType reason = new ReasonType(faultString);
/* 496 */     Element detailNode = null;
/* 497 */     QName firstEntry = null;
/* 498 */     if (detail == null && soapFaultException != null) {
/* 499 */       Detail detail1 = soapFaultException.getFault().getDetail();
/* 500 */       firstEntry = getFirstDetailEntryName(detail1);
/* 501 */     } else if (detail != null) {
/*     */       try {
/* 503 */         DOMResult dr = new DOMResult();
/* 504 */         ce.getBond().marshal(detail, dr);
/* 505 */         detailNode = (Element)dr.getNode().getFirstChild();
/* 506 */         firstEntry = getFirstDetailEntryName(detailNode);
/* 507 */       } catch (JAXBException e1) {
/*     */         
/* 509 */         faultString = e.getMessage();
/*     */       } 
/*     */     } 
/*     */     
/* 513 */     SOAP12Fault soap12Fault = new SOAP12Fault(code, reason, faultNode, faultRole, detailNode);
/*     */ 
/*     */     
/* 516 */     if (ce == null) {
/* 517 */       soap12Fault.captureStackTrace(e);
/*     */     }
/* 519 */     Message msg = JAXBMessage.create(JAXB_CONTEXT, soap12Fault, soapVersion);
/* 520 */     return (Message)new FaultMessage(msg, firstEntry);
/*     */   }
/*     */   
/*     */   private static SubcodeType fillSubcodes(SubcodeType parent, QName value) {
/* 524 */     SubcodeType newCode = new SubcodeType(value);
/* 525 */     parent.setSubcode(newCode);
/* 526 */     return newCode;
/*     */   }
/*     */   
/*     */   private static QName getDefaultFaultCode(SOAPVersion soapVersion) {
/* 530 */     return soapVersion.faultCodeServer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SOAPFaultBuilder create(Message msg) throws JAXBException {
/* 540 */     return (SOAPFaultBuilder)msg.readPayloadAsJAXB(JAXB_CONTEXT.createUnmarshaller());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 548 */   private static final Logger logger = Logger.getLogger(SOAPFaultBuilder.class.getName());
/*     */ 
/*     */ 
/*     */   
/*     */   public static final boolean captureStackTrace;
/*     */ 
/*     */   
/* 555 */   static final String CAPTURE_STACK_TRACE_PROPERTY = SOAPFaultBuilder.class.getName() + ".captureStackTrace";
/*     */   
/*     */   static {
/* 558 */     boolean tmpVal = false;
/*     */     try {
/* 560 */       tmpVal = Boolean.getBoolean(CAPTURE_STACK_TRACE_PROPERTY);
/* 561 */     } catch (SecurityException securityException) {}
/*     */ 
/*     */     
/* 564 */     captureStackTrace = tmpVal;
/* 565 */     JAXB_CONTEXT = createJAXBContext();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static JAXBContext createJAXBContext() {
/* 571 */     if (isJDKRuntime()) {
/* 572 */       Permissions permissions = new Permissions();
/* 573 */       permissions.add(new RuntimePermission("accessClassInPackage.com.sun.xml.internal.ws.fault"));
/* 574 */       permissions.add(new ReflectPermission("suppressAccessChecks"));
/* 575 */       return AccessController.<JAXBContext>doPrivileged(new PrivilegedAction<JAXBContext>()
/*     */           {
/*     */             public JAXBContext run()
/*     */             {
/*     */               try {
/* 580 */                 return JAXBContext.newInstance(new Class[] { SOAP11Fault.class, SOAP12Fault.class }, );
/* 581 */               } catch (JAXBException e) {
/* 582 */                 throw new Error(e);
/*     */               } 
/*     */             }
/*     */           },  new AccessControlContext(new ProtectionDomain[] { new ProtectionDomain(null, permissions) }));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 591 */       return JAXBContext.newInstance(new Class[] { SOAP11Fault.class, SOAP12Fault.class });
/* 592 */     } catch (JAXBException e) {
/* 593 */       throw new Error(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isJDKRuntime() {
/* 599 */     return SOAPFaultBuilder.class.getName().contains("internal");
/*     */   }
/*     */   
/*     */   abstract DetailType getDetail();
/*     */   
/*     */   abstract void setDetail(DetailType paramDetailType);
/*     */   
/*     */   abstract String getFaultString();
/*     */   
/*     */   protected abstract Throwable getProtocolException();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/fault/SOAPFaultBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */