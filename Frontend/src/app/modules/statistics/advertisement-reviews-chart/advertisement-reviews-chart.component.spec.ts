import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvertisementReviewsChartComponent } from './advertisement-reviews-chart.component';

describe('AdvertisementReviewsChartComponent', () => {
  let component: AdvertisementReviewsChartComponent;
  let fixture: ComponentFixture<AdvertisementReviewsChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdvertisementReviewsChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdvertisementReviewsChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
