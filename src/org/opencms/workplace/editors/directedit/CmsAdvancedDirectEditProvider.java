/*
 * This library is part of OpenCms -
 * the Open Source Content Management System
 *
 * Copyright (c) Alkacon Software GmbH (http://www.alkacon.com)
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
 * For further information about Alkacon Software, please see the
 * company website: http://www.alkacon.com
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.opencms.workplace.editors.directedit;

import org.opencms.i18n.CmsEncoder;
import org.opencms.json.JSONException;
import org.opencms.json.JSONObject;
import org.opencms.workplace.editors.Messages;

import java.util.Random;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Provider for the OpenCms AdvancedDirectEdit.<p>
 * 
 * Since OpenCms version 8.0.0.<p>
 * 
 * This provider DOES NOT support {@link CmsDirectEditMode#MANUAL} mode.<p>
 * 
 * @since 8.0.0
 */
public class CmsAdvancedDirectEditProvider extends A_CmsDirectEditProvider {

    /** Indicates the permissions for the last element the was opened. */
    protected int m_lastPermissionMode;

    /** The random number generator used for element ids. */
    private Random m_random = new Random();

    /** True if the elements should be assigned randomly generated ids. */
    protected boolean m_useIds;

    /**
     * Returns the end HTML for a disabled direct edit button.<p>
     * 
     * @return the end HTML for a disabled direct edit button
     */
    public String endDirectEditDisabled() {

        return "";
    }

    /**
     * Returns the end HTML for an enabled direct edit button.<p>
     * 
     * @return the end HTML for an enabled direct edit button
     */
    public String endDirectEditEnabled() {

        return "<div class=\"cms-editable-end\"></div>";
    }

    /**
     * Generates a random element id.<p>
     * 
     * @return a random  element id 
     */
    public synchronized String getRandomId() {

        return "editable_" + Math.abs(m_random.nextLong());
    }

    /**
     * @see org.opencms.workplace.editors.directedit.I_CmsDirectEditProvider#insertDirectEditEnd(javax.servlet.jsp.PageContext)
     */
    public void insertDirectEditEnd(PageContext context) throws JspException {

        String content;
        switch (m_lastPermissionMode) {

            case 1: // disabled
                content = endDirectEditDisabled();
                break;
            case 2: // enabled
                content = endDirectEditEnabled();
                break;
            default: // inactive or undefined
                content = null;
        }
        m_lastPermissionMode = 0;
        print(context, content);
    }

    /**
     * @see org.opencms.workplace.editors.directedit.I_CmsDirectEditProvider#insertDirectEditIncludes(javax.servlet.jsp.PageContext, org.opencms.workplace.editors.directedit.CmsDirectEditParams)
     */
    @SuppressWarnings("unused")
    public void insertDirectEditIncludes(PageContext context, CmsDirectEditParams params) throws JspException {

        // For Advanced Direct Edit all necessary js and css-code is included by the enableADE tag. Further includes in the head are not needed.

    }

    /**
     * @see org.opencms.workplace.editors.directedit.I_CmsDirectEditProvider#insertDirectEditStart(javax.servlet.jsp.PageContext, org.opencms.workplace.editors.directedit.CmsDirectEditParams)
     */
    public boolean insertDirectEditStart(PageContext context, CmsDirectEditParams params) throws JspException {

        String content;
        // check the direct edit permissions of the current user          
        CmsDirectEditResourceInfo resourceInfo = getResourceInfo(params.getResourceName());
        // check the permission mode
        m_lastPermissionMode = resourceInfo.getPermissions().getPermission();
        switch (m_lastPermissionMode) {
            case 1: // disabled
                content = startDirectEditDisabled(params, resourceInfo);
                break;
            case 2: // enabled
                try {
                    content = startDirectEditEnabled(params, resourceInfo);
                } catch (JSONException e) {
                    throw new JspException(e);
                }
                break;
            default: // inactive or undefined
                content = null;
        }
        print(context, content);
        return content != null;
    }

    /**
     * Returns <code>false</code> because the default provider does not support manual button placement.<p>
     * 
     * @see org.opencms.workplace.editors.directedit.I_CmsDirectEditProvider#isManual(org.opencms.workplace.editors.directedit.CmsDirectEditMode)
     */
    @Override
    public boolean isManual(CmsDirectEditMode mode) {

        return false;
    }

    /**
     * @see org.opencms.workplace.editors.directedit.I_CmsDirectEditProvider#newInstance()
     */
    public I_CmsDirectEditProvider newInstance() {

        CmsAdvancedDirectEditProvider result = new CmsAdvancedDirectEditProvider();
        result.m_configurationParameters = m_configurationParameters;
        return result;
    }

    /**
     * Returns the start HTML for a disabled direct edit button.<p>
     * 
     * @param params the direct edit parameters
     * @param resourceInfo contains information about the resource to edit
     * 
     * @return the start HTML for a disabled direct edit button
     */
    public String startDirectEditDisabled(CmsDirectEditParams params, CmsDirectEditResourceInfo resourceInfo) {

        StringBuffer result = new StringBuffer(256);

        result.append("<!-- EDIT BLOCK START (DISABLED): ");
        result.append(params.m_resourceName);
        result.append(" [");
        result.append(resourceInfo.getResource().getState());
        result.append("] ");
        if (!resourceInfo.getLock().isUnlocked()) {
            result.append(" locked ");
            result.append(resourceInfo.getLock().getProject().getName());
        }
        result.append(" -->\n");
        return result.toString();
    }

    /**
     * Returns the start HTML for an enabled direct edit button.<p>
     *
     * @param params the direct edit parameters
     * @param resourceInfo contains information about the resource to edit
     * 
     * @return the start HTML for an enabled direct edit button
     * @throws JSONException 
     */
    public String startDirectEditEnabled(CmsDirectEditParams params, CmsDirectEditResourceInfo resourceInfo)
    throws JSONException {

        String editLocale = m_cms.getRequestContext().getLocale().toString();
        String editId = getNextDirectEditId();
        String editNewLink = CmsEncoder.encode(params.getLinkForNew());
        // putting together all needed data
        JSONObject editableData = new JSONObject();
        editableData.put("editId", editId);
        editableData.put("structureId", resourceInfo.getResource().getStructureId());
        editableData.put("sitePath", params.getResourceName());
        editableData.put("elementlanguage", editLocale);
        editableData.put("elementname", params.getElement());
        editableData.put("newlink", editNewLink);
        editableData.put("hasEdit", params.getButtonSelection().isShowEdit());
        editableData.put("hasDelete", params.getButtonSelection().isShowDelete());
        editableData.put("hasNew", params.getButtonSelection().isShowNew());
        editableData.put("newtitle", m_messages.key(Messages.GUI_EDITOR_TITLE_NEW_0));

        StringBuffer result = new StringBuffer(512);
        if (m_useIds) {
            result.append("<div id=\"" + getRandomId() + "\" class='cms-editable' rel='").append(
                editableData.toString()).append("'></div>");
        } else {
            result.append("<div class='cms-editable' rel='").append(editableData.toString()).append("'></div>");
        }

        return result.toString();
    }
}