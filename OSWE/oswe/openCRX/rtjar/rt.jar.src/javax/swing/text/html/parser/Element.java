/*     */ package javax.swing.text.html.parser;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.BitSet;
/*     */ import java.util.Hashtable;
/*     */ import sun.awt.AppContext;
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
/*     */ public final class Element
/*     */   implements DTDConstants, Serializable
/*     */ {
/*     */   public int index;
/*     */   public String name;
/*     */   public boolean oStart;
/*     */   public boolean oEnd;
/*     */   public BitSet inclusions;
/*     */   public BitSet exclusions;
/*  51 */   public int type = 19;
/*     */ 
/*     */ 
/*     */   
/*     */   public ContentModel content;
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeList atts;
/*     */ 
/*     */ 
/*     */   
/*     */   public Object data;
/*     */ 
/*     */ 
/*     */   
/*     */   Element(String paramString, int paramInt) {
/*  68 */     this.name = paramString;
/*  69 */     this.index = paramInt;
/*  70 */     if (paramInt > getMaxIndex()) {
/*  71 */       AppContext.getAppContext().put(MAX_INDEX_KEY, Integer.valueOf(paramInt));
/*     */     }
/*     */   }
/*     */   
/*  75 */   private static final Object MAX_INDEX_KEY = new Object();
/*     */   
/*     */   static int getMaxIndex() {
/*  78 */     Integer integer = (Integer)AppContext.getAppContext().get(MAX_INDEX_KEY);
/*  79 */     return (integer != null) ? integer
/*  80 */       .intValue() : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  88 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean omitStart() {
/*  95 */     return this.oStart;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean omitEnd() {
/* 102 */     return this.oEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/* 109 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContentModel getContent() {
/* 116 */     return this.content;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeList getAttributes() {
/* 123 */     return this.atts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex() {
/* 130 */     return this.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 137 */     return (this.type == 17);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 144 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeList getAttribute(String paramString) {
/* 151 */     for (AttributeList attributeList = this.atts; attributeList != null; attributeList = attributeList.next) {
/* 152 */       if (attributeList.name.equals(paramString)) {
/* 153 */         return attributeList;
/*     */       }
/*     */     } 
/* 156 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeList getAttributeByValue(String paramString) {
/* 163 */     for (AttributeList attributeList = this.atts; attributeList != null; attributeList = attributeList.next) {
/* 164 */       if (attributeList.values != null && attributeList.values.contains(paramString)) {
/* 165 */         return attributeList;
/*     */       }
/*     */     } 
/* 168 */     return null;
/*     */   }
/*     */ 
/*     */   
/* 172 */   static Hashtable<String, Integer> contentTypes = new Hashtable<>();
/*     */   
/*     */   static {
/* 175 */     contentTypes.put("CDATA", Integer.valueOf(1));
/* 176 */     contentTypes.put("RCDATA", Integer.valueOf(16));
/* 177 */     contentTypes.put("EMPTY", Integer.valueOf(17));
/* 178 */     contentTypes.put("ANY", Integer.valueOf(19));
/*     */   }
/*     */   
/*     */   public static int name2type(String paramString) {
/* 182 */     Integer integer = contentTypes.get(paramString);
/* 183 */     return (integer != null) ? integer.intValue() : 0;
/*     */   }
/*     */   
/*     */   Element() {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/parser/Element.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */