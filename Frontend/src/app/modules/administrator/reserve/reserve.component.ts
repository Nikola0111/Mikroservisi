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

  constructor(private advertisementService: AdvertisementService, private formBuilder: FormBuilder) { }

  ngOnInit() {

    this.reserveForm = this.formBuilder.group({
      timeFrom: [''],
      timeTo: ['']
    });

    this.advertisementService.getAll().subscribe(
      data => {
        this.advertisements = data;
      });
  }

  onSubmit() {
    this.adv.reservedFrom = this.timeFrom;
    this.adv.reservedTo = this.timeTo;
    this.advertisementService.update(this.adv);
  }

}