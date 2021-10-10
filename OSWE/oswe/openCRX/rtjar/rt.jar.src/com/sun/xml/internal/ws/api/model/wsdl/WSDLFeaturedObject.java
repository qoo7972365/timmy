package com.sun.xml.internal.ws.api.model.wsdl;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import com.sun.xml.internal.ws.api.WSFeatureList;
import javax.xml.ws.WebServiceFeature;

public interface WSDLFeaturedObject extends WSDLObject {
  @Nullable
  <F extends WebServiceFeature> F getFeature(@NotNull Class<F> paramClass);
  
  @NotNull
  WSFeatureList getFeatures();
  
  void addFeature(@NotNull WebServiceFeature paramWebServiceFeature);
}


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/model/wsdl/WSDLFeaturedObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */