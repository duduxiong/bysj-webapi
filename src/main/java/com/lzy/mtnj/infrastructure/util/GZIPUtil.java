package com.lzy.mtnj.infrastructure.util;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.util.List;
public class GZIPUtil {
    /**
     *
     * @Title: pack
     * @Description: 将一组文件打成tar包
     * @param sources
     *            要打包的原文件数组
     * @param target
     *            打包后的文件
     * @return File 返回打包后的文件
     * @throws
     */
    public static File pack(List<File> sources, File target,String rootDiretory) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(target);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        TarArchiveOutputStream os = new TarArchiveOutputStream(out);
        for (File file : sources) {
            try {
                System.out.println(file.getName());
                os.putArchiveEntry(new TarArchiveEntry(file,rootDiretory+"/"+file.getName()));
                FileInputStream fis = new FileInputStream(file);
                IOUtils.copy(fis, os);
                os.closeArchiveEntry();
                fis.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (os != null) {
            try {
                os.flush();
                os.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return target;
    }
}
