package com.github.company.util;

import com.github.company.dao.DaoService;
import com.github.company.dao.entity.User;
import com.github.company.dao.model.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Avatar {

    public static final String DEFAULT_AVATAR = PropertiesReader.getProperty("avatar");
    private static final String STORAGE_PATH = PropertiesReader.getProperty("storage");

    private DaoService daoService = DaoService.getInstance();
    private UserDao userDao = daoService.getUserDao();
    private String saveAvatar;
    private String oldAvatar;
    private boolean isSaved;

    /**
     * @return returns link for which avatars are available
     * @throws IllegalStateException if upload file size > maxFileSize
     * @throws IOException           if can't write file
     * @see #save(HttpServletRequest)
     */

    public String save(@NotNull HttpServletRequest request) throws IllegalStateException, IOException, ServletException {
        HttpSession httpSession = request.getSession(false);
        User user = (User) httpSession.getAttribute("sessionUser");
        if (user != null) oldAvatar = user.getAvatar();
        Part file = request.getPart("avatar");
        int maxFileSize = 1024 * 1024;
        if (file.getSize() > maxFileSize) throw new IllegalStateException("Загружаемый файл слишком большой");
        else if (file.getSize() > 0) {
            String pathToFile = "storage" + File.separator + "avatar";
            File folder = new File(STORAGE_PATH + pathToFile);
            if (!folder.exists()) folder.mkdir();
            String fileType = file.getSubmittedFileName().substring(file.getSubmittedFileName().lastIndexOf("."));
            saveAvatar = STORAGE_PATH + pathToFile + File.separator + new Date().getTime() + fileType;
            file.write(saveAvatar);
            isSaved = true;
            return saveAvatar.substring(STORAGE_PATH.length() - 1).replaceAll(File.separator + File.separator, "/");
        }
        return user != null ? user.getAvatar() : DEFAULT_AVATAR;
    }

    /**
     * delete avatar from storage and update current user in session and database
     *
     * @see #delete(HttpServletRequest)
     */

    public void delete(@NotNull HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);
        User user = (User) httpSession.getAttribute("sessionUser");
        if (user != null && !user.getAvatar().equals(DEFAULT_AVATAR)) {
            String path = user.getAvatar().replaceAll("[/]", File.separator + File.separator);
            File file = new File(STORAGE_PATH + path);
            if (file.exists())
                if (file.delete()) {
                    user.setAvatar(DEFAULT_AVATAR);
                    userDao.update(user);
                    httpSession.setAttribute("sessionUser", user);
                }
        }
    }

    /**
     * delete from storage avatar by absolute path
     *
     * @param path - absolute path to avatar
     * @see #deleteFromStorage(String)
     */

    public static void deleteFromStorage(@NotNull String path) {
        if (!path.equals(DEFAULT_AVATAR)) {
            File avatar = new File(STORAGE_PATH + path);
            if (avatar.exists()) avatar.delete();
        }
    }

    /**
     * delete from storage old user avatar after saving new file {@link #isSaved}
     * does not allow the storage of unnecessary files
     *
     * @see #clean()
     */

    public void clean() {
        if (isSaved && !oldAvatar.equals(DEFAULT_AVATAR)) {
            File avatar = new File(STORAGE_PATH + oldAvatar);
            if (avatar.exists())
                if (avatar.delete())
                    isSaved = false;
        }
    }

    /**
     * delete from storage saved avatar if something went wrong (example - fail registration) {@link #isSaved}
     * does not allow the storage of unnecessary files
     *
     * @see #rollBack()
     */

    public void rollBack() {
        if (isSaved) {
            File avatar = new File(saveAvatar);
            if (avatar.exists())
                if (avatar.delete())
                    isSaved = false;
        }
    }
}