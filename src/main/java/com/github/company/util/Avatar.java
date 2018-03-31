package com.github.company.util;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Avatar {

    private static final Logger LOGGER = Logger.getLogger(Avatar.class);
    private static final String STORAGE_PATH = PropUtil.getProperty("app.storage");
    public static final String DEFAULT_AVATAR = "/resources/img/avatar.png";

    private String uploaded;
    private boolean isWrite;

    @Nullable
    public String upload(@NotNull Part file) throws IllegalStateException {
        long maxFileSize = 1024 * 1024;
        if (file.getSize() > maxFileSize) throw new IllegalStateException("Загружаемый файл слишком большой");
        else if (file.getSize() > 0) {
            File storage = new File(STORAGE_PATH + "storage" + File.separator + "avatar");
            if (!storage.exists())
                if (storage.mkdir()) LOGGER.info("Create " + storage.getAbsolutePath());
                else {
                    LOGGER.error("Can`t create " + storage.getAbsolutePath());
                    return null;
                }
            String type = file.getSubmittedFileName().substring(file.getSubmittedFileName().lastIndexOf("."));
            uploaded = storage.getAbsolutePath() + File.separator + new Date().getTime() + type;
            try {
                file.write(uploaded);
            } catch (IOException e) {
                LOGGER.error(e.toString());
                return null;
            }
            isWrite = true;
            return uploaded.substring(STORAGE_PATH.length() - 1)
                    .replaceAll(File.separator + File.separator, "/");
        } else return null;
    }

    /**
     * delete image from storage
     *
     * @param path - path to avatar
     * @see #delete(String)
     */

    public static void delete(@NotNull String path) {
        if (!path.equals(DEFAULT_AVATAR)) {
            File avatar = new File(STORAGE_PATH + path);
            if (avatar.exists())
                if (!avatar.delete()) LOGGER.error("Can`t delete " + avatar.getAbsolutePath());
        }
    }

    /**
     * delete from storage saved image if something went wrong (example - fail registration) {@link #isWrite}
     * does not allow the storage of unnecessary files
     *
     * @see #rollBack()
     */

    public void rollBack() {
        if (isWrite) {
            File avatar = new File(uploaded);
            if (avatar.exists())
                if (avatar.delete()) isWrite = false;
                else LOGGER.error("Can`t delete " + avatar.getAbsolutePath());
        }
    }
}