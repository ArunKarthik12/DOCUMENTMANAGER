import { Component, OnInit } from '@angular/core';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FileUploadService } from 'src/app/services/file-upload.service';
import { Doc } from 'src/app/doc';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {


  fileInfos?: Observable<any>;

  constructor(private uploadService: FileUploadService) { }

  ngOnInit(): void {
    this.fileInfos = this.uploadService.getFiles();
  }

  edit(id:string){
    console.log("edited");
    console.log(id);
    // this.showPrompt(id);
    var DocumentName = prompt("Rename the Document : ");
    console.log(DocumentName);
    // this.uploadService.update(id,this.DocumentName);
  }

  deletepost(id:string){
    console.log("delete");

    this.uploadService.delval(id).subscribe(data=>{
      document.location.reload();
    })
    
  }


  
}
