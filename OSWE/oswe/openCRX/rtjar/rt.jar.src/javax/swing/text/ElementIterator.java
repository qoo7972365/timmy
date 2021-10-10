/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ import java.util.Stack;
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
/*     */ public class ElementIterator
/*     */   implements Cloneable
/*     */ {
/*     */   private Element root;
/*  75 */   private Stack<StackItem> elementStack = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class StackItem
/*     */     implements Cloneable
/*     */   {
/*     */     Element item;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int childIndex;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private StackItem(Element param1Element) {
/*  98 */       this.item = param1Element;
/*  99 */       this.childIndex = -1;
/*     */     }
/*     */     
/*     */     private void incrementIndex() {
/* 103 */       this.childIndex++;
/*     */     }
/*     */     
/*     */     private Element getElement() {
/* 107 */       return this.item;
/*     */     }
/*     */     
/*     */     private int getIndex() {
/* 111 */       return this.childIndex;
/*     */     }
/*     */     
/*     */     protected Object clone() throws CloneNotSupportedException {
/* 115 */       return super.clone();
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
/*     */   public ElementIterator(Document paramDocument) {
/* 127 */     this.root = paramDocument.getDefaultRootElement();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ElementIterator(Element paramElement) {
/* 137 */     this.root = paramElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Object clone() {
/*     */     try {
/* 149 */       ElementIterator elementIterator = new ElementIterator(this.root);
/* 150 */       if (this.elementStack != null) {
/* 151 */         elementIterator.elementStack = new Stack<>();
/* 152 */         for (byte b = 0; b < this.elementStack.size(); b++) {
/* 153 */           StackItem stackItem1 = this.elementStack.elementAt(b);
/* 154 */           StackItem stackItem2 = (StackItem)stackItem1.clone();
/* 155 */           elementIterator.elementStack.push(stackItem2);
/*     */         } 
/*     */       } 
/* 158 */       return elementIterator;
/* 159 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 160 */       throw new InternalError(cloneNotSupportedException);
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
/*     */   public Element first() {
/* 172 */     if (this.root == null) {
/* 173 */       return null;
/*     */     }
/*     */     
/* 176 */     this.elementStack = new Stack<>();
/* 177 */     if (this.root.getElementCount() != 0) {
/* 178 */       this.elementStack.push(new StackItem(this.root));
/*     */     }
/* 180 */     return this.root;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int depth() {
/* 189 */     if (this.elementStack == null) {
/* 190 */       return 0;
/*     */     }
/* 192 */     return this.elementStack.size();
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
/*     */   public Element current() {
/* 204 */     if (this.elementStack == null) {
/* 205 */       return first();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 211 */     if (!this.elementStack.empty()) {
/* 212 */       StackItem stackItem = this.elementStack.peek();
/* 213 */       Element element = stackItem.getElement();
/* 214 */       int i = stackItem.getIndex();
/*     */       
/* 216 */       if (i == -1) {
/* 217 */         return element;
/*     */       }
/*     */       
/* 220 */       return element.getElement(i);
/*     */     } 
/* 222 */     return null;
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
/*     */ 
/*     */   
/*     */   public Element next() {
/* 239 */     if (this.elementStack == null) {
/* 240 */       return first();
/*     */     }
/*     */ 
/*     */     
/* 244 */     if (this.elementStack.isEmpty()) {
/* 245 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 250 */     StackItem stackItem = this.elementStack.peek();
/* 251 */     Element element = stackItem.getElement();
/* 252 */     int i = stackItem.getIndex();
/*     */     
/* 254 */     if (i + 1 < element.getElementCount()) {
/* 255 */       Element element1 = element.getElement(i + 1);
/* 256 */       if (element1.isLeaf()) {
/*     */ 
/*     */ 
/*     */         
/* 260 */         stackItem.incrementIndex();
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 265 */         this.elementStack.push(new StackItem(element1));
/*     */       } 
/* 267 */       return element1;
/*     */     } 
/*     */ 
/*     */     
/* 271 */     this.elementStack.pop();
/* 272 */     if (!this.elementStack.isEmpty()) {
/*     */ 
/*     */       
/* 275 */       StackItem stackItem1 = this.elementStack.peek();
/* 276 */       stackItem1.incrementIndex();
/*     */ 
/*     */       
/* 279 */       return next();
/*     */     } 
/*     */     
/* 282 */     return null;
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
/*     */   public Element previous() {
/*     */     int i;
/* 297 */     if (this.elementStack == null || (i = this.elementStack.size()) == 0) {
/* 298 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 303 */     StackItem stackItem = this.elementStack.peek();
/* 304 */     Element element = stackItem.getElement();
/* 305 */     int j = stackItem.getIndex();
/*     */     
/* 307 */     if (j > 0)
/*     */     {
/* 309 */       return getDeepestLeaf(element.getElement(--j)); } 
/* 310 */     if (j == 0)
/*     */     {
/*     */ 
/*     */       
/* 314 */       return element; } 
/* 315 */     if (j == -1) {
/* 316 */       if (i == 1)
/*     */       {
/* 318 */         return null;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 323 */       StackItem stackItem1 = this.elementStack.pop();
/* 324 */       stackItem = this.elementStack.peek();
/*     */ 
/*     */       
/* 327 */       this.elementStack.push(stackItem1);
/* 328 */       element = stackItem.getElement();
/* 329 */       j = stackItem.getIndex();
/* 330 */       return (j == -1) ? element : getDeepestLeaf(element
/* 331 */           .getElement(j));
/*     */     } 
/*     */     
/* 334 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Element getDeepestLeaf(Element paramElement) {
/* 342 */     if (paramElement.isLeaf()) {
/* 343 */       return paramElement;
/*     */     }
/* 345 */     int i = paramElement.getElementCount();
/* 346 */     if (i == 0) {
/* 347 */       return paramElement;
/*     */     }
/* 349 */     return getDeepestLeaf(paramElement.getElement(i - 1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void dumpTree() {
/*     */     Element element;
/* 360 */     while ((element = next()) != null) {
/* 361 */       System.out.println("elem: " + element.getName());
/* 362 */       AttributeSet attributeSet = element.getAttributes();
/* 363 */       String str = "";
/* 364 */       Enumeration<?> enumeration = attributeSet.getAttributeNames();
/* 365 */       while (enumeration.hasMoreElements()) {
/* 366 */         Object object1 = enumeration.nextElement();
/* 367 */         Object object2 = attributeSet.getAttribute(object1);
/* 368 */         if (object2 instanceof AttributeSet) {
/*     */           
/* 370 */           str = str + object1 + "=**AttributeSet** "; continue;
/*     */         } 
/* 372 */         str = str + object1 + "=" + object2 + " ";
/*     */       } 
/*     */       
/* 375 */       System.out.println("attributes: " + str);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/ElementIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */