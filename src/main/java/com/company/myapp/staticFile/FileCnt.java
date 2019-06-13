package com.company.myapp.staticFile;

/**
 * 保存文件类型与数目的结构
 */
class FileCnt {
    String suffix;// 扩展名
    int num;// 文件的个数

    @Override
    public String toString() {
        return "FileCnt{" +
            "suffix='" + suffix + '\'' +
            ", num=" + num +
            '}';
    }

    public FileCnt(String suffix, int num) {
        this.suffix = suffix;
        this.num = num;
    }

    public String getSuffix() {

        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void increaseNum() {
        this.num++;
    }
}
