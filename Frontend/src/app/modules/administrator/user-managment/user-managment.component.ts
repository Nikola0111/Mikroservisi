import { Component, OnInit } from '@angular/core';
import {EndUserService} from '../../../services/EndUserService/end-user.service';
import {EndUser} from '../../../model/endUser';

@Component({
  selector: 'app-user-managment',
  templateUrl: './user-managment.component.html',
  styleUrls: ['./user-managment.component.css']
})
export class UserManagmentComponent implements OnInit {
  endUsers: EndUser[];
  constructor(private endUserService: EndUserService) { }

  ngOnInit() {
    this.endUserService.getRegisteredUsers().subscribe(data => {
      this.endUsers = data;
      console.log(this.endUsers);
    });
  }

  deactivate(endUser: EndUser) {
    this.endUserService.deactivate(endUser.jmbg).subscribe(data =>
      this.endUserService.getRegisteredUsers().subscribe(users => this.endUsers = users));
  }

  block(endUser: EndUser) {
    endUser.blocked = true;
    this.endUserService.block(endUser.jmbg).subscribe();
  }

  unblock(endUser: EndUser) {
    endUser.blocked = false;
    this.endUserService.unblock(endUser.jmbg).subscribe();
  }
}
