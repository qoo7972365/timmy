/*     */ package com.sun.imageio.plugins.wbmp;
/*     */ 
/*     */ import com.sun.imageio.plugins.common.I18N;
/*     */ import com.sun.imageio.plugins.common.ImageUtil;
/*     */ import javax.imageio.metadata.IIOMetadata;
/*     */ import javax.imageio.metadata.IIOMetadataNode;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WBMPMetadata
/*     */   extends IIOMetadata
/*     */ {
/*     */   static final String nativeMetadataFormatName = "javax_imageio_wbmp_1.0";
/*     */   public int wbmpType;
/*     */   public int width;
/*     */   public int height;
/*     */   
/*     */   public WBMPMetadata() {
/*  53 */     super(true, "javax_imageio_wbmp_1.0", "com.sun.imageio.plugins.wbmp.WBMPMetadataFormat", null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReadOnly() {
/*  60 */     return true;
/*     */   }
/*     */   
/*     */   public Node getAsTree(String paramString) {
/*  64 */     if (paramString.equals("javax_imageio_wbmp_1.0"))
/*  65 */       return getNativeTree(); 
/*  66 */     if (paramString
/*  67 */       .equals("javax_imageio_1.0")) {
/*  68 */       return getStandardTree();
/*     */     }
/*  70 */     throw new IllegalArgumentException(I18N.getString("WBMPMetadata0"));
/*     */   }
/*     */ 
/*     */   
/*     */   private Node getNativeTree() {
/*  75 */     IIOMetadataNode iIOMetadataNode = new IIOMetadataNode("javax_imageio_wbmp_1.0");
/*     */ 
/*     */     
/*  78 */     addChildNode(iIOMetadataNode, "WBMPType", new Integer(this.wbmpType));
/*  79 */     addChildNode(iIOMetadataNode, "Width", new Integer(this.width));
/*  80 */     addChildNode(iIOMetadataNode, "Height", new Integer(this.height));
/*     */     
/*  82 */     return iIOMetadataNode;
/*     */   }
/*     */   
/*     */   public void setFromTree(String paramString, Node paramNode) {
/*  86 */     throw new IllegalStateException(I18N.getString("WBMPMetadata1"));
/*     */   }
/*     */   
/*     */   public void mergeTree(String paramString, Node paramNode) {
/*  90 */     throw new IllegalStateException(I18N.getString("WBMPMetadata1"));
/*     */   }
/*     */   
/*     */   public void reset() {
/*  94 */     throw new IllegalStateException(I18N.getString("WBMPMetadata1"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private IIOMetadataNode addChildNode(IIOMetadataNode paramIIOMetadataNode, String paramString, Object paramObject) {
/* 100 */     IIOMetadataNode iIOMetadataNode = new IIOMetadataNode(paramString);
/* 101 */     if (paramObject != null) {
/* 102 */       iIOMetadataNode.setUserObject(paramObject);
/* 103 */       iIOMetadataNode.setNodeValue(ImageUtil.convertObjectToString(paramObject));
/*     */     } 
/* 105 */     paramIIOMetadataNode.appendChild(iIOMetadataNode);
/* 106 */     return iIOMetadataNode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected IIOMetadataNode getStandardChromaNode() {
/* 112 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Chroma");
/* 113 */     IIOMetadataNode iIOMetadataNode2 = new IIOMetadataNode("BlackIsZero");
/* 114 */     iIOMetadataNode2.setAttribute("value", "TRUE");
/*     */     
/* 116 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/* 117 */     return iIOMetadataNode1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IIOMetadataNode getStandardDimensionNode() {
/* 122 */     IIOMetadataNode iIOMetadataNode1 = new IIOMetadataNode("Dimension");
/* 123 */     IIOMetadataNode iIOMetadataNode2 = null;
/*     */ 
/*     */ 
/*     */     
/* 127 */     iIOMetadataNode2 = new IIOMetadataNode("ImageOrientation");
/* 128 */     iIOMetadataNode2.setAttribute("value", "Normal");
/* 129 */     iIOMetadataNode1.appendChild(iIOMetadataNode2);
/*     */     
/* 131 */     return iIOMetadataNode1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/wbmp/WBMPMetadata.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */