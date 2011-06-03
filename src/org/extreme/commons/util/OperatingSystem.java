package org.extreme.commons.util;

import java.io.File;

import javax.swing.UIManager;

/**
 * Operating system detection routines.
 */
public class OperatingSystem {
	private OperatingSystem() {
	}
    /**
     * �����ο���org.gjt.sp.jedit.OperatingSystem.(http://www.jedit.org)
     * �༭��JEdit��Դ����, ����JEdit��һ��GNU�Ŀ�Դ����������ȫCopy,�ͻ�Υ��GNU��Դ��Э��.
     * �������������һ���ǳ����õ��࣬���ǵ�����в����ԣ��Ųο�������࣬
     * �����޸Ĺ��������ŵ����ǵ�Դ������.
     * ��Ϊ�˲����𲻱�Ҫ�Ĳ�Ȩ���ף��Ժ�����Ҫ��ô��.��
     */
    /**
     * Returns if we're running Windows 95/98/ME/NT/2000/XP, or OS/2.
     */
    public static boolean isDOSDerived() {
        return isWindows() || isOS2();
    }

    /**
     * Returns if we're running Windows 95/98/ME/NT/2000/XP.
     */
    public static boolean isWindows() {
        return os == WINDOWS_9x || os == WINDOWS_NT;
    }

    /**
     * Returns if we're running Windows 95/98/ME.
     */
    public static boolean isWindows9x() {
        return os == WINDOWS_9x;
    }

    /**
     * Returns if we're running Windows NT/2000/XP.
     */
    public static boolean isWindowsNT() {
        return os == WINDOWS_NT;
    }

    /**
     * Returns if we're running OS/2.
     */
    public static boolean isOS2() {
        return os == OS2;
    }

    /**
     * Returns if we're running Unix (this includes MacOS X).
     */
    public static boolean isUnix() {
        return os == UNIX || os == MAC_OS_X;
    }

    /**
     * Returns if we're running MacOS X.
     */
    public static boolean isMacOS() {
        return os == MAC_OS_X;
    }

    /**
     * Returns if we're running MacOS X and using the native look and feel.
     */
    public static boolean isMacOSLF() {
        return (isMacOS() && UIManager.getLookAndFeel().isNativeLookAndFeel());
    }

    /**
     * Returns if Java 2 version 1.4 is in use.
     */
    public static boolean isJava14() {
        return java14;
    }

    /**
     * Returns if Java 2 version 1.5 is in use.
     */
    public static boolean isJava15() {
        return java15;
    }
    
    /**
     * Returns if Java 2 version 1.6 is in use.
     */
    public static boolean isJava16() {
        return java16;
    }    
    
    /**
     * Is IBM JDK
     */
    public static boolean isIBMJDK() {
        return ibmJDK;
    }

    /**
     * Is Sun JDK
     */
    public static boolean isSunJDK() {
        return sunJDK;
    }
    
    private static final int UNIX = 0x31337;
    private static final int WINDOWS_9x = 0x640;
    private static final int WINDOWS_NT = 0x666;
    private static final int OS2 = 0xDEAD;
    private static final int MAC_OS_X = 0xABC;
    private static final int UNKNOWN = 0xBAD;

    private static int os;
    private static boolean java14;
    private static boolean java15;
    private static boolean java16;
    
    private static boolean ibmJDK = false;
    private static boolean sunJDK = true;

    static {
        if (System.getProperty("mrj.version") != null) {
            os = MAC_OS_X;
        } else {
            String osName = System.getProperty("os.name");
            if (osName.indexOf("Windows 9") != -1
                    || osName.indexOf("Windows M") != -1) {
                os = WINDOWS_9x;
            } else if (osName.indexOf("Windows") != -1) {
                os = WINDOWS_NT;
            } else if (osName.indexOf("OS/2") != -1) {
                os = OS2;
            } else if (File.separatorChar == '/') {
                os = UNIX;
            } else {
                os = UNKNOWN;
            }
        }

        String javaVersion = System.getProperty("java.version").substring(0, 3);
        java14 = javaVersion.compareTo("1.4") == 0;
        java15 = javaVersion.compareTo("1.5") == 0;
        java16 = javaVersion.compareTo("1.6") == 0;
        
        ibmJDK = System.getProperty("java.vendor").toUpperCase().startsWith("IBM");
        sunJDK = System.getProperty("java.vendor").toUpperCase().startsWith("SUN");
    }
}
