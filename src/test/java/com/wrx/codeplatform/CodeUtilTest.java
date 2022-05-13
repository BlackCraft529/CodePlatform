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
                        "#include<iostream>\n" +
                                "using namespace std;\n" +
                                "#define FALSE 0\n" +
                                "#define TRUE 1\n" +
                                "#define OK 1\n" +
                                "#define m 3\t\t\t\t\t\t//B-树的阶，暂设为3\n" +
                                "typedef struct BTNode{\n" +
                                "\tint keynum;\t\t\t\t\t//结点中关键字的个数，即结点的大小\n" +
                                "\tBTNode *parent;\t\t\t\t//指向双亲结点\n" +
                                "\tint key[m+1];\t\t\t\t//关键字矢量，0号单元未用\n" +
                                "\tBTNode *ptr[m+1];\t\t\t//子树指针矢量\n" +
                                "}BTNode,*BTree;\n" +
                                "\n" +
                                "//- - - - - B-树的查找结果类型定义- - - - -\n" +
                                "struct Result{\n" +
                                "  BTNode *pt;     \t\t\t\t\t\t\t//指向找到的结点\n" +
                                "  int i;           \t\t\t\t\t\t\t//1..m，在结点中的关键字序号\n" +
                                "  int tag;         \t\t\t\t\t\t\t//1：查找成功，0：查找失败\n" +
                                "}; \t                           \n" +
                                "\n" +
                                "\n" +
                                "int Search(BTree T,int key)\n" +
                                "{\n" +
                                "\tBTree p=T;\t\n" +
                                "\tint endnum;\n" +
                                "\tif(p)\t\t\t\t\t\t//树不为空时\n" +
                                "\t{\n" +
                                "\t\tendnum=p->keynum;\t\t//获得首节点包含的记录个数\n" +
                                "\t}\n" +
                                "\telse\n" +
                                "\t{\n" +
                                "\t\treturn 0;\t\t\t\t//返回没找到\n" +
                                "\t}\n" +
                                "\tint i=0;\n" +
                                "\tif(endnum==0)\n" +
                                "\t{\n" +
                                "\t\treturn i;\t\t\t\t//树存在，但仅有一个为空根节点\n" +
                                "\t}\n" +
                                "\telse if(key>=p->key[endnum])//节点不为空，但当前值比最大的key还大\n" +
                                "\t{\n" +
                                "\t\ti=endnum;\n" +
                                "\t\treturn i;\n" +
                                "\t}\n" +
                                "\telse if(key<=p->key[1])\t\t//节点不为空，但当前值比最小的key还小\n" +
                                "\t{\n" +
                                "\t\treturn i;}\n" +
                                "\telse\n" +
                                "\t{\n" +
                                "\t\tfor(i=1;i<endnum;i++)\t//有合适的位置，即处于当前结点的最大和最小值之间，或找到了\n" +
                                "\t\t{\n" +
                                "\t\t\tif(p->key[i]<=key && key<p->key[i+1])\n" +
                                "\t\t\t\treturn i;\n" +
                                "\t\t}\n" +
                                "\t}\n" +
                                "}\n" +
                                "\n" +
                                "void Insert(BTree &q,int i,int x,BTree &ap)\n" +
                                "{//将x插入q结点的i+1位置中\n" +
                                "\tint j;\n" +
                                "\tfor(j=m-1;j>i;j--)\t\t\t\n" +
                                "\t{\n" +
                                "\t\t//将插入位置之后的key全部后移一位\n" +
                                "\t\tq->key[j+1]=q->key[j];\n" +
                                "\t}\n" +
                                "\tfor(j=m;j>i;j--)\n" +
                                "\t{\n" +
                                "\t\t//相应地也移动其后ptr的位置\n" +
                                "\t\tq->ptr[j]=q->ptr[j-1];\n" +
                                "\t}\n" +
                                "\tq->key[i+1]=x;//插入x到该位置\n" +
                                "\tq->ptr[i+1]=ap;\n" +
                                "\tq->keynum++;\n" +
                                "}\n" +
                                "\n" +
                                "void split(BTree &q,int s,BTree &ap)\n" +
                                "{\t//将q->key[s+1,..,m], q->ptr[s+1,..,m]移入新结点*ap作为右结点\n" +
                                "\t//原结点作为新的左侧结点\n" +
                                "\t//中间值被保存在ap[0]->key中，等待找到跳转回InsertBTree（）寻找到到合适的插入位置插入\n" +
                                "\tint i;\n" +
                                "\tap=new BTNode;\n" +
                                "\tfor(i=s+1;i<=m;i++)\n" +
                                "\t{\t//将q->key[s+1,..,m]保存到ap->key[0,..,m-s+1]中\n" +
                                "\t\t//将q->ptr[s+1,..,m]保存到ap->ptr[0,..,m-s+1]中\n" +
                                "\t\tap->key[i-s-1]=q->key[i];\t\n" +
                                "\t\tap->ptr[i-s-1]=q->ptr[i];\n" +
                                "\t}\n" +
                                "\tif(ap->ptr[0])\n" +
                                "\t{\n" +
                                "\t\t//当ap有子树的时候\n" +
                                "\t\tfor(i=0;i<=1;i++)\n" +
                                "\t\t{\n" +
                                "\t\t\t//将ap的子树的父亲改为ap自己\n" +
                                "\t\t\tap->ptr[i]->parent=ap;\n" +
                                "\t\t}\n" +
                                "\t}\n" +
                                "\tap->keynum=(m-s)-1;\n" +
                                "\tap->parent=q->parent;//将ap的父亲改为q的父亲\n" +
                                "\n" +
                                "\tq->keynum=q->keynum-(m-s);//修改q的记录个数\n" +
                                "}\n" +
                                "\n" +
                                "void NewRoot(BTree &T,BTree q,int x,BTree &ap)//生成含信息（T, x, ap）的新的根结点*T，原T和ap为子树指针\n" +
                                "{\n" +
                                "\tBTree newT=new BTNode;//新建一个结点作为新的根\n" +
                                "\t\n" +
                                "\tnewT->key[1]=x;//写入新根的key[1]\n" +
                                "\tnewT->ptr[0]=T;//将原来的树根作为新根的左子树\n" +
                                "\tnewT->ptr[1]=ap;//ap作为新根的右子树\n" +
                                "\tnewT->keynum=1;\n" +
                                "\tnewT->parent=NULL;//新根的父亲为空\n" +
                                "\n" +
                                "\tap->parent=newT;//ap的父亲为新根\n" +
                                "\tT->parent=newT;//T的父亲为新根\n" +
                                "\n" +
                                "\tT=newT;//树改成新根引导的\n" +
                                "}\n" +
                                "\n" +
                                "//算法7.9　B-树的插入\n" +
                                "int InsertBTree(BTree &T,int K,BTree q,int i){\n" +
                                "  int x=K;\n" +
                                "  BTree ap=NULL;\n" +
                                "  int finished=FALSE;//x表示新插入的关键字，ap为一个空指针\n" +
                                "  while(q&&!finished){\n" +
                                "    Insert(q,i,x,ap);      \t\t//将x和ap分别插入到q->key[i+1]和q->ptr[i+1]\n" +
                                "    if (q->keynum<m)\n" +
                                "\t\tfinished=TRUE;   \t//插入完成\n" +
                                "    else{                      \t//分裂结点*q\n" +
                                "     int s= m/2;\n" +
                                "\t split(q,s,ap);\n" +
                                "\tx=ap->key[0];//\t x=q->key[s];\n" +
                                "      //将q->key[s+1..m], q->ptr[s..m]和q->recptr[s+1..m] 移入新结点*ap\n" +
                                "      q=q->parent;\n" +
                                "      if(q)\n" +
                                "\t  {\n" +
                                "\t\t  i=Search(q,x);\n" +
                                "\t  }\t\t//在双亲结点*q中查找x的插入位置\n" +
                                "\t}\t\t\t\t\t\t//else\n" +
                                "  }\t\t\t\t\t\t\t//while\n" +
                                "  if(!finished)    \t\t\t//T是空树（参数q初值为NULL）或者根结点已分裂为结点*q和*ap\n" +
                                "      NewRoot(T,q,x,ap);\t\t//生成含信息（T, x, ap）的新的根结点*T，原T和ap为子树指针\n" +
                                "  return  OK;\n" +
                                "}\t\t\t\t\t\t\t//InsertBTree\t\t\t\t\t\t//InsertBTree\n" +
                                "\n" +
                                "//算法7.8　B-树的查找\n" +
                                "Result SearchBTree(BTree &T, int key){\n" +
                                "\t/*在m阶B-树T上查找关键字key，返回结果(pt,i,tag)。若查找成功，则特征值tag=1，指针pt所指结点中第i个关键字等于key；否则特征值tag=0，等于key的关键字应插入在指针pt所指结点中第i和第i+1个关键字之间*/\n" +
                                "\tBTree p=T;\n" +
                                "\tBTree q=NULL;\n" +
                                "\tint found=FALSE;\n" +
                                "\tint i=0;\t\t\t//初始化，p指向待查结点，q指向p的双亲\n" +
                                "while(p&&!found){\n" +
                                "    i=Search(p,key);               \t\n" +
                                "    //在p-＞key[1..keynum]中查找i，使得：p-＞key[i]＜=key＜p-＞key[i+1]\n" +
                                "    if(i>0&&p->key[i]==key)\n" +
                                "\t\tfound=TRUE;\t\t//找到待查关键字\n" +
                                "    else\n" +
                                "\t{\n" +
                                "\t\tq=p;\n" +
                                "\t\tp=p->ptr[i];\n" +
                                "\t}\n" +
                                "  }\n" +
                                "\tResult result;\n" +
                                "if(found)\n" +
                                "{\n" +
                                "\tresult.pt=p;\n" +
                                "\tresult.i=i;\n" +
                                "\tresult.tag=1;\n" +
                                "\treturn result;\n" +
                                "}              \t\t//查找成功\n" +
                                "else\n" +
                                "{\n" +
                                "\tresult.pt=q;\n" +
                                "\tresult.i=i;\n" +
                                "\tresult.tag=0;\n" +
                                "\treturn result;\n" +
                                "}              \t\t\t//查找不成功，返回K的插入位置信息\n" +
                                "}//SearchBTree\n" +
                                "\n" +
                                "void InitialBTree(BTree &T)\n" +
                                "{\n" +
                                "\t//初始化一个空的根\n" +
                                "\tT->keynum=0;\t\t\n" +
                                "\tT->parent=NULL;\t\n" +
                                "\tfor(int i=0;i<m+1;i++)\n" +
                                "\t{\n" +
                                "\t\tT->ptr[i]=NULL;\n" +
                                "\t}\n" +
                                "}\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "void main()\n" +
                                "{\n" +
                                "\tBTree T=new BTNode;\n" +
                                "\tInitialBTree(T);\n" +
                                "\t//先用SearchBTree()找到要插入的位置，得到一个Result结构体\n" +
                                "\t//再用InsertBTree()插入数据\n" +
                                "\tResult result;\n" +
                                "\tint a[11]={45,24,53,90,3,12,50,61,70,100};\n" +
                                "\tfor(int i=0;i<10;i++)\n" +
                                "\t{\n" +
                                "\t\tresult=SearchBTree(T,a[i]);\n" +
                                "\t\tif(result.tag==0)\n" +
                                "\t\t{\n" +
                                "\t\t\tInsertBTree(T,a[i],result.pt,result.i);\n" +
                                "\t\t}\n" +
                                "\t}\n" +
                                "\tcout<<\"OK\";\n" +
                                "}\n",
                "\n" +
                        "#include<iostream>\n" +
                        "using namespace std;\n" +
                        "#define FALSE 0\n" +
                        "#define TRUE 1\n" +
                        "#define OK 1\n" +
                        "#define m 3\t\t\t\t\t\t//B-树的阶，暂设为3\n" +
                        "typedef struct BTNode{\n" +
                        "\tint keynum;\t\t\t\t\t//结点中关键字的个数，即结点的大小\n" +
                        "\tBTNode *parent;\t\t\t\t//指向双亲结点\n" +
                        "\tint key[m+1];\t\t\t\t//关键字矢量，0号单元未用\n" +
                        "\tBTNode *ptr[m+1];\t\t\t//子树指针矢量\n" +
                        "}BTNode,*BTree;\n" +
                        "\n" +
                        "//- - - - - B-树的查找结果类型定义- - - - -\n" +
                        "struct Result{\n" +
                        "  BTNode *pt;     \t\t\t\t\t\t\t//指向找到的结点\n" +
                        "  int i;           \t\t\t\t\t\t\t//1..m，在结点中的关键字序号\n" +
                        "  int tag;         \t\t\t\t\t\t\t//1：查找成功，0：查找失败\n" +
                        "};\n" +
                        "\n" +
                        "\n" +
                        "int sousuo(BTree T,int key)\n" +
                        "{\n" +
                        "\tBTree p=T;\n" +
                        "\tint endnum;\n" +
                        "\tif(p)\t\t\t\t\t\t//树不为空时\n" +
                        "\t{\n" +
                        "\t\tendnum=p->keynum;\t\t//获得首节点包含的记录个数\n" +
                        "\t}\n" +
                        "\telse\n" +
                        "\t{\n" +
                        "\t\treturn 0;\t\t\t\t//返回没找到\n" +
                        "\t}\n" +
                        "\tint i=0;\n" +
                        "\tif(endnum==0)\n" +
                        "\t{\n" +
                        "\t\treturn i;\t\t\t\t//树存在，但仅有一个为空根节点\n" +
                        "\t}\n" +
                        "\telse if(key>=p->key[endnum])//节点不为空，但当前值比最大的key还大\n" +
                        "\t{\n" +
                        "\t\ti=endnum;\n" +
                        "\t\treturn i;\n" +
                        "\t}\n" +
                        "\telse if(key<=p->key[1])\t\t//节点不为空，但当前值比最小的key还小\n" +
                        "\t{\n" +
                        "\t\treturn i;}\n" +
                        "\telse\n" +
                        "\t{\n" +
                        "\t\tfor(i=1;i<endnum;i++)\t//有合适的位置，即处于当前结点的最大和最小值之间，或找到了\n" +
                        "\t\t{\n" +
                        "\t\t\tif(p->key[i]<=key && key<p->key[i+1])\n" +
                        "\t\t\t\treturn i;\n" +
                        "\t\t}\n" +
                        "\t}\n" +
                        "}\n" +
                        "\n" +
                        "void charuxindejiedian(BTree &q,int i,int x,BTree &ap)\n" +
                        "{//将x插入q结点的i+1位置中\n" +
                        "\tint j;\n" +
                        "\tfor(j=m-1;j>i;j--)\n" +
                        "\t{\n" +
                        "\t\t//将插入位置之后的key全部后移一位\n" +
                        "\t\tq->key[j+1]=q->key[j];\n" +
                        "\t}\n" +
                        "\tfor(j=m;j>i;j--)\n" +
                        "\t{\n" +
                        "\t\t//相应地也移动其后ptr的位置\n" +
                        "\t\tq->ptr[j]=q->ptr[j-1];\n" +
                        "\t}\n" +
                        "\tq->key[i+1]=x;//插入x到该位置\n" +
                        "\tq->ptr[i+1]=ap;\n" +
                        "\tq->keynum++;\n" +
                        "}\n" +
                        "\n" +
                        "void fenge_function(BTree &q,int s,BTree &ap)\n" +
                        "{\t//将q->key[s+1,..,m], q->ptr[s+1,..,m]移入新结点*ap作为右结点\n" +
                        "\t//原结点作为新的左侧结点\n" +
                        "\t//中间值被保存在ap[0]->key中，等待找到跳转回InsertBTree（）寻找到到合适的插入位置插入\n" +
                        "\tint i;\n" +
                        "\tap=new BTNode;\n" +
                        "\tfor(i=s+1;i<=m;i++)\n" +
                        "\t{\t//将q->key[s+1,..,m]保存到ap->key[0,..,m-s+1]中\n" +
                        "\t\t//将q->ptr[s+1,..,m]保存到ap->ptr[0,..,m-s+1]中\n" +
                        "\t\tap->key[i-s-1]=q->key[i];\n" +
                        "\t\tap->ptr[i-s-1]=q->ptr[i];\n" +
                        "\t}\n" +
                        "\tif(ap->ptr[0])\n" +
                        "\t{\n" +
                        "\t\t//当ap有子树的时候\n" +
                        "\t\tfor(i=0;i<=1;i++)\n" +
                        "\t\t{\n" +
                        "\t\t\t//将ap的子树的父亲改为ap自己\n" +
                        "\t\t\tap->ptr[i]->parent=ap;\n" +
                        "\t\t}\n" +
                        "\t}\n" +
                        "\tap->keynum=(m-s)-1;\n" +
                        "\tap->parent=q->parent;//将ap的父亲改为q的父亲\n" +
                        "\n" +
                        "\tq->keynum=q->keynum-(m-s);//修改q的记录个数\n" +
                        "}\n" +
                        "\n" +
                        "void xinjiedianshengcheng(BTree &T,BTree q,int x,BTree &ap)//生成含信息（T, x, ap）的新的根结点*T，原T和ap为子树指针\n" +
                        "{\n" +
                        "\tBTree newT=new BTNode;//新建一个结点作为新的根\n" +
                        "\n" +
                        "\tnewT->key[1]=x;//写入新根的key[1]\n" +
                        "\tnewT->ptr[0]=T;//将原来的树根作为新根的左子树\n" +
                        "\tnewT->ptr[1]=ap;//ap作为新根的右子树\n" +
                        "\tnewT->keynum=1;\n" +
                        "\tnewT->parent=NULL;//新根的父亲为空\n" +
                        "\n" +
                        "\tap->parent=newT;//ap的父亲为新根\n" +
                        "\tT->parent=newT;//T的父亲为新根\n" +
                        "\n" +
                        "\tT=newT;//树改成新根引导的\n" +
                        "}\n" +
                        "\n" +
                        "//算法7.9　B-树的插入\n" +
                        "int InsertBTree(BTree &T,int K,BTree q,int i){\n" +
                        "  int x=K;\n" +
                        "  BTree ap=NULL;\n" +
                        "  int finished=FALSE;//x表示新插入的关键字，ap为一个空指针\n" +
                        "  while(q&&!finished){\n" +
                        "    charuxindejiedian(q,i,x,ap);      \t\t//将x和ap分别插入到q->key[i+1]和q->ptr[i+1]\n" +
                        "    if (q->keynum<m)\n" +
                        "\t\tfinished=TRUE;   \t//插入完成\n" +
                        "    else{                      \t//分裂结点*q\n" +
                        "     int s= m/2;\n" +
                        "\t fenge_function(q,s,ap);\n" +
                        "\tx=ap->key[0];//\t x=q->key[s];\n" +
                        "      //将q->key[s+1..m], q->ptr[s..m]和q->recptr[s+1..m] 移入新结点*ap\n" +
                        "      q=q->parent;\n" +
                        "      if(q)\n" +
                        "\t  {\n" +
                        "\t\t  i=sousuo(q,x);\n" +
                        "\t  }\t\t//在双亲结点*q中查找x的插入位置\n" +
                        "\t}\t\t\t\t\t\t//else\n" +
                        "  }\t\t\t\t\t\t\t//while\n" +
                        "  if(!finished)    \t\t\t//T是空树（参数q初值为NULL）或者根结点已分裂为结点*q和*ap\n" +
                        "      xinjiedianshengcheng(T,q,x,ap);\t\t//生成含信息（T, x, ap）的新的根结点*T，原T和ap为子树指针\n" +
                        "  return  OK;\n" +
                        "}\t\t\t\t\t\t\t//InsertBTree\t\t\t\t\t\t//InsertBTree\n" +
                        "\n" +
                        "//算法7.8　B-树的查找\n" +
                        "Result SearchBTree(BTree &T, int key){\n" +
                        "\t/*在m阶B-树T上查找关键字key，返回结果(pt,i,tag)。若查找成功，则特征值tag=1，指针pt所指结点中第i个关键字等于key；否则特征值tag=0，等于key的关键字应插入在指针pt所指结点中第i和第i+1个关键字之间*/\n" +
                        "\tBTree p=T;\n" +
                        "\tBTree q=NULL;\n" +
                        "\tint found=FALSE;\n" +
                        "\tint i=0;\t\t\t//初始化，p指向待查结点，q指向p的双亲\n" +
                        "while(p&&!found){\n" +
                        "    i=sousuo(p,key);\n" +
                        "    //在p-＞key[1..keynum]中查找i，使得：p-＞key[i]＜=key＜p-＞key[i+1]\n" +
                        "    if(i>0&&p->key[i]==key)\n" +
                        "\t\tfound=TRUE;\t\t//找到待查关键字\n" +
                        "    else\n" +
                        "\t{\n" +
                        "\t\tq=p;\n" +
                        "\t\tp=p->ptr[i];\n" +
                        "\t}\n" +
                        "  }\n" +
                        "\tResult result;\n" +
                        "if(found)\n" +
                        "{\n" +
                        "\tresult.pt=p;\n" +
                        "\tresult.i=i;\n" +
                        "\tresult.tag=1;\n" +
                        "\treturn result;\n" +
                        "}              \t\t//查找成功\n" +
                        "else\n" +
                        "{\n" +
                        "\tresult.pt=q;\n" +
                        "\tresult.i=i;\n" +
                        "\tresult.tag=0;\n" +
                        "\treturn result;\n" +
                        "}              \t\t\t//查找不成功，返回K的插入位置信息\n" +
                        "}//SearchBTree\n" +
                        "\n" +
                        "void InitialBTree(BTree &T)\n" +
                        "{\n" +
                        "\t//初始化一个空的根\n" +
                        "\tT->keynum=0;\n" +
                        "\tT->parent=NULL;\n" +
                        "\tfor(int i=0;i<m+1;i++)\n" +
                        "\t{\n" +
                        "\t\tT->ptr[i]=NULL;\n" +
                        "\t}\n" +
                        "}\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "void main()\n" +
                        "{\n" +
                        "\tBTree T=new BTNode;\n" +
                        "\tInitialBTree(T);\n" +
                        "\t//先用SearchBTree()找到要插入的位置，得到一个Result结构体\n" +
                        "\t//再用InsertBTree()插入数据\n" +
                        "\tResult result;\n" +
                        "\tint a[11]={45,24,53,90,3,12,50,61,70,100};\n" +
                        "\tfor(int i=0;i<10;i++)\n" +
                        "\t{\n" +
                        "\t\tresult=SearchBTree(T,a[i]);\n" +
                        "\t\tif(result.tag==0)\n" +
                        "\t\t{\n" +
                        "\t\t\tInsertBTree(T,a[i],result.pt,result.i);\n" +
                        "\t\t}\n" +
                        "\t}\n" +
                        "\tcout<<\"OK\";\n" +
                        "}\n",};
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
