package com.example.bean;

import com.evernote.edam.error.EDAMNotFoundException;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.thrift.TException;

public class keywordEx {
    private String article;
    private String keyWord;

    public keywordEx() {
        setKeyWord(""+"hello world");

    }

    public void keyWordExtration(){

    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getKeyWord() throws TException, EDAMNotFoundException, EDAMSystemException, EDAMUserException {
        testRank tr=new testRank();
        String kw=tr.getKeyWords("",article);
        makeNote.makeNoteIn(kw,article);
        return kw;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
