/*     */ package com.sun.org.apache.xerces.internal.impl.xs.traversers;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.xs.SchemaGrammar;
/*     */ import com.sun.org.apache.xerces.internal.impl.xs.SchemaSymbols;
/*     */ import com.sun.org.apache.xerces.internal.impl.xs.XSAnnotationImpl;
/*     */ import com.sun.org.apache.xerces.internal.impl.xs.XSModelGroupImpl;
/*     */ import com.sun.org.apache.xerces.internal.impl.xs.XSParticleDecl;
/*     */ import com.sun.org.apache.xerces.internal.impl.xs.util.XInt;
/*     */ import com.sun.org.apache.xerces.internal.impl.xs.util.XSObjectListImpl;
/*     */ import com.sun.org.apache.xerces.internal.util.DOMUtil;
/*     */ import com.sun.org.apache.xerces.internal.xs.XSObject;
/*     */ import com.sun.org.apache.xerces.internal.xs.XSObjectList;
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
/*     */ abstract class XSDAbstractParticleTraverser
/*     */   extends XSDAbstractTraverser
/*     */ {
/*     */   ParticleArray fPArray;
/*     */   
/*     */   XSDAbstractParticleTraverser(XSDHandler handler, XSAttributeChecker gAttrCheck) {
/*  46 */     super(handler, gAttrCheck);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 384 */     this.fPArray = new ParticleArray();
/*     */   }
/*     */   
/*     */   XSParticleDecl traverseAll(Element allDecl, XSDocumentInfo schemaDoc, SchemaGrammar grammar, int allContextFlags, XSObject parent) {
/*     */     XSObjectList annotations;
/*     */     Object[] attrValues = this.fAttrChecker.checkAttributes(allDecl, false, schemaDoc);
/*     */     Element child = DOMUtil.getFirstChildElement(allDecl);
/*     */     XSAnnotationImpl annotation = null;
/*     */     if (child != null && DOMUtil.getLocalName(child).equals(SchemaSymbols.ELT_ANNOTATION)) {
/*     */       annotation = traverseAnnotationDecl(child, attrValues, false, schemaDoc);
/*     */       child = DOMUtil.getNextSiblingElement(child);
/*     */     } else {
/*     */       String text = DOMUtil.getSyntheticAnnotation(allDecl);
/*     */       if (text != null)
/*     */         annotation = traverseSyntheticAnnotation(allDecl, text, attrValues, false, schemaDoc); 
/*     */     } 
/*     */     String childName = null;
/*     */     this.fPArray.pushContext();
/*     */     for (; child != null; child = DOMUtil.getNextSiblingElement(child)) {
/*     */       XSParticleDecl xSParticleDecl = null;
/*     */       childName = DOMUtil.getLocalName(child);
/*     */       if (childName.equals(SchemaSymbols.ELT_ELEMENT)) {
/*     */         xSParticleDecl = this.fSchemaHandler.fElementTraverser.traverseLocal(child, schemaDoc, grammar, 1, parent);
/*     */       } else {
/*     */         Object[] args = { "all", "(annotation?, element*)", DOMUtil.getLocalName(child) };
/*     */         reportSchemaError("s4s-elt-must-match.1", args, child);
/*     */       } 
/*     */       if (xSParticleDecl != null)
/*     */         this.fPArray.addParticle(xSParticleDecl); 
/*     */     } 
/*     */     XSParticleDecl particle = null;
/*     */     XInt minAtt = (XInt)attrValues[XSAttributeChecker.ATTIDX_MINOCCURS];
/*     */     XInt maxAtt = (XInt)attrValues[XSAttributeChecker.ATTIDX_MAXOCCURS];
/*     */     Long defaultVals = (Long)attrValues[XSAttributeChecker.ATTIDX_FROMDEFAULT];
/*     */     XSModelGroupImpl group = new XSModelGroupImpl();
/*     */     group.fCompositor = 103;
/*     */     group.fParticleCount = this.fPArray.getParticleCount();
/*     */     group.fParticles = this.fPArray.popContext();
/*     */     if (annotation != null) {
/*     */       annotations = new XSObjectListImpl();
/*     */       ((XSObjectListImpl)annotations).addXSObject(annotation);
/*     */     } else {
/*     */       annotations = XSObjectListImpl.EMPTY_LIST;
/*     */     } 
/*     */     group.fAnnotations = annotations;
/*     */     particle = new XSParticleDecl();
/*     */     particle.fType = 3;
/*     */     particle.fMinOccurs = minAtt.intValue();
/*     */     particle.fMaxOccurs = maxAtt.intValue();
/*     */     particle.fValue = group;
/*     */     particle.fAnnotations = annotations;
/*     */     particle = checkOccurrences(particle, SchemaSymbols.ELT_ALL, (Element)allDecl.getParentNode(), allContextFlags, defaultVals.longValue());
/*     */     this.fAttrChecker.returnAttrArray(attrValues, schemaDoc);
/*     */     return particle;
/*     */   }
/*     */   
/*     */   XSParticleDecl traverseSequence(Element seqDecl, XSDocumentInfo schemaDoc, SchemaGrammar grammar, int allContextFlags, XSObject parent) {
/*     */     return traverseSeqChoice(seqDecl, schemaDoc, grammar, allContextFlags, false, parent);
/*     */   }
/*     */   
/*     */   XSParticleDecl traverseChoice(Element choiceDecl, XSDocumentInfo schemaDoc, SchemaGrammar grammar, int allContextFlags, XSObject parent) {
/*     */     return traverseSeqChoice(choiceDecl, schemaDoc, grammar, allContextFlags, true, parent);
/*     */   }
/*     */   
/*     */   private XSParticleDecl traverseSeqChoice(Element decl, XSDocumentInfo schemaDoc, SchemaGrammar grammar, int allContextFlags, boolean choice, XSObject parent) {
/*     */     XSObjectList annotations;
/*     */     Object[] attrValues = this.fAttrChecker.checkAttributes(decl, false, schemaDoc);
/*     */     Element child = DOMUtil.getFirstChildElement(decl);
/*     */     XSAnnotationImpl annotation = null;
/*     */     if (child != null && DOMUtil.getLocalName(child).equals(SchemaSymbols.ELT_ANNOTATION)) {
/*     */       annotation = traverseAnnotationDecl(child, attrValues, false, schemaDoc);
/*     */       child = DOMUtil.getNextSiblingElement(child);
/*     */     } else {
/*     */       String text = DOMUtil.getSyntheticAnnotation(decl);
/*     */       if (text != null)
/*     */         annotation = traverseSyntheticAnnotation(decl, text, attrValues, false, schemaDoc); 
/*     */     } 
/*     */     String childName = null;
/*     */     this.fPArray.pushContext();
/*     */     for (; child != null; child = DOMUtil.getNextSiblingElement(child)) {
/*     */       XSParticleDecl xSParticleDecl = null;
/*     */       childName = DOMUtil.getLocalName(child);
/*     */       if (childName.equals(SchemaSymbols.ELT_ELEMENT)) {
/*     */         xSParticleDecl = this.fSchemaHandler.fElementTraverser.traverseLocal(child, schemaDoc, grammar, 0, parent);
/*     */       } else if (childName.equals(SchemaSymbols.ELT_GROUP)) {
/*     */         xSParticleDecl = this.fSchemaHandler.fGroupTraverser.traverseLocal(child, schemaDoc, grammar);
/*     */         if (hasAllContent(xSParticleDecl)) {
/*     */           xSParticleDecl = null;
/*     */           reportSchemaError("cos-all-limited.1.2", null, child);
/*     */         } 
/*     */       } else if (childName.equals(SchemaSymbols.ELT_CHOICE)) {
/*     */         xSParticleDecl = traverseChoice(child, schemaDoc, grammar, 0, parent);
/*     */       } else if (childName.equals(SchemaSymbols.ELT_SEQUENCE)) {
/*     */         xSParticleDecl = traverseSequence(child, schemaDoc, grammar, 0, parent);
/*     */       } else if (childName.equals(SchemaSymbols.ELT_ANY)) {
/*     */         xSParticleDecl = this.fSchemaHandler.fWildCardTraverser.traverseAny(child, schemaDoc, grammar);
/*     */       } else {
/*     */         Object[] args;
/*     */         if (choice) {
/*     */           args = new Object[] { "choice", "(annotation?, (element | group | choice | sequence | any)*)", DOMUtil.getLocalName(child) };
/*     */         } else {
/*     */           args = new Object[] { "sequence", "(annotation?, (element | group | choice | sequence | any)*)", DOMUtil.getLocalName(child) };
/*     */         } 
/*     */         reportSchemaError("s4s-elt-must-match.1", args, child);
/*     */       } 
/*     */       if (xSParticleDecl != null)
/*     */         this.fPArray.addParticle(xSParticleDecl); 
/*     */     } 
/*     */     XSParticleDecl particle = null;
/*     */     XInt minAtt = (XInt)attrValues[XSAttributeChecker.ATTIDX_MINOCCURS];
/*     */     XInt maxAtt = (XInt)attrValues[XSAttributeChecker.ATTIDX_MAXOCCURS];
/*     */     Long defaultVals = (Long)attrValues[XSAttributeChecker.ATTIDX_FROMDEFAULT];
/*     */     XSModelGroupImpl group = new XSModelGroupImpl();
/*     */     group.fCompositor = choice ? 101 : 102;
/*     */     group.fParticleCount = this.fPArray.getParticleCount();
/*     */     group.fParticles = this.fPArray.popContext();
/*     */     if (annotation != null) {
/*     */       annotations = new XSObjectListImpl();
/*     */       ((XSObjectListImpl)annotations).addXSObject(annotation);
/*     */     } else {
/*     */       annotations = XSObjectListImpl.EMPTY_LIST;
/*     */     } 
/*     */     group.fAnnotations = annotations;
/*     */     particle = new XSParticleDecl();
/*     */     particle.fType = 3;
/*     */     particle.fMinOccurs = minAtt.intValue();
/*     */     particle.fMaxOccurs = maxAtt.intValue();
/*     */     particle.fValue = group;
/*     */     particle.fAnnotations = annotations;
/*     */     particle = checkOccurrences(particle, choice ? SchemaSymbols.ELT_CHOICE : SchemaSymbols.ELT_SEQUENCE, (Element)decl.getParentNode(), allContextFlags, defaultVals.longValue());
/*     */     this.fAttrChecker.returnAttrArray(attrValues, schemaDoc);
/*     */     return particle;
/*     */   }
/*     */   
/*     */   protected boolean hasAllContent(XSParticleDecl particle) {
/*     */     if (particle != null && particle.fType == 3)
/*     */       return (((XSModelGroupImpl)particle.fValue).fCompositor == 103); 
/*     */     return false;
/*     */   }
/*     */   
/*     */   protected static class ParticleArray {
/*     */     XSParticleDecl[] fParticles = new XSParticleDecl[10];
/*     */     int[] fPos = new int[5];
/*     */     int fContextCount = 0;
/*     */     
/*     */     void pushContext() {
/*     */       this.fContextCount++;
/*     */       if (this.fContextCount == this.fPos.length) {
/*     */         int newSize = this.fContextCount * 2;
/*     */         int[] newArray = new int[newSize];
/*     */         System.arraycopy(this.fPos, 0, newArray, 0, this.fContextCount);
/*     */         this.fPos = newArray;
/*     */       } 
/*     */       this.fPos[this.fContextCount] = this.fPos[this.fContextCount - 1];
/*     */     }
/*     */     
/*     */     int getParticleCount() {
/*     */       return this.fPos[this.fContextCount] - this.fPos[this.fContextCount - 1];
/*     */     }
/*     */     
/*     */     void addParticle(XSParticleDecl particle) {
/*     */       if (this.fPos[this.fContextCount] == this.fParticles.length) {
/*     */         int newSize = this.fPos[this.fContextCount] * 2;
/*     */         XSParticleDecl[] newArray = new XSParticleDecl[newSize];
/*     */         System.arraycopy(this.fParticles, 0, newArray, 0, this.fPos[this.fContextCount]);
/*     */         this.fParticles = newArray;
/*     */       } 
/*     */       this.fPos[this.fContextCount] = this.fPos[this.fContextCount] + 1;
/*     */       this.fParticles[this.fPos[this.fContextCount]] = particle;
/*     */     }
/*     */     
/*     */     XSParticleDecl[] popContext() {
/*     */       int count = this.fPos[this.fContextCount] - this.fPos[this.fContextCount - 1];
/*     */       XSParticleDecl[] array = null;
/*     */       if (count != 0) {
/*     */         array = new XSParticleDecl[count];
/*     */         System.arraycopy(this.fParticles, this.fPos[this.fContextCount - 1], array, 0, count);
/*     */         for (int i = this.fPos[this.fContextCount - 1]; i < this.fPos[this.fContextCount]; i++)
/*     */           this.fParticles[i] = null; 
/*     */       } 
/*     */       this.fContextCount--;
/*     */       return array;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xs/traversers/XSDAbstractParticleTraverser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */