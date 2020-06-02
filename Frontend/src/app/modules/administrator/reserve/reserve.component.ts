import { Component, OnInit } from '@angular/core';
import {AdvertisementService} from '../../../services/advertisement.service/advertisement.service';
import {Advertisement} from '../../../model/advertisement';
import {FormBuilder, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-reserve',
  templateUrl: './reserve.component.html',
  styleUrls: ['./reserve.component.css']
})
export class ReserveComponent implements OnInit {

  advertisements: Advertisement[];
  adv: Advertisement = new Advertisement();
  timeFrom: Date;
  timeTo: Date;
  reserveForm: FormGroup;
  adName: string;

  constructor(private advertisementService: AdvertisementService, private formBuilder: FormBuilder) {
    this.advertisementService.getAll().subscribe(
      data => {
        this.advertisements = data;
      });
   }

  ngOnInit() {

    this.reserveForm = this.formBuilder.group({
      advSelect: [''],
      timeFrom: [''],
      timeTo: ['']
    });

  }

  onSubmit() {
    for(let i = 0; i < this.advertisements.length; i++) {
      if(this.advertisements[i].name === this.adName) {
        this.adv = this.advertisements[i];
      }
    }
    this.adv.reservedFrom = this.timeFrom;
    this.adv.reservedTo = this.timeTo;
    this.advertisementService.update(this.adv).subscribe();
  }

}