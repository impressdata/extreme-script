package org.extreme.commons.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ClassFinder {
	public String className;

    public ArrayList jarFiles = new ArrayList();

    public ClassFinder() {
    }

    public ClassFinder(String className) {
        this.className = className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List findClass(String dir, boolean recurse) {
        searchDir(dir, recurse);
        return this.jarFiles;
    }

    protected void searchDir(String dir, boolean recurse) {
        try {
            File d = new File(dir);
            if (!d.isDirectory()) {
                return;
            }
            File[] files = d.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (recurse && files[i].isDirectory()) {
                    searchDir(files[i].getAbsolutePath(), true);
                } else {
                    String filename = files[i].getAbsolutePath();
                    if (filename.endsWith(".jar")|| filename.endsWith(".zip")) {
                        ZipFile zip = new ZipFile(filename);
                        Enumeration entries = zip.entries();
                        while (entries.hasMoreElements()) {
                            ZipEntry entry = (ZipEntry) entries.nextElement();
                            String thisClassName = getClassName(entry);
                            if (thisClassName.equals(this.className) || thisClassName.equals(this.className + ".class")) {
                                this.jarFiles.add(filename);
                            }
                        }
                    } else if (filename.endsWith(".class")) {
                    	String pointFilePath = filename.replaceAll("\\\\", ".");
                    	if (pointFilePath.endsWith(this.className + ".class")) {
                    		this.jarFiles.add(filename);
                    	}
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List getFilenames() {
        return this.jarFiles;
    }

    protected String getClassName(ZipEntry entry) {
        StringBuffer className = new StringBuffer(entry.getName().replace('/', '.'));
        return className.toString();
    }
}
