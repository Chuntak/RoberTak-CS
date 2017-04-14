package com.backpack.dao;

import com.backpack.models.SyllabusModel;
import com.google.cloud.storage.Blob;

/**
 * Created by Chuntak on 4/12/2017.
 */
public class SyllabusDAO extends DAOBase {
    public String uploadSyllabus(SyllabusModel syllabus){
        Blob blob = dbs.uploadFile(syllabus.getFile());
        return blob.getMediaLink();
    }
}
