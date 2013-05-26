package com.github.gspell4eclipse.engine.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.ui.statushandlers.StatusManager;

import com.github.gspell4eclipse.Activator;
import com.github.gspell4eclipse.engine.ISpellingRegionSelector;


public class RegionSelectors {

  private static RegionSelectors instance = null;

  private Map<IContentType, ISpellingRegionSelector> selectors = new HashMap<IContentType, ISpellingRegionSelector>();

  private RegionSelectors() {

    IConfigurationElement extensions[] = Platform.getExtensionRegistry().getConfigurationElementsFor("foo.spelling.region.selector");
    
    Object regionSelector = null;
    
    for (int i = 0; i < extensions.length; i++) {
      try {
        regionSelector = extensions[i].createExecutableExtension("class");        
      } catch (CoreException e) {
        IStatus status = new Status(Status.WARNING, Activator.PLUGIN_ID,
            Status.OK, "Can't create content type region selector", e);
        StatusManager.getManager().handle(status, StatusManager.LOG);        
      }

      String type = extensions[i].getAttribute("contentType");
      
      IContentType contentType = Platform.getContentTypeManager().getContentType(type);
      
      if(contentType != null) {
        selectors.put(contentType, (ISpellingRegionSelector) regionSelector);
      }
      else {
        IStatus status = new Status(Status.WARNING, Activator.PLUGIN_ID, "No such content type: " + contentType);
        StatusManager.getManager().handle(status, StatusManager.LOG);               
      }
    }
  }

  static RegionSelectors getInstance() {
    if (instance == null) {
      instance = new RegionSelectors();
    }

    return instance;
  }

  /**
   * Get a spelling selector for the specified content type.
   * 
   * @param contentType
   *          The content type.
   * @return The spelling selector or <code>null</code> if no selector could be
   *         found.
   */

  ISpellingRegionSelector getRegionSelector(IContentType contentType) {
    if (selectors.containsKey(contentType)) {
      return selectors.get(contentType);
    }

    return getRegionSelector(contentType.getBaseType());
  }
}
