package com.example.bean;

import com.evernote.edam.error.EDAMNotFoundException;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.edam.notestore.NoteStore;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Notebook;
import com.evernote.thrift.TException;
import com.evernote.thrift.protocol.TBinaryProtocol;
import com.evernote.thrift.transport.THttpClient;
import com.evernote.thrift.transport.TTransportException;

import java.util.ArrayList;
import java.util.List;

public class makeNote {
    private static String authToken = "S=s22:U=1a71345:E=17bc05f8cd7:C=17b9c530660:P=1cd:A=en-devtoken:V=2:H=fee636d01d97a85cbb2c01f0bcaaacf1";

    private static String noteStoreUrl ="https://app.yinxiang.com/shard/s22/notestore";




    public static Note makeNoteIn(String noteTitle,String noteBody) throws TException, EDAMNotFoundException, EDAMSystemException, EDAMUserException {
        THttpClient noteStoreTrans = new THttpClient(noteStoreUrl);
        TBinaryProtocol noteStoreProt = new TBinaryProtocol(noteStoreTrans);
        NoteStore.Client noteStore = new NoteStore.Client(noteStoreProt, noteStoreProt);
        Notebook notebook = new Notebook();
        List<String> kwList=mkStrToList(noteTitle);

        return makeNoteIn(noteStore,noteTitle,noteBody,kwList,notebook);
    }


    public static Note makeNoteIn(NoteStore.Client noteStore, String noteTitle,String noteBody, List<String> tags, Notebook parentNotebook) throws TException, EDAMNotFoundException, EDAMSystemException, EDAMUserException {

        String nBody = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        nBody += "<!DOCTYPE en-note SYSTEM \"http://xml.evernote.com/pub/enml2.dtd\">";
        nBody += "<en-note>" + noteBody + "</en-note>";

        // Create note object
        Note ourNote = new Note();
        ourNote.setTitle(noteTitle);
        ourNote.setContent(nBody);
        //List<String> tag=makeTag(noteStore,tags);
        ourNote.setTagNames(tags);


        // parentNotebook is optional; if omitted, default notebook is used
        if (parentNotebook != null && parentNotebook.isSetGuid()) {
            ourNote.setNotebookGuid(parentNotebook.getGuid());
        }

        // Attempt to create note in Evernote account
        Note note = null;
        try {
            note = noteStore.createNote(authToken,ourNote);
        } catch (EDAMUserException edue) {
            // Something was wrong with the note data
            // See EDAMErrorCode enumeration for error code explanation
            // http://dev.yinxiang.com/documentation/reference/Errors.html#Enum_EDAMErrorCode
            System.out.println("EDAMUserException: " + edue);
        } catch (EDAMNotFoundException ednfe) {
            // Parent Notebook GUID doesn't correspond to an actual notebook
            System.out.println("EDAMNotFoundException: Invalid parent notebook GUID");
        } catch (Exception e) {
            // Other unexpected exceptions
            e.printStackTrace();
        }

        // Return created note object
        return note;

    }


    public static List<String> mkStrToList(String str){
        List<String> keyword=new ArrayList<>();
        str = str.replaceAll("\\s", "");
        String []kw=str.split("#");
        for (String i:kw
             ) {
            if (!i.equals("")) {
                keyword.add(i);
            }
        }
        return keyword;
    }

    public static void main(String[] args) throws TException, EDAMNotFoundException, EDAMSystemException, EDAMUserException {
        String s="计算机#数据结构#bbs#   #d";

        System.out.println(mkStrToList(s));
        String sd="sfhjasdhfjaksjdhfkjahsdfj";
        //makeNoteIn(s,sd);
    }



}
