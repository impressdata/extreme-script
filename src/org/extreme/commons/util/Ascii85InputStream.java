/*
 *
 * Ascii 85 De/En coding streams.
 * Used in postscript level 2.
 * Radim Kolar hsn@cybermail.net
 * http://home.worldonline.cz/~cz210552/  
 */
package org.extreme.commons.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Ascii85InputStream extends FilterInputStream {

    private int index;
    private int n;
    private boolean eof;

    private byte ascii[];
    private byte b[];

    public Ascii85InputStream(InputStream is) {
        super(is);

        index = n = 0;
        eof = false;
        ascii = new byte[5];
        b = new byte[4];
    }

    public final int read() throws IOException {
        if (index >= n) {
            if (eof) return -1;
            index = 0;
            int k;
            byte z;
            do {
                int zz = (byte) in.read();
                if (zz == -1) {
                    eof = true;
                    return -1;
                }
                z = (byte) zz;
            } while (z == '\n' || z == '\r' || z == ' ');
            if (z == '~' | z == 'x') {
                eof = true;
                ascii = b = null;
                n = 0;
                return -1;
            } else if (z == 'z') {
                b[0] = b[1] = b[2] = b[3] = 0;
                n = 4;
            } else {
                ascii[0] = z; // may be EOF here....
                for (k = 1; k < 5; ++k) {
                    do {
                        int zz = (byte) in.read();
                        if (zz == -1) {
                            eof = true;
                            return -1;
                        }
                        z = (byte) zz;
                    } while (z == '\n' || z == '\r' || z == ' ');
                    ascii[k] = z;
                    if (z == '~' | z == 'x') break;
                }
                n = k - 1;
                if (n == 0) {
                    eof = true;
                    ascii = b = null;
                    return -1;
                }
                if (k < 5) {
                    for (++k; k < 5; ++k)
                        ascii[k] = 0x21;
                    eof = true;
                }
                // decode stream
                long t = 0;
                for (k = 0; k < 5; ++k) {
                    z = (byte) (ascii[k] - 0x21);
                    // System.out.println("z="+z);
                    if (z < 0 || z > 93) {
                        n = 0;
                        eof = true;
                        ascii = b = null;
                        throw new IOException("Invalid data in Ascii85 stream");
                    }
                    t = (t * 85L) + z;
                }
                // System.out.println("Word decoded="+Long.toString(t,16));
                for (k = 3; k >= 0; --k) {
                    b[k] = (byte) (t & 0xFFL);
                    t >>>= 8;
                }
            }
        }
        return b[index++] & 0xFF;
    }

    public final int read(byte data[], int ofs, int len) throws IOException {
        if (eof && index >= n) return -1;
        for (int i = 0; i < len; i++) {
            if (index < n) {
                data[i + ofs] = b[index++];
            } else {
                int t = read();
                if (t == -1) return i;
                data[i + ofs] = (byte) t;
            }
        }
        return len;
    }

    public void close() throws IOException {
        ascii = null;
        eof = true;
        b = null;
        super.close();
    }

/* non supported interface methods */

    public boolean upported() {
        return false;
    }

    public long skip(long n) {
        return 0;
    }

    public int available() {
        return 0;
    }

    public void mark(int readlimit) {
    }

    public void reset() throws IOException {
        throw new IOException("Reset is not supported");
    }
}
