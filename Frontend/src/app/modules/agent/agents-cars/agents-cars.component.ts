import { Component, OnInit } from '@angular/core';
import {AgentService} from '../../../services/AgentService/agent.service';
import {SessionService} from '../../../services/SessionService/session.service';
import {CarDTO} from '../../../dtos/car-dto';
import {Router} from '@angular/router';
import {AdvertisementService} from '../../../services/advertisement.service/advertisement.service';
import {AdvertisementReportDTO} from '../../../dtos/AdvertisementReportDTO';

@Component({
  selector: 'app-agents-cars',
  templateUrl: './agents-cars.component.html',
  styleUrls: ['./agents-cars.component.css']
})
export class AgentsCarsComponent implements OnInit {
  cars: AdvertisementReportDTO[];
  constructor(private advertisementService: AdvertisementService, private sessionService: SessionService, private router: Router) { }

  ngOnInit() {
    this.advertisementService.getOwnersCars(this.sessionService.ulogovaniKorisnik.id).subscribe(data => {
      this.cars = data;
      console.log(this.cars);
    });
  }

  enterReport(car: AdvertisementReportDTO) {
    this.sessionService.bookingID = car.bookingID;
    this.router.navigate(['/report', car.id]);
  }
}
