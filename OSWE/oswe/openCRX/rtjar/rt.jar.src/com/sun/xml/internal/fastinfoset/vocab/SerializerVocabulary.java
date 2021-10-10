/*     */ package com.sun.xml.internal.fastinfoset.vocab;
/*     */ 
/*     */ import com.sun.xml.internal.fastinfoset.QualifiedName;
/*     */ import com.sun.xml.internal.fastinfoset.util.CharArrayIntMap;
/*     */ import com.sun.xml.internal.fastinfoset.util.FixedEntryStringIntMap;
/*     */ import com.sun.xml.internal.fastinfoset.util.KeyIntMap;
/*     */ import com.sun.xml.internal.fastinfoset.util.LocalNameQualifiedNamesMap;
/*     */ import com.sun.xml.internal.fastinfoset.util.StringIntMap;
/*     */ import com.sun.xml.internal.org.jvnet.fastinfoset.Vocabulary;
/*     */ import java.util.Iterator;
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
/*     */ public class SerializerVocabulary
/*     */   extends Vocabulary
/*     */ {
/*     */   public final StringIntMap restrictedAlphabet;
/*     */   public final StringIntMap encodingAlgorithm;
/*     */   public final StringIntMap namespaceName;
/*     */   public final StringIntMap prefix;
/*     */   public final StringIntMap localName;
/*     */   public final StringIntMap otherNCName;
/*     */   public final StringIntMap otherURI;
/*     */   public final StringIntMap attributeValue;
/*     */   public final CharArrayIntMap otherString;
/*     */   public final CharArrayIntMap characterContentChunk;
/*     */   public final LocalNameQualifiedNamesMap elementName;
/*     */   public final LocalNameQualifiedNamesMap attributeName;
/*  57 */   public final KeyIntMap[] tables = new KeyIntMap[12];
/*     */   
/*     */   protected boolean _useLocalNameAsKey;
/*     */   
/*     */   protected SerializerVocabulary _readOnlyVocabulary;
/*     */   
/*     */   public SerializerVocabulary() {
/*  64 */     this.tables[0] = (KeyIntMap)(this.restrictedAlphabet = new StringIntMap(4));
/*  65 */     this.tables[1] = (KeyIntMap)(this.encodingAlgorithm = new StringIntMap(4));
/*  66 */     this.tables[2] = (KeyIntMap)(this.prefix = (StringIntMap)new FixedEntryStringIntMap("xml", 8));
/*  67 */     this.tables[3] = (KeyIntMap)(this.namespaceName = (StringIntMap)new FixedEntryStringIntMap("http://www.w3.org/XML/1998/namespace", 8));
/*  68 */     this.tables[4] = (KeyIntMap)(this.localName = new StringIntMap());
/*  69 */     this.tables[5] = (KeyIntMap)(this.otherNCName = new StringIntMap(4));
/*  70 */     this.tables[6] = (KeyIntMap)(this.otherURI = new StringIntMap(4));
/*  71 */     this.tables[7] = (KeyIntMap)(this.attributeValue = new StringIntMap());
/*  72 */     this.tables[8] = (KeyIntMap)(this.otherString = new CharArrayIntMap(4));
/*  73 */     this.tables[9] = (KeyIntMap)(this.characterContentChunk = new CharArrayIntMap());
/*  74 */     this.tables[10] = (KeyIntMap)(this.elementName = new LocalNameQualifiedNamesMap());
/*  75 */     this.tables[11] = (KeyIntMap)(this.attributeName = new LocalNameQualifiedNamesMap());
/*     */   }
/*     */ 
/*     */   
/*     */   public SerializerVocabulary(Vocabulary v, boolean useLocalNameAsKey) {
/*  80 */     this();
/*     */     
/*  82 */     this._useLocalNameAsKey = useLocalNameAsKey;
/*  83 */     convertVocabulary(v);
/*     */   }
/*     */   
/*     */   public SerializerVocabulary getReadOnlyVocabulary() {
/*  87 */     return this._readOnlyVocabulary;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setReadOnlyVocabulary(SerializerVocabulary readOnlyVocabulary, boolean clear) {
/*  92 */     for (int i = 0; i < this.tables.length; i++) {
/*  93 */       this.tables[i].setReadOnlyMap(readOnlyVocabulary.tables[i], clear);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInitialVocabulary(SerializerVocabulary initialVocabulary, boolean clear) {
/*  99 */     setExternalVocabularyURI(null);
/* 100 */     setInitialReadOnlyVocabulary(true);
/* 101 */     setReadOnlyVocabulary(initialVocabulary, clear);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setExternalVocabulary(String externalVocabularyURI, SerializerVocabulary externalVocabulary, boolean clear) {
/* 106 */     setInitialReadOnlyVocabulary(false);
/* 107 */     setExternalVocabularyURI(externalVocabularyURI);
/* 108 */     setReadOnlyVocabulary(externalVocabulary, clear);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 112 */     for (int i = 0; i < this.tables.length; i++) {
/* 113 */       this.tables[i].clear();
/*     */     }
/*     */   }
/*     */   
/*     */   private void convertVocabulary(Vocabulary v) {
/* 118 */     addToTable(v.restrictedAlphabets.iterator(), this.restrictedAlphabet);
/* 119 */     addToTable(v.encodingAlgorithms.iterator(), this.encodingAlgorithm);
/* 120 */     addToTable(v.prefixes.iterator(), this.prefix);
/* 121 */     addToTable(v.namespaceNames.iterator(), this.namespaceName);
/* 122 */     addToTable(v.localNames.iterator(), this.localName);
/* 123 */     addToTable(v.otherNCNames.iterator(), this.otherNCName);
/* 124 */     addToTable(v.otherURIs.iterator(), this.otherURI);
/* 125 */     addToTable(v.attributeValues.iterator(), this.attributeValue);
/* 126 */     addToTable(v.otherStrings.iterator(), this.otherString);
/* 127 */     addToTable(v.characterContentChunks.iterator(), this.characterContentChunk);
/* 128 */     addToTable(v.elements.iterator(), this.elementName);
/* 129 */     addToTable(v.attributes.iterator(), this.attributeName);
/*     */   }
/*     */   
/*     */   private void addToTable(Iterator<String> i, StringIntMap m) {
/* 133 */     while (i.hasNext()) {
/* 134 */       addToTable(i.next(), m);
/*     */     }
/*     */   }
/*     */   
/*     */   private void addToTable(String s, StringIntMap m) {
/* 139 */     if (s.length() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 143 */     m.obtainIndex(s);
/*     */   }
/*     */   
/*     */   private void addToTable(Iterator<String> i, CharArrayIntMap m) {
/* 147 */     while (i.hasNext()) {
/* 148 */       addToTable(i.next(), m);
/*     */     }
/*     */   }
/*     */   
/*     */   private void addToTable(String s, CharArrayIntMap m) {
/* 153 */     if (s.length() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 157 */     char[] c = s.toCharArray();
/* 158 */     m.obtainIndex(c, 0, c.length, false);
/*     */   }
/*     */   
/*     */   private void addToTable(Iterator<QName> i, LocalNameQualifiedNamesMap m) {
/* 162 */     while (i.hasNext()) {
/* 163 */       addToNameTable(i.next(), m);
/*     */     }
/*     */   }
/*     */   
/*     */   private void addToNameTable(QName n, LocalNameQualifiedNamesMap m) {
/* 168 */     int namespaceURIIndex = -1;
/* 169 */     int prefixIndex = -1;
/* 170 */     if (n.getNamespaceURI().length() > 0) {
/* 171 */       namespaceURIIndex = this.namespaceName.obtainIndex(n.getNamespaceURI());
/* 172 */       if (namespaceURIIndex == -1) {
/* 173 */         namespaceURIIndex = this.namespaceName.get(n.getNamespaceURI());
/*     */       }
/*     */       
/* 176 */       if (n.getPrefix().length() > 0) {
/* 177 */         prefixIndex = this.prefix.obtainIndex(n.getPrefix());
/* 178 */         if (prefixIndex == -1) {
/* 179 */           prefixIndex = this.prefix.get(n.getPrefix());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 184 */     int localNameIndex = this.localName.obtainIndex(n.getLocalPart());
/* 185 */     if (localNameIndex == -1) {
/* 186 */       localNameIndex = this.localName.get(n.getLocalPart());
/*     */     }
/*     */ 
/*     */     
/* 190 */     QualifiedName name = new QualifiedName(n.getPrefix(), n.getNamespaceURI(), n.getLocalPart(), m.getNextIndex(), prefixIndex, namespaceURIIndex, localNameIndex);
/*     */ 
/*     */     
/* 193 */     LocalNameQualifiedNamesMap.Entry entry = null;
/* 194 */     if (this._useLocalNameAsKey) {
/* 195 */       entry = m.obtainEntry(n.getLocalPart());
/*     */     }
/*     */     else {
/*     */       
/* 199 */       String qName = (prefixIndex == -1) ? n.getLocalPart() : (n.getPrefix() + ":" + n.getLocalPart());
/* 200 */       entry = m.obtainEntry(qName);
/*     */     } 
/*     */     
/* 203 */     entry.addQualifiedName(name);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/vocab/SerializerVocabulary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */