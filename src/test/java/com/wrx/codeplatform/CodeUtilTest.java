package com.wrx.codeplatform;

import com.wrx.codeplatform.utils.code.CodeFactory;
import com.wrx.codeplatform.utils.code.impl.CodeFactoryImpl;
import com.wrx.codeplatform.utils.common.TxtFileUtils;
import org.junit.jupiter.api.Test;

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
}
