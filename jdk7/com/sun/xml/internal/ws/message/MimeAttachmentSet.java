/*
 * Copyright (c) 1997, 2010, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package com.sun.xml.internal.ws.message;

import com.sun.xml.internal.ws.api.message.AttachmentSet;
import com.sun.xml.internal.ws.api.message.Attachment;
import com.sun.xml.internal.ws.encoding.MimeMultipartParser;
import com.sun.xml.internal.ws.resources.EncodingMessages;
import com.sun.istack.internal.Nullable;

import javax.xml.ws.WebServiceException;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;

/**
 * {@link AttachmentSet} backed by {@link com.sun.xml.internal.ws.encoding.MimeMultipartParser}
 *
 * @author Vivek Pandey
 */
public final class MimeAttachmentSet implements AttachmentSet {
    private final MimeMultipartParser mpp;
    private Map<String, Attachment> atts = new HashMap<String, Attachment>();


    public MimeAttachmentSet(MimeMultipartParser mpp) {
        this.mpp = mpp;
    }

    @Nullable
    public Attachment get(String contentId) {
        Attachment att;
        /**
         * First try to get the Attachment from internal map, maybe this attachment
         * is added by the user.
         */
        att = atts.get(contentId);
        if(att != null)
            return att;
        try {
            /**
             * Attachment is not found in the internal map, now do look in
             * the mpp, if found add to the internal Attachment map.
             */
            att = mpp.getAttachmentPart(contentId);
            if(att != null){
                atts.put(contentId, att);
            }
        } catch (IOException e) {
            throw new WebServiceException(EncodingMessages.NO_SUCH_CONTENT_ID(contentId), e);
        }
        return att;
    }

    /**
     * This is expensive operation, its going to to read all the underlying
     * attachments in {@link MimeMultipartParser}.
     */
    public boolean isEmpty() {
        return atts.size() <= 0 && mpp.getAttachmentParts().isEmpty();
    }

    public void add(Attachment att) {
        atts.put(att.getContentId(), att);
    }

    /**
     * Expensive operation.
     */
    public Iterator<Attachment> iterator() {
        /**
         * Browse thru all the attachments in the mpp, add them to #atts,
         * then return whether its empty.
         */
        Map<String, Attachment> attachments = mpp.getAttachmentParts();
        for(Map.Entry<String, Attachment> att : attachments.entrySet()) {
            if(atts.get(att.getKey()) == null){
                atts.put(att.getKey(), att.getValue());
            }
        }

        return atts.values().iterator();
    }
}
