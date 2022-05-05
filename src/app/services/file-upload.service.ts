import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Doc } from '../doc';

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {

  public dc = {} as Doc
  desc:Doc = this.dc
  constructor(private http: HttpClient) { }

  private baseUrl = 'http://localhost:8080';
  
  upload(file: File): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();
    formData.append('file', file);

    const req = new HttpRequest('POST', `${this.baseUrl}/upload`, formData,{
      reportProgress: true,
      responseType: 'json'
    });
    
    return this.http.request(req);
  }
  


  getFiles(): Observable<any> {
    return this.http.get(`${this.baseUrl}/files`);
  }
  
  
  delval(id:string){
    console.log("del service");
    return this.http.delete(`${this.baseUrl}/deleteById/${id}`);   
  }
}