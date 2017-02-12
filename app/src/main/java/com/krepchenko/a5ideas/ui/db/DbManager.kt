package com.krepchenko.a5ideas.ui.db

import android.content.ContentResolver
import android.content.ContentValues

/**
 * Created by ann on 2/12/17.
 */
open class DbManager{

    companion object IdeaManger{
        fun insertIdea(contentResolver: ContentResolver,idea: Idea){
            Thread(Runnable {  contentResolver.insert(IdeaContentProvider.IDEAS, ideaToContentValues(idea))}).start()
        }

        private fun ideaToContentValues(idea: Idea) : ContentValues{
            val contentValues: ContentValues = ContentValues()
            contentValues.put("name",idea.name)
            contentValues.put("description", idea.description)
            return contentValues
        }
    }


}