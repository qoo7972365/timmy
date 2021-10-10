/*     */ package javax.swing.event;
/*     */ 
/*     */ import java.util.EventObject;
/*     */ import javax.swing.tree.TreePath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TreeModelEvent
/*     */   extends EventObject
/*     */ {
/*     */   protected TreePath path;
/*     */   protected int[] childIndices;
/*     */   protected Object[] children;
/*     */   
/*     */   public TreeModelEvent(Object paramObject, Object[] paramArrayOfObject1, int[] paramArrayOfint, Object[] paramArrayOfObject2) {
/* 131 */     this(paramObject, (paramArrayOfObject1 == null) ? null : new TreePath(paramArrayOfObject1), paramArrayOfint, paramArrayOfObject2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TreeModelEvent(Object paramObject, TreePath paramTreePath, int[] paramArrayOfint, Object[] paramArrayOfObject) {
/* 156 */     super(paramObject);
/* 157 */     this.path = paramTreePath;
/* 158 */     this.childIndices = paramArrayOfint;
/* 159 */     this.children = paramArrayOfObject;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TreeModelEvent(Object paramObject, Object[] paramArrayOfObject) {
/* 186 */     this(paramObject, (paramArrayOfObject == null) ? null : new TreePath(paramArrayOfObject));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TreeModelEvent(Object paramObject, TreePath paramTreePath) {
/* 208 */     super(paramObject);
/* 209 */     this.path = paramTreePath;
/* 210 */     this.childIndices = new int[0];
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
/*     */   
/*     */   public TreePath getTreePath() {
/* 228 */     return this.path;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] getPath() {
/* 239 */     if (this.path != null)
/* 240 */       return this.path.getPath(); 
/* 241 */     return null;
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
/*     */   public Object[] getChildren() {
/* 256 */     if (this.children != null) {
/* 257 */       int i = this.children.length;
/* 258 */       Object[] arrayOfObject = new Object[i];
/*     */       
/* 260 */       System.arraycopy(this.children, 0, arrayOfObject, 0, i);
/* 261 */       return arrayOfObject;
/*     */     } 
/* 263 */     return null;
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
/*     */   public int[] getChildIndices() {
/* 277 */     if (this.childIndices != null) {
/* 278 */       int i = this.childIndices.length;
/* 279 */       int[] arrayOfInt = new int[i];
/*     */       
/* 281 */       System.arraycopy(this.childIndices, 0, arrayOfInt, 0, i);
/* 282 */       return arrayOfInt;
/*     */     } 
/* 284 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 294 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 296 */     stringBuffer.append(getClass().getName() + " " + 
/* 297 */         Integer.toString(hashCode()));
/* 298 */     if (this.path != null)
/* 299 */       stringBuffer.append(" path " + this.path); 
/* 300 */     if (this.childIndices != null) {
/* 301 */       stringBuffer.append(" indices [ ");
/* 302 */       for (byte b = 0; b < this.childIndices.length; b++)
/* 303 */         stringBuffer.append(Integer.toString(this.childIndices[b]) + " "); 
/* 304 */       stringBuffer.append("]");
/*     */     } 
/* 306 */     if (this.children != null) {
/* 307 */       stringBuffer.append(" children [ ");
/* 308 */       for (byte b = 0; b < this.children.length; b++)
/* 309 */         stringBuffer.append(this.children[b] + " "); 
/* 310 */       stringBuffer.append("]");
/*     */     } 
/* 312 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/event/TreeModelEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */