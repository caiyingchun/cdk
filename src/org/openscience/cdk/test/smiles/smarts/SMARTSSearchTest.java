/* $RCSfile$
 * $Author$
 * $Date$
 * $Revision$
 *
 * Copyright (C) 2004  The Chemistry Development Kit (CDK) project
 *
 * Contact: cdk-devel@lists.sourceforge.net
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.openscience.cdk.test.smiles.smarts;

import junit.framework.*;

import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.smiles.smarts.SMARTSParser;
import org.openscience.cdk.smiles.smarts.ParseException;
import org.openscience.cdk.tools.LoggingTool;

/**
 * JUnit test routines for the SMARTS substructure search.
 *
 * @cdk.module test
 * @cdk.require ant1.6
 *
 * @author Egon Willighagen
 */
public class SMARTSSearchTest extends TestCase {
    
    private LoggingTool logger;
    
    public SMARTSSearchTest() {}

    public SMARTSSearchTest(java.lang.String testName) {
        super(testName);
        logger = new LoggingTool(this);
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(SMARTSSearchTest.class);
        return suite;
    }
    
    public void testAromaticBond() throws ParseException {
        try {
            QueryAtomContainer query = SMARTSParser.parse("C:C");
            logger.debug("Query C:C: " + query.toString());
            SmilesParser sp = new SmilesParser();

            AtomContainer atomContainer = sp.parseSmiles("c1ccccc1"); // benzene, aromatic
            assertTrue(UniversalIsomorphismTester.isSubgraph(atomContainer, query));
            
            atomContainer = sp.parseSmiles("C1CCCCC1"); // hexane, non-aromatic
            assertFalse(UniversalIsomorphismTester.isSubgraph(atomContainer, query));
        } catch (CDKException exception) {
            fail(exception.getMessage());
        }
    }

    public void testSingleBond() throws ParseException {
        try {
            QueryAtomContainer query = SMARTSParser.parse("C-C");
            logger.debug("Query C-C: " + query.toString());
            SmilesParser sp = new SmilesParser();

            AtomContainer atomContainer = sp.parseSmiles("CCC"); // benzene, aromatic
            assertTrue(UniversalIsomorphismTester.isSubgraph(atomContainer, query));

            atomContainer = sp.parseSmiles("C=C"); // benzene, aromatic
            assertFalse(UniversalIsomorphismTester.isSubgraph(atomContainer, query));

            atomContainer = sp.parseSmiles("C#C"); // benzene, aromatic
            assertFalse(UniversalIsomorphismTester.isSubgraph(atomContainer, query));
        } catch (CDKException exception) {
            fail(exception.getMessage());
        }
    }

    public void testDoubleBond() throws ParseException {
        try {
            QueryAtomContainer query = SMARTSParser.parse("C=C");
            logger.debug("Query C=C: " + query.toString());
            SmilesParser sp = new SmilesParser();

            AtomContainer atomContainer = sp.parseSmiles("CCC"); // benzene, aromatic
            assertFalse(UniversalIsomorphismTester.isSubgraph(atomContainer, query));

            atomContainer = sp.parseSmiles("C=C"); // benzene, aromatic
            assertTrue(UniversalIsomorphismTester.isSubgraph(atomContainer, query));

            atomContainer = sp.parseSmiles("C#C"); // benzene, aromatic
            assertFalse(UniversalIsomorphismTester.isSubgraph(atomContainer, query));
        } catch (CDKException exception) {
            fail(exception.getMessage());
        }
    }

    public void testTripleBond() throws ParseException {
        try {
            QueryAtomContainer query = SMARTSParser.parse("C#C");
            logger.debug("Query C#C: " + query.toString());
            SmilesParser sp = new SmilesParser();

            AtomContainer atomContainer = sp.parseSmiles("CCC"); // benzene, aromatic
            assertFalse(UniversalIsomorphismTester.isSubgraph(atomContainer, query));

            atomContainer = sp.parseSmiles("C=C"); // benzene, aromatic
            assertFalse(UniversalIsomorphismTester.isSubgraph(atomContainer, query));

            atomContainer = sp.parseSmiles("C#C"); // benzene, aromatic
            assertTrue(UniversalIsomorphismTester.isSubgraph(atomContainer, query));
        } catch (CDKException exception) {
            fail(exception.getMessage());
        }
    }

    public void testAnyOrderBond() throws ParseException {
        try {
            QueryAtomContainer query = SMARTSParser.parse("C~C");
            logger.debug("Query C~C: " + query.toString());
            SmilesParser sp = new SmilesParser();

            AtomContainer atomContainer = sp.parseSmiles("CCC"); // benzene, aromatic
            assertTrue(UniversalIsomorphismTester.isSubgraph(atomContainer, query));

            atomContainer = sp.parseSmiles("C=C"); // benzene, aromatic
            assertTrue(UniversalIsomorphismTester.isSubgraph(atomContainer, query));

            atomContainer = sp.parseSmiles("C#C"); // benzene, aromatic
            assertTrue(UniversalIsomorphismTester.isSubgraph(atomContainer, query));
        } catch (CDKException exception) {
            fail(exception.getMessage());
        }
    }

}
