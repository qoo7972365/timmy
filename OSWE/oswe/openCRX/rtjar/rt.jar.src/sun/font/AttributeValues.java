/*     */ package sun.font;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.font.GraphicAttribute;
/*     */ import java.awt.font.NumericShaper;
/*     */ import java.awt.font.TextAttribute;
/*     */ import java.awt.font.TransformAttribute;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.NoninvertibleTransformException;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.im.InputMethodHighlight;
/*     */ import java.text.Annotation;
/*     */ import java.text.AttributedCharacterIterator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
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
/*     */ public final class AttributeValues
/*     */   implements Cloneable
/*     */ {
/*     */   private int defined;
/*     */   private int nondefault;
/*  64 */   private String family = "Default";
/*  65 */   private float weight = 1.0F;
/*  66 */   private float width = 1.0F;
/*     */   private float posture;
/*  68 */   private float size = 12.0F;
/*     */   private float tracking;
/*     */   private NumericShaper numericShaping;
/*     */   private AffineTransform transform;
/*     */   private GraphicAttribute charReplacement;
/*     */   private Paint foreground;
/*     */   private Paint background;
/*  75 */   private float justification = 1.0F;
/*     */   
/*     */   private Object imHighlight;
/*     */   private Font font;
/*  79 */   private byte imUnderline = -1;
/*     */   private byte superscript;
/*  81 */   private byte underline = -1;
/*  82 */   private byte runDirection = -2;
/*     */   
/*     */   private byte bidiEmbedding;
/*     */   
/*     */   private byte kerning;
/*     */   private byte ligatures;
/*     */   private boolean strikethrough;
/*     */   private boolean swapColors;
/*     */   private AffineTransform baselineTransform;
/*     */   private AffineTransform charTransform;
/*  92 */   private static final AttributeValues DEFAULT = new AttributeValues();
/*     */   
/*     */   public String getFamily() {
/*  95 */     return this.family; } public void setFamily(String paramString) {
/*  96 */     this.family = paramString; update(EAttribute.EFAMILY);
/*     */   }
/*  98 */   public float getWeight() { return this.weight; } public void setWeight(float paramFloat) {
/*  99 */     this.weight = paramFloat; update(EAttribute.EWEIGHT);
/*     */   }
/* 101 */   public float getWidth() { return this.width; } public void setWidth(float paramFloat) {
/* 102 */     this.width = paramFloat; update(EAttribute.EWIDTH);
/*     */   }
/* 104 */   public float getPosture() { return this.posture; } public void setPosture(float paramFloat) {
/* 105 */     this.posture = paramFloat; update(EAttribute.EPOSTURE);
/*     */   }
/* 107 */   public float getSize() { return this.size; } public void setSize(float paramFloat) {
/* 108 */     this.size = paramFloat; update(EAttribute.ESIZE);
/*     */   } public AffineTransform getTransform() {
/* 110 */     return this.transform;
/*     */   } public void setTransform(AffineTransform paramAffineTransform) {
/* 112 */     this.transform = (paramAffineTransform == null || paramAffineTransform.isIdentity()) ? DEFAULT.transform : new AffineTransform(paramAffineTransform);
/*     */ 
/*     */     
/* 115 */     updateDerivedTransforms();
/* 116 */     update(EAttribute.ETRANSFORM);
/*     */   }
/*     */   public void setTransform(TransformAttribute paramTransformAttribute) {
/* 119 */     this
/*     */       
/* 121 */       .transform = (paramTransformAttribute == null || paramTransformAttribute.isIdentity()) ? DEFAULT.transform : paramTransformAttribute.getTransform();
/* 122 */     updateDerivedTransforms();
/* 123 */     update(EAttribute.ETRANSFORM);
/*     */   }
/*     */   public int getSuperscript() {
/* 126 */     return this.superscript;
/*     */   } public void setSuperscript(int paramInt) {
/* 128 */     this.superscript = (byte)paramInt; update(EAttribute.ESUPERSCRIPT);
/*     */   }
/* 130 */   public Font getFont() { return this.font; } public void setFont(Font paramFont) {
/* 131 */     this.font = paramFont; update(EAttribute.EFONT);
/*     */   } public GraphicAttribute getCharReplacement() {
/* 133 */     return this.charReplacement;
/*     */   } public void setCharReplacement(GraphicAttribute paramGraphicAttribute) {
/* 135 */     this.charReplacement = paramGraphicAttribute; update(EAttribute.ECHAR_REPLACEMENT);
/*     */   } public Paint getForeground() {
/* 137 */     return this.foreground;
/*     */   } public void setForeground(Paint paramPaint) {
/* 139 */     this.foreground = paramPaint; update(EAttribute.EFOREGROUND);
/*     */   } public Paint getBackground() {
/* 141 */     return this.background;
/*     */   } public void setBackground(Paint paramPaint) {
/* 143 */     this.background = paramPaint; update(EAttribute.EBACKGROUND);
/*     */   } public int getUnderline() {
/* 145 */     return this.underline;
/*     */   } public void setUnderline(int paramInt) {
/* 147 */     this.underline = (byte)paramInt; update(EAttribute.EUNDERLINE);
/*     */   } public boolean getStrikethrough() {
/* 149 */     return this.strikethrough;
/*     */   } public void setStrikethrough(boolean paramBoolean) {
/* 151 */     this.strikethrough = paramBoolean; update(EAttribute.ESTRIKETHROUGH);
/*     */   } public int getRunDirection() {
/* 153 */     return this.runDirection;
/*     */   } public void setRunDirection(int paramInt) {
/* 155 */     this.runDirection = (byte)paramInt; update(EAttribute.ERUN_DIRECTION);
/*     */   } public int getBidiEmbedding() {
/* 157 */     return this.bidiEmbedding;
/*     */   } public void setBidiEmbedding(int paramInt) {
/* 159 */     this.bidiEmbedding = (byte)paramInt; update(EAttribute.EBIDI_EMBEDDING);
/*     */   } public float getJustification() {
/* 161 */     return this.justification;
/*     */   } public void setJustification(float paramFloat) {
/* 163 */     this.justification = paramFloat; update(EAttribute.EJUSTIFICATION);
/*     */   } public Object getInputMethodHighlight() {
/* 165 */     return this.imHighlight;
/*     */   } public void setInputMethodHighlight(Annotation paramAnnotation) {
/* 167 */     this.imHighlight = paramAnnotation; update(EAttribute.EINPUT_METHOD_HIGHLIGHT);
/*     */   } public void setInputMethodHighlight(InputMethodHighlight paramInputMethodHighlight) {
/* 169 */     this.imHighlight = paramInputMethodHighlight; update(EAttribute.EINPUT_METHOD_HIGHLIGHT);
/*     */   } public int getInputMethodUnderline() {
/* 171 */     return this.imUnderline;
/*     */   } public void setInputMethodUnderline(int paramInt) {
/* 173 */     this.imUnderline = (byte)paramInt; update(EAttribute.EINPUT_METHOD_UNDERLINE);
/*     */   } public boolean getSwapColors() {
/* 175 */     return this.swapColors;
/*     */   } public void setSwapColors(boolean paramBoolean) {
/* 177 */     this.swapColors = paramBoolean; update(EAttribute.ESWAP_COLORS);
/*     */   } public NumericShaper getNumericShaping() {
/* 179 */     return this.numericShaping;
/*     */   } public void setNumericShaping(NumericShaper paramNumericShaper) {
/* 181 */     this.numericShaping = paramNumericShaper; update(EAttribute.ENUMERIC_SHAPING);
/*     */   } public int getKerning() {
/* 183 */     return this.kerning;
/*     */   } public void setKerning(int paramInt) {
/* 185 */     this.kerning = (byte)paramInt; update(EAttribute.EKERNING);
/*     */   } public float getTracking() {
/* 187 */     return this.tracking;
/*     */   } public void setTracking(float paramFloat) {
/* 189 */     this.tracking = (byte)(int)paramFloat; update(EAttribute.ETRACKING);
/*     */   } public int getLigatures() {
/* 191 */     return this.ligatures;
/*     */   } public void setLigatures(int paramInt) {
/* 193 */     this.ligatures = (byte)paramInt; update(EAttribute.ELIGATURES);
/*     */   }
/*     */   
/* 196 */   public AffineTransform getBaselineTransform() { return this.baselineTransform; } public AffineTransform getCharTransform() {
/* 197 */     return this.charTransform;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getMask(EAttribute paramEAttribute) {
/* 202 */     return paramEAttribute.mask;
/*     */   }
/*     */   
/*     */   public static int getMask(EAttribute... paramVarArgs) {
/* 206 */     int i = 0;
/* 207 */     for (EAttribute eAttribute : paramVarArgs) {
/* 208 */       i |= eAttribute.mask;
/*     */     }
/* 210 */     return i;
/*     */   }
/*     */ 
/*     */   
/* 214 */   public static final int MASK_ALL = getMask(EAttribute.class.getEnumConstants()); private static final String DEFINED_KEY = "sun.font.attributevalues.defined_key";
/*     */   
/*     */   public void unsetDefault() {
/* 217 */     this.defined &= this.nondefault;
/*     */   }
/*     */   
/*     */   public void defineAll(int paramInt) {
/* 221 */     this.defined |= paramInt;
/* 222 */     if ((this.defined & EAttribute.EBASELINE_TRANSFORM.mask) != 0) {
/* 223 */       throw new InternalError("can't define derived attribute");
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean allDefined(int paramInt) {
/* 228 */     return ((this.defined & paramInt) == paramInt);
/*     */   }
/*     */   
/*     */   public boolean anyDefined(int paramInt) {
/* 232 */     return ((this.defined & paramInt) != 0);
/*     */   }
/*     */   
/*     */   public boolean anyNonDefault(int paramInt) {
/* 236 */     return ((this.nondefault & paramInt) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDefined(EAttribute paramEAttribute) {
/* 242 */     return ((this.defined & paramEAttribute.mask) != 0);
/*     */   }
/*     */   
/*     */   public boolean isNonDefault(EAttribute paramEAttribute) {
/* 246 */     return ((this.nondefault & paramEAttribute.mask) != 0);
/*     */   }
/*     */   
/*     */   public void setDefault(EAttribute paramEAttribute) {
/* 250 */     if (paramEAttribute.att == null) {
/* 251 */       throw new InternalError("can't set default derived attribute: " + paramEAttribute);
/*     */     }
/* 253 */     i_set(paramEAttribute, DEFAULT);
/* 254 */     this.defined |= paramEAttribute.mask;
/* 255 */     this.nondefault &= paramEAttribute.mask ^ 0xFFFFFFFF;
/*     */   }
/*     */   
/*     */   public void unset(EAttribute paramEAttribute) {
/* 259 */     if (paramEAttribute.att == null) {
/* 260 */       throw new InternalError("can't unset derived attribute: " + paramEAttribute);
/*     */     }
/* 262 */     i_set(paramEAttribute, DEFAULT);
/* 263 */     this.defined &= paramEAttribute.mask ^ 0xFFFFFFFF;
/* 264 */     this.nondefault &= paramEAttribute.mask ^ 0xFFFFFFFF;
/*     */   }
/*     */   
/*     */   public void set(EAttribute paramEAttribute, AttributeValues paramAttributeValues) {
/* 268 */     if (paramEAttribute.att == null) {
/* 269 */       throw new InternalError("can't set derived attribute: " + paramEAttribute);
/*     */     }
/* 271 */     if (paramAttributeValues == null || paramAttributeValues == DEFAULT) {
/* 272 */       setDefault(paramEAttribute);
/*     */     }
/* 274 */     else if ((paramAttributeValues.defined & paramEAttribute.mask) != 0) {
/* 275 */       i_set(paramEAttribute, paramAttributeValues);
/* 276 */       update(paramEAttribute);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(EAttribute paramEAttribute, Object paramObject) {
/* 282 */     if (paramEAttribute.att == null) {
/* 283 */       throw new InternalError("can't set derived attribute: " + paramEAttribute);
/*     */     }
/* 285 */     if (paramObject != null) {
/*     */       try {
/* 287 */         i_set(paramEAttribute, paramObject);
/* 288 */         update(paramEAttribute);
/*     */         return;
/* 290 */       } catch (Exception exception) {}
/*     */     }
/*     */     
/* 293 */     setDefault(paramEAttribute);
/*     */   }
/*     */   
/*     */   public Object get(EAttribute paramEAttribute) {
/* 297 */     if (paramEAttribute.att == null) {
/* 298 */       throw new InternalError("can't get derived attribute: " + paramEAttribute);
/*     */     }
/* 300 */     if ((this.nondefault & paramEAttribute.mask) != 0) {
/* 301 */       return i_get(paramEAttribute);
/*     */     }
/* 303 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeValues merge(Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap) {
/* 309 */     return merge(paramMap, MASK_ALL);
/*     */   }
/*     */ 
/*     */   
/*     */   public AttributeValues merge(Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap, int paramInt) {
/* 314 */     if (paramMap instanceof AttributeMap && ((AttributeMap)paramMap)
/* 315 */       .getValues() != null) {
/* 316 */       merge(((AttributeMap)paramMap).getValues(), paramInt);
/* 317 */     } else if (paramMap != null && !paramMap.isEmpty()) {
/* 318 */       for (Map.Entry<? extends AttributedCharacterIterator.Attribute, ?> entry : paramMap.entrySet()) {
/*     */         try {
/* 320 */           EAttribute eAttribute = EAttribute.forAttribute((AttributedCharacterIterator.Attribute)entry.getKey());
/* 321 */           if (eAttribute != null && (paramInt & eAttribute.mask) != 0) {
/* 322 */             set(eAttribute, entry.getValue());
/*     */           }
/* 324 */         } catch (ClassCastException classCastException) {}
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 329 */     return this;
/*     */   }
/*     */   
/*     */   public AttributeValues merge(AttributeValues paramAttributeValues) {
/* 333 */     return merge(paramAttributeValues, MASK_ALL);
/*     */   }
/*     */   
/*     */   public AttributeValues merge(AttributeValues paramAttributeValues, int paramInt) {
/* 337 */     int i = paramInt & paramAttributeValues.defined;
/* 338 */     for (EAttribute eAttribute : EAttribute.atts) {
/* 339 */       if (i == 0) {
/*     */         break;
/*     */       }
/* 342 */       if ((i & eAttribute.mask) != 0) {
/* 343 */         i &= eAttribute.mask ^ 0xFFFFFFFF;
/* 344 */         i_set(eAttribute, paramAttributeValues);
/* 345 */         update(eAttribute);
/*     */       } 
/*     */     } 
/* 348 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AttributeValues fromMap(Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap) {
/* 354 */     return fromMap(paramMap, MASK_ALL);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AttributeValues fromMap(Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap, int paramInt) {
/* 359 */     return (new AttributeValues()).merge(paramMap, paramInt);
/*     */   }
/*     */   
/*     */   public Map<TextAttribute, Object> toMap(Map<TextAttribute, Object> paramMap) {
/* 363 */     if (paramMap == null)
/* 364 */       paramMap = new HashMap<>(); 
/*     */     int i;
/*     */     byte b;
/* 367 */     for (i = this.defined, b = 0; i != 0; b++) {
/* 368 */       EAttribute eAttribute = EAttribute.atts[b];
/* 369 */       if ((i & eAttribute.mask) != 0) {
/* 370 */         i &= eAttribute.mask ^ 0xFFFFFFFF;
/* 371 */         paramMap.put(eAttribute.att, get(eAttribute));
/*     */       } 
/*     */     } 
/*     */     
/* 375 */     return paramMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean is16Hashtable(Hashtable<Object, Object> paramHashtable) {
/* 383 */     return paramHashtable.containsKey("sun.font.attributevalues.defined_key");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static AttributeValues fromSerializableHashtable(Hashtable<Object, Object> paramHashtable) {
/* 389 */     AttributeValues attributeValues = new AttributeValues();
/* 390 */     if (paramHashtable != null && !paramHashtable.isEmpty()) {
/* 391 */       for (Map.Entry<Object, Object> entry : paramHashtable.entrySet()) {
/* 392 */         Object object1 = entry.getKey();
/* 393 */         Object object2 = entry.getValue();
/* 394 */         if (object1.equals("sun.font.attributevalues.defined_key")) {
/* 395 */           attributeValues.defineAll(((Integer)object2).intValue());
/*     */           continue;
/*     */         } 
/*     */         try {
/* 399 */           EAttribute eAttribute = EAttribute.forAttribute((AttributedCharacterIterator.Attribute)object1);
/* 400 */           if (eAttribute != null) {
/* 401 */             attributeValues.set(eAttribute, object2);
/*     */           }
/*     */         }
/* 404 */         catch (ClassCastException classCastException) {}
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 409 */     return attributeValues;
/*     */   }
/*     */   
/*     */   public Hashtable<Object, Object> toSerializableHashtable() {
/* 413 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 414 */     int i = this.defined; int j; byte b;
/* 415 */     for (j = this.defined, b = 0; j != 0; b++) {
/* 416 */       EAttribute eAttribute = EAttribute.atts[b];
/* 417 */       if ((j & eAttribute.mask) != 0) {
/* 418 */         j &= eAttribute.mask ^ 0xFFFFFFFF;
/* 419 */         Object object = get(eAttribute);
/* 420 */         if (object != null)
/*     */         {
/* 422 */           if (object instanceof java.io.Serializable) {
/* 423 */             hashtable.put(eAttribute.att, object);
/*     */           } else {
/* 425 */             i &= eAttribute.mask ^ 0xFFFFFFFF;
/*     */           }  } 
/*     */       } 
/*     */     } 
/* 429 */     hashtable.put("sun.font.attributevalues.defined_key", Integer.valueOf(i));
/*     */     
/* 431 */     return hashtable;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 436 */     return this.defined << 8 ^ this.nondefault;
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*     */     try {
/* 441 */       return equals((AttributeValues)paramObject);
/*     */     }
/* 443 */     catch (ClassCastException classCastException) {
/*     */       
/* 445 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(AttributeValues paramAttributeValues) {
/* 453 */     if (paramAttributeValues == null) return false; 
/* 454 */     if (paramAttributeValues == this) return true;
/*     */     
/* 456 */     return (this.defined == paramAttributeValues.defined && this.nondefault == paramAttributeValues.nondefault && this.underline == paramAttributeValues.underline && this.strikethrough == paramAttributeValues.strikethrough && this.superscript == paramAttributeValues.superscript && this.width == paramAttributeValues.width && this.kerning == paramAttributeValues.kerning && this.tracking == paramAttributeValues.tracking && this.ligatures == paramAttributeValues.ligatures && this.runDirection == paramAttributeValues.runDirection && this.bidiEmbedding == paramAttributeValues.bidiEmbedding && this.swapColors == paramAttributeValues.swapColors && 
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
/* 468 */       equals(this.transform, paramAttributeValues.transform) && 
/* 469 */       equals(this.foreground, paramAttributeValues.foreground) && 
/* 470 */       equals(this.background, paramAttributeValues.background) && 
/* 471 */       equals(this.numericShaping, paramAttributeValues.numericShaping) && 
/* 472 */       equals(Float.valueOf(this.justification), Float.valueOf(paramAttributeValues.justification)) && 
/* 473 */       equals(this.charReplacement, paramAttributeValues.charReplacement) && this.size == paramAttributeValues.size && this.weight == paramAttributeValues.weight && this.posture == paramAttributeValues.posture && 
/*     */ 
/*     */ 
/*     */       
/* 477 */       equals(this.family, paramAttributeValues.family) && 
/* 478 */       equals(this.font, paramAttributeValues.font) && this.imUnderline == paramAttributeValues.imUnderline && 
/*     */       
/* 480 */       equals(this.imHighlight, paramAttributeValues.imHighlight));
/*     */   }
/*     */   
/*     */   public AttributeValues clone() {
/*     */     try {
/* 485 */       AttributeValues attributeValues = (AttributeValues)super.clone();
/* 486 */       if (this.transform != null) {
/* 487 */         attributeValues.transform = new AffineTransform(this.transform);
/* 488 */         attributeValues.updateDerivedTransforms();
/*     */       } 
/*     */ 
/*     */       
/* 492 */       return attributeValues;
/*     */     }
/* 494 */     catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 496 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 501 */     StringBuilder stringBuilder = new StringBuilder();
/* 502 */     stringBuilder.append('{'); int i; byte b;
/* 503 */     for (i = this.defined, b = 0; i != 0; b++) {
/* 504 */       EAttribute eAttribute = EAttribute.atts[b];
/* 505 */       if ((i & eAttribute.mask) != 0) {
/* 506 */         i &= eAttribute.mask ^ 0xFFFFFFFF;
/* 507 */         if (stringBuilder.length() > 1) {
/* 508 */           stringBuilder.append(", ");
/*     */         }
/* 510 */         stringBuilder.append(eAttribute);
/* 511 */         stringBuilder.append('=');
/* 512 */         switch (eAttribute) { case EFAMILY:
/* 513 */             stringBuilder.append('"');
/* 514 */             stringBuilder.append(this.family);
/* 515 */             stringBuilder.append('"'); break;
/* 516 */           case EWEIGHT: stringBuilder.append(this.weight); break;
/* 517 */           case EWIDTH: stringBuilder.append(this.width); break;
/* 518 */           case EPOSTURE: stringBuilder.append(this.posture); break;
/* 519 */           case ESIZE: stringBuilder.append(this.size); break;
/* 520 */           case ETRANSFORM: stringBuilder.append(this.transform); break;
/* 521 */           case ESUPERSCRIPT: stringBuilder.append(this.superscript); break;
/* 522 */           case EFONT: stringBuilder.append(this.font); break;
/* 523 */           case ECHAR_REPLACEMENT: stringBuilder.append(this.charReplacement); break;
/* 524 */           case EFOREGROUND: stringBuilder.append(this.foreground); break;
/* 525 */           case EBACKGROUND: stringBuilder.append(this.background); break;
/* 526 */           case EUNDERLINE: stringBuilder.append(this.underline); break;
/* 527 */           case ESTRIKETHROUGH: stringBuilder.append(this.strikethrough); break;
/* 528 */           case ERUN_DIRECTION: stringBuilder.append(this.runDirection); break;
/* 529 */           case EBIDI_EMBEDDING: stringBuilder.append(this.bidiEmbedding); break;
/* 530 */           case EJUSTIFICATION: stringBuilder.append(this.justification); break;
/* 531 */           case EINPUT_METHOD_HIGHLIGHT: stringBuilder.append(this.imHighlight); break;
/* 532 */           case EINPUT_METHOD_UNDERLINE: stringBuilder.append(this.imUnderline); break;
/* 533 */           case ESWAP_COLORS: stringBuilder.append(this.swapColors); break;
/* 534 */           case ENUMERIC_SHAPING: stringBuilder.append(this.numericShaping); break;
/* 535 */           case EKERNING: stringBuilder.append(this.kerning); break;
/* 536 */           case ELIGATURES: stringBuilder.append(this.ligatures); break;
/* 537 */           case ETRACKING: stringBuilder.append(this.tracking); break;
/* 538 */           default: throw new InternalError(); }
/*     */         
/* 540 */         if ((this.nondefault & eAttribute.mask) == 0) {
/* 541 */           stringBuilder.append('*');
/*     */         }
/*     */       } 
/*     */     } 
/* 545 */     stringBuilder.append("[btx=" + this.baselineTransform + ", ctx=" + this.charTransform + "]");
/* 546 */     stringBuilder.append('}');
/* 547 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean equals(Object paramObject1, Object paramObject2) {
/* 553 */     return (paramObject1 == null) ? ((paramObject2 == null)) : paramObject1.equals(paramObject2);
/*     */   }
/*     */   
/*     */   private void update(EAttribute paramEAttribute) {
/* 557 */     this.defined |= paramEAttribute.mask;
/* 558 */     if (i_validate(paramEAttribute)) {
/* 559 */       if (i_equals(paramEAttribute, DEFAULT)) {
/* 560 */         this.nondefault &= paramEAttribute.mask ^ 0xFFFFFFFF;
/*     */       } else {
/* 562 */         this.nondefault |= paramEAttribute.mask;
/*     */       } 
/*     */     } else {
/* 565 */       setDefault(paramEAttribute);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void i_set(EAttribute paramEAttribute, AttributeValues paramAttributeValues) {
/* 572 */     switch (paramEAttribute) { case EFAMILY:
/* 573 */         this.family = paramAttributeValues.family; return;
/* 574 */       case EWEIGHT: this.weight = paramAttributeValues.weight; return;
/* 575 */       case EWIDTH: this.width = paramAttributeValues.width; return;
/* 576 */       case EPOSTURE: this.posture = paramAttributeValues.posture; return;
/* 577 */       case ESIZE: this.size = paramAttributeValues.size; return;
/* 578 */       case ETRANSFORM: this.transform = paramAttributeValues.transform; updateDerivedTransforms(); return;
/* 579 */       case ESUPERSCRIPT: this.superscript = paramAttributeValues.superscript; return;
/* 580 */       case EFONT: this.font = paramAttributeValues.font; return;
/* 581 */       case ECHAR_REPLACEMENT: this.charReplacement = paramAttributeValues.charReplacement; return;
/* 582 */       case EFOREGROUND: this.foreground = paramAttributeValues.foreground; return;
/* 583 */       case EBACKGROUND: this.background = paramAttributeValues.background; return;
/* 584 */       case EUNDERLINE: this.underline = paramAttributeValues.underline; return;
/* 585 */       case ESTRIKETHROUGH: this.strikethrough = paramAttributeValues.strikethrough; return;
/* 586 */       case ERUN_DIRECTION: this.runDirection = paramAttributeValues.runDirection; return;
/* 587 */       case EBIDI_EMBEDDING: this.bidiEmbedding = paramAttributeValues.bidiEmbedding; return;
/* 588 */       case EJUSTIFICATION: this.justification = paramAttributeValues.justification; return;
/* 589 */       case EINPUT_METHOD_HIGHLIGHT: this.imHighlight = paramAttributeValues.imHighlight; return;
/* 590 */       case EINPUT_METHOD_UNDERLINE: this.imUnderline = paramAttributeValues.imUnderline; return;
/* 591 */       case ESWAP_COLORS: this.swapColors = paramAttributeValues.swapColors; return;
/* 592 */       case ENUMERIC_SHAPING: this.numericShaping = paramAttributeValues.numericShaping; return;
/* 593 */       case EKERNING: this.kerning = paramAttributeValues.kerning; return;
/* 594 */       case ELIGATURES: this.ligatures = paramAttributeValues.ligatures; return;
/* 595 */       case ETRACKING: this.tracking = paramAttributeValues.tracking; return; }
/* 596 */      throw new InternalError();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean i_equals(EAttribute paramEAttribute, AttributeValues paramAttributeValues) {
/* 601 */     switch (paramEAttribute) { case EFAMILY:
/* 602 */         return equals(this.family, paramAttributeValues.family);
/* 603 */       case EWEIGHT: return (this.weight == paramAttributeValues.weight);
/* 604 */       case EWIDTH: return (this.width == paramAttributeValues.width);
/* 605 */       case EPOSTURE: return (this.posture == paramAttributeValues.posture);
/* 606 */       case ESIZE: return (this.size == paramAttributeValues.size);
/* 607 */       case ETRANSFORM: return equals(this.transform, paramAttributeValues.transform);
/* 608 */       case ESUPERSCRIPT: return (this.superscript == paramAttributeValues.superscript);
/* 609 */       case EFONT: return equals(this.font, paramAttributeValues.font);
/* 610 */       case ECHAR_REPLACEMENT: return equals(this.charReplacement, paramAttributeValues.charReplacement);
/* 611 */       case EFOREGROUND: return equals(this.foreground, paramAttributeValues.foreground);
/* 612 */       case EBACKGROUND: return equals(this.background, paramAttributeValues.background);
/* 613 */       case EUNDERLINE: return (this.underline == paramAttributeValues.underline);
/* 614 */       case ESTRIKETHROUGH: return (this.strikethrough == paramAttributeValues.strikethrough);
/* 615 */       case ERUN_DIRECTION: return (this.runDirection == paramAttributeValues.runDirection);
/* 616 */       case EBIDI_EMBEDDING: return (this.bidiEmbedding == paramAttributeValues.bidiEmbedding);
/* 617 */       case EJUSTIFICATION: return (this.justification == paramAttributeValues.justification);
/* 618 */       case EINPUT_METHOD_HIGHLIGHT: return equals(this.imHighlight, paramAttributeValues.imHighlight);
/* 619 */       case EINPUT_METHOD_UNDERLINE: return (this.imUnderline == paramAttributeValues.imUnderline);
/* 620 */       case ESWAP_COLORS: return (this.swapColors == paramAttributeValues.swapColors);
/* 621 */       case ENUMERIC_SHAPING: return equals(this.numericShaping, paramAttributeValues.numericShaping);
/* 622 */       case EKERNING: return (this.kerning == paramAttributeValues.kerning);
/* 623 */       case ELIGATURES: return (this.ligatures == paramAttributeValues.ligatures);
/* 624 */       case ETRACKING: return (this.tracking == paramAttributeValues.tracking); }
/* 625 */      throw new InternalError();
/*     */   }
/*     */ 
/*     */   
/*     */   private void i_set(EAttribute paramEAttribute, Object paramObject) {
/* 630 */     switch (paramEAttribute) { case EFAMILY:
/* 631 */         this.family = ((String)paramObject).trim(); return;
/* 632 */       case EWEIGHT: this.weight = ((Number)paramObject).floatValue(); return;
/* 633 */       case EWIDTH: this.width = ((Number)paramObject).floatValue(); return;
/* 634 */       case EPOSTURE: this.posture = ((Number)paramObject).floatValue(); return;
/* 635 */       case ESIZE: this.size = ((Number)paramObject).floatValue(); return;
/*     */       case ETRANSFORM:
/* 637 */         if (paramObject instanceof TransformAttribute) {
/* 638 */           TransformAttribute transformAttribute = (TransformAttribute)paramObject;
/* 639 */           if (transformAttribute.isIdentity()) {
/* 640 */             this.transform = null;
/*     */           } else {
/* 642 */             this.transform = transformAttribute.getTransform();
/*     */           } 
/*     */         } else {
/* 645 */           this.transform = new AffineTransform((AffineTransform)paramObject);
/*     */         } 
/* 647 */         updateDerivedTransforms(); return;
/*     */       case ESUPERSCRIPT:
/* 649 */         this.superscript = (byte)((Integer)paramObject).intValue(); return;
/* 650 */       case EFONT: this.font = (Font)paramObject; return;
/* 651 */       case ECHAR_REPLACEMENT: this.charReplacement = (GraphicAttribute)paramObject; return;
/* 652 */       case EFOREGROUND: this.foreground = (Paint)paramObject; return;
/* 653 */       case EBACKGROUND: this.background = (Paint)paramObject; return;
/* 654 */       case EUNDERLINE: this.underline = (byte)((Integer)paramObject).intValue(); return;
/* 655 */       case ESTRIKETHROUGH: this.strikethrough = ((Boolean)paramObject).booleanValue(); return;
/*     */       case ERUN_DIRECTION:
/* 657 */         if (paramObject instanceof Boolean) {
/* 658 */           this.runDirection = (byte)(TextAttribute.RUN_DIRECTION_LTR.equals(paramObject) ? 0 : 1);
/*     */         } else {
/* 660 */           this.runDirection = (byte)((Integer)paramObject).intValue();
/*     */         }  return;
/*     */       case EBIDI_EMBEDDING:
/* 663 */         this.bidiEmbedding = (byte)((Integer)paramObject).intValue(); return;
/* 664 */       case EJUSTIFICATION: this.justification = ((Number)paramObject).floatValue(); return;
/*     */       case EINPUT_METHOD_HIGHLIGHT:
/* 666 */         if (paramObject instanceof Annotation) {
/* 667 */           Annotation annotation = (Annotation)paramObject;
/* 668 */           this.imHighlight = annotation.getValue();
/*     */         } else {
/* 670 */           this.imHighlight = paramObject;
/*     */         }  return;
/*     */       case EINPUT_METHOD_UNDERLINE:
/* 673 */         this.imUnderline = (byte)((Integer)paramObject).intValue(); return;
/*     */       case ESWAP_COLORS:
/* 675 */         this.swapColors = ((Boolean)paramObject).booleanValue(); return;
/* 676 */       case ENUMERIC_SHAPING: this.numericShaping = (NumericShaper)paramObject; return;
/* 677 */       case EKERNING: this.kerning = (byte)((Integer)paramObject).intValue(); return;
/* 678 */       case ELIGATURES: this.ligatures = (byte)((Integer)paramObject).intValue(); return;
/* 679 */       case ETRACKING: this.tracking = ((Number)paramObject).floatValue(); return; }
/* 680 */      throw new InternalError();
/*     */   }
/*     */ 
/*     */   
/*     */   private Object i_get(EAttribute paramEAttribute) {
/* 685 */     switch (paramEAttribute) { case EFAMILY:
/* 686 */         return this.family;
/* 687 */       case EWEIGHT: return Float.valueOf(this.weight);
/* 688 */       case EWIDTH: return Float.valueOf(this.width);
/* 689 */       case EPOSTURE: return Float.valueOf(this.posture);
/* 690 */       case ESIZE: return Float.valueOf(this.size);
/*     */       case ETRANSFORM:
/* 692 */         return (this.transform == null) ? TransformAttribute.IDENTITY : new TransformAttribute(this.transform);
/*     */       
/*     */       case ESUPERSCRIPT:
/* 695 */         return Integer.valueOf(this.superscript);
/* 696 */       case EFONT: return this.font;
/* 697 */       case ECHAR_REPLACEMENT: return this.charReplacement;
/* 698 */       case EFOREGROUND: return this.foreground;
/* 699 */       case EBACKGROUND: return this.background;
/* 700 */       case EUNDERLINE: return Integer.valueOf(this.underline);
/* 701 */       case ESTRIKETHROUGH: return Boolean.valueOf(this.strikethrough);
/*     */       case ERUN_DIRECTION:
/* 703 */         switch (this.runDirection) {
/*     */           
/*     */           case 0:
/* 706 */             return TextAttribute.RUN_DIRECTION_LTR;
/* 707 */           case 1: return TextAttribute.RUN_DIRECTION_RTL;
/* 708 */         }  return null;
/*     */       
/*     */       case EBIDI_EMBEDDING:
/* 711 */         return Integer.valueOf(this.bidiEmbedding);
/* 712 */       case EJUSTIFICATION: return Float.valueOf(this.justification);
/* 713 */       case EINPUT_METHOD_HIGHLIGHT: return this.imHighlight;
/* 714 */       case EINPUT_METHOD_UNDERLINE: return Integer.valueOf(this.imUnderline);
/* 715 */       case ESWAP_COLORS: return Boolean.valueOf(this.swapColors);
/* 716 */       case ENUMERIC_SHAPING: return this.numericShaping;
/* 717 */       case EKERNING: return Integer.valueOf(this.kerning);
/* 718 */       case ELIGATURES: return Integer.valueOf(this.ligatures);
/* 719 */       case ETRACKING: return Float.valueOf(this.tracking); }
/* 720 */      throw new InternalError();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean i_validate(EAttribute paramEAttribute) {
/* 725 */     switch (paramEAttribute) { case EFAMILY:
/* 726 */         if (this.family == null || this.family.length() == 0)
/* 727 */           this.family = DEFAULT.family;  return true;
/* 728 */       case EWEIGHT: return (this.weight > 0.0F && this.weight < 10.0F);
/* 729 */       case EWIDTH: return (this.width >= 0.5F && this.width < 10.0F);
/* 730 */       case EPOSTURE: return (this.posture >= -1.0F && this.posture <= 1.0F);
/* 731 */       case ESIZE: return (this.size >= 0.0F);
/* 732 */       case ETRANSFORM: if (this.transform != null && this.transform.isIdentity())
/* 733 */           this.transform = DEFAULT.transform;  return true;
/* 734 */       case ESUPERSCRIPT: return (this.superscript >= -7 && this.superscript <= 7);
/* 735 */       case EFONT: return true;
/* 736 */       case ECHAR_REPLACEMENT: return true;
/* 737 */       case EFOREGROUND: return true;
/* 738 */       case EBACKGROUND: return true;
/* 739 */       case EUNDERLINE: return (this.underline >= -1 && this.underline < 6);
/* 740 */       case ESTRIKETHROUGH: return true;
/* 741 */       case ERUN_DIRECTION: return (this.runDirection >= -2 && this.runDirection <= 1);
/* 742 */       case EBIDI_EMBEDDING: return (this.bidiEmbedding >= -61 && this.bidiEmbedding < 62);
/* 743 */       case EJUSTIFICATION: this.justification = Math.max(0.0F, Math.min(this.justification, 1.0F));
/* 744 */         return true;
/* 745 */       case EINPUT_METHOD_HIGHLIGHT: return true;
/* 746 */       case EINPUT_METHOD_UNDERLINE: return (this.imUnderline >= -1 && this.imUnderline < 6);
/* 747 */       case ESWAP_COLORS: return true;
/* 748 */       case ENUMERIC_SHAPING: return true;
/* 749 */       case EKERNING: return (this.kerning >= 0 && this.kerning <= 1);
/* 750 */       case ELIGATURES: return (this.ligatures >= 0 && this.ligatures <= 1);
/* 751 */       case ETRACKING: return (this.tracking >= -1.0F && this.tracking <= 10.0F); }
/* 752 */      throw new InternalError("unknown attribute: " + paramEAttribute);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getJustification(Map<?, ?> paramMap) {
/* 761 */     if (paramMap != null) {
/* 762 */       if (paramMap instanceof AttributeMap && ((AttributeMap)paramMap)
/* 763 */         .getValues() != null) {
/* 764 */         return (((AttributeMap)paramMap).getValues()).justification;
/*     */       }
/* 766 */       Object object = paramMap.get(TextAttribute.JUSTIFICATION);
/* 767 */       if (object != null && object instanceof Number) {
/* 768 */         return Math.max(0.0F, Math.min(1.0F, ((Number)object).floatValue()));
/*     */       }
/*     */     } 
/* 771 */     return DEFAULT.justification;
/*     */   }
/*     */   
/*     */   public static NumericShaper getNumericShaping(Map<?, ?> paramMap) {
/* 775 */     if (paramMap != null) {
/* 776 */       if (paramMap instanceof AttributeMap && ((AttributeMap)paramMap)
/* 777 */         .getValues() != null) {
/* 778 */         return (((AttributeMap)paramMap).getValues()).numericShaping;
/*     */       }
/* 780 */       Object object = paramMap.get(TextAttribute.NUMERIC_SHAPING);
/* 781 */       if (object != null && object instanceof NumericShaper) {
/* 782 */         return (NumericShaper)object;
/*     */       }
/*     */     } 
/* 785 */     return DEFAULT.numericShaping;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeValues applyIMHighlight() {
/* 793 */     if (this.imHighlight != null) {
/* 794 */       InputMethodHighlight inputMethodHighlight = null;
/* 795 */       if (this.imHighlight instanceof InputMethodHighlight) {
/* 796 */         inputMethodHighlight = (InputMethodHighlight)this.imHighlight;
/*     */       } else {
/* 798 */         inputMethodHighlight = (InputMethodHighlight)((Annotation)this.imHighlight).getValue();
/*     */       } 
/*     */       
/* 801 */       Map<TextAttribute, ?> map = inputMethodHighlight.getStyle();
/* 802 */       if (map == null) {
/* 803 */         Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 804 */         map = toolkit.mapInputMethodHighlight(inputMethodHighlight);
/*     */       } 
/*     */       
/* 807 */       if (map != null) {
/* 808 */         return clone().merge((Map)map);
/*     */       }
/*     */     } 
/*     */     
/* 812 */     return this;
/*     */   }
/*     */   
/*     */   public static AffineTransform getBaselineTransform(Map<?, ?> paramMap) {
/* 816 */     if (paramMap != null) {
/* 817 */       AttributeValues attributeValues = null;
/* 818 */       if (paramMap instanceof AttributeMap && ((AttributeMap)paramMap)
/* 819 */         .getValues() != null) {
/* 820 */         attributeValues = ((AttributeMap)paramMap).getValues();
/* 821 */       } else if (paramMap.get(TextAttribute.TRANSFORM) != null) {
/* 822 */         attributeValues = fromMap((Map)paramMap);
/*     */       } 
/* 824 */       if (attributeValues != null) {
/* 825 */         return attributeValues.baselineTransform;
/*     */       }
/*     */     } 
/* 828 */     return null;
/*     */   }
/*     */   
/*     */   public static AffineTransform getCharTransform(Map<?, ?> paramMap) {
/* 832 */     if (paramMap != null) {
/* 833 */       AttributeValues attributeValues = null;
/* 834 */       if (paramMap instanceof AttributeMap && ((AttributeMap)paramMap)
/* 835 */         .getValues() != null) {
/* 836 */         attributeValues = ((AttributeMap)paramMap).getValues();
/* 837 */       } else if (paramMap.get(TextAttribute.TRANSFORM) != null) {
/* 838 */         attributeValues = fromMap((Map)paramMap);
/*     */       } 
/* 840 */       if (attributeValues != null) {
/* 841 */         return attributeValues.charTransform;
/*     */       }
/*     */     } 
/* 844 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateDerivedTransforms() {
/* 849 */     if (this.transform == null) {
/* 850 */       this.baselineTransform = null;
/* 851 */       this.charTransform = null;
/*     */     } else {
/* 853 */       this.charTransform = new AffineTransform(this.transform);
/* 854 */       this.baselineTransform = extractXRotation(this.charTransform, true);
/*     */       
/* 856 */       if (this.charTransform.isIdentity()) {
/* 857 */         this.charTransform = null;
/*     */       }
/*     */       
/* 860 */       if (this.baselineTransform.isIdentity()) {
/* 861 */         this.baselineTransform = null;
/*     */       }
/*     */     } 
/*     */     
/* 865 */     if (this.baselineTransform == null) {
/* 866 */       this.nondefault &= EAttribute.EBASELINE_TRANSFORM.mask ^ 0xFFFFFFFF;
/*     */     } else {
/* 868 */       this.nondefault |= EAttribute.EBASELINE_TRANSFORM.mask;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static AffineTransform extractXRotation(AffineTransform paramAffineTransform, boolean paramBoolean) {
/* 874 */     return extractRotation(new Point2D.Double(1.0D, 0.0D), paramAffineTransform, paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AffineTransform extractYRotation(AffineTransform paramAffineTransform, boolean paramBoolean) {
/* 879 */     return extractRotation(new Point2D.Double(0.0D, 1.0D), paramAffineTransform, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static AffineTransform extractRotation(Point2D.Double paramDouble, AffineTransform paramAffineTransform, boolean paramBoolean) {
/* 885 */     paramAffineTransform.deltaTransform(paramDouble, paramDouble);
/* 886 */     AffineTransform affineTransform = AffineTransform.getRotateInstance(paramDouble.x, paramDouble.y);
/*     */     
/*     */     try {
/* 889 */       AffineTransform affineTransform1 = affineTransform.createInverse();
/* 890 */       double d1 = paramAffineTransform.getTranslateX();
/* 891 */       double d2 = paramAffineTransform.getTranslateY();
/* 892 */       paramAffineTransform.preConcatenate(affineTransform1);
/* 893 */       if (paramBoolean && (
/* 894 */         d1 != 0.0D || d2 != 0.0D)) {
/* 895 */         paramAffineTransform.setTransform(paramAffineTransform.getScaleX(), paramAffineTransform.getShearY(), paramAffineTransform
/* 896 */             .getShearX(), paramAffineTransform.getScaleY(), 0.0D, 0.0D);
/* 897 */         affineTransform.setTransform(affineTransform.getScaleX(), affineTransform.getShearY(), affineTransform
/* 898 */             .getShearX(), affineTransform.getScaleY(), d1, d2);
/*     */       }
/*     */     
/*     */     }
/* 902 */     catch (NoninvertibleTransformException noninvertibleTransformException) {
/* 903 */       return null;
/*     */     } 
/* 905 */     return affineTransform;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/AttributeValues.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */