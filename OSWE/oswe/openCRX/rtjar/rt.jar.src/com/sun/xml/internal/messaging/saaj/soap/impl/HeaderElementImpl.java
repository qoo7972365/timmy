/*     */ package com.sun.xml.internal.messaging.saaj.soap.impl;
/*     */ 
/*     */ import com.sun.xml.internal.messaging.saaj.soap.SOAPDocumentImpl;
/*     */ import com.sun.xml.internal.messaging.saaj.soap.name.NameImpl;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPHeaderElement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class HeaderElementImpl
/*     */   extends ElementImpl
/*     */   implements SOAPHeaderElement
/*     */ {
/*  39 */   protected static Name RELAY_ATTRIBUTE_LOCAL_NAME = NameImpl.createFromTagName("relay");
/*     */   
/*  41 */   protected static Name MUST_UNDERSTAND_ATTRIBUTE_LOCAL_NAME = NameImpl.createFromTagName("mustUnderstand"); Name actorAttNameWithoutNS;
/*     */   Name roleAttNameWithoutNS;
/*     */   
/*  44 */   public HeaderElementImpl(SOAPDocumentImpl ownerDoc, Name qname) { super(ownerDoc, qname);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     this.actorAttNameWithoutNS = NameImpl.createFromTagName("actor");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  93 */     this.roleAttNameWithoutNS = NameImpl.createFromTagName("role"); } public HeaderElementImpl(SOAPDocumentImpl ownerDoc, QName qname) { super(ownerDoc, qname); this.actorAttNameWithoutNS = NameImpl.createFromTagName("actor"); this.roleAttNameWithoutNS = NameImpl.createFromTagName("role"); }
/*     */   
/*     */   protected abstract NameImpl getActorAttributeName(); protected abstract NameImpl getRoleAttributeName(); protected abstract NameImpl getMustunderstandAttributeName(); protected abstract boolean getMustunderstandAttributeValue(String paramString); protected abstract String getMustunderstandLiteralValue(boolean paramBoolean);
/*     */   protected abstract NameImpl getRelayAttributeName();
/*  97 */   public String getRole() { String role = getAttributeValue((Name)getRoleAttributeName());
/*  98 */     return role; }
/*     */   protected abstract boolean getRelayAttributeValue(String paramString);
/*     */   protected abstract String getRelayLiteralValue(boolean paramBoolean);
/*     */   protected abstract String getActorOrRole();
/*     */   public void setParentElement(SOAPElement element) throws SOAPException { if (!(element instanceof javax.xml.soap.SOAPHeader)) { log.severe("SAAJ0130.impl.header.elem.parent.mustbe.header"); throw new SOAPException("Parent of a SOAPHeaderElement has to be a SOAPHeader"); }  super.setParentElement(element); } public void setActor(String actorUri) { try { removeAttribute((Name)getActorAttributeName()); addAttribute((Name)getActorAttributeName(), actorUri); }
/* 103 */     catch (SOAPException sOAPException) {} } public void setRole(String roleUri) throws SOAPException { removeAttribute((Name)getRoleAttributeName()); addAttribute((Name)getRoleAttributeName(), roleUri); } public String getActor() { String actor = getAttributeValue((Name)getActorAttributeName()); return actor; } public void setMustUnderstand(boolean mustUnderstand) { try { removeAttribute((Name)getMustunderstandAttributeName());
/* 104 */       addAttribute((Name)
/* 105 */           getMustunderstandAttributeName(), 
/* 106 */           getMustunderstandLiteralValue(mustUnderstand)); }
/* 107 */     catch (SOAPException sOAPException) {} }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getMustUnderstand() {
/* 112 */     String mu = getAttributeValue((Name)getMustunderstandAttributeName());
/*     */     
/* 114 */     if (mu != null) {
/* 115 */       return getMustunderstandAttributeValue(mu);
/*     */     }
/* 117 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRelay(boolean relay) throws SOAPException {
/* 122 */     removeAttribute((Name)getRelayAttributeName());
/* 123 */     addAttribute((Name)
/* 124 */         getRelayAttributeName(), 
/* 125 */         getRelayLiteralValue(relay));
/*     */   }
/*     */   
/*     */   public boolean getRelay() {
/* 129 */     String mu = getAttributeValue((Name)getRelayAttributeName());
/* 130 */     if (mu != null) {
/* 131 */       return getRelayAttributeValue(mu);
/*     */     }
/* 133 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/soap/impl/HeaderElementImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */