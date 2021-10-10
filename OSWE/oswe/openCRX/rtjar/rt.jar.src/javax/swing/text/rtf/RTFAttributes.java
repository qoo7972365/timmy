/*     */ package javax.swing.text.rtf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Dictionary;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import javax.swing.text.AttributeSet;
/*     */ import javax.swing.text.MutableAttributeSet;
/*     */ import javax.swing.text.StyleConstants;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class RTFAttributes
/*     */ {
/*     */   static RTFAttribute[] attributes;
/*     */   
/*     */   static {
/*  39 */     Vector<BooleanAttribute> vector = new Vector();
/*  40 */     boolean bool1 = false;
/*  41 */     boolean bool2 = true;
/*  42 */     byte b1 = 2;
/*  43 */     byte b2 = 3;
/*  44 */     byte b3 = 4;
/*  45 */     Boolean bool3 = Boolean.valueOf(true);
/*  46 */     Boolean bool4 = Boolean.valueOf(false);
/*     */     
/*  48 */     vector.addElement(new BooleanAttribute(bool1, StyleConstants.Italic, "i"));
/*  49 */     vector.addElement(new BooleanAttribute(bool1, StyleConstants.Bold, "b"));
/*  50 */     vector.addElement(new BooleanAttribute(bool1, StyleConstants.Underline, "ul"));
/*  51 */     vector.addElement(NumericAttribute.NewTwips(bool2, StyleConstants.LeftIndent, "li", 0.0F, 0));
/*     */     
/*  53 */     vector.addElement(NumericAttribute.NewTwips(bool2, StyleConstants.RightIndent, "ri", 0.0F, 0));
/*     */     
/*  55 */     vector.addElement(NumericAttribute.NewTwips(bool2, StyleConstants.FirstLineIndent, "fi", 0.0F, 0));
/*     */ 
/*     */     
/*  58 */     vector.addElement(new AssertiveAttribute(bool2, StyleConstants.Alignment, "ql", 0));
/*     */     
/*  60 */     vector.addElement(new AssertiveAttribute(bool2, StyleConstants.Alignment, "qr", 2));
/*     */     
/*  62 */     vector.addElement(new AssertiveAttribute(bool2, StyleConstants.Alignment, "qc", 1));
/*     */     
/*  64 */     vector.addElement(new AssertiveAttribute(bool2, StyleConstants.Alignment, "qj", 3));
/*     */     
/*  66 */     vector.addElement(NumericAttribute.NewTwips(bool2, StyleConstants.SpaceAbove, "sa", 0));
/*     */     
/*  68 */     vector.addElement(NumericAttribute.NewTwips(bool2, StyleConstants.SpaceBelow, "sb", 0));
/*     */ 
/*     */     
/*  71 */     vector.addElement(new AssertiveAttribute(b3, "tab_alignment", "tqr", 1));
/*     */     
/*  73 */     vector.addElement(new AssertiveAttribute(b3, "tab_alignment", "tqc", 2));
/*     */     
/*  75 */     vector.addElement(new AssertiveAttribute(b3, "tab_alignment", "tqdec", 4));
/*     */ 
/*     */ 
/*     */     
/*  79 */     vector.addElement(new AssertiveAttribute(b3, "tab_leader", "tldot", 1));
/*     */     
/*  81 */     vector.addElement(new AssertiveAttribute(b3, "tab_leader", "tlhyph", 2));
/*     */     
/*  83 */     vector.addElement(new AssertiveAttribute(b3, "tab_leader", "tlul", 3));
/*     */     
/*  85 */     vector.addElement(new AssertiveAttribute(b3, "tab_leader", "tlth", 4));
/*     */     
/*  87 */     vector.addElement(new AssertiveAttribute(b3, "tab_leader", "tleq", 5));
/*     */ 
/*     */ 
/*     */     
/*  91 */     vector.addElement(new BooleanAttribute(bool1, "caps", "caps"));
/*  92 */     vector.addElement(new BooleanAttribute(bool1, "outl", "outl"));
/*  93 */     vector.addElement(new BooleanAttribute(bool1, "scaps", "scaps"));
/*  94 */     vector.addElement(new BooleanAttribute(bool1, "shad", "shad"));
/*  95 */     vector.addElement(new BooleanAttribute(bool1, "v", "v"));
/*  96 */     vector.addElement(new BooleanAttribute(bool1, "strike", "strike"));
/*     */     
/*  98 */     vector.addElement(new BooleanAttribute(bool1, "deleted", "deleted"));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     vector.addElement(new AssertiveAttribute(b2, "saveformat", "defformat", "RTF"));
/* 104 */     vector.addElement(new AssertiveAttribute(b2, "landscape", "landscape"));
/*     */     
/* 106 */     vector.addElement(NumericAttribute.NewTwips(b2, "paperw", "paperw", 12240));
/*     */     
/* 108 */     vector.addElement(NumericAttribute.NewTwips(b2, "paperh", "paperh", 15840));
/*     */     
/* 110 */     vector.addElement(NumericAttribute.NewTwips(b2, "margl", "margl", 1800));
/*     */     
/* 112 */     vector.addElement(NumericAttribute.NewTwips(b2, "margr", "margr", 1800));
/*     */     
/* 114 */     vector.addElement(NumericAttribute.NewTwips(b2, "margt", "margt", 1440));
/*     */     
/* 116 */     vector.addElement(NumericAttribute.NewTwips(b2, "margb", "margb", 1440));
/*     */     
/* 118 */     vector.addElement(NumericAttribute.NewTwips(b2, "gutter", "gutter", 0));
/*     */ 
/*     */     
/* 121 */     vector.addElement(new AssertiveAttribute(bool2, "widowctrl", "nowidctlpar", bool4));
/*     */     
/* 123 */     vector.addElement(new AssertiveAttribute(bool2, "widowctrl", "widctlpar", bool3));
/*     */     
/* 125 */     vector.addElement(new AssertiveAttribute(b2, "widowctrl", "widowctrl", bool3));
/*     */ 
/*     */ 
/*     */     
/* 129 */     RTFAttribute[] arrayOfRTFAttribute = new RTFAttribute[vector.size()];
/* 130 */     vector.copyInto((Object[])arrayOfRTFAttribute);
/* 131 */     attributes = arrayOfRTFAttribute;
/*     */   }
/*     */ 
/*     */   
/*     */   static Dictionary<String, RTFAttribute> attributesByKeyword() {
/* 136 */     Hashtable<Object, Object> hashtable = new Hashtable<>(attributes.length);
/*     */     
/* 138 */     for (RTFAttribute rTFAttribute : attributes) {
/* 139 */       hashtable.put(rTFAttribute.rtfName(), rTFAttribute);
/*     */     }
/*     */     
/* 142 */     return (Dictionary)hashtable;
/*     */   }
/*     */ 
/*     */   
/*     */   static abstract class GenericAttribute
/*     */   {
/*     */     int domain;
/*     */     
/*     */     Object swingName;
/*     */     
/*     */     String rtfName;
/*     */ 
/*     */     
/*     */     protected GenericAttribute(int param1Int, Object param1Object, String param1String) {
/* 156 */       this.domain = param1Int;
/* 157 */       this.swingName = param1Object;
/* 158 */       this.rtfName = param1String;
/*     */     }
/*     */     
/* 161 */     public int domain() { return this.domain; }
/* 162 */     public Object swingName() { return this.swingName; } public String rtfName() {
/* 163 */       return this.rtfName;
/*     */     }
/*     */ 
/*     */     
/*     */     abstract boolean set(MutableAttributeSet param1MutableAttributeSet);
/*     */     
/*     */     abstract boolean set(MutableAttributeSet param1MutableAttributeSet, int param1Int);
/*     */     
/*     */     abstract boolean setDefault(MutableAttributeSet param1MutableAttributeSet);
/*     */     
/*     */     public boolean write(AttributeSet param1AttributeSet, RTFGenerator param1RTFGenerator, boolean param1Boolean) throws IOException {
/* 174 */       return writeValue(param1AttributeSet.getAttribute(this.swingName), param1RTFGenerator, param1Boolean);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean writeValue(Object param1Object, RTFGenerator param1RTFGenerator, boolean param1Boolean) throws IOException {
/* 181 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class BooleanAttribute
/*     */     extends GenericAttribute
/*     */     implements RTFAttribute
/*     */   {
/*     */     boolean rtfDefault;
/*     */     boolean swingDefault;
/* 192 */     protected static final Boolean True = Boolean.valueOf(true);
/* 193 */     protected static final Boolean False = Boolean.valueOf(false);
/*     */ 
/*     */ 
/*     */     
/*     */     public BooleanAttribute(int param1Int, Object param1Object, String param1String, boolean param1Boolean1, boolean param1Boolean2) {
/* 198 */       super(param1Int, param1Object, param1String);
/* 199 */       this.swingDefault = param1Boolean1;
/* 200 */       this.rtfDefault = param1Boolean2;
/*     */     }
/*     */ 
/*     */     
/*     */     public BooleanAttribute(int param1Int, Object param1Object, String param1String) {
/* 205 */       super(param1Int, param1Object, param1String);
/*     */       
/* 207 */       this.swingDefault = false;
/* 208 */       this.rtfDefault = false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean set(MutableAttributeSet param1MutableAttributeSet) {
/* 215 */       param1MutableAttributeSet.addAttribute(this.swingName, True);
/*     */       
/* 217 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean set(MutableAttributeSet param1MutableAttributeSet, int param1Int) {
/* 223 */       Boolean bool = (param1Int != 0) ? True : False;
/*     */       
/* 225 */       param1MutableAttributeSet.addAttribute(this.swingName, bool);
/*     */       
/* 227 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean setDefault(MutableAttributeSet param1MutableAttributeSet) {
/* 232 */       if (this.swingDefault != this.rtfDefault || param1MutableAttributeSet
/* 233 */         .getAttribute(this.swingName) != null)
/* 234 */         param1MutableAttributeSet.addAttribute(this.swingName, Boolean.valueOf(this.rtfDefault)); 
/* 235 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean writeValue(Object param1Object, RTFGenerator param1RTFGenerator, boolean param1Boolean) throws IOException {
/*     */       Boolean bool;
/* 245 */       if (param1Object == null) {
/* 246 */         bool = Boolean.valueOf(this.swingDefault);
/*     */       } else {
/* 248 */         bool = (Boolean)param1Object;
/*     */       } 
/* 250 */       if (param1Boolean || bool.booleanValue() != this.rtfDefault) {
/* 251 */         if (bool.booleanValue()) {
/* 252 */           param1RTFGenerator.writeControlWord(this.rtfName);
/*     */         } else {
/* 254 */           param1RTFGenerator.writeControlWord(this.rtfName, 0);
/*     */         } 
/*     */       }
/* 257 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class AssertiveAttribute
/*     */     extends GenericAttribute
/*     */     implements RTFAttribute
/*     */   {
/*     */     Object swingValue;
/*     */ 
/*     */     
/*     */     public AssertiveAttribute(int param1Int, Object param1Object, String param1String) {
/* 270 */       super(param1Int, param1Object, param1String);
/* 271 */       this.swingValue = Boolean.valueOf(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public AssertiveAttribute(int param1Int, Object param1Object1, String param1String, Object param1Object2) {
/* 276 */       super(param1Int, param1Object1, param1String);
/* 277 */       this.swingValue = param1Object2;
/*     */     }
/*     */ 
/*     */     
/*     */     public AssertiveAttribute(int param1Int1, Object param1Object, String param1String, int param1Int2) {
/* 282 */       super(param1Int1, param1Object, param1String);
/* 283 */       this.swingValue = Integer.valueOf(param1Int2);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean set(MutableAttributeSet param1MutableAttributeSet) {
/* 288 */       if (this.swingValue == null) {
/* 289 */         param1MutableAttributeSet.removeAttribute(this.swingName);
/*     */       } else {
/* 291 */         param1MutableAttributeSet.addAttribute(this.swingName, this.swingValue);
/*     */       } 
/* 293 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean set(MutableAttributeSet param1MutableAttributeSet, int param1Int) {
/* 298 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean setDefault(MutableAttributeSet param1MutableAttributeSet) {
/* 303 */       param1MutableAttributeSet.removeAttribute(this.swingName);
/* 304 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean writeValue(Object param1Object, RTFGenerator param1RTFGenerator, boolean param1Boolean) throws IOException {
/* 312 */       if (param1Object == null) {
/* 313 */         return !param1Boolean;
/*     */       }
/*     */       
/* 316 */       if (param1Object.equals(this.swingValue)) {
/* 317 */         param1RTFGenerator.writeControlWord(this.rtfName);
/* 318 */         return true;
/*     */       } 
/*     */       
/* 321 */       return !param1Boolean;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class NumericAttribute
/*     */     extends GenericAttribute
/*     */     implements RTFAttribute
/*     */   {
/*     */     int rtfDefault;
/*     */     
/*     */     Number swingDefault;
/*     */     float scale;
/*     */     
/*     */     protected NumericAttribute(int param1Int, Object param1Object, String param1String) {
/* 336 */       super(param1Int, param1Object, param1String);
/* 337 */       this.rtfDefault = 0;
/* 338 */       this.swingDefault = null;
/* 339 */       this.scale = 1.0F;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public NumericAttribute(int param1Int1, Object param1Object, String param1String, int param1Int2, int param1Int3) {
/* 345 */       this(param1Int1, param1Object, param1String, Integer.valueOf(param1Int2), param1Int3, 1.0F);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public NumericAttribute(int param1Int1, Object param1Object, String param1String, Number param1Number, int param1Int2, float param1Float) {
/* 351 */       super(param1Int1, param1Object, param1String);
/* 352 */       this.swingDefault = param1Number;
/* 353 */       this.rtfDefault = param1Int2;
/* 354 */       this.scale = param1Float;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public static NumericAttribute NewTwips(int param1Int1, Object param1Object, String param1String, float param1Float, int param1Int2) {
/* 360 */       return new NumericAttribute(param1Int1, param1Object, param1String, new Float(param1Float), param1Int2, 20.0F);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public static NumericAttribute NewTwips(int param1Int1, Object param1Object, String param1String, int param1Int2) {
/* 366 */       return new NumericAttribute(param1Int1, param1Object, param1String, null, param1Int2, 20.0F);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean set(MutableAttributeSet param1MutableAttributeSet) {
/* 371 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean set(MutableAttributeSet param1MutableAttributeSet, int param1Int) {
/*     */       Float float_;
/* 378 */       if (this.scale == 1.0F) {
/* 379 */         Integer integer = Integer.valueOf(param1Int);
/*     */       } else {
/* 381 */         float_ = new Float(param1Int / this.scale);
/* 382 */       }  param1MutableAttributeSet.addAttribute(this.swingName, float_);
/* 383 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean setDefault(MutableAttributeSet param1MutableAttributeSet) {
/* 388 */       Number number = (Number)param1MutableAttributeSet.getAttribute(this.swingName);
/* 389 */       if (number == null)
/* 390 */         number = this.swingDefault; 
/* 391 */       if (number != null && ((this.scale == 1.0F && number
/* 392 */         .intValue() == this.rtfDefault) || 
/* 393 */         Math.round(number.floatValue() * this.scale) == this.rtfDefault))
/*     */       {
/* 395 */         return true; } 
/* 396 */       set(param1MutableAttributeSet, this.rtfDefault);
/* 397 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean writeValue(Object param1Object, RTFGenerator param1RTFGenerator, boolean param1Boolean) throws IOException {
/* 405 */       Number number = (Number)param1Object;
/* 406 */       if (number == null)
/* 407 */         number = this.swingDefault; 
/* 408 */       if (number == null)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 413 */         return true;
/*     */       }
/* 415 */       int i = Math.round(number.floatValue() * this.scale);
/* 416 */       if (param1Boolean || i != this.rtfDefault)
/* 417 */         param1RTFGenerator.writeControlWord(this.rtfName, i); 
/* 418 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/rtf/RTFAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */