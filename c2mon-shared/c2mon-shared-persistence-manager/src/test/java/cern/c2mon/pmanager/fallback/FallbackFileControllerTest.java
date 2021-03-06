/******************************************************************************
 * Copyright (C) 2010-2016 CERN. All rights not expressly granted are reserved.
 *
 * This file is part of the CERN Control and Monitoring Platform 'C2MON'.
 * C2MON is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the license.
 *
 * C2MON is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with C2MON. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************************/

package cern.c2mon.pmanager.fallback;

import cern.c2mon.pmanager.fallback.exception.DataFallbackException;
import cern.c2mon.pmanager.mock.FallbackImpl;


import junit.framework.TestCase;

/**
 * JUnit test for the FallbackFileController class
 *
 * To execute this test you have to provide the following runtime arguments
 *  -Dtim.log.fallback.file=XXX.log
 *
 * @author mruizgar
 *
 */
public class FallbackFileControllerTest extends TestCase {

    /** Number of lines to be deleted */
    private static final int NUMBER_OF_LINES = 5;

    /** The fallback file path */
    private static final String PATH = "/tmp/falback-test.txt";

    /** It sets up the class for the test */
    protected void setUp() {

    }

    /**
     * Tests the OpenOutputStream method, a new stream should be created
     */
    public final void testOpenOutputStream() {
        FallbackFileController fFileController = new FallbackFileController(PATH);
        try {

            fFileController.openFallbackOutputStream();
        } catch (DataFallbackException e) {
            fail("The OutputStream could not be opened");
        }
        assertNotNull(fFileController.getOutput());
    }

    /**
     * Tests the OpenInputStream method, the test is successful when the stream
     * is correctly opened
     */
    public final void testOpenInputStream() {
        FallbackFileController fFileController = new FallbackFileController(PATH);
        try {
            fFileController.openFallbackInputStream();
        } catch (DataFallbackException e) {
            fail("The InputStream could not be opened");
        }
        assertNotNull(fFileController.getInput());
    }

    /**
     * Tests that a new tag is correctly logged in the fallback log file
     */
    public final void testWriteLine() {
        FallbackFileController fFileController = new FallbackFileController(PATH);

        FallbackImpl fallbackImpl = new FallbackImpl();
        try {
            fFileController.openFallbackOutputStream();
            fFileController.writeLine(fallbackImpl);
            fFileController.closeFallbackOutputStream();
        } catch (DataFallbackException e) {
            fail("An error has ocurred while trying to write in the fallback log file");
        }

        assertFalse(fFileController.isFallBackFileEmpty());
    }

    /**
     * Tests that lines from the fallback log file are correctly read TODO fix test
     */
//   public final void testReadLines() {
//        FallbackFileController fFileController = new FallbackFileController(PATH);
//
//        try {
//            fFileController.openFallbackInputStream();
//            List dataTags = fFileController.readLines(FallbackFileControllerTest.NUMBER_OF_LINES, new FallbackImpl());
//            fFileController.closeFallbackInputStream();
//            assertTrue(dataTags.size() >= 1);
//        } catch (DataFallbackException e) {
//            fail("An error has ocurred while trying to read back some lines from the fallback log file");
//        }
//
//    }

    /**
     * Tests that lines are correctly removed from the fallback log file TODO fix this test
     * @throws InterruptedException
     * @throws DataFallbackException
     */
//   public final void testClearFileContents() throws InterruptedException, DataFallbackException {
//       FallbackFileController fFileController = new FallbackFileController(PATH);
//       fFileController.setReadBackLines(fFileController.getNumberOfLines());
//
//        try {
//            // When we arrive here the fallback descriptor is open due to the call to the constructor,
//            // that makes the descriptor point to the last processed line
//            fFileController.closeFallbackInputStream();
//            fFileController.updateNumberOfProcessedLines();
//            fFileController.clearFileContents();
//        } catch (DataFallbackException e) {
//            e.printStackTrace();
//            fail("Error while removing the lines from the fallback file");
//        }
//        assertTrue(fFileController.isFallBackFileEmpty());
//    }

    /**
     * Tests the CloseInputStream method, the test is successful when the stream
     * is correclty closed
     */
    public final void testCloseInputStream() {
        FallbackFileController fFileController = new FallbackFileController(PATH);
        try {
            fFileController.closeFallbackInputStream();
        } catch (DataFallbackException e) {
            fail("The InputStream could not be closed");
        }
        assertNull(fFileController.getInput());
    }

    /**
     * Tests the CloseOutputStream method, the test is successful when the
     * stream is correctly closed
     */
    public final void testCloseOutputStream() {
        FallbackFileController fFileController = new FallbackFileController(PATH);
        try {
            fFileController.closeFallbackOutputStream();
        } catch (DataFallbackException e) {
            fail("The OutputStream could not be closed");
        }
        assertNull(fFileController.getOutput());
    }

}
