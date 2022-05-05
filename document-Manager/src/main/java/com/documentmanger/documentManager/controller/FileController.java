package com.documentmanger.documentManager.controller;

//import java.awt.PageAttributes.MediaType;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.documentmanger.documentManager.service.FileStorageService;
import com.documentmanger.documentManager.model.Doc;
import com.documentmanger.documentManager.message.ResponseFile;
import com.documentmanger.documentManager.message.ResponseMessage;
import com.documentmanger.documentManager.model.FileDB;
import com.documentmanger.documentManager.repository.docrepository;

@RestController
@CrossOrigin("http://localhost:4200")
public class FileController {
  @Autowired
  private FileStorageService storageService;
  private docrepository docrepo;
  String f_id;
  
  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
     String message = "";
    try {
      storageService.store(file);
//      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      message = "Uploaded the file successfully";
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
//      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
        message = "Could not upload the file !";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }
  
  @PostMapping("/upload/doc")
  public ResponseEntity<ResponseMessage> docAdd(@RequestBody Doc newDoc) {
     String message = "";
    try {
//      storageService.storedoc(newDoc);
//      message = "Uploaded the file successfully: " + file.getOriginalFilename();
//    	Doc doc = new Doc(newDoc.getName(),newDoc.getDesc());
  	    docrepo.save(newDoc);
      message = "Uploaded the file successfully";
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
//      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
        message = "Could not upload the file !";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }
//  @PutMapping("/update/{id}")
//  public ResponseEntity<ResponseMessage> updateDoc(@RequestBody FileDB newFileDB, @PathVariable String id) {
//      String data="";
//	  String message = "";
//    try {
//    	storageService.update(newFileDB, id);
//        message = "Uploaded the file successfully: " + newFileDB.getName();
//        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
//      } catch (Exception e) {
//        message = "Could not upload the file: " + newFileDB.getName() + "!";
//        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
//      }
//  }

  
  
//  @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//  @ResponseStatus(HttpStatus.OK)
//  public ResponseEntity<ResponseMessage> updateVehicle(@PathVariable String id,
//		  @RequestParam("file") MultipartFile file){
//      return new ResponseEntity<>(vehicleCommandService.updateVehicle(id, ), HttpStatus.OK);
//  }
//  
  
  
  
  @GetMapping("/files")
  public ResponseEntity<List<ResponseFile>> getListFiles() {
    List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {	
      String fileDownloadUri = ServletUriComponentsBuilder
          .fromCurrentContextPath()
          .path("/files/")
          .path(dbFile.getId())
          .toUriString();
//           String f_id = dbFile.getId();
//           System.out.println(f_id);
String fileName = dbFile.getName();
    int pos = fileName.lastIndexOf(".");
    if (pos > 0 && pos < (fileName.length() - 1)) { // If '.' is not the first or last character.
        fileName = fileName.substring(0, pos);
    }
      
      return new ResponseFile(
    	  dbFile.getId(),
//          dbFile.getName(),
    	  fileName,
          fileDownloadUri,
          dbFile.getType(),
          dbFile.getData().length);
    }).collect(Collectors.toList());
   
    return ResponseEntity.status(HttpStatus.OK).body(files);
  }
  @GetMapping("/files/{id}")
  public ResponseEntity<byte[]> getFile(@PathVariable String id) {
    FileDB fileDB = storageService.getFile(id);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
        .body(fileDB.getData());
  }
  @DeleteMapping("/deleteall")
  public ResponseEntity<String> deleteStatus() {
	 String status = storageService.deleteAllFiles(); 
	 return ResponseEntity.status(HttpStatus.OK).body(status);
  }
//  @DeleteMapping("/deleteById/{id}")
//  public ResponseEntity<String> deleteById(@PathVariable String id) {
//	 String stat = storageService.deleteFileById(id); 
////     String f_id = dbFile.getId();
//	 return ResponseEntity.status(HttpStatus.OK).body(stat);
//  }
  @DeleteMapping("/deleteById/{id}")
  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable String id) {
    try {
    	storageService.deleteFileById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
//  @PutMapping("edit")
////	public Laptop updateLaptop(@RequestBody Laptop lap) {
////		return laprepo.save(lap);
////	}
//  public ResponseEntity<ResponseMessage> update(@RequestParam("file") MultipartFile file) {
//	     String message = "";
//	    try {
//	      storageService.up(file);
//	      message = "Uploaded the file successfully: " + file.getOriginalFilename();
//	      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
//	    } catch (Exception e) {
//	      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
//	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
//	    }
//	  }
  
  
}