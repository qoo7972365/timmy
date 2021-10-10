/*     */ package com.sun.org.apache.xalan.internal.xsltc.compiler;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*     */ import com.sun.org.apache.bcel.internal.generic.PUSH;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
/*     */ import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
/*     */ import com.sun.org.apache.xml.internal.serializer.ElemDesc;
/*     */ import com.sun.org.apache.xml.internal.serializer.ToHTMLStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class LiteralElement
/*     */   extends Instruction
/*     */ {
/*     */   private String _name;
/*  49 */   private LiteralElement _literalElemParent = null;
/*  50 */   private List<SyntaxTreeNode> _attributeElements = null;
/*  51 */   private Map<String, String> _accessedPrefixes = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean _allAttributesUnique = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getName() {
/*  62 */     return this._qname;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void display(int indent) {
/*  69 */     indent(indent);
/*  70 */     Util.println("LiteralElement name = " + this._name);
/*  71 */     displayContents(indent + 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String accessedNamespace(String prefix) {
/*  78 */     if (this._literalElemParent != null) {
/*  79 */       String result = this._literalElemParent.accessedNamespace(prefix);
/*  80 */       if (result != null) {
/*  81 */         return result;
/*     */       }
/*     */     } 
/*  84 */     return (this._accessedPrefixes != null) ? this._accessedPrefixes.get(prefix) : null;
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
/*     */   public void registerNamespace(String prefix, String uri, SymbolTable stable, boolean declared) {
/*  96 */     if (this._literalElemParent != null) {
/*  97 */       String parentUri = this._literalElemParent.accessedNamespace(prefix);
/*  98 */       if (parentUri != null && parentUri.equals(uri)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 104 */     if (this._accessedPrefixes == null) {
/* 105 */       this._accessedPrefixes = new Hashtable<>();
/*     */     
/*     */     }
/* 108 */     else if (!declared) {
/*     */       
/* 110 */       String old = this._accessedPrefixes.get(prefix);
/* 111 */       if (old != null) {
/* 112 */         if (old.equals(uri)) {
/*     */           return;
/*     */         }
/* 115 */         prefix = stable.generateNamespacePrefix();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 120 */     if (!prefix.equals("xml")) {
/* 121 */       this._accessedPrefixes.put(prefix, uri);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String translateQName(QName qname, SymbolTable stable) {
/* 132 */     String localname = qname.getLocalPart();
/* 133 */     String prefix = qname.getPrefix();
/*     */ 
/*     */     
/* 136 */     if (prefix == null) {
/* 137 */       prefix = "";
/* 138 */     } else if (prefix.equals("xmlns")) {
/* 139 */       return "xmlns";
/*     */     } 
/*     */     
/* 142 */     String alternative = stable.lookupPrefixAlias(prefix);
/* 143 */     if (alternative != null) {
/* 144 */       stable.excludeNamespaces(prefix);
/* 145 */       prefix = alternative;
/*     */     } 
/*     */ 
/*     */     
/* 149 */     String uri = lookupNamespace(prefix);
/* 150 */     if (uri == null) return localname;
/*     */ 
/*     */     
/* 153 */     registerNamespace(prefix, uri, stable, false);
/*     */ 
/*     */     
/* 156 */     if (prefix != "") {
/* 157 */       return prefix + ":" + localname;
/*     */     }
/* 159 */     return localname;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAttribute(SyntaxTreeNode attribute) {
/* 166 */     if (this._attributeElements == null) {
/* 167 */       this._attributeElements = new ArrayList<>(2);
/*     */     }
/* 169 */     this._attributeElements.add(attribute);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFirstAttribute(SyntaxTreeNode attribute) {
/* 176 */     if (this._attributeElements == null) {
/* 177 */       this._attributeElements = new ArrayList<>(2);
/*     */     }
/* 179 */     this._attributeElements.add(0, attribute);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
/* 188 */     if (this._attributeElements != null) {
/* 189 */       for (SyntaxTreeNode node : this._attributeElements) {
/* 190 */         node.typeCheck(stable);
/*     */       }
/*     */     }
/* 193 */     typeCheckContents(stable);
/* 194 */     return Type.Void;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<String, String>> getNamespaceScope(SyntaxTreeNode node) {
/* 203 */     Map<String, String> all = new HashMap<>();
/*     */     
/* 205 */     while (node != null) {
/* 206 */       Map<String, String> mapping = node.getPrefixMapping();
/* 207 */       if (mapping != null) {
/* 208 */         for (String prefix : mapping.keySet()) {
/* 209 */           if (!all.containsKey(prefix)) {
/* 210 */             all.put(prefix, mapping.get(prefix));
/*     */           }
/*     */         } 
/*     */       }
/* 214 */       node = node.getParent();
/*     */     } 
/* 216 */     return all.entrySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parseContents(Parser parser) {
/* 224 */     SymbolTable stable = parser.getSymbolTable();
/* 225 */     stable.setCurrentNode(this);
/*     */ 
/*     */     
/* 228 */     SyntaxTreeNode parent = getParent();
/* 229 */     if (parent != null && parent instanceof LiteralElement) {
/* 230 */       this._literalElemParent = (LiteralElement)parent;
/*     */     }
/*     */     
/* 233 */     this._name = translateQName(this._qname, stable);
/*     */ 
/*     */     
/* 236 */     int count = this._attributes.getLength();
/* 237 */     for (int i = 0; i < count; i++) {
/* 238 */       QName qname = parser.getQName(this._attributes.getQName(i));
/* 239 */       String uri = qname.getNamespace();
/* 240 */       String val = this._attributes.getValue(i);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 245 */       if (qname.equals(parser.getUseAttributeSets())) {
/* 246 */         if (!Util.isValidQNames(val)) {
/* 247 */           ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", val, this);
/* 248 */           parser.reportError(3, err);
/*     */         } 
/* 250 */         setFirstAttribute(new UseAttributeSets(val, parser));
/*     */       
/*     */       }
/* 253 */       else if (qname.equals(parser.getExtensionElementPrefixes())) {
/* 254 */         stable.excludeNamespaces(val);
/*     */       
/*     */       }
/* 257 */       else if (qname.equals(parser.getExcludeResultPrefixes())) {
/* 258 */         stable.excludeNamespaces(val);
/*     */       }
/*     */       else {
/*     */         
/* 262 */         String prefix = qname.getPrefix();
/* 263 */         if ((prefix == null || !prefix.equals("xmlns")) && (prefix != null || 
/* 264 */           !qname.getLocalPart().equals("xmlns")) && (uri == null || 
/* 265 */           !uri.equals("http://www.w3.org/1999/XSL/Transform"))) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 271 */           String name = translateQName(qname, stable);
/* 272 */           LiteralAttribute attr = new LiteralAttribute(name, val, parser, this);
/* 273 */           addAttribute(attr);
/* 274 */           attr.setParent(this);
/* 275 */           attr.parseContents(parser);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 281 */     Set<Map.Entry<String, String>> include = getNamespaceScope(this);
/* 282 */     for (Map.Entry<String, String> entry : include) {
/* 283 */       String prefix = entry.getKey();
/* 284 */       if (!prefix.equals("xml")) {
/* 285 */         String uri = lookupNamespace(prefix);
/* 286 */         if (uri != null && !stable.isExcludedNamespace(uri)) {
/* 287 */           registerNamespace(prefix, uri, stable, true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 292 */     parseChildren(parser);
/*     */ 
/*     */     
/* 295 */     for (int j = 0; j < count; j++) {
/* 296 */       QName qname = parser.getQName(this._attributes.getQName(j));
/* 297 */       String val = this._attributes.getValue(j);
/*     */ 
/*     */       
/* 300 */       if (qname.equals(parser.getExtensionElementPrefixes())) {
/* 301 */         stable.unExcludeNamespaces(val);
/*     */       
/*     */       }
/* 304 */       else if (qname.equals(parser.getExcludeResultPrefixes())) {
/* 305 */         stable.unExcludeNamespaces(val);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean contextDependent() {
/* 311 */     return dependentContents();
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
/*     */   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
/* 323 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 324 */     InstructionList il = methodGen.getInstructionList();
/*     */ 
/*     */     
/* 327 */     this._allAttributesUnique = checkAttributesUnique();
/*     */ 
/*     */     
/* 330 */     il.append(methodGen.loadHandler());
/*     */     
/* 332 */     il.append(new PUSH(cpg, this._name));
/* 333 */     il.append(DUP2);
/* 334 */     il.append(methodGen.startElement());
/*     */ 
/*     */     
/* 337 */     int j = 0;
/* 338 */     while (j < elementCount()) {
/* 339 */       SyntaxTreeNode item = elementAt(j);
/* 340 */       if (item instanceof Variable) {
/* 341 */         item.translate(classGen, methodGen);
/*     */       }
/* 343 */       j++;
/*     */     } 
/*     */ 
/*     */     
/* 347 */     if (this._accessedPrefixes != null) {
/* 348 */       for (Map.Entry<String, String> entry : this._accessedPrefixes.entrySet()) {
/* 349 */         String prefix = entry.getKey();
/* 350 */         String uri = entry.getValue();
/* 351 */         il.append(methodGen.loadHandler());
/* 352 */         il.append(new PUSH(cpg, prefix));
/* 353 */         il.append(new PUSH(cpg, uri));
/* 354 */         il.append(methodGen.namespace());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 359 */     if (this._attributeElements != null) {
/* 360 */       for (SyntaxTreeNode node : this._attributeElements) {
/* 361 */         if (!(node instanceof XslAttribute)) {
/* 362 */           node.translate(classGen, methodGen);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 368 */     translateContents(classGen, methodGen);
/*     */ 
/*     */     
/* 371 */     il.append(methodGen.endElement());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isHTMLOutput() {
/* 378 */     return (getStylesheet().getOutputMethod() == 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ElemDesc getElemDesc() {
/* 387 */     if (isHTMLOutput()) {
/* 388 */       return ToHTMLStream.getElemDesc(this._name);
/*     */     }
/*     */     
/* 391 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean allAttributesUnique() {
/* 398 */     return this._allAttributesUnique;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkAttributesUnique() {
/* 405 */     boolean hasHiddenXslAttribute = canProduceAttributeNodes(this, true);
/* 406 */     if (hasHiddenXslAttribute) {
/* 407 */       return false;
/*     */     }
/* 409 */     if (this._attributeElements != null) {
/* 410 */       int numAttrs = this._attributeElements.size();
/* 411 */       Map<String, SyntaxTreeNode> attrsTable = null;
/* 412 */       for (int i = 0; i < numAttrs; i++) {
/* 413 */         SyntaxTreeNode node = this._attributeElements.get(i);
/*     */         
/* 415 */         if (node instanceof UseAttributeSets) {
/* 416 */           return false;
/*     */         }
/* 418 */         if (node instanceof XslAttribute) {
/* 419 */           if (attrsTable == null) {
/* 420 */             attrsTable = new HashMap<>();
/* 421 */             for (int k = 0; k < i; k++) {
/* 422 */               SyntaxTreeNode n = this._attributeElements.get(k);
/* 423 */               if (n instanceof LiteralAttribute) {
/* 424 */                 LiteralAttribute literalAttr = (LiteralAttribute)n;
/* 425 */                 attrsTable.put(literalAttr.getName(), literalAttr);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 430 */           XslAttribute xslAttr = (XslAttribute)node;
/* 431 */           AttributeValue attrName = xslAttr.getName();
/* 432 */           if (attrName instanceof AttributeValueTemplate) {
/* 433 */             return false;
/*     */           }
/* 435 */           if (attrName instanceof SimpleAttributeValue) {
/* 436 */             SimpleAttributeValue simpleAttr = (SimpleAttributeValue)attrName;
/* 437 */             String name = simpleAttr.toString();
/* 438 */             if (name != null && attrsTable.get(name) != null)
/* 439 */               return false; 
/* 440 */             if (name != null) {
/* 441 */               attrsTable.put(name, xslAttr);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 447 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canProduceAttributeNodes(SyntaxTreeNode node, boolean ignoreXslAttribute) {
/* 457 */     List<SyntaxTreeNode> contents = node.getContents();
/* 458 */     for (SyntaxTreeNode child : contents) {
/* 459 */       if (child instanceof Text) {
/* 460 */         Text text = (Text)child;
/* 461 */         if (text.isIgnore()) {
/*     */           continue;
/*     */         }
/* 464 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 468 */       if (child instanceof LiteralElement || child instanceof ValueOf || child instanceof XslElement || child instanceof Comment || child instanceof Number || child instanceof ProcessingInstruction)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 474 */         return false; } 
/* 475 */       if (child instanceof XslAttribute) {
/* 476 */         if (ignoreXslAttribute) {
/*     */           continue;
/*     */         }
/* 479 */         return true;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 485 */       if (child instanceof CallTemplate || child instanceof ApplyTemplates || child instanceof Copy || child instanceof CopyOf)
/*     */       {
/*     */ 
/*     */         
/* 489 */         return true; } 
/* 490 */       if ((child instanceof If || child instanceof ForEach) && 
/*     */         
/* 492 */         canProduceAttributeNodes(child, false)) {
/* 493 */         return true;
/*     */       }
/* 495 */       if (child instanceof Choose) {
/* 496 */         List<SyntaxTreeNode> chooseContents = child.getContents();
/* 497 */         for (SyntaxTreeNode chooseChild : chooseContents) {
/* 498 */           if ((chooseChild instanceof When || chooseChild instanceof Otherwise) && 
/* 499 */             canProduceAttributeNodes(chooseChild, false)) {
/* 500 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 505 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/LiteralElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */