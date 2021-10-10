/*      */ package com.sun.org.apache.xerces.internal.impl.xs;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.impl.dv.SchemaDVFactory;
/*      */ import com.sun.org.apache.xerces.internal.impl.dv.ValidatedInfo;
/*      */ import com.sun.org.apache.xerces.internal.impl.dv.XSSimpleType;
/*      */ import com.sun.org.apache.xerces.internal.impl.dv.xs.XSSimpleTypeDecl;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.identity.IdentityConstraint;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.util.ObjectListImpl;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.util.SimpleLocator;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.util.StringListImpl;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.util.XSNamedMap4Types;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.util.XSNamedMapImpl;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.util.XSObjectListImpl;
/*      */ import com.sun.org.apache.xerces.internal.parsers.DOMParser;
/*      */ import com.sun.org.apache.xerces.internal.parsers.SAXParser;
/*      */ import com.sun.org.apache.xerces.internal.parsers.XML11Configuration;
/*      */ import com.sun.org.apache.xerces.internal.util.SymbolHash;
/*      */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*      */ import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
/*      */ import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarDescription;
/*      */ import com.sun.org.apache.xerces.internal.xni.grammars.XSGrammar;
/*      */ import com.sun.org.apache.xerces.internal.xs.StringList;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSAnnotation;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSAttributeDeclaration;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSAttributeGroupDefinition;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSElementDeclaration;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSModel;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSModelGroupDefinition;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSNamedMap;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSNamespaceItem;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSNotationDeclaration;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSObject;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSObjectList;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSParticle;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSTypeDefinition;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSWildcard;
/*      */ import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.util.Vector;
/*      */ import org.xml.sax.SAXException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SchemaGrammar
/*      */   implements XSGrammar, XSNamespaceItem
/*      */ {
/*      */   String fTargetNamespace;
/*      */   SymbolHash fGlobalAttrDecls;
/*      */   SymbolHash fGlobalAttrGrpDecls;
/*      */   SymbolHash fGlobalElemDecls;
/*      */   SymbolHash fGlobalGroupDecls;
/*      */   SymbolHash fGlobalNotationDecls;
/*      */   SymbolHash fGlobalIDConstraintDecls;
/*      */   SymbolHash fGlobalTypeDecls;
/*      */   SymbolHash fGlobalAttrDeclsExt;
/*      */   SymbolHash fGlobalAttrGrpDeclsExt;
/*      */   SymbolHash fGlobalElemDeclsExt;
/*      */   SymbolHash fGlobalGroupDeclsExt;
/*      */   SymbolHash fGlobalNotationDeclsExt;
/*      */   SymbolHash fGlobalIDConstraintDeclsExt;
/*      */   SymbolHash fGlobalTypeDeclsExt;
/*      */   SymbolHash fAllGlobalElemDecls;
/*  111 */   XSDDescription fGrammarDescription = null;
/*      */ 
/*      */   
/*  114 */   XSAnnotationImpl[] fAnnotations = null;
/*      */ 
/*      */   
/*      */   int fNumAnnotations;
/*      */ 
/*      */   
/*  120 */   private SymbolTable fSymbolTable = null;
/*      */   
/*  122 */   private SoftReference fSAXParser = null;
/*  123 */   private SoftReference fDOMParser = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean fIsImmutable = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int BASICSET_COUNT = 29;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int FULLSET_COUNT = 46;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int GRAMMAR_XS = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int GRAMMAR_XSI = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Vector fImported;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int INITIAL_SIZE = 16;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int INC_SIZE = 16;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int fCTCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private XSComplexTypeDecl[] fComplexTypeDecls;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SimpleLocator[] fCTLocators;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int REDEFINED_GROUP_INIT_SIZE = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int fRGCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private XSGroupDecl[] fRedefinedGroupDecls;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SimpleLocator[] fRGLocators;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean fFullChecked;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int fSubGroupCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private XSElementDecl[] fSubGroups;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class BuiltinSchemaGrammar
/*      */     extends SchemaGrammar
/*      */   {
/*      */     private static final String EXTENDED_SCHEMA_FACTORY_CLASS = "com.sun.org.apache.xerces.internal.impl.dv.xs.ExtendedSchemaDVFactoryImpl";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public BuiltinSchemaGrammar(int grammar, short schemaVersion) {
/*      */       SchemaDVFactory schemaFactory;
/*  277 */       if (schemaVersion == 1) {
/*  278 */         schemaFactory = SchemaDVFactory.getInstance();
/*      */       } else {
/*      */         
/*  281 */         schemaFactory = SchemaDVFactory.getInstance("com.sun.org.apache.xerces.internal.impl.dv.xs.ExtendedSchemaDVFactoryImpl");
/*      */       } 
/*      */       
/*  284 */       if (grammar == 1) {
/*      */         
/*  286 */         this.fTargetNamespace = SchemaSymbols.URI_SCHEMAFORSCHEMA;
/*      */ 
/*      */         
/*  289 */         this.fGrammarDescription = new XSDDescription();
/*  290 */         this.fGrammarDescription.fContextType = 3;
/*  291 */         this.fGrammarDescription.setNamespace(SchemaSymbols.URI_SCHEMAFORSCHEMA);
/*      */ 
/*      */         
/*  294 */         this.fGlobalAttrDecls = new SymbolHash(1);
/*  295 */         this.fGlobalAttrGrpDecls = new SymbolHash(1);
/*  296 */         this.fGlobalElemDecls = new SymbolHash(1);
/*  297 */         this.fGlobalGroupDecls = new SymbolHash(1);
/*  298 */         this.fGlobalNotationDecls = new SymbolHash(1);
/*  299 */         this.fGlobalIDConstraintDecls = new SymbolHash(1);
/*      */ 
/*      */         
/*  302 */         this.fGlobalAttrDeclsExt = new SymbolHash(1);
/*  303 */         this.fGlobalAttrGrpDeclsExt = new SymbolHash(1);
/*  304 */         this.fGlobalElemDeclsExt = new SymbolHash(1);
/*  305 */         this.fGlobalGroupDeclsExt = new SymbolHash(1);
/*  306 */         this.fGlobalNotationDeclsExt = new SymbolHash(1);
/*  307 */         this.fGlobalIDConstraintDeclsExt = new SymbolHash(1);
/*  308 */         this.fGlobalTypeDeclsExt = new SymbolHash(1);
/*      */ 
/*      */         
/*  311 */         this.fAllGlobalElemDecls = new SymbolHash(1);
/*      */ 
/*      */         
/*  314 */         this.fGlobalTypeDecls = schemaFactory.getBuiltInTypes();
/*      */ 
/*      */ 
/*      */         
/*  318 */         int length = this.fGlobalTypeDecls.getLength();
/*  319 */         XSTypeDefinition[] typeDefinitions = new XSTypeDefinition[length];
/*  320 */         this.fGlobalTypeDecls.getValues((Object[])typeDefinitions, 0);
/*  321 */         for (int i = 0; i < length; i++) {
/*  322 */           XSTypeDefinition xtd = typeDefinitions[i];
/*  323 */           if (xtd instanceof XSSimpleTypeDecl) {
/*  324 */             ((XSSimpleTypeDecl)xtd).setNamespaceItem(this);
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/*  329 */         this.fGlobalTypeDecls.put(fAnyType.getName(), fAnyType);
/*      */       }
/*  331 */       else if (grammar == 2) {
/*      */         
/*  333 */         this.fTargetNamespace = SchemaSymbols.URI_XSI;
/*      */         
/*  335 */         this.fGrammarDescription = new XSDDescription();
/*  336 */         this.fGrammarDescription.fContextType = 3;
/*  337 */         this.fGrammarDescription.setNamespace(SchemaSymbols.URI_XSI);
/*      */ 
/*      */         
/*  340 */         this.fGlobalAttrGrpDecls = new SymbolHash(1);
/*  341 */         this.fGlobalElemDecls = new SymbolHash(1);
/*  342 */         this.fGlobalGroupDecls = new SymbolHash(1);
/*  343 */         this.fGlobalNotationDecls = new SymbolHash(1);
/*  344 */         this.fGlobalIDConstraintDecls = new SymbolHash(1);
/*  345 */         this.fGlobalTypeDecls = new SymbolHash(1);
/*      */ 
/*      */         
/*  348 */         this.fGlobalAttrDeclsExt = new SymbolHash(1);
/*  349 */         this.fGlobalAttrGrpDeclsExt = new SymbolHash(1);
/*  350 */         this.fGlobalElemDeclsExt = new SymbolHash(1);
/*  351 */         this.fGlobalGroupDeclsExt = new SymbolHash(1);
/*  352 */         this.fGlobalNotationDeclsExt = new SymbolHash(1);
/*  353 */         this.fGlobalIDConstraintDeclsExt = new SymbolHash(1);
/*  354 */         this.fGlobalTypeDeclsExt = new SymbolHash(1);
/*      */ 
/*      */         
/*  357 */         this.fAllGlobalElemDecls = new SymbolHash(1);
/*      */ 
/*      */         
/*  360 */         this.fGlobalAttrDecls = new SymbolHash(8);
/*  361 */         String name = null;
/*  362 */         String tns = null;
/*  363 */         XSSimpleType type = null;
/*  364 */         short scope = 1;
/*      */ 
/*      */         
/*  367 */         name = SchemaSymbols.XSI_TYPE;
/*  368 */         tns = SchemaSymbols.URI_XSI;
/*  369 */         type = schemaFactory.getBuiltInType("QName");
/*  370 */         this.fGlobalAttrDecls.put(name, new SchemaGrammar.BuiltinAttrDecl(name, tns, type, scope));
/*      */ 
/*      */         
/*  373 */         name = SchemaSymbols.XSI_NIL;
/*  374 */         tns = SchemaSymbols.URI_XSI;
/*  375 */         type = schemaFactory.getBuiltInType("boolean");
/*  376 */         this.fGlobalAttrDecls.put(name, new SchemaGrammar.BuiltinAttrDecl(name, tns, type, scope));
/*      */         
/*  378 */         XSSimpleType anyURI = schemaFactory.getBuiltInType("anyURI");
/*      */ 
/*      */         
/*  381 */         name = SchemaSymbols.XSI_SCHEMALOCATION;
/*  382 */         tns = SchemaSymbols.URI_XSI;
/*  383 */         type = schemaFactory.createTypeList("#AnonType_schemaLocation", SchemaSymbols.URI_XSI, (short)0, anyURI, null);
/*  384 */         if (type instanceof XSSimpleTypeDecl) {
/*  385 */           ((XSSimpleTypeDecl)type).setAnonymous(true);
/*      */         }
/*  387 */         this.fGlobalAttrDecls.put(name, new SchemaGrammar.BuiltinAttrDecl(name, tns, type, scope));
/*      */ 
/*      */         
/*  390 */         name = SchemaSymbols.XSI_NONAMESPACESCHEMALOCATION;
/*  391 */         tns = SchemaSymbols.URI_XSI;
/*  392 */         type = anyURI;
/*  393 */         this.fGlobalAttrDecls.put(name, new SchemaGrammar.BuiltinAttrDecl(name, tns, type, scope));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public XMLGrammarDescription getGrammarDescription() {
/*  400 */       return this.fGrammarDescription.makeClone();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setImportedGrammars(Vector importedGrammars) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void addGlobalAttributeDecl(XSAttributeDecl decl) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void addGlobalAttributeDecl(XSAttributeDecl decl, String location) {}
/*      */ 
/*      */     
/*      */     public void addGlobalAttributeGroupDecl(XSAttributeGroupDecl decl) {}
/*      */ 
/*      */     
/*      */     public void addGlobalAttributeGroupDecl(XSAttributeGroupDecl decl, String location) {}
/*      */ 
/*      */     
/*      */     public void addGlobalElementDecl(XSElementDecl decl) {}
/*      */ 
/*      */     
/*      */     public void addGlobalElementDecl(XSElementDecl decl, String location) {}
/*      */ 
/*      */     
/*      */     public void addGlobalElementDeclAll(XSElementDecl decl) {}
/*      */ 
/*      */     
/*      */     public void addGlobalGroupDecl(XSGroupDecl decl) {}
/*      */ 
/*      */     
/*      */     public void addGlobalGroupDecl(XSGroupDecl decl, String location) {}
/*      */ 
/*      */     
/*      */     public void addGlobalNotationDecl(XSNotationDecl decl) {}
/*      */ 
/*      */     
/*      */     public void addGlobalNotationDecl(XSNotationDecl decl, String location) {}
/*      */ 
/*      */     
/*      */     public void addGlobalTypeDecl(XSTypeDefinition decl) {}
/*      */ 
/*      */     
/*      */     public void addGlobalTypeDecl(XSTypeDefinition decl, String location) {}
/*      */ 
/*      */     
/*      */     public void addGlobalComplexTypeDecl(XSComplexTypeDecl decl) {}
/*      */ 
/*      */     
/*      */     public void addGlobalComplexTypeDecl(XSComplexTypeDecl decl, String location) {}
/*      */ 
/*      */     
/*      */     public void addGlobalSimpleTypeDecl(XSSimpleType decl) {}
/*      */ 
/*      */     
/*      */     public void addGlobalSimpleTypeDecl(XSSimpleType decl, String location) {}
/*      */ 
/*      */     
/*      */     public void addComplexTypeDecl(XSComplexTypeDecl decl, SimpleLocator locator) {}
/*      */ 
/*      */     
/*      */     public void addRedefinedGroupDecl(XSGroupDecl derived, XSGroupDecl base, SimpleLocator locator) {}
/*      */ 
/*      */     
/*      */     public synchronized void addDocument(Object document, String location) {}
/*      */ 
/*      */     
/*      */     synchronized DOMParser getDOMParser() {
/*  471 */       return null;
/*      */     }
/*      */     synchronized SAXParser getSAXParser() {
/*  474 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class Schema4Annotations
/*      */     extends SchemaGrammar
/*      */   {
/*  490 */     public static final Schema4Annotations INSTANCE = new Schema4Annotations();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Schema4Annotations() {
/*  499 */       this.fTargetNamespace = SchemaSymbols.URI_SCHEMAFORSCHEMA;
/*      */ 
/*      */       
/*  502 */       this.fGrammarDescription = new XSDDescription();
/*  503 */       this.fGrammarDescription.fContextType = 3;
/*  504 */       this.fGrammarDescription.setNamespace(SchemaSymbols.URI_SCHEMAFORSCHEMA);
/*      */ 
/*      */ 
/*      */       
/*  508 */       this.fGlobalAttrDecls = new SymbolHash(1);
/*  509 */       this.fGlobalAttrGrpDecls = new SymbolHash(1);
/*  510 */       this.fGlobalElemDecls = new SymbolHash(6);
/*  511 */       this.fGlobalGroupDecls = new SymbolHash(1);
/*  512 */       this.fGlobalNotationDecls = new SymbolHash(1);
/*  513 */       this.fGlobalIDConstraintDecls = new SymbolHash(1);
/*      */ 
/*      */       
/*  516 */       this.fGlobalAttrDeclsExt = new SymbolHash(1);
/*  517 */       this.fGlobalAttrGrpDeclsExt = new SymbolHash(1);
/*  518 */       this.fGlobalElemDeclsExt = new SymbolHash(6);
/*  519 */       this.fGlobalGroupDeclsExt = new SymbolHash(1);
/*  520 */       this.fGlobalNotationDeclsExt = new SymbolHash(1);
/*  521 */       this.fGlobalIDConstraintDeclsExt = new SymbolHash(1);
/*  522 */       this.fGlobalTypeDeclsExt = new SymbolHash(1);
/*      */ 
/*      */       
/*  525 */       this.fAllGlobalElemDecls = new SymbolHash(6);
/*      */ 
/*      */       
/*  528 */       this.fGlobalTypeDecls = SG_SchemaNS.fGlobalTypeDecls;
/*      */ 
/*      */       
/*  531 */       XSElementDecl annotationDecl = createAnnotationElementDecl(SchemaSymbols.ELT_ANNOTATION);
/*  532 */       XSElementDecl documentationDecl = createAnnotationElementDecl(SchemaSymbols.ELT_DOCUMENTATION);
/*  533 */       XSElementDecl appinfoDecl = createAnnotationElementDecl(SchemaSymbols.ELT_APPINFO);
/*      */ 
/*      */       
/*  536 */       this.fGlobalElemDecls.put(annotationDecl.fName, annotationDecl);
/*  537 */       this.fGlobalElemDecls.put(documentationDecl.fName, documentationDecl);
/*  538 */       this.fGlobalElemDecls.put(appinfoDecl.fName, appinfoDecl);
/*      */       
/*  540 */       this.fGlobalElemDeclsExt.put("," + annotationDecl.fName, annotationDecl);
/*  541 */       this.fGlobalElemDeclsExt.put("," + documentationDecl.fName, documentationDecl);
/*  542 */       this.fGlobalElemDeclsExt.put("," + appinfoDecl.fName, appinfoDecl);
/*      */       
/*  544 */       this.fAllGlobalElemDecls.put(annotationDecl, annotationDecl);
/*  545 */       this.fAllGlobalElemDecls.put(documentationDecl, documentationDecl);
/*  546 */       this.fAllGlobalElemDecls.put(appinfoDecl, appinfoDecl);
/*      */ 
/*      */       
/*  549 */       XSComplexTypeDecl annotationType = new XSComplexTypeDecl();
/*  550 */       XSComplexTypeDecl documentationType = new XSComplexTypeDecl();
/*  551 */       XSComplexTypeDecl appinfoType = new XSComplexTypeDecl();
/*      */ 
/*      */       
/*  554 */       annotationDecl.fType = annotationType;
/*  555 */       documentationDecl.fType = documentationType;
/*  556 */       appinfoDecl.fType = appinfoType;
/*      */ 
/*      */       
/*  559 */       XSAttributeGroupDecl annotationAttrs = new XSAttributeGroupDecl();
/*  560 */       XSAttributeGroupDecl documentationAttrs = new XSAttributeGroupDecl();
/*  561 */       XSAttributeGroupDecl appinfoAttrs = new XSAttributeGroupDecl();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  566 */       XSAttributeUseImpl annotationIDAttr = new XSAttributeUseImpl();
/*  567 */       annotationIDAttr.fAttrDecl = new XSAttributeDecl();
/*  568 */       annotationIDAttr.fAttrDecl.setValues(SchemaSymbols.ATT_ID, null, (XSSimpleType)this.fGlobalTypeDecls.get("ID"), (short)0, (short)2, null, annotationType, null);
/*      */       
/*  570 */       annotationIDAttr.fUse = 0;
/*  571 */       annotationIDAttr.fConstraintType = 0;
/*      */       
/*  573 */       XSAttributeUseImpl documentationSourceAttr = new XSAttributeUseImpl();
/*  574 */       documentationSourceAttr.fAttrDecl = new XSAttributeDecl();
/*  575 */       documentationSourceAttr.fAttrDecl.setValues(SchemaSymbols.ATT_SOURCE, null, (XSSimpleType)this.fGlobalTypeDecls.get("anyURI"), (short)0, (short)2, null, documentationType, null);
/*      */       
/*  577 */       documentationSourceAttr.fUse = 0;
/*  578 */       documentationSourceAttr.fConstraintType = 0;
/*      */       
/*  580 */       XSAttributeUseImpl documentationLangAttr = new XSAttributeUseImpl();
/*  581 */       documentationLangAttr.fAttrDecl = new XSAttributeDecl();
/*  582 */       documentationLangAttr.fAttrDecl.setValues("lang".intern(), NamespaceContext.XML_URI, (XSSimpleType)this.fGlobalTypeDecls.get("language"), (short)0, (short)2, null, documentationType, null);
/*      */       
/*  584 */       documentationLangAttr.fUse = 0;
/*  585 */       documentationLangAttr.fConstraintType = 0;
/*      */       
/*  587 */       XSAttributeUseImpl appinfoSourceAttr = new XSAttributeUseImpl();
/*  588 */       appinfoSourceAttr.fAttrDecl = new XSAttributeDecl();
/*  589 */       appinfoSourceAttr.fAttrDecl.setValues(SchemaSymbols.ATT_SOURCE, null, (XSSimpleType)this.fGlobalTypeDecls.get("anyURI"), (short)0, (short)2, null, appinfoType, null);
/*      */       
/*  591 */       appinfoSourceAttr.fUse = 0;
/*  592 */       appinfoSourceAttr.fConstraintType = 0;
/*      */ 
/*      */       
/*  595 */       XSWildcardDecl otherAttrs = new XSWildcardDecl();
/*  596 */       otherAttrs.fNamespaceList = new String[] { this.fTargetNamespace, null };
/*  597 */       otherAttrs.fType = 2;
/*  598 */       otherAttrs.fProcessContents = 3;
/*      */ 
/*      */       
/*  601 */       annotationAttrs.addAttributeUse(annotationIDAttr);
/*  602 */       annotationAttrs.fAttributeWC = otherAttrs;
/*      */       
/*  604 */       documentationAttrs.addAttributeUse(documentationSourceAttr);
/*  605 */       documentationAttrs.addAttributeUse(documentationLangAttr);
/*  606 */       documentationAttrs.fAttributeWC = otherAttrs;
/*      */       
/*  608 */       appinfoAttrs.addAttributeUse(appinfoSourceAttr);
/*  609 */       appinfoAttrs.fAttributeWC = otherAttrs;
/*      */ 
/*      */ 
/*      */       
/*  613 */       XSParticleDecl annotationParticle = createUnboundedModelGroupParticle();
/*      */       
/*  615 */       XSModelGroupImpl annotationChoice = new XSModelGroupImpl();
/*  616 */       annotationChoice.fCompositor = 101;
/*  617 */       annotationChoice.fParticleCount = 2;
/*  618 */       annotationChoice.fParticles = new XSParticleDecl[2];
/*  619 */       annotationChoice.fParticles[0] = createChoiceElementParticle(appinfoDecl);
/*  620 */       annotationChoice.fParticles[1] = createChoiceElementParticle(documentationDecl);
/*  621 */       annotationParticle.fValue = annotationChoice;
/*      */ 
/*      */ 
/*      */       
/*  625 */       XSParticleDecl anyWCSequenceParticle = createUnboundedAnyWildcardSequenceParticle();
/*      */ 
/*      */       
/*  628 */       annotationType.setValues("#AnonType_" + SchemaSymbols.ELT_ANNOTATION, this.fTargetNamespace, SchemaGrammar.fAnyType, (short)2, (short)0, (short)3, (short)2, false, annotationAttrs, null, annotationParticle, new XSObjectListImpl(null, 0));
/*      */ 
/*      */       
/*  631 */       annotationType.setName("#AnonType_" + SchemaSymbols.ELT_ANNOTATION);
/*  632 */       annotationType.setIsAnonymous();
/*      */       
/*  634 */       documentationType.setValues("#AnonType_" + SchemaSymbols.ELT_DOCUMENTATION, this.fTargetNamespace, SchemaGrammar.fAnyType, (short)2, (short)0, (short)3, (short)3, false, documentationAttrs, null, anyWCSequenceParticle, new XSObjectListImpl(null, 0));
/*      */ 
/*      */       
/*  637 */       documentationType.setName("#AnonType_" + SchemaSymbols.ELT_DOCUMENTATION);
/*  638 */       documentationType.setIsAnonymous();
/*      */       
/*  640 */       appinfoType.setValues("#AnonType_" + SchemaSymbols.ELT_APPINFO, this.fTargetNamespace, SchemaGrammar.fAnyType, (short)2, (short)0, (short)3, (short)3, false, appinfoAttrs, null, anyWCSequenceParticle, new XSObjectListImpl(null, 0));
/*      */ 
/*      */       
/*  643 */       appinfoType.setName("#AnonType_" + SchemaSymbols.ELT_APPINFO);
/*  644 */       appinfoType.setIsAnonymous();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public XMLGrammarDescription getGrammarDescription() {
/*  651 */       return this.fGrammarDescription.makeClone();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setImportedGrammars(Vector importedGrammars) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void addGlobalAttributeDecl(XSAttributeDecl decl) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void addGlobalAttributeDecl(XSAttributeGroupDecl decl, String location) {}
/*      */ 
/*      */     
/*      */     public void addGlobalAttributeGroupDecl(XSAttributeGroupDecl decl) {}
/*      */ 
/*      */     
/*      */     public void addGlobalAttributeGroupDecl(XSAttributeGroupDecl decl, String location) {}
/*      */ 
/*      */     
/*      */     public void addGlobalElementDecl(XSElementDecl decl) {}
/*      */ 
/*      */     
/*      */     public void addGlobalElementDecl(XSElementDecl decl, String location) {}
/*      */ 
/*      */     
/*      */     public void addGlobalElementDeclAll(XSElementDecl decl) {}
/*      */ 
/*      */     
/*      */     public void addGlobalGroupDecl(XSGroupDecl decl) {}
/*      */ 
/*      */     
/*      */     public void addGlobalGroupDecl(XSGroupDecl decl, String location) {}
/*      */ 
/*      */     
/*      */     public void addGlobalNotationDecl(XSNotationDecl decl) {}
/*      */ 
/*      */     
/*      */     public void addGlobalNotationDecl(XSNotationDecl decl, String location) {}
/*      */ 
/*      */     
/*      */     public void addGlobalTypeDecl(XSTypeDefinition decl) {}
/*      */ 
/*      */     
/*      */     public void addGlobalTypeDecl(XSTypeDefinition decl, String location) {}
/*      */ 
/*      */     
/*      */     public void addGlobalComplexTypeDecl(XSComplexTypeDecl decl) {}
/*      */ 
/*      */     
/*      */     public void addGlobalComplexTypeDecl(XSComplexTypeDecl decl, String location) {}
/*      */ 
/*      */     
/*      */     public void addGlobalSimpleTypeDecl(XSSimpleType decl) {}
/*      */ 
/*      */     
/*      */     public void addGlobalSimpleTypeDecl(XSSimpleType decl, String location) {}
/*      */ 
/*      */     
/*      */     public void addComplexTypeDecl(XSComplexTypeDecl decl, SimpleLocator locator) {}
/*      */ 
/*      */     
/*      */     public void addRedefinedGroupDecl(XSGroupDecl derived, XSGroupDecl base, SimpleLocator locator) {}
/*      */ 
/*      */     
/*      */     public synchronized void addDocument(Object document, String location) {}
/*      */ 
/*      */     
/*      */     synchronized DOMParser getDOMParser() {
/*  722 */       return null;
/*      */     }
/*      */     synchronized SAXParser getSAXParser() {
/*  725 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private XSElementDecl createAnnotationElementDecl(String localName) {
/*  733 */       XSElementDecl eDecl = new XSElementDecl();
/*  734 */       eDecl.fName = localName;
/*  735 */       eDecl.fTargetNamespace = this.fTargetNamespace;
/*  736 */       eDecl.setIsGlobal();
/*  737 */       eDecl.fBlock = 7;
/*      */       
/*  739 */       eDecl.setConstraintType((short)0);
/*  740 */       return eDecl;
/*      */     }
/*      */     
/*      */     private XSParticleDecl createUnboundedModelGroupParticle() {
/*  744 */       XSParticleDecl particle = new XSParticleDecl();
/*  745 */       particle.fMinOccurs = 0;
/*  746 */       particle.fMaxOccurs = -1;
/*  747 */       particle.fType = 3;
/*  748 */       return particle;
/*      */     }
/*      */     
/*      */     private XSParticleDecl createChoiceElementParticle(XSElementDecl ref) {
/*  752 */       XSParticleDecl particle = new XSParticleDecl();
/*  753 */       particle.fMinOccurs = 1;
/*  754 */       particle.fMaxOccurs = 1;
/*  755 */       particle.fType = 1;
/*  756 */       particle.fValue = ref;
/*  757 */       return particle;
/*      */     }
/*      */     
/*      */     private XSParticleDecl createUnboundedAnyWildcardSequenceParticle() {
/*  761 */       XSParticleDecl particle = createUnboundedModelGroupParticle();
/*  762 */       XSModelGroupImpl sequence = new XSModelGroupImpl();
/*  763 */       sequence.fCompositor = 102;
/*  764 */       sequence.fParticleCount = 1;
/*  765 */       sequence.fParticles = new XSParticleDecl[1];
/*  766 */       sequence.fParticles[0] = createAnyLaxWildcardParticle();
/*  767 */       particle.fValue = sequence;
/*  768 */       return particle;
/*      */     }
/*      */     
/*      */     private XSParticleDecl createAnyLaxWildcardParticle() {
/*  772 */       XSParticleDecl particle = new XSParticleDecl();
/*  773 */       particle.fMinOccurs = 1;
/*  774 */       particle.fMaxOccurs = 1;
/*  775 */       particle.fType = 2;
/*      */       
/*  777 */       XSWildcardDecl anyWC = new XSWildcardDecl();
/*  778 */       anyWC.fNamespaceList = null;
/*  779 */       anyWC.fType = 1;
/*  780 */       anyWC.fProcessContents = 3;
/*      */       
/*  782 */       particle.fValue = anyWC;
/*  783 */       return particle;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLGrammarDescription getGrammarDescription() {
/*  792 */     return this.fGrammarDescription;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isNamespaceAware() {
/*  797 */     return true;
/*      */   }
/*      */   
/*  800 */   protected SchemaGrammar() { this.fImported = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1062 */     this.fCTCount = 0;
/* 1063 */     this.fComplexTypeDecls = new XSComplexTypeDecl[16];
/* 1064 */     this.fCTLocators = new SimpleLocator[16];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1069 */     this.fRGCount = 0;
/* 1070 */     this.fRedefinedGroupDecls = new XSGroupDecl[2];
/* 1071 */     this.fRGLocators = new SimpleLocator[1];
/*      */ 
/*      */ 
/*      */     
/* 1075 */     this.fFullChecked = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1159 */     this.fSubGroupCount = 0;
/* 1160 */     this.fSubGroups = new XSElementDecl[16];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1361 */     this.fComponents = null;
/* 1362 */     this.fComponentsExt = null;
/*      */ 
/*      */ 
/*      */     
/* 1366 */     this.fDocuments = null;
/* 1367 */     this.fLocations = null; } public SchemaGrammar(String targetNamespace, XSDDescription grammarDesc, SymbolTable symbolTable) { this.fImported = null; this.fCTCount = 0; this.fComplexTypeDecls = new XSComplexTypeDecl[16]; this.fCTLocators = new SimpleLocator[16]; this.fRGCount = 0; this.fRedefinedGroupDecls = new XSGroupDecl[2]; this.fRGLocators = new SimpleLocator[1]; this.fFullChecked = false; this.fSubGroupCount = 0; this.fSubGroups = new XSElementDecl[16]; this.fComponents = null; this.fComponentsExt = null; this.fDocuments = null; this.fLocations = null; this.fTargetNamespace = targetNamespace; this.fGrammarDescription = grammarDesc; this.fSymbolTable = symbolTable; this.fGlobalAttrDecls = new SymbolHash(); this.fGlobalAttrGrpDecls = new SymbolHash(); this.fGlobalElemDecls = new SymbolHash(); this.fGlobalGroupDecls = new SymbolHash(); this.fGlobalNotationDecls = new SymbolHash(); this.fGlobalIDConstraintDecls = new SymbolHash(); this.fGlobalAttrDeclsExt = new SymbolHash(); this.fGlobalAttrGrpDeclsExt = new SymbolHash(); this.fGlobalElemDeclsExt = new SymbolHash(); this.fGlobalGroupDeclsExt = new SymbolHash(); this.fGlobalNotationDeclsExt = new SymbolHash(); this.fGlobalIDConstraintDeclsExt = new SymbolHash(); this.fGlobalTypeDeclsExt = new SymbolHash(); this.fAllGlobalElemDecls = new SymbolHash(); if (this.fTargetNamespace == SchemaSymbols.URI_SCHEMAFORSCHEMA) { this.fGlobalTypeDecls = SG_SchemaNS.fGlobalTypeDecls.makeClone(); } else { this.fGlobalTypeDecls = new SymbolHash(); }  } public SchemaGrammar(SchemaGrammar grammar) { this.fImported = null; this.fCTCount = 0; this.fComplexTypeDecls = new XSComplexTypeDecl[16]; this.fCTLocators = new SimpleLocator[16]; this.fRGCount = 0; this.fRedefinedGroupDecls = new XSGroupDecl[2]; this.fRGLocators = new SimpleLocator[1]; this.fFullChecked = false; this.fSubGroupCount = 0; this.fSubGroups = new XSElementDecl[16]; this.fComponents = null; this.fComponentsExt = null; this.fDocuments = null; this.fLocations = null; this.fTargetNamespace = grammar.fTargetNamespace; this.fGrammarDescription = grammar.fGrammarDescription.makeClone(); this.fSymbolTable = grammar.fSymbolTable; this.fGlobalAttrDecls = grammar.fGlobalAttrDecls.makeClone(); this.fGlobalAttrGrpDecls = grammar.fGlobalAttrGrpDecls.makeClone(); this.fGlobalElemDecls = grammar.fGlobalElemDecls.makeClone(); this.fGlobalGroupDecls = grammar.fGlobalGroupDecls.makeClone(); this.fGlobalNotationDecls = grammar.fGlobalNotationDecls.makeClone(); this.fGlobalIDConstraintDecls = grammar.fGlobalIDConstraintDecls.makeClone(); this.fGlobalTypeDecls = grammar.fGlobalTypeDecls.makeClone(); this.fGlobalAttrDeclsExt = grammar.fGlobalAttrDeclsExt.makeClone(); this.fGlobalAttrGrpDeclsExt = grammar.fGlobalAttrGrpDeclsExt.makeClone(); this.fGlobalElemDeclsExt = grammar.fGlobalElemDeclsExt.makeClone(); this.fGlobalGroupDeclsExt = grammar.fGlobalGroupDeclsExt.makeClone(); this.fGlobalNotationDeclsExt = grammar.fGlobalNotationDeclsExt.makeClone(); this.fGlobalIDConstraintDeclsExt = grammar.fGlobalIDConstraintDeclsExt.makeClone(); this.fGlobalTypeDeclsExt = grammar.fGlobalTypeDeclsExt.makeClone(); this.fAllGlobalElemDecls = grammar.fAllGlobalElemDecls.makeClone(); this.fNumAnnotations = grammar.fNumAnnotations; if (this.fNumAnnotations > 0) { this.fAnnotations = new XSAnnotationImpl[grammar.fAnnotations.length]; System.arraycopy(grammar.fAnnotations, 0, this.fAnnotations, 0, this.fNumAnnotations); }  this.fSubGroupCount = grammar.fSubGroupCount; if (this.fSubGroupCount > 0) { this.fSubGroups = new XSElementDecl[grammar.fSubGroups.length]; System.arraycopy(grammar.fSubGroups, 0, this.fSubGroups, 0, this.fSubGroupCount); }  this.fCTCount = grammar.fCTCount; if (this.fCTCount > 0) { this.fComplexTypeDecls = new XSComplexTypeDecl[grammar.fComplexTypeDecls.length]; this.fCTLocators = new SimpleLocator[grammar.fCTLocators.length]; System.arraycopy(grammar.fComplexTypeDecls, 0, this.fComplexTypeDecls, 0, this.fCTCount); System.arraycopy(grammar.fCTLocators, 0, this.fCTLocators, 0, this.fCTCount); }  this.fRGCount = grammar.fRGCount; if (this.fRGCount > 0) { this.fRedefinedGroupDecls = new XSGroupDecl[grammar.fRedefinedGroupDecls.length]; this.fRGLocators = new SimpleLocator[grammar.fRGLocators.length]; System.arraycopy(grammar.fRedefinedGroupDecls, 0, this.fRedefinedGroupDecls, 0, this.fRGCount); System.arraycopy(grammar.fRGLocators, 0, this.fRGLocators, 0, this.fRGCount); }  if (grammar.fImported != null) { this.fImported = new Vector(); for (int i = 0; i < grammar.fImported.size(); i++) this.fImported.add(grammar.fImported.elementAt(i));  }  if (grammar.fLocations != null) for (int k = 0; k < grammar.fLocations.size(); k++) addDocument(null, grammar.fLocations.elementAt(k));   }
/*      */   public void setImportedGrammars(Vector importedGrammars) { this.fImported = importedGrammars; }
/*      */   public Vector getImportedGrammars() { return this.fImported; }
/* 1370 */   public final String getTargetNamespace() { return this.fTargetNamespace; } public void addGlobalAttributeDecl(XSAttributeDecl decl) { this.fGlobalAttrDecls.put(decl.fName, decl); decl.setNamespaceItem(this); } public void addGlobalAttributeDecl(XSAttributeDecl decl, String location) { this.fGlobalAttrDeclsExt.put(((location != null) ? location : "") + "," + decl.fName, decl); if (decl.getNamespaceItem() == null) decl.setNamespaceItem(this);  } public void addGlobalAttributeGroupDecl(XSAttributeGroupDecl decl) { this.fGlobalAttrGrpDecls.put(decl.fName, decl); decl.setNamespaceItem(this); } public void addGlobalAttributeGroupDecl(XSAttributeGroupDecl decl, String location) { this.fGlobalAttrGrpDeclsExt.put(((location != null) ? location : "") + "," + decl.fName, decl); if (decl.getNamespaceItem() == null) decl.setNamespaceItem(this);  } public void addGlobalElementDeclAll(XSElementDecl decl) { if (this.fAllGlobalElemDecls.get(decl) == null) { this.fAllGlobalElemDecls.put(decl, decl); if (decl.fSubGroup != null) { if (this.fSubGroupCount == this.fSubGroups.length) this.fSubGroups = resize(this.fSubGroups, this.fSubGroupCount + 16);  this.fSubGroups[this.fSubGroupCount++] = decl; }  }  } public void addGlobalElementDecl(XSElementDecl decl) { this.fGlobalElemDecls.put(decl.fName, decl); decl.setNamespaceItem(this); } public void addGlobalElementDecl(XSElementDecl decl, String location) { this.fGlobalElemDeclsExt.put(((location != null) ? location : "") + "," + decl.fName, decl); if (decl.getNamespaceItem() == null) decl.setNamespaceItem(this);  } public void addGlobalGroupDecl(XSGroupDecl decl) { this.fGlobalGroupDecls.put(decl.fName, decl); decl.setNamespaceItem(this); } public void addGlobalGroupDecl(XSGroupDecl decl, String location) { this.fGlobalGroupDeclsExt.put(((location != null) ? location : "") + "," + decl.fName, decl); if (decl.getNamespaceItem() == null) decl.setNamespaceItem(this);  } public void addGlobalNotationDecl(XSNotationDecl decl) { this.fGlobalNotationDecls.put(decl.fName, decl); decl.setNamespaceItem(this); } public void addGlobalNotationDecl(XSNotationDecl decl, String location) { this.fGlobalNotationDeclsExt.put(((location != null) ? location : "") + "," + decl.fName, decl); if (decl.getNamespaceItem() == null) decl.setNamespaceItem(this);  } public void addGlobalTypeDecl(XSTypeDefinition decl) { this.fGlobalTypeDecls.put(decl.getName(), decl); if (decl instanceof XSComplexTypeDecl) { ((XSComplexTypeDecl)decl).setNamespaceItem(this); } else if (decl instanceof XSSimpleTypeDecl) { ((XSSimpleTypeDecl)decl).setNamespaceItem(this); }  } public void addGlobalTypeDecl(XSTypeDefinition decl, String location) { this.fGlobalTypeDeclsExt.put(((location != null) ? location : "") + "," + decl.getName(), decl); if (decl.getNamespaceItem() == null) if (decl instanceof XSComplexTypeDecl) { ((XSComplexTypeDecl)decl).setNamespaceItem(this); } else if (decl instanceof XSSimpleTypeDecl) { ((XSSimpleTypeDecl)decl).setNamespaceItem(this); }   } public void addGlobalComplexTypeDecl(XSComplexTypeDecl decl) { this.fGlobalTypeDecls.put(decl.getName(), decl); decl.setNamespaceItem(this); } public void addGlobalComplexTypeDecl(XSComplexTypeDecl decl, String location) { this.fGlobalTypeDeclsExt.put(((location != null) ? location : "") + "," + decl.getName(), decl); if (decl.getNamespaceItem() == null) decl.setNamespaceItem(this);  } public void addGlobalSimpleTypeDecl(XSSimpleType decl) { this.fGlobalTypeDecls.put(decl.getName(), decl); if (decl instanceof XSSimpleTypeDecl) ((XSSimpleTypeDecl)decl).setNamespaceItem(this);  } public void addGlobalSimpleTypeDecl(XSSimpleType decl, String location) { this.fGlobalTypeDeclsExt.put(((location != null) ? location : "") + "," + decl.getName(), decl); if (decl.getNamespaceItem() == null && decl instanceof XSSimpleTypeDecl) ((XSSimpleTypeDecl)decl).setNamespaceItem(this);  } public final void addIDConstraintDecl(XSElementDecl elmDecl, IdentityConstraint decl) { elmDecl.addIDConstraint(decl); this.fGlobalIDConstraintDecls.put(decl.getIdentityConstraintName(), decl); } public final void addIDConstraintDecl(XSElementDecl elmDecl, IdentityConstraint decl, String location) { this.fGlobalIDConstraintDeclsExt.put(((location != null) ? location : "") + "," + decl.getIdentityConstraintName(), decl); } public final XSAttributeDecl getGlobalAttributeDecl(String declName) { return (XSAttributeDecl)this.fGlobalAttrDecls.get(declName); } public final XSAttributeDecl getGlobalAttributeDecl(String declName, String location) { return (XSAttributeDecl)this.fGlobalAttrDeclsExt.get(((location != null) ? location : "") + "," + declName); } public final XSAttributeGroupDecl getGlobalAttributeGroupDecl(String declName) { return (XSAttributeGroupDecl)this.fGlobalAttrGrpDecls.get(declName); } public final XSAttributeGroupDecl getGlobalAttributeGroupDecl(String declName, String location) { return (XSAttributeGroupDecl)this.fGlobalAttrGrpDeclsExt.get(((location != null) ? location : "") + "," + declName); } public final XSElementDecl getGlobalElementDecl(String declName) { return (XSElementDecl)this.fGlobalElemDecls.get(declName); } public final XSElementDecl getGlobalElementDecl(String declName, String location) { return (XSElementDecl)this.fGlobalElemDeclsExt.get(((location != null) ? location : "") + "," + declName); } public final XSGroupDecl getGlobalGroupDecl(String declName) { return (XSGroupDecl)this.fGlobalGroupDecls.get(declName); } public final XSGroupDecl getGlobalGroupDecl(String declName, String location) { return (XSGroupDecl)this.fGlobalGroupDeclsExt.get(((location != null) ? location : "") + "," + declName); } public final XSNotationDecl getGlobalNotationDecl(String declName) { return (XSNotationDecl)this.fGlobalNotationDecls.get(declName); } public synchronized void addDocument(Object document, String location) { if (this.fDocuments == null) {
/* 1371 */       this.fDocuments = new Vector();
/* 1372 */       this.fLocations = new Vector();
/*      */     } 
/* 1374 */     this.fDocuments.addElement(document);
/* 1375 */     this.fLocations.addElement(location); }
/*      */   public final XSNotationDecl getGlobalNotationDecl(String declName, String location) { return (XSNotationDecl)this.fGlobalNotationDeclsExt.get(((location != null) ? location : "") + "," + declName); }
/*      */   public final XSTypeDefinition getGlobalTypeDecl(String declName) { return (XSTypeDefinition)this.fGlobalTypeDecls.get(declName); } public final XSTypeDefinition getGlobalTypeDecl(String declName, String location) { return (XSTypeDefinition)this.fGlobalTypeDeclsExt.get(((location != null) ? location : "") + "," + declName); } public final IdentityConstraint getIDConstraintDecl(String declName) { return (IdentityConstraint)this.fGlobalIDConstraintDecls.get(declName); } public final IdentityConstraint getIDConstraintDecl(String declName, String location) { return (IdentityConstraint)this.fGlobalIDConstraintDeclsExt.get(((location != null) ? location : "") + "," + declName); } public final boolean hasIDConstraints() { return (this.fGlobalIDConstraintDecls.getLength() > 0); } public void addComplexTypeDecl(XSComplexTypeDecl decl, SimpleLocator locator) { if (this.fCTCount == this.fComplexTypeDecls.length) { this.fComplexTypeDecls = resize(this.fComplexTypeDecls, this.fCTCount + 16); this.fCTLocators = resize(this.fCTLocators, this.fCTCount + 16); }  this.fCTLocators[this.fCTCount] = locator; this.fComplexTypeDecls[this.fCTCount++] = decl; } public void addRedefinedGroupDecl(XSGroupDecl derived, XSGroupDecl base, SimpleLocator locator) { if (this.fRGCount == this.fRedefinedGroupDecls.length) { this.fRedefinedGroupDecls = resize(this.fRedefinedGroupDecls, this.fRGCount << 1); this.fRGLocators = resize(this.fRGLocators, this.fRGCount); }  this.fRGLocators[this.fRGCount / 2] = locator; this.fRedefinedGroupDecls[this.fRGCount++] = derived; this.fRedefinedGroupDecls[this.fRGCount++] = base; } final XSComplexTypeDecl[] getUncheckedComplexTypeDecls() { if (this.fCTCount < this.fComplexTypeDecls.length) { this.fComplexTypeDecls = resize(this.fComplexTypeDecls, this.fCTCount); this.fCTLocators = resize(this.fCTLocators, this.fCTCount); }  return this.fComplexTypeDecls; } final SimpleLocator[] getUncheckedCTLocators() { if (this.fCTCount < this.fCTLocators.length) { this.fComplexTypeDecls = resize(this.fComplexTypeDecls, this.fCTCount); this.fCTLocators = resize(this.fCTLocators, this.fCTCount); }  return this.fCTLocators; } final XSGroupDecl[] getRedefinedGroupDecls() { if (this.fRGCount < this.fRedefinedGroupDecls.length) { this.fRedefinedGroupDecls = resize(this.fRedefinedGroupDecls, this.fRGCount); this.fRGLocators = resize(this.fRGLocators, this.fRGCount / 2); }  return this.fRedefinedGroupDecls; } final SimpleLocator[] getRGLocators() { if (this.fRGCount < this.fRedefinedGroupDecls.length) { this.fRedefinedGroupDecls = resize(this.fRedefinedGroupDecls, this.fRGCount); this.fRGLocators = resize(this.fRGLocators, this.fRGCount / 2); }  return this.fRGLocators; } final void setUncheckedTypeNum(int newSize) { this.fCTCount = newSize; this.fComplexTypeDecls = resize(this.fComplexTypeDecls, this.fCTCount); this.fCTLocators = resize(this.fCTLocators, this.fCTCount); } final XSElementDecl[] getSubstitutionGroups() { if (this.fSubGroupCount < this.fSubGroups.length) this.fSubGroups = resize(this.fSubGroups, this.fSubGroupCount);  return this.fSubGroups; } public static final XSComplexTypeDecl fAnyType = new XSAnyType(); private static class XSAnyType extends XSComplexTypeDecl {
/*      */     public XSAnyType() { this.fName = "anyType"; this.fTargetNamespace = SchemaSymbols.URI_SCHEMAFORSCHEMA; this.fBaseType = this; this.fDerivedBy = 2; this.fContentType = 3; this.fParticle = null; this.fAttrGrp = null; } public void setValues(String name, String targetNamespace, XSTypeDefinition baseType, short derivedBy, short schemaFinal, short block, short contentType, boolean isAbstract, XSAttributeGroupDecl attrGrp, XSSimpleType simpleType, XSParticleDecl particle) {} public void setName(String name) {} public void setIsAbstractType() {} public void setContainsTypeID() {} public void setIsAnonymous() {} public void reset() {} public XSObjectList getAttributeUses() { return XSObjectListImpl.EMPTY_LIST; } public XSAttributeGroupDecl getAttrGrp() { XSWildcardDecl wildcard = new XSWildcardDecl(); wildcard.fProcessContents = 3; XSAttributeGroupDecl attrGrp = new XSAttributeGroupDecl(); attrGrp.fAttributeWC = wildcard; return attrGrp; } public XSWildcard getAttributeWildcard() { XSWildcardDecl wildcard = new XSWildcardDecl(); wildcard.fProcessContents = 3; return wildcard; } public XSParticle getParticle() { XSWildcardDecl wildcard = new XSWildcardDecl(); wildcard.fProcessContents = 3; XSParticleDecl particleW = new XSParticleDecl(); particleW.fMinOccurs = 0; particleW.fMaxOccurs = -1; particleW.fType = 2; particleW.fValue = wildcard; XSModelGroupImpl group = new XSModelGroupImpl(); group.fCompositor = 102; group.fParticleCount = 1; group.fParticles = new XSParticleDecl[1]; group.fParticles[0] = particleW; XSParticleDecl particleG = new XSParticleDecl(); particleG.fType = 3; particleG.fValue = group; return particleG; } public XSObjectList getAnnotations() { return XSObjectListImpl.EMPTY_LIST; } public XSNamespaceItem getNamespaceItem() { return SchemaGrammar.SG_SchemaNS; } } private static class BuiltinAttrDecl extends XSAttributeDecl {
/* 1379 */     public BuiltinAttrDecl(String name, String tns, XSSimpleType type, short scope) { this.fName = name; this.fTargetNamespace = tns; this.fType = type; this.fScope = scope; } public void setValues(String name, String targetNamespace, XSSimpleType simpleType, short constraintType, short scope, ValidatedInfo valInfo, XSComplexTypeDecl enclosingCT) {} public void reset() {} public XSAnnotation getAnnotation() { return null; } public XSNamespaceItem getNamespaceItem() { return SchemaGrammar.SG_XSI; } } public static final BuiltinSchemaGrammar SG_SchemaNS = new BuiltinSchemaGrammar(1, (short)1); private static final BuiltinSchemaGrammar SG_SchemaNSExtended = new BuiltinSchemaGrammar(1, (short)2); public static final XSSimpleType fAnySimpleType = (XSSimpleType)SG_SchemaNS.getGlobalTypeDecl("anySimpleType"); public static final BuiltinSchemaGrammar SG_XSI = new BuiltinSchemaGrammar(2, (short)1); private static final short MAX_COMP_IDX = 16; public static SchemaGrammar getS4SGrammar(short schemaVersion) { if (schemaVersion == 1) return SG_SchemaNS;  return SG_SchemaNSExtended; } static final XSComplexTypeDecl[] resize(XSComplexTypeDecl[] oldArray, int newSize) { XSComplexTypeDecl[] newArray = new XSComplexTypeDecl[newSize]; System.arraycopy(oldArray, 0, newArray, 0, Math.min(oldArray.length, newSize)); return newArray; } static final XSGroupDecl[] resize(XSGroupDecl[] oldArray, int newSize) { XSGroupDecl[] newArray = new XSGroupDecl[newSize]; System.arraycopy(oldArray, 0, newArray, 0, Math.min(oldArray.length, newSize)); return newArray; } static final XSElementDecl[] resize(XSElementDecl[] oldArray, int newSize) { XSElementDecl[] newArray = new XSElementDecl[newSize]; System.arraycopy(oldArray, 0, newArray, 0, Math.min(oldArray.length, newSize)); return newArray; } static final SimpleLocator[] resize(SimpleLocator[] oldArray, int newSize) { SimpleLocator[] newArray = new SimpleLocator[newSize]; System.arraycopy(oldArray, 0, newArray, 0, Math.min(oldArray.length, newSize)); return newArray; } private static final boolean[] GLOBAL_COMP = new boolean[] { false, true, true, true, false, true, true, false, false, false, false, true, false, false, false, true, true }; private XSNamedMap[] fComponents; private ObjectList[] fComponentsExt; private Vector fDocuments; private Vector fLocations; public synchronized void removeDocument(int index) { if (this.fDocuments != null && index >= 0 && index < this.fDocuments
/*      */       
/* 1381 */       .size()) {
/* 1382 */       this.fDocuments.removeElementAt(index);
/* 1383 */       this.fLocations.removeElementAt(index);
/*      */     }  }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSchemaNamespace() {
/* 1393 */     return this.fTargetNamespace;
/*      */   }
/*      */ 
/*      */   
/*      */   synchronized DOMParser getDOMParser() {
/* 1398 */     if (this.fDOMParser != null) {
/* 1399 */       DOMParser dOMParser = this.fDOMParser.get();
/* 1400 */       if (dOMParser != null) {
/* 1401 */         return dOMParser;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1406 */     XML11Configuration config = new XML11Configuration(this.fSymbolTable);
/*      */ 
/*      */ 
/*      */     
/* 1410 */     config.setFeature("http://xml.org/sax/features/namespaces", true);
/* 1411 */     config.setFeature("http://xml.org/sax/features/validation", false);
/*      */     
/* 1413 */     DOMParser parser = new DOMParser(config);
/*      */     try {
/* 1415 */       parser.setFeature("http://apache.org/xml/features/dom/defer-node-expansion", false);
/*      */     }
/* 1417 */     catch (SAXException sAXException) {}
/* 1418 */     this.fDOMParser = new SoftReference<>(parser);
/* 1419 */     return parser;
/*      */   }
/*      */   
/*      */   synchronized SAXParser getSAXParser() {
/* 1423 */     if (this.fSAXParser != null) {
/* 1424 */       SAXParser sAXParser = this.fSAXParser.get();
/* 1425 */       if (sAXParser != null) {
/* 1426 */         return sAXParser;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1431 */     XML11Configuration config = new XML11Configuration(this.fSymbolTable);
/*      */ 
/*      */ 
/*      */     
/* 1435 */     config.setFeature("http://xml.org/sax/features/namespaces", true);
/* 1436 */     config.setFeature("http://xml.org/sax/features/validation", false);
/* 1437 */     SAXParser parser = new SAXParser(config);
/* 1438 */     this.fSAXParser = new SoftReference<>(parser);
/* 1439 */     return parser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized XSNamedMap getComponents(short objectType) {
/* 1456 */     if (objectType <= 0 || objectType > 16 || !GLOBAL_COMP[objectType])
/*      */     {
/* 1458 */       return XSNamedMapImpl.EMPTY_MAP;
/*      */     }
/*      */     
/* 1461 */     if (this.fComponents == null) {
/* 1462 */       this.fComponents = new XSNamedMap[17];
/*      */     }
/*      */     
/* 1465 */     if (this.fComponents[objectType] == null) {
/* 1466 */       SymbolHash table = null;
/* 1467 */       switch (objectType) {
/*      */         case 3:
/*      */         case 15:
/*      */         case 16:
/* 1471 */           table = this.fGlobalTypeDecls;
/*      */           break;
/*      */         case 1:
/* 1474 */           table = this.fGlobalAttrDecls;
/*      */           break;
/*      */         case 2:
/* 1477 */           table = this.fGlobalElemDecls;
/*      */           break;
/*      */         case 5:
/* 1480 */           table = this.fGlobalAttrGrpDecls;
/*      */           break;
/*      */         case 6:
/* 1483 */           table = this.fGlobalGroupDecls;
/*      */           break;
/*      */         case 11:
/* 1486 */           table = this.fGlobalNotationDecls;
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1492 */       if (objectType == 15 || objectType == 16) {
/*      */         
/* 1494 */         this.fComponents[objectType] = new XSNamedMap4Types(this.fTargetNamespace, table, objectType);
/*      */       } else {
/*      */         
/* 1497 */         this.fComponents[objectType] = new XSNamedMapImpl(this.fTargetNamespace, table);
/*      */       } 
/*      */     } 
/*      */     
/* 1501 */     return this.fComponents[objectType];
/*      */   }
/*      */   
/*      */   public synchronized ObjectList getComponentsExt(short objectType) {
/* 1505 */     if (objectType <= 0 || objectType > 16 || !GLOBAL_COMP[objectType])
/*      */     {
/* 1507 */       return ObjectListImpl.EMPTY_LIST;
/*      */     }
/*      */     
/* 1510 */     if (this.fComponentsExt == null) {
/* 1511 */       this.fComponentsExt = new ObjectList[17];
/*      */     }
/*      */     
/* 1514 */     if (this.fComponentsExt[objectType] == null) {
/* 1515 */       SymbolHash table = null;
/* 1516 */       switch (objectType) {
/*      */         case 3:
/*      */         case 15:
/*      */         case 16:
/* 1520 */           table = this.fGlobalTypeDeclsExt;
/*      */           break;
/*      */         case 1:
/* 1523 */           table = this.fGlobalAttrDeclsExt;
/*      */           break;
/*      */         case 2:
/* 1526 */           table = this.fGlobalElemDeclsExt;
/*      */           break;
/*      */         case 5:
/* 1529 */           table = this.fGlobalAttrGrpDeclsExt;
/*      */           break;
/*      */         case 6:
/* 1532 */           table = this.fGlobalGroupDeclsExt;
/*      */           break;
/*      */         case 11:
/* 1535 */           table = this.fGlobalNotationDeclsExt;
/*      */           break;
/*      */       } 
/*      */       
/* 1539 */       Object[] entries = table.getEntries();
/* 1540 */       this.fComponentsExt[objectType] = new ObjectListImpl(entries, entries.length);
/*      */     } 
/*      */     
/* 1543 */     return this.fComponentsExt[objectType];
/*      */   }
/*      */   
/*      */   public synchronized void resetComponents() {
/* 1547 */     this.fComponents = null;
/* 1548 */     this.fComponentsExt = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XSTypeDefinition getTypeDefinition(String name) {
/* 1559 */     return getGlobalTypeDecl(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XSAttributeDeclaration getAttributeDeclaration(String name) {
/* 1569 */     return getGlobalAttributeDecl(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XSElementDeclaration getElementDeclaration(String name) {
/* 1579 */     return getGlobalElementDecl(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XSAttributeGroupDefinition getAttributeGroup(String name) {
/* 1589 */     return getGlobalAttributeGroupDecl(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XSModelGroupDefinition getModelGroupDefinition(String name) {
/* 1600 */     return getGlobalGroupDecl(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XSNotationDeclaration getNotationDeclaration(String name) {
/* 1611 */     return getGlobalNotationDecl(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StringList getDocumentLocations() {
/* 1621 */     return new StringListImpl(this.fLocations);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XSModel toXSModel() {
/* 1631 */     return new XSModelImpl(new SchemaGrammar[] { this });
/*      */   }
/*      */   
/*      */   public XSModel toXSModel(XSGrammar[] grammars) {
/* 1635 */     if (grammars == null || grammars.length == 0) {
/* 1636 */       return toXSModel();
/*      */     }
/* 1638 */     int len = grammars.length;
/* 1639 */     boolean hasSelf = false;
/* 1640 */     for (int i = 0; i < len; i++) {
/* 1641 */       if (grammars[i] == this) {
/* 1642 */         hasSelf = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 1647 */     SchemaGrammar[] gs = new SchemaGrammar[hasSelf ? len : (len + 1)];
/* 1648 */     for (int j = 0; j < len; j++)
/* 1649 */       gs[j] = (SchemaGrammar)grammars[j]; 
/* 1650 */     if (!hasSelf)
/* 1651 */       gs[len] = this; 
/* 1652 */     return new XSModelImpl(gs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XSObjectList getAnnotations() {
/* 1659 */     if (this.fNumAnnotations == 0) {
/* 1660 */       return XSObjectListImpl.EMPTY_LIST;
/*      */     }
/* 1662 */     return new XSObjectListImpl((XSObject[])this.fAnnotations, this.fNumAnnotations);
/*      */   }
/*      */   
/*      */   public void addAnnotation(XSAnnotationImpl annotation) {
/* 1666 */     if (annotation == null) {
/*      */       return;
/*      */     }
/* 1669 */     if (this.fAnnotations == null) {
/* 1670 */       this.fAnnotations = new XSAnnotationImpl[2];
/*      */     }
/* 1672 */     else if (this.fNumAnnotations == this.fAnnotations.length) {
/* 1673 */       XSAnnotationImpl[] newArray = new XSAnnotationImpl[this.fNumAnnotations << 1];
/* 1674 */       System.arraycopy(this.fAnnotations, 0, newArray, 0, this.fNumAnnotations);
/* 1675 */       this.fAnnotations = newArray;
/*      */     } 
/* 1677 */     this.fAnnotations[this.fNumAnnotations++] = annotation;
/*      */   }
/*      */   
/*      */   public void setImmutable(boolean isImmutable) {
/* 1681 */     this.fIsImmutable = isImmutable;
/*      */   }
/*      */   
/*      */   public boolean isImmutable() {
/* 1685 */     return this.fIsImmutable;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xs/SchemaGrammar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */