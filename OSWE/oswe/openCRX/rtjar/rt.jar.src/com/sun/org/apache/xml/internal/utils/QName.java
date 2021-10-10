/*     */ package com.sun.org.apache.xml.internal.utils;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.res.XMLMessages;
/*     */ import java.io.Serializable;
/*     */ import java.util.Stack;
/*     */ import java.util.StringTokenizer;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QName
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 467434581652829920L;
/*     */   protected String _localName;
/*     */   protected String _namespaceURI;
/*     */   protected String _prefix;
/*     */   public static final String S_XMLNAMESPACEURI = "http://www.w3.org/XML/1998/namespace";
/*     */   private int m_hashCode;
/*     */   
/*     */   public QName() {}
/*     */   
/*     */   public QName(String namespaceURI, String localName) {
/*  95 */     this(namespaceURI, localName, false);
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
/*     */   public QName(String namespaceURI, String localName, boolean validate) {
/* 112 */     if (localName == null) {
/* 113 */       throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_NULL", null));
/*     */     }
/*     */     
/* 116 */     if (validate)
/*     */     {
/* 118 */       if (!XML11Char.isXML11ValidNCName(localName))
/*     */       {
/* 120 */         throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_INVALID", null));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 125 */     this._namespaceURI = namespaceURI;
/* 126 */     this._localName = localName;
/* 127 */     this.m_hashCode = toString().hashCode();
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
/*     */   public QName(String namespaceURI, String prefix, String localName) {
/* 141 */     this(namespaceURI, prefix, localName, false);
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
/*     */   public QName(String namespaceURI, String prefix, String localName, boolean validate) {
/* 159 */     if (localName == null) {
/* 160 */       throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_NULL", null));
/*     */     }
/*     */     
/* 163 */     if (validate) {
/*     */       
/* 165 */       if (!XML11Char.isXML11ValidNCName(localName))
/*     */       {
/* 167 */         throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_INVALID", null));
/*     */       }
/*     */ 
/*     */       
/* 171 */       if (null != prefix && !XML11Char.isXML11ValidNCName(prefix))
/*     */       {
/* 173 */         throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_PREFIX_INVALID", null));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 178 */     this._namespaceURI = namespaceURI;
/* 179 */     this._prefix = prefix;
/* 180 */     this._localName = localName;
/* 181 */     this.m_hashCode = toString().hashCode();
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
/*     */   public QName(String localName) {
/* 193 */     this(localName, false);
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
/*     */   public QName(String localName, boolean validate) {
/* 209 */     if (localName == null) {
/* 210 */       throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_NULL", null));
/*     */     }
/*     */     
/* 213 */     if (validate)
/*     */     {
/* 215 */       if (!XML11Char.isXML11ValidNCName(localName))
/*     */       {
/* 217 */         throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_INVALID", null));
/*     */       }
/*     */     }
/*     */     
/* 221 */     this._namespaceURI = null;
/* 222 */     this._localName = localName;
/* 223 */     this.m_hashCode = toString().hashCode();
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
/*     */   public QName(String qname, Stack namespaces) {
/* 236 */     this(qname, namespaces, false);
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
/*     */   public QName(String qname, Stack<NameSpace> namespaces, boolean validate) {
/* 252 */     String namespace = null;
/* 253 */     String prefix = null;
/* 254 */     int indexOfNSSep = qname.indexOf(':');
/*     */     
/* 256 */     if (indexOfNSSep > 0) {
/*     */       
/* 258 */       prefix = qname.substring(0, indexOfNSSep);
/*     */       
/* 260 */       if (prefix.equals("xml")) {
/*     */         
/* 262 */         namespace = "http://www.w3.org/XML/1998/namespace";
/*     */       } else {
/*     */         
/* 265 */         if (prefix.equals("xmlns")) {
/*     */           return;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 271 */         int depth = namespaces.size();
/*     */         
/* 273 */         for (int i = depth - 1; i >= 0; i--) {
/*     */           
/* 275 */           NameSpace ns = namespaces.elementAt(i);
/*     */           
/* 277 */           while (null != ns) {
/*     */             
/* 279 */             if (null != ns.m_prefix && prefix.equals(ns.m_prefix)) {
/*     */               
/* 281 */               namespace = ns.m_uri;
/* 282 */               i = -1;
/*     */               
/*     */               break;
/*     */             } 
/*     */             
/* 287 */             ns = ns.m_next;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 292 */       if (null == namespace)
/*     */       {
/* 294 */         throw new RuntimeException(
/* 295 */             XMLMessages.createXMLMessage("ER_PREFIX_MUST_RESOLVE", new Object[] { prefix }));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 301 */     this
/* 302 */       ._localName = (indexOfNSSep < 0) ? qname : qname.substring(indexOfNSSep + 1);
/*     */     
/* 304 */     if (validate)
/*     */     {
/* 306 */       if (this._localName == null || !XML11Char.isXML11ValidNCName(this._localName))
/*     */       {
/* 308 */         throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_INVALID", null));
/*     */       }
/*     */     }
/*     */     
/* 312 */     this._namespaceURI = namespace;
/* 313 */     this._prefix = prefix;
/* 314 */     this.m_hashCode = toString().hashCode();
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
/*     */   public QName(String qname, Element namespaceContext, PrefixResolver resolver) {
/* 329 */     this(qname, namespaceContext, resolver, false);
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
/*     */   public QName(String qname, Element namespaceContext, PrefixResolver resolver, boolean validate) {
/* 347 */     this._namespaceURI = null;
/*     */     
/* 349 */     int indexOfNSSep = qname.indexOf(':');
/*     */     
/* 351 */     if (indexOfNSSep > 0)
/*     */     {
/* 353 */       if (null != namespaceContext) {
/*     */         
/* 355 */         String prefix = qname.substring(0, indexOfNSSep);
/*     */         
/* 357 */         this._prefix = prefix;
/*     */         
/* 359 */         if (prefix.equals("xml")) {
/*     */           
/* 361 */           this._namespaceURI = "http://www.w3.org/XML/1998/namespace";
/*     */         }
/*     */         else {
/*     */           
/* 365 */           if (prefix.equals("xmlns")) {
/*     */             return;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 371 */           this._namespaceURI = resolver.getNamespaceForPrefix(prefix, namespaceContext);
/*     */         } 
/*     */ 
/*     */         
/* 375 */         if (null == this._namespaceURI)
/*     */         {
/* 377 */           throw new RuntimeException(
/* 378 */               XMLMessages.createXMLMessage("ER_PREFIX_MUST_RESOLVE", new Object[] { prefix }));
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
/* 390 */     this
/* 391 */       ._localName = (indexOfNSSep < 0) ? qname : qname.substring(indexOfNSSep + 1);
/*     */     
/* 393 */     if (validate)
/*     */     {
/* 395 */       if (this._localName == null || !XML11Char.isXML11ValidNCName(this._localName))
/*     */       {
/* 397 */         throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_INVALID", null));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 402 */     this.m_hashCode = toString().hashCode();
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
/*     */   public QName(String qname, PrefixResolver resolver) {
/* 416 */     this(qname, resolver, false);
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
/*     */   public QName(String qname, PrefixResolver resolver, boolean validate) {
/* 432 */     String prefix = null;
/* 433 */     this._namespaceURI = null;
/*     */     
/* 435 */     int indexOfNSSep = qname.indexOf(':');
/*     */     
/* 437 */     if (indexOfNSSep > 0) {
/*     */       
/* 439 */       prefix = qname.substring(0, indexOfNSSep);
/*     */       
/* 441 */       if (prefix.equals("xml")) {
/*     */         
/* 443 */         this._namespaceURI = "http://www.w3.org/XML/1998/namespace";
/*     */       }
/*     */       else {
/*     */         
/* 447 */         this._namespaceURI = resolver.getNamespaceForPrefix(prefix);
/*     */       } 
/*     */       
/* 450 */       if (null == this._namespaceURI)
/*     */       {
/* 452 */         throw new RuntimeException(
/* 453 */             XMLMessages.createXMLMessage("ER_PREFIX_MUST_RESOLVE", new Object[] { prefix }));
/*     */       }
/*     */ 
/*     */       
/* 457 */       this._localName = qname.substring(indexOfNSSep + 1);
/*     */     } else {
/* 459 */       if (indexOfNSSep == 0)
/*     */       {
/* 461 */         throw new RuntimeException(
/* 462 */             XMLMessages.createXMLMessage("ER_NAME_CANT_START_WITH_COLON", null));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 468 */       this._localName = qname;
/*     */     } 
/*     */     
/* 471 */     if (validate)
/*     */     {
/* 473 */       if (this._localName == null || !XML11Char.isXML11ValidNCName(this._localName))
/*     */       {
/* 475 */         throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_ARG_LOCALNAME_INVALID", null));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 481 */     this.m_hashCode = toString().hashCode();
/* 482 */     this._prefix = prefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespaceURI() {
/* 493 */     return this._namespaceURI;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/* 504 */     return this._prefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalName() {
/* 514 */     return this._localName;
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
/*     */   public String toString() {
/* 527 */     return (this._prefix != null) ? (this._prefix + ":" + this._localName) : ((this._namespaceURI != null) ? ("{" + this._namespaceURI + "}" + this._localName) : this._localName);
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
/*     */   public String toNamespacedString() {
/* 543 */     return (this._namespaceURI != null) ? ("{" + this._namespaceURI + "}" + this._localName) : this._localName;
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
/*     */   public String getNamespace() {
/* 555 */     return getNamespaceURI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalPart() {
/* 565 */     return getLocalName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 575 */     return this.m_hashCode;
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
/*     */   public boolean equals(String ns, String localPart) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: invokevirtual getNamespaceURI : ()Ljava/lang/String;
/*     */     //   4: astore_3
/*     */     //   5: aload_0
/*     */     //   6: invokevirtual getLocalName : ()Ljava/lang/String;
/*     */     //   9: aload_2
/*     */     //   10: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   13: ifeq -> 51
/*     */     //   16: aconst_null
/*     */     //   17: aload_3
/*     */     //   18: if_acmpeq -> 37
/*     */     //   21: aconst_null
/*     */     //   22: aload_1
/*     */     //   23: if_acmpeq -> 37
/*     */     //   26: aload_3
/*     */     //   27: aload_1
/*     */     //   28: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   31: ifeq -> 51
/*     */     //   34: goto -> 47
/*     */     //   37: aconst_null
/*     */     //   38: aload_3
/*     */     //   39: if_acmpne -> 51
/*     */     //   42: aconst_null
/*     */     //   43: aload_1
/*     */     //   44: if_acmpne -> 51
/*     */     //   47: iconst_1
/*     */     //   48: goto -> 52
/*     */     //   51: iconst_0
/*     */     //   52: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #591	-> 0
/*     */     //   #593	-> 5
/*     */     //   #595	-> 28
/*     */     //   #593	-> 52
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	53	0	this	Lcom/sun/org/apache/xml/internal/utils/QName;
/*     */     //   0	53	1	ns	Ljava/lang/String;
/*     */     //   0	53	2	localPart	Ljava/lang/String;
/*     */     //   5	48	3	thisnamespace	Ljava/lang/String;
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
/*     */   public boolean equals(Object object) {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: aload_0
/*     */     //   2: if_acmpne -> 7
/*     */     //   5: iconst_1
/*     */     //   6: ireturn
/*     */     //   7: aload_1
/*     */     //   8: instanceof com/sun/org/apache/xml/internal/utils/QName
/*     */     //   11: ifeq -> 84
/*     */     //   14: aload_1
/*     */     //   15: checkcast com/sun/org/apache/xml/internal/utils/QName
/*     */     //   18: astore_2
/*     */     //   19: aload_0
/*     */     //   20: invokevirtual getNamespaceURI : ()Ljava/lang/String;
/*     */     //   23: astore_3
/*     */     //   24: aload_2
/*     */     //   25: invokevirtual getNamespaceURI : ()Ljava/lang/String;
/*     */     //   28: astore #4
/*     */     //   30: aload_0
/*     */     //   31: invokevirtual getLocalName : ()Ljava/lang/String;
/*     */     //   34: aload_2
/*     */     //   35: invokevirtual getLocalName : ()Ljava/lang/String;
/*     */     //   38: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   41: ifeq -> 82
/*     */     //   44: aconst_null
/*     */     //   45: aload_3
/*     */     //   46: if_acmpeq -> 67
/*     */     //   49: aconst_null
/*     */     //   50: aload #4
/*     */     //   52: if_acmpeq -> 67
/*     */     //   55: aload_3
/*     */     //   56: aload #4
/*     */     //   58: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   61: ifeq -> 82
/*     */     //   64: goto -> 78
/*     */     //   67: aconst_null
/*     */     //   68: aload_3
/*     */     //   69: if_acmpne -> 82
/*     */     //   72: aconst_null
/*     */     //   73: aload #4
/*     */     //   75: if_acmpne -> 82
/*     */     //   78: iconst_1
/*     */     //   79: goto -> 83
/*     */     //   82: iconst_0
/*     */     //   83: ireturn
/*     */     //   84: iconst_0
/*     */     //   85: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #609	-> 0
/*     */     //   #610	-> 5
/*     */     //   #612	-> 7
/*     */     //   #613	-> 14
/*     */     //   #614	-> 19
/*     */     //   #615	-> 24
/*     */     //   #617	-> 30
/*     */     //   #619	-> 58
/*     */     //   #617	-> 83
/*     */     //   #623	-> 84
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   19	65	2	qname	Lcom/sun/org/apache/xml/internal/utils/QName;
/*     */     //   24	60	3	thisnamespace	Ljava/lang/String;
/*     */     //   30	54	4	thatnamespace	Ljava/lang/String;
/*     */     //   0	86	0	this	Lcom/sun/org/apache/xml/internal/utils/QName;
/*     */     //   0	86	1	object	Ljava/lang/Object;
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
/*     */   public static QName getQNameFromString(String name) {
/*     */     QName qname;
/* 637 */     StringTokenizer tokenizer = new StringTokenizer(name, "{}", false);
/*     */     
/* 639 */     String s1 = tokenizer.nextToken();
/* 640 */     String s2 = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
/*     */     
/* 642 */     if (null == s2) {
/* 643 */       qname = new QName(null, s1);
/*     */     } else {
/* 645 */       qname = new QName(s1, s2);
/*     */     } 
/* 647 */     return qname;
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
/*     */   public static boolean isXMLNSDecl(String attRawName) {
/* 661 */     return (attRawName.startsWith("xmlns") && (attRawName
/* 662 */       .equals("xmlns") || attRawName
/* 663 */       .startsWith("xmlns:")));
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
/*     */   public static String getPrefixFromXMLNSDecl(String attRawName) {
/* 677 */     int index = attRawName.indexOf(':');
/*     */     
/* 679 */     return (index >= 0) ? attRawName.substring(index + 1) : "";
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
/*     */   public static String getLocalPart(String qname) {
/* 692 */     int index = qname.indexOf(':');
/*     */     
/* 694 */     return (index < 0) ? qname : qname.substring(index + 1);
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
/*     */   public static String getPrefixPart(String qname) {
/* 707 */     int index = qname.indexOf(':');
/*     */     
/* 709 */     return (index >= 0) ? qname.substring(0, index) : "";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/utils/QName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */