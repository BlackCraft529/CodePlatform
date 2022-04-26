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
     * TODO: 增加 0.x相似度代码的训练集，减少短代码训练，增加 变量修改、循环变种、变量名修改的训练集。
     */
    @Test
    public void getCode(){
        String codes[] = new String[]{
                        "int main()\n" +
                                "{\n" +
                                "    int iChicken, jChicken, kChicken;\n" +
                                "\n" +
                                "    for( iChicken=0; iChicken <= 100; iChicken++ )\n" +
                                "        for( jChicken=0; jChicken <= 100; jChicken++ )\n" +
                                "            for( kChicken=0; kChicken <= 100; kChicken++ )\n" +
                                "            {\n" +
                                "                if( 5*iChicken+3*jChicken+kChicken/3==100 && kChicken%3==0 && iChicken+jChicken+kChicken==100 )\n" +
                                "                {\n" +
                                "                    printf(\"1. %2d s，2. %2d s，3. %2d s\\n\", iChicken, jChicken, kChicken);\n" +
                                "                }\n" +
                                "            }\n" +
                                "\n" +
                                "    return 0;\n" +
                                "}",
                "int main()\n" +
                        "{\n" +
                        "    int i, j, k;\n" +
                        "\n" +
                        "    printf(\"result：\\n\");\n" +
                        "\n" +
                        "    for( i=0; i <= 100; i++ )\n" +
                        "        for( j=0; j <= 100; j++ )\n" +
                        "            for( k=0; k <= 100; k++ )\n" +
                        "            {\n" +
                        "                if( 5*i+3*j+k/3==100 && k%3==0 && i+j+k==100 )\n" +
                        "                {\n" +
                        "                    printf(\"gongji %2d s，muji %2d s，xiaoji %2d s\\n\", i, j, k);\n" +
                        "                }\n" +
                        "            }\n" +
                        "\n" +
                        "    return 0;\n" +
                        "}",};
        List<String> codesList = new ArrayList<>();
        for (String code : codes) {
            String args = code;
            args = DelComments.delComments(args);
            args = args.replaceAll("\n", "").replaceAll(" ", "").replaceAll("\t", "");
            codesList.add(args);
        }
        for (String s: codesList){
            System.out.println("    "+s);
        }
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
