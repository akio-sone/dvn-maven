/*
   Copyright (C) 2005-2012, by the President and Fellows of Harvard College.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

   Dataverse Network - A web application to share, preserve and analyze research data.
   Developed at the Institute for Quantitative Social Science, Harvard University.
   Version 3.0.
*/
package edu.harvard.iq.dvn.ingest.statdataio.impl.plugins.sav;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.logging.*;
import java.util.Locale;

import static java.lang.System.*;

import org.apache.commons.codec.binary.Hex;

import edu.harvard.iq.dvn.ingest.org.thedata.statdataio.*;
import edu.harvard.iq.dvn.ingest.org.thedata.statdataio.spi.*;

/**
 *
 * @author akio sone at UNC-Odum
 */
public class SAVFileReaderSpi extends StatDataFileReaderSpi{

    private static Logger dbgLog = Logger.getLogger(SAVFileReaderSpi.class.getPackage().getName());
    private static int SAV_HEADER_SIZE = 4;
    private static String SAV_FILE_SIGNATURE = "$FL2";

    private static String[] formatNames = {"sav", "SAV"};
    private static String[] extensions = {"sav"};
    private static String[] mimeType = {"application/x-spss-sav"};

    
    
    /**
     *
     */
    public SAVFileReaderSpi() {
        super(
            "HU-IQSS-DVN-project",
            "0.1",
            formatNames, extensions, mimeType, SAVFileReaderSpi.class.getName());
        dbgLog.fine(SAVFileReaderSpi.class.getName()+" is called");
    }

    /**
     * Returns the value of the description of the corresponding
     * SAVFileReader class.
     *
     * @param locale
     * @return the value of the description of the corresponding
     * SAVFileReader class
     */
    public String getDescription(Locale locale) {
        return "HU-IQSS-DVN-project SPSS System File Reader";
    }

    @Override
    public boolean canDecodeInput(Object source) throws IOException {
        out.println("this method is actually called: object");
        if (!(source instanceof BufferedInputStream)) {
            return false;
        } else if (source instanceof File){
            out.println("source is a File object");
        } else {
            out.println("not File object");
        }
        if (source  == null){
            throw new IllegalArgumentException("source == null!");
        }
        BufferedInputStream stream = (BufferedInputStream)source;

        dbgLog.fine("applying the sav test\n");

        byte[] b = new byte[SAV_HEADER_SIZE];

        if (stream.markSupported()){
            stream.mark(0);
        }
        int nbytes = stream.read(b, 0, SAV_HEADER_SIZE);

        if (nbytes == 0){
            throw new IOException();
        }
        //printHexDump(b, "hex dump of the byte-array");
        dbgLog.info("hex dump of the 1st 4 bytes[$FL2 == 24 46 4C 32]="+
                new String(Hex.encodeHex(b)));
        if (stream.markSupported()){
            stream.reset();
        }

        boolean DEBUG = false;

        String hdr4sav = new String(b);
        dbgLog.fine("from string[$FL2 == 24 46 4C 32]=" + new String(Hex.encodeHex(b)).toUpperCase());

        if (hdr4sav.equals(SAV_FILE_SIGNATURE)) {
            dbgLog.fine("this file is spss-sav type");
            return true;
        } else {
            dbgLog.fine("this file is NOT spss-sav type");
            return false;
        }
    }


    @Override
    public boolean canDecodeInput(BufferedInputStream stream) throws IOException {
        if (stream ==null){
            throw new IllegalArgumentException("stream == null!");
        }

        dbgLog.fine("\napplying the sav test: inputstream case\n");

        byte[] b = new byte[SAV_HEADER_SIZE];
        
        if (stream.markSupported()){
            stream.mark(0);
        }
        int nbytes = stream.read(b, 0, SAV_HEADER_SIZE);

        if (nbytes == 0){
            throw new IOException();
        }
        //printHexDump(b, "hex dump of the byte-array");
        dbgLog.info("hex dump of the 1st 4 bytes[$FL2 == 24 46 4C 32]="+
                (new String (Hex.encodeHex(b))).toUpperCase());


        if (stream.markSupported()){
            stream.reset();
        }

        boolean DEBUG = false;

        String hdr4sav = new String(b);
        dbgLog.fine("from string[$FL2 == 24 46 4C 32]=" + new String(Hex.encodeHex(b)).toUpperCase());


        if (hdr4sav.equals(SAV_FILE_SIGNATURE)) {
            dbgLog.fine("this file is spss-sav type");
            return true;
        } else {
            dbgLog.fine("this file is NOT spss-sav type");
            return false;
        }
    }

    @Override
    public boolean canDecodeInput(File file) throws IOException {
        if (file ==null){
            throw new IllegalArgumentException("file == null!");
        }
        if (!file.canRead()){
            throw new IOException("cannot read the input file");
        }

        dbgLog.fine("applying the sav test\n");

        // set-up a FileChannel instance for a given file object
        FileChannel srcChannel = new FileInputStream(file).getChannel();

        // create a read-only MappedByteBuffer
        MappedByteBuffer buff = srcChannel.map(FileChannel.MapMode.READ_ONLY, 0, SAV_HEADER_SIZE);

        //printHexDump(buff, "hex dump of the byte-buffer");
        dbgLog.info("hex dump of the 1st 4 bytes[$FL2 == 24 46 4C 32]="+
                new String(Hex.encodeHex(buff.array())));

        buff.rewind();

        boolean DEBUG = false;

        byte[] hdr4 = new byte[4];
        buff.get(hdr4, 0, 4);
        String hdr4sav = new String(hdr4);
        dbgLog.fine("from string[hdr4]=" + new String(Hex.encodeHex(hdr4)).toUpperCase());
        
        if (hdr4sav.equals("$FL2")) {
            dbgLog.fine("this file is spss-sav type");
            return true;
        } else {
            dbgLog.fine("this file is NOT spss-sav type");
        }
        return false;
    }


    @Override
    public StatDataFileReader createReaderInstance(Object ext) throws
        IOException {
        return new SAVFileReader(this);
    }

}
