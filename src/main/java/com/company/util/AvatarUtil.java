package com.company.util;

import com.company.dao.entity.User;
import com.company.dao.factory.DaoFactory;
import com.company.dao.model.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class AvatarUtil {

    private DaoFactory daoFactory = DaoFactory.getInstance();
    private UserDao userDao = daoFactory.getUserDao();
    private final String defaultAvatar = "/resources/img/avatar.png";
    private final String sessionUser = "sessionUser";
    private String saveAvatar;
    private String oldAvatar;
    private String storagePath;
    private HttpServletRequest request;
    private HttpSession httpSession;
    private User user;
    private boolean isSaved;

    public AvatarUtil(HttpServletRequest request) {
        String tmp = request.getServletContext().getRealPath("");
        storagePath = tmp.substring(0, tmp.indexOf("target"));
        httpSession = request.getSession(false);
        user = (User) httpSession.getAttribute(sessionUser);
        if (user != null) oldAvatar = user.getAvatar();
        this.request = request;
    }

    /**
     * @return returns link for which avatars are available
     * @throws IllegalStateException if upload file size > maxFileSize
     * @throws IOException           if can't write file
     * @see #save()
     */

    public String save() throws IllegalStateException, IOException, ServletException {
        Part file = request.getPart("avatar");
        int maxFileSize = 1024 * 1024;
        if (file.getSize() > maxFileSize) throw new IllegalStateException("Загружаемый файл слишком большой");
        else if (file.getSize() > 0) {
            String pathToFile = "storage" + File.separator + "avatar";
            File folder = new File(storagePath + pathToFile);
            if (!folder.exists()) folder.mkdir();
            String fileType = file.getSubmittedFileName().substring(file.getSubmittedFileName().lastIndexOf("."));
            saveAvatar = storagePath + pathToFile + File.separator + new Date().getTime() + fileType;
            file.write(saveAvatar);
            isSaved = true;
            return saveAvatar.substring(storagePath.length() - 1).replaceAll(File.separator + File.separator, "/");
        }
        return user != null ? user.getAvatar() : defaultAvatar;
    }

    /**
     * delete avatar from storage and update current user in session and database
     *
     * @see #delete()
     */

    public void delete() {
        if (user != null && !user.getAvatar().equals(defaultAvatar)) {
            String path = user.getAvatar().replaceAll("[/]", File.separator + File.separator);
            File file = new File(storagePath + path);
            if (file.exists())
                if (file.delete()) {
                    user.setAvatar(defaultAvatar);
                    userDao.update(user);
                    httpSession.setAttribute(sessionUser, user);
                }
        }
    }

    /**
     * delete from storage old user avatar after saving new file {@link #isSaved}
     * does not allow the storage of unnecessary files
     *
     * @see #clean()
     */

    public void clean() {
        if (isSaved && !oldAvatar.equals(defaultAvatar)) {
            File avatar = new File(storagePath + oldAvatar);
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

    /**
     * @return returns the current state of the saved object
     * @see #isSaved()
     */

    public boolean isSaved() {
        return isSaved;
    }
}
