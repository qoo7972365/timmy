/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.ExtProdUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import com.adventnet.appmanager.vserver.VMUtil;
/*     */ import com.manageengine.appmanager.util.VMStorageMappingFinder;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessage;
/*     */ import org.apache.struts.action.ActionMessages;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StorageMappingAction
/*     */   extends DispatchAction
/*     */ {
/*     */   public ActionForward showStoragePage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  33 */     isOpStorConfigured(request, mapping);
/*  34 */     return mapping.findForward("StorageTab");
/*     */   }
/*     */   
/*     */   public ActionForward showStorageMapping(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  39 */     ActionMessages messages = handleNoMapping();
/*  40 */     String displayMode = request.getParameter("displayMode");
/*  41 */     displayMode = (displayMode == null) || (displayMode.equals("")) ? "Tabular" : displayMode;
/*  42 */     request.setAttribute("displaymode", displayMode);
/*  43 */     ActionForward forward = mapping.findForward("StorageTable");
/*  44 */     if (displayMode.equals("Map"))
/*     */     {
/*  46 */       forward = mapping.findForward("StorageMap");
/*  47 */       populateStorageMap(request, messages);
/*     */     }
/*     */     else
/*     */     {
/*  51 */       populateStorageTable(request, messages);
/*     */     }
/*     */     
/*  54 */     if (!messages.isEmpty())
/*     */     {
/*  56 */       request.setAttribute("SAN_NOMAPPING_KEY", messages);
/*     */     }
/*  58 */     return forward;
/*     */   }
/*     */   
/*     */ 
/*     */   private void populateStorageMap(HttpServletRequest request, ActionMessages messages)
/*     */   {
/*  64 */     VMStorageMappingFinder vmStorageMapper = null;
/*     */     try
/*     */     {
/*  67 */       if (request.isUserInRole("OPERATOR"))
/*     */       {
/*  69 */         vmStorageMapper = new VMStorageMappingFinder(true, true, DBUtil.getOperatorMons(request.getRemoteUser()));
/*     */       }
/*     */       else
/*     */       {
/*  73 */         vmStorageMapper = new VMStorageMappingFinder(true);
/*     */       }
/*  75 */       JSONObject jsonObj = (JSONObject)vmStorageMapper.call();
/*  76 */       if (jsonObj.length() == 0)
/*     */       {
/*  78 */         messages.add("NOMAPPING", new ActionMessage("am.storagemapping.nomapping"));
/*     */       }
/*  80 */       request.setAttribute("StorageMapping", jsonObj);
/*  81 */       AMLog.info("StorageMappingAction-populateStorageMap storage mapping: " + jsonObj);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  85 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   private void populateStorageTable(HttpServletRequest request, ActionMessages messages)
/*     */   {
/*  91 */     VMStorageMappingFinder vmStorageMapper = null;
/*     */     try
/*     */     {
/*  94 */       if (request.isUserInRole("OPERATOR"))
/*     */       {
/*  96 */         vmStorageMapper = new VMStorageMappingFinder(false, true, DBUtil.getOperatorMons(request.getRemoteUser()));
/*     */       }
/*     */       else
/*     */       {
/* 100 */         vmStorageMapper = new VMStorageMappingFinder();
/*     */       }
/* 102 */       List<Properties> storageMapping = (List)vmStorageMapper.call();
/* 103 */       if (storageMapping.isEmpty())
/*     */       {
/* 105 */         messages.add("NOMAPPING", new ActionMessage("am.storagemapping.nomapping"));
/*     */       }
/* 107 */       request.setAttribute("StorageMapping", storageMapping);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 111 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public ActionForward showVMStorageMapping(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 117 */     AMLog.info("StorageMappingAction-showVMStorageMapping resourceid: " + request.getParameter("resourceid") + " moname: " + request.getParameter("moname"));
/* 118 */     ActionForward forward = null;
/* 119 */     boolean noSANMsg = false;
/*     */     try
/*     */     {
/* 122 */       String displayMode = request.getParameter("displayMode");
/* 123 */       String resourceid = request.getParameter("resourceid");
/* 124 */       if (displayMode.equals("Map"))
/*     */       {
/* 126 */         VMStorageMappingFinder vmStorageMapper = new VMStorageMappingFinder(Integer.valueOf(resourceid), displayMode.equals("Map"));
/* 127 */         JSONObject storageMapping = (JSONObject)vmStorageMapper.call();
/* 128 */         request.setAttribute("StorageMapping", storageMapping);
/* 129 */         forward = mapping.findForward("VMPageStorageMap");
/* 130 */         if (storageMapping.length() == 0)
/*     */         {
/* 132 */           noSANMsg = true;
/*     */         }
/*     */         
/*     */       }
/*     */       else
/*     */       {
/* 138 */         VMStorageMappingFinder vmStorageMapper = new VMStorageMappingFinder(Integer.valueOf(resourceid));
/* 139 */         List<Properties> storageMapping = (List)vmStorageMapper.call();
/* 140 */         request.setAttribute("StorageMapping", storageMapping);
/* 141 */         forward = mapping.findForward("VMPageStorageTable");
/* 142 */         if (storageMapping.isEmpty())
/*     */         {
/* 144 */           noSANMsg = true;
/*     */         }
/*     */       }
/* 147 */       request.setAttribute("resourceid", resourceid);
/* 148 */       ExtProdUtil.getInstance(); if (ExtProdUtil.isServiceConfigured(ExtProdUtil.opstor))
/*     */       {
/* 150 */         request.setAttribute("isOpStorConfigured", Boolean.valueOf(true));
/*     */       }
/*     */       else
/*     */       {
/* 154 */         request.setAttribute("isOpStorConfigured", Boolean.valueOf(false));
/*     */       }
/*     */       
/* 157 */       if (noSANMsg)
/*     */       {
/* 159 */         request.setAttribute("NoSanMessage", FormatUtil.getString("am.storagemapping.noexternaldevice.table", new String[] { ExtProdUtil.opstor, OEMUtil.getOEMString("product.name") }));
/*     */       }
/*     */       
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 166 */       e.printStackTrace();
/*     */     }
/* 168 */     return forward;
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
/*     */   public void isOpStorConfigured(HttpServletRequest request, ActionMapping mapping)
/*     */   {
/* 181 */     ActionMessages messages = new ActionMessages();
/* 182 */     ExtProdUtil.getInstance(); if (!ExtProdUtil.isServiceConfigured(ExtProdUtil.opstor))
/*     */     {
/* 184 */       messages.add("NOOPSTOR", new ActionMessage("OpStor is not Configured", new String[] { ExtProdUtil.opstor }));
/* 185 */       request.setAttribute("SAN_NO_OPSTOR", messages);
/*     */     }
/*     */   }
/*     */   
/*     */   public ActionMessages handleNoMapping()
/*     */   {
/* 191 */     ActionMessages messages = new ActionMessages();
/* 192 */     ExtProdUtil.getInstance(); if (!ExtProdUtil.isServiceConfigured(ExtProdUtil.opstor))
/*     */     {
/* 194 */       messages.add("NOOPSTOR", new ActionMessage("OpStor is not Configured", new String[] { ExtProdUtil.opstor }));
/*     */     }
/* 196 */     else if (ExtProdUtil.getInstance().getExternalDeviceCount(ExtProdUtil.opstor) == 0)
/*     */     {
/* 198 */       messages.add("NOEXTDEVICE", new ActionMessage("am.storagemapping.noexternaldevice"));
/*     */     }
/* 200 */     if (VMUtil.getESXCount() == 0)
/*     */     {
/* 202 */       messages.add("NOESXDEVICE", new ActionMessage(FormatUtil.getString("am.storagemapping.noesx.addonrequired")));
/*     */     }
/* 204 */     return messages;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\StorageMappingAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */