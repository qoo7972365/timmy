/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import javax.naming.CompositeName;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NameNotFoundException;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.directory.Attribute;
/*     */ import javax.naming.directory.BasicAttribute;
/*     */ import javax.naming.directory.DirContext;
/*     */ import javax.naming.directory.InitialDirContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class LdapAttribute
/*     */   extends BasicAttribute
/*     */ {
/*     */   static final long serialVersionUID = -4288716561020779584L;
/*  45 */   private transient DirContext baseCtx = null;
/*  46 */   private Name rdn = new CompositeName();
/*     */ 
/*     */   
/*     */   private String baseCtxURL;
/*     */   
/*     */   private Hashtable<String, ? super String> baseCtxEnv;
/*     */ 
/*     */   
/*     */   public Object clone() {
/*  55 */     LdapAttribute ldapAttribute = new LdapAttribute(this.attrID, this.baseCtx, this.rdn);
/*  56 */     ldapAttribute.values = (Vector<Object>)this.values.clone();
/*  57 */     return ldapAttribute;
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
/*     */   public boolean add(Object paramObject) {
/*  70 */     this.values.addElement(paramObject);
/*  71 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LdapAttribute(String paramString) {
/*  80 */     super(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LdapAttribute(String paramString, DirContext paramDirContext, Name paramName) {
/*  91 */     super(paramString);
/*  92 */     this.baseCtx = paramDirContext;
/*  93 */     this.rdn = paramName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setParent(DirContext paramDirContext, Name paramName) {
/* 101 */     this.baseCtx = paramDirContext;
/* 102 */     this.rdn = paramName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DirContext getBaseCtx() throws NamingException {
/* 112 */     if (this.baseCtx == null) {
/* 113 */       if (this.baseCtxEnv == null) {
/* 114 */         this.baseCtxEnv = new Hashtable<>(3);
/*     */       }
/* 116 */       this.baseCtxEnv.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
/*     */       
/* 118 */       this.baseCtxEnv.put("java.naming.provider.url", this.baseCtxURL);
/* 119 */       this.baseCtx = new InitialDirContext(this.baseCtxEnv);
/*     */     } 
/* 121 */     return this.baseCtx;
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 135 */     setBaseCtxInfo();
/*     */ 
/*     */     
/* 138 */     paramObjectOutputStream.defaultWriteObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setBaseCtxInfo() {
/* 148 */     Hashtable<String, Object> hashtable = null;
/* 149 */     Hashtable<String, ? super String> hashtable1 = null;
/*     */     
/* 151 */     if (this.baseCtx != null) {
/* 152 */       hashtable = ((LdapCtx)this.baseCtx).envprops;
/* 153 */       this.baseCtxURL = ((LdapCtx)this.baseCtx).getURL();
/*     */     } 
/*     */     
/* 156 */     if (hashtable != null && hashtable.size() > 0)
/*     */     {
/*     */       
/* 159 */       for (String str : hashtable.keySet()) {
/* 160 */         if (str.indexOf("security") != -1) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 165 */           if (hashtable1 == null) {
/* 166 */             hashtable1 = (Hashtable)hashtable.clone();
/*     */           }
/* 168 */           hashtable1.remove(str);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 174 */     this.baseCtxEnv = (hashtable1 == null) ? (Hashtable)hashtable : hashtable1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DirContext getAttributeSyntaxDefinition() throws NamingException {
/* 183 */     DirContext dirContext1 = getBaseCtx().getSchema(this.rdn);
/* 184 */     DirContext dirContext2 = (DirContext)dirContext1.lookup("AttributeDefinition/" + 
/* 185 */         getID());
/*     */     
/* 187 */     Attribute attribute = dirContext2.getAttributes("").get("SYNTAX");
/*     */     
/* 189 */     if (attribute == null || attribute.size() == 0) {
/* 190 */       throw new NameNotFoundException(
/* 191 */           getID() + "does not have a syntax associated with it");
/*     */     }
/*     */     
/* 194 */     String str = (String)attribute.get();
/*     */ 
/*     */     
/* 197 */     return (DirContext)dirContext1.lookup("SyntaxDefinition/" + str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DirContext getAttributeDefinition() throws NamingException {
/* 207 */     DirContext dirContext = getBaseCtx().getSchema(this.rdn);
/*     */     
/* 209 */     return (DirContext)dirContext.lookup("AttributeDefinition/" + 
/* 210 */         getID());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/LdapAttribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */