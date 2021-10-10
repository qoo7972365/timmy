/*     */ package com.oracle.webservices.internal.api.message;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.EnvelopeStyle;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.util.ServiceFinder;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import javax.xml.soap.MimeHeaders;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.WebServiceFeature;
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
/*     */ public abstract class MessageContextFactory
/*     */ {
/*  42 */   private static final MessageContextFactory DEFAULT = (MessageContextFactory)new com.sun.xml.internal.ws.api.message.MessageContextFactory(new WebServiceFeature[0]);
/*     */ 
/*     */   
/*     */   protected abstract MessageContextFactory newFactory(WebServiceFeature... paramVarArgs);
/*     */ 
/*     */   
/*     */   public abstract MessageContext createContext();
/*     */ 
/*     */   
/*     */   public abstract MessageContext createContext(SOAPMessage paramSOAPMessage);
/*     */   
/*     */   public abstract MessageContext createContext(Source paramSource);
/*     */   
/*     */   public abstract MessageContext createContext(Source paramSource, EnvelopeStyle.Style paramStyle);
/*     */   
/*     */   public abstract MessageContext createContext(InputStream paramInputStream, String paramString) throws IOException;
/*     */   
/*     */   @Deprecated
/*     */   public abstract MessageContext createContext(InputStream paramInputStream, MimeHeaders paramMimeHeaders) throws IOException;
/*     */   
/*     */   public static MessageContextFactory createFactory(WebServiceFeature... f) {
/*  63 */     return createFactory(null, f);
/*     */   }
/*     */   
/*     */   public static MessageContextFactory createFactory(ClassLoader cl, WebServiceFeature... f) {
/*  67 */     for (MessageContextFactory factory : ServiceFinder.find(MessageContextFactory.class, cl)) {
/*  68 */       MessageContextFactory newfac = factory.newFactory(f);
/*  69 */       if (newfac != null) return newfac; 
/*     */     } 
/*  71 */     return (MessageContextFactory)new com.sun.xml.internal.ws.api.message.MessageContextFactory(f);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public abstract MessageContext doCreate();
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public abstract MessageContext doCreate(SOAPMessage paramSOAPMessage);
/*     */   
/*     */   @Deprecated
/*     */   public abstract MessageContext doCreate(Source paramSource, SOAPVersion paramSOAPVersion);
/*     */   
/*     */   @Deprecated
/*     */   public static MessageContext create(ClassLoader... classLoader) {
/*  87 */     return serviceFinder(classLoader, new Creator()
/*     */         {
/*     */           public MessageContext create(MessageContextFactory f) {
/*  90 */             return f.doCreate();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static MessageContext create(final SOAPMessage m, ClassLoader... classLoader) {
/*  97 */     return serviceFinder(classLoader, new Creator()
/*     */         {
/*     */           public MessageContext create(MessageContextFactory f) {
/* 100 */             return f.doCreate(m);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static MessageContext create(final Source m, final SOAPVersion v, ClassLoader... classLoader) {
/* 107 */     return serviceFinder(classLoader, new Creator()
/*     */         {
/*     */           public MessageContext create(MessageContextFactory f) {
/* 110 */             return f.doCreate(m, v);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   private static MessageContext serviceFinder(ClassLoader[] classLoader, Creator creator) {
/* 117 */     ClassLoader cl = (classLoader.length == 0) ? null : classLoader[0];
/* 118 */     for (MessageContextFactory factory : ServiceFinder.find(MessageContextFactory.class, cl)) {
/* 119 */       MessageContext messageContext = creator.create(factory);
/* 120 */       if (messageContext != null)
/* 121 */         return messageContext; 
/*     */     } 
/* 123 */     return creator.create(DEFAULT);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   private static interface Creator {
/*     */     MessageContext create(MessageContextFactory param1MessageContextFactory);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/webservices/internal/api/message/MessageContextFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */