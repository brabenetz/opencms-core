/*
* File   : $Source: /alkacon/cvs/opencms/src/com/opencms/template/cache/Attic/CmsElementCache.java,v $
* Date   : $Date: 2001/05/28 15:01:55 $
* Version: $Revision: 1.3 $
*
* Copyright (C) 2000  The OpenCms Group
*
* This File is part of OpenCms -
* the Open Source Content Mananagement System
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* For further information about OpenCms, please see the
* OpenCms Website: http://www.opencms.com
*
* You should have received a copy of the GNU General Public License
* long with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/
package com.opencms.template.cache;

import java.util.*;
import java.io.*;
import com.opencms.core.*;
import com.opencms.file.*;

/**
 * This is the starting class for OpenCms element cache. Element cache was implemented for
 * performance issues. The idea is to create a flat hirarchie of elements that
 * can be accessed fast and efficient for the frontend users in the online
 * project.
 *
 * On publishing-time the data in the element cache area will be created or updated.
 * All inefficiant XML-files are changed to the efficient element cache data
 * structure. For createing the content no XML-parsing and DOM-accessing is
 * neccessairy.
 * @author: Andreas Schouten
 */
public class CmsElementCache {

    private CmsUriLocator m_uriLocator;

    private CmsElementLocator m_elementLocator;

    public CmsElementCache(){
        m_uriLocator = new CmsUriLocator(10000);
        m_elementLocator = new CmsElementLocator(50000);
    }

    public CmsElementCache(int uriCachesize, int elementCachesize) {
        m_uriLocator = new CmsUriLocator(uriCachesize);
        m_elementLocator = new CmsElementLocator(elementCachesize);
    }

    public CmsUriLocator getUriLocator() {
        return m_uriLocator;
    }

    public CmsElementLocator getElementLocator() {
        return m_elementLocator;
    }

    public void cleanupCache(Vector changedResources){
        m_uriLocator.deleteUris(changedResources);
        m_elementLocator.deleteElementsByTemplateNames(changedResources);
    }

    public byte[] callCanonicalRoot(CmsObject cms, Hashtable parameters) throws CmsException {
        CmsUri uri = m_uriLocator.get(new CmsUriDescriptor(cms.getRequestContext().getUri()));
        return uri.callCanonicalRoot(this, cms, parameters);
    }

}