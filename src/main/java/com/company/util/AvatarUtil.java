package com.company.util;

import com.company.pojo.User;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class AvatarUtil {

    private final String defaultAvatar = "/resources/img/avatar.png";
    private String saveAvatar;
    private String storagePath;
    private HttpServletRequest request;
    private HttpSession httpSession;
    private User sessionUser;

    public AvatarUtil(HttpServletRequest request) {
        String tmp = request.getServletContext().getRealPath("");
        storagePath = tmp.substring(0, tmp.indexOf("target"));
        httpSession = request.getSession(false);
        sessionUser = (User) httpSession.getAttribute("sessionUser");
        this.request = request;
    }

    public String saveOrUpdate() throws IllegalStateException, IOException, ServletException {
        Part file = request.getPart("user_avatar");
        int maxFileSize = 1024 * 1024;
        if (file.getSize() > maxFileSize) throw new IllegalStateException(file.getSize() + "КБ");
        if (file.getSize() > 0) {
            String pathToFile = "storage" + File.separator + "avatar";
            File folder = new File(storagePath + pathToFile);
            if (!folder.exists()) folder.mkdir();
            String fileType = file.getSubmittedFileName().substring(file.getSubmittedFileName().lastIndexOf("."));
            String filePath = storagePath + pathToFile + File.separator + new Date().getTime() + fileType;
            file.write(filePath);
            saveAvatar = filePath;
            delete(false);
            return filePath.substring(storagePath.length()-1).replaceAll(File.separator + File.separator, "/");
        }
        if (sessionUser != null && file.getSize() == 0) return sessionUser.getUserAvatar();
        return defaultAvatar;
    }

    /**
     * @param deleteAndUpdate false - only delete from storage
     *                        true - delete from storage and update current user at session and database
     * @see #delete(boolean)
     */

    public void delete(boolean deleteAndUpdate) {
        if (sessionUser != null && !sessionUser.getUserAvatar().equals(defaultAvatar)) {
            String path = sessionUser.getUserAvatar().replaceAll("/", File.separator + File.separator);
            File file = new File(storagePath + path);
            if (file.exists())
                if (file.delete() && deleteAndUpdate) {
                    Session session = HibernateUtil.getSession();
                    try {
                        session.beginTransaction();
                        sessionUser.setUserAvatar(defaultAvatar);
                        session.update(sessionUser);
                        session.getTransaction().commit();
                        httpSession.setAttribute("sessionUser", sessionUser);
                    } finally {
                        if (session.getTransaction() != null) session.getTransaction().rollback();
                        session.close();
                    }
                }
        }
    }

    public void rollBack() {
        if (saveAvatar != null) {
            File avatar = new File(saveAvatar);
            if (avatar.exists()) avatar.delete();
        }
    }
}
