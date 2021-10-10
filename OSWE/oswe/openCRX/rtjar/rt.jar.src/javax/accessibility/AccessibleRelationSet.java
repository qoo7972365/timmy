/*     */ package javax.accessibility;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AccessibleRelationSet
/*     */ {
/*  56 */   protected Vector<AccessibleRelation> relations = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AccessibleRelationSet() {
/*  62 */     this.relations = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AccessibleRelationSet(AccessibleRelation[] paramArrayOfAccessibleRelation) {
/*  73 */     if (paramArrayOfAccessibleRelation.length != 0) {
/*  74 */       this.relations = new Vector<>(paramArrayOfAccessibleRelation.length);
/*  75 */       for (byte b = 0; b < paramArrayOfAccessibleRelation.length; b++) {
/*  76 */         add(paramArrayOfAccessibleRelation[b]);
/*     */       }
/*     */     } 
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
/*     */   public boolean add(AccessibleRelation paramAccessibleRelation) {
/*  92 */     if (this.relations == null) {
/*  93 */       this.relations = new Vector<>();
/*     */     }
/*     */ 
/*     */     
/*  97 */     AccessibleRelation accessibleRelation = get(paramAccessibleRelation.getKey());
/*  98 */     if (accessibleRelation == null) {
/*  99 */       this.relations.addElement(paramAccessibleRelation);
/* 100 */       return true;
/*     */     } 
/* 102 */     Object[] arrayOfObject1 = accessibleRelation.getTarget();
/* 103 */     Object[] arrayOfObject2 = paramAccessibleRelation.getTarget();
/* 104 */     int i = arrayOfObject1.length + arrayOfObject2.length;
/* 105 */     Object[] arrayOfObject3 = new Object[i]; int j;
/* 106 */     for (j = 0; j < arrayOfObject1.length; j++) {
/* 107 */       arrayOfObject3[j] = arrayOfObject1[j];
/*     */     }
/* 109 */     j = arrayOfObject1.length; byte b = 0;
/* 110 */     for (; j < i; 
/* 111 */       j++, b++) {
/* 112 */       arrayOfObject3[j] = arrayOfObject2[b];
/*     */     }
/* 114 */     accessibleRelation.setTarget(arrayOfObject3);
/*     */     
/* 116 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAll(AccessibleRelation[] paramArrayOfAccessibleRelation) {
/* 126 */     if (paramArrayOfAccessibleRelation.length != 0) {
/* 127 */       if (this.relations == null) {
/* 128 */         this.relations = new Vector<>(paramArrayOfAccessibleRelation.length);
/*     */       }
/* 130 */       for (byte b = 0; b < paramArrayOfAccessibleRelation.length; b++) {
/* 131 */         add(paramArrayOfAccessibleRelation[b]);
/*     */       }
/*     */     } 
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
/*     */   public boolean remove(AccessibleRelation paramAccessibleRelation) {
/* 148 */     if (this.relations == null) {
/* 149 */       return false;
/*     */     }
/* 151 */     return this.relations.removeElement(paramAccessibleRelation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 159 */     if (this.relations != null) {
/* 160 */       this.relations.removeAllElements();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 169 */     if (this.relations == null) {
/* 170 */       return 0;
/*     */     }
/* 172 */     return this.relations.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(String paramString) {
/* 183 */     return (get(paramString) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AccessibleRelation get(String paramString) {
/* 193 */     if (this.relations == null) {
/* 194 */       return null;
/*     */     }
/* 196 */     int i = this.relations.size();
/* 197 */     for (byte b = 0; b < i; b++) {
/*     */       
/* 199 */       AccessibleRelation accessibleRelation = this.relations.elementAt(b);
/* 200 */       if (accessibleRelation != null && accessibleRelation.getKey().equals(paramString)) {
/* 201 */         return accessibleRelation;
/*     */       }
/*     */     } 
/* 204 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AccessibleRelation[] toArray() {
/* 213 */     if (this.relations == null) {
/* 214 */       return new AccessibleRelation[0];
/*     */     }
/*     */     
/* 217 */     AccessibleRelation[] arrayOfAccessibleRelation = new AccessibleRelation[this.relations.size()];
/* 218 */     for (byte b = 0; b < arrayOfAccessibleRelation.length; b++) {
/* 219 */       arrayOfAccessibleRelation[b] = this.relations.elementAt(b);
/*     */     }
/* 221 */     return arrayOfAccessibleRelation;
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
/*     */   public String toString() {
/* 233 */     String str = "";
/* 234 */     if (this.relations != null && this.relations.size() > 0) {
/* 235 */       str = ((AccessibleRelation)this.relations.elementAt(0)).toDisplayString();
/* 236 */       for (byte b = 1; b < this.relations.size(); b++)
/*     */       {
/*     */         
/* 239 */         str = str + "," + ((AccessibleRelation)this.relations.elementAt(b)).toDisplayString();
/*     */       }
/*     */     } 
/* 242 */     return str;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/accessibility/AccessibleRelationSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */