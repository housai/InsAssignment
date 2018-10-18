package com.klein.instagram.Picture;

import java.io.File;
import java.util.Comparator;

/**
 * Image sort by modified
 * Created by hupei on 2016/7/14.
 */
class SortPictureList implements Comparator {
    @Override
    public int compare(Object o, Object t1) {
        String path1 = o.toString();
        String path2 = t1.toString();
        File file1 = new File(path1);
        File file2 = new File(path2);
        if (file1.lastModified() < file2.lastModified()) {
            return 1;
        } else return -1;
    }
}