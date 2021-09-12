/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.framework.credentialManager.CredentialManagerUtil;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.security.authorization.Coding;
/*     */ import com.manageengine.appmanager.util.DelegatedUserRoleUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.DynaActionForm;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ import org.htmlparser.util.Translate;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CredentialManagerAction
/*     */   extends DispatchAction
/*     */ {
/*  37 */   String isWindows = System.getProperty("os.name").toLowerCase().indexOf("windows") != -1 ? "true" : "false";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  44 */   CredentialManagerUtil credUtil = CredentialManagerUtil.getInstance();
/*     */   
/*     */   public void CredentialManagerAction() {}
/*     */   
/*  48 */   public ActionForward credentialNameExistsCheck(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) { String enteredCredName = request.getParameter("enteredName");
/*  49 */     int userID = DelegatedUserRoleUtil.getLoginUserid(request);
/*  50 */     List credNameList = this.credUtil.getCredentialNames(userID);
/*     */     
/*  52 */     String isPresent = credNameList.contains(enteredCredName) ? "true" : "false";
/*     */     try
/*     */     {
/*  55 */       response.setContentType("text/html; charset=UTF-8");
/*  56 */       PrintWriter out = response.getWriter();
/*  57 */       out.println(isPresent.trim());
/*  58 */       out.flush();
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*  62 */       ex.printStackTrace();
/*     */     }
/*  64 */     return null;
/*     */   }
/*     */   
/*     */   public ActionForward showMonitorsForCredential(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  69 */     String forwardPage = "monitorsToCredentialPage";
/*  70 */     String credentialID = request.getParameter("credentialID");
/*  71 */     List monitorsList = this.credUtil.getMonitorsAssociatedToCredentialID(credentialID);
/*  72 */     request.setAttribute("list", monitorsList);
/*  73 */     Properties credProps = this.credUtil.getDetailsForACredential(credentialID);
/*  74 */     request.setAttribute("credentialName", credProps.getProperty("credentialName", ""));
/*  75 */     request.setAttribute("credentialType", credProps.getProperty("credentialType", ""));
/*  76 */     request.setAttribute("credentialID", credentialID);
/*  77 */     request.setAttribute("credentialTypeToDisplay", credProps.getProperty("credentialTypeToDisplay"));
/*  78 */     return mapping.findForward(forwardPage);
/*     */   }
/*     */   
/*     */ 
/*     */   public ActionForward showMonitorsForSelectedCredential(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  84 */     response.setContentType("text/json; charset=utf-8");
/*  85 */     String credentialID = request.getParameter("credentialID");
/*  86 */     List monitorsList = this.credUtil.getMonitorsAssociatedToCredentialID(credentialID);
/*  87 */     JSONArray jsonArray = getListOfDevicesAsJSONArray(monitorsList, null);
/*     */     try
/*     */     {
/*  90 */       response.setContentType("text/json;charset=UTF-8");
/*  91 */       PrintWriter out = response.getWriter();
/*  92 */       out.println(jsonArray);
/*  93 */       out.flush();
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*  97 */       ex.printStackTrace();
/*     */     }
/*  99 */     return null;
/*     */   }
/*     */   
/*     */   public ActionForward showAllMonitors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 104 */     response.setContentType("text/json;charset=UTF-8");
/* 105 */     String credentialID = request.getParameter("credentialID");
/* 106 */     List monitorsList = this.credUtil.getAllMonitorsOfThisType(credentialID);
/* 107 */     List associatedList = this.credUtil.getListAssociatedToCredential(credentialID);
/* 108 */     JSONArray jsonArray = getListOfDevicesAsJSONArray(monitorsList, associatedList);
/*     */     try
/*     */     {
/* 111 */       response.setContentType("text/json;charset=UTF-8");
/* 112 */       PrintWriter out = response.getWriter();
/* 113 */       out.println(jsonArray);
/* 114 */       out.flush();
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 118 */       ex.printStackTrace();
/*     */     }
/* 120 */     return null;
/*     */   }
/*     */   
/*     */   public ActionForward associateCredentialToResource(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 125 */     String resourceID = request.getParameter("resourceID");
/* 126 */     long credentialID = new Long(request.getParameter("credentialID")).longValue();
/* 127 */     ArrayList resourceIDList = new ArrayList();
/* 128 */     resourceIDList.add(Integer.valueOf(Integer.parseInt(resourceID)));
/* 129 */     String credentialType = this.credUtil.getCredentialTypeFromCredentialID(credentialID + "");
/* 130 */     boolean associationPerformed = this.credUtil.updateResourceIDsAsPerCredentials(null, resourceIDList, credentialID, credentialType);
/* 131 */     if (associationPerformed)
/*     */     {
/* 133 */       this.credUtil.addToCredentialToResourceMap(credentialID, Integer.parseInt(resourceID));
/*     */     }
/* 135 */     String toSend = associationPerformed ? "true" : "false";
/*     */     try
/*     */     {
/* 138 */       response.setContentType("text/json charset=UTF-8");
/* 139 */       PrintWriter out = response.getWriter();
/* 140 */       out.write(toSend);
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 144 */       ex.printStackTrace();
/*     */     }
/* 146 */     return null;
/*     */   }
/*     */   
/*     */   public ActionForward removeAssociationFromMonitors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 151 */     long credentialID = new Long(request.getParameter("credentialID")).longValue();
/* 152 */     String monitorList = request.getParameter("monitorsInArray");
/* 153 */     List<String> monitorsList = Arrays.asList(monitorList.split("\\s*,\\s*"));
/* 154 */     JSONArray jsonArray = new JSONArray();
/* 155 */     ArrayList monArrayList = new ArrayList();
/* 156 */     for (int i = 0; i < monitorsList.size(); i++)
/*     */     {
/* 158 */       JSONObject jsonObject = new JSONObject();
/*     */       try
/*     */       {
/* 161 */         String resourceID = (String)monitorsList.get(i);
/* 162 */         jsonObject.put("resourceID", resourceID);
/* 163 */         boolean result = this.credUtil.removeFromMapping(resourceID);
/* 164 */         jsonObject.put("result", String.valueOf(result));
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/* 168 */         ex.printStackTrace();
/*     */       }
/* 170 */       jsonArray.put(jsonObject);
/*     */     }
/*     */     try
/*     */     {
/* 174 */       response.setContentType("text/json charset=UTF-8");
/* 175 */       PrintWriter out = response.getWriter();
/* 176 */       out.println(jsonArray);
/* 177 */       out.flush();
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 181 */       ex.printStackTrace();
/*     */     }
/* 183 */     return null;
/*     */   }
/*     */   
/*     */   public ActionForward associateCredentialToManyResources(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 188 */     long credentialID = new Long(request.getParameter("credentialID")).longValue();
/* 189 */     String monitorList = request.getParameter("monitorsInArray");
/* 190 */     List<String> monitorsList = Arrays.asList(monitorList.split("\\s*,\\s*"));
/* 191 */     ArrayList monArrayList = new ArrayList();
/* 192 */     for (int i = 0; i < monitorsList.size(); i++)
/*     */     {
/* 194 */       monArrayList.add(Integer.valueOf(Integer.parseInt((String)monitorsList.get(i))));
/*     */     }
/* 196 */     String credentialType = this.credUtil.getCredentialTypeFromCredentialID(credentialID + "");
/* 197 */     boolean associationPerformed = this.credUtil.updateResourceIDsAsPerCredentials(null, monArrayList, credentialID, credentialType);
/* 198 */     JSONArray monitorArray = new JSONArray();
/* 199 */     if (associationPerformed)
/*     */     {
/* 201 */       for (int i = 0; i < monArrayList.size(); i++)
/*     */       {
/* 203 */         JSONObject jsonObject = new JSONObject();
/* 204 */         int resourceID = ((Integer)monArrayList.get(i)).intValue();
/* 205 */         boolean associatedResult = this.credUtil.performAddOrUpdate(credentialID, resourceID);
/*     */         try
/*     */         {
/* 208 */           jsonObject.put("resourceID", resourceID);
/* 209 */           jsonObject.put("result", String.valueOf(associatedResult));
/*     */         }
/*     */         catch (Exception ex)
/*     */         {
/* 213 */           ex.printStackTrace();
/*     */         }
/* 215 */         monitorArray.put(jsonObject);
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/* 220 */       response.setContentType("text/json charset=UTF-8");
/* 221 */       PrintWriter out = response.getWriter();
/* 222 */       out.println(monitorArray);
/* 223 */       out.flush();
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 227 */       ex.printStackTrace();
/*     */     }
/* 229 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public ActionForward changeToReadableFormat(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 235 */     String credentialID = request.getParameter("credentialID");
/* 236 */     String rowID = request.getParameter("rowID");
/* 237 */     Properties credProps = this.credUtil.rowNameVsValue(Long.parseLong(credentialID));
/* 238 */     String requiredCredValue = credProps.getProperty(rowID, "");
/*     */     try
/*     */     {
/* 241 */       response.setContentType("text/json charset=UTF-8");
/* 242 */       PrintWriter out = response.getWriter();
/* 243 */       out.write(requiredCredValue);
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 247 */       ex.printStackTrace();
/*     */     }
/* 249 */     return null;
/*     */   }
/*     */   
/*     */   public ActionForward removeAssociationToCredentialID(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 254 */     String resourceID = request.getParameter("resourceID");
/* 255 */     boolean removeFromMapping = this.credUtil.removeFromMapping(resourceID);
/* 256 */     String toSend = removeFromMapping ? "true" : "false";
/*     */     try
/*     */     {
/* 259 */       response.setContentType("text/json charset=UTF-8");
/* 260 */       PrintWriter out = response.getWriter();
/* 261 */       out.write(toSend);
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 265 */       ex.printStackTrace();
/*     */     }
/* 267 */     return null;
/*     */   }
/*     */   
/*     */   private JSONArray getListOfDevicesAsJSONArray(List monitorsList, List deviceAssociatedList)
/*     */   {
/* 272 */     JSONArray jsonArray = new JSONArray();
/* 273 */     for (int i = 0; i < monitorsList.size(); i++)
/*     */     {
/* 275 */       JSONObject jsonObject = new JSONObject();
/* 276 */       Properties prop = (Properties)monitorsList.get(i);
/*     */       try
/*     */       {
/* 279 */         int resourceID = new Integer(prop.getProperty("resourceID")).intValue();
/* 280 */         jsonObject.put("resourceID", prop.getProperty("resourceID", "-1"));
/* 281 */         jsonObject.put("resourceName", prop.getProperty("resourceName", ""));
/* 282 */         jsonObject.put("displayName", prop.getProperty("displayName", ""));
/* 283 */         jsonObject.put("associated", true);
/* 284 */         if ((deviceAssociatedList != null) && (!deviceAssociatedList.contains(Integer.valueOf(resourceID))))
/*     */         {
/* 286 */           jsonObject.put("associated", false);
/*     */         }
/*     */         
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/* 292 */         ex.printStackTrace();
/*     */       }
/* 294 */       jsonArray.put(jsonObject);
/*     */     }
/* 296 */     return jsonArray;
/*     */   }
/*     */   
/*     */   public ActionForward showCredentialManager(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 301 */     String forwardPage = "credentialManagerPage";
/* 302 */     int userID = DelegatedUserRoleUtil.getLoginUserid(request);
/* 303 */     boolean isPrivileged = Constants.isPrivilegedUser(request);
/* 304 */     List credentialList = this.credUtil.getCredentialList(userID, isPrivileged);
/* 305 */     AMLog.debug("CredentialManagerAction :: showCredentialManager : Final credentialList to be displayed in the CredentialManager : " + credentialList);
/* 306 */     request.setAttribute("list", credentialList);
/* 307 */     request.setAttribute("HelpKey", "CredentialManager");
/* 308 */     request.setAttribute("isAdmin", Boolean.valueOf(EnterpriseUtil.isAdminServer()));
/* 309 */     if ("true".equals(request.getParameter("reqForAdminLayout")))
/*     */     {
/* 311 */       forwardPage = "NewCredentialManagerPage";
/*     */     }
/* 313 */     return mapping.findForward(forwardPage);
/*     */   }
/*     */   
/*     */   public ActionForward showCredentialByTypeToEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 318 */     DynaActionForm cmForm = (DynaActionForm)form;
/* 319 */     String forwardPage = "credentialTypePage";
/* 320 */     String credID = request.getParameter("credentialID");
/*     */     
/*     */ 
/*     */ 
/* 324 */     String credName = this.credUtil.getCredentialNameForCredID(credID);
/*     */     
/* 326 */     String typeName = request.getParameter("credentialType");
/* 327 */     List credentialValuesInList = new ArrayList();
/* 328 */     cmForm.set("type", typeName);
/*     */     
/* 330 */     JSONArray jsonArray = this.credUtil.getJSONArrayFromCredentialID(Long.parseLong(credID));
/* 331 */     credentialValuesInList = this.credUtil.parseCredentialValuesColumn(jsonArray);
/* 332 */     request.setAttribute("isWindows", this.isWindows);
/* 333 */     request.setAttribute("credentialValuesInList", credentialValuesInList);
/* 334 */     request.setAttribute("buttonToPerform", "Update");
/* 335 */     request.setAttribute("credentialID", credID);
/* 336 */     request.setAttribute("credentialName", credName);
/* 337 */     request.setAttribute("showWMIMonitors", EnterpriseUtil.isAdminServer() ? Boolean.valueOf(true) : this.isWindows);
/* 338 */     return mapping.findForward(forwardPage);
/*     */   }
/*     */   
/*     */   public ActionForward showCredentialByTypeName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 343 */     DynaActionForm cmForm = (DynaActionForm)form;
/* 344 */     String forwardPage = "credentialTypePage";
/* 345 */     String typeName = request.getParameter("type");
/* 346 */     String fromDiscovery = request.getParameter("fromDiscovery");
/* 347 */     typeName = typeName != null ? typeName : "MYSQLDB:3306";
/*     */     
/* 349 */     cmForm.set("type", typeName);
/* 350 */     JSONArray parentJSONArray = this.credUtil.getCredentialAsJSONArrayByType(typeName);
/* 351 */     List credentialValuesInList = this.credUtil.parseCredentialValuesColumn(parentJSONArray);
/* 352 */     request.setAttribute("credentialValuesInList", credentialValuesInList);
/* 353 */     request.setAttribute("credentialName", "");
/* 354 */     request.setAttribute("buttonToPerform", "Save");
/* 355 */     request.setAttribute("credentialID", "-1");
/* 356 */     request.setAttribute("isWindows", this.isWindows);
/* 357 */     request.setAttribute("fromDiscovery", fromDiscovery);
/* 358 */     request.setAttribute("showWMIMonitors", EnterpriseUtil.isAdminServer() ? Boolean.valueOf(true) : this.isWindows);
/*     */     
/* 360 */     return mapping.findForward(forwardPage);
/*     */   }
/*     */   
/*     */   public ActionForward saveCredentials(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 365 */     DynaActionForm cmForm = (DynaActionForm)form;
/* 366 */     HashMap credentialMapToAdd = new HashMap();
/*     */     
/* 368 */     String forwardPage = "credentialManagerPage";
/* 369 */     String forwardPageCredentialTypePage = "credentialTypePage";
/* 370 */     String credType = request.getParameter("type");
/*     */     
/* 372 */     String credName = (String)cmForm.get("credentialName");
/* 373 */     String credentialID = request.getParameter("credentialID");
/* 374 */     String fromDiscovery = request.getParameter("fromDiscovery");
/* 375 */     JSONArray jsonArrayToAdd = this.credUtil.getJSONArrayToAdd(request, credType);
/* 376 */     credentialMapToAdd.put("credentialName", credName);
/* 377 */     credentialMapToAdd.put("credentialType", credType);
/* 378 */     credentialMapToAdd.put("credentialDescr", "Test");
/* 379 */     credentialMapToAdd.put("credentialDetails", jsonArrayToAdd);
/* 380 */     credentialMapToAdd.put("credentialID", credentialID);
/* 381 */     boolean addUpdate = true;
/*     */     
/* 383 */     if (new Long(credentialID).longValue() == -1L)
/*     */     {
/* 385 */       int userID = DelegatedUserRoleUtil.getLoginUserid(request);
/* 386 */       Long credID = this.credUtil.addCredential(userID, credentialMapToAdd);
/* 387 */       credentialID = Long.toString(credID.longValue());
/* 388 */       credentialMapToAdd.put("credentialID", credID.toString());
/*     */       try
/*     */       {
/* 391 */         if (EnterpriseUtil.isAdminServer())
/*     */         {
/* 393 */           addUpdate = CredentialManagerUtil.addCredentialTasktoSync(credentialMapToAdd, "add", "all");
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 398 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 403 */       addUpdate = this.credUtil.updateACredential(request, credentialMapToAdd);
/*     */       try
/*     */       {
/* 406 */         if (EnterpriseUtil.isAdminServer())
/*     */         {
/* 408 */           addUpdate = CredentialManagerUtil.addCredentialTasktoSync(credentialMapToAdd, "update");
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 413 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/* 417 */     request.setAttribute("fromSaveOperation", "true");
/* 418 */     if (fromDiscovery.equalsIgnoreCase("true"))
/*     */     {
/*     */ 
/*     */       try
/*     */       {
/* 423 */         response.setContentType("text/json charset=UTF-8");
/* 424 */         PrintWriter out = response.getWriter();
/* 425 */         JSONObject jsonCredId = new JSONObject();
/* 426 */         jsonCredId.put("credentialID", credentialID);
/* 427 */         out.println(jsonCredId);
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/* 431 */         ex.printStackTrace();
/*     */       }
/* 433 */       return null;
/*     */     }
/* 435 */     return new ActionForward("/credentialManager.do?method=showCredentialByTypeName&operation=add&saved=true");
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
/*     */   public ActionForward deleteCredential(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 449 */     long credentialID = -1L;
/* 450 */     boolean confirmDelete = true;
/* 451 */     String credID = request.getParameter("credentialID");
/* 452 */     if (credID != null)
/*     */     {
/* 454 */       credentialID = new Long(credID).longValue();
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 459 */       confirmDelete = this.credUtil.deleteCredential(credentialID);
/* 460 */       response.setContentType("text/html; charset=UTF-8");
/* 461 */       PrintWriter out = response.getWriter();
/* 462 */       String result = confirmDelete ? "true" : "false";
/* 463 */       out.write(result);
/* 464 */       out.flush();
/*     */       try
/*     */       {
/* 467 */         if (EnterpriseUtil.isAdminServer())
/*     */         {
/*     */           try
/*     */           {
/* 471 */             HashMap credentialIDToDelete = new HashMap();
/* 472 */             credentialIDToDelete.put("credentialID", credID);
/* 473 */             confirmDelete = CredentialManagerUtil.addCredentialTasktoSync(credentialIDToDelete, "delete");
/*     */           }
/*     */           catch (Exception ee)
/*     */           {
/* 477 */             ee.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 483 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 488 */       ex.printStackTrace();
/*     */     }
/*     */     
/* 491 */     return null;
/*     */   }
/*     */   
/*     */   private void createSSHFileInCredentialName(HttpServletRequest request, String credType)
/*     */   {
/*     */     try
/*     */     {
/* 498 */       String credID = request.getParameter("credentialID");
/* 499 */       String credName = "";
/* 500 */       if (new Long(credID).longValue() == -1L)
/*     */       {
/* 502 */         credName = request.getParameter("credentialName");
/*     */       }
/*     */       else
/*     */       {
/* 506 */         Properties credProps = this.credUtil.getDetailsForACredential(credID);
/* 507 */         credName = credProps.getProperty("credentialName");
/*     */       }
/*     */       
/* 510 */       String nmsHome = System.getProperty("webnms.rootdir");
/* 511 */       String fileName1 = nmsHome + File.separator + "credentialManager_privateKey_" + credName + ".txt";
/* 512 */       FileWriter fw = null;
/*     */       try
/*     */       {
/* 515 */         if ((request.getParameter("sshPKAuth") != null) && (request.getParameter("sshPKAuth").equalsIgnoreCase("on")))
/*     */         {
/* 517 */           String privateKey = request.getParameter("description");
/* 518 */           File sshFile = new File(fileName1);
/* 519 */           if (sshFile.exists())
/*     */           {
/* 521 */             sshFile.delete();
/*     */           }
/* 523 */           fw = new FileWriter(sshFile);
/* 524 */           fw.write(privateKey);
/*     */         }
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/* 529 */         ex.printStackTrace();
/*     */       }
/*     */       finally
/*     */       {
/*     */         try
/*     */         {
/* 535 */           if (fw != null)
/*     */           {
/* 537 */             fw.close();
/*     */           }
/*     */         }
/*     */         catch (Exception ex) {}
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 551 */       return;
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 548 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private JSONArray getJSONArrayToAdd(HttpServletRequest request, String credType)
/*     */   {
/* 555 */     if ("ssh".equalsIgnoreCase(credType))
/*     */     {
/* 557 */       createSSHFileInCredentialName(request, credType);
/*     */     }
/*     */     
/* 560 */     JSONArray jsonArrayForType = new JSONArray();
/*     */     try
/*     */     {
/* 563 */       jsonArrayForType = this.credUtil.getCredentialAsJSONArrayByType(credType);
/* 564 */       ArrayList<String> toHideList = new ArrayList();
/* 565 */       ArrayList<String> toShowList = new ArrayList();
/*     */       
/* 567 */       for (int i = 0; i < jsonArrayForType.length(); i++)
/*     */       {
/* 569 */         JSONObject jsonObject = jsonArrayForType.getJSONObject(i);
/* 570 */         String rowType = jsonObject.getString("rowType");
/* 571 */         if (("text".equalsIgnoreCase(rowType)) || ("password".equalsIgnoreCase(rowType)) || ("textarea".equalsIgnoreCase(rowType)))
/*     */         {
/* 573 */           String rowName = jsonObject.getString("rowName");
/* 574 */           if (toHideList.contains(rowName))
/*     */           {
/* 576 */             jsonObject.put("display", "display:none");
/*     */           }
/* 578 */           if (toShowList.contains(rowName))
/*     */           {
/* 580 */             jsonObject.put("display", "display:table-row");
/*     */           }
/* 582 */           String valueToShowFromRequest = request.getParameter(rowName);
/* 583 */           if ("textarea".equalsIgnoreCase(rowType))
/*     */           {
/* 585 */             valueToShowFromRequest = "";
/*     */           }
/* 587 */           valueToShowFromRequest = valueToShowFromRequest != null ? valueToShowFromRequest.trim() : "";
/* 588 */           String hiddenValue = request.getParameter("hidden_" + rowName);
/* 589 */           if ((hiddenValue != null) && (hiddenValue.equalsIgnoreCase("decoded")))
/*     */           {
/* 591 */             valueToShowFromRequest = Coding.convertToNewBase(valueToShowFromRequest);
/*     */           }
/*     */           else
/*     */           {
/* 595 */             valueToShowFromRequest = Translate.decode(valueToShowFromRequest);
/*     */           }
/*     */           
/* 598 */           jsonObject.put("valueToShow", valueToShowFromRequest);
/*     */         }
/* 600 */         if ("radio".equalsIgnoreCase(rowType))
/*     */         {
/* 602 */           String rowName = jsonObject.getString("rowName");
/* 603 */           String valueToShowFromRequest = request.getParameter(rowName);
/* 604 */           valueToShowFromRequest = valueToShowFromRequest != null ? valueToShowFromRequest : "";
/* 605 */           jsonObject.put("radioSelected", valueToShowFromRequest);
/*     */         }
/*     */         
/* 608 */         if ("dropdown".equalsIgnoreCase(rowType))
/*     */         {
/* 610 */           String rowName = jsonObject.getString("rowName");
/*     */           
/* 612 */           String valueToShowFromRequest = request.getParameter(rowName);
/* 613 */           valueToShowFromRequest = valueToShowFromRequest != null ? valueToShowFromRequest : "";
/* 614 */           jsonObject.put("dropDownSelected", valueToShowFromRequest);
/*     */         }
/* 616 */         if ("checkbox".equalsIgnoreCase(rowType))
/*     */         {
/* 618 */           String rowName = jsonObject.getString("rowName");
/*     */           
/* 620 */           String valueToShowFromRequest = request.getParameter(rowName);
/*     */           
/* 622 */           valueToShowFromRequest = valueToShowFromRequest != null ? "checked" : "";
/* 623 */           jsonObject.put("checked", valueToShowFromRequest);
/* 624 */           String argsToShow = jsonObject.getString("argsToShow");
/* 625 */           String argsToHide = jsonObject.getString("argsToHide");
/* 626 */           if (!argsToShow.equalsIgnoreCase(""))
/*     */           {
/* 628 */             String[] toShowStrings = argsToShow.split(",");
/* 629 */             if (!valueToShowFromRequest.equalsIgnoreCase("checked"))
/*     */             {
/* 631 */               for (int k = 0; k < toShowStrings.length; k++)
/*     */               {
/* 633 */                 toHideList.add(toShowStrings[k].trim());
/*     */               }
/*     */               
/*     */             }
/*     */             else {
/* 638 */               for (int k = 0; k < toShowStrings.length; k++)
/*     */               {
/* 640 */                 toShowList.add(toShowStrings[k].trim());
/*     */               }
/*     */               
/*     */             }
/*     */             
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 651 */       ex.printStackTrace();
/*     */     }
/* 653 */     return jsonArrayForType;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\CredentialManagerAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */