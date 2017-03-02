package com.krepchenko.a5ideas.ui.db

import android.content.ContentResolver
import android.content.ContentValues
import com.krepchenko.a5ideas.DB

/**
 * Created by ann on 2/12/17.
 */
open class DbManager {

    companion object IdeaManger {
        fun insertIdea(contentResolver: ContentResolver, idea: Idea) {
            Thread(Runnable { contentResolver.insert(IdeaContentProvider.IDEAS, ideaToContentValues(idea)) }).start()
        }

        private fun ideaToContentValues(idea: Idea): ContentValues {
            val contentValues: ContentValues = ContentValues()
            if(idea.id>0){
                contentValues.put(DB.Columns.Ideas.Id,idea.id)
            }
            contentValues.put(DB.Columns.Ideas.Name, idea.name)
            contentValues.put(DB.Columns.Ideas.Description, idea.description)
            contentValues.put(DB.Columns.Ideas.PictureUri, idea.pictureUri)
            return contentValues
        }
    }


}