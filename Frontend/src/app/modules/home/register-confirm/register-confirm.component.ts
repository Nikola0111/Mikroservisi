import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {RegisterService} from '../../../services/RegisterService/register.service';

@Component({
  selector: 'app-register-confirm',
  templateUrl: './register-confirm.component.html',
  styleUrls: ['./register-confirm.component.css']
})
export class RegisterConfirmComponent implements OnInit {

  constructor(private router: Router, private registerService: RegisterService) { }

  ngOnInit() {
    const url = this.router.url;
    const splitted = url.split('=');
    this.registerService.verify(splitted[1]).subscribe(data => {
      console.log('treba da ode na drugu stranicu')
      this.router.navigate(['/homepage']);
    }, error => {
      this.router.navigate(['/homepage']);
      if (error.error === 1) {
        alert('Greska pri registraciji');
      } else if(error.error === 2){
        alert('Isteklo je 7 dana, registrujte se opet');
      } else {
        alert('Nepoznata greska');
      }
    });
  }

}
