/*      */ package com.sun.java.util.jar.pack;
/*      */ 
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class Attribute
/*      */   implements Comparable<Attribute>
/*      */ {
/*      */   Layout def;
/*      */   byte[] bytes;
/*      */   Object fixups;
/*      */   
/*      */   public String name() {
/*   55 */     return this.def.name();
/*   56 */   } public Layout layout() { return this.def; }
/*   57 */   public byte[] bytes() { return this.bytes; }
/*   58 */   public int size() { return this.bytes.length; } public ConstantPool.Entry getNameRef() {
/*   59 */     return this.def.getNameRef();
/*      */   }
/*      */   private Attribute(Attribute paramAttribute) {
/*   62 */     this.def = paramAttribute.def;
/*   63 */     this.bytes = paramAttribute.bytes;
/*   64 */     this.fixups = paramAttribute.fixups;
/*      */   }
/*      */   
/*      */   public Attribute(Layout paramLayout, byte[] paramArrayOfbyte, Object paramObject) {
/*   68 */     this.def = paramLayout;
/*   69 */     this.bytes = paramArrayOfbyte;
/*   70 */     this.fixups = paramObject;
/*   71 */     Fixups.setBytes(paramObject, paramArrayOfbyte);
/*      */   }
/*      */   public Attribute(Layout paramLayout, byte[] paramArrayOfbyte) {
/*   74 */     this(paramLayout, paramArrayOfbyte, null);
/*      */   }
/*      */   
/*      */   public Attribute addContent(byte[] paramArrayOfbyte, Object paramObject) {
/*   78 */     assert isCanonical();
/*   79 */     if (paramArrayOfbyte.length == 0 && paramObject == null)
/*   80 */       return this; 
/*   81 */     Attribute attribute = new Attribute(this);
/*   82 */     attribute.bytes = paramArrayOfbyte;
/*   83 */     attribute.fixups = paramObject;
/*   84 */     Fixups.setBytes(paramObject, paramArrayOfbyte);
/*   85 */     return attribute;
/*      */   }
/*      */   public Attribute addContent(byte[] paramArrayOfbyte) {
/*   88 */     return addContent(paramArrayOfbyte, null);
/*      */   }
/*      */   
/*      */   public void finishRefs(ConstantPool.Index paramIndex) {
/*   92 */     if (this.fixups != null) {
/*   93 */       Fixups.finishRefs(this.fixups, this.bytes, paramIndex);
/*   94 */       this.fixups = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isCanonical() {
/*   99 */     return (this == this.def.canon);
/*      */   }
/*      */ 
/*      */   
/*      */   public int compareTo(Attribute paramAttribute) {
/*  104 */     return this.def.compareTo(paramAttribute.def);
/*      */   }
/*      */   
/*  107 */   private static final Map<List<Attribute>, List<Attribute>> canonLists = new HashMap<>();
/*  108 */   private static final Map<Layout, Attribute> attributes = new HashMap<>();
/*  109 */   private static final Map<Layout, Attribute> standardDefs = new HashMap<>(); static final byte EK_INT = 1; static final byte EK_BCI = 2; static final byte EK_BCO = 3; static final byte EK_FLAG = 4; static final byte EK_REPL = 5; static final byte EK_REF = 6; static final byte EK_UN = 7; static final byte EK_CASE = 8; static final byte EK_CALL = 9; static final byte EK_CBLE = 10; static final byte EF_SIGN = 1;
/*      */   static final byte EF_DELTA = 2;
/*      */   static final byte EF_NULL = 4;
/*      */   static final byte EF_BACK = 8;
/*      */   static final int NO_BAND_INDEX = -1;
/*      */   
/*      */   public static List<Attribute> getCanonList(List<Attribute> paramList) {
/*  116 */     synchronized (canonLists) {
/*  117 */       List<Attribute> list = canonLists.get(paramList);
/*  118 */       if (list == null) {
/*  119 */         list = new ArrayList(paramList.size());
/*  120 */         list.addAll(paramList);
/*  121 */         list = Collections.unmodifiableList(list);
/*  122 */         canonLists.put(paramList, list);
/*      */       } 
/*  124 */       return list;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static Attribute find(int paramInt, String paramString1, String paramString2) {
/*  130 */     Layout layout = Layout.makeKey(paramInt, paramString1, paramString2);
/*  131 */     synchronized (attributes) {
/*  132 */       Attribute attribute = attributes.get(layout);
/*  133 */       if (attribute == null) {
/*  134 */         attribute = (new Layout(paramInt, paramString1, paramString2)).canonicalInstance();
/*  135 */         attributes.put(layout, attribute);
/*      */       } 
/*  137 */       return attribute;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static Layout keyForLookup(int paramInt, String paramString) {
/*  142 */     return Layout.makeKey(paramInt, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Attribute lookup(Map<Layout, Attribute> paramMap, int paramInt, String paramString) {
/*  149 */     if (paramMap == null) {
/*  150 */       paramMap = standardDefs;
/*      */     }
/*  152 */     return paramMap.get(Layout.makeKey(paramInt, paramString));
/*      */   }
/*      */ 
/*      */   
/*      */   public static Attribute define(Map<Layout, Attribute> paramMap, int paramInt, String paramString1, String paramString2) {
/*  157 */     Attribute attribute = find(paramInt, paramString1, paramString2);
/*  158 */     paramMap.put(Layout.makeKey(paramInt, paramString1), attribute);
/*  159 */     return attribute; } public static String contextName(int paramInt) { switch (paramInt) { case 0: return "class";case 1: return "field";case 2: return "method";case 3: return "code"; }  return null; } public static abstract class Holder {
/*      */     protected int flags; protected List<Attribute> attributes; protected abstract ConstantPool.Entry[] getCPMap(); public int attributeSize() { return (this.attributes == null) ? 0 : this.attributes.size(); } public void trimToSize() { if (this.attributes == null) return;  if (this.attributes.isEmpty()) { this.attributes = null; return; }  if (this.attributes instanceof ArrayList) { ArrayList<Attribute> arrayList = (ArrayList)this.attributes; arrayList.trimToSize(); boolean bool = true; for (Attribute attribute : arrayList) { if (!attribute.isCanonical()) bool = false;  if (attribute.fixups != null) { assert !attribute.isCanonical(); attribute.fixups = Fixups.trimToSize(attribute.fixups); }  }  if (bool) this.attributes = Attribute.getCanonList(arrayList);  }  } public void addAttribute(Attribute param1Attribute) { if (this.attributes == null) { this.attributes = new ArrayList<>(3); } else if (!(this.attributes instanceof ArrayList)) { this.attributes = new ArrayList<>(this.attributes); }  this.attributes.add(param1Attribute); } public Attribute removeAttribute(Attribute param1Attribute) { if (this.attributes == null) return null;  if (!this.attributes.contains(param1Attribute)) return null;  if (!(this.attributes instanceof ArrayList)) this.attributes = new ArrayList<>(this.attributes);  this.attributes.remove(param1Attribute); return param1Attribute; } public Attribute getAttribute(int param1Int) { return this.attributes.get(param1Int); } protected void visitRefs(int param1Int, Collection<ConstantPool.Entry> param1Collection) { if (this.attributes == null) return;  for (Attribute attribute : this.attributes) attribute.visitRefs(this, param1Int, param1Collection);  } static final List<Attribute> noAttributes = Arrays.asList(new Attribute[0]); public List<Attribute> getAttributes() { if (this.attributes == null) return noAttributes;  return this.attributes; } public void setAttributes(List<Attribute> param1List) { if (param1List.isEmpty()) { this.attributes = null; } else { this.attributes = param1List; }  } public Attribute getAttribute(String param1String) { if (this.attributes == null) return null;  for (Attribute attribute : this.attributes) { if (attribute.name().equals(param1String)) return attribute;  }  return null; } public Attribute getAttribute(Attribute.Layout param1Layout) { if (this.attributes == null) return null;  for (Attribute attribute : this.attributes) { if (attribute.layout() == param1Layout) return attribute;  }  return null; } public Attribute removeAttribute(String param1String) { return removeAttribute(getAttribute(param1String)); } public Attribute removeAttribute(Attribute.Layout param1Layout) { return removeAttribute(getAttribute(param1Layout)); } public void strip(String param1String) { removeAttribute(getAttribute(param1String)); } } public static abstract class ValueStream {
/*      */     public int getInt(int param1Int) { throw undef(); } public void putInt(int param1Int1, int param1Int2) { throw undef(); } public ConstantPool.Entry getRef(int param1Int) { throw undef(); } public void putRef(int param1Int, ConstantPool.Entry param1Entry) { throw undef(); } public int decodeBCI(int param1Int) { throw undef(); } public int encodeBCI(int param1Int) { throw undef(); } public void noteBackCall(int param1Int) {} private RuntimeException undef() { return new UnsupportedOperationException("ValueStream method"); } } public static class Layout implements Comparable<Layout> {
/*      */     int ctype; String name; boolean hasRefs; String layout; int bandCount; Element[] elems; Attribute canon; public int ctype() { return this.ctype; } public String name() { return this.name; } public String layout() { return this.layout; } public Attribute canonicalInstance() { return this.canon; } public ConstantPool.Entry getNameRef() { return ConstantPool.getUtf8Entry(name()); } public boolean isEmpty() { return this.layout.isEmpty(); } public Layout(int param1Int, String param1String1, String param1String2) { this.ctype = param1Int; this.name = param1String1.intern(); this.layout = param1String2.intern(); assert param1Int < 4; boolean bool = param1String2.startsWith("["); try { if (!bool) { this.elems = Attribute.tokenizeLayout(this, -1, param1String2); } else { String[] arrayOfString = Attribute.splitBodies(param1String2); Element[] arrayOfElement = new Element[arrayOfString.length]; this.elems = arrayOfElement; byte b; for (b = 0; b < arrayOfElement.length; b++) { Element element = new Element(); element.kind = 10; element.removeBand(); element.bandIndex = -1; element.layout = arrayOfString[b]; arrayOfElement[b] = element; }  for (b = 0; b < arrayOfElement.length; b++) { Element element = arrayOfElement[b]; element.body = Attribute.tokenizeLayout(this, b, arrayOfString[b]); }  }  } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) { throw new RuntimeException("Bad attribute layout: " + param1String2, stringIndexOutOfBoundsException); }  this.canon = new Attribute(this, Constants.noBytes); } private Layout() {} static Layout makeKey(int param1Int, String param1String1, String param1String2) { Layout layout = new Layout(); layout.ctype = param1Int; layout.name = param1String1.intern(); layout.layout = param1String2.intern(); assert param1Int < 4; return layout; } static Layout makeKey(int param1Int, String param1String) { return makeKey(param1Int, param1String, ""); } public Attribute addContent(byte[] param1ArrayOfbyte, Object param1Object) { return this.canon.addContent(param1ArrayOfbyte, param1Object); } public Attribute addContent(byte[] param1ArrayOfbyte) { return this.canon.addContent(param1ArrayOfbyte, null); } public boolean equals(Object param1Object) { return (param1Object != null && param1Object.getClass() == Layout.class && equals((Layout)param1Object)); } public boolean equals(Layout param1Layout) { return (this.name.equals(param1Layout.name) && this.layout.equals(param1Layout.layout) && this.ctype == param1Layout.ctype); } public int hashCode() { return ((17 + this.name.hashCode()) * 37 + this.layout.hashCode()) * 37 + this.ctype; } public int compareTo(Layout param1Layout) { int i = this.name.compareTo(param1Layout.name); if (i != 0) return i;  i = this.layout.compareTo(param1Layout.layout); if (i != 0) return i;  return this.ctype - param1Layout.ctype; } public String toString() { String str = Attribute.contextName(this.ctype) + "." + this.name + "[" + this.layout + "]"; assert (str = stringForDebug()) != null; return str; } private String stringForDebug() { return Attribute.contextName(this.ctype) + "." + this.name + Arrays.<Element>asList(this.elems); } public class Element {
/*  163 */       String layout; byte flags; byte kind; byte len; byte refKind; int bandIndex; int value; Element[] body; boolean flagTest(byte param2Byte) { return ((this.flags & param2Byte) != 0); } Element() { this.bandIndex = Attribute.Layout.this.bandCount++; } void removeBand() { Attribute.Layout.this.bandCount--; assert this.bandIndex == Attribute.Layout.this.bandCount; this.bandIndex = -1; } public boolean hasBand() { return (this.bandIndex >= 0); } public String toString() { String str = this.layout; assert (str = stringForDebug()) != null; return str; } private String stringForDebug() { Element[] arrayOfElement = this.body; switch (this.kind) { case 9: arrayOfElement = null; break;case 8: if (flagTest((byte)8)) arrayOfElement = null;  break; }  return this.layout + (!hasBand() ? "" : ("#" + this.bandIndex)) + "<" + ((this.flags == 0) ? "" : ("" + this.flags)) + this.kind + this.len + ((this.refKind == 0) ? "" : ("" + this.refKind)) + ">" + ((this.value == 0) ? "" : ("(" + this.value + ")")) + ((arrayOfElement == null) ? "" : ("" + Arrays.<Element>asList(arrayOfElement))); } } public boolean hasCallables() { return (this.elems.length > 0 && (this.elems[0]).kind == 10); } private static final Element[] noElems = new Element[0]; public Element[] getCallables() { if (hasCallables()) return Arrays.<Element>copyOf(this.elems, this.elems.length);  return noElems; } public Element[] getEntryPoint() { if (hasCallables()) return (this.elems[0]).body;  return Arrays.<Element>copyOf(this.elems, this.elems.length); } public void parse(Attribute.Holder param1Holder, byte[] param1ArrayOfbyte, int param1Int1, int param1Int2, Attribute.ValueStream param1ValueStream) { int i = Attribute.parseUsing(getEntryPoint(), param1Holder, param1ArrayOfbyte, param1Int1, param1Int2, param1ValueStream); if (i != param1Int1 + param1Int2) throw new InternalError("layout parsed " + (i - param1Int1) + " out of " + param1Int2 + " bytes");  } public Object unparse(Attribute.ValueStream param1ValueStream, ByteArrayOutputStream param1ByteArrayOutputStream) { Object[] arrayOfObject = { null }; Attribute.unparseUsing(getEntryPoint(), arrayOfObject, param1ValueStream, param1ByteArrayOutputStream); return arrayOfObject[0]; } public String layoutForClassVersion(Package.Version param1Version) { if (param1Version.lessThan(Constants.JAVA6_MAX_CLASS_VERSION)) return Attribute.expandCaseDashNotation(this.layout);  return this.layout; } } public class Element { String layout; byte flags; byte kind; byte len; byte refKind; int bandIndex; int value; Element[] body; boolean flagTest(byte param1Byte) { return ((this.flags & param1Byte) != 0); } Element() { this.bandIndex = ((Attribute.Layout)Attribute.this).bandCount++; } void removeBand() { this.this$0.bandCount--; assert this.bandIndex == this.this$0.bandCount; this.bandIndex = -1; } public boolean hasBand() { return (this.bandIndex >= 0); } public String toString() { String str = this.layout; assert (str = stringForDebug()) != null; return str; } private String stringForDebug() { Element[] arrayOfElement = this.body; switch (this.kind) { case 9: arrayOfElement = null; break;case 8: if (flagTest((byte)8)) arrayOfElement = null;  break; }  return this.layout + (!hasBand() ? "" : ("#" + this.bandIndex)) + "<" + ((this.flags == 0) ? "" : ("" + this.flags)) + this.kind + this.len + ((this.refKind == 0) ? "" : ("" + this.refKind)) + ">" + ((this.value == 0) ? "" : ("(" + this.value + ")")) + ((arrayOfElement == null) ? "" : ("" + Arrays.<Element>asList(arrayOfElement))); } } public static class FormatException extends IOException { private static final long serialVersionUID = -2542243830788066513L; private int ctype; private String name; String layout; public FormatException(String param1String1, int param1Int, String param1String2, String param1String3) { super(Constants.ATTR_CONTEXT_NAME[param1Int] + " attribute \"" + param1String2 + "\"" + ((param1String1 == null) ? "" : (": " + param1String1))); this.ctype = param1Int; this.name = param1String2; this.layout = param1String3; } public FormatException(String param1String1, int param1Int, String param1String2) { this(param1String1, param1Int, param1String2, null); } } static { Map<Layout, Attribute> map1 = standardDefs;
/*  164 */     define(map1, 0, "Signature", "RSH");
/*  165 */     define(map1, 0, "Synthetic", "");
/*  166 */     define(map1, 0, "Deprecated", "");
/*  167 */     define(map1, 0, "SourceFile", "RUH");
/*  168 */     define(map1, 0, "EnclosingMethod", "RCHRDNH");
/*  169 */     define(map1, 0, "InnerClasses", "NH[RCHRCNHRUNHFH]");
/*  170 */     define(map1, 0, "BootstrapMethods", "NH[RMHNH[KLH]]");
/*      */     
/*  172 */     define(map1, 1, "Signature", "RSH");
/*  173 */     define(map1, 1, "Synthetic", "");
/*  174 */     define(map1, 1, "Deprecated", "");
/*  175 */     define(map1, 1, "ConstantValue", "KQH");
/*      */     
/*  177 */     define(map1, 2, "Signature", "RSH");
/*  178 */     define(map1, 2, "Synthetic", "");
/*  179 */     define(map1, 2, "Deprecated", "");
/*  180 */     define(map1, 2, "Exceptions", "NH[RCH]");
/*  181 */     define(map1, 2, "MethodParameters", "NB[RUNHFH]");
/*      */ 
/*      */     
/*  184 */     define(map1, 3, "StackMapTable", "[NH[(1)]][TB(64-127)[(2)](247)[(1)(2)](248-251)[(1)](252)[(1)(2)](253)[(1)(2)(2)](254)[(1)(2)(2)(2)](255)[(1)NH[(2)]NH[(2)]]()[]][H][TB(7)[RCH](8)[PH]()[]]");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  199 */     define(map1, 3, "LineNumberTable", "NH[PHH]");
/*  200 */     define(map1, 3, "LocalVariableTable", "NH[PHOHRUHRSHH]");
/*  201 */     define(map1, 3, "LocalVariableTypeTable", "NH[PHOHRUHRSHH]");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  251 */     String[] arrayOfString1 = { normalizeLayoutString("\n  # parameter_annotations :=\n  [ NB[(1)] ]     # forward call to annotations"), normalizeLayoutString("\n  # annotations :=\n  [ NH[(1)] ]     # forward call to annotation\n  "), normalizeLayoutString("\n  # annotation :=\n  [RSH\n    NH[RUH (1)]   # forward call to value\n    ]"), normalizeLayoutString("\n  # value :=\n  [TB # Callable 2 encodes one tagged value.\n    (\\B,\\C,\\I,\\S,\\Z)[KIH]\n    (\\D)[KDH]\n    (\\F)[KFH]\n    (\\J)[KJH]\n    (\\c)[RSH]\n    (\\e)[RSH RUH]\n    (\\s)[RUH]\n    (\\[)[NH[(0)]] # backward self-call to value\n    (\\@)[RSH NH[RUH (0)]] # backward self-call to value\n    ()[] ]") };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  295 */     String[] arrayOfString2 = { normalizeLayoutString("\n # type-annotations :=\n  [ NH[(1)(2)(3)] ]     # forward call to type-annotations"), normalizeLayoutString("\n  # type-annotation :=\n  [TB\n    (0-1) [B] # {CLASS, METHOD}_TYPE_PARAMETER\n    (16) [FH] # CLASS_EXTENDS\n    (17-18) [BB] # {CLASS, METHOD}_TYPE_PARAMETER_BOUND\n    (19-21) [] # FIELD, METHOD_RETURN, METHOD_RECEIVER\n    (22) [B] # METHOD_FORMAL_PARAMETER\n    (23) [H] # THROWS\n    (64-65) [NH[PHOHH]] # LOCAL_VARIABLE, RESOURCE_VARIABLE\n    (66) [H] # EXCEPTION_PARAMETER\n    (67-70) [PH] # INSTANCEOF, NEW, {CONSTRUCTOR, METHOD}_REFERENCE_RECEIVER\n    (71-75) [PHB] # CAST, {CONSTRUCTOR,METHOD}_INVOCATION_TYPE_ARGUMENT, {CONSTRUCTOR, METHOD}_REFERENCE_TYPE_ARGUMENT\n    ()[] ]"), normalizeLayoutString("\n # type-path\n [ NB[BB] ]") };
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  300 */     Map<Layout, Attribute> map2 = standardDefs;
/*  301 */     String str1 = arrayOfString1[3];
/*  302 */     String str2 = arrayOfString1[1] + arrayOfString1[2] + arrayOfString1[3];
/*  303 */     String str3 = arrayOfString1[0] + str2;
/*  304 */     String str4 = arrayOfString2[0] + arrayOfString2[1] + arrayOfString2[2] + arrayOfString1[2] + arrayOfString1[3];
/*      */ 
/*      */     
/*  307 */     for (byte b = 0; b < 4; b++) {
/*  308 */       if (b != 3) {
/*  309 */         define(map2, b, "RuntimeVisibleAnnotations", str2);
/*      */         
/*  311 */         define(map2, b, "RuntimeInvisibleAnnotations", str2);
/*      */ 
/*      */         
/*  314 */         if (b == 2) {
/*  315 */           define(map2, b, "RuntimeVisibleParameterAnnotations", str3);
/*      */           
/*  317 */           define(map2, b, "RuntimeInvisibleParameterAnnotations", str3);
/*      */           
/*  319 */           define(map2, b, "AnnotationDefault", str1);
/*      */         } 
/*      */       } 
/*      */       
/*  323 */       define(map2, b, "RuntimeVisibleTypeAnnotations", str4);
/*      */       
/*  325 */       define(map2, b, "RuntimeInvisibleTypeAnnotations", str4);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1256 */     assert expandCaseDashNotation("1-5").equals("1,2,3,4,5");
/* 1257 */     assert expandCaseDashNotation("-2--1").equals("-2,-1");
/* 1258 */     assert expandCaseDashNotation("-2-1").equals("-2,-1,0,1");
/* 1259 */     assert expandCaseDashNotation("-1-0").equals("-1,0"); }
/*      */   void visitRefs(Holder paramHolder, int paramInt, final Collection<ConstantPool.Entry> refs) { if (paramInt == 0) refs.add(getNameRef());  if (this.bytes.length == 0) return;  if (!this.def.hasRefs) return;  if (this.fixups != null) { Fixups.visitRefs(this.fixups, refs); return; }  this.def.parse(paramHolder, this.bytes, 0, this.bytes.length, new ValueStream() {
/*      */           public void putInt(int param1Int1, int param1Int2) {}
/*      */           public void putRef(int param1Int, ConstantPool.Entry param1Entry) { refs.add(param1Entry); }
/*      */           public int encodeBCI(int param1Int) { return param1Int; }
/*      */         }); }
/*      */   public void parse(Holder paramHolder, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, ValueStream paramValueStream) { this.def.parse(paramHolder, paramArrayOfbyte, paramInt1, paramInt2, paramValueStream); }
/*      */   public Object unparse(ValueStream paramValueStream, ByteArrayOutputStream paramByteArrayOutputStream) { return this.def.unparse(paramValueStream, paramByteArrayOutputStream); } public String toString() { return this.def + "{" + ((this.bytes == null) ? -1 : size()) + "}" + ((this.fixups == null) ? "" : this.fixups.toString()); } public static String normalizeLayoutString(String paramString) { StringBuilder stringBuilder = new StringBuilder(); for (int i = 0, j = paramString.length(); i < j; ) { char c = paramString.charAt(i++); if (c <= ' ') continue;  if (c == '#') { int k = paramString.indexOf('\n', i); int m = paramString.indexOf('\r', i); if (k < 0) k = j;  if (m < 0)
/*      */           m = j;  i = Math.min(k, m); continue; }  if (c == '\\') { stringBuilder.append(paramString.charAt(i++)); continue; }  if (c == '0' && paramString.startsWith("0x", i - 1)) { int k = i - 1; int m = k + 2; while (m < j) { char c1 = paramString.charAt(m); if ((c1 >= '0' && c1 <= '9') || (c1 >= 'a' && c1 <= 'f'))
/* 1268 */             m++;  }  if (m > k) { String str = paramString.substring(k, m); stringBuilder.append(Integer.decode(str)); i = m; continue; }  stringBuilder.append(c); continue; }  stringBuilder.append(c); }  return stringBuilder.toString(); } static int parseUsing(Layout.Element[] paramArrayOfElement, Holder paramHolder, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, ValueStream paramValueStream) { int i = 0;
/* 1269 */     int j = 0;
/* 1270 */     int k = paramInt1 + paramInt2;
/* 1271 */     int[] arrayOfInt = { 0 };
/* 1272 */     for (byte b = 0; b < paramArrayOfElement.length; b++) {
/* 1273 */       int n, i1, i2; byte b1; Layout.Element element2; int i3; ConstantPool.Entry entry; Layout.Element element1 = paramArrayOfElement[b];
/* 1274 */       int m = element1.bandIndex;
/*      */ 
/*      */       
/* 1277 */       switch (element1.kind) {
/*      */         case 1:
/* 1279 */           paramInt1 = parseInt(element1, paramArrayOfbyte, paramInt1, arrayOfInt);
/* 1280 */           n = arrayOfInt[0];
/* 1281 */           paramValueStream.putInt(m, n);
/*      */           break;
/*      */         case 2:
/* 1284 */           paramInt1 = parseInt(element1, paramArrayOfbyte, paramInt1, arrayOfInt);
/* 1285 */           i1 = arrayOfInt[0];
/* 1286 */           i2 = paramValueStream.encodeBCI(i1);
/* 1287 */           if (!element1.flagTest((byte)2)) {
/*      */             
/* 1289 */             n = i2;
/*      */           } else {
/*      */             
/* 1292 */             n = i2 - j;
/*      */           } 
/* 1294 */           i = i1;
/* 1295 */           j = i2;
/* 1296 */           paramValueStream.putInt(m, n);
/*      */           break;
/*      */         case 3:
/* 1299 */           assert element1.flagTest((byte)2);
/*      */           
/* 1301 */           paramInt1 = parseInt(element1, paramArrayOfbyte, paramInt1, arrayOfInt);
/* 1302 */           i1 = i + arrayOfInt[0];
/* 1303 */           i2 = paramValueStream.encodeBCI(i1);
/* 1304 */           n = i2 - j;
/* 1305 */           i = i1;
/* 1306 */           j = i2;
/* 1307 */           paramValueStream.putInt(m, n);
/*      */           break;
/*      */         case 4:
/* 1310 */           paramInt1 = parseInt(element1, paramArrayOfbyte, paramInt1, arrayOfInt);
/* 1311 */           n = arrayOfInt[0];
/* 1312 */           paramValueStream.putInt(m, n);
/*      */           break;
/*      */         case 5:
/* 1315 */           paramInt1 = parseInt(element1, paramArrayOfbyte, paramInt1, arrayOfInt);
/* 1316 */           n = arrayOfInt[0];
/* 1317 */           paramValueStream.putInt(m, n);
/* 1318 */           for (b1 = 0; b1 < n; b1++) {
/* 1319 */             paramInt1 = parseUsing(element1.body, paramHolder, paramArrayOfbyte, paramInt1, k - paramInt1, paramValueStream);
/*      */           }
/*      */           break;
/*      */         case 7:
/* 1323 */           paramInt1 = parseInt(element1, paramArrayOfbyte, paramInt1, arrayOfInt);
/* 1324 */           n = arrayOfInt[0];
/* 1325 */           paramValueStream.putInt(m, n);
/* 1326 */           element2 = matchCase(element1, n);
/* 1327 */           paramInt1 = parseUsing(element2.body, paramHolder, paramArrayOfbyte, paramInt1, k - paramInt1, paramValueStream);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 9:
/* 1332 */           assert element1.body.length == 1;
/* 1333 */           assert (element1.body[0]).kind == 10;
/* 1334 */           if (element1.flagTest((byte)8))
/* 1335 */             paramValueStream.noteBackCall(element1.value); 
/* 1336 */           paramInt1 = parseUsing((element1.body[0]).body, paramHolder, paramArrayOfbyte, paramInt1, k - paramInt1, paramValueStream);
/*      */           break;
/*      */         case 6:
/* 1339 */           paramInt1 = parseInt(element1, paramArrayOfbyte, paramInt1, arrayOfInt);
/* 1340 */           i3 = arrayOfInt[0];
/*      */           
/* 1342 */           if (i3 == 0) {
/* 1343 */             entry = null;
/*      */           } else {
/* 1345 */             ConstantPool.Entry[] arrayOfEntry = paramHolder.getCPMap();
/* 1346 */             entry = (i3 >= 0 && i3 < arrayOfEntry.length) ? arrayOfEntry[i3] : null;
/*      */ 
/*      */             
/* 1349 */             byte b2 = element1.refKind;
/* 1350 */             if (entry != null && b2 == 13 && entry
/* 1351 */               .getTag() == 1) {
/*      */               
/* 1353 */               String str1 = entry.stringValue();
/* 1354 */               entry = ConstantPool.getSignatureEntry(str1);
/*      */             } 
/*      */ 
/*      */             
/* 1358 */             String str = (entry == null) ? "invalid CP index" : ("type=" + ConstantPool.tagName(entry.tag));
/* 1359 */             if (entry == null || !entry.tagMatches(b2)) {
/* 1360 */               throw new IllegalArgumentException("Bad constant, expected type=" + 
/*      */                   
/* 1362 */                   ConstantPool.tagName(b2) + " got " + str);
/*      */             }
/*      */           } 
/* 1365 */           paramValueStream.putRef(m, entry); break;
/*      */         default:
/*      */           assert false; break;
/*      */       } 
/*      */     } 
/* 1370 */     return paramInt1; } static Layout.Element[] tokenizeLayout(Layout paramLayout, int paramInt, String paramString) { ArrayList<Layout.Element> arrayList = new ArrayList(paramString.length()); tokenizeLayout(paramLayout, paramInt, paramString, arrayList); Layout.Element[] arrayOfElement = new Layout.Element[arrayList.size()]; arrayList.toArray(arrayOfElement); return arrayOfElement; } static void tokenizeLayout(Layout paramLayout, int paramInt, String paramString, List<Layout.Element> paramList) { boolean bool = false; for (int i = paramString.length(), j = 0; j < i; ) { int m; byte b1; ArrayList<Layout.Element> arrayList; byte b2; String str; int n, i1; Layout.Element element2; int k = j; paramLayout.getClass(); Layout.Element element1 = new Layout.Element(); switch (paramString.charAt(j++)) { case 'B': case 'H': case 'I': case 'V': b1 = 1; j--; j = tokenizeUInt(element1, paramString, j); break;case 'S': b1 = 1; j--; j = tokenizeSInt(element1, paramString, j); break;case 'P': b1 = 2; if (paramString.charAt(j++) == 'O') { element1.flags = (byte)(element1.flags | 0x2); if (!bool) { j = -j; continue; }  j++; }  j--; j = tokenizeUInt(element1, paramString, j); break;case 'O': b1 = 3; element1.flags = (byte)(element1.flags | 0x2); if (!bool) { j = -j; continue; }  j = tokenizeSInt(element1, paramString, j); break;case 'F': b1 = 4; j = tokenizeUInt(element1, paramString, j); break;case 'N': b1 = 5; j = tokenizeUInt(element1, paramString, j); if (paramString.charAt(j++) != '[') { j = -j; continue; }  j = skipBody(paramString, m = j); element1.body = tokenizeLayout(paramLayout, paramInt, paramString.substring(m, j++)); break;case 'T': b1 = 7; j = tokenizeSInt(element1, paramString, j); arrayList = new ArrayList(); label163: while (true) { if (paramString.charAt(j++) != '(') { j = -j; break; }  int i2 = j; j = paramString.indexOf(')', j); String str1 = paramString.substring(i2, j++); int i3 = str1.length(); if (paramString.charAt(j++) != '[') { j = -j; break; }  if (paramString.charAt(j) == ']') { m = j; } else { j = skipBody(paramString, m = j); }  Layout.Element[] arrayOfElement = tokenizeLayout(paramLayout, paramInt, paramString.substring(m, j++)); if (i3 == 0) { paramLayout.getClass(); Layout.Element element = new Layout.Element(); element.body = arrayOfElement; element.kind = 8; element.removeBand(); arrayList.add(element); break; }  boolean bool1 = true; int i4; for (i4 = 0;; i4 = i5 + 1) { int i6, i7, i5 = str1.indexOf(',', i4); if (i5 < 0) i5 = i3;  String str2 = str1.substring(i4, i5); if (str2.length() == 0) str2 = "empty";  int i8 = findCaseDash(str2, 0); if (i8 >= 0) { i6 = parseIntBefore(str2, i8); i7 = parseIntAfter(str2, i8); if (i6 >= i7) { j = -j; continue label163; }  } else { i6 = i7 = Integer.parseInt(str2); }  for (;; i6++) { paramLayout.getClass(); Layout.Element element = new Layout.Element(); element.body = arrayOfElement; element.kind = 8; element.removeBand(); if (!bool1) element.flags = (byte)(element.flags | 0x8);  bool1 = false; element.value = i6; arrayList.add(element); if (i6 == i7) break;  }  if (i5 == i3) continue label163;  }  }  element1.body = new Layout.Element[arrayList.size()]; arrayList.toArray(element1.body); element1.kind = b1; for (b2 = 0; b2 < element1.body.length - 1; b2++) { Layout.Element element = element1.body[b2]; if (matchCase(element1, element.value) != element) { j = -j; break; }  }  break;case '(': b1 = 9; element1.removeBand(); j = paramString.indexOf(')', j); str = paramString.substring(k + 1, j++); n = Integer.parseInt(str); i1 = paramInt + n; if (!(n + "").equals(str) || paramLayout.elems == null || i1 < 0 || i1 >= paramLayout.elems.length) { j = -j; continue; }  element2 = paramLayout.elems[i1]; assert element2.kind == 10; element1.value = i1; element1.body = new Layout.Element[] { element2 }; if (n <= 0) { element1.flags = (byte)(element1.flags | 0x8); element2.flags = (byte)(element2.flags | 0x8); }  break;case 'K': b1 = 6; switch (paramString.charAt(j++)) { case 'I': element1.refKind = 3; break;case 'J': element1.refKind = 5; break;case 'F': element1.refKind = 4; break;case 'D': element1.refKind = 6; break;case 'S': element1.refKind = 8; break;case 'Q': element1.refKind = 53; break;case 'M': element1.refKind = 15; break;case 'T': element1.refKind = 16; break;case 'L': element1.refKind = 51; break; }  j = -j; continue;case 'R': b1 = 6; switch (paramString.charAt(j++)) { case 'C': element1.refKind = 7; break;case 'S': element1.refKind = 13; break;case 'D': element1.refKind = 12; break;case 'F': element1.refKind = 9; break;case 'M': element1.refKind = 10; break;case 'I': element1.refKind = 11; break;case 'U': element1.refKind = 1; break;case 'Q': element1.refKind = 50; break;case 'Y': element1.refKind = 18; break;case 'B': element1.refKind = 17; break;case 'N': element1.refKind = 52; break; }  j = -j; continue;default: j = -j; continue; }  if (b1 == 6) { if (paramString.charAt(j++) == 'N') { element1.flags = (byte)(element1.flags | 0x4); j++; }  j--; j = tokenizeUInt(element1, paramString, j); paramLayout.hasRefs = true; }  bool = (b1 == 2) ? true : false; element1.kind = b1; element1.layout = paramString.substring(k, j); paramList.add(element1); }  } static String[] splitBodies(String paramString) { ArrayList<String> arrayList = new ArrayList(); for (int i = 0; i < paramString.length(); i++) { if (paramString.charAt(i++) != '[') paramString.charAt(-i);  int j; i = skipBody(paramString, j = i); arrayList.add(paramString.substring(j, i)); }  String[] arrayOfString = new String[arrayList.size()]; arrayList.toArray(arrayOfString); return arrayOfString; } private static int skipBody(String paramString, int paramInt) { assert paramString.charAt(paramInt - 1) == '['; if (paramString.charAt(paramInt) == ']') return -paramInt;  for (byte b = 1; b;) { switch (paramString.charAt(paramInt++)) { case '[': b++;case ']': b--; }  }  paramInt--; assert paramString.charAt(paramInt) == ']'; return paramInt; } private static int tokenizeUInt(Layout.Element paramElement, String paramString, int paramInt) { switch (paramString.charAt(paramInt++)) { case 'V': paramElement.len = 0; return paramInt;case 'B': paramElement.len = 1; return paramInt;case 'H': paramElement.len = 2; return paramInt;case 'I': paramElement.len = 4; return paramInt; }  return -paramInt; } private static int tokenizeSInt(Layout.Element paramElement, String paramString, int paramInt) { if (paramString.charAt(paramInt) == 'S') { paramElement.flags = (byte)(paramElement.flags | 0x1); paramInt++; }  return tokenizeUInt(paramElement, paramString, paramInt); } private static boolean isDigit(char paramChar) { return (paramChar >= '0' && paramChar <= '9'); }
/*      */   static int findCaseDash(String paramString, int paramInt) { if (paramInt <= 0) paramInt = 1;  int i = paramString.length() - 2; while (true) { int j = paramString.indexOf('-', paramInt); if (j < 0 || j > i) return -1;  if (isDigit(paramString.charAt(j - 1))) { char c = paramString.charAt(j + 1); if (c == '-' && j + 2 < paramString.length()) c = paramString.charAt(j + 2);  if (isDigit(c)) return j;  }  paramInt = j + 1; }  }
/*      */   static int parseIntBefore(String paramString, int paramInt) { int i = paramInt; int j = i; while (j > 0 && isDigit(paramString.charAt(j - 1))) j--;  if (j == i) return Integer.parseInt("empty");  if (j >= 1 && paramString.charAt(j - 1) == '-') j--;  assert j == 0 || !isDigit(paramString.charAt(j - 1)); return Integer.parseInt(paramString.substring(j, i)); }
/*      */   static int parseIntAfter(String paramString, int paramInt) { int i = paramInt + 1; int j = i; int k = paramString.length(); if (j < k && paramString.charAt(j) == '-') j++;  while (j < k && isDigit(paramString.charAt(j))) j++;  if (i == j) return Integer.parseInt("empty");  return Integer.parseInt(paramString.substring(i, j)); }
/*      */   static String expandCaseDashNotation(String paramString) { int i = findCaseDash(paramString, 0); if (i < 0) return paramString;  StringBuilder stringBuilder = new StringBuilder(paramString.length() * 3); int j = 0; do { stringBuilder.append(paramString.substring(j, i)); j = i + 1; int k = parseIntBefore(paramString, i); int m = parseIntAfter(paramString, i); assert k < m; stringBuilder.append(","); for (int n = k + 1; n < m; n++) { stringBuilder.append(n); stringBuilder.append(","); }  i = findCaseDash(paramString, j); } while (i >= 0); stringBuilder.append(paramString.substring(j)); return stringBuilder.toString(); }
/* 1375 */   static Layout.Element matchCase(Layout.Element paramElement, int paramInt) { assert paramElement.kind == 7;
/* 1376 */     int i = paramElement.body.length - 1;
/* 1377 */     for (byte b = 0; b < i; b++) {
/* 1378 */       Layout.Element element = paramElement.body[b];
/* 1379 */       assert element.kind == 8;
/* 1380 */       if (paramInt == element.value)
/* 1381 */         return element; 
/*      */     } 
/* 1383 */     return paramElement.body[i]; }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseInt(Layout.Element paramElement, byte[] paramArrayOfbyte, int paramInt, int[] paramArrayOfint) {
/* 1388 */     int i = 0;
/* 1389 */     int j = paramElement.len * 8;
/*      */     int k;
/* 1391 */     for (k = j, k -= 8; k >= 0;) {
/* 1392 */       i += (paramArrayOfbyte[paramInt++] & 0xFF) << k;
/*      */     }
/* 1394 */     if (j < 32 && paramElement.flagTest((byte)1)) {
/*      */       
/* 1396 */       k = 32 - j;
/* 1397 */       i = i << k >> k;
/*      */     } 
/* 1399 */     paramArrayOfint[0] = i;
/* 1400 */     return paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void unparseUsing(Layout.Element[] paramArrayOfElement, Object[] paramArrayOfObject, ValueStream paramValueStream, ByteArrayOutputStream paramByteArrayOutputStream) {
/* 1409 */     int i = 0;
/* 1410 */     int j = 0;
/* 1411 */     for (byte b = 0; b < paramArrayOfElement.length; b++) {
/* 1412 */       int m, n, i1; byte b1; Layout.Element element2; ConstantPool.Entry entry; boolean bool; Layout.Element element1 = paramArrayOfElement[b];
/* 1413 */       int k = element1.bandIndex;
/*      */ 
/*      */       
/* 1416 */       switch (element1.kind) {
/*      */         case 1:
/* 1418 */           m = paramValueStream.getInt(k);
/* 1419 */           unparseInt(element1, m, paramByteArrayOutputStream);
/*      */           break;
/*      */         case 2:
/* 1422 */           m = paramValueStream.getInt(k);
/* 1423 */           if (!element1.flagTest((byte)2)) {
/*      */             
/* 1425 */             i1 = m;
/*      */           } else {
/*      */             
/* 1428 */             i1 = j + m;
/*      */           } 
/* 1430 */           assert i == paramValueStream.decodeBCI(j);
/* 1431 */           n = paramValueStream.decodeBCI(i1);
/* 1432 */           unparseInt(element1, n, paramByteArrayOutputStream);
/* 1433 */           i = n;
/* 1434 */           j = i1;
/*      */           break;
/*      */         case 3:
/* 1437 */           m = paramValueStream.getInt(k);
/* 1438 */           assert element1.flagTest((byte)2);
/*      */           
/* 1440 */           assert i == paramValueStream.decodeBCI(j);
/* 1441 */           i1 = j + m;
/* 1442 */           n = paramValueStream.decodeBCI(i1);
/* 1443 */           unparseInt(element1, n - i, paramByteArrayOutputStream);
/* 1444 */           i = n;
/* 1445 */           j = i1;
/*      */           break;
/*      */         case 4:
/* 1448 */           m = paramValueStream.getInt(k);
/* 1449 */           unparseInt(element1, m, paramByteArrayOutputStream);
/*      */           break;
/*      */         case 5:
/* 1452 */           m = paramValueStream.getInt(k);
/* 1453 */           unparseInt(element1, m, paramByteArrayOutputStream);
/* 1454 */           for (b1 = 0; b1 < m; b1++) {
/* 1455 */             unparseUsing(element1.body, paramArrayOfObject, paramValueStream, paramByteArrayOutputStream);
/*      */           }
/*      */           break;
/*      */         case 7:
/* 1459 */           m = paramValueStream.getInt(k);
/* 1460 */           unparseInt(element1, m, paramByteArrayOutputStream);
/* 1461 */           element2 = matchCase(element1, m);
/* 1462 */           unparseUsing(element2.body, paramArrayOfObject, paramValueStream, paramByteArrayOutputStream);
/*      */           break;
/*      */         case 9:
/* 1465 */           assert element1.body.length == 1;
/* 1466 */           assert (element1.body[0]).kind == 10;
/* 1467 */           unparseUsing((element1.body[0]).body, paramArrayOfObject, paramValueStream, paramByteArrayOutputStream);
/*      */           break;
/*      */         case 6:
/* 1470 */           entry = paramValueStream.getRef(k);
/*      */           
/* 1472 */           if (entry != null) {
/*      */             
/* 1474 */             paramArrayOfObject[0] = Fixups.addRefWithLoc(paramArrayOfObject[0], paramByteArrayOutputStream.size(), entry);
/* 1475 */             bool = false;
/*      */           } else {
/* 1477 */             bool = false;
/*      */           } 
/* 1479 */           unparseInt(element1, bool, paramByteArrayOutputStream);
/*      */           break;
/*      */         default:
/*      */           assert false;
/*      */           break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static void unparseInt(Layout.Element paramElement, int paramInt, ByteArrayOutputStream paramByteArrayOutputStream) {
/* 1488 */     int i = paramElement.len * 8;
/* 1489 */     if (i == 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1493 */     if (i < 32) {
/* 1494 */       int m, k = 32 - i;
/*      */       
/* 1496 */       if (paramElement.flagTest((byte)1)) {
/* 1497 */         m = paramInt << k >> k;
/*      */       } else {
/* 1499 */         m = paramInt << k >>> k;
/* 1500 */       }  if (m != paramInt) {
/* 1501 */         throw new InternalError("cannot code in " + paramElement.len + " bytes: " + paramInt);
/*      */       }
/*      */     } 
/* 1504 */     for (int j = i; j >= 0;)
/* 1505 */       paramByteArrayOutputStream.write((byte)(paramInt >>> j)); 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/util/jar/pack/Attribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */