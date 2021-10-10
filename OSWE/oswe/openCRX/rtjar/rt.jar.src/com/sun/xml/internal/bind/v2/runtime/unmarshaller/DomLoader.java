/*     */ package com.sun.xml.internal.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
/*     */ import javax.xml.bind.annotation.DomHandler;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.sax.TransformerHandler;
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
/*     */ public class DomLoader<ResultT extends Result>
/*     */   extends Loader
/*     */ {
/*     */   private final DomHandler<?, ResultT> dom;
/*     */   
/*     */   private final class State
/*     */   {
/*  51 */     private TransformerHandler handler = null;
/*     */ 
/*     */     
/*     */     private final ResultT result;
/*     */ 
/*     */     
/*  57 */     int depth = 1;
/*     */     
/*     */     public State(UnmarshallingContext context) throws SAXException {
/*  60 */       this.handler = JAXBContextImpl.createTransformerHandler((context.getJAXBContext()).disableSecurityProcessing);
/*  61 */       this.result = (ResultT)DomLoader.this.dom.createUnmarshaller(context);
/*     */       
/*  63 */       this.handler.setResult((Result)this.result);
/*     */ 
/*     */       
/*     */       try {
/*  67 */         this.handler.setDocumentLocator(context.getLocator());
/*  68 */         this.handler.startDocument();
/*  69 */         declarePrefixes(context, context.getAllDeclaredPrefixes());
/*  70 */       } catch (SAXException e) {
/*  71 */         context.handleError(e);
/*  72 */         throw e;
/*     */       } 
/*     */     }
/*     */     
/*     */     public Object getElement() {
/*  77 */       return DomLoader.this.dom.getElement((Result)this.result);
/*     */     }
/*     */     
/*     */     private void declarePrefixes(UnmarshallingContext context, String[] prefixes) throws SAXException {
/*  81 */       for (int i = prefixes.length - 1; i >= 0; i--) {
/*  82 */         String nsUri = context.getNamespaceURI(prefixes[i]);
/*  83 */         if (nsUri == null) throw new IllegalStateException("prefix '" + prefixes[i] + "' isn't bound"); 
/*  84 */         this.handler.startPrefixMapping(prefixes[i], nsUri);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void undeclarePrefixes(String[] prefixes) throws SAXException {
/*  89 */       for (int i = prefixes.length - 1; i >= 0; i--)
/*  90 */         this.handler.endPrefixMapping(prefixes[i]); 
/*     */     }
/*     */   }
/*     */   
/*     */   public DomLoader(DomHandler<?, ResultT> dom) {
/*  95 */     super(true);
/*  96 */     this.dom = dom;
/*     */   }
/*     */ 
/*     */   
/*     */   public void startElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 101 */     UnmarshallingContext context = state.getContext();
/* 102 */     if (state.getTarget() == null) {
/* 103 */       state.setTarget(new State(context));
/*     */     }
/* 105 */     State s = (State)state.getTarget();
/*     */     try {
/* 107 */       s.declarePrefixes(context, context.getNewlyDeclaredPrefixes());
/* 108 */       s.handler.startElement(ea.uri, ea.local, ea.getQname(), ea.atts);
/* 109 */     } catch (SAXException e) {
/* 110 */       context.handleError(e);
/* 111 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void childElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 117 */     state.setLoader(this);
/* 118 */     State s = (State)state.getPrev().getTarget();
/* 119 */     s.depth++;
/* 120 */     state.setTarget(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public void text(UnmarshallingContext.State state, CharSequence text) throws SAXException {
/* 125 */     if (text.length() == 0)
/*     */       return; 
/*     */     try {
/* 128 */       State s = (State)state.getTarget();
/* 129 */       s.handler.characters(text.toString().toCharArray(), 0, text.length());
/* 130 */     } catch (SAXException e) {
/* 131 */       state.getContext().handleError(e);
/* 132 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void leaveElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 138 */     State s = (State)state.getTarget();
/* 139 */     UnmarshallingContext context = state.getContext();
/*     */     
/*     */     try {
/* 142 */       s.handler.endElement(ea.uri, ea.local, ea.getQname());
/* 143 */       s.undeclarePrefixes(context.getNewlyDeclaredPrefixes());
/* 144 */     } catch (SAXException e) {
/* 145 */       context.handleError(e);
/* 146 */       throw e;
/*     */     } 
/*     */     
/* 149 */     if (--s.depth == 0) {
/*     */       
/*     */       try {
/* 152 */         s.undeclarePrefixes(context.getAllDeclaredPrefixes());
/* 153 */         s.handler.endDocument();
/* 154 */       } catch (SAXException e) {
/* 155 */         context.handleError(e);
/* 156 */         throw e;
/*     */       } 
/*     */ 
/*     */       
/* 160 */       state.setTarget(s.getElement());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/unmarshaller/DomLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */