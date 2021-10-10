/*     */ package javax.swing.tree;
/*     */ 
/*     */ import java.beans.ConstructorProperties;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TreePath
/*     */   implements Serializable
/*     */ {
/*     */   private TreePath parentPath;
/*     */   private Object lastPathComponent;
/*     */   
/*     */   @ConstructorProperties({"path"})
/*     */   public TreePath(Object[] paramArrayOfObject) {
/* 101 */     if (paramArrayOfObject == null || paramArrayOfObject.length == 0)
/* 102 */       throw new IllegalArgumentException("path in TreePath must be non null and not empty."); 
/* 103 */     this.lastPathComponent = paramArrayOfObject[paramArrayOfObject.length - 1];
/* 104 */     if (this.lastPathComponent == null) {
/* 105 */       throw new IllegalArgumentException("Last path component must be non-null");
/*     */     }
/*     */     
/* 108 */     if (paramArrayOfObject.length > 1) {
/* 109 */       this.parentPath = new TreePath(paramArrayOfObject, paramArrayOfObject.length - 1);
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
/*     */   
/*     */   public TreePath(Object paramObject) {
/* 122 */     if (paramObject == null)
/* 123 */       throw new IllegalArgumentException("path in TreePath must be non null."); 
/* 124 */     this.lastPathComponent = paramObject;
/* 125 */     this.parentPath = null;
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
/*     */   protected TreePath(TreePath paramTreePath, Object paramObject) {
/* 138 */     if (paramObject == null)
/* 139 */       throw new IllegalArgumentException("path in TreePath must be non null."); 
/* 140 */     this.parentPath = paramTreePath;
/* 141 */     this.lastPathComponent = paramObject;
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
/*     */   protected TreePath(Object[] paramArrayOfObject, int paramInt) {
/* 162 */     this.lastPathComponent = paramArrayOfObject[paramInt - 1];
/* 163 */     if (this.lastPathComponent == null) {
/* 164 */       throw new IllegalArgumentException("Path elements must be non-null");
/*     */     }
/*     */     
/* 167 */     if (paramInt > 1) {
/* 168 */       this.parentPath = new TreePath(paramArrayOfObject, paramInt - 1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TreePath() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] getPath() {
/* 187 */     int i = getPathCount();
/* 188 */     Object[] arrayOfObject = new Object[i--];
/*     */     
/* 190 */     for (TreePath treePath = this; treePath != null; treePath = treePath.getParentPath()) {
/* 191 */       arrayOfObject[i--] = treePath.getLastPathComponent();
/*     */     }
/* 193 */     return arrayOfObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getLastPathComponent() {
/* 202 */     return this.lastPathComponent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPathCount() {
/* 211 */     byte b = 0;
/* 212 */     for (TreePath treePath = this; treePath != null; treePath = treePath.getParentPath()) {
/* 213 */       b++;
/*     */     }
/* 215 */     return b;
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
/*     */   public Object getPathComponent(int paramInt) {
/* 227 */     int i = getPathCount();
/*     */     
/* 229 */     if (paramInt < 0 || paramInt >= i) {
/* 230 */       throw new IllegalArgumentException("Index " + paramInt + " is out of the specified range");
/*     */     }
/*     */     
/* 233 */     TreePath treePath = this;
/*     */     
/* 235 */     for (int j = i - 1; j != paramInt; j--) {
/* 236 */       treePath = treePath.getParentPath();
/*     */     }
/* 238 */     return treePath.getLastPathComponent();
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
/*     */   public boolean equals(Object paramObject) {
/* 250 */     if (paramObject == this)
/* 251 */       return true; 
/* 252 */     if (paramObject instanceof TreePath) {
/* 253 */       TreePath treePath1 = (TreePath)paramObject;
/*     */       
/* 255 */       if (getPathCount() != treePath1.getPathCount())
/* 256 */         return false; 
/* 257 */       for (TreePath treePath2 = this; treePath2 != null; 
/* 258 */         treePath2 = treePath2.getParentPath()) {
/*     */         
/* 260 */         if (!treePath2.getLastPathComponent().equals(treePath1.getLastPathComponent())) {
/* 261 */           return false;
/*     */         }
/* 263 */         treePath1 = treePath1.getParentPath();
/*     */       } 
/* 265 */       return true;
/*     */     } 
/* 267 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 277 */     return getLastPathComponent().hashCode();
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
/*     */   public boolean isDescendant(TreePath paramTreePath) {
/* 300 */     if (paramTreePath == this) {
/* 301 */       return true;
/*     */     }
/* 303 */     if (paramTreePath != null) {
/* 304 */       int i = getPathCount();
/* 305 */       int j = paramTreePath.getPathCount();
/*     */       
/* 307 */       if (j < i)
/*     */       {
/* 309 */         return false; } 
/* 310 */       while (j-- > i)
/* 311 */         paramTreePath = paramTreePath.getParentPath(); 
/* 312 */       return equals(paramTreePath);
/*     */     } 
/* 314 */     return false;
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
/*     */   public TreePath pathByAddingChild(Object paramObject) {
/* 326 */     if (paramObject == null) {
/* 327 */       throw new NullPointerException("Null child not allowed");
/*     */     }
/* 329 */     return new TreePath(this, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TreePath getParentPath() {
/* 339 */     return this.parentPath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 349 */     StringBuffer stringBuffer = new StringBuffer("["); byte b;
/*     */     int i;
/* 351 */     for (b = 0, i = getPathCount(); b < i; 
/* 352 */       b++) {
/* 353 */       if (b > 0)
/* 354 */         stringBuffer.append(", "); 
/* 355 */       stringBuffer.append(getPathComponent(b));
/*     */     } 
/* 357 */     stringBuffer.append("]");
/* 358 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/tree/TreePath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */