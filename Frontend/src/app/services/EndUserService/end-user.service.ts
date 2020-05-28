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
    return this.http.get<EndUser[]>('server/enduser/getUnregistered', httpOptions);
  }

  public getAdminUnregistered(){
    return this.http.get<EndUser[]>('server/enduser/getAdminUnregistered', httpOptions);
  }

  public confirm(id: number){
    return this.http.post<string>('server/enduser/accept', id, httpOptions);
  }

  public reject(id: number) {
    return this.http.post<string>('server/enduser/reject', id, httpOptions);
  }

  public getRegisteredUsers() {
    return this.http.get<EndUser[]>('/server/enduser/getRegisteredUsers', httpOptions);
  }

  public deactivate(jmbg: string) {
    return this.http.post(`/server/enduser/deactivate/${jmbg}`, httpOptions);
  }

  public block(jmbg: string) {
    return this.http.post(`/server/enduser/block/${jmbg}`, httpOptions);
  }

  public unblock(jmbg: string) {
    return this.http.post(`/server/enduser/unblock/${jmbg}`, httpOptions);
  }
}
