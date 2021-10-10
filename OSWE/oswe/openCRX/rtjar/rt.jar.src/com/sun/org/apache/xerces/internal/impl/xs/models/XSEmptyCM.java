/*     */ package com.sun.org.apache.xerces.internal.impl.xs.models;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.xs.SubstitutionGroupHandler;
/*     */ import com.sun.org.apache.xerces.internal.impl.xs.XMLSchemaException;
/*     */ import com.sun.org.apache.xerces.internal.xni.QName;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XSEmptyCM
/*     */   implements XSCMValidator
/*     */ {
/*     */   private static final short STATE_START = 0;
/*  51 */   private static final Vector EMPTY = new Vector(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] startContentModel() {
/*  68 */     return new int[] { 0 };
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
/*     */   public Object oneTransition(QName elementName, int[] currentState, SubstitutionGroupHandler subGroupHandler) {
/*  83 */     if (currentState[0] < 0) {
/*  84 */       currentState[0] = -2;
/*  85 */       return null;
/*     */     } 
/*     */     
/*  88 */     currentState[0] = -1;
/*  89 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean endContentModel(int[] currentState) {
/* 100 */     boolean isFinal = false;
/* 101 */     int state = currentState[0];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     if (state < 0) {
/* 107 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 111 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkUniqueParticleAttribution(SubstitutionGroupHandler subGroupHandler) throws XMLSchemaException {
/* 121 */     return false;
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
/*     */   public Vector whatCanGoHere(int[] state) {
/* 134 */     return EMPTY;
/*     */   }
/*     */   
/*     */   public ArrayList checkMinMaxBounds() {
/* 138 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xs/models/XSEmptyCM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */