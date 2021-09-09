/*
 * Copyright (c) 1997, 2008, Oracle and/or its affiliates. All rights reserved.
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


/*
 * The Original Code is HAT. The Initial Developer of the
 * Original Code is Bill Foote, with contributions from others
 * at JavaSoft/Sun.
 */

package com.sun.tools.hat.internal.util;

import java.util.Enumeration;
import java.util.NoSuchElementException;

public class CompositeEnumeration implements Enumeration {
    Enumeration e1;
    Enumeration e2;

    public CompositeEnumeration(Enumeration e1, Enumeration e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public boolean hasMoreElements() {
        return e1.hasMoreElements() || e2.hasMoreElements();
    }

    public Object nextElement() {
        if (e1.hasMoreElements()) {
            return e1.nextElement();
        }

        if (e2.hasMoreElements()) {
            return e2.nextElement();
        }

        throw new NoSuchElementException();
    }
}
