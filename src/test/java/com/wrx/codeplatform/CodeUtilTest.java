package com.wrx.codeplatform;

import com.wrx.codeplatform.framework.controller.UserController;
import com.wrx.codeplatform.utils.code.CodeFactory;
import com.wrx.codeplatform.utils.code.TransformersModel;
import com.wrx.codeplatform.utils.code.impl.CodeFactoryImpl;
import com.wrx.codeplatform.utils.code.impl.SentenceTransformersModel;
import com.wrx.codeplatform.utils.common.TxtFileUtils;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/13 16:10
 */
public class CodeUtilTest {
    @Test
    public void testCodeUtil(){
        CodeFactory codeFactory = new CodeFactoryImpl();
        StringBuffer buf = TxtFileUtils.readTxtFile("codes/1.c");
        codeFactory.getCleanCodePiece(buf.toString());
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
