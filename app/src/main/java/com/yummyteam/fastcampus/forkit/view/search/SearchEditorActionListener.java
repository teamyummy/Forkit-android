package com.yummyteam.fastcampus.forkit.view.search;

import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

/**
 * Created by Dabin on 2016-12-08.
 */

public class SearchEditorActionListener implements TextView.OnEditorActionListener {
    private SearchInterface mListner;

    SearchEditorActionListener(SearchInterface mListener){

        this.mListner = mListener;
    }
    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if(actionId == EditorInfo.IME_ACTION_SEARCH)
        {
            String keyWord = textView.getText().toString();
            if(keyWord.length() ==0){
                mListner.showAlert();
            }else{
                mListner.getKeyWord(keyWord);
            }
            Log.e("onListener","keyword = " + keyWord);

        }
        return true;
    }
}
