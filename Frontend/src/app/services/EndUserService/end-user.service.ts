import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {EndUser} from '../../model/endUser';

const httpOptions = {headers: new HttpHeaders({'Content-Type' : 'application/json'})};


@Injectable({
  providedIn: 'root'
})
export class EndUserService {

  constructor(private http: HttpClient) { }

  public getUnregistered() {
    console.log('getuje neregistrovane');
    return this.http.get<EndUser[]>('/server/authentication/getUnregistered', httpOptions);
  }

  public getAdminUnregistered(){
    return this.http.get<EndUser[]>('/server/authentication/getAdminUnregistered', httpOptions);
  }

  public confirm(id: number){
    return this.http.post<string>('/server/authentication/accept', id, httpOptions);
  }

  public reject(id: number) {
    return this.http.post<string>('/server/authentication/reject', id, httpOptions);
  }

  public getRegisteredUsers() {
    return this.http.get<EndUser[]>('/server/authentication/getRegisteredUsers', httpOptions);
  }

  public deactivate(jmbg: string) {
    return this.http.post(`/server/authentication/deactivate/${jmbg}`, httpOptions);
  }

  public block(jmbg: string) {
    return this.http.post(`/server/authentication/block/${jmbg}`, httpOptions);
  }

  public unblock(jmbg: string) {
    return this.http.post(`/server/authentication/unblock/${jmbg}`, httpOptions);
  }
}
