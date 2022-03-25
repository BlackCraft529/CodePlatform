package com.wrx.codeplatform;

import com.wrx.codeplatform.utils.code.TransformersModel;
import com.wrx.codeplatform.utils.code.impl.SentenceTransformersModel;
import org.junit.jupiter.api.Test;
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
