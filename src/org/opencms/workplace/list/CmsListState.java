/*
 * File   : $Source: /alkacon/cvs/opencms/src/org/opencms/workplace/list/CmsListState.java,v $
 * Date   : $Date: 2006/03/27 14:52:27 $
 * Version: $Revision: 1.7 $
 *
 * This library is part of OpenCms -
 * the Open Source Content Mananagement System
 *
 * Copyright (c) 2005 Alkacon Software GmbH (http://www.alkacon.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about Alkacon Software GmbH, please see the
 * company website: http://www.alkacon.com
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.opencms.workplace.list;

/**
 * Class for storing the state of a list.<p>
 * 
 * A list state includes:<br>
 * <ul>
 * <li>The current sorted Column</li>
 * <li>The sorted column Order</li>
 * <li>The current displayed Page</li>
 * <li>The current search Filter</li>
 * </ul>
 * <p>
 * 
 * @author Michael Moossen 
 * 
 * @version $Revision: 1.7 $ 
 * 
 * @since 6.0.0 
 */
public class CmsListState {

    /** Current sorted column. */
    private final String m_column;
    /** Current search filter. */
    private final String m_filter;
    /** Current sort order. */
    private final CmsListOrderEnum m_order;
    /** Current visible page. */
    private final int m_page;

    /**
     * Default Constructor.<p>
     * 
     * @param list the list to read the state from
     */
    public CmsListState(CmsHtmlList list) {

        m_column = list.getSortedColumn();
        m_filter = list.getSearchFilter();
        m_page = list.getCurrentPage();
        m_order = list.getCurrentSortOrder();
    }

    /**
     * Returns the column.<p>
     *
     * @return the column
     */
    public String getColumn() {

        return m_column;
    }

    /**
     * Returns the filter.<p>
     *
     * @return the filter
     */
    public String getFilter() {

        return m_filter;
    }

    /**
     * Returns the order.<p>
     *
     * @return the order
     */
    public CmsListOrderEnum getOrder() {

        return m_order;
    }

    /**
     * Returns the page.<p>
     *
     * @return the page
     */
    public int getPage() {

        return m_page;
    }

}
