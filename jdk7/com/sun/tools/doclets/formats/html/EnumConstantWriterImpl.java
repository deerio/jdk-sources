/*
 * Copyright (c) 2003, 2010, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.tools.doclets.formats.html;

import java.io.*;

import com.sun.javadoc.*;
import com.sun.tools.doclets.formats.html.markup.*;
import com.sun.tools.doclets.internal.toolkit.*;
import com.sun.tools.doclets.internal.toolkit.util.*;

/**
 * Writes enum constant documentation in HTML format.
 *
 * @author Jamie Ho
 * @author Bhavesh Patel (Modified)
 */
public class EnumConstantWriterImpl extends AbstractMemberWriter
    implements EnumConstantWriter, MemberSummaryWriter {

    public EnumConstantWriterImpl(SubWriterHolderWriter writer,
        ClassDoc classdoc) {
        super(writer, classdoc);
    }

    public EnumConstantWriterImpl(SubWriterHolderWriter writer) {
        super(writer);
    }

    /**
     * {@inheritDoc}
     */
    public Content getMemberSummaryHeader(ClassDoc classDoc,
            Content memberSummaryTree) {
        memberSummaryTree.addContent(HtmlConstants.START_OF_ENUM_CONSTANT_SUMMARY);
        Content memberTree = writer.getMemberTreeHeader();
        writer.addSummaryHeader(this, classDoc, memberTree);
        return memberTree;
    }

    /**
     * {@inheritDoc}
     */
    public Content getEnumConstantsDetailsTreeHeader(ClassDoc classDoc,
            Content memberDetailsTree) {
        memberDetailsTree.addContent(HtmlConstants.START_OF_ENUM_CONSTANT_DETAILS);
        Content enumConstantsDetailsTree = writer.getMemberTreeHeader();
        enumConstantsDetailsTree.addContent(writer.getMarkerAnchor("enum_constant_detail"));
        Content heading = HtmlTree.HEADING(HtmlConstants.DETAILS_HEADING,
                writer.enumConstantsDetailsLabel);
        enumConstantsDetailsTree.addContent(heading);
        return enumConstantsDetailsTree;
    }

    /**
     * {@inheritDoc}
     */
    public Content getEnumConstantsTreeHeader(FieldDoc enumConstant,
            Content enumConstantsDetailsTree) {
        enumConstantsDetailsTree.addContent(
                writer.getMarkerAnchor(enumConstant.name()));
        Content enumConstantsTree = writer.getMemberTreeHeader();
        Content heading = new HtmlTree(HtmlConstants.MEMBER_HEADING);
        heading.addContent(enumConstant.name());
        enumConstantsTree.addContent(heading);
        return enumConstantsTree;
    }

    /**
     * {@inheritDoc}
     */
    public Content getSignature(FieldDoc enumConstant) {
        Content pre = new HtmlTree(HtmlTag.PRE);
        writer.addAnnotationInfo(enumConstant, pre);
        addModifiers(enumConstant, pre);
        Content enumConstantLink = new RawHtml(writer.getLink(new LinkInfoImpl(LinkInfoImpl.CONTEXT_MEMBER,
                enumConstant.type())));
        pre.addContent(enumConstantLink);
        pre.addContent(" ");
        if (configuration().linksource) {
            Content enumConstantName = new StringContent(enumConstant.name());
            writer.addSrcLink(enumConstant, enumConstantName, pre);
        } else {
            addName(enumConstant.name(), pre);
        }
        return pre;
    }

    /**
     * {@inheritDoc}
     */
    public void addDeprecated(FieldDoc enumConstant, Content enumConstantsTree) {
        addDeprecatedInfo(enumConstant, enumConstantsTree);
    }

    /**
     * {@inheritDoc}
     */
    public void addComments(FieldDoc enumConstant, Content enumConstantsTree) {
        addComment(enumConstant, enumConstantsTree);
    }

    /**
     * {@inheritDoc}
     */
    public void addTags(FieldDoc enumConstant, Content enumConstantsTree) {
        writer.addTagsInfo(enumConstant, enumConstantsTree);
    }

    /**
     * {@inheritDoc}
     */
    public Content getEnumConstantsDetails(Content enumConstantsDetailsTree) {
        return getMemberTree(enumConstantsDetailsTree);
    }

    /**
     * {@inheritDoc}
     */
    public Content getEnumConstants(Content enumConstantsTree,
            boolean isLastContent) {
        return getMemberTree(enumConstantsTree, isLastContent);
    }

    /**
     * {@inheritDoc}
     */
    public void close() throws IOException {
        writer.close();
    }

    public int getMemberKind() {
        return VisibleMemberMap.ENUM_CONSTANTS;
    }

    /**
     * {@inheritDoc}
     */
    public void addSummaryLabel(Content memberTree) {
        Content label = HtmlTree.HEADING(HtmlConstants.SUMMARY_HEADING,
                writer.getResource("doclet.Enum_Constant_Summary"));
        memberTree.addContent(label);
    }

    /**
     * {@inheritDoc}
     */
    public String getTableSummary() {
        return configuration().getText("doclet.Member_Table_Summary",
                configuration().getText("doclet.Enum_Constant_Summary"),
                configuration().getText("doclet.enum_constants"));
    }

    /**
     * {@inheritDoc}
     */
    public String getCaption() {
        return configuration().getText("doclet.Enum_Constants");
    }

    /**
     * {@inheritDoc}
     */
    public String[] getSummaryTableHeader(ProgramElementDoc member) {
        String[] header = new String[] {
            configuration().getText("doclet.0_and_1",
                    configuration().getText("doclet.Enum_Constant"),
                    configuration().getText("doclet.Description"))
        };
        return header;
    }

    /**
     * {@inheritDoc}
     */
    public void addSummaryAnchor(ClassDoc cd, Content memberTree) {
        memberTree.addContent(writer.getMarkerAnchor("enum_constant_summary"));
    }

    /**
     * {@inheritDoc}
     */
    public void addInheritedSummaryAnchor(ClassDoc cd, Content inheritedTree) {
    }

    /**
     * {@inheritDoc}
     */
    public void addInheritedSummaryLabel(ClassDoc cd, Content inheritedTree) {
    }

    /**
     * {@inheritDoc}
     */
    protected void addSummaryLink(int context, ClassDoc cd, ProgramElementDoc member,
            Content tdSummary) {
        Content strong = HtmlTree.STRONG(new RawHtml(
                writer.getDocLink(context, (MemberDoc) member, member.name(), false)));
        Content code = HtmlTree.CODE(strong);
        tdSummary.addContent(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSummaryColumnStyle(HtmlTree tdTree) {
        tdTree.addStyle(HtmlStyle.colOne);
    }

    /**
     * {@inheritDoc}
     */
    protected void addInheritedSummaryLink(ClassDoc cd,
            ProgramElementDoc member, Content linksTree) {
    }

    /**
     * {@inheritDoc}
     */
    protected void addSummaryType(ProgramElementDoc member, Content tdSummaryType) {
        //Not applicable.
    }

    /**
     * {@inheritDoc}
     */
    protected Content getDeprecatedLink(ProgramElementDoc member) {
        return writer.getDocLink(LinkInfoImpl.CONTEXT_MEMBER,
                (MemberDoc) member, ((FieldDoc)member).qualifiedName());
    }

    /**
     * {@inheritDoc}
     */
    protected Content getNavSummaryLink(ClassDoc cd, boolean link) {
        if (link) {
            return writer.getHyperLink("", (cd == null)?
                "enum_constant_summary":
                "enum_constants_inherited_from_class_" +
                configuration().getClassName(cd),
                writer.getResource("doclet.navEnum"));
        } else {
            return writer.getResource("doclet.navEnum");
        }
    }

    /**
     * {@inheritDoc}
     */
    protected void addNavDetailLink(boolean link, Content liNav) {
        if (link) {
            liNav.addContent(writer.getHyperLink("", "enum_constant_detail",
                    writer.getResource("doclet.navEnum")));
        } else {
            liNav.addContent(writer.getResource("doclet.navEnum"));
        }
    }
}
