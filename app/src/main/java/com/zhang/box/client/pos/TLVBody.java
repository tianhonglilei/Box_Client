package com.zhang.box.client.pos;

public class TLVBody {

    /**
     * 数据类型
     */
    private String title;
    /**
     * 数据长度
     */
    private String length;
    /**
     * 数据内容
     */
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLength() {
        return length;
    }

    /**
     * 计算数据长度
     */
    public void setLength() {
        int length = getContent().length() / 2;
        this.length = Test.longTo16(length, 4);
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        setLength();
        return "TLVBody{" +
                "title='" + title + '\'' +
                ", length='" + length + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String toHex() {
        setLength();
        return title + length + content;
    }
}
