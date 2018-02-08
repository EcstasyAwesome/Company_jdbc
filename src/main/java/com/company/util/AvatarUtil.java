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

    private String defaultAvatar = "/resources/img/avatar.png";
    private String saveAvatar;
    private String storagePath;
    private HttpServletRequest request;

    public AvatarUtil(HttpServletRequest request) {
        this.request = request;
        String tmp = request.getServletContext().getRealPath("");
        storagePath = tmp.substring(0, tmp.indexOf("target"));
    }

    public String save() throws IllegalStateException, IOException, ServletException {
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
            return filePath.substring(storagePath.length()).replaceAll(File.separator + File.separator, "/");
        }
        return defaultAvatar;
    }

    public void delete() {
        HttpSession httpSession = request.getSession(false);
        User sessionUser = (User) httpSession.getAttribute("sessionUser");
        if (sessionUser != null) {
            String path = sessionUser.getUserAvatar().replaceAll("/", File.separator + File.separator);
            File file = new File(storagePath + path.substring(1));
            if (file.exists())
                if (file.delete()) {
                    Session session = HibernateUtil.getSession();
                    try {
                        session.beginTransaction();
                        User user = session.get(User.class, sessionUser.getUserId());
                        user.setUserAvatar(defaultAvatar);
                        session.update(user);
                        session.getTransaction().commit();
                        httpSession.setAttribute("sessionUser", user);
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
