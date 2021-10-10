/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
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
/*     */ class XAtomList
/*     */ {
/*  34 */   Set<XAtom> atoms = new HashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XAtomList() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XAtomList(long paramLong, int paramInt) {
/*  48 */     init(paramLong, paramInt);
/*     */   }
/*     */   private void init(long paramLong, int paramInt) {
/*  51 */     for (byte b = 0; b < paramInt; b++) {
/*  52 */       add(new XAtom(XToolkit.getDisplay(), XAtom.getAtom(paramLong + (paramInt * XAtom.getAtomSize()))));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XAtomList(XAtom[] paramArrayOfXAtom) {
/*  61 */     init(paramArrayOfXAtom);
/*     */   }
/*     */   private void init(XAtom[] paramArrayOfXAtom) {
/*  64 */     for (byte b = 0; b < paramArrayOfXAtom.length; b++) {
/*  65 */       add(paramArrayOfXAtom[b]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XAtom[] getAtoms() {
/*  73 */     XAtom[] arrayOfXAtom = new XAtom[size()];
/*  74 */     Iterator<XAtom> iterator = this.atoms.iterator();
/*  75 */     byte b = 0;
/*  76 */     while (iterator.hasNext()) {
/*  77 */       arrayOfXAtom[b++] = iterator.next();
/*     */     }
/*  79 */     return arrayOfXAtom;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getAtomsData() {
/*  89 */     return XAtom.toData(getAtoms());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(XAtom paramXAtom) {
/*  96 */     return this.atoms.contains(paramXAtom);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(XAtom paramXAtom) {
/* 103 */     this.atoms.add(paramXAtom);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(XAtom paramXAtom) {
/* 110 */     this.atoms.remove(paramXAtom);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 118 */     return this.atoms.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XAtomList subset(int paramInt, Map<Integer, XAtom> paramMap) {
/* 126 */     XAtomList xAtomList = new XAtomList();
/* 127 */     Iterator<Integer> iterator = paramMap.keySet().iterator();
/* 128 */     while (iterator.hasNext()) {
/* 129 */       Integer integer = iterator.next();
/* 130 */       if ((paramInt & integer.intValue()) == integer.intValue()) {
/* 131 */         XAtom xAtom = paramMap.get(integer);
/* 132 */         if (contains(xAtom)) {
/* 133 */           xAtomList.add(xAtom);
/*     */         }
/*     */       } 
/*     */     } 
/* 137 */     return xAtomList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<XAtom> iterator() {
/* 144 */     return this.atoms.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAll(XAtomList paramXAtomList) {
/* 151 */     Iterator<XAtom> iterator = paramXAtomList.iterator();
/* 152 */     while (iterator.hasNext()) {
/* 153 */       add(iterator.next());
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString() {
/* 158 */     StringBuffer stringBuffer = new StringBuffer();
/* 159 */     stringBuffer.append("[");
/* 160 */     Iterator<XAtom> iterator = this.atoms.iterator();
/* 161 */     while (iterator.hasNext()) {
/* 162 */       stringBuffer.append(iterator.next().toString());
/* 163 */       if (iterator.hasNext()) {
/* 164 */         stringBuffer.append(", ");
/*     */       }
/*     */     } 
/* 167 */     stringBuffer.append("]");
/* 168 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XAtomList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */