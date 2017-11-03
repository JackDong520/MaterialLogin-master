package cn.fanrunqi.materiallogin.javabeans;

/**
 * Created by 72408 on 2017/6/8.
 */
public class UserEntity {
    private int id;
    private String user;
    private String pwd;

    public UserEntity(int id, String user, String pwd) {
        this.id = id;
        this.user = user;
        this.pwd = pwd;
    }

    public UserEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (pwd != null ? !pwd.equals(that.pwd) : that.pwd != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (pwd != null ? pwd.hashCode() : 0);
        return result;
    }
}
