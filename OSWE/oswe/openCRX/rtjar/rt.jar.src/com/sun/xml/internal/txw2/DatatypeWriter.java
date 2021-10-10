/*     */ package com.sun.xml.internal.txw2;
/*     */ 
/*     */ import java.util.AbstractList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface DatatypeWriter<DT>
/*     */ {
/*  59 */   public static final List<DatatypeWriter<?>> BUILTIN = Collections.unmodifiableList(new AbstractList<DatatypeWriter<?>>()
/*     */       {
/*  61 */         private DatatypeWriter<?>[] BUILTIN_ARRAY = new DatatypeWriter[] { new DatatypeWriter<String>()
/*     */             {
/*     */               public Class<String> getType() {
/*  64 */                 return String.class;
/*     */               }
/*     */               public void print(String s, NamespaceResolver resolver, StringBuilder buf) {
/*  67 */                 buf.append(s);
/*     */               }
/*     */             }, new DatatypeWriter<Integer>()
/*     */             {
/*     */               public Class<Integer> getType() {
/*  72 */                 return Integer.class;
/*     */               }
/*     */               public void print(Integer i, NamespaceResolver resolver, StringBuilder buf) {
/*  75 */                 buf.append(i);
/*     */               }
/*     */             }, new DatatypeWriter<Float>()
/*     */             {
/*     */               public Class<Float> getType() {
/*  80 */                 return Float.class;
/*     */               }
/*     */               public void print(Float f, NamespaceResolver resolver, StringBuilder buf) {
/*  83 */                 buf.append(f);
/*     */               }
/*     */             }, new DatatypeWriter<Double>()
/*     */             {
/*     */               public Class<Double> getType() {
/*  88 */                 return Double.class;
/*     */               }
/*     */               public void print(Double d, NamespaceResolver resolver, StringBuilder buf) {
/*  91 */                 buf.append(d);
/*     */               }
/*     */             }, new DatatypeWriter<QName>()
/*     */             {
/*     */               public Class<QName> getType() {
/*  96 */                 return QName.class;
/*     */               }
/*     */               public void print(QName qn, NamespaceResolver resolver, StringBuilder buf) {
/*  99 */                 String p = resolver.getPrefix(qn.getNamespaceURI());
/* 100 */                 if (p.length() != 0)
/* 101 */                   buf.append(p).append(':'); 
/* 102 */                 buf.append(qn.getLocalPart());
/*     */               }
/*     */             } };
/*     */ 
/*     */         
/*     */         public DatatypeWriter<?> get(int n) {
/* 108 */           return this.BUILTIN_ARRAY[n];
/*     */         }
/*     */         
/*     */         public int size() {
/* 112 */           return this.BUILTIN_ARRAY.length;
/*     */         }
/*     */       });
/*     */   
/*     */   Class<DT> getType();
/*     */   
/*     */   void print(DT paramDT, NamespaceResolver paramNamespaceResolver, StringBuilder paramStringBuilder);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/txw2/DatatypeWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */