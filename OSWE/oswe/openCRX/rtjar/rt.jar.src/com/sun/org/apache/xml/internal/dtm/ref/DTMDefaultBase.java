/*      */ package com.sun.org.apache.xml.internal.dtm.ref;
/*      */ 
/*      */ import com.sun.org.apache.xml.internal.dtm.DTM;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMAxisTraverser;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMException;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMManager;
/*      */ import com.sun.org.apache.xml.internal.dtm.DTMWSFilter;
/*      */ import com.sun.org.apache.xml.internal.res.XMLMessages;
/*      */ import com.sun.org.apache.xml.internal.utils.BoolStack;
/*      */ import com.sun.org.apache.xml.internal.utils.SuballocatedIntVector;
/*      */ import com.sun.org.apache.xml.internal.utils.XMLString;
/*      */ import com.sun.org.apache.xml.internal.utils.XMLStringFactory;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.util.Vector;
/*      */ import javax.xml.transform.Source;
/*      */ import org.w3c.dom.Node;
/*      */ import org.xml.sax.ContentHandler;
/*      */ import org.xml.sax.SAXException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class DTMDefaultBase
/*      */   implements DTM
/*      */ {
/*      */   static final boolean JJK_DEBUG = false;
/*      */   public static final int ROOTNODE = 0;
/*      */   protected int m_size;
/*      */   protected SuballocatedIntVector m_exptype;
/*      */   protected SuballocatedIntVector m_firstch;
/*      */   protected SuballocatedIntVector m_nextsib;
/*      */   protected SuballocatedIntVector m_prevsib;
/*      */   protected SuballocatedIntVector m_parent;
/*      */   protected Vector m_namespaceDeclSets;
/*      */   protected SuballocatedIntVector m_namespaceDeclSetElements;
/*      */   protected int[][][] m_elemIndexes;
/*      */   public static final int DEFAULT_BLOCKSIZE = 512;
/*      */   public static final int DEFAULT_NUMBLOCKS = 32;
/*      */   public static final int DEFAULT_NUMBLOCKS_SMALL = 4;
/*      */   protected static final int NOTPROCESSED = -2;
/*      */   public DTMManager m_mgr;
/*      */   protected DTMManagerDefault m_mgrDefault;
/*      */   protected SuballocatedIntVector m_dtmIdent;
/*      */   protected String m_documentBaseURI;
/*      */   protected DTMWSFilter m_wsfilter;
/*      */   protected boolean m_shouldStripWS;
/*      */   protected BoolStack m_shouldStripWhitespaceStack;
/*      */   protected XMLStringFactory m_xstrf;
/*      */   protected ExpandedNameTable m_expandedNameTable;
/*      */   protected boolean m_indexing;
/*      */   protected DTMAxisTraverser[] m_traversers;
/*      */   private Vector m_namespaceLists;
/*      */   
/*      */   public DTMDefaultBase(DTMManager mgr, Source source, int dtmIdentity, DTMWSFilter whiteSpaceFilter, XMLStringFactory xstringfactory, boolean doIndexing) {
/*  173 */     this(mgr, source, dtmIdentity, whiteSpaceFilter, xstringfactory, doIndexing, 512, true, false);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void ensureSizeOfIndex(int namespaceID, int LocalNameID) {
/*      */     if (null == this.m_elemIndexes) {
/*      */       this.m_elemIndexes = new int[namespaceID + 20][][];
/*      */     } else if (this.m_elemIndexes.length <= namespaceID) {
/*      */       int[][][] indexes = this.m_elemIndexes;
/*      */       this.m_elemIndexes = new int[namespaceID + 20][][];
/*      */       System.arraycopy(indexes, 0, this.m_elemIndexes, 0, indexes.length);
/*      */     } 
/*      */     int[][] localNameIndex = this.m_elemIndexes[namespaceID];
/*      */     if (null == localNameIndex) {
/*      */       localNameIndex = new int[LocalNameID + 100][];
/*      */       this.m_elemIndexes[namespaceID] = localNameIndex;
/*      */     } else if (localNameIndex.length <= LocalNameID) {
/*      */       int[][] indexes = localNameIndex;
/*      */       localNameIndex = new int[LocalNameID + 100][];
/*      */       System.arraycopy(indexes, 0, localNameIndex, 0, indexes.length);
/*      */       this.m_elemIndexes[namespaceID] = localNameIndex;
/*      */     } 
/*      */     int[] elemHandles = localNameIndex[LocalNameID];
/*      */     if (null == elemHandles) {
/*      */       elemHandles = new int[128];
/*      */       localNameIndex[LocalNameID] = elemHandles;
/*      */       elemHandles[0] = 1;
/*      */     } else if (elemHandles.length <= elemHandles[0] + 1) {
/*      */       int[] indexes = elemHandles;
/*      */       elemHandles = new int[elemHandles[0] + 1024];
/*      */       System.arraycopy(indexes, 0, elemHandles, 0, indexes.length);
/*      */       localNameIndex[LocalNameID] = elemHandles;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void indexNode(int expandedTypeID, int identity) {
/*      */     ExpandedNameTable ent = this.m_expandedNameTable;
/*      */     short type = ent.getType(expandedTypeID);
/*      */     if (1 == type) {
/*      */       int namespaceID = ent.getNamespaceID(expandedTypeID);
/*      */       int localNameID = ent.getLocalNameID(expandedTypeID);
/*      */       ensureSizeOfIndex(namespaceID, localNameID);
/*      */       int[] index = this.m_elemIndexes[namespaceID][localNameID];
/*      */       index[index[0]] = identity;
/*      */       index[0] = index[0] + 1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int findGTE(int[] list, int start, int len, int value) {
/*      */     int low = start;
/*      */     int high = start + len - 1;
/*      */     int end = high;
/*      */     while (low <= high) {
/*      */       int mid = low + high >>> 1;
/*      */       int c = list[mid];
/*      */       if (c > value) {
/*      */         high = mid - 1;
/*      */         continue;
/*      */       } 
/*      */       if (c < value) {
/*      */         low = mid + 1;
/*      */         continue;
/*      */       } 
/*      */       return mid;
/*      */     } 
/*      */     return (low <= end && list[low] > value) ? low : -1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int findElementFromIndex(int nsIndex, int lnIndex, int firstPotential) {
/*      */     int[][][] indexes = this.m_elemIndexes;
/*      */     if (null != indexes && nsIndex < indexes.length) {
/*      */       int[][] lnIndexs = indexes[nsIndex];
/*      */       if (null != lnIndexs && lnIndex < lnIndexs.length) {
/*      */         int[] elems = lnIndexs[lnIndex];
/*      */         if (null != elems) {
/*      */           int pos = findGTE(elems, 1, elems[0], firstPotential);
/*      */           if (pos > -1) {
/*      */             return elems[pos];
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     return -2;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract int getNextNodeIdentity(int paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract boolean nextNode();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract int getNumberOfNodes();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected short _type(int identity) {
/*      */     int info = _exptype(identity);
/*      */     if (-1 != info) {
/*      */       return this.m_expandedNameTable.getType(info);
/*      */     }
/*      */     return -1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _exptype(int identity) {
/*      */     if (identity == -1) {
/*      */       return -1;
/*      */     }
/*      */     while (identity >= this.m_size) {
/*      */       if (!nextNode() && identity >= this.m_size) {
/*      */         return -1;
/*      */       }
/*      */     } 
/*      */     return this.m_exptype.elementAt(identity);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _level(int identity) {
/*      */     while (identity >= this.m_size) {
/*      */       boolean isMore = nextNode();
/*      */       if (!isMore && identity >= this.m_size) {
/*      */         return -1;
/*      */       }
/*      */     } 
/*      */     int i = 0;
/*      */     while (-1 != (identity = _parent(identity))) {
/*      */       i++;
/*      */     }
/*      */     return i;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _firstch(int identity) {
/*      */     int info = (identity >= this.m_size) ? -2 : this.m_firstch.elementAt(identity);
/*      */     while (info == -2) {
/*      */       boolean isMore = nextNode();
/*      */       if (identity >= this.m_size && !isMore) {
/*      */         return -1;
/*      */       }
/*      */       info = this.m_firstch.elementAt(identity);
/*      */       if (info == -2 && !isMore) {
/*      */         return -1;
/*      */       }
/*      */     } 
/*      */     return info;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _nextsib(int identity) {
/*      */     int info = (identity >= this.m_size) ? -2 : this.m_nextsib.elementAt(identity);
/*      */     while (info == -2) {
/*      */       boolean isMore = nextNode();
/*      */       if (identity >= this.m_size && !isMore) {
/*      */         return -1;
/*      */       }
/*      */       info = this.m_nextsib.elementAt(identity);
/*      */       if (info == -2 && !isMore) {
/*      */         return -1;
/*      */       }
/*      */     } 
/*      */     return info;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _prevsib(int identity) {
/*      */     if (identity < this.m_size) {
/*      */       return this.m_prevsib.elementAt(identity);
/*      */     }
/*      */     while (true) {
/*      */       boolean isMore = nextNode();
/*      */       if (identity >= this.m_size && !isMore) {
/*      */         return -1;
/*      */       }
/*      */       if (identity < this.m_size) {
/*      */         return this.m_prevsib.elementAt(identity);
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _parent(int identity) {
/*      */     if (identity < this.m_size) {
/*      */       return this.m_parent.elementAt(identity);
/*      */     }
/*      */     while (true) {
/*      */       boolean isMore = nextNode();
/*      */       if (identity >= this.m_size && !isMore) {
/*      */         return -1;
/*      */       }
/*      */       if (identity < this.m_size) {
/*      */         return this.m_parent.elementAt(identity);
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dumpDTM(OutputStream os) {
/*      */     try {
/*      */       if (os == null) {
/*      */         File f = new File("DTMDump" + hashCode() + ".txt");
/*      */         System.err.println("Dumping... " + f.getAbsolutePath());
/*      */         os = new FileOutputStream(f);
/*      */       } 
/*      */       PrintStream ps = new PrintStream(os);
/*      */       while (nextNode());
/*      */       int nRecords = this.m_size;
/*      */       ps.println("Total nodes: " + nRecords);
/*      */       for (int index = 0; index < nRecords; index++) {
/*      */         String typestring;
/*      */         int i = makeNodeHandle(index);
/*      */         ps.println("=========== index=" + index + " handle=" + i + " ===========");
/*      */         ps.println("NodeName: " + getNodeName(i));
/*      */         ps.println("NodeNameX: " + getNodeNameX(i));
/*      */         ps.println("LocalName: " + getLocalName(i));
/*      */         ps.println("NamespaceURI: " + getNamespaceURI(i));
/*      */         ps.println("Prefix: " + getPrefix(i));
/*      */         int exTypeID = _exptype(index);
/*      */         ps.println("Expanded Type ID: " + Integer.toHexString(exTypeID));
/*      */         int type = _type(index);
/*      */         switch (type) {
/*      */           case 2:
/*      */             typestring = "ATTRIBUTE_NODE";
/*      */             break;
/*      */           case 4:
/*      */             typestring = "CDATA_SECTION_NODE";
/*      */             break;
/*      */           case 8:
/*      */             typestring = "COMMENT_NODE";
/*      */             break;
/*      */           case 11:
/*      */             typestring = "DOCUMENT_FRAGMENT_NODE";
/*      */             break;
/*      */           case 9:
/*      */             typestring = "DOCUMENT_NODE";
/*      */             break;
/*      */           case 10:
/*      */             typestring = "DOCUMENT_NODE";
/*      */             break;
/*      */           case 1:
/*      */             typestring = "ELEMENT_NODE";
/*      */             break;
/*      */           case 6:
/*      */             typestring = "ENTITY_NODE";
/*      */             break;
/*      */           case 5:
/*      */             typestring = "ENTITY_REFERENCE_NODE";
/*      */             break;
/*      */           case 13:
/*      */             typestring = "NAMESPACE_NODE";
/*      */             break;
/*      */           case 12:
/*      */             typestring = "NOTATION_NODE";
/*      */             break;
/*      */           case -1:
/*      */             typestring = "NULL";
/*      */             break;
/*      */           case 7:
/*      */             typestring = "PROCESSING_INSTRUCTION_NODE";
/*      */             break;
/*      */           case 3:
/*      */             typestring = "TEXT_NODE";
/*      */             break;
/*      */           default:
/*      */             typestring = "Unknown!";
/*      */             break;
/*      */         } 
/*      */         ps.println("Type: " + typestring);
/*      */         int firstChild = _firstch(index);
/*      */         if (-1 == firstChild) {
/*      */           ps.println("First child: DTM.NULL");
/*      */         } else if (-2 == firstChild) {
/*      */           ps.println("First child: NOTPROCESSED");
/*      */         } else {
/*      */           ps.println("First child: " + firstChild);
/*      */         } 
/*      */         if (this.m_prevsib != null) {
/*      */           int prevSibling = _prevsib(index);
/*      */           if (-1 == prevSibling) {
/*      */             ps.println("Prev sibling: DTM.NULL");
/*      */           } else if (-2 == prevSibling) {
/*      */             ps.println("Prev sibling: NOTPROCESSED");
/*      */           } else {
/*      */             ps.println("Prev sibling: " + prevSibling);
/*      */           } 
/*      */         } 
/*      */         int nextSibling = _nextsib(index);
/*      */         if (-1 == nextSibling) {
/*      */           ps.println("Next sibling: DTM.NULL");
/*      */         } else if (-2 == nextSibling) {
/*      */           ps.println("Next sibling: NOTPROCESSED");
/*      */         } else {
/*      */           ps.println("Next sibling: " + nextSibling);
/*      */         } 
/*      */         int parent = _parent(index);
/*      */         if (-1 == parent) {
/*      */           ps.println("Parent: DTM.NULL");
/*      */         } else if (-2 == parent) {
/*      */           ps.println("Parent: NOTPROCESSED");
/*      */         } else {
/*      */           ps.println("Parent: " + parent);
/*      */         } 
/*      */         int level = _level(index);
/*      */         ps.println("Level: " + level);
/*      */         ps.println("Node Value: " + getNodeValue(i));
/*      */         ps.println("String Value: " + getStringValue(i));
/*      */       } 
/*      */     } catch (IOException ioe) {
/*      */       ioe.printStackTrace(System.err);
/*      */       throw new RuntimeException(ioe.getMessage());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String dumpNode(int nodeHandle) {
/*      */     String typestring;
/*      */     if (nodeHandle == -1) {
/*      */       return "[null]";
/*      */     }
/*      */     switch (getNodeType(nodeHandle)) {
/*      */       case 2:
/*      */         typestring = "ATTR";
/*      */         break;
/*      */       case 4:
/*      */         typestring = "CDATA";
/*      */         break;
/*      */       case 8:
/*      */         typestring = "COMMENT";
/*      */         break;
/*      */       case 11:
/*      */         typestring = "DOC_FRAG";
/*      */         break;
/*      */       case 9:
/*      */         typestring = "DOC";
/*      */         break;
/*      */       case 10:
/*      */         typestring = "DOC_TYPE";
/*      */         break;
/*      */       case 1:
/*      */         typestring = "ELEMENT";
/*      */         break;
/*      */       case 6:
/*      */         typestring = "ENTITY";
/*      */         break;
/*      */       case 5:
/*      */         typestring = "ENT_REF";
/*      */         break;
/*      */       case 13:
/*      */         typestring = "NAMESPACE";
/*      */         break;
/*      */       case 12:
/*      */         typestring = "NOTATION";
/*      */         break;
/*      */       case -1:
/*      */         typestring = "null";
/*      */         break;
/*      */       case 7:
/*      */         typestring = "PI";
/*      */         break;
/*      */       case 3:
/*      */         typestring = "TEXT";
/*      */         break;
/*      */       default:
/*      */         typestring = "Unknown!";
/*      */         break;
/*      */     } 
/*      */     return "[" + nodeHandle + ": " + typestring + "(0x" + Integer.toHexString(getExpandedTypeID(nodeHandle)) + ") " + getNodeNameX(nodeHandle) + " {" + getNamespaceURI(nodeHandle) + "}=\"" + getNodeValue(nodeHandle) + "\"]";
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFeature(String featureId, boolean state) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasChildNodes(int nodeHandle) {
/*      */     int identity = makeNodeIdentity(nodeHandle);
/*      */     int firstChild = _firstch(identity);
/*      */     return (firstChild != -1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int makeNodeHandle(int nodeIdentity) {
/*      */     if (-1 == nodeIdentity) {
/*      */       return -1;
/*      */     }
/*      */     return this.m_dtmIdent.elementAt(nodeIdentity >>> 16) + (nodeIdentity & 0xFFFF);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int makeNodeIdentity(int nodeHandle) {
/*      */     if (-1 == nodeHandle) {
/*      */       return -1;
/*      */     }
/*      */     if (this.m_mgrDefault != null) {
/*      */       int whichDTMindex = nodeHandle >>> 16;
/*      */       if (this.m_mgrDefault.m_dtms[whichDTMindex] != this) {
/*      */         return -1;
/*      */       }
/*      */       return this.m_mgrDefault.m_dtm_offsets[whichDTMindex] | nodeHandle & 0xFFFF;
/*      */     } 
/*      */     int whichDTMid = this.m_dtmIdent.indexOf(nodeHandle & 0xFFFF0000);
/*      */     return (whichDTMid == -1) ? -1 : ((whichDTMid << 16) + (nodeHandle & 0xFFFF));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFirstChild(int nodeHandle) {
/*      */     int identity = makeNodeIdentity(nodeHandle);
/*      */     int firstChild = _firstch(identity);
/*      */     return makeNodeHandle(firstChild);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTypedFirstChild(int nodeHandle, int nodeType) {
/*      */     if (nodeType < 14) {
/*      */       int firstChild = _firstch(makeNodeIdentity(nodeHandle));
/*      */       for (; firstChild != -1; firstChild = _nextsib(firstChild)) {
/*      */         int eType = _exptype(firstChild);
/*      */         if (eType == nodeType || (eType >= 14 && this.m_expandedNameTable.getType(eType) == nodeType)) {
/*      */           return makeNodeHandle(firstChild);
/*      */         }
/*      */       } 
/*      */     } else {
/*      */       int firstChild = _firstch(makeNodeIdentity(nodeHandle));
/*      */       for (; firstChild != -1; firstChild = _nextsib(firstChild)) {
/*      */         if (_exptype(firstChild) == nodeType) {
/*      */           return makeNodeHandle(firstChild);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     return -1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMDefaultBase(DTMManager mgr, Source source, int dtmIdentity, DTMWSFilter whiteSpaceFilter, XMLStringFactory xstringfactory, boolean doIndexing, int blocksize, boolean usePrevsib, boolean newNameTable)
/*      */   {
/*      */     int numblocks;
/*      */     this.m_size = 0;
/*      */     this.m_namespaceDeclSets = null;
/*      */     this.m_namespaceDeclSetElements = null;
/*      */     this.m_mgrDefault = null;
/*      */     this.m_shouldStripWS = false;
/* 1246 */     this.m_namespaceLists = null; if (blocksize <= 64) {
/*      */       numblocks = 4; this.m_dtmIdent = new SuballocatedIntVector(4, 1);
/*      */     } else {
/*      */       numblocks = 32; this.m_dtmIdent = new SuballocatedIntVector(32);
/*      */     }  this.m_exptype = new SuballocatedIntVector(blocksize, numblocks); this.m_firstch = new SuballocatedIntVector(blocksize, numblocks); this.m_nextsib = new SuballocatedIntVector(blocksize, numblocks); this.m_parent = new SuballocatedIntVector(blocksize, numblocks); if (usePrevsib)
/*      */       this.m_prevsib = new SuballocatedIntVector(blocksize, numblocks);  this.m_mgr = mgr; if (mgr instanceof DTMManagerDefault)
/*      */       this.m_mgrDefault = (DTMManagerDefault)mgr;  this.m_documentBaseURI = (null != source) ? source.getSystemId() : null; this.m_dtmIdent.setElementAt(dtmIdentity, 0);
/*      */     this.m_wsfilter = whiteSpaceFilter;
/*      */     this.m_xstrf = xstringfactory;
/*      */     this.m_indexing = doIndexing;
/*      */     if (doIndexing) {
/*      */       this.m_expandedNameTable = new ExpandedNameTable();
/*      */     } else {
/*      */       this.m_expandedNameTable = this.m_mgrDefault.getExpandedNameTable(this);
/*      */     } 
/*      */     if (null != whiteSpaceFilter) {
/*      */       this.m_shouldStripWhitespaceStack = new BoolStack();
/*      */       pushShouldStripWhitespace(false);
/* 1264 */     }  } protected void declareNamespaceInContext(int elementNodeIndex, int namespaceNodeIndex) { SuballocatedIntVector nsList = null;
/* 1265 */     if (this.m_namespaceDeclSets == null) {
/*      */ 
/*      */ 
/*      */       
/* 1269 */       this.m_namespaceDeclSetElements = new SuballocatedIntVector(32);
/* 1270 */       this.m_namespaceDeclSetElements.addElement(elementNodeIndex);
/* 1271 */       this.m_namespaceDeclSets = new Vector();
/* 1272 */       nsList = new SuballocatedIntVector(32);
/* 1273 */       this.m_namespaceDeclSets.addElement(nsList);
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1279 */       int last = this.m_namespaceDeclSetElements.size() - 1;
/*      */       
/* 1281 */       if (last >= 0 && elementNodeIndex == this.m_namespaceDeclSetElements.elementAt(last))
/*      */       {
/* 1283 */         nsList = this.m_namespaceDeclSets.elementAt(last);
/*      */       }
/*      */     } 
/* 1286 */     if (nsList == null) {
/*      */       
/* 1288 */       this.m_namespaceDeclSetElements.addElement(elementNodeIndex);
/*      */ 
/*      */       
/* 1291 */       SuballocatedIntVector inherited = findNamespaceContext(_parent(elementNodeIndex));
/*      */       
/* 1293 */       if (inherited != null) {
/*      */ 
/*      */ 
/*      */         
/* 1297 */         int isize = inherited.size();
/*      */ 
/*      */ 
/*      */         
/* 1301 */         nsList = new SuballocatedIntVector(Math.max(Math.min(isize + 16, 2048), 32));
/*      */ 
/*      */         
/* 1304 */         for (int j = 0; j < isize; j++)
/*      */         {
/* 1306 */           nsList.addElement(inherited.elementAt(j));
/*      */         }
/*      */       } else {
/* 1309 */         nsList = new SuballocatedIntVector(32);
/*      */       } 
/*      */       
/* 1312 */       this.m_namespaceDeclSets.addElement(nsList);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1319 */     int newEType = _exptype(namespaceNodeIndex);
/*      */     
/* 1321 */     for (int i = nsList.size() - 1; i >= 0; i--) {
/*      */       
/* 1323 */       if (newEType == getExpandedTypeID(nsList.elementAt(i))) {
/*      */         
/* 1325 */         nsList.setElementAt(makeNodeHandle(namespaceNodeIndex), i);
/*      */         return;
/*      */       } 
/*      */     } 
/* 1329 */     nsList.addElement(makeNodeHandle(namespaceNodeIndex)); }
/*      */   public int getLastChild(int nodeHandle) { int identity = makeNodeIdentity(nodeHandle); int child = _firstch(identity); int lastChild = -1; while (child != -1) {
/*      */       lastChild = child; child = _nextsib(child);
/*      */     }  return makeNodeHandle(lastChild); }
/*      */   public abstract int getAttributeNode(int paramInt, String paramString1, String paramString2);
/*      */   public int getFirstAttribute(int nodeHandle) { int nodeID = makeNodeIdentity(nodeHandle); return makeNodeHandle(getFirstAttributeIdentity(nodeID)); } protected int getFirstAttributeIdentity(int identity) { int type = _type(identity); if (1 == type)
/*      */       while (-1 != (identity = getNextNodeIdentity(identity))) {
/*      */         type = _type(identity); if (type == 2)
/*      */           return identity; 
/*      */         if (13 != type)
/*      */           break; 
/*      */       }  
/* 1341 */     return -1; } protected SuballocatedIntVector findNamespaceContext(int elementNodeIndex) { if (null != this.m_namespaceDeclSetElements) {
/*      */ 
/*      */ 
/*      */       
/* 1345 */       int wouldBeAt = findInSortedSuballocatedIntVector(this.m_namespaceDeclSetElements, elementNodeIndex);
/*      */       
/* 1347 */       if (wouldBeAt >= 0)
/* 1348 */         return this.m_namespaceDeclSets.elementAt(wouldBeAt); 
/* 1349 */       if (wouldBeAt == -1) {
/* 1350 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1354 */       wouldBeAt = -1 - wouldBeAt;
/*      */ 
/*      */       
/* 1357 */       int candidate = this.m_namespaceDeclSetElements.elementAt(--wouldBeAt);
/* 1358 */       int ancestor = _parent(elementNodeIndex);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1363 */       if (wouldBeAt == 0 && candidate < ancestor) {
/* 1364 */         int uppermostNSCandidateID, rootHandle = getDocumentRoot(makeNodeHandle(elementNodeIndex));
/* 1365 */         int rootID = makeNodeIdentity(rootHandle);
/*      */ 
/*      */         
/* 1368 */         if (getNodeType(rootHandle) == 9) {
/* 1369 */           int ch = _firstch(rootID);
/* 1370 */           uppermostNSCandidateID = (ch != -1) ? ch : rootID;
/*      */         } else {
/* 1372 */           uppermostNSCandidateID = rootID;
/*      */         } 
/*      */         
/* 1375 */         if (candidate == uppermostNSCandidateID) {
/* 1376 */           return this.m_namespaceDeclSets.elementAt(wouldBeAt);
/*      */         }
/*      */       } 
/*      */       
/* 1380 */       label35: while (wouldBeAt >= 0 && ancestor > 0) {
/*      */         
/* 1382 */         if (candidate == ancestor)
/*      */         {
/* 1384 */           return this.m_namespaceDeclSets.elementAt(wouldBeAt); } 
/* 1385 */         if (candidate < ancestor)
/*      */           while (true)
/*      */           
/* 1388 */           { ancestor = _parent(ancestor);
/* 1389 */             if (candidate >= ancestor)
/* 1390 */               continue label35;  }   if (wouldBeAt > 0)
/*      */         {
/* 1392 */           candidate = this.m_namespaceDeclSetElements.elementAt(--wouldBeAt);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1399 */     return null; }
/*      */   protected int getTypedAttribute(int nodeHandle, int attType) { int type = getNodeType(nodeHandle);
/*      */     if (1 == type) {
/*      */       int identity = makeNodeIdentity(nodeHandle);
/*      */       while (-1 != (identity = getNextNodeIdentity(identity))) {
/*      */         type = _type(identity);
/*      */         if (type == 2) {
/*      */           if (_exptype(identity) == attType)
/*      */             return makeNodeHandle(identity); 
/*      */           continue;
/*      */         } 
/*      */         if (13 != type)
/*      */           break; 
/*      */       } 
/*      */     } 
/*      */     return -1; }
/*      */   public int getNextSibling(int nodeHandle) { if (nodeHandle == -1)
/*      */       return -1; 
/*      */     return makeNodeHandle(_nextsib(makeNodeIdentity(nodeHandle))); } protected int findInSortedSuballocatedIntVector(SuballocatedIntVector vector, int lookfor) {
/* 1418 */     int i = 0;
/* 1419 */     if (vector != null) {
/* 1420 */       int first = 0;
/* 1421 */       int last = vector.size() - 1;
/*      */       
/* 1423 */       while (first <= last) {
/* 1424 */         i = (first + last) / 2;
/* 1425 */         int test = lookfor - vector.elementAt(i);
/* 1426 */         if (test == 0) {
/* 1427 */           return i;
/*      */         }
/* 1429 */         if (test < 0) {
/* 1430 */           last = i - 1;
/*      */           continue;
/*      */         } 
/* 1433 */         first = i + 1;
/*      */       } 
/*      */ 
/*      */       
/* 1437 */       if (first > i) {
/* 1438 */         i = first;
/*      */       }
/*      */     } 
/*      */     
/* 1442 */     return -1 - i; } public int getTypedNextSibling(int nodeHandle, int nodeType) { if (nodeHandle == -1)
/*      */       return -1; 
/*      */     int node = makeNodeIdentity(nodeHandle);
/*      */     int eType;
/*      */     while ((node = _nextsib(node)) != -1 && (eType = _exptype(node)) != nodeType && this.m_expandedNameTable.getType(eType) != nodeType);
/*      */     return (node == -1) ? -1 : makeNodeHandle(node); }
/*      */   public int getPreviousSibling(int nodeHandle) { if (nodeHandle == -1)
/*      */       return -1; 
/*      */     if (this.m_prevsib != null)
/*      */       return makeNodeHandle(_prevsib(makeNodeIdentity(nodeHandle))); 
/*      */     int nodeID = makeNodeIdentity(nodeHandle);
/*      */     int parent = _parent(nodeID);
/*      */     int node = _firstch(parent);
/*      */     int result = -1;
/*      */     while (node != nodeID) {
/*      */       result = node;
/*      */       node = _nextsib(node);
/*      */     } 
/*      */     return makeNodeHandle(result); }
/* 1461 */   public int getFirstNamespaceNode(int nodeHandle, boolean inScope) { if (inScope) {
/*      */       
/* 1463 */       int i = makeNodeIdentity(nodeHandle);
/* 1464 */       if (_type(i) == 1) {
/*      */         
/* 1466 */         SuballocatedIntVector nsContext = findNamespaceContext(i);
/* 1467 */         if (nsContext == null || nsContext.size() < 1) {
/* 1468 */           return -1;
/*      */         }
/* 1470 */         return nsContext.elementAt(0);
/*      */       } 
/*      */       
/* 1473 */       return -1;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1483 */     int identity = makeNodeIdentity(nodeHandle);
/* 1484 */     if (_type(identity) == 1) {
/*      */       
/* 1486 */       while (-1 != (identity = getNextNodeIdentity(identity))) {
/*      */         
/* 1488 */         int type = _type(identity);
/* 1489 */         if (type == 13)
/* 1490 */           return makeNodeHandle(identity); 
/* 1491 */         if (2 != type)
/*      */           break; 
/*      */       } 
/* 1494 */       return -1;
/*      */     } 
/*      */     
/* 1497 */     return -1; }
/*      */    public int getNextAttribute(int nodeHandle) {
/*      */     int nodeID = makeNodeIdentity(nodeHandle);
/*      */     if (_type(nodeID) == 2)
/*      */       return makeNodeHandle(getNextAttributeIdentity(nodeID)); 
/*      */     return -1;
/*      */   } protected int getNextAttributeIdentity(int identity) {
/*      */     while (-1 != (identity = getNextNodeIdentity(identity))) {
/*      */       int type = _type(identity);
/*      */       if (type == 2)
/*      */         return identity; 
/*      */       if (type != 13)
/*      */         break; 
/*      */     } 
/*      */     return -1;
/*      */   }
/*      */   public int getNextNamespaceNode(int baseHandle, int nodeHandle, boolean inScope) {
/* 1514 */     if (inScope) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1521 */       SuballocatedIntVector nsContext = findNamespaceContext(makeNodeIdentity(baseHandle));
/*      */       
/* 1523 */       if (nsContext == null)
/* 1524 */         return -1; 
/* 1525 */       int i = 1 + nsContext.indexOf(nodeHandle);
/* 1526 */       if (i <= 0 || i == nsContext.size()) {
/* 1527 */         return -1;
/*      */       }
/* 1529 */       return nsContext.elementAt(i);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1534 */     int identity = makeNodeIdentity(nodeHandle);
/* 1535 */     while (-1 != (identity = getNextNodeIdentity(identity))) {
/*      */       
/* 1537 */       int type = _type(identity);
/* 1538 */       if (type == 13)
/*      */       {
/* 1540 */         return makeNodeHandle(identity);
/*      */       }
/* 1542 */       if (type != 2) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1548 */     return -1;
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
/*      */   public int getParent(int nodeHandle) {
/* 1561 */     int identity = makeNodeIdentity(nodeHandle);
/*      */     
/* 1563 */     if (identity > 0) {
/* 1564 */       return makeNodeHandle(_parent(identity));
/*      */     }
/* 1566 */     return -1;
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
/*      */   public int getDocument() {
/* 1579 */     return this.m_dtmIdent.elementAt(0);
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
/*      */   public int getOwnerDocument(int nodeHandle) {
/* 1597 */     if (9 == getNodeType(nodeHandle)) {
/* 1598 */       return -1;
/*      */     }
/* 1600 */     return getDocumentRoot(nodeHandle);
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
/*      */   public int getDocumentRoot(int nodeHandle) {
/* 1613 */     return getManager().getDTM(nodeHandle).getDocument();
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
/*      */   public abstract XMLString getStringValue(int paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getStringValueChunkCount(int nodeHandle) {
/* 1643 */     error(XMLMessages.createXMLMessage("ER_METHOD_NOT_SUPPORTED", null));
/*      */     
/* 1645 */     return 0;
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
/*      */ 
/*      */   
/*      */   public char[] getStringValueChunk(int nodeHandle, int chunkIndex, int[] startAndLen) {
/* 1666 */     error(XMLMessages.createXMLMessage("ER_METHOD_NOT_SUPPORTED", null));
/*      */     
/* 1668 */     return null;
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
/*      */   public int getExpandedTypeID(int nodeHandle) {
/* 1682 */     int id = makeNodeIdentity(nodeHandle);
/* 1683 */     if (id == -1)
/* 1684 */       return -1; 
/* 1685 */     return _exptype(id);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public int getExpandedTypeID(String namespace, String localName, int type) {
/* 1707 */     ExpandedNameTable ent = this.m_expandedNameTable;
/*      */     
/* 1709 */     return ent.getExpandedTypeID(namespace, localName, type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLocalNameFromExpandedNameID(int expandedNameID) {
/* 1720 */     return this.m_expandedNameTable.getLocalName(expandedNameID);
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
/*      */   public String getNamespaceFromExpandedNameID(int expandedNameID) {
/* 1732 */     return this.m_expandedNameTable.getNamespace(expandedNameID);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNamespaceType(int nodeHandle) {
/* 1743 */     int identity = makeNodeIdentity(nodeHandle);
/* 1744 */     int expandedNameID = _exptype(identity);
/*      */     
/* 1746 */     return this.m_expandedNameTable.getNamespaceID(expandedNameID);
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
/*      */   public abstract String getNodeName(int paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNodeNameX(int nodeHandle) {
/* 1772 */     error(XMLMessages.createXMLMessage("ER_METHOD_NOT_SUPPORTED", null));
/*      */     
/* 1774 */     return null;
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
/*      */   public abstract String getLocalName(int paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract String getPrefix(int paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract String getNamespaceURI(int paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract String getNodeValue(int paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short getNodeType(int nodeHandle) {
/* 1836 */     if (nodeHandle == -1)
/* 1837 */       return -1; 
/* 1838 */     return this.m_expandedNameTable.getType(_exptype(makeNodeIdentity(nodeHandle)));
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
/*      */   public short getLevel(int nodeHandle) {
/* 1852 */     int identity = makeNodeIdentity(nodeHandle);
/* 1853 */     return (short)(_level(identity) + 1);
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
/*      */   public int getNodeIdent(int nodeHandle) {
/* 1870 */     return makeNodeIdentity(nodeHandle);
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
/*      */   public int getNodeHandle(int nodeId) {
/* 1887 */     return makeNodeHandle(nodeId);
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
/*      */ 
/*      */   
/*      */   public boolean isSupported(String feature, String version) {
/* 1908 */     return false;
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
/*      */   public String getDocumentBaseURI() {
/* 1920 */     return this.m_documentBaseURI;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDocumentBaseURI(String baseURI) {
/* 1930 */     this.m_documentBaseURI = baseURI;
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
/*      */   public String getDocumentSystemIdentifier(int nodeHandle) {
/* 1944 */     return this.m_documentBaseURI;
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
/*      */   public String getDocumentEncoding(int nodeHandle) {
/* 1959 */     return "UTF-8";
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
/*      */   public String getDocumentStandalone(int nodeHandle) {
/* 1974 */     return null;
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
/*      */   public String getDocumentVersion(int documentHandle) {
/* 1989 */     return null;
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
/*      */   public boolean getDocumentAllDeclarationsProcessed() {
/* 2006 */     return true;
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
/*      */   
/*      */   public abstract String getDocumentTypeDeclarationSystemIdentifier();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract String getDocumentTypeDeclarationPublicIdentifier();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int getElementById(String paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract String getUnparsedEntityURI(String paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean supportsPreStripping() {
/* 2094 */     return true;
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
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNodeAfter(int nodeHandle1, int nodeHandle2) {
/* 2116 */     int index1 = makeNodeIdentity(nodeHandle1);
/* 2117 */     int index2 = makeNodeIdentity(nodeHandle2);
/*      */     
/* 2119 */     return (index1 != -1 && index2 != -1 && index1 <= index2);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCharacterElementContentWhitespace(int nodeHandle) {
/* 2142 */     return false;
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
/*      */   public boolean isDocumentAllDeclarationsProcessed(int documentHandle) {
/* 2159 */     return true;
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
/*      */   public abstract boolean isAttributeSpecified(int paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void dispatchCharactersEvents(int paramInt, ContentHandler paramContentHandler, boolean paramBoolean) throws SAXException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void dispatchToEvents(int paramInt, ContentHandler paramContentHandler) throws SAXException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Node getNode(int nodeHandle) {
/* 2218 */     return new DTMNodeProxy(this, nodeHandle);
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
/*      */   public void appendChild(int newChild, boolean clone, boolean cloneDepth) {
/* 2237 */     error(XMLMessages.createXMLMessage("ER_METHOD_NOT_SUPPORTED", null));
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
/*      */   public void appendTextChild(String str) {
/* 2251 */     error(XMLMessages.createXMLMessage("ER_METHOD_NOT_SUPPORTED", null));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void error(String msg) {
/* 2261 */     throw new DTMException(msg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean getShouldStripWhitespace() {
/* 2272 */     return this.m_shouldStripWS;
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
/*      */   protected void pushShouldStripWhitespace(boolean shouldStrip) {
/* 2284 */     this.m_shouldStripWS = shouldStrip;
/*      */     
/* 2286 */     if (null != this.m_shouldStripWhitespaceStack) {
/* 2287 */       this.m_shouldStripWhitespaceStack.push(shouldStrip);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void popShouldStripWhitespace() {
/* 2297 */     if (null != this.m_shouldStripWhitespaceStack) {
/* 2298 */       this.m_shouldStripWS = this.m_shouldStripWhitespaceStack.popAndTop();
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
/*      */   protected void setShouldStripWhitespace(boolean shouldStrip) {
/* 2311 */     this.m_shouldStripWS = shouldStrip;
/*      */     
/* 2313 */     if (null != this.m_shouldStripWhitespaceStack) {
/* 2314 */       this.m_shouldStripWhitespaceStack.setTop(shouldStrip);
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
/*      */   public void documentRegistration() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void documentRelease() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void migrateTo(DTMManager mgr) {
/* 2344 */     this.m_mgr = mgr;
/* 2345 */     if (mgr instanceof DTMManagerDefault) {
/* 2346 */       this.m_mgrDefault = (DTMManagerDefault)mgr;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DTMManager getManager() {
/* 2357 */     return this.m_mgr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SuballocatedIntVector getDTMIDs() {
/* 2368 */     if (this.m_mgr == null) return null; 
/* 2369 */     return this.m_dtmIdent;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/dtm/ref/DTMDefaultBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */