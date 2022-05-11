package com.wrx.codeplatform;

import com.wrx.codeplatform.utils.code.TransformersModel;
import com.wrx.codeplatform.utils.code.impl.SentenceTransformersModel;
import com.wrx.codeplatform.utils.code.util.DelComments;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author 魏荣轩
 * @date 2022/3/13 16:10
 */
public class CodeUtilTest {

    public class Calculator{
        public double add(double par1, double par2){
            return par1+par2;
        }
        public double sub(double par1, double par2){
            return par1-par2;
        }
        public double mul(double par1, double par2){
            return par1*par2;
        }
        public double div(double par1, double par2){
            return par1/par2;
        }
    }

    /**
     * 分类：
     *      1.仅替换变量名： 5.0
     *      2.增加/删除无关语句： 4.5
     *      3.变更循环结构： 3.5
     *      4.算法不同： 0.0
     *
     * TODO：
     *      增加子函数代码段
     */
    @Test
    public void getCode(){
        String codes[] = new String[]{
                        "#include <stdio.h>\n" +
                                " \n" +
                                "void output(long int b, long int i){\n" +
                                "    printf(\"\\n%ld = 800 * %ld + 9 * %ld\\n\", b,i,i);\n" +
                                "}\n" +
                                " \n" +
                                " \n" +
                                "int main(){\n" +
                                " \n" +
                                "    void output(long int b, long int i);\n" +
                                "    long int a,b,i;\n" +
                                "    a = 809;\n" +
                                "    for(i = 10; i < 100; i++){\n" +
                                "        b = i * a;\n" +
                                "        if (b >= 1000 && b <= 10000 && 8 * i < 100 && 9 * i >= 100){\n" +
                                "            output(b, i);\n" +
                                "        }\n" +
                                "    }\n" +
                                "    return 0;\n" +
                                "}",
                "#include <stdio.h>\n" +
                        "\n" +
                        "void shuchuhanshu(long int b, long int i){\n" +
                        "    printf(\"\\n%ld = 800 * %ld + 9 * %ld\\n\", b,i,i);\n" +
                        "}\n" +
                        "\n" +
                        "\n" +
                        "int main(){\n" +
                        "\n" +
                        "    void shuchuhanshu(long int b, long int i);\n" +
                        "    long int a,b,i;\n" +
                        "    a = 809;\n" +
                        "    for(i = 10; i < 100; i++){\n" +
                        "        b = i * a;\n" +
                        "        if (b >= 1000 && b <= 10000 && 8 * i < 100 && 9 * i >= 100){\n" +
                        "            output(b, i);\n" +
                        "        }\n" +
                        "    }\n" +
                        "    return 0;\n" +
                        "}",};
        List<String> codesList = new ArrayList<>();
        String REGEX_CHINESE = "[\u4e00-\u9fa5]";
        for (String code : codes) {
            String args = code;
            args = DelComments.delComments(args);
            args = args.replaceAll("\n","").replaceAll(" +"," ").replaceAll("\t","").replaceAll(REGEX_CHINESE, "");
            codesList.add(args);
        }
        System.out.println(codesList.get(0)+"\t"+codesList.get(1));

    }

    @Test
    public void test(){
        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);
        double par1 = scanner.nextDouble();
        double par2 = scanner.nextDouble();
        System.out.println(calculator.add(par1, par2));
    }

    @Test
    public void testSentenceTransformersModel(){
        TransformersModel transformersModel = new SentenceTransformersModel();
        String code1 = "int main(){\n" +
                "\tint a[N][N],n;\n" +
                "\tscanf(\"%d\",&n);\n" +
                "\tfor(int i=0;i<n;i++)\n" +
                "\t\tfor(int j=0;j<n;j++)\n" +
                "\t\t\tscanf(\"%d\",&a[i][j]);\n" +
                "\tprintf(\"%d\\n\",fun(a,n));\n" +
                "\treturn 0;\n" +
                "}",
                code2 = "#include<stdio.h>\n" +
                        "int main(){\n" +
                        "\tchar a[100];\n" +
                        "\tint j;\n" +
                        "\tgets(a);\n" +
                        "\tfor(j=0;a[j]!='\\0';j++);\n" +
                        "\tprintf(\"Length=%d\\n\",j);\n" +
                        "\treturn 0;\n" +
                        "}";
        List<String> res = transformersModel.getModelOut(code1, code2);
        System.out.println("相似度: "+res.get(res.size()-1));
    }
}
