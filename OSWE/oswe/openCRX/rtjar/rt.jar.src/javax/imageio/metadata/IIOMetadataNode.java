/*      */ package javax.imageio.metadata;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import org.w3c.dom.Attr;
/*      */ import org.w3c.dom.DOMException;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.NamedNodeMap;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ import org.w3c.dom.TypeInfo;
/*      */ import org.w3c.dom.UserDataHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class IIOMetadataNode
/*      */   implements Element, NodeList
/*      */ {
/*  235 */   private String nodeName = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  241 */   private String nodeValue = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  246 */   private Object userObject = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  252 */   private IIOMetadataNode parent = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  257 */   private int numChildren = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  263 */   private IIOMetadataNode firstChild = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  269 */   private IIOMetadataNode lastChild = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  275 */   private IIOMetadataNode nextSibling = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  281 */   private IIOMetadataNode previousSibling = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  287 */   private List attributes = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IIOMetadataNode() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IIOMetadataNode(String paramString) {
/*  301 */     this.nodeName = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkNode(Node paramNode) throws DOMException {
/*  309 */     if (paramNode == null) {
/*      */       return;
/*      */     }
/*  312 */     if (!(paramNode instanceof IIOMetadataNode)) {
/*  313 */       throw new IIODOMException((short)4, "Node not an IIOMetadataNode!");
/*      */     }
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
/*      */   public String getNodeName() {
/*  326 */     return this.nodeName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNodeValue() {
/*  335 */     return this.nodeValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNodeValue(String paramString) {
/*  342 */     this.nodeValue = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short getNodeType() {
/*  352 */     return 1;
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
/*      */ 
/*      */ 
/*      */   
/*      */   public Node getParentNode() {
/*  369 */     return this.parent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NodeList getChildNodes() {
/*  380 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Node getFirstChild() {
/*  391 */     return this.firstChild;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Node getLastChild() {
/*  402 */     return this.lastChild;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Node getPreviousSibling() {
/*  413 */     return this.previousSibling;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Node getNextSibling() {
/*  424 */     return this.nextSibling;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NamedNodeMap getAttributes() {
/*  435 */     return new IIONamedNodeMap(this.attributes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Document getOwnerDocument() {
/*  445 */     return null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Node insertBefore(Node paramNode1, Node paramNode2) {
/*  464 */     if (paramNode1 == null) {
/*  465 */       throw new IllegalArgumentException("newChild == null!");
/*      */     }
/*      */     
/*  468 */     checkNode(paramNode1);
/*  469 */     checkNode(paramNode2);
/*      */     
/*  471 */     IIOMetadataNode iIOMetadataNode1 = (IIOMetadataNode)paramNode1;
/*  472 */     IIOMetadataNode iIOMetadataNode2 = (IIOMetadataNode)paramNode2;
/*      */ 
/*      */     
/*  475 */     IIOMetadataNode iIOMetadataNode3 = null;
/*  476 */     IIOMetadataNode iIOMetadataNode4 = null;
/*      */     
/*  478 */     if (paramNode2 == null) {
/*  479 */       iIOMetadataNode3 = this.lastChild;
/*  480 */       iIOMetadataNode4 = null;
/*  481 */       this.lastChild = iIOMetadataNode1;
/*      */     } else {
/*  483 */       iIOMetadataNode3 = iIOMetadataNode2.previousSibling;
/*  484 */       iIOMetadataNode4 = iIOMetadataNode2;
/*      */     } 
/*      */     
/*  487 */     if (iIOMetadataNode3 != null) {
/*  488 */       iIOMetadataNode3.nextSibling = iIOMetadataNode1;
/*      */     }
/*  490 */     if (iIOMetadataNode4 != null) {
/*  491 */       iIOMetadataNode4.previousSibling = iIOMetadataNode1;
/*      */     }
/*      */     
/*  494 */     iIOMetadataNode1.parent = this;
/*  495 */     iIOMetadataNode1.previousSibling = iIOMetadataNode3;
/*  496 */     iIOMetadataNode1.nextSibling = iIOMetadataNode4;
/*      */ 
/*      */     
/*  499 */     if (this.firstChild == iIOMetadataNode2) {
/*  500 */       this.firstChild = iIOMetadataNode1;
/*      */     }
/*      */     
/*  503 */     this.numChildren++;
/*  504 */     return iIOMetadataNode1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Node replaceChild(Node paramNode1, Node paramNode2) {
/*  522 */     if (paramNode1 == null) {
/*  523 */       throw new IllegalArgumentException("newChild == null!");
/*      */     }
/*      */     
/*  526 */     checkNode(paramNode1);
/*  527 */     checkNode(paramNode2);
/*      */     
/*  529 */     IIOMetadataNode iIOMetadataNode1 = (IIOMetadataNode)paramNode1;
/*  530 */     IIOMetadataNode iIOMetadataNode2 = (IIOMetadataNode)paramNode2;
/*      */     
/*  532 */     IIOMetadataNode iIOMetadataNode3 = iIOMetadataNode2.previousSibling;
/*  533 */     IIOMetadataNode iIOMetadataNode4 = iIOMetadataNode2.nextSibling;
/*      */     
/*  535 */     if (iIOMetadataNode3 != null) {
/*  536 */       iIOMetadataNode3.nextSibling = iIOMetadataNode1;
/*      */     }
/*  538 */     if (iIOMetadataNode4 != null) {
/*  539 */       iIOMetadataNode4.previousSibling = iIOMetadataNode1;
/*      */     }
/*      */     
/*  542 */     iIOMetadataNode1.parent = this;
/*  543 */     iIOMetadataNode1.previousSibling = iIOMetadataNode3;
/*  544 */     iIOMetadataNode1.nextSibling = iIOMetadataNode4;
/*      */     
/*  546 */     if (this.firstChild == iIOMetadataNode2) {
/*  547 */       this.firstChild = iIOMetadataNode1;
/*      */     }
/*  549 */     if (this.lastChild == iIOMetadataNode2) {
/*  550 */       this.lastChild = iIOMetadataNode1;
/*      */     }
/*      */     
/*  553 */     iIOMetadataNode2.parent = null;
/*  554 */     iIOMetadataNode2.previousSibling = null;
/*  555 */     iIOMetadataNode2.nextSibling = null;
/*      */     
/*  557 */     return iIOMetadataNode2;
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
/*      */   
/*      */   public Node removeChild(Node paramNode) {
/*  572 */     if (paramNode == null) {
/*  573 */       throw new IllegalArgumentException("oldChild == null!");
/*      */     }
/*  575 */     checkNode(paramNode);
/*      */     
/*  577 */     IIOMetadataNode iIOMetadataNode1 = (IIOMetadataNode)paramNode;
/*      */     
/*  579 */     IIOMetadataNode iIOMetadataNode2 = iIOMetadataNode1.previousSibling;
/*  580 */     IIOMetadataNode iIOMetadataNode3 = iIOMetadataNode1.nextSibling;
/*      */     
/*  582 */     if (iIOMetadataNode2 != null) {
/*  583 */       iIOMetadataNode2.nextSibling = iIOMetadataNode3;
/*      */     }
/*  585 */     if (iIOMetadataNode3 != null) {
/*  586 */       iIOMetadataNode3.previousSibling = iIOMetadataNode2;
/*      */     }
/*      */     
/*  589 */     if (this.firstChild == iIOMetadataNode1) {
/*  590 */       this.firstChild = iIOMetadataNode3;
/*      */     }
/*  592 */     if (this.lastChild == iIOMetadataNode1) {
/*  593 */       this.lastChild = iIOMetadataNode2;
/*      */     }
/*      */     
/*  596 */     iIOMetadataNode1.parent = null;
/*  597 */     iIOMetadataNode1.previousSibling = null;
/*  598 */     iIOMetadataNode1.nextSibling = null;
/*      */     
/*  600 */     this.numChildren--;
/*  601 */     return iIOMetadataNode1;
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
/*      */   
/*      */   public Node appendChild(Node paramNode) {
/*  616 */     if (paramNode == null) {
/*  617 */       throw new IllegalArgumentException("newChild == null!");
/*      */     }
/*  619 */     checkNode(paramNode);
/*      */ 
/*      */     
/*  622 */     return insertBefore(paramNode, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasChildNodes() {
/*  631 */     return (this.numChildren > 0);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Node cloneNode(boolean paramBoolean) {
/*  649 */     IIOMetadataNode iIOMetadataNode = new IIOMetadataNode(this.nodeName);
/*  650 */     iIOMetadataNode.setUserObject(getUserObject());
/*      */ 
/*      */     
/*  653 */     if (paramBoolean) {
/*  654 */       IIOMetadataNode iIOMetadataNode1 = this.firstChild;
/*  655 */       for (; iIOMetadataNode1 != null; 
/*  656 */         iIOMetadataNode1 = iIOMetadataNode1.nextSibling) {
/*  657 */         iIOMetadataNode.appendChild(iIOMetadataNode1.cloneNode(true));
/*      */       }
/*      */     } 
/*      */     
/*  661 */     return iIOMetadataNode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void normalize() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSupported(String paramString1, String paramString2) {
/*  681 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNamespaceURI() throws DOMException {
/*  688 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPrefix() {
/*  699 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPrefix(String paramString) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLocalName() {
/*  718 */     return this.nodeName;
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
/*      */   public String getTagName() {
/*  730 */     return this.nodeName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAttribute(String paramString) {
/*  740 */     Attr attr = getAttributeNode(paramString);
/*  741 */     if (attr == null) {
/*  742 */       return "";
/*      */     }
/*  744 */     return attr.getValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getAttributeNS(String paramString1, String paramString2) {
/*  753 */     return getAttribute(paramString2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAttribute(String paramString1, String paramString2) {
/*  758 */     boolean bool = true;
/*  759 */     char[] arrayOfChar = paramString1.toCharArray();
/*  760 */     for (byte b = 0; b < arrayOfChar.length; b++) {
/*  761 */       if (arrayOfChar[b] >= '￾') {
/*  762 */         bool = false;
/*      */         break;
/*      */       } 
/*      */     } 
/*  766 */     if (!bool) {
/*  767 */       throw new IIODOMException((short)5, "Attribute name is illegal!");
/*      */     }
/*      */     
/*  770 */     removeAttribute(paramString1, false);
/*  771 */     this.attributes.add(new IIOAttr(this, paramString1, paramString2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAttributeNS(String paramString1, String paramString2, String paramString3) {
/*  781 */     setAttribute(paramString2, paramString3);
/*      */   }
/*      */   
/*      */   public void removeAttribute(String paramString) {
/*  785 */     removeAttribute(paramString, true);
/*      */   }
/*      */   
/*      */   private void removeAttribute(String paramString, boolean paramBoolean) {
/*  789 */     int i = this.attributes.size();
/*  790 */     for (byte b = 0; b < i; b++) {
/*  791 */       IIOAttr iIOAttr = this.attributes.get(b);
/*  792 */       if (paramString.equals(iIOAttr.getName())) {
/*  793 */         iIOAttr.setOwnerElement(null);
/*  794 */         this.attributes.remove(b);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/*  800 */     if (paramBoolean) {
/*  801 */       throw new IIODOMException((short)8, "No such attribute!");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAttributeNS(String paramString1, String paramString2) {
/*  811 */     removeAttribute(paramString2);
/*      */   }
/*      */   
/*      */   public Attr getAttributeNode(String paramString) {
/*  815 */     Node node = getAttributes().getNamedItem(paramString);
/*  816 */     return (Attr)node;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Attr getAttributeNodeNS(String paramString1, String paramString2) {
/*  826 */     return getAttributeNode(paramString2);
/*      */   }
/*      */   public Attr setAttributeNode(Attr paramAttr) throws DOMException {
/*      */     IIOAttr iIOAttr;
/*  830 */     Element element = paramAttr.getOwnerElement();
/*  831 */     if (element != null) {
/*  832 */       if (element == this) {
/*  833 */         return null;
/*      */       }
/*  835 */       throw new DOMException((short)10, "Attribute is already in use");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  841 */     if (paramAttr instanceof IIOAttr) {
/*  842 */       iIOAttr = (IIOAttr)paramAttr;
/*  843 */       iIOAttr.setOwnerElement(this);
/*      */     }
/*      */     else {
/*      */       
/*  847 */       iIOAttr = new IIOAttr(this, paramAttr.getName(), paramAttr.getValue());
/*      */     } 
/*      */     
/*  850 */     Attr attr = getAttributeNode(iIOAttr.getName());
/*  851 */     if (attr != null) {
/*  852 */       removeAttributeNode(attr);
/*      */     }
/*      */     
/*  855 */     this.attributes.add(iIOAttr);
/*      */     
/*  857 */     return attr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Attr setAttributeNodeNS(Attr paramAttr) {
/*  866 */     return setAttributeNode(paramAttr);
/*      */   }
/*      */   
/*      */   public Attr removeAttributeNode(Attr paramAttr) {
/*  870 */     removeAttribute(paramAttr.getName());
/*  871 */     return paramAttr;
/*      */   }
/*      */   
/*      */   public NodeList getElementsByTagName(String paramString) {
/*  875 */     ArrayList arrayList = new ArrayList();
/*  876 */     getElementsByTagName(paramString, arrayList);
/*  877 */     return new IIONodeList(arrayList);
/*      */   }
/*      */   
/*      */   private void getElementsByTagName(String paramString, List<IIOMetadataNode> paramList) {
/*  881 */     if (this.nodeName.equals(paramString)) {
/*  882 */       paramList.add(this);
/*      */     }
/*      */     
/*  885 */     Node node = getFirstChild();
/*  886 */     while (node != null) {
/*  887 */       ((IIOMetadataNode)node).getElementsByTagName(paramString, paramList);
/*  888 */       node = node.getNextSibling();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NodeList getElementsByTagNameNS(String paramString1, String paramString2) {
/*  897 */     return getElementsByTagName(paramString2);
/*      */   }
/*      */   
/*      */   public boolean hasAttributes() {
/*  901 */     return (this.attributes.size() > 0);
/*      */   }
/*      */   
/*      */   public boolean hasAttribute(String paramString) {
/*  905 */     return (getAttributeNode(paramString) != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasAttributeNS(String paramString1, String paramString2) {
/*  913 */     return hasAttribute(paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLength() {
/*  919 */     return this.numChildren;
/*      */   }
/*      */   
/*      */   public Node item(int paramInt) {
/*  923 */     if (paramInt < 0) {
/*  924 */       return null;
/*      */     }
/*      */     
/*  927 */     Node node = getFirstChild();
/*  928 */     while (node != null && paramInt-- > 0) {
/*  929 */       node = node.getNextSibling();
/*      */     }
/*  931 */     return node;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getUserObject() {
/*  942 */     return this.userObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUserObject(Object paramObject) {
/*  953 */     this.userObject = paramObject;
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
/*      */   public void setIdAttribute(String paramString, boolean paramBoolean) throws DOMException {
/*  966 */     throw new DOMException((short)9, "Method not supported");
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
/*      */   public void setIdAttributeNS(String paramString1, String paramString2, boolean paramBoolean) throws DOMException {
/*  979 */     throw new DOMException((short)9, "Method not supported");
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
/*      */   public void setIdAttributeNode(Attr paramAttr, boolean paramBoolean) throws DOMException {
/*  991 */     throw new DOMException((short)9, "Method not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeInfo getSchemaTypeInfo() throws DOMException {
/* 1001 */     throw new DOMException((short)9, "Method not supported");
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
/*      */   public Object setUserData(String paramString, Object paramObject, UserDataHandler paramUserDataHandler) throws DOMException {
/* 1013 */     throw new DOMException((short)9, "Method not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getUserData(String paramString) throws DOMException {
/* 1023 */     throw new DOMException((short)9, "Method not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getFeature(String paramString1, String paramString2) throws DOMException {
/* 1034 */     throw new DOMException((short)9, "Method not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSameNode(Node paramNode) throws DOMException {
/* 1044 */     throw new DOMException((short)9, "Method not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEqualNode(Node paramNode) throws DOMException {
/* 1054 */     throw new DOMException((short)9, "Method not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String lookupNamespaceURI(String paramString) throws DOMException {
/* 1064 */     throw new DOMException((short)9, "Method not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDefaultNamespace(String paramString) throws DOMException {
/* 1075 */     throw new DOMException((short)9, "Method not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String lookupPrefix(String paramString) throws DOMException {
/* 1085 */     throw new DOMException((short)9, "Method not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTextContent() throws DOMException {
/* 1095 */     throw new DOMException((short)9, "Method not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTextContent(String paramString) throws DOMException {
/* 1105 */     throw new DOMException((short)9, "Method not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short compareDocumentPosition(Node paramNode) throws DOMException {
/* 1116 */     throw new DOMException((short)9, "Method not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getBaseURI() throws DOMException {
/* 1126 */     throw new DOMException((short)9, "Method not supported");
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/metadata/IIOMetadataNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */