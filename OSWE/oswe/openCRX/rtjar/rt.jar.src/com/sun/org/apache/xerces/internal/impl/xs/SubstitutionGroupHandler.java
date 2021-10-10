/*     */ package com.sun.org.apache.xerces.internal.impl.xs;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.xni.QName;
/*     */ import com.sun.org.apache.xerces.internal.xs.XSObjectList;
/*     */ import com.sun.org.apache.xerces.internal.xs.XSSimpleTypeDefinition;
/*     */ import com.sun.org.apache.xerces.internal.xs.XSTypeDefinition;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SubstitutionGroupHandler
/*     */ {
/*  42 */   private static final XSElementDecl[] EMPTY_GROUP = new XSElementDecl[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   XSGrammarBucket fGrammarBucket;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Map<XSElementDecl, Object> fSubGroupsB;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XSElementDecl getMatchingElemDecl(QName element, XSElementDecl exemplar) {
/*     */     if (element.localpart == exemplar.fName && element.uri == exemplar.fTargetNamespace) {
/*     */       return exemplar;
/*     */     }
/*     */     if (exemplar.fScope != 1) {
/*     */       return null;
/*     */     }
/*     */     if ((exemplar.fBlock & 0x4) != 0) {
/*     */       return null;
/*     */     }
/*     */     SchemaGrammar sGrammar = this.fGrammarBucket.getGrammar(element.uri);
/*     */     if (sGrammar == null) {
/*     */       return null;
/*     */     }
/*     */     XSElementDecl eDecl = sGrammar.getGlobalElementDecl(element.localpart);
/*     */     if (eDecl == null) {
/*     */       return null;
/*     */     }
/*     */     if (substitutionGroupOK(eDecl, exemplar, exemplar.fBlock)) {
/*     */       return eDecl;
/*     */     }
/*     */     return null;
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
/*     */   protected boolean substitutionGroupOK(XSElementDecl element, XSElementDecl exemplar, short blockingConstraint) {
/*     */     if (element == exemplar) {
/*     */       return true;
/*     */     }
/*     */     if ((blockingConstraint & 0x4) != 0) {
/*     */       return false;
/*     */     }
/*     */     XSElementDecl subGroup = element.fSubGroup;
/*     */     while (subGroup != null && subGroup != exemplar) {
/*     */       subGroup = subGroup.fSubGroup;
/*     */     }
/*     */     if (subGroup == null) {
/*     */       return false;
/*     */     }
/*     */     return typeDerivationOK(element.fType, exemplar.fType, blockingConstraint);
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
/*     */   private boolean typeDerivationOK(XSTypeDefinition derived, XSTypeDefinition base, short blockingConstraint) {
/*     */     short devMethod = 0, blockConstraint = blockingConstraint;
/*     */     XSTypeDefinition type = derived;
/*     */     while (type != base && type != SchemaGrammar.fAnyType) {
/*     */       if (type.getTypeCategory() == 15) {
/*     */         devMethod = (short)(devMethod | ((XSComplexTypeDecl)type).fDerivedBy);
/*     */       } else {
/*     */         devMethod = (short)(devMethod | 0x2);
/*     */       } 
/*     */       type = type.getBaseType();
/*     */       if (type == null) {
/*     */         type = SchemaGrammar.fAnyType;
/*     */       }
/*     */       if (type.getTypeCategory() == 15) {
/*     */         blockConstraint = (short)(blockConstraint | ((XSComplexTypeDecl)type).fBlock);
/*     */       }
/*     */     } 
/*     */     if (type != base) {
/*     */       if (base.getTypeCategory() == 16) {
/*     */         XSSimpleTypeDefinition st = (XSSimpleTypeDefinition)base;
/*     */         if (st.getVariety() == 3) {
/*     */           XSObjectList memberTypes = st.getMemberTypes();
/*     */           int length = memberTypes.getLength();
/*     */           for (int i = 0; i < length; i++) {
/*     */             if (typeDerivationOK(derived, (XSTypeDefinition)memberTypes.item(i), blockingConstraint)) {
/*     */               return true;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       return false;
/*     */     } 
/*     */     if ((devMethod & blockConstraint) != 0) {
/*     */       return false;
/*     */     }
/*     */     return true;
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
/*     */   public SubstitutionGroupHandler(XSGrammarBucket grammarBucket) {
/* 179 */     this.fSubGroupsB = new HashMap<>();
/*     */ 
/*     */     
/* 182 */     this.fSubGroups = (Map)new HashMap<>();
/*     */     this.fGrammarBucket = grammarBucket;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 188 */     this.fSubGroupsB.clear();
/* 189 */     this.fSubGroups.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inSubstitutionGroup(XSElementDecl element, XSElementDecl exemplar) {
/*     */     return substitutionGroupOK(element, exemplar, exemplar.fBlock);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSubstitutionGroup(XSElementDecl[] elements) {
/* 199 */     for (int i = elements.length - 1; i >= 0; i--) {
/* 200 */       XSElementDecl element = elements[i];
/* 201 */       XSElementDecl subHead = element.fSubGroup;
/*     */       
/* 203 */       Vector<XSElementDecl> subGroup = (Vector)this.fSubGroupsB.get(subHead);
/* 204 */       if (subGroup == null) {
/*     */         
/* 206 */         subGroup = new Vector();
/* 207 */         this.fSubGroupsB.put(subHead, subGroup);
/*     */       } 
/*     */       
/* 210 */       subGroup.addElement(element);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final OneSubGroup[] EMPTY_VECTOR = new OneSubGroup[0];
/*     */ 
/*     */   
/*     */   Map<XSElementDecl, XSElementDecl[]> fSubGroups;
/*     */ 
/*     */ 
/*     */   
/*     */   public XSElementDecl[] getSubstitutionGroup(XSElementDecl element) {
/* 224 */     XSElementDecl[] subGroup = this.fSubGroups.get(element);
/* 225 */     if (subGroup != null) {
/* 226 */       return subGroup;
/*     */     }
/* 228 */     if ((element.fBlock & 0x4) != 0) {
/* 229 */       this.fSubGroups.put(element, EMPTY_GROUP);
/* 230 */       return EMPTY_GROUP;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 235 */     OneSubGroup[] groupB = getSubGroupB(element, new OneSubGroup());
/* 236 */     int len = groupB.length, rlen = 0;
/* 237 */     XSElementDecl[] ret = new XSElementDecl[len];
/*     */ 
/*     */     
/* 240 */     for (int i = 0; i < len; i++) {
/* 241 */       if ((element.fBlock & (groupB[i]).dMethod) == 0) {
/* 242 */         ret[rlen++] = (groupB[i]).sub;
/*     */       }
/*     */     } 
/* 245 */     if (rlen < len) {
/* 246 */       XSElementDecl[] ret1 = new XSElementDecl[rlen];
/* 247 */       System.arraycopy(ret, 0, ret1, 0, rlen);
/* 248 */       ret = ret1;
/*     */     } 
/*     */     
/* 251 */     this.fSubGroups.put(element, ret);
/*     */     
/* 253 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   private OneSubGroup[] getSubGroupB(XSElementDecl element, OneSubGroup methods) {
/* 258 */     Object subGroup = this.fSubGroupsB.get(element);
/*     */ 
/*     */     
/* 261 */     if (subGroup == null) {
/* 262 */       this.fSubGroupsB.put(element, EMPTY_VECTOR);
/* 263 */       return EMPTY_VECTOR;
/*     */     } 
/*     */ 
/*     */     
/* 267 */     if (subGroup instanceof OneSubGroup[]) {
/* 268 */       return (OneSubGroup[])subGroup;
/*     */     }
/*     */     
/* 271 */     Vector<XSElementDecl> group = (Vector)subGroup; Vector<OneSubGroup> newGroup = new Vector();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 276 */     for (int i = group.size() - 1; i >= 0; i--) {
/*     */       
/* 278 */       XSElementDecl sub = group.elementAt(i);
/* 279 */       if (getDBMethods(sub.fType, element.fType, methods)) {
/*     */ 
/*     */         
/* 282 */         short dMethod = methods.dMethod;
/* 283 */         short bMethod = methods.bMethod;
/*     */         
/* 285 */         newGroup.addElement(new OneSubGroup(sub, methods.dMethod, methods.bMethod));
/*     */         
/* 287 */         OneSubGroup[] group1 = getSubGroupB(sub, methods);
/* 288 */         for (int k = group1.length - 1; k >= 0; k--) {
/*     */           
/* 290 */           short dSubMethod = (short)(dMethod | (group1[k]).dMethod);
/* 291 */           short bSubMethod = (short)(bMethod | (group1[k]).bMethod);
/*     */           
/* 293 */           if ((dSubMethod & bSubMethod) == 0)
/*     */           {
/* 295 */             newGroup.addElement(new OneSubGroup((group1[k]).sub, dSubMethod, bSubMethod)); } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 299 */     OneSubGroup[] ret = new OneSubGroup[newGroup.size()];
/* 300 */     for (int j = newGroup.size() - 1; j >= 0; j--) {
/* 301 */       ret[j] = newGroup.elementAt(j);
/*     */     }
/*     */     
/* 304 */     this.fSubGroupsB.put(element, ret);
/*     */     
/* 306 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean getDBMethods(XSTypeDefinition typed, XSTypeDefinition typeb, OneSubGroup methods) {
/* 311 */     short dMethod = 0, bMethod = 0;
/* 312 */     while (typed != typeb && typed != SchemaGrammar.fAnyType) {
/* 313 */       if (typed.getTypeCategory() == 15) {
/* 314 */         dMethod = (short)(dMethod | ((XSComplexTypeDecl)typed).fDerivedBy);
/*     */       } else {
/* 316 */         dMethod = (short)(dMethod | 0x2);
/* 317 */       }  typed = typed.getBaseType();
/*     */ 
/*     */       
/* 320 */       if (typed == null)
/* 321 */         typed = SchemaGrammar.fAnyType; 
/* 322 */       if (typed.getTypeCategory() == 15) {
/* 323 */         bMethod = (short)(bMethod | ((XSComplexTypeDecl)typed).fBlock);
/*     */       }
/*     */     } 
/* 326 */     if (typed != typeb || (dMethod & bMethod) != 0) {
/* 327 */       return false;
/*     */     }
/*     */     
/* 330 */     methods.dMethod = dMethod;
/* 331 */     methods.bMethod = bMethod;
/* 332 */     return true;
/*     */   }
/*     */   
/*     */   private static final class OneSubGroup { XSElementDecl sub;
/*     */     short dMethod;
/*     */     
/*     */     OneSubGroup(XSElementDecl sub, short dMethod, short bMethod) {
/* 339 */       this.sub = sub;
/* 340 */       this.dMethod = dMethod;
/* 341 */       this.bMethod = bMethod;
/*     */     }
/*     */     
/*     */     short bMethod;
/*     */     
/*     */     OneSubGroup() {} }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xs/SubstitutionGroupHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */