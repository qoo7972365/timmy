/*     */ package com.sun.xml.internal.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.bind.DatatypeConverterImpl;
/*     */ import com.sun.xml.internal.bind.v2.runtime.JaxBeanInfo;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.xml.sax.Attributes;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XsiTypeLoader
/*     */   extends Loader
/*     */ {
/*     */   private final JaxBeanInfo defaultBeanInfo;
/*     */   
/*     */   public XsiTypeLoader(JaxBeanInfo defaultBeanInfo) {
/*  54 */     super(true);
/*  55 */     this.defaultBeanInfo = defaultBeanInfo;
/*     */   }
/*     */   
/*     */   public void startElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/*  59 */     JaxBeanInfo beanInfo = parseXsiType(state, ea, this.defaultBeanInfo);
/*  60 */     if (beanInfo == null) {
/*  61 */       beanInfo = this.defaultBeanInfo;
/*     */     }
/*  63 */     Loader loader = beanInfo.getLoader(null, false);
/*  64 */     state.setLoader(loader);
/*  65 */     loader.startElement(state, ea);
/*     */   }
/*     */   
/*     */   static JaxBeanInfo parseXsiType(UnmarshallingContext.State state, TagName ea, @Nullable JaxBeanInfo defaultBeanInfo) throws SAXException {
/*  69 */     UnmarshallingContext context = state.getContext();
/*  70 */     JaxBeanInfo beanInfo = null;
/*     */ 
/*     */     
/*  73 */     Attributes atts = ea.atts;
/*  74 */     int idx = atts.getIndex("http://www.w3.org/2001/XMLSchema-instance", "type");
/*     */     
/*  76 */     if (idx >= 0) {
/*     */ 
/*     */       
/*  79 */       String value = atts.getValue(idx);
/*     */       
/*  81 */       QName type = DatatypeConverterImpl._parseQName(value, context);
/*  82 */       if (type == null) {
/*  83 */         reportError(Messages.NOT_A_QNAME.format(new Object[] { value }, ), true);
/*     */       } else {
/*  85 */         if (defaultBeanInfo != null && defaultBeanInfo.getTypeNames().contains(type))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  92 */           return defaultBeanInfo;
/*     */         }
/*  94 */         beanInfo = context.getJAXBContext().getGlobalType(type);
/*  95 */         if (beanInfo == null && 
/*  96 */           context.parent.hasEventHandler() && context
/*  97 */           .shouldErrorBeReported()) {
/*  98 */           String nearest = context.getJAXBContext().getNearestTypeName(type);
/*  99 */           if (nearest != null) {
/* 100 */             reportError(Messages.UNRECOGNIZED_TYPE_NAME_MAYBE.format(new Object[] { type, nearest }, ), true);
/*     */           } else {
/* 102 */             reportError(Messages.UNRECOGNIZED_TYPE_NAME.format(new Object[] { type }, ), true);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 115 */     return beanInfo;
/*     */   }
/*     */   
/* 118 */   static final QName XsiTypeQNAME = new QName("http://www.w3.org/2001/XMLSchema-instance", "type");
/*     */ 
/*     */   
/*     */   public Collection<QName> getExpectedAttributes() {
/* 122 */     Collection<QName> expAttrs = new HashSet<>();
/* 123 */     expAttrs.addAll(super.getExpectedAttributes());
/* 124 */     expAttrs.add(XsiTypeQNAME);
/* 125 */     return Collections.unmodifiableCollection(expAttrs);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/unmarshaller/XsiTypeLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */