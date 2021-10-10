/*     */ package com.sun.xml.internal.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import com.sun.xml.internal.bind.v2.runtime.JaxBeanInfo;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.ValidationEvent;
/*     */ import javax.xml.bind.helpers.ValidationEventImpl;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.xml.sax.SAXException;
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
/*     */ public abstract class Loader
/*     */ {
/*     */   protected boolean expectText;
/*     */   
/*     */   protected Loader(boolean expectText) {
/*  49 */     this.expectText = expectText;
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
/*     */   protected Loader() {}
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
/*     */   public void startElement(UnmarshallingContext.State state, TagName ea) throws SAXException {}
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
/*     */   public void childElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/*  90 */     reportUnexpectedChildElement(ea, true);
/*  91 */     state.setLoader(Discarder.INSTANCE);
/*  92 */     state.setReceiver(null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void reportUnexpectedChildElement(TagName ea, boolean canRecover) throws SAXException {
/*  97 */     if (canRecover) {
/*     */ 
/*     */ 
/*     */       
/* 101 */       UnmarshallingContext context = UnmarshallingContext.getInstance();
/* 102 */       if (!context.parent.hasEventHandler() || 
/* 103 */         !context.shouldErrorBeReported())
/*     */         return; 
/*     */     } 
/* 106 */     if (ea.uri != ea.uri.intern() || ea.local != ea.local.intern()) {
/* 107 */       reportError(Messages.UNINTERNED_STRINGS.format(new Object[0]), canRecover);
/*     */     } else {
/* 109 */       reportError(Messages.UNEXPECTED_ELEMENT.format(new Object[] { ea.uri, ea.local, computeExpectedElements() }, ), canRecover);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<QName> getExpectedChildElements() {
/* 116 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<QName> getExpectedAttributes() {
/* 123 */     return Collections.emptyList();
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
/*     */   public void text(UnmarshallingContext.State state, CharSequence text) throws SAXException {
/* 135 */     text = text.toString().replace('\r', ' ').replace('\n', ' ').replace('\t', ' ').trim();
/* 136 */     reportError(Messages.UNEXPECTED_TEXT.format(new Object[] { text }, ), true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean expectText() {
/* 144 */     return this.expectText;
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
/*     */   public void leaveElement(UnmarshallingContext.State state, TagName ea) throws SAXException {}
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
/*     */   private String computeExpectedElements() {
/* 174 */     StringBuilder r = new StringBuilder();
/*     */     
/* 176 */     for (QName n : getExpectedChildElements()) {
/* 177 */       if (r.length() != 0) r.append(','); 
/* 178 */       r.append("<{").append(n.getNamespaceURI()).append('}').append(n.getLocalPart()).append('>');
/*     */     } 
/* 180 */     if (r.length() == 0) {
/* 181 */       return "(none)";
/*     */     }
/*     */     
/* 184 */     return r.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void fireBeforeUnmarshal(JaxBeanInfo beanInfo, Object child, UnmarshallingContext.State state) throws SAXException {
/* 194 */     if (beanInfo.lookForLifecycleMethods()) {
/* 195 */       UnmarshallingContext context = state.getContext();
/* 196 */       Unmarshaller.Listener listener = context.parent.getListener();
/* 197 */       if (beanInfo.hasBeforeUnmarshalMethod()) {
/* 198 */         beanInfo.invokeBeforeUnmarshalMethod(context.parent, child, state.getPrev().getTarget());
/*     */       }
/* 200 */       if (listener != null) {
/* 201 */         listener.beforeUnmarshal(child, state.getPrev().getTarget());
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
/*     */   protected final void fireAfterUnmarshal(JaxBeanInfo beanInfo, Object child, UnmarshallingContext.State state) throws SAXException {
/* 214 */     if (beanInfo.lookForLifecycleMethods()) {
/* 215 */       UnmarshallingContext context = state.getContext();
/* 216 */       Unmarshaller.Listener listener = context.parent.getListener();
/* 217 */       if (beanInfo.hasAfterUnmarshalMethod()) {
/* 218 */         beanInfo.invokeAfterUnmarshalMethod(context.parent, child, state.getTarget());
/*     */       }
/* 220 */       if (listener != null) {
/* 221 */         listener.afterUnmarshal(child, state.getTarget());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void handleGenericException(Exception e) throws SAXException {
/* 230 */     handleGenericException(e, false);
/*     */   }
/*     */   
/*     */   public static void handleGenericException(Exception e, boolean canRecover) throws SAXException {
/* 234 */     reportError(e.getMessage(), e, canRecover);
/*     */   }
/*     */   
/*     */   public static void handleGenericError(Error e) throws SAXException {
/* 238 */     reportError(e.getMessage(), false);
/*     */   }
/*     */   
/*     */   protected static void reportError(String msg, boolean canRecover) throws SAXException {
/* 242 */     reportError(msg, null, canRecover);
/*     */   }
/*     */   
/*     */   public static void reportError(String msg, Exception nested, boolean canRecover) throws SAXException {
/* 246 */     UnmarshallingContext context = UnmarshallingContext.getInstance();
/* 247 */     context.handleEvent((ValidationEvent)new ValidationEventImpl(canRecover ? 1 : 2, msg, context
/*     */ 
/*     */           
/* 250 */           .getLocator().getLocation(), nested), canRecover);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void handleParseConversionException(UnmarshallingContext.State state, Exception e) throws SAXException {
/* 260 */     state.getContext().handleError(e);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/unmarshaller/Loader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */