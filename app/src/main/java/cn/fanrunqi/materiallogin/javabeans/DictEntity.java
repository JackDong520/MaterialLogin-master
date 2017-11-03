package cn.fanrunqi.materiallogin.javabeans;

import java.io.Serializable;

/**
 * Created by 72408 on 2017/6/8.
 */
public class DictEntity implements Serializable {
    private String word;
    private String meaning;
    private int id;


    public DictEntity() {
    }

    public DictEntity(String word, String meaning, int id) {
        this.word = word;
        this.meaning = meaning;
        this.id = id;
    }


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return "DictEntity{" +
                "word='" + word + '\'' +
                ", meaning='" + meaning + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DictEntity that = (DictEntity) o;

        if (word != null ? !word.equals(that.word) : that.word != null) return false;
        if (meaning != null ? !meaning.equals(that.meaning) : that.meaning != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = word != null ? word.hashCode() : 0;
        result = 31 * result + (meaning != null ? meaning.hashCode() : 0);
        return result;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
