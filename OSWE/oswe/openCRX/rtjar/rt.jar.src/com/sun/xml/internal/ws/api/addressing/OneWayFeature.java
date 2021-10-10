/*     */ package com.sun.xml.internal.ws.api.addressing;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.org.glassfish.gmbal.ManagedAttribute;
/*     */ import com.sun.org.glassfish.gmbal.ManagedData;
/*     */ import com.sun.xml.internal.ws.api.FeatureConstructor;
/*     */ import java.net.URL;
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
/*     */ @ManagedData
/*     */ public class OneWayFeature
/*     */   extends WebServiceFeature
/*     */ {
/*     */   public static final String ID = "http://java.sun.com/xml/ns/jaxws/addressing/oneway";
/*     */   private String messageId;
/*     */   private WSEndpointReference replyTo;
/*     */   private WSEndpointReference sslReplyTo;
/*     */   private WSEndpointReference from;
/*     */   private WSEndpointReference faultTo;
/*     */   private WSEndpointReference sslFaultTo;
/*     */   private String relatesToID;
/*     */   private boolean useAsyncWithSyncInvoke = false;
/*     */   
/*     */   public OneWayFeature() {
/*  78 */     this.enabled = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OneWayFeature(boolean enabled) {
/*  88 */     this.enabled = enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OneWayFeature(boolean enabled, WSEndpointReference replyTo) {
/*  98 */     this.enabled = enabled;
/*  99 */     this.replyTo = replyTo;
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
/*     */   @FeatureConstructor({"enabled", "replyTo", "from", "relatesTo"})
/*     */   public OneWayFeature(boolean enabled, WSEndpointReference replyTo, WSEndpointReference from, String relatesTo) {
/* 112 */     this.enabled = enabled;
/* 113 */     this.replyTo = replyTo;
/* 114 */     this.from = from;
/* 115 */     this.relatesToID = relatesTo;
/*     */   }
/*     */   
/*     */   public OneWayFeature(AddressingPropertySet a, AddressingVersion v) {
/* 119 */     this.enabled = true;
/* 120 */     this.messageId = a.getMessageId();
/* 121 */     this.relatesToID = a.getRelatesTo();
/* 122 */     this.replyTo = makeEPR(a.getReplyTo(), v);
/* 123 */     this.faultTo = makeEPR(a.getFaultTo(), v);
/*     */   }
/*     */   
/*     */   private WSEndpointReference makeEPR(String x, AddressingVersion v) {
/* 127 */     if (x == null) return null; 
/* 128 */     return new WSEndpointReference(x, v);
/*     */   }
/*     */   
/*     */   public String getMessageId() {
/* 132 */     return this.messageId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public String getID() {
/* 140 */     return "http://java.sun.com/xml/ns/jaxws/addressing/oneway";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasSslEprs() {
/* 145 */     return (this.sslReplyTo != null || this.sslFaultTo != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public WSEndpointReference getReplyTo() {
/* 155 */     return this.replyTo;
/*     */   }
/*     */   
/*     */   public WSEndpointReference getReplyTo(boolean ssl) {
/* 159 */     return (ssl && this.sslReplyTo != null) ? this.sslReplyTo : this.replyTo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReplyTo(WSEndpointReference address) {
/* 168 */     this.replyTo = address;
/*     */   }
/*     */   
/*     */   public WSEndpointReference getSslReplyTo() {
/* 172 */     return this.sslReplyTo;
/*     */   }
/*     */   
/*     */   public void setSslReplyTo(WSEndpointReference sslReplyTo) {
/* 176 */     this.sslReplyTo = sslReplyTo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public WSEndpointReference getFrom() {
/* 186 */     return this.from;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFrom(WSEndpointReference address) {
/* 195 */     this.from = address;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public String getRelatesToID() {
/* 205 */     return this.relatesToID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRelatesToID(String id) {
/* 214 */     this.relatesToID = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSEndpointReference getFaultTo() {
/* 223 */     return this.faultTo;
/*     */   }
/*     */   
/*     */   public WSEndpointReference getFaultTo(boolean ssl) {
/* 227 */     return (ssl && this.sslFaultTo != null) ? this.sslFaultTo : this.faultTo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFaultTo(WSEndpointReference address) {
/* 236 */     this.faultTo = address;
/*     */   }
/*     */   
/*     */   public WSEndpointReference getSslFaultTo() {
/* 240 */     return this.sslFaultTo;
/*     */   }
/*     */   
/*     */   public void setSslFaultTo(WSEndpointReference sslFaultTo) {
/* 244 */     this.sslFaultTo = sslFaultTo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseAsyncWithSyncInvoke() {
/* 253 */     return this.useAsyncWithSyncInvoke;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUseAsyncWithSyncInvoke(boolean useAsyncWithSyncInvoke) {
/* 262 */     this.useAsyncWithSyncInvoke = useAsyncWithSyncInvoke;
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
/*     */   public static WSEndpointReference enableSslForEpr(@NotNull WSEndpointReference epr, @Nullable String sslHost, int sslPort) {
/* 278 */     if (!epr.isAnonymous()) {
/* 279 */       URL url; String address = epr.getAddress();
/*     */       
/*     */       try {
/* 282 */         url = new URL(address);
/* 283 */       } catch (Exception e) {
/* 284 */         throw new RuntimeException(e);
/*     */       } 
/* 286 */       String protocol = url.getProtocol();
/* 287 */       if (!protocol.equalsIgnoreCase("https")) {
/* 288 */         protocol = "https";
/* 289 */         String host = url.getHost();
/* 290 */         if (sslHost != null) {
/* 291 */           host = sslHost;
/*     */         }
/* 293 */         int port = url.getPort();
/* 294 */         if (sslPort > 0) {
/* 295 */           port = sslPort;
/*     */         }
/*     */         try {
/* 298 */           url = new URL(protocol, host, port, url.getFile());
/* 299 */         } catch (Exception e) {
/* 300 */           throw new RuntimeException(e);
/*     */         } 
/* 302 */         address = url.toExternalForm();
/* 303 */         return new WSEndpointReference(address, epr
/* 304 */             .getVersion());
/*     */       } 
/*     */     } 
/*     */     
/* 308 */     return epr;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/addressing/OneWayFeature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */