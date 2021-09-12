/*    */ package com.adventnet.appmanager.fault.rcatree;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import javax.swing.tree.DefaultMutableTreeNode;
/*    */ import javax.swing.tree.TreeNode;
/*    */ 
/*    */ public class AMRCATreeNodeRenderer implements com.adventnet.webclient.components.tree.TreeNodeRenderer
/*    */ {
/*    */   private String actionLink;
/*    */   private String displayName;
/*    */   private String id;
/*    */   private String imageIcon;
/*    */   private String target;
/*    */   
/*    */   public AMRCATreeNodeRenderer()
/*    */   {
/* 17 */     this.actionLink = null;
/* 18 */     this.displayName = null;
/* 19 */     this.id = null;
/* 20 */     this.imageIcon = null;
/* 21 */     this.target = null;
/*    */   }
/*    */   
/*    */   public String getActionLink()
/*    */   {
/* 26 */     return this.actionLink;
/*    */   }
/*    */   
/*    */   public String getDisplayName()
/*    */   {
/* 31 */     return java.net.URLDecoder.decode(this.displayName);
/*    */   }
/*    */   
/*    */   public String getID()
/*    */   {
/* 36 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getImageIcon()
/*    */   {
/* 41 */     return this.imageIcon;
/*    */   }
/*    */   
/*    */   public String getTarget()
/*    */   {
/* 46 */     return this.target;
/*    */   }
/*    */   
/*    */   public void setTreeNode(TreeNode treeNode)
/*    */   {
/* 51 */     HashMap<String, String> userObject = (HashMap)((DefaultMutableTreeNode)treeNode).getUserObject();
/* 52 */     this.id = ((String)userObject.get("id"));
/* 53 */     this.actionLink = ((String)userObject.get("actionlink"));
/* 54 */     this.displayName = ((String)userObject.get("displayname"));
/* 55 */     this.imageIcon = ((String)userObject.get("imageicon"));
/* 56 */     this.target = ((String)userObject.get("target"));
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\fault\rcatree\AMRCATreeNodeRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */