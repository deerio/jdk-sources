/*
 * Copyright (c) 1999, 2007, Oracle and/or its affiliates. All rights reserved.
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

package sun.security.pkcs12;

import java.io.*;
import java.security.*;

import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.AlgorithmId;
import sun.security.pkcs.ParsingException;


/**
 * A MacData type, as defined in PKCS#12.
 *
 * @author Sharon Liu
 */

class MacData {

    private String digestAlgorithmName;
    private AlgorithmParameters digestAlgorithmParams;
    private byte[] digest;
    private byte[] macSalt;
    private int iterations;

    // the ASN.1 encoded contents of this class
    private byte[] encoded = null;

    /**
     * Parses a PKCS#12 MAC data.
     */
    MacData(DerInputStream derin)
        throws IOException, ParsingException
    {
        DerValue[] macData = derin.getSequence(2);

        // Parse the digest info
        DerInputStream digestIn = new DerInputStream(macData[0].toByteArray());
        DerValue[] digestInfo = digestIn.getSequence(2);

        // Parse the DigestAlgorithmIdentifier.
        AlgorithmId digestAlgorithmId = AlgorithmId.parse(digestInfo[0]);
        this.digestAlgorithmName = digestAlgorithmId.getName();
        this.digestAlgorithmParams = digestAlgorithmId.getParameters();
        // Get the digest.
        this.digest = digestInfo[1].getOctetString();

        // Get the salt.
        this.macSalt = macData[1].getOctetString();

        // Iterations is optional. The default value is 1.
        if (macData.length > 2) {
            this.iterations = macData[2].getInteger();
        } else {
            this.iterations = 1;
        }
    }

    MacData(String algName, byte[] digest, byte[] salt, int iterations)
        throws NoSuchAlgorithmException
    {
        if (algName == null)
           throw new NullPointerException("the algName parameter " +
                                               "must be non-null");

        AlgorithmId algid = AlgorithmId.get(algName);
        this.digestAlgorithmName = algid.getName();
        this.digestAlgorithmParams = algid.getParameters();

        if (digest == null) {
            throw new NullPointerException("the digest " +
                                           "parameter must be non-null");
        } else if (digest.length == 0) {
            throw new IllegalArgumentException("the digest " +
                                                "parameter must not be empty");
        } else {
            this.digest = digest.clone();
        }

        this.macSalt = salt;
        this.iterations = iterations;

        // delay the generation of ASN.1 encoding until
        // getEncoded() is called
        this.encoded = null;

    }

    MacData(AlgorithmParameters algParams, byte[] digest,
        byte[] salt, int iterations) throws NoSuchAlgorithmException
    {
        if (algParams == null)
           throw new NullPointerException("the algParams parameter " +
                                               "must be non-null");

        AlgorithmId algid = AlgorithmId.get(algParams);
        this.digestAlgorithmName = algid.getName();
        this.digestAlgorithmParams = algid.getParameters();

        if (digest == null) {
            throw new NullPointerException("the digest " +
                                           "parameter must be non-null");
        } else if (digest.length == 0) {
            throw new IllegalArgumentException("the digest " +
                                                "parameter must not be empty");
        } else {
            this.digest = digest.clone();
        }

        this.macSalt = salt;
        this.iterations = iterations;

        // delay the generation of ASN.1 encoding until
        // getEncoded() is called
        this.encoded = null;

    }

    String getDigestAlgName() {
        return digestAlgorithmName;
    }

    byte[] getSalt() {
        return macSalt;
    }

    int getIterations() {
        return iterations;
    }

    byte[] getDigest() {
        return digest;
    }

    /**
     * Returns the ASN.1 encoding of this object.
     * @return the ASN.1 encoding.
     * @exception IOException if error occurs when constructing its
     * ASN.1 encoding.
     */
    public byte[] getEncoded() throws NoSuchAlgorithmException, IOException
    {
        if (this.encoded != null)
            return this.encoded.clone();

        DerOutputStream out = new DerOutputStream();
        DerOutputStream tmp = new DerOutputStream();

        DerOutputStream tmp2 = new DerOutputStream();
        // encode encryption algorithm
        AlgorithmId algid = AlgorithmId.get(digestAlgorithmName);
        algid.encode(tmp2);

        // encode digest data
        tmp2.putOctetString(digest);

        tmp.write(DerValue.tag_Sequence, tmp2);

        // encode salt
        tmp.putOctetString(macSalt);

        // encode iterations
        tmp.putInteger(iterations);

        // wrap everything into a SEQUENCE
        out.write(DerValue.tag_Sequence, tmp);
        this.encoded = out.toByteArray();

        return this.encoded.clone();
    }

}
