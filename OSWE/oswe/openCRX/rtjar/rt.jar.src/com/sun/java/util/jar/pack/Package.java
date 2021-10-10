/*      */ package com.sun.java.util.jar.pack;
/*      */ 
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.SequenceInputStream;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class Package
/*      */ {
/*      */   int verbose;
/*      */   final int magic = -889270259;
/*      */   int default_modtime;
/*      */   int default_options;
/*      */   Version defaultClassVersion;
/*      */   final Version minClassVersion;
/*      */   final Version maxClassVersion;
/*      */   final Version packageVersion;
/*      */   Version observedHighestClassVersion;
/*      */   ConstantPool.IndexGroup cp;
/*      */   public static final Attribute.Layout attrCodeEmpty;
/*      */   public static final Attribute.Layout attrBootstrapMethodsEmpty;
/*      */   public static final Attribute.Layout attrInnerClassesEmpty;
/*      */   public static final Attribute.Layout attrSourceFileSpecial;
/*      */   public static final Map<Attribute.Layout, Attribute> attrDefs;
/*      */   ArrayList<Class> classes;
/*      */   ArrayList<File> files;
/*      */   List<InnerClass> allInnerClasses;
/*      */   Map<ConstantPool.ClassEntry, InnerClass> allInnerClassesByThis;
/*      */   private static final int SLASH_MIN = 46;
/*      */   private static final int SLASH_MAX = 47;
/*      */   private static final int DOLLAR_MIN = 0;
/*      */   private static final int DOLLAR_MAX = 45;
/*      */   
/*      */   public Package() {
/*   66 */     PropMap propMap = Utils.currentPropMap();
/*   67 */     if (propMap != null) {
/*   68 */       this.verbose = propMap.getInteger("com.sun.java.util.jar.pack.verbose");
/*      */     }
/*      */     
/*   71 */     this.magic = -889270259;
/*      */     
/*   73 */     this.default_modtime = 0;
/*   74 */     this.default_options = 0;
/*      */     
/*   76 */     this.defaultClassVersion = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   84 */     this.observedHighestClassVersion = null;
/*      */ 
/*      */ 
/*      */     
/*   88 */     this.cp = new ConstantPool.IndexGroup();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  169 */     this.classes = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  732 */     this.files = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  892 */     this.allInnerClasses = new ArrayList<>(); this.minClassVersion = Constants.JAVA_MIN_CLASS_VERSION; this.maxClassVersion = Constants.JAVA_MAX_CLASS_VERSION; this.packageVersion = null; } public void reset() { this.cp = new ConstantPool.IndexGroup(); this.classes.clear(); this.files.clear(); BandStructure.nextSeqForDebug = 0; this.observedHighestClassVersion = null; } Version getDefaultClassVersion() { return this.defaultClassVersion; } private void setHighestClassVersion() { if (this.observedHighestClassVersion != null) return;  Version version = Constants.JAVA_MIN_CLASS_VERSION; for (Class clazz : this.classes) { Version version1 = clazz.getVersion(); if (version.lessThan(version1)) version = version1;  }  this.observedHighestClassVersion = version; } public Package(Version paramVersion1, Version paramVersion2, Version paramVersion3) { PropMap propMap = Utils.currentPropMap(); if (propMap != null) this.verbose = propMap.getInteger("com.sun.java.util.jar.pack.verbose");  this.magic = -889270259; this.default_modtime = 0; this.default_options = 0; this.defaultClassVersion = null; this.observedHighestClassVersion = null; this.cp = new ConstantPool.IndexGroup(); this.classes = new ArrayList<>(); this.files = new ArrayList<>(); this.allInnerClasses = new ArrayList<>(); this.minClassVersion = (paramVersion1 == null) ? Constants.JAVA_MIN_CLASS_VERSION : paramVersion1; this.maxClassVersion = (paramVersion2 == null) ? Constants.JAVA_MAX_CLASS_VERSION : paramVersion2; this.packageVersion = paramVersion3; }
/*      */   Version getHighestClassVersion() { setHighestClassVersion(); return this.observedHighestClassVersion; }
/*      */   public List<Class> getClasses() { return this.classes; }
/*      */   public final class Class extends Attribute.Holder implements Comparable<Class> {
/*      */     Package.File file;
/*      */     int magic;
/*      */     Package.Version version;
/*      */     ConstantPool.Entry[] cpMap;
/*      */     ConstantPool.ClassEntry thisClass;
/*      */     ConstantPool.ClassEntry superClass;
/*      */     ConstantPool.ClassEntry[] interfaces;
/*      */     ArrayList<Field> fields;
/*      */     ArrayList<Method> methods;
/*      */     ArrayList<Package.InnerClass> innerClasses;
/*      */     ArrayList<ConstantPool.BootstrapMethodEntry> bootstrapMethods;
/*      */     public Package getPackage() { return Package.this; }
/*      */     Class(int param1Int, ConstantPool.ClassEntry param1ClassEntry1, ConstantPool.ClassEntry param1ClassEntry2, ConstantPool.ClassEntry[] param1ArrayOfClassEntry) { this.magic = -889275714; this.version = Package.this.defaultClassVersion; this.flags = param1Int; this.thisClass = param1ClassEntry1; this.superClass = param1ClassEntry2; this.interfaces = param1ArrayOfClassEntry; boolean bool = Package.this.classes.add(this); assert bool; }
/*      */     Class(String param1String) { initFile(Package.this.newStub(param1String)); }
/*      */     List<Field> getFields() { return (this.fields == null) ? Package.noFields : this.fields; }
/*      */     List<Method> getMethods() { return (this.methods == null) ? Package.noMethods : this.methods; }
/*      */     public String getName() { return this.thisClass.stringValue(); }
/*      */     Package.Version getVersion() { return this.version; }
/*      */     public int compareTo(Class param1Class) { String str1 = getName(); String str2 = param1Class.getName(); return str1.compareTo(str2); }
/*      */     String getObviousSourceFile() { return Package.getObviousSourceFile(getName()); }
/*      */     private void transformSourceFile(boolean param1Boolean) { Attribute attribute1 = getAttribute(Package.attrSourceFileSpecial); if (attribute1 == null)
/*      */         return;  String str = getObviousSourceFile(); ArrayList<ConstantPool.Entry> arrayList = new ArrayList(1); attribute1.visitRefs(this, 1, arrayList); ConstantPool.Utf8Entry utf8Entry = (ConstantPool.Utf8Entry)arrayList.get(0); Attribute attribute2 = attribute1; if (utf8Entry == null) {
/*      */         if (param1Boolean) {
/*      */           attribute2 = Attribute.find(0, "SourceFile", "H"); attribute2 = attribute2.addContent(new byte[2]);
/*      */         } else {
/*      */           byte[] arrayOfByte = new byte[2]; utf8Entry = Package.getRefString(str); Object object = null; object = Fixups.addRefWithBytes(object, arrayOfByte, utf8Entry); attribute2 = Package.attrSourceFileSpecial.addContent(arrayOfByte, object);
/*      */         } 
/*      */       } else if (str.equals(utf8Entry.stringValue())) {
/*      */         if (param1Boolean) {
/*      */           attribute2 = Package.attrSourceFileSpecial.addContent(new byte[2]);
/*      */         } else {
/*      */           assert false;
/*      */         } 
/*      */       }  if (attribute2 != attribute1) {
/*      */         if (Package.this.verbose > 2)
/*      */           Utils.log.fine("recoding obvious SourceFile=" + str);  ArrayList<Attribute> arrayList1 = new ArrayList<>(getAttributes()); int i = arrayList1.indexOf(attribute1); arrayList1.set(i, attribute2); setAttributes(arrayList1);
/*      */       }  }
/*      */     void minimizeSourceFile() { transformSourceFile(true); }
/*      */     void expandSourceFile() { transformSourceFile(false); }
/*      */     protected ConstantPool.Entry[] getCPMap() { return this.cpMap; }
/*      */     protected void setCPMap(ConstantPool.Entry[] param1ArrayOfEntry) { this.cpMap = param1ArrayOfEntry; }
/*      */     boolean hasBootstrapMethods() { return (this.bootstrapMethods != null && !this.bootstrapMethods.isEmpty()); }
/*      */     List<ConstantPool.BootstrapMethodEntry> getBootstrapMethods() { return this.bootstrapMethods; }
/*      */     ConstantPool.BootstrapMethodEntry[] getBootstrapMethodMap() { return hasBootstrapMethods() ? this.bootstrapMethods.<ConstantPool.BootstrapMethodEntry>toArray(new ConstantPool.BootstrapMethodEntry[this.bootstrapMethods.size()]) : null; }
/*      */     void setBootstrapMethods(Collection<ConstantPool.BootstrapMethodEntry> param1Collection) { assert this.bootstrapMethods == null; this.bootstrapMethods = new ArrayList<>(param1Collection); }
/*      */     boolean hasInnerClasses() { return (this.innerClasses != null); }
/*      */     List<Package.InnerClass> getInnerClasses() { return this.innerClasses; }
/*      */     public void setInnerClasses(Collection<Package.InnerClass> param1Collection) { this.innerClasses = (param1Collection == null) ? null : new ArrayList<>(param1Collection); Attribute attribute = getAttribute(Package.attrInnerClassesEmpty); if (this.innerClasses != null && attribute == null) {
/*      */         addAttribute(Package.attrInnerClassesEmpty.canonicalInstance());
/*      */       } else if (this.innerClasses == null && attribute != null) {
/*      */         removeAttribute(attribute);
/*      */       }  }
/*      */     public List<Package.InnerClass> computeGloballyImpliedICs() { HashSet<ConstantPool.Entry> hashSet1 = new HashSet(); ArrayList<Package.InnerClass> arrayList1 = this.innerClasses; this.innerClasses = null; visitRefs(0, hashSet1); this.innerClasses = arrayList1; ConstantPool.completeReferencesIn(hashSet1, true); HashSet<ConstantPool.Entry> hashSet2 = new HashSet(); for (ConstantPool.Entry entry : hashSet1) {
/*      */         if (!(entry instanceof ConstantPool.ClassEntry))
/*      */           continue;  while (entry != null) {
/*      */           Package.InnerClass innerClass = Package.this.getGlobalInnerClass(entry); if (innerClass == null || !hashSet2.add(entry))
/*      */             break;  entry = innerClass.outerClass;
/*      */         } 
/*      */       }  ArrayList<Package.InnerClass> arrayList2 = new ArrayList(); for (Package.InnerClass innerClass : Package.this.allInnerClasses) {
/*      */         if (hashSet2.contains(innerClass.thisClass) || innerClass.outerClass == this.thisClass) {
/*      */           if (Package.this.verbose > 1)
/*      */             Utils.log.fine("Relevant IC: " + innerClass);  arrayList2.add(innerClass);
/*      */         } 
/*      */       }  return arrayList2; }
/*      */     private List<Package.InnerClass> computeICdiff() { List<Package.InnerClass> list1 = computeGloballyImpliedICs(); List<Package.InnerClass> list2 = getInnerClasses(); if (list2 == null)
/*      */         list2 = Collections.emptyList();  if (list2.isEmpty())
/*      */         return list1;  if (list1.isEmpty())
/*      */         return list2;  HashSet<Package.InnerClass> hashSet = new HashSet<>(list2); hashSet.retainAll(new HashSet(list1)); list1.addAll(list2); list1.removeAll(hashSet); return list1; }
/*      */     void minimizeLocalICs() { List<Package.InnerClass> list2, list1 = computeICdiff(); ArrayList<Package.InnerClass> arrayList = this.innerClasses; if (list1.isEmpty()) {
/*      */         list2 = null; if (arrayList != null && arrayList.isEmpty())
/*      */           if (Package.this.verbose > 0)
/*      */             Utils.log.info("Warning: Dropping empty InnerClasses attribute from " + this);  
/*      */       } else if (arrayList == null) {
/*      */         list2 = Collections.emptyList();
/*      */       } else {
/*      */         list2 = list1;
/*      */       }  setInnerClasses(list2); if (Package.this.verbose > 1 && list2 != null)
/*      */         Utils.log.fine("keeping local ICs in " + this + ": " + list2);  }
/*      */     int expandLocalICs() { List<Package.InnerClass> list; boolean bool; ArrayList<Package.InnerClass> arrayList = this.innerClasses; if (arrayList == null) {
/*      */         List<Package.InnerClass> list1 = computeGloballyImpliedICs(); if (list1.isEmpty()) {
/*      */           list = null; bool = false;
/*      */         } else {
/*      */           list = list1; bool = true;
/*      */         } 
/*      */       } else if (arrayList.isEmpty()) {
/*      */         list = null; bool = false;
/*      */       } else {
/*      */         list = computeICdiff(); bool = list.containsAll(arrayList) ? true : true;
/*      */       }  setInnerClasses(list); return bool; }
/*      */     public abstract class Member extends Attribute.Holder implements Comparable<Member> {
/*      */       ConstantPool.DescriptorEntry descriptor;
/*      */       protected Member(int param2Int, ConstantPool.DescriptorEntry param2DescriptorEntry) { this.flags = param2Int; this.descriptor = param2DescriptorEntry; }
/*      */       public Package.Class thisClass() { return Package.Class.this; }
/*      */       public ConstantPool.DescriptorEntry getDescriptor() { return this.descriptor; }
/*      */       public String getName() { return this.descriptor.nameRef.stringValue(); }
/*      */       public String getType() { return this.descriptor.typeRef.stringValue(); }
/*      */       protected ConstantPool.Entry[] getCPMap() { return Package.Class.this.cpMap; }
/*      */       protected void visitRefs(int param2Int, Collection<ConstantPool.Entry> param2Collection) { if (Package.this.verbose > 2)
/*      */           Utils.log.fine("visitRefs " + this);  if (param2Int == 0) {
/*      */           param2Collection.add(this.descriptor.nameRef); param2Collection.add(this.descriptor.typeRef);
/*      */         } else {
/*      */           param2Collection.add(this.descriptor);
/*      */         }  super.visitRefs(param2Int, param2Collection); }
/*      */       public String toString() { return Package.Class.this + "." + this.descriptor.prettyString(); } }
/*      */     public class Field extends Member {
/*      */       int order;
/*      */       public Field(int param2Int, ConstantPool.DescriptorEntry param2DescriptorEntry) { super(param2Int, param2DescriptorEntry); assert !param2DescriptorEntry.isMethod(); if (Package.Class.this.fields == null)
/*      */           Package.Class.this.fields = new ArrayList<>();  boolean bool = Package.Class.this.fields.add(this); assert bool; this.order = Package.Class.this.fields.size(); }
/*      */       public byte getLiteralTag() { return this.descriptor.getLiteralTag(); }
/*      */       public int compareTo(Package.Class.Member param2Member) { Field field = (Field)param2Member; return this.order - field.order; } }
/*      */     public class Method extends Member {
/*      */       Code code;
/*      */       public Method(int param2Int, ConstantPool.DescriptorEntry param2DescriptorEntry) { super(param2Int, param2DescriptorEntry); assert param2DescriptorEntry.isMethod(); if (Package.Class.this.methods == null)
/*      */           Package.Class.this.methods = new ArrayList<>();  boolean bool = Package.Class.this.methods.add(this); assert bool; }
/*      */       public void trimToSize() { super.trimToSize(); if (this.code != null)
/*      */           this.code.trimToSize();  }
/*      */       public int getArgumentSize() { int i = this.descriptor.typeRef.computeSize(true); byte b = Modifier.isStatic(this.flags) ? 0 : 1; return b + i; } public int compareTo(Package.Class.Member param2Member) { Method method = (Method)param2Member; return getDescriptor().compareTo(method.getDescriptor()); } public void strip(String param2String) { if ("Code".equals(param2String))
/*      */           this.code = null;  if (this.code != null)
/*      */           this.code.strip(param2String);  super.strip(param2String); } protected void visitRefs(int param2Int, Collection<ConstantPool.Entry> param2Collection) { super.visitRefs(param2Int, param2Collection); if (this.code != null) {
/*      */           if (param2Int == 0)
/*      */             param2Collection.add(Package.getRefString("Code"));  this.code.visitRefs(param2Int, param2Collection);
/*      */         }  } } public void trimToSize() { super.trimToSize(); for (byte b = 0; b <= 1; b++) {
/*      */         ArrayList arrayList = (ArrayList)((b == 0) ? this.fields : this.methods); if (arrayList != null) {
/*      */           arrayList.trimToSize(); for (Member member : arrayList)
/*      */             member.trimToSize(); 
/*      */         } 
/*      */       }  if (this.innerClasses != null)
/*      */         this.innerClasses.trimToSize();  } public void strip(String param1String) { if ("InnerClass".equals(param1String))
/*      */         this.innerClasses = null;  for (byte b = 0; b <= 1; b++) {
/*      */         ArrayList arrayList = (ArrayList)((b == 0) ? this.fields : this.methods); if (arrayList != null)
/*      */           for (Member member : arrayList)
/*      */             member.strip(param1String);  
/*      */       }  super.strip(param1String); } protected void visitRefs(int param1Int, Collection<ConstantPool.Entry> param1Collection) { if (Package.this.verbose > 2)
/*      */         Utils.log.fine("visitRefs " + this);  param1Collection.add(this.thisClass); param1Collection.add(this.superClass); param1Collection.addAll(Arrays.asList((ConstantPool.Entry[])this.interfaces)); for (byte b = 0; b <= 1; b++) {
/*      */         ArrayList arrayList = (ArrayList)((b == 0) ? this.fields : this.methods); if (arrayList != null)
/*      */           for (Member member : arrayList) {
/*      */             boolean bool = false; try {
/*      */               member.visitRefs(param1Int, param1Collection); bool = true;
/*      */             } finally {
/*      */               if (!bool)
/*      */                 Utils.log.warning("Error scanning " + member); 
/*      */             } 
/*      */           }  
/*      */       }  visitInnerClassRefs(param1Int, param1Collection); super.visitRefs(param1Int, param1Collection); } protected void visitInnerClassRefs(int param1Int, Collection<ConstantPool.Entry> param1Collection) { Package.visitInnerClassRefs(this.innerClasses, param1Int, param1Collection); } void finishReading() { trimToSize(); maybeChooseFileName(); } public void initFile(Package.File param1File) { assert this.file == null; if (param1File == null)
/*      */         param1File = Package.this.newStub(canonicalFileName());  this.file = param1File; assert param1File.isClassStub(); param1File.stubClass = this; maybeChooseFileName(); } public void maybeChooseFileName() { if (this.thisClass == null)
/*      */         return;  String str = canonicalFileName(); if (this.file.nameString.equals(""))
/*      */         this.file.nameString = str;  if (this.file.nameString.equals(str)) {
/*      */         this.file.name = Package.getRefString(""); return;
/*      */       }  if (this.file.name == null)
/*      */         this.file.name = Package.getRefString(this.file.nameString);  } public String canonicalFileName() { if (this.thisClass == null)
/*      */         return null;  return this.thisClass.stringValue() + ".class"; } public java.io.File getFileName(java.io.File param1File) { String str1 = this.file.name.stringValue(); if (str1.equals(""))
/*      */         str1 = canonicalFileName();  String str2 = str1.replace('/', java.io.File.separatorChar); return new java.io.File(param1File, str2); } public java.io.File getFileName() { return getFileName((java.io.File)null); } public String toString() { return this.thisClass.stringValue(); } } static { HashMap<Object, Object> hashMap = new HashMap<>(3); attrCodeEmpty = Attribute.define((Map)hashMap, 2, "Code", "").layout(); attrBootstrapMethodsEmpty = Attribute.define((Map)hashMap, 0, "BootstrapMethods", "").layout(); attrInnerClassesEmpty = Attribute.define((Map)hashMap, 0, "InnerClasses", "").layout(); attrSourceFileSpecial = Attribute.define((Map)hashMap, 0, "SourceFile", "RUNH").layout();
/*      */     attrDefs = Collections.unmodifiableMap(hashMap);
/* 1049 */     assert lastIndexOf(0, 45, "x$$y$", 4) == 2;
/* 1050 */     assert lastIndexOf(46, 47, "x//y/", 4) == 2; } public class Field extends Class.Member {
/*      */     int order; public Field(int param1Int, ConstantPool.DescriptorEntry param1DescriptorEntry) { super((Package.Class)Package.this, param1Int, param1DescriptorEntry); assert !param1DescriptorEntry.isMethod(); if (((Package.Class)Package.this).fields == null) ((Package.Class)Package.this).fields = new ArrayList<>();  boolean bool = ((Package.Class)Package.this).fields.add(this); assert bool; this.order = ((Package.Class)Package.this).fields.size(); } public byte getLiteralTag() { return this.descriptor.getLiteralTag(); } public int compareTo(Package.Class.Member param1Member) { Field field = (Field)param1Member; return this.order - field.order; } } public class Method extends Class.Member {
/*      */     Code code; public Method(int param1Int, ConstantPool.DescriptorEntry param1DescriptorEntry) { super((Package.Class)Package.this, param1Int, param1DescriptorEntry); assert param1DescriptorEntry.isMethod(); if (((Package.Class)Package.this).methods == null) ((Package.Class)Package.this).methods = new ArrayList<>();  boolean bool = ((Package.Class)Package.this).methods.add(this); assert bool; } public void trimToSize() { super.trimToSize(); if (this.code != null) this.code.trimToSize();  } public int getArgumentSize() { int i = this.descriptor.typeRef.computeSize(true); byte b = Modifier.isStatic(this.flags) ? 0 : 1; return b + i; } public int compareTo(Package.Class.Member param1Member) { Method method = (Method)param1Member; return getDescriptor().compareTo(method.getDescriptor()); } public void strip(String param1String) { if ("Code".equals(param1String)) this.code = null;  if (this.code != null) this.code.strip(param1String);  super.strip(param1String); } protected void visitRefs(int param1Int, Collection<ConstantPool.Entry> param1Collection) { super.visitRefs(param1Int, param1Collection); if (this.code != null) { if (param1Int == 0) param1Collection.add(Package.getRefString("Code"));  this.code.visitRefs(param1Int, param1Collection); }  } } void addClass(Class paramClass) { assert paramClass.getPackage() == this; boolean bool = this.classes.add(paramClass); assert bool; if (paramClass.file == null) paramClass.initFile((File)null);  addFile(paramClass.file); } public List<File> getFiles() { return this.files; }
/*      */   public List<File> getClassStubs() { ArrayList<File> arrayList = new ArrayList(this.classes.size()); for (Class clazz : this.classes) { assert clazz.file.isClassStub(); arrayList.add(clazz.file); }  return arrayList; }
/* 1054 */   private static int lastIndexOf(int paramInt1, int paramInt2, String paramString, int paramInt3) { for (int i = paramInt3; --i >= 0; ) {
/* 1055 */       char c = paramString.charAt(i);
/* 1056 */       if (c >= paramInt1 && c <= paramInt2) {
/* 1057 */         return i;
/*      */       }
/*      */     } 
/* 1060 */     return -1; } public final class File implements Comparable<File> {
/*      */     String nameString; ConstantPool.Utf8Entry name; int modtime = 0; int options = 0; Package.Class stubClass; ArrayList<byte[]> prepend = (ArrayList)new ArrayList<>(); ByteArrayOutputStream append = new ByteArrayOutputStream(); File(ConstantPool.Utf8Entry param1Utf8Entry) { this.name = param1Utf8Entry; this.nameString = param1Utf8Entry.stringValue(); } File(String param1String) { param1String = Package.fixupFileName(param1String); this.name = Package.getRefString(param1String); this.nameString = this.name.stringValue(); } public boolean isDirectory() { return this.nameString.endsWith("/"); } public boolean isClassStub() { return ((this.options & 0x2) != 0); } public Package.Class getStubClass() { assert isClassStub(); assert this.stubClass != null; return this.stubClass; } public boolean isTrivialClassStub() { return (isClassStub() && this.name.stringValue().equals("") && (this.modtime == 0 || this.modtime == Package.this.default_modtime) && (this.options & 0xFFFFFFFD) == 0); } public boolean equals(Object param1Object) { if (param1Object == null || param1Object.getClass() != File.class) return false;  File file = (File)param1Object; return file.nameString.equals(this.nameString); } public int hashCode() { return this.nameString.hashCode(); } public int compareTo(File param1File) { return this.nameString.compareTo(param1File.nameString); } public String toString() { return this.nameString + "{" + (isClassStub() ? "*" : "") + (BandStructure.testBit(this.options, 1) ? "@" : "") + ((this.modtime == 0) ? "" : ("M" + this.modtime)) + ((getFileLength() == 0L) ? "" : ("[" + getFileLength() + "]")) + "}"; } public java.io.File getFileName() { return getFileName(null); } public java.io.File getFileName(java.io.File param1File) { String str1 = this.nameString; String str2 = str1.replace('/', java.io.File.separatorChar); return new java.io.File(param1File, str2); } public void addBytes(byte[] param1ArrayOfbyte) { addBytes(param1ArrayOfbyte, 0, param1ArrayOfbyte.length); } public void addBytes(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) { if ((this.append.size() | param1Int2) << 2 < 0) { this.prepend.add(this.append.toByteArray()); this.append.reset(); }  this.append.write(param1ArrayOfbyte, param1Int1, param1Int2); } public long getFileLength() { long l = 0L; if (this.prepend == null || this.append == null) return 0L;  for (byte[] arrayOfByte : this.prepend) l += arrayOfByte.length;  l += this.append.size(); return l; } public void writeTo(OutputStream param1OutputStream) throws IOException { if (this.prepend == null || this.append == null) return;  for (byte[] arrayOfByte : this.prepend) param1OutputStream.write(arrayOfByte);  this.append.writeTo(param1OutputStream); } public void readFrom(InputStream param1InputStream) throws IOException { byte[] arrayOfByte = new byte[65536]; int i; while ((i = param1InputStream.read(arrayOfByte)) > 0) addBytes(arrayOfByte, 0, i);  } public InputStream getInputStream() { ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.append.toByteArray()); if (this.prepend.isEmpty()) return byteArrayInputStream;  ArrayList<ByteArrayInputStream> arrayList = new ArrayList(this.prepend.size() + 1); for (byte[] arrayOfByte : this.prepend) arrayList.add(new ByteArrayInputStream(arrayOfByte));  arrayList.add(byteArrayInputStream); return new SequenceInputStream(Collections.enumeration((Collection)arrayList)); } protected void visitRefs(int param1Int, Collection<ConstantPool.Entry> param1Collection) { assert this.name != null; param1Collection.add(this.name); } } File newStub(String paramString) { File file = new File(paramString); file.options |= 0x2; file.prepend = null; file.append = null; return file; } private static String fixupFileName(String paramString) { String str = paramString.replace(java.io.File.separatorChar, '/'); if (str.startsWith("/")) throw new IllegalArgumentException("absolute file name " + str);  return str; } void addFile(File paramFile) { boolean bool = this.files.add(paramFile); assert bool; } public List<InnerClass> getAllInnerClasses() { return this.allInnerClasses; } public void setAllInnerClasses(Collection<InnerClass> paramCollection) { assert paramCollection != this.allInnerClasses; this.allInnerClasses.clear(); this.allInnerClasses.addAll(paramCollection); this.allInnerClassesByThis = new HashMap<>(this.allInnerClasses.size()); for (InnerClass innerClass : this.allInnerClasses) { Object object = this.allInnerClassesByThis.put(innerClass.thisClass, innerClass); assert object == null; }  } public InnerClass getGlobalInnerClass(ConstantPool.Entry paramEntry) { assert paramEntry instanceof ConstantPool.ClassEntry; return this.allInnerClassesByThis.get(paramEntry); } static class InnerClass implements Comparable<InnerClass> {
/*      */     final ConstantPool.ClassEntry thisClass; final ConstantPool.ClassEntry outerClass; final ConstantPool.Utf8Entry name; final int flags; final boolean predictable; InnerClass(ConstantPool.ClassEntry param1ClassEntry1, ConstantPool.ClassEntry param1ClassEntry2, ConstantPool.Utf8Entry param1Utf8Entry, int param1Int) { this.thisClass = param1ClassEntry1; this.outerClass = param1ClassEntry2; this.name = param1Utf8Entry; this.flags = param1Int; this.predictable = computePredictable(); } private boolean computePredictable() { String[] arrayOfString = Package.parseInnerClassName(this.thisClass.stringValue()); if (arrayOfString == null) return false;  String str1 = arrayOfString[0]; String str2 = arrayOfString[2]; String str3 = (this.name == null) ? null : this.name.stringValue(); String str4 = (this.outerClass == null) ? null : this.outerClass.stringValue(); return (str2 == str3 && str1 == str4); } public boolean equals(Object param1Object) { if (param1Object == null || param1Object.getClass() != InnerClass.class) return false;  InnerClass innerClass = (InnerClass)param1Object; return (eq(this.thisClass, innerClass.thisClass) && eq(this.outerClass, innerClass.outerClass) && eq(this.name, innerClass.name) && this.flags == innerClass.flags); } private static boolean eq(Object param1Object1, Object param1Object2) { return (param1Object1 == null) ? ((param1Object2 == null)) : param1Object1.equals(param1Object2); } public int hashCode() { return this.thisClass.hashCode(); } public int compareTo(InnerClass param1InnerClass) { return this.thisClass.compareTo(param1InnerClass.thisClass); } protected void visitRefs(int param1Int, Collection<ConstantPool.Entry> param1Collection) { param1Collection.add(this.thisClass); if (param1Int == 0 || !this.predictable) { param1Collection.add(this.outerClass); param1Collection.add(this.name); }  } public String toString() { return this.thisClass.stringValue(); } } private static void visitInnerClassRefs(Collection<InnerClass> paramCollection, int paramInt, Collection<ConstantPool.Entry> paramCollection1) { if (paramCollection == null) return;  if (paramInt == 0) paramCollection1.add(getRefString("InnerClasses"));  if (paramCollection.size() > 0) for (InnerClass innerClass : paramCollection) innerClass.visitRefs(paramInt, paramCollection1);   }
/*      */   static String[] parseInnerClassName(String paramString) { String str1, str2, str3; int i, k = paramString.length(); int m = lastIndexOf(46, 47, paramString, paramString.length()) + 1; int j = lastIndexOf(0, 45, paramString, paramString.length()); if (j < m) return null;  if (isDigitString(paramString, j + 1, k)) { str2 = paramString.substring(j + 1, k); str3 = null; i = j; } else if ((i = lastIndexOf(0, 45, paramString, j - 1)) > m && isDigitString(paramString, i + 1, j)) { str2 = paramString.substring(i + 1, j); str3 = paramString.substring(j + 1, k).intern(); } else { i = j; str2 = null; str3 = paramString.substring(j + 1, k).intern(); }  if (str2 == null) { str1 = paramString.substring(0, i).intern(); } else { str1 = null; }  return new String[] { str1, str2, str3 }; }
/* 1064 */   private static boolean isDigitString(String paramString, int paramInt1, int paramInt2) { if (paramInt1 == paramInt2) return false; 
/* 1065 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 1066 */       char c = paramString.charAt(i);
/* 1067 */       if (c < '0' || c > '9') return false; 
/*      */     } 
/* 1069 */     return true; }
/*      */ 
/*      */   
/*      */   static String getObviousSourceFile(String paramString) {
/* 1073 */     String str = paramString;
/* 1074 */     int i = lastIndexOf(46, 47, str, str.length()) + 1;
/* 1075 */     str = str.substring(i);
/* 1076 */     int j = str.length();
/*      */     
/*      */     do {
/* 1079 */       int k = lastIndexOf(0, 45, str, j - 1);
/* 1080 */       if (k < 0)
/*      */         break; 
/* 1082 */       j = k;
/* 1083 */     } while (j != 0);
/*      */ 
/*      */     
/* 1086 */     return str.substring(0, j) + ".java";
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
/*      */   static ConstantPool.Utf8Entry getRefString(String paramString) {
/* 1100 */     return ConstantPool.getUtf8Entry(paramString);
/*      */   }
/*      */   
/*      */   static ConstantPool.LiteralEntry getRefLiteral(Comparable<?> paramComparable) {
/* 1104 */     return ConstantPool.getLiteralEntry(paramComparable);
/*      */   }
/*      */ 
/*      */   
/*      */   void stripAttributeKind(String paramString) {
/* 1109 */     if (this.verbose > 0)
/* 1110 */       Utils.log.info("Stripping " + paramString.toLowerCase() + " data and attributes..."); 
/* 1111 */     switch (paramString) {
/*      */       case "Debug":
/* 1113 */         strip("SourceFile");
/* 1114 */         strip("LineNumberTable");
/* 1115 */         strip("LocalVariableTable");
/* 1116 */         strip("LocalVariableTypeTable");
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case "Compile":
/* 1123 */         strip("Deprecated");
/* 1124 */         strip("Synthetic");
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case "Exceptions":
/* 1130 */         strip("Exceptions");
/*      */         break;
/*      */       case "Constant":
/* 1133 */         stripConstantFields();
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void trimToSize() {
/* 1139 */     this.classes.trimToSize();
/* 1140 */     for (Class clazz : this.classes) {
/* 1141 */       clazz.trimToSize();
/*      */     }
/* 1143 */     this.files.trimToSize();
/*      */   }
/*      */   
/*      */   public void strip(String paramString) {
/* 1147 */     for (Class clazz : this.classes) {
/* 1148 */       clazz.strip(paramString);
/*      */     }
/*      */   }
/*      */   
/*      */   public void stripConstantFields() {
/* 1153 */     for (Class clazz : this.classes) {
/* 1154 */       for (Iterator<Class.Field> iterator = clazz.fields.iterator(); iterator.hasNext(); ) {
/* 1155 */         Class.Field field = iterator.next();
/* 1156 */         if (Modifier.isFinal(field.flags) && 
/*      */           
/* 1158 */           Modifier.isStatic(field.flags) && field
/* 1159 */           .getAttribute("ConstantValue") != null && 
/* 1160 */           !field.getName().startsWith("serial") && 
/* 1161 */           this.verbose > 2) {
/* 1162 */           Utils.log.fine(">> Strip " + this + " ConstantValue");
/* 1163 */           iterator.remove();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void visitRefs(int paramInt, Collection<ConstantPool.Entry> paramCollection) {
/* 1171 */     for (Class clazz : this.classes) {
/* 1172 */       clazz.visitRefs(paramInt, paramCollection);
/*      */     }
/* 1174 */     if (paramInt != 0) {
/* 1175 */       for (File file : this.files) {
/* 1176 */         file.visitRefs(paramInt, paramCollection);
/*      */       }
/* 1178 */       visitInnerClassRefs(this.allInnerClasses, paramInt, paramCollection);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void reorderFiles(boolean paramBoolean1, boolean paramBoolean2) {
/* 1189 */     if (!paramBoolean1)
/*      */     {
/* 1191 */       Collections.sort(this.classes);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1199 */     List<File> list = getClassStubs();
/* 1200 */     for (Iterator<File> iterator = this.files.iterator(); iterator.hasNext(); ) {
/* 1201 */       File file = iterator.next();
/* 1202 */       if (file.isClassStub() || (paramBoolean2 && file
/* 1203 */         .isDirectory())) {
/* 1204 */         iterator.remove();
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1213 */     Collections.sort(this.files, new Comparator<File>()
/*      */         {
/*      */           public int compare(Package.File param1File1, Package.File param1File2) {
/* 1216 */             String str1 = param1File1.nameString;
/* 1217 */             String str2 = param1File2.nameString;
/* 1218 */             if (str1.equals(str2)) return 0; 
/* 1219 */             if ("META-INF/MANIFEST.MF".equals(str1)) return -1; 
/* 1220 */             if ("META-INF/MANIFEST.MF".equals(str2)) return 1;
/*      */             
/* 1222 */             String str3 = str1.substring(1 + str1.lastIndexOf('/'));
/* 1223 */             String str4 = str2.substring(1 + str2.lastIndexOf('/'));
/*      */             
/* 1225 */             String str5 = str3.substring(1 + str3.lastIndexOf('.'));
/* 1226 */             String str6 = str4.substring(1 + str4.lastIndexOf('.'));
/*      */ 
/*      */             
/* 1229 */             int i = str5.compareTo(str6);
/* 1230 */             if (i != 0) return i; 
/* 1231 */             i = str1.compareTo(str2);
/* 1232 */             return i;
/*      */           }
/*      */         });
/*      */ 
/*      */     
/* 1237 */     this.files.addAll(list);
/*      */   }
/*      */ 
/*      */   
/*      */   void trimStubs() {
/* 1242 */     for (ListIterator<File> listIterator = this.files.listIterator(this.files.size()); listIterator.hasPrevious(); ) {
/* 1243 */       File file = listIterator.previous();
/* 1244 */       if (!file.isTrivialClassStub()) {
/* 1245 */         if (this.verbose > 1)
/* 1246 */           Utils.log.fine("Keeping last non-trivial " + file); 
/*      */         break;
/*      */       } 
/* 1249 */       if (this.verbose > 2)
/* 1250 */         Utils.log.fine("Removing trivial " + file); 
/* 1251 */       listIterator.remove();
/*      */     } 
/*      */     
/* 1254 */     if (this.verbose > 0) {
/* 1255 */       Utils.log.info("Transmitting " + this.files.size() + " files, including per-file data for " + getClassStubs().size() + " classes out of " + this.classes.size());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   void buildGlobalConstantPool(Set<ConstantPool.Entry> paramSet) {
/* 1261 */     if (this.verbose > 1)
/* 1262 */       Utils.log.fine("Checking for unused CP entries"); 
/* 1263 */     paramSet.add(getRefString(""));
/* 1264 */     visitRefs(1, paramSet);
/* 1265 */     ConstantPool.completeReferencesIn(paramSet, false);
/* 1266 */     if (this.verbose > 1)
/* 1267 */       Utils.log.fine("Sorting CP entries"); 
/* 1268 */     ConstantPool.Index index = ConstantPool.makeIndex("unsorted", paramSet);
/* 1269 */     ConstantPool.Index[] arrayOfIndex = ConstantPool.partitionByTag(index); byte b;
/* 1270 */     for (b = 0; b < ConstantPool.TAGS_IN_ORDER.length; b++) {
/* 1271 */       byte b1 = ConstantPool.TAGS_IN_ORDER[b];
/*      */       
/* 1273 */       ConstantPool.Index index1 = arrayOfIndex[b1];
/* 1274 */       if (index1 != null) {
/* 1275 */         ConstantPool.sort(index1);
/* 1276 */         this.cp.initIndexByTag(b1, index1);
/* 1277 */         arrayOfIndex[b1] = null;
/*      */       } 
/* 1279 */     }  for (b = 0; b < arrayOfIndex.length; b++) {
/* 1280 */       ConstantPool.Index index1 = arrayOfIndex[b];
/* 1281 */       assert index1 == null;
/*      */     } 
/* 1283 */     for (b = 0; b < ConstantPool.TAGS_IN_ORDER.length; b++) {
/* 1284 */       byte b1 = ConstantPool.TAGS_IN_ORDER[b];
/* 1285 */       ConstantPool.Index index1 = this.cp.getIndexByTag(b1);
/* 1286 */       assert index1.assertIsSorted();
/* 1287 */       if (this.verbose > 2) Utils.log.fine(index1.dumpString());
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   void ensureAllClassFiles() {
/* 1293 */     HashSet<File> hashSet = new HashSet<>(this.files);
/* 1294 */     for (Class clazz : this.classes) {
/*      */       
/* 1296 */       if (!hashSet.contains(clazz.file))
/* 1297 */         this.files.add(clazz.file); 
/*      */     } 
/*      */   }
/*      */   
/* 1301 */   static final List<Object> noObjects = Arrays.asList(new Object[0]);
/* 1302 */   static final List<Class.Field> noFields = Arrays.asList(new Class.Field[0]);
/* 1303 */   static final List<Class.Method> noMethods = Arrays.asList(new Class.Method[0]);
/* 1304 */   static final List<InnerClass> noInnerClasses = Arrays.asList(new InnerClass[0]);
/*      */   
/*      */   protected static final class Version
/*      */   {
/*      */     public final short major;
/*      */     public final short minor;
/*      */     
/*      */     private Version(short param1Short1, short param1Short2) {
/* 1312 */       this.major = param1Short1;
/* 1313 */       this.minor = param1Short2;
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1317 */       return this.major + "." + this.minor;
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 1321 */       return (param1Object instanceof Version && this.major == ((Version)param1Object).major && this.minor == ((Version)param1Object).minor);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int intValue() {
/* 1327 */       return (this.major << 16) + this.minor;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1331 */       return (this.major << 16) + 7 + this.minor;
/*      */     }
/*      */     
/*      */     public static Version of(int param1Int1, int param1Int2) {
/* 1335 */       return new Version((short)param1Int1, (short)param1Int2);
/*      */     }
/*      */     
/*      */     public static Version of(byte[] param1ArrayOfbyte) {
/* 1339 */       int i = (param1ArrayOfbyte[0] & 0xFF) << 8 | param1ArrayOfbyte[1] & 0xFF;
/* 1340 */       int j = (param1ArrayOfbyte[2] & 0xFF) << 8 | param1ArrayOfbyte[3] & 0xFF;
/* 1341 */       return new Version((short)j, (short)i);
/*      */     }
/*      */     
/*      */     public static Version of(int param1Int) {
/* 1345 */       short s1 = (short)param1Int;
/* 1346 */       short s2 = (short)(param1Int >>> 16);
/* 1347 */       return new Version(s2, s1);
/*      */     }
/*      */     
/*      */     public static Version makeVersion(PropMap param1PropMap, String param1String) {
/* 1351 */       int i = param1PropMap.getInteger("com.sun.java.util.jar.pack." + param1String + ".minver", -1);
/*      */       
/* 1353 */       int j = param1PropMap.getInteger("com.sun.java.util.jar.pack." + param1String + ".majver", -1);
/*      */       
/* 1355 */       return (i >= 0 && j >= 0) ? of(j, i) : null;
/*      */     }
/*      */     public byte[] asBytes() {
/* 1358 */       return new byte[] { (byte)(this.minor >> 8), (byte)this.minor, (byte)(this.major >> 8), (byte)this.major };
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int compareTo(Version param1Version) {
/* 1365 */       return intValue() - param1Version.intValue();
/*      */     }
/*      */     
/*      */     public boolean lessThan(Version param1Version) {
/* 1369 */       return (compareTo(param1Version) < 0);
/*      */     }
/*      */     
/*      */     public boolean greaterThan(Version param1Version) {
/* 1373 */       return (compareTo(param1Version) > 0);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/util/jar/pack/Package.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */