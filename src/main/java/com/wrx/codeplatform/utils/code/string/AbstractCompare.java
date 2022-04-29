package com.wrx.codeplatform.utils.code.string;

/**
 * @author 魏荣轩
 * @date 2020/10/28 22:27
 */
public abstract class AbstractCompare {
	/**
	 * 提取预处理代码段
	 *
	 * @param filePath 文件路径
	 * @return 内容
	 */
	public abstract String getPreProcessedCode(String filePath);

    /**
     * 提取代码相似度百分比
     *
     * @param code1 代码1
     * @param code2 代码2
     * @return 百分比
     */
	public abstract double getSimilarity(String code1,String code2);
}
