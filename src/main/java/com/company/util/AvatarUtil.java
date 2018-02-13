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
    private boolean isSaved;

    public AvatarUtil(HttpServletRequest request) {
        String tmp = request.getServletContext().getRealPath("");
        storagePath = tmp.substring(0, tmp.indexOf("target"));
        httpSession = request.getSession(false);
        sessionUser = (User) httpSession.getAttribute("sessionUser");
        this.request = request;
    }

    /**
     * @return returns link for which avatars are available
     * @throws IllegalStateException if upload file size > maxFileSize
     * @throws IOException           if can't write file
     * @see #save()
     */

    public String save() throws IllegalStateException, IOException, ServletException {
        Part file = request.getPart("user_avatar");
        int maxFileSize = 1024 * 1024;
        if (file.getSize() > maxFileSize) throw new IllegalStateException(file.getSize() + "КБ");
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
        return sessionUser != null ? sessionUser.getUserAvatar() : defaultAvatar;
    }

    /**
     * @param update false - delete from storage avatar of the current user
     *               true - delete from storage avatar and update current user in session and database
     * @see #delete(boolean)
     */

    public void delete(boolean update) {
        if (sessionUser != null && !sessionUser.getUserAvatar().equals(defaultAvatar)) {
            String path = sessionUser.getUserAvatar().replaceAll("[/]", File.separator + File.separator);
            File file = new File(storagePath + path);
            if (file.exists())
                if (file.delete() && update) {
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

    /**
     * delete from storage old user avatar after saving new file {@link #isSaved}
     * does not allow the storage of unnecessary files
     *
     * @see #clean()
     */

    public void clean() {
        if (isSaved) delete(false);
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
            if (avatar.exists()) avatar.delete();
        }
    }
}
