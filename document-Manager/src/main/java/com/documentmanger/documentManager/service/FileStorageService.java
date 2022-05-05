package com.documentmanger.documentManager.service;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.documentmanger.documentManager.model.Doc;
import com.documentmanger.documentManager.model.FileDB;
import com.documentmanger.documentManager.repository.FileDBRepository;
import com.documentmanger.documentManager.repository.docrepository;
//import org.apache.commons.io.FilenameUtils;
//String fileNameWithOutExt = FilenameUtils.removeExtension(fileNameWithExt);
@Service
public class FileStorageService {
  @Autowired
  private FileDBRepository fileDBRepository;
  
  
  public FileDB store(MultipartFile file) throws IOException {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//    int pos = fileName.lastIndexOf(".");
//    if (pos > 0 && pos < (fileName.length() - 1)) { // If '.' is not the first or last character.
//        fileName = fileName.substring(0, pos);
//    }
    FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
    return fileDBRepository.save(FileDB);
  }
  
//  public Doc storedoc(Doc newDoc){
//	  Doc Doc = new Doc(newDoc.getName(),newDoc.getDesc());
//	  return docrepo.save(Doc);
//}
  
//  public FileDB update(FileDB newFileDB,String id) {
//	  return fileDBRepository.findById(id)
//		      .map(FileDB -> {
//		    	  FileDB.setId(newFileDB.getId());
//		        FileDB.setName(newFileDB.getName());
//		        FileDB.setName(data);
//		        FileDB.setType(newFileDB.getType());
//		        FileDB.setData(newFileDB.getData());
//		        return fileDBRepository.save(FileDB);
//		      })
//		      .orElseGet(() -> {
//		        newFileDB.setId(id);
//		        return fileDBRepository.save(newFileDB);
//		      });
//		
//	}
  
  
  
  public FileDB getFile(String id) {
    return fileDBRepository.findById(id).get();
  }
  
  public Stream<FileDB> getAllFiles() {
    return fileDBRepository.findAll().stream();
  }
  
//  public void deleteFileById(String id) {
//
//	    fileDBRepository.deleteById(id); 
//	    return  ;
//	  }
  
//  public String deleteFileById(String id) {
//	  String result = "";
//	  fileDBRepository.deleteById(id);
//	  result="specific record deleted";
//	  return result;
//	}
  public Stream<FileDB> deleteFileById(String id) {
	  fileDBRepository.deleteById(id);
	  return fileDBRepository.findAll().stream();
	}
  
  
  
  public String deleteAllFiles() {
		String result = "";
		try {	
			fileDBRepository.deleteAll();
			result = "all records deleted";
		}
		catch(Exception e) {
			result = "error occurred";
		}
		
		return result;
	}
}