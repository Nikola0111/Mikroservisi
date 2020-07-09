import { Component, OnInit } from '@angular/core';
import { Chart } from 'chart.js';
import {Advertisement} from '../../../model/advertisement';
import {AdvertisementService} from '../../../services/advertisement.service/advertisement.service';
import {SessionService} from '../../../services/SessionService/session.service';

@Component({
  selector: 'app-max-travelled-chart',
  templateUrl: './max-travelled-chart.component.html',
  styleUrls: ['./max-travelled-chart.component.css']
})
export class MaxTravelledChartComponent implements OnInit {
  advertisements: Array<Advertisement>;
  advertisementNames: any;
  advertisementsTravelled: any;

  constructor( private advertisementService: AdvertisementService,
               private  sessionService: SessionService) {

  }

  ngOnInit() {
    this.advertisements = new Array<Advertisement>();
    this.advertisementNames = new Array<string>();
    this.advertisementsTravelled = new Array<number>();
    const ctx = document.getElementById('myChart');
    
    this.advertisementService.getAllByPostedBy(this.sessionService.ulogovaniKorisnik.id).subscribe(
      data => {
     this.advertisements = data;
     for (let i = 0; i < data.length; i ++) {
          this.advertisementNames[i] = data[i].name;
        }
        for (let i = 0; i < data.length; i ++) {
          this.advertisementsTravelled[i] = data[i].travelled;
        }

        const myChart = new Chart(ctx, {
          type: 'bar',
          data: {
            labels: this.advertisementNames,
            datasets: [{
              label: 'Travelled',
              data: this.advertisementsTravelled,
              backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)'
              ],
              borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)'
              ],
              borderWidth: 1
            }]
          },
          options: {
            scales: {
              yAxes: [{
                ticks: {
                  beginAtZero: true
                }
              }]
            }
          }
        });
      }
    );

    // this.advertisementService.getAll().subscribe(
    //   data => {
    //     this.advertisements = data;
  } 

}
