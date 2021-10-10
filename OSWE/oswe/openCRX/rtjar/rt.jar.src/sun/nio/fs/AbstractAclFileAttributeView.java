/*    */ package sun.nio.fs;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.nio.file.attribute.AclEntry;
/*    */ import java.nio.file.attribute.AclFileAttributeView;
/*    */ import java.nio.file.attribute.UserPrincipal;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class AbstractAclFileAttributeView
/*    */   implements AclFileAttributeView, DynamicFileAttributeView
/*    */ {
/*    */   private static final String OWNER_NAME = "owner";
/*    */   private static final String ACL_NAME = "acl";
/*    */   
/*    */   public final String name() {
/* 44 */     return "acl";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final void setAttribute(String paramString, Object paramObject) throws IOException {
/* 52 */     if (paramString.equals("owner")) {
/* 53 */       setOwner((UserPrincipal)paramObject);
/*    */       return;
/*    */     } 
/* 56 */     if (paramString.equals("acl")) {
/* 57 */       setAcl((List<AclEntry>)paramObject);
/*    */       return;
/*    */     } 
/* 60 */     throw new IllegalArgumentException("'" + name() + ":" + paramString + "' not recognized");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final Map<String, Object> readAttributes(String[] paramArrayOfString) throws IOException {
/* 68 */     boolean bool1 = false;
/* 69 */     boolean bool2 = false;
/* 70 */     for (String str : paramArrayOfString) {
/* 71 */       if (str.equals("*")) {
/* 72 */         bool2 = true;
/* 73 */         bool1 = true;
/*    */       
/*    */       }
/* 76 */       else if (str.equals("acl")) {
/* 77 */         bool1 = true;
/*    */       
/*    */       }
/* 80 */       else if (str.equals("owner")) {
/* 81 */         bool2 = true;
/*    */       } else {
/*    */         
/* 84 */         throw new IllegalArgumentException("'" + name() + ":" + str + "' not recognized");
/*    */       } 
/*    */     } 
/* 87 */     HashMap<Object, Object> hashMap = new HashMap<>(2);
/* 88 */     if (bool1)
/* 89 */       hashMap.put("acl", getAcl()); 
/* 90 */     if (bool2)
/* 91 */       hashMap.put("owner", getOwner()); 
/* 92 */     return (Map)Collections.unmodifiableMap(hashMap);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/AbstractAclFileAttributeView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */