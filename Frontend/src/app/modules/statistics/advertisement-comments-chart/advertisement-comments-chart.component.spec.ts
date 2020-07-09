import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvertisementCommentsChartComponent } from './advertisement-comments-chart.component';

describe('AdvertisementCommentsChartComponent', () => {
  let component: AdvertisementCommentsChartComponent;
  let fixture: ComponentFixture<AdvertisementCommentsChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdvertisementCommentsChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdvertisementCommentsChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
