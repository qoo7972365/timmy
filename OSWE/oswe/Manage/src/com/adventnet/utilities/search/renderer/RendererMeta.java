package com.adventnet.utilities.search.renderer;

import com.adventnet.utilities.search.SearchResult;

public abstract interface RendererMeta
{
  public abstract TableStructure render(SearchResult paramSearchResult)
    throws Exception;
}


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\utilities\search\renderer\RendererMeta.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */