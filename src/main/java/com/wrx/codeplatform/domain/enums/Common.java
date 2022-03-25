package com.wrx.codeplatform.domain.enums;

/**
 * @author 魏荣轩
 * @date 2022/3/25 23:00
 */
public enum Common {

    EVERY_PAGE_EVALUATIONS(10);

    private int par;
    Common(int par){
        this.par = par;
    }
    public int getPar(){
        return par;
    }
}
