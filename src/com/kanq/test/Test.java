package com.kanq.test;

public class Test {
    public static void main(String[] args) {
    	String templetPath = "D://lmcfxkzdzz.dot"; //模版文件  
    	String otPath = "D://test.doc"; //保存文件  
    	GF_JacobUtil word = new GF_JacobUtil();  
    	try {  
    	    //是否显示打开word  
    	    word.openWord(false);  
    	    //打开模版文件  
    	    word.openDocument(templetPath);  
    	    //替换书签内容  
////    	    word.replaceBookMark("Prj_Name", "项目");  
    	   
//    	       sword.replaceBookMark("采字左", "sda");
//    	       word.replaceBookMark("编号", "1313123");
//    	       word.replaceBookMark("采字左", "的撒大大");
//    	       word.replaceBookMark("采字20","asdadad");
//             word.replaceBookMark("采字号", "虚心3241");
//             word.replaceBookMark("开头栏", "阿萨辛");
//             word.replaceBookMark("根据", "三大殿");
//             word.replaceBookMark("林场", "435sefsdf");
//             word.replaceBookMark("林班", "32313");
//             word.replaceBookMark("作业区", "阿斯达的");
//             word.replaceBookMark("小班", "313231"); 
//             word.replaceBookMark("采伐四至东", "sadasd");
//             word.replaceBookMark("采伐四至南", "asdasd");
//             word.replaceBookMark("采伐四至西", "asdasd");
//             word.replaceBookMark("采伐四至北", "sdad");
    	   
    	    //保存到path  
    	    word.saveFileAs(otPath );  
    	} catch (Exception ex) {  
    	    ex.printStackTrace();  
    	} finally {  
    	    //关闭Word  
    	    word.closeDocument();  
    	    word.closeWord();  
    	}  
	}
}
