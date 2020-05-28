import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {CarDTO} from '../../dtos/car-dto';
import {CarReport} from '../../model/car-report';

const httpOptions = {headers: new HttpHeaders({'Content-Type' : 'application/json'})};

@Injectable({
  providedIn: 'root'
})
export class AgentService {

  constructor(private http: HttpClient) { }

  public checkPasswordChanged() {
    return this.http.get<boolean>('/server/agent/checkPasswordChanged', httpOptions);
  }

  public getOwnersCars() {
    return this.http.get<CarDTO[]>('/server/agent/getOwnersCars', httpOptions);
  }

  public saveReport(report: CarReport){
    const body = JSON.stringify(report);
    return this.http.post('/server/agent/saveReport', body, httpOptions);
  }
}
