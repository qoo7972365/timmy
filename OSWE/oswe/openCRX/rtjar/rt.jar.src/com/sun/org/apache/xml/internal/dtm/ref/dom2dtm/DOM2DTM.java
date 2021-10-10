/*      */ package com.sun.org.apache.xml.internal.dtm.ref.dom2dtm;
/*      */ 
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMManager;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMWSFilter;
/*      */ import com.sun.org.apache.xml.internal.dtm.ref.DTMDefaultBaseIterators;
/*      */ import com.sun.org.apache.xml.internal.dtm.ref.DTMManagerDefault;
/*      */ import com.sun.org.apache.xml.internal.dtm.ref.ExpandedNameTable;
/*      */ import com.sun.org.apache.xml.internal.dtm.ref.IncrementalSAXSource;
/*      */ import com.sun.org.apache.xml.internal.res.XMLMessages;
/*      */ import com.sun.org.apache.xml.internal.utils.FastStringBuffer;
/*      */ import com.sun.org.apache.xml.internal.utils.QName;
/*      */ import com.sun.org.apache.xml.internal.utils.StringBufferPool;
/*      */ import com.sun.org.apache.xml.internal.utils.TreeWalker;
/*      */ import com.sun.org.apache.xml.internal.utils.XMLCharacterRecognizer;
/*      */ import com.sun.org.apache.xml.internal.utils.XMLString;
/*      */ import com.sun.org.apache.xml.internal.utils.XMLStringFactory;
/*      */ import java.util.Vector;
/*      */ import javax.xml.transform.SourceLocator;
/*      */ import javax.xml.transform.dom.DOMSource;
/*      */ import org.w3c.dom.Attr;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.DocumentType;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Entity;
/*      */ import org.w3c.dom.NamedNodeMap;
/*      */ import org.w3c.dom.Node;
/*      */ import org.xml.sax.ContentHandler;
/*      */ import org.xml.sax.DTDHandler;
/*      */ import org.xml.sax.EntityResolver;
/*      */ import org.xml.sax.ErrorHandler;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.ext.DeclHandler;
/*      */ import org.xml.sax.ext.LexicalHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DOM2DTM
/*      */   extends DTMDefaultBaseIterators
/*      */ {
/*      */   static final boolean JJK_DEBUG = false;
/*      */   static final boolean JJK_NEWCODE = true;
/*      */   static final String NAMESPACE_DECL_NS = "http://www.w3.org/XML/1998/namespace";
/*      */   private transient Node m_pos;
/*   81 */   private int m_last_parent = 0;
/*      */ 
/*      */   
/*   84 */   private int m_last_kid = -1; private transient Node m_root; boolean m_processedFirstElement = false; private transient boolean m_nodesAreProcessed; TreeWalker m_walker; protected int addNode(Node node, int parentIndex, int previousSibling, int forceNodeType) { int type, nodeIndex = this.m_nodes.size(); if (this.m_dtmIdent.size() == nodeIndex >>> 16) try { if (this.m_mgr == null) throw new ClassCastException();  DTMManagerDefault mgrD = (DTMManagerDefault)this.m_mgr; int id = mgrD.getFirstFreeDTMID(); mgrD.addDTM(this, id, nodeIndex); this.m_dtmIdent.addElement(id << 16); } catch (ClassCastException e) { error(XMLMessages.createXMLMessage("ER_NO_DTMIDS_AVAIL", null)); }   this.m_size++; if (-1 == forceNodeType) { type = node.getNodeType(); } else { type = forceNodeType; }  if (2 == type) { String name = node.getNodeName(); if (name.startsWith("xmlns:") || name.equals("xmlns")) type = 13;  }  this.m_nodes.addElement(node); this.m_firstch.setElementAt(-2, nodeIndex); this.m_nextsib.setElementAt(-2, nodeIndex); this.m_prevsib.setElementAt(previousSibling, nodeIndex); this.m_parent.setElementAt(parentIndex, nodeIndex); if (-1 != parentIndex && type != 2 && type != 13) if (-2 == this.m_firstch.elementAt(parentIndex)) this.m_firstch.setElementAt(nodeIndex, parentIndex);   String nsURI = node.getNamespaceURI(); String localName = (type == 7) ? node.getNodeName() : node.getLocalName(); if ((type == 1 || type == 2) && null == localName)
/*      */       localName = node.getNodeName();  ExpandedNameTable exnt = this.m_expandedNameTable; if (node.getLocalName() != null || type == 1 || type == 2); int expandedNameID = (null != localName) ? exnt.getExpandedTypeID(nsURI, localName, type) : exnt.getExpandedTypeID(type); this.m_exptype.setElementAt(expandedNameID, nodeIndex); indexNode(expandedNameID, nodeIndex); if (-1 != previousSibling)
/*      */       this.m_nextsib.setElementAt(nodeIndex, previousSibling);  if (type == 13)
/*      */       declareNamespaceInContext(parentIndex, nodeIndex);  return nodeIndex; }
/*      */   public int getNumberOfNodes() { return this.m_nodes.size(); }
/*      */   protected boolean nextNode() { if (this.m_nodesAreProcessed)
/*      */       return false;  Node pos = this.m_pos; Node next = null; int nexttype = -1; do { if (pos.hasChildNodes()) { next = pos.getFirstChild(); if (next != null && 10 == next.getNodeType())
/*      */           next = next.getNextSibling();  if (5 != pos.getNodeType()) { this.m_last_parent = this.m_last_kid; this.m_last_kid = -1; if (null != this.m_wsfilter) { short wsv = this.m_wsfilter.getShouldStripSpace(makeNodeHandle(this.m_last_parent), this); boolean shouldStrip = (3 == wsv) ? getShouldStripWhitespace() : ((2 == wsv)); pushShouldStripWhitespace(shouldStrip); }  }  } else { if (this.m_last_kid != -1)
/*      */           if (this.m_firstch.elementAt(this.m_last_kid) == -2)
/*      */             this.m_firstch.setElementAt(-1, this.m_last_kid);   while (this.m_last_parent != -1) { next = pos.getNextSibling(); if (next != null && 10 == next.getNodeType())
/*      */             next = next.getNextSibling();  if (next != null)
/*      */             break;  pos = pos.getParentNode(); if (pos == null); if (pos != null && 5 == pos.getNodeType())
/*      */             continue;  popShouldStripWhitespace(); if (this.m_last_kid == -1) { this.m_firstch.setElementAt(-1, this.m_last_parent); } else { this.m_nextsib.setElementAt(-1, this.m_last_kid); }  this.m_last_parent = this.m_parent.elementAt(this.m_last_kid = this.m_last_parent); }  if (this.m_last_parent == -1)
/*      */           next = null;  }  if (next != null)
/*      */         nexttype = next.getNodeType();  if (5 != nexttype)
/*      */         continue;  pos = next; } while (5 == nexttype); if (next == null) { this.m_nextsib.setElementAt(-1, 0); this.m_nodesAreProcessed = true; this.m_pos = null; return false; }  boolean suppressNode = false; Node lastTextNode = null; nexttype = next.getNodeType(); if (3 == nexttype || 4 == nexttype) { suppressNode = (null != this.m_wsfilter && getShouldStripWhitespace()); Node n = next; while (n != null) { lastTextNode = n; if (3 == n.getNodeType())
/*      */           nexttype = 3;  suppressNode &= XMLCharacterRecognizer.isWhiteSpace(n.getNodeValue()); n = logicalNextDOMTextNode(n); }  } else if (7 == nexttype) { suppressNode = pos.getNodeName().toLowerCase().equals("xml"); }  if (!suppressNode) { int nextindex = addNode(next, this.m_last_parent, this.m_last_kid, nexttype); this.m_last_kid = nextindex; if (1 == nexttype) { int attrIndex = -1; NamedNodeMap attrs = next.getAttributes(); int attrsize = (attrs == null) ? 0 : attrs.getLength(); if (attrsize > 0)
/*      */           for (int i = 0; i < attrsize; i++) { attrIndex = addNode(attrs.item(i), nextindex, attrIndex, -1); this.m_firstch.setElementAt(-1, attrIndex); if (!this.m_processedFirstElement && "xmlns:xml".equals(attrs.item(i).getNodeName()))
/*      */               this.m_processedFirstElement = true;  }   if (!this.m_processedFirstElement) { attrIndex = addNode(new DOM2DTMdefaultNamespaceDeclarationNode((Element)next, "xml", "http://www.w3.org/XML/1998/namespace", makeNodeHandle(((attrIndex == -1) ? nextindex : attrIndex) + 1)), nextindex, attrIndex, -1); this.m_firstch.setElementAt(-1, attrIndex); this.m_processedFirstElement = true; }  if (attrIndex != -1)
/*      */           this.m_nextsib.setElementAt(-1, attrIndex);  }  }  if (3 == nexttype || 4 == nexttype)
/*      */       next = lastTextNode;  this.m_pos = next; return true; }
/*      */   public Node getNode(int nodeHandle) { int identity = makeNodeIdentity(nodeHandle); return this.m_nodes.elementAt(identity); }
/*  106 */   protected Vector m_nodes = new Vector();
/*      */   protected Node lookupNode(int nodeIdentity) { return this.m_nodes.elementAt(nodeIdentity); }
/*      */   protected int getNextNodeIdentity(int identity) { identity++; if (identity >= this.m_nodes.size())
/*      */       if (!nextNode())
/*      */         identity = -1;   return identity; }
/*      */   private int getHandleFromNode(Node node) { if (null != node) { boolean isMore; int len = this.m_nodes.size(); int i = 0; do { for (; i < len; i++) { if (this.m_nodes.elementAt(i) == node)
/*      */             return makeNodeHandle(i);  }  isMore = nextNode(); len = this.m_nodes.size(); } while (isMore || i < len); }  return -1; }
/*      */   public int getHandleOfNode(Node node) { if (null != node)
/*      */       if (this.m_root == node || (this.m_root.getNodeType() == 9 && this.m_root == node.getOwnerDocument()) || (this.m_root.getNodeType() != 9 && this.m_root.getOwnerDocument() == node.getOwnerDocument())) { Node cursor = node; for (; cursor != null; cursor = (cursor.getNodeType() != 2) ? cursor.getParentNode() : ((Attr)cursor).getOwnerElement()) { if (cursor == this.m_root)
/*      */             return getHandleFromNode(node);  }  }   return -1; }
/*      */   public int getAttributeNode(int nodeHandle, String namespaceURI, String name) { if (null == namespaceURI)
/*      */       namespaceURI = "";  int type = getNodeType(nodeHandle); if (1 == type) { int identity = makeNodeIdentity(nodeHandle); while (-1 != (identity = getNextNodeIdentity(identity))) { type = _type(identity); if (type == 2 || type == 13) { Node node = lookupNode(identity); String nodeuri = node.getNamespaceURI(); if (null == nodeuri)
/*      */             nodeuri = "";  String nodelocalname = node.getLocalName(); if (nodeuri.equals(namespaceURI) && name.equals(nodelocalname))
/*      */             return makeNodeHandle(identity);  }  }  }  return -1; }
/*      */   public XMLString getStringValue(int nodeHandle) { int type = getNodeType(nodeHandle); Node node = getNode(nodeHandle); if (1 == type || 9 == type || 11 == type) { String s; FastStringBuffer buf = StringBufferPool.get(); try { getNodeData(node, buf); s = (buf.length() > 0) ? buf.toString() : ""; } finally { StringBufferPool.free(buf); }  return this.m_xstrf.newstr(s); }  if (3 == type || 4 == type) { FastStringBuffer buf = StringBufferPool.get(); while (node != null) { buf.append(node.getNodeValue()); node = logicalNextDOMTextNode(node); }  String s = (buf.length() > 0) ? buf.toString() : ""; StringBufferPool.free(buf); return this.m_xstrf.newstr(s); }  return this.m_xstrf.newstr(node.getNodeValue()); }
/*      */   public boolean isWhitespace(int nodeHandle) { int type = getNodeType(nodeHandle); Node node = getNode(nodeHandle); if (3 == type || 4 == type) { FastStringBuffer buf = StringBufferPool.get(); while (node != null) { buf.append(node.getNodeValue()); node = logicalNextDOMTextNode(node); }  boolean b = buf.isWhitespace(0, buf.length()); StringBufferPool.free(buf); return b; }  return false; }
/*      */   protected static void getNodeData(Node node, FastStringBuffer buf) { Node child; switch (node.getNodeType()) { case 1: case 9: case 11: for (child = node.getFirstChild(); null != child; child = child.getNextSibling())
/*      */           getNodeData(child, buf);  break;
/*      */       case 2: case 3: case 4: buf.append(node.getNodeValue()); break; }  }
/*  125 */   public String getNodeName(int nodeHandle) { Node node = getNode(nodeHandle); return node.getNodeName(); } public DOM2DTM(DTMManager mgr, DOMSource domSource, int dtmIdentity, DTMWSFilter whiteSpaceFilter, XMLStringFactory xstringfactory, boolean doIndexing) { super(mgr, domSource, dtmIdentity, whiteSpaceFilter, xstringfactory, doIndexing);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1703 */     this.m_walker = new TreeWalker(null); this.m_pos = this.m_root = domSource.getNode(); this.m_last_parent = this.m_last_kid = -1; this.m_last_kid = addNode(this.m_root, this.m_last_parent, this.m_last_kid, -1); if (1 == this.m_root.getNodeType()) { NamedNodeMap attrs = this.m_root.getAttributes(); int attrsize = (attrs == null) ? 0 : attrs.getLength(); if (attrsize > 0) { int attrIndex = -1; for (int i = 0; i < attrsize; i++) { attrIndex = addNode(attrs.item(i), 0, attrIndex, -1); this.m_firstch.setElementAt(-1, attrIndex); }  this.m_nextsib.setElementAt(-1, attrIndex); }  }  this.m_nodesAreProcessed = false; }
/*      */   public String getNodeNameX(int nodeHandle) { Node node; short type = getNodeType(nodeHandle); switch (type) { case 13: node = getNode(nodeHandle); name = node.getNodeName(); if (name.startsWith("xmlns:")) { name = QName.getLocalPart(name); } else if (name.equals("xmlns")) { name = ""; }  return name;
/*      */       case 1: case 2: case 5:
/*      */       case 7:
/*      */         node = getNode(nodeHandle); name = node.getNodeName(); return name; }  String name = ""; return name; }
/*      */   public String getLocalName(int nodeHandle) { int id = makeNodeIdentity(nodeHandle); if (-1 == id)
/*      */       return null;  Node newnode = this.m_nodes.elementAt(id); String newname = newnode.getLocalName(); if (null == newname) { String qname = newnode.getNodeName(); if ('#' == qname.charAt(0)) { newname = ""; } else { int index = qname.indexOf(':'); newname = (index < 0) ? qname : qname.substring(index + 1); }  }  return newname; }
/*      */   public String getPrefix(int nodeHandle) { Node node; String qname; int index; short type = getNodeType(nodeHandle); switch (type) { case 13:
/*      */         node = getNode(nodeHandle); qname = node.getNodeName(); index = qname.indexOf(':'); prefix = (index < 0) ? "" : qname.substring(index + 1); return prefix;
/*      */       case 1:
/*      */       case 2:
/*      */         node = getNode(nodeHandle); qname = node.getNodeName(); index = qname.indexOf(':'); prefix = (index < 0) ? "" : qname.substring(0, index); return prefix; }  String prefix = ""; return prefix; }
/*      */   public String getNamespaceURI(int nodeHandle) { int id = makeNodeIdentity(nodeHandle); if (id == -1)
/* 1716 */       return null;  Node node = this.m_nodes.elementAt(id); return node.getNamespaceURI(); } public void dispatchToEvents(int nodeHandle, ContentHandler ch) throws SAXException { TreeWalker treeWalker = this.m_walker;
/* 1717 */     ContentHandler prevCH = treeWalker.getContentHandler();
/*      */     
/* 1719 */     if (null != prevCH)
/*      */     {
/* 1721 */       treeWalker = new TreeWalker(null);
/*      */     }
/* 1723 */     treeWalker.setContentHandler(ch);
/*      */ 
/*      */ 
/*      */     
/* 1727 */     try { Node node = getNode(nodeHandle);
/* 1728 */       treeWalker.traverseFragment(node); }
/*      */     
/*      */     finally
/*      */     
/* 1732 */     { treeWalker.setContentHandler(null); }  } private Node logicalNextDOMTextNode(Node n) { Node p = n.getNextSibling(); if (p == null) { n = n.getParentNode(); for (; n != null && 5 == n.getNodeType(); n = n.getParentNode()) { p = n.getNextSibling(); if (p != null)
/*      */           break;  }  }  n = p; while (n != null && 5 == n.getNodeType()) { if (n.hasChildNodes()) { n = n.getFirstChild(); continue; }  n = n.getNextSibling(); }  if (n != null) { int ntype = n.getNodeType(); if (3 != ntype && 4 != ntype)
/*      */         n = null;  }  return n; } public String getNodeValue(int nodeHandle) { int type = _exptype(makeNodeIdentity(nodeHandle)); type = (-1 != type) ? getNodeType(nodeHandle) : -1; if (3 != type && 4 != type)
/*      */       return getNode(nodeHandle).getNodeValue();  Node node = getNode(nodeHandle); Node n = logicalNextDOMTextNode(node); if (n == null)
/*      */       return node.getNodeValue();  FastStringBuffer buf = StringBufferPool.get(); buf.append(node.getNodeValue()); while (n != null) { buf.append(n.getNodeValue()); n = logicalNextDOMTextNode(n); }  String s = (buf.length() > 0) ? buf.toString() : ""; StringBufferPool.free(buf); return s; }
/*      */   public String getDocumentTypeDeclarationSystemIdentifier() { Document doc; if (this.m_root.getNodeType() == 9) { doc = (Document)this.m_root; } else { doc = this.m_root.getOwnerDocument(); }  if (null != doc) { DocumentType dtd = doc.getDoctype(); if (null != dtd)
/*      */         return dtd.getSystemId();  }
/*      */      return null; }
/*      */   public String getDocumentTypeDeclarationPublicIdentifier() { Document doc; if (this.m_root.getNodeType() == 9) { doc = (Document)this.m_root; }
/*      */     else { doc = this.m_root.getOwnerDocument(); }
/*      */      if (null != doc) { DocumentType dtd = doc.getDoctype(); if (null != dtd)
/*      */         return dtd.getPublicId();  }
/*      */      return null; }
/*      */   public int getElementById(String elementId) { Document doc = (this.m_root.getNodeType() == 9) ? (Document)this.m_root : this.m_root.getOwnerDocument(); if (null != doc) { Node elem = doc.getElementById(elementId); if (null != elem) { int elemHandle = getHandleFromNode(elem); if (-1 == elemHandle) { int identity = this.m_nodes.size() - 1; while (-1 != (identity = getNextNodeIdentity(identity))) { Node node = getNode(identity); if (node == elem) { elemHandle = getHandleFromNode(elem); break; }
/*      */              }
/*      */            }
/*      */          return elemHandle; }
/*      */        }
/*      */      return -1; }
/*      */   public String getUnparsedEntityURI(String name) { String url = ""; Document doc = (this.m_root.getNodeType() == 9) ? (Document)this.m_root : this.m_root.getOwnerDocument(); if (null != doc) { DocumentType doctype = doc.getDoctype(); if (null != doctype) { NamedNodeMap entities = doctype.getEntities(); if (null == entities)
/*      */           return url;  Entity entity = (Entity)entities.getNamedItem(name); if (null == entity)
/*      */           return url;  String notationName = entity.getNotationName(); if (null != notationName) { url = entity.getSystemId(); if (null == url)
/*      */             url = entity.getPublicId();  }
/*      */          }
/*      */        }
/*      */      return url; }
/*      */   public boolean isAttributeSpecified(int attributeHandle) { int type = getNodeType(attributeHandle); if (2 == type) { Attr attr = (Attr)getNode(attributeHandle); return attr.getSpecified(); }
/*      */      return false; }
/*      */   public void setIncrementalSAXSource(IncrementalSAXSource source) {}
/*      */   public ContentHandler getContentHandler() { return null; }
/* 1762 */   public SourceLocator getSourceLocatorFor(int node) { return null; }
/*      */ 
/*      */   
/*      */   public LexicalHandler getLexicalHandler() {
/*      */     return null;
/*      */   }
/*      */   
/*      */   public EntityResolver getEntityResolver() {
/*      */     return null;
/*      */   }
/*      */   
/*      */   public DTDHandler getDTDHandler() {
/*      */     return null;
/*      */   }
/*      */   
/*      */   public ErrorHandler getErrorHandler() {
/*      */     return null;
/*      */   }
/*      */   
/*      */   public DeclHandler getDeclHandler() {
/*      */     return null;
/*      */   }
/*      */   
/*      */   public boolean needsTwoThreads() {
/*      */     return false;
/*      */   }
/*      */   
/*      */   private static boolean isSpace(char ch) {
/*      */     return XMLCharacterRecognizer.isWhiteSpace(ch);
/*      */   }
/*      */   
/*      */   public void dispatchCharactersEvents(int nodeHandle, ContentHandler ch, boolean normalize) throws SAXException {
/*      */     if (normalize) {
/*      */       XMLString str = getStringValue(nodeHandle);
/*      */       str = str.fixWhiteSpace(true, true, false);
/*      */       str.dispatchCharactersEvents(ch);
/*      */     } else {
/*      */       int type = getNodeType(nodeHandle);
/*      */       Node node = getNode(nodeHandle);
/*      */       dispatchNodeData(node, ch, 0);
/*      */       if (3 == type || 4 == type)
/*      */         while (null != (node = logicalNextDOMTextNode(node)))
/*      */           dispatchNodeData(node, ch, 0);  
/*      */     } 
/*      */   }
/*      */   
/*      */   protected static void dispatchNodeData(Node node, ContentHandler ch, int depth) throws SAXException {
/*      */     Node child;
/*      */     String str;
/*      */     switch (node.getNodeType()) {
/*      */       case 1:
/*      */       case 9:
/*      */       case 11:
/*      */         for (child = node.getFirstChild(); null != child; child = child.getNextSibling())
/*      */           dispatchNodeData(child, ch, depth + 1); 
/*      */         break;
/*      */       case 7:
/*      */       case 8:
/*      */         if (0 != depth)
/*      */           break; 
/*      */       case 2:
/*      */       case 3:
/*      */       case 4:
/*      */         str = node.getNodeValue();
/*      */         if (ch instanceof CharacterNodeHandler) {
/*      */           ((CharacterNodeHandler)ch).characters(node);
/*      */           break;
/*      */         } 
/*      */         ch.characters(str.toCharArray(), 0, str.length());
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setProperty(String property, Object value) {}
/*      */   
/*      */   public static interface CharacterNodeHandler {
/*      */     void characters(Node param1Node) throws SAXException;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/dtm/ref/dom2dtm/DOM2DTM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */